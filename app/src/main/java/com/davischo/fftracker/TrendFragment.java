package com.davischo.fftracker;

import android.database.Cursor;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.davischo.fftracker.MainActivity.storage;

/**
 * Created by davischo on 6/14/18.
 */

public class TrendFragment extends Fragment {
    Spinner timeSpan, category;
    LineChart display;
    int timeInd = 0, categoryInd = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                         Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_trend, container, false);
        timeSpan = rootView.findViewById(R.id.timeSpan);
        category = rootView.findViewById(R.id.category);
        display = rootView.findViewById(R.id.trendDisplay);

        String query = "";

        //TODO Fix the category for the graph
        final List<String> categories = new ArrayList<String>();
        categories.add("Weight");
        categories.add("Calories Eaten");
        categories.add("Calories Burned");
        ArrayAdapter arrayAdapter1 = new ArrayAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item, categories);
        category.setAdapter(arrayAdapter1);
        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                categoryInd = i;
                populateGraph(FFTrackerHelper.queries[timeInd][categoryInd]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        //TODO Fix the time span for the graph
        final List<String> times = new ArrayList<String>();
        times.add("Last 7");
        times.add("Last 30");
        times.add("All-time");
        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item, times);
        timeSpan.setAdapter(arrayAdapter);
        timeSpan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                timeInd = i;
                populateGraph(FFTrackerHelper.queries[timeInd][categoryInd]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        return rootView;
    }

    private void populateGraph(String query){

        ArrayList<Entry> entries = new ArrayList<Entry>();
        Cursor c = null;
        c = storage.rawQuery(query, null);
        int timeIndex = c.getColumnIndex("time");
        int valueIndex = c.getColumnIndex("value");
        c.moveToLast();
        //Start from smallest x values first, as required by MPAndroidChart
        while(c!=null && !c.isBeforeFirst()){
            entries.add(new Entry(FFTrackerHelper.getMilliseconds(c.getString(timeIndex)), c.getInt(valueIndex)));
            //System.out.println("MILLISECONDS IS " + c.getString(timeIndex) + " " + FFTrackerHelper.getMilliseconds(c.getString(timeIndex)));
            c.moveToPrevious();
        }
        if(entries.isEmpty()){
            display.clear();
            display.setBackgroundColor(Color.CYAN);
            display.setNoDataText("Oops, enter some values first!");
            return;
        }
        LineDataSet lineDataSet = new LineDataSet(entries, "Weight");
        lineDataSet.setColor(getResources().getColor(R.color.colorPrimary));
        lineDataSet.setCircleColor(getResources().getColor(R.color.colorAccent));
        LineData lineData = new LineData(lineDataSet);
        XAxis xAxis = display.getXAxis();
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                long time = (long)value;
                Date date = new Date(time);
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                return (cal.get(Calendar.MONTH)+1) + "-" + cal.get(Calendar.DAY_OF_MONTH) + "-" + cal.get(Calendar.YEAR);
            }
        });
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setSpaceMin(10000000f);
        xAxis.setSpaceMax(10000000f);
        display.getLegend().setEnabled(false);
        display.setData(lineData);
        display.setDescription(null);
        display.setBorderColor(Color.RED);
        display.invalidate();
    }
}

