package com.vladkanash.quick_finance.listener;

import android.app.Activity;
import android.widget.TextView;

import com.android.volley.Response;
import com.vladkanash.quick_finance.R;
import com.vladkanash.quick_finance.activity.RefinancingRateActivity;
import com.vladkanash.quick_finance.entity.RefinancingRate;
import com.vladkanash.quick_finance.parser.RefinancingRateXmlParser;

import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by vladkanash on 12/11/15.
 */
public class RefinancingRateResponseListener implements Response.Listener<String> {

    private RefinancingRate rate;
    private Activity activity;

    public RefinancingRateResponseListener(RefinancingRateActivity refinancingRateActivity) {
        this.activity = refinancingRateActivity;
    }

    @Override
    public void onResponse(String response) {
        String s;
        try {
            s = new String(response.getBytes("ISO-8859-1"), "UTF-8");

            RefinancingRateXmlParser xmlParser = new RefinancingRateXmlParser();
            InputStream stream = new ByteArrayInputStream(s.getBytes("UTF-8"));
            rate = (RefinancingRate) xmlParser.parse(stream);
            setRateValue();
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
    }

    private void setRateValue() {
        TextView rateView = (TextView) activity.findViewById(R.id.rate_text);
        TextView dateView = (TextView) activity.findViewById(R.id.date_text);

        rateView.setText(rate.getRate() + "%");
        dateView.setText(rate.getDate());
    }
}
