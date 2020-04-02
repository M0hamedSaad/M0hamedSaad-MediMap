package com.example.s3adoon.gp_v;

import java.security.PrivateKey;

public class Hospital extends Unit

{
    private String spec ;
    public double lat ,lng;

    public Hospital(String name, String location, String start , String end ,String spec, double lat, double lng )

    {
        super();

        //this.id=id;
        this.name = name;
        this.start = start;
        this.end = end ;
        this.location= location;
        //this.type = type;
        this.spec=spec;
        this.lat = lat ;
        this.lng = lng ;
    }

    public Hospital() {

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

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

}



