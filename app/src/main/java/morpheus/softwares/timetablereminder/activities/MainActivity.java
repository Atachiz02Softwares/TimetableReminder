package morpheus.softwares.timetablereminder.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
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

        setFragment(new Home());

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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbar_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.signout);
        final ImageButton signOut = (ImageButton) MenuItemCompat.getActionView(menuItem);

        signOut.setOnClickListener(v -> {
            String id = getIntent().getStringExtra("id");
            builder = new MaterialAlertDialogBuilder(MainActivity.this, R.style.MaterialAlertDialogRounded);

            builder.setIcon(R.drawable.round_logout_24)
                    .setMessage("Are you sure you want to sign out?")
                    .setPositiveButton("Sign Out", (dialog, which) -> {
                        database.updateStatus(id, getString(R.string.offline));
                        finishAffinity();
                    })
                    .setNegativeButton("Cancel", (dialog, which) -> alertDialog.dismiss()).show();

            alertDialog = builder.create();
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.show();
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void setFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, fragment).commit();
    }
}