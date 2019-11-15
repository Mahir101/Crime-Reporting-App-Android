package com.example.crimereporter;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class PostActivity extends AppCompatActivity {


    private ImageButton imgbtn;
    private EditText titleText;
    private EditText descText;
    private Button submitBtn;
    private Uri imagePath = null;
    private ProgressDialog progressBar;

    private DatabaseReference databaseRef;
    private StorageReference storageRef;

    private FirebaseAuth mAuth;

    static final int GALLERY_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        imgbtn = (ImageButton) findViewById(R.id.imageSelect);
        titleText = (EditText) findViewById(R.id.titleSelect);
        descText = (EditText) findViewById(R.id.descSelect);
        submitBtn = (Button) findViewById(R.id.submitBtn);
        progressBar = new ProgressDialog(this);

        storageRef = FirebaseStorage.getInstance().getReference();
        databaseRef = FirebaseDatabase.getInstance().getReference().child("Crime");


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

    }

    //method for posting
    private void startPosting() {
        progressBar.setMessage("Posting");
        progressBar.show();
        final String postTitle = titleText.getText().toString().trim();
        final String postDesc = descText.getText().toString().trim();
        mAuth = FirebaseAuth.getInstance();
        if(!TextUtils.isEmpty(postTitle) && !TextUtils.isEmpty(postDesc) && imagePath != null){

            //path where posts are to be stored on the server
            final StorageReference filePath = storageRef.child("Crime_images").child(UUID.randomUUID().toString());
            //final StorageReference filePath = storageRef;
            FirebaseUser user1 = mAuth.getCurrentUser();
            final DatabaseReference newpost = databaseRef.child(user1.getUid()).push();
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
                                   newpost.child("image").setValue( downloadUri.toString());
                                   //do your stuff- uri.toString() will give you download URL\\
                               }
                           });
                           newpost.child("title").setValue(postTitle);
                           newpost.child("description").setValue(postDesc);


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