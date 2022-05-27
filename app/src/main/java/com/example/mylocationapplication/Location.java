package com.example.mylocationapplication;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

public class Location implements Serializable {

    int _id;
    String name;
    double lat;
    double lng;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public Location() {
    }

    public Location(String name, double lat, double lng) {
        this.name = name;
        this.lat = lat;
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public Location(int _id, String name, double lat, double lng) {
        this._id = _id;
        this.name = name;
        this.lat = lat;
        this.lng = lng;
    }
}
