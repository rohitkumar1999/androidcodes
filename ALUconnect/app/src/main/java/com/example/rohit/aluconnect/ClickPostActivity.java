package com.example.rohit.aluconnect;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ClickPostActivity extends AppCompatActivity {
    private ImageView PostImage ;
    private TextView PostDescription ;
    private Button DeletePostButton,EditPostButton ;
    private DatabaseReference clickPostRef ;
    private FirebaseAuth mAuth ;
    private String PostKey,currentUserID,databaseUserID,Description,image;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_click_post);
        mAuth = FirebaseAuth.getInstance() ;
        currentUserID = mAuth.getCurrentUser().getUid() ;

        PostKey=getIntent().getExtras().get("PostKey").toString()  ;
        clickPostRef = FirebaseDatabase.getInstance().getReference().child("Posts").child(PostKey) ;

        PostImage = (ImageView) findViewById(R.id.click_post_image) ;
        PostDescription= (TextView) findViewById(R.id.click_post_description) ;
        EditPostButton = (Button)findViewById(R.id.edit_post_button);
        DeletePostButton = (Button)findViewById(R.id.delete_post_btton);

        DeletePostButton.setVisibility(View.INVISIBLE);
        EditPostButton.setVisibility(View.INVISIBLE);



        clickPostRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               if(dataSnapshot.exists())
               {
                   Description = dataSnapshot.child("description").getValue().toString() ;
                   image = dataSnapshot.child("postimage").getValue().toString() ;
                   databaseUserID = dataSnapshot.child("uid").getValue().toString() ;

                   if(currentUserID.equals(databaseUserID))
                   {
                       DeletePostButton.setVisibility(View.VISIBLE);
                       EditPostButton.setVisibility(View. VISIBLE);
                   }

                   EditPostButton.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View view) {
                           Editcurrentpost(Description) ;
                       }
                   });


                   PostDescription.setText(Description);
                   Picasso.get().load(image).into(PostImage) ;
               }


            }
            private void Editcurrentpost(String Description)
            {
                AlertDialog.Builder builder =  new AlertDialog.Builder(ClickPostActivity.this) ;
                builder.setTitle("Edit Post") ;

                final EditText inputField = new EditText(ClickPostActivity.this) ;
                inputField.setText(Description);

                builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    clickPostRef.child("description").setValue(inputField.getText().toString())  ;
                    Toast.makeText(ClickPostActivity.this,"Post has been edited succesfully",Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();


                    }
                });
                Dialog dialog = builder.create() ;
                dialog.show();
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.holo_green_dark);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        DeletePostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeleteCurrentPost() ;
            }
        });



    }
    private void DeleteCurrentPost()
    {
        clickPostRef.removeValue() ;
        SendUserToMainActivity() ;
        Toast.makeText(this,"Post has been deleted",Toast.LENGTH_SHORT).show();


    }
    private void SendUserToMainActivity()
    {
        Intent mainIntent = new Intent(ClickPostActivity.this, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }
}
