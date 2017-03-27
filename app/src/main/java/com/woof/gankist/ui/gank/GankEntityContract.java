package com.woof.gankist.ui.gank;

import com.woof.gankist.base.BasePresenter;
import com.woof.gankist.base.BaseView;
import com.woof.gankist.dao.entity.GankEntity;

import java.util.List;

/**
 * Created by Woof on 3/20/2017.
 * 契约类，对view和presenter进行统一管理，presenter持有view的弱引用
 */

public interface GankEntityContract {

    interface View extends BaseView<Presenter> {
//        // 显示错误
//        void showError();
//        // 显示加载准状态
//        void showLoading();
//        // 停止显示加载
//        void stopLoading();
//        // 获取数据进行显示
//        void showResults(ArrayList<GankEntity.Entity> list);
//        // 用于对特定的日期进行加载
        int STATE_LOADING_IDLE = 0;
        int STATE_LOADING_REFRESHING = 1;
        int STATE_LOADING_RESERVING = 2;
        // 加载状态
        void setloading(int state);
        void addGankEntities(List<GankEntity.Entity> entities);
        void clearEntities();
        void showNetWorkError();
    }

    abstract class Presenter extends BasePresenter<View> {
        // 数据请求，通过对类型的设置实现不同的请求，presenter持有view的引用，通过getView实现调用
        public abstract void setCategory(String category);
        public abstract String getCategory();
        public abstract void refresh();
        public abstract void loadMore();
        public abstract void startReading(int position);
        public abstract void readRandom();
    }
}
