package com.droidoxy.easymoneyrewards.adapters;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
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
import com.droidoxy.easymoneyrewards.activities.FragmentsActivity;
import com.droidoxy.easymoneyrewards.app.App;
import com.droidoxy.easymoneyrewards.model.Payouts;

import java.util.List;

/**
 * Created by DroidOXY
 */

public class PayoutsAdapter extends RecyclerView.Adapter<PayoutsAdapter.ViewHolder>{

    private Context context;
    private List<Payouts> listItem;

    public PayoutsAdapter(Context context, List<Payouts> listItem) {
        this.context = context;
        this.listItem = listItem;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_redeem_list,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        String PrevItem;
        final Payouts payout = listItem.get(position);

        final String title = payout.getPayoutName();
        final String subtitle = payout.getSubtitle();
        final String message = payout.getPayoutMessage();
        final String amount = payout.getAmount();
        final String points = payout.getReqPoints();
        final String payoutId = payout.getPayoutId();
        final String status = payout.getStatus();
        final String image = payout.getImage();

        if(App.getInstance().get("REDEEM_DISPLAY_TITLE",false)){

            if(position == 0){

                PrevItem = "empty";

            }else{

                PrevItem = listItem.get(position - 1).getPayoutName();
            }

            if (!title.equals(PrevItem)){

                holder.date.setText(title);
                holder.date.setVisibility(View.VISIBLE);
            }

        }

        holder.tnName.setText(title);
        holder.tncat.setText(subtitle);

        if(App.getInstance().get("REDEEM_DISPLAY_AMOUNT",true)){
            holder.amount.setText(amount);
        }else{
            holder.amount.setText(context.getResources().getString(R.string.redeem));
        }

        if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN && App.getInstance().get("REDEEM_DISPLAY_AMOUNT_BG",false)){

            holder.amount.setTextColor(context.getResources().getColor(R.color.white));
            holder.amount.setTextSize(14);
            holder.amount.setBackground(ContextCompat.getDrawable(context,R.drawable.rounded_bg));
            //holder.amount.setBackground(context.getDrawable(R.drawable.rounded_bg));
        }

        Glide.with(context).load(image)
                .apply(new RequestOptions().override(60,60))
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                .apply(RequestOptions.skipMemoryCacheOf(true))
                .into(holder.image);


        holder.SingleItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ((FragmentsActivity)context).Redeem(title, subtitle, message, amount, points, payoutId, status, image);

            }
        });

    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView date,tnName,tncat,amount;
        ImageView image;
        LinearLayout SingleItem;
        ViewHolder(View itemView) {
            super(itemView);

            date = itemView.findViewById(R.id.date);
            tnName = itemView.findViewById(R.id.tnName);
            tncat = itemView.findViewById(R.id.tnType);
            amount = itemView.findViewById(R.id.amount);
            image = itemView.findViewById(R.id.image);
            SingleItem = itemView.findViewById(R.id.SingleItem);
        }
    }
}
