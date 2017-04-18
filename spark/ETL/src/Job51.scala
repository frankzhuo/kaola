import org.apache.spark._
import org.apache.spark.graphx._
// To make some of the examples work we will also need RDD
import org.apache.spark.rdd.RDD
import  scala.collection.mutable.Set

/**
 * @author Administrator
 */
object Job51 extends App {
        val sparkConf = new SparkConf().setAppName("ETL")
        val sc = new SparkContext(sparkConf)
        val regex="""([0-9]{6})""".r
        var data: RDD[ String]  = sc.textFile("/data/51jobnew",100).filter{
            line=>
            var ar : Array[String] = line.split("\001")
            if(ar.length>3) 
              true
            else 
              false
        }.map{ line =>
          var result:String=""    
          var ar : Array[String] = line.split("\001")
          result= ar(0).toString()+"\001" +ar(1).toString()+"\001"
          var tmpar : Array[String] = ar(2).toString().split("\\|")
          if(tmpar.length>2)
            result=result+tmpar(0).replaceAll(" ", "").trim()+"\001" +tmpar(1).replaceAll(" ", "").trim()+"\001"+tmpar(2).replaceAll(" ", "").trim()+"\001"
          else if (tmpar.length>1){
            if(tmpar(0).toString().indexOf("人")>0){
              result=result+"\001"+tmpar(0).replaceAll(" ", "").trim()+"\001"+tmpar(1).replaceAll(" ", "").trim()+"\001"
            }else if(tmpar(1).toString().indexOf("人")>0){
              result=result+tmpar(0).replaceAll(" ", "").trim()+"\001"+tmpar(1).toString().replaceAll(" ", "").trim()+"\001"+"\001"
            }else{
              result=result+tmpar(0).replaceAll(" ", "").trim()+"\001"+"\001"++tmpar(1).replaceAll(" ", "").trim()+"\001"
            }
              
          }
          else{
             if(tmpar(0).toString().indexOf("人")>0){
              result=result+"\001"+tmpar(0).replaceAll(" ", "")+"\001"+"\001"
             }else{
              result=result+tmpar(0).replaceAll(" ", "")+"\001"+"\001"+"\001"
            }
            
          }
          result=result+ ar(3).toString()+"\001"
          if(ar.length>4){
            result=result+ar(4).toString()+"\001" 
            val code=regex.findFirstIn(ar(4).toString())
            code match{
              case Some(x)  => result=result+x
              case None  =>  result=result+"  "
            }
          }
          else{
            result=result+"\001  " 
          }
          //result=result+"\n"
          result
        }
        data.saveAsTextFile("/data/51result/");
        //data.saveAsTextFile("/data/result/ok12");
}

