package com.kao.etl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;

import com.kao.util.StringUtil;

public class lieTohang {
	public static void main(String[] args) {
         try {
			InputStreamReader read = new  InputStreamReader(new FileInputStream(new File("C:/Users/kaola/Desktop/telphone.txt")),"utf-8");
			BufferedReader bufferread = new BufferedReader(read);
			BufferedWriter bufferwrite = new BufferedWriter(new FileWriter(new File("C:/Users/kaola/Desktop/telphone_row.txt")));
			String line = "";
			int i = 0;
			int num=0;
			//imei	最近汇报时间	最近汇报城市	标签属性	家庭地(geohash)	工作地（geohash）	app安装列表	app活跃列表
			while ((line = bufferread.readLine()) != null) {
				++num;
				if (line.split("\t").length ==8) {
					System.out.println("总行数：" + num + " 满足条件行数：" + (++i) + " colNum :" + line.split("\t").length);
					System.out.println(line);
					String[] strs = line.split("\t");
					 
					String[] str3 = strs[3].split(",");
					 
					String biaoqian1 = strs[0] +"\t"+strs[1] +"\t"+strs[2] +"\t";
					String biaoqian2 = "\t"+strs[4] +"\t"+strs[5] +"\t"+strs[6] +"\t"+strs[7] +"\n";
					
					for (int j = 0; j < str3.length; j++) {
						String biaoqian = str3[j];
						bufferwrite.write(biaoqian1 + biaoqian + biaoqian2);
					}
				}else if (line.split("\t").length ==7){
					System.out.println("总行数：" + num + " 满足条件行数：" + (++i) + " colNum :" + line.split("\t").length);
					System.out.println(line);
					String[] strs = line.split("\t");
					 
					String[] str3 = strs[3].split(",");
					 
					String biaoqian1 = strs[0] +"\t"+strs[1] +"\t"+strs[2] +"\t";
					String biaoqian2 = "\t"+strs[4] +"\t"+strs[5] +"\t"+strs[6]  +"\n";
					
					for (int j = 0; j < str3.length; j++) {
						String biaoqian = str3[j];
						bufferwrite.write(biaoqian1 + biaoqian + biaoqian2);
					}
				}
				else if (line.split("\t").length ==6){
					System.out.println("总行数：" + num + " 满足条件行数：" + (++i) + " colNum :" + line.split("\t").length);
					System.out.println(line);
					String[] strs = line.split("\t");
					 
					String[] str3 = strs[3].split(",");
					 
					String biaoqian1 = strs[0] +"\t"+strs[1] +"\t"+strs[2] +"\t";
					String biaoqian2 = "\t"+strs[4] +"\t"+strs[5] +"\n";
					
					for (int j = 0; j < str3.length; j++) {
						String biaoqian = str3[j];
						bufferwrite.write(biaoqian1 + biaoqian + biaoqian2);
					}
				}
				else if (line.split("\t").length ==5){
					System.out.println("总行数：" + num + " 满足条件行数：" + (++i) + " colNum :" + line.split("\t").length);
					System.out.println(line);
					String[] strs = line.split("\t");
					 
					String[] str3 = strs[3].split(",");
					 
					String biaoqian1 = strs[0] +"\t"+strs[1] +"\t"+strs[2] +"\t";
					String biaoqian2 = "\t"+strs[4] +"\n";
					
					for (int j = 0; j < str3.length; j++) {
						String biaoqian = str3[j];
						bufferwrite.write(biaoqian1 + biaoqian + biaoqian2);
					}
				}
				else{
					System.out.println("总行数：" + num + " 满足条件行数：" + (++i) + " colNum :" + line.split("\t").length);
					System.out.println(line);
					String[] strs = line.split("\t");
					 
					String[] str3 = strs[3].split(",");
					 
					String biaoqian1 = strs[0] +"\t"+strs[1] +"\t"+strs[2] +"\t";
					String biaoqian2 = "\n";
					
					for (int j = 0; j < str3.length; j++) {
						String biaoqian = str3[j];
						bufferwrite.write(biaoqian1 + biaoqian + biaoqian2);
					}
					
					
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
