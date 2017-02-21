package com.test.kraftu.tinkoffnews.source;


import com.test.kraftu.tinkoffnews.model.HeadPost;
import com.test.kraftu.tinkoffnews.model.Post;
import com.test.kraftu.tinkoffnews.source.database.DbDataSource;
import com.test.kraftu.tinkoffnews.source.network.ServerApi;
import com.test.kraftu.tinkoffnews.tools.ComparatorDate;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;

public class CachedDataSource implements DataSource {

    @Inject
    ServerApi serverApi;
    @Inject
    DbDataSource dbDataSource;

    public CachedDataSource(ServerApi serverApi, DbDataSource dbDataSource) {
        this.serverApi = serverApi;
        this.dbDataSource = dbDataSource;
    }

    @Override
    public Observable<List<HeadPost>> getHeaderPosts(boolean fromCache) {
        if(fromCache){
            return dbDataSource.getHeaderPosts();
        }
        else {
            return serverApi.getHeadPosts()
                    .map(headsResponse -> {
                        dbDataSource.saveHeadPostList(headsResponse.list);
                        return headsResponse;
                    })
                    .map(headsResponse -> {
                        Collections.sort(headsResponse.list, new ComparatorDate());
                        return headsResponse.list;
                    });
        }
    }

    @Override
    public Observable<Post> getPost(String postId) {
        return serverApi.getPost(postId).map(postResponse -> {
            dbDataSource.savePost(postResponse.post);
            return postResponse.post;
        }).onErrorResumeNext(throwable -> dbDataSource.getPost(postId));
    }
}
