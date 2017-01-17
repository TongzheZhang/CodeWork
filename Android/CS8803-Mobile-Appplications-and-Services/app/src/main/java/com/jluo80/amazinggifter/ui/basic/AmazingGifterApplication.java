package com.jluo80.amazinggifter.ui.basic;

import com.google.firebase.database.FirebaseDatabase;


public class AmazingGifterApplication extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        /** Enable disk persistence  */
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
