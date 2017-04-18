package com.kaola.edata;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class UniqueData2 {

	public static void main(String[] args) {
		   
	        try {		 
	        	File file = new File(args[0]);
	        	File[]filelist=file.listFiles();
	        	for(int ii=0;ii<filelist.length;ii++){
				    //File file = new File("");
				    BufferedReader reader = null;
				    //FileWriter fileWritter = new FileWriter(args[0]+"r",false);
			        //BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
		            System.out.println("以行为单位读取文件内容，一次读一整行：");
		            reader = new BufferedReader(new FileReader(filelist[ii]));
		            String tempString="";
		            String []split1=args[2].split("#");
		            
		            while ((tempString = reader.readLine()) != null) {
		            	String split[]=tempString.split("\001");
		            	System.out.println(split.length);
		            	if(split.length<2)
		            		continue;
		            	try{
		            		String key="";
		            		//System.out.println(args[2]);      		
		            		//System.out.println(split1+args[2]);
		            		//System.out.println(split1.length);
		            		for(String sp : split1){
		            			//System.out.println(sp);
		            			key=key+split[	Integer.parseInt(sp)];
		            		}
		            		System.out.println(key);
			            	if(JredisUtil.hgetS(args[1], key)==null){	                
			            		//bufferWritter.write(tempString+"\n\r");
			            		JredisUtil.hsetS(args[1], key,"ok");
			            	}else{
			            		System.out.println("重复key:"+key);
			            		//System.out.println("重复"+tempString);
			            	}
		            	}catch(Exception o){
		            		o.printStackTrace();
		            	}
		            }
	        	}
	        }catch(Exception o){
	        	o.printStackTrace();
	        }
	}

}
