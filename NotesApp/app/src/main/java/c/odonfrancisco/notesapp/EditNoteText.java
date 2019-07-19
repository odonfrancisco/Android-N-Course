package c.odonfrancisco.notesapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class EditNoteText extends AppCompatActivity {

    TextView noteTextView;
    ArrayList<String> notesArrayList = new ArrayList<String>();
    SharedPreferences sharedPreferences;
    int noteIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note_text);

        Intent intent = getIntent();

        sharedPreferences = getSharedPreferences("c.odonfrancisco.notesapp", Context.MODE_PRIVATE);

        Set<String> notesList = sharedPreferences.getStringSet("notes", new HashSet<String>());
        notesArrayList.addAll(notesList);

        noteIndex = intent.getIntExtra("noteID",-1);

        Toast.makeText(this, notesArrayList.get(noteIndex), Toast.LENGTH_SHORT).show();

        // ok fool im going to sleep now but try and access shared preferences directly from
            // this activity instead of sending info BACK to main activity i think that'll be more fluid

        noteTextView = findViewById(R.id.editNoteText);
        noteTextView.setText(notesArrayList.get(noteIndex));

    }

    @Override
    public void onBackPressed() {
        notesArrayList.set(noteIndex, noteTextView.getText().toString());
        Set<String> notesSet = new HashSet<>(notesArrayList);
        sharedPreferences.edit().putStringSet("notes", notesSet).apply();

        setResult(RESULT_OK, new Intent());

        finish();
    }
}
