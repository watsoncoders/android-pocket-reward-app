package com.droidoxy.easymoneyrewards.util;

import android.content.Context;
import android.content.res.TypedArray;

/**
 * Utility class
 *
 * Created by yash on 10/9/2017.
 */
public class UtilsMiscellaneous
{

    public static int getThemeAttributeDimensionSize(Context context, int attr)
    {
        TypedArray a = null;
        try
        {
            a = context.getTheme().obtainStyledAttributes(new int[] { attr });
            return a.getDimensionPixelSize(0, 0);
        }
        finally
        {
            if(a != null)
            {
                a.recycle();
            }
        }
    }

}
