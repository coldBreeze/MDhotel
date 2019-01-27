package com.mdhotel.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 鏃ユ湡宸ュ叿绫?
 
 *
 */
public class DateUtils {
	public static Date parseTimestamp(String dateStr){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = dateFormat.parse(dateStr);
		}
		catch(ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	public static String parseDateTime2(Date date){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStr = null;
		try {
			dateStr = dateFormat.format(date);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return dateStr;
	}
	public static String parseDateTime(Date date){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStr = null;
		try {
			dateStr = dateFormat.format(date);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		dateStr=dateStr.replace("-","").replace(" ", "").replace(":", "");
		return dateStr;
	}
	public static Date parseDate(String dateStr){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = dateFormat.parse(dateStr);
		}
		catch(ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	public static String parseDate(Date date){
		String dateStr = "";
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			dateStr = dateFormat.format(date);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return dateStr;
	}
	public static Date getPrevDate(Date date) {  
        Calendar calendar = Calendar.getInstance();  
        calendar.setTime(date);  
        calendar.add(Calendar.DAY_OF_MONTH, -1);  
        date = calendar.getTime();  
        return date;  
    } 
	//获取当前系统前一天
	public static String getPrevDay(Date date) {  
        Calendar calendar = Calendar.getInstance();  
        calendar.setTime(date);   
        date = calendar.getTime();  
		int month=date.getMonth()+1;
		int day=date.getDate();
		String m=month+"";
		String d=day+"";
		if(month<10){
			m="0"+m;
		}
		if(day<10){
			d="0"+d;
		}
        return m+d;  
    } 
	public static void main(String[] args) {
		Date date=new Date();
		for(int i=0;i<7;i++){
			String day=getPrevDay(date);
			date=getPrevDate(date);
			System.out.println(day);
		}
	}
}
