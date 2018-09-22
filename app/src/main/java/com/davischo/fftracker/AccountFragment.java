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

import static com.davischo.fftracker.First_Run_Activity.ACTIVITY_LEVEL_DEFAULT;
import static com.davischo.fftracker.First_Run_Activity.DAY_DEFAULT;
import static com.davischo.fftracker.First_Run_Activity.GOAL_DEFAULT;
import static com.davischo.fftracker.First_Run_Activity.HEIGHT_DEFAULT;
import static com.davischo.fftracker.First_Run_Activity.MONTH_DEFAULT;
import static com.davischo.fftracker.First_Run_Activity.WEIGHT_DEFAULT;
import static com.davischo.fftracker.First_Run_Activity.sharedPreferences;

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
        String gender = FFTrackerHelper.getLocalGender(); //sharedPreferences.getString("gender", GENDER_DEFAULT);
        int dobYear = FFTrackerHelper.getLocalDOBYear(); //sharedPreferences.getInt("dobYear", YEAR_DEFAULT);
//        System.out.println("CAL REMAINING IS " + FFTrackerHelper.calculateCalRemain());
        int dobMonth = sharedPreferences.getInt("dobMonth", MONTH_DEFAULT);
        int dobDay = sharedPreferences.getInt("dobDay", DAY_DEFAULT);
        int height = sharedPreferences.getInt("height", HEIGHT_DEFAULT);
        int weight = sharedPreferences.getInt("weight", WEIGHT_DEFAULT);
        int activity_level = sharedPreferences.getInt("activity_level", ACTIVITY_LEVEL_DEFAULT);
        int goal = sharedPreferences.getInt("goal", GOAL_DEFAULT);

        /*display user stats*/
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

        //TODO weight editing

        return rootView;
    }
}
