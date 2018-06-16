package com.davischo.fftracker;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import java.util.Calendar;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import static android.app.AlertDialog.THEME_HOLO_LIGHT;
import static com.davischo.fftracker.First_Run_Activity.DAY_DEFAULT;
import static com.davischo.fftracker.First_Run_Activity.MONTH_DEFAULT;
import static com.davischo.fftracker.First_Run_Activity.YEAR_DEFAULT;
import static com.davischo.fftracker.First_Run_Activity.editor;
import static com.davischo.fftracker.First_Run_Activity.sharedPreferences;

/**
 * Created by yx on 2018/6/12.
 */

public class birthDatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = sharedPreferences.getInt("dobYear", YEAR_DEFAULT);
        int month = sharedPreferences.getInt("dobMonth", MONTH_DEFAULT);
        int day = sharedPreferences.getInt("dobDay", DAY_DEFAULT);
        // Create a new instance of DatePickerDialog and return it
        DatePickerDialog dialog =  new DatePickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT, this, year, month-1, day);
        dialog.getDatePicker().setMaxDate(c.getTimeInMillis());  //disable future time selection
        dialog.setTitle("Select your birthday");
        return dialog;

    }
    public void onDateSet(DatePicker view, int year, int month, int day) {

        EditText birthdayEditText = getActivity().findViewById(R.id.birthdayEditText);
        birthdayEditText.setText((month+1)+"/"+ day +"/"+ year);
        int age = FFTrackerHelper.getAge(year, month+1, day);
        //save to local storage for now:
        editor.putInt("dobYear", year);
        editor.putInt("dobMonth", month + 1);
        editor.putInt("dobDay", day);
        editor.putInt("age", age);

        editor.commit();

        Toast.makeText(getActivity(), "Your information successfully updated", Toast.LENGTH_SHORT).show();
        //push DOB data onto database:


    }

}

