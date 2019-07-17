package c.odonfrancisco.tic_tac_toe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    int turn = 1;

    public void clickSquare(View view) {
        View clickedSquare = view;

        int squareId = clickedSquare.getId();

        ImageView square = findViewById(squareId);


        if(turn == 1) {
            square.setImageResource(R.drawable.pink_button);
            turn = 2;
        } else {
            square.setImageResource(R.drawable.fart);
            turn = 1;
        }


        Log.i("viewDat", String.valueOf(view.getId()));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
