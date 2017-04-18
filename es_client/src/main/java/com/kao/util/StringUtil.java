package com.kao.util;

public class StringUtil {
	/*
	 * 将字符数组转化成  以split分割符的字符串
	 */
	public static String ArrToStr(String[] arr,String split) {
		String str = "";
		for (int j = 0; j < arr.length; j++) {
			str = str + arr[j];
			if (j == arr.length - 1) {
				str = str + "\n";
			} else {
				str = str + split;
			}
		}
		return str;
	}
}
