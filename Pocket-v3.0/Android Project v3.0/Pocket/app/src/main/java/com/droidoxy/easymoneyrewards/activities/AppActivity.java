package com.droidoxy.easymoneyrewards.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.HashMap;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.util.Map;

import com.droidoxy.easymoneyrewards.R;
import com.droidoxy.easymoneyrewards.app.App;
import com.droidoxy.easymoneyrewards.utils.AppUtils;
import com.droidoxy.easymoneyrewards.utils.CustomRequest;
import com.droidoxy.easymoneyrewards.utils.Dialogs;

import cn.pedant.SweetAlert.SweetAlertDialog;
import static com.droidoxy.easymoneyrewards.Config.ENABLE_APP_INTRO;

/**
 * Created by DroidOXY
 */

public class AppActivity extends ActivityBase {

    Button loginBtn, signupBtn;
    RelativeLayout loadingScreen;
    LinearLayout contentScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);

        contentScreen = (LinearLayout) findViewById(R.id.contentScreen);
        loadingScreen = (RelativeLayout) findViewById(R.id.loadingScreen);

        if (App.getInstance().get("isFirstTimeLaunch",true) && ENABLE_APP_INTRO) {

            startActivity(new Intent(this, IntroActivity.class));
            finish();
        }

        loginBtn = (Button) findViewById(R.id.loginBtn);
        signupBtn = (Button) findViewById(R.id.signupBtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(AppActivity.this, LoginActivity.class));
            }
        });

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(AppActivity.this, SignupActivity.class));

            }
        });

        App.getInstance().getCountryCode();
    }

    @Override
    protected void onStart() {

        super.onStart();

        if(!App.getInstance().isConnected()) {

            showLoadingScreen();

            Dialogs.warningDialog(this, getResources().getString(R.string.title_network_error), getResources().getString(R.string.msg_network_error), false, false, "", getResources().getString(R.string.retry), new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    onStart();
                }
            });

        }else if(App.getInstance().getId() != 0) {

            showLoadingScreen();

            CustomRequest jsonReq = new CustomRequest(Request.Method.POST, METHOD_ACCOUNT_AUTHORIZE, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            if (App.getInstance().authorize(response)) {

                                if (App.getInstance().getState() == ACCOUNT_STATE_ENABLED) {

                                    // AppInit();
                                    ActivityCompat.finishAffinity(AppActivity.this);
                                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(i);

                                } else {

                                    showContentScreen();
                                    App.getInstance().logout();
                                }

                            } else if(App.getInstance().getErrorCode() == 699 || App.getInstance().getErrorCode() == 999){

                                Dialogs.validationError(AppActivity.this,App.getInstance().getErrorCode());

                            } else if(App.getInstance().getErrorCode() == 799){

                                Dialogs.warningDialog(AppActivity.this, getResources().getString(R.string.update_app), getResources().getString(R.string.update_app_description), false, false, "", getResources().getString(R.string.update), new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        AppUtils.gotoMarket(AppActivity.this);
                                    }
                                });

                            } else {

                                showContentScreen();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    showContentScreen();
                }
            }) {

                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("data", App.getInstance().getAuthorize());

                    return params;
                }
            };

            App.getInstance().addToRequestQueue(jsonReq);

        } else {

            showContentScreen();

        }
    }

    public void showContentScreen() {

        loadingScreen.setVisibility(View.GONE);

        contentScreen.setVisibility(View.VISIBLE);
    }

    public void showLoadingScreen() {

        contentScreen.setVisibility(View.GONE);

        loadingScreen.setVisibility(View.VISIBLE);
    }
}
