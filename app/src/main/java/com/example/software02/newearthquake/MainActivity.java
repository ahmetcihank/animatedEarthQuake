package com.example.software02.newearthquake;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;


import com.example.software02.newearthquake.Adapter.FeedAdapter;
import com.example.software02.newearthquake.Fragment.OsmFragment;
import com.example.software02.newearthquake.Fragment.CriteriaDialogFragment;
import com.example.software02.newearthquake.Fragment.HeatMapFragment;

import com.example.software02.newearthquake.Fragment.MapFragment;
import com.example.software02.newearthquake.Helper.HTTPDataHandler;
import com.example.software02.newearthquake.Interface.ItemClickListener;
import com.example.software02.newearthquake.Model.RootObject;
import com.google.android.gms.maps.GoogleMap;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MapFragment.MarkerListener, ItemClickListener,FeedAdapter.onListClickedRowListner
        {


    @BindView(R.id.google_map_option)
            RadioButton radioGoogle;
    @BindView(R.id.arcgis_option)
            RadioButton radioOsm;



  // RadioButton radioGoogle, radioArcgis;
    RecyclerView recyclerView;
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


    RootObject retroModel;

    private String[] mPlanetTitles ={"Deneme", "deneme", "heheh"};
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    boolean googleOp =true;


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


            Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
            setSupportActionBar(myToolbar);

             linearLayout = (LinearLayout) findViewById(R.id.linLay);


            radioGoogle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    radioOsm.setChecked(false);

                    MapFragment mapFragment = new MapFragment();

                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fm.beginTransaction();

                    fragmentTransaction.replace(R.id.mapFrame, mapFragment).commit();

                    googleOp =true;

                }
            });

            radioOsm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    radioGoogle.setChecked(false);

                    OsmFragment osmFragment = new OsmFragment();

                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fm.beginTransaction();

                    fragmentTransaction.replace(R.id.mapFrame, osmFragment).commit();

                    googleOp =false;


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



                        if( googleOp ==true)

                        {
                            MapFragment mapFragment = (MapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFrame);

                            if (mapFragment != null) {
                                // GoogleMap googleMap;

                                mapFragment.setLatitude(latitude);
                                mapFragment.setLongitude(longitude);
                                //mapFragment.onMapReady(googleMap);
                                mapFragment.setMarker();

                            }

                        }


                        else if(googleOp==false) {
                            try {

                                OsmFragment osmFragment = (OsmFragment) getSupportFragmentManager().findFragmentById(R.id.mapFrame);
                                if (osmFragment != null) {
                                    osmFragment.setLatitude(latitude);
                                    osmFragment.setLongitude(longitude);
                                    osmFragment.setMarker();

                                }
                            } catch (Error err) {
                            }
                        }


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

    @Override
     public boolean onCreateOptionsMenu(Menu menu)
     {
         MenuInflater menuInflater = getMenuInflater();
         menuInflater.inflate(R.menu.m, menu);
         return  true;
     }


            @Override
            public boolean onOptionsItemSelected(MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.action_add_criteria:
                        Toast.makeText(getBaseContext(),"This is Criteria",Toast.LENGTH_SHORT).show();
                        FragmentManager fragmentManager =   getSupportFragmentManager();
                        Fragment frag = fragmentManager.findFragmentByTag("criteria_frag");

                        if (frag != null)
                        {
                                  fragmentManager.beginTransaction().remove(frag).commit();
                        }
                        
                        CriteriaDialogFragment criteriaDialogFragment = CriteriaDialogFragment.getNewInstance();
                        criteriaDialogFragment.show(fragmentManager,"criteria_frag");
                        

                }
                return true;
            }
        }
