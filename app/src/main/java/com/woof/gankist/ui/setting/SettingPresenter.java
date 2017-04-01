package com.woof.gankist.ui.setting;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.woof.gankist.util.GlideHelper;
import com.woof.gankist.util.PrefManager;

/**
 * Created by Woof on 3/31/2017.
 */

public class SettingPresenter extends SettingContract.Presenter {

    private Context mContext;
    private SettingContract.View mView;
    private PrefManager mPrefManager;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 2:
                    mView.showCacheCleanTint();
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };

    public SettingPresenter(Context context, SettingContract.View view) {
        mContext = context;
        mView = view;
        mPrefManager = PrefManager.getInstance();
    }

    @Override
    public void onAttachView(SettingContract.View view) {
        super.onAttachView(view);
    }

    @Override
    void setColorEnable(boolean b) {
        mPrefManager.setColorEnable(b);
    }

    @Override
    void setNoPicture(boolean b) {
        mPrefManager.setDisplayImage(b);
    }

    @Override
    void setBuiltInBrowse(boolean b) {
        mPrefManager.setBuiltInDisplay(b);
    }

    @Override
    void cleanCache() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                GlideHelper.cleanDiskCache(mContext);
                Message message = new Message();
                message.what = 2;
                mHandler.sendMessage(message);
                GlideHelper.imageClear(mContext);
            }
        }).start();

    }
}
