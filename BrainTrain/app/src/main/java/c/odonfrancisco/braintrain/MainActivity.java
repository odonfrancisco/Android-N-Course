package c.odonfrancisco.braintrain;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    LinearLayout gameContainer;
    TextView additionTextView;
    TextView scoreTextView;
    TextView timerTextView;
    android.support.v7.widget.GridLayout choicesGrid;
    ArrayList<String> numsArr;
    TextView finalScoreTextView;
    Button resetButton;

    int score;
    int problemsGuessed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameContainer = findViewById(R.id.gameContainer);
        additionTextView = findViewById(R.id.additionText);
        scoreTextView = findViewById(R.id.scoreText);
        timerTextView = findViewById(R.id.timerText);
        choicesGrid = findViewById(R.id.choicesGrid);
        finalScoreTextView = findViewById(R.id.finalScoreText);
        resetButton = findViewById(R.id.resetButton);
        score = 0;
        problemsGuessed = 0;
    }

    public void startGame(View view){
        gameContainer.setVisibility(View.VISIBLE);
        view.setVisibility(View.INVISIBLE);
        finalScoreTextView.setVisibility(View.INVISIBLE);
        resetButton.setVisibility(View.INVISIBLE);

//        choicesGrid.setEnabled(false);
        for(int i=0; i<choicesGrid.getChildCount(); i++){
            choicesGrid.getChildAt(i).setEnabled(true);
        }

        score = 0;
        problemsGuessed = 0;

        startTimer();
        renderNewProblem(generateProblem());
//        Log.i("Array", String.valueOf(generateProblem()));
    }

    public void clickSolution(View view){
        int tag = Integer.parseInt(view.getTag().toString());

        if(numsArr.get(tag).equals(numsArr.get(5))){
            score ++;
            problemsGuessed ++;
        } else {
            problemsGuessed ++;
        }

        Log.i("score", String.valueOf(score));
        Log.i("Problems Guessed", String.valueOf(problemsGuessed));
        renderNewProblem(generateProblem());
    }

    private ArrayList<String> generateProblem(){
        int add1 = (int) Math.floor(Math.random()*40) + 1;
        int add2 = (int) Math.floor(Math.random()*40) + 1;

        int solution = add1 + add2;

        String additionText = add1 + " + " + add2;

//        Log.i("Added nums:", solution + "");

        numsArr = new ArrayList<>();

        numsArr.add(additionText);

        for(int i=0; i<4; i++){
            numsArr.add(String.valueOf((int) Math.floor(Math.random()*30)));
        }

        numsArr.set((int) Math.floor(Math.random()*4)+1, String.valueOf(solution));

        numsArr.add(String.valueOf(solution));
//        Log.i("array", String.valueOf(numsArr));
        return numsArr;
    }

    private void renderNewProblem(ArrayList<String> numsArr){
        additionTextView.setText(numsArr.get(0));
        scoreTextView.setText(score + "/" + problemsGuessed);

        for(int i=1; i<choicesGrid.getChildCount()+1; i++){
            TextView choice = (TextView) choicesGrid.getChildAt(i-1);

            choice.setTag(i);
            choice.setText(numsArr.get(i));
        }

    }

    private void startTimer(){
        CountDownTimer countDown = new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerTextView.setText(convertMillisToSeconds(millisUntilFinished));
            }

            @Override
            public void onFinish() {
                gameOver();
            }
        };
        countDown.start();
    }

    private String convertMillisToSeconds(long millis){
        long seconds = millis/1000;

        return String.valueOf(seconds);
    }

    private void gameOver(){
        finalScoreTextView.setText("You scored " + score + "/" + problemsGuessed + "!!");
        finalScoreTextView.setVisibility(View.VISIBLE);

        resetButton.setVisibility(View.VISIBLE);

//        choicesGrid.setEnabled(false);
        for(int i=0; i<choicesGrid.getChildCount(); i++){
            choicesGrid.getChildAt(i).setEnabled(false);
        }

    }
}
