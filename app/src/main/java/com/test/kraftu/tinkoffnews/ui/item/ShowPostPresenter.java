package com.test.kraftu.tinkoffnews.ui.item;


import android.os.Bundle;
import android.text.TextUtils;

import com.test.kraftu.tinkoffnews.source.DataSource;
import com.test.kraftu.tinkoffnews.tools.HtmlUntil;

import javax.inject.Inject;

import nucleus.presenter.RxPresenter;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ShowPostPresenter extends RxPresenter<ShowPostActivity> {
    private static final int REQUEST_ITEM = 1;

    @Inject
    DataSource dataSource;

    String postId;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        restartableLatestCache(REQUEST_ITEM,
                () -> dataSource.getPost(postId)
                        .subscribeOn(Schedulers.io())
                        .map(postResponse -> {
                            if(TextUtils.isEmpty(postResponse.getContent())) return null;
                            return HtmlUntil.fromHtml(postResponse.getContent());
                        })
                        .observeOn(AndroidSchedulers.mainThread()),
                (showPostActivity, textSpanned) -> {
                  showPostActivity.setTextContent(textSpanned);
                },(showPostActivity, throwable) ->
                        showPostActivity.onNetworkError(throwable));
    }

    public void loadPost(String postId){
        this.postId = postId;
        start(REQUEST_ITEM);
    }
}
