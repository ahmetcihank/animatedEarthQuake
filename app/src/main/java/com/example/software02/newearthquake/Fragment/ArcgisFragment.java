package com.example.software02.newearthquake.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.esri.arcgisruntime.geometry.GeometryEngine;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.SpatialReference;

import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.Viewpoint;

import com.example.software02.newearthquake.R;

import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.views.overlay.Marker;


/**
 * Created by SOFTWARE02 on 1/23/2018.
 */

public class ArcgisFragment extends Fragment implements  Runnable   {

  /*   MapView mMapView;
     View view;
     ArcGISMap map; */

    View view;

  MapView mapView;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Thread t = new Thread(this);
        t.start();
       // Point esri4326 = new Point(-117.903557, 33.666354);
       // mMapView.setViewpointCenterAsync(esri4326,12);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


     /*   Point londonPoint = new Point(28677947.756181,22987445.6186465,2);
        Viewpoint viewpoint = new Viewpoint(londonPoint,3.14);

        view = inflater.inflate(R.layout.arcgis_layout,container,false);
        map  =  new ArcGISMap(Basemap.createNationalGeographic());    //new ArcGISMap(Basemap.Type.TERRAIN_WITH_LABELS, 34.056295, -117.195800, 16);
        mMapView =(MapView) (view).findViewById(R.id.mapViewEsri);
        mMapView.setViewpoint(viewpoint);
        mMapView.setMap(map); */


       // mMapView.setViewpointAsync(viewpoint,23);


        view = inflater.inflate(R.layout.arcgis_layout,container,false);






        return view;
    }

    public ArcgisFragment(){}

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        Context ctx = getActivity();
        //important! set your user agent to prevent getting banned from the osm servers
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        Configuration.getInstance().setUserAgentValue(getActivity().getPackageName());

        mapView =(MapView) (view).findViewById(R.id.mapViewOsm);
        mapView.setTileSource(TileSourceFactory.MAPNIK);
        mapView.setBuiltInZoomControls(true);
        MapController mMapController = (MapController) mapView.getController();
        mMapController.setZoom(13);
        GeoPoint gPt = new GeoPoint(51500000, -150000);
        mMapController.setCenter(gPt);
        Marker marker = new Marker(mapView);
        marker.setPosition(gPt);
        //mMapController.setCenter();
        mapView.getOverlays().clear();
        mapView.getOverlays().add(marker);


    }

    @Override
    public void onPause(){
       // mapView.pause();
        super.onPause();

    }

    @Override
    public void onResume(){
       // mapView.resume();
        super.onResume();
        Configuration.getInstance().load(getContext(), PreferenceManager.getDefaultSharedPreferences(getContext()));

    }


    @Override
    public void run() {


    }
}
