package com.droidoxy.easymoneyrewards.util;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * This utility class is for device related stuff.
 *
 * Created by yash on 10/9/2017.
 */

public class UtilsDevice
{
    public static int getScreenWidth(Context context)
    {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();

        return metrics.widthPixels;
    }
}
