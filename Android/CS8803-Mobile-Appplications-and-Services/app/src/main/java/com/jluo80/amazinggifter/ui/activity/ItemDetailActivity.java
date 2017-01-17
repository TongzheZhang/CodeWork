package com.jluo80.amazinggifter.ui.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jluo80.amazinggifter.R;
import com.jluo80.amazinggifter.ui.basic.BaseActivity;
import com.jluo80.amazinggifter.utils.MySingleton;


public class ItemDetailActivity extends BaseActivity {

    NetworkImageView itemPicture;
    TextView itemName;
    TextView itemPrice;
    TextView itemVisit;
    TextView itemPick;
    private ImageLoader mImageLoader;
    DatabaseReference mDatabase;
    final Context context = this;
    private static final String TAG = ItemDetailActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        /** Setup Toolbar and ActionBar. */
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /** Add back arrow to toolbar. */
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        itemPicture = (NetworkImageView) findViewById(R.id.item_picture);
        itemName = (TextView) findViewById(R.id.item_name);
        itemPrice = (TextView) findViewById(R.id.item_price);
        itemVisit = (TextView) findViewById(R.id.item_visit);
        itemPick = (TextView) findViewById(R.id.item_pick);


        Intent intent = ItemDetailActivity.this.getIntent();
        final String category = intent.getStringExtra("category");
        final String dueDate = intent.getStringExtra("due_date");
        final String initiatorId = intent.getStringExtra("initiator_id");
        final String itemId = intent.getStringExtra("item_id");
        final String itemUrl = intent.getStringExtra("item_url");
        final String name = intent.getStringExtra("name");
        final String pictureUrl = intent.getStringExtra("picture_url");
        final String postTime = intent.getStringExtra("post_time");
        final String price = intent.getStringExtra("price");
        final String progress = intent.getStringExtra("progress");
        final String reason = intent.getStringExtra("reason");
        final String receiverId = intent.getStringExtra("receiver_id");

        mImageLoader = MySingleton.getInstance(itemName.getContext()).getImageLoader();
        itemPicture.setImageUrl(pictureUrl, mImageLoader);
        itemName.setText(name);
        itemPrice.setText("US $" + price);

        /** Set onClickListener to redirect the use to the product website. */
        itemVisit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri itemUri = Uri.parse(itemUrl);
                Context context = view.getContext();
                context.startActivity(new Intent(Intent.ACTION_VIEW, itemUri));
            }
        });

        /** Set onClickListener to confirm the gift for the user. */
        itemPick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle("Confirm Your In-App Purchase");
                alertDialogBuilder
                        .setMessage("Do you want to pick this product?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                /** Save Gift data to Firebase. */
                                mDatabase = FirebaseDatabase.getInstance().getReference();
                                String uniqueKey = mDatabase.child("gift").push().getKey();
                                mDatabase.child("gift").child(uniqueKey).child("category").setValue(category);
                                mDatabase.child("gift").child(uniqueKey).child("due_date").setValue(dueDate);
                                mDatabase.child("gift").child(uniqueKey).child("initiator_id").setValue(initiatorId);
                                mDatabase.child("gift").child(uniqueKey).child("item_id").setValue(itemId);
                                mDatabase.child("gift").child(uniqueKey).child("item_url").setValue(itemUrl);
                                mDatabase.child("gift").child(uniqueKey).child("name").setValue(name);
                                mDatabase.child("gift").child(uniqueKey).child("picture_url").setValue(pictureUrl);
                                mDatabase.child("gift").child(uniqueKey).child("post_time").setValue(postTime);
                                mDatabase.child("gift").child(uniqueKey).child("price").setValue(Double.parseDouble(price));
                                mDatabase.child("gift").child(uniqueKey).child("progress").setValue(Double.parseDouble(progress));
                                mDatabase.child("gift").child(uniqueKey).child("reason").setValue(reason);
                                mDatabase.child("gift").child(uniqueKey).child("receiver_id").setValue(receiverId);

                                if(receiverId.equals(initiatorId)) {
                                    mDatabase.child("user/" + initiatorId + "/my_gift/wish_list").child(uniqueKey).setValue(true);
                                } else {
                                    mDatabase.child("user/" + initiatorId + "/gift_for_friend").child(uniqueKey).setValue(true);
                                    mDatabase.child("user/" + receiverId + "/my_gift/from_friends").child(uniqueKey).setValue(true);
                                }
                                Intent intent = new Intent(ItemDetailActivity.this, MainScreenActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                /** Create alert dialog. */
                AlertDialog alertDialog = alertDialogBuilder.create();

                /** Show it. */
                alertDialog.show();
            }
        });
    }
}
