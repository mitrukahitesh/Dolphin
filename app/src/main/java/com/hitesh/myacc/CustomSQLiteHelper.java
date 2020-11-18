package com.hitesh.myacc;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class CustomSQLiteHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Transactions";
    public static final String MONTHS = "MONTHS";

    public CustomSQLiteHelper(Context context, int version) {
        super(context, DATABASE_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE MONTHS(_id INTEGER PRIMARY KEY AUTOINCREMENT, month TEXT)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        
    }
}
