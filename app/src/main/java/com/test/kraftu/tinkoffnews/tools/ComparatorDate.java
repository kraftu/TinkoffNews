package com.test.kraftu.tinkoffnews.tools;

import com.test.kraftu.tinkoffnews.model.HeadPost;

import java.util.Comparator;

public class ComparatorDate implements Comparator<HeadPost> {
    @Override
    public int compare(HeadPost o1, HeadPost o2) {
        if(o1.getDatePost() == null
                || o2.getDatePost() == null) return 0;
        return o1.getDatePost().getMilliseconds()
                < o2.getDatePost().getMilliseconds()?1:-1;
    }
}
