package com.droidoxy.easymoneyrewards.constants;

import com.droidoxy.easymoneyrewards.app.App;

import static com.droidoxy.easymoneyrewards.Config.SERVER_URL;

/**
 * Created by DroidOXY
 */
 
public interface Constants {

    public static final String CLIENT_ID = "1";

    public static final String API_DOMAIN = SERVER_URL;

    public static final String API_FILE_EXTENSION = ".php";
    public static final String API_VERSION = "v3";

    public static final String METHOD_ACCOUNT_LOGIN = API_DOMAIN + "api/" + API_VERSION + "/account.signIn" + API_FILE_EXTENSION;
    public static final String METHOD_ACCOUNT_SIGNUP = API_DOMAIN + "api/" + API_VERSION + "/account.signUp" + API_FILE_EXTENSION;
    public static final String METHOD_ACCOUNT_RECOVERY = API_DOMAIN + "api/" + API_VERSION + "/account.recovery" + API_FILE_EXTENSION;
    public static final String METHOD_ACCOUNT_AUTHORIZE = API_DOMAIN + "api/" + API_VERSION + "/account.authorize" + API_FILE_EXTENSION;
    public static final String METHOD_ACCOUNT_LOGOUT = API_DOMAIN + "api/" + API_VERSION + "/account.logOut" + API_FILE_EXTENSION;

    public static final String APP_PAYOUTS = API_DOMAIN + "api/" + API_VERSION + "/app.Payouts" + API_FILE_EXTENSION;
    public static final String ACCOUNT_REFER = API_DOMAIN + "api/" + API_VERSION + "/account.Refer" + API_FILE_EXTENSION;
    public static final String APP_OFFERWALLS = API_DOMAIN + "api/" + API_VERSION + "/app.OfferWalls" + API_FILE_EXTENSION;
    public static final String ACCOUNT_REDEEM = API_DOMAIN + "api/" + API_VERSION + "/account.Redeem" + API_FILE_EXTENSION;
    public static final String ACCOUNT_REWARD = API_DOMAIN + "api/" + API_VERSION + "/account.Reward" + API_FILE_EXTENSION;
    public static final String ACCOUNT_BALANCE = API_DOMAIN + "api/" + API_VERSION + "/account.Balance" + API_FILE_EXTENSION;
    public static final String ACCOUNT_CHECKIN = API_DOMAIN + "api/" + API_VERSION + "/account.Checkin" + API_FILE_EXTENSION;
    public static final String APP_OFFERSTATUS = API_DOMAIN + "api/" + API_VERSION + "/app.OfferStatus" + API_FILE_EXTENSION;
    public static final String ACCOUNT_TRANSACTIONS = API_DOMAIN + "api/" + API_VERSION + "/account.Transactions" + API_FILE_EXTENSION;

    public static final String API_OFFERTORO = API_DOMAIN + "api/" + API_VERSION + "/api.OfferToro" + API_FILE_EXTENSION;

    public static final int ERROR_SUCCESS = 0;

    public static final int ERROR_LOGIN_TAKEN = 300;
    public static final int ERROR_EMAIL_TAKEN = 301;
    public static final int ERROR_IP_TAKEN = 302;

    public static final int ACCOUNT_STATE_ENABLED = 0;
    public static final int ACCOUNT_STATE_DISABLED = 1;
    public static final int ACCOUNT_STATE_BLOCKED = 2;
    public static final int ACCOUNT_STATE_DEACTIVATED = 3;

    public static final int ERROR_UNKNOWN = 100;
    public static final int ERROR_ACCESS_TOKEN = 101;

    public static final int ERROR_ACCOUNT_ID = 400;

    public static Boolean DEBUG_MODE = App.getInstance().get("APP_DEBUG_MODE",false);

    public static final String LICENSE_COPY = "http://www.codyhub.com/item/android-rewards-app-pocket/";
}