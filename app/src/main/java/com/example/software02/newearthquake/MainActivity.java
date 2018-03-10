package com.example.software02.newearthquake;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;


import com.example.software02.newearthquake.Adapter.FeedAdapter;
import com.example.software02.newearthquake.Adapter.TitleAdapter;
import com.example.software02.newearthquake.Fragment.ArcgisFragment;
import com.example.software02.newearthquake.Fragment.ContenetFragment;
import com.example.software02.newearthquake.Fragment.HeatMapFragment;
import com.example.software02.newearthquake.Fragment.MapBox;
import com.example.software02.newearthquake.Fragment.MapFragment;
import com.example.software02.newearthquake.Helper.Common;
import com.example.software02.newearthquake.Helper.HTTPDataHandler;
import com.example.software02.newearthquake.Interface.EarthQuakeService;
import com.example.software02.newearthquake.Interface.ItemClickListener;
import com.example.software02.newearthquake.Model.LatLonGoogle;
import com.example.software02.newearthquake.Model.ListRetroModel;
import com.example.software02.newearthquake.Model.RSSObject;
import com.example.software02.newearthquake.Model.RootObject;
import com.google.android.gms.maps.GoogleMap;
import com.google.gson.Gson;
import com.mapbox.mapboxsdk.Mapbox;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends FragmentActivity implements MapFragment.MarkerListener, ItemClickListener,FeedAdapter.onListClickedRowListner
        {


    @BindView(R.id.google_map_option)
            RadioButton radioGoogle;
    @BindView(R.id.arcgis_option)
            RadioButton radioArcgis;
    @BindView(R.id.mapbox_option)
            RadioButton radioMapBox;




  // RadioButton radioGoogle, radioArcgis;
    RecyclerView recyclerView;
    RSSObject rssObject;
    FeedAdapter adapter;
    double latitude, longitude;
    MapFragment.MarkerListener markerListener;
    String str1;
    List<String> mtitles ;
    List<String> mdescription;
    List<String> heatLatitude = new ArrayList<String>();
    List<String> heatLongitude = new ArrayList<String>();

    int mPositionTitle ;
    LinearLayout linearLayout;
    String magnitude;
    EarthQuakeService mearthQuakeService;
    ListRetroModel listRetroModel;
    RootObject retroModel;

    private String[] mPlanetTitles ={"Deneme", "deneme", "heheh"};
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;



    String returnLatStr, returnLonStr;

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

    private  final String link = "https://www.emsc-csem.org/service/rss/rss.php?typ=emsc";
    private  final String RSS_to_Json_API = "https://api.rss2json.com/v1/api.json?rss_url=";


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putStringArrayList("mtitles", (ArrayList<String>) mtitles);         //On LAndscape mode datas are dissappear To save them ....
        outState.putStringArrayList("mdescription", (ArrayList<String>) mdescription);
        outState.putStringArrayList("mheatLatitude", (ArrayList<String>) heatLatitude);
        outState.putStringArrayList("mheatLongitude", (ArrayList<String>) heatLongitude);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(getResources().getConfiguration().orientation==Configuration.ORIENTATION_PORTRAIT) {
            ButterKnife.bind(this);

            //ArcGISMap map  = new ArcGISMap(Basemap.Type.TERRAIN_WITH_LABELS, 34.056295, -117.195800, 16);
            //radioGoogle =(RadioButton) findViewById(R.id.google_map_option);
            //radioArcgis =(RadioButton) findViewById(R.id.arcgis_option);
             linearLayout = (LinearLayout) findViewById(R.id.linLay);


            radioGoogle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    radioArcgis.setChecked(false);
                    radioMapBox.setChecked(false);



                    MapFragment mapFragment = new MapFragment();

                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fm.beginTransaction();

                    fragmentTransaction.replace(R.id.mapFrame, mapFragment).commit();

                }
            });

            radioArcgis.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    radioGoogle.setChecked(false);
                    radioMapBox.setChecked(false);
                    ArcgisFragment arcgisFragment = new ArcgisFragment();

                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fm.beginTransaction();

                    fragmentTransaction.replace(R.id.mapFrame, arcgisFragment).commit();


                }
            });


            radioMapBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    radioArcgis.setChecked(false);
                    radioGoogle.setChecked(false);




                    MapBox mapBox = new MapBox();


                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fm.beginTransaction();

                    fragmentTransaction.replace(R.id.mapFrame, mapBox).commit();
                }
            });



            recyclerView = (RecyclerView) findViewById(R.id.earthQuakeList);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);


           loadRSS2();


            str1 = getPackageName();

            File s = Environment.getExternalStorageDirectory();

        /*Set Default Map To Google Map Arcgis (Doesnt Work properly)*/
            MapFragment arcgisFragment = new MapFragment();

            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();

            fragmentTransaction.add(R.id.mapFrame, arcgisFragment).commit();

        }

        /*DETERMINE THE ORIENTATION MODE OF PHONE AND THEN SET THE  LAYOUT */

        else if(getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE)
        {
           /* final ContenetFragment contenetFragment = new ContenetFragment();
            mtitles = savedInstanceState.getStringArrayList("mtitles");
            mdescription = savedInstanceState.getStringArrayList("mdescription");

            final TextView mTextView = (TextView) findViewById(R.id.myContentText);
            RecyclerView recyclerViewTitle = findViewById(R.id.titlesListView);


            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
            recyclerViewTitle.setLayoutManager(linearLayoutManager);
            TitleAdapter adapter = new TitleAdapter(getBaseContext(), mtitles, new TitleAdapter.onClickTitleListener() {
                @Override
                public void onTitleSelected(int positionOftitle) {


                    mPositionTitle =positionOftitle ;
                    mTextView.setText(mdescription.get(mPositionTitle));


                }
            });
            recyclerViewTitle.setAdapter(adapter);  */

            heatLatitude = savedInstanceState.getStringArrayList("mheatLatitude");
            heatLongitude = savedInstanceState.getStringArrayList("mheatLongitude");

            HeatMapFragment heatMapFragment = new HeatMapFragment();
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction  fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.add(R.id.heatMap,heatMapFragment).commit();


            heatMapFragment.setLatStr(heatLatitude);
            heatMapFragment.setLonStr(heatLongitude);


        }






    }

    private void loadRSS2()
    {
        @SuppressLint("StaticFieldLeak") AsyncTask<String, String,String> loadRSSAsync = new AsyncTask<String, String, String>() {


            @Override
            protected String doInBackground(String... strings) {
                String result;

                HTTPDataHandler http =new HTTPDataHandler();
                result = http.getHTTPData(strings[0]);
                return  result;

            }

            @Override
            protected void onPostExecute(String s) {

               RootObject rootObject[] = new Gson().fromJson(s,RootObject[].class);





                adapter = new FeedAdapter(rootObject, getBaseContext(), new FeedAdapter.onListClickedRowListner() {
                    @Override
                    public void onListSelected(String lat, String lon, List<String> titles, String mg) {

                        latitude= Double.parseDouble(lat);
                        longitude= Double.parseDouble(lon);
                        magnitude = mg;

                        MapFragment mapFragment = (MapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFrame);

                        if(mapFragment!= null)
                        {
                            GoogleMap googleMap;

                            mapFragment.setLatitude(latitude);
                            mapFragment.setLongitude(longitude);
                            //mapFragment.onMapReady(googleMap);
                            mapFragment.setMarker();

                           // mapFragment.mgoogleMap


                        }

                     //   setLatitude(latitude);
                     //   setLongitude(longitude);


                    }
                },linearLayout,recyclerView);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                for(int i =0; i<rootObject.length; i++)
                {
                    heatLatitude.add(rootObject[i].getLatitude());
                    heatLongitude.add(rootObject[i].getLongitude());
                }


            }
        };

      //  StringBuilder sb = new StringBuilder(RSS_to_Json_API);
       // sb.append(link);
        //loadRSSAsync.execute(sb.toString());

        loadRSSAsync.execute("https://earthquake-report.com/feeds/recent-eq?json");

        }






    @Override
    public void getLocation(String lat, String lon, String description) {

        MapFragment mapFragment = (MapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFrame);

        if(mapFragment!= null)
        {


        }


    }


    @Override
    public void onClick(View view, int position, boolean isLongClick) {


    }






    @Override
    public void onListSelected(String lat, String lon, List<String> titles, String magnitude) {

    }
}
