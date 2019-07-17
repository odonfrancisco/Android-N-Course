package c.odonfrancisco.multipleactivitesdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView namesList;
    ArrayList<String> names;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpListView();
    }

    private void setUpListView(){
        namesList = findViewById(R.id.namesListView);

        names = new ArrayList<>();

        names.add("Weenie");
        names.add("Chreast");
        names.add("Yeast");
        names.add("Weast");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, names);

        namesList.setAdapter(arrayAdapter);

        namesList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("position", String.valueOf(position));
                Log.i("id", String.valueOf(id));
                clickName(position);
            }
        });

    }

    public void switchActivity(View view){
        Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
        intent.putExtra("username", "Rob");

        startActivity(intent);
    }

    private void clickName(int position){

        Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
        intent.putExtra("Name", names.get(position));

        startActivity(intent);
    }

}
