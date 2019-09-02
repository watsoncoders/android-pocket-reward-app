package com.droidoxy.easymoneyrewards.utils;

import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.widget.Toast;

import com.droidoxy.easymoneyrewards.R;

import static android.content.Context.CLIPBOARD_SERVICE;

/**
 * Created by DroidOXY
 */

public class AppUtils
{
    public static int getScreenWidth(Context context)
    {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();

        return metrics.widthPixels;
    }

    public static void copytoClipBoard(Context ctx, String data){

        ClipboardManager clipboard = (ClipboardManager) ctx.getSystemService(CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("label", data);
        clipboard.setPrimaryClip(clip);
    }

    public static void toastLong(Context ctx, String data){

        Toast.makeText(ctx, data, Toast.LENGTH_LONG).show();
    }

    public static void toastShort(Context ctx, String data){

        Toast.makeText(ctx, data, Toast.LENGTH_SHORT).show();
    }

    public static void parse(Context ctx, String data){

        Uri uri = Uri.parse(data);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        ctx.startActivity(intent);

    }

    public static void shareApplication(Context ctx) {
        try {

            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, ctx.getString(R.string.app_name));
            String sAux = ctx.getString(R.string.share_text) + "\n\n";
            sAux = sAux + "https://play.google.com/store/apps/details?id="+ctx.getPackageName();
            i.putExtra(Intent.EXTRA_TEXT, sAux);
            ctx.startActivity(Intent.createChooser(i, ctx.getString(R.string.choose_one)));

        }
        catch(Exception e){
            //e.toString();
        }
    }

    public static void gotoMarket(Context ctx) {
        Uri uri = Uri.parse("market://details?id=" + ctx.getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);

        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_NEW_DOCUMENT | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            ctx.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            ctx.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + ctx.getPackageName())));
        }
    }

}
