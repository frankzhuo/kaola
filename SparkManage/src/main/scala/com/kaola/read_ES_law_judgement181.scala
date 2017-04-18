package com.kaola

import org.apache.spark.sql._
import org.apache.spark.{SparkConf, SparkContext}
import org.elasticsearch.hadoop.cfg.ConfigurationOptions._
import org.elasticsearch.spark.sql.EsSparkSQL

object read_ES_law_personmore181 {
 def main(args: Array[String]) {
   val conf = new SparkConf()
   val sc = new SparkContext(conf)
   val sqlContext = new SQLContext(sc)

   val df_personmore = EsSparkSQL.esDF(sqlContext, Map(ES_RESOURCE_READ -> "law_personmore/c_personmore", ES_NODES -> "10.1.80.181"));
   //  val df_personmore = EsSparkSQL.esDF(sqlContext, Map(ES_RESOURCE_READ -> "law_personmore/c_personmore", ES_NODES -> "10.5.28.13"));
   df_personmore.registerTempTable("df_personmore")

   val personmore = sqlContext.sql("SELECT distinct body.name,body.cardnum FROM df_personmore T");
     personmore.count();


     //val df_unitmore = EsSparkSQL.esDF(sqlContext, Map(ES_RESOURCE_READ -> "law_unitmore/c_unitmore", ES_NODES -> "10.1.80.181"));
     val df_unitmore = EsSparkSQL.esDF(sqlContext, Map(ES_RESOURCE_READ -> "law_unitmore/c_unitmore", ES_NODES -> "10.5.28.13"));
     df_unitmore.registerTempTable("df_unitmore")

     val unitmore = sqlContext.sql("SELECT distinct body.name,body.cardnum FROM df_unitmore T");
     unitmore.count();

 }
}