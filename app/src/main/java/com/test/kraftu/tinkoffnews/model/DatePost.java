package com.test.kraftu.tinkoffnews.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class DatePost {

    @SerializedName("milliseconds")
    @Expose
    private long milliseconds;

    public DatePost() {
    }

    public DatePost(long milliseconds) {
        this.milliseconds = milliseconds;
    }

    public long getMilliseconds() {
        return milliseconds;
    }

    public void setMilliseconds(long milliseconds) {
        this.milliseconds = milliseconds;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
