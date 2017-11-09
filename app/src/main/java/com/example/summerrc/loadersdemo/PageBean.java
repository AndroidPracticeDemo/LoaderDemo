package com.example.summerrc.loadersdemo;

/**
 * Created by SummerRC on 17/11/9.
 * description: model class for a html page
 */

public class PageBean {
    private String mUrl;
    private String mContent;

    public PageBean(String url, String content) {
        mUrl = url;
        mContent = content;
    }

    public String getUrl() {
        return mUrl;
    }

    public String getContent() {
        return mContent;
    }
}
