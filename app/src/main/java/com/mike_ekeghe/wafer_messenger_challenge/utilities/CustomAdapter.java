package com.mike_ekeghe.wafer_messenger_challenge.utilities;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.mike_ekeghe.wafer_messenger_challenge.MainActivity;
import com.mike_ekeghe.wafer_messenger_challenge.R;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    ArrayList<String> title;
    ArrayList<String> currency;
    ArrayList<String> language;
    private static final String TAG = "CUSTOM ADAPTOR";


    Context context;

    public CustomAdapter(Context context, ArrayList<String> title,
                         ArrayList<String> currency, ArrayList<String> language
    ) {
        this.context = context;
        this.title = title;
        this.currency = currency;
        this.language = language;

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.country_item, parent, false);
        MyViewHolder vh = new MyViewHolder(v); // pass the view to View Holder
        return vh;
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        // set the data in items
        Log.d(TAG, "title.get(position) is : " + title.get(position));
        Log.d(TAG, "currency.get(position) is : " + currency.get(position));
        Log.d(TAG, "language.get(position) is : " + language.get(position));

        holder.tvTitle.setText(title.get(position));
        holder.tvCurrency.setText(currency.get(position));
        holder.tvLanguage.setText(language.get(position));


    }







/*        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // display a toast with person tvTitle on item click
               *//* Bundle bundle = new Bundle();
                bundle.putString("DISPLAY_NAME", title.get(position));
                bundle.putString("DESCRIPTION", currency.get(position));*//*
            }
        });*/




    @Override
    public int getItemCount() {
        return title.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvCurrency, tvLanguage;// init the item view's

        public MyViewHolder(View itemView) {
            super(itemView);

            // get the reference of item view's
            tvTitle = itemView.findViewById(R.id.title);
            tvCurrency = itemView.findViewById(R.id.currency);
            tvLanguage = itemView.findViewById(R.id.language);
        }
    }
}