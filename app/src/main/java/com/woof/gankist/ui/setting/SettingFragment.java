package com.woof.gankist.ui.setting;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.util.Log;

import com.woof.gankist.R;
import com.woof.gankist.base.BaseApplication;
import com.woof.gankist.util.PrefManager;

/**
 * Created by Woof on 3/27/2017.
 */

public class SettingFragment extends PreferenceFragment implements SettingContract.View,
        Preference.OnPreferenceChangeListener, Preference.OnPreferenceClickListener {

    private static final String TAG = SettingFragment.class.getSimpleName();

    private Context mContext;
    private SettingContract.Presenter mPresenter;
    private SwitchPreference mChangeColor;
    private SwitchPreference mShowImage;
    private CheckBoxPreference mInnerBrowse;
    private Preference mCleanCache;

    public SettingFragment() {

        mContext = BaseApplication.getAppContext();
        Log.e(TAG, "运行构造函数");
        mPresenter = new SettingPresenter(mContext, this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.setting);
        mChangeColor = ((SwitchPreference) findPreference(PrefManager.TAB_COLOR));
        mShowImage   = ((SwitchPreference) findPreference(PrefManager.DISPLAY_IMAGE));
        mInnerBrowse = ((CheckBoxPreference) findPreference(PrefManager.BUILT_IN_DISPLAY));
        mCleanCache  = findPreference(PrefManager.CACHE_CLEAN);

        mChangeColor.setOnPreferenceChangeListener(this);
        mShowImage.setOnPreferenceChangeListener(this);
        mInnerBrowse.setOnPreferenceChangeListener(this);
        mCleanCache.setOnPreferenceClickListener(this);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object o) {
        if (mChangeColor == preference) {
            mPresenter.setColorEnable((Boolean) o);
            Log.e("温度转换", String.valueOf(PrefManager.getInstance().getColorEnable(true)));
        } else if (mShowImage == preference) {
            mPresenter.setNoPicture((Boolean) o);
            Log.e("温度转换1", String.valueOf(PrefManager.getInstance().getDisplayImage(true)));
        } else {
            mPresenter.setBuiltInBrowse((Boolean) o);
            Log.e("温度转换2", String.valueOf(PrefManager.getInstance().getBuildDisplay(true)));
        }
        return true;
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        if (mCleanCache == preference) {
//            GlideHelper.imageClear(getActivity());
            mPresenter.cleanCache();
        }
        return true;
    }

    @Override
    public void showCacheCleanTint() {
        Snackbar.make(getView(), R.string.clear_image_cache, Snackbar.LENGTH_SHORT).show();
    }

}
