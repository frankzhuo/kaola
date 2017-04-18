import org.apache.spark._
//import org.apache.spark.graphx._
// To make some of the examples work we will also need RDD
//import org.apache.spark.rdd.RDD
//import  scala.collection.mutable.Set
//import  org.apache.spark.SparkContext._

/**
  * Created by Administrator on 2016/5/4.
  */
object Baidu {
  val sparkConf = new SparkConf().setAppName("ETL")
  val sc = new SparkContext(sparkConf)
  val regex="""Some\((.)\)""".r
  var data  = sc.textFile("/data/baidu",100).filter{
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
  val   rdata=data.reduceByKey( (x,y)  =>  y)
   // .map{(x,y) => y}
  //val mdata= rdata.map{ x => x._2}
  val idata= rdata.map{ x => x._1}
 // rdata.saveAsTextFile("/data/baidutmp")
  var person  = sc.textFile("/data/person",100).filter{
    line=>
      val ar : Array[String] = line.split("\001")
      if(ar.length>6)
        true
      else
        false
  }.map{
    line=>
      val tar : Array[String] = line.split("\001")
      tar(0)
  }
  val sub=idata.subtract(person)
  val psub=sub.map(x =>(x,x))

  val result=psub.leftOuterJoin(rdata).map(x=>x._2).map{x=>
      x._2.getOrElse(0,"")
    }
  result.saveAsTextFile("/data/result5")
  result.count()

}
