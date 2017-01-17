package com.jluo80.amazinggifter.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.facebook.share.widget.ShareDialog;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jluo80.amazinggifter.R;
import com.jluo80.amazinggifter.model.Contributor;
import com.jluo80.amazinggifter.ui.basic.BaseActivity;
import com.jluo80.amazinggifter.utils.MySingleton;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.util.ArrayList;


public class ShippingAddressActivity extends BaseActivity {

    private static final String TAG = ShippingAddressActivity.class.getName();
    private DatabaseReference mDatabase;
    TextView addressFirst, addressSecond, city, state, zip, country, phoneNumber;
    Button modify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping_address);

        initializeScreen();

        SharedPreferences mSharedPreferences = this.getSharedPreferences("facebookLogin", Activity.MODE_PRIVATE);
        String myFacebookId = mSharedPreferences.getString("facebookId", "");

        mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference shippingAddress = mDatabase.child("user").child(myFacebookId);
        shippingAddress.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                addressFirst.setText(dataSnapshot.child("address_first").getValue(String.class));
                addressSecond.setText(dataSnapshot.child("address_second").getValue(String.class));
                city.setText(dataSnapshot.child("city").getValue(String.class));
                state.setText(dataSnapshot.child("state").getValue(String.class));
                zip.setText(dataSnapshot.child("zipcode").getValue(String.class));
                country.setText(dataSnapshot.child("country").getValue(String.class));
                phoneNumber.setText(dataSnapshot.child("phone").getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShippingAddressActivity.this, ShippingAddressModifyActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void initializeScreen() {
        /** Set the toolbar of the add gifts activity. */
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /** Add back arrow to toolbar. */
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        addressFirst = (TextView) findViewById(R.id.address_line_1);
        addressSecond = (TextView) findViewById(R.id.address_line_2);
        city = (TextView) findViewById(R.id.city);
        state = (TextView) findViewById(R.id.state);
        zip = (TextView) findViewById(R.id.zip);
        country = (TextView) findViewById(R.id.country);
        phoneNumber = (TextView) findViewById(R.id.phone_number);
        modify = (Button) findViewById(R.id.modify);
    }
}
