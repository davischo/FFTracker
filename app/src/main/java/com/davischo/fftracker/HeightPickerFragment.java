package com.davischo.fftracker;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import static com.davischo.fftracker.FFFragment.refreshFFFragment;
import static com.davischo.fftracker.MainActivity.editor;
import static com.davischo.fftracker.MainActivity.refreshAll;

/**
 * Created by yx on 2018/6/17.
 */

public class HeightPickerFragment extends DialogFragment implements NumberPicker.OnValueChangeListener{
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final NumberPicker numberPicker = new NumberPicker(getActivity());
        numberPicker.setMinValue(130);
        numberPicker.setMaxValue(250);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Choose Height");
        builder.setMessage("Choose a number:");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                onValueChange(numberPicker, numberPicker.getValue(), numberPicker.getValue());
                //set new height edittext;
                editor.putInt("height", numberPicker.getValue()).commit();
                refreshFFFragment();
                EditText heightEditText = getActivity().findViewById(R.id.heightEditText);
                heightEditText.setText(numberPicker.getValue() + " CM");
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //do nothing.
            }
        });
        builder.setView(numberPicker);
        return builder.create();
    }
    public void onValueChange(NumberPicker numberPicker, int i, int i1) {
        Toast.makeText(getActivity(), "Height Changed!", Toast.LENGTH_SHORT).show();
    }
}
