package com.vladkanash.quick_finance.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.vladkanash.quick_finance.R;
import com.vladkanash.quick_finance.listener.ExchangeRatesResponseListener;
import com.vladkanash.quick_finance.listener.RateChartResponseListener;

import lecho.lib.hellocharts.view.LineChartView;

/**
 * Created by vladkanash on 12/10/15.
 */
public class RateChartActivity extends AppCompatActivity {

    private final String URL_STRING = "http://www.nbrb.by/Services/XmlExRatesDyn.aspx?curId=145&fromDate=11/1/2015&toDate=11/30/2015";

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        setContentView(R.layout.activity_rate_chart);

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        Response.Listener<String> listener = new RateChartResponseListener(this);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URL_STRING,
                listener,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
        queue.add(stringRequest);
    }

}
