package com.example.software02.newearthquake.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.software02.newearthquake.R;

import org.w3c.dom.Text;

/**
 * Created by SOFTWARE02 on 3/24/2018.
 */

public class CriteriaDialogFragment extends DialogFragment
{

    Integer criteriaKm =0;

    public static CriteriaDialogFragment getNewInstance()
    {
        CriteriaDialogFragment fr = new CriteriaDialogFragment();
        return fr;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View root;
        root= inflater.inflate(R.layout.criteria_fragment_dialog,container, false);

        TextView popupText = (TextView) root.findViewById(R.id.popUpText);

        return root;
    }
}
