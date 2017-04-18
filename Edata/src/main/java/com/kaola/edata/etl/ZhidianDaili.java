package com.kaola.edata.etl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ZhidianDaili {
	private static BufferedWriter  filew=null;
	public  static String  clean(String str,boolean flag){
		//System.out.println("%%%%%%%%%%%%%%%%%%%"+str);
		String result="";
		//System.out.println("clean"+str);
		
		int end=str.indexOf("\002",3);
		if(end<0)
			end=str.indexOf("\005",3);
		if(end<0)
			end=str.indexOf("\004",3);
		if(end<0)
			end=str.indexOf("\003",3);
		if(end<0)
			end=str.indexOf("\006",3);
		//System.out.println(end);
		if(end>1)
		   result=str.substring(0,end);
		else 
		   result=str;
		//System.out.println("clean:"+result);
		result=result.trim();
		//System.out.println(result);
		end=result.indexOf(" ");		
		
		if(end>1)
			result=str.substring(0,end+1);
		end=result.indexOf(" ");
			
		if(end>1)
			result=str.substring(0,end+1);
		//System.out.println(result);
		result=result.trim();
		result=result.replaceAll("；", "");
		result=result.replaceAll("。", "");
		//System.out.println("1"+result);
		end=result.indexOf("公司");
		if(end>-1)
			  result=result.substring(0,end+2);
		//System.out.println("2"+result);
		if(flag){
			end=result.indexOf(",");
			if(end<0)
				end=result.indexOf("，");
			if(end>-1)
				   result=result.substring(0,end);
		}
		//System.out.println("3"+result);
		result=result.trim();
		result=result.replaceAll(" ", "");
		end=result.indexOf("：");
		if(end>-1)
			  result=result.substring(end+1);
		return  result;
		
	}
	public static void main(String[] args) {
		try{
			
			//File file=new File("d:\\zhidianresult.csv");
			//File file=new File("d:\\zhidian_all_result.csv");
			//File file=new File("/data/zhidian_all_result.csv");
			File file=new File(args[0]);
			//File file=new File("D:\\支点\\resultaa");
	    	BufferedReader reader=new BufferedReader(new FileReader(file));
	    	//filew=new BufferedWriter(new FileWriter("d:\\zhidian_extract_result_final.csv"));

	    	//filew=new BufferedWriter(new FileWriter("/data/zhidian_extract_result_final.csv"));
	    	filew=new BufferedWriter(new FileWriter(args[1]));

	    	String tmp="";
	    	System.out.println("ss");
	    	InputStreamReader read = new InputStreamReader(
	        new FileInputStream(file),"UTF-8");//考虑到编码格式
	        BufferedReader bufferedReader = new BufferedReader(read);
	        String lineTxt = null;
	        int i=0;
		  	//Pattern pattern = Pattern.compile(".*中标单位[:|：]|.*成交人[:|：]|中标 人[:|：]|.*中标供应商名称[:|：]|.*中 标 人[:|：]|.*成交供应商名称[:|：]|.*预中标单位为[:|：]|.*中标单位名称[:|：]|.*中标供应商[:|：]|.*中标人[:|：]|.*中标的服务单位[:|：]|.*中 标 人[:|：]|.*成交供应商[:|：]|.*中标人名称[:|：]|.*中标候选人[:|：]");        
	  	    Pattern pattern = Pattern.compile(".*采购代理机构全称[:|：](.*)|.*采购代理机构名称[:|：](.*)|.*采购代理机构[:|：](.*)");        

	        int jj=0;
	        int kk=0;
	        while((tmp = bufferedReader.readLine()) != null){
	        	try{
		            i++;		            
		    		String []ar=tmp.split("\",\"");
		    		if(ar.length<39)
		    			continue;
		    		//System.out.println(i);
		    		String str =ar[38];		    	
		    		String []ar1=str.split("\002");	    		
		    		String name="";
		    		String amount="";
		  	        //Pattern patternnum = Pattern.compile(".*成交金额[:|：]|.*报价[:|：]|.*中标金额[:|：]|.*预中标价格[:|：]|.*中标价[:|：]|.*成交报价[:|：]");
	
		    		for(String line:ar1){
		    			//if(line.indexOf("采购代理机构")>-1)
		    			//	System.out.println(line);
		    			//if(line.length()>2500)
		    			//	continue;
		    		  	Matcher matcher = pattern.matcher(line); 		    		  	
		    		  	int j=0;
		    		  	StringBuffer sb = new StringBuffer(); 
		    		  	if(matcher.find()){
		    		  		//System.out.println(line);
		    		  		String groupstr="";
		    		  		for(int n=1;n<4;n++){
		    		  			if(matcher.group(n)!=null)
		    		  			groupstr=matcher.group(n);
		    		  		}
		    		  		//System.out.println("&&&&&&&&&&&&&&&"+groupstr);
		    		  		//matcher.appendReplacement(sb, " "); 
		    		  		//System.out.println("第"+i+"次匹配后sb的内容是："+sb); 
		    		  		//matcher.appendTail(sb); 
		    		  		//System.out.println("调用m.appendTail(sb)后sb的最终内容是:"+ sb.toString()); 	    	     	        	
		    		  		String tstr=clean(groupstr,true);
		    		  		if(tstr.length()<30){		    	        	
			    	        	if(name.indexOf(tstr)>-1)
			    	        		continue;
			    		  		name =tstr;
			    		  		jj++;
			    		  		filew.write(ar[2]+"\001"+ar[3]+"\001"+ar[4]+"\001"+name+"\n");	
				    			filew.flush();	    	  	        
			    	  	        //System.out.println(i);
			    	    		//System.out.println(jj);
				    	        //System.out.println("*********name:"+name);
				    	        //System.out.println(line);
			    	        }
			    	    }
		    		    
		    		 }
	        	}	
	    		catch(Exception e){
	    			e.printStackTrace();
	    		}
	    	
	        }
	        filew.close();
	        bufferedReader.close();
	        System.out.println(i);
	        System.out.println(jj);
	        System.out.println(kk);
		}
        catch(Exception  o){
        	o.printStackTrace();
        }
	}

}
