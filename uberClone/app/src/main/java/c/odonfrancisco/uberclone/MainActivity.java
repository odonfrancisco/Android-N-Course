package c.odonfrancisco.uberclone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private boolean driver;
    private Button requestUberButton;

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.driverRiderSwitch: {
                Switch driverSwitch = (Switch) v;
                if(driverSwitch.isChecked()){
                    driver = true;
                    Log.i("switch checked", "true");
                    Toast.makeText(getApplicationContext(), "True", Toast.LENGTH_SHORT).show();
                } else {
                    driver = false;
                    Toast.makeText(getApplicationContext(), "False", Toast.LENGTH_SHORT).show();
                    Log.i("switched checked", "false");
                }
            } break;
            case R.id.startButton : {
                anonymousLogin();
            } break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        driver = false;

        ParseObject exampleObject = new ParseObject("ExampleObject");
        exampleObject.put("working", "true");

        findViewById(R.id.driverRiderSwitch).setOnClickListener(this);
        findViewById(R.id.startButton).setOnClickListener(this);

        ParseAnalytics.trackAppOpenedInBackground(getIntent());
    }

    private void getStarted(){
        if((boolean) ParseUser.getCurrentUser().get("driver")){
            Log.i("Redirecting as", "Driver");
        } else {
            Intent intent = new Intent(getApplicationContext(), RiderActivity.class);
            intent.putExtra("driver", driver);
            startActivity(intent);
            Log.i("Redirecting as", "Rider");
        }
    }

    private boolean anonymousLogin(){
        if(ParseUser.getCurrentUser().get("driver") == null){
            ParseAnonymousUtils.logIn(new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e) {
                    if (e == null){
                        if(driver){
                            Log.i("Login", "Successful as driver");
                        } else {
                            Log.i("Login", "Successful as rider");
                        }
                        user.put("driver", driver);
                        getStarted();
                    }
                }
            });
        } else {
            ParseUser.getCurrentUser().put("driver", driver);
            getStarted();
        }
        return true;
    }

}
