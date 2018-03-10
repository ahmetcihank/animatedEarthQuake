package com.example.software02.newearthquake.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.software02.newearthquake.Interface.ItemClickListener;
import com.example.software02.newearthquake.R;

/**
 * Created by SOFTWARE02 on 2/4/2018.
 */

public class TitleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    TextView titleTextView;
    ItemClickListener itemClickListener;

    public TitleViewHolder(View itemView) {
        super(itemView);

        titleTextView = (TextView) (itemView).findViewById(R.id.earthQuakeTitle);
        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
     itemClickListener.onClick(view,getAdapterPosition(),false);
    }

    public void setItemClickListener(ItemClickListener itemClickListener)
    {
        this.itemClickListener = itemClickListener;
    }
}
