package com.davischo.fftracker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import static com.davischo.fftracker.First_Run_Activity.editor;
import static com.davischo.fftracker.First_Run_Activity.sharedPreferences;

/**
 * Created by yx on 2018/6/11.
 */

public class askingGenderFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.asking_gender_layout, container, false);
        //do stuff...eg: store user input.
        Switch genderSwitch = rootView.findViewById(R.id.genderSwitch);
        genderSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(!b) {
                    editor.putString("gender", "male").commit();
                }else {
                    editor.putString("gender", "female").commit();
                }
                Log.i("gender stored is: ",  sharedPreferences.getString("gender", "male"));
            }
        });
        return rootView;
    }
}
