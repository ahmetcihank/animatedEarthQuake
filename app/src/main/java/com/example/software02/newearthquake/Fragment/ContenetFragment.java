package com.example.software02.newearthquake.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.software02.newearthquake.R;

/**
 * Created by SOFTWARE02 on 2/4/2018.
 */

public class ContenetFragment extends Fragment {

    TextView contentText;
    private String description ;

    private static ContenetFragment instance = new ContenetFragment();

    public static ContenetFragment getInstance() {   // Singleton Pattern
        return instance;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.eq_content_layout,container, false);


        return root;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        contentText = (TextView) getView().findViewById(R.id.contentText);


    }

    public void setContentText(String cnt){
        contentText.setText(getDescription());
    }


    public void setDescription(String description)
    {
        this.description = description ;
    }


    public String getDescription() {
        return description;
    }
}
