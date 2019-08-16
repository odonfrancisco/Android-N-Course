package c.odonfrancisco.uberclone;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class DriverMapActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener {

    private FusedLocationProviderClient fusedLocationClient;
    private GoogleMap mMap;
    Button acceptRideButton;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_map);
        acceptRideButton = findViewById(R.id.acceptButton);
        acceptRideButton.setOnClickListener(this);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onClick(View view){
        switch(view.getId()){
            case R.id.acceptButton: {
                acceptRide();
            }
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

        intent = getIntent();
        LatLng riderLatLng = new LatLng(intent.getDoubleExtra("riderLat", 0), intent.getDoubleExtra("riderLong", 0));
        LatLng driverLatLng = new LatLng(intent.getDoubleExtra("driverLat", 0), intent.getDoubleExtra("driverLong", 0));

        setMapMarkers(mMap, driverLatLng, riderLatLng);
        setMapBounds(mMap, driverLatLng, riderLatLng);
    }

    private void setMapMarkers(GoogleMap map, LatLng driverLatLng, LatLng riderLatLng){
        map.clear();
        map.addMarker(new MarkerOptions().position(driverLatLng).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)).title("Your Location"));
        map.addMarker(new MarkerOptions().position(riderLatLng).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)).title("Rider Location"));
    }

    private void setMapBounds(GoogleMap map, LatLng driverLatLng, LatLng riderLatLng){
        LatLngBounds.Builder bounds = new LatLngBounds.Builder();
        bounds.include(driverLatLng);
        bounds.include(riderLatLng);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        map.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), displayMetrics.widthPixels, 250, 0));
    }

    private void acceptRide(){
        ParseQuery<ParseObject> query = new ParseQuery<>("Request");
        query.getInBackground(intent.getStringExtra("requestID"), new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                object.put("driverID", ParseUser.getCurrentUser().getObjectId());
                object.saveInBackground();
            }
        });

        String riderLatLngString =
                getIntent().getDoubleExtra("riderLat", 0)
                + "," +
                getIntent().getDoubleExtra("riderLong", 0);

        Log.i("RiderLatLng", riderLatLngString);

        Uri gmIntentUri = Uri.parse("google.navigation:q=" + riderLatLngString);

        Intent googleMapsIntent = new Intent(Intent.ACTION_VIEW, gmIntentUri);
        googleMapsIntent.setPackage("com.google.android.apps.maps");
        if(googleMapsIntent.resolveActivity(getPackageManager()) != null){
            startActivity(googleMapsIntent);
        }

    }



}
