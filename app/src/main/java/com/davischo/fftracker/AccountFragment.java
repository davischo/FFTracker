package com.davischo.fftracker;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import static com.davischo.fftracker.FFTrackerHelper.*;

/**
 * Created by yx on 2018/6/12.
 */

public class AccountFragment extends Fragment {

    FragmentManager fm = getFragmentManager();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_account, container, false);

        //display user stats on my account page
        String gender = getLocalGender(); //sharedPreferences.getString("gender", GENDER_DEFAULT);
        int dobYear = getLocalDOBYear(); //sharedPreferences.getInt("dobYear", YEAR_DEFAULT);
        int dobMonth = getLocalDOBMonth(); //sharedPreferences.getInt("dobMonth", MONTH_DEFAULT);
        int dobDay = getLocalDOBDay(); //sharedPreferences.getInt("dobDay", DAY_DEFAULT);
        int height = getLocalHeight(); //sharedPreferences.getInt("height", HEIGHT_DEFAULT);
        float weight = getLocalWeight(); //sharedPreferences.getFloat("weight", WEIGHT_DEFAULT);
        int activity_level = getLocalActivityLevel(); //sharedPreferences.getInt("activity_level", ACTIVITY_LEVEL_DEFAULT);
        int goal = getLocalGoal(); //sharedPreferences.getInt("goal", GOAL_DEFAULT);

        /*display user stats*/
        Switch genderSwitch = rootView.findViewById(R.id.genderSwitch);
        if(gender == "male") genderSwitch.setChecked(false);
        else genderSwitch.setChecked(true);

        EditText birthdayEditText = rootView.findViewById(R.id.birthdayEditText);
        birthdayEditText.setText(dobMonth+"/"+ dobDay +"/"+ dobYear);

        EditText heightEditText = rootView.findViewById(R.id.heightEditText);
        heightEditText.setText(height + " cm");

        TextView weightEditText = rootView.findViewById(R.id.weightEditText);
        weightEditText.setText(weight + " kg");

        EditText activityLevelEditText = rootView.findViewById(R.id.activityLevelEditText);
        String[] activity_level_strArray = getResources().getStringArray(R.array.activity_level_array);
        activityLevelEditText.setText(activity_level_strArray[activity_level]);

        EditText goalEditText = rootView.findViewById(R.id.goalEditText);
        String[] goal_strArray = getResources().getStringArray(R.array.goal_array);
        goalEditText.setText(goal_strArray[goal]);

        /*edit user stats*/
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
}
