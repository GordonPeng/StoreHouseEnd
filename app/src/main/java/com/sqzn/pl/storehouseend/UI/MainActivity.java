package com.sqzn.pl.storehouseend.UI;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.sqzn.pl.storehouseend.Fragment.HomeFragment;
import com.sqzn.pl.storehouseend.Fragment.MineFragment;
import com.sqzn.pl.storehouseend.R;
import com.sqzn.pl.storehouseend.SHApplication;
import com.sqzn.pl.storehouseend.Utils.Util;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    private String mCurrentFragmentTag;

    @BindView(R.id.home)
    TextView home;
    @BindView(R.id.mine)
    TextView mine;
    @BindView(R.id.tv_title)
    TextView tv_title;
    HomeFragment homeFragment = new HomeFragment();
    MineFragment mineFragment = new MineFragment();
    Fragment fragment;
    private Context mContext;

    public static Fragment[] mFragments;
    public static View[] views;

    @Override
    public void initViews() {
        mContext = this;
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //底部菜单和页面切换联动
        mFragments = new Fragment[2];
        mFragments[0] = homeFragment;
        mFragments[1] = mineFragment;
        setFragmentShow(0, "主页");
        home.setSelected(true);
        mine.setSelected(false);

        SHApplication.getInstance().addActivity(this);
    }


    // 底部菜单点击监听
    @OnClick({R.id.home, R.id.mine})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.home:
                setFragmentShow(0, "主页");
                home.setSelected(true);
                mine.setSelected(false);
                break;
            case R.id.mine:
                setFragmentShow(1, "我的");
                home.setSelected(false);
                mine.setSelected(true);
                break;
        }
    }

    // 显示当前fragment
    private void setFragmentShow(int whichIsDefault, String title) {
        if (mFragments[whichIsDefault].isAdded()) {
            getSupportFragmentManager().beginTransaction()
//                    .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                    .hide(mFragments[0])
                    .hide(mFragments[1])
                    .show(mFragments[whichIsDefault])
                    .commit();
        } else {
            getSupportFragmentManager().beginTransaction()
//                    .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                    .hide(mFragments[0])
                    .hide(mFragments[1])
                    .add(R.id.main_fl, mFragments[whichIsDefault])
                    .show(mFragments[whichIsDefault])
                    .commit();

        }
        tv_title.setText(title);
    }

    //双击退出程序
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Util.BackToLanuch(mContext);
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }


}
