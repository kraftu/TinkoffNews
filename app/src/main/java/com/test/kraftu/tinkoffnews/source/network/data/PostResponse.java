package com.test.kraftu.tinkoffnews.source.network.data;


import com.google.gson.annotations.SerializedName;
import com.test.kraftu.tinkoffnews.model.Post;

public class PostResponse {
    @SerializedName("resultCode")
    public String resultCode;

    @SerializedName("payload")
    public Post post;
}
