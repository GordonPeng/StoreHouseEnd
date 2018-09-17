package com.sqzn.pl.storehouseend.UI;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.CaptureActivity;
import com.sqzn.pl.storehouseend.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ScanActivity extends BaseActivity {
    @BindView(R.id.tv_scan_result)
    TextView tv_scan_result;
    public static final int REQUEST_CODE_SCAN = 101;
    public static final int PHOTO_REQUEST_SAOYISAO = 100;
    private String TAG = "TT";

    @Override
    public void initViews() {
        setContentView(R.layout.activity_scan);
        ButterKnife.bind(this);


    }

    @OnClick(R.id.tv_scan_result)
    public void onClick(View view) {
        // 取得相机权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, PHOTO_REQUEST_SAOYISAO);
        } else {
            // 权限已经取得的情况下调用
            // 调用扫一扫


        }

    }

    /**
     * 重写申请权限操作返回值的方法
     **/
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PHOTO_REQUEST_SAOYISAO:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 权限申请成功，扫一扫
                    Intent intent = new Intent(ScanActivity.this,
                            CaptureActivity.class);
                    startActivityForResult(intent, REQUEST_CODE_SCAN);
                } else {
                    Toast.makeText(this, "无相机调用权限，扫一扫功能无法使用，", Toast.LENGTH_SHORT).show();
                }
        }
    }

    /**
     * 重写取得活动返回值的方法
     **/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        // 扫一扫返回值


    }

}
