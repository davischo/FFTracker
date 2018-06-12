package com.davischo.fftracker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;

import static com.davischo.fftracker.First_Run_Activity.editor;

/**
 * Created by yx on 2018/6/11.
 */

public class askingAgeFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.asking_age_layout, container, false);
        NumberPicker agePicker = rootView.findViewById(R.id.agePicker);
        agePicker.setMinValue(1);
        agePicker.setMaxValue(100);
        agePicker.setValue(25);
        agePicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                editor.putInt("age", numberPicker.getValue()).commit();
            }
        });
        return rootView;
    }
}
