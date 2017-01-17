package com.jluo80.amazinggifter.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jluo80.amazinggifter.R;
import com.jluo80.amazinggifter.model.Contributor;
import com.jluo80.amazinggifter.ui.basic.BaseActivity;

import java.util.ArrayList;


public class ContributorActivity extends BaseActivity {

    private static final String TAG = ContributorActivity.class.getName();
    private DatabaseReference mDatabase;
    private ArrayList<Contributor> mContributorArray = new ArrayList<>();
    private ContributorRecyclerAdapter mAdapter;
    private RecyclerView mRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contributor);

        /** Setup Toolbar and ActionBar. */
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /** Add back arrow to toolbar. */
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        Intent intent = ContributorActivity.this.getIntent();
        String giftKey = intent.getStringExtra("gift_key");

        mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference contributorList = mDatabase.child("contribution").child(giftKey);
        contributorList.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                final String contributorKey = dataSnapshot.getKey();
                Log.e(TAG, "onChildAdded:" + contributorKey);
                Log.e(TAG, "onChildAdded:" + dataSnapshot.getValue(Contributor.class));
                Contributor mContributor = dataSnapshot.getValue(Contributor.class);
                mContributorArray.add(mContributor);
                mAdapter.notifyDataSetChanged();
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

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(ContributorActivity.this));
        mAdapter = new ContributorRecyclerAdapter(this, mContributorArray);
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
