package com.jnu.student.data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class TimeUtil {
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String[] WEEKDAYS = {"", "日", "一", "二", "三", "四", "五", "六"};
    private static final TimeZone BEIJING_TIMEZONE = TimeZone.getTimeZone("Asia/Shanghai");

    public static String getBeijingTime() {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
        sdf.setTimeZone(BEIJING_TIMEZONE);
        return sdf.format(new Date());
    }

    public static String getDayOfWeek(String dateString) {
        try {
            SimpleDateFormat inputDateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
            inputDateFormat.setTimeZone(BEIJING_TIMEZONE);

            SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd  EEEE", new Locale("zh", "CN"));
            outputDateFormat.setTimeZone(BEIJING_TIMEZONE);

            Date date = inputDateFormat.parse(dateString);
            return outputDateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return "日期格式错误";
        }
    }

    public static String getFormattedTimeAgoHour(int hoursAgo) {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
        formatter.setTimeZone(BEIJING_TIMEZONE);

        long hoursAgoMillis = hoursAgo * 60 * 60 * 1000;
        long targetTimeMillis = System.currentTimeMillis() - hoursAgoMillis;
        return formatter.format(new Date(targetTimeMillis));
    }

    public static String getFormattedTimeAgoDay(int dayAgo) {
        return getFormattedTimeAgoHour(24 * dayAgo);
    }

    public static String getCurrentHour() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH", Locale.getDefault());
        sdf.setTimeZone(BEIJING_TIMEZONE);
        return sdf.format(new Date());
    }

    public static String getCurrentDay() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd", Locale.getDefault());
        sdf.setTimeZone(BEIJING_TIMEZONE);
        return sdf.format(new Date());
    }

    private static String getDayOfWeekString(int dayOfWeek) {
        return WEEKDAYS[dayOfWeek];
    }

    public static String formatMonthAndDay(String inputDateString, String outputPattern) {
        try {
            SimpleDateFormat inputDateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
            Date date = inputDateFormat.parse(inputDateString);
            SimpleDateFormat outputDateFormat = new SimpleDateFormat(outputPattern, Locale.getDefault());
            return outputDateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
