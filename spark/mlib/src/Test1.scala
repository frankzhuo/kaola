
import org.apache.spark.mllib.linalg.{Vector, Vectors}

/**
 * @author Administrator
 */
object Test1 extends App {
  val sv2: Vector = Vectors.sparse(3, Seq((0, 1.0), (2, 3.0)))
  sv2.toString()
}