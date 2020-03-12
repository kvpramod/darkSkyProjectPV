package com.darksky.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;


public class DateUtils {

    private static Calendar cal = Calendar.getInstance();
    private static Locale locale = Locale.getDefault();
    private static String year;
    private static String month;
    private static String day;
    private static String[] dayHalfArray = {"am" , "pm"};

    public static String getCurrentFullDateTime() {
        return cal.getTime().toString();
    }

    public static String getDayNameFull() {
        return cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, locale);
    }

    public static String getDayNameShort() {
        return cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, locale);
    }

    public static String getMonthNameFull() {
        return cal.getDisplayName(Calendar.MONTH, Calendar.LONG, locale);
    }

    public static String getMonthNameShort() {
        return cal.getDisplayName(Calendar.MONTH, Calendar.SHORT, locale);
    }

    public static int getMonthInNumber() { return cal.get(Calendar.MONTH); }

    public static int getDayOfTheMonth() {
        return cal.get(Calendar.DATE);
    }

    public static String getCurrentTimeInHHMMSS() {
        String hour = Integer.toString(cal.get(Calendar.HOUR_OF_DAY));
        String minute = Integer.toString(cal.get(Calendar.MINUTE));
        String second = Integer.toString(cal.get(Calendar.SECOND));
        return hour+":"+minute+":"+second;
    }

    public static String getTimeZone() {
        return cal.getTimeZone().getID();
    }

    public static int getCurrentYear() {
        return cal.get(Calendar.YEAR);
    }

    public static void setYear(int year) {
        cal.set(Calendar.YEAR, year);
    }

    public static void setTime(int hour, int min, int sec) {
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, min);
        cal.set(Calendar.SECOND, sec);
    }

    public static void incrementByDays(int days) {
        cal.add(Calendar.DATE, days);
    }


    public static void incrementByMonths(int months) {
        cal.add(Calendar.MONTH, months);
    }

    public static void incrementByYears(int years) {
        cal.add(Calendar.YEAR, years);
    }

    public static void setDateDetails(String date) {
        String escape = "[/-]";
        String[] dateInStringArray = date.split(escape);
        year = dateInStringArray[2];
        month = dateInStringArray[0];
        day = dateInStringArray[1];
    }

    public static String getYear() {
        return year;
    }

    public static String getMonth() {
        return month;
    }

    public static String getDay() {
        return day;
    }

    public static Date getDateFromValidStringFormat(String validDateString) {
        return isDateValid(validDateString);
    }


    public static Date isDateValid(String dateToCheck) {
        Date date=null;
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        sdf.setLenient(false);
        try {
            date = sdf.parse(dateToCheck);
        }
        catch (ParseException e) {
            System.out.println("Invalid date supplied, or in wrong format - " + dateToCheck);
            e.printStackTrace();
        }
        return date;
    }


    public static boolean isDateOfFutureOrToday(String toBeFutureDate, String dateToCompare) {
        Date fDate = getDateFromValidStringFormat(toBeFutureDate);
        Date cDate = getDateFromValidStringFormat(dateToCompare);
        return (fDate.after(cDate) || fDate.equals(cDate));
    }


    public static boolean isDateOfFutureOrTodayFromToday(String toBeFutureDate) {
        Date fDate = getDateFromValidStringFormat(toBeFutureDate);
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        try {
            now = sdf.parse(sdf.format(now));
        }
        catch (ParseException e) {
            System.out.println("Invalid date supplied, or in wrong format - " + now);
            e.printStackTrace();
        }
        return (fDate.after(now) || fDate.equals(now));
    }


    public static long getDaysBetweenDates(String futureDate, String date) {
        long diff = getDateFromValidStringFormat(futureDate).getTime() - getDateFromValidStringFormat(date).getTime();
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

    public static String[] getCurrentHourIn12Hours_hhaaFormat() {
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("hhaa");
        String time = sdf.format(now);
        String[] hourAndDayHalf = {time.substring(0,2) , time.substring(2)};
        return hourAndDayHalf;
    }

    public static String switchDayHalf(String dayHalf) {
        String newDayHalf = null;
        if(dayHalf.equals(dayHalfArray[0])) {
            newDayHalf = dayHalfArray[1];
        } else {
            newDayHalf = dayHalfArray[0];
        }
        return newDayHalf;
    }



}
