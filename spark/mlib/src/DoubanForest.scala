
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
object Douban2 extends App {
 val sparkConf = new SparkConf().setAppName("graph")
 val sc = new SparkContext(sparkConf)
 
 var  label  :RDD[Double] = sc.textFile("/data/doubansplit1.txt",50).map { line =>
       val fields = line.split("\001")
       var  result=0.0;
       try{
            if(fields(0).toDouble>3)
                result=1.0
         
         }catch{                
              case  e:Exception =>result=0.0
         } 
       result
  }
  var  data   = sc.textFile("/data/doubansplit1.txt",50).map { line =>
     val fields = line.split("\001")
     fields(1).trim()
  }
  val documents: RDD[Seq[String]] = data.map(_.split(" ").toSeq)
  val hashingTF = new HashingTF()
  val tf: RDD[Vector] = hashingTF.transform(documents)
  tf.cache()
  val idf = new IDF().fit(tf)
  val tfidf: RDD[Vector] = idf.transform(tf)
  
  var  zipped:RDD[(Double,Vector)]=label.zip(tfidf)
  var  adata=zipped.map { line =>
    LabeledPoint(line._1,line._2)  
  }
  
  var splits = adata.randomSplit(Array(0.7, 0.3))
  var (trainingData, testData) = (splits(0), splits(1))
  /*
  var pdata=trainingData.filter { x => x.label==0 }
  trainingData=trainingData.union(pdata)
  trainingData=trainingData.union(pdata)
  trainingData=trainingData.union(pdata)
  trainingData=trainingData.union(pdata)
  trainingData=trainingData.union(pdata)
  
  trainingData.filter { x => x.label==1 }.count
  trainingData.filter { x => x.label==0 }.count
*/
  val numClasses = 2
  val categoricalFeaturesInfo = Map[Int, Int]()
  val numTrees = 20 // Use more in practice.
  val featureSubsetStrategy = "auto" // Let the algorithm choose.
  val impurity = "gini"
  val maxDepth = 4
  val maxBins = 32
  
  var model = RandomForest.trainClassifier(trainingData, numClasses, categoricalFeaturesInfo,
    numTrees, featureSubsetStrategy, impurity, maxDepth, maxBins)

  
  // Evaluate model on test instances and compute test error
  var labelAndPreds = testData.map { point =>
    val prediction = model.predict(point.features)
      (prediction,point.label)
  }
  var testErr = labelAndPreds.filter(r => r._1 != r._2).count.toDouble / testData.count()
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