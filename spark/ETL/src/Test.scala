

/**
 * @author Administrator
 */
object Test extends App {
          var  result ="";
          var  str ="12345678"
          println(str.indexOf("sdd"))
          println(str.substring(3))
          println(str.substring(0,4))
          var tmpar : Array[String] = "民营公司   | 50-150人   |  建筑/建材/工程".split("\\|")
          //var tmpar : Array[String] = "民营公司  |  50-150人  |  建筑/建材/工程".split("\\|")
          println("s"+tmpar.length+"ss32222222222222222")
          
          if(tmpar.length>2)
            result=result+ tmpar(0).toString().trim()+"\001" +tmpar(1).toString().trim()+"\001"+tmpar(2).toString().trim()+"\001"
          else if (tmpar.length>1)
            result=result+tmpar(0).toString()+"\001"+"\001"++tmpar(1).toString()+"\001"
          else
            result=result+tmpar(0).toString()+"\001"+"\001"+"\001"
          println(result)
          println(tmpar(0).replaceAll(" ", "")+tmpar(1).replaceAll(" ", "")+tmpar(2).replaceAll(" ", ""))
          val ok="北京海淀区上地三街嘉华大厦D栋1204 (邮编：100085)"
          //val ok="sdf211242ter"
          val regex="""([0-9]{6})""".r
          //println(regex.findFirstIn(ok).get)
          //scala.Int
          //var num:Int=0
          //val regex(num)=ok
         // println(num)
          
}