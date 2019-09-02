package com.droidoxy.easymoneyrewards;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.adscendmedia.sdk.ui.AdscendMediaWrapper;
import com.adscendmedia.sdk.ui.OffersActivity;
import com.adscendmedia.sdk.util.CompletedOfferRequestListener;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.droidoxy.easymoneyrewards.adapter.ViewPagerAdapter;
import com.droidoxy.easymoneyrewards.app.App;
import com.droidoxy.easymoneyrewards.views.ScrimInsetsFrameLayout;
import com.droidoxy.easymoneyrewards.sliding.SlidingTabLayout;
import com.droidoxy.easymoneyrewards.util.UtilsDevice;
import com.droidoxy.easymoneyrewards.util.UtilsMiscellaneous;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.iid.FirebaseInstanceId;
import com.nativex.monetization.MonetizationManager;
import com.nativex.monetization.business.reward.Reward;
import com.nativex.monetization.communication.RedeemRewardData;
import com.nativex.monetization.enums.AdEvent;
import com.nativex.monetization.enums.NativeXAdPlacement;
import com.nativex.monetization.listeners.OnAdEventV2;
import com.nativex.monetization.listeners.RewardListener;
import com.nativex.monetization.listeners.SessionListener;
import com.nativex.monetization.mraid.AdInfo;
import com.offertoro.sdk.OTOfferWallSettings;
import com.offertoro.sdk.interfaces.OfferWallListener;
import com.offertoro.sdk.sdk.OffersInit;
import com.playerize.superrewards.SRUserPoints;
import com.playerize.superrewards.SuperRewards;
import com.supersonic.adapters.supersonicads.SupersonicConfig;
import com.supersonic.mediationsdk.sdk.OfferwallListener;
import com.supersonic.mediationsdk.sdk.RewardedVideoListener;
import com.supersonic.mediationsdk.sdk.Supersonic;
import com.supersonic.mediationsdk.sdk.SupersonicFactory;
import com.supersonicads.sdk.agent.SupersonicAdsAdvertiserAgent;
import net.adxmi.android.AdManager;
import net.adxmi.android.os.OffersManager;
import net.adxmi.android.os.PointsChangeNotify;
import net.adxmi.android.os.PointsEarnNotify;
import net.adxmi.android.os.PointsManager;
import net.adxmi.android.video.VideoAdManager;
import net.adxmi.android.video.VideoAdRequestListener;
import net.adxmi.android.video.VideoRewardsListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import com.white.mobi.sdk.IRewardsListener;
import com.white.mobi.sdk.WMManager;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import dmax.dialog.SpotsDialog;
import android.util.Log;
import net.adxmi.android.os.EarnPointsOrderInfo;
import net.adxmi.android.os.EarnPointsOrderList;
import com.droidoxy.easymoneyrewards.constants.Constants;
import com.google.android.gms.ads.AdListener;
import com.supersonic.mediationsdk.logger.SupersonicError;
import com.supersonic.mediationsdk.model.Placement;
import com.tjeannin.apprate.AppRate;
import android.content.Context;
import android.content.SharedPreferences.Editor;

/**
 * Created by yashDev on 06/10/2017.
 */
public class MainActivityAwesome extends ActionBarActivity implements PointsChangeNotify,
        PointsEarnNotify, VideoRewardsListener, RewardedVideoListener, OfferwallListener, OnAdEventV2, SessionListener, RewardListener {

    // Declaring Your View and Variables
    Toolbar toolbar;
    ViewPager pager;
    ViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    CharSequence Titles[]={"Home"};
    int Numboftabs =1;
    private final static int REQUEST_READ_PHONE_STATE = 1;
    private InterstitialAd interstitial;
    public Timer AdTimer;
    public boolean doubleBackToExitPressedOnce = false;
    private Supersonic mSupersonicInstance;
    private final String TAG = "MainActivity";
    ListView listView;
    private  Menu menu;
    private AlertDialog progressDialog;
    private PrefManager prefManager;
    public static final String PREFS_NAME = "MyApp_Settings";
    private TextView pointTextView;
    int totalPoints;
    private GoogleApiClient client;
    static final int PLAY_REWARDED_VIDEO = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_awesome);

        init_slider();
        init_navigator();
        initView();
        init_admob();

    }

    void init_admob(){

        // -------------------------------  AdMob Banner ------------------------------------------------------------
        AdView adView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().setRequestAgent("android_studio:ad_template").build();
        adView.loadAd(adRequest);

        // -------------------------------- AdMob Interstitial ----------------------------
        // Prepare the Interstitial Ad
        interstitial = new InterstitialAd(MainActivityAwesome.this);
        // Insert the Ad Unit ID
        interstitial.setAdUnitId(getString(R.string.interstitial_ad_unit_id));

        // Load ads into Interstitial Ads
        interstitial.loadAd(adRequest);

        AdTimer = new Timer();

        // Prepare an Interstitial Ad Listener
        interstitial.setAdListener(new AdListener() {
            public void onAdLoaded() {
                // Call displayInterstitial() function with timer
                if (AdTimer != null) {
                    AdTimer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    displayInterstitial();
                                }
                            });
                        }
                    }, Integer.parseInt(getString(R.string.admob_interstitial_delay)));
                }
            }
        });
    }

    private void initView() {
        invalidateOptionsMenu();
        TextView username = (TextView) findViewById(R.id.nav_drawer_display_name);
        TextView email = (TextView) findViewById(R.id.nav_drawer_display_email);

        username.setText(App.getInstance().getFullname());
        email.setText(App.getInstance().getEmail());

        // Instructions
        FrameLayout instructions = (FrameLayout) findViewById(R.id.instructions);
        instructions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent instructions = new Intent(getBaseContext(), InstructionsActivity.class);
                startActivity(instructions);
                mDrawerLayout.closeDrawers();
            }
        });

        // Refer & Earn
        FrameLayout refer = (FrameLayout) findViewById(R.id.refer);
        refer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent refer = new Intent(getBaseContext(), ReferActivity.class);
                startActivity(refer);
                mDrawerLayout.closeDrawers();
            }
        });

        // Redeem
        FrameLayout redeem = (FrameLayout) findViewById(R.id.redeem);
        redeem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent redeem = new Intent(getBaseContext(), RedeemActivity.class);
                startActivity(redeem);
                mDrawerLayout.closeDrawers();
            }
        });

        // About
        FrameLayout about = (FrameLayout) findViewById(R.id.about);
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent about = new Intent(getBaseContext(), AboutActivity.class);
                startActivity(about);
                mDrawerLayout.closeDrawers();
            }
        });

        // Share
        FrameLayout share = (FrameLayout) findViewById(R.id.nav_share);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareURL();
                mDrawerLayout.closeDrawers();
            }
        });

        // Rate This App
        FrameLayout rate = (FrameLayout) findViewById(R.id.rate_this_app);
        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rateThisApp();
                mDrawerLayout.closeDrawers();
            }
        });

        // Privacy Policy
        FrameLayout policy = (FrameLayout) findViewById(R.id.policy);
        policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parseURL(Config.policy_Url);
                mDrawerLayout.closeDrawers();
            }
        });

        // Contact Us
        FrameLayout contact = (FrameLayout) findViewById(R.id.contact);
        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parseURL(Config.contact_Url);
                mDrawerLayout.closeDrawers();
            }
        });

        init_v1_8();

    }

    // Main fuctions
    public void daily_checkin(){
        daily_award(Config.daily_reward,getString(R.string.daily_reward));
    }

    public void adxmi_offerwall(){
        if (hasGetReadPhoneStatePermission()) {
            OffersManager.getInstance(MainActivityAwesome.this).showOffersWall();
        } else {
            requestReadPhoneStatePermission();
        }
    }

    public void offer_toro_offerwall(){

        OffersInit.getInstance().showOfferWall(MainActivityAwesome.this);
    }

    public void super_sonic_offerwall(){

        if (mSupersonicInstance.isOfferwallAvailable()) {
            mSupersonicInstance.showOfferwall();
        } else {
            showshort(getString(R.string.try_after));
        }
    }

    public void super_sonic_videos(){

        if (mSupersonicInstance.isRewardedVideoAvailable())
        {
            mSupersonicInstance.showRewardedVideo();
        } else {
            showshort(getString(R.string.no_videos_available));
        }
    }

    public void super_rewards_offerwall(){

        prefManager = new PrefManager(MainActivityAwesome.this);

        SharedPreferences settings2 = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String userId = settings2.getString("user_id", App.getInstance().getUsername());

        SuperRewards sr = new SuperRewards(getResources(), "com.playerize.awesomeapp");
        sr.showOffers(MainActivityAwesome.this, Config.appHash, userId);

    }

    public void adscend_offerwall(){
        prefManager = new PrefManager(MainActivityAwesome.this);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String subId1 = settings.getString("user_id", App.getInstance().getUsername());
        Intent Adscend_offer = OffersActivity.getIntentForOfferWall(MainActivityAwesome.this, Config.pubId, Config.adwallId, subId1);
        startActivity(Adscend_offer);

    }

    public void white_mobi_offerwall(){

        WMManager.showOfferWall(Config.whitemobi_key);
    }

    public void nativex_offerwall(){
        if( MonetizationManager.isAdReady(NativeXAdPlacement.Main_Menu_Screen) ) {

            MonetizationManager.showReadyAd(MainActivityAwesome.this, NativeXAdPlacement.Main_Menu_Screen, null);
        }
        else {
            ;
            showshort(getString(R.string.try_after));
        }
    }

    public void nativex_videos(){
        //show the NativeX Videos
        if( MonetizationManager.isAdReady(NativeXAdPlacement.Level_Failed) ) {

            MonetizationManager.showReadyAd(MainActivityAwesome.this, NativeXAdPlacement.Level_Failed, null);
        }
        else {
            showshort(getString(R.string.no_videos_available));
        }

    }

    @Override
    public void onStart() {
        super.onStart();

        getCompletedTransactions();

    }
    @Override
    protected void onStop() {
        super.onStop();

    }
    @Override
    protected void onPause() {
        super.onPause();
        WMManager.onPause();

    }
    @Override
    protected void onResume() {
        super.onResume();
        WMManager.onResume(this);
        MonetizationManager.createSession(getApplicationContext(), Config.NativeX_AppId, this);
        MonetizationManager.setRewardListener(this);

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroyOfferWall();
        destroyVideoAd();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.points:
                Intent redeem = new Intent(getBaseContext(), RedeemActivity.class);
                startActivity(redeem);
                return true;
            case R.id.sync:

                updateUserPoints();

                /**
                 Intent intent = getIntent();
                 finish();
                 startActivity(intent);
                 **/

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void setOptionTitle(int id, String title)
    {
        MenuItem item = menu.findItem(id);
        item.setTitle(title);
    }
    @Override
    public boolean onPrepareOptionsMenu(final Menu menu) {
        String URL = Config.Base_Url+"get/gtuspo.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        menu.findItem(R.id.points).setTitle(getString(R.string.points)+" :" + response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // showshort(error.toString());
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("username",App.getInstance().getUsername());
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        return super.onPrepareOptionsMenu(menu);
    }
    void updateUserPoints() {
        final AlertDialog updating = new SpotsDialog(MainActivityAwesome.this, R.style.Custom);
        updating.show();
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                String URL = Config.Base_Url+"get/gtuspo.php";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                setOptionTitle(R.id.points, getString(R.string.points)+" : " +response);
                                new Timer().schedule(new TimerTask() {
                                    @Override
                                    public void run() {
                                        updating.dismiss();
                                    }
                                }, 1000);

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // showshort(error.toString());
                                new Timer().schedule(new TimerTask() {
                                    @Override
                                    public void run() {
                                        updating.dismiss();
                                    }
                                }, 1000);

                            }
                        }){
                    @Override
                    protected Map<String,String> getParams(){
                        Map<String,String> params = new HashMap<String, String>();
                        params.put("username", App.getInstance().getUsername());
                        return params;
                    }

                };

                RequestQueue requestQueue = Volley.newRequestQueue(MainActivityAwesome.this);
                requestQueue.add(stringRequest);
            }
        }, 1000);

    }

    // Linked Functions

    void daily_award(int points, final String type){

        String awr = Config.Base_Url+"get/daily.php";
        final String awrds = Integer.toString(points);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dd = new SimpleDateFormat("dd-MM-yyyy");
        final String Current_Date = dd.format(c.getTime());

        final String v1 ="1";
        final String v0 ="0";
        final String v2 ="2";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, awr,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.intern() == v1.intern()) {
                            AlertDialog alert = new AlertDialog.Builder(MainActivityAwesome.this).create();
                            alert.setTitle(getString(R.string.great));
                            alert.setMessage(Config.daily_reward + " " + getString(R.string.points_received));
                            alert.setCanceledOnTouchOutside(false);

                            alert.setIcon(R.drawable.custom_img);

                            alert.setButton("ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    updateUserPoints();
                                }
                            });
                            alert.show();

                        }
                        if(response.intern() == v0.intern()){
                            AlertDialog alert = new AlertDialog.Builder(MainActivityAwesome.this).create();

                            alert.setTitle(getString(R.string.daily_reward_taken));
                            alert.setMessage(getString(R.string.try_after));
                            alert.setCanceledOnTouchOutside(false);

                            alert.setButton("ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                }

                            });
                            alert.show();

                        }
                        if(response.intern() == v2.intern()){
                            showshort(getString(R.string.server_problem));
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("username",App.getInstance().getUsername());
                params.put("points",awrds);
                params.put("type",type);
                params.put("date",Current_Date);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    void showshort(String msg){
        Toast.makeText(this,msg, Toast.LENGTH_SHORT).show();
    }
    void showlong(String msg){
        Toast.makeText(this,msg, Toast.LENGTH_LONG).show();
    }

    private void initOfferWall() {
        OffersManager.getInstance(this).onAppLaunch();

        PointsManager.getInstance(this).registerNotify(this);

        PointsManager.getInstance(this).registerPointsEarnNotify(this);
    }

    private void initVideoAd() {

        VideoAdManager.getInstance(this).registerRewards(this);
        VideoAdManager.getInstance(this).requestVideoAd(new VideoAdRequestListener() {

            @Override
            public void onRequestSucceed() {
                //Log.e("adxmi", "video request succeed");
            }

            @Override
            public void onRequestFail(int errorCode) {
                //Log.e("adxmi", "video request fail");
            }
        });

    }

    public void getCompletedTransactions() {

        prefManager = new PrefManager(MainActivityAwesome.this);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String subId1 = settings.getString("user_id", App.getInstance().getUsername());

        CompletedOfferRequestListener listener = new CompletedOfferRequestListener() {

            @Override
            public void onSuccess(ArrayList<Map<String, String>> completedOffers) {

                if (completedOffers != null) {
                    for (Map<String, String> offer : completedOffers) {
                        Iterator it = offer.entrySet().iterator();
                        while (it.hasNext()) {
                            Map.Entry pair = (Map.Entry) it.next();
                            //showshort(pair.getKey() + " has been completed for Points :  " + pair.getValue());
                            String s = ""+pair.getValue();
                            if(Integer.parseInt(s) != 0) {
                                award(Integer.parseInt(s), getString(R.string.adscend_media_credit));
                                it.remove();
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Object message) {

            }
        };

        AdscendMediaWrapper.getCompletedTransactions(getApplicationContext(), Config.pubId, Config.adwallId, subId1, listener); // Call to getCompletedOffers api from sdk
    }

    void award(int points, final String type){
        String awr = Config.Base_Url+"get/award.php";
        final String awrds = Integer.toString(points);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dd = new SimpleDateFormat("dd-MM-yyyy");
        final String Current_Date = dd.format(c.getTime());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, awr,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Enable this only on testing - yash
                        showshort(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        showshort("Server Problem!");
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("username",App.getInstance().getUsername());
                params.put("points",awrds);
                params.put("type",type);
                params.put("date",Current_Date);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }


    void init_offertoro(){

        OTOfferWallSettings.getInstance().configInit(Config.OfferToro_AppId,
                Config.OfferToro_Secret_Key, App.getInstance().getUsername());

        OffersInit.getInstance().create(this);
        OffersInit.getInstance().setOfferWallListener(new OfferWallListener() {
            @Override
            public void onOTOfferWallInitSuccess() {

            }

            @Override
            public void onOTOfferWallInitFail(String s) {

            }

            @Override
            public void onOTOfferWallOpened() {

            }

            @Override
            public void onOTOfferWallCredited(double v, double v1) {
                Double D = v;
                int ot_credits = Integer.valueOf(D.intValue());
                if(ot_credits != 0) {
                    award(ot_credits, getString(R.string.OfferToro_credit));
                }
            }

            @Override
            public void onOTOfferWallClosed() {

            }
        });
    }

    void chckwhtmobi(){
        WMManager.setRewardListener(new IRewardsListener() {
            @Override
            public void onRewarded(int amount) {
                if(amount!=0){
                    award(amount,getString(R.string.whitemobi_credit));
                }

            }
        });
    }

    public void checksr(){
        prefManager = new PrefManager(MainActivityAwesome.this);
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String userId = settings.getString("user_id", App.getInstance().getUsername());

        SRUserPoints userPoints = new SRUserPoints(getApplicationContext());
        if (userPoints.updatePoints(Config.appHash, userId)) {
            int totalpoints = userPoints.getNewPoints();
            if(totalpoints !=0){
                award(totalpoints,getString(R.string.SuperRewards_credit));
            }
        }
    }

    private void destroyVideoAd() {
        VideoAdManager.getInstance(this).unRegisterRewards(this);
        VideoAdManager.getInstance(this).onDestroy();
    }

    public void displayInterstitial() {
        // If Ads are loaded, show Interstitial else show nothing.
        if (interstitial.isLoaded()) {
            interstitial.show();
        }
    }

    private void checkReadPhoneStatePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!hasGetReadPhoneStatePermission()) {
                requestReadPhoneStatePermission();
            }
        }
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }
    public void createSessionCompleted(boolean success, boolean isOfferWallEnabled, String message) {
        if (success) {
            MonetizationManager.fetchAd(this, NativeXAdPlacement.Game_Launch, this);
            MonetizationManager.fetchAd(this, NativeXAdPlacement.Main_Menu_Screen, this);
            MonetizationManager.fetchAd(this, NativeXAdPlacement.Level_Failed, this);
            MonetizationManager.fetchAd(this, NativeXAdPlacement.Store_Open, this);
            MonetizationManager.fetchAd(this, NativeXAdPlacement.Player_Generated_Event, this);
        }
    }

    @Override
    public void onEvent(AdEvent event, AdInfo adInfo, String message) {
        switch (event) {
            case FETCHED:

                break;
            case NO_AD:
                break;
            case BEFORE_DISPLAY:

                break;
            case DISMISSED:
                MonetizationManager.fetchAd(this, adInfo.getPlacement(), this);

                break;
            case VIDEO_COMPLETED:
                break;
            case ERROR:
                break;
            default:
                break;
        }

    }

    @Override
    public void onRedeem(RedeemRewardData rewardData) {
        for (Reward reward : rewardData.getRewards()) {
            int nativex_amount = Double.valueOf(reward.getAmount()).intValue();
            if(nativex_amount !=0) {
                award(nativex_amount, getString(R.string.nativex_credit));
            }
        }
        rewardData.showAlert(MainActivityAwesome.this);
    }

    private boolean hasGetReadPhoneStatePermission() {
        return ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestReadPhoneStatePermission() {
        //You can choose a more friendly notice text. And you can choose any view you like, such as dialog.
        showshort(getString(R.string.permission_notice));
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_PHONE_STATE}, REQUEST_READ_PHONE_STATE);
    }

    //Callback for requestPermission
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        try {
            switch (requestCode) {
                case REQUEST_READ_PHONE_STATE: {
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        showshort(getString(R.string.permission_granted));
                    } else {
                        //had not get the permission
                        showshort(getString(R.string.permission_not_granted));
                    }
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {

        if (doubleBackToExitPressedOnce) {
            finish();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        showshort(getString(R.string.click_back_again));

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 1500);

        //  super.onBackPressed();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
    private void destroyOfferWall() {
        PointsManager.getInstance(this).unRegisterNotify(this);
        PointsManager.getInstance(this).unRegisterPointsEarnNotify(this);
        OffersManager.getInstance(this).onAppExit();
    }

    void validate(){
        if (Config.Base_Url.intern() == Constants.API_License && !BuildConfig.DEBUG && Constants.release) {

            App.getInstance().logout();
            Intent i = new Intent(getApplicationContext(), AppActivity.class);
            startActivity(i);
            finish();
            ActivityCompat.finishAffinity(MainActivityAwesome.this);
        }
    }

    void init_app_rate(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(Config.rate_title)
                .setMessage(String.format(Config.rate_message, getString(R.string.app_name)))
                .setPositiveButton(Config.rate_yes, null)
                .setNegativeButton(Config.rate_never, null)
                .setNeutralButton(Config.rate_later, null);

        new AppRate(this)
                .setShowIfAppHasCrashed(false)
                .setMinDaysUntilPrompt(1)
                .setMinLaunchesUntilPrompt(2)
                .setCustomDialog(builder)
                .init();

    }
    // sharing
    void shareURL() {
        try
        { Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
            String sAux = Config.share_text+"\n";
            sAux = sAux + "https://play.google.com/store/apps/details?id="+getApplicationContext().getPackageName()+"\n";
            i.putExtra(Intent.EXTRA_TEXT, sAux);
            startActivity(Intent.createChooser(i, "choose one"));
        }
        catch(Exception e)
        { //e.toString();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLAY_REWARDED_VIDEO && resultCode == RESULT_OK) {
            // Log.d(TAG, "User was credited");
        } else if (requestCode == PLAY_REWARDED_VIDEO && resultCode == RESULT_CANCELED) {
            // Log.d(TAG, "User was not credited");
        }
    }

    public void fcm_id(){
        String fcm_server = Config.Base_Url+"get/token.php";
        final String fcm_token = FirebaseInstanceId.getInstance().getToken();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, fcm_server,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("name",App.getInstance().getUsername());
                params.put("fcm_id",fcm_token);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    @Override
    public void onPointBalanceChange(int pointsBalance) {
        pointTextView.setText(getResources().getString(R.string.text_current_points) + pointsBalance);
        if(pointsBalance != 0){
            award(pointsBalance,getString(R.string.Adxmi_offerwall_credit));
            PointsManager.getInstance(this).spendPoints(pointsBalance);
        }
    }

    @Override
    public void onPointEarn(Context context, EarnPointsOrderList list) {
        for (int i = 0; i < list.size(); ++i) {
            EarnPointsOrderInfo info = list.get(i);
            // Log.i("Adxmi", info.getMessage());
        }
    }

    @Override
    public void onVideoRewards(int reward) {
        PointsManager.getInstance(this).awardPoints(reward);
    }

    @Override
    public void onRewardedVideoInitSuccess() {
        // Log.d(TAG, "onRewardedVideoInitSuccess");
    }

    @Override
    public void onRewardedVideoInitFail(SupersonicError supersonicError) {
        //Log.d(TAG, "onRewardedVideoInitFail" + " " + supersonicError);
    }

    @Override
    public void onRewardedVideoAdOpened() {
        //Log.d(TAG, "onRewardedVideoAdOpened");
        showshort(getString(R.string.video_open_notice));
    }

    @Override
    public void onRewardedVideoAdClosed() {
        //Log.d(TAG, "onRewardedVideoAdClosed");
    }


    @Override
    public void onVideoAvailabilityChanged(final boolean available) {
        // Log.d(TAG, "onVideoAvailabilityChanged" + " " + available);
    }

    @Override
    public void onVideoStart() {
        // Log.d(TAG, "onVideoStart");
    }

    @Override
    public void onVideoEnd() {
        //Log.d(TAG, "onVideoEnd");
    }

    @Override
    public void onRewardedVideoAdRewarded(Placement placement) {
        //Log.d(TAG, "onRewardedVideoAdRewarded" + " " + placement);
        int rewardAmount = placement.getRewardAmount();
        showshort(rewardAmount + " " +getString(R.string.points_received));
        if(rewardAmount != 0) {
            award(rewardAmount, getString(R.string.SuperSonic_video_credit));
        }

    }

    @Override
    public void onRewardedVideoShowFail(SupersonicError supersonicError) {
        //Log.d(TAG, "onRewardedVideoShowFail" + " " + supersonicError);
    }

    @Override
    public void onOfferwallInitSuccess() {
        // Log.d(TAG, "onOfferwallInitSuccess");
    }

    @Override
    public void onOfferwallInitFail(SupersonicError supersonicError) {
        // Log.d(TAG, "onOfferwallInitFail" + " " + supersonicError);
    }

    @Override
    public void onOfferwallOpened() {
        Log.d(TAG, "onOfferwallOpened");
    }

    @Override
    public void onOfferwallShowFail(SupersonicError supersonicError) {
        // Log.d(TAG, "onOfferwallShowFail" + " " + supersonicError);
    }

    @Override
    public boolean onOfferwallAdCredited(int credits, int totalCredits, boolean totalCreditsFlag) {
        int c_amount = totalCredits;
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        int t_amount = settings.getInt("tamount", 0);
        if (c_amount > t_amount) {
            int t = t_amount;
            int q = c_amount - t_amount;
            int n_amount = t + q;
            Editor editor = settings.edit();
            editor.putInt("tamount", n_amount);
            editor.commit();

            showshort(q + " " + getString(R.string.points_received));
            award(q,getString(R.string.SuperSonic_offerwall_credit));
        }        return false;
    }

    @Override
    public void onGetOfferwallCreditsFail(SupersonicError supersonicError) {
        //Log.d(TAG, "onGetOfferwallCreditsFail" + " " + supersonicError);
    }

    @Override
    public void onOfferwallClosed() {
        Log.d(TAG, "onOfferwallClosed");
    }


    // Rate this App
    void rateThisApp() {
        Uri uri = Uri.parse("market://details?id=" + getApplicationContext().getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_NEW_DOCUMENT | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName())));
        }
    }

    void parseURL(String url){
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    private void init_slider() {
        // Creating The Toolbar and setting it as the Toolbar for the activity
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        adapter =  new ViewPagerAdapter(getSupportFragmentManager(),Titles,Numboftabs);
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);
        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true);
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.tabsScrollColor);
            }
        });
        tabs.setViewPager(pager);
    }
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private ScrimInsetsFrameLayout mScrimInsetsFrameLayout;
    private void init_navigator(){
        // Navigation Drawer
        mDrawerLayout = (DrawerLayout) findViewById(R.id.main_activity_DrawerLayout);
        mDrawerLayout.setStatusBarBackgroundColor(getResources().getColor(R.color.primaryDark));
        mScrimInsetsFrameLayout = (ScrimInsetsFrameLayout) findViewById(R.id.main_activity_navigation_drawer_rootLayout);
        mActionBarDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,toolbar,R.string.navigation_drawer_opened,R.string.navigation_drawer_closed)
        {   @Override
            public void onDrawerSlide(View drawerView, float slideOffset)
            {
                // Disables the burger/arrow animation by default
                super.onDrawerSlide(drawerView, 0);
            }
        };
        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);
        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
        mActionBarDrawerToggle.syncState();
        // Navigation Drawer layout width
        int possibleMinDrawerWidth = UtilsDevice.getScreenWidth(this) -
                UtilsMiscellaneous.getThemeAttributeDimensionSize(this, android.R.attr.actionBarSize);
        int maxDrawerWidth = getResources().getDimensionPixelSize(R.dimen.navigation_drawer_max_width);
        mScrimInsetsFrameLayout.getLayoutParams().width = Math.min(possibleMinDrawerWidth, maxDrawerWidth);
        getSupportActionBar().setTitle(R.string.app_name);
    }


    void init_v1_8(){
        AdManager.getInstance(this).init(Config.AppId, Config.AppSecret);
        initOfferWall();
        initVideoAd();

        prefManager = new PrefManager(this);
        if (prefManager.isUserId()) {

            UUID uid = UUID.fromString("7f9a5ca3-d341-40ce-9e1f-f2808632f37a");

            String id = uid.randomUUID().toString();

            SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
            // Writing data to SharedPreferences
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("user_id", id);
            editor.apply();

            prefManager.setUserId(false);
        }
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String userId = settings.getString("user_id", App.getInstance().getUsername());
        String appKey = Config.AppKey;

        mSupersonicInstance = SupersonicFactory.getInstance();
        SupersonicAdsAdvertiserAgent.getInstance().reportAppStarted(this);
        mSupersonicInstance.setRewardedVideoListener(this);
        mSupersonicInstance.initRewardedVideo(this, appKey, userId);
        mSupersonicInstance.setOfferwallListener(this);
        SupersonicConfig.getConfigObj().setClientSideCallbacks(true);
        mSupersonicInstance.initOfferwall(this, appKey, userId);

        // getting all completed offers
        getCompletedTransactions();
        // checking user permisions
        checkReadPhoneStatePermission();
        // checking supersonic validations
        checksr();
        // initializing App Rate Alert
        init_app_rate();
        // validating the code
        validate();
        // initializing offertoro
        init_offertoro();
        chckwhtmobi();
        // getting fcm id
        fcm_id();

    }
}