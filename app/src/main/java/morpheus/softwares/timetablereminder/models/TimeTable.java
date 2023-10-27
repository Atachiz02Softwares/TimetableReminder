package morpheus.softwares.timetablereminder.models;

public class TimeTable {
    private int id;
    private String courseCode, courseTitle;
    private long timeInMillis;

    public TimeTable(int id, String courseCode, String courseTitle, long timeInMillis) {
        setId(id);
        setCourseCode(courseCode);
        setCourseTitle(courseTitle);
        setTimeInMillis(timeInMillis);
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

    public long getTimeInMillis() {
        return timeInMillis;
    }

    public void setTimeInMillis(long timeInMillis) {
        this.timeInMillis = timeInMillis;
    }
}