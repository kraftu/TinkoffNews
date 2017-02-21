package com.test.kraftu.tinkoffnews.model;


import com.google.gson.annotations.SerializedName;

public class Post {
    @SerializedName("title")
    private HeadPost headPost;
    @SerializedName("content")
    private String content;

    public Post() {
    }

    public Post(String postId, String title, long pubDate, String content) {
        this.headPost = new HeadPost(postId,title,pubDate);
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public HeadPost getHeadPost() {
        return headPost;
    }

    public void setHeadPost(HeadPost headPost) {
        this.headPost = headPost;
    }

    @Override
    public String toString() {
        return "Post{" +
                "headPost=" + headPost +
                ", content='" + content +
                '}';
    }
}
