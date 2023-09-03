package morpheus.softwares.timetablereminder.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import morpheus.softwares.timetablereminder.R;
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
                    startActivity(new Intent(SigninActivity.this, MainActivity.class)
                            .putExtra("username", username));
                    break;
                } else {
                    Toast.makeText(this, "Sign in credentials don't match!", Toast.LENGTH_SHORT).show();
                    break;
                }
            }
        });
    }
}