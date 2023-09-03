package morpheus.softwares.timetablereminder.activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import morpheus.softwares.timetablereminder.R;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Set the listener for item selection
        NavigationBarView.OnItemSelectedListener((NavigationBarView.OnItemSelectedListener) item -> {
            if (item.getItemId() == R.id.navigation_profile) {
                Toast.makeText(getApplicationContext(), "Profile", Toast.LENGTH_SHORT).show();
                return true;
            } else if (item.getItemId() == R.id.navigation_home) {
                Toast.makeText(getApplicationContext(), "Home", Toast.LENGTH_SHORT).show();
                return true;
            } else if (item.getItemId() == R.id.navigation_courses) {
                Toast.makeText(getApplicationContext(), "Courses", Toast.LENGTH_SHORT).show();
                return true;
            }
            return false;
        });

        // Set the default selected item (optional)
        bottomNavigationView.setSelectedItemId(R.id.navigation_home);
    }
}