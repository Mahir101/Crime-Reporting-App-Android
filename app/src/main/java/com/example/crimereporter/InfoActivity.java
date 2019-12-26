package com.example.crimereporter;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class InfoActivity extends AppCompatActivity {


    private String phoneNumber="", ss;
    TextView tx1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        tx1 = findViewById(R.id.txview);
        ss = tx1.getText().toString().trim();
        for(int i=0; i<ss.length(); i++){
            if(ss.charAt(i)>='0' && ss.charAt(i)<='9' || ss.charAt(i)=='+'){
                phoneNumber += ss.charAt(i);
            }
        }
        System.out.println(phoneNumber);
    }
    public void tview(View view){

        startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null)));
    }
}
