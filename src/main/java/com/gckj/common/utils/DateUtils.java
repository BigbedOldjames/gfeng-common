package com.gckj.common.utils;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * @Description：日期工具类, 继承org.apache.commons.lang.time.DateUtils类
 * @author：ldc
 * date：2020-06-23
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
	
	private static String[] parsePatterns = {
		"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM", 
		"yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
		"yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM",
		"yyyyMMddHHmmss", "yyyyMMdd"};

	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd）
	 */
	public static String getDate() {
		return getDate("yyyy-MM-dd");
	}
	
	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String getDate(String pattern) {
		return DateFormatUtils.format(new Date(), pattern);
	}
	
	/**
	 * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String formatDate(Date date, Object... pattern) {
		String formatDate = null;
		if (pattern != null && pattern.length > 0) {
			formatDate = DateFormatUtils.format(date, pattern[0].toString());
		} else {
			formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
		}
		return formatDate;
	}
	
	/**
	 * 得到日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String formatDateTime(Date date) {
		return formatDate(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到当前时间字符串 格式（HH:mm:ss）
	 */
	public static String getTime() {
		return formatDate(new Date(), "HH:mm:ss");
	}

	/**
	 * 得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String getDateTime() {
		return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到当前年份字符串 格式（yyyy）
	 */
	public static String getYear() {
		return formatDate(new Date(), "yyyy");
	}

	/**
	 * 得到当前月份字符串 格式（MM）
	 */
	public static String getMonth() {
		return formatDate(new Date(), "MM");
	}

	/**
	 * 得到当天字符串 格式（dd）
	 */
	public static String getDay() {
		return formatDate(new Date(), "dd");
	}

	/**
	 * 得到当前星期字符串 格式（E）星期几
	 */
	public static String getWeek() {
		return formatDate(new Date(), "E");
	}
	
	/**
	 * 日期型字符串转化为日期 格式
	 * { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", 
	 *   "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm",
	 *   "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm" }
	 */
	public static Date parseDate(Object str) {
		if (str == null){
			return null;
		}
		try {
			return parseDate(str.toString(), parsePatterns);
		} catch (ParseException e) {
			return null;
		}
	}
	
	/**
	 * 获取过去的天数
	 * @param date
	 * @return
	 */
	public static long pastDays(Date date) {
		long t = new Date().getTime()-date.getTime();
		return t/(24*60*60*1000);
	}

	/**
	 * 获取过去的小时
	 * @param date
	 * @return
	 */
	public static long pastHour(Date date) {
		long t = new Date().getTime()-date.getTime();
		return t/(60*60*1000);
	}
	
	/**
	 * 获取过去的分钟
	 * @param date
	 * @return
	 */
	public static long pastMinutes(Date date) {
		long t = new Date().getTime()-date.getTime();
		return t/(60*1000);
	}
	
	/**
	 * 转换为时间（天,时:分:秒.毫秒）
	 * @param timeMillis
	 * @return
	 */
    public static String formatDateTime(long timeMillis){
		long day = timeMillis/(24*60*60*1000);
		long hour = (timeMillis/(60*60*1000)-day*24);
		long min = ((timeMillis/(60*1000))-day*24*60-hour*60);
		long s = (timeMillis/1000-day*24*60*60-hour*60*60-min*60);
		long sss = (timeMillis-day*24*60*60*1000-hour*60*60*1000-min*60*1000-s*1000);
		return (day>0?day+",":"")+hour+":"+min+":"+s+"."+sss;
    }
	
	/**
	 * 获取两个日期之间的天数
	 * 
	 * @param before
	 * @param after
	 * @return
	 */
	public static double getDistanceOfTwoDate(Date before, Date after) {
		long beforeTime = before.getTime();
		long afterTime = after.getTime();
		return (afterTime - beforeTime) / (1000 * 60 * 60 * 24);
	}
	
	/**
	 *  获取当前月第一天：
	 */
	public static Date getCurMonthFirstDate() {
		Calendar c = Calendar.getInstance();   
	    c.add(Calendar.MONTH, 0);
	    c.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
	    return c.getTime();
	}
	
	/**
	 *  获取当前月第一天：
	 */
	public static String getCurMonthFirst() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
		Date date = getCurMonthFirstDate();
	    return format.format(date);
	}
	
	/**
	 * 获取当前月最后一天
	 * @return
	 */
	public static String getEndMonthLast() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
		Date date = getMaxMonthDate();
	    return format.format(date);
	}
	
	/**
	 *  增加开始时分秒后缀：
	 */
	public static String addStartSuffix(Object str) {
	    return str + " 00:00:00";
	}
	
	/**
	 *  增加结束时分秒后缀：
	 */
	public static String addEndSuffix(Object str) {
	    return str + " 23:59:59";
	}

	/**
	 * 得到几天前的日期
	 * @return
	 * @throws ParseException
	 */
	public static String getStatetime(int countDay){

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, - countDay);
		Date monday = c.getTime();
		String preMonday = sdf.format(monday);
		return preMonday;
	}

	/**
	 * 获取几天前的日期
	 * @param countDay
	 * @return
	 */
	public static String getStateTime(int countDay){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, - countDay);
		Date monday = c.getTime();
		String preMonday = sdf.format(monday);
		return preMonday;
	}
	
	/**
	 * 得到某个时间几天前的日期
	 * @return
	 * @throws ParseException
	 */
	public static String getStatetime(Date date,int countDay){

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, - countDay);
		Date monday = c.getTime();
		String preMonday = sdf.format(monday);
		return preMonday;
	}
	
	/**
	 * 获取上个月的最后一天.
	 * @return 
	 */
	public static String getLastMaxMonthDate(String pattern) {
        SimpleDateFormat dft = new SimpleDateFormat(pattern);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return dft.format(calendar.getTime());
    }
	
	
	/**
	 * 获取某个时间下个月第一天
	 * @return
	 */
	public static Date nextMonthFirstDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.MONTH, 1);
        return calendar.getTime();
    }
	
	/**
	 * 获取某个时间下个月最后一天
	 * @return
	 */
	public static Date nextMonthLastDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return calendar.getTime();
    }
	
	/**
	 * 获取某个时间上个月第一天
	 * @return
	 */
	public static Date lastMonthFirstDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        return calendar.getTime();
    }
	
	/**
	 * 获取某个时间去年这个月第一天
	 * @return
	 */
	public static Date lastYearFirstDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -12);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        return calendar.getTime();
    }
	
	/**
	 * 获取某个时间上个月最后一天
	 * @return
	 */
	public static Date lastMonthMaxDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return calendar.getTime();
    }
	
	/**
	 * 获取当月的最后一天.
	 * @return 
	 */
	public static String getMaxMonth(String pattern) {
        SimpleDateFormat dft = new SimpleDateFormat(pattern);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return dft.format(calendar.getTime());
    }
	
	/**
	 * 获取当月的最后一天.
	 * @return 
	 */
	public static Date getMaxMonthDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return calendar.getTime();
    }
	
	/**
	 * 取得月第一天
     *
	 * @param date
     * @return
	 **/
	public static Date getFirstDateOfMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
		return c.getTime();
	}
	
	/**
	 * 取得月最后一天
     *
	 * @param date
     * @return
	 **/
	public static Date getLastDateOfMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		return c.getTime();
	}

	/**
	 * 取得月最后一天
	 *
	 * @param date
	 * @return
	 */
	public static Date getFirstDateOfNextMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.MONTH, c.get(Calendar.MONTH)+1);
		c.set(Calendar.DAY_OF_MONTH, 1);
		return c.getTime();
	}

	/**
	 * 根据年月取得月最后一天
	 *
	 * @param int year
	 * @param int month
	 * @return
	 */
	public static String getLastDayOfMonth(int year, int month) {     
        Calendar cal = Calendar.getInstance();     
        cal.set(Calendar.YEAR, year);     
        cal.set(Calendar.MONTH, month-1);     
        cal.set(Calendar.DAY_OF_MONTH,cal.getActualMaximum(Calendar.DATE));  
       return  new   SimpleDateFormat( "yyyy-MM-dd ").format(cal.getTime());  
    }
	
	/**
	 * 根据年月取得月第一天
	 *
	 * @param int year
	 * @param int month
	 * @return
	 */
	public static String getFirstDayOfMonth(int year, int month) {     
        Calendar cal = Calendar.getInstance();     
        cal.set(Calendar.YEAR, year);     
        cal.set(Calendar.MONTH, month-1);  
        cal.set(Calendar.DAY_OF_MONTH,cal.getMinimum(Calendar.DATE));  
       return   new   SimpleDateFormat( "yyyy-MM-dd ").format(cal.getTime());  
    }
	
	/**
	 * 获取一个月的所有天数-拉数据
	 * @param firstDay
	 * 			开始时间
	 * @param lastDay
	 * 			结束时间
	 * @return
	 */
	public static String getDayDate(String firstDay,String lastDay) {     
		String str = "";
		Date currentDayDate = new Date();
		if(StringUtils.isNotNull(firstDay)){
			str = firstDay;
			 currentDayDate = DateUtils.parseDate(firstDay);
		}else{
			str = "2016-07-31 08:00:00";
			currentDayDate = DateUtils.parseDate(firstDay);
		}
		StringBuilder sb = new StringBuilder();
		sb.append(str);
		String str1 = str.substring(8, 10);
		int day = Integer.parseInt(str1);
		int newDay = day+1;
		StringBuilder replace = new  StringBuilder();
		if(newDay < 30){
			if(newDay >= 10){
				replace = sb.replace(8, 10, newDay+"");
			}else{
				replace = sb.replace(9, 10, newDay+"");
			}
			str = replace.toString();
		}else{
			String substring = str.substring(5,7);
			int day1 = Integer.parseInt(substring);
			int newDay1 = day1+1;
			if(newDay1 < 10){
				replace = sb.replace(5, 7, "0"+newDay1+"");
				String string = replace.toString();
				StringBuilder A1 = new StringBuilder();
				A1.append(string);
				replace = A1.replace(8, 10, "01");
				str = replace.toString();
				return str;
			}else if(newDay1 <= 12){
				replace = sb.replace(5, 7, newDay1+"");
				String string = replace.toString();
				StringBuilder A1 = new StringBuilder();
				A1.append(string);
				replace = A1.replace(8, 10, "01");
				getDayDate(replace.toString(),lastDay);
				str = replace.toString();
				return str;
			}else if(newDay1 == 13){
				String S1 = sb.substring(2,4);
				int dayA2 = Integer.parseInt(S1);
				int newDayA1 = dayA2+1;
				
				replace = sb.replace(2, 4, newDayA1+"");
				replace.replace(4,10,"-01-01");
				str = replace.toString();
				return str;
			}else{
				replace = sb.replace(5, 7, "01");
				String string = replace.toString();
				StringBuilder A1 = new StringBuilder();
				A1.append(string);
				replace = A1.replace(8, 10, "01");
				str = replace.toString();
				return str;
			}
		}
		return str;  
    }
	
	/**
	 * 日期向后推迟两天,时分秒随机生成
	 * @param date
	 * @return
	 */
	public static String getTwoDate(Date date) {
		Calendar calendar  =  Calendar.getInstance();
	    calendar.setTime(date); //需要将date数据转移到Calender对象中操作
	    Random random = new Random();
	    int sj = random.nextInt(3)+1;
	    calendar.add(calendar.DATE,sj);//把日期往后增加n天.正数往后推,负数往前移动 
	    date=calendar.getTime();   
	    String strDate = DateUtils.formatDate(date, "yyyy-MM-dd HH:mm:ss");
	    StringBuilder oldDate = new StringBuilder();
	    oldDate.append(strDate);
	    int hourSJ = random.nextInt(23)+1;
	    String hourStr = "";
	    if(hourSJ < 10){
	    	hourStr = "0"+hourSJ;
	    }else{
	    	hourStr = hourSJ+"";
	    }
	    StringBuilder hourDate = oldDate.replace(11, 13, hourStr);
	    int minSJ = random.nextInt(59)+1;
	    String minStr = "";
	    if(minSJ < 10){
	    	minStr = "0"+minSJ;
	    }else{
	    	minStr = minSJ+"";
	    }
	    StringBuilder minDate = hourDate.replace(14, 16, minStr);
	    int secondSJ = random.nextInt(899)+1;
	    String secondStr = "";
	    if(secondSJ < 10){
	    	secondStr = "0"+secondSJ;
	    }else{
	    	secondStr = secondSJ+"";
	    }
	    StringBuilder newDate = minDate.replace(17, 20, secondStr);
	    return newDate.toString();
	}
	
}
