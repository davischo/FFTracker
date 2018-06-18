package com.davischo.fftracker;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by davischo on 6/14/18.
 */

public class TrendFragment extends Fragment {
    Spinner timeSpan, category;
    LineChart display;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                         Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_trend, container, false);
        timeSpan = rootView.findViewById(R.id.timeSpan);
        category = rootView.findViewById(R.id.category);
        display = rootView.findViewById(R.id.trendDisplay);

        List<String> times = new ArrayList<String>();
        times.add("Week");
        times.add("Month");
        times.add("Year");
        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item, times);
        timeSpan.setAdapter(arrayAdapter);
        timeSpan.setSelection(0);

        List<String> categories = new ArrayList<String>();
        categories.add("Weight");
        categories.add("Calories Burned");
        categories.add("Calories Consumed");
        ArrayAdapter arrayAdapter1 = new ArrayAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item, categories);
        category.setAdapter(arrayAdapter1);
        category.setSelection(0);

        ArrayList<Entry> entries = new ArrayList<Entry>();
        entries.add(new Entry(1529119353000f, 200f));
        entries.add(new Entry(1529205753000f, 195f));
        entries.add(new Entry(1529292153000f, 190f));
        entries.add(new Entry(1529378553000f, 180f));
        entries.add(new Entry(1529464953000f, 150f));
        LineDataSet lineDataSet = new LineDataSet(entries, "Weight");
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
        display.setBackgroundColor(Color.LTGRAY);
        display.setBorderColor(Color.CYAN);
        display.invalidate();

        return rootView;
    }
}

