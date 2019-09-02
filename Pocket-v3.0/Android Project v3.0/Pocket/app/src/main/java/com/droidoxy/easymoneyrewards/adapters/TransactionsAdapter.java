package com.droidoxy.easymoneyrewards.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
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
import com.droidoxy.easymoneyrewards.model.Transactions;

import java.util.List;


/**
 * Created by DroidOXY
 */
 
public class TransactionsAdapter extends RecyclerView.Adapter<TransactionsAdapter.ViewHolder>{

    private Context context;
    private List<Transactions> listItem;
    String PrevDate;

    public TransactionsAdapter(Context context, List<Transactions> listItem) {
        this.context = context;
        this.listItem = listItem;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transactions_list,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,final int position) {


        final Transactions transaction = listItem.get(position);

        String date = transaction.getTnDate();
        String TnType = transaction.getTnType();
        String status = transaction.getStatus();

        if(position == 0){

            PrevDate = "empty";

        }else{

            PrevDate = listItem.get(position - 1).getTnDate();
        }

        holder.tnName.setText(transaction.getTnName());
        holder.tncat.setText("#id : " + transaction.getTnId());

        if (date.intern() != PrevDate.intern()){

            holder.date.setText(date);
            holder.date.setVisibility(View.VISIBLE);
        }

        if (TnType.equals("cr")){

            holder.amount.setText("+ " + transaction.getAmount());
            holder.amount.setTextColor(context.getResources().getColor(R.color.green));

        }else if(TnType.equals("db")){

            holder.amount.setText("- " + transaction.getAmount());
            holder.amount.setTextColor(context.getResources().getColor(R.color.red));
        }

        if(status.equals("0")){

            ((GradientDrawable)holder.statusName.getBackground()).setColor(context.getResources().getColor(R.color.yellow));
            holder.statusName.setText(context.getResources().getString(R.string.pending));

        }else if(status.equals("1")){

            ((GradientDrawable)holder.statusName.getBackground()).setColor(context.getResources().getColor(R.color.green_light));
            holder.statusName.setText(context.getResources().getString(R.string.success));

        }else if(status.equals("3")){

            ((GradientDrawable)holder.statusName.getBackground()).setColor(context.getResources().getColor(R.color.red));
            holder.statusName.setText(context.getResources().getString(R.string.rejected));

        }else {

            holder.statusName.setText(context.getResources().getString(R.string.app_currency));
        }


        /** Glide.with(context).load(transaction.getImage())
                .apply(new RequestOptions().override(60,60))
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                .apply(RequestOptions.skipMemoryCacheOf(true))
                .into(holder.image);  **/

    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView date,tnName,tncat,amount,statusName;
        ImageView image;
        LinearLayout SingleItem;
        public ViewHolder(View itemView) {
            super(itemView);

            date = itemView.findViewById(R.id.date);
            tnName = itemView.findViewById(R.id.tnName);
            tncat = itemView.findViewById(R.id.tnType);
            amount = itemView.findViewById(R.id.amount);
            statusName = itemView.findViewById(R.id.statusName);
            image = itemView.findViewById(R.id.image);
            SingleItem = itemView.findViewById(R.id.SingleItem);
        }
    }
}
