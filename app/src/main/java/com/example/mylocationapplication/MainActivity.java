package com.example.mylocationapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    PlacesClient placesClient;
    Button btnAddLocation;
    String Location;
    LatLng selectedLatLong;
    DatabaseHandler db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAddLocation = findViewById(R.id.btnAddLocation);
        db = new DatabaseHandler(this);

        String API_KEY = "AIzaSyBSNyp6GQnnKlrMr7hD2HGiyF365tFlK5U";
        if(!Places.isInitialized()){
            Places.initialize(getApplicationContext(),API_KEY);
        }

        placesClient = Places.createClient(this);
        final AutocompleteSupportFragment autocompleteSupportFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocompleteFragment);

        autocompleteSupportFragment.setPlaceFields(Arrays.asList(Place.Field.ID,
                Place.Field.LAT_LNG, Place.Field.NAME));
        autocompleteSupportFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                final LatLng latLng = place.getLatLng();
                Log.v("DS","LAt"+latLng.latitude+"$$$"+latLng.longitude);
                Toast.makeText(MainActivity.this, ""+place.getName(),Toast.LENGTH_LONG).show();
                Location = place.getName();
                selectedLatLong = place.getLatLng();
            }

            @Override
            public void onError(@NonNull Status status) {

            }
        });

        btnAddLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Location.equals("")){
                    Toast.makeText(MainActivity.this, "Select Location",Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(MainActivity.this, "Location"+Location+"latlong"+
                            selectedLatLong.latitude,Toast.LENGTH_LONG).show();
                    db.addLocation(new Location(Location, selectedLatLong.latitude,selectedLatLong.longitude));
                    Intent intent = new Intent(MainActivity.this, LocationListActivity.class);
                    startActivity(intent);
                }

            }
        });

    }
}