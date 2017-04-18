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

public class ZhidianExtract2 {
	private static BufferedWriter  filew=null;
	public  static String  clean(String str,boolean flag){
		String result="";
		//System.out.println("clean"+str);
		int end=str.indexOf("\006");
		if(end<0)
			end=str.indexOf("\005");
		if(end<0)
			end=str.indexOf("\004");
		if(end<0)
			end=str.indexOf("\003");
		if(end<0)
			end=str.indexOf("\002");
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
	        //Pattern pattern = Pattern.compile("[中标候选人准[予许]*.*撤回.*[起诉|申请]");
	        //Pattern pattern = Pattern.compile(".*[中标|中 标|成交].*[单位|名称|供应商名单|供应商|候选人][:|：]");
	        //Pattern pattern = Pattern.compile(".*[中标供应商名称|中标单位|中 标 人|成交供应商名称|预中标单位为|中标单位名称|中标供应商|中标人|成交供应商|中标人名称|中标候选人]{1}[:|：]{1}");

	        
	        //Pattern patternnum = Pattern.compile(".*[中标|成交].*[价格|金额|价][:|：]");
		    //Pattern pattern = Pattern.compile(".*中标单位:|中标供应商名称:|中 标 人:|成交供应商名称:|预中标单位为:|中标单位名称:|中标供应商:|中标人:|成交供应商:|中标人名称:|中标候选人:");        
	        //Pattern patternnum = Pattern.compile(".*成交金额:|中标金额:|预中标价格:|中标价:|成交报价:");
	  	    Pattern pattern = Pattern.compile(".*中标单位[:|：]|.*成交人[:|：]|中标 人[:|：]|.*中标供应商名称[:|：]|.*中 标 人[:|：]|.*成交供应商名称[:|：]|.*预中标单位为[:|：]|.*中标单位名称[:|：]|.*中标供应商[:|：]|.*中标人[:|：]|.*中标的服务单位[:|：]|.*中 标 人[:|：]|.*成交供应商[:|：]|.*中标人名称[:|：]|.*中标候选人[:|：]");        

	        int jj=0;
	        int kk=0;
	        while((tmp = bufferedReader.readLine()) != null){
	        	try{
		            i++;
		            
		            //if(i>1)
		            //	break;
		            //System.out.println("##"+i);
		    		//System.out.println(tmp);
		    		String []ar=tmp.split("\",\"");
		    		//System.out.println(ar.length);
		    		//System.out.println("**"+i);
		  	        
		  	        //System.out.println(kk);
		    		if(ar.length<39)
		    			continue;
		    		//System.out.println(i);
		    		String str =ar[38];
		    		
		    		//if(str.length()>300)
		    		//	continue;
		    		//Syststem.out.println(str);
		    		String []ar1=str.split("\002");
		    		//if(ar1.length<6)
		    		//	continue;
		    		//System.out.println(ar1[0]);
		    		//System.out.println(ar[4]);
		    		
		    		if(!(ar[4].equals("中标公告")||ar[4].equals("成交公告"))){
		    			kk++;
		    			//System.out.println("**"+ar[4]);
		    			continue;
		    			//System.out.println(str);
		    		}
		    		
		    		String name="";
		    		String amount="";
		  	            //Pattern patternnum = Pattern.compile(".*成交金额[:|：]|.*报价[:|：]|.*中标金额[:|：]|.*预中标价格[:|：]|.*中标价[:|：]|.*成交报价[:|：]");
	
		    		for(String line:ar1){
		    			//System.out.println(line);
		    			if(line.length()>2500)
		    				continue;
		    		  	Matcher matcher = pattern.matcher(line); 
		    		  	//Matcher matcher1 = patternnum.matcher(line); 
		    		  	//int num=matcher.groupCount();
		    		  	//int num1=matcher.groupCount();
		    		  	
		    		  	int j=0;
		    		  	StringBuffer sb = new StringBuffer(); 
		    		  	if(matcher.find()){
		    		  		//matcher.appendReplacement(sb, " "); 
		    		  		//System.out.println("第"+i+"次匹配后sb的内容是："+sb); 
		    		  		matcher.appendTail(sb); 
		    		  		//System.out.println("调用m.appendTail(sb)后sb的最终内容是:"+ sb.toString()); 	    	     	        	
		    		  		String tstr=clean(sb.toString(),true);
		    		  		if(tstr.length()<30){
			    	        	
			    	        	if(name.indexOf(tstr)>-1)
			    	        		continue;
			    		  		name =name+tstr+ " ";
			    		  		jj++;
			    		  		filew.write(tmp+",\""+tstr+"\"\n");	
				    			//filew.flush();
			    	    		System.out.println("**"+i);
			    	  	        
			    	  	        //System.out.println(kk);
			    	    		System.out.println(jj);
				    	        System.out.println("name:"+tstr);
				    	        //System.out.println(line);
			    	        }
			    	    }
		    		    
	//	    		  	sb = new StringBuffer(); 
	//	    		  	if(matcher1.find()){
	//	    		  		matcher1.appendReplacement(sb, " "); 
	//	    		  		//System.out.println("第"+i+"次匹配后sb的内容是："+sb); 
	//	    		  		matcher1.appendTail(sb); 
	//	    		  		//System.out.println("调用m.appendTail(sb)后sb的最终内容是:"+ sb.toString()); 	    	     	        		    		      		   
	//	    		  		if(clean(sb.toString(),false).length()<50){
	//		    		  		amount =amount+clean(sb.toString(),false) + "  ";	
	//			    	        //System.out.println("amount"+amount);
	//			    	        //System.out.println(line);
	//	    		  		}
	//		    	    }
		    		 }
	        	}	
	    		catch(Exception e){
	    			e.printStackTrace();
	    		}
	    	
//	    		if(!name.equals("") || !amount.equals("")){
//	    			jj++;
//	    			//System.out.println(tmp+",\""+name.replaceAll("\\\\\\\\002", "")+"\",\""+amount.replaceAll("\\\\\\\\002", "")+"\"\n\r");
//	    			//filew.write(tmp+",\""+name+"\",\""+amount+"\"\n");	
//	    			//filew.flush();   
//	    		}

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
