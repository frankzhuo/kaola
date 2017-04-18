package com.kaola.edata.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Common {

	static SimpleDateFormat	s	= new SimpleDateFormat("yyyy-MM-dd");
	private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private static Date changeDateForTest() {
		Date date = new Date();
		//--------------如果想修改当前日期，可以在下面几行代码设置----------------

//		String dateStr = "2012-05-14";
//		try {
//			date = s.parse(dateStr);
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
		//----------------------------------------------------------------
		return date;
	}

	/**
	 * 获取当前日期
	 * @return
	 */
	public static String getNowDateStr() {
		Date date = new Date();
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
		date = changeDateForTest();
		return s.format(date);
	}
	
	
	/**
	 * 获取当前年
	 * @return
	 */
	public static String getYear(String date){
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			calendar.setTime(sf.parse(date));

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "" + calendar.get(Calendar.YEAR);
	}
	
	/**
	 * 获取当前月
	 * @return M
	 */
	public static String getMonth(String date){
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			calendar.setTime(sf.parse(date));

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "" + (calendar.get(Calendar.MONTH)+1);
	}
	

	/**
	 * 以当前日期为基点，加减天数，获取新的日期
	 * @param days
	 * @return
	 */
	public static String getStatisticsDate(int days) {

		String str = "";
		Date date = new Date();

		date = changeDateForTest();

		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DAY_OF_MONTH, days);
		str = s.format(c.getTime());
		return str;
	}

	/**
	 * 获取统计周增量的开始日期(参数格式必须为yy-MM-dd)
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static String getLastMaxWeekDate(String date) {

		String returnStr = "";
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(s.parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK) - 1;

		if (dayOfWeek == 0) {
			dayOfWeek = 7;
		} else if (dayOfWeek == 1) {
			dayOfWeek = 8;
		}

		int offset = 0 - dayOfWeek;
		c.add(Calendar.DATE, offset + 1);

		returnStr = s.format(c.getTime());
		return returnStr;

	}

	/**
	 * 获取月初日期(参数格式必须为yy-MM-dd)
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static String getMinMonthDate(String date) {
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(s.parse(date));
			int day = calendar.get(Calendar.DATE);
			if (day == 1) {
				calendar.add(Calendar.MONTH, -1);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		return s.format(calendar.getTime());
	}

	/**
	 * 获取当前年月
	 * @param date
	 * @return yyyy-MM
	 */
	public static String getYearMonth(String date) {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM");
		try {
			calendar.add(Calendar.MONTH, 1);
			calendar.setTime(sf.parse(date));

		} catch (ParseException e) {
			e.printStackTrace();
		}
		//	        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));  
		return sf.format(calendar.getTime());
	}
	
	
	/**
	 * 获取上月日期(YYYY-MM)
	 * @param date
	 * @return
	 */
	public static String getYearLastMonth(String date) {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM");
		try {
			
			calendar.setTime(sf.parse(date));
			calendar.add(Calendar.MONTH, -1);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return sf.format(calendar.getTime());
	}
	

	/**
	 * 获取多少号
	 * @param date
	 * @return
	 */
	public static String getDate(String date) {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			calendar.setTime(sf.parse(date));

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "" + calendar.get(Calendar.DATE);
	}

	/**
	 * 获取月末日期(参数格式必须为yy-MM-dd)
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static String getMaxMonthDate(String date) {
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(s.parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return s.format(calendar.getTime());
	}

	/**
	 * 判断是否为周日
	 * @param date
	 */
	public static boolean isSunday(String date) {
		boolean returnStr = false;

		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(s.parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int week = calendar.get(Calendar.DAY_OF_WEEK);
		if (week == 1) {
			return true;
		}
		return returnStr;
	}
	
	/**
	 * 是否为月末
	 * @param date
	 * @return
	 */
	public static boolean isMonthEnd(String date) {
		boolean returnStr = false;
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(s.parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
//		System.out.println(""+calendar.get(Calendar.DATE));
//		System.out.println(""+calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		if(calendar.get(Calendar.DATE)==calendar.getActualMaximum(Calendar.DAY_OF_MONTH)){
			return true;
		}

		return returnStr;
	}

	/**
	 * 获取今天是周几的汉字名称
	 * @param date(yyyy-MM-dd)
	 */
	public static String getDateWeekName(String date) {
		final String dayNames[] = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(s.parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
		System.out.println(dayOfWeek);
		return dayNames[dayOfWeek - 1];

	}

	public static void test() {
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(s.parse("2012-10-01"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		System.out.println(c.get(Calendar.YEAR));
	}

	/**
	 * 传入字符型日期，转为为日期型
	 * @param dateStr
	 * @return
	 */
	public static Date getStrToDate(String dateStr) {
		Date d = null;
		try {
			d = s.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return d;
	}
	
	/**
	 * 获取当前传入日期所在周的周日日期
	 * @param date
	 * @return
	 */
	public static String getWeekEndDate(String date){
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			calendar.setTime(sf.parse(date));
			int curDay = calendar.get(Calendar.DAY_OF_WEEK);
			if(curDay==1){ 
//				calendar.add(Calendar.DATE,-6); 
			} 
			else{ 
				calendar.add(Calendar.DATE,8-curDay); 
			} 
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return s.format(calendar.getTime());
	}
	
	
	/**
	 * @see 字符串转日期 格式：yyyy-MM-dd hh:mm:ss
	 * @param 字符串日期
	 * @return java.util.Date
	 * @throws ParseException
	 */
	public static Date strConvertDate(String date) {
		try {
			if (date != null) {
				return sdf.parse(date);
			} else {
				return null;
			}
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 检查是否为null
	 * @param str
	 * @return
	 */
	public static boolean isNull(String str) {
		if (str == null || str.trim().length() == 0) {
			return true;
		}

		return false;
	}
	
	

	public static void main(String args[]) {
//		String a = Common.getMinMonthDate("2012-07-01");
		System.out.println(getDateWeekName("2012-08-28"));
//		System.out.println(Common.getYearLastMonth(Common.getNowDateStr()));
		

	}

}
