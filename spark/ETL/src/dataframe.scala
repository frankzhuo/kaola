/**
  * Created by Administrator on 2016/5/5.
  */

import org.apache.spark.sql.hive.HiveContext
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.Row
import scala.util.Random

object dataframe {
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
   // val sqlContext = new org.apache.spark.sql.SQLContext(sc)
    //================数据处理开始======================
    import org.apache.spark.sql.Row
    import org.apache.spark.sql.{Column => col}
    import org.apache.spark.sql.types.{StructType,StructField,StringType};
    import org.apache.spark.sql.Row;
    import sqlContext.implicits._
    val data  = sc.textFile("/data/baidu",100).filter{
      line=>
        val ar : Array[String] = line.split("\001")
        if(ar.length>6)
          true
        else
          false
    }.map{
      line=>
        val tar : Array[String] = line.split("\001")
        (tar(0),line)
    }
    val pp=data.toDF()

    val   rdata=data.reduceByKey( (x,y)  =>  y)

    val   rdata1=data.groupByKey()

    rdata1.first()._2.mkString
    // Generate the schema based on the string of schema
   // val schemaString="id,cid,name,casecode,age,sex,cardnum,courtname,areaname,gistid,regdate,gistunit,duty,performance,disrupttypename,pulishdate,partytypename"
   // val schemaString="id,cid,name,casecode,age,sex,cardnum,courtname,areaname,gistid,regdate,gistunit,duty,performance,disrupttypename"
    val schemaString="id,cid,name"
    val schema =
      StructType(
        schemaString.split(",").map(fieldName => StructField(fieldName, StringType, true)))
    schema.length
    val  data1  = sc.textFile("/data/baidu",100).map(_.split("\001")).filter(p=>p.length>16).map{p =>
      Row(p(0), p(1),p(2))
      // Row.fromSeq(p.toSeq)
    }
    val dataf= sqlContext.createDataFrame(data1,schema)
    dataf.registerTempTable("people")
    val nn=   sqlContext.sql("select id  from people")

    val oo=dataf.filter(dataf("id") >0 )
    val oo1=dataf.groupBy("name")
    import org.apache.spark.sql.GroupedData
    import org.apache.spark.sql.GroupedData
    dataf.saveAsTable("rr")
    dataf.saveAsTable("spss.ok2")
    nn.filter(nn("age")==="11").collect().foreach(println)
    val kk=dataf.rdd
    val yy=kk.map(u =>u.get(0)+""+u.get(2))
    //oldDataFrame1.filter(col("pk_mobile")==="13901154251")
    nn.count()
    //val peopleDataFrame = sqlContext.createDataFrame(rdata, schema)
    //val yuanData = sqlContext.sql("SELECT * FROM ZHENGXIN.xiaodai")
    //val yuanData1 = sqlContext.sql("SELECT * FROM merzx.v2_var_single_zb")
    // val typeName:String = tableName.split("\\.")(1)
    val routeEs:String = "test/test"


  }

}
