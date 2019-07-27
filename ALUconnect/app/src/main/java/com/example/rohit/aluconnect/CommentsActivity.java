package com.example.rohit.aluconnect;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Comment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class CommentsActivity extends AppCompatActivity {

    private ImageButton postcommentbutton ;
    private EditText CommentInputText ;
    private RecyclerView  commentlist ;
    private DatabaseReference UsersRef ,PostsRef;
    private FirebaseAuth mAuth ;


    private String PostKey,CuurentUserId ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);


        PostKey = getIntent().getExtras().get("PostKey").toString() ;

        mAuth = FirebaseAuth.getInstance() ;
        CuurentUserId = mAuth.getCurrentUser().getUid() ;


        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users") ;
        PostsRef = FirebaseDatabase.getInstance().getReference().child("Posts").child(PostKey).child("Comments");


        commentlist = (RecyclerView)findViewById(R.id.comment_list) ;
        commentlist.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this) ;
        linearLayoutManager.setReverseLayout(true);linearLayoutManager.setStackFromEnd(true);
        commentlist.setLayoutManager(linearLayoutManager);

        CommentInputText = (EditText)findViewById(R.id.comment_input);
        postcommentbutton = (ImageButton)findViewById(R.id.post_comment_button) ;


        postcommentbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                UsersRef.child(CuurentUserId).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            String username = dataSnapshot.child("username").getValue().toString();
                            validatecomment(username);
                            CommentInputText.setText("");

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });


    }
    public void onStart()
    {
        super.onStart();
        FirebaseRecyclerAdapter<comments ,CommentsViewHolder >  firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<comments, CommentsViewHolder>(
                        comments.class,R.layout.all_comments_layout,CommentsViewHolder.class,PostsRef
                ) {
                    @Override
                    protected void populateViewHolder(CommentsViewHolder viewHolder, comments model, int position) {
                        viewHolder.setUsername(model.getUsername());
                        viewHolder.setComment(model.getComment());
                        viewHolder.setDate(model.getDate());
                        viewHolder.setTime(model.getTime());
                    }
                };
        commentlist.setAdapter(firebaseRecyclerAdapter);
    }
    public static class CommentsViewHolder extends RecyclerView.ViewHolder
    {
        View mView ;
        public CommentsViewHolder(View itemView) {
            super(itemView);
            mView=itemView ;
        }
        public void setUsername(String username)
        {
            TextView myusername = (TextView)mView.findViewById(R.id.comment_username) ;
            myusername.setText("@"+username+"  ");
        }
        public void setDate(String date)
        {
            TextView mydate = (TextView)mView.findViewById(R.id.comment_date) ;
            mydate.setText("Date:  "+date);
        }
        public void setTime(String time)
        {
            TextView mytime = (TextView)mView.findViewById(R.id.comment_time) ;
            mytime.setText("  Time:"+time);
        }
        public void setComment(String comment)
        {
            TextView mycomment = (TextView)mView.findViewById(R.id.comment_text) ;
            mycomment.setText(comment);
        }



    }
    private void validatecomment(String Username)
    {
        String commentText =  CommentInputText.getText().toString() ;

        if(TextUtils.isEmpty(commentText))
        {
            Toast.makeText(this , "please write text to comment..... ",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Calendar calFordDate = Calendar.getInstance();
            SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMMM-yyyy");

            final String saveCurrentDate = currentDate.format(calFordDate.getTime());


            Calendar calFordTime = Calendar.getInstance();
            SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm");
                final String  saveCurrentTime = currentTime.format(calFordDate.getTime());

                final String RandomKey  = CuurentUserId+saveCurrentDate+saveCurrentDate;

            HashMap commentMap = new HashMap( );
            commentMap.put("uid",CuurentUserId) ;
            commentMap.put("comment",commentText) ;
            commentMap.put("date",saveCurrentDate) ;
            commentMap.put("time",saveCurrentTime) ;
            commentMap.put("username",Username) ;

            PostsRef.child(RandomKey).updateChildren(commentMap).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(CommentsActivity.this , "You have Commented Succesfully ..... ",Toast.LENGTH_SHORT).show();

                    }
                    else
                    {
                        Toast.makeText(CommentsActivity.this , "Error Occured Try again..... ",Toast.LENGTH_SHORT).show();

                    }
                }
            }) ;





        }
    }
}
