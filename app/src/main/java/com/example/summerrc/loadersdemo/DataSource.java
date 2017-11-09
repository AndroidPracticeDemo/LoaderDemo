package com.example.summerrc.loadersdemo;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SummerRC on 17/11/9.
 * description: Main entry point for accessing data
 */

public class DataSource {
    private String mUrl;
    private boolean mCacheIsDirty = false;
    private List<DataObserver> mObservers = new ArrayList<>();
    private PageBean mPageBean;

    public DataSource(String url) {
        mUrl = url;
    }

    public PageBean getPageBean() {
        if (mPageBean!=null && !mCacheIsDirty) {
            return mPageBean;
        }
        try {
            HttpURLConnection urlConnection = (HttpURLConnection) (new URL(mUrl)).openConnection();
            urlConnection.setConnectTimeout(20000);     //请求超时时间为2秒
            urlConnection.setReadTimeout(30000);        //读取超时时间为3秒
            BufferedInputStream in = new BufferedInputStream(urlConnection.getInputStream());
            String content = "";
            int i = in.read();
            while (i != -1) {
                content += String.valueOf((char) i);
                i = in.read();
            }
            mPageBean = new PageBean(mUrl, content);
            mCacheIsDirty = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mPageBean;
    }

    public void refreshData() {
        mCacheIsDirty = true;
        notifyObserver();
    }

    public void addObserver(DataObserver observer) {
        if (!mObservers.contains(observer)) {
            mObservers.add(observer);
        }
    }

    public void removeObserver(DataObserver observer) {
        if (mObservers.contains(observer)) {
            mObservers.remove(observer);
        }
    }

    private void notifyObserver() {
        for (DataObserver observer : mObservers) {
            observer.onDataChanged();
        }
    }

    public interface DataObserver {
        void onDataChanged();
    }
}
