package c.odonfrancisco.hikerswatch;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    LocationManager locationManager;
    LocationListener locationListener;
    TextView latitudeText;
    TextView longitudeText;
    TextView accuracyText;
    TextView altitudeText;
    TextView addressText;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

            if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){

                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        latitudeText = findViewById(R.id.latitudeText);
        longitudeText = findViewById(R.id.longitudeText);
        accuracyText = findViewById(R.id.accuracyText);
        altitudeText = findViewById(R.id.altitudeText);
        addressText = findViewById(R.id.addressText);

        setLocationManagerListener();


    }


    private void setLocationManagerListener(){

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.i("Location Changed to", location.toString());
                Log.i("Accuracy", String.valueOf(location.getAccuracy()));
                Log.i("Altitude", String.valueOf(location.getAltitude()));

                latitudeText.setText("Latitude: " + String.valueOf(location.getLatitude()));
                longitudeText.setText("Longitude: " + String.valueOf(location.getLongitude()));
                accuracyText.setText("Accuracy: " + String.valueOf(location.getAccuracy()));
                altitudeText.setText("Altitude: " + String.valueOf(location.getAltitude()));

                addressText.setText(geocacheLocation(location));
//                geocacheLocation(location);

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);


        }
    }

    private String geocacheLocation(Location location){
        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

        String result = "";

        try{
            List<Address> addressesList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 2);

            if(addressesList != null && addressesList.size() > 0){

                Address address = addressesList.get(0);

                Log.i("addressesList", addressesList.toString());

                Log.i("Address[0]", addressesList.get(0).toString());

                Log.i("getAddressLine", address.getAddressLine(0));

                result = "Address: " + address.getAddressLine(0);

            } else {
                result = "No Address Found For This Location";
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;

    }

}
