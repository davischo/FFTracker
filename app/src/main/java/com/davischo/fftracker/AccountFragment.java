package com.davischo.fftracker;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.KeyListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import static com.davischo.fftracker.FFTrackerHelper.*;
import static com.davischo.fftracker.MainActivity.editor;

/**
 * Created by yx on 2018/6/12.
 */

public class AccountFragment extends Fragment {

    static Fragment accFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_account, container, false);

        accFragment = this;

        //display user stats on my account page
        String gender = getLocalGender();
        String name = getLocalName();
        int dobYear = getLocalDOBYear();
        int dobMonth = getLocalDOBMonth();
        int dobDay = getLocalDOBDay();
        int height = getLocalHeight();
        float weight = getLocalWeight();
        final int activity_level = getLocalActivityLevel();
        int goal = getLocalGoal();

        /*display user stats*/
        Switch genderSwitch = rootView.findViewById(R.id.genderSwitch);
        if(gender == "male") genderSwitch.setChecked(false);
        else genderSwitch.setChecked(true);

        EditText nameEditText = rootView.findViewById(R.id.usernameEditText);
        nameEditText.setText(name);

        EditText birthdayEditText = rootView.findViewById(R.id.birthdayEditText);
        birthdayEditText.setText(dobMonth+"/"+ dobDay +"/"+ dobYear);

        EditText heightEditText = rootView.findViewById(R.id.heightEditText);
        heightEditText.setText(height + " cm");

        TextView weightText = rootView.findViewById(R.id.weightText);
        weightText.setText(weight + " kg");

        Spinner activityLevelSpinner = rootView.findViewById(R.id.activityLevelSpinner);
        String[] activity_level_strArray = getResources().getStringArray(R.array.activity_level_array);
        ArrayAdapter activityLevelAdapter = new ArrayAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item, activity_level_strArray);
        activityLevelSpinner.setAdapter(activityLevelAdapter);
        activityLevelSpinner.setSelection(activity_level);
        activityLevelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                editor.putInt("activity_level", i).commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Spinner goalSpinner = rootView.findViewById(R.id.goalSpinner);
        String[] goal_strArray = getResources().getStringArray(R.array.goal_array);
        ArrayAdapter goalAdapter = new ArrayAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item, goal_strArray);
        goalSpinner.setAdapter(goalAdapter);
        goalSpinner.setSelection(goal);
        goalSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                editor.putInt("goal", i).commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        /*edit user stats*/
        nameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                editor.putString("name", editable.toString());
            }
        });

        birthdayEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment birthDatePickerDialogFragment = new birthDatePickerFragment();
                birthDatePickerDialogFragment.show(getFragmentManager(), "birthDatePickerDialogFragment");
            }
        });

        heightEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HeightPickerFragment newFragment = new HeightPickerFragment();
                newFragment.show(getFragmentManager(), "height picker");
            }
        });

        return rootView;
    }

    public static void refreshAccountFragment(){
        FragmentTransaction ft = accFragment.getFragmentManager().beginTransaction();
        ft.detach(accFragment).attach(accFragment).commit();
    }
}
