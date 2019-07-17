package c.odonfrancisco.connect_3_rob;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;

public class MainActivity extends AppCompatActivity {

    int activePlayer = 0;

    boolean activeGame = true;

    int[] gameScore = {2, 2, 2, 2, 2, 2, 2, 2, 2};

    int[][] winningPositions = {{0,1,2}, {3,4,5}, {6,7,8}, {0,3,6}, {1,4,7}, {2,5,8}, {0,4,8}, {2,4,6}};

    public void dropIn(View view) {
        View clickedSquare = view;

        int squareId = clickedSquare.getId();

        ImageView square = findViewById(squareId);

        int taggedTag = Integer.parseInt(clickedSquare.getTag().toString());


        if(gameScore[taggedTag] == 2 && activeGame){

            square.setTranslationY(-1000f);

            gameScore[taggedTag] = activePlayer;
            if(activePlayer == 0){
                square.setImageResource(R.drawable.fart);
                activePlayer = 1;
            } else {
                square.setImageResource(R.drawable.pink_button);
                activePlayer = 0;
            }
            square.animate().translationYBy(1000f).setDuration(300);


            checkScore();
        }

    }

    private void checkScore(){
        for(int i=0; i<winningPositions.length; i++){

            int[] checkPosition = new int[3];

            for(int e=0; e<winningPositions[i].length; e++){
                checkPosition[e] = gameScore[winningPositions[i][e]];
                System.out.println("position: " + e + checkPosition[e]);
            }


            if(checkPosition[0] == checkPosition[1] &&
                    checkPosition[1]== checkPosition[2] &&
                    checkPosition[0] != 2){
//                Toast.makeText(MainActivity.this, "Player " + checkPosition[0]+1 + " won!!", Toast.LENGTH_LONG).show();

                String winner = "Pink";

                if(checkPosition[0] == 0){
                    winner = "Fart";
                }

                TextView winnerMessage = findViewById(R.id.winningMessage);

                winnerMessage.setText(winner + " has won!!");

                LinearLayout playAgain = findViewById(R.id.playAgainLayout);

                playAgain.setVisibility(View.VISIBLE);
                activeGame = false;
            } else {
                boolean gameOver = true;

                for (int counterState : gameScore){
                    if(counterState == 2) gameOver = false;
                }

                if (gameOver){
                    TextView winnerMessage = findViewById(R.id.winningMessage);

                    winnerMessage.setText("It's a Tie!!");

                    LinearLayout playAgain = findViewById(R.id.playAgainLayout);

                    playAgain.setVisibility(View.VISIBLE);
                    activeGame = false;
                }
            }


        }
    }

    public void resetGame(View view){
        LinearLayout playAgain = findViewById(R.id.playAgainLayout);

        playAgain.setVisibility(View.INVISIBLE);

        activePlayer = 0;

        activeGame = true;

        for(int i=0; i<gameScore.length; i++){
            gameScore[i] = 2;
        }

        android.support.v7.widget.GridLayout grid = findViewById(R.id.gridLayout);

        for (int i=0; i<grid.getChildCount(); i++){
            ((ImageView) grid.getChildAt(i)).setImageResource(0);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
