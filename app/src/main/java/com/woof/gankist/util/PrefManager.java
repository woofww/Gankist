package com.woof.gankist.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.woof.gankist.base.BaseApplication;

/**
 * Created by Woof on 3/20/2017.
 */

public class PrefManager {

    private SharedPreferences mSharedPreferences;

    private PrefManager() {
        mSharedPreferences = BaseApplication.getAppContext()
                .getSharedPreferences("setting", Context.MODE_PRIVATE);
    }

    private static class PrefManagerHolder {
        private static final PrefManager sInstance = new PrefManager();
    }

    public static PrefManager getInstance() {
//        return PrefManagerHolder.sInstance;
        return new PrefManager();
    }

    public void setInt(String key, int value) {
        mSharedPreferences.edit().putInt(key, value).apply();
    }

    public int getInt(String key, int initValue) {
        return mSharedPreferences.getInt(key, initValue);
    }
}
