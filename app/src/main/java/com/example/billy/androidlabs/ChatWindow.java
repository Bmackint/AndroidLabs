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



        //pulls first row 3 times

        Cursor c = db.rawQuery("SELECT messages FROM message_table", null);
        int numInstances = c.getCount();
        int messageColumn = c.getColumnIndex("Messages");
        Log.i(ACTIVITY_NAME,  Integer.toString(numInstances) + " rows in db, " + "and columns: " + dbHelper.KEY_ID + ", " + dbHelper.KEY_MESSAGE );
        if(c.getCount() !=0){
            c.moveToFirst();
            for(int i = 1; i <= c.getCount(); i++){
                String resultMessage = c.getString(messageColumn);
                Log.i(ACTIVITY_NAME, "Cursor's column count: " + c.getColumnCount() + "     current row: " + c.getPosition());
                msgList.add(resultMessage);
                c.moveToNext();
            }

        }


        /*
        if(c.getCount() != 0) {
            c.moveToFirst();

            msgList.add(c.getString(c.getPosition()));
        }
        */


        /*
        while(!c.isAfterLast()){

            Log.i(ACTIVITY_NAME, text.getText().toString() + c.getString(c.getColumnIndex(dbHelper.KEY_MESSAGE)));
            Log.i(ACTIVITY_NAME, text.getText().toString() + c.getString(c.getColumnIndex("KEY_MESSAGE")));
        }
        */
        send.setOnClickListener((e)->{
          if(!db.isOpen()){
              db.isOpen();
          }
          String input = text.getText().toString();

          msgList.add(input);

          chatAdapter.notifyDataSetChanged();

          text.setText("");
        //lab 5
            ContentValues newRow = new ContentValues();
            newRow.put("messages", input);
            db.insert(dbHelper.getTableName(),null, newRow);
            db.close();

        });

        //db.close();
       // Cursor c = db.rawQuery("SELECT messages FROM messages, null,);
        if(db.isOpen()){
            Log.i("is open", "db is open");
        }else if(!db.isOpen()){
            Log.i("is closed", "db is closed");
        }
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
