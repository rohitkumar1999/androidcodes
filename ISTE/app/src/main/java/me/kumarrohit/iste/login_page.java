package com.codingblocks.iosd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.ActionBar;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;



public class login_page extends AppCompatActivity  {
    private EditText maillogin, passlogin;
    private CheckBox checkBox;
    private Button login, signuplogin;
    private TextView forgetpass;
    private FirebaseAuth mauth;
    private ProgressBar progressBar;
    private  Fragment fragment1;
    RelativeLayout frame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();


        setContentView(R.layout.activity_login_page);
        maillogin = findViewById(R.id.loginemail);
        passlogin = findViewById(R.id.loginpass);
        checkBox = findViewById(R.id.logincheck);
        login = findViewById(R.id.loginbutton);
        signuplogin = findViewById(R.id.signupbuttonlogin);
        forgetpass = findViewById(R.id.loginforgot);
        progressBar = findViewById(R.id.progressBar_cyclic);
    frame=findViewById(R.id.mrel);

       login.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               progressBar.setVisibility(v.VISIBLE);
               getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                       WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

               Allowusertologin();
               getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

           }

       });
       signuplogin.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Log.d("in", "onClick:signup ");



             Intent intent=new Intent(login_page.this,signup.class);
             startActivity(intent);


           }
       });


           forgetpass.setOnClickListener(new View.OnClickListener() {

               @Override
               public void onClick(View v) {
                   progressBar.setVisibility(View.VISIBLE);
                   if(maillogin.getText().toString().equals(null))
                   {
                       Toast.makeText(login_page.this,"please enter your registered email id",Toast.LENGTH_SHORT).show();
                       progressBar.setVisibility(View.INVISIBLE);

                   }
                   else  if(!Patterns.EMAIL_ADDRESS.matcher(maillogin.getText().toString()).matches())
                   {
                       Toast.makeText(login_page.this,"please enter a valid email address",Toast.LENGTH_SHORT).show();
                       progressBar.setVisibility(View.INVISIBLE);



                   }
                 else{

                   mauth.sendPasswordResetEmail(maillogin.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                       @Override
                       public void onComplete(@NonNull Task<Void> task) {
                           if (task.isSuccessful()) {
                               progressBar.setVisibility(View.INVISIBLE);
                               Toast.makeText(getApplicationContext(), "email sent to your email", Toast.LENGTH_SHORT).show();
                           }
                       else {
                           Toast.makeText(getApplicationContext(),"not a registered email",Toast.LENGTH_SHORT).show();
                           progressBar.setVisibility(View.INVISIBLE);
                           }
                       }
                   });
               }
           }

                                         });

//        forgetpass.setOnClickListener(this);
        mauth = FirebaseAuth.getInstance();


    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mauth.getCurrentUser();
        if (user != null) {
            Sendusertomainactivity();

        }
    }

    private void Allowusertologin() {
        String mail = maillogin.getText().toString();
        String pass = passlogin.getText().toString();
        if (TextUtils.isEmpty(mail)) {
            Toast.makeText(this, "Please write your email", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(pass)) {
            Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show();
        } else {


            mauth.signInWithEmailAndPassword(mail, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {

                        checkifemailverified();


                    } else {
                        String message = task.getException().getMessage();
                        Toast.makeText(login_page.this, "Error Occured:" + message, Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.INVISIBLE);


                    }

                }
            });
        }




    }

    public void onBackPressed() {
        super.onBackPressed();
        progressBar.setVisibility(View.GONE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }


        private void Sendusertomainactivity()
        {
            Intent main = new Intent(this, MainActivity.class);
            main.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(main);


        }
        private  void checkifemailverified()
        {


            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            if (user.isEmailVerified())
            {

              Sendusertomainactivity();
                Toast.makeText(login_page.this, "you are logged in sucessfully", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
                // user is verified, so you can finish this activity or send user to activity which you want.
                finish();

            }
            else
            {
                Toast.makeText(login_page.this,"please verify your mail",Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);

                // email is not verified, so just prompt the message to the user and restart this activity.
                // NOTE: don't forget to log out the user.
                FirebaseAuth.getInstance().signOut();


            }



        }

    }


