package com.demo.demo_coin.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Nishi on 5/11/2018.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    public static final int DAABASE_VERSION = 2;

    public static final String DATABASE_NAME = "coin.db";


    public static final String TABLE_COIN = "coin_details";


    public static final String C_ID = "id";
    public static final String C_COIN_ID = "coin_id";
    public static final String C_NAME = "coin_name";


    public static final String CREATE_TABLE_Coin = " CREATE TABLE "
            + TABLE_COIN +
            " ( " +
            C_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            C_COIN_ID + " INTEGER NOT NULL, " +
            C_NAME + " TEXT NOT NULL " +
            " ) ";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DAABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_Coin);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_COIN);
        onCreate(sqLiteDatabase);

    }

}
