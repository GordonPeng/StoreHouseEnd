package com.sqzn.pl.storehouseend.UI;

import android.content.Intent;

import com.sqzn.pl.storehouseend.R;
import com.sqzn.pl.storehouseend.SHApplication;

import butterknife.ButterKnife;
import butterknife.OnClick;

class GuideActivity extends BaseActivity {

    @Override
    public void initViews() {
        setContentView(R.layout.activity_guide);

        ButterKnife.bind(this);
        SHApplication.getInstance().addActivity(this);




    }
    @OnClick(R.id.btn_comit_guide)
    public void onClik(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
