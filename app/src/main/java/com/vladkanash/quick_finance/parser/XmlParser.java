package com.vladkanash.quick_finance.parser;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by vladkanash on 12/11/15.
 */
public interface XmlParser {

    public Object parse(InputStream in) throws XmlPullParserException, IOException;
}
