package com.sqzn.pl.storehouseend.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.sqzn.pl.storehouseend.R;
import com.sqzn.pl.storehouseend.SHApplication;

public class Util {
    private static long mExitTime = 0;

    public static SharedPreferences getSharedPreferences(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        return preferences;
    }

    public static void BackToLanuch(Context mContext) {
        // TODO Auto-generated method stub
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            Util.showToast(mContext, "再按一次退出程序");
            mExitTime = System.currentTimeMillis();
        } else {


//            Glide.get(mContext).clearMemory();//清除内存缓存
            SHApplication.getInstance().exit();
        }
    }


    public static void showToast(Context context, String msg) {
        Toast toast = new Toast(context);
        View toastRoot = LayoutInflater.from(context).inflate(R.layout.my_toast, null);
        toast.setView(toastRoot);
        TextView tv = (TextView) toastRoot.findViewById(R.id.my_toast_info);
        tv.setText(msg);
        toast.show();
    }



}
