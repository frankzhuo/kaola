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

public class ZhengJianHuiETL {
	public static void main(String[] args) {
         try {
			InputStreamReader read = new  InputStreamReader(new FileInputStream(new File("D:/work/crawler/chufa/zhengjianhui.txt")));
			BufferedReader bufferread = new BufferedReader(read);
			BufferedWriter bufferwrite = new BufferedWriter(new FileWriter(new File("D:/work/crawler/chufa/zhengjianhui_etl.txt")));
			String line = "";
			int i = 0;
			int num=0;
			while ((line = bufferread.readLine()) != null) {
				++num;
				if (line.split("\001").length ==8) {
					System.out.println("总行数：" + num + " 满足条件行数：" + (++i) + " colNum :" + line.split("\001").length);
					//System.out.println(line);
					String[] strs = line.split("\001");
					String[] results = new String[] { " ", " ", " ","-" };

					String title= strs[6];
					String publishDate= strs[3].replaceAll("年","-").replaceAll("月","-").replaceAll("日","");
					String content= strs[7];
					String name= "-";

					String[] lines = content.split("\002");
					String firstLine = lines[1];
					
					String reg = "当事人[:：](.+?)[，,]";
					Pattern pat = Pattern.compile(reg);
					Matcher mat = pat.matcher(firstLine);
					
					if (mat.find()) {
						name = mat.group(1);
						
					}else{
						name = firstLine.split("，")[0].replace("：", "");
					}
					
					System.out.println(name);
					
					results[0] =title;
					results[1] = publishDate;
					results[2] = content.trim();
					results[3] = name;
					
					line = StringUtil.ArrToStr(results, "\001");
					bufferwrite.write(line);
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
