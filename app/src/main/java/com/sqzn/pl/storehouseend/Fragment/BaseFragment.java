package com.sqzn.pl.storehouseend.Fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment implements View.OnClickListener{

    protected String[] WRITE_PERMISSION = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
    protected static final int PERMISSION_REQUEST_CODE = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return CreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initBundle();
        setupViews();
    }

    protected abstract View CreateView(LayoutInflater inflater,
                                       ViewGroup container, Bundle savedInstanceState);

    protected abstract void setupViews();

    protected void initDate() {

    }

    protected void initBundle() {

    }


    @Override
    public  void onClick(View arg0){}


    public void initResult(Bundle bundle) {
    }

    public boolean back() {
        return true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    /**
     * 检查、申请权限
     * true 为有权限  false为没有权限
     */
    protected boolean checkPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        } else {
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                return false;
            } else {
                return true;
            }
        }
    }
}
