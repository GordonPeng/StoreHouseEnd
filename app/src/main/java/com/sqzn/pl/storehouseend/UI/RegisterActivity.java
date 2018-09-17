package com.sqzn.pl.storehouseend.UI;

import com.sqzn.pl.storehouseend.R;
import com.sqzn.pl.storehouseend.SHApplication;

import butterknife.ButterKnife;

public class RegisterActivity extends BaseActivity {
    @Override
    public void initViews() {
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        SHApplication.getInstance().addActivity(this);

    }
}
