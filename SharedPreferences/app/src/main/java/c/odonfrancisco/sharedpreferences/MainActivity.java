package c.odonfrancisco.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = this.getSharedPreferences("c.odonfrancisco.sharedpreferences", Context.MODE_PRIVATE);

        ArrayList<String> friends = new ArrayList<>();
        friends.add("monica");
        friends.add("chandler");

//        Would using a Parcelable be better for this?
//        try {
//            sharedPreferences.edit().putString("friends",ObjectSerializer.serialize(friends)).apply();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        ArrayList<String> newFriends = new ArrayList<>();
        try {
            newFriends = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("friends",
                    ObjectSerializer.serialize(new ArrayList<String>())));
        } catch (IOException e) {
            e.printStackTrace();
        }

//        sharedPreferences.edit().putString("username", "fran").apply();

//        String username = sharedPreferences.getString("username", "");
//
        Log.d(TAG, newFriends.toString());

    }
}
