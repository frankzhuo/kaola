package com.kao.etl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;

public class xypj_change {
	public static void main(String[] args) {
         try {
			InputStreamReader read = new  InputStreamReader(new FileInputStream(new File("D:/work/mail6.attach.xypj.txt")));
			BufferedReader bufferread = new BufferedReader(read);
			BufferedWriter bufferwrite = new BufferedWriter(new FileWriter(new File("D:/work/xypj_done.txt")));
			String line = "";
			int i=0;
			while((line =bufferread.readLine()) !=null){
				
				if(line.split("\001").length ==33){
					System.out.println(++i+" colNum :"+line.split("\001").length);
					System.out.println(line);
				}
				line = line.replaceAll(" ", "").replaceAll("\\/","-");
				//bufferwrite.write(line.trim()+"\n");
			}
			
			//bufferwrite.flush();
		    bufferread.close();
		    bufferread.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			
		}
	}
}
