package c.odonfrancisco.databasedemo;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try{

            SQLiteDatabase myDatabase = this.openOrCreateDatabase("Users", MODE_PRIVATE, null);
//            myDatabase.execSQL("CREATE TABLE IF NOT EXISTS users (name VARCHAR, age INT(3))");

            myDatabase.execSQL("CREATE TABLE IF NOT EXISTS newUsers (name VARCHAR, age INTEGER(3), id INTEGER PRIMARY KEY)");
//
//            myDatabase.execSQL("INSERT INTO newUsers (name, age) VALUES ('Odon', 21)");
//            myDatabase.execSQL("INSERT INTO newUsers (name, age) VALUES ('Luis', 19)");
//            myDatabase.execSQL("INSERT INTO newUsers (name, age) VALUES ('CHreast', 2), ('yeast', 5)");

//            myDatabase.execSQL("DELETE FROM users WHERE name = 'Odon' LIMIT 1");
//            myDatabase.execSQL("UPDATE users SET age = 43 WHERE  name = 'Odon' ");

            myDatabase.execSQL("DELETE FROM newUsers WHERE id = 1");


            Cursor c = myDatabase.rawQuery("SELECT * FROM newUsers", null);


            int nameIndex = c.getColumnIndex("name");
            int ageIndex = c.getColumnIndex("age");
            int idIndex = c.getColumnIndex("id");

            c.moveToFirst();

            while(c != null){

                Log.i("Results - name", c.getString(nameIndex));
                Log.i("Results - age", Integer.toString(c.getInt(ageIndex)));
                Log.i("Results - id", Integer.toString(c.getInt(idIndex)));

                c.moveToNext();
            }

        } catch(Exception e) {
              e.printStackTrace();
        }


//        try {
//
//            SQLiteDatabase eventsDB = this.openOrCreateDatabase("Events", MODE_PRIVATE, null);
//
//            eventsDB.execSQL("CREATE TABLE IF NOT EXISTS events (name VARCHAR, date int(4))");
//
//            eventsDB.execSQL("INSERT INTO events ('name', 'date') VALUES ('9/11', 2001), ('Financial Fun', 2008)");
//
//            Cursor c = eventsDB.rawQuery("SELECT * FROM events", null);
//
//            int nameIndex = c.getColumnIndex("name");
//            int dateIndex = c.getColumnIndex("date");
//
//            c.moveToFirst();
//
//            while (c != null){
//                Log.i("Results - Name", c.getString(nameIndex));
//                Log.i("Results - Date", Integer.toString(c.getInt(dateIndex)));
//
//                c.moveToNext();
//
//            }
//
//
//
//        } catch (Exception e){
//            e.printStackTrace();
//
//        }

















    }
}
