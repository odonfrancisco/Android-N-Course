package c.odonfrancisco.listviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> fam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView myListView = findViewById(R.id.myListView);

        // ArrayList<String> fam = new ArrayList<>(asList("Emily", "Baylee", "Joshua", "Millenium Falcon", "Ryan"));

        fam = new ArrayList<>();

        fam.add("Emily");
        fam.add("Baylee");
        fam.add("Joshua");
        fam.add("Miles");
        fam.add("Ryan");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, fam);

        myListView.setAdapter(arrayAdapter);

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(MainActivity.this, fam.get(position), Toast.LENGTH_SHORT).show();

            }
        });

    }
}
