package com.droidoxy.easymoneyrewards.utils;

import android.content.Context;
import android.content.res.TypedArray;

/**
 * Created by DroidOXY
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
