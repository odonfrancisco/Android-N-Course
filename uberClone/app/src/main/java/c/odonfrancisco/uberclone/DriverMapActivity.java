package c.odonfrancisco.uberclone;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;

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

public class DriverMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private FusedLocationProviderClient fusedLocationClient;
    private GoogleMap mMap;
    private Location driverLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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


        Intent intent = getIntent();
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

}
