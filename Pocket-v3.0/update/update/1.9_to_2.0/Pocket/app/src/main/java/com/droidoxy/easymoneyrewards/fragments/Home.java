package com.droidoxy.easymoneyrewards.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import com.droidoxy.easymoneyrewards.AboutActivity;
import com.droidoxy.easymoneyrewards.Config;
import com.droidoxy.easymoneyrewards.InstructionsActivity;
import com.droidoxy.easymoneyrewards.MainActivity;
import com.droidoxy.easymoneyrewards.R;
import com.droidoxy.easymoneyrewards.RedeemActivity;
import com.droidoxy.easymoneyrewards.ReferActivity;
import com.droidoxy.easymoneyrewards.adapter.HomeAdapter;

/**
 * Created by yashDev | DroidOXY
 */

public class Home extends ListFragment{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final HomeAdapter adapter=new HomeAdapter(getActivity().getBaseContext(), getResources().getStringArray(R.array.titles), Config.images,getResources().getStringArray(R.array.description));
        setListAdapter(adapter);

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setEmptyText(getString(R.string.loading));
        getListView().setDivider(null);
        getListView().setBackgroundColor(getResources().getColor(R.color.home_bg_color));
        getListView().setFastScrollEnabled(true);
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                switch (position) {

                    // Daily Checkin
                    case 0:

                        Activity daily = getActivity();
                        if(daily instanceof MainActivity){
                            MainActivity show = (MainActivity) daily;
                            show.daily_checkin();
                        }

                        break;

                    // Video ads SuperSonic
                    case 1:

                        Activity super_sonic_videos = getActivity();
                        if(super_sonic_videos instanceof MainActivity){
                            MainActivity show = (MainActivity) super_sonic_videos;
                            show.super_sonic_videos();
                        }

                        break;

                    // Video ads Nativex
                    case 2:

                        Activity nativex_videos = getActivity();
                        if(nativex_videos instanceof MainActivity){
                            MainActivity show = (MainActivity) nativex_videos;
                            show.nativex_videos();
                        }

                        break;

                    // OfferToro OfferWall
                    case 3:

                        Activity offer_toro_offerwall = getActivity();
                        if(offer_toro_offerwall instanceof MainActivity){
                            MainActivity show = (MainActivity) offer_toro_offerwall;
                            show.offer_toro_offerwall();
                        }

                        break;

                    // AdGateMedia OfferWall
                    case 4:

                        Activity adGateMedia_offerwall = getActivity();
                        if(adGateMedia_offerwall instanceof MainActivity){
                            MainActivity show = (MainActivity) adGateMedia_offerwall;
                            show.adgateMedia_offerwall();
                        }

                        break;

                    // Fyber OfferWall
                    case 5:

                        Activity fyber_offerwall = getActivity();
                        if(fyber_offerwall instanceof MainActivity){
                            MainActivity show = (MainActivity) fyber_offerwall;
                            show.fyber_offerwall();
                        }

                        break;

                    // SuperSonic OfferWall
                    case 6:

                        Activity super_sonic_offerwall = getActivity();
                        if(super_sonic_offerwall instanceof MainActivity){
                            MainActivity show = (MainActivity) super_sonic_offerwall;
                            show.super_sonic_offerwall();
                        }
                        break;

                    // Adscend Media Offerwall
                    case 7:

                        Activity adscend_offerwall = getActivity();
                        if(adscend_offerwall instanceof MainActivity){
                            MainActivity show = (MainActivity) adscend_offerwall;
                            show.adscend_offerwall();
                        }

                        break;

                    // Super Rewards Offerwall
                    case 8:

                        Activity super_rewards_offerwall = getActivity();
                        if(super_rewards_offerwall instanceof MainActivity){
                            MainActivity show = (MainActivity) super_rewards_offerwall;
                            show.super_rewards_offerwall();
                        }

                        break;

                    // Offer Wall ads Adxmi(Apps)
                    case 9:

                        Activity adxmi_offerwall = getActivity();
                        if(adxmi_offerwall instanceof MainActivity){
                            MainActivity show = (MainActivity) adxmi_offerwall;
                            show.adxmi_offerwall();
                        }

                        break;

                    // NativeX OfferWall
                    case 10:

                        Activity nativex_offerwall = getActivity();
                        if(nativex_offerwall instanceof MainActivity){
                            MainActivity show = (MainActivity) nativex_offerwall;
                            show.nativex_offerwall();
                        }

                        break;

                    // White Mobi OfferWall
                    case 11:

                        Activity white_mobi_offerwall = getActivity();
                        if(white_mobi_offerwall instanceof MainActivity){
                            MainActivity show = (MainActivity) white_mobi_offerwall;
                            show.white_mobi_offerwall();
                        }

                        break;

                    // Redeem
                    case 12:
                        Intent redeem = new Intent(getContext(), RedeemActivity.class);
                        startActivity(redeem);
                        break;

                    // Instructions
                    case 13:
                        Intent i = new Intent(getContext(), InstructionsActivity.class);
                        startActivity(i);
                        break;

                    // Refer & Earn
                    case 14:
                        Intent refer = new Intent(getContext(), ReferActivity.class);
                        startActivity(refer);

                        break;

                    // About
                    case 15:
                        Intent about = new Intent(getContext(),AboutActivity.class);
                        startActivity(about);
                        break;
                }
            }
        });
    }
    public Home() {}
}
