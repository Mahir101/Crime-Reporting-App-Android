package com.example.crimereporter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class FirstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
    }

    public void sendtoMain(View view) {
        Intent intent = new Intent(FirstActivity.this, MapShowActivity.class);
        startActivity(intent);
        finish();
    }

    public void sendtoMain2(View view) {
        Intent intent = new Intent(FirstActivity.this, AdminLogin.class);
        startActivity(intent);
        finish();
    }
}
