package com.example.billy.androidlabs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class StartActivity extends Activity {
    protected final String ACTIVITY_NAME = "StartActivity";
    Toast msgPassedToast;

    Button b2;
    Button ForeCastBtn;
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Button b1 = (Button)findViewById(R.id.button);
        b2 = (Button)findViewById(R.id.startChatButton);
        ForeCastBtn = (Button)findViewById(R.id.startWeatherForecastButton);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextScreen = new Intent(StartActivity.this, ListItemsActivity.class);
                startActivityForResult(nextScreen, 50);
            }
        });
//
        b2.setOnClickListener((e)->{
            Log.i(ACTIVITY_NAME, "User clicked start Chat");
            Intent nextScreen = new Intent(StartActivity.this, ChatWindow.class);
            startActivity(nextScreen);
        });

        ForeCastBtn.setOnClickListener((e)->{
           Log.i(ACTIVITY_NAME, "User clicked Check Forecast");
           Intent nextScreen = new Intent(StartActivity.this, WeatherForecast.class);
           startActivity(nextScreen);
        });

    }
    @Override
    public void onActivityResult(int requestCode, int responseCode, Intent data){
        if(requestCode == 50){
            Log.i(ACTIVITY_NAME, "Returned to StartActivity.onActivityResult");
        }
        if(responseCode == Activity.RESULT_OK){
            String messagePassed = data.getStringExtra("Response");
            msgPassedToast = Toast.makeText(StartActivity.this, messagePassed, Toast.LENGTH_SHORT);
            msgPassedToast.show();
        }
    }

    public void onResume(){
        super.onResume();
        Log.i(ACTIVITY_NAME, "In onResume()");
    }
    public void onStart(){
        super.onStart();
        Log.i(ACTIVITY_NAME, "In onCreate()");
    }
    public void onPause(){
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
}