package com.test.kraftu.tinkoffnews;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;
import com.test.kraftu.tinkoffnews.source.CachedDataSource;
import com.test.kraftu.tinkoffnews.source.DataSource;
import com.test.kraftu.tinkoffnews.source.database.DbDataSource;
import com.test.kraftu.tinkoffnews.source.database.DbOpenHelper;
import com.test.kraftu.tinkoffnews.source.network.ServerApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.schedulers.Schedulers;


@Module
public class AppModule {
    private final Context context;

    AppModule(Context context) {
        this.context = context;
    }

    @Provides @Singleton Context provideApplication() {
        return context;
    }

    @Provides
    @Singleton
    ServerApi getServerApi(){
        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(ServerApi.BASE_URL)
                .build().create(ServerApi.class);
    }

    @Provides @Singleton
    SQLiteOpenHelper getOpenHelper(Context context) {
        return new DbOpenHelper(context);
    }

    @Provides @Singleton
    SqlBrite provideSqlBrite() {
        return new SqlBrite.Builder().build();
    }

    @Provides @Singleton
    BriteDatabase getDatabase(SqlBrite sqlBrite, SQLiteOpenHelper helper) {
        BriteDatabase db = sqlBrite.wrapDatabaseHelper(helper, Schedulers.io());
        db.setLoggingEnabled(true);
        return db;
    }

    @Provides @Singleton
    DbDataSource getDbDataSource(BriteDatabase briteDatabase) {
        return new DbDataSource(briteDatabase);
    }

    @Provides
    @Singleton
    DataSource getDataSource(ServerApi serverApi, DbDataSource dbDataSource){
        return new CachedDataSource(serverApi,dbDataSource);
    }
}
