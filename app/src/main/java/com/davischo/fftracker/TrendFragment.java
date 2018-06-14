package com.davischo.fftracker;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by davischo on 6/14/18.
 */

public class TrendFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                         Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_trend, container, false);
        return rootView;
    }
}
