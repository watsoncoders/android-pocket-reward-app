package com.droidoxy.easymoneyrewards.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.droidoxy.easymoneyrewards.R;
import com.droidoxy.easymoneyrewards.activities.MainActivity;
import com.droidoxy.easymoneyrewards.activities.OfferDetailsActivity;
import com.droidoxy.easymoneyrewards.app.App;
import com.droidoxy.easymoneyrewards.model.Offers;

import java.util.List;

/**
 * Created by DroidOXY
 */
 
public class OffersAdapter extends RecyclerView.Adapter<OffersAdapter.MyViewHolder> {
    Context context;


    private List<Offers> moviesList;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView title, url, sub_title,amount;
        LinearLayout linearLayout, addlayout,SingleItem;


        public MyViewHolder(View view) {
            super(view);

            image = (ImageView) view.findViewById(R.id.image);
            title = (TextView) view.findViewById(R.id.title);
            url = (TextView) view.findViewById(R.id.view);
            sub_title = (TextView) view.findViewById(R.id.sub_title);
            amount = (TextView) view.findViewById(R.id.amount);
            linearLayout = (LinearLayout) view.findViewById(R.id.linear);
            SingleItem = (LinearLayout) view.findViewById(R.id.single_item);
            addlayout = view.findViewById(R.id.addlayout);

        }

    }


    public OffersAdapter(Context mainActivityContacts, List<Offers> moviesList) {
        this.moviesList = moviesList;
        this.context = mainActivityContacts;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_offers_list, parent, false);


        return new MyViewHolder(itemView);


    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        holder.addlayout.setVisibility(View.GONE);
        holder.linearLayout.setVisibility(View.VISIBLE);

        final Offers offers = moviesList.get(position);

        holder.title.setText(offers.getTitle());
        holder.url.setText(offers.getUrl());
        holder.amount.setText("+ " + offers.getAmount());

        // loading image using Glide library
        Glide.with(context).load(offers.getImage())
                //.apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                //.apply(RequestOptions.skipMemoryCacheOf(true))
                .into(holder.image);

        String offerStatus = App.getInstance().get(offers.getOfferid(),"none");

        if(offerStatus.equals("completed") || offerStatus.equals("rejected")){

            holder.SingleItem.setVisibility(View.GONE);

        }else{

            holder.sub_title.setText(offers.getSubtitle());
        }

        holder.SingleItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String title = offers.getTitle();
                String sub_title = offers.getSubtitle();
                String amount = offers.getAmount();
                String url = offers.getUrl();
                String image = offers.getImage();
                String OriginalAmount = offers.getOriginalAmount();
                String partner = offers.getPartner();
                String uniq_id = offers.getUniq_id();
                String offerid = offers.getOfferid();
                String bg_image = offers.getBg_image();
                String instructions_title = offers.getInst_title();
                String instruction_one = offers.getInst1();
                String instruction_two = offers.getInst2();
                String instruction_three = offers.getInst3();
                String instruction_four = offers.getInst4();
                Boolean webview = offers.getInappViewable();

                // ((MainActivity)context).offer_opener(title,offerid);
                Intent details = new Intent(context, OfferDetailsActivity.class);
                details.putExtra("uniq_id", uniq_id);
                details.putExtra("offerid", offerid);
                details.putExtra("app_name", title);
                details.putExtra("description", sub_title);
                details.putExtra("icon_url", image);
                details.putExtra("bg_image_url", bg_image);
                details.putExtra("amount", amount);
                details.putExtra("OriginalAmount", OriginalAmount);
                details.putExtra("link", url);
                details.putExtra("partner", partner);
                details.putExtra("instructionsTitle", instructions_title);
                details.putExtra("first_text", instruction_one);
                details.putExtra("second_text", instruction_two);
                details.putExtra("third_text", instruction_three);
                details.putExtra("fourth_text", instruction_four);
                details.putExtra("webview", webview);
                context.startActivity(details);

           }
        });

        /*

        if (position == 2) {
            holder.addlayout.setVisibility(View.VISIBLE);
            holder.linearLayout.setVisibility(View.GONE);
            View view = LayoutInflater.from(context).inflate(R.layout.layout_offers_view, holder.addlayout, false);
            holder.addlayout.addView(view);
        }

        */

    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

}


