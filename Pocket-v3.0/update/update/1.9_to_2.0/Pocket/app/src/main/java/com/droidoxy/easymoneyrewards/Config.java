/**
 * Configuration File 
 * Edit this data according to your Requirement
 * 
 * @author DroidOXY
 */

package com.droidoxy.easymoneyrewards;

public class Config
{
	// Debug/Testing Mode  enable = true || disable = false ( see it to false for Live Apps)
	static Boolean debug_mode = false;

	// Manual/Email Login and Signup enable = true || disable = false
	static Boolean enable_email_login = true;

	// Gmail Login and Signup enable = true || disable = false
	static Boolean enable_gmail_login = true;

	// Facebook Login and Signup enable = true || disable = false
	static Boolean enable_facebook_login = true;

	/**
	 *
	 *  Select App Theme ( UI/UX )
	 *
	 * theme1 = Normal activity
	 *
	 * theme2  = With Navigation Drawer
	 *
	 * theme3 = With Navigation Drawer  App Theme  and Tabs
	**/

	static String app_theme_ui = "theme3";

	// Daily Reward Points
	static int daily_reward = 25;

	// Referal Reward Points
	static int referal_reward = 100;

	// AdGate Media WallId - Read Doc
	static String adgateMedia_id ="naucrg";

	// Fyber AppId and Security Token - Read Doc
	static String fyber_id ="109537";
	static String fyber_security_token ="dfff3dda950b7b7e76be7845e22e4635";

	// OfferToro AppId and secret key - Read Doc
	static String OfferToro_AppId ="3084";
	static String OfferToro_Secret_Key ="b923e0ba9df6a2bef732b3b951e44f77";

	// White Mobi App key - Read Doc
	static String whitemobi_key ="60a01bc2f9967155";

	// Adxmi AppId - Read Doc
	static String AppId ="1a3572449fabd457";

	// Adxmi AppSecret - Read Doc
	static String AppSecret ="ed05a09f01917266";

	// SuperSonic AppKey - Read Doc
	static String AppKey ="53f22b55";

	// NativeX AppId - Read Doc
	static String NativeX_AppId = "98361";

	// Adscend Media keys - Read Doc
	static String pubId = "107661";
	static String adwallId = "7451";

	// Super Rewards AppHash - Read Doc
	static String appHash = "slihtyrqpdi.423735976755";

	// Server URL ie., Webpanel Hosted Url - must be http://folder.example.com/  do-not use http://example.com/folder/
	public static String Base_Url = "http://www.example.com/";

	// URL to your Privacy Policy
	public static String policy_Url = "http://www.example.com/privacy";

	// URL to your Contact Form
	public static String contact_Url = "http://www.example.com/contact";

	// your Contact Email
	public static String com_email = "support@example.com";

	// your Company Website
	public static String com_website = "www.example.com";

	// Images for MainActivity
    public static int[] images={R.drawable.ic_checkin,
						R.drawable.ic_video,
						R.drawable.ic_video,
						R.drawable.ic_offertoro,
						R.drawable.ic_adgate,
						R.drawable.ic_fyber,
						R.drawable.ic_super_sonic,
						R.drawable.ic_adscend,
						R.drawable.ic_sr,
						R.drawable.ic_adxmi,
						R.drawable.ic_nativex,
						R.drawable.ic_whitemobi,
						R.drawable.ic_redeem,
						R.drawable.ic_instructions,
						R.drawable.ic_refer,
						R.drawable.ic_about};

    // Edit MainActivity Titles and Description in main_screen_strings.xml file in res->values folder


	//---------------------------------------------------
	//Images for Redeem Activity

	public static int[] payout_images={
		R.drawable.ic_paypal_logo,
		R.drawable.ic_paypal_logo,
		R.drawable.ic_paytm,
		R.drawable.ic_paytm,
		R.drawable.ic_amazon_icon,
		R.drawable.ic_googleplay_icon};


	// Edit RedeemActivity Titles and Description in redeem_screen_strings.xml file in res->values folder

    // Google Analytics [[OPTIONAL ]]
	static String analytics_property_id = "UA-76982496-1";

}