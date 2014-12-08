package com.ui.common;

public class Validation {
	public static String ifStringNull(String input){
		String result= input;
		if ((input==null) || (input.compareToIgnoreCase("")==0)){
			result = "Tidak Ada Data";
		}
		return result;
	}
}
