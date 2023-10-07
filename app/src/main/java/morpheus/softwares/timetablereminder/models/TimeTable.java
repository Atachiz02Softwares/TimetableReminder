package morpheus.softwares.timetablereminder.models;

public class TimeTable {
    private int id;
    private String courseCode, courseTitle, date, time;

    public TimeTable(int id, String courseCode, String courseTitle, String date, String time) {
        setId(id);
        setCourseCode(courseCode);
        setCourseTitle(courseTitle);
        setDate(date);
        setTime(time);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}