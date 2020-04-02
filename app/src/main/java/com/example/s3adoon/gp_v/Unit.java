package com.example.s3adoon.gp_v;


import android.content.Intent;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import static android.support.v4.content.ContextCompat.startActivity;

public class Unit
{

    public String name  , location , start , end  ,type,id ;
    public double lat ,lng;
    public  Unit()
    {

    }

    public Unit(String name ,String location,String start , String end ,String type , double lat, double lng) {
      //  this.id=id;
        this.name = name;
        this.start = start;
        this.end = end;
        this.location= location;
        this.type = type;
        this.lat = lat ;
        this.lng = lng ;


    }
    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}
