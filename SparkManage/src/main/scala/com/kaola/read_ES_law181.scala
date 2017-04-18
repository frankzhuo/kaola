package com.kaola

import org.apache.spark.sql._
import org.apache.spark.{SparkConf, SparkContext}
import org.elasticsearch.hadoop.cfg.ConfigurationOptions._
import org.elasticsearch.spark.sql.EsSparkSQL
//in hadoop18
//spark-shell --master yarn --jars /home/hdfs/elasticsearch-hadoop-2.2.0.jar --num-excutors 10
 /**
  * 正式库 中 notice_type 一起查寻时，有问题
  */
object read_ES_law181 {
  def main(args: Array[String]) {
    val conf = new SparkConf()
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)
   // val df_court_notice = EsSparkSQL.esDF(sqlContext, Map(ES_RESOURCE_READ -> "law_court/court_notice", ES_NODES -> "10.5.28.13"));
    val df_court_notice = EsSparkSQL.esDF(sqlContext, Map(ES_RESOURCE_READ -> "law_court/court_notice", ES_NODES -> "10.1.80.181"));
    df_court_notice.registerTempTable("df_court_notice")

    df_court_notice.select("body.notice_type").distinct().show()

//
    val xzp1 = sqlContext.sql("SELECT body.party,body.notice_type,body.notice_user_name,body.publish_time FROM df_court_notice T" +
      " where body.notice_user_name like '[西藏]%' and length(body.party)<4 and body.notice_type != '裁判文书' and body.publish_time like '2015%' limit 10")
    val xzp2 = sqlContext.sql("SELECT body.party,body.notice_type,body.notice_user_name,body.publish_time FROM df_court_notice T" +
      " where body.notice_user_name like '[西藏]%' and length(body.party)<4 and body.notice_type != '裁判文书' and body.publish_time like '2016%' limit 10")
    val xzp3 = sqlContext.sql("SELECT body.party,body.notice_type,body.notice_user_name,body.publish_time FROM df_court_notice T" +
      " where body.notice_user_name like '[西藏]%' and length(body.party)<4 and body.notice_type != '裁判文书' and body.publish_time like '2017%' limit 10")
    val xzo1 = sqlContext.sql("SELECT body.party,body.notice_type,body.notice_user_name,body.publish_time FROM df_court_notice T" +
      " where body.notice_user_name like '[西藏]%' and length(body.party)>4 and body.notice_type != '裁判文书' and body.party like '%公司%' and body.publish_time like '2015%' limit 10")
    val xzo2 = sqlContext.sql("SELECT body.party,body.notice_type,body.notice_user_name,body.publish_time FROM df_court_notice T" +
      " where body.notice_user_name like '[西藏]%' and length(body.party)>4 and body.notice_type != '裁判文书' and body.party like '%公司%' and body.publish_time like '2016%' limit 10")
    val xzo3 = sqlContext.sql("SELECT body.party,body.notice_type,body.notice_user_name,body.publish_time FROM df_court_notice T" +
      " where body.notice_user_name like '[西藏]%' and length(body.party)>4 and body.notice_type != '裁判文书' and body.party like '%公司%' and body.publish_time like '2017%' limit 10")
    xzp1.rdd.saveAsTextFile("/data/court_notice/xzp1")
    xzp2.rdd.saveAsTextFile("/data/court_notice/xzp2")
    xzp3.rdd.saveAsTextFile("/data/court_notice/xzp3")
    xzo1.rdd.saveAsTextFile("/data/court_notice/xzo1")
    xzo2.rdd.saveAsTextFile("/data/court_notice/xzo2")
    xzo3.rdd.saveAsTextFile("/data/court_notice/xzo3")


    val sxp1 = sqlContext.sql("SELECT body.party,body.notice_type,body.notice_user_name,body.publish_time FROM df_court_notice T" +
      " where body.notice_user_name like '[陕西]%' and length(body.party)<4 and body.notice_type != '裁判文书' and body.publish_time like '2015%' limit 10")
    val sxp2 = sqlContext.sql("SELECT body.party,body.notice_type,body.notice_user_name,body.publish_time FROM df_court_notice T" +
      " where body.notice_user_name like '[陕西]%' and length(body.party)<4 and body.notice_type != '裁判文书' and body.publish_time like '2016%' limit 10")
    val sxp3 = sqlContext.sql("SELECT body.party,body.notice_type,body.notice_user_name,body.publish_time FROM df_court_notice T" +
      " where body.notice_user_name like '[陕西]%' and length(body.party)<4 and body.notice_type != '裁判文书' and body.publish_time like '2017%' limit 10")
    val sxo1 = sqlContext.sql("SELECT body.party,body.notice_type,body.notice_user_name,body.publish_time FROM df_court_notice T" +
      " where body.notice_user_name like '[陕西]%' and length(body.party)>4 and body.notice_type != '裁判文书' and body.party like '%公司%' and body.publish_time like '2015%' limit 10")
    val sxo2 = sqlContext.sql("SELECT body.party,body.notice_type,body.notice_user_name,body.publish_time FROM df_court_notice T" +
      " where body.notice_user_name like '[陕西]%' and length(body.party)>4 and body.notice_type != '裁判文书' and body.party like '%公司%' and body.publish_time like '2016%' limit 10")
    val sxo3 = sqlContext.sql("SELECT body.party,body.notice_type,body.notice_user_name,body.publish_time FROM df_court_notice T" +
      " where body.notice_user_name like '[陕西]%' and length(body.party)>4 and body.notice_type != '裁判文书' and body.party like '%公司%' and body.publish_time like '2017%' limit 10")
    sxp1.rdd.saveAsTextFile("/data/court_notice/sxp1")
    sxp2.rdd.saveAsTextFile("/data/court_notice/sxp2")
    sxp3.rdd.saveAsTextFile("/data/court_notice/sxp3")
    sxo1.rdd.saveAsTextFile("/data/court_notice/sxo1")
    sxo2.rdd.saveAsTextFile("/data/court_notice/sxo2")
    sxo3.rdd.saveAsTextFile("/data/court_notice/sxo3")



    val gsp1 = sqlContext.sql("SELECT body.party,body.notice_type,body.notice_user_name,body.publish_time FROM df_court_notice T" +
      " where body.notice_user_name like '[甘肃]%' and length(body.party)<4 and body.notice_type != '裁判文书' and body.publish_time like '2015%' limit 10")
    val gsp2 = sqlContext.sql("SELECT body.party,body.notice_type,body.notice_user_name,body.publish_time FROM df_court_notice T" +
      " where body.notice_user_name like '[甘肃]%' and length(body.party)<4 and body.notice_type != '裁判文书' and body.publish_time like '2016%' limit 10")
    val gsp3 = sqlContext.sql("SELECT body.party,body.notice_type,body.notice_user_name,body.publish_time FROM df_court_notice T" +
      " where body.notice_user_name like '[甘肃]%' and length(body.party)<4 and body.notice_type != '裁判文书' and body.publish_time like '2017%' limit 10")
    val gso1 = sqlContext.sql("SELECT body.party,body.notice_type,body.notice_user_name,body.publish_time FROM df_court_notice T" +
      " where body.notice_user_name like '[甘肃]%' and length(body.party)>4 and body.notice_type != '裁判文书' and body.party like '%公司%' and body.publish_time like '2015%' limit 10")
    val gso2 = sqlContext.sql("SELECT body.party,body.notice_type,body.notice_user_name,body.publish_time FROM df_court_notice T" +
      " where body.notice_user_name like '[甘肃]%' and length(body.party)>4 and body.notice_type != '裁判文书' and body.party like '%公司%' and body.publish_time like '2016%' limit 10")
    val gso3 = sqlContext.sql("SELECT body.party,body.notice_type,body.notice_user_name,body.publish_time FROM df_court_notice T" +
      " where body.notice_user_name like '[甘肃]%' and length(body.party)>4 and body.notice_type != '裁判文书' and body.party like '%公司%' and body.publish_time like '2017%' limit 10")
    gsp1.rdd.saveAsTextFile("/data/court_notice/gsp1")
    gsp2.rdd.saveAsTextFile("/data/court_notice/gsp2")
    gsp3.rdd.saveAsTextFile("/data/court_notice/gsp3")
    gso1.rdd.saveAsTextFile("/data/court_notice/gso1")
    gso2.rdd.saveAsTextFile("/data/court_notice/gso2")
    gso3.rdd.saveAsTextFile("/data/court_notice/gso3")


    val qhp1 = sqlContext.sql("SELECT body.party,body.notice_type,body.notice_user_name,body.publish_time FROM df_court_notice T" +
      " where body.notice_user_name like '[青海]%' and length(body.party)<4 and body.notice_type != '裁判文书' and body.publish_time like '2015%' limit 10")
    val qhp2 = sqlContext.sql("SELECT body.party,body.notice_type,body.notice_user_name,body.publish_time FROM df_court_notice T" +
      " where body.notice_user_name like '[青海]%' and length(body.party)<4 and body.notice_type != '裁判文书' and body.publish_time like '2016%' limit 10")
    val qhp3 = sqlContext.sql("SELECT body.party,body.notice_type,body.notice_user_name,body.publish_time FROM df_court_notice T" +
      " where body.notice_user_name like '[青海]%' and length(body.party)<4 and body.notice_type != '裁判文书' and body.publish_time like '2017%' limit 10")
    val qho1 = sqlContext.sql("SELECT body.party,body.notice_type,body.notice_user_name,body.publish_time FROM df_court_notice T" +
      " where body.notice_user_name like '[青海]%' and length(body.party)>4 and body.notice_type != '裁判文书' and body.party like '%公司%' and body.publish_time like '2015%' limit 10")
    val qho2 = sqlContext.sql("SELECT body.party,body.notice_type,body.notice_user_name,body.publish_time FROM df_court_notice T" +
      " where body.notice_user_name like '[青海]%' and length(body.party)>4 and body.notice_type != '裁判文书' and body.party like '%公司%' and body.publish_time like '2016%' limit 10")
    val qho3 = sqlContext.sql("SELECT body.party,body.notice_type,body.notice_user_name,body.publish_time FROM df_court_notice T" +
      " where body.notice_user_name like '[青海]%' and length(body.party)>4 and body.notice_type != '裁判文书' and body.party like '%公司%' and body.publish_time like '2017%' limit 10")
    qhp1.rdd.saveAsTextFile("/data/court_notice/qhp1")
    qhp2.rdd.saveAsTextFile("/data/court_notice/qhp2")
    qhp3.rdd.saveAsTextFile("/data/court_notice/qhp3")
    qho1.rdd.saveAsTextFile("/data/court_notice/qho1")
    qho2.rdd.saveAsTextFile("/data/court_notice/qho2")
    qho3.rdd.saveAsTextFile("/data/court_notice/qho3")


    val nxp1 = sqlContext.sql("SELECT body.party,body.notice_type,body.notice_user_name,body.publish_time FROM df_court_notice T" +
      " where body.notice_user_name like '[宁夏]%' and length(body.party)<4 and body.notice_type != '裁判文书' and body.publish_time like '2015%' limit 10")
    val nxp2 = sqlContext.sql("SELECT body.party,body.notice_type,body.notice_user_name,body.publish_time FROM df_court_notice T" +
      " where body.notice_user_name like '[宁夏]%' and length(body.party)<4 and body.notice_type != '裁判文书' and body.publish_time like '2016%' limit 10")
    val nxp3 = sqlContext.sql("SELECT body.party,body.notice_type,body.notice_user_name,body.publish_time FROM df_court_notice T" +
      " where body.notice_user_name like '[宁夏]%' and length(body.party)<4 and body.notice_type != '裁判文书' and body.publish_time like '2017%' limit 10")
    val nxo1 = sqlContext.sql("SELECT body.party,body.notice_type,body.notice_user_name,body.publish_time FROM df_court_notice T" +
      " where body.notice_user_name like '[宁夏]%' and length(body.party)>4 and body.notice_type != '裁判文书' and body.party like '%公司%' and body.publish_time like '2015%' limit 10")
    val nxo2 = sqlContext.sql("SELECT body.party,body.notice_type,body.notice_user_name,body.publish_time FROM df_court_notice T" +
      " where body.notice_user_name like '[宁夏]%' and length(body.party)>4 and body.notice_type != '裁判文书' and body.party like '%公司%' and body.publish_time like '2016%' limit 10")
    val nxo3 = sqlContext.sql("SELECT body.party,body.notice_type,body.notice_user_name,body.publish_time FROM df_court_notice T" +
      " where body.notice_user_name like '[宁夏]%' and length(body.party)>4 and body.notice_type != '裁判文书' and body.party like '%公司%' and body.publish_time like '2017%' limit 10")
    nxp1.rdd.saveAsTextFile("/data/court_notice/nxp1")
    nxp2.rdd.saveAsTextFile("/data/court_notice/nxp2")
    nxp3.rdd.saveAsTextFile("/data/court_notice/nxp3")
    nxo1.rdd.saveAsTextFile("/data/court_notice/nxo1")
    nxo2.rdd.saveAsTextFile("/data/court_notice/nxo2")
    nxo3.rdd.saveAsTextFile("/data/court_notice/nxo3")
  }
}