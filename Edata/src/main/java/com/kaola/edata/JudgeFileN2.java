package com.kaola.edata;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.CRF.CRFSegment;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.NLPTokenizer;




public class JudgeFileN2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		try{
		
		    File file = new File(args[0]);
	        BufferedReader reader = null;
	        FileWriter fileWritter = new FileWriter(args[1],true);
            BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
            int  n=0;
           //int  i=1;
	        try {
	            System.out.println("以行为单位读取文件内容，一次读一整行：");
	            reader = new BufferedReader(new FileReader(file));
	            String tempString = null;
	            //int line = 1;
	            // 一次读入一行，直到读入null为文件结束
	            HanLP.Config.ShowTermNature = true;    // 关闭词性显示
	           // Segment segment = new CRFSegment().enableCustomDictionary(false);
	            int  j=0;
	            boolean  flag=false;
	            String yuangao="";
	            String beigao="";
	            while ((tempString = reader.readLine()) != null) {
	                // 显示行号
	            	flag=false;
	                //System.out.println(j++);
	                //bufferWritter.write(i+tempString+"\n");
	                //segment.seg(tempString);
	            	//System.out.println(tempString);
	            	yuangao="";
		            beigao="";
		            String []str=tempString.split("\001");
	            	String merge="";
	            	if(str.length>6)
	            		merge=str[2]+"."+str[6];
	            	else
	            		merge=tempString;
	            	//System.out.println(merge);
	            	String ok="";
	            	try{
	            		ok=str[6].substring(0,str[6].indexOf("。"));
	            	}catch(Exception oo){
	            		ok =merge;
	            	}
	            	
	            	Segment segment = HanLP.newSegment().enableNameRecognize(true).enableOrganizationRecognize(true).enablePlaceRecognize(false);
	                List<Term> termList = segment.seg(merge);
	                //System.out.println(termList);
	                
	                for(int  i=0;i<termList.size()-4;i++){
	                	//System.out.println(i);
	                	
	                	if(termList.get(i).word.equals("原告")||termList.get(i).word.equals("原告人")||termList.get(i).word.equals("上诉人")||termList.get(i).word.equals("申请人")||termList.get(i).word.equals("申请复议人")  ){
	                		//System.out.println(termList.get(i+2).nature.name());
	                		if(i>0&&termList.get(i-1).word.equals("原审"))
	                			continue;
	                		
	                		if(termList.get(i+1).nature.name().equals("nr")||termList.get(i+1).nature.name().equals("nt")){
	                			//System.out.println(termList.get(i+1).word);
	                			yuangao=termList.get(i+1).word;
	            	            
	                			flag=true;
	                			break;
	                			
	                		}
	                		else if(termList.get(i+1).nature.name().startsWith("w")&&(termList.get(i+2).nature.name().equals("nr")||termList.get(i+2).nature.name().equals("nt"))){
	                			//System.out.println(termList.get(i+2).word);
	                			yuangao=termList.get(i+2).word;
	                			flag=true;
	                			break;
	                	    }
	                		else if(termList.get(i+3).nature.name().equals("nr")||termList.get(i+3).nature.name().equals("nt")){
	                			//System.out.println(termList.get(i+3).word);
	                			//yuangao=termList.get(i+3).word;
	                			//flag=true;
	                			//break;
	                		}
	                		else if(termList.get(i+4).nature.name().equals("nr")||termList.get(i+4).nature.name().equals("nt")){
	                			//System.out.println(termList.get(i+4).word);
	                			//yuangao=termList.get(i+4).word;
	                			//flag=true;
	                			//break;
	                		}
	                	}
	                }
	                //System.out.println("原告："+yuangao);
	                
	                for(int  i=0;i<termList.size()-4;i++){
	                	if(i>0&&termList.get(i-1).word.equals("原审"))
                			continue;
	                	if(termList.get(i).word.equals("被告")||termList.get(i).word.equals("被告人")||termList.get(i).word.equals("被申请人")||termList.get(i).word.equals("被上诉人")||termList.get(i).word.equals("被执行人")){
	                		if(termList.get(i+1).nature.name().equals("nr")||termList.get(i+1).nature.name().equals("nt")){
	                			//System.out.println(termList.get(i+1).word);
	                			beigao=termList.get(i+1).word;
	                			flag=true;
	                			break;
	                		}
	                		else if(termList.get(i+2).nature.name().equals("nr")||termList.get(i+2).nature.name().equals("nt")){
	                			//System.out.println(termList.get(i+2).word);
	                			beigao=termList.get(i+2).word;
	                			flag=true;
	                			break;
	                	    }
	                		else if(termList.get(i+3).nature.name().equals("nr")||termList.get(i+3).nature.name().equals("nt")){
	                			//System.out.println(termList.get(i+3).word);
	                			//beigao=termList.get(i+3).word;
	                			//flag=true;
	                			break;
	                		}
	                		else if(termList.get(i+4).nature.name().equals("nr")||termList.get(i+4).nature.name().equals("nt")){
	                			//System.out.println(termList.get(i+4).word);
	                			//beigao=termList.get(i+4).word;
	                			//flag=true;
	                			break;
	                		}
	                	}
	                }
	                //System.out.println("被告："+beigao);
	               
	                
	                if(!flag){
	                	
	                	//System.out.println(tempString);
	                	//System.out.println(termList);
	                	//fileWritter.write(tempString+"\n\r");
	                	//fileWritter.write(termList+"\n\r");
	                }
	                if(beigao.equals(yuangao)){  
	                	flag=false;
	                	beigao="";
	                	yuangao="";
	                	//fileWritter.write(tempString+"\001"+yuangao+"\001"+beigao+"\n\r");	                	
	                }
	                if(!flag||yuangao.equals("")){
	                	//System.out.println(ok);
	                	try{
			                //System.out.println(""+(j++))
		                	int  ybegin=-1;
		                	int  length=0;
			                if(ok.indexOf("上诉人（原审被告）")>-1){
			                	ybegin=ok.indexOf("上诉人（原审被告）");
			                	length=9;
			                }
			                else if(ok.indexOf("上诉人（原审被告、反诉原告）")>-1){
			                	ybegin=ok.indexOf("上诉人（原审被告、反诉原告）");
			                	length=14;
			                }
			                else if(ok.indexOf("上诉人（一审原告）")>-1){
			                	ybegin=ok.indexOf("上诉人（一审原告）");
			                	length=9;
			                }		         
			                else if(ok.indexOf("再审申请人（一审被告、二审上诉人）")>-1){
			                	ybegin=ok.indexOf("再审申请人（一审被告、二审上诉人）");
			                	length=17;
			                }	             		          
			                else if(ok.indexOf("申请执行人")>-1){
			                	ybegin=ok.indexOf("申请执行人");
			                	length=5;
			                }
			                else if(ok.indexOf("申请再审人")>-1){
			                	ybegin=ok.indexOf("申请再审人");
			                	length=5;
			                }	
			                else if(ok.indexOf("上诉人（原审原告，反诉被告）")>-1){
			                	ybegin=ok.indexOf("上诉人（原审原告，反诉被告）");
			                	length=14;
			                }
			                else if(ok.indexOf("上诉人（一审被告）")>-1){
			                	ybegin=ok.indexOf("上诉人（一审被告）");
			                	length=9;
			                }	
			                else if(ok.indexOf("申诉人（一审被告、二审上诉人）")>-1){
			                	ybegin=ok.indexOf("申诉人（一审被告、二审上诉人）");
			                	length=16;
			                }				               		                
			                else if(ok.indexOf("起诉人")>-1){
			                	ybegin=ok.indexOf("起诉人");
			                	length=5;
			                }
			                else if(ok.indexOf("上诉人")>-1){
			                	ybegin=ok.indexOf("上诉人");
			                	length=5;
			                }
			                
			                else if(ok.indexOf("申请人")>-1){
			                	ybegin=ok.indexOf("申请人");
			                	length=3;
			                }
			                
			                else if(ok.indexOf("原告")>-1){
			                	ybegin=ok.indexOf("原告");
			                	length=2;
			                }
			                if(ybegin>-1){
				                int tbegin=0;
				                if(ybegin+length<ok.length())
				                	tbegin=ybegin+length;
				                //System.out.println(tbegin);
				                String tmp=ok.substring(tbegin, ok.length());
				                int  douhao=-1;
				                douhao=tmp.indexOf(",");
				                if(douhao==-1)
				                	douhao=tmp.indexOf("，");
				                if(douhao>-1)
				                	yuangao=tmp.substring(0,douhao) ;
				                else 
				                	yuangao=tmp;
				                //System.out.println(yuangao);
				                //System.out.println("**"+yuangao.replaceAll("：", "").replaceAll("）", ""));
				                yuangao=yuangao.replaceAll("：", "").replaceAll("）", "");
			                }
		                }catch(Exception o){
		                	
		                }
	                 
		                
	                }
	                if(beigao=="")
	                	beigao=" ";
	               	fileWritter.write(tempString+"\001"+yuangao+"\001"+beigao+"\n\r");	                	
	               	//System.out.println("原告："+yuangao);
	                //System.out.println("****************");
	               	if(yuangao.equals("")&&beigao.equals("")){
	               		System.out.println((n++)+"***********"+tempString);
	               	}
	                //System.out.println(""+(j++));
	               
	 				 //IndexResponse response =(IndexResponse)client.prepareIndex("twitter", "tweet").setSource(json).execute().actionGet();
	 	
	            }
	            reader.close();
	            bufferWritter.close();
	            
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            if (reader != null) {
	                try {
	                    reader.close();
	                } catch (IOException e1) {
	                }
	            }
	        }

		}catch(Exception o){
			o.printStackTrace();
		}
	}

}
