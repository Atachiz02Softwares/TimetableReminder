package morpheus.softwares.timetablereminder.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import morpheus.softwares.timetablereminder.R;
import morpheus.softwares.timetablereminder.models.Course;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseHolder> {
    Context context;
    ArrayList<Course> courses;

    public CourseAdapter(Context context, ArrayList<Course> courses) {
        this.context = context;
        this.courses = courses;
    }

    @NonNull
    @Override
    public CourseAdapter.CourseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.courses_view, parent, false);
        return new CourseHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseHolder holder, int position) {
        Course course = courses.get(position);

        holder.courseCode.setText(course.getCourseCode());
        holder.courseDate.setText(course.getDate());
        holder.courseTime.setText(course.getTime());
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    public static class CourseHolder extends RecyclerView.ViewHolder {
        TextView courseCode, courseDate, courseTime;

        public CourseHolder(@NonNull View itemView) {
            super(itemView);

            courseCode = itemView.findViewById(R.id.courseCode);
            courseDate = itemView.findViewById(R.id.courseDate);
            courseTime = itemView.findViewById(R.id.courseTime);
        }
    }
}