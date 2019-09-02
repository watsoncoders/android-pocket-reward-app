package com.droidoxy.easymoneyrewards.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.droidoxy.easymoneyrewards.R;
import com.droidoxy.easymoneyrewards.app.App;
import com.droidoxy.easymoneyrewards.model.UserHistory;

import java.util.List;


public class UserRedeemHistoryAdapter extends BaseAdapter {
	private Activity activity;
	private LayoutInflater inflater;
	private List<UserHistory> historyItems;
	ImageLoader imageLoader = App.getInstance().getImageLoader();

	public UserRedeemHistoryAdapter(Activity activity, List<UserHistory> movieItems) {
		this.activity = activity;
		this.historyItems = movieItems;
	}

	@Override
	public int getCount() {
		return historyItems.size();
	}

	@Override
	public Object getItem(int location) {
		return historyItems.get(location);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (inflater == null)
			inflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (convertView == null)
			convertView = inflater.inflate(R.layout.single_row_user_redeem_history, null);

		if (imageLoader == null)
			imageLoader = App.getInstance().getImageLoader();
		NetworkImageView thumbNail = (NetworkImageView) convertView.findViewById(R.id.thumbnail);
		TextView title = (TextView) convertView.findViewById(R.id.title);
		TextView rating = (TextView) convertView.findViewById(R.id.rating);
		TextView genre = (TextView) convertView.findViewById(R.id.genre);
		TextView year = (TextView) convertView.findViewById(R.id.releaseYear);

		// getting movie data for the row
		UserHistory m = historyItems.get(position);

		// thumbnail image
		thumbNail.setImageUrl(m.getThumbnailUrl(), imageLoader);

		// title
		title.setText(m.getTitle());

		// rating
//		rating.setText("Rating: " + String.valueOf(m.getRating()));

		rating.setText(m.getRating());

		// genre
		genre.setText("Status : " + m.getGenre());

		// release year
		//	year.setText(String.valueOf(m.getYear()));

		year.setText(m.getYear());

		return convertView;
	}

}