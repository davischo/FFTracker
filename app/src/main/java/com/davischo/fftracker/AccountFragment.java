package com.davischo.fftracker;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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

    static Fragment accFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_account, container, false);

        accFragment = this;

        //display user stats on my account page
        String gender = getLocalGender();
        int dobYear = getLocalDOBYear();
        int dobMonth = getLocalDOBMonth();
        int dobDay = getLocalDOBDay();
        int height = getLocalHeight();
        float weight = getLocalWeight();
        int activity_level = getLocalActivityLevel();
        int goal = getLocalGoal();

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

    public static void refreshAccountFragment(){
        FragmentTransaction ft = accFragment.getFragmentManager().beginTransaction();
        ft.detach(accFragment).attach(accFragment).commit();
    }
}
