package com.kaola.edata.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
	/**
	 * MD5加密类
	 * @param str 要加密的字符串
	 * @return    加密后的字符串
	 */
	public static String code(String str){
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes());
			byte[]byteDigest = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < byteDigest.length; offset++) {
				i = byteDigest[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			//32位加密
			return buf.toString();
			// 16位的加密
			//return buf.toString().substring(8, 24); 
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	/**
	 * xinglibao
	 * @param inStr
	 * @return
	 * @throws Exception
	 */
	public static String toMd5(String inStr)  {
		MessageDigest md5 = null;
		StringBuffer hexValue = new StringBuffer();
		try {
			md5 = MessageDigest.getInstance("MD5");
			char[] charArray = inStr.toCharArray();
			byte[] byteArray = new byte[charArray.length];

			for (int i = 0; i < charArray.length; i++)
				byteArray[i] = (byte) charArray[i];

			byte[] md5Bytes = md5.digest(byteArray);

			for (int i = 0; i < md5Bytes.length; i++) {
				int val = ((int) md5Bytes[i]) & 0xff;
				if (val < 16)
					hexValue.append("0");
				hexValue.append(Integer.toHexString(val));
			}
		} catch (Exception e) {
			//throw new Exception("MD5加密失败！");
		}
		return hexValue.toString();
	}
	
	public static void main(String[] args) {
		String str = "sss以行为单位读取文件内容，一次读一整行：以行为单位读取文件内容，一次读一整行：以行为单位读取文件内容，一次读一整行：以行为单位读取文件内容，一次读一整行：/以行为单位读取文件内容，一次读一整行：/以行为单位读取文件内容，一次读一整行：/以行为单位读取文件内容，一次读一整行：";
	    System.out.println(code(str));
	    String str2 = "qqqqsss以行为单位读取文件内容，一次读一整行：以行为单位读取文件内容，一次读一整行：以行为单位读取文件内容，一次读一整行：以行为单位读取文件内容，一次读一整行：/以行为单位读取文件内容，一次读一整行：/以行为单位读取文件内容，一次读一整行：/以行为单位读取文件内容，一次读一整行：";
	    System.out.println(MD5.code("822891054110022"));
	    System.out.println(MD5.toMd5("822891054110022"));
	}
	
}
