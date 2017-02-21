package com.test.kraftu.tinkoffnews.source.database;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.squareup.sqlbrite.BriteDatabase;
import com.test.kraftu.tinkoffnews.model.HeadPost;
import com.test.kraftu.tinkoffnews.model.Post;
import com.test.kraftu.tinkoffnews.source.DataSource;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Func1;

import static com.test.kraftu.tinkoffnews.source.database.DbContract.getLong;
import static com.test.kraftu.tinkoffnews.source.database.DbContract.getString;

public class DbDataSource{

    private Func1<Cursor, HeadPost> mHeadsPostMapperFunction;
    private Func1<Cursor, Post> mPostMapperFunction;

    BriteDatabase briteDatabase;

    public DbDataSource(BriteDatabase briteDatabase) {
        this.briteDatabase = briteDatabase;
        mHeadsPostMapperFunction = this::getHeadPost;
        mPostMapperFunction = this::getPost;
    }

    private HeadPost getHeadPost(@NonNull Cursor c) {
        String postId = getString(c, DbContract.PostColumns.COLUMN_ENTRY_ID);
        String title = getString(c, DbContract.PostColumns.COLUMN_TITLE);
        long pubDate = getLong(c, DbContract.PostColumns.COLUMN_PUB_DATE);
        return new HeadPost(postId, title, pubDate);
    }

    private Post getPost(@NonNull Cursor c) {
        String postId = getString(c, DbContract.PostColumns.COLUMN_ENTRY_ID);
        String title = getString(c, DbContract.PostColumns.COLUMN_TITLE);
        long pubDate = getLong(c, DbContract.PostColumns.COLUMN_PUB_DATE);
        String content = getString(c, DbContract.PostColumns.COLUMN_CONTENT);
        return new Post(postId, title, pubDate,content);
    }

    public void saveHeadPostList(List<HeadPost> listPost){
        BriteDatabase.Transaction transaction = briteDatabase.newTransaction();
        for(HeadPost headPost : listPost){
            saveHeadPost(headPost);
        }
        transaction.markSuccessful();
        transaction.close();

    }
    public void saveHeadPost(HeadPost headPost){
        ContentValues values = new ContentValues();
        values.put(DbContract.PostColumns.COLUMN_ENTRY_ID, headPost.getId());
        values.put(DbContract.PostColumns.COLUMN_TITLE, headPost.getText());
        values.put(DbContract.PostColumns.COLUMN_PUB_DATE, headPost.getDatePost().getMilliseconds());
        briteDatabase.insert(DbContract.PostColumns.TABLE, values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public void savePost(Post post){
        ContentValues values = new ContentValues();
        values.put(DbContract.PostColumns.COLUMN_CONTENT, post.getContent());

        String selection = DbContract.PostColumns.COLUMN_ENTRY_ID + " LIKE ?";
        String[] selectionArgs = {post.getHeadPost().getId()};
        briteDatabase.update(DbContract.PostColumns.TABLE, values, selection, selectionArgs);
    }

    public Observable<List<HeadPost>> getHeaderPosts(){
        String[] projection = {
                DbContract.PostColumns.COLUMN_ENTRY_ID,
                DbContract.PostColumns.COLUMN_TITLE,
                DbContract.PostColumns.COLUMN_PUB_DATE,
        };
        String sql = String.format("SELECT %s FROM %s ORDER BY %s DESC",
                TextUtils.join(",", projection), DbContract.PostColumns.TABLE,
                DbContract.PostColumns.COLUMN_PUB_DATE);
        return briteDatabase.createQuery(DbContract.PostColumns.TABLE, sql)
                .mapToList(mHeadsPostMapperFunction);
    }
    public Observable<Post> getPost(String postId){
        String[] projection = {
                DbContract.PostColumns.COLUMN_ENTRY_ID,
                DbContract.PostColumns.COLUMN_TITLE,
                DbContract.PostColumns.COLUMN_PUB_DATE,
                DbContract.PostColumns.COLUMN_CONTENT,
        };
        String sql = String.format("SELECT %s FROM %s WHERE %s LIKE ?",
                TextUtils.join(",", projection), DbContract.PostColumns.TABLE,
                DbContract.PostColumns.COLUMN_ENTRY_ID);
        return briteDatabase.createQuery(DbContract.PostColumns.TABLE, sql, postId)
                .mapToOneOrDefault(mPostMapperFunction, null);

    }
    public void deleteAllPost() {
        briteDatabase.delete(DbContract.PostColumns.TABLE, null);
    }

}