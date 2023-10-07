package morpheus.softwares.timetablereminder.models;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.widget.Toast;

import morpheus.softwares.timetablereminder.R;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // Handle the alarm action here, e.g., play a sound or show a notification
        Toast.makeText(context, "It's time for your course!", Toast.LENGTH_SHORT).show();
        // You can play a sound here if you wish
        playAlarmSound(context);
    }

    private void playAlarmSound(Context context) {
        MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.alarm_sound); // Replace with your sound resource
        mediaPlayer.start();
    }
}