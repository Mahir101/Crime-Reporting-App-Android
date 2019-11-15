package com.example.crimereporter;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class FirstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

    }

    public void sendtoMain(View view) {
        Intent intent = new Intent(FirstActivity.this, MainActivity.class);

        startActivity(intent);
        finish();
    }
}
