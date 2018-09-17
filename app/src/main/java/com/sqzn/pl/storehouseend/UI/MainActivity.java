package com.sqzn.pl.storehouseend.UI;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
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
    private FragmentTransaction fragmentTransaction;
    private HomeFragment homeFragment;
    private MineFragment mineFragment;
    private Context mContext;


    @Override
    public void initViews() {
        mContext = this;
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        //底部菜单和页面切换联动
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        homeFragment = new HomeFragment();
        mineFragment = new MineFragment();
        fragmentTransaction.add(R.id.main_fl, homeFragment, "mine")
                .add(R.id.main_fl, mineFragment, "mine")
                .hide(mineFragment).commit();
        mCurrentFragmentTag = "home";
        home.setSelected(true);


        SHApplication.getInstance().addActivity(this);
    }


    // 底部菜单点击监听
    @OnClick({R.id.home, R.id.mine})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.home:
                getSupportFragmentManager().beginTransaction().show(homeFragment).hide(mineFragment).commit();
                mCurrentFragmentTag = "home";
                home.setSelected(true);
                mine.setSelected(false);
                break;
            case R.id.mine:
                getSupportFragmentManager().beginTransaction().show(mineFragment).hide(homeFragment).commit();
                mCurrentFragmentTag = "mine";
                home.setSelected(false);
                mine.setSelected(true);
                break;
        }
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
