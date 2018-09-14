package com.sqzn.pl.storehouseend.UI;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;

import com.sqzn.pl.storehouseend.R;
import com.sqzn.pl.storehouseend.Utils.Util;

import butterknife.ButterKnife;

public class LogoActivity extends BaseActivity {

    private Handler handler;
    private boolean backEnable;
    private Context mContext;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private int enterTag;//   初始0：LogoActivity-->Guide-->Login-->main
                         //      1:

    @Override
    public void initViews() {

        setContentView(R.layout.activity_logo);

        ButterKnife.bind(this);
        preferences = Util.getSharedPreferences(mContext);
        editor = preferences.edit();
        enterTag = preferences.getInt("enterTag", 0);
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                backEnable = true;
                if (enterTag ==0) {
                    Intent intent = new Intent(mContext, GuideActivity.class);
                    mContext.startActivity(intent);
                    editor.putInt("enterTag", ++enterTag).apply();
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    LogoActivity.this.finish();
                } else {
                    gotoMain();

                }
            }
        }, 1000);
    }

    private void gotoMain() {

        String phone = preferences.getString("phone", null);
        if (phone != null) {
            Intent intent = new Intent(mContext, MainActivity.class);
//            intent.putExtra("sharedStr", sharedStr);
            editor.putInt("enterTag", ++enterTag).commit();
            mContext.startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            LogoActivity.this.finish();
        } else {
            Intent intent = new Intent(mContext, LoginActivity.class);
            editor.putInt("enterTag", ++enterTag).commit();
            mContext.startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            LogoActivity.this.finish();
        }
    }
}
