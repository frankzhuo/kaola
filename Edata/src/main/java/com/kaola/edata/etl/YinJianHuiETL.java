package com.kaola.edata.etl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.kaola.edata.util.StringUtil;

public class YinJianHuiETL {
	public static void main(String[] args) {
         try {
			InputStreamReader read = new  InputStreamReader(new FileInputStream(new File("D:/work/crawler/chufa/yinjianfenju.txt")));
			//InputStreamReader read = new  InputStreamReader(new FileInputStream(new File("D:/work/crawler/chufa/yinjianhuijg.txt")));
			//InputStreamReader read = new  InputStreamReader(new FileInputStream(new File("D:/work/crawler/chufa/yinjianju.txt")));
			BufferedReader bufferread = new BufferedReader(read);
			
			BufferedWriter bufferwrite = new BufferedWriter(new FileWriter(new File("D:/work/crawler/chufa/yinjianfenju_etl.txt")));
			//BufferedWriter bufferwrite = new BufferedWriter(new FileWriter(new File("D:/work/crawler/chufa/yinjianhuijg_etl.txt")));
			//BufferedWriter bufferwrite = new BufferedWriter(new FileWriter(new File("D:/work/crawler/chufa/yinjianju_etl.txt")));
			String line = "";
			int i = 0;
			int num=0;
			while ((line = bufferread.readLine()) != null) {
				++num;
				if (line.split("\001").length ==10) {
					System.out.println("总行数：" + num + " 满足条件行数：" + (++i) + " colNum :" + line.split("\001").length);
					//System.out.println(line);
					String[] strs = line.split("\001");
					
					strs[9] = strs[9].replaceAll("年","-").replaceAll("月","-").replaceAll("日","");
					
					line = StringUtil.ArrToStr(strs, "\001");
					bufferwrite.write(line);
				}else{
					System.out.println(line.split("\001").length+":"+line);
				}
			}

			bufferwrite.flush();
			bufferwrite.close();
			bufferread.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			
		}
	}
}
