package com.kaola.hbase;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.filter.RowFilter;
import org.apache.hadoop.hbase.filter.SubstringComparator;
import org.apache.hadoop.hbase.util.Bytes;

public class HBaseCount {
	
	static FileWriter  filec=null;
    // 声明静态配置
    static Configuration conf = null;
    static {
   
        conf = HBaseConfiguration.create();
        System.setProperty("hadoop.home.dir","D:/spark/hadoop2.6");
        
       // conf.set("hbase.zookeeper.quorum", "trs-11:2181,trs-12:2181,trs-13:2181");
        
        //Current Active Master: hadoop22, Backup Master hadoop16
        conf.set("hbase.zookeeper.quorum", "hadoop06:2181,hadoop01:2181,hadoop22:2181,hadoop16:2181,hadoop02:2181");
        
    }

    /*
     * 遍历查询hbase表
     * 
     * @tableName 表名
     */
    public static void getResultScann(String tableName, Long minStamp,long maxStamp) throws IOException {
     	try {
			filec=new FileWriter("E:/data/201601_20161227.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
        Scan scan = new Scan();
        //scan.setStartRow(Bytes.toBytes(start_rowkey));
        //scan.setStopRow(Bytes.toBytes(stop_rowkey));
//        long minStamp = 1451577600000l;
//        long maxStamp = 1454256000000l;
        scan.setTimeRange(minStamp, maxStamp);
        scan.setBatch(1);
        
        ResultScanner rs = null;
        HTable table = new HTable(conf, Bytes.toBytes(tableName));
        int countNum = 0;
        int disCountNum = 0;
       // Set set = new HashSet();
        String rowKey="";
        String cell="";
        try {
            rs = table.getScanner(scan);
            for (Result r : rs) {
            	countNum ++ ;
            	System.out.println("countNum ======"+countNum);
            	
                for (KeyValue kv : r.list()) {
                	rowKey = Bytes.toString(kv.getRow());
                	cell = Bytes.toString(kv.getValue());
                	
                	//set.add(rowKey.split("-")[0]);
                	
                	String[] cells = cell.split(",");
                	String val=cells[0]+","+cells[4]+","+cells[7]+"\n";
                	filec.write(val);
//                    System.out.println("row:" + Bytes.toString(kv.getRow()));
//                    System.out.println("family:" + Bytes.toString(kv.getFamily()));
//                    System.out.println("qualifier:"  + Bytes.toString(kv.getQualifier()));
//                    System.out.println("value:" + Bytes.toString(kv.getValue()));
//                    System.out.println("timestamp:" + kv.getTimestamp());
//                    System.out.println("-------------------------------------------");
                }
            }
        } finally {
            rs.close();
            System.out.println("============end=================countNum:"+countNum+"===disCountNum:");
        }
    }

    
    
    
    public static void main(String[] args) throws Exception {
    	SimpleDateFormat sdf_end = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	long minStamp = sdf_end.parse("2016-01-01 00:00:00").getTime();
    	long maxStamp = sdf_end.parse("2016-01-31 23:59:59").getTime();
    	
    	System.out.println(minStamp +","+ maxStamp);
    	
    	String tableName ="lklpos_atm_risk_control";
    	getResultScann(tableName,minStamp,maxStamp);
    }
    
    
    
}
