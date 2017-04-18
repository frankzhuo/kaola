package com.kaola.hbase;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Mutation;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.mapreduce.TableReducer;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;

public class HbaseMR {

	public static class MyMapper extends TableMapper<Text, IntWritable> {

		private static IntWritable one = new IntWritable(1);
		private static Text word = new Text();

		@Override
		protected void map(ImmutableBytesWritable key, Result value,
				Mapper<ImmutableBytesWritable, Result, Text, IntWritable>.Context context)
				throws IOException, InterruptedException {
			System.out.println(value.listCells());
			System.out.println(value.list());
			System.out.println(value.list().size() + "--------------------");
			String words = Bytes.toString(value.list().get(0).getValue());
			StringTokenizer st = new StringTokenizer(words);
			while (st.hasMoreTokens()) {
				String s = st.nextToken();
				word.set(s);
				context.write(word, one);
			}
		}

	}

	public static class MyReducer extends TableReducer<Text, IntWritable, ImmutableBytesWritable> {

		@Override
		protected void reduce(Text key, Iterable<IntWritable> values,
				Reducer<Text, IntWritable, ImmutableBytesWritable, Mutation>.Context context)
				throws IOException, InterruptedException {
			// 同一个key 其值 保存到 values 数组

			int num = 0;
			for (IntWritable val : values) {
				num += val.get();
			}

			System.out.println(Bytes.toBytes(key.toString()) + "========================+" + num + "===="
					+ Bytes.toBytes(num) + "----");

			Put put = new Put(Bytes.toBytes(key.toString()));
			put.add(Bytes.toBytes("result"), Bytes.toBytes("num"), Bytes.toBytes(num + "")); // ?????

			context.write(new ImmutableBytesWritable(Bytes.toBytes(key.toString())), put);
		}

	}

	public static void main(String[] args) throws Exception {
		;

		System.out.println(Bytes.toString("\\x00\\x00\\x00\\x01".getBytes()));

		HBaseUtil hbu = new HBaseUtil();
		// hbu.getResult("stat", "HBase");

		// // 创建表
		String tableName = "word";
		String[] family = { "content" };
		hbu.deleteTable(tableName);
		hbu.creatTable(tableName, family);

		String tableName2 = "stat";
		String[] family2 = { "result" };
		// hbu.creatTable(tableName2, family2);
		// 为表添加数据

		String[] column1 = { "col" };
		String[] value11 = { "1111" };
		String[] value12 = { "2222" };
		String[] value13 = { "3333" };
		HBaseUtil.addOneData("rowkey1", "word", "content", column1, value11);
		HBaseUtil.addOneData("rowkey2", "word", "content", column1, value12);
		HBaseUtil.addOneData("rowkey3", "word", "content", column1, value13);

		// System.exit(-1);

		// hadoop jar HbaseMR.jar HbaseMR
		//
		Configuration conf = HBaseConfiguration.create();
		Job job = new Job(conf, "jyk_test");

		job.setJarByClass(HbaseMR.class);

		Scan scan = new Scan();
		scan.setCaching(500);
		scan.setCacheBlocks(false);
		scan.addColumn(Bytes.toBytes("content"), Bytes.toBytes("col"));

		TableMapReduceUtil.initTableMapperJob("word", scan, MyMapper.class, Text.class, IntWritable.class, job);
		TableMapReduceUtil.initTableReducerJob("stat", MyReducer.class, job);

		job.setMapperClass(MyMapper.class);
		job.setReducerClass(MyReducer.class);
		job.setNumReduceTasks(1);

		System.exit(job.waitForCompletion(true) ? 0 : 1);

	}

}
