
import org.apache.spark._
//import org.apache.spark.graphx._
// To make some of the examples work we will also need RDD
import org.apache.spark.rdd.RDD
import  scala.collection.mutable.Set
//import  org.apache.spark.SparkContext._
/**
 * @author Administrator
 */
object Word {
        val sparkConf = new SparkConf().setAppName("ETL")
        val sc = new SparkContext(sparkConf)
        //val  ok=sc.parallelize([(3,5),(2,3),(1,2)])
        
         var data  = sc.textFile("/data/51job",100).flatMap { x => x.split(" ") }

//         
//        val  d1: RDD[String,Int]   =data.map[String,Int]{
//            line=>
//            (line,1)
//        }  
//
       val a :Int = 1
        var data1 :RDD[(String,Int)] = sc.textFile("/data/ok",100).map{
            line=>
            (line,1)
        }

        var aa=data1.reduceByKey(_+_)
        aa.collect().foreach(println)
//        data.reduce(f)

}