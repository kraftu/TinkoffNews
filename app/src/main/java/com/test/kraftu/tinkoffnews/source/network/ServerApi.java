package com.test.kraftu.tinkoffnews.source.network;

import com.test.kraftu.tinkoffnews.source.network.data.HeadsResponse;
import com.test.kraftu.tinkoffnews.source.network.data.PostResponse;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;


public interface ServerApi {
    String BASE_URL = "https://api.tinkoff.ru/v1/";


    @GET("news")
    Observable<HeadsResponse> getHeadPosts();

    @GET("news_content")
    Observable<PostResponse> getPost(@Query("id") String postId);

}
