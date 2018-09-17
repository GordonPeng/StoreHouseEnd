package com.sqzn.pl.storehouseend.Fragment;

import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.journeyapps.barcodescanner.CaptureActivity;
import com.sqzn.pl.storehouseend.R;
import com.sqzn.pl.storehouseend.UI.MainActivity;
import com.sqzn.pl.storehouseend.UI.ScanActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.google.zxing.integration.android.IntentIntegrator.REQUEST_CODE;

public class HomeFragment extends BaseFragment {
    private View mView;


    @Override
    protected View CreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_home, null);
        ButterKnife.bind(this, mView);
        return mView;
    }

    @Override
    protected void setupViews() {
    }

    @OnClick({R.id.ll_store_in, R.id.ll_store_out, R.id.ll_store_count, R.id.ll_store_scan})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_store_in:
                break;
            case R.id.ll_store_out:
                break;
            case R.id.ll_store_count:
                break;
            case R.id.ll_store_scan:
                Intent intent = new Intent(getActivity(), ScanActivity.class);
                startActivity(intent);
                break;
        }
    }

}
