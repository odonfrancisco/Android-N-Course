package c.odonfrancisco.instagramclone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class UserProfileActivity extends AppCompatActivity {
    ParseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        ParseQuery<ParseUser> query = ParseUser.getQuery();
        try {
            user = query.get(getIntent().getStringExtra("id"));
            Toast.makeText(getApplicationContext(), "User found: " + user.getUsername(), Toast.LENGTH_SHORT).show();

        } catch (ParseException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Unable to find the user you are looking for", Toast.LENGTH_SHORT).show();
        }
    }

    private void getUserPhotos(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Image");
        query.whereEqualTo("userID", user.getObjectId());
    }
}
