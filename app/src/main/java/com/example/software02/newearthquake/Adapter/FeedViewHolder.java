package com.example.software02.newearthquake.Adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.software02.newearthquake.Interface.ItemClickListener;
import com.example.software02.newearthquake.R;

import org.w3c.dom.Text;

/**
 * Created by SOFTWARE02 on 1/25/2018.
 */

public class FeedViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView earthquakeLocation, getEarthquakeMagnitude;  // Head Section of CardView
    TextView eartQuakeTitle, getEarthquakeDepth; // Expanded Section Section of CardView
    TextView earthQuakeTimeTitle, earthQuakeTimeValue; // Also Expendable Section But Time Value and Title
    ItemClickListener itemClickListener;
    CardView cardView;
    LinearLayout button, expendableView;





    public FeedViewHolder(View itemView) {
        super(itemView);

        earthquakeLocation = (TextView) itemView.findViewById(R.id.earthQuakeLocation);
        getEarthquakeMagnitude =(TextView) itemView.findViewById(R.id.earthQuakeMagnitude);
        eartQuakeTitle =(TextView) itemView.findViewById(R.id.disasterTitle);
        getEarthquakeDepth= (TextView) itemView.findViewById(R.id.disasterDepth);
        cardView = (CardView) itemView.findViewById(R.id.post_card_view);
        button = (LinearLayout) itemView.findViewById(R.id.button);
        expendableView = (LinearLayout) itemView.findViewById(R.id.expandableLayout);
        earthQuakeTimeTitle = (TextView) itemView.findViewById(R.id.disasterTimeTitle);
        earthQuakeTimeValue = (TextView) itemView.findViewById(R.id.disasterTimeValue);
       itemView.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {

        itemClickListener.onClick(view,getAdapterPosition(),false);

    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;

    }
}
