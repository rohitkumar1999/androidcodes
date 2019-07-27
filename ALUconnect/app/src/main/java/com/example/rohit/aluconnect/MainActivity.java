package com.example.rohit.aluconnect;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends AppCompatActivity {




    private NavigationView navigationview ;
    private DrawerLayout drawerlayout ;

    private ActionBarDrawerToggle actionBarDrawerToggle ;

    private RecyclerView postList ;
    private Toolbar mToolbar ;
    private FirebaseAuth mAuth ;
    private DatabaseReference UsersRef ,PostsRef,LikesRef ;
    private CircleImageView NavProfileImage;
    private TextView NavProfileUserName ;
    private ImageButton AddNewPostButton ;
    String currentUserID;
    Boolean LikeChecker = false ;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser()!=null) {
            currentUserID = mAuth.getCurrentUser().getUid();

        }

        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users");
        PostsRef = FirebaseDatabase.getInstance().getReference().child("Posts");
        LikesRef = FirebaseDatabase.getInstance().getReference().child("Likes");

        mToolbar = (Toolbar)findViewById(R.id.main_page_toolbar) ;
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("home");

        drawerlayout = (DrawerLayout)findViewById(R.id. drawable_layout) ;
        actionBarDrawerToggle = new ActionBarDrawerToggle(MainActivity.this ,drawerlayout, R.string.Drawer_open,R.string.Drawer_close) ;
        drawerlayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        AddNewPostButton = (ImageButton) findViewById(R.id.add_new_post_button);


        navigationview = (NavigationView)findViewById(R.id.navigation_view) ;
        postList = (RecyclerView) findViewById(R.id.all_users_post_list);
        postList.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        postList.setLayoutManager(linearLayoutManager);


        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        postList.setLayoutManager(linearLayoutManager);
        View navView = navigationview.inflateHeaderView(R.layout.navigation_header) ;

        NavProfileImage = (CircleImageView) navView.findViewById(R.id.nav_profile_image);
        NavProfileUserName = (TextView) navView.findViewById(R.id.nav_user_full_name);


        UsersRef.child(currentUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.exists())
                {
                    if(dataSnapshot.hasChild("fullname"))
                    {
                        String fullname = dataSnapshot.child("fullname").getValue().toString();
                        NavProfileUserName.setText(fullname);
                    }
                    if(dataSnapshot.hasChild("profileimage"))
                    {
                        String image = dataSnapshot.child("profileimage").getValue().toString();
                        Picasso.get().load(image).placeholder(R.drawable.profile).into(NavProfileImage);

                    }
                    else
                    {
                        Toast.makeText(MainActivity.this, "Profile name do not exists...", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        navigationview.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                UserMenuSelector(item);
                return false;
            }
        });
        AddNewPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                SendUserToPostActivity();
            }
        });

        DisplayAllUsersPosts() ;
    }
    private void DisplayAllUsersPosts()
    {


        FirebaseRecyclerAdapter<Posts, PostsViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Posts, PostsViewHolder>
                        (
                                Posts.class,
                                R.layout.all_post_layout,
                                PostsViewHolder.class,
                                PostsRef
                        )
                {
                    @Override
                    protected void populateViewHolder(PostsViewHolder viewHolder, Posts model, int position)
                    {
                        final String PostKey = getRef(position).getKey() ;

                        viewHolder.setFullname(model.getFullname());
                        viewHolder.setTime(model.getTime());
                        viewHolder.setDate(model.getDate());
                        viewHolder.setDescription(model.getDescription());
                        viewHolder.setProfileimage(getApplicationContext(), model.getProfileimage());
                        viewHolder.setPostimage(getApplicationContext(), model.getPostimage());
                        viewHolder.setLikeButtonStatus(PostKey) ;
                        viewHolder.commentpostbutton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent commentsIntent = new Intent(MainActivity.this,CommentsActivity.class) ;
                                commentsIntent.putExtra("PostKey",PostKey) ;
                                startActivity(commentsIntent);

                            }
                        });
                        viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent clickPostIntent = new Intent(MainActivity.this,ClickPostActivity.class) ;
                                clickPostIntent.putExtra("PostKey",PostKey) ;
                                startActivity(clickPostIntent);
                            }
                        });
                        viewHolder.likepostbutton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v)
                            {

                                LikeChecker = true ;

                                LikesRef.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        if(LikeChecker.equals(true))
                                        {
                                            if(dataSnapshot.child(PostKey).hasChild(currentUserID))
                                            {
                                                LikesRef.child(PostKey).child(currentUserID).removeValue() ;
                                                LikeChecker = false ;
                                            }
                                            else
                                            {
                                                LikesRef.child(PostKey).child(currentUserID).setValue(true) ;
                                                LikeChecker = false ;


                                            }
                                        }


                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });



                            }
                        });

                    }

                };
        postList.setAdapter(firebaseRecyclerAdapter);
    }
    public static class PostsViewHolder extends RecyclerView.ViewHolder
    {
        View mView;
        ImageButton likepostbutton,commentpostbutton ;
        TextView displaynooflikes ;
        int countlikes ;
        String currentUserId ;
        DatabaseReference LikesRef  ;

        public PostsViewHolder(View itemView)
        {
            super(itemView);
            mView = itemView;
            likepostbutton=(ImageButton) mView.findViewById(R.id.like_button) ;
            commentpostbutton=(ImageButton) mView.findViewById(R.id.comment_button) ;
            displaynooflikes=(TextView) mView.findViewById(R.id.display_no_of_likes) ;
            LikesRef = FirebaseDatabase.getInstance().getReference().child("Likes") ;
            currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid() ;




        }

        public void setLikeButtonStatus(final String PostKey)
        {
            LikesRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.child(PostKey).hasChild(currentUserId))
                    {
                        countlikes = (int)dataSnapshot.child(PostKey).getChildrenCount() ;
                        likepostbutton.setImageResource(R.drawable.like);
                        displaynooflikes.setText(Integer.toString(countlikes)) ;
                    }
                    else
                    {
                        countlikes = (int)dataSnapshot.child(PostKey).getChildrenCount() ;
                        likepostbutton.setImageResource(R.drawable.dislike);
                        displaynooflikes.setText(Integer.toString(countlikes)) ;
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

        public void setFullname(String fullname)
        {
            TextView username = (TextView) mView.findViewById(R.id.post_username);
            username.setText(fullname);
        }

        public void setProfileimage(Context ctx, String profileimage)
        {
            CircleImageView image = (CircleImageView) mView.findViewById(R.id.post_profile_image);
            Picasso.get().load(profileimage).placeholder(R.drawable.profile).into(image) ;

        }

        public void setTime(String time)
        {
            TextView PostTime = (TextView) mView.findViewById(R.id.post_time);
            PostTime.setText("    " + time);
        }

        public void setDate(String date)
        {
            TextView PostDate = (TextView) mView.findViewById(R.id.post_date);
            PostDate.setText("    " + date);
        }

        public void setDescription(String description)
        {
            TextView PostDescription = (TextView) mView.findViewById(R.id.post_desc);
            PostDescription.setText(description);
        }

        public void setPostimage(Context ctx1, String postimage)
        {
            ImageView PostImage = (ImageView) mView.findViewById(R.id.post_image);
            Picasso.get().load(postimage).placeholder(R.drawable.profile).into(PostImage) ;
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null)
        {
            sendUserToLoginActivity() ;
        }
        else
        {
            CheckUserExistence();
        }

    }
    private void SendUserToPostActivity()
    {
        Intent addNewPostIntent = new Intent(MainActivity.this, PostActivity.class);
        startActivity(addNewPostIntent);
    }
    private void SendUserToSetupActivity()
    {
        Intent setupIntent = new Intent(MainActivity.this, SetupActivity.class);
        setupIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(setupIntent);
        finish();
    }
    private void CheckUserExistence()
    {
        final String current_user_id = mAuth.getCurrentUser().getUid();

        UsersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if(!dataSnapshot.hasChild(current_user_id))
                {
                    SendUserToSetupActivity();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void sendUserToLoginActivity() {
        Intent LoginIntent = new Intent(MainActivity.this,loginActivity.class) ;
        LoginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK) ;
        startActivity(LoginIntent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(actionBarDrawerToggle.onOptionsItemSelected(item))
        {
            return true ;
        }
        return super.onOptionsItemSelected(item);
    }

    public void UserMenuSelector(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.nav_post:
                SendUserToPostActivity();
                break;
            case R.id.nav_profile  :
                SendtoProfileActivity() ;
                break;

            case R.id.nav_finddriends  :
                Uri uri = Uri.parse("http://aluconnect.herokuapp.com/search"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;


            case R.id.nav_home  :
                sendusertomainactivity() ;
                break;
            case R.id.nav_settings  :
                SendUserToSettingActivity();
                break;
            case R.id.logout   :
                mAuth.signOut();
                sendUserToLoginActivity();
                break;
        }
    }

    private void SendtoProfileActivity() {
        Intent settingintent = new Intent(MainActivity.this,ProfileActivity.class) ;
        startActivity(settingintent);
    }

    private void SendUserToSettingActivity()
    {
        Intent settingintent = new Intent(MainActivity.this,SettingsActivity.class) ;
        startActivity(settingintent);
    }

    private void sendusertomainactivity()
    {
        Intent settingintentq = new Intent(MainActivity.this,MainActivity.class) ;
        startActivity(settingintentq);
    }


}
