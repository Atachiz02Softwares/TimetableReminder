package morpheus.softwares.timetablereminder.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import morpheus.softwares.timetablereminder.R;
import morpheus.softwares.timetablereminder.fragments.Courses;
import morpheus.softwares.timetablereminder.fragments.Home;
import morpheus.softwares.timetablereminder.fragments.Profile;
import morpheus.softwares.timetablereminder.models.Database;
import morpheus.softwares.timetablereminder.models.User;

public class SigninActivity extends AppCompatActivity {
    EditText userName, passWord;
    Button signIn;

    ArrayList<User> users;
    Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        userName = findViewById(R.id.username);
        passWord = findViewById(R.id.password);
        signIn = findViewById(R.id.signin);

        database = new Database(this);
        users = database.selectAllUsers();

        signIn.setOnClickListener(v -> {
            for (User user : users) {
                String email = user.getUsername(), pin = user.getPassword(),
                        username = String.valueOf(userName.getText()), password = String.valueOf(passWord.getText());

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(this, "No field should be empty!", Toast.LENGTH_SHORT).show();
                    break;
                } else if (email.equals(username) && pin.equals(password)) {
                    Bundle bundle = new Bundle();
                    bundle.putString("username", username);
                    new Profile().setArguments(bundle);
                    new Home().setArguments(bundle);
                    new Courses().setArguments(bundle);

                    startActivity(new Intent(SigninActivity.this, MainActivity.class));
                    break;
                } else {
                    Toast.makeText(this, "Sign in credentials don't match!", Toast.LENGTH_SHORT).show();
                    break;
                }
            }
        });
    }
}