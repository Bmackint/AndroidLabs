package com.example.billy.androidlabs;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ChatDatabaseHelper extends SQLiteOpenHelper{
    public static final String DATABASE_NAME = "Messages.db";
    public static final String TABLE_NAME = "Message_table";
    public static final String KEY_ID = "_id";
   public static final String KEY_MESSAGE = "messages";
    public static final int VERSION_NUM = 1;
  //  private static String KEY_ID;


    public ChatDatabaseHelper(Context ctx){
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME +
                "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_MESSAGE +" TEXT);");
               // "(KEY_ID INTEGER PRIMARY KEY AUTOINCREMENT, KEY_MESSAGE TEXT);");
        Log.i("ChatDataBaseHelper", "Calling on create");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
        Log.i("ChatDataBaseHelper", "Calling onUpgrade" + oldVersion + "New version: " + newVersion);
    }
    @Override
    public void onDowngrade(SQLiteDatabase db, int newVersion, int oldVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
        Log.i("ChatDataBaseHelper", "Calling onDowngrade" + newVersion + "Newversion: " + oldVersion);
    }
    public String getTableName(){
        return TABLE_NAME;
    }
    public String getDatabaseName(){
        return DATABASE_NAME;
    }
}
