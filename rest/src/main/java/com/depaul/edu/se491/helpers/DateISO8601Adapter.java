package com.depaul.edu.se491.helpers;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Tom Mitic on 2/13/15.
 *
 * A date helper to put all dates in ISO 8601 format
 */
public class DateISO8601Adapter extends XmlAdapter<String, Date> {

    private static final String ISO_8601_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSZZ";
    private SimpleDateFormat dateFormat;

    public DateISO8601Adapter() {
        super();

        dateFormat = new SimpleDateFormat(ISO_8601_DATE_FORMAT);
    }

    @Override
    public Date unmarshal(String v) throws Exception {
        return dateFormat.parse(v);
    }

    @Override
    public String marshal(Date v) {
        return dateFormat.format(v);
    }
}
