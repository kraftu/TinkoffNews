package com.test.kraftu.tinkoffnews.ui.list;

import android.os.Bundle;

import com.test.kraftu.tinkoffnews.source.DataSource;

import javax.inject.Inject;
import nucleus.presenter.RxPresenter;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class MainPresenter extends RxPresenter<MainActivity> {
    private static final int REQUEST_ITEMS = 1;

    @Inject
    DataSource dataSource;

    boolean isLoadingFromCache;
    boolean isLoadingState;

    @Override
    protected void onTakeView(MainActivity mainActivity) {
        super.onTakeView(mainActivity);
        mainActivity.showProgress(isLoadingState);
    }

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        restartableLatestCache(REQUEST_ITEMS,
                () -> dataSource.getHeaderPosts(isLoadingFromCache)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                ,(mainActivity, headsResponse) -> {
                    isLoadingState = false;
                    mainActivity.showProgress(isLoadingState);
                    mainActivity.setItems(headsResponse);
                }
                ,(mainActivity, throwable) -> {
                    mainActivity.onNetworkError(throwable);
                    loadHeadsPost(true);
                });
    }

    public void loadHeadsPost(boolean isCached){
        this.isLoadingFromCache = isCached;
        isLoadingState = true;
        start(REQUEST_ITEMS);
    }
}
