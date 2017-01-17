package com.jluo80.amazinggifter.ui.activity;


import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseReference;
import com.jluo80.amazinggifter.R;
import com.jluo80.amazinggifter.model.Friend;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;


public class FriendContactFragment extends Fragment {

    private static final String TAG = FriendContactFragment.class.getName();
    private ArrayList<Friend> mFriendArray = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private FriendContactRecyclerAdapter mAdapter;
    private DatabaseReference mDatabase;

    public FriendContactFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new FriendContactRecyclerAdapter(getContext(), mFriendArray);
        SharedPreferences mSharedPreferences = this.getActivity().getSharedPreferences("facebookLogin", Activity.MODE_PRIVATE);
        String facebookFriends = mSharedPreferences.getString("facebookFriends", null);

        try {
            JSONArray friendslist = new JSONArray(facebookFriends);
            for (int i = 0; i < friendslist.length(); i++) {
                String friendId = friendslist.getJSONObject(i).getString("id");
                String friendName = friendslist.getJSONObject(i).getString("name");
                Friend mFriend = new Friend(friendId, friendName);
                mFriendArray.add(mFriend);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


//        /** Set will filter duplicate friend data. */
//        Set<String> friendsIdSet = new HashSet<>();
//        friendsIdSet = mSharedPreferences.getStringSet("friendsIdSet", null);
//        List<String> idList = new ArrayList<>(friendsIdSet);

//        mDatabase = FirebaseDatabase.getInstance().getReference();
//        for(int i = 0; i < idList.size(); i++) {
//            final String friendId = idList.get(i);
//
//            DatabaseReference myFriendsList = mDatabase.child("user").child(friendId).child("name");
//            myFriendsList.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    String friendName = dataSnapshot.getValue(String.class);
//                    Friend mFriend = new Friend(friendId, friendName);
//                    mFriendArray.add(mFriend);
//                    mAdapter.notifyDataSetChanged();
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//                    Log.w(TAG, "getUser:onCancelled", databaseError.toException());
//                }
//            });
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView =  inflater.inflate(R.layout.fragment_list_view, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new FriendContactRecyclerAdapter(getContext(), mFriendArray);
        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }
}
