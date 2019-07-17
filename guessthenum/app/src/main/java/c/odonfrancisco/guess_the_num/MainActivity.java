package c.odonfrancisco.guess_the_num;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    int boundNum = 28;
    int randomNum;
    Random rand = new Random();

    public void toastDat(String content) {
        Toast.makeText(MainActivity.this, content, Toast.LENGTH_LONG).show();
    }

    public void guessNum(View view) {
        Log.i("randomNum", String.valueOf(randomNum));

        EditText guessedInput = findViewById(R.id.guessInput);
        int guessedNum = Integer.parseInt(guessedInput.getText().toString());

        Log.i("guessedInput", String.valueOf(guessedNum));
        if (guessedNum > randomNum) {
            toastDat("Lower!");
//            Toast.makeText(MainActivity.this, "Lower!", Toast.LENGTH_LONG).show();
        } else if( guessedNum < randomNum) {
            toastDat("Higher!");
//            Toast.makeText(MainActivity.this, "Higher!", Toast.LENGTH_LONG).show();
        } else {
            toastDat("Nice! Now guess the new number");
//            Toast.makeText(MainActivity.this, "Nice! Now guess the new number", Toast.LENGTH_LONG).show();
            this.randomNum = rand.nextInt(boundNum);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        randomNum = rand.nextInt(boundNum);

    }
}
