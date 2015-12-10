package com.vladkanash.quick_finance.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.vladkanash.quick_finance.R;

/**
 * Created by vladkanash on 12/10/15.
 */

public class ExchangeCalculatorActivity extends AppCompatActivity {

    private double rate;

    @Override
   public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        setContentView(R.layout.activity_exchange_calculator);

        this.rate = extras.getDouble("EXTRA_RATE");
        String charCode = extras.getString("EXTRA_CHARCODE");

        TextView charCodeView = (TextView) findViewById(R.id.text_rate);
        charCodeView.setText(charCode);



        final EditText editBYR = (EditText) findViewById(R.id.edit_byr);
        final EditText editRate = (EditText) findViewById(R.id.edit_rate);

        editBYR.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!editRate.hasFocus()) {
                    if (s.length() != 0 ) {
                        Long BYRCount = Long.parseLong(s.toString());
                        Long L = Math.round(BYRCount / rate);
                        editRate.setText(String.format("%d", L.longValue()));
                    } else {
                        editRate.setText("0");
                    }
                }
            }
        });
        editRate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!editBYR.hasFocus()) {
                    if (s.length() != 0) {
                        Long rateCount = Long.parseLong(s.toString());
                        Long L = Math.round(rateCount * rate);
                        editBYR.setText(String.format("%d", L.longValue()));
                    } else {
                        editBYR.setText("0");
                    }
                }
            }
        });

    }
}
