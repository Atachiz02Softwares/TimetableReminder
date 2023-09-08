package morpheus.softwares.timetablereminder.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import morpheus.softwares.timetablereminder.R;
import morpheus.softwares.timetablereminder.models.Database;
import morpheus.softwares.timetablereminder.models.UserProfile;

public class Profile extends Fragment {
    Database database;
    AlertDialog alertDialog;
    MaterialAlertDialogBuilder builder;

    public Profile() {
        database = new Database(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.profile, container, false);

        TextView idNumber = view.findViewById(R.id.idNumber), studentName = view.findViewById(R.id.name),
                email = view.findViewById(R.id.email), lvl = view.findViewById(R.id.level),
                dept = view.findViewById(R.id.department);
        FloatingActionButton floatingActionButton = view.findViewById(R.id.fabAddProfile);

        assert getArguments() != null;
        String username = getArguments().getString("username");

        ArrayList<UserProfile> userProfiles = database.selectAllUserProfiles();
        for (UserProfile userProfile : userProfiles) {
            if (userProfile.getUsername().equals(username)) {
                idNumber.setText(userProfile.getIdNumber());
                studentName.setText(userProfile.getName());
                email.setText(userProfile.getName());
                lvl.setText(userProfile.getLevel());
                dept.setText(userProfile.getDepartment());
                break;
            }
        }

        floatingActionButton.setOnClickListener(v -> {
            builder = new MaterialAlertDialogBuilder(requireContext(), R.style.MaterialAlertDialogRounded);

            View dialogView = getLayoutInflater().inflate(R.layout.add_profile_dialog, null);
            EditText addID = dialogView.findViewById(R.id.addID), addEmail = dialogView.findViewById(R.id.addEmail),
                    addName = dialogView.findViewById(R.id.addName), addDepartment = dialogView.findViewById(R.id.addDepartment),
                    addLevel = dialogView.findViewById(R.id.addLevel);
            Button add = dialogView.findViewById(R.id.addProfile);

            String id = String.valueOf(addID.getText()), mail = String.valueOf(addEmail.getText()),
                    name = String.valueOf(addName.getText()), level = String.valueOf(addLevel.getText()),
                    department = String.valueOf(addDepartment.getText());

            add.setOnClickListener(v1 -> {
                database.insertUserProfile(new UserProfile(0, username, id, mail, name, level, department));
                alertDialog.dismiss();
            });

            builder.setView(dialogView);

            alertDialog = builder.create();
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.show();
        });

        return view;
    }
}