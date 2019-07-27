package com.example.rohit.aluconnect;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {
    private TextView username,userfullname,userstatus,usercounrty,usergender,userdob,user_college_name,user_college_uid,user_grad_year ;
    private CircleImageView user_profile_image ;
    private DatabaseReference profileUserRef ;
    private FirebaseAuth mAuth ;
    private String currentUserId ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();
        profileUserRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId);

        username = (TextView) findViewById(R.id.myprofile_username);
        userstatus = (TextView) findViewById(R.id.myprofile_status);
        userfullname = (TextView) findViewById(R.id.myprofile_fullname);
        usercounrty = (TextView) findViewById(R.id.myprofile_country);
        userdob = (TextView) findViewById(R.id.myprofile_dateob);
        usergender = (TextView) findViewById(R.id.myprofile_gender);
        user_college_name = (TextView) findViewById(R.id.myprofile_collegename);
        user_college_uid = (TextView) findViewById(R.id.myprofile_collegeuid);
        user_grad_year = (TextView) findViewById(R.id.myprofile_gradyear);
        user_profile_image=(CircleImageView)findViewById(R.id.myprofile_pic) ;

        profileUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
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


                    Picasso.get().load(myprofileimage).placeholder(R.drawable.profile).into(user_profile_image);

                    username.setText("USERNAME: @"+myusername);
                    user_college_name.setText("College Name:"+mycollegename);
                    userfullname.setText("Full Name:"+myfullname);
                    userstatus.setText(mystatus);
                    user_college_uid.setText("Your College ID:"+mycollegeid);
                    user_grad_year.setText("Graduation Year:"+mygradyear);
                    usercounrty.setText("Phone No:"+mycountry);
                    userdob.setText("Date Of Birth :"+mydob);
                    usergender.setText("Gender :"+mygender);


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
