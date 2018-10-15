package com.example.billy.androidlabs;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ChatDatabaseHelper extends SQLiteOpenHelper{
    public static String DATABASE_NAME;
    public static String TABLE_NAME = "MESSAGES";
 //   public static String KEY_ID = "_id";
    public static String KEY_MESSAGE;
    public static int VERSION_NUM = 1;
  //  private static String KEY_ID;


    public ChatDatabaseHelper(Context ctx){
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME +
                "(KEY_ID INTEGER PRIMARY KEY AUTOINCREMENT, KEY_MESSAGE TEXT);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    public String getTableName(){
        return TABLE_NAME;
    }
}
