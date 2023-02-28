package ph.easybus.ebmodels.utils;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateTimeUtils {

    /*
    public static final String ISO_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    public static Date convertUtcStringToDate(String dateString, String format) {
        Date date = Calendar.getInstance().getTime();
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.getDefault());
            formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
            date = formatter.parse(dateString);
        } catch (ParseException e) { e.printStackTrace(); }
        return date;
    }

    public static Date convertStringToDate(String dateString, String format) {
        Date date = Calendar.getInstance().getTime();
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.getDefault());
            //formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
            date = formatter.parse(dateString);
        } catch (ParseException e) { e.printStackTrace(); }
        return date;
    }

    public static String convertDateToString(Date date, String format) {
        String dateText = "";
        SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.getDefault());
        //formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
        dateText = formatter.format(date);
        return dateText;
    } */

    public static final String ISO_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    public static String toDateText(Date date, String format) {
        if (date == null) return "";
        SimpleDateFormat timeFormatter = new SimpleDateFormat(format, Locale.getDefault());
        return String.format("%s", timeFormatter.format(date));
    }

    public static String toDateTimeText(Date dateTime, String format) {
        if (dateTime == null) return "";
        SimpleDateFormat timeFormatter = new SimpleDateFormat(format, Locale.getDefault());
        DateFormatSymbols symbols = new DateFormatSymbols(Locale.getDefault());
        //symbols.setAmPmStrings(new String[] { "am", "pm" });
        symbols.setAmPmStrings(new String[] { "AM", "PM" });
        timeFormatter.setDateFormatSymbols(symbols);
        return String.format("%s", timeFormatter.format(dateTime));
    }

    public static String toISODate(Date date) {
        if (date == null) return "";
        SimpleDateFormat formatter = new SimpleDateFormat(ISO_FORMAT, Locale.getDefault());
        return String.format("%s", formatter.format(date));
    }

    public static String toISODateUtc(Date date) {
        if (date == null) return "";
        SimpleDateFormat formatter = new SimpleDateFormat(ISO_FORMAT, Locale.getDefault());
        formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
        return String.format("%s", formatter.format(date));
    }

    public static Date toDate(String date) {
        Date utcDate = Calendar.getInstance().getTime();
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(ISO_FORMAT, Locale.getDefault());
            utcDate = formatter.parse(date);
        } catch (ParseException | NullPointerException e) {
            e.printStackTrace();
        }
        return utcDate;
    }

    public static Date toDate(String date, String format) {
        Date utcDate = Calendar.getInstance().getTime();
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.getDefault());
            utcDate = formatter.parse(date);
        } catch (ParseException | NullPointerException e) {
            e.printStackTrace();
        }
        return utcDate;
    }

    public static Date toDateUtc(String date) {
        Date utcDate = Calendar.getInstance().getTime();
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(ISO_FORMAT, Locale.getDefault());
            formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
            utcDate = formatter.parse(date);
        } catch (ParseException | NullPointerException e) {
            e.printStackTrace();
        }
        return utcDate;
    }

    public static Date toDateUtc(String date, String format) {
        Date utcDate = Calendar.getInstance().getTime();
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.getDefault());
            formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
            utcDate = formatter.parse(date);
        } catch (ParseException | NullPointerException e) {
            e.printStackTrace();
        }
        return utcDate;
    }

    public static String toDateUtc(Date date, String format) {
        if (date == null) return "";
        SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.getDefault());
        formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
        return String.format("%s", formatter.format(date));
    }
}
