package com.example.software02.newearthquake.Fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.software02.newearthquake.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by SOFTWARE02 on 1/21/2018.
 */

public class MapFragment extends Fragment implements OnMapReadyCallback  {



    GoogleMap mgoogleMap;
    MapView mapView;
    View root;
    MarkerListener markerListener;
    double latitude, longitude;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public interface MarkerListener{
         public void getLocation(String lat, String lon, String description);
    }

    public GoogleMap getMgoogleMap() {
        return mgoogleMap;
    }


    public void setMarker()
    {
        mgoogleMap.clear();
        mgoogleMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title("Hey Bitch"));
        mgoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude,longitude), 3));
       // mgoogleMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title("Hey Bitch"));
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         root = inflater.inflate(R.layout.map_farment_layout,container,false);

        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mapView = (root).findViewById(R.id.mapView);
        if(mapView!=null)
        { mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }

    }

    public MapFragment(){}

    @Override
    public void onMapReady(GoogleMap googleMap) {

        MapsInitializer.initialize(getContext());

        mgoogleMap = googleMap;
        googleMap.setMapType(mgoogleMap.MAP_TYPE_NORMAL);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude,longitude), 5));
        googleMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title("Hey"));

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
              markerListener = (MarkerListener) context;
           }
           catch (ClassCastException ex)
           {
               throw  new ClassCastException(context.toString());
           }
    }

}
