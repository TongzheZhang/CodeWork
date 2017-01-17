package com.jluo80.amazinggifter.ui.basic;

import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.jluo80.amazinggifter.R;
import com.jluo80.amazinggifter.ui.activity.DatePickerFragment;
import com.jluo80.amazinggifter.ui.activity.MainScreenActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class BaseActivity extends AppCompatActivity {

    private ProgressDialog mProgressDialog;

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }
        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        hideProgressDialog();
    }

    public boolean isEmpty(String content) {
        return content.trim().length() == 0;
    }

    public void showDatePickerDialog(View v) {
        DialogFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public String getCurrentDateAndTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("MM-dd-yyyy HH:mm");
        return mdformat.format(calendar.getTime());
    }

    public String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("MM/dd/yy");
        return mdformat.format(calendar.getTime());
    }

    public static long dateDiff(String str) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
        Date dueDate = null;
        try {
            dueDate = dateFormat.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date currentDate = new Date();
        long diffTime = dueDate.getTime() - currentDate.getTime();
        long diffDays = diffTime / (1000 * 60 * 60 * 24);
        return diffDays;
    }

    /** Whenever you wish to exit all open activities, you should press a button which loads the
     * first Activity that runs when your application starts then clear all the other activities,
     * then have the last remaining activity finished. To do so apply the following code in your
     * project.
     *
     * There would */

    public void facebookLogout() {
        FacebookSdk.sdkInitialize(getApplication().getApplicationContext());
        if (AccessToken.getCurrentAccessToken() != null && FirebaseAuth.getInstance().getCurrentUser() != null) {
            FirebaseAuth.getInstance().signOut();
            LoginManager.getInstance().logOut();
        }
        Intent intent = new Intent(getApplicationContext(), MainScreenActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("EXIT", true);
        startActivity(intent);
        finish();
    }

    /** Inflate the menu; this adds items to the action bar if it is present. */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /** Handle action bar item clicks here. The action bar will automatically
     * handle clicks on the Home/Up button, so long as you specify a parent
     * activity in AndroidManifest.xml. */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                return true;
            case R.id.action_refresh:
                finish();
                startActivity(getIntent());
                return true;
            case R.id.action_logout:
                facebookLogout();
                return true;
            case android.R.id.home:
                /** android.R.id.home
                 * When onClick, you can determine if you want to start the MainScreenActivity
                 * or go back to the previous activity.
                 *
                 * Basically, onBackPressed() method works the same as finish() if you didn't
                 * overwrite the method. */
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
