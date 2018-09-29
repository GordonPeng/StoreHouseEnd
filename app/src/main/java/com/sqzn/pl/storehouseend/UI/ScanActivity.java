package com.sqzn.pl.storehouseend.UI;

import android.Manifest;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.sqzn.pl.storehouseend.R;
import com.sqzn.pl.storehouseend.Utils.ImageUtil;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ScanActivity extends BaseActivity {
    @BindView(R.id.tv_scan_result)
    TextView tv_scan_result;
    public static final int REQUEST_CODE_SCAN = 101;
    public static final int PHOTO_REQUEST_SAOYISAO = 100;
    private String TAG = "TT";
    /**
     * 扫描跳转Activity RequestCode
     */
    public static final int REQUEST_CODE = 111;
    /**
     * 选择系统图片Request Code
     */
    public static final int REQUEST_IMAGE = 112;

    @Override
    public void initViews() {
        setContentView(R.layout.activity_scan);
        ButterKnife.bind(this);


    }

    @OnClick(R.id.tv_scan_result)
    public void onClick(View view) {

//        startActivityForResult(intent, REQUEST_CODE);
//
//        // 取得相机权限
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            requestPermissions(new String[]{Manifest.permission.CAMERA}, PHOTO_REQUEST_SAOYISAO);
//        } else {
//            // 权限已经取得的情况下调用
//            // 调用扫一扫
//
//        }

    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        /**
//         * 处理二维码扫描结果
//         */
//        if (requestCode == REQUEST_CODE) {
//            //处理扫描结果（在界面上显示）
//            if (null != data) {
//                Bundle bundle = data.getExtras();
//                if (bundle == null) {
//                    return;
//                }
//                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
//                    String result = bundle.getString(CodeUtils.RESULT_STRING);
//                    Toast.makeText(this, "解析结果:" + result, Toast.LENGTH_LONG).show();
//                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
//                    Toast.makeText(this, "解析二维码失败", Toast.LENGTH_LONG).show();
//                }
//            }
//        }
//
//        /**
//         * 选择系统图片并解析
//         */
//        else if (requestCode == REQUEST_IMAGE) {
//            if (data != null) {
//                Uri uri = data.getData();
//                try {
//                    CodeUtils.analyzeBitmap(ImageUtil.getImageAbsolutePath(this, uri), new CodeUtils.AnalyzeCallback() {
//                        @Override
//                        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
//                            Toast.makeText(ScanActivity.this, "解析结果:" + result, Toast.LENGTH_LONG).show();
//                        }
//
//                        @Override
//                        public void onAnalyzeFailed() {
//                            Toast.makeText(ScanActivity.this, "解析二维码失败", Toast.LENGTH_LONG).show();
//                        }
//                    });
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
////        else if (requestCode == REQUEST_CAMERA_PERM) {
////            Toast.makeText(this, "从设置页面返回...", Toast.LENGTH_SHORT)
////                    .show();
////        }
//    }

}
