package c.odonfrancisco.hackernewsreader;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> titles = new ArrayList<>();
    ArrayList<String> content = new ArrayList<>();
    ArrayAdapter arrayAdapter;

    String urlPrefix = "https://hacker-news.firebaseio.com/v0/item/";
    String urlSuffix = ".json?print=pretty";

    SQLiteDatabase articlesDB;

    public class DownloadTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;

            try{
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();

                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);

                int data = reader.read();

                while (data != -1) {
                    char current = (char) data;

                    result += current;

                    data = reader.read();

                }

                JSONArray jsonArray = new JSONArray(result);

                int numberOfItems = 20;

                if(jsonArray.length() < numberOfItems){
                    numberOfItems = jsonArray.length();
                }

                articlesDB.execSQL("DELETE FROM articles");

                for(int i = 0; i < numberOfItems; i++){
                    String articleId = jsonArray.getString(i);
                    url = new URL(urlPrefix + articleId + urlSuffix);

                    urlConnection = (HttpURLConnection) url.openConnection();

                    in = urlConnection.getInputStream();
                    reader = new InputStreamReader(in);
                    data = reader.read();

                    String articleInfo = "";

                    while (data != -1) {
                        char current = (char) data;
                        articleInfo += current;
                        data = reader.read();
                    }

                    JSONObject jsonObject = new JSONObject(articleInfo);

                    if (!jsonObject.isNull("title") && !jsonObject.isNull("url")) {

                        String articleTitle = jsonObject.getString("title");
                        String articleURL = jsonObject.getString("url");

                        String articlePageData = "";

                        url = new URL(articleURL);

                        urlConnection = (HttpURLConnection) url.openConnection();

                        in = urlConnection.getInputStream();
                        reader = new InputStreamReader(in);
                        data = reader.read();

                        while (data != -1) {
                            char current = (char) data;
                            articlePageData += current;
                            data = reader.read();
                        }

                        String sql = "INSERT INTO articleContents (articleId, title, content) VALUES (? , ? , ?)";

                        SQLiteStatement statement = articlesDB.compileStatement(sql);

                        statement.bindString(1, articleId);
                        statement.bindString(2, articleTitle);
                        statement.bindString(3, articlePageData);

                        statement.execute();

                    }

                }

                return result;

            } catch (Exception e){
                e.printStackTrace();

                return "Failed";
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            updateListView();
        }
    }

    String topStoriesUrl = "https://hacker-news.firebaseio.com/v0/topstories.json?print=pretty";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.listView);

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, titles);

        listView.setAdapter(arrayAdapter);

        articlesDB = this.openOrCreateDatabase("Articles", MODE_PRIVATE, null);

//        articlesDB.execSQL("DROP TABLE articles");

//        articlesDB.execSQL("CREATE TABLE IF NOT EXISTS articleContents (" +
//                "articleID INTEGER," +
//                "title VARCHAR, " +
//                "content VARCHAR," +
//                "id INTEGER PRIMARY KEY )");

        updateListView();


        final DownloadTask downloadTask = new DownloadTask();

        try {

//           downloadTask.execute(topStoriesUrl).get();

        } catch (Exception e){
            e.printStackTrace();
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getApplicationContext(), ArticleWebView.class);
                intent.putExtra("content", content.get(position));

                startActivity(intent);
            }
        });
    }

    private void updateListView(){
        Cursor c = articlesDB.rawQuery("SELECT * FROM articleContents", null);
        int contentIndex = c.getColumnIndex("content");
        int titleIndex = c.getColumnIndex("title");

        if(c.moveToFirst()){

            titles.clear();
            content.clear();

            do {
                titles.add(c.getString(titleIndex));
                content.add(c.getString(contentIndex));
            } while(c.moveToNext());

            Log.i("titles arr", titles.get(3));

            arrayAdapter.notifyDataSetChanged();

        }
    }
}
