package com.woof.gankist.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.jude.rollviewpager.hintview.ColorPointHintView;
import com.woof.gankist.R;
import com.woof.gankist.base.BaseViewHolder;
import com.woof.gankist.dao.entity.GankEntity;
import com.woof.gankist.util.GlideHelper;
import com.woof.gankist.util.listener.OnRecyclerViewItemListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * TODO: 完成Adapter的bind部分
 * Created by Woof on 3/20/2017.
 */

public class GankContentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private Handler handler = new Handler();
    private List<GankEntity.Entity> mEntities;
    private OnRecyclerViewItemListener mOnRecyclerViewItemListener;

    // 调试运行
    private static final String TAG = GankContentAdapter.class.getSimpleName();
    private boolean isLoadMore;

    // 布局类型Gank包含两种类型，分为有图和无图，第三种为加载更多
    private static final int TYPE_ITEM_IMG  = 0;
    private static final int TYPE_ITEM_TEXT = 1;
    private static final int TYPE_LOAD_MORE = 2;

    public GankContentAdapter() {
        mEntities = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        switch (viewType){
            case TYPE_ITEM_TEXT:
                return new GankTextViewHolder(LayoutInflater.from(mContext).
                inflate(R.layout.item_gank_text, parent, false));
            case TYPE_ITEM_IMG:
                return new GankImageViewHolder(LayoutInflater.from(mContext).
                inflate(R.layout.item_gank_img, parent, false));
            case TYPE_LOAD_MORE:
                return new LoadMoreViewHolder(LayoutInflater.from(mContext).
                inflate(R.layout.item_loadmore, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int itemType = getItemViewType(position);
        switch (itemType) {
            case TYPE_ITEM_TEXT:
                ((GankTextViewHolder) holder).bind(mEntities.get(position));
                break;
            case TYPE_ITEM_IMG:
                ((GankImageViewHolder) holder).bind(mEntities.get(position));
                break;
        }
    }

//    (isLoadMore ? 1 : 0)
    @Override
    public int getItemCount() {
        return mEntities.size() + (isLoadMore ? 1 : 0);
    }

    /**
     * 通过该方法进行item类型判断，有图无图需要对
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {


        if (isLoadMore && position == getItemCount() - 1) {
            Log.e("加载更多", "在此处加载" + position);
            return GankContentAdapter.TYPE_LOAD_MORE;
        }
        GankEntity.Entity entity = mEntities.get(position);
        if (entity.images == null || entity.images.size() == 0) {
            return GankContentAdapter.TYPE_ITEM_TEXT;
        } else {
            return GankContentAdapter.TYPE_ITEM_IMG;
        }
    }

    public void addEntities(List<GankEntity.Entity> entities) {
        // 从此处传入bean类实体
        int insertPosition = this.mEntities.size();
        this.mEntities.addAll(entities);
        notifyDataSetChanged();
        notifyItemRangeInserted(insertPosition, entities.size());
    }

    public void clearEntities() {
        int removeCount = mEntities.size();
        mEntities.clear();
        notifyItemRangeChanged(0, removeCount);
    }

    public void setReserving(boolean reserving) {
        if (this.isLoadMore == reserving) {return;}

        this.isLoadMore = reserving;
        // 当需要加载更多的时候，进行数据插入。
        if (reserving) {
            notifyDataSetChanged();
            notifyItemInserted(getItemCount());

        } else {
            notifyDataSetChanged();
            notifyItemRemoved(getItemCount() - 1);
        }
    }

    public void setOnRecyclerViewItemListerner(OnRecyclerViewItemListener listerner){
        this.mOnRecyclerViewItemListener = listerner;
    }

    public void setCategoryImage(ImageView categoryImage, String category) {
        switch (category) {
            case "Android":
                categoryImage.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.android));
                break;
            case "iOS":
                categoryImage.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.ios));
                break;
            case "前端":
                categoryImage.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.js));
                break;
            default:
                categoryImage.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.ic_launcher));
                break;
        }
    }

    public class GankTextViewHolder extends BaseViewHolder<GankEntity.Entity> {
        @BindView(R.id.tv_gank_title)
        TextView gank_title;
        @BindView(R.id.tv_gank_author)
        TextView gank_author;
        @BindView(R.id.tv_gank_publish)
        TextView gank_publish_date;
        @BindView(R.id.iv_category)
        ImageView category;
        @BindView(R.id.ll_clickable)
        LinearLayout mLinearLayout;


        public GankTextViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void bind(GankEntity.Entity entities) {
            gank_title.setText(entities.title);
            gank_author.setText(entities.author != null ? String.format("via %s", entities.author) : "");
            gank_publish_date.setText(entities.publishedAt);
            setCategoryImage(category, entities.type);
        }
    }

    public class GankImageViewHolder extends BaseViewHolder<GankEntity.Entity> {

//        @BindView(R.id.rollerview_gank)
//        RollPagerView gankRollPagerView;
        @BindView(R.id.tv_gank_author)
        TextView gank_author;
        @BindView(R.id.tv_gank_title)
        TextView gank_title;
        @BindView(R.id.tv_gank_publish)
        TextView gank_publish_date;
        @BindView(R.id.iv_category)
        ImageView category;
        @BindView(R.id.ll_clickable)
        LinearLayout mLinearLayout;


        public GankImageViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void bind(GankEntity.Entity entities) {
//            if (entities.images.size() <= 1) {
//                gankRollPagerView.setHintView(null);
//            } else {
//                gankRollPagerView.setHintView(new ColorPointHintView(
//                        mContext,
//                        Color.WHITE,
//                        Color.parseColor("#BDBDBD")));
//            }
//            gankRollPagerView.setAdapter(new RollPagerAdapter(entities));
            setCategoryImage(category, entities.type);
            gank_title.setText(entities.title);
            gank_author.setText(String.format("via %s", entities.author));
            gank_publish_date.setText(entities.publishedAt);
        }
    }

    public class LoadMoreViewHolder extends RecyclerView.ViewHolder {

        public LoadMoreViewHolder(View itemView) {
            super(itemView);
        }

    }

    public class RollPagerAdapter extends StaticPagerAdapter {

        public GankEntity.Entity mEntity;


        public RollPagerAdapter(GankEntity.Entity entity) {
            mEntity = entity;
        }

        @Override
        public View getView(ViewGroup container, int position) {
            ImageView view = new ImageView(container.getContext());
            GlideHelper.imageLoad(mContext, mEntity.images.get(position), view);
            return view;
        }

        @Override
        public int getCount() {
            return mEntity.images.size();
        }
    }
}
