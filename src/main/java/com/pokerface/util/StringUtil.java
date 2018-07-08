package com.pokerface.util;

public class StringUtil {

	public static boolean isEmpty(String str){
		return str == null || str.isEmpty();
	}
	
	public static int parseInt(String str){
		int result;
		try{
			result = Integer.parseInt(str);
		} catch(Exception e) {
			result = 0;
		}
		return result;
	}
}
