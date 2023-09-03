package morpheus.softwares.timetablereminder.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import morpheus.softwares.timetablereminder.R;

public class Profile extends Fragment {
    public Profile() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.profile, container, false);

//        Button button = view.findViewById(R.id.button);

        return view;
    }
}