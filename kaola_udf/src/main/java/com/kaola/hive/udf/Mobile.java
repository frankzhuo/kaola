package com.kaola.hive.udf;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.hadoop.hive.ql.exec.UDF;

public class Mobile extends UDF
{
  public int evaluate(String str)
  {
    if (str == null)
      return 0;

    Pattern p = null;
    Matcher m = null;
    boolean b = false;
    p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$");
    m = p.matcher(str);
    b = m.matches();
    return ((b) ? 1 : 0);
  }
}