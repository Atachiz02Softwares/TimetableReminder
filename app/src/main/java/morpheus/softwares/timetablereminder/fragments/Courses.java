package morpheus.softwares.timetablereminder.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import morpheus.softwares.timetablereminder.R;
import morpheus.softwares.timetablereminder.adapters.CourseAdapter;
import morpheus.softwares.timetablereminder.models.Course;
import morpheus.softwares.timetablereminder.models.Database;

public class Courses extends Fragment {
    Database database;
    ArrayList<Course> courses;
    CourseAdapter courseAdapter;
    RecyclerView recyclerView;

    public Courses() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.courses, container, false);

        database = new Database(getContext());

        courses = database.selectAllCourses();
        recyclerView = view.findViewById(R.id.coursesRecycler);
        courseAdapter = new CourseAdapter(getContext(), courses);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(courseAdapter);

        return view;
    }
}