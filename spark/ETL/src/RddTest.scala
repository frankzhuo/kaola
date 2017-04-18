
import org.apache.spark._
import org.apache.spark.graphx._
// To make some of the examples work we will also need RDD
import org.apache.spark.rdd.RDD
import  scala.collection.mutable.Set
/**
 * @author Administrator
 */
object RddTest {
        val sparkConf = new SparkConf().setAppName("ETL")
        val sc = new SparkContext(sparkConf)
        
        var data: RDD[ String]  = sc.textFile("/data/51job",100).filter{
            line=>
            var ar : Array[String] = line.split("\001")
            if(ar.length>3) 
              true
            else 
              false
        }
        
}