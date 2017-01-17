package com.jluo80.amazinggifter.ui.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jluo80.amazinggifter.R;
import com.jluo80.amazinggifter.model.Gift;
import com.jluo80.amazinggifter.ui.basic.BaseActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class MyGiftFragment extends Fragment {

    private static final String TAG = MyGiftFragment.class.getName();
    private HashMap<String, Gift> mGiftMap = new HashMap<>();
    private RecyclerView mRecyclerView;
    private MyGiftRecyclerAdapter mAdapter;
    private DatabaseReference mDatabase;

    public MyGiftFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new MyGiftRecyclerAdapter(getContext(), mGiftMap);
        SharedPreferences mSharedPreferences = this.getActivity().getSharedPreferences("facebookLogin", Activity.MODE_PRIVATE);
        String facebookId = mSharedPreferences.getString("facebookId", "");


        mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference myWishList = mDatabase.child("user/" + facebookId + "/my_gift/" + "/wish_list");
        myWishList.addChildEventListener(new ChildEventListener() {
            /** Add new gift */
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                /** com.jluo80.amazinggifter.ui.activity.MyGiftFragment:-KMa77KnGU5hsF8dngVc*/
                final String uniqueKey = dataSnapshot.getKey();
                Log.e(TAG, "onChildAdded:" + uniqueKey);
                /** com.jluo80.amazinggifter.ui.activity.MyGiftFragment:true */
                Log.e(TAG, "onChildAdded:" + dataSnapshot.getValue());

                DatabaseReference giftRef = mDatabase.child("gift").child(uniqueKey);
                giftRef.addValueEventListener(new ValueEventListener() {
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
                        int dayToGo = (int) BaseActivity.dateDiff(end);

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
                /** com.jluo80.amazinggifter.ui.activity.MyGiftFragment:-KMa77KnGU5hsF8dngVc*/
                final String uniqueKey = dataSnapshot.getKey();
                Log.e(TAG, "onChildAdded:" + uniqueKey);
                /** com.jluo80.amazinggifter.ui.activity.MyGiftFragment:true */
                Log.e(TAG, "onChildAdded:" + dataSnapshot.getValue());

                DatabaseReference giftRef = mDatabase.child("gift/" + uniqueKey);
                giftRef.addValueEventListener(new ValueEventListener() {
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
                        int dayToGo = (int) BaseActivity.dateDiff(end);

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e("Test Refresh Problem", "MyGiftFragment onCreateView");

        View rootView =  inflater.inflate(R.layout.fragment_list_view, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new MyGiftRecyclerAdapter(getContext(), mGiftMap);
        mRecyclerView.setAdapter(mAdapter);

        return rootView;
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

    public String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("MM/dd/yy");
        return mdformat.format(calendar.getTime());
    }
}
