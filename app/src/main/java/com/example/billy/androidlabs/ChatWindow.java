package com.example.billy.androidlabs;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatWindow extends Activity {
    protected final String ACTIVITY_NAME = "ChatWindow";
    EditText text;
    Button send;
    ListView msgView;
    ArrayList<String> msgList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
        text = (EditText) findViewById(R.id.chatMsg);
        send = (Button) findViewById(R.id.sendButton);
        msgView = (ListView) findViewById(R.id.chatView);
        msgList = new ArrayList<>();


        ChatAdapter chatAdapter = new ChatAdapter(this);/// is chatWindow the context?
        msgView.setAdapter(chatAdapter);

        ChatDatabaseHelper dbHelper = new ChatDatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT KEY_MESSAGE FROM MESSAGES", null);
        while(!c.isAfterLast()){
            Log.i(ACTIVITY_NAME, "SQL Message" + c.getString(c.getColumnIndex(dbHelper.KEY_MESSAGE)));
        }
        send.setOnClickListener((e)->{
          String input = text.getText().toString();

          msgList.add(input);

          chatAdapter.notifyDataSetChanged();

          text.setText("");
        //lab 5
            ContentValues newRow = new ContentValues();
            newRow.put("KEY_MESSAGE", input);
            db.insert(dbHelper.getTableName(),null, newRow);

        });

       // Cursor c = db.rawQuery("SELECT messages FROM messages, null,);
    }

    private class ChatAdapter extends ArrayAdapter<String>{
        ChatAdapter(Context context) {
            super(context, 0);
        }
        public int getCount(){
            return msgList.size();
        }
        public String getItem(int position){
            return msgList.get(position);
        }

        public View getView(int position, View convertView, ViewGroup parent){
            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();
            View result = null;
            if(position%2 == 0){
                result = inflater.inflate(R.layout.chat_row_incoming, null);
            }else{
                result = inflater.inflate(R.layout.chat_row_outgoing, null);
            }

            TextView message = (TextView) result.findViewById(R.id.message_text);
            message.setText(getItem(position));
            return result;
        }

        public String getItemID(int position){ // change Long -> String (temp)
            return msgList.get(position);
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
