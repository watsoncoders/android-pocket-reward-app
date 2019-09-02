package com.droidoxy.easymoneyrewards.adapters;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.droidoxy.easymoneyrewards.model.OfferWalls;

import java.util.List;

/**
 * Created by DroidOXY
 */
 
public class OfferWallsAdapter extends RecyclerView.Adapter<OfferWallsAdapter.MyViewHolder> {
    Context context;


    private List<OfferWalls> offerWallsList;


    public class MyViewHolder extends RecyclerView.ViewHolder {


        ImageView image;
        TextView name, sub_title;
        LinearLayout single_item;


        public MyViewHolder(View view) {
            super(view);

            image = (ImageView) view.findViewById(R.id.image);
            name = (TextView) view.findViewById(R.id.name);
            sub_title = (TextView) view.findViewById(R.id.sub_title);
            single_item = (LinearLayout) view.findViewById(R.id.single_item);

        }

    }


    public OfferWallsAdapter(Context mainActivityContacts, List<OfferWalls> offerWallsList) {
        this.offerWallsList = offerWallsList;
        this.context = mainActivityContacts;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_offerwall_grid, parent, false);

        return new MyViewHolder(itemView);


    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        final OfferWalls offerWalls = offerWallsList.get(position);

        holder.name.setText(offerWalls.getTitle());
        holder.sub_title.setText(offerWalls.getSubtitle());

        holder.sub_title.setVisibility(View.GONE);

        // loading image using Glide library
        Glide.with(context).load(offerWalls.getImage())
                .apply(new RequestOptions().override(60,60))
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                .apply(RequestOptions.skipMemoryCacheOf(true))
                .into(holder.image);

        holder.single_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ((MainActivity)context).openOfferWall(offerWalls.getTitle(),offerWalls.getSubtitle(),offerWalls.getType());

            } // END ON CLICK
        });

    }

    @Override
    public int getItemCount() {
        return offerWallsList.size();
    }

}


