package c.odonfrancisco.uberclone;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.android.gms.maps.model.LatLng;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class DriverActivity extends AppCompatActivity {
    private ListView requestListView;
    private LocationManager locationManager;
    private LocationListener locationListener;
    Location userLocation;
    ArrayList<ParseObject> requestsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);

        requestListView = findViewById(R.id.requestsListView);

        requestListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), DriverMapActivity.class);
                ParseGeoPoint riderRequest = (ParseGeoPoint) ((ParseObject) requestsList.get(position)).get("riderLocation");
                intent.putExtra("riderLat", String.valueOf(riderRequest.getLatitude()));
                intent.putExtra("riderLong", String.valueOf(riderRequest.getLongitude()));

                startActivity(intent);
            }
        });

        getRequests();
        setLocationListener();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == 1){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 120, locationListener);
                }
            }
        }

    }

    private void setLocationListener(){
        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.i("locationToString", location.toString());
                userLocation = location;
                setListView();
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

    private void setListView(){
        ArrayList<String> distanceList = new ArrayList<>();
        if (requestsList != null && requestsList.size() > 0) {
            for(ParseObject request : requestsList){
                distanceList.add(getRequestDistance(request));
            }
        } else {
            distanceList.add("No New Requests at this time");
        }

        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1, distanceList);
        requestListView.setAdapter(adapter);

    }

    private String getRequestDistance(ParseObject request){
        ParseGeoPoint requestLocation = (ParseGeoPoint) request.get("riderLocation");
        float[] distance = new float[1];
        Location.distanceBetween(userLocation.getLatitude(), userLocation.getLongitude(), requestLocation.getLatitude(), requestLocation.getLongitude(), distance);
        Log.i("Distance", String.valueOf(distance[0] * 0.000621371192f));

        return distance[0] * 0.000621371192f + " miles away";
    }

    private void getRequests(){
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Request");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    requestsList = new ArrayList();
                    requestsList.addAll(objects);
                } else {
                    if(requestsList == null){
                        requestsList = new ArrayList<>();
                    }
                }
            }
        });

    }



}
