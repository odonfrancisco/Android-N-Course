package c.odonfrancisco.notesapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                Set<String> notes = sharedPreferences.getStringSet("notes", new HashSet<String>());
                initializeRecyclerView(notes);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()){
            case R.id.newNote:
                Intent intent = new Intent(this, EditNoteText.class);
                intent.putExtra("noteID", -1);
                startActivityForResult(intent, 1);
                return true;
            default:
                return false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("c.odonfrancisco.notesapp", Context.MODE_PRIVATE);

        int timesOpened = sharedPreferences.getInt("timesOpened", 0);

        if (timesOpened > 0){

        } else {


            Set<String> set = new HashSet<String>();

            set.add("Example note");

            sharedPreferences.edit().putStringSet("notes", set).apply();
        }
        Toast.makeText(this, "times opened still " + timesOpened +" fix dis shit", Toast.LENGTH_SHORT).show();

        Set<String> notes = sharedPreferences.getStringSet("notes", new HashSet<String>());

        initializeRecyclerView(notes);
    }

    private void initializeRecyclerView(Set<String> notes){
        ArrayList<String> notesArray = new ArrayList<String>();
        notesArray.addAll(notes);


        RecyclerView recyclerView = findViewById(R.id.notesRecyclerView);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, MainActivity.this, notesArray);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void deleteAlertDialog(){

    }
}