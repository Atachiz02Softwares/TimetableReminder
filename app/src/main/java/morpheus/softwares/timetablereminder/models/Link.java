package morpheus.softwares.timetablereminder.models;

import android.content.Context;

import java.util.ArrayList;

import morpheus.softwares.timetablereminder.R;

public class Link {
    Context context;
    Database database;

    public Link(Context context) {
        this.context = context;
        database = new Database(context);
    }

    /**
     * Checks if the user has signed up before
     */
    public boolean hasSignedUp(String username) {
        ArrayList<User> users = database.selectAllUsers();

        for (User user : users) if (user.getUsername().equalsIgnoreCase(username)) return true;
        return false;
    }

    /**
     * Checks if the current user is signed in
     */
    public boolean isSignedIn(String username) {
        ArrayList<User> users = database.selectAllUsers();

        for (User user : users)
            if (user.getStatus().equals(context.getString(R.string.online))) return true;

        return false;
    }
}