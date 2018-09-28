package com.example.billy.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;



public class LoginActivity extends Activity {

    private EditText et;
    private SharedPreferences prefs;

    protected static final String ACTIVITY_NAME = "LoginActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        et = (EditText) findViewById(R.id.editText);

        prefs = getSharedPreferences("NewSaveFile", Context.MODE_PRIVATE);
        String userString = prefs.getString("UserInput", "no Value Exists");

        et.setText(userString);
        Button b1 = (Button) findViewById(R.id.loginButton);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = et.getText().toString();

                SharedPreferences.Editor edit = prefs.edit();
                edit.putString("UserInput", input);

                edit.commit();

                Intent nextScreen = new Intent(LoginActivity.this, StartActivity.class);
                startActivity(nextScreen);

            }
        });
        }
        public void onResume(){
            super.onResume();
            Log.i(ACTIVITY_NAME, "In onResume()");
        }

        public void onStart() {
            super.onStart();
            Log.i(ACTIVITY_NAME, "In onCreate()");
        }

        public void onPause() {
            super.onPause();
            Log.i(ACTIVITY_NAME, "In onPause()");
        }

        public void onStop(){
            super.onStop();
            Log.i(ACTIVITY_NAME, "In onStop()");
        }
        public void onDestroy(){
            super.onDestroy();
            Log.i(ACTIVITY_NAME, "In onDestroy");
        }

        //   SharedPreferences loginEmailPref = getSharedPreferences(, Context.MODE_PRIVATE);

    }

