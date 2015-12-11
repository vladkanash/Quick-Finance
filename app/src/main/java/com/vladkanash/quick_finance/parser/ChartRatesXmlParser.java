package com.vladkanash.quick_finance.parser;

import android.util.Xml;

import com.vladkanash.quick_finance.entity.ChartRate;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by vladkanash on 12/9/15.
 */
public class ChartRatesXmlParser implements XmlParser{

    private static final String CURRENCY_TAG = "Currency";
    private static final String RECORD_TAG = "Record";
    private static final String RATE_TAG = "Rate";
    private static final String DATE_ATTR = "Date";

    private static final String ns = null;

    @Override
    public List parse(InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return readFeed(parser);
        } finally {
            in.close();
        }
    }

    private List readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
        List entries = new ArrayList();

        parser.require(XmlPullParser.START_TAG, ns, CURRENCY_TAG);
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            String name = parser.getName();
            // Starts by looking for the entry tag
            if (name.equals(RECORD_TAG)) {
                String dateStr = parser.getAttributeValue(null, DATE_ATTR);
                entries.add(readRecord(parser, dateStr));
            } else {
                skip(parser);
            }
        }
        return entries;
    }

    private ChartRate readRecord(XmlPullParser parser, String dateStr) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, RECORD_TAG);
        double rate = 0.0;
        Date date = null;

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            switch (name) {
                case RATE_TAG:
                    rate = Double.valueOf(readProperty(parser, RATE_TAG));
                    break;
                default:
                    skip(parser);
                    break;
            }
        }

        DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        try {
            date = formatter.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return new ChartRate(rate, date);
    }


    private String readProperty(XmlPullParser parser, String propName) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, propName);
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, propName);
        return title;
    }

    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }


}
