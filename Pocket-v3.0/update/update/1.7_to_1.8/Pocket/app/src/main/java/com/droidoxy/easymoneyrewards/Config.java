/**
 * Configuration File 
 * Edit this data according to your Requirement
 * 
 * @author DroidOXY
 */

package com.droidoxy.easymoneyrewards;

public class Config
{
	// Navigation drawer enable = true || disable = false
	static Boolean enable_navigation_drawer = true;

	// Daily Reward Points
	static int daily_reward = 25;

	// Referal Reward Points
	static int referal_reward = 100;

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
	public static String Base_Url = "http://example.com/";

	// URL to your Privacy Policy
	public static String policy_Url = "http://www.example.com/privacy";

	// URL to your Contact Form
	public static String contact_Url = "http://www.example.com/contact";

	// Images for MainActivity
    public static int[] images={R.drawable.ic_checkin,
						R.drawable.ic_video,
						R.drawable.ic_video,
						R.drawable.ic_offertoro,
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
						
    //Titles for MainActivity
    public static String[] titles ={"Daily Check-In",
								"SuperSonic Videos",
								"NativeX Videos",
								"OfferToro OfferWall",
								"SuperSonic OfferWall",
								"Adscend OfferWall",
								"SuperRewards OfferWall",
								"Adxmi OfferWall",
								"Nativex OfferWall",
								"White Mobi OfferWall",
								"Redeem",
								"Instructions",
								"Refer & Earn",
								"About"};
								
	//Description for MainActuvity Titles
    public static String[] description={"Open Daily and Earn 25 Points",
								"Watch Videos to Earn Points",
								"Watch Videos to Earn Points",
								"Install Apps and Earn Points",
								"Install Apps and Earn Points",
								"Install Apps and Earn Points",
								"Install Apps and Earn Points",
								"Install Apps and Earn Points",
								"Install Apps and Earn Points",
								"Install Apps and Earn Points",
								"Turn your Points into Cash",
								"How to Earn Points",
								"Refer Freiends & Earn 100 Points",
								"Advertise with Us"};

	//---------------------------------------------------
	//Images for Redeem Activity

	static int[] payout_images={
		R.drawable.ic_paypal_logo,
		R.drawable.ic_paypal_logo,
		R.drawable.ic_paytm,
		R.drawable.ic_paytm,
		R.drawable.ic_amazon_icon,
		R.drawable.ic_googleplay_icon};

    //Titles for Redeem Activity
	static String[] payout_titles ={
		"Paypal",
		"Paypal",
		"Paytm",
		"Paytm",
		"Amazon",
		"Google Play"};

	//Description for Redeem Activity Titles
	static String[] payout_description={
		"1000 Points = $1 USD",
		"5000 Points = $5 USD",
		"1000 Points = 100 INR",
		"5000 Points = 500 INR",
		"3000 Points = $2.5 USD",
		"9000 Points = $10 USD"};

    // Google Analytics [[OPTIONAL ]]
	static String analytics_property_id = "UA-76982496-1";

	// Share text and link for Share Button
	static String share_text = "Hello, look what a beautiful app that I found here:";
	static String refer_text1 = "Get 100 Points by using my referal Code : ";

	// APP RATING
	static String rate_later = "Perhaps Later";
    static String rate_never = "No Thanks";
    static String rate_yes="Rate Now";
    static String rate_message = "We hope you enjoy using %1$s. Would you like to help us by rating us in the Store?";
	static String rate_title = "Enjoying our app?";

}