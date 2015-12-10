package com.vladkanash.quick_finance.parser;

import android.util.Xml;

import com.vladkanash.quick_finance.entity.ExchangeRate;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vladkanash on 12/9/15.
 */
public class ExchangeRatesXmlParser {

    private static final String ID_ATTR = "Id";
    private static final String NAME_TAG = "Name";
    private static final String CHARCODE_TAG = "CharCode";
    private static final String NUMCODE_TAG = "NumCode";
    private static final String SCALE_TAG = "Scale";
    private static final String RATE_TAG = "Rate";
    private static final String CURRENCY_TAG = "Currency";
    private static final String EXRATES_TAG = "DailyExRates";

    private static final String ns = null;

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

        parser.require(XmlPullParser.START_TAG, ns, EXRATES_TAG);
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            String name = parser.getName();
            // Starts by looking for the entry tag
            if (name.equals(CURRENCY_TAG)) {
                int id = Integer.valueOf(parser.getAttributeValue(null, ID_ATTR));
                entries.add(readEntry(parser, id));
            } else {
                skip(parser);
            }
        }
        return entries;
    }

    private ExchangeRate readEntry(XmlPullParser parser, int id) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, CURRENCY_TAG);
        String charCode = null;
        String rateName = null;
        int numCode = 0;
        int scale = 0;
        double rate = 0.0;

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            switch (name) {
                case NUMCODE_TAG:
                    numCode = Integer.valueOf(readProperty(parser, NUMCODE_TAG));
                    break;
                case CHARCODE_TAG:
                    charCode = readProperty(parser, CHARCODE_TAG);
                    break;
                case SCALE_TAG:
                    scale = Integer.valueOf(readProperty(parser, SCALE_TAG));
                    break;
                case NAME_TAG:
                    rateName = readProperty(parser, NAME_TAG);
                    break;
                case RATE_TAG:
                    rate = Double.valueOf(readProperty(parser, RATE_TAG));
                    break;
                default:
                    skip(parser);
                    break;
            }
        }
        return new ExchangeRate(id, numCode, scale, charCode, rateName, rate);
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
