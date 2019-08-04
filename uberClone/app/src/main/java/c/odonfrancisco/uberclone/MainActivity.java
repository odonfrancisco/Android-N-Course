package c.odonfrancisco.uberclone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ParseObject exampleObject = new ParseObject("ExampleObject");
        exampleObject.put("working", "true");

        exampleObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null){
                    Log.i("ParseSave", "successful");
                } else {
                    Log.i("ParseSave", "Failed");
                }
            }
        });

        ParseAnalytics.trackAppOpenedInBackground(getIntent());
    }
}
