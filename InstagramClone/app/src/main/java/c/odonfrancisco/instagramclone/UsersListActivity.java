package c.odonfrancisco.instagramclone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class UsersListActivity extends AppCompatActivity {
    ArrayList<String> usersList;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);

        listView = findViewById(R.id.usersList);
        usersList = new ArrayList<>();

        ParseQuery<ParseUser> userQuery = ParseUser.getQuery();

        userQuery.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername());
        userQuery.addAscendingOrder("username");

        userQuery.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if(e == null && objects.size() > 0) {
//                    usersList = objects;
                    for (ParseUser user : objects) {
                        usersList.add(user.getUsername());
                        Log.i("UserInfo", user.toString());
                        Log.i("Username", user.getUsername());
                    }
                } else {
                    Log.i("Error retrieving users", e.getLocalizedMessage());
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1, usersList);
                listView.setAdapter(adapter);
            }
        });



    }
}
