package com.woof.gankist.ui.collection;

import android.util.Log;

import com.woof.gankist.dao.bean.Collection;
import com.woof.gankist.util.Constant;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by Woof on 3/29/2017.
 */

public class CollectionPresenter extends CollectionContract.Presenter {

    private CollectionContract.View mView;

    private int mPage = 0;

    public CollectionPresenter(CollectionContract.View view) { mView = view; }

    @Override
    public void onAttachView(CollectionContract.View view) {
        super.onAttachView(view);
        getCollectionItems(true);
    }

    @Override
    void getCollectionItems(boolean isRefresh) {
        if (isRefresh) {
            mView.showSwiploading();
            mPage = 0;
        } else {
            mPage += 1;
        }

        List<Collection> collections= DataSupport
                .limit(Constant.PAGE_SIZE_COLLECTION)
                .offset(Constant.PAGE_SIZE_COLLECTION * mPage)
                .order("createTime desc")
                .find(Collection.class);

        if (isRefresh) {
            mView.setCollectionItems(collections);
            mView.hideSwipLoading();
            mView.setLoading();
            if (collections == null || collections.size() == 0) {
                mView.hideSwipLoading();
                mView.setEmpty();
                return;
            }
        } else {
            mView.addCollectionItems(collections);
        }
        boolean isLastPage = collections.size() < Constant.PAGE_SIZE_COLLECTION;
        if (isLastPage) {
            mView.setLoadMore();
        }
    }
}
