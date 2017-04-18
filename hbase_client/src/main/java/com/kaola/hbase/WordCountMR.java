package com.kaola.hbase;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
/**
 *  hadoop jar ./HbaseMR.jar com.kaola.hbase.WordCountMR /user/root/word /user/root/out3
 * @author kaola
 *
 */
public class WordCountMR {
	
	public static int total_t = 0;
	/**
	 * Mapper<KEYIN, VALUEIN, KEYOUT, VALUEOUT>
	 *    MapContext<KEYIN,VALUEIN,KEYOUT,VALUEOUT> 
	 *    	 context.write((KEYOUT) key, (VALUEOUT) value);
	 * @author jianyikai
	 *
	 */
	public static class WCMap extends Mapper<Object, Text, Text, IntWritable> {
        
		//输出key的类型 与定义map时第三个参数的类型一致
		//其实keyin 的类型无 LongWritable 和Object，
		@Override
		protected void map(Object key, Text value,
				Context context)
				throws IOException, InterruptedException {

			Text outKey = new Text();
			
			String[] words = value.toString().split(" ");
			for(String word : words){
				outKey.set(word);
				System.out.println(word+"---map program--"+key.getClass());
				//super.map(outKey, new IntWritable(1), context);
				context.write(outKey, new IntWritable(1));
			}
		}
	}
	
	//经过shuffle后再映射到各个值
	public static class WCReduce extends Reducer<Text, IntWritable, Text, IntWritable>{
        //定义全局变量,但只能在同一个reduce过程中是全局
		public static int total = 0;
		@Override
		protected void reduce(Text key, Iterable<IntWritable> values,
				Context content) throws IOException, InterruptedException {
			
			int sum  =  0;
			for (IntWritable data : values) {
				sum  = sum + data.get();
			}
			
			System.out.println(key+"--reduce---"+sum);
			total = total + sum ;
			
			total_t = total_t + sum ;
			System.out.println(key.getClass()+"--reduce-total--"+total);
			System.out.println(key.getClass()+"--reduce-total_t--"+total_t);
			content.write(key, new IntWritable(sum));
		}
		
		
	}

	public static void main(String[] args) throws Exception {
		Configuration conf  =new Configuration();
		conf.set("mapred.input.dir", args[0]);
		conf.set("mapred.output.dir",args[1]);
		Job job = new Job(conf,"jian_test_word_count"); //jobname
		
		FileSystem fileSystem = FileSystem.get(conf);
		if(fileSystem.exists(new Path(args[1]))){
			fileSystem.delete(new Path(args[1]));
		}
		
		//job.setNumReduceTasks(1);
		//Job job = Job.getInstance(conf);
		
		job.setJarByClass(WordCountMR.class);
		
		//输出 map combine reduce 处理类
		job.setMapperClass(WCMap.class);
		
		job.setCombinerClass(WCReduce.class); //加上combiner 会在map过程将reduce的结果打印出来
		
		job.setReducerClass(WCReduce.class);
		
		//设置输出类型
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		
		//设置输入目录 和 输出目录
		FileInputFormat.addInputPath(new JobConf(conf), new Path(args[0]));
		FileOutputFormat.setOutputPath(new JobConf(conf), new Path(args[1]));
		
		//输出结果
		System.exit(job.waitForCompletion(true) ? 1 :0);
		

	}

}
