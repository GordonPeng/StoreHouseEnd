package com.sqzn.pl.storehouseend.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class Util {
    public static SharedPreferences getSharedPreferences(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        return preferences;
    }
}
