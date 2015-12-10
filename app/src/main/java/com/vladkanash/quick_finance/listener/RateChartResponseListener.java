package com.vladkanash.quick_finance.listener;

import android.app.Activity;
import android.graphics.Color;

import com.android.volley.Response.Listener;
import com.vladkanash.quick_finance.R;
import com.vladkanash.quick_finance.adapter.ExchangeRatesListAdapter;
import com.vladkanash.quick_finance.entity.ChartRate;
import com.vladkanash.quick_finance.entity.ExchangeRate;
import com.vladkanash.quick_finance.parser.ChartRatesXmlParser;
import com.vladkanash.quick_finance.parser.ExchangeRatesXmlParser;

import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import lecho.lib.hellocharts.formatter.SimpleAxisValueFormatter;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.LineChartView;

/**
 * Created by vladkanash on 12/10/15.
 */
public class RateChartResponseListener implements Listener<String> {

    private ArrayList ratesList;
    private Activity activity;
    final private String COLOR_DARK = "#009688";

    public RateChartResponseListener(Activity listActivity) {
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

            ChartRatesXmlParser xmlParser = new ChartRatesXmlParser();
            InputStream stream = new ByteArrayInputStream(s.getBytes("UTF-8"));
            ratesList = (ArrayList)xmlParser.parse(stream);
            setChartData();
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
    }

    private void setChartData() {

        List<PointValue> values = new ArrayList<>();
        Calendar cal = Calendar.getInstance();

        for (ChartRate item : (ArrayList<ChartRate>)ratesList) {
            cal.setTime(item.getDate());
            values.add(new PointValue(cal.get(Calendar.DAY_OF_MONTH), (int) item.getRate()));
        }

        Line line = new Line(values);
        List<Line> lines = new ArrayList<>();
        line.setHasPoints(false);
        line.setFilled(true);
        line.setCubic(false);
        line.setColor(Color.GRAY);
        line.setStrokeWidth(1);
        lines.add(line);

        LineChartData data = new LineChartData();
        data.setLines(lines);

        data.setAxisXBottom(new Axis()
                .setTextColor(Color.GRAY)
                .setMaxLabelChars(4)
                .setFormatter(new SimpleAxisValueFormatter())
                .setHasLines(true)
                .setHasTiltedLabels(false));

        data.setAxisYRight(new Axis()
                .setTextColor(Color.GRAY)
                .setMaxLabelChars(5)
                .setHasLines(true)
                .setFormatter(new SimpleAxisValueFormatter()));

        LineChartView chart = (LineChartView) activity.findViewById(R.id.chart);
        chart.setLineChartData(data);

//        Viewport v = chart.getMaximumViewport();
//        v.set(v.left, tempoRange, v.right, 0);
//        chart.setMaximumViewport(v);
//        chart.setCurrentViewport(v);
    }

}
