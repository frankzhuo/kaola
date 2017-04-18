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




public class JudgeFileNG {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		try{
			//File file = new File("d:\\xinshi.txt");
		    File file = new File("d:\\washed_judicial_3.txt.COMPLETED");
	        BufferedReader reader = null;
	     
         
           //int  i=1;
	        try {
	            System.out.println("以行为单位读取文件内容，一次读一整行：");
	            reader = new BufferedReader(new FileReader(file));
	            String tempString = null;
	            //int line = 1;
	            // 一次读入一行，直到读入null为文件结束
	            HanLP.Config.ShowTermNature = true;    // 关闭词性显示
	           // Segment segment = new CRFSegment().enableCustomDictionary(false);
	            int  j=1;
	            boolean  flag=false;
	            String yuangao="";
	            String beigao="";
	            while ((tempString = reader.readLine()) != null) {
	            	FileWriter fileWritter = new FileWriter("d:\\judgement5\\"+(500000+j)+".txt",true);
	                BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
	                // 显示行号
	            	flag=false;
	                //System.out.println(j++);
	                //bufferWritter.write(i+tempString+"\n");
	                //segment.seg(tempString);
	            	//System.out.println(tempString);
	            	yuangao="";
		            beigao="";
	            	Segment segment = HanLP.newSegment().enableNameRecognize(true).enableOrganizationRecognize(true).enablePlaceRecognize(false);
	                List<Term> termList = segment.seg(tempString);
	                //System.out.println(termList);
	                int begin=tempString.indexOf("判决如下：");
	                if(begin==-1)
	                	begin=tempString.indexOf("裁定如下：");	 
	                if(begin==-1)
	                	begin=tempString.indexOf("判决如下：");
	                if(begin==-1)
	                	begin=tempString.indexOf("本院认为：");
	                if(begin==-1)
	                	begin=tempString.indexOf("本院认为，");	 
	                
	                
	                int end=tempString.indexOf("审　判　长");
	                if(end==-1)
	                	end=tempString.length();
	                String str="";
	                if(begin!=-1&&end!=-1&&begin<end){
	                	str=tempString.substring(begin+5, end);
	                }else
	                	str=tempString;
	             
	                System.out.println(str);
	                System.out.println("begin"+begin+"end"+end);
	                fileWritter.write(str);
	                bufferWritter.close();
	                j++;
	 				 //IndexResponse response =(IndexResponse)client.prepareIndex("twitter", "tweet").setSource(json).execute().actionGet();
	 	
	            }
	            reader.close();
	            
	            
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
