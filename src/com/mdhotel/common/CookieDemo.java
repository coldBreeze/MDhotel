package com.mdhotel.common;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieDemo {

	public static void addFormCookie(String type,String name,String value,HttpServletResponse response){
		response.setContentType("text/html;charset=utf-8");
		 String loginInfo = name+","+value;
		 System.out.println(loginInfo);      
		Cookie c=new Cookie(URLEncoder.encode(type),URLEncoder.encode(loginInfo));
		 c.setMaxAge(7*24*60*60);  
         c.setPath("/");
		response.addCookie(c);
		System.out.println(type+","+loginInfo);
		
	}
	public static String getFormCookie(HttpServletRequest request){
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Cookie[] cookies=request.getCookies();
		if(cookies!=null){
			for(Cookie c:cookies){		
				System.out.println(URLDecoder.decode(c.getName())+":"+URLDecoder.decode(c.getValue()));
					return URLDecoder.decode(c.getName())+":"+URLDecoder.decode(c.getValue());
					
			}
		}
		return "";
	}
	public static boolean hasCookie(String type,HttpServletRequest request){
	
		Cookie[] cookies=request.getCookies();
		if(cookies!=null){
			for(Cookie c:cookies){
				if(c.getName().equals(type)){
					return true;
				}
			}
			return false;
		}
		return false;	
	}
	public static boolean isCookie(HttpServletRequest request){
		Cookie[] cookies=request.getCookies();
		if(cookies!=null){
			return true;
		}
		return false;
		
	}
}
