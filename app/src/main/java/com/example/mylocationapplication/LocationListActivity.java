package com.example.mylocationapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LocationListActivity extends AppCompatActivity {

    ListView lv_LocationList;
    DatabaseHandler db;
    ArrayList<Location> LocationList;
    Button btnAdd, btnMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_list);

        lv_LocationList = findViewById(R.id.lv_ContacList);
        LocationList = new ArrayList<>();
        db = new DatabaseHandler(this);

        btnAdd = findViewById(R.id.btnAdd);
        btnMap = findViewById(R.id.btnMap);

        selectQuery("ASC");

        /*List<Location> contacts = db.getAllLocation();

        for (Location cn : contacts) {
            String log = "Id: " + cn.get_id() + " ,Name: " + cn.getName() + " ,Lat: " +
                    cn.getLat()+ " image "+cn.getLng();
            LocationList.add(new Location(cn.get_id(), cn.getName(), cn.getLat(), cn.getLng()));
            // Writing Contacts to log
            Log.v("Name: ", log);
        }

        CustomAdapter customAdapter = new CustomAdapter
                (LocationListActivity.this, LocationList);
        lv_LocationList.setAdapter(customAdapter);*/

        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LocationListActivity.this, MapsActivity.class);

                Bundle args = new Bundle();
                args.putSerializable("data",(Serializable) LocationList);
                i.putExtra("BUNDLE",args);
                startActivity(i);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LocationListActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }

    private void selectQuery(String order) {
        LocationList.clear();
        List<Location> contacts = db.getAllLocation(order);

        for (Location cn : contacts) {
            String log = "Id: " + cn.get_id() + " ,Name: " + cn.getName() + " ,Lat: " +
                    cn.getLat()+ " image "+cn.getLng();
            LocationList.add(new Location(cn.get_id(), cn.getName(), cn.getLat(), cn.getLng()));
            // Writing Contacts to log
            Log.v("Name: ", log);
        }

        CustomAdapter customAdapter = new CustomAdapter
                (LocationListActivity.this, LocationList);
        lv_LocationList.setAdapter(customAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.desc:
                    selectQuery("DESC");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}