package com.example.summerrc.loadersdemo;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

/**
 * Created by SummerRC on 17/11/9.
 * description: 内容加载器
 */

public class DataLoader extends AsyncTaskLoader<PageBean> implements DataSource.DataObserver{
    private DataSource mDataSource;

    public DataLoader(Context context, DataSource dataSource) {
        super(context);
        mDataSource = dataSource;
        mDataSource.addObserver(this);
    }

    @Override
    public PageBean loadInBackground() {
        return mDataSource.getPageBean();
    }

    @Override
    public void deliverResult(PageBean data) {
        super.deliverResult(data);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public void onDataChanged() {
        forceLoad();
    }
}
