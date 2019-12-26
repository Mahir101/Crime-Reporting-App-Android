package com.example.crimereporter;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HelpActivity extends AppCompatActivity {

    DatabaseReference databaseRe;
    FirebaseAuth mAuth;
    private String ph1="",ph2="", link="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        link = "http://www.google.com/maps/place/24.917228,91.8297245";


    }
    public void phn(View view){
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user1 = mAuth.getCurrentUser();
        databaseRe = FirebaseDatabase.getInstance().getReference().child("Users").child(user1.getUid());

        databaseRe.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                UserInformation value = dataSnapshot.getValue(UserInformation.class);
                // Log.d(TAG, "Value is: " + value);
                //  dataSnapshot.getRef().child("name").setValue("sagol");

                /*String nam = value.name;
                String ci = value.city;
                String mo = value.mobileNo;
                String po = value.postAddress;
                String em = user1.getEmail();
                String co = value.country;*/

                ph1 = value.p1;
                ph2 = value.p2;
                System.out.println(ph1);
                System.out.println(ph2);




            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });

        String SMS_SENT_INTENT_FILTER = "com.example.crimereporter.sms_send";
         String SMS_DELIVERED_INTENT_FILTER = "com.example.crimereporter.sms_delivered";

       // String message = "hey, this is my message";

       // String phnNo = " "//preferable use complete international number

        PendingIntent sentPI = PendingIntent.getBroadcast(this, 0, new Intent(
                SMS_SENT_INTENT_FILTER), 0);
        PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0, new Intent(
                SMS_DELIVERED_INTENT_FILTER), 0);



        String number = ph1.trim();
        String sms = "PLEASE HELP I'M IN DANGER. MY LOCATION is " + link;
        SmsManager sms1 = SmsManager.getDefault();
        sms1.sendTextMessage(number, null, sms, sentPI, deliveredPI);



        //SmsManager.getDefault().sendTextMessage(number, null, sms, null,null);
        /*Uri uri = Uri.parse("smsto:" + smsNumber);
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        intent.putExtra("sms_body", smsText);
        startActivity(intent);*/

    }
}
