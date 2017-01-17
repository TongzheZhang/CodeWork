package com.jluo80.amazinggifter.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jluo80.amazinggifter.R;
import com.jluo80.amazinggifter.ui.basic.BaseActivity;
import com.jluo80.amazinggifter.utils.MySingleton;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import org.json.JSONException;
import java.math.BigDecimal;


public class ContributionActivity extends BaseActivity {

    NetworkImageView itemPicture;
    Button itemVisit, itemShare, contributionConfrim, contributorDetail;
    ShareDialog shareDialog;
    EditText contributeAmount;
    TextView itemName, itemPrice, currentRatio, itemReason, itemDueDate, currentProgress;
    ProgressBar progressBar;
    private String flag, contributorId, giftKey, progress, contributorName, postTime, name;
    private ImageLoader mImageLoader;

    DatabaseReference mDatabase;

    /** PayPal payment service configuration. */
    private static final String TAG = ContributionActivity.class.getName();
    private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_NO_NETWORK;
    private static final String CONFIG_CLIENT_ID = "ARYn41LX3OAcYQk7mbvr-id1aMmQH8S9pkyWOAj6duko_QNc0BDi26oCOF9mkmf92c3JggffakKtHagd\n";
    private static final int REQUEST_CODE_PAYMENT = 1;


    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(CONFIG_ENVIRONMENT)
            .clientId(CONFIG_CLIENT_ID)
            // The following are only used in PayPalFuturePaymentActivity.
            .merchantName("Example Merchant")
            .merchantPrivacyPolicyUri(Uri.parse("https://www.example.com/privacy"))
            .merchantUserAgreementUri(Uri.parse("https://www.example.com/legal"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_contribution);

        /** Set the toolbar of the add gifts activity. */
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /** Add back arrow to toolbar. */
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        /** Four buttons in this activity. */
        itemVisit = (Button) findViewById(R.id.item_visit);
        itemShare = (Button) findViewById(R.id.button_facebook_share);
        contributorDetail = (Button) findViewById(R.id.detail);
        contributionConfrim = (Button) findViewById(R.id.contributionConfirm);


        itemPicture = (NetworkImageView) findViewById(R.id.item_picture);
        itemName = (TextView) findViewById(R.id.item_name);
        itemPrice = (TextView) findViewById(R.id.item_price);
        itemReason = (TextView) findViewById(R.id.reason);
        itemDueDate = (TextView) findViewById(R.id.due_date);
        currentProgress =(TextView) findViewById(R.id.current_progress);
        contributeAmount = (EditText) findViewById(R.id.contribution_amount);

        currentRatio = (TextView) findViewById(R.id.current_ratio);
        progressBar = (ProgressBar) findViewById(R.id.progress);

        Intent intent = ContributionActivity.this.getIntent();
        final String pictureUrl = intent.getStringExtra("picture_url");
        name = intent.getStringExtra("name");
        final String price = intent.getStringExtra("price");
        String reason = intent.getStringExtra("reason");
        String dueDate = intent.getStringExtra("due_date");
        progress = intent.getStringExtra("progress");
        final String itemUrl = intent.getStringExtra("item_url");
//        postTime = intent.getStringExtra("post_time");
        giftKey = intent.getStringExtra("unique_key");
        flag = intent.getStringExtra("me_friend_tab");

        SharedPreferences mSharedPreferences = this.getSharedPreferences("facebookLogin", Activity.MODE_PRIVATE);
        contributorId = mSharedPreferences.getString("facebookId", "");
        contributorName = mSharedPreferences.getString("username", "");

        mImageLoader = MySingleton.getInstance(itemName.getContext()).getImageLoader();
        itemPicture.setImageUrl(pictureUrl, mImageLoader);
        itemName.setText(name);
        itemPrice.setText("US $" + price);
        itemReason.setText(reason);
        itemDueDate.setText(dateDiff(dueDate) + " days to go");
        currentProgress.setText("US $" + progress + "/" + price);

        int ratio = ((int) (Double.parseDouble(progress) / Double.parseDouble(price) * 100));
        Log.e(TAG, ratio + " ");
        currentRatio.setText(ratio + "%");
        progressBar.setProgress(ratio);

        /** Set onClickListener to redirect the use to the product website. */
        itemVisit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(itemUrl)));
            }
        });


        /** Set onClickListener to share the gift to your facebook friend. */
        shareDialog = new ShareDialog(this);
        itemShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ShareDialog.canShow(ShareLinkContent.class)) {

                    ShareLinkContent linkContent = new ShareLinkContent.Builder()
                            .setContentTitle("How to prepare an Amazing Gift for your friends or families?")
                            .setImageUrl(Uri.parse(pictureUrl))
                            .setContentDescription(
                                    "Let's Try Amazing Gifter !")
                            .setContentUrl(Uri.parse(itemUrl))
                            .build();
                    shareDialog.show(linkContent);
                }
            }
        });

        /** Set onClickListener to check the contributor list. */
        contributorDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ContributionActivity.this, ContributorActivity.class);
                intent.putExtra("gift_key", giftKey);
                startActivity(intent);
            }
        });


        /** Set onClickListener to confirm your contribution. */
        contributionConfrim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isEmpty(contributeAmount.getText().toString())) {
                    Toast.makeText(ContributionActivity.this, "No contribution is made.", Toast.LENGTH_SHORT).show();
                } else if(Double.parseDouble(contributeAmount.getText().toString()) > Double.parseDouble(price) - Double.parseDouble(progress)) {
                    Toast.makeText(ContributionActivity.this, "Your contribution is over the price.", Toast.LENGTH_SHORT).show();
                }else {
                    onBuyPressed(view);
                }
            }
        });

    }

    public void onBuyPressed(View pressed) {
        PayPalPayment thingToBuy = getThingToBuy(PayPalPayment.PAYMENT_INTENT_SALE);

        Intent intent = new Intent(ContributionActivity.this, PaymentActivity.class);

        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);

        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);

        startActivityForResult(intent, REQUEST_CODE_PAYMENT);

    }

    private PayPalPayment getThingToBuy(String paymentIntent) {
        return new PayPalPayment(new BigDecimal(contributeAmount.getText().toString()), "USD", name, paymentIntent);
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
            if (confirm != null) {
                try {
                    Log.i(TAG, confirm.toJSONObject().toString(4));
                    Log.i(TAG, confirm.getPayment().toJSONObject().toString(4));

                    double amount = Double.parseDouble(contributeAmount.getText().toString()) + Double.parseDouble(progress);

                    mDatabase = FirebaseDatabase.getInstance().getReference();
                    mDatabase.child("gift").child(giftKey).child("progress").setValue(amount);
                    String contributionKey = mDatabase.child("contribution").child(giftKey).push().getKey();
                    mDatabase.child("contribution").child(giftKey).child(contributionKey).child("amount").setValue(Double.parseDouble(contributeAmount.getText().toString()));
                    mDatabase.child("contribution").child(giftKey).child(contributionKey).child("contributor_id").setValue(contributorId);
                    mDatabase.child("contribution").child(giftKey).child(contributionKey).child("contributor_name").setValue(contributorName);
                    mDatabase.child("contribution").child(giftKey).child(contributionKey).child("time").setValue(getCurrentDateAndTime());

                    /** "me" means user contributes to themselves, otherwise, user contributes to friends. */

                    if(flag.equals("me")) {
                        Intent intent = new Intent(ContributionActivity.this, MainScreenActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        mDatabase.child("user").child(contributorId).child("gift_for_friend").child(giftKey).setValue("true");
                        finish();
                        Intent intent = new Intent(ContributionActivity.this, FriendGiftActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    // TODO: send 'confirm' to your server for verification.
                    // see https://developer.paypal.com/webapps/developer/docs/integration/mobile/verify-mobile-payment/
                    // for more details.
                    displayResultText("Thank you for your contribution!");

                } catch (JSONException e) {
                    Log.e("Payment service", "an extremely unlikely failure occurred: ", e);
                }
            }
        }
        else if (resultCode == Activity.RESULT_CANCELED) {
            Log.i("Payment service", "The user canceled.");
        }
        else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
            Log.i("Payment service", "An invalid payment was submitted. Please see the docs.");
        }
    }

    protected void displayResultText(String result) {
        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }
}
