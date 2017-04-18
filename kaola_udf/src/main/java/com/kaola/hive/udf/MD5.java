package com.kaola.hive.udf;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.hadoop.hive.ql.exec.UDF;

import com.sun.javadoc.ThrowsTag;

import sun.misc.BASE64Decoder;

public class MD5  extends UDF {
	
	  public String evaluate(String str){
		  return code(str);
	  }
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
	public static String toMd5(String inStr) throws Exception {
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
			throw new Exception("MD5加密失败！");
		}
		return hexValue.toString();
	}
	
	public static String getTime() throws Exception {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		SimpleDateFormat sdf_end = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Date sysDate = new Date();
		cal.setTime(sysDate);
		
		cal.add(Calendar.YEAR, -1);
		
		Date yesDate = cal.getTime();
		
		return sdf_end.parse(sdf.format(yesDate)).getTime()/ 1000L+ ","+sdf_end.parse(sdf.format(sysDate)).getTime()/ 1000L;
		
	}
	
	public static void main(String[] args) throws Exception {
		String str = "sss以行为单位读取文件内容，一次读一整行：以行为单位读取文件内容，一次读一整行：以行为单位读取文件内容，一次读一整行：以行为单位读取文件内容，一次读一整行：/以行为单位读取文件内容，一次读一整行：/以行为单位读取文件内容，一次读一整行：/以行为单位读取文件内容，一次读一整行：";
	   // System.out.println(code(str));
	    String str2 = "qqqqsss以行为单位读取文件内容，一次读一整行：以行为单位读取文件内容，一次读一整行：以行为单位读取文件内容，一次读一整行：以行为单位读取文件内容，一次读一整行：/以行为单位读取文件内容，一次读一整行：/以行为单位读取文件内容，一次读一整行：/以行为单位读取文件内容，一次读一整行：";
	    
	    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	    Date startDate = df.parse("2016-12-14 00:00:39");
	    long date = (startDate.getTime() / 1000L);
	    System.out.println(date);
	    
	    
	    MD5 md5 = new MD5();

	    System.out.println("===="+DigestUtils.md5Hex("822100050392640")+"_detail_*/cf:col/"+getTime());
	    System.out.println("===="+DigestUtils.md5Hex("822100050392766")+"_detail_*/cf:col/"+getTime());
	    

	    
	    System.out.println(new String(new BASE64Decoder().decodeBuffer("ODIyNTg0MDU3MzIwNDQ5LEpKSyzlhpzkuJrpk7booYwsMTg1MDAsMjAxNi0wOS0yNSAxNzoxODo0NiwxNTYsMCwxNjA5MjU1MTg1MjU1NSxTLDAxMjAwMQ==")));
//	    822305076290008
//	    http://10.1.80.172:20550/lklpos_atm_risk_control/c53ef8814387cc25d62e650bacf0258d_rk_*/cf:col/1449590400000,1481212800000
//	    返回：	无
//	    	
//	    822161059984268		
//	    http://10.1.80.172:20550/lklpos_atm_risk_control/0f0babba782849a0f78210f7d0f281e6_rk_*/cf:col/1449590400000,1481212800000
//	    返回：15122031767536,822161059984268,20151220,\N,80,\N,\N,HPS03,严重,20151220050000 	
//	    
//	    
//	    822100045110185
//	    http://10.1.80.172:20550/lklpos_atm_risk_control/89ef76e76ea7c94a4c3604fcf381ff82_rk_*/cf:col/1449590400000,1481212800000
//	    返回：16010532398826,822100045110185,20160105,\N,80,\N,\N,HPS03,严重,20160105050000	
	    
	}
	
}
