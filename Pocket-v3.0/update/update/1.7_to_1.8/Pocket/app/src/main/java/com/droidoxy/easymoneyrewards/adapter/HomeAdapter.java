package com.droidoxy.easymoneyrewards.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.droidoxy.easymoneyrewards.R;

/**
 * Created by yash on 10/9/2017.
 */

public class HomeAdapter extends ArrayAdapter<String>
{

    Context context;

    int image[];

    String[] title;

    String[] description;

    public HomeAdapter(Context context, String[] titles, int[] imgs, String[] desc)
    {

        super(context, R.layout.single_row,titles);

        this.context=context;

        this.image=imgs;

        this.title=titles;

        this.description=desc;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View row=inflater.inflate(R.layout.home_fragment_single_row, parent, false);

        ImageView myimage= (ImageView) row.findViewById(R.id.icon);

        TextView mytitle= (TextView) row.findViewById(R.id.title);

        TextView mydesc= (TextView) row.findViewById(R.id.sub_title);

        myimage.setImageResource(image[position]);

        mytitle.setText(title[position]);

        mydesc.setText(description[position]);

        return row;

    }
}