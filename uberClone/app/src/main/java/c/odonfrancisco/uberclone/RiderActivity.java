package c.odonfrancisco.uberclone;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
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
    private Location userLocation;
    private Button requestUberButton;
    private boolean uberRequested = false;
    private String requestID;
    Handler handler = new Handler();
    TextView infoTextView;


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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        logoutUser();
    }

    @Override
    public void onClick(View view){
        if(view.getId() == requestUberButton.getId()){
            onUberButtonPressed();
        } else if(view.getId() == R.id.logoutButton){
            logoutButtonPressed();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 120 ,locationListener);
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

        infoTextView = findViewById(R.id.infoTextView);
        requestUberButton = findViewById(R.id.requestUberButton);
        requestUberButton.setOnClickListener(this);
        findViewById(R.id.logoutButton).setOnClickListener(this);

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Request");
        query.whereEqualTo("riderID", ParseUser.getCurrentUser().getObjectId());
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    if(objects.size() > 0){
                        requestUberButton.setText(R.string.cancel_uber);
                        uberRequested = true;
                        requestID = objects.get(0).getObjectId();
                    }
                }
            }
        });
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.i("handler.postDelayed", "Called");
                checkForUpdates();
            }
        }, 2000);
    }

    private void checkForUpdates(){
        if(requestID != null){
            Log.i("checkForUpdates", "requestID not null");
            ParseQuery<ParseObject> query = new ParseQuery<>("Request");
            query.getInBackground(requestID, new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject object, ParseException e) {
                    if(e == null){
                        Log.i("checkForUpdates done", (String) object.get("driverID"));
                        if(String.valueOf(object.get("driverID")).equals("")){
                            infoTextView.setText("");
                            requestUberButton.setVisibility(View.VISIBLE);
                        } else {
                            infoTextView.setText("Driver is on the way");
                            requestUberButton.setVisibility(View.INVISIBLE);
                        }

                    }
                }
            });
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Log.i("handler.postDelayed", "Called inside checkUp");
                    checkForUpdates();
                }
            }, 2000);
        }
    }

    private void setLocationListener(){
        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.i("locationToString", location.toString());
                userLocation = location;
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


    private void setUserMarker(){
        if(userLocation != null){
            LatLng userLatLng = new LatLng(userLocation.getLatitude(), userLocation.getLongitude());

            mMap.clear();
            mMap.addMarker(new MarkerOptions().position(userLatLng).title("It's you!!"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLatLng, 9));
        } else {
            Log.i("SetUserMarker" , "Called Prematurely");
        }
    }

    private void onUberButtonPressed(){
        if(!uberRequested){
            requestUberButton.setText(R.string.cancel_uber);

            ParseGeoPoint parseGeoPoint = new ParseGeoPoint(userLocation.getLatitude(), userLocation.getLongitude());
            final ParseObject newRequest = new ParseObject("Request");
            newRequest.put("riderID", ParseUser.getCurrentUser().getObjectId());
            newRequest.put("driverID", "");
//            newRequest.put("driverLocation", new ParseGeoPoint());
            newRequest.put("riderLocation", parseGeoPoint);

            newRequest.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null){
                        Log.i("Request saved", "Request ID: " + newRequest.getObjectId());
                        requestID = newRequest.getObjectId();
                        checkForUpdates();
                    } else {
                        Log.i("Request saved", "Failed");
                        e.printStackTrace();
                    }
                }
            });
        } else {
            requestUberButton.setText(R.string.request_uber);

            ParseQuery query = new ParseQuery<ParseObject>("Request");
            query.whereEqualTo("riderID", ParseUser.getCurrentUser().getObjectId());


            query.findInBackground(new FindCallback() {
                @Override
                public void done(Object o, Throwable throwable){
                    if(throwable == null){
//                        Log.i("objects", o.get(0).toString());
                        ArrayList<ParseObject> arrayList = (ArrayList) o;
                        for(ParseObject object : arrayList){
                            object.deleteInBackground();
                        }

                    }
                }

                @Override
                public void done(List objects, ParseException e) {

                }
            });
        }
        uberRequested = !uberRequested;
    }

    private void logoutButtonPressed(){
        logoutUser();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    private void logoutUser(){
        if(requestID != null && uberRequested){
            ParseQuery<ParseObject> query = new ParseQuery<>("Request");
            query.getInBackground(requestID, new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject object, ParseException e) {
                    if(e == null){
                        try {
                            object.delete();
                        } catch (ParseException e1) {
                            e1.printStackTrace();
                        }
                    } else {
                        e.printStackTrace();
                    }
                }
            });
            requestID = null;
            uberRequested = false;
        }
        ParseUser.logOut();
    }

}
