package c.odonfrancisco.phrasesbuttons;

import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    MediaPlayer mediaPlayer;
    boolean mediaStarted = false;
    String lastClicked;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void pressFlag(View view){
        View clickedFlag = view;

//        Object id = view.getTag();

//        ImageView flag = findViewById(id);

        String tag = clickedFlag.getTag().toString();

//        if(mediaStarted){
//            mediaPlayer.stop();
//        }

        switch(tag){
            case "germany1": playSound(R.raw.germanprofessions, tag); break;
            case "germany2": playSound(R.raw.germantime, tag); break;
            case "norway1": playSound(R.raw.norwegiancommon, tag); break;
            case "norway2": playSound(R.raw.norwegianknow, tag); break;
            case "spain1": playSound(R.raw.spanishbodyparts, tag); break;
            case "spain2": playSound(R.raw.spanishvos, tag); break;
            case "dutch1": playSound(R.raw.dutchbasicphrases, tag); break;
            case "dutch2": playSound(R.raw.dutchcolors, tag); break;
        }

    }

    private void playSound(int resource, String tag){



        if(lastClicked == tag){
            if(mediaPlayer.isPlaying()){
                Log.i("MediaPlayer Status ", "Playing. Now Paused");
                mediaPlayer.pause();

            } else {
                mediaPlayer.start();
            }

        } else {
            mediaPlayer.pause();
            mediaPlayer = MediaPlayer.create(this, resource);
            mediaPlayer.start();
            lastClicked = tag;
        }

        mediaStarted = true;
    }

}
