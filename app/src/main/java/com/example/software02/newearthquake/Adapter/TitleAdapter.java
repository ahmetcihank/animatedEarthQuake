package com.example.software02.newearthquake.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.software02.newearthquake.Interface.ItemClickListener;
import com.example.software02.newearthquake.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SOFTWARE02 on 2/4/2018.
 */

public class TitleAdapter extends RecyclerView.Adapter<TitleViewHolder> {


    private List<String> titles = new ArrayList<String>();
    private Context context;
    onClickTitleListener onClickTitleListener;

    public TitleAdapter(Context context, List<String> titles , onClickTitleListener onClickTitleListener)
    {
        this.titles= titles;
        this.context = context;
        this.onClickTitleListener = onClickTitleListener;
    }


    public interface onClickTitleListener {
        void onTitleSelected(int positionOftitle);
    }


    @Override
    public TitleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.title_source_layout,parent, false);
        return  new TitleViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TitleViewHolder holder, int position)
    {
        holder.titleTextView.setText(titles.get(position));
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                    onClickTitleListener.onTitleSelected(position);
            }
        });

    }

    @Override
    public int getItemCount()
    {
        return titles.size();
    }
}
