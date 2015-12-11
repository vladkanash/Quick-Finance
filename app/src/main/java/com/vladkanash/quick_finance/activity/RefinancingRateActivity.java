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
import com.vladkanash.quick_finance.listener.RateChartResponseListener;
import com.vladkanash.quick_finance.listener.RefinancingRateResponseListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by vladkanash on 12/11/15.
 */
public class RefinancingRateActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        setContentView(R.layout.activity_refinancing_rate);

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        Response.Listener<String> listener = new RefinancingRateResponseListener(this);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                getURL(),
                listener,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
        queue.add(stringRequest);
    }

    private String getURL() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        String dateStr = df.format(c.getTime());
        return String.format("http://www.nbrb.by/Services/XmlRefRate.aspx?onDate=%s", dateStr);
    }
}
