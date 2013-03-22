package es.weso.demo.util;

import java.util.Date;
import java.util.GregorianCalendar;

public class StringToDateParser {
	
	/**
     * Parse date with format YYYY-MM-DD
     * @param date Date to parser into object Date
     * @return An object Dat with the correct date
     */
    public static Date parseCompleteDateExtend(String date) {
        String[] parts = date.split("-");
        int year = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        int day = Integer.parseInt(parts[2].substring(0, 2));
        return new GregorianCalendar(year, month, day).getTime();
    }

}
