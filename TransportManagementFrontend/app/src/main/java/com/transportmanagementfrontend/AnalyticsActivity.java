package com.transportmanagementfrontend;

import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.*;
import com.github.mikephil.charting.components.*;
import com.github.mikephil.charting.data.*;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.*;

public class AnalyticsActivity extends AppCompatActivity {

    PieChart pieChartUsers;
    BarChart barChartDrivers;
    LineChart lineChartRevenue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analytics);

        pieChartUsers = findViewById(R.id.pieChartUsers);
        barChartDrivers = findViewById(R.id.barChartDrivers);
        lineChartRevenue = findViewById(R.id.lineChartRevenue);

        setupPieChartUsers();
        setupBarChartDrivers();
        setupLineChartRevenue();
    }

    private void setupPieChartUsers() {
        List<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(50f, "Customers"));
        entries.add(new PieEntry(30f, "Drivers"));
        entries.add(new PieEntry(20f, "Owners"));

        PieDataSet dataSet = new PieDataSet(entries, "User Types");
        dataSet.setColors(Color.CYAN, Color.MAGENTA, Color.YELLOW);
        PieData data = new PieData(dataSet);
        pieChartUsers.setData(data);
        pieChartUsers.setCenterText("User Distribution");
        pieChartUsers.setUsePercentValues(true);
        pieChartUsers.invalidate();
    }

    private void setupBarChartDrivers() {
        List<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0f, 10f)); // Jan
        entries.add(new BarEntry(1f, 15f)); // Feb
        entries.add(new BarEntry(2f, 20f)); // Mar

        BarDataSet dataSet = new BarDataSet(entries, "New Drivers");
        dataSet.setColors(Color.parseColor("#FFA726"));
        BarData data = new BarData(dataSet);
        barChartDrivers.setData(data);

        // Fixing lambda issue with ValueFormatter
        barChartDrivers.getXAxis().setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                switch ((int) value) {
                    case 0: return "Jan";
                    case 1: return "Feb";
                    case 2: return "Mar";
                    default: return "";
                }
            }
        });

        barChartDrivers.getXAxis().setGranularity(1f);
        barChartDrivers.invalidate();
    }

    private void setupLineChartRevenue() {
        List<Entry> entries = new ArrayList<>();
        entries.add(new Entry(0f, 1000f));
        entries.add(new Entry(1f, 1500f));
        entries.add(new Entry(2f, 1200f));

        LineDataSet dataSet = new LineDataSet(entries, "Monthly Revenue");
        dataSet.setColor(Color.BLUE);
        dataSet.setCircleColor(Color.RED);
        LineData data = new LineData(dataSet);
        lineChartRevenue.setData(data);

        // Fixing lambda issue with ValueFormatter
        lineChartRevenue.getXAxis().setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                switch ((int) value) {
                    case 0: return "Jan";
                    case 1: return "Feb";
                    case 2: return "Mar";
                    default: return "";
                }
            }
        });

        lineChartRevenue.getXAxis().setGranularity(1f);
        lineChartRevenue.invalidate();
    }
}
