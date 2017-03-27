package com.woof.gankist.ui.main;

import com.woof.gankist.base.BasePresenter;
import com.woof.gankist.base.BaseView;

import java.util.List;

/**
 * Created by Woof on 3/22/2017.
 */

public interface MainContract {

    interface View extends BaseView<Presenter> {

        void setTabTitle(List<String> tabs);
    }

    abstract class Presenter extends BasePresenter<View> {

        abstract void loadCategories();
    }
}
