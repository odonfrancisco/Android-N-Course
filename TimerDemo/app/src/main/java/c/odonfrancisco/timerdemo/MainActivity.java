package c.odonfrancisco.timerdemo;

import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new CountDownTimer(10000, 1000) {
            public void onTick(long millisecondsUntilDone){
                // Countdown counding down every second
                Log.i("Seconds left", String.valueOf(millisecondsUntilDone / 1000));

            }

            public void onFinish() {
                // Counter is finished after 10 seconds
                Log.i("Done!", "Countdown timer finished");

            }
        }.start();


//        final Handler handler = new Handler();
//        Runnable run = new Runnable() {
//            @Override
//            public void run() {
//                Log.i("Runnable", " keeps running away from me");
//                handler.postDelayed(this, 1000);
//            }
//        };
//
//
//        handler.post(run);
    }
}
