package com.woof.gankist.ui.setting;

import android.os.Bundle;

import com.woof.gankist.R;
import com.woof.gankist.base.ToolBarActivity;

/**
 * Created by Woof on 3/27/2017.
 */

public class SettingActivity extends ToolBarActivity {
    @Override
    protected int provideContentViewId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getToolbar().setTitle("设置");
        getFragmentManager().beginTransaction().replace(R.id.framelayout,
                new SettingFragment()).commit();
    }

    @Override
    public boolean canBack() {
        return true;
    }
}
