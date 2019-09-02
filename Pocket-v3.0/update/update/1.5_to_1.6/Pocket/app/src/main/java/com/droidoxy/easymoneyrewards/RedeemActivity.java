package com.droidoxy.easymoneyrewards;

import java.util.Calendar;

import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.content.*;

import net.adxmi.android.os.PointsManager;
import android.os.Bundle;
import android.content.Intent;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.droidoxy.easymoneyrewards.app.App;
import com.droidoxy.easymoneyrewards.util.CustomRequest;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.text.*;

public class RedeemActivity extends BaseActivity {

	ListView listView;
	int totalPoints;
	private TextView pointTextView;

	public static String Redeem_URL = Config.Base_Url + "do_redeem.php";
	private static String TAG = "RedeemActivity";
	//private ProgressDialog pDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_redeem);

		getSupportActionBar().setIcon(R.drawable.ic_back_icon);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setTitle("Redeem");

		initview();
		validate();

		// executing retreival task here
		String URL = Config.Base_Url+"get/gtuspo.php";

		StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						//System.out.println(response);
						// Toast.makeText(MainActivity.this,response,Toast.LENGTH_SHORT).show();
						totalPoints = Integer.parseInt(response);
					}
				},
				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Toast.makeText(RedeemActivity.this,error.toString(),Toast.LENGTH_SHORT).show();
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

		pointTextView.setText(getResources().getString(R.string.text_current_points) + totalPoints);

		// -------------------------------  AdMob Banner ------------------------------------------------------------
		AdView adView = (AdView) findViewById(R.id.adView22);
		AdRequest adRequest = new AdRequest.Builder().setRequestAgent("android_studio:ad_template").build();
		adView.loadAd(adRequest);

	}

	private void initview() {

		pointTextView = (TextView) findViewById(R.id.tv_current_points);

		listView = (ListView) findViewById(R.id.listView);

		CustomAdapter adapter = new CustomAdapter(this, Config.payout_titles, Config.payout_images, Config.payout_description);

		listView.setAdapter(adapter);

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				switch (position) {

					case 0:
						// Paypal

						//	Redeem(Gift_Name, Points_To_Redeem, Gift_Amount, Gift_Message);

						Redeem("Paypal", 1000, "$1 USD", "Enter your Paypal Email :");

						break;

					case 1:
						// Paypal

						//	Redeem(Gift_Name, Points_To_Redeem, Gift_Amount, Gift_Message);

						Redeem("Paypal", 5000, "$5 USD", "Enter your Paypal Email :");

						break;

					case 2:
						// Paytm

						//	Redeem(Gift_Name, Points_To_Redeem, Gift_Amount, Gift_Message);

						Redeem("Paytm", 1000, "100 INR", "Enter your Paytm Mobile Number :");

						break;

					case 3:
						// Paytm

						//	Redeem(Gift_Name, Points_To_Redeem, Gift_Amount, Gift_Message);

						Redeem("Paytm", 5000, "500 INR", "Enter your Paytm Mobile Number :");

						break;

					case 4:
						// Amazon

						//	Redeem(Gift_Name, Points_To_Redeem, Gift_Amount, Gift_Message);

						Redeem("Amazon", 3000, "$2.5 USD", "Enter your Amazon Email :");

						break;

					case 5:
						// Google Play

						//	Redeem(Gift_Name, Points_To_Redeem, Gift_Amount, Gift_Message);

						Redeem("Google Play", 9000, "$10 USD", "Enter your Google Play Email :");

						break;

				}

			}

			@SuppressWarnings("unused")
			public void onClick(View v) {
			}


		});

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

	public void Redeem(String Gift_Name, int Points_To_Redeem, String Gift_Amount, String Gift_Message) {

		final String gift_name = Gift_Name;
		final String amount = Gift_Amount;
		final int points = Points_To_Redeem;

		if (totalPoints >= points) {

			AlertDialog.Builder alert = new AlertDialog.Builder(RedeemActivity.this);

			alert.setTitle(R.string.app_name);
			alert.setMessage(Gift_Message);
			alert.setCancelable(false);

			// Set an EditText view to get user input
			final EditText input = new EditText(RedeemActivity.this);
			alert.setView(input);

			alert.setPositiveButton("Redeem Now", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					String user_input = input.getText().toString();

					if (user_input.length() >= 5) {
						// Do something with value!
						insert(user_input, gift_name, amount, points);

					} else {

						AlertDialog alert = new AlertDialog.Builder(RedeemActivity.this).create();

						alert.setTitle(" Error..");
						alert.setMessage("Please Enter Something");
						alert.setCanceledOnTouchOutside(false);

						alert.setButton("ok", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
								//
							}

						});
						alert.show();
					}
				}
			});

			alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					// Canceled.
				}
			});

			alert.show();
		} else {

			AlertDialog alert = new AlertDialog.Builder(RedeemActivity.this).create();

			alert.setTitle("Oops ...");
			alert.setMessage("No Enough Points to Redeem !!");
			alert.setCanceledOnTouchOutside(false);
			alert.setButton("ok", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					// Do nothing here
				}

			});
			alert.show();

		}
	}

	void validate(){
		if (Config.Base_Url.intern() == "http://pocket.droid.oxywebs.in/" && !BuildConfig.DEBUG) {

			App.getInstance().logout();
			Intent i = new Intent(getApplicationContext(), AppActivity.class);
			startActivity(i);
			finish();
			ActivityCompat.finishAffinity(RedeemActivity.this);
		}
	}

	// function to award the points
	void spend(int points, final String type, final String msg){

		String awr = Config.Base_Url+"get/redeem.php";
		final String awrds = Integer.toString(points);
		Calendar c = Calendar.getInstance();
		SimpleDateFormat dd = new SimpleDateFormat("dd-MM-yyyy");
		final String Current_Date = dd.format(c.getTime());
		final String val1 = "1";

		StringRequest stringRequest = new StringRequest(Request.Method.POST, awr,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {

						if(response.intern() == val1.intern()){
							AlertDialog alert = new AlertDialog.Builder(RedeemActivity.this).create();

							alert.setTitle("Great !!");
							alert.setMessage(msg);
							alert.setCanceledOnTouchOutside(false);
							alert.setIcon(R.drawable.custom_img);

							alert.setButton("ok", new DialogInterface.OnClickListener(){
								public void onClick(DialogInterface dialog, int which){
									// reloading activity
									Intent intent = getIntent();
									finish();
									startActivity(intent);
								}

							});
							alert.show();
						}else{
							Toast.makeText(RedeemActivity.this,response,Toast.LENGTH_SHORT).show();

						}

					}
				},
				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Toast.makeText(RedeemActivity.this,"Server Problem! Try After Some Time",Toast.LENGTH_SHORT).show();
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

	public void insert(String user_input, String gift_name, String amount, int points) {

		String deviceName = android.os.Build.MODEL;
		String deviceMan = android.os.Build.MANUFACTURER;

		Calendar c = Calendar.getInstance();
		SimpleDateFormat dd = new SimpleDateFormat("dd-MM-yyyy");
		String Current_Date = dd.format(c.getTime());

		String po = "" + points;
		insertToDatabase(user_input, deviceName, deviceMan, gift_name, amount, points, po, Current_Date);
	}

	private void insertToDatabase(String user_input, String deviceName, String deviceMan, String gift_name, String amount, int points, String po, String Current_Date) {

		final String val1 = user_input;
		final String val2 = deviceName;
		final String val3 = deviceMan;
		final String val4 = gift_name;
		final String val5 = amount;
		final int val6 = points;
		final String val7 = po;
		final String val8 = Current_Date;

		if (App.getInstance().isConnected()) {

			CustomRequest jsonReq = new CustomRequest(Request.Method.POST, Redeem_URL, null,
					new Response.Listener<JSONObject>() {
						@Override
						public void onResponse(JSONObject response) {

							spend(val6,"Redeemed for : "+val5+" - "+val4,val6+" Points Successfully Redeemed for "+val5);

						}
					}, new Response.ErrorListener() {
				@Override
				public void onErrorResponse(VolleyError error) {

					Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
				}
			}) {

				@Override
				protected Map<String, String> getParams() {
					Map<String, String> params = new HashMap<String, String>();
					params.put("username",App.getInstance().getUsername());
					params.put("user_input", val1);
					params.put("deviceName", val2);
					params.put("deviceMan", val3);
					params.put("gift_name", val4);
					params.put("amount", val5);
					params.put("points", val7);
					params.put("Current_Date", val8);

					/**
					 final String val1 = user_input;
					 final String val2 = deviceName;
					 final String val3 = deviceMan;
					 final String val4 = gift_name;
					 final String val5 = amount;
					 final int val6 = points;
					 final String val7 = po;
					 final String val8 = Current_Date;
					 */
					return params;
				}
			};

			App.getInstance().addToRequestQueue(jsonReq);

		} else {

			Toast.makeText(getApplicationContext(), R.string.msg_network_error, Toast.LENGTH_SHORT).show();
		}

		// ends here
	}
	// Override this method to do what you want when the menu is recreated

	@Override
	public boolean onPrepareOptionsMenu(final Menu menu) {
		// executing retreival task here
		String URL = Config.Base_Url+"get/gtuspo.php";

		StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						//System.out.println(response);
						// Toast.makeText(MainActivity.this,response,Toast.LENGTH_SHORT).show();
						menu.findItem(R.id.points).setTitle("Points :" + response);

					}
				},
				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Toast.makeText(RedeemActivity.this,error.toString(),Toast.LENGTH_SHORT).show();
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
		getMenuInflater().inflate(R.menu.menu_redeem, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

			case android.R.id.home:
				this.finish();
				break;

			case R.id.points:
				break;
			case R.id.sync:
				Intent intent = getIntent();
				finish();
				startActivity(intent);

			default:
				return super.onOptionsItemSelected(item);
		}
		return true;
	}
}