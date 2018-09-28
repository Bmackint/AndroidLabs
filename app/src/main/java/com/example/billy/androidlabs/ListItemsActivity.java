package com.example.billy.androidlabs;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

public class ListItemsActivity extends Activity {
    ImageButton b1;
    Switch s1;
    CheckBox c2;
    Toast switchToast;
    CharSequence toastText;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    protected static final String ACTIVITY_NAME = "ListItemsActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_items);
        b1 = (ImageButton)findViewById(R.id.imageButton);

        s1 = (Switch)findViewById(R.id.switch1);

        c2 = (CheckBox) findViewById(R.id.checkBox2);

        final CharSequence textOn = "Switch is On";
        final CharSequence textOff = "Switch is Off";

        b1.setOnClickListener(new View.OnClickListener() {


            private void dispatchTakePictureIntent(){
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null){
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }

            @Override
            public void onClick(View v) {
       //         Intent nextScreen = new Intent(ListItemsActivity.this, ListItemsActivity.class);
                dispatchTakePictureIntent();

            }
        });
//switch
        s1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(s1.isChecked() == true){
                    switchToast = Toast.makeText(ListItemsActivity.this, textOn, Toast.LENGTH_SHORT );
                    switchToast.show();
                }else {
//                    toastText = "Switch is Off";
                    switchToast = Toast.makeText(ListItemsActivity.this, textOff, Toast.LENGTH_LONG);
                    switchToast.show();
                }
            }
        });
//checkbox
        c2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ListItemsActivity.this);
                builder.setMessage(R.string.Activity_End_Dialogue);

                builder.setTitle(R.string.Activity_End_Title);
                builder.setPositiveButton(R.string.Ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("Response", "Here is my Response");
                        setResult(ListItemsActivity.RESULT_OK, resultIntent);
                        finish();
                    }
                });

                builder.setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            b1.setImageBitmap(imageBitmap);
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
