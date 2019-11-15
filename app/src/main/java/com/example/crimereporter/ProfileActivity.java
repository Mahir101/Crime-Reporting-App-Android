package com.example.crimereporter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;


import com.google.firebase.auth.FirebaseUser;

import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    private EditText name;
    private EditText email;
    private EditText mobile;
    private EditText city;
    private EditText country;
    private EditText age;
    private Button btn;

    private DatabaseReference databaseRef,databaseRe;
   // private StorageReference storageRef;
    private FirebaseAuth mAuth;


    private String TAG="marakhacchi";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        name = (EditText) findViewById(R.id.proName);
        email = (EditText) findViewById(R.id.proEmail);
        mobile = (EditText) findViewById(R.id.proMobile);
        city = (EditText) findViewById(R.id.proCity);
        country = (EditText) findViewById(R.id.proCountry);
        age= (EditText) findViewById(R.id.proAge);
        btn = (Button) findViewById(R.id.button);
        databaseRef = FirebaseDatabase.getInstance().getReference().child("Users");
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user1 = mAuth.getCurrentUser();
        databaseRe = FirebaseDatabase.getInstance().getReference().child("Users").child(user1.getUid());

        databaseRe.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);
                Map<String, String> map = dataSnapshot.getValue(Map.class);
                String nam = map.get("name");
                String ci = map.get("city");
                String mo = map.get("mobileNo");
                String po = map.get("postAddress");
                String em = user1.getDisplayName();
                String co = map.get("country");

                name.setText("Name: "+nam);
                email.setText("Email: "+em);
                mobile.setText("Mobile: "+mo);
                city.setText("City: "+ci);
                age.setText("Post Address: "+po);
                country.setText("Country: "+co);



            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final String TAG = "marakhacchi";
                String emailx = email.getText().toString().trim();
                final String namex = name.getText().toString().trim();
                final String cityx = city.getText().toString().trim();
                final String countryx = country.getText().toString().trim();
                final String agex = age.getText().toString().trim();
                final String mobilex = mobile.getText().toString().trim();


                if (TextUtils.isEmpty(namex)) {
                    Toast.makeText(getApplicationContext(), "Enter your Name!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(cityx)) {
                    Toast.makeText(getApplicationContext(), "Enter your city!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(countryx)) {
                    Toast.makeText(getApplicationContext(), "Enter your country!", Toast.LENGTH_SHORT).show();
                    return;
                }


                if (TextUtils.isEmpty(agex)) {
                    Toast.makeText(getApplicationContext(), "Enter your Post Address!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(mobilex)) {
                    Toast.makeText(getApplicationContext(), "Enter Mobile No.!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!TextUtils.isEmpty(namex)  && !TextUtils.isEmpty(agex) && !TextUtils.isEmpty(cityx)
                        && !TextUtils.isEmpty(mobilex)  && !TextUtils.isEmpty(countryx) ) {

                    FirebaseUser user1 = mAuth.getCurrentUser();
                    UserInformation user2 = new UserInformation(namex, cityx, countryx, agex, mobilex);
                    databaseRef.child("Users").child(user1.getUid()).setValue(user2);

                   sendToMain();
                }
            }
        });
    }
    public void sendToMain(){
        Intent mainIntent = new Intent(ProfileActivity.this, MainActivity.class);
        startActivity(mainIntent);
        finish();
    }

}
