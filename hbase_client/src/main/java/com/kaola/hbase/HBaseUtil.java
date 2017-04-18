package com.kaola.hbase;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.HTablePool;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.ColumnCountGetFilter;
import org.apache.hadoop.hbase.filter.ColumnPaginationFilter;
import org.apache.hadoop.hbase.filter.ColumnPrefixFilter;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.filter.PageFilter;
import org.apache.hadoop.hbase.filter.RowFilter;
import org.apache.hadoop.hbase.filter.SingleColumnValueExcludeFilter;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.filter.SubstringComparator;
import org.apache.hadoop.hbase.filter.ValueFilter;
import org.apache.hadoop.hbase.regionserver.BloomType;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.hbase.io.compress.Compression;

public class HBaseUtil {
    // 声明静态配置
    static Configuration conf = null;
    static {
        conf = HBaseConfiguration.create();
        System.setProperty("hadoop.home.dir","D:/spark/hadoop2.6");
        
        conf.set("hbase.zookeeper.quorum", "trs-11:2181,trs-12:2181,trs-13:2181");
        
        //Current Active Master: hadoop22, Backup Master hadoop16
        //conf.set("hbase.zookeeper.quorum", "hadoop06:2181,hadoop01:2181,hadoop22:2181,hadoop16:2181,hadoop02:2181");
        
    }

    /*
     * 创建表
     * 
     * @tableName 表名
     * 
     * @family 列族列表
     */
    public static void creatTable(String tableName, String[] family)
            throws Exception {
        HBaseAdmin admin = new HBaseAdmin(conf);
        HTableDescriptor desc = new HTableDescriptor(tableName);
        for (int i = 0; i < family.length; i++) {
            desc.addFamily(new HColumnDescriptor(family[i]));
        }
        if (admin.tableExists(tableName)) {
            System.out.println("table Exists!");
            System.exit(0);
        } else {
            admin.createTable(desc);
            System.out.println("create table Success!");
        }
    }

    /*
     * 为表添加数据（适合添加以一个列镞）
     * 
     * @rowKey rowKey
     * 
     * @tableName 表名
     * 
     * @column1 第一个列族列表
     * 
     * @value1 第一个列的值的列表
     */
    public static void addOneData(String rowKey, String tableName,String familyName,String[] column1, String[] value1)
            throws IOException {
        Put put = new Put(Bytes.toBytes(rowKey));// 设置rowkey
        HTable table = new HTable(conf, Bytes.toBytes(tableName));// HTabel负责跟记录相关的操作如增删改查等//
                                                                    // 获取表
        for (int j = 0; j < column1.length; j++) {
            put.add(Bytes.toBytes(familyName),Bytes.toBytes(column1[j]), Bytes.toBytes(value1[j]));
        }
        table.put(put);
        System.out.println("add data Success!");
    }
    
    /*
     * 为表添加数据（适合知道有多少列族的固定表）
     * 
     * @rowKey rowKey
     * 
     * @tableName 表名
     * 
     * @column1 第一个列族列表
     * 
     * @value1 第一个列的值的列表
     * 
     * @column2 第二个列族列表
     * 
     * @value2 第二个列的值的列表
     */
    public static void addData(String rowKey, String tableName,
            String[] column1, String[] value1, String[] column2, String[] value2)
            throws IOException {
        Put put = new Put(Bytes.toBytes(rowKey));// 设置rowkey
        HTable table = new HTable(conf, Bytes.toBytes(tableName));// HTabel负责跟记录相关的操作如增删改查等//
                                                                    // 获取表
        HColumnDescriptor[] columnFamilies = table.getTableDescriptor() // 获取所有的列族
                .getColumnFamilies();

        for (int i = 0; i < columnFamilies.length; i++) {
            String familyName = columnFamilies[i].getNameAsString(); // 获取列族名
            if (familyName.equals("article")) { // article列族put数据
                for (int j = 0; j < column1.length; j++) {
                    put.add(Bytes.toBytes(familyName),Bytes.toBytes(column1[j]), Bytes.toBytes(value1[j]));
                }
            }
            if (familyName.equals("author")) { // author列族put数据
                for (int j = 0; j < column2.length; j++) {
                    put.add(Bytes.toBytes(familyName), Bytes.toBytes(column2[j]), Bytes.toBytes(value2[j]));
                }
            }
        }
        table.put(put);
        System.out.println("add data Success!");
    }

    /*
     * 根据rwokey查询
     * 
     * @rowKey rowKey
     * 
     * @tableName 表名
     */
    public static Result getResult(String tableName, String rowKey)
            throws IOException {
        Get get = new Get(Bytes.toBytes(rowKey));
        HTable table = new HTable(conf, Bytes.toBytes(tableName));// 获取表
        Result result = table.get(get);
        for (KeyValue kv : result.list()) {
            System.out.println("family:" + Bytes.toString(kv.getFamily()));
            System.out.println("qualifier:" + Bytes.toString(kv.getQualifier()));
            System.out.println("value:" + Bytes.toString(kv.getValue()));
            System.out.println("Timestamp:" + kv.getTimestamp());
            System.out.println("-------------------------------------------");
        }
        return result;
    }

    /*
     * 遍历查询hbase表
     * 
     * @tableName 表名
     */
    public static void getResultScann(String tableName) throws IOException {
        Scan scan = new Scan();
        ResultScanner rs = null;
        HTable table = new HTable(conf, Bytes.toBytes(tableName));
        try {
            rs = table.getScanner(scan);
            for (Result r : rs) {
                for (KeyValue kv : r.list()) {
                    System.out.println("row:" + Bytes.toString(kv.getRow()));
                    System.out.println("family:" + Bytes.toString(kv.getFamily()));
                    System.out.println("qualifier:" + Bytes.toString(kv.getQualifier()));
                    System.out.println("value:" + Bytes.toString(kv.getValue()));
                    System.out.println("timestamp:" + kv.getTimestamp());
                    System.out.println("-------------------------------------------");
                }
            }
        } finally {
            rs.close();
        }
    }

    /*
     * 遍历查询hbase表
     * 
     * @tableName 表名
     */
    public static void getResultScann(String tableName, String start_rowkey,String stop_rowkey) throws IOException {
        Scan scan = new Scan();
        scan.setStartRow(Bytes.toBytes(start_rowkey));
        scan.setStopRow(Bytes.toBytes(stop_rowkey));
        ResultScanner rs = null;
        HTable table = new HTable(conf, Bytes.toBytes(tableName));
        try {
            rs = table.getScanner(scan);
            for (Result r : rs) {
                for (KeyValue kv : r.list()) {
                    System.out.println("row:" + Bytes.toString(kv.getRow()));
                    System.out.println("family:" + Bytes.toString(kv.getFamily()));
                    System.out.println("qualifier:"  + Bytes.toString(kv.getQualifier()));
                    System.out.println("value:" + Bytes.toString(kv.getValue()));
                    System.out.println("timestamp:" + kv.getTimestamp());
                    System.out.println("-------------------------------------------");
                }
            }
        } finally {
            rs.close();
        }
    }

    /*
     * 查询表中的某一列
     * 
     * @tableName 表名
     * 
     * @rowKey rowKey
     */
    public static void getResultByColumn(String tableName, String rowKey,String familyName, String columnName) throws IOException {
        HTable table = new HTable(conf, Bytes.toBytes(tableName));
        Get get = new Get(Bytes.toBytes(rowKey));
        get.addColumn(Bytes.toBytes(familyName), Bytes.toBytes(columnName)); // 获取指定列族和列修饰符对应的列
        Result result = table.get(get);
        for (KeyValue kv : result.list()) {
            System.out.println("family:" + Bytes.toString(kv.getFamily()));
            System.out.println("qualifier:" + Bytes.toString(kv.getQualifier()));
            System.out.println("value:" + Bytes.toString(kv.getValue()));
            System.out.println("Timestamp:" + kv.getTimestamp());
            System.out.println("-------------------------------------------");
        }
    }

    /*
     * 更新表中的某一列
     * 
     * @tableName 表名
     * 
     * @rowKey rowKey
     * 
     * @familyName 列族名
     * 
     * @columnName 列名
     * 
     * @value 更新后的值
     */
    public static void updateTable(String tableName, String rowKey,String familyName, String columnName, String value)
            throws IOException {
        HTable table = new HTable(conf, Bytes.toBytes(tableName));
        Put put = new Put(Bytes.toBytes(rowKey));
        put.add(Bytes.toBytes(familyName), Bytes.toBytes(columnName),Bytes.toBytes(value));
        table.put(put);
        System.out.println("update table Success!");
    }

    /*
     * 查询某列数据的多个版本
     * 
     * @tableName 表名
     * 
     * @rowKey rowKey
     * 
     * @familyName 列族名
     * 
     * @columnName 列名
     */
    public static void getResultByVersion(String tableName, String rowKey,String familyName, String columnName) throws IOException {
        HTable table = new HTable(conf, Bytes.toBytes(tableName));
        Get get = new Get(Bytes.toBytes(rowKey));
        get.addColumn(Bytes.toBytes(familyName), Bytes.toBytes(columnName));
        get.setMaxVersions(5);
        Result result = table.get(get);
        for (KeyValue kv : result.list()) {
            System.out.println("family:" + Bytes.toString(kv.getFamily()));
            System.out.println("qualifier:" + Bytes.toString(kv.getQualifier()));
            System.out.println("value:" + Bytes.toString(kv.getValue()));
            System.out.println("Timestamp:" + kv.getTimestamp());
            System.out.println("-------------------------------------------");
        }
        /*
         * List<?> results = table.get(get).list(); Iterator<?> it =
         * results.iterator(); while (it.hasNext()) {
         * System.out.println(it.next().toString()); }
         */
    }

    /*
     * 删除指定的列
     * 
     * @tableName 表名
     * 
     * @rowKey rowKey
     * 
     * @familyName 列族名
     * 
     * @columnName 列名
     */
    public static void deleteColumn(String tableName, String rowKey,
            String falilyName, String columnName) throws IOException {
        HTable table = new HTable(conf, Bytes.toBytes(tableName));
        Delete deleteColumn = new Delete(Bytes.toBytes(rowKey));
        deleteColumn.deleteColumns(Bytes.toBytes(falilyName),Bytes.toBytes(columnName));
        table.delete(deleteColumn);
        System.out.println(falilyName + ":" + columnName + " is deleted!");
    }

    /*
     * 删除指定的行
     * 
     * @tableName 表名
     * 
     * @rowKey rowKey
     */
    public static void deleteAllColumn(String tableName, String rowKey)
            throws IOException {
    	
    	// HTable table = new HTable(conf, Bytes.toBytes(tableName));
    	//池 方式
    	HTablePool pool = new HTablePool(conf, 100);
    	HTableInterface table =pool.getTable(Bytes.toBytes(tableName));
    	
    	HTable table2 = new HTable(conf, Bytes.toBytes(tableName));
    	
        System.out.println("Bytes.toBytes(rowKey):"+Bytes.toBytes(rowKey));
        Delete deleteAll = new Delete(Bytes.toBytes(rowKey));
        table.delete(deleteAll);
        System.out.println("all columns are deleted!");
    }

    /*
     * 删除表
     * 
     * @tableName 表名
     */
    public static void deleteTable(String tableName) throws IOException {
        HBaseAdmin admin = new HBaseAdmin(conf);
        admin.disableTable(tableName);
        admin.deleteTable(tableName);
        System.out.println(tableName + "is deleted!");
    }
    
    
    
    public static void getColumnCountfilter(String tableName,String rowKey) throws Exception{
    	HTable table = new HTable(conf, Bytes.toBytes(tableName));
    	Get get = new Get(rowKey.getBytes());
    	
    	ColumnCountGetFilter filter = new ColumnCountGetFilter(3);
    	get.setFilter(filter);
    	
    	Result result = table.get(get);
    	System.out.println("getColumnCountfilter:" +result);
    	
    }
    
    public static void getColumnPagetfilter(String tableName) throws Exception{
    	HTable table = new HTable(conf, Bytes.toBytes(tableName));
        Scan scan = new Scan();
    	//取几列数据
    	ColumnPaginationFilter filter = new ColumnPaginationFilter(3,0);
    	scan.setFilter(filter);
    	
    	ResultScanner result = table.getScanner(scan);
    	
    	for (Result r : result) {
			for (KeyValue kv : r.raw()) {
				System.out.println(new String(kv.getRow()) + ":getColumnPagetfilter:" + Bytes.toString(kv.getValue()));
			}
		}
    	
    }
    
    public static void getColumnPrefixfilter(String tableName) throws Exception{
    	HTable table = new HTable(conf, Bytes.toBytes(tableName));
        Scan scan = new Scan();
    	//取几列数据
        ColumnPrefixFilter filter = new ColumnPrefixFilter(Bytes.toBytes("lee"));
    	scan.setFilter(filter);
    	
    	ResultScanner result = table.getScanner(scan);
    	
    	for (Result r : result) {
 			for (KeyValue kv : r.raw()) {
				System.out.println(new String(kv.getRow()) + ":getColumnPrefixfilter:" + r	);
 			}
		}
    	result.close();
    }

    public static void getPagefilter(String tableName) throws Exception{
    	HTable table = new HTable(conf, Bytes.toBytes(tableName));
        Scan scan = new Scan();
    	//取几列数据
        PageFilter filter = new PageFilter(5);
    	scan.setFilter(filter);
    	
    	ResultScanner result = table.getScanner(scan);
    	
    	for (Result r : result) {
 			for (KeyValue kv : r.raw()) {
				System.out.println(new String(kv.getRow()) + ":getPagefilter:" + r	);
 			}
		}
    	result.close();
    }
    
    public static void getSingleColumnValfilter(String tableName) throws Exception{
    	HTable table = new HTable(conf, Bytes.toBytes(tableName));
        Scan scan = new Scan();
    	//排出过滤得列
        SingleColumnValueFilter filter = new SingleColumnValueFilter(Bytes.toBytes("article"),Bytes.toBytes("content"),CompareOp.EQUAL,new SubstringComparator("need"));
    	scan.setFilter(filter);
    	
    	
        SingleColumnValueFilter filter2 = new SingleColumnValueFilter(Bytes.toBytes("article"),Bytes.toBytes("content"),CompareOp.EQUAL,new SubstringComparator("nicholas"));
    	scan.setFilter(filter2);
    	
    	ResultScanner result = table.getScanner(scan);
    	
    	for (Result r : result) {
			for (KeyValue kv : r.raw()) {
				System.out.println(new String(kv.getRow()) + ":getSingleColumnValfilter:" +Bytes.toString(kv.getValue()));
			}
		}
    	result.close();
    }
    
    
    public static void getSingleColumnExcfilter(String tableName) throws Exception{
    	HTable table = new HTable(conf, Bytes.toBytes(tableName));
        Scan scan = new Scan();
    	//排出过滤得列
        SingleColumnValueExcludeFilter filter = new SingleColumnValueExcludeFilter(Bytes.toBytes("article"),Bytes.toBytes("content"),CompareOp.EQUAL,new SubstringComparator("database"));
    	scan.setFilter(filter);
    	
    	ResultScanner result = table.getScanner(scan);
    	
    	for (Result r : result) {
			for (KeyValue kv : r.raw()) {
				System.out.println(new String(kv.getRow()) + ":getSingleColumnExcfilter:" +Bytes.toString(kv.getValue()));
			}
		}
    	result.close();
    }
    
    public static void getValuefilter(String tableName) throws Exception{
    	HTable table = new HTable(conf, Bytes.toBytes(tableName));
        Scan scan = new Scan();
    	//排出过滤得列
       ValueFilter filter = new ValueFilter(CompareOp.EQUAL,new SubstringComparator("your Big Data. 3333"));
    	scan.setFilter(filter);
    	
    	ResultScanner result = table.getScanner(scan);
    	
    	for (Result r : result) {
			for (KeyValue kv : r.raw()) {
				System.out.println(new String(kv.getRow()) + ":getValuefilter:" +Bytes.toString(kv.getValue()));
			}
		}
    	result.close();
    }
    
    public static void getRowfilter(String tableName,String strRow) throws Exception{
    	HTable table = new HTable(conf, Bytes.toBytes(tableName));
        Scan scan = new Scan();
    	//排出过滤得列

        RowFilter filter = new RowFilter(CompareOp.EQUAL,new SubstringComparator(strRow));
    	scan.setFilter(filter);
    	
    	ResultScanner result = table.getScanner(scan);
    	
    	for (Result r : result) {
			for (KeyValue kv : r.raw()) {
				System.out.println(new String(kv.getRow()) + ":getRowfilter:" +Bytes.toString(kv.getValue()));
			}
		}
    	result.close();
    }
    
    
    
    public static boolean createTable(String tablename, Configuration conf, String[] columns, String startKey, String endKey, int numRegions, int timeToLive)
    {
      HBaseAdmin admin;
      try
      {
        admin = new HBaseAdmin(conf);

        if (admin.tableExists(tablename)) {
          admin.close();
          return false;
        }
        HTableDescriptor tableDesc = new HTableDescriptor(tablename);
        String[] arr$ = columns;
        int len$ = arr$.length; 
        
        for (int i$ = 0; i$ < len$; ++i$) { 
          String column = arr$[i$];
          HColumnDescriptor colFamDescriptor = new HColumnDescriptor(column);

          colFamDescriptor.setCompressionType(Compression.Algorithm.SNAPPY);
          colFamDescriptor.setCompactionCompressionType(Compression.Algorithm.SNAPPY);

          colFamDescriptor.setInMemory(true);
          colFamDescriptor.setBloomFilterType(BloomType.ROWCOL);

          if (timeToLive > 0) {
            colFamDescriptor.setTimeToLive(timeToLive);
          }

          tableDesc.addFamily(colFamDescriptor);
        }
       // admin.createTable(tableDesc, Bytes.toBytes(startKey), Bytes.toBytes(endKey), numRegions);
        admin.createTable(tableDesc);
        admin.close();
        return true;
      }
      catch (Exception e) {
        e.printStackTrace(); }
      return false;
    }
    
    public static void main2(String[] args) throws Exception {
    	String tableName ="lklpos_atm_risk_control_new";
    	deleteTable(tableName);
    	createTable(tableName, conf, "cf".split(":"), "", "", 60, 0);
    	//getRowfilter("lklpos_atm_risk_control","_detail_1456381274");
    }
    
    
    
    public static void main(String[] args) throws Exception {

        // 创建表
        String tableName = "blog2";
        String[] family = { "article", "author" };
        creatTable(tableName, family);

        // 为表添加数据

        String[] column1 = { "title", "content", "tag" };
        String[] value11 = {
                "Head First HBase 111",
                "HBase is the Hadoop database. Use it when you need random, realtime read/write access to your Big Data. 1111",
                "Hadoop,HBase,NoSQL 111" };
        String[] value12 = {
                "Head First HBase 2222",
                "HBase is the Hadoop database. Use it when you need random, realtime read/write access to your Big Data. 2222",
                "Hadoop,HBase,NoSQL 2222" };
        String[] value13 = {
                "Head First HBase 3333",
                "HBase is the Hadoop database. Use it when you need random, realtime read/write access to your Big Data. 3333",
                "Hadoop,HBase,NoSQL 3333" };
        String[] column2 = { "name", "nickname" };
        String[] value21 = { "nicholas 111", "lee 111" };
        String[] value22 = { "nicholas 222", "lee 222" };
        String[] value23 = { "nicholas 333", "lee 333" };
        addData("rowkey1", "blog2", column1, value11, column2, value21);
        addData("rowkey2", "blog2", column1, value12, column2, value22);
        addData("rowkey3", "blog2", column1, value13, column2, value23);

        // 遍历查询
        getResultScann("blog2", "rowkey4", "rowkey5");
        // 根据row key范围遍历查询
        getResultScann("blog2", "rowkey4", "rowkey5");

        // 查询
        getResult("blog2", "rowkey1");

        // 查询某一列的值
        getResultByColumn("blog2", "rowkey1", "author", "name");

        // 更新列
       // updateTable("blog2", "rowkey1", "author", "name", "bin");

        // 查询某一列的值
        getResultByColumn("blog2", "rowkey1", "author", "name");

        // 查询某列的多版本
        getResultByVersion("blog2", "rowkey1", "author", "name");

        // 删除一列
         //deleteColumn("blog2", "rowkey1", "author", "nickname");

        // 删除所有列
        //deleteAllColumn("blog2", "rowkey1");

        // 删除表
        //deleteTable("blog2");
        
        getColumnCountfilter(tableName, "rowkey1");
        
        getColumnPagetfilter(tableName);
        
        getColumnPrefixfilter(tableName);
        
        getPagefilter(tableName);
        
        getValuefilter(tableName);
        
        getSingleColumnValfilter(tableName);
        
        getRowfilter(tableName,"2");
    }


}
