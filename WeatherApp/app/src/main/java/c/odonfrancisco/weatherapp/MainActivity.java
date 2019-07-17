package c.odonfrancisco.weatherapp;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    TextView weatherResultsText;
    EditText requestedCityText;
    TextView temperatureResultsText;

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

                String err = e.getLocalizedMessage();
                return err;
            }


            return result;
        }

        @Override
        protected void onPostExecute(String result){


            setWeatherResultsText(result);

            Log.i("Results", result);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestedCityText = findViewById(R.id.requestedCity);
        weatherResultsText = findViewById(R.id.weatherInfo);
        temperatureResultsText = findViewById(R.id.temperatureView);


    }

    public void requestWeather(View view){
        String city = String.valueOf(requestedCityText.getText());

        InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        mgr.hideSoftInputFromWindow(requestedCityText.getWindowToken(), 0);


        DownloadTask task  = new DownloadTask();

//        Log.i("CITY INPUT", city);

        String url = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "&APPID=" + apiKey;

        task.execute(url);
    }

    private void setWeatherResultsText(String results){

        String weatherText = "";
        String temperatureText = "";

        try {
            JSONObject jsonObject = new JSONObject(results);

            String weatherStr = jsonObject.getString("weather");

            Log.i("MAIN", jsonObject.getString("main"));

            JSONObject mainObj = new JSONObject(jsonObject.getString("main"));

            JSONObject weatherArr = new JSONObject(new JSONArray(weatherStr).getString(0));

            String temp = mainObj.getString("temp");

            Log.i("TEMP from MAIN", temp);

            String weatherDescription = weatherArr.getString("description");

//            double temperature = String. temp;

            weatherText = weatherDescription;

            temperatureText = String.valueOf(temp);


        } catch (JSONException e) {
            e.printStackTrace();

            weatherText = "Sorry! Weather is not available for that area";
        }

        weatherResultsText.setText(weatherText);
        temperatureResultsText.setText(temperatureText);

    }

}
