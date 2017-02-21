package com.test.kraftu.tinkoffnews.source.network.data;


import com.google.gson.annotations.SerializedName;
import com.test.kraftu.tinkoffnews.model.HeadPost;

import java.util.List;

public class HeadsResponse {
    @SerializedName("resultCode")
    public String resultCode;

    @SerializedName("payload")
    public List<HeadPost> list;
}
