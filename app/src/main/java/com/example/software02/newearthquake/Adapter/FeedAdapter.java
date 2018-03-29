package com.example.software02.newearthquake.Adapter;

import android.animation.TimeInterpolator;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.software02.newearthquake.Fragment.MapFragment;
import com.example.software02.newearthquake.Interface.ItemClickListener;
import com.example.software02.newearthquake.Model.RootObject;
import com.example.software02.newearthquake.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SOFTWARE02 on 1/25/2018.
 */


public class FeedAdapter extends RecyclerView.Adapter<FeedViewHolder> {

    private  LinearLayout linearLayout;
    private Context mContext;
    private LayoutInflater inflater;
    private String description = "";
    private List<String> titles = new ArrayList<String>();
    onListClickedRowListner onListClickedRowListner;
    String latStr;
    String lonStr;
    RootObject rootObject[];
    int mExpandedPosition = 0;
    private RecyclerView recyclerView;
    private SparseBooleanArray expandState = new SparseBooleanArray();

    MapFragment.MarkerListener markerListener = new MapFragment.MarkerListener() {
        @Override
        public void getLocation(String lat, String lon, String description) {

        }
    };



    public interface onListClickedRowListner {
        void onListSelected(String lat, String lon, List<String> titles, String magnitude);


    }



    public FeedAdapter(RootObject rootObject[], Context mContext , onListClickedRowListner onListClickedRowListner,
                       LinearLayout linearLayout, RecyclerView recyclerView) {
       // this.rssObject = rssObject;

        this.mContext = mContext;
        this.onListClickedRowListner = onListClickedRowListner;
        this.linearLayout = linearLayout;
        this.rootObject = rootObject;
        this.recyclerView = recyclerView;

        for (int i = 0; i < rootObject.length; i++) {
            expandState.append(i, false);
        }

    }




    @Override
    public FeedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.source_layout,parent,false);
        return new FeedViewHolder(itemView);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(final FeedViewHolder holder, int position) {


        holder.earthquakeLocation.setText(rootObject[position].getLocation());
      //  holder.eartQuakeTitle.setText( rootObject[position].getDate_time());
        holder.getEarthquakeDepth.setText(rootObject[position].getDepth());
        holder.getEarthquakeMagnitude.setText(rootObject[position].getMagnitude());


       // DateFormat df = DateFormat.getDateInstance(DateFormat.LONG, Locale.ENGLISH);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String earthQuakeDateString = df.format(rootObject[position].getDate_time());

        holder.earthQuakeTimeValue.setText(earthQuakeDateString);


        final  String mG = rootObject[position].getMagnitude();
        latStr = rootObject[position].getLatitude();
        lonStr = rootObject[position].getLongitude();


        for(int i=0; i<rootObject.length;i++)
        {
            titles.add(rootObject[i].getTitle());
        }

        final float magnitute = Float.parseFloat(mG);


        /*SET BACKGROUND COLOR OF CARD VİEWS WITH RESPECT TO DISASTER MAGNITUDE :)*/
        if(magnitute>=1 && magnitute<2)
           ((CardView)holder.cardView).setBackgroundColor( Color.parseColor("#ff7272"));
        else if(magnitute>=2 && magnitute<3)
            ((CardView)holder.cardView).setBackgroundColor( Color.parseColor("#ff5e5e"));
        else if(magnitute>=3 && magnitute<4)
            ((CardView)holder.cardView).setBackgroundColor( Color.parseColor("#ff4b4b"));
        else if(magnitute>=4 && magnitute<5)
            ((CardView)holder.cardView).setBackgroundColor( Color.parseColor("#ff2323"));
        else if(magnitute>=5 && magnitute<6)
            ((CardView)holder.cardView).setBackgroundColor( Color.parseColor("#ff1010"));
        else if(magnitute>=6 && magnitute<7)
            ((CardView)holder.cardView).setBackgroundColor( Color.parseColor("#fb0000"));
        else if(magnitute>=7 && magnitute<8)
            ((CardView)holder.cardView).setBackgroundColor( Color.parseColor("#e70000"));
        else if(magnitute>=8 && magnitute<9)
            ((CardView)holder.cardView).setBackgroundColor( Color.parseColor("#d40000"));
        else if(magnitute>9)
            ((CardView)holder.cardView).setBackgroundColor( Color.parseColor("#d40000"));








        //check if view is expanded
        final boolean isExpanded = expandState.get(position);
        holder.expendableView.setVisibility(isExpanded?View.VISIBLE:View.GONE);

        holder.button.setRotation(expandState.get(position) ? 180f : 0f);


        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, final int position, boolean isLongClick) {


                latStr = rootObject[position].getLatitude();
                lonStr = rootObject[position].getLongitude();

                onListClickedRowListner.onListSelected(latStr,lonStr, titles, mG);
                markerListener.getLocation(latStr,lonStr, description);
                Toast.makeText(mContext,"Wow", Toast.LENGTH_SHORT).show();

                final Animation animShake = AnimationUtils.loadAnimation(mContext,R.anim.shake);

                float FREQ = 0f;  //Initialize vibrating frequency //3F
                final float DECAY = 3f; //2F

                 if ( magnitute>=1 && magnitute<2)
                    FREQ = 1f;
                else if(magnitute>=2 && magnitute<3)
                    FREQ = 2f;
                else if(magnitute>=3 && magnitute<4)
                    FREQ = 3f;
                else if(magnitute>=4 && magnitute<5)
                    FREQ = 4f;
                else if(magnitute>=5 && magnitute<6)
                    FREQ = 5f;
                else if(magnitute>=6 && magnitute<7)
                    FREQ = 6f;
                else if(magnitute>=7 && magnitute<8)
                    FREQ = 7f;
                else if(magnitute>=8 && magnitute<9)
                    FREQ = 8f;
                else if(magnitute>9)
                    FREQ= 9f;

                final float mFREQ = FREQ;

                // interpolator that goes 1 -> -1 -> 1 -> -1 in a sine wave pattern.
                TimeInterpolator decayingSineWave = new TimeInterpolator() {
                    @Override
                    public float getInterpolation(float input) {
                        double raw = Math.sin(mFREQ * input * 2 * Math.PI);
                        return (float)(raw * Math.exp(-input * DECAY));
                    }
                };

                linearLayout.animate()
                        .xBy(-100)
                        .setInterpolator(decayingSineWave)
                        .setDuration(500)
                        .start();

                if (holder.expendableView.getVisibility() == View.VISIBLE){
                   // createRotateAnimator(holder.button, 180f, 0f).start();
                    holder.expendableView.setVisibility(View.GONE);

                    expandState.put(position, false);
                }else{
                  //  createRotateAnimator(holder.button, 0f, 180f).start();
                    holder.expendableView.setVisibility(View.VISIBLE);

                    expandState.put(position, true);
                }

            }
            //Code to rotate button
         /*   private ObjectAnimator createRotateAnimator(final View target, final float from, final float to) {
                ObjectAnimator animator = ObjectAnimator.ofFloat(target, "rotation", from, to);
                animator.setDuration(300);
                animator.setInterpolator(new LinearInterpolator());
                return animator;
            } */

        });





    }

    @Override
    public int getItemCount() {
        return rootObject.length;
    }

}
