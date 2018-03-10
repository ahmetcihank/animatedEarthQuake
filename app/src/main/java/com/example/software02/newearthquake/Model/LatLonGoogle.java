package com.example.software02.newearthquake.Model;

/**
 * Created by SOFTWARE02 on 3/4/2018.
 */

public class LatLonGoogle
{
    private  double Lat =0;
    private  double Lon = 0;


    public LatLonGoogle(double Lat, double Lon)
    {
        this.Lat = Lat;
        this.Lon = Lon;
    }

    public double getLat() {
        return Lat;
    }

    public void setLat(double lat) {
        Lat = lat;
    }

    public double getLon() {
        return Lon;
    }

    public void setLon(double lon) {
        Lon = lon;
    }
}
