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
            TABLE_USERS = "users", TABLE_COURSES = "courses";
    public static final int DATABASE_VERSION = 1;

    public Database(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE users (id integer PRIMARY KEY AUTOINCREMENT, username text, password text)");
        db.execSQL("CREATE TABLE supervisors (id integer PRIMARY KEY AUTOINCREMENT, course_code " +
                "text, course_title text, date text, time text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COURSES);
        onCreate(db);
    }

    public void insertUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sqlInsert = "INSERT INTO " + TABLE_USERS;
        sqlInsert += " values( null, '" + user.getUsername() + "', '" + user.getPassword() + "' )";
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

    public ArrayList<User> selectAllUsers() {
        String sqlQuery = "SELECT * FROM " + TABLE_USERS;

        SQLiteDatabase db = this.getWritableDatabase();
        @SuppressLint("Recycle")
        Cursor cursor = db.rawQuery(sqlQuery, null);

        ArrayList<User> users = new ArrayList<>();
        while (cursor.moveToNext()) {
            User currentUser = new User(cursor.getInt(0), cursor.getString(1),
                    cursor.getString(2));
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

    public void updateCourseTime(String courseCode, String newDate, String newTime) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("date", newDate);
        values.put("time", newTime);
        db.update(TABLE_USERS, values, "course_code=?", new String[]{courseCode});
        db.close();
    }
}