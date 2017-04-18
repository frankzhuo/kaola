
import java.text.SimpleDateFormat
import java.util.{Calendar, Date}

import org.apache.spark._
import org.apache.spark.rdd.RDD

import scala.collection.mutable.{ArrayBuffer, Map}

/**
 * @author chengqj
 */
class Merzxback {
  val sparkConf = new SparkConf().setAppName("ETL")
  val sc = new SparkContext(sparkConf)

  var data :RDD[(String,String)] = sc.textFile("/user/hive/warehouse/zhengxin.db/mer_tmp",100).map{
    line=>
      val tar : Array[String] = line.split("\001")
      (tar(0)+":"+tar(1)+":"+tar(2),tar(3)+":"+tar(4))
  }
  //var result=data.groupByKey()
  //reuslt.take(10)
  var result=data.groupByKey().map {
    line =>
      var value = ""
      var map1 = Map[String, Int]()
      line._2.foreach {
        x =>
          var par: Array[String] = x.toString.split(":")
          map1 += (par(0) -> Integer.parseInt(par(1)))
        //value = value + x
      }
      var day1=false;
      var day3=false;
      var day7=false;
      var day15=false;
      var ar14day=ArrayBuffer[Int](15)
      for(j<-0 to 14){
        ar14day.insert(j,0)
      }
      var now:Date = new Date()
      for (i <- now.getDate to 365+now.getDate) {
          var dateFormat: SimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd")
          var cal: Calendar = Calendar.getInstance()
          cal.add(Calendar.DATE, -(365+now.getDate-i))
          var dstr = dateFormat.format(cal.getTime())
          var ivalue = map1.getOrElse(dstr,0)
          if(ivalue>3)
            day1=true;
          if((ivalue+ar14day(13)+ar14day(12)) >5)
            day3=true;
          if((ivalue+ar14day(13)+ar14day(12)+ar14day(11)+ar14day(10)+ar14day(9)+ar14day(8)) >8)
            day7=true;
          if((ivalue+ar14day(13)+ar14day(12)+ar14day(11)+ar14day(10)+ar14day(9)+ar14day(8)+ar14day(7)+ar14day(6)+ar14day(5)+ar14day(4)+ar14day(3)+ar14day(2)+ar14day(1)+ar14day(0)) >12)
            day15=true;

          for(j<-0 to 13){
            ar14day(j)=ar14day(j+1)
          }
          ar14day(14)=ivalue
      }
      if(day1==true || day3==true || day7==true || day15==true)
        (line._1,true)
      else
        (line._1,false)
  }
  result.cache()
  result.take(3)
  result.count()

  var resultend=result.filter{
    line=>
      if(line._2==true)
        true
      else
        false
  }
  resultend.take(3)
  resultend.cache()
  resultend.saveAsTextFile("/user/root/merzx4")

  //val   rdata=data.
    /*
      val tar : Array[String] = y.split("\001")
      (tar(0)+":"+tar(1)+":"+tar(2),y)
      }
*/
 }
