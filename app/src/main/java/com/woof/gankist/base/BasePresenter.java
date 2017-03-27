package com.woof.gankist.base;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * Created by Woof on 3/20/2017.
 */

public abstract class BasePresenter<T> {

    private Reference<T> mView;
    // 获取数据进行界面显示，在fragment的onResume方法当中调用
//    public void start() {}

    public T getView() {return mView.get();}

    public void onAttachView(T view) {
        this.mView = new WeakReference(view);
    }

    public void onDetachView() {
        // presenter持有view的引用，当不需要view attach的时候，赋值null，避免内存泄露
//        mView.clear();
        mView.clear();
        mView = null;
    }
}
