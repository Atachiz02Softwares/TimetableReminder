package morpheus.softwares.timetablereminder.models;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "timetable.db",
            TABLE_USERS = "users", TABLE_COURSES = "courses", TIME_TABLE = "timetable", TABLE_PROFILE = "profile";
    public static final int DATABASE_VERSION = 1;

    public Database(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE users (id integer PRIMARY KEY AUTOINCREMENT, username text, " +
                "password text, status text)");
        db.execSQL("CREATE TABLE courses (id integer PRIMARY KEY AUTOINCREMENT, course_code " +
                "text, course_title text, date text, time text)");
        db.execSQL("CREATE TABLE timetable (id integer PRIMARY KEY AUTOINCREMENT, course_code " +
                "text, course_title text, date text, time text)");
        db.execSQL("CREATE TABLE profile (id integer PRIMARY KEY AUTOINCREMENT, username text, id_number text, " +
                "email text, name text, level text, department text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COURSES);
        db.execSQL("DROP TABLE IF EXISTS " + TIME_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROFILE);
        onCreate(db);
    }

    public void insertUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sqlInsert = "INSERT INTO " + TABLE_USERS;
        sqlInsert += " values( null, '" + user.getUsername() + "', '" + user.getPassword() + "', " +
                "'" + user.getStatus() + "' )";
        db.execSQL(sqlInsert);
        db.close();
    }

    public void insertCourse(Course course) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sqlInsert = "INSERT INTO " + TABLE_COURSES;
        sqlInsert += " values( null, '" + course.getCourseCode() + "', '" + course.getCourseTitle() +
                "', '" + course.getDate() + "', '" + course.getTime() + "' )";
        db.execSQL(sqlInsert);
        db.close();
    }

    public void insertTimeTable(TimeTable timeTable) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sqlInsert = "INSERT INTO " + TIME_TABLE;
        sqlInsert += " values( null, '" + timeTable.getCourseCode() + "', '" + timeTable.getCourseTitle() +
                "', '" + timeTable.getDate() + "', '" + timeTable.getTime() + "' )";
        db.execSQL(sqlInsert);
        db.close();
    }

    public void insertUserProfile(UserProfile userProfile) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sqlInsert = "INSERT INTO " + TABLE_PROFILE;
        sqlInsert += " values( null, '" + userProfile.getUsername() + "', '" + userProfile.getIdNumber() +
                "', '" + userProfile.getEmail() + "', '" + userProfile.getName() + "', '" +
                userProfile.getLevel() + "', '" + userProfile.getDepartment() + "' )";
        db.execSQL(sqlInsert);
        db.close();
    }

    public ArrayList<User> selectAllUsers() {
        String sqlQuery = "SELECT * FROM " + TABLE_USERS;

        SQLiteDatabase db = this.getWritableDatabase();
        @SuppressLint("Recycle")
        Cursor cursor = db.rawQuery(sqlQuery, null);

        ArrayList<User> users = new ArrayList<>();
        while (cursor.moveToNext()) {
            User currentUser = new User(cursor.getInt(0), cursor.getString(1),
                    cursor.getString(2), cursor.getString(3));
            users.add(currentUser);
        }

        db.close();
        return users;
    }

    public ArrayList<Course> selectAllCourses() {
        String sqlQuery = "SELECT * FROM " + TABLE_COURSES;

        SQLiteDatabase db = this.getWritableDatabase();
        @SuppressLint("Recycle")
        Cursor cursor = db.rawQuery(sqlQuery, null);

        ArrayList<Course> courses = new ArrayList<>();
        while (cursor.moveToNext()) {
            Course currentCourse = new Course(cursor.getInt(0),
                    cursor.getString(1), cursor.getString(2),
                    cursor.getString(3), cursor.getString(4));
            courses.add(currentCourse);
        }

        db.close();
        return courses;
    }

    public Course getLastCourse() {
        SQLiteDatabase db = this.getReadableDatabase();
        Course lastCourse = null;

        String sqlQuery = "SELECT * FROM " + TABLE_COURSES + " ORDER BY id DESC LIMIT 1";
        Cursor cursor = db.rawQuery(sqlQuery, null);

        if (cursor != null && cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex("id");
            int courseCodeIndex = cursor.getColumnIndex("course_code");
            int courseTitleIndex = cursor.getColumnIndex("course_title");
            int dateIndex = cursor.getColumnIndex("date");
            int timeIndex = cursor.getColumnIndex("time");

            lastCourse = new Course(
                    cursor.getInt(idIndex),
                    cursor.getString(courseCodeIndex),
                    cursor.getString(courseTitleIndex),
                    cursor.getString(dateIndex),
                    cursor.getString(timeIndex)
            );
            cursor.close();
        }

        db.close();
        return lastCourse;
    }

    public ArrayList<TimeTable> selectAllTimeTables() {
        String sqlQuery = "SELECT * FROM " + TIME_TABLE;

        SQLiteDatabase db = this.getWritableDatabase();
        @SuppressLint("Recycle")
        Cursor cursor = db.rawQuery(sqlQuery, null);

        ArrayList<TimeTable> timeTables = new ArrayList<>();
        while (cursor.moveToNext()) {
            TimeTable currentTimeTable = new TimeTable(cursor.getInt(0),
                    cursor.getString(1), cursor.getString(2),
                    cursor.getString(3), cursor.getString(4));
            timeTables.add(currentTimeTable);
        }

        db.close();
        return timeTables;
    }

    public ArrayList<UserProfile> selectAllUserProfiles() {
        String sqlQuery = "SELECT * FROM " + TABLE_PROFILE;

        SQLiteDatabase db = this.getWritableDatabase();
        @SuppressLint("Recycle")
        Cursor cursor = db.rawQuery(sqlQuery, null);

        ArrayList<UserProfile> userProfiles = new ArrayList<>();
        while (cursor.moveToNext()) {
            UserProfile userProfile = new UserProfile(cursor.getInt(0), cursor.getString(1),
                    cursor.getString(2), cursor.getString(3), cursor.getString(4),
                    cursor.getString(5), cursor.getString(6));
            userProfiles.add(userProfile);
        }

        db.close();
        return userProfiles;
    }

    public void deleteUser(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USERS, "email=?", new String[]{email});
        db.close();
    }

    public void deleteCourse(String courseCode) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_COURSES, "course_code=?", new String[]{courseCode});
        db.close();
    }

    public void deleteTimeTable(String courseCode) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TIME_TABLE, "course_code=?", new String[]{courseCode});
        db.close();
    }

    public void deleteUserProfile(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PROFILE, "course_code=?", new String[]{username});
        db.close();
    }

    public void updateCourseTime(String courseCode, String newDate, String newTime) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("date", newDate);
        values.put("time", newTime);
        db.update(TABLE_COURSES, values, "course_code=?", new String[]{courseCode});
        db.close();
    }

    public void updateTimeTable(String courseCode, String newDate, String newTime) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("date", newDate);
        values.put("time", newTime);
        db.update(TIME_TABLE, values, "course_code=?", new String[]{courseCode});
        db.close();
    }

    public void updateStatus(String username, String newStatus) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("status", newStatus);
        db.update(TABLE_USERS, values, "username=?", new String[]{username});
        db.close();
    }
}