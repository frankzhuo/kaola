
import org.apache.spark._
import org.apache.spark.rdd._
import org.apache.spark.SparkContext
import org.apache.spark.mllib.classification.{LogisticRegressionWithLBFGS, LogisticRegressionModel}
import org.apache.spark.mllib.evaluation.MulticlassMetrics
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.util.MLUtils

import org.apache.spark.rdd.RDD
import org.apache.spark.SparkContext
import org.apache.spark.mllib.feature.HashingTF
import org.apache.spark.mllib.linalg.Vector
import org.apache.spark.mllib.feature.IDF
import org.apache.spark.mllib.tree.RandomForest
import org.apache.spark.mllib.evaluation.BinaryClassificationMetrics

/**
 * @author chengqj
 */
object Test3 extends App {
 val sparkConf = new SparkConf().setAppName("graph")
 val sc = new SparkContext(sparkConf)
 
 var  label  :RDD[Double] = sc.textFile("/data/doubansplit.txt",50).map { line =>
       val fields = line.split("\001")
       var  result=0.0;
       if(fields(0).toDouble>3)
          result=1.0
       result
  }
  var  data   = sc.textFile("/data/doubansplit.txt",50).map { line =>
     val fields = line.split("\001")
     fields(1).trim()
  }
  val documents: RDD[Seq[String]] = data.map(_.split(" ").toSeq)
  val hashingTF = new HashingTF()
  val tf: RDD[Vector] = hashingTF.transform(documents)
  
  /*
  var  data1   = sc.textFile("/data/douban.txt",50).map { line =>
     val fields = line.split("\001")
     fields(1).trim()
  }
  val hashingTF1 = new HashingTF()
  val documents1: RDD[Seq[String]] = data1.map(_.split(" ").toSeq)
  val tf1: RDD[Vector] = hashingTF1.transform(documents1)
  tf1.take(2)
  */
  
  tf.cache()
  val idf = new IDF().fit(tf)
  val tfidf: RDD[Vector] = idf.transform(tf)
 
  
  var  zipped:RDD[(Double,Vector)]=label.zip(tfidf)
  var  adata=zipped.map { line =>
    LabeledPoint(line._1,line._2)  
  }
  
  var model = LogisticRegressionModel.load(sc, "/data/douban2")
  
 
  var  num=0
  for(i<- 0 until model.weights.size){
    
     if(model.weights(i)!= 0.0){
        num=num+1
        println( i+" "+model.weights(i))
     }
  }  
     
  //for( t  <-  model.weights  if t>1.0 ) yield t
 

  /*
    var labelAndPreds = pdata.map { point =>
    val prediction = model.predict(point.features)
      (prediction,point)
  }
  */
  // Evaluate model on test instances and compute test error
  
  model.predict(adata.take(1)(0).features)
  
  //model.predict(trainingData.take(2)(1).features)
  
  var labelAndPreds = adata.map { point =>
    val prediction = model.predict(point.features)
      (prediction,point.label)
  }
  var testErr = labelAndPreds.filter(r => r._1 != r._2).count.toDouble / adata.count()
  var precision = labelAndPreds.filter(r=>r._1==r._2&&r._2==1).count.toDouble/(labelAndPreds.filter(r=>r._1==r._2&&r._2==1).count.toDouble+labelAndPreds.filter(r=>r._1==1&&r._2==0).count.toDouble)
  
  var recall = labelAndPreds.filter(r=>r._1==r._2&&r._2==1).count.toDouble/(labelAndPreds.filter(r=>r._1==r._2&&r._2==1).count.toDouble+labelAndPreds.filter(r=>r._1==0&&r._2==1).count.toDouble)
  
  //var precision = labelAndPreds.filter(r=>r._1==r._2&&r._2==1).count.toDouble/labelAndPreds.filter(r=>r._2==1).count
  //var recall = labelAndPreds.filter(r=>r._1==r._2&&r._2==1).count.toDouble/(labelAndPreds.filter(r=>r._1==r._2&&r._2==1).count.toDouble+labelAndPreds.filter(r=>r._1==1&&r._2==1).count.toDouble)
  var metrics = new BinaryClassificationMetrics(labelAndPreds)
  var auROC = metrics.areaUnderROC()
  
  println("AUC =" + auROC)
  println("Test Error = " + testErr)
  println("TP/(TP+FP)"+precision+"################################################")
  println("TP/(TP+FN)"+recall+"################################################")
  
  data.collect.foreach(println)



}