package com.davischo.fftracker;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;

import static com.davischo.fftracker.R.layout.fragment_ff;

/**
 * Created by davischo on 6/14/18.
 */

public class FFFragment extends Fragment{
    PieChart calChart;
    LinearLayout display;
    FloatingActionButton add;
    LinearLayout add_menu;
    static EditText add_weight, add_exercised, add_eaten;
    static EditText food_name, exercise_name;
    Button add_submit;
    static SQLiteDatabase db;

    public static void submitClicked(View v){
        if(add_weight.getText().toString().equals("") && add_exercised.getText().toString().equals("")
                        && add_eaten.getText().toString().equals("")) {
                Toast.makeText(v.getContext(), "Please Input at Least One Value", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(v.getContext(), "Updated!", Toast.LENGTH_SHORT).show();
            }
            if(!add_weight.getText().toString().equals("")){
                String rounded = String.format("%.1f", Float.valueOf(add_weight.getText().toString()));
                System.out.println("WEIGHT ENTERED IS:" + rounded);
                //STORE DATA HERE
                add_weight.setText("");
            }
            if(!add_exercised.getText().toString().equals("")){
                System.out.println("EXERCISE ENTERED IS:" + Integer.valueOf(add_exercised.getText().toString()));
                db.execSQL("INSERT INTO exercise VALUES ('" + FFTrackerHelper.getCurrentDate() + "', '"
                        + exercise_name.getText().toString()+ "', "
                        + Integer.valueOf(add_exercised.getText().toString()) + ")");
                //STORE DATA HERE
                add_exercised.setText("");
            }
            if(!add_eaten.getText().toString().equals("")) {
                System.out.println("FOOD ENTERED IS:" + Integer.valueOf(add_eaten.getText().toString()));
                db.execSQL("INSERT INTO food VALUES ('" + FFTrackerHelper.getCurrentDate() + "', '"
                        + food_name.getText().toString()+ "', "
                        + Integer.valueOf(add_eaten.getText().toString()) + ")");
                //STORE DATA HERE
                add_eaten.setText("");
            }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(fragment_ff, container, false);

        display = rootView.findViewById(R.id.display);

        //Add food list divided
        TextView title = new TextView(getContext());
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.BLACK);
        title.setTextSize(16);
        title.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        title.setText("Food");
        display.addView(title);
        LinearLayout newLin;
        TextView newText;
        Button newButton;

        int foodCal = 0, exerciseCal = 0;

        db = getActivity().openOrCreateDatabase("dataPoints", Context.MODE_PRIVATE, null);
        //TODO Make it show today's food only
        //Populate food list
        if(db.rawQuery("SELECT * FROM food WHERE time LIKE '" +
                FFTrackerHelper.getCurrentDate() + "'", null).getCount()!=0) {
            Cursor c = db.rawQuery("SELECT * FROM food WHERE time LIKE '" +
                    FFTrackerHelper.getCurrentDate() + "'", null);
            int nameIndex = c.getColumnIndex("name");
            int calIndex = c.getColumnIndex("calories");
            c.moveToFirst();
            while (!c.isAfterLast()) {
                foodCal += c.getInt(calIndex);
                newLin = new LinearLayout(getContext());
                newText = new TextView(getContext());
                newButton = new Button(getContext());
                newLin.setOrientation(LinearLayout.HORIZONTAL);
                newText.setText(c.getString(nameIndex) + " " + c.getString(calIndex) + "cal" );
                newText.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT, 1));
                newText.setGravity(Gravity.CENTER);
                newLin.addView(newText);
                newButton.setText("Delete");
                newButton.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT, 3));
                newButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //TODO actually delete the content
                        System.out.println("DELETE BUTTON PRESSED");
                    }
                });
                newLin.addView(newButton);
                display.addView(newLin);
                c.moveToNext();
            }
        }
        else{
            TextView empty = new TextView(getContext());
            empty.setText("Please input a value for today.");
            display.addView(empty);
        }

        //Add exercise list divider
        title = new TextView(getContext());
        title.setText("Exercise");
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.BLACK);
        title.setTextSize(16);
        title.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        display.addView(title);

        //Populate exercise list
        //TODO make it today's exercise only
        if(db.rawQuery("SELECT * FROM exercise WHERE time LIKE '" +
                FFTrackerHelper.getCurrentDate() + "'", null).getCount()!=0) {
            Cursor c = db.rawQuery("SELECT * FROM exercise WHERE time LIKE '" +
                    FFTrackerHelper.getCurrentDate() +"'", null);
            int nameIndex = c.getColumnIndex("name");
            int calIndex = c.getColumnIndex("calories");
            c.moveToFirst();
            while (!c.isAfterLast()) {
                exerciseCal += c.getInt(calIndex);
                newLin = new LinearLayout(getContext());
                newText = new TextView(getContext());
                newButton = new Button(getContext());
                newLin.setOrientation(LinearLayout.HORIZONTAL);
                newText.setText(c.getString(nameIndex) + " " + c.getString(calIndex) + "cal" );
                newText.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT, 1));
                newText.setGravity(Gravity.CENTER);
                newLin.addView(newText);
                newButton.setText("Delete");
                newButton.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT, 3));
                newButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //TODO actually delete the content
                        System.out.println("DELETE BUTTON PRESSED");
                    }
                });
                newLin.addView(newButton);
                display.addView(newLin);
                c.moveToNext();
            }
        }
        else{
            TextView empty = new TextView(getContext());
            empty.setText("Please input a value for today.");
            display.addView(empty);
        }

        //Populate Pie Chart
        calChart = rootView.findViewById(R.id.calChart);
        List<PieEntry> entries = new ArrayList<PieEntry>();
        entries.add(new PieEntry(exerciseCal, "Exercised"));
        entries.add(new PieEntry(foodCal, "Eaten"));
        int calRemain = 2000;
        try {
            calRemain = FFTrackerHelper.calculateCalRemain();
        }
        catch(NullPointerException e){
            //First Run Activity needs to run first.
            System.out.println("Currently running First_Run_Activity.");
        }
        if(foodCal>=calRemain)
            calRemain = 0;
        else
            calRemain -= foodCal;
        entries.add(new PieEntry(calRemain, "Remaining"));

        PieDataSet set = new PieDataSet(entries, "Calorie Breakdown");
        set.setColors(new int[]{Color.BLUE, Color.RED, Color.GREEN});
        PieData data = new PieData(set);
        calChart.setData(data);
        calChart.setEntryLabelColor(Color.BLACK);
        calChart.getLegend().setEnabled(false);
        calChart.setDescription(null);
        calChart.setCenterText("Calorie Breakdown");
        calChart.invalidate(); // refresh

        //Add Menu Display
        add = rootView.findViewById(R.id.add_button);
        add_weight = rootView.findViewById(R.id.add_weight);
        exercise_name = rootView.findViewById(R.id.exercise_name);
        add_exercised = rootView.findViewById(R.id.add_exercised);
        food_name = rootView.findViewById(R.id.food_name);
        add_eaten = rootView.findViewById(R.id.add_eaten);
        add_menu = rootView.findViewById(R.id.add_menu);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("PLUS BUTTON PRESSED");
                if(add_menu.getVisibility()==View.VISIBLE) {
                    add_menu.setVisibility(View.INVISIBLE);
                }
                else {
                    add_menu.setVisibility(View.VISIBLE);
                }
            }
        });

        return rootView;
    }


}