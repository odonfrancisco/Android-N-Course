package c.odonfrancisco.uberclone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.driverRiderSwitch){
            Switch driverSwitch = (Switch) v;
            if(driverSwitch.isChecked()){
                Log.i("switch checked", "true");
                Toast.makeText(getApplicationContext(), "True", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "False", Toast.LENGTH_SHORT).show();
                Log.i("switched checked", "false");
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ParseObject exampleObject = new ParseObject("ExampleObject");
        exampleObject.put("working", "true");

        findViewById(R.id.driverRiderSwitch).setOnClickListener(this);

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
