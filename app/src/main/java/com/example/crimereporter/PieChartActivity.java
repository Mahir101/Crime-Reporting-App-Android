package com.example.crimereporter;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PieChartActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference mRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_chart);
        PieChart pieChart = findViewById(R.id.piechart);

        firebaseDatabase = FirebaseDatabase.getInstance();
        mRef = firebaseDatabase.getReference().child("Stat").child("Year");



        //res.add(new Entry(123f, 0));
       // res.add(new Entry(111f,1));

        //year.add("2018");
        //year.add("2918");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int cnt=0;
                ArrayList res = new ArrayList();
                ArrayList year = new ArrayList();


                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    //res.add(new Entry(,cnt)) ;

                    DataPoint dp = ds.getValue(DataPoint.class);
                    //System.out.println(dp.getX() + "  "+ dp.getY() + "   "+ dp.getYear());

                    //Syste
                    res.add(new Entry(dp.getX(), cnt));
                    String ye= ds.getRef().getKey();
                    year.add(ye);
                    //System.out.println(ye);
                    cnt++;
                }
                PieDataSet dataSet = new PieDataSet(res, "Crimes occured by Year");

                PieData data = new PieData(year, dataSet);
                pieChart.setData(data);
                dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                pieChart.animateXY(5000, 5000);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //retrieveData();
    }

}
