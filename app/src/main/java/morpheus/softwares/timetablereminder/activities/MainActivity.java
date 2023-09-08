package morpheus.softwares.timetablereminder.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;

import morpheus.softwares.timetablereminder.R;
import morpheus.softwares.timetablereminder.fragments.Courses;
import morpheus.softwares.timetablereminder.fragments.Home;
import morpheus.softwares.timetablereminder.fragments.Profile;
import morpheus.softwares.timetablereminder.models.Course;
import morpheus.softwares.timetablereminder.models.Database;

public class MainActivity extends AppCompatActivity {
    MaterialToolbar toolbar;
    FloatingActionButton floatingActionButton;
    BottomNavigationView bottomNavigationView;
    MaterialAlertDialogBuilder builder;
    AlertDialog alertDialog;
    Calendar calendar;

    Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        floatingActionButton = findViewById(R.id.fabAddCourse);
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        calendar = Calendar.getInstance();
        database = new Database(this);

        String username = getIntent().getStringExtra("username");

        setSupportActionBar(toolbar);
        bottomNavigationView.setSelectedItemId(R.id.navigation_home);   // Set the default selected item (optional)

        // Set the listener for item selection
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.navigation_profile) {
                setFragment(new Profile());
            } else if (itemId == R.id.navigation_home) {
                setFragment(new Home());
            } else if (itemId == R.id.navigation_courses) {
                setFragment(new Courses());
            }
            return true;
        });

        floatingActionButton.setOnClickListener(v -> {
            builder = new MaterialAlertDialogBuilder(MainActivity.this,
                    R.style.MaterialAlertDialogRounded);

            View view = getLayoutInflater().inflate(R.layout.add_course_dialog, null);
            EditText code = view.findViewById(R.id.courseCode), title = view.findViewById(R.id.courseTitle);
            DatePicker datePicker = view.findViewById(R.id.datePicker);
            TimePicker timePicker = view.findViewById(R.id.timePicker);
            Button add = view.findViewById(R.id.buttonAdd);

            String courseCode = String.valueOf(code.getText()), courseTitle = String.valueOf(title.getText());
            final String[] date = new String[1];
            final String[] time = new String[1];

            datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH), (view1, year, monthOfYear, dayOfMonth) ->
                            date[0] = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

            timePicker.setOnTimeChangedListener((view12, hourOfDay, minute) -> time[0] = hourOfDay + ":" + minute);

            add.setOnClickListener(v1 -> database.insertCourse(new Course(0, courseCode, courseTitle, date[0], time[0])));

            builder.setView(view);

            alertDialog = builder.create();
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.show();
        });
    }

    private void setFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, fragment).commit();
    }
}