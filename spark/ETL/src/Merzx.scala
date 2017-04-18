
import org.apache.spark._
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.Row
import org.apache.spark.sql.types.{StringType, StructField, StructType}
import  scala.collection.mutable.Map
import  scala.collection.mutable.ArrayBuffer
import  java.text.SimpleDateFormat
import  java.util.Calendar
import  java.util.Date


/**
 * @author chengqj
 */
object Merzx {
  def  main(args: Array[String]) {
    val sparkConf = new SparkConf().setAppName("ETL")
    val sc = new SparkContext(sparkConf)

    var data :RDD[(String,String)] = sc.textFile("/user/hive/warehouse/zhengxin.db/mer_tmp",100).map{
      line=>
        var tar : Array[String] = line.split("\001")
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
        var ar14day=new Array[Int](15)
        //for(j<-0 to 14){
        //  ar14day.insert(j,0)
        //}
        var now:Date = new Date()
        for (i <- 0 to 365) {
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
    //result.cache()
    //result.take(3)
    //result.count()

    var resultend=result.filter{
      line=>
        if(line._2==true)
          true
        else
          false
    }
   // resultend.take(3)
    //resultend.count()
    //resultend.saveAsTextFile("/user/root/merzx9")
    var rowRDD = resultend.map{
      line =>
        var par: Array[String]=line._1.split(":")
        Row(par(0), par(1),par(2))
    }

    var hiveContext = new org.apache.spark.sql.hive.HiveContext(sc)
    //import hiveContext.implicits._

    // The schema is encoded in a string
    var schemaString = "orgid mer_id cardno"

    // Import Spark SQL data types and Row.
    // Generate the schema based on the string of schema
    var schema1 =
      StructType(
        schemaString.split(" ").map(fieldName => StructField(fieldName, StringType, true)))

    // Apply the schema to the RDD.
    var rDataFrame1 = hiveContext.createDataFrame(rowRDD, schema1)
    // Register the DataFrames as a table.
    //rDataFrame.registerTempTable("merzx_times")

    hiveContext.sql("use zhengxin")
    rDataFrame1.insertInto(tableName="merzx_times2",overwrite=true)
  }
  //val   rdata=data.
    /*
      val tar : Array[String] = y.split("\001")
      (tar(0)+":"+tar(1)+":"+tar(2),y)
      }
*/
 }
