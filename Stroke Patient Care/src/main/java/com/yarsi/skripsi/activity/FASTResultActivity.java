package com.yarsi.skripsi.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.ScatterChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.ScatterData;
import com.github.mikephil.charting.data.ScatterDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import com.yarsi.skripsi.R;

import java.util.ArrayList;

public class FASTResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fast_result);

        ScatterChart scatterChart = (ScatterChart) findViewById(R.id.chart);

        ArrayList<Entry> entries = new ArrayList<>();

        Intent intent = getIntent();
        String fast1 = intent.getStringExtra("FAST1");
        String fast2 = intent.getStringExtra("FAST2");
        String fast3 = intent.getStringExtra("FAST3");
        String fast4 = intent.getStringExtra("FAST4");

        int total = 0;

        if (fast1.equals("Yes"))
            total += 25;
        if (fast2.equals("Yes"))
            total += 25;
        if (fast3.equals("Yes"))
            total += 25;
        if (fast4.equals("Yes"))
            total += 25;

        entries.add(new Entry(total, 0));

        ScatterDataSet dataset = new ScatterDataSet(entries, "# result F.A.S.T");

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("January");
        labels.add("February");
        labels.add("March");
        labels.add("April");
        labels.add("May");
        labels.add("June");
        labels.add("July");
        labels.add("August");
        labels.add("September");
        labels.add("October");
        labels.add("November");
        labels.add("December");

        ScatterData data = new ScatterData(labels, dataset);
        scatterChart.setData(data);
        scatterChart.setDescription("Description");
        dataset.setColors(ColorTemplate.COLORFUL_COLORS); //
        dataset.setScatterShapeSize(10);
        dataset.setScatterShape(ScatterChart.ScatterShape.CIRCLE);
        scatterChart.animateY(5000);
    }
}
