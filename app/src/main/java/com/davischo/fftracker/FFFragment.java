package com.davischo.fftracker;

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
    EditText add_weight;
    EditText add_exercised;
    EditText add_eaten;
    Button add_submit;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(fragment_ff, container, false);

        display = rootView.findViewById(R.id.display);

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

        //Populate Food List
        for(int i = 0; i < 5; i++) {
            newLin = new LinearLayout(getContext());
            newText = new TextView(getContext());
            newButton = new Button(getContext());
            newLin.setOrientation(LinearLayout.HORIZONTAL);
            newText.setText("Item " + i);
            newText.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
            newText.setGravity(Gravity.CENTER);
            newLin.addView(newText);
            newButton.setText("Delete");
            newButton.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 0.1f));
            newButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("DELETE BUTTON PRESSED");
                }
            });
            newLin.addView(newButton);
            display.addView(newLin);
        }
        //Populate Exercise List
        title = new TextView(getContext());
        title.setText("Exercise");
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.BLACK);
        title.setTextSize(16);
        title.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        display.addView(title);
        for(int i = 0; i < 5; i++) {
            newLin = new LinearLayout(getContext());
            newText = new TextView(getContext());
            newButton = new Button(getContext());
            newLin.setOrientation(LinearLayout.HORIZONTAL);
            newText.setText("Item " + i);
            newText.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
            newText.setGravity(Gravity.CENTER);
            newLin.addView(newText);
            newButton.setText("Delete");
            newButton.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 0.1f));
            newButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("DELETE BUTTON PRESSED");
                }
            });
            newLin.addView(newButton);
            display.addView(newLin);
        }

        //Populate Pie Chart
        calChart = rootView.findViewById(R.id.calChart);
        List<PieEntry> entries = new ArrayList<PieEntry>();
        entries.add(new PieEntry(1000, "Exercised"));
        entries.add(new PieEntry(1000, "Eaten"));
        entries.add(new PieEntry(2000, "Remaining"));

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
        add_exercised = rootView.findViewById(R.id.add_exercised);
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

        //Adding New Data
        add_submit = rootView.findViewById(R.id.add_submit);
        add_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(add_weight.getText().toString().equals("") && add_exercised.getText().toString().equals("")
                        && add_eaten.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Please Input at Least One Value", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getActivity(), "Updated!", Toast.LENGTH_SHORT).show();
                }
                if(!add_weight.getText().toString().equals("")){
                    String rounded = String.format("%.1f", Float.valueOf(add_weight.getText().toString()));
                    System.out.println("WEIGHT ENTERED IS:" + rounded);
                    //STORE DATA HERE
                    add_weight.setText("");
                }
                if(!add_exercised.getText().toString().equals("")){
                    System.out.println("EXERCISE ENTERED IS:" + Integer.valueOf(add_exercised.getText().toString()));
                    //STORE DATA HERE
                    add_exercised.setText("");
                }
                if(!add_eaten.getText().toString().equals("")) {
                    System.out.println("FOOD ENTERED IS:" + Integer.valueOf(add_eaten.getText().toString()));
                    //STORE DATA HERE
                    add_eaten.setText("");
                }
            }
        });

        return rootView;
    }


}