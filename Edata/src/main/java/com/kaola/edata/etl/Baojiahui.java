package com.kaola.edata.etl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Baojiahui {

	public static void main(String[] args) {
		   
	        try {		 
	        	File file = new File(args[0]);
				    //File file = new File("");
			    BufferedReader reader = null;
			    FileWriter fileWritter = new FileWriter(args[1],false);
		        BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
	            System.out.println("以行为单位读取文件内容，一次读一整行：");
	            reader = new BufferedReader(new FileReader(file));
	            String tempString="";
	            Pattern pattern1 = Pattern.compile("受处罚人(个人)：|受罚单位：|受罚人：|被处罚单位名称：|受罚人姓名：|受处罚人(个人)：|处罚机构：|受罚（机构）：|受 处 罚 人：|.*被告知人[：|:]|.*处罚单位[：|:]|.*受处理人[：|:]|.*被处罚人[：|:]|.*机构名称[：|:]|.*受罚人（机构）[：|:]|.*受 罚 人[：|:]|.*受处罚人名称[：|:]|.*受处罚机构名称[：|:]|.*受处罚单位[：|:]|.*受处罚人员姓名[：|:]|.*受处罚机构[：|:]|.*被处罚单位[：|:]|.*受处罚机构[：|:]|.*受处罚人（机构）[：|:]|.*受罚单位 受 罚 人[：|:]|受处罚人名称[：|:]|.*受处罚人姓名[：|:]|.*受处罚单位[：|:]|.*受处罚人[：|:]|.*当事人[：|:]|.*当[ | ]*事[ | ]*人[：|:]|.*当事人名称[：|:]|.*当事人姓名[：|:]");
	            //Pattern pattern2 = Pattern.compile("身份证号码：|身份证号：");
	            int  nn=0;
	            while ((tempString = reader.readLine()) != null) {
	            	if(tempString.indexOf(".xls")>-1){
	            		continue;
	            	}
	            	String split[]=tempString.split("\001");
	            	//System.out.println(split.length);
	            	//System.out.println("*****"+tempString);
	            	if(split.length<2)
	            		continue;
	            	try{
	            		String danshiren="";
	            		String newct="";
	            		String content[]=split[2].split("\002");	            		         
	            		for(String sp : content){
	            			//System.out.println(sp);         			
	            			if(sp.indexOf("身份证")>-1){
	            				sp=sp.replaceAll("x", "*");
	            				//System.out.println("!!!"+sp);
	            				if(sp.contains("XX")){
	            					sp=sp.replaceAll("X", "*");
	            				}
	            				newct=newct+sp+"\002";
	            				//System.out.println("!!"+newct);
	            			}else
	            				newct=newct+sp+"\002";	            	
	            			Matcher match1=pattern1.matcher(sp);
	            			StringBuffer  sb1=new StringBuffer();
	            			if(match1.find()){
	            				match1.appendTail(sb1);
	            				String str=sb1.toString();
	            				//System.out.println(sb1);
	            				int begin=str.indexOf("：");
	            				if(begin<1)
	            					begin=str.indexOf(":");
	            				if(begin<1)
	            					begin=0;
	            				else
	            					begin=begin+1;
	            				int end=str.indexOf(" ",begin+3);
	            				
	            				if(end<1)
	            					end=str.indexOf("，");
	            				if(end<1||end<begin)
	            					end=str.length();
//	            				System.out.println(begin);
//	            				System.out.println(end);
//	            				System.out.println(str.length());
	            				
	            				danshiren=danshiren+" "+str.substring(begin,end);
	            				//System.out.println(danshiren);
	            			}
	            			
	            			//key=key+split[	Integer.parseInt(sp)];
	            		}
	            		//System.out.println(danshiren);
	            	    if(danshiren.equals("")){
	            	    	System.out.println("**"+tempString);
	            	    	danshiren=" ";
	            	    	nn++;
	            	    	System.out.println(nn);
	            	    } 	
	            		//System.out.println(key);
		            	bufferWritter.write(split[0]+"\001"+split[1]+"\001"+newct+"\001"+danshiren+"\n\r");
		            	bufferWritter.flush();
	            	}catch(Exception o){
	            		o.printStackTrace();
	            	}
	            	
	            }
	        }catch(Exception o){
	        	o.printStackTrace();
	        }
	}

}
