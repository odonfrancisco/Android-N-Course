package c.odonfrancisco.androidweardemo;

import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends WearableActivity implements View.OnClickListener {

    private TextView mTextView;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = (TextView) findViewById(R.id.text);
        button = findViewById(R.id.inquiryButton);

        button.setOnClickListener(this);

        // Enables Always-on
        setAmbientEnabled();
    }


    @Override
    public void onClick(View v) {
        Log.i("viewID", String.valueOf(v.getId()));
        if(v.getId() == R.id.inquiryButton){
            Toast.makeText(getApplicationContext(), "Yes you clicked", Toast.LENGTH_SHORT).show();
        }
    }
}
