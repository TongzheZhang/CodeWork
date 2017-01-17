package com.jluo80.amazinggifter.ui.activity;

import android.content.Context;
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
import com.jluo80.amazinggifter.model.Contributor;
import com.jluo80.amazinggifter.utils.MySingleton;

import java.util.ArrayList;

public class ContributorRecyclerAdapter extends RecyclerView.Adapter<ContributorRecyclerAdapter.ViewHolder>{

    private Context mContext;
    private ArrayList<Contributor> mContributors;
    private ImageLoader mImageLoader;

    ContributorRecyclerAdapter(Context context, ArrayList<Contributor> contributors) {
        this.mContext = context;
        this.mContributors = contributors;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.contributor_card_view, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
        final Contributor contributor = mContributors.get(i);
        viewHolder.contributorName.setText(contributor.getContributor_name());
        viewHolder.contributorAmount.setText("US $"+ Double.toString(contributor.getAmount()));
        viewHolder.time.setText(contributor.getTime());

        /** Get the ImageLoader through your singleton class. */
        mImageLoader = MySingleton.getInstance(viewHolder.contributorName.getContext()).getImageLoader();
        viewHolder.contributorPicture.setImageUrl(ImageRequest.getProfilePictureUri(contributor.getContributor_id(), 120, 120).toString(), mImageLoader);
    }

    @Override
    public int getItemCount() {
        return mContributors.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        CardView mCardView;
        NetworkImageView contributorPicture;
        TextView contributorName;
        TextView contributorAmount;
        TextView time;

        ViewHolder(View view) {
            super(view);

            mCardView = (CardView) itemView.findViewById(R.id.cv);
            contributorPicture = (NetworkImageView) itemView.findViewById(R.id.contributor_picture);
            contributorName = (TextView) itemView.findViewById(R.id.contributor_name);
            contributorAmount = (TextView) itemView.findViewById(R.id.contributor_amount);
            time = (TextView) itemView.findViewById(R.id.time);
        }
    }

}
