package com.kao.etl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;

import com.kao.util.StringUtil;

public class WM {
	public static void main(String[] args) {
         try {
			InputStreamReader read = new  InputStreamReader(new FileInputStream(new File("C:/Users/kaola/Desktop/psd_lb_baseinfo_md5_b_daochu/000000_0")),"utf-8");
			BufferedReader bufferread = new BufferedReader(read);
			BufferedWriter bufferwrite = new BufferedWriter(new FileWriter(new File("C:/Users/kaola/Desktop/wm/split0.txt")));
			String line = "";
			int i = 0;
			int num=0;
			while ((line = bufferread.readLine()) != null) {
				if(num%4000000 == 0 && num >0){
					i++;
					bufferwrite.flush();
					bufferwrite.close();
					bufferwrite = new BufferedWriter(new FileWriter(new File("C:/Users/kaola/Desktop/wm/split"+i+".txt")));
				}
				System.out.println(num+"----"+line);
				bufferwrite.write(line+"\n");
				num++;
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
