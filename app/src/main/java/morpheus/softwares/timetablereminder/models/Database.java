//package morpheus.softwares.timetablereminder.models;
//
//import android.annotation.SuppressLint;
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//
//import androidx.annotation.Nullable;
//
//import java.util.ArrayList;
//
//public class Database extends SQLiteOpenHelper {
//    public static final String DATABASE_NAME = "timetable.db",
//            TABLE_USERS = "users", TABLE_STUDENTS = "students", TABLE_SUPERVISORS = "supervisors",
//            TABLE_COORDINATOR = "coordinator", TABLE_PROJECTS = "projects", TABLE_FILES = "files",
//            FILE_NAME = "file_name", FILE_DATA = "file_data";
//    public static final int DATABASE_VERSION = 1;
//
//    public Database(@Nullable Context context) {
//        super(context, DATABASE_NAME, null, DATABASE_VERSION);
//    }
//
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        db.execSQL("CREATE TABLE users (id integer PRIMARY KEY AUTOINCREMENT, email " +
//                "text, pin text, name text, role text, status text, onlineOffline text)");
//        db.execSQL("CREATE TABLE students (id integer PRIMARY KEY AUTOINCREMENT, id_number " +
//                "text, email text, first_project text, second_project text, " +
//                "third_project text, first_area text, second_area text, third_area text, " +
//                "grouping text, date text, first_status text, second_status text, third_status " +
//                "text, first_report text, second_report text, third_report text)");
//        db.execSQL("CREATE TABLE supervisors (id integer PRIMARY KEY AUTOINCREMENT, name text, " +
//                "phone_number text, email text, area text)");
//        db.execSQL("CREATE TABLE coordinator (id integer PRIMARY KEY AUTOINCREMENT, name text, phone_number text, email text)");
//        db.execSQL("CREATE TABLE projects (id integer PRIMARY KEY AUTOINCREMENT, email text, approved_topic text)");
//        db.execSQL("CREATE TABLE files (id integer PRIMARY KEY AUTOINCREMENT, email text," +
//                FILE_NAME + " text, " + FILE_DATA + " blob)");
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTS);
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SUPERVISORS);
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COORDINATOR);
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROJECTS);
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FILES);
//        onCreate(db);
//    }
//
//    /**
//     * Adds a row to Users Table
//     */
//    public void insertUser(User users) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        String sqlInsert = "INSERT INTO " + TABLE_USERS;
//        sqlInsert += " values( null, '" + users.getEmail() + "', '" + users.getPin() +
//                "', '" + users.getName() + "', '" + users.getRole() + "', '" + users.getStatus() +
//                "', '" + users.getOnlineOffline() + "' )";
//
//        db.execSQL(sqlInsert);
//        db.close();
//    }
//
//    /**
//     * Adds a row to Coordinator Table
//     */
//    public void insertCoordinator(Coordinator coordinator) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        String sqlInsert = "INSERT INTO " + TABLE_COORDINATOR;
//        sqlInsert += " values( null, '" + coordinator.getName() + "', '" + coordinator.getPhoneNumber() + "', '" +
//                coordinator.getEmail() + "' )";
//
//        db.execSQL(sqlInsert);
//        db.close();
//    }
//
//    /**
//     * Adds a row to Supervisors Table
//     */
//    public void insertSupervisor(Supervisor supervisors) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        String sqlInsert = "INSERT INTO " + TABLE_SUPERVISORS;
//        sqlInsert += " values( null, '" + supervisors.getName() + "', '" + supervisors.getPhoneNumber() +
//                "', '" + supervisors.getEmail() + "', '" + supervisors.getArea() + "' )";
//
//        db.execSQL(sqlInsert);
//        db.close();
//    }
//
//    /**
//     * Adds a row to Projects Table
//     */
//    public void insertProject(Project projects) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        String sqlInsert = "INSERT INTO " + TABLE_PROJECTS;
//        sqlInsert += " values( null, '" + projects.getIdNumber() + "', '" + projects.getApprovedTopic() + "' )";
//
//        db.execSQL(sqlInsert);
//        db.close();
//    }
//
//    /**
//     * Adds a row to Students Table
//     */
//    public void insertStudent(Student students) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        String sqlInsert = "INSERT INTO " + TABLE_STUDENTS;
//        sqlInsert += " values( null, '" + students.getIdNumber() + "', '" + students.getEmail() + "', '" +
//                students.getFirstProject() + "', '" + students.getSecondProject() + "', '" +
//                students.getThirdProject() + "', '" + students.getFirstArea() + "', '" +
//                students.getSecondArea() + "', '" + students.getThirdArea() + "', '" +
//                students.getGrouping() + "', '" + students.getDate() + "', '" +
//                students.getFirstStatus() + "', '" + students.getSecondStatus() + "', '" +
//                students.getThirdStatus() + "', '" + students.getFirstReport() + "', '" +
//                students.getSecondReport() + "', '" + students.getThirdReport() + "' )";
//
//        db.execSQL(sqlInsert);
//        db.close();
//    }
//
//    /**
//     * Adds a row to Files Table
//     */
//    public void insertAttachment(Attachment attachment) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(FILE_NAME, attachment.getAttachmentName());
//        values.put(FILE_DATA, attachment.getAttachmentData());
//        db.insert(TABLE_FILES, null, values);
//        db.close();
//    }
//
//    /**
//     * Selects and returns all the rows in Users Table
//     */
//    public ArrayList<User> selectAllUsers() {
//        String sqlQuery = "SELECT * FROM " + TABLE_USERS;
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        @SuppressLint("Recycle")
//        Cursor cursor = db.rawQuery(sqlQuery, null);
//
//        ArrayList<User> users = new ArrayList<>();
//        while (cursor.moveToNext()) {
//            User currentUser = new User(cursor.getInt(0),
//                    cursor.getString(1), cursor.getString(2), cursor.getString(3),
//                    cursor.getString(4), cursor.getString(5), cursor.getString(6));
//            users.add(currentUser);
//        }
//
//        db.close();
//        return users;
//    }
//
//    /**
//     * Selects and returns all the rows in Coordinator Table
//     */
//    public ArrayList<Coordinator> selectAllCoordinators() {
//        String sqlQuery = "SELECT * FROM " + TABLE_COORDINATOR;
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        @SuppressLint("Recycle")
//        Cursor cursor = db.rawQuery(sqlQuery, null);
//
//        ArrayList<Coordinator> coordinators = new ArrayList<>();
//        while (cursor.moveToNext()) {
//            Coordinator currentCoordinator = new Coordinator(cursor.getInt(0),
//                    cursor.getString(1), cursor.getString(2), cursor.getString(3));
//            coordinators.add(currentCoordinator);
//        }
//
//        db.close();
//        return coordinators;
//    }
//
//    /**
//     * Selects and returns all the rows in Supervisors Table
//     */
//    public ArrayList<Supervisor> selectAllSupervisors() {
//        String sqlQuery = "SELECT * FROM " + TABLE_SUPERVISORS;
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        @SuppressLint("Recycle")
//        Cursor cursor = db.rawQuery(sqlQuery, null);
//
//        ArrayList<Supervisor> supervisors = new ArrayList<>();
//        while (cursor.moveToNext()) {
//            Supervisor currentSupervisor = new Supervisor(cursor.getInt(0),
//                    cursor.getString(1), cursor.getString(2), cursor.getString(3),
//                    cursor.getString(4));
//            supervisors.add(currentSupervisor);
//        }
//
//        db.close();
//        return supervisors;
//    }
//
//    /**
//     * Selects and returns all the rows in Projects Table
//     */
//    public ArrayList<Project> selectAllProjects() {
//        String sqlQuery = "SELECT * FROM " + TABLE_PROJECTS;
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        @SuppressLint("Recycle")
//        Cursor cursor = db.rawQuery(sqlQuery, null);
//
//        ArrayList<Project> projects = new ArrayList<>();
//        while (cursor.moveToNext()) {
//            Project currentProject = new Project(cursor.getInt(0),
//                    cursor.getString(1), cursor.getString(2));
//            projects.add(currentProject);
//        }
//
//        db.close();
//        return projects;
//    }
//
//    /**
//     * Selects and returns all the rows in Students Table
//     */
//    public ArrayList<Student> selectAllStudents() {
//        String sqlQuery = "SELECT * FROM " + TABLE_STUDENTS;
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        @SuppressLint("Recycle")
//        Cursor cursor = db.rawQuery(sqlQuery, null);
//
//        ArrayList<Student> students = new ArrayList<>();
//        while (cursor.moveToNext()) {
//            Student currentStudent = new Student(cursor.getInt(0),
//                    cursor.getString(1), cursor.getString(2), cursor.getString(3),
//                    cursor.getString(4), cursor.getString(5), cursor.getString(6),
//                    cursor.getString(7), cursor.getString(8), cursor.getString(9),
//                    cursor.getString(10), cursor.getString(11), cursor.getString(12),
//                    cursor.getString(13), cursor.getString(14), cursor.getString(15),
//                    cursor.getString(16));
//            students.add(currentStudent);
//        }
//
//        db.close();
//        return students;
//    }
//
//    /**
//     * Selects and returns all the rows in Files Table
//     */
//    public ArrayList<Attachment> selectAllAttachments() {
//        String sqlQuery = "SELECT * FROM " + TABLE_FILES;
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        @SuppressLint("Recycle")
//        Cursor cursor = db.rawQuery(sqlQuery, null);
//
//        ArrayList<Attachment> attachments = new ArrayList<>();
//        while (cursor.moveToNext()) {
//            Attachment attachment = new Attachment(cursor.getInt(0), cursor.getString(1),
//                    cursor.getString(2), cursor.getBlob(3));
//            attachments.add(attachment);
//        }
//
//        db.close();
//        return attachments;
//    }
//
//    /**
//     * Retrieve attachment from database
//     */
//    public byte[] getFileData(String filename) {
//        SQLiteDatabase db = this.getReadableDatabase();
//        String[] columns = {FILE_DATA};
//        String selection = FILE_NAME + "=?";
//        String[] selectionArgs = {filename};
//        Cursor cursor = db.query(TABLE_FILES, columns, selection, selectionArgs, null, null, null);
//
//        byte[] fileData = null;
//        if (cursor.moveToFirst()) {
//            int dataIndex = cursor.getColumnIndex(FILE_DATA);
//            fileData = cursor.getBlob(dataIndex);
//        }
//
//        cursor.close();
//        db.close();
//        return fileData;
//    }
//
//
//    /**
//     * Removes the row with the selected item from Users Table
//     */
//    public void deleteUser(String email) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(TABLE_USERS, "email=?", new String[]{email});
//        db.close();
//    }
//
//    /**
//     * Removes the row with the selected item from Students Table
//     */
//    public void deleteStudent(String email) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(TABLE_STUDENTS, "email=?", new String[]{email});
//        db.close();
//    }
//
//    /**
//     * Removes the row with the selected item from Projects Table
//     */
//    public void deleteProject(String email) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(TABLE_PROJECTS, "id_number=?", new String[]{email});
//        db.close();
//    }
//
//    /**
//     * Removes the row with the selected item from Supervisors Table
//     */
//    public void deleteSupervisor(String email) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(TABLE_SUPERVISORS, "email=?", new String[]{email});
//        db.close();
//    }
//
//    /**
//     * Removes the row with the selected item from Coordinator Table
//     */
//    public void deleteCoordinator(String email) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(TABLE_COORDINATOR, "email=?", new String[]{email});
//        db.close();
//    }
//
//    /**
//     * Returns the total number of rows in Users Table
//     */
//    public int getUsersCount() {
//        String countQuery = "SELECT * FROM " + TABLE_USERS;
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(countQuery, null);
//        cursor.close();
//        return cursor.getCount();
//    }
//
//    /**
//     * Returns the total number of rows in Coordinator Table
//     */
//    public int getCoordinatorCount() {
//        String countQuery = "SELECT * FROM " + TABLE_COORDINATOR;
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(countQuery, null);
//        cursor.close();
//        return cursor.getCount();
//    }
//
//    /**
//     * Returns the total number of rows in Projects Table
//     */
//    public int getProjectCount() {
//        String countQuery = "SELECT * FROM " + TABLE_PROJECTS;
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(countQuery, null);
//        cursor.close();
//        return cursor.getCount();
//    }
//
//    /**
//     * Returns the total number of rows in Students Table
//     */
//    public int getStudentCount() {
//        String countQuery = "SELECT * FROM " + TABLE_STUDENTS;
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(countQuery, null);
//        cursor.close();
//        return cursor.getCount();
//    }
//
//    /**
//     * Returns the total number of rows in Supervisors Table
//     */
//    public int getSupervisorCount() {
//        String countQuery = "SELECT * FROM " + TABLE_SUPERVISORS;
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(countQuery, null);
//        cursor.close();
//        return cursor.getCount();
//    }
//
//    /**
//     * Clears all rows in Users Table and returns the number of rows remaining
//     */
//    public Integer clearUsers() {
//        SQLiteDatabase db = this.getReadableDatabase();
//        return db.delete(TABLE_USERS, null, null);
//    }
//
//    /**
//     * Clears all rows in Coordinator Table and returns the number of rows remaining
//     */
//    public Integer clearCoordinators() {
//        SQLiteDatabase db = this.getReadableDatabase();
//        return db.delete(TABLE_COORDINATOR, null, null);
//    }
//
//    /**
//     * Clears all rows in Projects Table and returns the number of rows remaining
//     */
//    public Integer clearProjects() {
//        SQLiteDatabase db = this.getReadableDatabase();
//        return db.delete(TABLE_PROJECTS, null, null);
//    }
//
//    /**
//     * Clears all rows in Students Table and returns the number of rows remaining
//     */
//    public Integer clearStudents() {
//        SQLiteDatabase db = this.getReadableDatabase();
//        return db.delete(TABLE_STUDENTS, null, null);
//    }
//
//    /**
//     * Clears all rows in Projects Table and returns the number of rows remaining
//     */
//    public Integer clearSupervisors() {
//        SQLiteDatabase db = this.getReadableDatabase();
//        return db.delete(TABLE_SUPERVISORS, null, null);
//    }
//
//    /**
//     * Updates the status of a user in the users table.
//     *
//     * @param email     The unique email of the user whose status is to be updated.
//     * @param newStatus The new status to be set for the user.
//     */
//    public void updateUserStatus(String email, String newStatus) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put("status", newStatus);
//        db.update(TABLE_USERS, values, "email=?", new String[]{email});
//        db.close();
//    }
//
//    /**
//     * Updates the status of a user in the users table.
//     *
//     * @param email                  The unique email of the user whose online/offline status is to be
//     *                               updated.
//     * @param newOnlineOfflineStatus The new status to be set for the user.
//     */
//    public void updateUserOnlineOfflineStatus(String email, String newOnlineOfflineStatus) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put("onlineOffline", newOnlineOfflineStatus);
//        db.update(TABLE_USERS, values, "email=?", new String[]{email});
//        db.close();
//    }
//
//    /**
//     * Updates the status of the first submitted topic in the Student's table.
//     *
//     * @param email             The unique email of the user whose project's approval status
//     *                          is to be updated.
//     * @param newApprovalStatus The new project approval status to be set for the user.
//     */
//    public void updateFistTopicApprovalStatus(String email, String newApprovalStatus) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put("first_status", newApprovalStatus);
//        db.update(TABLE_STUDENTS, values, "email=?", new String[]{email});
//        db.close();
//    }
//
//    public void updateSecondTopicApprovalStatus(String email, String newApprovalStatus) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put("second_status", newApprovalStatus);
//        db.update(TABLE_STUDENTS, values, "email=?", new String[]{email});
//        db.close();
//    }
//
//    public void updateThirdTopicApprovalStatus(String email, String newApprovalStatus) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put("third_status", newApprovalStatus);
//        db.update(TABLE_STUDENTS, values, "email=?", new String[]{email});
//        db.close();
//    }
//
//    public void updateAttachment(String email, String newAttachmentName, String newAttachmentData) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(FILE_NAME, newAttachmentName);
//        values.put(FILE_DATA, newAttachmentData);
//        db.update(TABLE_FILES, values, "email=?", new String[]{email});
//        db.close();
//    }
//}