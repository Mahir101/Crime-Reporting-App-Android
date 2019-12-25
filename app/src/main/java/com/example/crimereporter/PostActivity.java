package com.example.crimereporter;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class PostActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    private ImageButton imgbtn;
    private EditText titleText;
    private String adminUUID = "7UQtnet3OChJ5d7eXViVi9Yuxm32";
    private EditText descText;
    private TextView latText;
    private TextView lngText;
    private Button submitBtn;
    private Button mapBtn;
    private Uri imagePath = null;
    private ProgressDialog progressBar;

    private DatabaseReference databaseRef,dba,mRef,mRefm;
    private StorageReference storageRef;

    private FirebaseAuth mAuth;
    private String typex="";

    static final int GALLERY_REQUEST = 1;


    String[] crimes = { "Murder", "Eve-Teasing", "Theft", "Assault", "Rape", "Kidnapping"};
    private Spinner spin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        imgbtn = (ImageButton) findViewById(R.id.imageSelect);
        titleText = (EditText) findViewById(R.id.titleSelect);
        descText = (EditText) findViewById(R.id.descSelect);
        latText = (TextView) findViewById(R.id.latSelect);
        lngText = (TextView) findViewById(R.id.lngSelect);
        submitBtn = (Button) findViewById(R.id.submitBtn);
        mapBtn = (Button) findViewById(R.id.mapbutt);
        progressBar = new ProgressDialog(this);

        storageRef = FirebaseStorage.getInstance().getReference();
        databaseRef = FirebaseDatabase.getInstance().getReference().child("Crime");
        mRef = FirebaseDatabase.getInstance().getReference().child("Stat").child("Year");
        mRefm = FirebaseDatabase.getInstance().getReference().child("Stat").child("Month");
        dba = FirebaseDatabase.getInstance().getReference().child("Admin_Crime");

        Spinner spin = (Spinner) findViewById(R.id.spinner);
        spin.setOnItemSelectedListener(this);

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,crimes);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);






        imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GALLERY_REQUEST);
            }
        });


        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPosting();
            }

        });
        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PostActivity.this, MapActivity.class));
            }

        });

        try{
            Bundle extras = getIntent().getExtras();
            if(extras != null){
                String lat = extras.getString("lat");
                String lng = extras.getString("lng");
                latText.setText("Latitude: "+lat);
                lngText.setText("Longitude: "+lng);
            }
        }
        catch(Exception e){
            toastMessage("vhul hocche!");
        }



    }
    //Performing action onItemSelected and onNothing selected
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
       typex = crimes[position];
    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    //method for posting
    private void startPosting() {
        progressBar.setMessage("Posting");
        progressBar.show();
        final String postTitle = titleText.getText().toString().trim();
        final String postDesc = descText.getText().toString().trim();
        final String lat = latText.getText().toString().trim();
        final String lng = lngText.getText().toString().trim();
        final String type = typex;
        mAuth = FirebaseAuth.getInstance();
        if(!TextUtils.isEmpty(type) && !TextUtils.isEmpty(lat) && !TextUtils.isEmpty(lng) && !TextUtils.isEmpty(postTitle) && !TextUtils.isEmpty(postDesc) && imagePath != null){

            //path where posts are to be stored on the server
            final StorageReference filePath = storageRef.child("Crime_images").child(UUID.randomUUID().toString());
            //final StorageReference filePath = storageRef;
            FirebaseUser user1 = mAuth.getCurrentUser();
            final DatabaseReference newpost = databaseRef.push();
            final DatabaseReference adminpost = dba.child(adminUUID).push();
            //uploading hobe

           filePath.putFile(imagePath)
                   .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                       @Override
                       public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                         //  String image = taskSnapshot.getStorage().getDownloadUrl().toString());

                           //Task<Uri> downloadUri = taskSnapshot.getStorage().getDownloadUrl();
                           filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                               @Override
                               public void onSuccess(Uri uri) {
                                   String downloadUri = uri.toString();
                                   newpost.child("image").setValue( downloadUri);
                                   adminpost.child("image").setValue( downloadUri);
                                   //do your stuff- uri.toString() will give you download URL\\
                               }
                           });
                           SimpleDateFormat sdf  = new SimpleDateFormat("yyyy");
                           String year = sdf.format(new Date());
                           System.out.println(year);
                           sdf = new SimpleDateFormat("mm");
                           String mon = sdf.format(new Date());
                           System.out.println(mon);


                           newpost.child("title").setValue(postTitle);
                           newpost.child("description").setValue(postDesc);
                           newpost.child("condition").setValue("Not Seen");
                           newpost.child("latitude").setValue(lat);
                           newpost.child("longitude").setValue(lng);
                           newpost.child("type").setValue(type);
                           newpost.child("uid").setValue(user1.getUid());
                           newpost.child("year").setValue(year);
                           //newpost.child("day").setValue(day);
                           newpost.child("month").setValue(mon);


                           mRef.addValueEventListener(new ValueEventListener() {
                               @Override
                               public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                   for(DataSnapshot ds: dataSnapshot.getChildren()){

                                       DataBar p1 = ds.getValue(DataBar.class);
                                       String xs = ds.getRef().getKey();
                                       Boolean f = false;
                                       if(xs == year)
                                       {
                                           f=true;
                                           float xp = p1.x;
                                           xp++;
                                           ds.getRef().child("x").setValue(xp);
                                       }
                                       if(f==false)
                                       {
                                           ds.getRef().child("x").setValue(1);
                                       }




                                   }

                               }

                               @Override
                               public void onCancelled(@NonNull DatabaseError databaseError) {

                               }
                           });



                           mRefm.addValueEventListener(new ValueEventListener() {
                               @Override
                               public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                   for(DataSnapshot ds: dataSnapshot.getChildren()){

                                       DataBar p1 = ds.getValue(DataBar.class);
                                       String xs = ds.getRef().getKey();
                                       Boolean f = false;

                                       String sz="";
                                       if(mon=="01") sz="1";
                                       if(mon=="02") sz="2";
                                       if(mon=="03") sz="3";
                                       if(mon=="04") sz="4";
                                       if(mon=="05") sz="5";
                                       if(mon=="06") sz="6";
                                       if(mon=="07") sz="7";
                                       if(mon=="08") sz="8";
                                       if(mon=="09") sz="9";
                                       if(mon=="10") sz="10";
                                       if(mon=="11") sz="11";
                                       if(mon=="12") sz="12";

                                       if(xs == sz)
                                       {
                                           f=true;
                                           float xp = p1.x;
                                           xp++;
                                           ds.getRef().child("x").setValue(xp);
                                       }
                                       if(f==false)
                                       {
                                           ds.getRef().child("x").setValue(1);
                                       }




                                   }

                               }

                               @Override
                               public void onCancelled(@NonNull DatabaseError databaseError) {

                               }
                           });




                           adminpost.child("title").setValue(postTitle);
                           adminpost.child("description").setValue(postDesc);
                           adminpost.child("condition").setValue("Seen");
                           adminpost.child("latitude").setValue(lat);
                           adminpost.child("longitude").setValue(lng);
                           adminpost.child("type").setValue(type);
                           newpost.child("year").setValue(year);
                           newpost.child("month").setValue(mon);
                          // newpost.child("day").setValue(day);


                           progressBar.dismiss();

                           startActivity(new Intent(PostActivity.this, MainActivity.class));
                           toastMessage("Posted");
                       }
                   })
                   .addOnFailureListener(new OnFailureListener() {
                       @Override
                       public void onFailure(@NonNull Exception e) {
                           progressBar.dismiss();
                           toastMessage("Failed to post");
                       }
                   })
                   .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                       @Override
                       public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                           double progress =(100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                           progressBar.setMessage("Posting "+(int)progress+"%");
                       }
                   });
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GALLERY_REQUEST && resultCode==RESULT_OK){

            imagePath = data.getData();
            imgbtn.setImageURI(imagePath);

        }
    }

    public void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

}