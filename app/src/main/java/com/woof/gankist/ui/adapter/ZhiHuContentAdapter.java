package com.woof.gankist.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.woof.gankist.R;
import com.woof.gankist.base.BaseViewHolder;
import com.woof.gankist.dao.entity.ZhiHuNews;
import com.woof.gankist.util.listener.OnRecyclerViewItemListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Woof on 3/20/2017.
 */

public class ZhiHuContentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private OnRecyclerViewItemListener mOnRecyclerViewItemListener;
    private ZhiHuNews mZhiHuNews;
    private List<ZhiHuNews.Story> mStories = new ArrayList<>();

    private static final int TYPE_ZHIHU = 0;
    private static final int TYPE_LOAD_MORE = 1;

    public ZhiHuContentAdapter(List<ZhiHuNews.Story> stories) {
        mStories = stories;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_ZHIHU:
                return new ZhiHuViewHolder(LayoutInflater
                        .from(mContext).inflate(R.layout.item_zhihu, parent, false));
            case TYPE_LOAD_MORE:
                return new ZhiHuLoadMoreViewHolder(LayoutInflater
                        .from(mContext).inflate(R.layout.item_loadmore, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int itemType = getItemViewType(position);
        switch (itemType) {
            case TYPE_ZHIHU:
                ((ZhiHuViewHolder) holder).bind(mZhiHuNews);
        }
    }

    @Override
    public int getItemCount() {
        return mStories.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == mStories.size()) {
            return ZhiHuContentAdapter.TYPE_LOAD_MORE;
        }
        return ZhiHuContentAdapter.TYPE_ZHIHU;
    }

    private class ZhiHuViewHolder extends BaseViewHolder<ZhiHuNews> {

        public ZhiHuViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void bind(ZhiHuNews zhiHuMessage) {

        }
    }

    private class ZhiHuLoadMoreViewHolder extends RecyclerView.ViewHolder {
        /**
         * 加载更多
         * @param itemView
         */
        public ZhiHuLoadMoreViewHolder(View itemView) {
            super(itemView);
        }
    }
}
