package com.davischo.fftracker;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;

import static com.davischo.fftracker.FFTrackerHelper.*;
import static com.davischo.fftracker.MainActivity.editor;
import static com.davischo.fftracker.MainActivity.refreshAll;
import static com.davischo.fftracker.MainActivity.storage;
import static com.davischo.fftracker.R.layout.fragment_ff;

/**
 * Created by davischo on 6/14/18.
 */

public class FFFragment extends Fragment{
    PieChart calChart;
    LinearLayout display;
    FloatingActionButton add;
    LinearLayout add_menu;
    LinearLayout foodList, exerciseList;
    static EditText add_weight, add_exercised, add_eaten;
    static EditText food_name, exercise_name;
    static Fragment ffFragment;

    public static void submitClicked(View v){
        if(add_weight.getText().toString().equals("") && add_exercised.getText().toString().equals("")
                        && add_eaten.getText().toString().equals("")) {
                Toast.makeText(v.getContext(), "Please Input at Least One Numeric Value", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(v.getContext(), "Updated!", Toast.LENGTH_SHORT).show();
            }
            if(!add_weight.getText().toString().equals("")){
                String rounded = String.format("%.1f", Float.valueOf(add_weight.getText().toString()));
                storage.execSQL("INSERT INTO weight(time, weight) VALUES ('"
                        + getCurrentDate() + "', " + Float.valueOf(rounded) + ")");
                editor.putFloat("weight", Float.valueOf(rounded)).commit();
                //STORE DATA HERE
                add_weight.setText("");
            }
            if(!add_exercised.getText().toString().equals("")){
                String exerciseName = "Exercise";
                if(!exercise_name.getText().toString().equals("")){
                    exerciseName = exercise_name.getText().toString();
                }
                System.out.println("EXERCISE ENTERED IS:" + Integer.valueOf(add_exercised.getText().toString()));
                storage.execSQL("INSERT INTO exercise(time, name, calories) VALUES ('" + getCurrentDate() + "', '"
                        + exerciseName + "', "
                        + Integer.valueOf(add_exercised.getText().toString()) + ")");
                //STORED DATA HERE
                exercise_name.setText("");
                add_exercised.setText("");
            }
            if(!add_eaten.getText().toString().equals("")) {
                String foodName = "Food";
                if(!food_name.getText().toString().equals("")){
                    foodName = food_name.getText().toString();
                }
                System.out.println("FOOD ENTERED IS:" + Integer.valueOf(add_eaten.getText().toString()));
                storage.execSQL("INSERT INTO food(time, name, calories) VALUES ('" + getCurrentDate() + "', '"
                        + foodName + "', "
                        + Integer.valueOf(add_eaten.getText().toString()) + ")");
                //STORED DATA HERE
                food_name.setText("");
                add_eaten.setText("");
            }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(fragment_ff, container, false);

        ffFragment = this;

        display = rootView.findViewById(R.id.display);

        foodList = rootView.findViewById(R.id.foodList);
        exerciseList = rootView.findViewById(R.id.exerciseList);

        LinearLayout newLin;
        TextView newText;
        Button newButton;
        String currDate = getCurrentDate();

        int foodCal = 0, exerciseCal = 0;

        //Populate food list
        if(storage.rawQuery("SELECT * FROM food WHERE time='" +
                currDate + "'", null).getCount()!=0) {
            Cursor c = storage.rawQuery("SELECT * FROM food WHERE time='" +
                    currDate + "'", null);
            int nameIndex = c.getColumnIndex("name");
            int calIndex = c.getColumnIndex("calories");
            int pkIndex = c.getColumnIndex("id");
            c.moveToFirst();
            while (!c.isAfterLast()) {
                foodCal += c.getInt(calIndex);
                newLin = new LinearLayout(getContext());
                newText = new TextView(getContext());
                newButton = new Button(getContext());
                newLin.setOrientation(LinearLayout.HORIZONTAL);
                newText.setText(c.getString(nameIndex) + " " + c.getString(calIndex) + " cal" );
                newText.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT, 1));
                newText.setGravity(Gravity.CENTER);
                newLin.addView(newText);
                newButton.setText("Delete");
                newButton.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT, 3));
                newButton.setOnClickListener(new FoodListener(c.getInt(pkIndex)));
                newLin.addView(newButton);
                foodList.addView(newLin);
                c.moveToNext();
            }
            c.close();
        }
        else{
            TextView empty = new TextView(getContext());
            empty.setText("Please input a value for today.");
            foodList.addView(empty);
        }

        //Populate exercise list
        if(storage.rawQuery("SELECT * FROM exercise WHERE time='" +
                currDate + "'", null).getCount()!=0) {
            Cursor c = storage.rawQuery("SELECT * FROM exercise WHERE time='" +
                    currDate +"'", null);
            int nameIndex = c.getColumnIndex("name");
            int calIndex = c.getColumnIndex("calories");
            int pkIndex = c.getColumnIndex("id");
            c.moveToFirst();
            while (!c.isAfterLast()) {
                exerciseCal += c.getInt(calIndex);
                newLin = new LinearLayout(getContext());
                newText = new TextView(getContext());
                newButton = new Button(getContext());
                newLin.setOrientation(LinearLayout.HORIZONTAL);
                newText.setText(c.getString(nameIndex) + " " + c.getString(calIndex) + " cal" );
                newText.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT, 1));
                newText.setGravity(Gravity.CENTER);
                newLin.addView(newText);
                newButton.setText("Delete");
                newButton.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT, 3));
                newButton.setOnClickListener(new ExerciseListener(c.getInt(pkIndex)));
                newLin.addView(newButton);
                exerciseList.addView(newLin);
                c.moveToNext();
            }
            c.close();
        }
        else{
            TextView empty = new TextView(getContext());
            empty.setText("Please input a value for today.");
            exerciseList.addView(empty);
        }

        //Populate Pie Chart
        calChart = rootView.findViewById(R.id.calChart);
        List<PieEntry> entries = new ArrayList<PieEntry>();
        entries.add(new PieEntry(exerciseCal, "Exercised"));
        entries.add(new PieEntry(foodCal, "Eaten"));
        //int calRemain = 2000;
        int calRemain = calculateCalRemain();
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
                if(add_menu.getVisibility()==View.VISIBLE) {
                    add_menu.animate().alpha(0).setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            add_menu.setEnabled(false);
                            add_menu.setVisibility(View.INVISIBLE);
                        }
                    });
                }
                else {
                    add_menu.setVisibility(View.VISIBLE);
                    add_menu.animate().alpha(1).setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            add_menu.setEnabled(true);
                        }
                    });
                }
            }
        });

        return rootView;
    }

    public static void refreshFFFragment(){
        FragmentTransaction ft = ffFragment.getFragmentManager().beginTransaction();
        ft.detach(ffFragment).attach(ffFragment).commit();
    }

    private class FoodListener implements View.OnClickListener {
        int rowid;

        public FoodListener(int rowid){
            this.rowid = rowid;
        }

        @Override
        public void onClick(View view) {
            //TODO SQL Query here
            System.out.println("Deleting Entry Number " + rowid);
            storage.execSQL("DELETE FROM food WHERE id=" + rowid);
            refreshAll();
        }
    }

    private class ExerciseListener implements View.OnClickListener {
        int rowid;

        public ExerciseListener(int rowid){
            this.rowid = rowid;
        }

        @Override
        public void onClick(View view) {
            //TODO SQL Query here
            System.out.println("Deleting Entry Number " + rowid);
            storage.execSQL("DELETE FROM exercise WHERE id=" + rowid);
            refreshAll();
        }
    }

}
