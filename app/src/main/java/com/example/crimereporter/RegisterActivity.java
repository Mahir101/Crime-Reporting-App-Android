package com.example.crimereporter;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class RegisterActivity extends AppCompatActivity {

    private EditText newpasswordText;
    private EditText newemailText;

    private EditText regmobile;
    private EditText regname;
    private EditText regcity;
    private EditText regcountry;
    private EditText postalAddress;

    private Button regbtn;

    private FirebaseAuth mAuth;

    private DatabaseReference databaseRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        newemailText = (EditText) findViewById(R.id.regemail);
        newpasswordText = (EditText) findViewById(R.id.regpass);

        regname = (EditText) findViewById(R.id.regName);
        regmobile = (EditText) findViewById(R.id.regMobile);
        regcity = (EditText) findViewById(R.id.regCity);
        regcountry = (EditText) findViewById(R.id.regCountry);
        postalAddress = (EditText) findViewById(R.id.regPostal);


        regbtn = (Button) findViewById(R.id.regbutton);

        databaseRef = FirebaseDatabase.getInstance().getReference();


        regbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String TAG = "marakhacchi";
                String email = newemailText.getText().toString().trim();
                String password = newpasswordText.getText().toString().trim();
                final String name = regname.getText().toString().trim();
                final String city = regcity.getText().toString().trim();
                final String country = regcountry.getText().toString().trim();
                final String postAddress = postalAddress.getText().toString().trim();
                final String mobileNo = regmobile.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password is too short. Enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!TextUtils.isEmpty(email ) && !TextUtils.isEmpty(password)) {
                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseUser user1 = mAuth.getCurrentUser();
                                        UserInformation user2 = new UserInformation(name, city, country, postAddress, mobileNo);
                                        databaseRef.child("Users").child(user1.getUid()).setValue(user2);

                                        sendToMain();
                                    } else {

                                            Log.e(TAG, "onComplete: Failed=" + task.getException().getMessage());

                                        toastmessage("Registration failed. Please Try again.");
                                    }

                                }
                            });
                }
            }

        });
    }

    //sending to main
    public void sendToMain(){
        Intent mainIntent = new Intent(RegisterActivity.this, MainActivity.class);
        startActivity(mainIntent);
        finish();
    }

    //toastin message
    public  void toastmessage(String message){
        Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();
    }

}
