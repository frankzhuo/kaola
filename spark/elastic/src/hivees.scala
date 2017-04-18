/**
  * Created by yn on 2016/3/11.
  */


import org.apache.spark.sql.hive.HiveContext
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.types.{StructType,StructField,StringType}
import org.elasticsearch.spark.sql._
import org.apache.spark.sql.Row
import org.elasticsearch.hadoop.cfg.ConfigurationOptions._
import scala.util.Random

object hivees {
  def main(args: Array[String]) {

    val conf = new SparkConf().setAppName("flume_streaming1")
      .set("es.batch.size.bytes", "300000000")
      .set("es.batch.size.entries", "10000")
      .set("es.batch.write.refresh", "false")
      .set("es.batch.write.retry.count", "50")
      .set("es.batch.write.retry.wait", "500")
      .set("es.http.timeout", "5m")
      .set("es.http.retries", "50")
      .set("es.action.heart.beat.lead", "50")

    val sc = new SparkContext(conf)
    val sqlContext = new HiveContext(sc)

    //================数据处理开始======================
    import org.apache.spark.sql.Row
    import org.elasticsearch.hadoop.cfg.ConfigurationOptions._
    //val yuanData = sqlContext.sql("SELECT * FROM ZHENGXIN.xiaodai")
    val yuanData = sqlContext.sql("SELECT * FROM ZHENGXIN.xiaodai")
    
   // val typeName:String = tableName.split("\\.")(1)
    val routeEs:String = "test/test"

    yuanData.saveToEs(Map(ES_RESOURCE_WRITE->routeEs,ES_NODES->"10.5.28.11",
      ES_MAPPING_ID->"idno"))
  }

}
