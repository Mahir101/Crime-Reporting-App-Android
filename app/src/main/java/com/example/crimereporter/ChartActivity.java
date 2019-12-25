package com.example.crimereporter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ChartActivity extends AppCompatActivity {
    Button btnBarChart, btnPieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        //BarChart barChart = (BarChart) findViewById(R.id.barchart);

        btnBarChart = findViewById(R.id.btnBarChart);
        btnPieChart = findViewById(R.id.btnPieChart);
        btnBarChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent I = new Intent(ChartActivity.this, BarChartActivity.class);
                startActivity(I);
            }
        });
        btnPieChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent I = new Intent(ChartActivity.this, PieChartActivity.class);
                startActivity(I);
            }
        });
    }
}