package com.kaola.edata;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import com.kaola.edata.util.MD5;

public class UniqueDataMD5 {

	public static void main(String[] args) {
		   
	        try {		 
	        	File file = new File(args[0]);
			    BufferedReader reader = null;
			    FileWriter fileWritter = new FileWriter(args[0]+"r",false);
		        BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
	            System.out.println("以行为单位读取文件内容，一次读一整行：");
	            reader = new BufferedReader(new FileReader(file));
	            String tempString="";
	            String []split1=args[2].split("#");
	            
	            while ((tempString = reader.readLine()) != null) {
	            	String split[]=tempString.split("\001");
	            	System.out.println(split.length);
	            	if(split.length<2)
	            		continue;
	            	try{
	            		String key="";
	            		for(String sp : split1){
	            			key=key+split[Integer.parseInt(sp)];
	            		}
	            		key = MD5.code(key);
		            	if(JredisUtil.hgetS(args[1], key)==null){	                
		            		bufferWritter.write(tempString+"\n\r");
		            		JredisUtil.hsetS(args[1], key,"ok");
		            	}else{
		            		System.out.println(key+"重复"+tempString);
		            	}
	            	}catch(Exception o){
	            		o.printStackTrace();
	            	}
	            }
	            if(bufferWritter !=null ){
	            	bufferWritter.close();
	            }
	            if(reader !=null ){
	            	reader.close();
	            }
	        }catch(Exception o){
	        	o.printStackTrace();
	        }
	}

}
