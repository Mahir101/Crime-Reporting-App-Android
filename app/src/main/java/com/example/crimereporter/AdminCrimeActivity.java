package com.example.crimereporter;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class AdminCrimeActivity extends AppCompatActivity {

    LinearLayoutManager mLayoutManager;
    SharedPreferences mSharedPref; //saving sort settings
    RecyclerView mRecyclerView;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mRef;

    private Toolbar mainToolbar;
    private FirebaseAuth fireAuth;
    private String TAG="tagkori";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //toolbar er jonne
        mainToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(mainToolbar);
        getSupportActionBar().setTitle("Crime Reports");
        fireAuth = FirebaseAuth.getInstance();
        mSharedPref = getSharedPreferences("SortSettings", MODE_PRIVATE);
        String mSorting = mSharedPref.getString("Sort", "newest"); //default=newest
        fireAuth = FirebaseAuth.getInstance();


        if (mSorting.equals("newest")) {
            mLayoutManager = new LinearLayoutManager(this);
            //this will load the items from bottom means newest first
            mLayoutManager.setReverseLayout(true);
            mLayoutManager.setStackFromEnd(true);
        } else if (mSorting.equals("oldest")) {
            mLayoutManager = new LinearLayoutManager(this);
            //this will load the items from bottom means oldest first
            mLayoutManager.setReverseLayout(false);
            mLayoutManager.setStackFromEnd(false);
        }
        //RecyclerView
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);

        //set layout as LinearLayout
        mRecyclerView.setLayoutManager(mLayoutManager);

        //send Query to FirebaseDatabase
        mFirebaseDatabase = FirebaseDatabase.getInstance();

        FirebaseUser user1 = fireAuth.getCurrentUser();
        FirebaseUser currentuser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentuser == null) {
            sendtoLogin();
        }
        else
            mRef = mFirebaseDatabase.getReference().child("Admin_Crime").child(user1.getUid());

    }
    private void firebaseSearch(String searchText) {

        //convert string entered in SearchView to lowercase
        String query = searchText;
        //String hello = "Seen";

        Query firebaseSearchQuery = mRef.orderByChild("title").startAt(query).endAt(query + "\uf8ff");

        FirebaseRecyclerAdapter<Post, ViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Post, ViewHolder>(
                        Post.class,
                        R.layout.row,
                        ViewHolder.class,
                        firebaseSearchQuery
                ) {
                    @Override
                    protected void populateViewHolder(ViewHolder viewHolder, Post Post1, int position) {
                        final String title = Post1.getTitle();
                        FirebaseDatabase mFD = FirebaseDatabase.getInstance();
                        DatabaseReference mR = mFD.getReference("Crime");
                        Query query= mR.orderByChild("title").equalTo(title);

                        Log.d("ajgubi","wtf");
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                dataSnapshot.getRef().child("condition").setValue("Seen");
                                //for(DataSnapshot ds: dataSnapshot.getChildren()){
                                //   ds.getRef().child("condition").setValue("Seen");
                                //}
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });




                        viewHolder.setDetails(getApplicationContext(),"Seen", Post1.getTitle(), Post1.getDescription(), Post1.getImage(),Post1.getType());
                        //Post1.setCondition("Seen");

                    }




                    @Override
                    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                        ViewHolder viewHolder = super.onCreateViewHolder(parent, viewType);

                        viewHolder.setOnClickListener(new ViewHolder.ClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {

                                //Views
                                /*TextView mTitleTv = view.findViewById(R.id.reTitle);
                                TextView mDescTv = view.findViewById(R.id.reDesc);
                                ImageView mImageView = view.findViewById(R.id.reImage);
                                //get data from views
                                String mTitle = mTitleTv.getText().toString();
                                String mDesc = mDescTv.getText().toString();
                                Drawable mDrawable = mImageView.getDrawable();
                                Bitmap mBitmap = ((BitmapDrawable) mDrawable).getBitmap();

                                //pass this data to new activity
                                Intent intent = new Intent(view.getContext(), PostDetailActivity.class);
                                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                mBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                                byte[] bytes = stream.toByteArray();
                                intent.putExtra("image", bytes); //put bitmap image as array of bytes
                                intent.putExtra("title", mTitle); // put title
                                intent.putExtra("description", mDesc); //put description
                                startActivity(intent); //start activity*/


                            }

                            @Override
                            public void onItemLongClick(View view, int position) {
                                //TODO do your own implementaion on long item click
                            }
                        });

                        return viewHolder;
                    }


                };

        //set adapter to recyclerview
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
    }


    //load data into recycler view onStart
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentuser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentuser == null) {
            sendtoLogin();
        }
        FirebaseRecyclerAdapter<Post, ViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Post, ViewHolder>(
                        Post.class,
                        R.layout.row,
                        ViewHolder.class,
                        mRef
                ) {
                    @Override
                    protected void populateViewHolder(ViewHolder viewHolder, Post Post1, int position) {
                        viewHolder.setDetails(getApplicationContext(),"Seen", Post1.getTitle(), Post1.getDescription(), Post1.getImage(), Post1.getType());
                        Post1.setCondition("Seen");
                    }

                    @Override
                    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                        ViewHolder viewHolder = super.onCreateViewHolder(parent, viewType);

                        viewHolder.setOnClickListener(new ViewHolder.ClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                //Views
                                /*TextView mTitleTv = view.findViewById(R.id.reTitle);
                                TextView mDescTv = view.findViewById(R.id.reDesc);
                                ImageView mImageView = view.findViewById(R.id.reImage);
                                //get data from views
                                String mTitle = mTitleTv.getText().toString();
                                String mDesc = mDescTv.getText().toString();
                                Drawable mDrawable = mImageView.getDrawable();
                                Bitmap mBitmap = ((BitmapDrawable) mDrawable).getBitmap();

                                //pass this data to new activity
                                Intent intentx = new Intent(view.getContext(), PostDetailActivity.class);
                                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                mBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                                byte[] bytes = stream.toByteArray();
                                intentx.putExtra("image", bytes); //put bitmap image as array of bytes
                                Log.d(TAG,bytes.toString());
                                intentx.putExtra("title", mTitle); // put title
                                Log.d(TAG,mTitle);
                                intentx.putExtra("description", mDesc); //put description
                                Log.d(TAG,mDesc);
                                startActivity(intentx);*/


                            }

                            @Override
                            public void onItemLongClick(View view, int position) {
                                //TODO do your own implementaion on long item click
                            }
                        });

                        return viewHolder;
                    }

                };

        //set adapter to recyclerview
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //inflate the menu; this adds items to the action bar if it present
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                firebaseSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Filter as you type
                firebaseSearch(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.actionAdd) {

            Intent postintent = new Intent(AdminCrimeActivity.this, PostActivity.class);
            startActivity(postintent);
        }

        switch (item.getItemId()) {

            case R.id.actionMiss:
                Intent missintent = new Intent(AdminCrimeActivity.this, AdminMissingActivity.class);
                startActivity(missintent);
                return true;
            case R.id.actionWeb:
                Intent web = new Intent(AdminCrimeActivity.this, WebActivity.class);
                startActivity(web);
                return true;
            case R.id.actionlogoutbtn:
                logout();
                return true;
            case R.id.action_sort:
                showSortDialog();
                return true;
            default:
                return false;   //eta ager ta
            // previous return; return super.onOptionsItemSelected(item);
        }
    }
    private void showSortDialog() {
        //options to display in dialog
        String[] sortOptions = {" Newest", " Oldest"};
        //create alert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Sort by") //set title
                .setIcon(R.drawable.ic_action_sort) //set icon
                .setItems(sortOptions, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // The 'which' argument contains the index position of the selected item
                        // 0 means "Newest" and 1 means "oldest"
                        if (which == 0) {
                            //sort by newest
                            //Edit our shared preferences
                            SharedPreferences.Editor editor = mSharedPref.edit();
                            editor.putString("Sort", "newest"); //where 'Sort' is key & 'newest' is value
                            editor.apply(); // apply/save the value in our shared preferences
                            recreate(); //restart activity to take effect
                        } else if (which == 1) {
                            {
                                //sort by oldest
                                //Edit our shared preferences
                                SharedPreferences.Editor editor = mSharedPref.edit();
                                editor.putString("Sort", "oldest"); //where 'Sort' is key & 'oldest' is value
                                editor.apply(); // apply/save the value in our shared preferences
                                recreate(); //restart activity to take effect
                            }
                        }
                    }
                });
        builder.show();
    }


    private void sendtoLogin() {
        Intent intent = new Intent(AdminCrimeActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }


    private void logout() {
        fireAuth.signOut();
        sendtoLogin();
    }
}