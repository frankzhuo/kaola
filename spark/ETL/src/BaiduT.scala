import org.apache.spark._
// To make some of the examples work we will also need RDD

/**
  * Created by Administrator on 2016/5/4.
  */
object BaiduT {
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
  rdata.count();
  var data1  = sc.textFile("/data/baidu",100).filter{
    line=>
      val ar : Array[String] = line.split("\001")
      if(ar.length>6)
        true
      else
        false
  }.map{
    line=>
      (line, line.hashCode)
  }
  val   rdata1=data1.reduceByKey( (x,y)  =>  y)
  rdata1.count();

}
