package c.odonfrancisco.eggtimer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SeekBar timerBar;
    TextView timerText;
    int timer;
    CountDownTimer countDown;
    MediaPlayer mPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPlayer = MediaPlayer.create(MainActivity.this, R.raw.carengine);


        timerBar = findViewById(R.id.timerBar);
        timerBar.setMax(400);

        timerText = findViewById(R.id.timerText);

        timerBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                timerText.setText(convertNum(progress));
                timer = progress;
//                convertNum(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private CharSequence convertNum(int num){
        Double num1 = num/60.0;

//        Log.i("Float", String.valueOf(num1));

        int hourNum = (int) Math.floor(num1);

        int minuteNum = (int) Math.round((num1 - Math.floor(num1))*60);

        Log.i("Value", hourNum + ":" + minuteNum);

        return hourNum + ":" + minuteNum;
//        Log.i("FirstPart", String.valueOf(hourNum));
//        Log.i("SecondHand", String.valueOf(minuteNum));

    }

    public void startTimer(View view){

        if(timerBar.getVisibility() == View.VISIBLE){

            timerBar.setVisibility(View.INVISIBLE);

            countDown = new CountDownTimer(timer*1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    timer -= 1;
                    timerText.setText(convertNum(timer));
                }

                @Override
                public void onFinish() {

                    timerBar.setVisibility(View.VISIBLE);
                    mPlayer.start();
                }
            }.start();

        } else {
            timerBar.setVisibility(View.VISIBLE);
            countDown.cancel();
        }

        Log.i("timer visibility: ", "" + timerBar.getVisibility());

    }

}
