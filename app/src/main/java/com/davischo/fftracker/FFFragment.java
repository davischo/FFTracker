package com.davischo.fftracker;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

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

        return rootView;
    }
}