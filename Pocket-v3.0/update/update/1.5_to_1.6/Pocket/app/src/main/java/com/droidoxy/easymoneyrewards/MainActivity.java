package com.droidoxy.easymoneyrewards;

import java.io.OutputStream;
import java.util.Properties;
import java.util.Calendar;
import android.Manifest;
import android.net.Uri;
import android.util.Log;
import android.app.*;
import android.os.*;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.pm.PackageManager;
import android.content.*;
import android.graphics.Color;
import net.adxmi.android.AdManager;
import net.adxmi.android.os.EarnPointsOrderInfo;
import net.adxmi.android.os.EarnPointsOrderList;
import net.adxmi.android.os.OffersBrowserConfig;
import net.adxmi.android.os.OffersManager;
import net.adxmi.android.os.PointsChangeNotify;
import net.adxmi.android.os.PointsEarnNotify;
import net.adxmi.android.os.PointsManager;
import net.adxmi.android.video.VideoAdManager;
import net.adxmi.android.video.VideoAdRequestListener;
import net.adxmi.android.video.VideoRewardsListener;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.iid.FirebaseInstanceId;
import com.supersonic.adapters.supersonicads.SupersonicConfig;
import com.supersonic.mediationsdk.logger.SupersonicError;
import com.supersonic.mediationsdk.model.Placement;
import com.supersonic.mediationsdk.sdk.OfferwallListener;
import com.supersonic.mediationsdk.sdk.RewardedVideoListener;
import com.supersonic.mediationsdk.sdk.Supersonic;
import com.supersonic.mediationsdk.sdk.SupersonicFactory;
import com.supersonicads.sdk.agent.SupersonicAdsAdvertiserAgent;
import com.tjeannin.apprate.AppRate;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import java.util.*;
import java.text.SimpleDateFormat;
import android.os.Bundle;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.util.HashMap;
import java.util.Map;
import com.adscendmedia.sdk.ui.AdscendMediaWrapper;
import com.adscendmedia.sdk.ui.MarketResearchActivity;
import com.adscendmedia.sdk.ui.OffersActivity;
import com.adscendmedia.sdk.ui.RewardedVideoActivity;
import com.adscendmedia.sdk.util.CompletedOfferRequestListener;
import com.adscendmedia.sdk.util.JsonArrayRequestListener;
import com.google.gson.JsonArray;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import com.playerize.superrewards.SuperRewards;
import com.playerize.superrewards.SRUserPoints;
import com.droidoxy.easymoneyrewards.app.App;

import com.nativex.monetization.MonetizationManager;
import com.nativex.monetization.business.reward.Reward;
import com.nativex.monetization.communication.RedeemRewardData;
import com.nativex.monetization.enums.AdEvent;
import com.nativex.monetization.enums.NativeXAdPlacement;
import com.nativex.monetization.listeners.OnAdEventV2;
import com.nativex.monetization.listeners.RewardListener;
import com.nativex.monetization.listeners.OnAdEvent;
import com.nativex.monetization.listeners.SessionListener;
import com.nativex.monetization.mraid.AdInfo;

class YourActivity extends Activity
{
    //Declare the Supersonic Mediation Agent
    private Supersonic mMediationAgent;
    //For example, add the following inside “onCreate()” method:
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //Get the mediation publisher instance
        mMediationAgent = SupersonicFactory.getInstance();
    }
}
public class MainActivity extends BaseActivity implements PointsChangeNotify,
        PointsEarnNotify, VideoRewardsListener, RewardedVideoListener, BaseSliderView.OnSliderClickListener, OfferwallListener, OnAdEventV2, SessionListener, RewardListener {

    private final static int REQUEST_READ_PHONE_STATE = 1;

    private InterstitialAd interstitial;
    public Timer AdTimer;
    public boolean doubleBackToExitPressedOnce = false;
    private Supersonic mSupersonicInstance;
    private final String TAG = "MainActivity";
    ListView listView;
    private PrefManager prefManager;
    public static final String PREFS_NAME = "MyApp_Settings";
    private TextView pointTextView;
    int totalPoints;
    Properties properties;
    String vc_name = "credits";
    private GoogleApiClient client;
    static final int PLAY_REWARDED_VIDEO = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("tag", "init usedMemory: " + Debug.getNativeHeapAllocatedSize() / 1024L);
        setContentView(R.layout.activity_main);
        initView();
        invalidateOptionsMenu();
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

        totalPoints = PointsManager.getInstance(this).queryPoints();
        pointTextView.setText(getResources().getString(R.string.text_current_points) + totalPoints);

        AdManager.getInstance(this).init(Config.AppId, Config.AppSecret);
        initOfferWall();
        initVideoAd();

        prefManager = new PrefManager(this);
        if (prefManager.isUserId()) {

            UUID uid = UUID.fromString("7f9a5ca3-d341-40ce-9e1f-f2808632f37a");

            String id = uid.randomUUID().toString();

            SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
            // Writing data to SharedPreferences
            Editor editor = settings.edit();
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

        getCompletedTransactions();
        checkReadPhoneStatePermission();
        checksr();
        validate();
        fcm_id();
        // -------------------------------  AdMob Banner ------------------------------------------------------------
        AdView adView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().setRequestAgent("android_studio:ad_template").build();
        adView.loadAd(adRequest);


        // -------------------------------- AdMob Interstitial ----------------------------
        // Prepare the Interstitial Ad
        interstitial = new InterstitialAd(MainActivity.this);
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


        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
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
    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onResume() {
        super.onResume();

        MonetizationManager.createSession(getApplicationContext(), Config.NativeX_AppId, this);
        MonetizationManager.setRewardListener(this);
        // do nothing here
    }


    public void createSessionCompleted(boolean success, boolean isOfferWallEnabled, String message) {
        if (success) {
            MonetizationManager.fetchAd(this, NativeXAdPlacement.Game_Launch, this);
            MonetizationManager.fetchAd(this, NativeXAdPlacement.Main_Menu_Screen, this);
            MonetizationManager.fetchAd(this, NativeXAdPlacement.Level_Failed, this);
            MonetizationManager.fetchAd(this, NativeXAdPlacement.Store_Open, this);
            MonetizationManager.fetchAd(this, NativeXAdPlacement.Player_Generated_Event, this);
        } else {
            // do nothin
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
        //Take possession of the balances returned here.
        int totalRewardAmount = 0;
        for (Reward reward : rewardData.getRewards()) {

            // add the reward amount to the total
            int nativex_amount = Double.valueOf(reward.getAmount()).intValue();
            totalRewardAmount += reward.getAmount();
            // Toast.makeText(MainActivity.this, nativex_amount+" Points Received", Toast.LENGTH_SHORT).show();
            award(nativex_amount,"NativeX reward");
        }
        rewardData.showAlert(MainActivity.this);
    }

    private boolean hasGetReadPhoneStatePermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestReadPhoneStatePermission() {
        //You can choose a more friendly notice text. And you can choose any view you like, such as dialog.
        Toast.makeText(this, "Only grant the permission, can you start the mission!", Toast.LENGTH_SHORT).show();
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_READ_PHONE_STATE);
    }

    //Callback for requestPermission
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        try {
            switch (requestCode) {
                case REQUEST_READ_PHONE_STATE: {
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "The Permission has granted,you can get your mission now", Toast.LENGTH_SHORT).show();
                    } else {
                        //had not get the permission
                        Toast.makeText(this, "Not get the permission,so you cannot get your mission", Toast.LENGTH_SHORT).show();
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
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 1500);

        //  super.onBackPressed();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        // TODO Auto-generated method stub
        // Toast.makeText(this,slider.getBundle().get("extra") + "",Toast.LENGTH_SHORT).show();
        startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(slider.getBundle().getString("extra"))));
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
                Log.e("adxmi", "video request succeed");
            }

            @Override
            public void onRequestFail(int errorCode) {
                Log.e("adxmi", "video request fail");
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://"+Config.package_name+"/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        destroyOfferWall();

        destroyVideoAd();

    }

    private void destroyOfferWall() {
        PointsManager.getInstance(this).unRegisterNotify(this);
        PointsManager.getInstance(this).unRegisterPointsEarnNotify(this);
        OffersManager.getInstance(this).onAppExit();
    }

    void validate(){
        if (Config.Base_Url.intern() == "http://pocket.droid.oxywebs.in/" && !BuildConfig.DEBUG) {

           App.getInstance().logout();
            Intent i = new Intent(getApplicationContext(), AppActivity.class);
            startActivity(i);
            finish();
            ActivityCompat.finishAffinity(MainActivity.this);
        }
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
                        Toast.makeText(MainActivity.this,response,Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this,"Server Problem!",Toast.LENGTH_SHORT).show();
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
                            AlertDialog alert = new AlertDialog.Builder(MainActivity.this).create();
                            alert.setTitle("Great !!");
                            alert.setMessage(Config.daily_reward + " Points Successfully Received");
                            alert.setCanceledOnTouchOutside(false);

                            alert.setIcon(R.drawable.custom_img);

                            alert.setButton("ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    Intent intent = getIntent();
                                    finish();
                                    startActivity(intent);
                                }
                            });
                            alert.show();

                        }
                        if(response.intern() == v0.intern()){
                            AlertDialog alert = new AlertDialog.Builder(MainActivity.this).create();

                            alert.setTitle("Taken Already..");
                            alert.setMessage("Try After SomeTime!!");
                            alert.setCanceledOnTouchOutside(false);

                            alert.setButton("ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                }

                            });
                            alert.show();

                        }
                        if(response.intern() == v2.intern()){

                            Toast.makeText(MainActivity.this,"server problem! Try Again after some time",Toast.LENGTH_SHORT).show();
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

    private void destroyVideoAd() {
        VideoAdManager.getInstance(this).unRegisterRewards(this);
        VideoAdManager.getInstance(this).onDestroy();
    }


    private void initView() {
        pointTextView = (TextView) findViewById(R.id.tv_current_points);

        listView = (ListView) findViewById(R.id.listView);

        CustomAdapter adapter = new CustomAdapter(this, Config.titles, Config.images, Config.description);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                switch (position) {

                    // Daily Checkin
                    case 0:
                            daily_award(Config.daily_reward,"Daily Checkin Credit");

                        break;

                    // Instructions
                    case 1:
                        Intent i = new Intent(getBaseContext(), InstructionsActivity.class);
                        startActivity(i);
                        // Instructions
                        break;

                    case 2:
                        Intent refer = new Intent(getBaseContext(), ReferActivity.class);
                        startActivity(refer);

                        break;

                    // Video ads SuperSonic
                    case 3:

                        if (mSupersonicInstance.isRewardedVideoAvailable())
                        {
                            mSupersonicInstance.showRewardedVideo();
                        } else {
                            Toast.makeText(MainActivity.this, "No Videos Available ! \n Try after SomeTime", Toast.LENGTH_SHORT).show();
                        }

                        break;

                    // Video ads Nativex
                    case 4:

                        //show the NativeX Videos
                        if( MonetizationManager.isAdReady(NativeXAdPlacement.Level_Failed) ) {

                            MonetizationManager.showReadyAd(MainActivity.this, NativeXAdPlacement.Level_Failed, null);
                        }
                        else {

                            Toast.makeText(MainActivity.this, "try again after sometimes", Toast.LENGTH_SHORT).show();
                        }

                        break;

                    // SuperSonic OfferWall
                    case 5:

                        //show the offerwall
                        if (mSupersonicInstance.isOfferwallAvailable()) {
                            mSupersonicInstance.showOfferwall();
                        } else {
                            Toast.makeText(MainActivity.this, "Loading....Try after Sometime !", Toast.LENGTH_SHORT).show();
                        }

                        break;

                    // Adscend Media Offerwall
                    case 6:
                    prefManager = new PrefManager(MainActivity.this);

                    SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                    String subId1 = settings.getString("user_id", App.getInstance().getUsername());
                    Intent Adscend_offer = OffersActivity.getIntentForOfferWall(MainActivity.this, Config.pubId, Config.adwallId, subId1);
                    startActivity(Adscend_offer);

                    break;

                    // Super Rewards Offerwall
                    case 7:

                        prefManager = new PrefManager(MainActivity.this);

                        SharedPreferences settings2 = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                        String userId = settings2.getString("user_id", App.getInstance().getUsername());

                        SuperRewards sr = new SuperRewards(getResources(), "com.playerize.awesomeapp");
                        sr.showOffers(MainActivity.this, Config.appHash, userId);

                    break;

                    // Offer Wall ads Adxmi(Apps)
                    case 8:
                        //checking the permission first
                        if (hasGetReadPhoneStatePermission()) {
                            OffersManager.getInstance(MainActivity.this).showOffersWall();
                        } else {
                            requestReadPhoneStatePermission();
                        }
                        break;

                    // NativeX OfferWall
                    case 9:

                        //show the NativeX offerwall
                        if( MonetizationManager.isAdReady(NativeXAdPlacement.Main_Menu_Screen) ) {

                            MonetizationManager.showReadyAd(MainActivity.this, NativeXAdPlacement.Main_Menu_Screen, null);
                        }
                        else {

                            Toast.makeText(MainActivity.this, "try again after sometimes", Toast.LENGTH_SHORT).show();
                        }

                        break;


                    // Redeem
                    case 10:

                        Intent redeem = new Intent(getBaseContext(), RedeemActivity.class);
                        startActivity(redeem);
                        break;

                    // About
                    case 11:
                        Intent about = new Intent(getBaseContext(),AboutActivity.class);
                        startActivity(about);
                        break;
                }
            }

            @SuppressWarnings("unused")
            public void onClick(View v) {
            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();

        getCompletedTransactions();
        client.connect();Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://"+Config.package_name+"/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    class CustomAdapter extends ArrayAdapter<String> {
        Context context;

        int image[];

        String[] title;

        String[] description;

        public CustomAdapter(Context context, String[] titles, int[] imgs, String[] desc) {

            super(context, R.layout.single_row, titles);

            this.context = context;

            this.image = imgs;

            this.title = titles;

            this.description = desc;

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View row = inflater.inflate(R.layout.single_row, parent, false);

            ImageView myimage = (ImageView) row.findViewById(R.id.imageView);

            TextView mytitle = (TextView) row.findViewById(R.id.textView);

            TextView mydesc = (TextView) row.findViewById(R.id.textView2);

            myimage.setImageResource(image[position]);

            mytitle.setText(title[position]);

            mydesc.setText(description[position]);

            return row;
        }
    }

    @Override
    public void onPointBalanceChange(int pointsBalance) {
        pointTextView.setText(getResources().getString(R.string.text_current_points) + pointsBalance);
        award(pointsBalance,"Adxmi OfferWall Credit");
        PointsManager.getInstance(this).spendPoints(pointsBalance);
    }

    @Override
    public void onPointEarn(Context context, EarnPointsOrderList list) {
        for (int i = 0; i < list.size(); ++i) {
            EarnPointsOrderInfo info = list.get(i);
            Log.i("Adxmi", info.getMessage());
        }
    }

    @Override
    public void onVideoRewards(int reward) {
        PointsManager.getInstance(this).awardPoints(reward);
    }

    @Override
    public void onRewardedVideoInitSuccess() {
        Log.d(TAG, "onRewardedVideoInitSuccess");
    }

    @Override
    public void onRewardedVideoInitFail(SupersonicError supersonicError) {
        Log.d(TAG, "onRewardedVideoInitFail" + " " + supersonicError);
    }

    @Override
    public void onRewardedVideoAdOpened() {
        Log.d(TAG, "onRewardedVideoAdOpened");
        Toast.makeText(MainActivity.this, "Watch till the End to get Points", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoAdClosed() {
        Log.d(TAG, "onRewardedVideoAdClosed");
    }


    @Override
    public void onVideoAvailabilityChanged(final boolean available) {
        Log.d(TAG, "onVideoAvailabilityChanged" + " " + available);
    }


    @Override
    public void onVideoStart() {
        Log.d(TAG, "onVideoStart");
    }

    @Override
    public void onVideoEnd() {
        Log.d(TAG, "onVideoEnd");
    }

    @Override
    public void onRewardedVideoAdRewarded(Placement placement) {
        Log.d(TAG, "onRewardedVideoAdRewarded" + " " + placement);
        String rewardName = placement.getRewardName();
        int rewardAmount = placement.getRewardAmount();
        Toast.makeText(MainActivity.this, rewardAmount + " " + rewardName + " Received !", Toast.LENGTH_LONG).show();
        award(rewardAmount,"SuperSonic Video Ad Credit");

    }

    @Override
    public void onRewardedVideoShowFail(SupersonicError supersonicError) {
        Log.d(TAG, "onRewardedVideoShowFail" + " " + supersonicError);
    }

    @Override
    public void onOfferwallInitSuccess() {
        Log.d(TAG, "onOfferwallInitSuccess");
    }

    @Override
    public void onOfferwallInitFail(SupersonicError supersonicError) {
        Log.d(TAG, "onOfferwallInitFail" + " " + supersonicError);
    }

    @Override
    public void onOfferwallOpened() {
        Log.d(TAG, "onOfferwallOpened");
    }

    @Override
    public void onOfferwallShowFail(SupersonicError supersonicError) {
        Log.d(TAG, "onOfferwallShowFail" + " " + supersonicError);
    }


    @Override
    public boolean onOfferwallAdCredited(int credits, int totalCredits, boolean totalCreditsFlag) {
        int c_amount = totalCredits;
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        int t_amount = settings.getInt("tamount", 0);

        if (c_amount == t_amount) {
        } else if (c_amount > t_amount) {
            int t = t_amount;
            int q = c_amount - t_amount;
            int n_amount = t + q;
            Editor editor = settings.edit();
            editor.putInt("tamount", n_amount);
            editor.commit();

            Toast.makeText(MainActivity.this, q + "Points Received !", Toast.LENGTH_SHORT).show();
            award(q,"SuperSonic OfferWall Credit");

        } else {

        }
        return false;
    }

    @Override
    public void onGetOfferwallCreditsFail(SupersonicError supersonicError) {
        Log.d(TAG, "onGetOfferwallCreditsFail" + " " + supersonicError);
    }

    @Override
    public void onOfferwallClosed() {
        Log.d(TAG, "onOfferwallClosed");
    }

    @Override
    public boolean onPrepareOptionsMenu(final Menu menu) {
        String URL = Config.Base_Url+"get/gtuspo.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        menu.findItem(R.id.points).setTitle("Points :" + response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                       // Toast.makeText(MainActivity.this,error.toString(),Toast.LENGTH_SHORT).show();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.points:
                openredeem();
                return true;
            case R.id.sync:
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void checksr(){
        prefManager = new PrefManager(MainActivity.this);
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String userId = settings.getString("user_id", App.getInstance().getUsername());

        SRUserPoints userPoints = new SRUserPoints(getApplicationContext());
        if (userPoints.updatePoints(Config.appHash, userId)) {
            int totalpoints = userPoints.getNewPoints();
            if(totalpoints ==0){
                // do nothin
            }else{
                award(totalpoints,"SuperRewards OfferWall Credit");
                Toast.makeText(getApplicationContext(), totalpoints + " Points Received!", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void getCompletedTransactions() {

        prefManager = new PrefManager(MainActivity.this);

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
                            Toast.makeText(MainActivity.this, pair.getKey() + " has been completed for Points :  " + pair.getValue(), Toast.LENGTH_SHORT).show();
                            String s = ""+pair.getValue();
                            award(Integer.parseInt(s),"Adscend Media Credit");
                            it.remove();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLAY_REWARDED_VIDEO && resultCode == RESULT_OK) {
            Log.d(TAG, "User was credited");
        } else if (requestCode == PLAY_REWARDED_VIDEO && resultCode == RESULT_CANCELED) {
            Log.d(TAG, "User was not credited");
        }
    }

    void openredeem() {

        Intent redeem = new Intent(getBaseContext(), RedeemActivity.class);
        startActivity(redeem);
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

    void diySaveVideoReward(int reward) {
        totalPoints += reward;
        pointTextView.setText(getResources().getString(R.string.text_current_points) + totalPoints);
        properties.setProperty("vc_name", vc_name);
        properties.setProperty("total_amount", "" + totalPoints);
        try {
            OutputStream outfile = openFileOutput("points.properties", 0);
            properties.store(outfile, "vc info");
            outfile.close();
        } catch (Exception err) {
        }

    }

    int diyLoadVideoReward() {
        properties = new Properties();
        int rewards;
        try {
            properties.load(openFileInput("points.properties"));
            vc_name = properties.getProperty("vc_name", "credits");
            rewards = Integer.parseInt(properties.getProperty("total_amount", "0"));
        } catch (Exception err) {
            vc_name = "credits";
            rewards = 0;
        }
        return rewards;

    }
}