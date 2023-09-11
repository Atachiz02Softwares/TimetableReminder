package morpheus.softwares.timetablereminder.activities;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import morpheus.softwares.timetablereminder.R;
import morpheus.softwares.timetablereminder.fragments.Courses;
import morpheus.softwares.timetablereminder.fragments.Home;
import morpheus.softwares.timetablereminder.fragments.Profile;
import morpheus.softwares.timetablereminder.models.Database;

public class MainActivity extends AppCompatActivity {
    MaterialToolbar toolbar;
    BottomNavigationView bottomNavigationView;

    MaterialAlertDialogBuilder builder;
    AlertDialog alertDialog;

    Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
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

        // TODO
        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.toolbar) {
                builder = new MaterialAlertDialogBuilder(MainActivity.this, R.style.MaterialAlertDialogRounded);

                builder.setIcon(R.drawable.round_logout_24)
                        .setMessage("Are you sure you want to sign out?")
                        .setPositiveButton("Sign Out", (dialog, which) -> {
                            database.updateStatus(username, getString(R.string.offline));
                            finishAffinity();
                        })
                        .setNegativeButton("Cancel", (dialog, which) -> alertDialog.dismiss()).show();

                alertDialog = builder.create();
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.show();
            }

            return false;
        });
    }

    private void setFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, fragment).commit();
    }
}