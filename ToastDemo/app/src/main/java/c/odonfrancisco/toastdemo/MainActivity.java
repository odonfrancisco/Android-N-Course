package c.odonfrancisco.toastdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public void viewName(View view) {

        ImageView image1 = findViewById(R.id.image1);
        ImageView image2 = findViewById(R.id.image2);

        if(image1.getVisibility() == View.VISIBLE){
            image1.setVisibility(View.GONE);
            image2.setVisibility((View.VISIBLE));
        } else {
            image1.setVisibility(View.VISIBLE);
            image2.setVisibility((View.GONE));
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
