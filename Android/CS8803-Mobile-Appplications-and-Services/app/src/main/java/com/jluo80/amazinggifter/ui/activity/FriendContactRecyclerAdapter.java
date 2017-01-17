package com.jluo80.amazinggifter.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.facebook.internal.ImageRequest;
import com.jluo80.amazinggifter.R;
import com.jluo80.amazinggifter.model.Friend;
import com.jluo80.amazinggifter.utils.MySingleton;

import java.util.ArrayList;


public class FriendContactRecyclerAdapter extends RecyclerView.Adapter<FriendContactRecyclerAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<Friend> mFriends;
    private ImageLoader mImageLoader;

    FriendContactRecyclerAdapter(Context context, ArrayList<Friend> friends) {
        this.mContext = context;
        this.mFriends = friends;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.friend_contact_card_view, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        final Friend friend = mFriends.get(i);

        SharedPreferences mSharedPreferences= mContext.getSharedPreferences("friendFacebookId", Activity.MODE_PRIVATE);
        final SharedPreferences.Editor editor = mSharedPreferences.edit();

        /** Set up friendPicture and friendName */
        mImageLoader = MySingleton.getInstance(viewHolder.friendName.getContext()).getImageLoader();
        viewHolder.friendPicture.setImageUrl(ImageRequest.getProfilePictureUri(friend.getFacebookId(), 120, 120).toString(), mImageLoader);
        viewHolder.friendName.setText(friend.getName());

        /** Set up onClick events. */
        viewHolder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                editor.putString("friendFacebookId", friend.getFacebookId());
                editor.apply();

                Context context = view.getContext();
                Intent intent = new Intent(mContext, FriendGiftActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mFriends.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        CardView mCardView;
        TextView friendName;
        NetworkImageView friendPicture;

        ViewHolder(View view) {
            super(view);
            mCardView = (CardView)itemView.findViewById(R.id.cv);
            friendName = (TextView)itemView.findViewById(R.id.friend_name);
            friendPicture = (NetworkImageView)itemView.findViewById(R.id.friend_picture);
        }
    }

}
