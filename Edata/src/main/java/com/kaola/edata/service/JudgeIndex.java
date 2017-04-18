package com.kaola.edata.service;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeBuilder;


public class JudgeIndex {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		try{
		
		   	Settings settings = ImmutableSettings.settingsBuilder().put("cluster.name", "lakala").build();
			Client client = new TransportClient(settings)
	        .addTransportAddress(new InetSocketTransportAddress("trs-11", 9300))
	        .addTransportAddress(new InetSocketTransportAddress("10.5.28.13", 9300));
			File file = new File("d:\\washed_judicial_7.txt.COMPLETED");
	        BufferedReader reader = null;
	        try {
	            System.out.println("以行为单位读取文件内容，一次读一整行：");
	            reader = new BufferedReader(new FileReader(file));
	            String tempString = null;
	            int line = 1;
	            // 一次读入一行，直到读入null为文件结束
	            while ((tempString = reader.readLine()) != null) {
	                // 显示行号
	                System.out.println("line " + line );
	                Map<String, Object> json = new HashMap<String, Object>();
	                String strs[]=tempString.split("\\001");
	     
	                try{
		 			    json.put("court",strs[0]); 	
		 			    System.out.println(strs[0]);
		 			    json.put("jtype",strs[1]); 	
		 			    json.put("title",strs[2]);
		 			    json.put("jdate",strs[3]);
		 			    json.put("jnum",strs[4]);
		 			    json.put("url",strs[5]);
		 			    json.put("content",strs[6]);	
		 			    client.prepareIndex("crawler_new", "judgement").setSource(json).execute().actionGet();   
		 				//System.out.println(strs[6]);
	                }catch(Exception o){
	                	o.printStackTrace();
	                }
	 				 //IndexResponse response =(IndexResponse)client.prepareIndex("twitter", "tweet").setSource(json).execute().actionGet();
	 				   
	 				//
	 			     
	                line++;
	            }
	            reader.close();
	            client.close();
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
