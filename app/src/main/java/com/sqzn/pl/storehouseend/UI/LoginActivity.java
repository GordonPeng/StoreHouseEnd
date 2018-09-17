package com.sqzn.pl.storehouseend.UI;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sqzn.pl.storehouseend.R;
import com.sqzn.pl.storehouseend.SHApplication;
import com.sqzn.pl.storehouseend.Views.ClearEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {
    @BindView(R.id.et_account)
    ClearEditText account;
    @BindView(R.id.et_password)
    ClearEditText password;
    @BindView(R.id.et_vercode)
    ClearEditText vercode;
    @BindView(R.id.ll_vercode)
    LinearLayout ll_vercode;
    @BindView(R.id.tv_login_by_method)
    TextView tv_login_by_method;

    private int loginMethood = 1;  /*1:密码登录
                                    *2:验证码登录
                                    */

    @Override
    public void initViews() {
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        SHApplication.getInstance().addActivity(this);


    }


    @OnClick({R.id.tv_to_register, R.id.tv_login_by_method, R.id.btn_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_to_register:
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_login:
                Intent intent2 = new Intent(this, MainActivity.class);
                startActivity(intent2);
                break;
            case R.id.tv_login_by_method:
                if (loginMethood ==1){
                    loginMethood=2;
                    password.setVisibility(View.GONE);
                    ll_vercode.setVisibility(View.VISIBLE);
                    tv_login_by_method.setText("密码登录");
                }else {
                    loginMethood=1;
                    password.setVisibility(View.VISIBLE);
                    ll_vercode.setVisibility(View.GONE);
                    tv_login_by_method.setText("验证码登录");

                }
                break;

        }
    }


}
