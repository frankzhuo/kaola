package com.kaola

import org.apache.spark.sql._
import org.apache.spark.{SparkConf, SparkContext}
import org.elasticsearch.hadoop.cfg.ConfigurationOptions._
import org.elasticsearch.spark.sql.EsSparkSQL

object read_ES_law_unitmore_imp_ex {
 def main(args: Array[String]) {
   val conf = new SparkConf()
   val sc = new SparkContext(conf)
   val sqlContext = new SQLContext(sc)

     //val df_unitmore = EsSparkSQL.esDF(sqlContext, Map(ES_RESOURCE_READ -> "law_unitmore/c_unitmore", ES_NODES -> "10.1.80.181"));
     val df_unitmore = EsSparkSQL.esDF(sqlContext, Map(ES_RESOURCE_READ -> "law_unitmore/c_unitmore", ES_NODES -> "10.5.28.13"));
     df_unitmore.registerTempTable("df_unitmore");

   val unitmore = sqlContext.sql("SELECT " +
     "trim(body.areaname) as areaname," +
     "trim(body.businessentity) as businessentity," +
     "trim(body.cardnum) as cardnum," +
     "trim(body.casecode) as casecode," +
     "trim(body.cid) as cid," +
     "trim(body.courtname) as courtname," +
     "trim(body.disrupttypename) as disrupttypename," +
     "trim(body.duty) as duty," +
     "trim(body.gistid) as gistid," +
     "trim(body.gistunit) as gistunit," +
     "trim(body.id) as id," +
     "trim(body.name) as name," +
     "trim(body.performance) as performance," +
     "trim(body.pulishdate) as pulishdate," +
     "trim(body.name) as name," +
     "regexp_replace(regexp_replace(regexp_replace(body.regdate,'年',''),'月',''),'日','') as regdate," +
     "regexp_replace(trim(body.sys_time),' 00:00:00','') as sys_time FROM df_unitmore T");

//   val esData = unitmore.map { row =>
//     val newRow = row.toSeq.toBuffer
//     // newRow(14)=newRow(14).toString.replaceAll("年","").replaceAll("月","").replaceAll("日","");
//     Row.fromSeq(newRow.seq)
//   }

   val esDataFrame = sqlContext.createDataFrame(unitmore.rdd, unitmore.schema)
   EsSparkSQL.saveToEs(esDataFrame,Map(ES_RESOURCE -> "law_shixin/c_unitmore_new", ES_NODES -> "10.5.28.13"))
   println("finish.............")


 }
}