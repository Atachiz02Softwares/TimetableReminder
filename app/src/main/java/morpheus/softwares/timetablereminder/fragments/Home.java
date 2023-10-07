package morpheus.softwares.timetablereminder.fragments;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

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
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import morpheus.softwares.timetablereminder.R;
import morpheus.softwares.timetablereminder.models.AlarmReceiver;
import morpheus.softwares.timetablereminder.models.Course;
import morpheus.softwares.timetablereminder.models.Database;
import morpheus.softwares.timetablereminder.models.TimeTable;

public class Home extends Fragment {
    Database database;
    AlertDialog alertDialog;
    MaterialAlertDialogBuilder builder;
    Calendar calendar;
    private int uniqueAlarmID = 0; // Used to assign unique IDs to alarms

    public Home() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.home, container, false);

        database = new Database(getContext());
        calendar = Calendar.getInstance();

        TextView course_code = view.findViewById(R.id.courseCode),
                course_title = view.findViewById(R.id.courseTitle),
                course_date = view.findViewById(R.id.courseDate),
                course_time = view.findViewById(R.id.courseTime);

//        TimeTable nextScheduledCourse = getNextScheduledCourse();
//        if (nextScheduledCourse != null) {
//            course_code.setText(nextScheduledCourse.getCourseCode());
//            course_title.setText(nextScheduledCourse.getCourseTitle());
//            course_date.setText(nextScheduledCourse.getDate());
//            course_time.setText(nextScheduledCourse.getTime());
//        }

        Course lastCourse = database.getLastCourse();
        if (lastCourse != null) {
            course_code.setText(lastCourse.getCourseCode());
            course_title.setText(lastCourse.getCourseTitle());
            course_date.setText(lastCourse.getDate());
            course_time.setText(lastCourse.getTime());
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
                @SuppressLint("DefaultLocale") String formattedDate =
                        String.format("%d%s %s, %d", day, getDayOfMonthSuffix(day), getMonthName(month), year);

                // Format the time including AM/PM
                String[] timeParts = time[0].split(":");
                int hour = Integer.parseInt(timeParts[0]);
                int minute = Integer.parseInt(timeParts[1]);
                String amPm = (hour < 12) ? "AM" : "PM";
                hour = (hour > 12) ? (hour - 12) : hour;
                @SuppressLint("DefaultLocale") String formattedTime = String.format("%02d:%02d %s", hour, minute, amPm);

                // Insert the course into the database
                database.insertCourse(new Course(0, courseCode, courseTitle, formattedDate, formattedTime));

                TimeTable timeTable = new TimeTable(0, courseCode, courseTitle, date[0], time[0]);
                database.insertTimeTable(timeTable);
                setAlarm(timeTable);    // Set an alarm for the course

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

    private void setAlarm(TimeTable timeTable) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd, hh:mm", Locale.US);
            Date courseDateTime = dateFormat.parse(timeTable.getDate() + ", " + timeTable.getTime());

            // Create a Calendar instance for the alarm time
            Calendar alarmCalendar = Calendar.getInstance();
            assert courseDateTime != null;
            alarmCalendar.setTime(courseDateTime);

            // Create an Intent for the alarm
            Intent alarmIntent = new Intent(requireContext(), AlarmReceiver.class);
            alarmIntent.putExtra("course_title", timeTable.getCourseTitle()); // Pass course title to BroadcastReceiver
            PendingIntent pendingIntent = PendingIntent.getBroadcast(requireContext(), uniqueAlarmID++,
                    alarmIntent, PendingIntent.FLAG_IMMUTABLE);

            // Get the AlarmManager and set the alarm using ELAPSED_REALTIME_WAKEUP
            AlarmManager alarmManager = (AlarmManager) requireContext().getSystemService(Context.ALARM_SERVICE);

            if (alarmManager != null) {
                long elapsedTime = alarmCalendar.getTimeInMillis() - System.currentTimeMillis();
                alarmManager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                        SystemClock.elapsedRealtime() + elapsedTime, pendingIntent);
            }
        } catch (SecurityException se) {
            // Handle the SecurityException here (e.g., show a message to the user)
            Toast.makeText(getContext(), se.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to get the next scheduled course
    private TimeTable getNextScheduledCourse() {
        // Get the current date and time
        Calendar currentCalendar = Calendar.getInstance();
        Date currentDate = currentCalendar.getTime();

        // Fetch all scheduled courses from the database
        ArrayList<TimeTable> timeTables = database.selectAllTimeTables();

        // Create a list to store upcoming courses
        ArrayList<TimeTable> upcomingCourses = new ArrayList<>();

        // Define a date format to parse course dates from the database
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd, hh:mm", Locale.US);

        // Iterate through the courses and filter upcoming ones
        for (TimeTable timeTable : timeTables) {
            try {
                // Parse the date and time of the course
                Date courseDateTime = dateFormat.parse(timeTable.getDate() + ", " + timeTable.getTime());

                // Compare with the current date and time
                assert courseDateTime != null;
                if (courseDateTime.after(currentDate)) {
                    upcomingCourses.add(timeTable);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        // Find the course with the earliest upcoming date and time
        TimeTable nextCourse = null;
        Date nextCourseDateTime = null;

        for (TimeTable timeTable : upcomingCourses) {
            try {
                Date courseDateTime = dateFormat.parse(timeTable.getDate() + ", " + timeTable.getTime());

                if (nextCourse == null || Objects.requireNonNull(courseDateTime).before(nextCourseDateTime)) {
                    nextCourse = timeTable;
                    nextCourseDateTime = courseDateTime;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return nextCourse;
    }
}