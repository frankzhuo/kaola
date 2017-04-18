package com.kaola.edata;

import java.text.SimpleDateFormat;

import com.kaola.edata.util.MD5;

public class Md5 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println( MD5.code("833504057220332"));
		//System.out.println( MD5.code("8611209205"));
		SimpleDateFormat  d=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try{
		System.out.println(d.parse("2016-03-31 19:34:15.0").getTime());
		}catch(Exception o){
			
		}
	}

}
