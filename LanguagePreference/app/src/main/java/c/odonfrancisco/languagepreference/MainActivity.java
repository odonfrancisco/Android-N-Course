package c.odonfrancisco.languagepreference;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.language_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.english:
                setLanguage("English");
                return true;
            case R.id.croatian:
                setLanguage("Croatian");
                return true;
            case R.id.french:
                setLanguage("French");
                return true;
            default:
                return false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("c.odonfrancisco.languagepreference", Context.MODE_PRIVATE);

        String languagePref = sharedPreferences.getString("languagePref", "null");

        if(languagePref == "null") {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .setTitle("Set your language preference")
                    .setMessage("Would you prefer croatian, english or french?")
                    .setPositiveButton("English", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            setLanguage("English");
                        }
                    })
                    .setNegativeButton("Croatian", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            setLanguage("Croatian");
                        }
                    })
                    .setNeutralButton("French", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            setLanguage("French");
                        }
                    })
                    .show();
        } else {
            setText();
        }

    }

    private void setLanguage(String language){
        Toast.makeText(MainActivity.this, language + " set as default language", Toast.LENGTH_SHORT).show();

        sharedPreferences.edit().putString("languagePref", language).apply();
        setText();
    }


    public void setText(){
        TextView languageText = findViewById(R.id.languageText);

        languageText.setText(sharedPreferences.getString("languagePref", "null"));
    }
}
