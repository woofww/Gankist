package com.woof.gankist.ui.main;

import java.util.Arrays;

/**
 * Created by Woof on 3/22/2017.
 */

public class MainPresenter  extends MainContract.Presenter {
    @Override
    public void onAttachView(MainContract.View view) {
        super.onAttachView(view);
        loadCategories();
    }

    /* 设置 tab 标题 */
    @Override
    void loadCategories() {
        getView().setTabTitle(Arrays.asList("Android", "iOS", "前端", "App", "瞎推荐", "all"));
    }

}
