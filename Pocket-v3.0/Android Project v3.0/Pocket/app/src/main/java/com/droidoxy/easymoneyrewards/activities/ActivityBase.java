package com.droidoxy.easymoneyrewards.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import com.droidoxy.easymoneyrewards.R;
import com.droidoxy.easymoneyrewards.app.App;
import com.droidoxy.easymoneyrewards.constants.Constants;
import com.droidoxy.easymoneyrewards.utils.Dialogs;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by DroidOXY
 */

public class ActivityBase extends AppCompatActivity implements Constants {

    public static final String TAG = "ActivityBase";

    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initpDialog();

        if(!App.getInstance().isConnected()) {

            Dialogs.warningDialog(this, getResources().getString(R.string.title_network_error), getResources().getString(R.string.msg_network_error), false, false, "", getResources().getString(R.string.retry), new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {

                    if(App.getInstance().isConnected()){

                        sweetAlertDialog.dismissWithAnimation();

                    }else{

                        ActivityCompat.finishAffinity(ActivityBase.this);
                        startActivity(new Intent(ActivityBase.this, AppActivity.class));

                    }
                }
            });

        }

    }

    protected void initpDialog() {

        pDialog = new ProgressDialog(this);
        pDialog.setTitle(getString(R.string.processing));
        pDialog.setMessage(getString(R.string.please_wait));
        pDialog.setCancelable(false);
    }

    protected void showpDialog() {

        if (!pDialog.isShowing())
            pDialog.show();
    }

    protected void hidepDialog() {

        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
