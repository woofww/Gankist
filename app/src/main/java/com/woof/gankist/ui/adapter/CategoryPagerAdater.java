package com.woof.gankist.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.woof.gankist.ui.gank.GankEntityFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Woof on 3/20/2017.
 * 首页viewpager内容
 */

public class CategoryPagerAdater extends FragmentPagerAdapter {
    private List<String> categories;

    public CategoryPagerAdater(FragmentManager fm) {
        super(fm);
        this.categories = new ArrayList<>();
    }

    @Override
    public Fragment getItem(int position) {
        return new GankEntityFragment().getInstance(categories.get(position));
    }

    @Override
    public int getCount() {
        return categories.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        /* 通过getPageTitle对标题进行设置 */
        return categories.get(position);
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
        notifyDataSetChanged();
    }
}
