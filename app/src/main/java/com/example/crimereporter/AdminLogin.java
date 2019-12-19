package com.example.crimereporter;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AdminLogin extends AppCompatActivity {

    private EditText emailText;
    private EditText passwordText;
    private Button loginBtn;
   // private Button mregbtn;
   // private FirebaseAuth fireAuth;
    private ProgressBar loginProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

     //   fireAuth = FirebaseAuth.getInstance();
        emailText = (EditText) findViewById(R.id.loginemail);
        passwordText = (EditText) findViewById(R.id.loginpassword);
        loginBtn = (Button) findViewById(R.id.loginbtn);
       // mregbtn = (Button) findViewById(R.id.loginregbtn);
        loginProgress = (ProgressBar) findViewById(R.id.loginprogress);


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_login = emailText.getText().toString();
                String pass_login = passwordText.getText().toString();
                if(!TextUtils.isEmpty(email_login) && !TextUtils.isEmpty(pass_login)){         //checking if elements are empty
                    loginProgress.setVisibility(View.VISIBLE);
                    String em = "admin@admin.com";
                    String pass = "admin12345";
                    if(email_login.equals(em) && pass_login.equals(pass)){
                            sendToMain();
                    }else {
                        toastmessage("Can't Login. TRY AGAIN WITH CORRECT INFORMATION");
                    }

                    loginProgress.setVisibility(View.INVISIBLE);
                }
                else{
                    toastmessage("Please Enter  Admin Email and Password");
                }

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        /*FirebaseUser currentUser = fireAuth.getCurrentUser();
        if(currentUser != null){
            sendToMain();
        }
*/
    }

    //sending to main
    public void sendToMain(){
        Intent mainIntent = new Intent(AdminLogin.this, MainActivity.class);
        startActivity(mainIntent);
        finish();
    }


    //toastin message
    public  void toastmessage(String message){
        Toast.makeText(AdminLogin.this, message, Toast.LENGTH_SHORT).show();
    }
}
