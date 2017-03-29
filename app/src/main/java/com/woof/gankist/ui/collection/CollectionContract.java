package com.woof.gankist.ui.collection;

import com.woof.gankist.base.BasePresenter;
import com.woof.gankist.base.BaseView;
import com.woof.gankist.dao.bean.Collection;

import java.util.List;

/**
 * Created by Woof on 3/29/2017.
 */

public interface CollectionContract {

    interface View extends BaseView<Presenter> {

        void addCollectionItems(List<Collection> collections);

        void setCollectionItems(List<Collection> collections);

        void showSwiploading();

        void hideSwipLoading();

        void setLoading();

        void setEmpty();

        void setLoadMore();
    }


    abstract class Presenter extends BasePresenter<View> {

        abstract void getCollectionItems(boolean isRefresh);
    }
}
