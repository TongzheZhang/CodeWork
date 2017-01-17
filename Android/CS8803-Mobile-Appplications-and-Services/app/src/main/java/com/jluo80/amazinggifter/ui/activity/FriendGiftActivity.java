package com.jluo80.amazinggifter.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jluo80.amazinggifter.R;
import com.jluo80.amazinggifter.model.Gift;
import com.jluo80.amazinggifter.ui.basic.BaseActivity;

import java.util.HashMap;


public class FriendGiftActivity extends BaseActivity {

    private static final String TAG = FriendGiftActivity.class.getName();
    private DatabaseReference mDatabase;
    private HashMap<String, Gift> mGiftMap = new HashMap<>();
    private FriendGiftRecyclerAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_gift);

        /** Setup Toolbar and ActionBar. */
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /** Add back arrow to toolbar. */
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        /** Setup FloatingActionButton. */
        fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context, AddGiftsActivity.class);
                intent.putExtra("me_friend_tab", "friend");
                context.startActivity(intent);
            }
        });


        mAdapter = new FriendGiftRecyclerAdapter(this, mGiftMap);

        SharedPreferences mSharedPreferences = this.getSharedPreferences("friendFacebookId", Activity.MODE_PRIVATE);
        final String facebookId  = mSharedPreferences.getString("friendFacebookId", "");

        mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference HisWishList = mDatabase.child("user/" + facebookId + "/my_gift/" + "/wish_list");
        HisWishList.addChildEventListener(new ChildEventListener() {
            /** Add new gift */
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                /** com.jluo80.amazinggifter.FriendGiftFragment:-KMa77KnGU5hsF8dngVc*/
                final String uniqueKey = dataSnapshot.getKey();
                Log.e(TAG, "onChildAdded:" + uniqueKey);
                /** com.jluo80.amazinggifter.FriendGiftFragment:true */
                Log.e(TAG, "onChildAdded:" + dataSnapshot.getValue());

                DatabaseReference ref = mDatabase.child("gift").child(uniqueKey);
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot item) {

                        final Gift gift = item.getValue(Gift.class);
                        gift.setUnique_key(uniqueKey);

                        mDatabase = FirebaseDatabase.getInstance().getReference();
                        DatabaseReference progressRef = mDatabase.child("gift").child(gift.getUnique_key()).child("progress");
                        progressRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                double mProgress = dataSnapshot.getValue(double.class);
                                gift.setProgress(mProgress);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                        String end = gift.getDue_date();
                        int dayToGo = (int) dateDiff(end);

                        if(gift.getProgress() == gift.getPrice() || dayToGo >= 0) {
                            mGiftMap.put(uniqueKey, gift);
                            mAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG, "getUser:onCancelled", databaseError.toException());

                    }
                });
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.e(TAG, "onChildChanged:" + dataSnapshot.getKey());

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.e(TAG, "onChildRemoved:" + dataSnapshot.getKey());

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                Log.d(TAG, "onChildMoved:" + dataSnapshot.getKey());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "postComments:onCancelled", databaseError.toException());

            }
        });


        DatabaseReference fromFriendsList = mDatabase.child("user/" + facebookId + "/my_gift/" + "/from_friends");
        fromFriendsList.addChildEventListener(new ChildEventListener() {
            /** Add new gift */
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                /** com.jluo80.amazinggifter.FriendGiftFragment:-KMa77KnGU5hsF8dngVc*/
                final String uniqueKey = dataSnapshot.getKey();
                Log.e(TAG, "onChildAdded:" + uniqueKey);
                /** com.jluo80.amazinggifter.FriendGiftFragment:true */
                Log.e(TAG, "onChildAdded:" + dataSnapshot.getValue());

                DatabaseReference ref = mDatabase.child("gift/" + uniqueKey);
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot item) {

                        final Gift gift = item.getValue(Gift.class);
                        gift.setUnique_key(uniqueKey);

                        mDatabase = FirebaseDatabase.getInstance().getReference();
                        DatabaseReference progressRef = mDatabase.child("gift").child(gift.getUnique_key()).child("progress");
                        progressRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                double mProgress = dataSnapshot.getValue(double.class);
                                gift.setProgress(mProgress);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                        String end = gift.getDue_date();
                        int dayToGo = (int) dateDiff(end);

                        if(gift.getProgress() == gift.getPrice() || dayToGo >= 0) {
                            mGiftMap.put(uniqueKey, gift);
                            mAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                    }
                });
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.e(TAG, "onChildChanged:" + dataSnapshot.getKey());

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.e(TAG, "onChildRemoved:" + dataSnapshot.getKey());

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                Log.d(TAG, "onChildMoved:" + dataSnapshot.getKey());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "postComments:onCancelled", databaseError.toException());

            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(FriendGiftActivity.this));
        mAdapter = new FriendGiftRecyclerAdapter(this, mGiftMap);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onStart(){
        super.onStart();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume(){
        super.onResume();
        mAdapter.notifyDataSetChanged();
    }
}
