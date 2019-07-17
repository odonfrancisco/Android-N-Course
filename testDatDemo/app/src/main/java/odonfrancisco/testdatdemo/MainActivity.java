package odonfrancisco.testdatdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public void clickDatThang(View view) {

        EditText username = findViewById(R.id.usernameInput);
        Log.i("Username: ", username.getText().toString());

        EditText password = findViewById(R.id.passwordInput);
        Log.i("password: ", password.getText().toString());



        Toast.makeText(MainActivity.this, "Wassup!!" + username.getText().toString(), Toast.LENGTH_LONG).show();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
