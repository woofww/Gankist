package com.woof.gankist.ui.webview;


/**
 * Created by Woof on 3/27/2017.
 */

public class WebViewPresenter extends WebViewContract.Presenter {

    private WebViewContract.View mViewWeb;

    private String mGankUrl;



    public WebViewPresenter(WebViewContract.View viewWeb) {
        this.mViewWeb = viewWeb;
    }

    private void loadData() {
        mGankUrl = mViewWeb.getLoadUrl();
        mViewWeb.loadGankURL(mGankUrl);
    }

    @Override
    public String getGankUrl() {
        return mGankUrl;
    }

    @Override
    public void favoriteGank() {
        // 是否收藏
    }

    @Override
    public void onAttachView(WebViewContract.View view) {
        super.onAttachView(view);
        mViewWeb.setGankTitle(mViewWeb.getGankTitle());
        loadData();
    }

    @Override
    public void onDetachView() {
        super.onDetachView();
    }
}
