package com.vladkanash.quick_finance.parser;

import android.util.Xml;

import com.vladkanash.quick_finance.entity.RefinancingRate;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by vladkanash on 12/11/15.
 */
public class RefinancingRateXmlParser implements XmlParser {

    private static final String REFRATE_TAG = "RefRate";
    private static final String ITEM_TAG = "Item";
    private static final String DATE_TAG = "Date";
    private static final String VALUE_TAG = "Value";

    private static final String ns = null;

    private RefinancingRate entry;

    @Override
    public Object parse(InputStream in) throws XmlPullParserException, IOException {
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

    private Object readFeed(XmlPullParser parser)  throws XmlPullParserException, IOException {

        parser.require(XmlPullParser.START_TAG, ns, REFRATE_TAG);
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            String name = parser.getName();
            // Starts by looking for the entry tag
            if (name.equals(ITEM_TAG)) {
                entry = readEntry(parser);
            } else {
                skip(parser);
            }
        }
        return entry;
    }

    private RefinancingRate readEntry(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, ITEM_TAG);
        String date = null;
        int rate = 0;

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            switch (name) {
                case VALUE_TAG:
                    rate = Integer.valueOf(readProperty(parser, VALUE_TAG));
                    break;
                case DATE_TAG:
                    date = readProperty(parser, DATE_TAG);
                    break;
                default:
                    skip(parser);
                    break;
            }
        }
        return new RefinancingRate(date, rate);
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
