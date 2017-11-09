package com.example.summerrc.loadersdemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<PageBean> {
    private final static int TASKS_GET_PAGE_CONTENT = 1;
    private final static String PAGE_URL = "http://xiayu.me";

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private TextView tvContent;
    private DataLoader mLoader;
    private LoaderManager mLoaderManager;
    private DataSource mDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvContent = (TextView) findViewById(R.id.tvContent);
        initSwipeRefreshLayout();

        mDataSource = new DataSource(PAGE_URL);
        mLoader = new DataLoader(this, mDataSource);
        mLoaderManager = getSupportLoaderManager();
    }

    private void initSwipeRefreshLayout() {
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setColorSchemeColors(Color.parseColor("#5EDCDD"));
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                tvContent.setText("");
                mDataSource.refreshData();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mLoaderManager.initLoader(TASKS_GET_PAGE_CONTENT, null, this);
    }

    @Override
    public Loader<PageBean> onCreateLoader(int id, Bundle args) {
        mSwipeRefreshLayout.setRefreshing(true);
        return mLoader;
    }

    @Override
    public void onLoadFinished(Loader<PageBean> loader, PageBean data) {
        mSwipeRefreshLayout.setRefreshing(false);
        setText(data.getContent());
    }

    @Override
    public void onLoaderReset(Loader<PageBean> loader) {
        // no-op
    }

    private void setText(String content) {
        if (!TextUtils.isEmpty(content)) {
            tvContent.setText(content);
        }
    }

}
