package c.odonfrancisco.multipleactivitesdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Intent intent = getIntent();

        Toast.makeText(this, "Hi " + intent.getStringExtra("Name") + "!", Toast.LENGTH_SHORT).show();

    }

    public void switchActivity(View view){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);

        startActivity(intent);
    }
}
