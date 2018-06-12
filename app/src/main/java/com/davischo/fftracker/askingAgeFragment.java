package com.davischo.fftracker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import java.util.Calendar;

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
        DatePicker birthDatePicker = rootView.findViewById(R.id.birthDatePicker);
        birthDatePicker.init(1980, 0, 1, new DatePicker.OnDateChangedListener(){
            @Override
            public void onDateChanged(DatePicker datePicker, int year, int month, int dayOfMonth) {
                editor.putInt("dobYear", year);
                editor.putInt("dobMonth", month + 1);
                editor.putInt("dobDay", dayOfMonth);
                editor.commit();
            }
        });
        return rootView;
    }
}
