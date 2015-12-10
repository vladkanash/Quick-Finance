package com.vladkanash.quick_finance.adapter;

/**
 * Created by vladkanash on 12/10/15.
 */

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.vladkanash.quick_finance.R;
import com.vladkanash.quick_finance.entity.ExchangeRate;

public class ExchangeRatesListAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<ExchangeRate> movieItems;


    public ExchangeRatesListAdapter(Activity activity, List<ExchangeRate> movieItems) {
        this.activity = activity;
        this.movieItems = movieItems;
    }

    @Override
    public int getCount() {
        return movieItems.size();
    }

    @Override
    public Object getItem(int location) {
        return movieItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.exchange_list_row, null);


        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView charCode = (TextView) convertView.findViewById(R.id.char_code);
        TextView rate = (TextView) convertView.findViewById(R.id.rate);

        // getting movie data for the row
        ExchangeRate item = movieItems.get(position);

        // title
        name.setText(item.getName());

        // charCode
        charCode.setText(item.getCharCode());

        // rate
        rate.setText(String.valueOf(item.getRate()));

        return convertView;
    }

}