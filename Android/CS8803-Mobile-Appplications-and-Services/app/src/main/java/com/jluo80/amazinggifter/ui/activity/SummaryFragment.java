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


public class SummaryFragment extends Fragment {

    private static final String TAG = SummaryFragment.class.getName();
    private ArrayList<Gift> mGiftArray = new ArrayList<>();
    private HashMap<String, Gift> mGiftMap = new HashMap<>();
    private RecyclerView mRecyclerView;
    private SummaryRecyclerAdapter mAdapter;
    private DatabaseReference mDatabase;

    public SummaryFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new SummaryRecyclerAdapter(getContext(), mGiftMap);

        SharedPreferences mSharedPreferences = this.getActivity().getSharedPreferences("facebookLogin", Activity.MODE_PRIVATE);
        String facebookId = mSharedPreferences.getString("facebookId", "");


        mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference myInvolved = mDatabase.child("user/" + facebookId + "/gift_for_friend");
        myInvolved.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                final String uniqueKey = dataSnapshot.getKey();
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
                        int dayToGo = (int) BaseActivity.dateDiff(end);

                        if(gift.getProgress() <= gift.getPrice() && dayToGo >= 0) {
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

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView =  inflater.inflate(R.layout.fragment_list_view, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new SummaryRecyclerAdapter(getContext(), mGiftMap);
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