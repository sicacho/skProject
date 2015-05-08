package com.skAndroid.dbHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.skAndroid.common.Common;

/**
 * Created by khangtnse60992 on 11/9/2014.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context) {
        super(context, "DBCounting", null, 2);
        // TODO Auto-generated constructor stub
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String createCounter = "CREATE TABLE IF NOT EXISTS "+ Common.COUNTER_TABLE + " (_id integer primary key autoincrement , " +
                " idDetail integer not null , score integer , times integer , target integer ); ";
        String createReminder = "CREATE TABLE IF NOT EXISTS "+ Common.REMINDER_TABLE + " (_id integer primary key autoincrement , " +
                " idCounter integer not null , sentence TEXT , sentenceTrans TEXT , type integer ); ";
        db.execSQL(createCounter);
        db.execSQL(createReminder);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+Common.COUNTER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+Common.REMINDER_TABLE);
        onCreate(db);
    }
}
