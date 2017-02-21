package com.test.kraftu.tinkoffnews.source.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.support.annotation.NonNull;

import com.squareup.sqlbrite.BriteDatabase;
import com.test.kraftu.tinkoffnews.model.HeadPost;
import com.test.kraftu.tinkoffnews.model.Post;

import rx.functions.Func1;


public class DbContract {
    public static final int BOOLEAN_FALSE = 0;
    public static final int BOOLEAN_TRUE = 1;

    public static abstract class PostColumns{
        public static final String TABLE = "post";
        public static final String COLUMN_ENTRY_ID = "postId";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_PUB_DATE = "publicationDate";
        public static final String COLUMN_CONTENT= "content";
    }

    public static String getString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndexOrThrow(columnName));
    }

    public static boolean getBoolean(Cursor cursor, String columnName) {
        return getInt(cursor, columnName) == BOOLEAN_TRUE;
    }

    public static long getLong(Cursor cursor, String columnName) {
        return cursor.getLong(cursor.getColumnIndexOrThrow(columnName));
    }

    public static int getInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndexOrThrow(columnName));
    }
}
