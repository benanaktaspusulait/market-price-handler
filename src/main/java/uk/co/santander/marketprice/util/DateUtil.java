package uk.co.santander.marketprice.util;

import lombok.extern.slf4j.Slf4j;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
public class DateUtil {

    public static final String DATE_FORMAT = "dd-MM-yyyy HH:mm:ss:SSS";

    public static String convert(Date date, String dateFormat) {
    	if(date == null) {
    		return null;
    	}
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return sdf.format(date);
    }

    public static Date convertToDate(String date, String dateFormat) throws ParseException {
            DateFormat formatter = new SimpleDateFormat(dateFormat);
            return formatter.parse(date);
    }


}
