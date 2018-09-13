package com.sqzn.pl.storehouseend.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.sqzn.pl.storehouseend.R;
import butterknife.ButterKnife;

public class MineFragment extends BaseFragment{
    private View mView;

    @Override
    protected View CreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_mine, null);
        ButterKnife.bind(this, mView);
        return mView;
    }

    @Override
    protected void setupViews() {

    }
}
