package com.kaola

import org.apache.spark.sql._
import org.apache.spark.{SparkConf, SparkContext}
import org.elasticsearch.hadoop.cfg.ConfigurationOptions._
import org.elasticsearch.spark.sql.EsSparkSQL

object read_ES_law_judgement_imp_ex {
 def main(args: Array[String]) {
   val conf = new SparkConf()
   val sc = new SparkContext(conf)
   val sqlContext = new SQLContext(sc)

     //val df_judgement = EsSparkSQL.esDF(sqlContext, Map(ES_RESOURCE_READ -> "law_judgement/judgement", ES_NODES -> "10.1.80.181"));
     val df_judgement = EsSparkSQL.esDF(sqlContext, Map(ES_RESOURCE_READ -> "law_judgement/judgement", ES_NODES -> "10.5.28.13"));
     df_judgement.registerTempTable("df_judgement");

   val judgement = sqlContext.sql("SELECT " +
     "nvl(trim(body.court),'') as court," +
     "nvl(trim(body.jtype),'') as jtype," +
     "nvl(trim(body.title),'') as title," +
     "regexp_replace(trim(body.jdate),' 00:00:00','') as jdate," +
     "nvl(trim(body.jnum),'') as jnum," +
     "nvl(trim(body.court_type),'') as court_type," +
     "nvl(trim(body.yuangao),'') as yuangao," +
     "nvl(trim(body.beigao),'') as beigao," +
     "nvl(trim(body.shangshuren),'') as shangshuren," +
     "nvl(trim(body.beishangshuren),'') as beishangshuren," +
     "nvl(trim(body.weitobianhuren),'') as weitobianhuren," +
     "nvl(trim(body.dangshiren),'') as dangshiren," +
     "nvl(trim(body.dangshiren_all),'') as dangshiren_all," +
     "nvl(trim(body.url),'') as url," +
     "nvl(trim(body.content),'') as content," +
     "nvl(trim(body.judge_date),'') as judge_date," +
     "nvl(trim(body.jcase),'') as jcase," +
     "nvl(trim(body.jprocees),'') as jprocees," +
     "nvl(trim(body.jsummary),'') as jsummary," +
     "nvl(trim(body.identity_card),'') as identity_card," +
     "nvl(trim(body.result_str),'') as result_str," +
     "nvl(trim(body.result),'') as result," +
     "regexp_replace(trim(body.sys_time),' 00:00:00','') as sys_time FROM df_judgement T");

   val esDataFrame = sqlContext.createDataFrame(judgement.rdd, judgement.schema)
   EsSparkSQL.saveToEs(esDataFrame,Map(ES_RESOURCE -> "law_shixin/judgement_new", ES_NODES -> "10.5.28.13"))
   println("finish.............")


 }
}