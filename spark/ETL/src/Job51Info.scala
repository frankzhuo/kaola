import org.apache.spark._
import org.apache.spark.graphx._
// To make some of the examples work we will also need RDD
import org.apache.spark.rdd.RDD
import  scala.collection.mutable.Setimport org.apache.spark._
import org.apache.spark.graphx._
// To make some of the examples work we will also need RDD
import org.apache.spark.rdd.RDD
import  scala.collection.mutable.Set

/**
 * @author Administrator
 */
object Job51Info extends App {
        val sparkConf = new SparkConf().setAppName("ETL")
        val sc = new SparkContext(sparkConf)
        val regex="""([0-9]+)年经验""".r     
        val regexP="""(招聘[0-9]+)人""".r
        var data: RDD[ String]  = sc.textFile("/data/51jobinfonew4",100).filter{
            line=>
            var ar : Array[String] = line.split("\001")
            if(ar.length>11)
              true
            else 
              false
        }.map{ line =>
          var result:String=""    
          var ar : Array[String] = line.split("\001")
          //url cname wname  address  money pubdate
          result= ar(0).toString()+"\001" +ar(1).toString()+"\001"+ar(2).toString()+"\001"+ar(4).toString()+"\001"+ar(5).toString()+"\001"+ar(6).toString()+"\001"
          var code=regex.findFirstIn(ar(8))
          //经验
            code match{
              case Some(x)  => result=result+x+"\001"
              case None  =>  result=result+" \001"
            }
          //人数
          code=regexP.findFirstIn(ar(8))
            code match{
              case Some(x)  => result=result+x+"\001"
              case None  =>  result=result+" \001"
            }
          //学历
          if(ar(8).indexOf("大专") > -1){
            result=result+"大专\001"
          }else if(ar(8).indexOf("本科") > -1)
            result=result+"本科\001"
          else  if(ar(8).indexOf("硕士") > -1)
             result=result+"硕士\001"
          else{
            result=result+" \001"
          }
          
          //highpoint 
          result= result+ar(9).toString()+"\001"
          //岗位
          var begin=ar(10).indexOf("岗位要求")
          if(begin<0)
            begin=ar(10).indexOf("任职要求")
          if(begin<0)
            begin=ar(10).indexOf("任职资格")
          if(begin<0)
            begin=ar(10).indexOf("岗位条件") 
          if(begin<0)
            begin=ar(10).indexOf("任职条件") 
            
          if(begin == 0){
             result= result+"\001"+ar(10)+"\001"
          }  
          else if(begin > 0){
             result= result+ar(10).substring(0, begin-1)+"\001"+ar(10).substring(begin)+"\001"
          }else{
            result= result+ar(10).toString()+"\001"+"\001"
          }
          //jobtype
          result= result+ar(11).toString()+"\001"
          //keyword
          if(ar.length>12)
            result= result+ ar(12).toString()+" "
          else
            result= result+" "
          //result=result+"\n"
          result
        }
        data.saveAsTextFile("/data/result/new18");
        //data.saveAsTextFile("/data/result/ok12");
}

