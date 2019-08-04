package c.odonfrancisco.instagramclone;

import android.content.Intent;
import android.inputmethodservice.Keyboard;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements View.OnKeyListener, View.OnClickListener {
    TextView textView;
    EditText passwordEditText;
    Button logInButton;

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.backgroundLayout){
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Instagram");

        textView = findViewById(R.id.signUpToggle);
        passwordEditText = findViewById(R.id.password);
        logInButton = findViewById(R.id.logInButton);

        RelativeLayout backgroundLayout = findViewById(R.id.backgroundLayout);

        backgroundLayout.setOnClickListener(this);
        passwordEditText.setOnKeyListener(this);

        if(ParseUser.getCurrentUser() != null){
            postUserAuthorization();
        }

        ParseAnalytics.trackAppOpenedInBackground(getIntent());
    }


    public void toggleLogIn(View view){
        Log.i("TextView Clicked", (String) textView.getText());

        switch(textView.getText().toString()){
            case "Log In": setLogInButton("Log In");
                            textView.setText("Sign Up"); break;
            case "Sign Up": setLogInButton("Sign Up");
                            textView.setText("Log In"); break;
        }
    }

    private void setLogInButton(final String buttonText){
        logInButton.setText(buttonText);

        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(buttonText.equals("Sign Up")){
                    signUp(v);
                } else {
                    logIn(v);
                }
            }
        });
    }

    public void logIn(View view){
        Log.i("Button Clicked", "Log In");
        String username = ((TextView) findViewById(R.id.username)).getText().toString();
        String password = passwordEditText.getText().toString();

        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e == null){
                    Toast.makeText(getApplicationContext(), "Log In Successful!", Toast.LENGTH_SHORT).show();
                    postUserAuthorization();
                } else {
                    Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void signUp(View view){
        String username = ((TextView) findViewById(R.id.username)).getText().toString();
        String password = passwordEditText.getText().toString();

        ParseUser newUser = new ParseUser();
        newUser.setUsername(username);
        newUser.setPassword(password);

        newUser.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null){
                    Toast.makeText(getApplicationContext(), "Sign up successful!", Toast.LENGTH_SHORT).show();
                    postUserAuthorization();
                } else {
                    Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void postUserAuthorization(){
        Intent intent = new Intent(getApplicationContext(), UsersListActivity.class);

        startActivity(intent);

    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN){
            if(logInButton.getText().toString().equals("Log In")) {
                logIn(v);
            } else {
                signUp(v);
            }
        }

        return false;
    }
}
