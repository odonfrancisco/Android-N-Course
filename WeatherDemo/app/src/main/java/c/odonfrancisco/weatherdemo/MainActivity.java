package c.odonfrancisco.weatherdemo;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    String apiKey = "8b375a0b80c64c0c168dd6ce707c9880";

    public class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            String result = "";
            URL url;
            HttpURLConnection connection;


            try {
                url = new URL(urls[0]);
                connection = (HttpURLConnection) url.openConnection();


                InputStream in = connection.getInputStream();

                InputStreamReader reader = new InputStreamReader(in);

                int data = reader.read();

                while(data != -1){

                    result += (char) data;

                    data = reader.read();
                }


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String str){
            super.onPostExecute(str);

            Log.i("Website Content", str);

            try {
                JSONObject jsonObject = new JSONObject(str);

                String weatherInfo = jsonObject.getString("weather");

                Log.i("Weather content", weatherInfo);

                JSONArray arr = new JSONArray(weatherInfo);

                for(int i=0; i<arr.length(); i++){

                    JSONObject currentObj = arr.getJSONObject(i);

                    Log.i("main", currentObj.getString("main"));
                    Log.i("description", currentObj.getString("description"));

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWeather("Miami,fl");
    }

    private void getWeather(String city){
        DownloadTask task = new DownloadTask();

        String url = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "&APPID=" + apiKey;

//        Log.i("Weather URL", url);

//        try {
//            String weatherData = task.execute(url).get();
                task.execute(url);
//            Log.i("Weather Data", weatherData);

//        } catch(Exception e){
//            e.printStackTrace();
//        }

    }
}
