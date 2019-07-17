package c.odonfrancisco.hideandshowbuttons;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = findViewById(R.id.myText);
    }

    public void toggleText(View view){
        String tag = String.valueOf(view.getTag());
        Log.i("tag", tag);

        if(tag.equals("show")){
            text.setVisibility(View.VISIBLE);
        } else {
            text.setVisibility(View.INVISIBLE);
        }


    }

}
