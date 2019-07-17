package c.odonfrancisco.guessdatcelebrity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> celebrityImages = new ArrayList<>();
    ArrayList<String> celebrityNames = new ArrayList<>();

    int currentCelebIndex;

    ImageView celebImage;
    LinearLayout layout;

    public class FetchCelebs extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {

            String result = "";
            URL url;
            HttpURLConnection urlConnection;

            try{
                url = new URL(urls[0]);

                urlConnection = (HttpURLConnection) url.openConnection();

                InputStream in = urlConnection.getInputStream();

                InputStreamReader reader = new InputStreamReader(in);

                int data = reader.read();

                while(data != -1){
                    char current = (char) data;

                    result += current;

                    data = reader.read();
                }

                return result;


            } catch (MalformedURLException e) {
                e.printStackTrace();

                return null;
            } catch (IOException e) {
                e.printStackTrace();

                return null;
            }

        }
    }

    public class ImageDownloader extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... urls) {

            try {
                URL url = new URL(urls[0]);

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                connection.connect();

                InputStream inputStream = connection.getInputStream();

                Bitmap celebImage = BitmapFactory.decodeStream(inputStream);

                return celebImage;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        celebImage = findViewById(R.id.celebrityImage);
        layout = findViewById(R.id.linearLayout);

        aggregateCelebs();
    }

    public void clickAnswer(View view){
        int tag = (int) view.getTag();

        if(tag == currentCelebIndex){
            Toast.makeText(MainActivity.this, "Correct!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, "Incorrect! It was " + celebrityNames.get(currentCelebIndex), Toast.LENGTH_SHORT).show();
        }

        setUpCeleb();

    }
    

    private void aggregateCelebs(){

        FetchCelebs task = new FetchCelebs();

        String result = null;

        try{
            result = task.execute("http://www.posh24.se/kandisar").get();
        } catch(InterruptedException e){
            e.printStackTrace();
        } catch (ExecutionException e){
            e.printStackTrace();
        }


        Pattern elementPattern = Pattern.compile("src=(.*?)>");
        Pattern nameImgPattern = Pattern.compile("\"(.*?)\"");

        Matcher elementMatcher = elementPattern.matcher(result);

        int i = 0;

        while(elementMatcher.find()){
            if(elementMatcher.group(1).contains("alt=")){
                Matcher nameImgMatcher = nameImgPattern.matcher(elementMatcher.group(1));

                while(nameImgMatcher.find()){
                    if(i == 0){

                        URL imageUrl;
                        try {
//                            Log.i("IMAGE URL", nameImgMatcher.group(1));

                            imageUrl = new URL(nameImgMatcher.group(1));

//                            Log.i("imageUrl", String.valueOf(imageUrl));

//                            celebrityImages.add(imageUrl);
                            celebrityImages.add(nameImgMatcher.group(1));
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                        i = 1;
                    } else {
                        celebrityNames.add(nameImgMatcher.group(1));
                        i=0;
                    }
                }

            }
//            Log.i("m.find()", elementMatcher.group(1));
        }

//        Log.i("Celebrity URLs", String.valueOf(celebrityImages));
//        Log.i("Celebrity Names", String.valueOf(celebrityNames));

        setUpCeleb();
    }
    
    
    private void setUpCeleb(){

        ImageDownloader task = new ImageDownloader();

        Random random = new Random();

        currentCelebIndex = random.nextInt(celebrityNames.size());
        int randomButton = random.nextInt(4) + 1;


        try {
            celebImage.setImageBitmap(task.execute(celebrityImages.get(currentCelebIndex)).get());

            for(int i=1; i<5; i++){
                Button button = (Button) layout.getChildAt(i);

                if(i == randomButton){
                    button.setText(celebrityNames.get(currentCelebIndex));
                    button.setTag(currentCelebIndex);

                } else {
                    int randomCeleb = random.nextInt(celebrityNames.size());

                    button.setText(celebrityNames.get(randomCeleb));
                    button.setTag(randomCeleb);

                }
            }

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
    
}

