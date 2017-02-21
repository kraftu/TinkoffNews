package com.test.kraftu.tinkoffnews.source.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DbOpenHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "Post.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String BOOLEAN_TYPE = " INTEGER";

    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DbContract.PostColumns.TABLE + " (" +
                    DbContract.PostColumns.COLUMN_ENTRY_ID + TEXT_TYPE + " PRIMARY KEY," +
                    DbContract.PostColumns.COLUMN_TITLE + TEXT_TYPE + COMMA_SEP +
                    DbContract.PostColumns.COLUMN_PUB_DATE + INTEGER_TYPE + COMMA_SEP +
                    DbContract.PostColumns.COLUMN_CONTENT + TEXT_TYPE +
                    " )";

    public DbOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
