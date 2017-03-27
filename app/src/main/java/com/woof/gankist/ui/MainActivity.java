package com.woof.gankist.ui;

import android.animation.ArgbEvaluator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.woof.gankist.R;
import com.woof.gankist.base.BaseActivity;
import com.woof.gankist.ui.adapter.CategoryPagerAdater;
import com.woof.gankist.ui.main.MainContract;
import com.woof.gankist.ui.main.MainPresenter;
import com.woof.gankist.ui.setting.SettingActivity;
import com.woof.gankist.util.DoubleClickUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.widget.Toast.LENGTH_SHORT;

/**
 * todo:正确获取消息列表并展示
 * todo:能够获取历史消息
 * todo:展示内容详情
 * todo:后台自动缓存内容详情，方便用户在无网络连接时查看
 * todo:收藏特定消息
 * todo:夜间模式
 * 继承自BaseActivity实现了状态栏的沉浸式，不需要在style文件当中声明
 */
public class MainActivity extends BaseActivity implements
        NavigationView.OnNavigationItemSelectedListener, ViewPager.OnPageChangeListener, MainContract.View {

    @BindView(R.id.nav_view)
    NavigationView mNavView;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.main_container)
    ViewPager mViewPager;

    private CategoryPagerAdater mCategoryPagerAdater;
    private MainContract.Presenter mPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mPresenter = new MainPresenter();
        setSupportActionBar(mToolbar);
        initDrawer();

        mCategoryPagerAdater = new CategoryPagerAdater(getSupportFragmentManager());
        mViewPager.setAdapter(mCategoryPagerAdater);
//        mViewPager.addOnPageChangeListener(this);
        mTabLayout.setupWithViewPager(mViewPager);
        mPresenter.onAttachView(this);
    }

    private void initDrawer() {
        if (mNavView != null){
            mNavView.setNavigationItemSelectedListener(this);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar,
                    R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            mDrawerLayout.addDrawerListener(toggle);
            toggle.syncState();

        }
    }

    @Override
    public void setTabTitle(List<String> tabs) {
        mCategoryPagerAdater.setCategories(tabs);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        /**
         * 调用NavigationItemSelected接口
         */
        final int id = item.getItemId();
        mDrawerLayout.closeDrawers();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                switch (id) {
                    case R.id.gank_part:
                        break;
                    case R.id.zhihu_part:
                        break;
                    case R.id.bookmark_part:
                        break;
                    case R.id.nav_setting:
                        startActivity(new Intent(MainActivity.this, SettingActivity.class));
                }
            }
        }, 216);
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
         /* 对后退操作进行响应 */
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (!DoubleClickUtil.CC()) {
                Toast.makeText(this, R.string.double_click_exit, LENGTH_SHORT).show();
            } else {
                finish();
            }
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        // 此处需要用工具类进行替代，去除冗余结构
        ArgbEvaluator evaluator = new ArgbEvaluator();
        if (position == 0) {
            mTabLayout.setBackgroundColor(getResources().getColor(R.color.android)); //先设置第0页时还没有滑动时tablayout的颜色
            mToolbar.setBackgroundColor(getResources().getColor(R.color.android));   //先设置第0页时还没有滑动时toolbar的颜色
            int evaluate = (Integer) evaluator.evaluate(positionOffset, getResources().getColor(R.color.android), getResources().getColor(R.color.android));
            mTabLayout.setBackgroundColor(evaluate);//设置背景颜色为算出的两种颜色之间的过渡色
            mTabLayout.setTabTextColors(Color.WHITE, Color.BLACK);
            mToolbar.setBackgroundColor(evaluate);
        }
        if (position == 1) {
            mTabLayout.setBackgroundColor(getResources().getColor(R.color.ios)); //先设置第0页时还没有滑动时tablayout的颜色
            mToolbar.setBackgroundColor(getResources().getColor(R.color.ios));   //先设置第0页时还没有滑动时toolbar的颜色
            int evaluate = (Integer) evaluator.evaluate(positionOffset, getResources().getColor(R.color.ios), getResources().getColor(R.color.ios));
            mTabLayout.setBackgroundColor(evaluate);//设置背景颜色为算出的两种颜色之间的过渡色
            mToolbar.setBackgroundColor(evaluate);
        }
        if (position == 2) {
            mTabLayout.setBackgroundColor(getResources().getColor(R.color.accent)); //先设置第0页时还没有滑动时tablayout的颜色
            mToolbar.setBackgroundColor(getResources().getColor(R.color.cardview_dark_background));   //先设置第0页时还没有滑动时toolbar的颜色
            int evaluate = (Integer) evaluator.evaluate(positionOffset, getResources().getColor(R.color.cardview_light_background), getResources().getColor(R.color.divider));
            mTabLayout.setBackgroundColor(evaluate);//设置背景颜色为算出的两种颜色之间的过渡色
            mToolbar.setBackgroundColor(evaluate);
        }
        if (position == 3) {
            mTabLayout.setBackgroundColor(getResources().getColor(R.color.accent)); //先设置第0页时还没有滑动时tablayout的颜色
            mToolbar.setBackgroundColor(getResources().getColor(R.color.cardview_dark_background));   //先设置第0页时还没有滑动时toolbar的颜色
            int evaluate = (Integer) evaluator.evaluate(positionOffset, getResources().getColor(R.color.cardview_light_background), getResources().getColor(R.color.divider));
            mTabLayout.setBackgroundColor(evaluate);//设置背景颜色为算出的两种颜色之间的过渡色
            mToolbar.setBackgroundColor(evaluate);
        }
        if (position == 4) {
            mTabLayout.setBackgroundColor(getResources().getColor(R.color.accent)); //先设置第0页时还没有滑动时tablayout的颜色
            mToolbar.setBackgroundColor(getResources().getColor(R.color.cardview_dark_background));   //先设置第0页时还没有滑动时toolbar的颜色
            int evaluate = (Integer) evaluator.evaluate(positionOffset, getResources().getColor(R.color.cardview_light_background), getResources().getColor(R.color.divider));
            mTabLayout.setBackgroundColor(evaluate);//设置背景颜色为算出的两种颜色之间的过渡色
            mToolbar.setBackgroundColor(evaluate);
        }
        if (position == 5) {
            mTabLayout.setBackgroundColor(getResources().getColor(R.color.accent)); //先设置第0页时还没有滑动时tablayout的颜色
            mToolbar.setBackgroundColor(getResources().getColor(R.color.cardview_dark_background));   //先设置第0页时还没有滑动时toolbar的颜色
            int evaluate = (Integer) evaluator.evaluate(positionOffset, getResources().getColor(R.color.cardview_light_background), getResources().getColor(R.color.divider));
            mTabLayout.setBackgroundColor(evaluate);//设置背景颜色为算出的两种颜色之间的过渡色
            mToolbar.setBackgroundColor(evaluate);
        }
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


}
