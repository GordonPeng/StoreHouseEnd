package com.sqzn.pl.storehouseend.UI;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.zxing.common.StringUtils;
import com.sqzn.pl.storehouseend.MyConst;
import com.sqzn.pl.storehouseend.R;
import com.sqzn.pl.storehouseend.Utils.HttpUtil;
import com.sqzn.pl.storehouseend.Utils.InputUtil;
import com.sqzn.pl.storehouseend.Utils.StringUtil;
import com.sqzn.pl.storehouseend.Utils.Util;
import com.sqzn.pl.storehouseend.Views.ClearEditText;
import com.sqzn.pl.storehouseend.model.Param;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;

public class LoginActivity extends BaseActivity {
    @BindView(R.id.et_account)
    ClearEditText account;
    @BindView(R.id.et_password)
    ClearEditText password;
    @BindView(R.id.et_login_vercode)
    ClearEditText verifycode;
    @BindView(R.id.ll_vercode)
    LinearLayout ll_vercode;
    @BindView(R.id.tv_login_by_method)
    TextView tv_login_by_method;
    @BindView(R.id.getcode_login)
    TextView getcode;
    private int loginMethood = 1;
    public static final int LOGINBYPASSWORD = 1;//1:密码登录
    public static final int LOGINBYVERCODE = 2;//2:验证码登录
    private Dialog dialog;
    private Context mContext;
    private String macAddress;
    private int code;
    private int GRANT_CODE = 101;
    private String[] PHONE_PERMISSION = {Manifest.permission.READ_PHONE_STATE};
    private MyCountTime mCountTime;
    private long mills;
    private String IMEI = "";

    @Override
    public void initViews() {
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mContext = this;
    }


    @SuppressLint("WrongConstant")
    @OnClick({R.id.tv_to_register, R.id.tv_login_by_method, R.id.btn_login, R.id.getcode_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_to_register:
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.getcode_login:
                if (StringUtil.isEmpty(account.getText().toString()) || account.getText().length() != 11) {
                    Util.showToast(mContext, "请输入正确的手机号");
                    return;
                }
                mCountTime = new MyCountTime(60000, 1000);
                getcode.setBackgroundColor(getResources().getColor(R.color.milk));
                mCountTime.start();
                getcode.setClickable(false);
//                getVerifyCode();
                break;
            case R.id.tv_login_by_method:
                if (loginMethood == LOGINBYPASSWORD) {
                    loginMethood = LOGINBYVERCODE;
                    password.setVisibility(View.GONE);
                    ll_vercode.setVisibility(View.VISIBLE);
                    tv_login_by_method.setText("密码登录");
                } else if (loginMethood == LOGINBYVERCODE) {
                    loginMethood = LOGINBYPASSWORD;
                    password.setVisibility(View.VISIBLE);
                    ll_vercode.setVisibility(View.GONE);
                    tv_login_by_method.setText("验证码登录");
                }
                break;
            case R.id.btn_login:
//                if (TextUtils.isEmpty(IMEI)) {
//                    ActivityCompat.requestPermissions(this, PHONE_PERMISSION, GRANT_CODE);
//                    return;
//                }
                if (StringUtil.isEmpty(account.getText().toString()) || account.getText().length() != 11) {
                    Util.showToast(mContext, "请输入正确的11位手机号");
                    return;
                }
                if (loginMethood == LOGINBYPASSWORD) {
                    if (StringUtil.isEmpty(password.getText().toString()) || password.getText().length() != 8) {
                        Util.showToast(mContext, "请输入正确的8位密码");
                        return;
                    }
                } else if (loginMethood == LOGINBYVERCODE) {
                    if (StringUtil.isEmpty(verifycode.getText().toString()) || verifycode.getText().length() != 6) {
                        Util.showToast(mContext, "请输入正确的6位验证码");
                        return;
                    }
                } else {
                    Util.showToast(mContext, "检查登录选项");
                }
                break;
        }
    }

    public void submit() {
        InputUtil.hideKeyboard((LoginActivity) mContext);
        dialog.show();
        ArrayList<Param> param = new ArrayList<Param>();
        param.add(new Param("mobile", account.getText().toString()));
        param.add(new Param("ver_code", verifycode.getText().toString()));
//        param.add(new Param("ji_id", ));
        param.add(new Param("device", "0"));//安卓设备
        param.add(new Param("imei", IMEI));
        param.add(new Param("mac", macAddress));
        HttpUtil.postAsyn(MyConst.userLogin,
                new HttpUtil.ResultCallback<JSONObject>() {
                    @Override
                    public void onError(Request request, Exception e) {
                        dialog.dismiss();
//                        code = 1;
//                        succeedTag = 1;
//                        gotoMain(code, succeedTag);
                    }

                    @Override
                    public void onResponse(JSONObject result) {
                        dialog.dismiss();
                        try {
                            code = result.getInt("code");
                            if (code == 0) {
//                                preferences = Util.getSharedPreferences(mContext);
//                                SharedPreferences.Editor editor = preferences.edit();
//                                editor.putString("phone", account.getText().toString());
//                                editor.putString("idcard", result.getString("result"));
//                                editor.apply();
//
//                                InputUtil.hideKeyboard((LoginActivity) mContext);
//                                Intent intent = new Intent(mContext, MainActivity.class);
//                                Util.StartActivity(intent);
                            } else {
//                                loginReceiveTip = result.getString("result");
//                                Util.showToast(mContext, loginReceiveTip);
                            }
//                            gotoMain(code, succeedTag);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, param);
    }

    private void getVerifyCode() {
//        final Dialog dialog = DialogFactory.createProgressDialog(mContext, null);
//        dialog.show();
        ArrayList<Param> param = new ArrayList<Param>();
        param.add(new Param("mobile", account.getText().toString()));
        param.add(new Param("type", "2"));
        HttpUtil.postAsyn(MyConst.getVerifyCode,
                new HttpUtil.ResultCallback<JSONObject>() {
                    @Override
                    public void onError(Request request, Exception e) {
                        dialog.dismiss();
                    }

                    @Override
                    public void onResponse(JSONObject result) {
                        dialog.dismiss();
                        try {
                            int code = result.getInt("code");
                            Log.e("LoginReceiveCode", code + "");
                            if (code == 12) {
                                mCountTime.cancel();
                                mCountTime = null;
                                getcode.setClickable(true);
                                getcode.setText(getResources().getString(
                                        R.string.getverifycode));
                            }
                            Util.showToast(mContext, result.getString("result"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, param);
    }

    /* 定义一个倒计时的内部类   用于验证码倒计时  */
    class MyCountTime extends CountDownTimer {
        public MyCountTime(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            getcode.setClickable(true);
            getcode.setBackgroundColor(getResources().getColor(R.color.yellow));
            getcode.setText(getResources().getString(
                    R.string.getverifycode));
            mCountTime = null;
        }

        @Override
        public void onTick(long millisUntilFinished) {
            mills = millisUntilFinished;
            getcode.setText("     " + millisUntilFinished / 1000
                    + "s" + "    ");
        }
    }
}
