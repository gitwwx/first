package com.example.first.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateTimeUtil {

    /**
     * 日期格式(yyyy-MM-dd)
     */
    public final static String DATE_PATTERN = "yyyy-MM-dd";
    /**
     * 时间格式(yyyy-MM-dd HH:mm:ss)
     */
    public final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    /**
     * 时间格式(yyyyMMdd HH:mm:ss)
     */
    public final static String DATE_TIME_PATTERN2 = "yyyyMMdd HH:mm:ss";
    /**
     * 时间格式(HH:mm:ss)
     */
    public final static String TIME_PATTERN = "HH:mm:ss";
    /**
     * 日期格式(yyyyMMdd)
     */
    public final static String DT_YYYYMMDD = "yyyyMMdd";
    /**
     * 日期格式(yyyy-MM-dd'T'HH:mm:ss)
     */
    public final static String DT_T = "yyyy-MM-dd'T'HH:mm:ss";
    /**
     * 日期格式(yyyy-MM-dd'T'HH:mm:ss'Z')
     */
    public final static String DT_Z = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    /**
     * 日期格式(yyyy-MM-dd'T'HH:mm:ss.SSSXXX)
     */
    public final static String DT_T000 = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";

    public static void main(String[] args) {
        System.out.println(parseTime7("2024-04-23T05:29:34Z"));
    }

    public static Integer dateToSimpleDayInteger(Date date) {
        try {
            if (null == date || date.getTime() == 0) {
                return -1;
            }
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DT_YYYYMMDD);
            String dayStr = simpleDateFormat.format(date);
            return Integer.parseInt(dayStr);
        } catch (Exception e) {
            return -1;
        }
    }

    // 获取两个时间相差分钟数
    public static long getMinuteTime(String startTime, String endTime) {
        long end = parse(endTime, DATE_TIME_PATTERN).getTime();
        //从对象中拿到时间
        long start = parse(startTime, DATE_TIME_PATTERN).getTime();
        return (end - start) / 1000 / 60;
    }

    /**
     * 时间相加减多少天
     *
     * @param date   日期
     * @param amount 加or减
     * @return
     */
    public static Date dateTimeAdd(Date date, int amount) {
        return dateTimeAdd(date, Calendar.DAY_OF_YEAR, amount);
    }

    /**
     * 时间操作，增加、减多少时间
     *
     * @param date   时间
     * @param field  the calendar field
     * @param amount the amount of date or time to be added to the field
     * @return 操作之后的时间
     */
    public static Date dateTimeAdd(Date date, int field, int amount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(field, amount);
        return calendar.getTime();
    }

    /**
     * 日期格式化 日期格式为：yyyy-MM-dd
     *
     * @param date    日期
     * @param pattern 格式，如：DateUtils.DATE_TIME_PATTERN
     * @return 返回yyyy-MM-dd格式日期
     */
    public static String format(Date date, String pattern) {
        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.format(date);
        }
        return null;
    }

    /**
     * 时间比较
     *
     * @param date1 时间1
     * @param date2 时间2
     * @return date1.getTime() > date2.getTime()
     */
    public static boolean compareTo(Date date1, Date date2) {
        return date1.getTime() > date2.getTime();
    }


    /**
     * 字符串转换为日期
     *
     * @param date   日期字符串
     * @param patten 格式yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static Date parse(String date, String patten) {
        SimpleDateFormat sdf = new SimpleDateFormat(patten);
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 时间相差多少秒
     *
     * @param old    旧时间
     * @param junior 新时间
     * @return junior.getTime() - old.getTime()
     */
    public static int difference(Date old, Date junior) {
        long from = old.getTime();
        long to = junior.getTime();
        return (int) ((to - from) / (1000));
    }

    /**
     * 计算两个日期相差的毫秒数
     *
     * @param begin
     * @param end
     * @return
     */
    public static long msDiff(Date begin, Date end) {
        long beginTime = begin.getTime();
        long endTime = end.getTime();
        return endTime - beginTime;
    }

    /**
     * 计算两个Date相差的分钟数
     *
     * @param begin
     * @param end
     * @return
     */
    public static long minDiff(Date begin, Date end) {
        long beginTime = begin.getTime();
        long endTime = end.getTime();
        return (endTime - beginTime) / 1000 / 60;
    }

    public static Date parse2(String date, String patten) {
        SimpleDateFormat sdf = new SimpleDateFormat(patten);
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
        }
        return new Date();
    }

    // 2024-03-20T11:14:14+08:00
    public static LocalDateTime parseTime(String strTime) {
        OffsetDateTime offsetDateTime = OffsetDateTime.parse(strTime, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        return offsetDateTime.toLocalDateTime();
    }

    // 2024-03-20 11:14:13
    public static LocalDateTime parseTime2(String strTime) {
        return LocalDateTime.from(createFormatter(DATE_TIME_PATTERN).parse(strTime));
    }

    public static DateTimeFormatter createFormatter(String pattern) {
        return DateTimeFormatter.ofPattern(pattern, Locale.getDefault())
                .withZone(ZoneId.systemDefault());
    }

    // 2024-03-20
    public static LocalDateTime parseTime3(String strTime) {
        return LocalDate.parse(strTime, DateTimeFormatter.ofPattern(DATE_PATTERN)).atTime(0, 0);
    }

    // 2024-03-20T11:14:13.123Z
    public static LocalDateTime parseTime4(String strTime) {
        // 将字符串解析为 Instant 对象
        Instant instant = Instant.parse(strTime);
        // 将 Instant 转换为 LocalDateTime
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    // 2024-03-20T11:14:13
    public static LocalDateTime parseTime5(String strTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DT_T);
        return LocalDateTime.parse(strTime, formatter);
    }

    // 2024-03-20T11:14:14.000+08:00
    public static LocalDateTime parseTime6(String strTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DT_T000);
        OffsetDateTime offsetDateTime = OffsetDateTime.parse(strTime, formatter);
        return offsetDateTime.toLocalDateTime();
    }

    // 2024-03-20T11:14:13Z
    public static LocalDateTime parseTime7(String strTime) {
        // 定义日期时间格式化器
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DT_Z);
        // 将字符串解析为 LocalDateTime 对象
        return LocalDateTime.parse(strTime, formatter);
    }
}
