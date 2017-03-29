package com.woof.gankist.ui.webview;

import com.woof.gankist.base.BasePresenter;
import com.woof.gankist.base.BaseView;

/**
 * Created by Woof on 3/27/2017.
 */

public interface WebViewContract {

    interface View extends BaseView<Presenter> {

        void setGankTitle(String title);

        String getGankTitle();

        void loadGankURL(String url);

        String getLoadUrl();
    }

    abstract class Presenter extends BasePresenter<View> {

        public abstract String getGankUrl();

        public abstract void favoriteGank();
    }

}
