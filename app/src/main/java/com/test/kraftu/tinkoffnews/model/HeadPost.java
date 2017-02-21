package com.test.kraftu.tinkoffnews.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HeadPost {
    @SerializedName("id")
    private String id;
    @SerializedName("text")
    private String text;
    @SerializedName("publicationDate")
    private DatePost datePost;

    public HeadPost() {
    }

    public HeadPost(String id, String text, long pubDate) {
        this.id = id;
        this.text = text;
        this.datePost = new DatePost(pubDate);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public DatePost getDatePost() {
        return datePost;
    }

    public void setDatePost(DatePost datePost) {
        this.datePost = datePost;
    }

    @Override
    public String toString() {
        return "HeadPost{" +
                "id='" + id + '\'' +
                ", text='" + text + '\'' +
                ", datePost=" + datePost +
                '}';
    }
}
