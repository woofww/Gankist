package com.woof.gankist.ui.collection;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.woof.gankist.R;
import com.woof.gankist.base.BaseActivity;
import com.woof.gankist.dao.bean.Collection;
import com.woof.gankist.ui.adapter.CollectionAdapter;
import com.woof.gankist.ui.webview.WebViewActivity;
import com.woof.gankist.util.widget.listener.OnRecyclerViewItemListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Woof on 3/29/2017.
 */

public class CollectionActivity extends BaseActivity implements CollectionContract.View,
        SwipeRefreshLayout.OnRefreshListener, OnRecyclerViewItemListener{

    @BindView(R.id.toolbar_collection)
    Toolbar mToolbar;
    @BindView(R.id.appbar_collection)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.rv_collection)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh_collection)
    SwipeRefreshLayout mRefreshLayout;

    public static final int REQUEST_CODE_COLLECTION = 123;
    public static final String COLLECTION_POSITION = "com.woof.gankist.ui.collection.CollectionActivity.favourite_position";
    private CollectionContract.Presenter mPresenter = new CollectionPresenter(this);
    private CollectionAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        ButterKnife.bind(this);
        initToolBar();
        initView();
        mPresenter.onAttachView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDetachView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_COLLECTION) {
            int position = data.getIntExtra(COLLECTION_POSITION, -1);
            if (position != -1) {
                mAdapter.notifyItemRemoved(position);
                mAdapter.mCollections.remove(position);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void initView() {
        mRefreshLayout.setOnRefreshListener(this);
        mAdapter = new CollectionAdapter();
        mAdapter.setOnRecyclerViewItemListerner(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);
    }


    private void initToolBar() {
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void addCollectionItems(List<Collection> collections) {
        mAdapter.mCollections.addAll(collections);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void setCollectionItems(List<Collection> collections) {
        mAdapter.mCollections =collections;
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showSwiploading() {
        mRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideSwipLoading() {
        mRefreshLayout.setRefreshing(false);
    }

    @Override
    public void setLoading() {

    }

    @Override
    public void setEmpty() {
        mAdapter.mCollections.clear();
        mAdapter.notifyDataSetChanged();
        Toast.makeText(this, "暂无收藏", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setLoadMore() {

    }

    @Override
    public void onRefresh() {
        mPresenter.getCollectionItems(true);
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra(WebViewActivity.TITLE, mAdapter.mCollections.get(position).getTitle());
        intent.putExtra(WebViewActivity.URL, mAdapter.mCollections.get(position).getUrl());
        intent.putExtra(WebViewActivity.COLLECT_POSITION, position);
        intent.putExtra(WebViewActivity.COLLECT_DATA, mAdapter.mCollections.get(position));
        startActivityForResult(intent, CollectionActivity.REQUEST_CODE_COLLECTION);
    }
}
