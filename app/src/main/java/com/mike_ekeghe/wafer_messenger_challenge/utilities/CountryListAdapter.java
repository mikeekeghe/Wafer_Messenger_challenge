package com.mike_ekeghe.wafer_messenger_challenge.utilities;

/**
 * Created by ravi on 26/09/17.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mike_ekeghe.wafer_messenger_challenge.R;

import java.util.List;

public class CountryListAdapter extends RecyclerView.Adapter<CountryListAdapter.MyViewHolder> {
    private Context context;
    private List<Item> CountryList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, currency, language;
        public RelativeLayout viewBackground, viewForeground;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            currency = view.findViewById(R.id.currency);
            language = view.findViewById(R.id.language);
            viewBackground = view.findViewById(R.id.view_background);
            viewForeground = view.findViewById(R.id.view_foreground);
        }
    }


    public CountryListAdapter(Context context, List<Item> CountryList) {
        this.context = context;
        this.CountryList = CountryList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.country_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Item item = CountryList.get(position);
        holder.title.setText(item.getTitle());
        holder.currency.setText(item.getCurrency());
        holder.language.setText("₹" + item.getLanguage());

    }
    public void removeItem(int position) {
        CountryList.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        return CountryList.size();
    }



    public void restoreItem(Item item, int position) {
        CountryList.add(position, item);
        // notify item added by position
        notifyItemInserted(position);
    }
}
