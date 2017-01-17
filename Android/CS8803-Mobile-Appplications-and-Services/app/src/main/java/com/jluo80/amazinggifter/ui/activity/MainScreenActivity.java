package com.jluo80.amazinggifter.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.jluo80.amazinggifter.R;
import com.jluo80.amazinggifter.ui.basic.BaseActivity;
import com.jluo80.amazinggifter.utils.MySingleton;


public class MainScreenActivity extends BaseActivity {

    private static final String TAG = MainScreenActivity.class.getName();
    private DrawerLayout mDrawerLayout;
    private FloatingActionButton fab;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("Test Refresh Problem", "MainScreenActivity onCreate");

//        FacebookSdk.sdkInitialize(getApplication().getApplicationContext());
//        mAuth = FirebaseAuth.getInstance();

        /** After finishing all the activities except for the first activity, MainScreenActivity.
         * Then we need to finish the MainScreenActivity's individually by the below code in
         * MainScreenActivity's onCreate. */

//        if (getIntent().getBooleanExtra("EXIT", false)) {
//            finish();
//
//            /** The killProcess and System.exit(0) can only kill the current activity. */
//            /** Without using these, the application will still run in the background,
//             * when you try to login again, there will be "Permission Denied" Firebase
//             * database error so that you can't get any response data. */
//            android.os.Process.killProcess(android.os.Process.myPid());
//            System.exit(0);
//        }

        setContentView(R.layout.activity_main_screen);
        /** Setup Toolbar and ActionBar. */
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        /** The android.R.id.home button in this activity is different from the other activities.
         * It is a menu icon instead of a back arrow icon. Besides, the onOptionsItemSelected has
         * benn overrode since the android.R.id.home function in this activity is to use to invoke
         * mDrawerLayout.openDrawer(GravityCompat.START);
         */
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);

        /** Setup DrawerLayout. */
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);

        /** 1.Setup drawer header content: Title and profile picture. */
        View drawerHeader = navigationView.getHeaderView(0);
        NetworkImageView drawerPicture = (NetworkImageView) drawerHeader.findViewById(R.id.drawer_picture);
        NetworkImageView coverPicture = (NetworkImageView) drawerHeader.findViewById(R.id.cover_picture);

        /** Fetch data from SharePreferences. */
        SharedPreferences mSharedPreferences = this.getSharedPreferences("facebookLogin", Activity.MODE_PRIVATE);
        String drawerPictureUrl = mSharedPreferences.getString("picture", "");
        String coverPictureUrl = mSharedPreferences.getString("cover", "");
        ImageLoader imageLoader = MySingleton.getInstance(MainScreenActivity.this.getApplicationContext()).getImageLoader();
        drawerPicture.setImageUrl(drawerPictureUrl, imageLoader);
        coverPicture.setImageUrl(coverPictureUrl, imageLoader);


        /** 2.Setup drawer body content: Name, E-mail and birthday. */
        /** Fetch data from SharePreferences. */
        String username = mSharedPreferences.getString("username", "");
        String email = mSharedPreferences.getString("email", "");
        String birthday = mSharedPreferences.getString("birthday", "");
        Menu menu = navigationView.getMenu();
        MenuItem nav_name = menu.findItem(R.id.navigation_item_name);
        nav_name.setTitle(username);
        MenuItem nav_email = menu.findItem(R.id.navigation_item_email);
        nav_email.setTitle(email);
        MenuItem nav_birthday = menu.findItem(R.id.navigation_item_birthday);
        nav_birthday.setTitle(birthday);

        /** Setup item onSelectedListener. */
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                Intent intent;

                if (menuItem.getItemId() == R.id.navigation_item_name) {
                    mDrawerLayout.closeDrawers();
                    intent = new Intent(MainScreenActivity.this, AddGiftsActivity.class);
                    startActivity(intent);
                    return true;
                }

                if (menuItem.getItemId() == R.id.navigation_item_email) {
                    mDrawerLayout.closeDrawers();
                    intent = new Intent(MainScreenActivity.this, AddGiftsActivity.class);
                    startActivity(intent);
                    return true;
                }

                if (menuItem.getItemId() == R.id.navigation_item_birthday) {
                    mDrawerLayout.closeDrawers();
                    intent = new Intent(MainScreenActivity.this, AddGiftsActivity.class);
                    startActivity(intent);
                    return true;
                }

                if (menuItem.getItemId() == R.id.navigation_item_address) {
                    mDrawerLayout.closeDrawers();
                    intent = new Intent(MainScreenActivity.this, ShippingAddressActivity.class);
                    startActivity(intent);
                    return true;
                }

                if (menuItem.getItemId() == R.id.navigation_item_about_me) {
                    mDrawerLayout.closeDrawers();
                    String resumeUrl = "http://jiahaoluo.deercv.com";
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(resumeUrl)));
                    return true;
                }

                if (menuItem.getItemId() == R.id.navigation_item_refresh) {
                    mDrawerLayout.closeDrawers();
                    finish();
                    startActivity(getIntent());
                    return true;
                }

                if (menuItem.getItemId() == R.id.navigation_item_logout) {
                    mDrawerLayout.closeDrawers();
                    facebookLogout();
                    return true;
                }
                return false;
            }
        });

        /** Setup FloatingActionButton. */
        fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context, AddGiftsActivity.class);
                intent.putExtra("me_friend_tab", "me");
                context.startActivity(intent);
            }
        });

        TabPagerAdapter adapter = new TabPagerAdapter(this, getSupportFragmentManager());
        final ViewPager viewPager = (ViewPager)findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);
        final TabLayout tabLayout = (TabLayout)findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                animateFab(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.getTabAt(position).select();
                animateFab(position);
            }
        });
    }


    static class TabPagerAdapter extends FragmentStatePagerAdapter {

        private Context mContext;

        public TabPagerAdapter(Context context, FragmentManager fm) {
            super(fm);
            mContext = context;
        }

        public Fragment getItem(int position) {
            if (position == 0) {
                return new MyGiftFragment();
            } else if (position == 1) {
                return new FriendContactFragment();
            } else {
                return new SummaryFragment();
            }
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (position == 0) {
                return mContext.getString(R.string.my_gifts);
            } else if (position == 1) {
                return mContext.getString(R.string.friends);
            } else {
                return mContext.getString(R.string.summary);
            }
        }
    }

    /** Override the onKeyDown method here, because when press
     * back key at the MainScreenActivity, we want the apps keep
     * running in the background instead of finish the activity.
     * However, by default, other activities would go back to the
     * previous activity when press back key. */

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(false);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

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
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void animateFab(int position) {
        switch (position) {
            case 0:
                fab.show();
                break;
            default:
                fab.hide();
                break;
        }
    }

    public void facebookLogout() {
        FacebookSdk.sdkInitialize(getApplicationContext());
        Log.e("SOS1",FirebaseAuth.getInstance().getCurrentUser() + "");
        Log.e("SOS1", AccessToken.getCurrentAccessToken() + "");
        if (AccessToken.getCurrentAccessToken() != null && FirebaseAuth.getInstance().getCurrentUser() != null) {
            FirebaseAuth.getInstance().signOut();
            LoginManager.getInstance().logOut();
            Log.e("SOS2",FirebaseAuth.getInstance().getCurrentUser() + "");
            Log.e("SOS2", AccessToken.getCurrentAccessToken() + "");
        }
        finish();
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }
}