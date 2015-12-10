package com.vladkanash.quick_finance.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.vladkanash.quick_finance.listener.ExchangeRatesResponseListener;
import com.vladkanash.quick_finance.R;


/**
 * Created by vladkanash on 12/9/15.
 */
public class ExchangeRatesListActivity extends AppCompatActivity {

    private final String URL_STRING = "http://www.nbrb.by/Services/XmlExRates.aspx?ondate=11/01/2015";

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        setContentView(R.layout.activity_exchange_rates_list);

        ListView lvMain = (ListView) findViewById(R.id.lv_rates);

        lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ExchangeRatesListActivity.this, ExchangeCalculatorActivity.class);
                TextView charCodeView = (TextView) view.findViewById(R.id.char_code);
                TextView rateView = (TextView) view.findViewById(R.id.rate);
                String charCode = charCodeView.getText().toString();
                double rate = Double.valueOf(rateView.getText().toString());

                Bundle extras = new Bundle();
                extras.putString("EXTRA_CHARCODE", charCode);
                extras.putDouble("EXTRA_RATE", rate);
                intent.putExtras(extras);

                startActivity(intent);
            }
        });

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        Response.Listener<String> listener = new ExchangeRatesResponseListener(this, lvMain);

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
