
/**
 * About Activity
 * 
 * @author DroidOXY
 */

package com.droidoxy.easymoneyrewards;

import android.view.MenuItem;
import android.view.Menu;
import android.net.Uri;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.droidoxy.easymoneyrewards.app.App;
import com.droidoxy.easymoneyrewards.constants.Constants;

/**
 * Created by yashDev | DroidOXY
 */

public class AboutActivity extends BaseActivity {
	Button btnLogout;
	TextView email,website;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);

		getSupportActionBar().setIcon(R.drawable.ic_back_icon);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setTitle(R.string.about);

		email = (TextView) findViewById(R.id.email);
		website = (TextView) findViewById(R.id.website);
		btnLogout = (Button) findViewById(R.id.btnLogout);

		email.setText(Config.com_email);
		website.setText(Config.com_website);

		validate();

		btnLogout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				//App.getInstance().logout();

				Intent i = new Intent(getApplicationContext(), ReferActivity.class);
				startActivity(i);
				finish();
				//ActivityCompat.finishAffinity(AboutActivity.this);
			}
		});

	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_about, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				this.finish();
				break;

			case R.id.share:
                shareURL();       
                return true;
				
			case R.id.buy:
				getcode();
				return true;

			default:
				return super.onOptionsItemSelected(item);
		}
		return true;
	}

	private void getcode() {
		Uri uri = Uri.parse(Constants.Code_License);
		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		startActivity(intent);
	}
	

    // sharing
    public void shareURL() {
		try
		{ Intent i = new Intent(Intent.ACTION_SEND);
			i.setType("text/plain");
			i.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
			String sAux = getString(R.string.share_text)+"\n";
			sAux = sAux + "https://play.google.com/store/apps/details?id="+getApplicationContext().getPackageName()+"\n";
			i.putExtra(Intent.EXTRA_TEXT, sAux);
			startActivity(Intent.createChooser(i,getString(R.string.choose_one)));
		}
		catch(Exception e)
		{ //e.toString();
		}
    }

	void validate(){
		if (Config.Base_Url.intern() == Constants.API_License && !BuildConfig.DEBUG && Constants.release) {

			App.getInstance().logout();
			Intent i = new Intent(getApplicationContext(), AppActivity.class);
			startActivity(i);
			finish();
			ActivityCompat.finishAffinity(AboutActivity.this);
		}
	}


}