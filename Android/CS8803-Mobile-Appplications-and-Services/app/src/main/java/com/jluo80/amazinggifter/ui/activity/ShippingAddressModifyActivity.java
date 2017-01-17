package com.jluo80.amazinggifter.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jluo80.amazinggifter.R;
import com.jluo80.amazinggifter.ui.basic.BaseActivity;


public class ShippingAddressModifyActivity extends BaseActivity {

    private static final String TAG = ShippingAddressModifyActivity.class.getName();
    private DatabaseReference mDatabase;
    EditText addressFirst, addressSecond, city, state, zipcode, country, phoneNumber;
    Button confirm;
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping_address_modify);

        initializeScreen();

        SharedPreferences mSharedPreferences = this.getSharedPreferences("facebookLogin", Activity.MODE_PRIVATE);
        final String myFacebookId = mSharedPreferences.getString("facebookId", "");


        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle("Shipping Address");
                alertDialogBuilder
                        .setMessage("Do you want to make these changes?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                /** Save Gift data to Firebase. */
                                mDatabase = FirebaseDatabase.getInstance().getReference();
                                DatabaseReference shippingAddress = mDatabase.child("user").child(myFacebookId);
                                shippingAddress.child("address_first").setValue(addressFirst.getText().toString());
                                shippingAddress.child("address_second").setValue(addressSecond.getText().toString());
                                shippingAddress.child("city").setValue(city.getText().toString());
                                shippingAddress.child("state").setValue(state.getText().toString());
                                shippingAddress.child("zipcode").setValue(zipcode.getText().toString());
                                shippingAddress.child("country").setValue(country.getText().toString());
                                shippingAddress.child("phone").setValue(phoneNumber.getText().toString());

                                Intent intent = new Intent(ShippingAddressModifyActivity.this, ShippingAddressActivity.class);
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

    public void initializeScreen() {
        /** Set the toolbar of the add gifts activity. */
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /** Add back arrow to toolbar. */
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        addressFirst = (EditText) findViewById(R.id.address_line_1);
        addressSecond = (EditText) findViewById(R.id.address_line_2);
        city = (EditText) findViewById(R.id.city);
        state = (EditText) findViewById(R.id.state);
        zipcode = (EditText) findViewById(R.id.zip);
        country = (EditText) findViewById(R.id.country);
        phoneNumber = (EditText) findViewById(R.id.phone_number);
        confirm = (Button) findViewById(R.id.confirm);
    }
}
