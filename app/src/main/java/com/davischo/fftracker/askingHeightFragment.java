package com.davischo.fftracker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;

import static com.davischo.fftracker.MainActivity.editor;

/**
 * Created by yx on 2018/6/11.
 */

public class askingHeightFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.asking_height_layout, container, false);
        NumberPicker heightPicker = rootView.findViewById(R.id.heightPicker);
        heightPicker.setMinValue(130);
        heightPicker.setMaxValue(250);
        heightPicker.setValue(165);
        heightPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                editor.putInt("height", numberPicker.getValue()).commit();
            }
        });
        return rootView;
    }
}
