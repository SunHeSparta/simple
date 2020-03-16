package com.simon.util;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * java8日期处理类
 * Create by SunHe on 2020/3/9
 */
public class DateUtil {

    public static void main(String[] args) {
        LocalDate now = DateUtil.now();
        System.out.println("当前日期:");
        System.out.println(now);
        System.out.println("---------------------");
        boolean b = DateUtil.checkBirthday(2020, 3, 9);
        System.out.println(b ? "今天生日" : "今天不是生日");
        System.out.println("---------------------");
        Long l = DateUtil.currentTimeInMillis();
        System.out.println("当前时间戳:" + l);
        System.out.println("---------------------");
        String s = DateUtil.dateToString(LocalDateTime.now(), "yyyy-MM-hh HH:mm:ss");
        System.out.println("格式化后的时间:" + s);
        System.out.println("---------------------");
        LocalDate formatLocalDate = DateUtil.format("20200309", DateTimeFormatter.BASIC_ISO_DATE);
        System.out.println("格式化后的时间:" + formatLocalDate);
        System.out.println("---------------------");
        Long ll = DateUtil.instantTimeInMillis();
        System.out.println("当前instant时间戳:" + ll);
        System.out.println("---------------------");
        Boolean leapYear = DateUtil.isLeapYear();
        System.out.println(leapYear ? "今年是闰年" : "今年不是闰年");
        System.out.println("---------------------");
        ZonedDateTime zonedDateTime = DateUtil.zonedDateTime("America/New_York");
        System.out.println("America/New_York zonedDateTime : " + zonedDateTime);
        System.out.println("---------------------");
        Period period = DateUtil.periodNow(2020, 2, 5);
        System.out.println("period:" + period);
        System.out.println("---------------------");
        YearMonth yearMonth = DateUtil.yearMonth(2020, Month.AUGUST);
        System.out.println("yearMonth : " + yearMonth);
        System.out.println("---------------------");
        LocalDate localDate = DateUtil.plusDate(1, ChronoUnit.YEARS);
        System.out.println("一年后,localDate:" + localDate);
        System.out.println("---------------------");
        LocalTime localTime = DateUtil.plusHours(3);
        System.out.println("三小时后,localTime:" + localTime);
    }

    /**
     * 获取今天的日期
     *
     * @return 今天的日期
     */
    public static LocalDate now() {
        return LocalDate.now();
    }

    /**
     * 检查像生日这种周期性事件
     *
     * @param year  年
     * @param month 月
     * @param day   日
     * @return 检查结果
     */
    public static boolean checkBirthday(int year, int month, int day) {
        LocalDate inputDate = LocalDate.of(year, month, day);
        MonthDay birthday = MonthDay.of(inputDate.getMonth(), inputDate.getDayOfMonth());
        LocalDate today = LocalDate.now();
        MonthDay currentMonthday = MonthDay.from(today);
        return birthday.equals(currentMonthday);
    }

    /**
     * 获取当前时间
     *
     * @return 当前时间, 没有日期
     */
    public static LocalTime nowTime() {
        return LocalTime.now();
    }

    /**
     * 几小时后的时间
     *
     * @param hours 几小时
     * @return 几小时后的时间
     */
    public static LocalTime plusHours(int hours) {
        LocalTime time = LocalTime.now();
        return time.plusHours(hours);
    }

    /**
     * 1个月、1年、1小时、1分钟甚至一个世纪后的日期
     *
     * @param i          日期加值
     * @param chronoUnit 加值单位
     * @return 1个月、1年、1小时、1分钟甚至一个世纪后的日期
     */
    public static LocalDate plusDate(int i, ChronoUnit chronoUnit) {
        LocalDate today = LocalDate.now();
        return today.plus(i, chronoUnit);
    }

    /**
     * 当期时间的时间戳
     *
     * @return 当期时间的时间戳
     */
    public static Long currentTimeInMillis() {
//        Clock clock = Clock.systemUTC();
        Clock clock = Clock.systemDefaultZone();
        return clock.millis();
    }

    /**
     * 由zoneId获取当前时区的日期和时间
     *
     * @param zone 时区
     * @return 当前时区的日期和时间
     */
    public static ZonedDateTime zonedDateTime(String zone) {
        ZoneId zoneId = ZoneId.of(zone);
        LocalDateTime localDateTime = LocalDateTime.now();
        return ZonedDateTime.of(localDateTime, zoneId);
    }

    /**
     * 信用卡到期日、FD到期日、期货期权到期日等
     *
     * @param year  年份
     * @param month 月份
     * @return 信用卡到期日、FD到期日、期货期权到期日等
     */
    public static YearMonth yearMonth(int year, Month month) {
        if (year == 0 || month == null) {
            return YearMonth.now();
        }
        return YearMonth.of(year, month);
    }

    /**
     * 是否是闰年
     *
     * @return 是否是闰年
     */
    public static Boolean isLeapYear() {
        LocalDate localDate = LocalDate.now();
        return localDate.isLeapYear();
    }

    /**
     * 计算两个日期之间的天数和月数
     *
     * @param year  年
     * @param month 月
     * @param day   日
     * @return 两个日期之间的天数和月数
     */
    public static Period periodNow(int year, int month, int day) {
        LocalDate today = LocalDate.now();
        LocalDate localDate = LocalDate.of(year, month, day);
        return Period.between(today, localDate);
    }

    /**
     * 当前时间戳
     *
     * @return 当前时间戳
     */
    public static Long instantTimeInMillis() {
        Instant instant = Instant.now();
        return instant.toEpochMilli();
    }

    /**
     * 格式化日期
     *
     * @param date              日期字符串
     * @param dateTimeFormatter 格式化
     * @return 格式化日期
     */
    public static LocalDate format(String date, DateTimeFormatter dateTimeFormatter) {
        return LocalDate.parse(date, dateTimeFormatter);
    }

    /**
     * 日期转字符串
     *
     * @param localDateTime 日期
     * @param format        格式
     * @return 日期转字符串
     */
    public static String dateToString(LocalDateTime localDateTime, String format) {
        return localDateTime.format(DateTimeFormatter.ofPattern(format));
    }

    public static LocalDate StringToDate(String date, String format) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern(format));
    }

}
