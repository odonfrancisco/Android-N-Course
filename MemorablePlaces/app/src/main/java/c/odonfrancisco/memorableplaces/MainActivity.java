package c.odonfrancisco.memorableplaces;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView placesListView;
    ArrayList<String> placesList = new ArrayList<>();
    ArrayList<String> latLngs = new ArrayList<>();
    SharedPreferences sharedPreferences;

    int MARKER_REQUEST = 1;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

//        Log.i("Request Code", String.valueOf(requestCode));
//        Log.i("Result Code", String.valueOf(resultCode));
//        Log.i("Result Data", String.valueOf(data));

        if(requestCode == MARKER_REQUEST){
            if(resultCode == RESULT_OK){
                ArrayList<LatLng> latLngs = data.getParcelableArrayListExtra("latLngs");
                ArrayList<String> addresses = data.getStringArrayListExtra("addresses");

                addToListView(addresses, latLngs);
            }
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = this.getSharedPreferences("c.odonfrancisco.memorableplaces", Context.MODE_PRIVATE);

        setUpListView();

        if(getIntent().getStringArrayListExtra("latLngs") != null){
            Log.i("intent werk", "INTENT TO CONSENT");
        }




        Log.i("Intention", "TRUTH");

//        if(getIntent().getStringArrayListExtra("latLngs") != null && getIntent().getStringArrayListExtra("addresses") != null){
//            latLngs = getIntent().getStringArrayListExtra("latLngs");
//            addresses = getIntent().getStringArrayListExtra("addresses");
//            addToListView(addresses, latLngs);
//        }

    }

    private void setUpListView(){
        placesListView = findViewById(R.id.namesListView);
        ArrayList<String> placeListSharedPref = new ArrayList<>();

        if(placeListSharedPref.size() == 0){
            placeListSharedPref.add("Add a new place...");
        }

        Log.d("MainActivity", String.valueOf(sharedPreferences.getString("placesList", "").length()));

        try {
            if(sharedPreferences.getString("placesList", "").length() > 0){

                placeListSharedPref = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences
                        .getString("placesList",
                                ObjectSerializer.serialize(new ArrayList<String>())));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, placeListSharedPref);

        placesListView.setAdapter(arrayAdapter);

        placesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switchActivity(position);

            }
        });

    }

    private void addToListView(ArrayList<String> addresses, ArrayList<LatLng> latLng){
        for(int i=0; i<addresses.size(); i++){
            placesList.add(addresses.get(i));
            latLngs.add("" + latLng.get(i).latitude + "," + latLng.get(i).longitude);
        }

        try {
            sharedPreferences.edit().putString("placesList", ObjectSerializer.serialize(placesList)).apply();
            sharedPreferences.edit().putString("latLngs", ObjectSerializer.serialize(latLngs)).apply();

        } catch (IOException e) {
            e.printStackTrace();
        }


        setUpListView();
    }

    public void switchActivity(int id){

        Intent intent = new Intent(this, MapsActivity.class);

        if(id != 0) {
//            Log.i("id", String.valueOf(id));
//            Log.i("Addresses", placesList.toString());
//            Log.i("latLngs", latLngs.toString());
            String address = null;
            String latLng = null;
            ArrayList<String> addressesArr;
            ArrayList<String> latLngArr;

            try {
                addressesArr = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("placesList", ""));
                address = addressesArr.get(id);

                latLngArr = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("latLngs", ""));
                latLng = latLngArr.get(id);

            } catch (IOException e) {
                e.printStackTrace();
            }

            intent.putExtra("Address", address);
            intent.putExtra("latLng", latLng);
//            Log.i("latLng", String.valueOf(latLngs.get(id-1)));
        }


        startActivityForResult(intent, MARKER_REQUEST);

    }
}
