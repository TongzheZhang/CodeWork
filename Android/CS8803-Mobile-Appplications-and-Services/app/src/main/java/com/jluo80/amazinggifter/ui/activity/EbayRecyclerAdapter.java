package com.jluo80.amazinggifter.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.jluo80.amazinggifter.R;
import com.jluo80.amazinggifter.model.Gift;
import com.jluo80.amazinggifter.utils.MySingleton;

import java.util.ArrayList;

public class EbayRecyclerAdapter extends RecyclerView.Adapter<EbayRecyclerAdapter.ViewHolder>{

    private Context mContext;
    private ArrayList<Gift> mGifts;
    private ImageLoader mImageLoader;

    EbayRecyclerAdapter(Context context, ArrayList<Gift> gifts) {
        this.mContext = context;
        this.mGifts = gifts;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ebay_search_card_view, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
        final Gift gift = mGifts.get(i);
        viewHolder.giftTitle.setText(gift.getName());
        viewHolder.currentPrice.setText("US $"+ gift.getPrice());

        /** Get the ImageLoader through your singleton class. */
        mImageLoader = MySingleton.getInstance(viewHolder.giftTitle.getContext()).getImageLoader();
        viewHolder.giftPicture.setImageUrl(gift.getPicture_url(), mImageLoader);


        viewHolder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(mContext, ItemDetailActivity.class);
                intent.putExtra("category", gift.getCategory());
                intent.putExtra("due_date", gift.getDue_date());
                intent.putExtra("initiator_id", gift.getInitiator_id());
                intent.putExtra("item_id", gift.getItem_id());
                intent.putExtra("item_url", gift.getItem_url());
                intent.putExtra("name", gift.getName());
                intent.putExtra("picture_url", gift.getPicture_url());
                intent.putExtra("post_time", gift.getPost_time());
                intent.putExtra("price", Double.toString(gift.getPrice()));
                intent.putExtra("progress", Double.toString(gift.getProgress()));
                intent.putExtra("reason", gift.getReason());
                intent.putExtra("receiver_id", gift.getReceiver_id());

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mGifts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        CardView mCardView;
        TextView giftTitle;
        TextView currentPrice;
        NetworkImageView giftPicture;

        ViewHolder(View view) {
            super(view);

            mCardView = (CardView)itemView.findViewById(R.id.cv);
            giftTitle = (TextView)itemView.findViewById(R.id.gift_title);
            currentPrice = (TextView)itemView.findViewById(R.id.current_price);
            giftPicture = (NetworkImageView)itemView.findViewById(R.id.gift_picture);
        }
    }

}
