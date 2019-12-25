package com.example.crimereporter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BarChartActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference mRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_chart);
        BarChart chart = findViewById(R.id.barchart);



        firebaseDatabase = FirebaseDatabase.getInstance();
        mRef = firebaseDatabase.getReference().child("Stat").child("Month");



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

                    DataBar dp = ds.getValue(DataBar.class);
                    //System.out.println(dp.getX() + "  "+ dp.getY() + "   "+ dp.getYear());

                    //Syste
                    res.add(new BarEntry(dp.getX(), cnt));
                    String ye= ds.getRef().getKey();
                    if(ye.equals("1"))
                        year.add("Jan");
                    if(ye.equals("2"))
                        year.add("Feb");
                    if(ye.equals("3"))
                        year.add("Mar");
                    if(ye.equals("4"))
                        year.add("Apr");
                    if(ye.equals("5"))
                        year.add("May");
                    if(ye.equals("6"))
                        year.add("Jun");
                    if(ye.equals("7"))
                        year.add("Jul");
                    if(ye.equals("8"))
                        year.add("Aug");
                    if(ye.equals("9"))
                        year.add("Sep");
                    if(ye.equals("10"))
                        year.add("Oct");
                    if(ye.equals("11"))
                        year.add("Nov");
                    if(ye.equals("12"))
                        year.add("Dec");

                    //System.out.println(ye);
                    cnt++;
                }
                BarDataSet bardataset = new BarDataSet(res, "Crimes occured by Month");
                chart.animateY(5000);
                BarData data = new BarData(year, bardataset);
                bardataset.setColors(ColorTemplate.COLORFUL_COLORS);
                chart.setData(data);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
