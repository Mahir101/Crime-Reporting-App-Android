package com.example.crimereporter;

public class Locations {


    float lng, lat;
    Locations(float lng, float lat){
        this.lng=lng;
        this.lat=lat;
    }
    Locations(){

    }
    public float getLng(){
        return lng;
    }

    public float getLat(){
        return lat;
    }
}
