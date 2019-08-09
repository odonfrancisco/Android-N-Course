package c.odonfrancisco.uberclone;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class RiderActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener {

    private GoogleMap mMap;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private LatLng userLatLng;
    private Button requestUberButton;
    private boolean uberRequested = false;

    @Override
    public void onClick(View view){
        if(view.getId() == requestUberButton.getId()){
            requestUber();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0 ,locationListener);
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        requestUberButton = findViewById(R.id.requestUberButton);
        requestUberButton.setOnClickListener(this);
    }

    private void setLocationListener(){
        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.i("locationToString", location.toString());
                userLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                setUserMarker();
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
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000,120, locationListener);
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        setLocationListener();
    }

    private void setUserMarker(){
        if(userLatLng != null){
            mMap.addMarker(new MarkerOptions().position(userLatLng).title("It's you!!"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(userLatLng));
        } else {
            Log.i("SetUserMarker" , "Called Prematurely");
        }
    }

    private void requestUber(){
        if(!uberRequested){
            requestUberButton.setText("Cancel Uber");

            ParseObject newRequest = new ParseObject("Request");
            newRequest.put("riderID", ParseUser.getCurrentUser().getObjectId());
            newRequest.put("driverID", "");
            newRequest.put("riderLocation", userLatLng.toString());

            newRequest.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null){
                        Log.i("Request saved", "Successfully");
                    } else {
                        Log.i("Request saved", "Failed");
                    }
                }
            });
        } else {
            requestUberButton.setText("Request Uber");

            ParseQuery query = new ParseQuery<ParseObject>("Request");
            query.whereEqualTo("riderID", ParseUser.getCurrentUser().getObjectId());


            query.findInBackground(new FindCallback() {
                @Override
                public void done(List objects, ParseException e) {
                    Log.i("objects", objects.get(0).toString());
                }

                @Override
                public void done(Object o, Throwable throwable) {
                    Log.i("object", o.toString());
                    ArrayList<ParseObject> arrayList = (ArrayList) o;
                    if (arrayList.size() == 1) {
                        arrayList.get(0).deleteInBackground(new DeleteCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e == null){
                                    Log.i("Deleted Object", "Deleted Successfully");
                                } else {
                                    Log.i("Deleted Object", "Deletion Failed");
                                }
                            }
                        });
                    }
                }
            });
        }
        uberRequested = !uberRequested;

    }

}
