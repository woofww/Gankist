package com.woof.gankist.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by Woof on 3/20/2017.
 * 通过泛型适配不同类型的ViewHolder
 * 并在bind方法中做视图绑定
 */

public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {

    public BaseViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public abstract void bind(T t) throws Exception;
}
