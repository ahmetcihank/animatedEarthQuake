package com.example.software02.newearthquake.Fragment;

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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.maps.android.heatmaps.HeatmapTileProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SOFTWARE02 on 2/17/2018.
 */

public class HeatMapFragment  extends Fragment implements OnMapReadyCallback
{

    View view;
    MapView mapView;
    GoogleMap mgoogleMap;
    MapFragment.MarkerListener markerListener;
    HeatmapTileProvider mProvider;
    TileOverlay mOverlay;
    List<String> latStr;
    List<String> lonStr;
    List<Double> Lat  = new ArrayList<Double>();
    List<Double> Lon = new ArrayList<Double>();
    List<LatLng> latLngList = new ArrayList<LatLng>();

    public List<String> getLatStr() {
        return latStr;
    }

    public void setLatStr(List<String> latStr) {
        this.latStr = latStr;
    }

    public List<String> getLonStr() {
        return lonStr;
    }

    public void setLonStr(List<String> lonStr) {
        this.lonStr = lonStr;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        for(int i =0; i<latStr.size(); i++)
        {
           Lat.add(Double.parseDouble(latStr.get(i)));
           Lon.add(Double.parseDouble(lonStr.get(i)));
           LatLng ltLnobj = new LatLng(Double.parseDouble(latStr.get(i)), Double.parseDouble(lonStr.get(i)));
           latLngList.add(ltLnobj);
        }



    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.activity_main,container, false);
       // mgoogleMap.addTileOverlay(new TileOverlayOptions().tileProvider(mProvider));


        return view;

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mapView = (view).findViewById(R.id.heatMap);
        if(mapView!=null)
        { mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }

    }

    public HeatMapFragment(){}

    @Override
    public void onMapReady(GoogleMap googleMap) {

        MapsInitializer.initialize(getContext());

        mgoogleMap = googleMap;
        mProvider = new HeatmapTileProvider.Builder().data(latLngList).build();
        mProvider.setRadius( HeatmapTileProvider.DEFAULT_RADIUS );
        mgoogleMap.addTileOverlay(new TileOverlayOptions().tileProvider(mProvider));


        // googleMap.setMapType(mgoogleMap.MAP_TYPE_NORMAL);



        //googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(10,10), 5));
        //googleMap.addMarker(new MarkerOptions().position(new LatLng(10, 10)).title("Hey Bitch"));




    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            markerListener = (MapFragment.MarkerListener) context;
        }
        catch (ClassCastException ex)
        {
            throw  new ClassCastException(context.toString());
        }
    }


}
