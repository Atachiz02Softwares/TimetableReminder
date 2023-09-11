package morpheus.softwares.timetablereminder.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import morpheus.softwares.timetablereminder.R;
import morpheus.softwares.timetablereminder.fragments.Courses;
import morpheus.softwares.timetablereminder.fragments.Home;
import morpheus.softwares.timetablereminder.fragments.Profile;
import morpheus.softwares.timetablereminder.models.Database;
import morpheus.softwares.timetablereminder.models.Link;
import morpheus.softwares.timetablereminder.models.User;

public class SignupActivity extends AppCompatActivity {
    EditText userName, passWord, confirmPassWord;
    Button signUp;
    TextView signInOption;

    ArrayList<User> users;
    Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        userName = findViewById(R.id.username);
        passWord = findViewById(R.id.password);
        confirmPassWord = findViewById(R.id.confirmPassword);
        signUp = findViewById(R.id.signup);
        signInOption = findViewById(R.id.signInOption);

        database = new Database(this);
        users = database.selectAllUsers();

        for (User user : users) {
            if (user.getStatus().equals(getString(R.string.online))) {
                Bundle bundle = new Bundle();
                bundle.putString("username", user.getUsername());
                new Profile().setArguments(bundle);
                new Home().setArguments(bundle);
                new Courses().setArguments(bundle);

                startActivity(new Intent(this, MainActivity.class).putExtra("username", user.getUsername()));
                finish();
                break;
            }
        }

        signUp.setOnClickListener(v -> {
            String username = String.valueOf(userName.getText()),
                    password = String.valueOf(passWord.getText()),
                    confirmPassword = String.valueOf(confirmPassWord.getText());
            boolean hasSignedUp = new Link(this).hasSignedUp(username);

            if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(SignupActivity.this, "No field should be empty!",
                        Toast.LENGTH_SHORT).show();
            } else if (!TextUtils.equals(password, confirmPassword)) {
                Toast.makeText(SignupActivity.this, "Password mismatch!", Toast.LENGTH_SHORT).show();
            } else if (hasSignedUp) {
                Toast.makeText(SignupActivity.this, "You already have an account!\nSign in " +
                        "instead...", Toast.LENGTH_SHORT).show();
            } else {
                database.insertUser(new User(0, username, password, getString(R.string.online)));

                Bundle bundle = new Bundle();
                bundle.putString("username", username);
                new Profile().setArguments(bundle);
                new Home().setArguments(bundle);
                new Courses().setArguments(bundle);

                startActivity(new Intent(SignupActivity.this, MainActivity.class));
            }
        });

        signInOption.setOnClickListener(v ->
                startActivity(new Intent(SignupActivity.this, SigninActivity.class)));
    }
}