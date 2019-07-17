package c.odonfrancisco.sounddemo;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    MediaPlayer mPlayer;
    AudioManager audioManager;
    SeekBar scrub;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPlayer = MediaPlayer.create(this, R.raw.chocolaterain);

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int curVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        int maxLength = mPlayer.getDuration();
        int curLength = mPlayer.getCurrentPosition();


        SeekBar volume = findViewById(R.id.seekBar);
        scrub = findViewById(R.id.seekScrub);

        volume.setMax(maxVolume);
        volume.setProgress(curVolume);

        scrub.setMax(maxLength);
        scrub.setProgress(curLength);

        volume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Toast.makeText(MainActivity.this, Integer.toString(progress), Toast.LENGTH_SHORT).show();

                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                scrub.setProgress(mPlayer.getCurrentPosition());
            }
        }, 0, 100);

        scrub.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
                mPlayer.seekTo(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar){
                pause(seekBar);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar){
                play(seekBar);
            }

        });


    }

    public void play(View view){
        mPlayer.start();
//        scrub.setProgress(mPlayer.getCurrentPosition());

    }

    public void pause(View view){
        mPlayer.pause();
    }
}
