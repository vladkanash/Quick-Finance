package com.vladkanash.quick_finance.listener;

import android.app.Activity;
import android.widget.ListView;

import com.android.volley.Response.Listener;
import com.vladkanash.quick_finance.adapter.ExchangeRatesListAdapter;
import com.vladkanash.quick_finance.entity.ExchangeRate;
import com.vladkanash.quick_finance.parser.ExchangeRatesXmlParser;

import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;

/**
 * Created by vladkanash on 12/10/15.
 */
public class ExchangeRatesResponseListener implements Listener<String> {

    private ArrayList ratesList;
    private ListView view;
    private Activity activity;

    public ExchangeRatesResponseListener(Activity listActivity, ListView lvMain) {
        this.view = lvMain;
        this.activity = listActivity;
    }

    public ArrayList getRatesList() {
        return ratesList;
    }

    public void setRatesList(ArrayList<ExchangeRate> ratesList) {
        this.ratesList = ratesList;
    }

    @Override
    public void onResponse(String response) {
        String s;
        try {
            s = new String(response.getBytes("ISO-8859-1"), "UTF-8");

            ExchangeRatesXmlParser xmlParser = new ExchangeRatesXmlParser();
            InputStream stream = new ByteArrayInputStream(s.getBytes("UTF-8"));
            ratesList = (ArrayList)xmlParser.parse(stream);
            setListAdapter();
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
    }

    private void setListAdapter() {
        ExchangeRatesListAdapter adapter = new ExchangeRatesListAdapter(activity, ratesList);
        view.setAdapter(adapter);
    }

}
