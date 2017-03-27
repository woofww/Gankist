package com.woof.gankist.ui.gank;

import android.os.Handler;
import android.util.Log;

import com.woof.gankist.dao.entity.GankEntity;
import com.woof.gankist.network.RetrofitHelper;
import com.woof.gankist.util.PrefManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Woof on 3/21/2017.
 */

public class GankEntityPresenter extends GankEntityContract.Presenter {

    private static final int PAGE_SIZE = 10; //可以考虑调整用seekbar进行设置

    private File cacheFile;
    private PrefManager mPrefManager;
    private List<GankEntity.Entity> mEntities = new ArrayList<>();
    private String category;
    private int currentPage = 1; // 初始化页码
    private boolean fullyloaded;

    public GankEntityPresenter() {
        // TODO: 3/23/2017 在Constructor部分进行数据库，网络请求，缓存等初始化
    }

    @Override
    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String getCategory() {
        return category;
    }

    @Override
    public void refresh() {
        /* 界面刷新时，未fullyload */
        fullyloaded = false;
        getView().setloading(GankEntityContract.View.STATE_LOADING_REFRESHING);
        loadGankEntity(1, true);
    }

    @Override
    public void loadMore() {
        if (fullyloaded) {return;}
        getView().setloading(GankEntityContract.View.STATE_LOADING_RESERVING);
        loadGankEntity(currentPage, false);
    }

    @Override
    public void startReading(int position) {

    }

    @Override
    public void readRandom() {
        // 随机抽取文章进行阅读，待实现
    }

    @Override
    public void onAttachView(GankEntityContract.View view) {
        super.onAttachView(view);

        if (mEntities.size() > 0) {
            getView().addGankEntities(mEntities);
            return;
        }

        /* 在应用刚启动的时候，mEntities中不存在数据 */
        List<GankEntity.Entity> entities = null; // TODO: 3/23/2017 请求数据库

        if (entities != null && entities.size() > 0) {
//            currentPage = PrefManager.getInstance().getInt(getLoadedPagesKey(), 1); // 加载当前页数
            mEntities.addAll(entities);
            getView().addGankEntities(entities);
        } else {
            /* 如果不存在缓存，进行数据刷新 */
            refresh();
        }
    }

    @Override
    public void onDetachView() {
        super.onDetachView();
    }

    private void loadGankEntity(int page, final boolean clearBefore) {
        /* 请求逻辑 */
        Call<GankEntity> call = RetrofitHelper
                .getInstance()
                .getGankService()
                .getGankEntity(category, PAGE_SIZE, page);

        call.enqueue(new Callback<GankEntity>() {
            @Override
            public void onResponse(Call<GankEntity> call, final Response<GankEntity> response) {
                /* 在独立线程进行数据库等操作，同时Retrofit已经完成异步请求，
                    返回时候已经在主线程，因此不需要独立开启新线程操作 */
                getView().setloading(GankEntityContract.View.STATE_LOADING_IDLE);
                if (response.body().hasError) {new Exception();}
                // 返回的response为空的时候
                if (response.body().entities.size() == 0) {
                    // 当请求为空的时候，不进行加载
                    fullyloaded = true;
                    return;
                }
                if (clearBefore) {
                    /* 如果此前界面曾被 clear 即 clearbefore 被标记为 true, 则重新进行请求 */
                    currentPage = 1;
                    mEntities.clear();
                    getView().clearEntities();
                    Log.e("刷新", "清空List");
                    // TODO: 3/22/2017 此处待加入缓存清除以及数据库等操作
                } else {
                    currentPage += 1;
                    Log.e("请求正常", "成功" + currentPage);
                }

                for (GankEntity.Entity entity : response.body().entities) {
                    // TODO: 3/22/2017 添加到数据库操作

                }
//                saveLoadedPageCount();
                mEntities.addAll(response.body().entities);
                getView().addGankEntities(response.body().entities);
            }

            @Override
            public void onFailure(Call<GankEntity> call, Throwable t) {
                getView().setloading(GankEntityContract.View.STATE_LOADING_IDLE);
                getView().showNetWorkError();
            }
        });
    }
//
//    private void saveLoadedPageCount() {
//        mPrefManager.setInt(getLoadedPagesKey(), currentPage);
//    }
//
//    private String getLoadedPagesKey() {
//        return category + "_loaded_pages";
//    }


}
