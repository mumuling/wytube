package com.cqxb.yecall.until;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeRender {

	private static SimpleDateFormat formatBuilder;

	/**
	 * 
	 * @param format "YYYY-MM-dd  hh:mm:ss"
	 * @return "yyyy-MM-dd  hh:mm:ss"
	 */
	public static String getDate(String format) {
		formatBuilder = new SimpleDateFormat(format);
		return formatBuilder.format(new Date());
	}

	/**
	 * 
	 * @return "MM-dd  hh:mm:ss"
	 */
	public static String getDate() {
		return getDate("MM-dd  hh:mm:ss");
		//return getDate("hh:mm:ss");
	}
	
	/**
	 * 时间戳
	 * @return
	 */
	public static Long getTimes(){
		return System.currentTimeMillis();  
	}
	
	
	/**
	 * long  转 Date
	 * @param time
	 * @param format
	 * @return
	 */
	public static String longToDate(Long time,String format){
		SimpleDateFormat sdf=new SimpleDateFormat(format);
		Date date = new Date(time);
		return sdf.format(date);
	}
	
	
	
	/**
	 * 格式化时间为时分秒
	 * @param time
	 * @return
	 */
    public static String secToTime(int time) {
        String timeStr = null;
        int hour = 0;
        int minute = 0;
        int second = 0;
        if (time <= 0)
            return "0秒";
        else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                if(minute>0){
                	timeStr =unitFormat(minute) + "分" + unitFormat(second)+"秒";
                }else{
                	timeStr = unitFormat(second)+"秒";
                }
            } else {
                hour = minute / 60;
                if (hour > 99)  
                    return "99时59分59秒";  
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;
                if(hour>0){
                	timeStr = unitFormat(hour) + "时" + unitFormat(minute) + "分" + unitFormat(second)+"秒";
                }else{
                	timeStr = unitFormat(minute) + "分" + unitFormat(second)+"秒";
                }
            }
        }
        return timeStr;
    }

    public static String unitFormat(int i) {
        String retStr = null;
        if (i >= 0 && i < 10)
            retStr = "0" + Integer.toString(i);
        else
            retStr = "" + i;
        return retStr;
    }
}
