package com.sqzn.pl.storehouseend.UI;

import com.sqzn.pl.storehouseend.R;

import butterknife.ButterKnife;

public class RegisterActivity extends BaseActivity {
    @Override
    public void initViews() {
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }
}
