package com.davischo.fftracker;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Switch;

import static com.davischo.fftracker.First_Run_Activity.WEIGHT_DEFAULT;
import static com.davischo.fftracker.First_Run_Activity.sharedPreferences;

/**
 * Created by yx on 2018/6/12.
 */

public class AccountFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_account, container, false);
        //display user stats on my account page
        String gender = sharedPreferences.getString("gender", "male");
        int dobYear = sharedPreferences.getInt("dobYear", 1980);
        int dobMonth = sharedPreferences.getInt("dobMonth", 1);
        int dobDay = sharedPreferences.getInt("dobDay", 1);
        int height = sharedPreferences.getInt("height", 165);
        int weight = sharedPreferences.getInt("weight", WEIGHT_DEFAULT);
        int activity_level = sharedPreferences.getInt("activity_level", 0);
        int goal = sharedPreferences.getInt("goal", 0);

        Switch genderSwitch = rootView.findViewById(R.id.genderSwitch);
        if(gender == "male") genderSwitch.setChecked(false);
        else genderSwitch.setChecked(true);

        EditText birthdayEditText = rootView.findViewById(R.id.birthdayEditText);
        birthdayEditText.setText(dobMonth+"/"+ dobDay +"/"+ dobYear);

        EditText heightEditText = rootView.findViewById(R.id.heightEditText);
        heightEditText.setText(height + " CM");

        EditText weightEditText = rootView.findViewById(R.id.weightEditText);
        weightEditText.setText(weight + " KG");

        EditText activityLevelEditText = rootView.findViewById(R.id.activityLevelEditText);
        String[] activity_level_strArray = getResources().getStringArray(R.array.activity_level_array);
        activityLevelEditText.setText(activity_level_strArray[activity_level]);

        EditText goalEditText = rootView.findViewById(R.id.goalEditText);
        String[] goal_strArray = getResources().getStringArray(R.array.goal_array);
        goalEditText.setText(goal_strArray[goal]);

        return rootView;
    }
}
