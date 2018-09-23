package com.davischo.fftracker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;

import static com.davischo.fftracker.First_Run_Activity.editor;

/**
 * Created by yx on 2018/6/14.
 */

public class askingWeightFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.asking_weight_layout, container, false);
        NumberPicker weightNumberPicker = (NumberPicker) rootView.findViewById(R.id.weightNumberPicker);
        weightNumberPicker.setMinValue(1);
        weightNumberPicker.setMaxValue(2000);
        weightNumberPicker.setValue(650);
        String[] displayVals = new String[1990];
        for(int i = 0; i < 1990; i++){
            displayVals[i] = new String("" + (double)(i+1)/10);
        }
        weightNumberPicker.setDisplayedValues(displayVals);
        weightNumberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                editor.putFloat("weight", (float)(i1/10.0)).commit();
                Log.i("weight", String.valueOf(i1));
            }
        });
        return rootView;
    }
}
