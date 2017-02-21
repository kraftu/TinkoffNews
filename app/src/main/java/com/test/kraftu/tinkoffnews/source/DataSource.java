package com.test.kraftu.tinkoffnews.source;


import com.test.kraftu.tinkoffnews.model.HeadPost;
import com.test.kraftu.tinkoffnews.model.Post;

import java.util.List;

import rx.Observable;

public interface DataSource {
    Observable<List<HeadPost>> getHeaderPosts(boolean fromCache);
    Observable<Post> getPost(String postId);
}
