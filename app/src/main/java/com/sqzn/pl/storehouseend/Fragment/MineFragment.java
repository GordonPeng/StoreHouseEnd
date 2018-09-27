package com.sqzn.pl.storehouseend.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sqzn.pl.storehouseend.R;
import com.sqzn.pl.storehouseend.SHApplication;
import com.sqzn.pl.storehouseend.UI.LoginActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class MineFragment extends BaseFragment {
    private View mView;
    private Context mContext;

    @Override
    protected View CreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_mine, null);
        ButterKnife.bind(this, mView);
        return mView;
    }

    @Override
    protected void setupViews() {
        mContext = getActivity();

    }

    @OnClick(R.id.btn_exit)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_exit:
                Log.d(">>>", "退出登录");
                Intent intent = new Intent(mContext, LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
                break;
        }
    }


}
