package com.droidoxy.easymoneyrewards.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.droidoxy.easymoneyrewards.Config;
import com.droidoxy.easymoneyrewards.MainActivity;
import com.droidoxy.easymoneyrewards.R;
import com.droidoxy.easymoneyrewards.adapter.HomeAdapter;


public class RedeemFragment extends ListFragment{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final HomeAdapter adapter=new HomeAdapter(getActivity().getBaseContext(), getResources().getStringArray(R.array.payout_titles), Config.payout_images,getResources().getStringArray(R.array.payout_description));
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
        });
    }
    public RedeemFragment() {}


    void Redeem(String Gift_Name, int Points_To_Redeem, String Gift_Amount, String Gift_Message) {

        Activity redeem = getActivity();
        if (redeem instanceof MainActivity) {
            MainActivity show = (MainActivity) redeem;
            show.Redeem(Gift_Name, Points_To_Redeem, Gift_Amount, Gift_Message);
        }
    }
}
