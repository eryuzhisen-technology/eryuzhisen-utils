/**
 * 
 */
package com.eryuzhisen.utils;

/**
 * Descripton:
 * @author WeiDunyong
 * @version 0.01
 */
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang3.time.FastDateFormat;

public class DateUtil {
	// 用来全局控制 上一周，本周，下一周的周数变化
	private int weeks = 0;
	private int MaxDate;// 一月最大天数
	private int MaxYear;// 一年最大天数

	/**
	 * <p>
	 * 默认时间格式
	 * </p>
	 */
	public static final String DEFAULT_DATE_FORMAT = "yyyyMMdd";

	public static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	public static final String DEFAULT_TIMESTAMP_FORMAT = "yyyyMMddHHmmss";

	public static final String DEFAULT_TIMEMILLIS_FORMAT = "yyyyMMddHHmmssSSS";

	public static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";

	/**
	 * <p>
	 * 仅年份格式
	 * </p>
	 */
	public static final String ONLY_YEAR_DATETIME_FORMAT = "yyyy";
	
	public static final String ONLY_MOTH_DATATIME_FORMAT = "MM-dd";

	/**
	 * <P>
	 * 时间格式(日期比较时使用到)
	 * </P>
	 */
	public static final String SECOND_DATETIME_FORMAT = "yyyy-MM-dd";

	/**
	 * <P>
	 * 星期格式
	 * </P>
	 */
	public static final String FOURTH_DATETIME_FORMAT = "E";

	/**
	 * 
	 * @param startDate
	 * @param endDate
	 * @return 两个日期间隔时间
	 */
	public static String getBtweenTwoDate(String startDate, String endDate) {
		SimpleDateFormat dfs = new SimpleDateFormat(DEFAULT_DATETIME_FORMAT);
		java.util.Date begin = null;
		java.util.Date end = null;
		try {
			begin = dfs.parse(startDate);
			end = dfs.parse(endDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long between = (end.getTime() - begin.getTime()) / 1000;// 除以1000是为了转换成秒
		long day = between / (24 * 3600);
		long hour = between % (24 * 3600) / 3600;
		long minute = between % 3600 / 60;
		long second = between % 60;

		return day + "天" + hour + "小时" + minute + "分" + second + "秒";

	}

	/**
	 * 
	 * @param date
	 * @return 以1970年开始的毫秒数
	 */
	public static long getTimeMillis(String date) {
		long l = 0;
		Date timeStamp = null;
		try {
			timeStamp = new SimpleDateFormat(DEFAULT_TIMEMILLIS_FORMAT).parse(date);
			l = timeStamp.getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return l;

	}

	/**
	 * 得到二个日期间的间隔天数
	 */
	public static String getTwoDay(String sj1, String sj2) {
		SimpleDateFormat myFormatter = new SimpleDateFormat(SECOND_DATETIME_FORMAT);
		long day = 0;
		try {
			java.util.Date date = myFormatter.parse(sj1);
			java.util.Date mydate = myFormatter.parse(sj2);
			day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
		} catch (Exception e) {
			return "";
		}
		return day + "";
	}

	/**
	 * 根据一个日期，返回是星期几的字符串
	 * 
	 * @param sdate
	 * @return
	 */
	public static String getWeek(String sdate) {
		// 再转换为时间
		Date date = DateUtil.strToDate(sdate);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		// int hour=c.get(Calendar.DAY_OF_WEEK);
		// hour中存的就是星期几了，其范围 1~7
		// 1=星期日 7=星期六，其他类推
		return new SimpleDateFormat("EEEE").format(c.getTime());
	}

	/**
	 * 将短时间格式字符串转换为时间 yyyy-MM-dd
	 * 
	 * @param strDate
	 * @return
	 */
	public static Date strToDate(String strDate) {
		SimpleDateFormat formatter = new SimpleDateFormat(SECOND_DATETIME_FORMAT);
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}

	/**
	 * 两个时间之间的天数
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static long getDays(String date1, String date2) {
		if (date1 == null || date1.equals(""))
			return 0;
		if (date2 == null || date2.equals(""))
			return 0;
		// 转换为标准时间
		SimpleDateFormat myFormatter = new SimpleDateFormat(SECOND_DATETIME_FORMAT);
		java.util.Date date = null;
		java.util.Date mydate = null;
		try {
			date = myFormatter.parse(date1);
			mydate = myFormatter.parse(date2);
		} catch (Exception e) {
		}
		long day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
		return day;
	}

	// 计算当月最后一天,返回字符串
	public String getDefaultDay() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat(SECOND_DATETIME_FORMAT);

		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
		lastDate.add(Calendar.MONTH, 1);// 加一个月，变为下月的1号
		lastDate.add(Calendar.DATE, -1);// 减去一天，变为当月最后一天

		str = sdf.format(lastDate.getTime());
		return str;
	}

	// 上月第一天
	public String getPreviousMonthFirst() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat(SECOND_DATETIME_FORMAT);

		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
		lastDate.add(Calendar.MONTH, -1);// 减一个月，变为下月的1号
		// lastDate.add(Calendar.DATE,-1);//减去一天，变为当月最后一天

		str = sdf.format(lastDate.getTime());
		return str;
	}

	// 获取当月第一天
	public String getFirstDayOfMonth() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat(SECOND_DATETIME_FORMAT);

		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
		str = sdf.format(lastDate.getTime());
		return str;
	}

	// 获得本周星期日的日期
	public String getCurrentWeekday() {
		weeks = 0;
		int mondayPlus = this.getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 6);
		Date monday = currentDate.getTime();

		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	// 获取当天时间
	public static String getNowTime(String dateformat) {
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat(dateformat);// 可以方便地修改日期格式
		String hehe = dateFormat.format(now);
		return hehe;
	}

	// 获得当前日期与本周日相差的天数
	private int getMondayPlus() {
		Calendar cd = Calendar.getInstance();
		// 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
		int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK) - 1; // 因为按中国礼拜一作为第一天所以这里减1
		if (dayOfWeek == 1) {
			return 0;
		} else {
			return 1 - dayOfWeek;
		}
	}

	// 获得本周一的日期
	public String getMondayOFWeek() {
		weeks = 0;
		int mondayPlus = this.getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus);
		Date monday = currentDate.getTime();

		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	// 获得相应周的周六的日期
	public String getSaturday() {
		int mondayPlus = this.getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 * weeks + 6);
		Date monday = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	// 获得上周星期日的日期
	public String getPreviousWeekSunday() {
		weeks = 0;
		weeks--;
		int mondayPlus = this.getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + weeks);
		Date monday = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	// 获得上周星期一的日期
	public String getPreviousWeekday() {
		weeks--;
		int mondayPlus = this.getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 * weeks);
		Date monday = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	// 获得下周星期一的日期
	public String getNextMonday() {
		weeks++;
		int mondayPlus = this.getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 7);
		Date monday = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	// 获得下周星期日的日期
	public String getNextSunday() {

		int mondayPlus = this.getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 + 6);
		Date monday = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	/**
	 * 获取一个星期前的日期
	 * 
	 * @return
	 */
	public static final SimpleDateFormat weekDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	public static void main(String[] args) {
		try {
//			String age = getAgeForStr(weekDateFormat.parse("2018-11-29"));
//			System.out.println(age);
//		    System.out.println(getInterval(DateUtil.StringToDate(DEFAULT_DATETIME_FORMAT, "2017-03-04 15:48:00")));
		    System.out.println(dateToString(ONLY_MOTH_DATATIME_FORMAT, new Date()));
		} catch (Exception e) {
			
		}
		/*try {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DAY_OF_MONTH, -1);
			System.out.println(weekDateFormat.format(cal.getTime()));
//			System.out.println(getAgeForStr(weekDateFormat.parse("2014-5-21")));
		} catch (ParseException e) {
//			logger.error("", e);
		}*/
		
//		System.out.println(getWeekAgo());
//		System.out.println(weekDateFormat.format(new Date()));
	    /*String[] dates = getDateAndDaysAgo(3);
	    for(String date:dates) {
	        System.out.println(date);
	    }*/
	}
	public static String getWeekAgo() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.WEEK_OF_YEAR, -1);
		Date d = c.getTime();
		return weekDateFormat.format(d);
	}

	@SuppressWarnings("unused")
	private int getMonthPlus() {
		Calendar cd = Calendar.getInstance();
		int monthOfNumber = cd.get(Calendar.DAY_OF_MONTH);
		cd.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		cd.roll(Calendar.DATE, -1);// 日期回滚一天，也就是最后一天
		MaxDate = cd.get(Calendar.DATE);
		if (monthOfNumber == 1) {
			return -MaxDate;
		} else {
			return 1 - monthOfNumber;
		}
	}

	// 获得当月第一天和最后一天日期
	public final static String[] getCurrentMonStartAndEnd() {
		String str[] = new String[2];
		SimpleDateFormat sdf = new SimpleDateFormat(SECOND_DATETIME_FORMAT);

		Calendar date = Calendar.getInstance();
		date.set(Calendar.DATE, 1);// 日期设为当月第一天
		str[0] = sdf.format(date.getTime()) + " 00:00:00";
		date.roll(Calendar.DATE, -1);
		str[1] = sdf.format(date.getTime()) + " 23:59:59";

		return str;
	}

	// 获得一个星期时间段两端的日期
	public final static String[] getCurrentAndSevenDayAgo() {
		String str[] = new String[2];
		SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATETIME_FORMAT);

		Calendar date = Calendar.getInstance();
		str[1] = sdf.format(date.getTime());

		date.add(Calendar.DATE, -7);
		str[0] = sdf.format(date.getTime());

		return str;
	}

	// 获得三天时间段两端的日期
	public final static String[] getCurrentAndThreeDayAgo() {
		String str[] = new String[2];
		SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATETIME_FORMAT);

		Calendar date = Calendar.getInstance();
		str[1] = sdf.format(date.getTime());

		date.add(Calendar.DATE, -3);
		str[0] = sdf.format(date.getTime());

		return str;
	}
	
	// 获得前几天时间的每个日期
    public final static String[] getDateAndDaysAgo(int beforeDays) {
        String str[] = new String[beforeDays];
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar date = Calendar.getInstance();
        for(int i=beforeDays-1;i>=0;i--) {
            str[i] = sdf.format(date.getTime());
            date.add(Calendar.DATE, -1);
        }
        return str;
    }

	// 获得当天时间段两端的日期
	public final static String[] getCurrentStartAndEnd() {
		String str[] = new String[2];
		SimpleDateFormat sdf = new SimpleDateFormat(SECOND_DATETIME_FORMAT);

		Calendar date = Calendar.getInstance();
		str[0] = sdf.format(date.getTime()) + " 00:00:00";

		str[1] = sdf.format(date.getTime()) + " 23:59:59";

		return str;
	}

	// 获得三个月时间段两端的日期
	public final static String[] getCurrentAndThreeMonAgo() {
		String str[] = new String[2];
		SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATETIME_FORMAT);

		Calendar date = Calendar.getInstance();
		str[1] = sdf.format(date.getTime());
		date.add(Calendar.MONTH, -3);
		str[0] = sdf.format(date.getTime());

		return str;
	}

	// 获得上月最后一天的日期
	public String getPreviousMonthEnd() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat(SECOND_DATETIME_FORMAT);

		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.MONTH, -1);// 减一个月
		lastDate.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		lastDate.roll(Calendar.DATE, -1);// 日期回滚一天，也就是上月最后一天
		str = sdf.format(lastDate.getTime());
		return str;
	}

	// 获得下个月第一天的日期
	public String getNextMonthFirst() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat(SECOND_DATETIME_FORMAT);

		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.MONTH, 1);// 减一个月
		lastDate.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		str = sdf.format(lastDate.getTime());
		return str;
	}

	// 获得下个月最后一天的日期
	public String getNextMonthEnd() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat(SECOND_DATETIME_FORMAT);

		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.MONTH, 1);// 加一个月
		lastDate.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		lastDate.roll(Calendar.DATE, -1);// 日期回滚一天，也就是本月最后一天
		str = sdf.format(lastDate.getTime());
		return str;
	}

	// 获得明年最后一天的日期
	public String getNextYearEnd() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat(SECOND_DATETIME_FORMAT);

		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.YEAR, 1);// 加一个年
		lastDate.set(Calendar.DAY_OF_YEAR, 1);
		lastDate.roll(Calendar.DAY_OF_YEAR, -1);
		str = sdf.format(lastDate.getTime());
		return str;
	}

	// 获得明年第一天的日期
	public String getNextYearFirst() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat(SECOND_DATETIME_FORMAT);

		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.YEAR, 1);// 加一个年
		lastDate.set(Calendar.DAY_OF_YEAR, 1);
		str = sdf.format(lastDate.getTime());
		return str;

	}

	// 获得本年有多少天
	@SuppressWarnings("unused")
	private int getMaxYear() {
		Calendar cd = Calendar.getInstance();
		cd.set(Calendar.DAY_OF_YEAR, 1);// 把日期设为当年第一天
		cd.roll(Calendar.DAY_OF_YEAR, -1);// 把日期回滚一天。
		int MaxYear = cd.get(Calendar.DAY_OF_YEAR);
		return MaxYear;
	}

	private int getYearPlus() {
		Calendar cd = Calendar.getInstance();
		int yearOfNumber = cd.get(Calendar.DAY_OF_YEAR);// 获得当天是一年中的第几天
		cd.set(Calendar.DAY_OF_YEAR, 1);// 把日期设为当年第一天
		cd.roll(Calendar.DAY_OF_YEAR, -1);// 把日期回滚一天。
		int MaxYear = cd.get(Calendar.DAY_OF_YEAR);
		if (yearOfNumber == 1) {
			return -MaxYear;
		} else {
			return 1 - yearOfNumber;
		}
	}

	// 获得本年第一天的日期
	public String getCurrentYearFirst() {
		int yearPlus = this.getYearPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, yearPlus);
		Date yearDay = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preYearDay = df.format(yearDay);
		return preYearDay;
	}

	// 获得本年最后一天的日期 *
	public String getCurrentYearEnd() {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat(ONLY_YEAR_DATETIME_FORMAT);// 可以方便地修改日期格式
		String years = dateFormat.format(date);
		return years + "-12-31";
	}

	// 获得上年第一天的日期 *
	public String getPreviousYearFirst() {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat(ONLY_YEAR_DATETIME_FORMAT);// 可以方便地修改日期格式
		String years = dateFormat.format(date);
		int years_value = Integer.parseInt(years);
		years_value--;
		return years_value + "-1-1";
	}

	// 获得上年最后一天的日期
	public String getPreviousYearEnd() {
		weeks--;
		int yearPlus = this.getYearPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, yearPlus + MaxYear * weeks + (MaxYear - 1));
		Date yearDay = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preYearDay = df.format(yearDay);
		getThisSeasonTime(11);
		return preYearDay;
	}

	// 获得本季度
	public String getThisSeasonTime(int month) {
		int array[][] = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 }, { 10, 11, 12 } };
		int season = 1;
		if (month >= 1 && month <= 3) {
			season = 1;
		}
		if (month >= 4 && month <= 6) {
			season = 2;
		}
		if (month >= 7 && month <= 9) {
			season = 3;
		}
		if (month >= 10 && month <= 12) {
			season = 4;
		}
		int start_month = array[season - 1][0];
		int end_month = array[season - 1][2];

		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat(ONLY_YEAR_DATETIME_FORMAT);// 可以方便地修改日期格式
		String years = dateFormat.format(date);
		int years_value = Integer.parseInt(years);

		int start_days = 1;// years+"-"+String.valueOf(start_month)+"-1";//getLastDayOfMonth(years_value,start_month);
		int end_days = getLastDayOfMonth(years_value, end_month);
		String seasonDate = years_value + "-" + start_month + "-" + start_days + ";" + years_value + "-" + end_month + "-" + end_days;
		return seasonDate;

	}

	/**
	 * 获取某年某月的最后一天
	 * 
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * @return 最后一天
	 */
	public static int getLastDayOfMonth(int year, int month) {
		if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
			return 31;
		}
		if (month == 4 || month == 6 || month == 9 || month == 11) {
			return 30;
		}
		if (month == 2) {
			if (isLeapYear(year)) {
				return 29;
			} else {
				return 28;
			}
		}
		return 0;
	}

	public static int getAllLastDayOfMonth(int year, int month) {
		if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
			return 32;
		}
		if (month == 4 || month == 6 || month == 9 || month == 11) {
			return 31;
		}
		if (month == 2) {
			if (isLeapYear(year)) {
				return 30;
			} else {
				return 29;
			}
		}
		return 0;
	}

	/**
	 * 是否闰年
	 * 
	 * @param year
	 *            年
	 * @return
	 */
	public static boolean isLeapYear(int year) {
		return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
	}

	/**
	 * 取得星期 格式为星期X
	 * 
	 * @return datetime string
	 */

	public static final String getWeekTime(Date nowDate) {
		SimpleDateFormat dataFormat = new SimpleDateFormat(FOURTH_DATETIME_FORMAT);// 星期格式
		String now_date = dataFormat.format(nowDate);
		return now_date;
	}

	/**
	 * 取得日期，格式为yyyy年MM月dd日
	 * 
	 * @return datetime string
	 */
	public static final String getChinereDateTime(Date nowDate) {

		DateFormat df = DateFormat.getDateInstance(DateFormat.LONG, Locale.CHINA);
		String now_date = df.format(nowDate);
		return now_date;

	}

	/**
	 * 取得当前日期，格式为yyyy-MM-dd HH:mm:ss
	 * 
	 * @return datetime string
	 */
	public static final String getCurrentDateTime() {

		return getCurrentDateTime(DEFAULT_DATETIME_FORMAT);

	}

	public static final String getCurrentTimeStamp() {
		return new SimpleDateFormat(DEFAULT_TIMESTAMP_FORMAT).format(Calendar.getInstance().getTime());
	}

	/**
	 * 
	 * @return 带毫秒时间戳
	 */
	public static final String getCurrentMsTimeStamp() {
		return new SimpleDateFormat(DEFAULT_TIMEMILLIS_FORMAT).format(Calendar.getInstance().getTime());
	}

	/**
	 * 取得当前年份
	 * 
	 * @return year int
	 */
	public static final int getCurrentYear() {
		return Integer.parseInt(getCurrentDateTime(ONLY_YEAR_DATETIME_FORMAT));
	}

	/**
	 * 用指定的日期格式取得当前时间
	 * 
	 * @param format
	 *            datetime output format
	 * @return datetime string
	 */
	public static final String getCurrentDateTime(String format) {
		java.util.Date date = new java.util.Date();
		FastDateFormat fdf = FastDateFormat.getInstance(format);
		return fdf.format(date);

	}

	/**
	 * 获取与当前时间相差time小时的时间
	 * 
	 * @param time
	 *            差距的小时数目
	 * @return 时间的字符串形式
	 */
	public static final String getLastOrNextDatetime(Integer time) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.HOUR_OF_DAY, time.intValue());
		Date date = new Date();
		date.setTime(c.getTimeInMillis());
		return FastDateFormat.getInstance(DEFAULT_DATETIME_FORMAT).format(date);
	}

	/**
	 * 比较当前时间和指定时间的大小
	 * 
	 * @param datetime
	 *            比较的时间
	 * @return Integer(0)说明当前时间和给定时间相等， 大于Integer(0),说明当前时间在给定时间之后，否则当前时间在给定时间之前
	 * @exception 格式化时间的时候发生异常返回null
	 */
	public static final Integer compare(String datetime) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(SECOND_DATETIME_FORMAT);
			Date otherDatetime = sdf.parse(datetime);
			Date now = new Date();
			now = sdf.parse(dateToString(SECOND_DATETIME_FORMAT, now));
			int i = now.compareTo(otherDatetime);
			return new Integer(i);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * <p>
	 * 重新格式化时间
	 * </p>
	 * 
	 * @param time
	 *            时间格式必须是yyyy-mm-dd格式
	 * @return 返回格式为yyyy-MM-dd的时间
	 */
	public static final String format(String time) {
		int head = time.indexOf("-");
		int foot = time.lastIndexOf("-");
		String month = time.substring(head + 1, foot);
		String date = time.substring(foot + 1);
		if (1 == month.length()) {
			month = "0" + month;// 1位数字月份补0
		}
		if (1 == date.length()) {
			date = "0" + date;// 1位数字日期补0
		}
		return time.substring(0, head) + "-" + month + "-" + date;
	}

	/**
	 * <p>
	 * 计算两个时间段的时间
	 * </p>
	 * 
	 * @param fromDatetime
	 *            开始时间字符串 例如：2007-7-8
	 * @param toDatetime
	 *            结束时间字符串 例如： 2007-9-8
	 * @return 包含时间字符串(格式yyyy-MM)的列表
	 */
	public static final List<String> computeDateTimeList(String fromDatetime, String toDatetime) {
		List<String> list = new ArrayList<String>();// 时间段列表
		fromDatetime = format(fromDatetime);
		toDatetime = format(toDatetime);
		Date fdt = StringToDate(SECOND_DATETIME_FORMAT, fromDatetime);
		Date tdt = StringToDate(SECOND_DATETIME_FORMAT, toDatetime);
		Integer v = compare(fromDatetime.substring(0, 7), toDatetime.substring(0, 7), "yyyy-MM");
		if (v.intValue() == 0) {
			// 只有一个月
			list.add(fromDatetime.substring(0, 7));
		} else if ((v.intValue() < 0) && (null != fdt) && (null != tdt)) {
			// 计算
			int year = Integer.parseInt(toDatetime.substring(0, 3)) - Integer.parseInt(fromDatetime.substring(0, 3));
			int month = Integer.parseInt(toDatetime.substring(5, 7)) - Integer.parseInt(fromDatetime.substring(5, 7));
			int len = year * 12 + month;

			for (int i = len; i > 0; i--) {
				Calendar c = Calendar.getInstance();
				c.setTime(tdt);
				c.add(Calendar.MONTH, -i);
				Date tmp = c.getTime();
				String tmpDtd = dateToString("yyyy-MM", tmp);
				if (tmpDtd != null) {
					list.add(tmpDtd);
				}
			}
		}
		return list;
	}

	/**
	 * <p>
	 * 比较两个时间大小
	 * </p>
	 * 
	 * @param t1
	 *            格式必须是yyyy-MM-dd
	 * @param t2
	 *            格式必须是yyyy-MM-dd
	 * @return 返回0说明两个时间相等，小于0说明t1在t2之前，大于0说明t1在t2之后
	 * @exception 格式化时间的时候发生异常返回null
	 */
	public static final Integer compare(String t1, String t2) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(SECOND_DATETIME_FORMAT);
			Date d1 = sdf.parse(t1);
			Date d2 = sdf.parse(t2);
			int i = d1.compareTo(d2);
			return new Integer(i);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * <p>
	 * 比较两个字符串时间的先后
	 * </p>
	 * 
	 * @param t1
	 *            起始时间
	 * @param t2
	 *            结束时间
	 * @param format
	 *            格式化格式
	 * @return 返回0说明两个时间相等，小于0说明t1在t2之前，大于0说明t1在t2之后
	 * @exception 格式化时间的时候发生异常返回null
	 */
	public static final Integer compare(String t1, String t2, String format) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			Date d1 = sdf.parse(t1);
			Date d2 = sdf.parse(t2);
			int i = d1.compareTo(d2);
			return new Integer(i);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * <p>
	 * 字符串时间转换成Date时间
	 * </p>
	 * 
	 * @param format
	 *            格式化格式
	 * @param datetime
	 *            字符串时间
	 * @return Date时间
	 * @exception 格式化时间的时候发生异常返回null
	 */
	public static final Date StringToDate(String format, String datetime) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.parse(datetime);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * <p>
	 * Date时间转换成字符串时间
	 * </p>
	 * 
	 * @param format
	 *            格式化格式
	 * @param datetime
	 *            Date时间
	 * @return 字符串时间
	 * @exception 格式化时间的时候发生异常返回null
	 */
	public static final String dateToString(String format, Date datetime) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.format(datetime);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * <p>
	 * Date时间转换成字符串时间
	 * </p>
	 * 
	 * @param format
	 *            格式化格式
	 * @param datetime
	 *            Date时间
	 * @return 字符串时间
	 * @exception 格式化时间的时候发生异常返回null
	 */
	@SuppressWarnings("deprecation")
	public static String getNextMonthFirstDay(String yearMonth) {

		String yearMonthDay = yearMonth + "-01";
		String nextMonthFirstDay = null;
		try {
			java.util.Date date = new Date(yearMonthDay.replaceAll("-", "/"));
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.add(Calendar.MONTH, 1);
			Date ndate = c.getTime();
			SimpleDateFormat sdf = new SimpleDateFormat(SECOND_DATETIME_FORMAT);
			nextMonthFirstDay = sdf.format(ndate);
		} catch (Exception e) {
			nextMonthFirstDay = "";
		}
		return nextMonthFirstDay;
	}

	// 根据对1970年1月1号的毫秒偏移量得到日期时间字符串
	public static String getDateTime(Long l) {

		try {
			Date time = new Date(l);
			return new SimpleDateFormat(DEFAULT_DATETIME_FORMAT).format(time);
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}

	public static Long getNumDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

		return new Long(sdf.format(new Date()));

	}

	/**
	 * 字符串类型
	 * 
	 * @param time
	 *            yyyy-MM-dd HH:mm:ss
	 * @return yyyy-MM-dd
	 */
	public static String getSimpleTime(String time) {
		return time.substring(0, 10);
	}

	/**
	 * 
	 * @param date
	 * @return
	 */
	public static GregorianCalendar getDateToGregorianCalendar(Date date) {
		GregorianCalendar calendar = new GregorianCalendar();
		if (date == null)
			return calendar;
		calendar.setTime(date);
		return calendar;
	}

	/**
	 * 根据生日返回年龄
	 * 
	 * @param date
	 * @return
	 */
	public static int getAge(Date date) {
		int age = 0;
		Date birthDay = date;
		if (birthDay == null)
			return age;

		Calendar cal = Calendar.getInstance();

		if (cal.before(birthDay)) {
			throw new IllegalArgumentException("The birthDay is before Now.It's unbelievable!");
		}

		int yearNow = cal.get(Calendar.YEAR);
		int monthNow = cal.get(Calendar.MONTH);
		int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
		cal.setTime(birthDay);

		int yearBirth = cal.get(Calendar.YEAR);
		int monthBirth = cal.get(Calendar.MONTH);
		int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

		age = yearNow - yearBirth;

		if (monthNow <= monthBirth) {
			if (monthNow == monthBirth) {
				// monthNow==monthBirth
				if (dayOfMonthNow < dayOfMonthBirth) {
					age--;
				} else {
					// do nothing
				}
			} else {
				// monthNow>monthBirth
				age--;
			}
		} else {
			// monthNow<monthBirth
			// donothing
		}
		if (age < 0)
			age = 0;
		if (age > 100)
			age = 100;

		return age;
	}
	
	/**
	 *  获取某一日期时的用户年龄
	 * 
	 * @param birthDate
	 * @param specifyDate
	 * @return
	 */
	public static Map<String,Integer> getAgeForMap(Date birthDate,Date specifyDate) {
		int age = 0;
		int month = 0;
		int day = 0;
		Date birthDay = birthDate;
		if (birthDay == null)
			return null;

		Calendar cal = Calendar.getInstance();
		
		cal.setTime(specifyDate);

		if (cal.before(birthDay)) {
			return null;
//			throw new IllegalArgumentException("The birthDay is before Now.It's unbelievable!");
		}

		int yearNow = cal.get(Calendar.YEAR);
		int monthNow = cal.get(Calendar.MONTH);
		int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
		cal.add(Calendar.MONTH, -1);
		int maxDayOfBeforeMonth = cal.getActualMaximum(Calendar.DATE);
		
		cal.setTime(birthDay);

		int yearBirth = cal.get(Calendar.YEAR);
		int monthBirth = cal.get(Calendar.MONTH);
		int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
		
		age = yearNow - yearBirth;
		
		if (monthNow <= monthBirth) {
			if (monthNow == monthBirth) {
				// monthNow==monthBirth
				if (dayOfMonthNow < dayOfMonthBirth) {
					month = 11;
					day = (maxDayOfBeforeMonth-dayOfMonthBirth) + dayOfMonthNow;
					age--;
				} else {
					if(dayOfMonthNow > dayOfMonthBirth) {
						day = dayOfMonthNow - dayOfMonthBirth;
					}
				}
			} else {
				// monthNow<monthBirth
				if (dayOfMonthNow < dayOfMonthBirth) {
					month = (12-monthBirth) + (monthNow-1);
					day = (maxDayOfBeforeMonth-dayOfMonthBirth) + dayOfMonthNow;
				} else {
					month = (12-monthBirth) + monthNow;
					if(dayOfMonthNow > dayOfMonthBirth) {
						day = dayOfMonthNow - dayOfMonthBirth;
					}
				}
				
				age--;
				
			}
		} else {
			// monthNow>monthBirth
			if (dayOfMonthNow < dayOfMonthBirth) {
				month = monthNow - monthBirth -1;
				day = (maxDayOfBeforeMonth-dayOfMonthBirth) + dayOfMonthNow;
			} else {
				month = monthNow - monthBirth;
				if(dayOfMonthNow > dayOfMonthBirth) {
					day = dayOfMonthNow - dayOfMonthBirth;
				}
			}
		}
		
		if (age < 0)
			age = 0;
		if (age > 100)
			age = 100;
		
		Map<String,Integer> map = new HashMap<String, Integer>();
		
		map.put("age", age);
		map.put("month", month);
		map.put("day", day);
		
		return map;
	}
	
	/**
	 * 根据生日返回年龄
	 * 
	 * @param date
	 * @param minus true 允许负数
	 * @return
	 */
	public static Map<String,Integer> getAgeForMap(Date date,boolean minus) {
		Map<String,Integer> map = new HashMap<String, Integer>();
		int age = 0;
		int month = 0;
		int day = 0;
		Date birthDay = date;
		if (birthDay == null)
			return null;

		Calendar cal = Calendar.getInstance();
		
		Calendar birthCal = Calendar.getInstance();
		birthCal.setTime(birthDay);
		if (cal.before(birthCal)) {
			if(!minus) {
				map.put("age", age);
				map.put("month", month);
				map.put("day", day);
				return map;
			} else {
				day = getDaysBetween(birthDay,new Date()).intValue();
				map.put("age", age);
				map.put("month", month);
				map.put("day", day);
				return map;
			}
			
//			throw new IllegalArgumentException("The birthDay is before Now.It's unbelievable!");
		}

		int yearNow = cal.get(Calendar.YEAR);
		int monthNow = cal.get(Calendar.MONTH);
		int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
		cal.add(Calendar.MONTH, -1);
		int maxDayOfBeforeMonth = cal.getActualMaximum(Calendar.DATE);
		
		cal.setTime(birthDay);

		int yearBirth = cal.get(Calendar.YEAR);
		int monthBirth = cal.get(Calendar.MONTH);
		int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
		
		age = yearNow - yearBirth;
		
		if (monthNow <= monthBirth) {
			if (monthNow == monthBirth) {
				// monthNow==monthBirth
				if (dayOfMonthNow < dayOfMonthBirth) {
					month = 11;
					day = (maxDayOfBeforeMonth-dayOfMonthBirth) + dayOfMonthNow;
					age--;
				} else {
					if(dayOfMonthNow > dayOfMonthBirth) {
						day = dayOfMonthNow - dayOfMonthBirth;
					}
				}
			} else {
				// monthNow<monthBirth
				if (dayOfMonthNow < dayOfMonthBirth) {
					month = (12-monthBirth) + (monthNow-1);
					day = (maxDayOfBeforeMonth-dayOfMonthBirth) + dayOfMonthNow;
				} else {
					month = (12-monthBirth) + monthNow;
					if(dayOfMonthNow > dayOfMonthBirth) {
						day = dayOfMonthNow - dayOfMonthBirth;
					}
				}
				
				age--;
				
			}
		} else {
			// monthNow>monthBirth
			if (dayOfMonthNow < dayOfMonthBirth) {
				month = monthNow - monthBirth -1;
				day = (maxDayOfBeforeMonth-dayOfMonthBirth) + dayOfMonthNow;
			} else {
				month = monthNow - monthBirth;
				if(dayOfMonthNow > dayOfMonthBirth) {
					day = dayOfMonthNow - dayOfMonthBirth;
				}
			}
		}
		
		if (age < 0)
			age = 0;
		if (age > 100)
			age = 100;
		
		map.put("age", age);
		map.put("month", month);
		map.put("day", day);
		
		return map;
	}
	
	/**
	 * 获取年龄，月龄字符串
	 * 
	 * @param date
	 * @return
	 */
	public static String getAgeForStr(Date date) {
		StringBuffer str = new StringBuffer();
		Map<String,Integer> map = getAgeForMap(date,false);
		if(map== null || map.isEmpty()) {
			return "";
		}
		
		if(map.get("age") > 0) {
			str.append(map.get("age")+"年");
		}
		if(map.get("month") > 0) {
			str.append(map.get("month")+"个月");
		}
		if(map.get("day") >= 0) {
			str.append(map.get("day")+"天");
		}
		
		return str.toString();
	}
	
	public static Long getDaysBetween(Date startDate, Date endDate) {  
        Calendar fromCalendar = Calendar.getInstance();  
        fromCalendar.setTime(startDate);  
        fromCalendar.set(Calendar.HOUR_OF_DAY, 0);  
        fromCalendar.set(Calendar.MINUTE, 0);  
        fromCalendar.set(Calendar.SECOND, 0);  
        fromCalendar.set(Calendar.MILLISECOND, 0);  
  
        Calendar toCalendar = Calendar.getInstance();  
        toCalendar.setTime(endDate);  
        toCalendar.set(Calendar.HOUR_OF_DAY, 0);  
        toCalendar.set(Calendar.MINUTE, 0);  
        toCalendar.set(Calendar.SECOND, 0);  
        toCalendar.set(Calendar.MILLISECOND, 0);  
  
        return (toCalendar.getTime().getTime() - fromCalendar.getTime().getTime()) / (1000 * 60 * 60 * 24);  
    }

	/**
	 * 获取昨天的日期
	 * 
	 * @return
	 */
	public static String getYesterday() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		String yesterday = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());

		return yesterday;
	}

	/**
	 * 获取位移的日期
	 * 
	 * @return
	 */
	public static String getOffsetDatetimeStr(int offset, String sFormat) {
		if (sFormat == null )
			sFormat = DEFAULT_DATETIME_FORMAT;

		return new SimpleDateFormat(sFormat).format(getOffsetDatetime(offset));		
	}

	/**
	 * 获取位移的日期
	 * 
	 * @return
	 */
	public static Date getOffsetDatetime(int offset) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, offset);
		return cal.getTime();		
	}
	
	public static String getDateWithHour(int hour) {
        Calendar cal = Calendar.getInstance();
        int hourOfDay = cal.get(Calendar.HOUR_OF_DAY);
        if(hourOfDay >= hour) {
            cal.add(Calendar.DAY_OF_MONTH, 1);
        }
        return  new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
	}
	
	public static String getInterval(Date createTime) { //传入的时间格式必须类似于2012-8-21 17:53:20这样的格式  
        String interval = null;  
        //用现在距离1970年的时间间隔new Date().getTime()减去以前的时间距离1970年的时间间隔d1.getTime()得出的就是以前的时间与现在时间的时间间隔  
        long time = new Date().getTime() - createTime.getTime();// 得出的时间间隔是毫秒  
          
        if(time/1000 < 10 && time/1000 >= 0) {  
        //如果时间间隔小于10秒则显示“刚刚”time/10得出的时间间隔的单位是秒  
            interval ="刚刚";  
              
        } else if(time/3600000 < 24 && time/3600000 > 0) {  
        //如果时间间隔小于24小时则显示多少小时前  
            int h = (int) (time/3600000);//得出的时间间隔的单位是小时  
            interval = h + "小时前";  
              
        } else if(time/60000 < 60 && time/60000 > 0) {  
        //如果时间间隔小于60分钟则显示多少分钟前  
            int m = (int) ((time%3600000)/60000);//得出的时间间隔的单位是分钟  
            interval = m + "分钟前";  
              
        } else if(time/1000 < 60 && time/1000 > 0) {  
        //如果时间间隔小于60秒则显示多少秒前  
            int se = (int) ((time%60000)/1000);  
            interval = se + "秒前";  
              
        }else {  
            //大于24小时，则显示正常的时间，但是不显示秒 
            if(isSameYear(createTime,new Date())) {//当前年只显示月，日
                SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");  
                interval = sdf.format(createTime); 
            } else {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
                interval = sdf.format(createTime); 
            }
        }  
        return interval;  
    }
	
	public static boolean isSameYear(Date date1,Date date2) {
	    Calendar cal1 = Calendar.getInstance();
	       cal1.setTime(date1);

	       Calendar cal2 = Calendar.getInstance();
	       cal2.setTime(date2);

	       return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
	}

}