package c.odonfrancisco.memorableplaces;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    LocationManager locationManager;
    LocationListener locationListener;
    Intent intent;
    ArrayList<LatLng> latLngList;
    ArrayList<String> addressList;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                requestLocationUpdatesLastKnownLocation();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        intent = new Intent(this, MainActivity.class);

        latLngList = new ArrayList<>();
        addressList = new ArrayList<>();
    }

    @Override
    public void onBackPressed(){
        intent.putParcelableArrayListExtra("latLngs", latLngList);
//        intent.putStringArrayListExtra("latLngs", latLngList);
        intent.putStringArrayListExtra("addresses", addressList);
        setResult(RESULT_OK, intent);
//        PendingIntent pendingIntent =
//                TaskStackBuilder.create(this)
//                        .addNextIntentWithParentStack(intent)
//                        .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
//        builder.setContentIntent(pendingIntent);

//        startActivity(intent);

        finish();
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

        if(getIntent().getExtras() != null){
            String address = getIntent().getStringExtra("Address");
            String latLng = getIntent().getStringExtra("latLng");
            Log.d("MapsActivity", latLng);

            String[] latlong = latLng.split(",");

            LatLng lateral = new LatLng(Double.parseDouble(latlong[0]), Double.parseDouble(latlong[1]));

            setMarker(address, lateral);
        } else {
            setUserLocation();
        }

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                addNewLocation(latLng);
            }
        });

    }

    private void addNewLocation(LatLng latLng){
        mMap.addMarker(new MarkerOptions().position(latLng).title("New LatLng"));
        latLngList.add(latLng);
        addressList.add(geocodeLocation(latLng));

    }

    private String geocodeLocation(LatLng latLng){
        Geocoder geocoder = new Geocoder(this);
        String address = "";
        try {
            address = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1).get(0).getAddressLine(0);
            Log.i("geocoder", geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1).toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return address;

    }

    private void setMarker(String address, LatLng latLng){
        mMap.addMarker(new MarkerOptions().position(latLng).title(address));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));
    }

    private void setUserLocation(){
        setLocationManagerListener();

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            requestLocationUpdatesLastKnownLocation();
        }

    }

    private void requestLocationUpdatesLastKnownLocation(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 20, locationListener);

            Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            LatLng lastLatLng = new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());

            mMap.addMarker(new MarkerOptions().position(lastLatLng).title("YOU!!"));

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lastLatLng, 14));
        }
    }

    private void setLocationManagerListener(){

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.i("Location", location.toString());

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
    }
}
