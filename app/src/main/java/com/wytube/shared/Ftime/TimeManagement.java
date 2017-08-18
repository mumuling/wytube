package com.wytube.shared.Ftime;

import java.text.ParseException;

/**
 * 时间转化工具类
 * @author max.chengdu 2015年11月29日
 *
 */
public class TimeManagement {
	/**
	 * 返回 yyyy-MM-dd
	 *
	 * @param String 参数为String类型yyyy-MM-dd HH:mm:ss
	 * @return 返回 yyyy-MM-dd
	 * @throws ParseException
	 */
	public static String exchangeStringDate(String date) throws ParseException {
		if (date != null && date.length() > 10) {
			String result = date.substring(0, 10);
			return result;
		}else{
			return null;
		}

	}

	/**
	 * 返回HH:mm:ss
	 *
	 * @param String 参数为String类型
	 * @return 返回HH:mm:ss
	 * @throws ParseException
	 */
	public static String exchangeStringTime(String date) throws ParseException {
		if (date != null && date.length() > 10) {
			String result = date.substring(10, date.length());
			return result;
		}else{
			return null;
		}
	}


}
