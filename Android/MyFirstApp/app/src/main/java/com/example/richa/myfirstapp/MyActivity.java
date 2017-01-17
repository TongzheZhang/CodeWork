package com.example.richa.myfirstapp;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.firebase.client.Firebase;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


public class MyActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "com.mycompany.myfirstapp.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        if(findViewById(R.id.fragment_container)!=null){
            if(savedInstanceState != null){
                return;
            }

        }
    }

    /**called when the user clicks the Send button*/
    //View stands for the view which be clicked, must be public, must be void
    public void sendMessage(View view){
        //do something in response to button
        Firebase usersRef = new Firebase("https://project-3295068924528844630.firebaseio.com");

        Map<String, Object> age = new HashMap<String, Object>();
        age.put("Dogs rule", "test");
        age.put("Cats drewl", "test");

        usersRef.setValue(age);

        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText)findViewById(R.id.edit_message);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);

    }
}
