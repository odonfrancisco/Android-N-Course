package c.odonfrancisco.notesapp;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class Startup extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferences sharedPreferences = getSharedPreferences("c.odonfrancisco.notesapp", Context.MODE_PRIVATE);
        int timesOpened = sharedPreferences.getInt("timesOpened", -1);
        sharedPreferences.edit().putInt("timesOpened", timesOpened+1).apply();
    }
}
