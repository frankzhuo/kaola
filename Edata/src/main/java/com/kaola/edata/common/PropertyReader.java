package com.kaola.edata.common;

import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;

public class PropertyReader {
	
	private static ResourceBundle resourceBundle;
	
	
	/**
	 * 功能:初始化加载设置配置文件
	 * <p>guoxl Apr 21, 2011 11:18:59 PM
	 * @param propertyFilePath
	 */
	 public  static void setResourceBundle(String propertyFilePath) {
		 resourceBundle = ResourceBundle.getBundle(propertyFilePath, Locale.ENGLISH);
	 }
	 
	 
	 /**
	  * 功能:通过key得到配置文件中对应的值
	  * <p>guoxl Apr 21, 2011 11:21:40 PM
	  * @param key
	  * @return
	  */
	 public static String getPropertyValueByKey(String key){
		 
		 String value="";
		 if(resourceBundle!=null){
			 value = resourceBundle.getString(key);
		 }
		 return value;
	 }
	 
	 /**
	  * 功能:得到配置文件中的所有key
	  * <p>oxl Apr 21, 2011 11:23:35 PM
	  * @return
	  */
	public static Enumeration<String> getKeys(){
		Enumeration<String> keys=null;
		if(resourceBundle!=null){
			keys =resourceBundle.getKeys();
		}
		return keys;
	}
	 
	
}
