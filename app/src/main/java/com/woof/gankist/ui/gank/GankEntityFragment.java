package com.woof.gankist.ui.gank;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.woof.gankist.R;
import com.woof.gankist.dao.entity.GankEntity;
import com.woof.gankist.ui.adapter.GankContentAdapter;
import com.woof.gankist.util.listener.OnRecyclerViewItemListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;

/**
 * Created by Woof on 3/21/2017.
 */

public class GankEntityFragment extends Fragment implements GankEntityContract.View {

    @BindView(R.id.rv_gank_content)
    RecyclerView mRecyclerView;
    @BindView(R.id.refresh)
    SwipeRefreshLayout mRefreshLayout;

    private Handler mHandler = new Handler();
    private GankContentAdapter mGankContentAdapter;
    private GankEntityContract.Presenter mPresenter = new GankEntityPresenter();

    private static final String CATEGORY_NAME = "CATEGORY_NAME";
    private static final int LOAD_MORE_THRESHOLD = 1;
    private int loadingState = STATE_LOADING_IDLE;


    public GankEntityFragment getInstance(String category) {

        GankEntityFragment fragment = new GankEntityFragment();
        fragment.mPresenter.setCategory(category);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mPresenter.setCategory(savedInstanceState.getString(CATEGORY_NAME));
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gank_content, container, false);
        ButterKnife.bind(this, view);

        mGankContentAdapter = new GankContentAdapter();
        mGankContentAdapter.setOnRecyclerViewItemListerner(new OnRecyclerViewItemListener() {
            @Override
            public void onItemClick(View view, int position) {
                // TODO: 3/23/2017 此处设置点击事件，跳转webview
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mGankContentAdapter);
        // 设置滑动监听
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            boolean isSlidingToLast = false;
            /* 滚动状态发生变化的时候 */
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager manager = ((LinearLayoutManager) recyclerView.getLayoutManager());
                if (newState == SCROLL_STATE_IDLE) {
                    int lastVisibleItem = manager.findLastVisibleItemPosition();
                    int totalItemCount = mGankContentAdapter.getItemCount();

                    Log.e("Item数量", String.valueOf(totalItemCount));

                    if (lastVisibleItem == (totalItemCount - 1) && isSlidingToLast) {
                        mPresenter.loadMore();
                    }
                }
            }
            /* 滚动过程的回调 */
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                // TODO: 3/23/2017 此处需要测试handler存在和不存在时候的区别
                isSlidingToLast = dy >= 0;
//                mHandler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        onRecyclerViewScrolled();
//                    }
//                });
            }
        });
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 处理请求
                mPresenter.refresh();
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.onAttachView(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter != null) {
            mPresenter.onDetachView();
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(CATEGORY_NAME, mPresenter.getCategory());
    }


    @Override
    public void setloading(final int state) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.setRefreshing(state == STATE_LOADING_REFRESHING);
                mGankContentAdapter.setReserving(state == STATE_LOADING_RESERVING);
            }
        });
    }

    @Override
    public void addGankEntities(List<GankEntity.Entity> entities) {
        if (loadingState != STATE_LOADING_IDLE) {throw new RuntimeException("illegal state");}
        mGankContentAdapter.addEntities(entities);
        mGankContentAdapter.notifyDataSetChanged();
    }

    @Override
    public void clearEntities() {
        if (loadingState != STATE_LOADING_IDLE) {throw new RuntimeException(""); }
        mGankContentAdapter.clearEntities();
        mGankContentAdapter.notifyDataSetChanged();
    }

    @Override
    public void showNetWorkError() {
        View view = getView();
        if (view != null) {
            Snackbar.make(view, "数据出现异常，请检查网络连接", Snackbar.LENGTH_SHORT).show();
        }
    }

    private void onRecyclerViewScrolled() {
//        if (mRecyclerView.getChildCount() > 0 && loadingState == STATE_LOADING_IDLE) {
//            /* 获取当前屏幕显示item数量*/
//            int lastItemIndex = mRecyclerView.getChildCount() - 1; // 获取屏幕最后一个item位置
//            View lastItem = mRecyclerView.getChildAt(lastItemIndex);
//            int adapterPosition = mRecyclerView.getChildAdapterPosition(lastItem);
//            // 当adapter的位置处于到达提前加载区域的时候，进行提前获取
//            if (adapterPosition >= mGankContentAdapter.getItemCount() - LOAD_MORE_THRESHOLD) {
////                mPresenter.reserve();
//            }
//        }
    }
}
