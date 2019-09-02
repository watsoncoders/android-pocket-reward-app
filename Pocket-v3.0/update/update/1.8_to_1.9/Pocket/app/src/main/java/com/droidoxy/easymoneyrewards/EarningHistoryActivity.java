
/**
 * Rewards History Activity
 *
 * @author yashDev | DroidOXY
 */

package com.droidoxy.easymoneyrewards;

import android.app.ProgressDialog;
import android.view.MenuItem;
import android.view.Menu;
import android.net.Uri;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.droidoxy.easymoneyrewards.adapter.UserHistoryAdapter;
import com.droidoxy.easymoneyrewards.app.App;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.droidoxy.easymoneyrewards.model.UserHistory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

public class EarningHistoryActivity extends BaseActivity {

    // Log tag
    private static final String TAG = EarningHistoryActivity.class.getSimpleName();

    private ProgressDialog pDialog;
    private List<UserHistory> historyList = new ArrayList<UserHistory>();
    private ListView listView;
    private PrefManager prf;
    private UserHistoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_rewards);

        prf = new PrefManager(this);

        getSupportActionBar().setIcon(R.drawable.ic_back_icon);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.earning_history);


        listView = (ListView) findViewById(R.id.list);
        TextView emptyText = (TextView) findViewById(R.id.empty);
        emptyText.setText(getString(R.string.no_rewards_yet));
        adapter = new UserHistoryAdapter(EarningHistoryActivity.this, historyList);
        listView.setAdapter(adapter);
        listView.setEmptyView(emptyText);
        listView.setDivider(null);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage(getString(R.string.loading));
        pDialog.show();

        // changing action bar color
        // getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1b1b1b")));

        JsonArrayRequest historyReq = new JsonArrayRequest(Config.Base_Url+"api/earning_history.php?username="+App.getInstance().getUsername(),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        hidePDialog();

                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject obj = response.getJSONObject(i);
                                UserHistory history = new UserHistory();
                                history.setTitle(obj.getString("type"));
                                history.setRating(obj.getString("date"));
                                history.setThumbnailUrl(Config.Base_Url+"images/reward.png");
                                history.setYear(obj.getString("points"));
                                //history.setGenre(obj.getString("time"));

                                historyList.add(history);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hidePDialog();

            }
        });

        // Adding request to request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(historyReq);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_empty, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                break;

            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }


}