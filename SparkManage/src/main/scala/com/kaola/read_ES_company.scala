package com.kaola

import org.apache.spark.sql._
import org.apache.spark.{ SparkConf, SparkContext }
import org.elasticsearch.hadoop.cfg.ConfigurationOptions._
import org.elasticsearch.spark.sql.EsSparkSQL

//spark-shell --master local --jars /root/jyk/elasticsearch-hadoop-2.2.0.jar
object read_ES_company {
  def main(args: Array[String]) {
    val conf = new SparkConf()
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)
    //私募通：融资数据
    val df_smt_invest = EsSparkSQL.esDF(sqlContext, Map(ES_RESOURCE_READ -> "company/smt_invest", ES_NODES -> "10.5.28.13"));
    val dt_smt_invest=df_smt_invest.select("body.comName")

    //国发改委信用企业
    val df_credit_company = EsSparkSQL.esDF(sqlContext, Map(ES_RESOURCE_READ -> "company/credit_company", ES_NODES -> "10.5.28.13"));
    val dt_credit_company=df_credit_company.select("body.compName")

    //守合同重信用企业
    val df_zxy_company = EsSparkSQL.esDF(sqlContext, Map(ES_RESOURCE_READ -> "company/zxy_company", ES_NODES -> "10.5.28.13"));
    val dt_zxy_company=df_zxy_company.select("body.compName")

    //中关村高薪企业查询
    val df_zgc_company = EsSparkSQL.esDF(sqlContext, Map(ES_RESOURCE_READ -> "company/zgc_company", ES_NODES -> "10.5.28.13"));
    val dt_zgc_company=df_zgc_company.select("body.comName")

    //中关村评级企业信息
    val df_pj_company = EsSparkSQL.esDF(sqlContext, Map(ES_RESOURCE_READ -> "company/pj_company", ES_NODES -> "10.5.28.13"));
    val dt_pj_company=df_pj_company.select("body.compName")


    //证券：债券负面事件企业查询
    val df_bond_negative_event = EsSparkSQL.esDF(sqlContext, Map(ES_RESOURCE_READ -> "security/bond_negative_event", ES_NODES -> "10.5.28.13"));
    val dt_bond_negative_event=df_bond_negative_event.select("body.publisher")


    //证券：上市公司查询
    val df_public_company = EsSparkSQL.esDF(sqlContext, Map(ES_RESOURCE_READ -> "security/public_company", ES_NODES -> "10.5.28.13"));
    val dt_public_company=df_public_company.select("body.ChName")


    //全国建筑市场监管与诚信(企业)
    val df_con_company = EsSparkSQL.esDF(sqlContext, Map(ES_RESOURCE_READ -> "construction/con_company", ES_NODES -> "10.5.28.13"));
    val dt_con_company=df_con_company.select("body.comp_name")

    var tt = dt_smt_invest.unionAll(dt_credit_company).unionAll(dt_zxy_company).unionAll(dt_zgc_company).unionAll(dt_pj_company).
      unionAll(dt_bond_negative_event).unionAll(dt_public_company).unionAll(dt_con_company)

    tt.distinct.rdd.coalesce(1,true).take(2)
    tt.distinct.rdd.coalesce(1).filter(row => row(0) !=null).filter(row => row(0).toString.replaceAll(" ","").trim().length() >3).map(row => row(0)).saveAsTextFile("/data/companyname3")

    //tt.distinct.rdd.coalesce(1).filter(row => row(0) !=null).filter(row => row(0).toString.replaceAll(" ","").trim().length() <=3).map(row => row(0)).saveAsTextFile("/data/companyname4")

    //dt_credit_company.registerTempTable("dataFrame")
    //println(dt_credit_company.head())
    //dt_credit_company.printSchema();
    //dt_credit_company.show();
    //org.apache.spark.sql.types.StructType
    //dt_credit_company.select("body.comName")

    val invest = sqlContext.sql("SELECT body.comName FROM dataFrame T")
  }
}