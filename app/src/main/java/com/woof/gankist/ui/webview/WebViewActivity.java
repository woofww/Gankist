package com.woof.gankist.ui.webview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.woof.gankist.R;
import com.woof.gankist.base.BaseActivity;
import com.woof.gankist.util.MenuUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Woof on 3/27/2017.
 */

public class WebViewActivity extends BaseActivity implements WebViewContract.View {

    public static final String URL = "com.woof.gankist.ui.webview.WebViewActivity.url";
    public static final String TITLE = "com.woof.gankist.ui.webview.WebViewActivity.title";

    /* 用于获取intent返回值 */

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_title)
    TextView mTitle;
    @BindView(R.id.web_view)
    WebView mWebView;
    @BindView(R.id.progressbar_webview)
    ProgressBar mProgressBar;
    @BindView(R.id.appbar)
    AppBarLayout mAppBarLayout;

    private WebViewContract.Presenter mPresenter = new WebViewPresenter(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        ButterKnife.bind(this);
        initToolBar();
        initWebView();
        mPresenter.onAttachView(this);

    }

    private void initToolBar() {
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initWebView() {
        WebSettings settings = mWebView.getSettings();
        settings.setLoadWithOverviewMode(true);
        settings.setJavaScriptEnabled(true);
        settings.setAppCacheEnabled(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setSupportZoom(true);

        mWebView.setWebChromeClient(new WebChrome());
        mWebView.setWebViewClient(new webClient());

    }

    @Override
    public void loadGankURL(String url) {
        mWebView.loadUrl(url);
    }

    @Override
    public String getLoadUrl() {
        return getIntent().getStringExtra(WebViewActivity.URL);  //进行数据请求
    }

    @Override
    public void setGankTitle(String title) {
        mTitle.setText(title);
    }

    @Override
    public String getGankTitle() {
        return getIntent().getStringExtra(WebViewActivity.TITLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.webview_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_share:
                // 获取到链接进行分享
                MenuUtil.share(this, mPresenter.getGankUrl());
                break;
            case R.id.action_copy:
                if (MenuUtil.copyText(this, mPresenter.getGankUrl())) {
                    Toast.makeText(this, "链接已复制", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.action_open_in_browser:
                MenuUtil.openWithBrowser(this, mPresenter.getGankUrl());
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            finish();
        }
    }

    private class WebChrome extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            mProgressBar.setVisibility(View.VISIBLE);
            mProgressBar.setProgress(newProgress);
        }
    }

    private class webClient extends WebViewClient {
        /* 当网络请求结束的时候，隐藏progressbar */
        @Override
        public void onPageFinished(WebView view, String url) {
            mProgressBar.setVisibility(View.GONE);
        }
    }
}
