package com.example.crimereporter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UIActivity extends AppCompatActivity {


    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mRef;

    private Toolbar mainToolbar;
    //private FirebaseAuth fireAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui);

        FirebaseUser currentuser = FirebaseAuth.getInstance().getCurrentUser();
        
        if (currentuser == null) {
            sendtoLogin();
        }

      //  fireAuth = FirebaseAuth.getInstance();


    }

    public void s1(View view) {
        Intent intent = new Intent(UIActivity.this, Main2Activity.class);

        startActivity(intent);
        finish();
    }
    public void s2(View view) {
        Intent intent = new Intent(UIActivity.this, MainActivity.class);

        startActivity(intent);
        finish();
    }
    public void s3(View view) {
        Intent intent = new Intent(UIActivity.this, PostActivity.class);

        startActivity(intent);
        finish();
    }
    public void s4(View view) {
        Intent intent = new Intent(UIActivity.this, MissingActivity.class);

        startActivity(intent);
        finish();
    }
    public void s5(View view) {
        Intent intent = new Intent(UIActivity.this, WebActivity.class);

        startActivity(intent);
        finish();
    }
    public void s6(View view) {
        Intent intent = new Intent(UIActivity.this, ProfileActivity.class);

        startActivity(intent);
        finish();
    }



    private void sendtoLogin() {
        Intent intent = new Intent(UIActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }



}
