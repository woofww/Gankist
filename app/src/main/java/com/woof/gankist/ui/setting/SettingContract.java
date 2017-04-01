package com.woof.gankist.ui.setting;

import android.preference.Preference;

import com.woof.gankist.base.BasePresenter;
import com.woof.gankist.base.BaseView;

/**
 * Created by Woof on 3/31/2017.
 */

public interface SettingContract {

    interface View extends BaseView<Presenter> {

        void showCacheCleanTint();
    }

    abstract class Presenter extends BasePresenter<View> {
        abstract void setColorEnable(boolean b);

        abstract void setNoPicture(boolean b);

        abstract void setBuiltInBrowse(boolean b);

        abstract void cleanCache();
    }
}
