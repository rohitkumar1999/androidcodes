package com.example.rohit.aluconnect;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsActivity extends AppCompatActivity {

    private Toolbar mToolbar ;
    private EditText usergender,userdob,user_college_name,user_college_uid,user_grad_year,userstatus ;
    private TextView username,userfullname,usercounrty ;
    private Button saveUpdate  ;
    private CircleImageView userprofimage ;
    private DatabaseReference settingsuserref ;
    private StorageReference UserProfileImageRef;
    private FirebaseAuth mAuth ;
    private ProgressDialog loadingBar ;

    private String currentUserID ;
    final static int Gallery_Pick = 1;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        settingsuserref = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserID);
        UserProfileImageRef = FirebaseStorage.getInstance().getReference().child("Profile Images");


        mToolbar = (Toolbar) findViewById(R.id.settings_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Account Settings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        username = (TextView) findViewById(R.id.settings_user_name);
        userstatus = (EditText) findViewById(R.id.settings_status);
        userfullname = (TextView) findViewById(R.id.settings_full_name);
        usercounrty = (TextView) findViewById(R.id.settings_country);
        userdob = (EditText) findViewById(R.id.settings_dob);
        usergender = (EditText) findViewById(R.id.settings_gender);
        user_college_name = (EditText) findViewById(R.id.settings_college_name);
        user_college_uid = (EditText) findViewById(R.id.settings_college_uid);
        user_grad_year = (EditText) findViewById(R.id.settings_passing_out_year);
        loadingBar = new ProgressDialog(this);


        userprofimage = (CircleImageView) findViewById(R.id.settings_profile_image);


        saveUpdate = (Button) findViewById(R.id.update_account_settings_buttons);


        settingsuserref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String myprofileimage = dataSnapshot.child("profileimage").getValue().toString();
                    String myusername = dataSnapshot.child("username").getValue().toString();
                    String myfullname = dataSnapshot.child("fullname").getValue().toString();

                    String mygender = dataSnapshot.child("gender").getValue().toString();

                    String mydob = dataSnapshot.child("dob").getValue().toString();

                    String mycountry = dataSnapshot.child("country").getValue().toString();
                    String mycollegeid = dataSnapshot.child("collegeid").getValue().toString();
                    String mycollegename = dataSnapshot.child("collegename").getValue().toString();
                    String mystatus = dataSnapshot.child("status").getValue().toString();
                    String mygradyear = dataSnapshot.child("graduationyear").getValue().toString();

                    Picasso.get().load(myprofileimage).placeholder(R.drawable.profile).into(userprofimage);

                    username.setText(myusername);
                    user_college_name.setText(mycollegename);
                    userfullname.setText(myfullname);
                    userstatus.setText(mystatus);
                    user_college_uid.setText(mycollegeid);
                    user_grad_year.setText(mygradyear);
                    usercounrty.setText(mycountry);
                    userdob.setText(mydob);
                    usergender.setText(mygender);




                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        saveUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValidateAccountinfo();
                ;
            }
        });
        userprofimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, Gallery_Pick);
            }
        });
    }
        protected void onActivityResult(int requestCode, int resultCode, Intent data)
        {
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == Gallery_Pick && resultCode == RESULT_OK && data != null) {
                Uri ImageUri = data.getData();

                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1, 1)
                        .start(this);
            }

            if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);

                if (resultCode == RESULT_OK) {
                    loadingBar.setTitle("Profile Image");
                    loadingBar.setMessage("Please wait, while we updating your profile image...");
                    loadingBar.setCanceledOnTouchOutside(true);

                    loadingBar.show();

                    Uri resultUri = result.getUri();

                    StorageReference filePath = UserProfileImageRef.child(currentUserID + ".jpg");

                    filePath.putFile(resultUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull final Task<UploadTask.TaskSnapshot> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(SettingsActivity.this, "Profile Image stored successfully to Firebase storage...", Toast.LENGTH_SHORT).show();

                                final String downloadUrl = task.getResult().getDownloadUrl().toString();

                                settingsuserref.child("profileimage").setValue(downloadUrl)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Intent selfIntent = new Intent(SettingsActivity.this, SettingsActivity.class);
                                                    startActivity(selfIntent);

                                                    Toast.makeText(SettingsActivity.this, "Profile Image stored to Firebase Database Successfully...", Toast.LENGTH_SHORT).show();
                                                    loadingBar.dismiss();
                                                } else {
                                                    String message = task.getException().getMessage();
                                                    Toast.makeText(SettingsActivity.this, "Error Occured: " + message, Toast.LENGTH_SHORT).show();
                                                    loadingBar.dismiss();
                                                }
                                            }
                                        });
                            }
                        }
                    });
                }
            }

        }





     private void ValidateAccountinfo()
     {
         String user__username = username.getText().toString() ;
         String user__fullname = userfullname.getText().toString() ;
         String user__dob = userdob.getText().toString() ;
         String user__status = userstatus.getText().toString() ;
         String user__country = usercounrty.getText().toString() ;
         String user__collegeid = user_college_uid.getText().toString() ;
         String user__collegename = user_college_name.getText().toString() ;
         String user__grad_year = user_grad_year.getText().toString() ;
         String user__gender = usergender.getText().toString() ;

         if(TextUtils.isEmpty(user__username))
         {
             Toast.makeText(this,"please write your username",Toast.LENGTH_SHORT).show();
         }
         else if(TextUtils.isEmpty(user__dob))
         {
             Toast.makeText(this,"please write your Date Of Birth",Toast.LENGTH_SHORT).show();
         }
         else if(TextUtils.isEmpty(user__status))
         {
             Toast.makeText(this,"please write your status",Toast.LENGTH_SHORT).show();
         }
         else if(TextUtils.isEmpty(user__country))
         {
             Toast.makeText(this,"please write your country",Toast.LENGTH_SHORT).show();
         }
         else if(TextUtils.isEmpty(user__collegeid))
         {
             Toast.makeText(this,"please write your CollegeId",Toast.LENGTH_SHORT).show();
         }
         else if(TextUtils.isEmpty(user__collegename))
         {
             Toast.makeText(this,"please write your College Name",Toast.LENGTH_SHORT).show();
         }
         else if(TextUtils.isEmpty(user__grad_year))
         {
             Toast.makeText(this,"please write your Graduation Year",Toast.LENGTH_SHORT).show();
         }
         else if(TextUtils.isEmpty(user__gender))
         {
             Toast.makeText(this,"please write your Gender",Toast.LENGTH_SHORT).show();
         }
         else if(TextUtils.isEmpty(user__fullname))
         {
             Toast.makeText(this,"please write your fullname",Toast.LENGTH_SHORT).show();
         }
         else
         {

             loadingBar.setTitle("Profile Image");
             loadingBar.setMessage("Please wait, while we updating your profile image...");
             loadingBar.setCanceledOnTouchOutside(true);

             loadingBar.show();
             UpdateAccountInfo(user__username,user__fullname,user__dob,user__status,user__country,user__collegeid,user__collegename,user__grad_year,user__gender) ;

         }


     }

    private void UpdateAccountInfo(String user__username, String user__fullname, String user__dob, String user__status, String user__country, String user__collegeid, String user__collegename, String user__grad_year, String user__gender) {

        HashMap userMap = new HashMap() ;
        userMap.put("username",user__username) ;
        userMap.put("fullname",user__fullname) ;
        userMap.put("dob",user__dob) ;
        userMap.put("status",user__status) ;
        userMap.put("gender",user__gender) ;
        userMap.put("collegeid",user__collegeid) ;
        userMap.put("collegename",user__collegename) ;
        userMap.put("country",user__country) ;
        userMap.put("graduationyear",user__grad_year);

        settingsuserref.updateChildren(userMap).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if(task.isSuccessful())
                {
                    SendUserToMainActivity();
                    Toast.makeText(SettingsActivity.this,"Accounts settings updated successfully",Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }
                else
                {
                    Toast.makeText(SettingsActivity.this,"Error occured while updating account  information",Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();

                }
            }
        });



    }
    private void SendUserToMainActivity()
    {
        Intent mainIntent = new Intent(SettingsActivity.this, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }

}
