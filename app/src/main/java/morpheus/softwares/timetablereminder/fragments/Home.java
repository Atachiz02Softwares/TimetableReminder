package morpheus.softwares.timetablereminder.fragments;

import static android.content.Context.NOTIFICATION_SERVICE;
import static morpheus.softwares.timetablereminder.models.AlarmReceiver.CHANNEL_ID;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

import morpheus.softwares.timetablereminder.R;
import morpheus.softwares.timetablereminder.activities.MainActivity;
import morpheus.softwares.timetablereminder.models.AlarmReceiver;
import morpheus.softwares.timetablereminder.models.Course;
import morpheus.softwares.timetablereminder.models.Database;
import morpheus.softwares.timetablereminder.models.TimeTable;

public class Home extends Fragment {
    Database database;
    AlertDialog alertDialog;
    MaterialAlertDialogBuilder builder;
    Calendar calendar;

    public Home() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.home, container, false);

        database = new Database(getContext());
        calendar = Calendar.getInstance();

        createNotificationChannel();

        TextView course_code = view.findViewById(R.id.courseCode),
                course_title = view.findViewById(R.id.courseTitle),
                course_date = view.findViewById(R.id.courseDate),
                course_time = view.findViewById(R.id.courseTime);

        Course nextScheduledCourse = getNextScheduledCourse();
        if (nextScheduledCourse != null) {
            course_code.setText(nextScheduledCourse.getCourseCode());
            course_title.setText(nextScheduledCourse.getCourseTitle());
            course_date.setText(nextScheduledCourse.getDate());
            course_time.setText(nextScheduledCourse.getTime());
        } else {
            course_code.setText(R.string.yay);
            course_title.setText("");
            course_date.setText("");
            course_time.setText("");
        }

        FloatingActionButton floatingActionButton = view.findViewById(R.id.fabAddCourse);
        floatingActionButton.setOnClickListener(v -> {
            builder = new MaterialAlertDialogBuilder(requireContext(), R.style.MaterialAlertDialogRounded);

            View dialogView = getLayoutInflater().inflate(R.layout.add_course_dialog, null);
            EditText code = dialogView.findViewById(R.id.courseCode), title = dialogView.findViewById(R.id.courseTitle);
            DatePicker datePicker = dialogView.findViewById(R.id.datePicker);
            TimePicker timePicker = dialogView.findViewById(R.id.timePicker);
            Button add = dialogView.findViewById(R.id.buttonAdd);

            final String[] date = new String[1];
            final String[] time = new String[1];

            datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH), (view1, year, monthOfYear, dayOfMonth) ->
                            date[0] = year + "/" + (monthOfYear + 1) + "/" + dayOfMonth);

            timePicker.setOnTimeChangedListener((view12, hourOfDay, minute) -> time[0] = hourOfDay + ":" + minute);

            add.setOnClickListener(v1 -> {
                String courseCode = String.valueOf(code.getText());
                String courseTitle = String.valueOf(title.getText());

                // Format the date in the desired format (e.g., "7th October, 2023")
                String[] dateParts = date[0].split("/");
                int year = Integer.parseInt(dateParts[0]);
                int month = Integer.parseInt(dateParts[1]);
                int day = Integer.parseInt(dateParts[2]);
                @SuppressLint("DefaultLocale")
                String formattedDate = String.format("%d%s %s, %d", day, getDayOfMonthSuffix(day), getMonthName(month), year);

                // Format the time including AM/PM
                String[] timeParts = time[0].split(":");
                int hour = Integer.parseInt(timeParts[0]);
                int minute = Integer.parseInt(timeParts[1]);
                String amPm = (hour < 12) ? "AM" : "PM";
                hour = (hour > 12) ? (hour - 12) : hour;
                @SuppressLint("DefaultLocale") String formattedTime = String.format("%02d:%02d %s", hour, minute, amPm);

                // Insert the course into the database
                database.insertCourse(new Course(0, courseCode, courseTitle, formattedDate, formattedTime));
//                database.insertCourse(new Course(0, courseCode, courseTitle, date[0], time[0]));

                // Create a calendar instance and set it to the selected date and time
                Calendar calendar = Calendar.getInstance();
                calendar.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(),
                        timePicker.getHour(), timePicker.getMinute(), 0);

                long alarmTime = calendar.getTimeInMillis();

                TimeTable timeTable = new TimeTable(0, courseCode, courseTitle, alarmTime);
                database.insertTimeTable(timeTable);

                // Set up the alarm using AlarmManager
                setAlarm(alarmTime, courseCode);

//                setAlarm(datePicker, timePicker, courseCode);

                alertDialog.dismiss();
            });

            builder.setView(dialogView);

            alertDialog = builder.create();
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.show();
        });

        return view;
    }

    private String getDayOfMonthSuffix(int day) {
        if (day >= 11 && day <= 13) {
            return "th";
        }
        switch (day % 10) {
            case 1:
                return "st";
            case 2:
                return "nd";
            case 3:
                return "rd";
            default:
                return "th";
        }
    }

    private String getMonthName(int month) {
        String[] months = {
                "January", "February", "March", "April", "May", "June", "July",
                "August", "September", "October", "November", "December"
        };
        return months[month - 1];
    }

    // Method to get the next scheduled course
    private Course getNextScheduledCourse() {
        // Get the current date and time
        Calendar currentCalendar = Calendar.getInstance();
        Date currentDate = currentCalendar.getTime();

        // Fetch all scheduled courses from the database
        ArrayList<Course> courses = database.selectAllCourses();

        // Create a list to store upcoming courses
        ArrayList<Course> upcomingCourses = new ArrayList<>();

        // Iterate through the courses and filter upcoming ones
        for (Course course : courses) {
            try {
                // Parse the date and time of the course
//                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.US);
                SimpleDateFormat dateFormat = new SimpleDateFormat("d MMMM, yyyy hh:mm a", Locale.US);
                Date courseDateTime = dateFormat.parse(course.getDate() + " " + course.getTime());

                // Compare with the current date and time
                assert courseDateTime != null;
                if (courseDateTime.after(currentDate)) {
                    upcomingCourses.add(course);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        // Sort the upcoming courses by date and time
        upcomingCourses.sort(Comparator.comparing(o -> {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.US);
                return dateFormat.parse(o.getDate() + " " + o.getTime());
            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            }
        }));

        // Get the first course in the sorted list, which is the next course
        if (!upcomingCourses.isEmpty()) {
            return upcomingCourses.get(0);
        }

        // If there are no upcoming courses, return null
        return null;
    }

    @SuppressLint("ScheduleExactAlarm")
    private void setAlarm(long alarmTime, String courseCode) {
        AlarmManager alarmManager = (AlarmManager) requireContext().getSystemService(Context.ALARM_SERVICE);
        Intent alarmIntent = new Intent(requireContext(), AlarmReceiver.class);
        alarmIntent.putExtra("courseCode", courseCode);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(requireContext(), 0, alarmIntent,
                PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        // Set the initial alarm
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, alarmTime, pendingIntent);

        // Calculate the time for the next occurrence (e.g., a week later)
        Calendar nextAlarmCalendar = Calendar.getInstance();
        nextAlarmCalendar.setTimeInMillis(alarmTime);
        nextAlarmCalendar.add(Calendar.DAY_OF_WEEK, 7); // Add one week

        long nextAlarmTime = nextAlarmCalendar.getTimeInMillis();

        // Schedule the next alarm
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, nextAlarmTime,
                AlarmManager.INTERVAL_DAY * 7, pendingIntent);

        // Create a notification to inform the user
        createNotification(courseCode);
    }

    private void createNotification(String courseCode) {
        Intent notificationIntent = new Intent(requireContext(), MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(requireContext(), 0,
                notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        // Build the notification
        Notification notification = new Notification.Builder(requireContext())
                .setContentTitle("Class Reminder")
                .setContentText(courseCode + " is starting soon!")
                .setSmallIcon(R.mipmap.ic_launcher_foreground) // Replace with your app's icon
                .setContentIntent(pendingIntent)
                .setAutoCancel(true) // Close the notification when the user taps it
                .build();

        NotificationManager notificationManager = (NotificationManager)
                requireContext().getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "ClassChannel";
            String description = "Class Channel for Alarm Manager";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            notificationChannel.setDescription(description);
            notificationChannel.enableVibration(true);
//            notificationChannel.setVibrationPattern(new long[] {1000,500,1000,500,1000,500});
            notificationChannel.setBypassDnd(true);

            NotificationManager notificationManager = requireContext().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);

//            SharedPreferences sh = getSharedPreferences("WordOfTheDay", Context.MODE_PRIVATE);
//            String wtd = sh.getString("word", wordOfDay);
//            scheduleDailyNotification(wtd);
        }
    }
}