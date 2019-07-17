package c.odonfrancisco.simpletimestable;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView timesTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timesTable = findViewById(R.id.timesTableList);
        SeekBar numberSlider = findViewById(R.id.numberBar);

        numberSlider.setMax(23);


        numberSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                if(progress == 0){
                    progress = 1;
                } else {
                    showTimesTable(progress);
                }

//                Toast.makeText(MainActivity.this, Integer.toString(progress), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }

    public void showTimesTable(int num){


        ArrayList<String> multipliedNums = new ArrayList<>();

        for(int i=1; i<20; i++){

            String multiplicationPhrase = i*num + "     " + num + " X " + i;

            multipliedNums.add(multiplicationPhrase);
        }

//        Byte[] multipliedNums = {2, 4, 6, 8, 10};

        ArrayAdapter<String> timesArrayList = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, multipliedNums);



        timesTable.setAdapter(timesArrayList);
    }


}
