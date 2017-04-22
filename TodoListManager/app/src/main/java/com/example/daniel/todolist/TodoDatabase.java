package com.example.daniel.todolist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Daniel on 20/04/2017.
 */

public class TodoDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "TodoDatabase";

    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_ENTRIES= "create table tasks(id text ,content text not null, creation_date text not null, remainder_date text not null, checkbox integer);";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS TodoDatabase";

    public TodoDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}

