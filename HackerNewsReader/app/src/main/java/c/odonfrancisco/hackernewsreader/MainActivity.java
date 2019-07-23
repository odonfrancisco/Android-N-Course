package c.odonfrancisco.hackernewsreader;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity {

    public class DownloadTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            StringBuilder result = new StringBuilder();
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

                    result.append(current);

                    data = reader.read();

                }

                return result.toString();

            } catch (Exception e){
                e.printStackTrace();

                return "Failed";
            }

        }
    }

    String url = "https://hacker-news.firebaseio.com/v0/topstories.json?print=pretty";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final DownloadTask downloadTask = new DownloadTask();
        String result = null;

        try {

           result = downloadTask.execute(url).get();

           Log.i("results", result);


        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
