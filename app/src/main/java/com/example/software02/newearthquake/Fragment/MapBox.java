package com.example.software02.newearthquake.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.software02.newearthquake.R;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;

/**
 * Created by SOFTWARE02 on 2/10/2018.
 */

public class MapBox extends Fragment
{
    View root;
    MapView mapView;
    Mapbox mb;


    public Mapbox getMb() {
        return mb;
    }

    public void setMb(Mapbox mb) {
        this.mb = mb;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mb = Mapbox.getInstance(getActivity(),
                "pk.eyJ1IjoiYWNpaGFuayIsImEiOiJjamRobGVpdjUwd2tpMndtZmpsemxybjBjIn0.dmYB5bqnEqC57WqfH06hIQ");


    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.mapbox_layout, container, false);
        mapView =(MapView) (root).findViewById(R.id.mapboxView);

        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {
                Activity activity = getActivity();
                if (getActivity() != null) {
                   // activity.setMapboxMap(mapboxMap);
                }
            }
        });
        mapView.onCreate(savedInstanceState);


        return  root;

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mapView.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
}
