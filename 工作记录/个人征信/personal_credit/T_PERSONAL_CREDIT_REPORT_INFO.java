// ORM class for table 'T_PERSONAL_CREDIT_REPORT_INFO'
// WARNING: This class is AUTO-GENERATED. Modify at your own risk.
//
// Debug information:
// Generated date: Fri Aug 21 22:39:18 CST 2015
// For connector: org.apache.sqoop.manager.OracleManager
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapred.lib.db.DBWritable;
import com.cloudera.sqoop.lib.JdbcWritableBridge;
import com.cloudera.sqoop.lib.DelimiterSet;
import com.cloudera.sqoop.lib.FieldFormatter;
import com.cloudera.sqoop.lib.RecordParser;
import com.cloudera.sqoop.lib.BooleanParser;
import com.cloudera.sqoop.lib.BlobRef;
import com.cloudera.sqoop.lib.ClobRef;
import com.cloudera.sqoop.lib.LargeObjectLoader;
import com.cloudera.sqoop.lib.SqoopRecord;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class T_PERSONAL_CREDIT_REPORT_INFO extends SqoopRecord  implements DBWritable, Writable {
  private final int PROTOCOL_VERSION = 3;
  public int getClassFormatVersion() { return PROTOCOL_VERSION; }
  protected ResultSet __cur_result_set;
  private String MOBILE_NUM;
  public String get_MOBILE_NUM() {
    return MOBILE_NUM;
  }
  public void set_MOBILE_NUM(String MOBILE_NUM) {
    this.MOBILE_NUM = MOBILE_NUM;
  }
  public T_PERSONAL_CREDIT_REPORT_INFO with_MOBILE_NUM(String MOBILE_NUM) {
    this.MOBILE_NUM = MOBILE_NUM;
    return this;
  }
  private String REG_DATE;
  public String get_REG_DATE() {
    return REG_DATE;
  }
  public void set_REG_DATE(String REG_DATE) {
    this.REG_DATE = REG_DATE;
  }
  public T_PERSONAL_CREDIT_REPORT_INFO with_REG_DATE(String REG_DATE) {
    this.REG_DATE = REG_DATE;
    return this;
  }
  private java.math.BigDecimal ZW_TIME;
  public java.math.BigDecimal get_ZW_TIME() {
    return ZW_TIME;
  }
  public void set_ZW_TIME(java.math.BigDecimal ZW_TIME) {
    this.ZW_TIME = ZW_TIME;
  }
  public T_PERSONAL_CREDIT_REPORT_INFO with_ZW_TIME(java.math.BigDecimal ZW_TIME) {
    this.ZW_TIME = ZW_TIME;
    return this;
  }
  private java.math.BigDecimal CREDIT_COUNT;
  public java.math.BigDecimal get_CREDIT_COUNT() {
    return CREDIT_COUNT;
  }
  public void set_CREDIT_COUNT(java.math.BigDecimal CREDIT_COUNT) {
    this.CREDIT_COUNT = CREDIT_COUNT;
  }
  public T_PERSONAL_CREDIT_REPORT_INFO with_CREDIT_COUNT(java.math.BigDecimal CREDIT_COUNT) {
    this.CREDIT_COUNT = CREDIT_COUNT;
    return this;
  }
  private java.math.BigDecimal DEBIT_COUNT;
  public java.math.BigDecimal get_DEBIT_COUNT() {
    return DEBIT_COUNT;
  }
  public void set_DEBIT_COUNT(java.math.BigDecimal DEBIT_COUNT) {
    this.DEBIT_COUNT = DEBIT_COUNT;
  }
  public T_PERSONAL_CREDIT_REPORT_INFO with_DEBIT_COUNT(java.math.BigDecimal DEBIT_COUNT) {
    this.DEBIT_COUNT = DEBIT_COUNT;
    return this;
  }
  private java.math.BigDecimal CREDIT_BJHK;
  public java.math.BigDecimal get_CREDIT_BJHK() {
    return CREDIT_BJHK;
  }
  public void set_CREDIT_BJHK(java.math.BigDecimal CREDIT_BJHK) {
    this.CREDIT_BJHK = CREDIT_BJHK;
  }
  public T_PERSONAL_CREDIT_REPORT_INFO with_CREDIT_BJHK(java.math.BigDecimal CREDIT_BJHK) {
    this.CREDIT_BJHK = CREDIT_BJHK;
    return this;
  }
  private java.math.BigDecimal CREDIT_HKMONTH;
  public java.math.BigDecimal get_CREDIT_HKMONTH() {
    return CREDIT_HKMONTH;
  }
  public void set_CREDIT_HKMONTH(java.math.BigDecimal CREDIT_HKMONTH) {
    this.CREDIT_HKMONTH = CREDIT_HKMONTH;
  }
  public T_PERSONAL_CREDIT_REPORT_INFO with_CREDIT_HKMONTH(java.math.BigDecimal CREDIT_HKMONTH) {
    this.CREDIT_HKMONTH = CREDIT_HKMONTH;
    return this;
  }
  private java.math.BigDecimal CREDIT_YJHK;
  public java.math.BigDecimal get_CREDIT_YJHK() {
    return CREDIT_YJHK;
  }
  public void set_CREDIT_YJHK(java.math.BigDecimal CREDIT_YJHK) {
    this.CREDIT_YJHK = CREDIT_YJHK;
  }
  public T_PERSONAL_CREDIT_REPORT_INFO with_CREDIT_YJHK(java.math.BigDecimal CREDIT_YJHK) {
    this.CREDIT_YJHK = CREDIT_YJHK;
    return this;
  }
  private java.math.BigDecimal DEBIT_IN;
  public java.math.BigDecimal get_DEBIT_IN() {
    return DEBIT_IN;
  }
  public void set_DEBIT_IN(java.math.BigDecimal DEBIT_IN) {
    this.DEBIT_IN = DEBIT_IN;
  }
  public T_PERSONAL_CREDIT_REPORT_INFO with_DEBIT_IN(java.math.BigDecimal DEBIT_IN) {
    this.DEBIT_IN = DEBIT_IN;
    return this;
  }
  private java.math.BigDecimal DEBIT_OUT;
  public java.math.BigDecimal get_DEBIT_OUT() {
    return DEBIT_OUT;
  }
  public void set_DEBIT_OUT(java.math.BigDecimal DEBIT_OUT) {
    this.DEBIT_OUT = DEBIT_OUT;
  }
  public T_PERSONAL_CREDIT_REPORT_INFO with_DEBIT_OUT(java.math.BigDecimal DEBIT_OUT) {
    this.DEBIT_OUT = DEBIT_OUT;
    return this;
  }
  private java.math.BigDecimal ZZAMOUNT;
  public java.math.BigDecimal get_ZZAMOUNT() {
    return ZZAMOUNT;
  }
  public void set_ZZAMOUNT(java.math.BigDecimal ZZAMOUNT) {
    this.ZZAMOUNT = ZZAMOUNT;
  }
  public T_PERSONAL_CREDIT_REPORT_INFO with_ZZAMOUNT(java.math.BigDecimal ZZAMOUNT) {
    this.ZZAMOUNT = ZZAMOUNT;
    return this;
  }
  private java.math.BigDecimal ZZNUM;
  public java.math.BigDecimal get_ZZNUM() {
    return ZZNUM;
  }
  public void set_ZZNUM(java.math.BigDecimal ZZNUM) {
    this.ZZNUM = ZZNUM;
  }
  public T_PERSONAL_CREDIT_REPORT_INFO with_ZZNUM(java.math.BigDecimal ZZNUM) {
    this.ZZNUM = ZZNUM;
    return this;
  }
  private java.math.BigDecimal DEBIT_ZZMONTH;
  public java.math.BigDecimal get_DEBIT_ZZMONTH() {
    return DEBIT_ZZMONTH;
  }
  public void set_DEBIT_ZZMONTH(java.math.BigDecimal DEBIT_ZZMONTH) {
    this.DEBIT_ZZMONTH = DEBIT_ZZMONTH;
  }
  public T_PERSONAL_CREDIT_REPORT_INFO with_DEBIT_ZZMONTH(java.math.BigDecimal DEBIT_ZZMONTH) {
    this.DEBIT_ZZMONTH = DEBIT_ZZMONTH;
    return this;
  }
  private java.math.BigDecimal DEBIT_YJZZ;
  public java.math.BigDecimal get_DEBIT_YJZZ() {
    return DEBIT_YJZZ;
  }
  public void set_DEBIT_YJZZ(java.math.BigDecimal DEBIT_YJZZ) {
    this.DEBIT_YJZZ = DEBIT_YJZZ;
  }
  public T_PERSONAL_CREDIT_REPORT_INFO with_DEBIT_YJZZ(java.math.BigDecimal DEBIT_YJZZ) {
    this.DEBIT_YJZZ = DEBIT_YJZZ;
    return this;
  }
  private java.math.BigDecimal DEBIT_CXMONTH;
  public java.math.BigDecimal get_DEBIT_CXMONTH() {
    return DEBIT_CXMONTH;
  }
  public void set_DEBIT_CXMONTH(java.math.BigDecimal DEBIT_CXMONTH) {
    this.DEBIT_CXMONTH = DEBIT_CXMONTH;
  }
  public T_PERSONAL_CREDIT_REPORT_INFO with_DEBIT_CXMONTH(java.math.BigDecimal DEBIT_CXMONTH) {
    this.DEBIT_CXMONTH = DEBIT_CXMONTH;
    return this;
  }
  private java.math.BigDecimal CREDIT_CXMONTH;
  public java.math.BigDecimal get_CREDIT_CXMONTH() {
    return CREDIT_CXMONTH;
  }
  public void set_CREDIT_CXMONTH(java.math.BigDecimal CREDIT_CXMONTH) {
    this.CREDIT_CXMONTH = CREDIT_CXMONTH;
  }
  public T_PERSONAL_CREDIT_REPORT_INFO with_CREDIT_CXMONTH(java.math.BigDecimal CREDIT_CXMONTH) {
    this.CREDIT_CXMONTH = CREDIT_CXMONTH;
    return this;
  }
  private String ZDFQ_FLAG;
  public String get_ZDFQ_FLAG() {
    return ZDFQ_FLAG;
  }
  public void set_ZDFQ_FLAG(String ZDFQ_FLAG) {
    this.ZDFQ_FLAG = ZDFQ_FLAG;
  }
  public T_PERSONAL_CREDIT_REPORT_INFO with_ZDFQ_FLAG(String ZDFQ_FLAG) {
    this.ZDFQ_FLAG = ZDFQ_FLAG;
    return this;
  }
  private java.math.BigDecimal XD_COUNT;
  public java.math.BigDecimal get_XD_COUNT() {
    return XD_COUNT;
  }
  public void set_XD_COUNT(java.math.BigDecimal XD_COUNT) {
    this.XD_COUNT = XD_COUNT;
  }
  public T_PERSONAL_CREDIT_REPORT_INFO with_XD_COUNT(java.math.BigDecimal XD_COUNT) {
    this.XD_COUNT = XD_COUNT;
    return this;
  }
  private java.math.BigDecimal XD_SUM;
  public java.math.BigDecimal get_XD_SUM() {
    return XD_SUM;
  }
  public void set_XD_SUM(java.math.BigDecimal XD_SUM) {
    this.XD_SUM = XD_SUM;
  }
  public T_PERSONAL_CREDIT_REPORT_INFO with_XD_SUM(java.math.BigDecimal XD_SUM) {
    this.XD_SUM = XD_SUM;
    return this;
  }
  private java.math.BigDecimal XD_AVE;
  public java.math.BigDecimal get_XD_AVE() {
    return XD_AVE;
  }
  public void set_XD_AVE(java.math.BigDecimal XD_AVE) {
    this.XD_AVE = XD_AVE;
  }
  public T_PERSONAL_CREDIT_REPORT_INFO with_XD_AVE(java.math.BigDecimal XD_AVE) {
    this.XD_AVE = XD_AVE;
    return this;
  }
  private java.math.BigDecimal XD_OUT_DAY_SUM;
  public java.math.BigDecimal get_XD_OUT_DAY_SUM() {
    return XD_OUT_DAY_SUM;
  }
  public void set_XD_OUT_DAY_SUM(java.math.BigDecimal XD_OUT_DAY_SUM) {
    this.XD_OUT_DAY_SUM = XD_OUT_DAY_SUM;
  }
  public T_PERSONAL_CREDIT_REPORT_INFO with_XD_OUT_DAY_SUM(java.math.BigDecimal XD_OUT_DAY_SUM) {
    this.XD_OUT_DAY_SUM = XD_OUT_DAY_SUM;
    return this;
  }
  private java.math.BigDecimal XD_NOSETTLE_SUM;
  public java.math.BigDecimal get_XD_NOSETTLE_SUM() {
    return XD_NOSETTLE_SUM;
  }
  public void set_XD_NOSETTLE_SUM(java.math.BigDecimal XD_NOSETTLE_SUM) {
    this.XD_NOSETTLE_SUM = XD_NOSETTLE_SUM;
  }
  public T_PERSONAL_CREDIT_REPORT_INFO with_XD_NOSETTLE_SUM(java.math.BigDecimal XD_NOSETTLE_SUM) {
    this.XD_NOSETTLE_SUM = XD_NOSETTLE_SUM;
    return this;
  }
  private java.math.BigDecimal XD_YQ_CNT;
  public java.math.BigDecimal get_XD_YQ_CNT() {
    return XD_YQ_CNT;
  }
  public void set_XD_YQ_CNT(java.math.BigDecimal XD_YQ_CNT) {
    this.XD_YQ_CNT = XD_YQ_CNT;
  }
  public T_PERSONAL_CREDIT_REPORT_INFO with_XD_YQ_CNT(java.math.BigDecimal XD_YQ_CNT) {
    this.XD_YQ_CNT = XD_YQ_CNT;
    return this;
  }
  private String ADDR_TOP1;
  public String get_ADDR_TOP1() {
    return ADDR_TOP1;
  }
  public void set_ADDR_TOP1(String ADDR_TOP1) {
    this.ADDR_TOP1 = ADDR_TOP1;
  }
  public T_PERSONAL_CREDIT_REPORT_INFO with_ADDR_TOP1(String ADDR_TOP1) {
    this.ADDR_TOP1 = ADDR_TOP1;
    return this;
  }
  private java.math.BigDecimal JY_CNT_TOP1;
  public java.math.BigDecimal get_JY_CNT_TOP1() {
    return JY_CNT_TOP1;
  }
  public void set_JY_CNT_TOP1(java.math.BigDecimal JY_CNT_TOP1) {
    this.JY_CNT_TOP1 = JY_CNT_TOP1;
  }
  public T_PERSONAL_CREDIT_REPORT_INFO with_JY_CNT_TOP1(java.math.BigDecimal JY_CNT_TOP1) {
    this.JY_CNT_TOP1 = JY_CNT_TOP1;
    return this;
  }
  private String ADDR_TOP2;
  public String get_ADDR_TOP2() {
    return ADDR_TOP2;
  }
  public void set_ADDR_TOP2(String ADDR_TOP2) {
    this.ADDR_TOP2 = ADDR_TOP2;
  }
  public T_PERSONAL_CREDIT_REPORT_INFO with_ADDR_TOP2(String ADDR_TOP2) {
    this.ADDR_TOP2 = ADDR_TOP2;
    return this;
  }
  private java.math.BigDecimal JY_CNT_TOP2;
  public java.math.BigDecimal get_JY_CNT_TOP2() {
    return JY_CNT_TOP2;
  }
  public void set_JY_CNT_TOP2(java.math.BigDecimal JY_CNT_TOP2) {
    this.JY_CNT_TOP2 = JY_CNT_TOP2;
  }
  public T_PERSONAL_CREDIT_REPORT_INFO with_JY_CNT_TOP2(java.math.BigDecimal JY_CNT_TOP2) {
    this.JY_CNT_TOP2 = JY_CNT_TOP2;
    return this;
  }
  private String ADDR_TOP3;
  public String get_ADDR_TOP3() {
    return ADDR_TOP3;
  }
  public void set_ADDR_TOP3(String ADDR_TOP3) {
    this.ADDR_TOP3 = ADDR_TOP3;
  }
  public T_PERSONAL_CREDIT_REPORT_INFO with_ADDR_TOP3(String ADDR_TOP3) {
    this.ADDR_TOP3 = ADDR_TOP3;
    return this;
  }
  private java.math.BigDecimal JY_CNT_TOP3;
  public java.math.BigDecimal get_JY_CNT_TOP3() {
    return JY_CNT_TOP3;
  }
  public void set_JY_CNT_TOP3(java.math.BigDecimal JY_CNT_TOP3) {
    this.JY_CNT_TOP3 = JY_CNT_TOP3;
  }
  public T_PERSONAL_CREDIT_REPORT_INFO with_JY_CNT_TOP3(java.math.BigDecimal JY_CNT_TOP3) {
    this.JY_CNT_TOP3 = JY_CNT_TOP3;
    return this;
  }
  private java.math.BigDecimal TOTAL_AM;
  public java.math.BigDecimal get_TOTAL_AM() {
    return TOTAL_AM;
  }
  public void set_TOTAL_AM(java.math.BigDecimal TOTAL_AM) {
    this.TOTAL_AM = TOTAL_AM;
  }
  public T_PERSONAL_CREDIT_REPORT_INFO with_TOTAL_AM(java.math.BigDecimal TOTAL_AM) {
    this.TOTAL_AM = TOTAL_AM;
    return this;
  }
  private java.math.BigDecimal CNT;
  public java.math.BigDecimal get_CNT() {
    return CNT;
  }
  public void set_CNT(java.math.BigDecimal CNT) {
    this.CNT = CNT;
  }
  public T_PERSONAL_CREDIT_REPORT_INFO with_CNT(java.math.BigDecimal CNT) {
    this.CNT = CNT;
    return this;
  }
  private java.math.BigDecimal BJ_AM;
  public java.math.BigDecimal get_BJ_AM() {
    return BJ_AM;
  }
  public void set_BJ_AM(java.math.BigDecimal BJ_AM) {
    this.BJ_AM = BJ_AM;
  }
  public T_PERSONAL_CREDIT_REPORT_INFO with_BJ_AM(java.math.BigDecimal BJ_AM) {
    this.BJ_AM = BJ_AM;
    return this;
  }
  private java.math.BigDecimal ELE_TOTAL_AM;
  public java.math.BigDecimal get_ELE_TOTAL_AM() {
    return ELE_TOTAL_AM;
  }
  public void set_ELE_TOTAL_AM(java.math.BigDecimal ELE_TOTAL_AM) {
    this.ELE_TOTAL_AM = ELE_TOTAL_AM;
  }
  public T_PERSONAL_CREDIT_REPORT_INFO with_ELE_TOTAL_AM(java.math.BigDecimal ELE_TOTAL_AM) {
    this.ELE_TOTAL_AM = ELE_TOTAL_AM;
    return this;
  }
  private java.math.BigDecimal ELE_CNT;
  public java.math.BigDecimal get_ELE_CNT() {
    return ELE_CNT;
  }
  public void set_ELE_CNT(java.math.BigDecimal ELE_CNT) {
    this.ELE_CNT = ELE_CNT;
  }
  public T_PERSONAL_CREDIT_REPORT_INFO with_ELE_CNT(java.math.BigDecimal ELE_CNT) {
    this.ELE_CNT = ELE_CNT;
    return this;
  }
  private java.math.BigDecimal ELE_BJ_AM;
  public java.math.BigDecimal get_ELE_BJ_AM() {
    return ELE_BJ_AM;
  }
  public void set_ELE_BJ_AM(java.math.BigDecimal ELE_BJ_AM) {
    this.ELE_BJ_AM = ELE_BJ_AM;
  }
  public T_PERSONAL_CREDIT_REPORT_INFO with_ELE_BJ_AM(java.math.BigDecimal ELE_BJ_AM) {
    this.ELE_BJ_AM = ELE_BJ_AM;
    return this;
  }
  private java.math.BigDecimal W_TOTAL_AM;
  public java.math.BigDecimal get_W_TOTAL_AM() {
    return W_TOTAL_AM;
  }
  public void set_W_TOTAL_AM(java.math.BigDecimal W_TOTAL_AM) {
    this.W_TOTAL_AM = W_TOTAL_AM;
  }
  public T_PERSONAL_CREDIT_REPORT_INFO with_W_TOTAL_AM(java.math.BigDecimal W_TOTAL_AM) {
    this.W_TOTAL_AM = W_TOTAL_AM;
    return this;
  }
  private java.math.BigDecimal W_CNT;
  public java.math.BigDecimal get_W_CNT() {
    return W_CNT;
  }
  public void set_W_CNT(java.math.BigDecimal W_CNT) {
    this.W_CNT = W_CNT;
  }
  public T_PERSONAL_CREDIT_REPORT_INFO with_W_CNT(java.math.BigDecimal W_CNT) {
    this.W_CNT = W_CNT;
    return this;
  }
  private java.math.BigDecimal W_BJ_AM;
  public java.math.BigDecimal get_W_BJ_AM() {
    return W_BJ_AM;
  }
  public void set_W_BJ_AM(java.math.BigDecimal W_BJ_AM) {
    this.W_BJ_AM = W_BJ_AM;
  }
  public T_PERSONAL_CREDIT_REPORT_INFO with_W_BJ_AM(java.math.BigDecimal W_BJ_AM) {
    this.W_BJ_AM = W_BJ_AM;
    return this;
  }
  private java.math.BigDecimal GAS_TOTAL_AM;
  public java.math.BigDecimal get_GAS_TOTAL_AM() {
    return GAS_TOTAL_AM;
  }
  public void set_GAS_TOTAL_AM(java.math.BigDecimal GAS_TOTAL_AM) {
    this.GAS_TOTAL_AM = GAS_TOTAL_AM;
  }
  public T_PERSONAL_CREDIT_REPORT_INFO with_GAS_TOTAL_AM(java.math.BigDecimal GAS_TOTAL_AM) {
    this.GAS_TOTAL_AM = GAS_TOTAL_AM;
    return this;
  }
  private java.math.BigDecimal GAS_CNT;
  public java.math.BigDecimal get_GAS_CNT() {
    return GAS_CNT;
  }
  public void set_GAS_CNT(java.math.BigDecimal GAS_CNT) {
    this.GAS_CNT = GAS_CNT;
  }
  public T_PERSONAL_CREDIT_REPORT_INFO with_GAS_CNT(java.math.BigDecimal GAS_CNT) {
    this.GAS_CNT = GAS_CNT;
    return this;
  }
  private java.math.BigDecimal GAS_BJ_AM;
  public java.math.BigDecimal get_GAS_BJ_AM() {
    return GAS_BJ_AM;
  }
  public void set_GAS_BJ_AM(java.math.BigDecimal GAS_BJ_AM) {
    this.GAS_BJ_AM = GAS_BJ_AM;
  }
  public T_PERSONAL_CREDIT_REPORT_INFO with_GAS_BJ_AM(java.math.BigDecimal GAS_BJ_AM) {
    this.GAS_BJ_AM = GAS_BJ_AM;
    return this;
  }
  private java.math.BigDecimal COMM_TOTAL_AM;
  public java.math.BigDecimal get_COMM_TOTAL_AM() {
    return COMM_TOTAL_AM;
  }
  public void set_COMM_TOTAL_AM(java.math.BigDecimal COMM_TOTAL_AM) {
    this.COMM_TOTAL_AM = COMM_TOTAL_AM;
  }
  public T_PERSONAL_CREDIT_REPORT_INFO with_COMM_TOTAL_AM(java.math.BigDecimal COMM_TOTAL_AM) {
    this.COMM_TOTAL_AM = COMM_TOTAL_AM;
    return this;
  }
  private java.math.BigDecimal COMM_CNT;
  public java.math.BigDecimal get_COMM_CNT() {
    return COMM_CNT;
  }
  public void set_COMM_CNT(java.math.BigDecimal COMM_CNT) {
    this.COMM_CNT = COMM_CNT;
  }
  public T_PERSONAL_CREDIT_REPORT_INFO with_COMM_CNT(java.math.BigDecimal COMM_CNT) {
    this.COMM_CNT = COMM_CNT;
    return this;
  }
  private java.math.BigDecimal COMM_BJ_AM;
  public java.math.BigDecimal get_COMM_BJ_AM() {
    return COMM_BJ_AM;
  }
  public void set_COMM_BJ_AM(java.math.BigDecimal COMM_BJ_AM) {
    this.COMM_BJ_AM = COMM_BJ_AM;
  }
  public T_PERSONAL_CREDIT_REPORT_INFO with_COMM_BJ_AM(java.math.BigDecimal COMM_BJ_AM) {
    this.COMM_BJ_AM = COMM_BJ_AM;
    return this;
  }
  private java.math.BigDecimal TV_TOTAL_AM;
  public java.math.BigDecimal get_TV_TOTAL_AM() {
    return TV_TOTAL_AM;
  }
  public void set_TV_TOTAL_AM(java.math.BigDecimal TV_TOTAL_AM) {
    this.TV_TOTAL_AM = TV_TOTAL_AM;
  }
  public T_PERSONAL_CREDIT_REPORT_INFO with_TV_TOTAL_AM(java.math.BigDecimal TV_TOTAL_AM) {
    this.TV_TOTAL_AM = TV_TOTAL_AM;
    return this;
  }
  private java.math.BigDecimal TV_CNT;
  public java.math.BigDecimal get_TV_CNT() {
    return TV_CNT;
  }
  public void set_TV_CNT(java.math.BigDecimal TV_CNT) {
    this.TV_CNT = TV_CNT;
  }
  public T_PERSONAL_CREDIT_REPORT_INFO with_TV_CNT(java.math.BigDecimal TV_CNT) {
    this.TV_CNT = TV_CNT;
    return this;
  }
  private java.math.BigDecimal TV_BJ_AM;
  public java.math.BigDecimal get_TV_BJ_AM() {
    return TV_BJ_AM;
  }
  public void set_TV_BJ_AM(java.math.BigDecimal TV_BJ_AM) {
    this.TV_BJ_AM = TV_BJ_AM;
  }
  public T_PERSONAL_CREDIT_REPORT_INFO with_TV_BJ_AM(java.math.BigDecimal TV_BJ_AM) {
    this.TV_BJ_AM = TV_BJ_AM;
    return this;
  }
  private String STARTDATE;
  public String get_STARTDATE() {
    return STARTDATE;
  }
  public void set_STARTDATE(String STARTDATE) {
    this.STARTDATE = STARTDATE;
  }
  public T_PERSONAL_CREDIT_REPORT_INFO with_STARTDATE(String STARTDATE) {
    this.STARTDATE = STARTDATE;
    return this;
  }
  private String ENDDATE;
  public String get_ENDDATE() {
    return ENDDATE;
  }
  public void set_ENDDATE(String ENDDATE) {
    this.ENDDATE = ENDDATE;
  }
  public T_PERSONAL_CREDIT_REPORT_INFO with_ENDDATE(String ENDDATE) {
    this.ENDDATE = ENDDATE;
    return this;
  }
  private String MONTH_NUM;
  public String get_MONTH_NUM() {
    return MONTH_NUM;
  }
  public void set_MONTH_NUM(String MONTH_NUM) {
    this.MONTH_NUM = MONTH_NUM;
  }
  public T_PERSONAL_CREDIT_REPORT_INFO with_MONTH_NUM(String MONTH_NUM) {
    this.MONTH_NUM = MONTH_NUM;
    return this;
  }
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof T_PERSONAL_CREDIT_REPORT_INFO)) {
      return false;
    }
    T_PERSONAL_CREDIT_REPORT_INFO that = (T_PERSONAL_CREDIT_REPORT_INFO) o;
    boolean equal = true;
    equal = equal && (this.MOBILE_NUM == null ? that.MOBILE_NUM == null : this.MOBILE_NUM.equals(that.MOBILE_NUM));
    equal = equal && (this.REG_DATE == null ? that.REG_DATE == null : this.REG_DATE.equals(that.REG_DATE));
    equal = equal && (this.ZW_TIME == null ? that.ZW_TIME == null : this.ZW_TIME.equals(that.ZW_TIME));
    equal = equal && (this.CREDIT_COUNT == null ? that.CREDIT_COUNT == null : this.CREDIT_COUNT.equals(that.CREDIT_COUNT));
    equal = equal && (this.DEBIT_COUNT == null ? that.DEBIT_COUNT == null : this.DEBIT_COUNT.equals(that.DEBIT_COUNT));
    equal = equal && (this.CREDIT_BJHK == null ? that.CREDIT_BJHK == null : this.CREDIT_BJHK.equals(that.CREDIT_BJHK));
    equal = equal && (this.CREDIT_HKMONTH == null ? that.CREDIT_HKMONTH == null : this.CREDIT_HKMONTH.equals(that.CREDIT_HKMONTH));
    equal = equal && (this.CREDIT_YJHK == null ? that.CREDIT_YJHK == null : this.CREDIT_YJHK.equals(that.CREDIT_YJHK));
    equal = equal && (this.DEBIT_IN == null ? that.DEBIT_IN == null : this.DEBIT_IN.equals(that.DEBIT_IN));
    equal = equal && (this.DEBIT_OUT == null ? that.DEBIT_OUT == null : this.DEBIT_OUT.equals(that.DEBIT_OUT));
    equal = equal && (this.ZZAMOUNT == null ? that.ZZAMOUNT == null : this.ZZAMOUNT.equals(that.ZZAMOUNT));
    equal = equal && (this.ZZNUM == null ? that.ZZNUM == null : this.ZZNUM.equals(that.ZZNUM));
    equal = equal && (this.DEBIT_ZZMONTH == null ? that.DEBIT_ZZMONTH == null : this.DEBIT_ZZMONTH.equals(that.DEBIT_ZZMONTH));
    equal = equal && (this.DEBIT_YJZZ == null ? that.DEBIT_YJZZ == null : this.DEBIT_YJZZ.equals(that.DEBIT_YJZZ));
    equal = equal && (this.DEBIT_CXMONTH == null ? that.DEBIT_CXMONTH == null : this.DEBIT_CXMONTH.equals(that.DEBIT_CXMONTH));
    equal = equal && (this.CREDIT_CXMONTH == null ? that.CREDIT_CXMONTH == null : this.CREDIT_CXMONTH.equals(that.CREDIT_CXMONTH));
    equal = equal && (this.ZDFQ_FLAG == null ? that.ZDFQ_FLAG == null : this.ZDFQ_FLAG.equals(that.ZDFQ_FLAG));
    equal = equal && (this.XD_COUNT == null ? that.XD_COUNT == null : this.XD_COUNT.equals(that.XD_COUNT));
    equal = equal && (this.XD_SUM == null ? that.XD_SUM == null : this.XD_SUM.equals(that.XD_SUM));
    equal = equal && (this.XD_AVE == null ? that.XD_AVE == null : this.XD_AVE.equals(that.XD_AVE));
    equal = equal && (this.XD_OUT_DAY_SUM == null ? that.XD_OUT_DAY_SUM == null : this.XD_OUT_DAY_SUM.equals(that.XD_OUT_DAY_SUM));
    equal = equal && (this.XD_NOSETTLE_SUM == null ? that.XD_NOSETTLE_SUM == null : this.XD_NOSETTLE_SUM.equals(that.XD_NOSETTLE_SUM));
    equal = equal && (this.XD_YQ_CNT == null ? that.XD_YQ_CNT == null : this.XD_YQ_CNT.equals(that.XD_YQ_CNT));
    equal = equal && (this.ADDR_TOP1 == null ? that.ADDR_TOP1 == null : this.ADDR_TOP1.equals(that.ADDR_TOP1));
    equal = equal && (this.JY_CNT_TOP1 == null ? that.JY_CNT_TOP1 == null : this.JY_CNT_TOP1.equals(that.JY_CNT_TOP1));
    equal = equal && (this.ADDR_TOP2 == null ? that.ADDR_TOP2 == null : this.ADDR_TOP2.equals(that.ADDR_TOP2));
    equal = equal && (this.JY_CNT_TOP2 == null ? that.JY_CNT_TOP2 == null : this.JY_CNT_TOP2.equals(that.JY_CNT_TOP2));
    equal = equal && (this.ADDR_TOP3 == null ? that.ADDR_TOP3 == null : this.ADDR_TOP3.equals(that.ADDR_TOP3));
    equal = equal && (this.JY_CNT_TOP3 == null ? that.JY_CNT_TOP3 == null : this.JY_CNT_TOP3.equals(that.JY_CNT_TOP3));
    equal = equal && (this.TOTAL_AM == null ? that.TOTAL_AM == null : this.TOTAL_AM.equals(that.TOTAL_AM));
    equal = equal && (this.CNT == null ? that.CNT == null : this.CNT.equals(that.CNT));
    equal = equal && (this.BJ_AM == null ? that.BJ_AM == null : this.BJ_AM.equals(that.BJ_AM));
    equal = equal && (this.ELE_TOTAL_AM == null ? that.ELE_TOTAL_AM == null : this.ELE_TOTAL_AM.equals(that.ELE_TOTAL_AM));
    equal = equal && (this.ELE_CNT == null ? that.ELE_CNT == null : this.ELE_CNT.equals(that.ELE_CNT));
    equal = equal && (this.ELE_BJ_AM == null ? that.ELE_BJ_AM == null : this.ELE_BJ_AM.equals(that.ELE_BJ_AM));
    equal = equal && (this.W_TOTAL_AM == null ? that.W_TOTAL_AM == null : this.W_TOTAL_AM.equals(that.W_TOTAL_AM));
    equal = equal && (this.W_CNT == null ? that.W_CNT == null : this.W_CNT.equals(that.W_CNT));
    equal = equal && (this.W_BJ_AM == null ? that.W_BJ_AM == null : this.W_BJ_AM.equals(that.W_BJ_AM));
    equal = equal && (this.GAS_TOTAL_AM == null ? that.GAS_TOTAL_AM == null : this.GAS_TOTAL_AM.equals(that.GAS_TOTAL_AM));
    equal = equal && (this.GAS_CNT == null ? that.GAS_CNT == null : this.GAS_CNT.equals(that.GAS_CNT));
    equal = equal && (this.GAS_BJ_AM == null ? that.GAS_BJ_AM == null : this.GAS_BJ_AM.equals(that.GAS_BJ_AM));
    equal = equal && (this.COMM_TOTAL_AM == null ? that.COMM_TOTAL_AM == null : this.COMM_TOTAL_AM.equals(that.COMM_TOTAL_AM));
    equal = equal && (this.COMM_CNT == null ? that.COMM_CNT == null : this.COMM_CNT.equals(that.COMM_CNT));
    equal = equal && (this.COMM_BJ_AM == null ? that.COMM_BJ_AM == null : this.COMM_BJ_AM.equals(that.COMM_BJ_AM));
    equal = equal && (this.TV_TOTAL_AM == null ? that.TV_TOTAL_AM == null : this.TV_TOTAL_AM.equals(that.TV_TOTAL_AM));
    equal = equal && (this.TV_CNT == null ? that.TV_CNT == null : this.TV_CNT.equals(that.TV_CNT));
    equal = equal && (this.TV_BJ_AM == null ? that.TV_BJ_AM == null : this.TV_BJ_AM.equals(that.TV_BJ_AM));
    equal = equal && (this.STARTDATE == null ? that.STARTDATE == null : this.STARTDATE.equals(that.STARTDATE));
    equal = equal && (this.ENDDATE == null ? that.ENDDATE == null : this.ENDDATE.equals(that.ENDDATE));
    equal = equal && (this.MONTH_NUM == null ? that.MONTH_NUM == null : this.MONTH_NUM.equals(that.MONTH_NUM));
    return equal;
  }
  public boolean equals0(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof T_PERSONAL_CREDIT_REPORT_INFO)) {
      return false;
    }
    T_PERSONAL_CREDIT_REPORT_INFO that = (T_PERSONAL_CREDIT_REPORT_INFO) o;
    boolean equal = true;
    equal = equal && (this.MOBILE_NUM == null ? that.MOBILE_NUM == null : this.MOBILE_NUM.equals(that.MOBILE_NUM));
    equal = equal && (this.REG_DATE == null ? that.REG_DATE == null : this.REG_DATE.equals(that.REG_DATE));
    equal = equal && (this.ZW_TIME == null ? that.ZW_TIME == null : this.ZW_TIME.equals(that.ZW_TIME));
    equal = equal && (this.CREDIT_COUNT == null ? that.CREDIT_COUNT == null : this.CREDIT_COUNT.equals(that.CREDIT_COUNT));
    equal = equal && (this.DEBIT_COUNT == null ? that.DEBIT_COUNT == null : this.DEBIT_COUNT.equals(that.DEBIT_COUNT));
    equal = equal && (this.CREDIT_BJHK == null ? that.CREDIT_BJHK == null : this.CREDIT_BJHK.equals(that.CREDIT_BJHK));
    equal = equal && (this.CREDIT_HKMONTH == null ? that.CREDIT_HKMONTH == null : this.CREDIT_HKMONTH.equals(that.CREDIT_HKMONTH));
    equal = equal && (this.CREDIT_YJHK == null ? that.CREDIT_YJHK == null : this.CREDIT_YJHK.equals(that.CREDIT_YJHK));
    equal = equal && (this.DEBIT_IN == null ? that.DEBIT_IN == null : this.DEBIT_IN.equals(that.DEBIT_IN));
    equal = equal && (this.DEBIT_OUT == null ? that.DEBIT_OUT == null : this.DEBIT_OUT.equals(that.DEBIT_OUT));
    equal = equal && (this.ZZAMOUNT == null ? that.ZZAMOUNT == null : this.ZZAMOUNT.equals(that.ZZAMOUNT));
    equal = equal && (this.ZZNUM == null ? that.ZZNUM == null : this.ZZNUM.equals(that.ZZNUM));
    equal = equal && (this.DEBIT_ZZMONTH == null ? that.DEBIT_ZZMONTH == null : this.DEBIT_ZZMONTH.equals(that.DEBIT_ZZMONTH));
    equal = equal && (this.DEBIT_YJZZ == null ? that.DEBIT_YJZZ == null : this.DEBIT_YJZZ.equals(that.DEBIT_YJZZ));
    equal = equal && (this.DEBIT_CXMONTH == null ? that.DEBIT_CXMONTH == null : this.DEBIT_CXMONTH.equals(that.DEBIT_CXMONTH));
    equal = equal && (this.CREDIT_CXMONTH == null ? that.CREDIT_CXMONTH == null : this.CREDIT_CXMONTH.equals(that.CREDIT_CXMONTH));
    equal = equal && (this.ZDFQ_FLAG == null ? that.ZDFQ_FLAG == null : this.ZDFQ_FLAG.equals(that.ZDFQ_FLAG));
    equal = equal && (this.XD_COUNT == null ? that.XD_COUNT == null : this.XD_COUNT.equals(that.XD_COUNT));
    equal = equal && (this.XD_SUM == null ? that.XD_SUM == null : this.XD_SUM.equals(that.XD_SUM));
    equal = equal && (this.XD_AVE == null ? that.XD_AVE == null : this.XD_AVE.equals(that.XD_AVE));
    equal = equal && (this.XD_OUT_DAY_SUM == null ? that.XD_OUT_DAY_SUM == null : this.XD_OUT_DAY_SUM.equals(that.XD_OUT_DAY_SUM));
    equal = equal && (this.XD_NOSETTLE_SUM == null ? that.XD_NOSETTLE_SUM == null : this.XD_NOSETTLE_SUM.equals(that.XD_NOSETTLE_SUM));
    equal = equal && (this.XD_YQ_CNT == null ? that.XD_YQ_CNT == null : this.XD_YQ_CNT.equals(that.XD_YQ_CNT));
    equal = equal && (this.ADDR_TOP1 == null ? that.ADDR_TOP1 == null : this.ADDR_TOP1.equals(that.ADDR_TOP1));
    equal = equal && (this.JY_CNT_TOP1 == null ? that.JY_CNT_TOP1 == null : this.JY_CNT_TOP1.equals(that.JY_CNT_TOP1));
    equal = equal && (this.ADDR_TOP2 == null ? that.ADDR_TOP2 == null : this.ADDR_TOP2.equals(that.ADDR_TOP2));
    equal = equal && (this.JY_CNT_TOP2 == null ? that.JY_CNT_TOP2 == null : this.JY_CNT_TOP2.equals(that.JY_CNT_TOP2));
    equal = equal && (this.ADDR_TOP3 == null ? that.ADDR_TOP3 == null : this.ADDR_TOP3.equals(that.ADDR_TOP3));
    equal = equal && (this.JY_CNT_TOP3 == null ? that.JY_CNT_TOP3 == null : this.JY_CNT_TOP3.equals(that.JY_CNT_TOP3));
    equal = equal && (this.TOTAL_AM == null ? that.TOTAL_AM == null : this.TOTAL_AM.equals(that.TOTAL_AM));
    equal = equal && (this.CNT == null ? that.CNT == null : this.CNT.equals(that.CNT));
    equal = equal && (this.BJ_AM == null ? that.BJ_AM == null : this.BJ_AM.equals(that.BJ_AM));
    equal = equal && (this.ELE_TOTAL_AM == null ? that.ELE_TOTAL_AM == null : this.ELE_TOTAL_AM.equals(that.ELE_TOTAL_AM));
    equal = equal && (this.ELE_CNT == null ? that.ELE_CNT == null : this.ELE_CNT.equals(that.ELE_CNT));
    equal = equal && (this.ELE_BJ_AM == null ? that.ELE_BJ_AM == null : this.ELE_BJ_AM.equals(that.ELE_BJ_AM));
    equal = equal && (this.W_TOTAL_AM == null ? that.W_TOTAL_AM == null : this.W_TOTAL_AM.equals(that.W_TOTAL_AM));
    equal = equal && (this.W_CNT == null ? that.W_CNT == null : this.W_CNT.equals(that.W_CNT));
    equal = equal && (this.W_BJ_AM == null ? that.W_BJ_AM == null : this.W_BJ_AM.equals(that.W_BJ_AM));
    equal = equal && (this.GAS_TOTAL_AM == null ? that.GAS_TOTAL_AM == null : this.GAS_TOTAL_AM.equals(that.GAS_TOTAL_AM));
    equal = equal && (this.GAS_CNT == null ? that.GAS_CNT == null : this.GAS_CNT.equals(that.GAS_CNT));
    equal = equal && (this.GAS_BJ_AM == null ? that.GAS_BJ_AM == null : this.GAS_BJ_AM.equals(that.GAS_BJ_AM));
    equal = equal && (this.COMM_TOTAL_AM == null ? that.COMM_TOTAL_AM == null : this.COMM_TOTAL_AM.equals(that.COMM_TOTAL_AM));
    equal = equal && (this.COMM_CNT == null ? that.COMM_CNT == null : this.COMM_CNT.equals(that.COMM_CNT));
    equal = equal && (this.COMM_BJ_AM == null ? that.COMM_BJ_AM == null : this.COMM_BJ_AM.equals(that.COMM_BJ_AM));
    equal = equal && (this.TV_TOTAL_AM == null ? that.TV_TOTAL_AM == null : this.TV_TOTAL_AM.equals(that.TV_TOTAL_AM));
    equal = equal && (this.TV_CNT == null ? that.TV_CNT == null : this.TV_CNT.equals(that.TV_CNT));
    equal = equal && (this.TV_BJ_AM == null ? that.TV_BJ_AM == null : this.TV_BJ_AM.equals(that.TV_BJ_AM));
    equal = equal && (this.STARTDATE == null ? that.STARTDATE == null : this.STARTDATE.equals(that.STARTDATE));
    equal = equal && (this.ENDDATE == null ? that.ENDDATE == null : this.ENDDATE.equals(that.ENDDATE));
    equal = equal && (this.MONTH_NUM == null ? that.MONTH_NUM == null : this.MONTH_NUM.equals(that.MONTH_NUM));
    return equal;
  }
  public void readFields(ResultSet __dbResults) throws SQLException {
    this.__cur_result_set = __dbResults;
    this.MOBILE_NUM = JdbcWritableBridge.readString(1, __dbResults);
    this.REG_DATE = JdbcWritableBridge.readString(2, __dbResults);
    this.ZW_TIME = JdbcWritableBridge.readBigDecimal(3, __dbResults);
    this.CREDIT_COUNT = JdbcWritableBridge.readBigDecimal(4, __dbResults);
    this.DEBIT_COUNT = JdbcWritableBridge.readBigDecimal(5, __dbResults);
    this.CREDIT_BJHK = JdbcWritableBridge.readBigDecimal(6, __dbResults);
    this.CREDIT_HKMONTH = JdbcWritableBridge.readBigDecimal(7, __dbResults);
    this.CREDIT_YJHK = JdbcWritableBridge.readBigDecimal(8, __dbResults);
    this.DEBIT_IN = JdbcWritableBridge.readBigDecimal(9, __dbResults);
    this.DEBIT_OUT = JdbcWritableBridge.readBigDecimal(10, __dbResults);
    this.ZZAMOUNT = JdbcWritableBridge.readBigDecimal(11, __dbResults);
    this.ZZNUM = JdbcWritableBridge.readBigDecimal(12, __dbResults);
    this.DEBIT_ZZMONTH = JdbcWritableBridge.readBigDecimal(13, __dbResults);
    this.DEBIT_YJZZ = JdbcWritableBridge.readBigDecimal(14, __dbResults);
    this.DEBIT_CXMONTH = JdbcWritableBridge.readBigDecimal(15, __dbResults);
    this.CREDIT_CXMONTH = JdbcWritableBridge.readBigDecimal(16, __dbResults);
    this.ZDFQ_FLAG = JdbcWritableBridge.readString(17, __dbResults);
    this.XD_COUNT = JdbcWritableBridge.readBigDecimal(18, __dbResults);
    this.XD_SUM = JdbcWritableBridge.readBigDecimal(19, __dbResults);
    this.XD_AVE = JdbcWritableBridge.readBigDecimal(20, __dbResults);
    this.XD_OUT_DAY_SUM = JdbcWritableBridge.readBigDecimal(21, __dbResults);
    this.XD_NOSETTLE_SUM = JdbcWritableBridge.readBigDecimal(22, __dbResults);
    this.XD_YQ_CNT = JdbcWritableBridge.readBigDecimal(23, __dbResults);
    this.ADDR_TOP1 = JdbcWritableBridge.readString(24, __dbResults);
    this.JY_CNT_TOP1 = JdbcWritableBridge.readBigDecimal(25, __dbResults);
    this.ADDR_TOP2 = JdbcWritableBridge.readString(26, __dbResults);
    this.JY_CNT_TOP2 = JdbcWritableBridge.readBigDecimal(27, __dbResults);
    this.ADDR_TOP3 = JdbcWritableBridge.readString(28, __dbResults);
    this.JY_CNT_TOP3 = JdbcWritableBridge.readBigDecimal(29, __dbResults);
    this.TOTAL_AM = JdbcWritableBridge.readBigDecimal(30, __dbResults);
    this.CNT = JdbcWritableBridge.readBigDecimal(31, __dbResults);
    this.BJ_AM = JdbcWritableBridge.readBigDecimal(32, __dbResults);
    this.ELE_TOTAL_AM = JdbcWritableBridge.readBigDecimal(33, __dbResults);
    this.ELE_CNT = JdbcWritableBridge.readBigDecimal(34, __dbResults);
    this.ELE_BJ_AM = JdbcWritableBridge.readBigDecimal(35, __dbResults);
    this.W_TOTAL_AM = JdbcWritableBridge.readBigDecimal(36, __dbResults);
    this.W_CNT = JdbcWritableBridge.readBigDecimal(37, __dbResults);
    this.W_BJ_AM = JdbcWritableBridge.readBigDecimal(38, __dbResults);
    this.GAS_TOTAL_AM = JdbcWritableBridge.readBigDecimal(39, __dbResults);
    this.GAS_CNT = JdbcWritableBridge.readBigDecimal(40, __dbResults);
    this.GAS_BJ_AM = JdbcWritableBridge.readBigDecimal(41, __dbResults);
    this.COMM_TOTAL_AM = JdbcWritableBridge.readBigDecimal(42, __dbResults);
    this.COMM_CNT = JdbcWritableBridge.readBigDecimal(43, __dbResults);
    this.COMM_BJ_AM = JdbcWritableBridge.readBigDecimal(44, __dbResults);
    this.TV_TOTAL_AM = JdbcWritableBridge.readBigDecimal(45, __dbResults);
    this.TV_CNT = JdbcWritableBridge.readBigDecimal(46, __dbResults);
    this.TV_BJ_AM = JdbcWritableBridge.readBigDecimal(47, __dbResults);
    this.STARTDATE = JdbcWritableBridge.readString(48, __dbResults);
    this.ENDDATE = JdbcWritableBridge.readString(49, __dbResults);
    this.MONTH_NUM = JdbcWritableBridge.readString(50, __dbResults);
  }
  public void readFields0(ResultSet __dbResults) throws SQLException {
    this.MOBILE_NUM = JdbcWritableBridge.readString(1, __dbResults);
    this.REG_DATE = JdbcWritableBridge.readString(2, __dbResults);
    this.ZW_TIME = JdbcWritableBridge.readBigDecimal(3, __dbResults);
    this.CREDIT_COUNT = JdbcWritableBridge.readBigDecimal(4, __dbResults);
    this.DEBIT_COUNT = JdbcWritableBridge.readBigDecimal(5, __dbResults);
    this.CREDIT_BJHK = JdbcWritableBridge.readBigDecimal(6, __dbResults);
    this.CREDIT_HKMONTH = JdbcWritableBridge.readBigDecimal(7, __dbResults);
    this.CREDIT_YJHK = JdbcWritableBridge.readBigDecimal(8, __dbResults);
    this.DEBIT_IN = JdbcWritableBridge.readBigDecimal(9, __dbResults);
    this.DEBIT_OUT = JdbcWritableBridge.readBigDecimal(10, __dbResults);
    this.ZZAMOUNT = JdbcWritableBridge.readBigDecimal(11, __dbResults);
    this.ZZNUM = JdbcWritableBridge.readBigDecimal(12, __dbResults);
    this.DEBIT_ZZMONTH = JdbcWritableBridge.readBigDecimal(13, __dbResults);
    this.DEBIT_YJZZ = JdbcWritableBridge.readBigDecimal(14, __dbResults);
    this.DEBIT_CXMONTH = JdbcWritableBridge.readBigDecimal(15, __dbResults);
    this.CREDIT_CXMONTH = JdbcWritableBridge.readBigDecimal(16, __dbResults);
    this.ZDFQ_FLAG = JdbcWritableBridge.readString(17, __dbResults);
    this.XD_COUNT = JdbcWritableBridge.readBigDecimal(18, __dbResults);
    this.XD_SUM = JdbcWritableBridge.readBigDecimal(19, __dbResults);
    this.XD_AVE = JdbcWritableBridge.readBigDecimal(20, __dbResults);
    this.XD_OUT_DAY_SUM = JdbcWritableBridge.readBigDecimal(21, __dbResults);
    this.XD_NOSETTLE_SUM = JdbcWritableBridge.readBigDecimal(22, __dbResults);
    this.XD_YQ_CNT = JdbcWritableBridge.readBigDecimal(23, __dbResults);
    this.ADDR_TOP1 = JdbcWritableBridge.readString(24, __dbResults);
    this.JY_CNT_TOP1 = JdbcWritableBridge.readBigDecimal(25, __dbResults);
    this.ADDR_TOP2 = JdbcWritableBridge.readString(26, __dbResults);
    this.JY_CNT_TOP2 = JdbcWritableBridge.readBigDecimal(27, __dbResults);
    this.ADDR_TOP3 = JdbcWritableBridge.readString(28, __dbResults);
    this.JY_CNT_TOP3 = JdbcWritableBridge.readBigDecimal(29, __dbResults);
    this.TOTAL_AM = JdbcWritableBridge.readBigDecimal(30, __dbResults);
    this.CNT = JdbcWritableBridge.readBigDecimal(31, __dbResults);
    this.BJ_AM = JdbcWritableBridge.readBigDecimal(32, __dbResults);
    this.ELE_TOTAL_AM = JdbcWritableBridge.readBigDecimal(33, __dbResults);
    this.ELE_CNT = JdbcWritableBridge.readBigDecimal(34, __dbResults);
    this.ELE_BJ_AM = JdbcWritableBridge.readBigDecimal(35, __dbResults);
    this.W_TOTAL_AM = JdbcWritableBridge.readBigDecimal(36, __dbResults);
    this.W_CNT = JdbcWritableBridge.readBigDecimal(37, __dbResults);
    this.W_BJ_AM = JdbcWritableBridge.readBigDecimal(38, __dbResults);
    this.GAS_TOTAL_AM = JdbcWritableBridge.readBigDecimal(39, __dbResults);
    this.GAS_CNT = JdbcWritableBridge.readBigDecimal(40, __dbResults);
    this.GAS_BJ_AM = JdbcWritableBridge.readBigDecimal(41, __dbResults);
    this.COMM_TOTAL_AM = JdbcWritableBridge.readBigDecimal(42, __dbResults);
    this.COMM_CNT = JdbcWritableBridge.readBigDecimal(43, __dbResults);
    this.COMM_BJ_AM = JdbcWritableBridge.readBigDecimal(44, __dbResults);
    this.TV_TOTAL_AM = JdbcWritableBridge.readBigDecimal(45, __dbResults);
    this.TV_CNT = JdbcWritableBridge.readBigDecimal(46, __dbResults);
    this.TV_BJ_AM = JdbcWritableBridge.readBigDecimal(47, __dbResults);
    this.STARTDATE = JdbcWritableBridge.readString(48, __dbResults);
    this.ENDDATE = JdbcWritableBridge.readString(49, __dbResults);
    this.MONTH_NUM = JdbcWritableBridge.readString(50, __dbResults);
  }
  public void loadLargeObjects(LargeObjectLoader __loader)
      throws SQLException, IOException, InterruptedException {
  }
  public void loadLargeObjects0(LargeObjectLoader __loader)
      throws SQLException, IOException, InterruptedException {
  }
  public void write(PreparedStatement __dbStmt) throws SQLException {
    write(__dbStmt, 0);
  }

  public int write(PreparedStatement __dbStmt, int __off) throws SQLException {
    JdbcWritableBridge.writeString(MOBILE_NUM, 1 + __off, 12, __dbStmt);
    JdbcWritableBridge.writeString(REG_DATE, 2 + __off, 12, __dbStmt);
    JdbcWritableBridge.writeBigDecimal(ZW_TIME, 3 + __off, 2, __dbStmt);
    JdbcWritableBridge.writeBigDecimal(CREDIT_COUNT, 4 + __off, 2, __dbStmt);
    JdbcWritableBridge.writeBigDecimal(DEBIT_COUNT, 5 + __off, 2, __dbStmt);
    JdbcWritableBridge.writeBigDecimal(CREDIT_BJHK, 6 + __off, 2, __dbStmt);
    JdbcWritableBridge.writeBigDecimal(CREDIT_HKMONTH, 7 + __off, 2, __dbStmt);
    JdbcWritableBridge.writeBigDecimal(CREDIT_YJHK, 8 + __off, 2, __dbStmt);
    JdbcWritableBridge.writeBigDecimal(DEBIT_IN, 9 + __off, 2, __dbStmt);
    JdbcWritableBridge.writeBigDecimal(DEBIT_OUT, 10 + __off, 2, __dbStmt);
    JdbcWritableBridge.writeBigDecimal(ZZAMOUNT, 11 + __off, 2, __dbStmt);
    JdbcWritableBridge.writeBigDecimal(ZZNUM, 12 + __off, 2, __dbStmt);
    JdbcWritableBridge.writeBigDecimal(DEBIT_ZZMONTH, 13 + __off, 2, __dbStmt);
    JdbcWritableBridge.writeBigDecimal(DEBIT_YJZZ, 14 + __off, 2, __dbStmt);
    JdbcWritableBridge.writeBigDecimal(DEBIT_CXMONTH, 15 + __off, 2, __dbStmt);
    JdbcWritableBridge.writeBigDecimal(CREDIT_CXMONTH, 16 + __off, 2, __dbStmt);
    JdbcWritableBridge.writeString(ZDFQ_FLAG, 17 + __off, 12, __dbStmt);
    JdbcWritableBridge.writeBigDecimal(XD_COUNT, 18 + __off, 2, __dbStmt);
    JdbcWritableBridge.writeBigDecimal(XD_SUM, 19 + __off, 2, __dbStmt);
    JdbcWritableBridge.writeBigDecimal(XD_AVE, 20 + __off, 2, __dbStmt);
    JdbcWritableBridge.writeBigDecimal(XD_OUT_DAY_SUM, 21 + __off, 2, __dbStmt);
    JdbcWritableBridge.writeBigDecimal(XD_NOSETTLE_SUM, 22 + __off, 2, __dbStmt);
    JdbcWritableBridge.writeBigDecimal(XD_YQ_CNT, 23 + __off, 2, __dbStmt);
    JdbcWritableBridge.writeString(ADDR_TOP1, 24 + __off, 12, __dbStmt);
    JdbcWritableBridge.writeBigDecimal(JY_CNT_TOP1, 25 + __off, 2, __dbStmt);
    JdbcWritableBridge.writeString(ADDR_TOP2, 26 + __off, 12, __dbStmt);
    JdbcWritableBridge.writeBigDecimal(JY_CNT_TOP2, 27 + __off, 2, __dbStmt);
    JdbcWritableBridge.writeString(ADDR_TOP3, 28 + __off, 12, __dbStmt);
    JdbcWritableBridge.writeBigDecimal(JY_CNT_TOP3, 29 + __off, 2, __dbStmt);
    JdbcWritableBridge.writeBigDecimal(TOTAL_AM, 30 + __off, 2, __dbStmt);
    JdbcWritableBridge.writeBigDecimal(CNT, 31 + __off, 2, __dbStmt);
    JdbcWritableBridge.writeBigDecimal(BJ_AM, 32 + __off, 2, __dbStmt);
    JdbcWritableBridge.writeBigDecimal(ELE_TOTAL_AM, 33 + __off, 2, __dbStmt);
    JdbcWritableBridge.writeBigDecimal(ELE_CNT, 34 + __off, 2, __dbStmt);
    JdbcWritableBridge.writeBigDecimal(ELE_BJ_AM, 35 + __off, 2, __dbStmt);
    JdbcWritableBridge.writeBigDecimal(W_TOTAL_AM, 36 + __off, 2, __dbStmt);
    JdbcWritableBridge.writeBigDecimal(W_CNT, 37 + __off, 2, __dbStmt);
    JdbcWritableBridge.writeBigDecimal(W_BJ_AM, 38 + __off, 2, __dbStmt);
    JdbcWritableBridge.writeBigDecimal(GAS_TOTAL_AM, 39 + __off, 2, __dbStmt);
    JdbcWritableBridge.writeBigDecimal(GAS_CNT, 40 + __off, 2, __dbStmt);
    JdbcWritableBridge.writeBigDecimal(GAS_BJ_AM, 41 + __off, 2, __dbStmt);
    JdbcWritableBridge.writeBigDecimal(COMM_TOTAL_AM, 42 + __off, 2, __dbStmt);
    JdbcWritableBridge.writeBigDecimal(COMM_CNT, 43 + __off, 2, __dbStmt);
    JdbcWritableBridge.writeBigDecimal(COMM_BJ_AM, 44 + __off, 2, __dbStmt);
    JdbcWritableBridge.writeBigDecimal(TV_TOTAL_AM, 45 + __off, 2, __dbStmt);
    JdbcWritableBridge.writeBigDecimal(TV_CNT, 46 + __off, 2, __dbStmt);
    JdbcWritableBridge.writeBigDecimal(TV_BJ_AM, 47 + __off, 2, __dbStmt);
    JdbcWritableBridge.writeString(STARTDATE, 48 + __off, 12, __dbStmt);
    JdbcWritableBridge.writeString(ENDDATE, 49 + __off, 12, __dbStmt);
    JdbcWritableBridge.writeString(MONTH_NUM, 50 + __off, 12, __dbStmt);
    return 50;
  }
  public void write0(PreparedStatement __dbStmt, int __off) throws SQLException {
    JdbcWritableBridge.writeString(MOBILE_NUM, 1 + __off, 12, __dbStmt);
    JdbcWritableBridge.writeString(REG_DATE, 2 + __off, 12, __dbStmt);
    JdbcWritableBridge.writeBigDecimal(ZW_TIME, 3 + __off, 2, __dbStmt);
    JdbcWritableBridge.writeBigDecimal(CREDIT_COUNT, 4 + __off, 2, __dbStmt);
    JdbcWritableBridge.writeBigDecimal(DEBIT_COUNT, 5 + __off, 2, __dbStmt);
    JdbcWritableBridge.writeBigDecimal(CREDIT_BJHK, 6 + __off, 2, __dbStmt);
    JdbcWritableBridge.writeBigDecimal(CREDIT_HKMONTH, 7 + __off, 2, __dbStmt);
    JdbcWritableBridge.writeBigDecimal(CREDIT_YJHK, 8 + __off, 2, __dbStmt);
    JdbcWritableBridge.writeBigDecimal(DEBIT_IN, 9 + __off, 2, __dbStmt);
    JdbcWritableBridge.writeBigDecimal(DEBIT_OUT, 10 + __off, 2, __dbStmt);
    JdbcWritableBridge.writeBigDecimal(ZZAMOUNT, 11 + __off, 2, __dbStmt);
    JdbcWritableBridge.writeBigDecimal(ZZNUM, 12 + __off, 2, __dbStmt);
    JdbcWritableBridge.writeBigDecimal(DEBIT_ZZMONTH, 13 + __off, 2, __dbStmt);
    JdbcWritableBridge.writeBigDecimal(DEBIT_YJZZ, 14 + __off, 2, __dbStmt);
    JdbcWritableBridge.writeBigDecimal(DEBIT_CXMONTH, 15 + __off, 2, __dbStmt);
    JdbcWritableBridge.writeBigDecimal(CREDIT_CXMONTH, 16 + __off, 2, __dbStmt);
    JdbcWritableBridge.writeString(ZDFQ_FLAG, 17 + __off, 12, __dbStmt);
    JdbcWritableBridge.writeBigDecimal(XD_COUNT, 18 + __off, 2, __dbStmt);
    JdbcWritableBridge.writeBigDecimal(XD_SUM, 19 + __off, 2, __dbStmt);
    JdbcWritableBridge.writeBigDecimal(XD_AVE, 20 + __off, 2, __dbStmt);
    JdbcWritableBridge.writeBigDecimal(XD_OUT_DAY_SUM, 21 + __off, 2, __dbStmt);
    JdbcWritableBridge.writeBigDecimal(XD_NOSETTLE_SUM, 22 + __off, 2, __dbStmt);
    JdbcWritableBridge.writeBigDecimal(XD_YQ_CNT, 23 + __off, 2, __dbStmt);
    JdbcWritableBridge.writeString(ADDR_TOP1, 24 + __off, 12, __dbStmt);
    JdbcWritableBridge.writeBigDecimal(JY_CNT_TOP1, 25 + __off, 2, __dbStmt);
    JdbcWritableBridge.writeString(ADDR_TOP2, 26 + __off, 12, __dbStmt);
    JdbcWritableBridge.writeBigDecimal(JY_CNT_TOP2, 27 + __off, 2, __dbStmt);
    JdbcWritableBridge.writeString(ADDR_TOP3, 28 + __off, 12, __dbStmt);
    JdbcWritableBridge.writeBigDecimal(JY_CNT_TOP3, 29 + __off, 2, __dbStmt);
    JdbcWritableBridge.writeBigDecimal(TOTAL_AM, 30 + __off, 2, __dbStmt);
    JdbcWritableBridge.writeBigDecimal(CNT, 31 + __off, 2, __dbStmt);
    JdbcWritableBridge.writeBigDecimal(BJ_AM, 32 + __off, 2, __dbStmt);
    JdbcWritableBridge.writeBigDecimal(ELE_TOTAL_AM, 33 + __off, 2, __dbStmt);
    JdbcWritableBridge.writeBigDecimal(ELE_CNT, 34 + __off, 2, __dbStmt);
    JdbcWritableBridge.writeBigDecimal(ELE_BJ_AM, 35 + __off, 2, __dbStmt);
    JdbcWritableBridge.writeBigDecimal(W_TOTAL_AM, 36 + __off, 2, __dbStmt);
    JdbcWritableBridge.writeBigDecimal(W_CNT, 37 + __off, 2, __dbStmt);
    JdbcWritableBridge.writeBigDecimal(W_BJ_AM, 38 + __off, 2, __dbStmt);
    JdbcWritableBridge.writeBigDecimal(GAS_TOTAL_AM, 39 + __off, 2, __dbStmt);
    JdbcWritableBridge.writeBigDecimal(GAS_CNT, 40 + __off, 2, __dbStmt);
    JdbcWritableBridge.writeBigDecimal(GAS_BJ_AM, 41 + __off, 2, __dbStmt);
    JdbcWritableBridge.writeBigDecimal(COMM_TOTAL_AM, 42 + __off, 2, __dbStmt);
    JdbcWritableBridge.writeBigDecimal(COMM_CNT, 43 + __off, 2, __dbStmt);
    JdbcWritableBridge.writeBigDecimal(COMM_BJ_AM, 44 + __off, 2, __dbStmt);
    JdbcWritableBridge.writeBigDecimal(TV_TOTAL_AM, 45 + __off, 2, __dbStmt);
    JdbcWritableBridge.writeBigDecimal(TV_CNT, 46 + __off, 2, __dbStmt);
    JdbcWritableBridge.writeBigDecimal(TV_BJ_AM, 47 + __off, 2, __dbStmt);
    JdbcWritableBridge.writeString(STARTDATE, 48 + __off, 12, __dbStmt);
    JdbcWritableBridge.writeString(ENDDATE, 49 + __off, 12, __dbStmt);
    JdbcWritableBridge.writeString(MONTH_NUM, 50 + __off, 12, __dbStmt);
  }
  public void readFields(DataInput __dataIn) throws IOException {
this.readFields0(__dataIn);  }
  public void readFields0(DataInput __dataIn) throws IOException {
    if (__dataIn.readBoolean()) { 
        this.MOBILE_NUM = null;
    } else {
    this.MOBILE_NUM = Text.readString(__dataIn);
    }
    if (__dataIn.readBoolean()) { 
        this.REG_DATE = null;
    } else {
    this.REG_DATE = Text.readString(__dataIn);
    }
    if (__dataIn.readBoolean()) { 
        this.ZW_TIME = null;
    } else {
    this.ZW_TIME = com.cloudera.sqoop.lib.BigDecimalSerializer.readFields(__dataIn);
    }
    if (__dataIn.readBoolean()) { 
        this.CREDIT_COUNT = null;
    } else {
    this.CREDIT_COUNT = com.cloudera.sqoop.lib.BigDecimalSerializer.readFields(__dataIn);
    }
    if (__dataIn.readBoolean()) { 
        this.DEBIT_COUNT = null;
    } else {
    this.DEBIT_COUNT = com.cloudera.sqoop.lib.BigDecimalSerializer.readFields(__dataIn);
    }
    if (__dataIn.readBoolean()) { 
        this.CREDIT_BJHK = null;
    } else {
    this.CREDIT_BJHK = com.cloudera.sqoop.lib.BigDecimalSerializer.readFields(__dataIn);
    }
    if (__dataIn.readBoolean()) { 
        this.CREDIT_HKMONTH = null;
    } else {
    this.CREDIT_HKMONTH = com.cloudera.sqoop.lib.BigDecimalSerializer.readFields(__dataIn);
    }
    if (__dataIn.readBoolean()) { 
        this.CREDIT_YJHK = null;
    } else {
    this.CREDIT_YJHK = com.cloudera.sqoop.lib.BigDecimalSerializer.readFields(__dataIn);
    }
    if (__dataIn.readBoolean()) { 
        this.DEBIT_IN = null;
    } else {
    this.DEBIT_IN = com.cloudera.sqoop.lib.BigDecimalSerializer.readFields(__dataIn);
    }
    if (__dataIn.readBoolean()) { 
        this.DEBIT_OUT = null;
    } else {
    this.DEBIT_OUT = com.cloudera.sqoop.lib.BigDecimalSerializer.readFields(__dataIn);
    }
    if (__dataIn.readBoolean()) { 
        this.ZZAMOUNT = null;
    } else {
    this.ZZAMOUNT = com.cloudera.sqoop.lib.BigDecimalSerializer.readFields(__dataIn);
    }
    if (__dataIn.readBoolean()) { 
        this.ZZNUM = null;
    } else {
    this.ZZNUM = com.cloudera.sqoop.lib.BigDecimalSerializer.readFields(__dataIn);
    }
    if (__dataIn.readBoolean()) { 
        this.DEBIT_ZZMONTH = null;
    } else {
    this.DEBIT_ZZMONTH = com.cloudera.sqoop.lib.BigDecimalSerializer.readFields(__dataIn);
    }
    if (__dataIn.readBoolean()) { 
        this.DEBIT_YJZZ = null;
    } else {
    this.DEBIT_YJZZ = com.cloudera.sqoop.lib.BigDecimalSerializer.readFields(__dataIn);
    }
    if (__dataIn.readBoolean()) { 
        this.DEBIT_CXMONTH = null;
    } else {
    this.DEBIT_CXMONTH = com.cloudera.sqoop.lib.BigDecimalSerializer.readFields(__dataIn);
    }
    if (__dataIn.readBoolean()) { 
        this.CREDIT_CXMONTH = null;
    } else {
    this.CREDIT_CXMONTH = com.cloudera.sqoop.lib.BigDecimalSerializer.readFields(__dataIn);
    }
    if (__dataIn.readBoolean()) { 
        this.ZDFQ_FLAG = null;
    } else {
    this.ZDFQ_FLAG = Text.readString(__dataIn);
    }
    if (__dataIn.readBoolean()) { 
        this.XD_COUNT = null;
    } else {
    this.XD_COUNT = com.cloudera.sqoop.lib.BigDecimalSerializer.readFields(__dataIn);
    }
    if (__dataIn.readBoolean()) { 
        this.XD_SUM = null;
    } else {
    this.XD_SUM = com.cloudera.sqoop.lib.BigDecimalSerializer.readFields(__dataIn);
    }
    if (__dataIn.readBoolean()) { 
        this.XD_AVE = null;
    } else {
    this.XD_AVE = com.cloudera.sqoop.lib.BigDecimalSerializer.readFields(__dataIn);
    }
    if (__dataIn.readBoolean()) { 
        this.XD_OUT_DAY_SUM = null;
    } else {
    this.XD_OUT_DAY_SUM = com.cloudera.sqoop.lib.BigDecimalSerializer.readFields(__dataIn);
    }
    if (__dataIn.readBoolean()) { 
        this.XD_NOSETTLE_SUM = null;
    } else {
    this.XD_NOSETTLE_SUM = com.cloudera.sqoop.lib.BigDecimalSerializer.readFields(__dataIn);
    }
    if (__dataIn.readBoolean()) { 
        this.XD_YQ_CNT = null;
    } else {
    this.XD_YQ_CNT = com.cloudera.sqoop.lib.BigDecimalSerializer.readFields(__dataIn);
    }
    if (__dataIn.readBoolean()) { 
        this.ADDR_TOP1 = null;
    } else {
    this.ADDR_TOP1 = Text.readString(__dataIn);
    }
    if (__dataIn.readBoolean()) { 
        this.JY_CNT_TOP1 = null;
    } else {
    this.JY_CNT_TOP1 = com.cloudera.sqoop.lib.BigDecimalSerializer.readFields(__dataIn);
    }
    if (__dataIn.readBoolean()) { 
        this.ADDR_TOP2 = null;
    } else {
    this.ADDR_TOP2 = Text.readString(__dataIn);
    }
    if (__dataIn.readBoolean()) { 
        this.JY_CNT_TOP2 = null;
    } else {
    this.JY_CNT_TOP2 = com.cloudera.sqoop.lib.BigDecimalSerializer.readFields(__dataIn);
    }
    if (__dataIn.readBoolean()) { 
        this.ADDR_TOP3 = null;
    } else {
    this.ADDR_TOP3 = Text.readString(__dataIn);
    }
    if (__dataIn.readBoolean()) { 
        this.JY_CNT_TOP3 = null;
    } else {
    this.JY_CNT_TOP3 = com.cloudera.sqoop.lib.BigDecimalSerializer.readFields(__dataIn);
    }
    if (__dataIn.readBoolean()) { 
        this.TOTAL_AM = null;
    } else {
    this.TOTAL_AM = com.cloudera.sqoop.lib.BigDecimalSerializer.readFields(__dataIn);
    }
    if (__dataIn.readBoolean()) { 
        this.CNT = null;
    } else {
    this.CNT = com.cloudera.sqoop.lib.BigDecimalSerializer.readFields(__dataIn);
    }
    if (__dataIn.readBoolean()) { 
        this.BJ_AM = null;
    } else {
    this.BJ_AM = com.cloudera.sqoop.lib.BigDecimalSerializer.readFields(__dataIn);
    }
    if (__dataIn.readBoolean()) { 
        this.ELE_TOTAL_AM = null;
    } else {
    this.ELE_TOTAL_AM = com.cloudera.sqoop.lib.BigDecimalSerializer.readFields(__dataIn);
    }
    if (__dataIn.readBoolean()) { 
        this.ELE_CNT = null;
    } else {
    this.ELE_CNT = com.cloudera.sqoop.lib.BigDecimalSerializer.readFields(__dataIn);
    }
    if (__dataIn.readBoolean()) { 
        this.ELE_BJ_AM = null;
    } else {
    this.ELE_BJ_AM = com.cloudera.sqoop.lib.BigDecimalSerializer.readFields(__dataIn);
    }
    if (__dataIn.readBoolean()) { 
        this.W_TOTAL_AM = null;
    } else {
    this.W_TOTAL_AM = com.cloudera.sqoop.lib.BigDecimalSerializer.readFields(__dataIn);
    }
    if (__dataIn.readBoolean()) { 
        this.W_CNT = null;
    } else {
    this.W_CNT = com.cloudera.sqoop.lib.BigDecimalSerializer.readFields(__dataIn);
    }
    if (__dataIn.readBoolean()) { 
        this.W_BJ_AM = null;
    } else {
    this.W_BJ_AM = com.cloudera.sqoop.lib.BigDecimalSerializer.readFields(__dataIn);
    }
    if (__dataIn.readBoolean()) { 
        this.GAS_TOTAL_AM = null;
    } else {
    this.GAS_TOTAL_AM = com.cloudera.sqoop.lib.BigDecimalSerializer.readFields(__dataIn);
    }
    if (__dataIn.readBoolean()) { 
        this.GAS_CNT = null;
    } else {
    this.GAS_CNT = com.cloudera.sqoop.lib.BigDecimalSerializer.readFields(__dataIn);
    }
    if (__dataIn.readBoolean()) { 
        this.GAS_BJ_AM = null;
    } else {
    this.GAS_BJ_AM = com.cloudera.sqoop.lib.BigDecimalSerializer.readFields(__dataIn);
    }
    if (__dataIn.readBoolean()) { 
        this.COMM_TOTAL_AM = null;
    } else {
    this.COMM_TOTAL_AM = com.cloudera.sqoop.lib.BigDecimalSerializer.readFields(__dataIn);
    }
    if (__dataIn.readBoolean()) { 
        this.COMM_CNT = null;
    } else {
    this.COMM_CNT = com.cloudera.sqoop.lib.BigDecimalSerializer.readFields(__dataIn);
    }
    if (__dataIn.readBoolean()) { 
        this.COMM_BJ_AM = null;
    } else {
    this.COMM_BJ_AM = com.cloudera.sqoop.lib.BigDecimalSerializer.readFields(__dataIn);
    }
    if (__dataIn.readBoolean()) { 
        this.TV_TOTAL_AM = null;
    } else {
    this.TV_TOTAL_AM = com.cloudera.sqoop.lib.BigDecimalSerializer.readFields(__dataIn);
    }
    if (__dataIn.readBoolean()) { 
        this.TV_CNT = null;
    } else {
    this.TV_CNT = com.cloudera.sqoop.lib.BigDecimalSerializer.readFields(__dataIn);
    }
    if (__dataIn.readBoolean()) { 
        this.TV_BJ_AM = null;
    } else {
    this.TV_BJ_AM = com.cloudera.sqoop.lib.BigDecimalSerializer.readFields(__dataIn);
    }
    if (__dataIn.readBoolean()) { 
        this.STARTDATE = null;
    } else {
    this.STARTDATE = Text.readString(__dataIn);
    }
    if (__dataIn.readBoolean()) { 
        this.ENDDATE = null;
    } else {
    this.ENDDATE = Text.readString(__dataIn);
    }
    if (__dataIn.readBoolean()) { 
        this.MONTH_NUM = null;
    } else {
    this.MONTH_NUM = Text.readString(__dataIn);
    }
  }
  public void write(DataOutput __dataOut) throws IOException {
    if (null == this.MOBILE_NUM) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    Text.writeString(__dataOut, MOBILE_NUM);
    }
    if (null == this.REG_DATE) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    Text.writeString(__dataOut, REG_DATE);
    }
    if (null == this.ZW_TIME) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    com.cloudera.sqoop.lib.BigDecimalSerializer.write(this.ZW_TIME, __dataOut);
    }
    if (null == this.CREDIT_COUNT) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    com.cloudera.sqoop.lib.BigDecimalSerializer.write(this.CREDIT_COUNT, __dataOut);
    }
    if (null == this.DEBIT_COUNT) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    com.cloudera.sqoop.lib.BigDecimalSerializer.write(this.DEBIT_COUNT, __dataOut);
    }
    if (null == this.CREDIT_BJHK) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    com.cloudera.sqoop.lib.BigDecimalSerializer.write(this.CREDIT_BJHK, __dataOut);
    }
    if (null == this.CREDIT_HKMONTH) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    com.cloudera.sqoop.lib.BigDecimalSerializer.write(this.CREDIT_HKMONTH, __dataOut);
    }
    if (null == this.CREDIT_YJHK) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    com.cloudera.sqoop.lib.BigDecimalSerializer.write(this.CREDIT_YJHK, __dataOut);
    }
    if (null == this.DEBIT_IN) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    com.cloudera.sqoop.lib.BigDecimalSerializer.write(this.DEBIT_IN, __dataOut);
    }
    if (null == this.DEBIT_OUT) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    com.cloudera.sqoop.lib.BigDecimalSerializer.write(this.DEBIT_OUT, __dataOut);
    }
    if (null == this.ZZAMOUNT) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    com.cloudera.sqoop.lib.BigDecimalSerializer.write(this.ZZAMOUNT, __dataOut);
    }
    if (null == this.ZZNUM) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    com.cloudera.sqoop.lib.BigDecimalSerializer.write(this.ZZNUM, __dataOut);
    }
    if (null == this.DEBIT_ZZMONTH) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    com.cloudera.sqoop.lib.BigDecimalSerializer.write(this.DEBIT_ZZMONTH, __dataOut);
    }
    if (null == this.DEBIT_YJZZ) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    com.cloudera.sqoop.lib.BigDecimalSerializer.write(this.DEBIT_YJZZ, __dataOut);
    }
    if (null == this.DEBIT_CXMONTH) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    com.cloudera.sqoop.lib.BigDecimalSerializer.write(this.DEBIT_CXMONTH, __dataOut);
    }
    if (null == this.CREDIT_CXMONTH) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    com.cloudera.sqoop.lib.BigDecimalSerializer.write(this.CREDIT_CXMONTH, __dataOut);
    }
    if (null == this.ZDFQ_FLAG) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    Text.writeString(__dataOut, ZDFQ_FLAG);
    }
    if (null == this.XD_COUNT) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    com.cloudera.sqoop.lib.BigDecimalSerializer.write(this.XD_COUNT, __dataOut);
    }
    if (null == this.XD_SUM) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    com.cloudera.sqoop.lib.BigDecimalSerializer.write(this.XD_SUM, __dataOut);
    }
    if (null == this.XD_AVE) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    com.cloudera.sqoop.lib.BigDecimalSerializer.write(this.XD_AVE, __dataOut);
    }
    if (null == this.XD_OUT_DAY_SUM) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    com.cloudera.sqoop.lib.BigDecimalSerializer.write(this.XD_OUT_DAY_SUM, __dataOut);
    }
    if (null == this.XD_NOSETTLE_SUM) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    com.cloudera.sqoop.lib.BigDecimalSerializer.write(this.XD_NOSETTLE_SUM, __dataOut);
    }
    if (null == this.XD_YQ_CNT) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    com.cloudera.sqoop.lib.BigDecimalSerializer.write(this.XD_YQ_CNT, __dataOut);
    }
    if (null == this.ADDR_TOP1) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    Text.writeString(__dataOut, ADDR_TOP1);
    }
    if (null == this.JY_CNT_TOP1) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    com.cloudera.sqoop.lib.BigDecimalSerializer.write(this.JY_CNT_TOP1, __dataOut);
    }
    if (null == this.ADDR_TOP2) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    Text.writeString(__dataOut, ADDR_TOP2);
    }
    if (null == this.JY_CNT_TOP2) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    com.cloudera.sqoop.lib.BigDecimalSerializer.write(this.JY_CNT_TOP2, __dataOut);
    }
    if (null == this.ADDR_TOP3) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    Text.writeString(__dataOut, ADDR_TOP3);
    }
    if (null == this.JY_CNT_TOP3) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    com.cloudera.sqoop.lib.BigDecimalSerializer.write(this.JY_CNT_TOP3, __dataOut);
    }
    if (null == this.TOTAL_AM) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    com.cloudera.sqoop.lib.BigDecimalSerializer.write(this.TOTAL_AM, __dataOut);
    }
    if (null == this.CNT) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    com.cloudera.sqoop.lib.BigDecimalSerializer.write(this.CNT, __dataOut);
    }
    if (null == this.BJ_AM) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    com.cloudera.sqoop.lib.BigDecimalSerializer.write(this.BJ_AM, __dataOut);
    }
    if (null == this.ELE_TOTAL_AM) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    com.cloudera.sqoop.lib.BigDecimalSerializer.write(this.ELE_TOTAL_AM, __dataOut);
    }
    if (null == this.ELE_CNT) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    com.cloudera.sqoop.lib.BigDecimalSerializer.write(this.ELE_CNT, __dataOut);
    }
    if (null == this.ELE_BJ_AM) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    com.cloudera.sqoop.lib.BigDecimalSerializer.write(this.ELE_BJ_AM, __dataOut);
    }
    if (null == this.W_TOTAL_AM) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    com.cloudera.sqoop.lib.BigDecimalSerializer.write(this.W_TOTAL_AM, __dataOut);
    }
    if (null == this.W_CNT) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    com.cloudera.sqoop.lib.BigDecimalSerializer.write(this.W_CNT, __dataOut);
    }
    if (null == this.W_BJ_AM) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    com.cloudera.sqoop.lib.BigDecimalSerializer.write(this.W_BJ_AM, __dataOut);
    }
    if (null == this.GAS_TOTAL_AM) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    com.cloudera.sqoop.lib.BigDecimalSerializer.write(this.GAS_TOTAL_AM, __dataOut);
    }
    if (null == this.GAS_CNT) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    com.cloudera.sqoop.lib.BigDecimalSerializer.write(this.GAS_CNT, __dataOut);
    }
    if (null == this.GAS_BJ_AM) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    com.cloudera.sqoop.lib.BigDecimalSerializer.write(this.GAS_BJ_AM, __dataOut);
    }
    if (null == this.COMM_TOTAL_AM) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    com.cloudera.sqoop.lib.BigDecimalSerializer.write(this.COMM_TOTAL_AM, __dataOut);
    }
    if (null == this.COMM_CNT) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    com.cloudera.sqoop.lib.BigDecimalSerializer.write(this.COMM_CNT, __dataOut);
    }
    if (null == this.COMM_BJ_AM) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    com.cloudera.sqoop.lib.BigDecimalSerializer.write(this.COMM_BJ_AM, __dataOut);
    }
    if (null == this.TV_TOTAL_AM) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    com.cloudera.sqoop.lib.BigDecimalSerializer.write(this.TV_TOTAL_AM, __dataOut);
    }
    if (null == this.TV_CNT) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    com.cloudera.sqoop.lib.BigDecimalSerializer.write(this.TV_CNT, __dataOut);
    }
    if (null == this.TV_BJ_AM) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    com.cloudera.sqoop.lib.BigDecimalSerializer.write(this.TV_BJ_AM, __dataOut);
    }
    if (null == this.STARTDATE) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    Text.writeString(__dataOut, STARTDATE);
    }
    if (null == this.ENDDATE) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    Text.writeString(__dataOut, ENDDATE);
    }
    if (null == this.MONTH_NUM) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    Text.writeString(__dataOut, MONTH_NUM);
    }
  }
  public void write0(DataOutput __dataOut) throws IOException {
    if (null == this.MOBILE_NUM) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    Text.writeString(__dataOut, MOBILE_NUM);
    }
    if (null == this.REG_DATE) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    Text.writeString(__dataOut, REG_DATE);
    }
    if (null == this.ZW_TIME) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    com.cloudera.sqoop.lib.BigDecimalSerializer.write(this.ZW_TIME, __dataOut);
    }
    if (null == this.CREDIT_COUNT) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    com.cloudera.sqoop.lib.BigDecimalSerializer.write(this.CREDIT_COUNT, __dataOut);
    }
    if (null == this.DEBIT_COUNT) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    com.cloudera.sqoop.lib.BigDecimalSerializer.write(this.DEBIT_COUNT, __dataOut);
    }
    if (null == this.CREDIT_BJHK) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    com.cloudera.sqoop.lib.BigDecimalSerializer.write(this.CREDIT_BJHK, __dataOut);
    }
    if (null == this.CREDIT_HKMONTH) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    com.cloudera.sqoop.lib.BigDecimalSerializer.write(this.CREDIT_HKMONTH, __dataOut);
    }
    if (null == this.CREDIT_YJHK) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    com.cloudera.sqoop.lib.BigDecimalSerializer.write(this.CREDIT_YJHK, __dataOut);
    }
    if (null == this.DEBIT_IN) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    com.cloudera.sqoop.lib.BigDecimalSerializer.write(this.DEBIT_IN, __dataOut);
    }
    if (null == this.DEBIT_OUT) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    com.cloudera.sqoop.lib.BigDecimalSerializer.write(this.DEBIT_OUT, __dataOut);
    }
    if (null == this.ZZAMOUNT) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    com.cloudera.sqoop.lib.BigDecimalSerializer.write(this.ZZAMOUNT, __dataOut);
    }
    if (null == this.ZZNUM) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    com.cloudera.sqoop.lib.BigDecimalSerializer.write(this.ZZNUM, __dataOut);
    }
    if (null == this.DEBIT_ZZMONTH) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    com.cloudera.sqoop.lib.BigDecimalSerializer.write(this.DEBIT_ZZMONTH, __dataOut);
    }
    if (null == this.DEBIT_YJZZ) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    com.cloudera.sqoop.lib.BigDecimalSerializer.write(this.DEBIT_YJZZ, __dataOut);
    }
    if (null == this.DEBIT_CXMONTH) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    com.cloudera.sqoop.lib.BigDecimalSerializer.write(this.DEBIT_CXMONTH, __dataOut);
    }
    if (null == this.CREDIT_CXMONTH) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    com.cloudera.sqoop.lib.BigDecimalSerializer.write(this.CREDIT_CXMONTH, __dataOut);
    }
    if (null == this.ZDFQ_FLAG) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    Text.writeString(__dataOut, ZDFQ_FLAG);
    }
    if (null == this.XD_COUNT) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    com.cloudera.sqoop.lib.BigDecimalSerializer.write(this.XD_COUNT, __dataOut);
    }
    if (null == this.XD_SUM) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    com.cloudera.sqoop.lib.BigDecimalSerializer.write(this.XD_SUM, __dataOut);
    }
    if (null == this.XD_AVE) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    com.cloudera.sqoop.lib.BigDecimalSerializer.write(this.XD_AVE, __dataOut);
    }
    if (null == this.XD_OUT_DAY_SUM) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    com.cloudera.sqoop.lib.BigDecimalSerializer.write(this.XD_OUT_DAY_SUM, __dataOut);
    }
    if (null == this.XD_NOSETTLE_SUM) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    com.cloudera.sqoop.lib.BigDecimalSerializer.write(this.XD_NOSETTLE_SUM, __dataOut);
    }
    if (null == this.XD_YQ_CNT) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    com.cloudera.sqoop.lib.BigDecimalSerializer.write(this.XD_YQ_CNT, __dataOut);
    }
    if (null == this.ADDR_TOP1) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    Text.writeString(__dataOut, ADDR_TOP1);
    }
    if (null == this.JY_CNT_TOP1) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    com.cloudera.sqoop.lib.BigDecimalSerializer.write(this.JY_CNT_TOP1, __dataOut);
    }
    if (null == this.ADDR_TOP2) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    Text.writeString(__dataOut, ADDR_TOP2);
    }
    if (null == this.JY_CNT_TOP2) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    com.cloudera.sqoop.lib.BigDecimalSerializer.write(this.JY_CNT_TOP2, __dataOut);
    }
    if (null == this.ADDR_TOP3) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    Text.writeString(__dataOut, ADDR_TOP3);
    }
    if (null == this.JY_CNT_TOP3) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    com.cloudera.sqoop.lib.BigDecimalSerializer.write(this.JY_CNT_TOP3, __dataOut);
    }
    if (null == this.TOTAL_AM) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    com.cloudera.sqoop.lib.BigDecimalSerializer.write(this.TOTAL_AM, __dataOut);
    }
    if (null == this.CNT) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    com.cloudera.sqoop.lib.BigDecimalSerializer.write(this.CNT, __dataOut);
    }
    if (null == this.BJ_AM) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    com.cloudera.sqoop.lib.BigDecimalSerializer.write(this.BJ_AM, __dataOut);
    }
    if (null == this.ELE_TOTAL_AM) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    com.cloudera.sqoop.lib.BigDecimalSerializer.write(this.ELE_TOTAL_AM, __dataOut);
    }
    if (null == this.ELE_CNT) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    com.cloudera.sqoop.lib.BigDecimalSerializer.write(this.ELE_CNT, __dataOut);
    }
    if (null == this.ELE_BJ_AM) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    com.cloudera.sqoop.lib.BigDecimalSerializer.write(this.ELE_BJ_AM, __dataOut);
    }
    if (null == this.W_TOTAL_AM) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    com.cloudera.sqoop.lib.BigDecimalSerializer.write(this.W_TOTAL_AM, __dataOut);
    }
    if (null == this.W_CNT) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    com.cloudera.sqoop.lib.BigDecimalSerializer.write(this.W_CNT, __dataOut);
    }
    if (null == this.W_BJ_AM) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    com.cloudera.sqoop.lib.BigDecimalSerializer.write(this.W_BJ_AM, __dataOut);
    }
    if (null == this.GAS_TOTAL_AM) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    com.cloudera.sqoop.lib.BigDecimalSerializer.write(this.GAS_TOTAL_AM, __dataOut);
    }
    if (null == this.GAS_CNT) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    com.cloudera.sqoop.lib.BigDecimalSerializer.write(this.GAS_CNT, __dataOut);
    }
    if (null == this.GAS_BJ_AM) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    com.cloudera.sqoop.lib.BigDecimalSerializer.write(this.GAS_BJ_AM, __dataOut);
    }
    if (null == this.COMM_TOTAL_AM) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    com.cloudera.sqoop.lib.BigDecimalSerializer.write(this.COMM_TOTAL_AM, __dataOut);
    }
    if (null == this.COMM_CNT) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    com.cloudera.sqoop.lib.BigDecimalSerializer.write(this.COMM_CNT, __dataOut);
    }
    if (null == this.COMM_BJ_AM) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    com.cloudera.sqoop.lib.BigDecimalSerializer.write(this.COMM_BJ_AM, __dataOut);
    }
    if (null == this.TV_TOTAL_AM) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    com.cloudera.sqoop.lib.BigDecimalSerializer.write(this.TV_TOTAL_AM, __dataOut);
    }
    if (null == this.TV_CNT) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    com.cloudera.sqoop.lib.BigDecimalSerializer.write(this.TV_CNT, __dataOut);
    }
    if (null == this.TV_BJ_AM) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    com.cloudera.sqoop.lib.BigDecimalSerializer.write(this.TV_BJ_AM, __dataOut);
    }
    if (null == this.STARTDATE) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    Text.writeString(__dataOut, STARTDATE);
    }
    if (null == this.ENDDATE) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    Text.writeString(__dataOut, ENDDATE);
    }
    if (null == this.MONTH_NUM) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    Text.writeString(__dataOut, MONTH_NUM);
    }
  }
  private static final DelimiterSet __outputDelimiters = new DelimiterSet((char) 1, (char) 10, (char) 0, (char) 0, false);
  public String toString() {
    return toString(__outputDelimiters, true);
  }
  public String toString(DelimiterSet delimiters) {
    return toString(delimiters, true);
  }
  public String toString(boolean useRecordDelim) {
    return toString(__outputDelimiters, useRecordDelim);
  }
  public String toString(DelimiterSet delimiters, boolean useRecordDelim) {
    StringBuilder __sb = new StringBuilder();
    char fieldDelim = delimiters.getFieldsTerminatedBy();
    __sb.append(FieldFormatter.escapeAndEnclose(MOBILE_NUM==null?"null":MOBILE_NUM, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(REG_DATE==null?"null":REG_DATE, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(ZW_TIME==null?"null":ZW_TIME.toPlainString(), delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(CREDIT_COUNT==null?"null":CREDIT_COUNT.toPlainString(), delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(DEBIT_COUNT==null?"null":DEBIT_COUNT.toPlainString(), delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(CREDIT_BJHK==null?"null":CREDIT_BJHK.toPlainString(), delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(CREDIT_HKMONTH==null?"null":CREDIT_HKMONTH.toPlainString(), delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(CREDIT_YJHK==null?"null":CREDIT_YJHK.toPlainString(), delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(DEBIT_IN==null?"null":DEBIT_IN.toPlainString(), delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(DEBIT_OUT==null?"null":DEBIT_OUT.toPlainString(), delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(ZZAMOUNT==null?"null":ZZAMOUNT.toPlainString(), delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(ZZNUM==null?"null":ZZNUM.toPlainString(), delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(DEBIT_ZZMONTH==null?"null":DEBIT_ZZMONTH.toPlainString(), delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(DEBIT_YJZZ==null?"null":DEBIT_YJZZ.toPlainString(), delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(DEBIT_CXMONTH==null?"null":DEBIT_CXMONTH.toPlainString(), delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(CREDIT_CXMONTH==null?"null":CREDIT_CXMONTH.toPlainString(), delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(ZDFQ_FLAG==null?"null":ZDFQ_FLAG, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(XD_COUNT==null?"null":XD_COUNT.toPlainString(), delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(XD_SUM==null?"null":XD_SUM.toPlainString(), delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(XD_AVE==null?"null":XD_AVE.toPlainString(), delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(XD_OUT_DAY_SUM==null?"null":XD_OUT_DAY_SUM.toPlainString(), delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(XD_NOSETTLE_SUM==null?"null":XD_NOSETTLE_SUM.toPlainString(), delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(XD_YQ_CNT==null?"null":XD_YQ_CNT.toPlainString(), delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(ADDR_TOP1==null?"null":ADDR_TOP1, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(JY_CNT_TOP1==null?"null":JY_CNT_TOP1.toPlainString(), delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(ADDR_TOP2==null?"null":ADDR_TOP2, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(JY_CNT_TOP2==null?"null":JY_CNT_TOP2.toPlainString(), delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(ADDR_TOP3==null?"null":ADDR_TOP3, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(JY_CNT_TOP3==null?"null":JY_CNT_TOP3.toPlainString(), delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(TOTAL_AM==null?"null":TOTAL_AM.toPlainString(), delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(CNT==null?"null":CNT.toPlainString(), delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(BJ_AM==null?"null":BJ_AM.toPlainString(), delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(ELE_TOTAL_AM==null?"null":ELE_TOTAL_AM.toPlainString(), delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(ELE_CNT==null?"null":ELE_CNT.toPlainString(), delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(ELE_BJ_AM==null?"null":ELE_BJ_AM.toPlainString(), delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(W_TOTAL_AM==null?"null":W_TOTAL_AM.toPlainString(), delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(W_CNT==null?"null":W_CNT.toPlainString(), delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(W_BJ_AM==null?"null":W_BJ_AM.toPlainString(), delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(GAS_TOTAL_AM==null?"null":GAS_TOTAL_AM.toPlainString(), delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(GAS_CNT==null?"null":GAS_CNT.toPlainString(), delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(GAS_BJ_AM==null?"null":GAS_BJ_AM.toPlainString(), delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(COMM_TOTAL_AM==null?"null":COMM_TOTAL_AM.toPlainString(), delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(COMM_CNT==null?"null":COMM_CNT.toPlainString(), delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(COMM_BJ_AM==null?"null":COMM_BJ_AM.toPlainString(), delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(TV_TOTAL_AM==null?"null":TV_TOTAL_AM.toPlainString(), delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(TV_CNT==null?"null":TV_CNT.toPlainString(), delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(TV_BJ_AM==null?"null":TV_BJ_AM.toPlainString(), delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(STARTDATE==null?"null":STARTDATE, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(ENDDATE==null?"null":ENDDATE, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(MONTH_NUM==null?"null":MONTH_NUM, delimiters));
    if (useRecordDelim) {
      __sb.append(delimiters.getLinesTerminatedBy());
    }
    return __sb.toString();
  }
  public void toString0(DelimiterSet delimiters, StringBuilder __sb, char fieldDelim) {
    __sb.append(FieldFormatter.escapeAndEnclose(MOBILE_NUM==null?"null":MOBILE_NUM, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(REG_DATE==null?"null":REG_DATE, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(ZW_TIME==null?"null":ZW_TIME.toPlainString(), delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(CREDIT_COUNT==null?"null":CREDIT_COUNT.toPlainString(), delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(DEBIT_COUNT==null?"null":DEBIT_COUNT.toPlainString(), delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(CREDIT_BJHK==null?"null":CREDIT_BJHK.toPlainString(), delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(CREDIT_HKMONTH==null?"null":CREDIT_HKMONTH.toPlainString(), delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(CREDIT_YJHK==null?"null":CREDIT_YJHK.toPlainString(), delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(DEBIT_IN==null?"null":DEBIT_IN.toPlainString(), delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(DEBIT_OUT==null?"null":DEBIT_OUT.toPlainString(), delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(ZZAMOUNT==null?"null":ZZAMOUNT.toPlainString(), delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(ZZNUM==null?"null":ZZNUM.toPlainString(), delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(DEBIT_ZZMONTH==null?"null":DEBIT_ZZMONTH.toPlainString(), delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(DEBIT_YJZZ==null?"null":DEBIT_YJZZ.toPlainString(), delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(DEBIT_CXMONTH==null?"null":DEBIT_CXMONTH.toPlainString(), delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(CREDIT_CXMONTH==null?"null":CREDIT_CXMONTH.toPlainString(), delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(ZDFQ_FLAG==null?"null":ZDFQ_FLAG, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(XD_COUNT==null?"null":XD_COUNT.toPlainString(), delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(XD_SUM==null?"null":XD_SUM.toPlainString(), delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(XD_AVE==null?"null":XD_AVE.toPlainString(), delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(XD_OUT_DAY_SUM==null?"null":XD_OUT_DAY_SUM.toPlainString(), delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(XD_NOSETTLE_SUM==null?"null":XD_NOSETTLE_SUM.toPlainString(), delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(XD_YQ_CNT==null?"null":XD_YQ_CNT.toPlainString(), delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(ADDR_TOP1==null?"null":ADDR_TOP1, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(JY_CNT_TOP1==null?"null":JY_CNT_TOP1.toPlainString(), delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(ADDR_TOP2==null?"null":ADDR_TOP2, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(JY_CNT_TOP2==null?"null":JY_CNT_TOP2.toPlainString(), delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(ADDR_TOP3==null?"null":ADDR_TOP3, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(JY_CNT_TOP3==null?"null":JY_CNT_TOP3.toPlainString(), delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(TOTAL_AM==null?"null":TOTAL_AM.toPlainString(), delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(CNT==null?"null":CNT.toPlainString(), delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(BJ_AM==null?"null":BJ_AM.toPlainString(), delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(ELE_TOTAL_AM==null?"null":ELE_TOTAL_AM.toPlainString(), delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(ELE_CNT==null?"null":ELE_CNT.toPlainString(), delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(ELE_BJ_AM==null?"null":ELE_BJ_AM.toPlainString(), delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(W_TOTAL_AM==null?"null":W_TOTAL_AM.toPlainString(), delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(W_CNT==null?"null":W_CNT.toPlainString(), delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(W_BJ_AM==null?"null":W_BJ_AM.toPlainString(), delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(GAS_TOTAL_AM==null?"null":GAS_TOTAL_AM.toPlainString(), delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(GAS_CNT==null?"null":GAS_CNT.toPlainString(), delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(GAS_BJ_AM==null?"null":GAS_BJ_AM.toPlainString(), delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(COMM_TOTAL_AM==null?"null":COMM_TOTAL_AM.toPlainString(), delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(COMM_CNT==null?"null":COMM_CNT.toPlainString(), delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(COMM_BJ_AM==null?"null":COMM_BJ_AM.toPlainString(), delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(TV_TOTAL_AM==null?"null":TV_TOTAL_AM.toPlainString(), delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(TV_CNT==null?"null":TV_CNT.toPlainString(), delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(TV_BJ_AM==null?"null":TV_BJ_AM.toPlainString(), delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(STARTDATE==null?"null":STARTDATE, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(ENDDATE==null?"null":ENDDATE, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(MONTH_NUM==null?"null":MONTH_NUM, delimiters));
  }
  private static final DelimiterSet __inputDelimiters = new DelimiterSet((char) 1, (char) 10, (char) 0, (char) 0, false);
  private RecordParser __parser;
  public void parse(Text __record) throws RecordParser.ParseError {
    if (null == this.__parser) {
      this.__parser = new RecordParser(__inputDelimiters);
    }
    List<String> __fields = this.__parser.parseRecord(__record);
    __loadFromFields(__fields);
  }

  public void parse(CharSequence __record) throws RecordParser.ParseError {
    if (null == this.__parser) {
      this.__parser = new RecordParser(__inputDelimiters);
    }
    List<String> __fields = this.__parser.parseRecord(__record);
    __loadFromFields(__fields);
  }

  public void parse(byte [] __record) throws RecordParser.ParseError {
    if (null == this.__parser) {
      this.__parser = new RecordParser(__inputDelimiters);
    }
    List<String> __fields = this.__parser.parseRecord(__record);
    __loadFromFields(__fields);
  }

  public void parse(char [] __record) throws RecordParser.ParseError {
    if (null == this.__parser) {
      this.__parser = new RecordParser(__inputDelimiters);
    }
    List<String> __fields = this.__parser.parseRecord(__record);
    __loadFromFields(__fields);
  }

  public void parse(ByteBuffer __record) throws RecordParser.ParseError {
    if (null == this.__parser) {
      this.__parser = new RecordParser(__inputDelimiters);
    }
    List<String> __fields = this.__parser.parseRecord(__record);
    __loadFromFields(__fields);
  }

  public void parse(CharBuffer __record) throws RecordParser.ParseError {
    if (null == this.__parser) {
      this.__parser = new RecordParser(__inputDelimiters);
    }
    List<String> __fields = this.__parser.parseRecord(__record);
    __loadFromFields(__fields);
  }

  private void __loadFromFields(List<String> fields) {
    Iterator<String> __it = fields.listIterator();
    String __cur_str = null;
    try {
    __cur_str = __it.next();
    if (__cur_str.equals("\\N")) { this.MOBILE_NUM = null; } else {
      this.MOBILE_NUM = __cur_str;
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N")) { this.REG_DATE = null; } else {
      this.REG_DATE = __cur_str;
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N") || __cur_str.length() == 0) { this.ZW_TIME = null; } else {
      this.ZW_TIME = new java.math.BigDecimal(__cur_str);
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N") || __cur_str.length() == 0) { this.CREDIT_COUNT = null; } else {
      this.CREDIT_COUNT = new java.math.BigDecimal(__cur_str);
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N") || __cur_str.length() == 0) { this.DEBIT_COUNT = null; } else {
      this.DEBIT_COUNT = new java.math.BigDecimal(__cur_str);
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N") || __cur_str.length() == 0) { this.CREDIT_BJHK = null; } else {
      this.CREDIT_BJHK = new java.math.BigDecimal(__cur_str);
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N") || __cur_str.length() == 0) { this.CREDIT_HKMONTH = null; } else {
      this.CREDIT_HKMONTH = new java.math.BigDecimal(__cur_str);
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N") || __cur_str.length() == 0) { this.CREDIT_YJHK = null; } else {
      this.CREDIT_YJHK = new java.math.BigDecimal(__cur_str);
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N") || __cur_str.length() == 0) { this.DEBIT_IN = null; } else {
      this.DEBIT_IN = new java.math.BigDecimal(__cur_str);
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N") || __cur_str.length() == 0) { this.DEBIT_OUT = null; } else {
      this.DEBIT_OUT = new java.math.BigDecimal(__cur_str);
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N") || __cur_str.length() == 0) { this.ZZAMOUNT = null; } else {
      this.ZZAMOUNT = new java.math.BigDecimal(__cur_str);
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N") || __cur_str.length() == 0) { this.ZZNUM = null; } else {
      this.ZZNUM = new java.math.BigDecimal(__cur_str);
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N") || __cur_str.length() == 0) { this.DEBIT_ZZMONTH = null; } else {
      this.DEBIT_ZZMONTH = new java.math.BigDecimal(__cur_str);
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N") || __cur_str.length() == 0) { this.DEBIT_YJZZ = null; } else {
      this.DEBIT_YJZZ = new java.math.BigDecimal(__cur_str);
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N") || __cur_str.length() == 0) { this.DEBIT_CXMONTH = null; } else {
      this.DEBIT_CXMONTH = new java.math.BigDecimal(__cur_str);
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N") || __cur_str.length() == 0) { this.CREDIT_CXMONTH = null; } else {
      this.CREDIT_CXMONTH = new java.math.BigDecimal(__cur_str);
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N")) { this.ZDFQ_FLAG = null; } else {
      this.ZDFQ_FLAG = __cur_str;
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N") || __cur_str.length() == 0) { this.XD_COUNT = null; } else {
      this.XD_COUNT = new java.math.BigDecimal(__cur_str);
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N") || __cur_str.length() == 0) { this.XD_SUM = null; } else {
      this.XD_SUM = new java.math.BigDecimal(__cur_str);
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N") || __cur_str.length() == 0) { this.XD_AVE = null; } else {
      this.XD_AVE = new java.math.BigDecimal(__cur_str);
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N") || __cur_str.length() == 0) { this.XD_OUT_DAY_SUM = null; } else {
      this.XD_OUT_DAY_SUM = new java.math.BigDecimal(__cur_str);
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N") || __cur_str.length() == 0) { this.XD_NOSETTLE_SUM = null; } else {
      this.XD_NOSETTLE_SUM = new java.math.BigDecimal(__cur_str);
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N") || __cur_str.length() == 0) { this.XD_YQ_CNT = null; } else {
      this.XD_YQ_CNT = new java.math.BigDecimal(__cur_str);
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N")) { this.ADDR_TOP1 = null; } else {
      this.ADDR_TOP1 = __cur_str;
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N") || __cur_str.length() == 0) { this.JY_CNT_TOP1 = null; } else {
      this.JY_CNT_TOP1 = new java.math.BigDecimal(__cur_str);
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N")) { this.ADDR_TOP2 = null; } else {
      this.ADDR_TOP2 = __cur_str;
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N") || __cur_str.length() == 0) { this.JY_CNT_TOP2 = null; } else {
      this.JY_CNT_TOP2 = new java.math.BigDecimal(__cur_str);
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N")) { this.ADDR_TOP3 = null; } else {
      this.ADDR_TOP3 = __cur_str;
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N") || __cur_str.length() == 0) { this.JY_CNT_TOP3 = null; } else {
      this.JY_CNT_TOP3 = new java.math.BigDecimal(__cur_str);
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N") || __cur_str.length() == 0) { this.TOTAL_AM = null; } else {
      this.TOTAL_AM = new java.math.BigDecimal(__cur_str);
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N") || __cur_str.length() == 0) { this.CNT = null; } else {
      this.CNT = new java.math.BigDecimal(__cur_str);
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N") || __cur_str.length() == 0) { this.BJ_AM = null; } else {
      this.BJ_AM = new java.math.BigDecimal(__cur_str);
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N") || __cur_str.length() == 0) { this.ELE_TOTAL_AM = null; } else {
      this.ELE_TOTAL_AM = new java.math.BigDecimal(__cur_str);
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N") || __cur_str.length() == 0) { this.ELE_CNT = null; } else {
      this.ELE_CNT = new java.math.BigDecimal(__cur_str);
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N") || __cur_str.length() == 0) { this.ELE_BJ_AM = null; } else {
      this.ELE_BJ_AM = new java.math.BigDecimal(__cur_str);
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N") || __cur_str.length() == 0) { this.W_TOTAL_AM = null; } else {
      this.W_TOTAL_AM = new java.math.BigDecimal(__cur_str);
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N") || __cur_str.length() == 0) { this.W_CNT = null; } else {
      this.W_CNT = new java.math.BigDecimal(__cur_str);
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N") || __cur_str.length() == 0) { this.W_BJ_AM = null; } else {
      this.W_BJ_AM = new java.math.BigDecimal(__cur_str);
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N") || __cur_str.length() == 0) { this.GAS_TOTAL_AM = null; } else {
      this.GAS_TOTAL_AM = new java.math.BigDecimal(__cur_str);
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N") || __cur_str.length() == 0) { this.GAS_CNT = null; } else {
      this.GAS_CNT = new java.math.BigDecimal(__cur_str);
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N") || __cur_str.length() == 0) { this.GAS_BJ_AM = null; } else {
      this.GAS_BJ_AM = new java.math.BigDecimal(__cur_str);
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N") || __cur_str.length() == 0) { this.COMM_TOTAL_AM = null; } else {
      this.COMM_TOTAL_AM = new java.math.BigDecimal(__cur_str);
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N") || __cur_str.length() == 0) { this.COMM_CNT = null; } else {
      this.COMM_CNT = new java.math.BigDecimal(__cur_str);
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N") || __cur_str.length() == 0) { this.COMM_BJ_AM = null; } else {
      this.COMM_BJ_AM = new java.math.BigDecimal(__cur_str);
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N") || __cur_str.length() == 0) { this.TV_TOTAL_AM = null; } else {
      this.TV_TOTAL_AM = new java.math.BigDecimal(__cur_str);
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N") || __cur_str.length() == 0) { this.TV_CNT = null; } else {
      this.TV_CNT = new java.math.BigDecimal(__cur_str);
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N") || __cur_str.length() == 0) { this.TV_BJ_AM = null; } else {
      this.TV_BJ_AM = new java.math.BigDecimal(__cur_str);
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N")) { this.STARTDATE = null; } else {
      this.STARTDATE = __cur_str;
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N")) { this.ENDDATE = null; } else {
      this.ENDDATE = __cur_str;
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N")) { this.MONTH_NUM = null; } else {
      this.MONTH_NUM = __cur_str;
    }

    } catch (RuntimeException e) {    throw new RuntimeException("Can't parse input data: '" + __cur_str + "'", e);    }  }

  private void __loadFromFields0(Iterator<String> __it) {
    String __cur_str = null;
    try {
    __cur_str = __it.next();
    if (__cur_str.equals("\\N")) { this.MOBILE_NUM = null; } else {
      this.MOBILE_NUM = __cur_str;
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N")) { this.REG_DATE = null; } else {
      this.REG_DATE = __cur_str;
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N") || __cur_str.length() == 0) { this.ZW_TIME = null; } else {
      this.ZW_TIME = new java.math.BigDecimal(__cur_str);
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N") || __cur_str.length() == 0) { this.CREDIT_COUNT = null; } else {
      this.CREDIT_COUNT = new java.math.BigDecimal(__cur_str);
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N") || __cur_str.length() == 0) { this.DEBIT_COUNT = null; } else {
      this.DEBIT_COUNT = new java.math.BigDecimal(__cur_str);
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N") || __cur_str.length() == 0) { this.CREDIT_BJHK = null; } else {
      this.CREDIT_BJHK = new java.math.BigDecimal(__cur_str);
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N") || __cur_str.length() == 0) { this.CREDIT_HKMONTH = null; } else {
      this.CREDIT_HKMONTH = new java.math.BigDecimal(__cur_str);
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N") || __cur_str.length() == 0) { this.CREDIT_YJHK = null; } else {
      this.CREDIT_YJHK = new java.math.BigDecimal(__cur_str);
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N") || __cur_str.length() == 0) { this.DEBIT_IN = null; } else {
      this.DEBIT_IN = new java.math.BigDecimal(__cur_str);
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N") || __cur_str.length() == 0) { this.DEBIT_OUT = null; } else {
      this.DEBIT_OUT = new java.math.BigDecimal(__cur_str);
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N") || __cur_str.length() == 0) { this.ZZAMOUNT = null; } else {
      this.ZZAMOUNT = new java.math.BigDecimal(__cur_str);
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N") || __cur_str.length() == 0) { this.ZZNUM = null; } else {
      this.ZZNUM = new java.math.BigDecimal(__cur_str);
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N") || __cur_str.length() == 0) { this.DEBIT_ZZMONTH = null; } else {
      this.DEBIT_ZZMONTH = new java.math.BigDecimal(__cur_str);
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N") || __cur_str.length() == 0) { this.DEBIT_YJZZ = null; } else {
      this.DEBIT_YJZZ = new java.math.BigDecimal(__cur_str);
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N") || __cur_str.length() == 0) { this.DEBIT_CXMONTH = null; } else {
      this.DEBIT_CXMONTH = new java.math.BigDecimal(__cur_str);
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N") || __cur_str.length() == 0) { this.CREDIT_CXMONTH = null; } else {
      this.CREDIT_CXMONTH = new java.math.BigDecimal(__cur_str);
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N")) { this.ZDFQ_FLAG = null; } else {
      this.ZDFQ_FLAG = __cur_str;
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N") || __cur_str.length() == 0) { this.XD_COUNT = null; } else {
      this.XD_COUNT = new java.math.BigDecimal(__cur_str);
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N") || __cur_str.length() == 0) { this.XD_SUM = null; } else {
      this.XD_SUM = new java.math.BigDecimal(__cur_str);
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N") || __cur_str.length() == 0) { this.XD_AVE = null; } else {
      this.XD_AVE = new java.math.BigDecimal(__cur_str);
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N") || __cur_str.length() == 0) { this.XD_OUT_DAY_SUM = null; } else {
      this.XD_OUT_DAY_SUM = new java.math.BigDecimal(__cur_str);
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N") || __cur_str.length() == 0) { this.XD_NOSETTLE_SUM = null; } else {
      this.XD_NOSETTLE_SUM = new java.math.BigDecimal(__cur_str);
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N") || __cur_str.length() == 0) { this.XD_YQ_CNT = null; } else {
      this.XD_YQ_CNT = new java.math.BigDecimal(__cur_str);
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N")) { this.ADDR_TOP1 = null; } else {
      this.ADDR_TOP1 = __cur_str;
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N") || __cur_str.length() == 0) { this.JY_CNT_TOP1 = null; } else {
      this.JY_CNT_TOP1 = new java.math.BigDecimal(__cur_str);
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N")) { this.ADDR_TOP2 = null; } else {
      this.ADDR_TOP2 = __cur_str;
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N") || __cur_str.length() == 0) { this.JY_CNT_TOP2 = null; } else {
      this.JY_CNT_TOP2 = new java.math.BigDecimal(__cur_str);
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N")) { this.ADDR_TOP3 = null; } else {
      this.ADDR_TOP3 = __cur_str;
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N") || __cur_str.length() == 0) { this.JY_CNT_TOP3 = null; } else {
      this.JY_CNT_TOP3 = new java.math.BigDecimal(__cur_str);
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N") || __cur_str.length() == 0) { this.TOTAL_AM = null; } else {
      this.TOTAL_AM = new java.math.BigDecimal(__cur_str);
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N") || __cur_str.length() == 0) { this.CNT = null; } else {
      this.CNT = new java.math.BigDecimal(__cur_str);
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N") || __cur_str.length() == 0) { this.BJ_AM = null; } else {
      this.BJ_AM = new java.math.BigDecimal(__cur_str);
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N") || __cur_str.length() == 0) { this.ELE_TOTAL_AM = null; } else {
      this.ELE_TOTAL_AM = new java.math.BigDecimal(__cur_str);
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N") || __cur_str.length() == 0) { this.ELE_CNT = null; } else {
      this.ELE_CNT = new java.math.BigDecimal(__cur_str);
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N") || __cur_str.length() == 0) { this.ELE_BJ_AM = null; } else {
      this.ELE_BJ_AM = new java.math.BigDecimal(__cur_str);
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N") || __cur_str.length() == 0) { this.W_TOTAL_AM = null; } else {
      this.W_TOTAL_AM = new java.math.BigDecimal(__cur_str);
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N") || __cur_str.length() == 0) { this.W_CNT = null; } else {
      this.W_CNT = new java.math.BigDecimal(__cur_str);
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N") || __cur_str.length() == 0) { this.W_BJ_AM = null; } else {
      this.W_BJ_AM = new java.math.BigDecimal(__cur_str);
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N") || __cur_str.length() == 0) { this.GAS_TOTAL_AM = null; } else {
      this.GAS_TOTAL_AM = new java.math.BigDecimal(__cur_str);
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N") || __cur_str.length() == 0) { this.GAS_CNT = null; } else {
      this.GAS_CNT = new java.math.BigDecimal(__cur_str);
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N") || __cur_str.length() == 0) { this.GAS_BJ_AM = null; } else {
      this.GAS_BJ_AM = new java.math.BigDecimal(__cur_str);
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N") || __cur_str.length() == 0) { this.COMM_TOTAL_AM = null; } else {
      this.COMM_TOTAL_AM = new java.math.BigDecimal(__cur_str);
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N") || __cur_str.length() == 0) { this.COMM_CNT = null; } else {
      this.COMM_CNT = new java.math.BigDecimal(__cur_str);
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N") || __cur_str.length() == 0) { this.COMM_BJ_AM = null; } else {
      this.COMM_BJ_AM = new java.math.BigDecimal(__cur_str);
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N") || __cur_str.length() == 0) { this.TV_TOTAL_AM = null; } else {
      this.TV_TOTAL_AM = new java.math.BigDecimal(__cur_str);
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N") || __cur_str.length() == 0) { this.TV_CNT = null; } else {
      this.TV_CNT = new java.math.BigDecimal(__cur_str);
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N") || __cur_str.length() == 0) { this.TV_BJ_AM = null; } else {
      this.TV_BJ_AM = new java.math.BigDecimal(__cur_str);
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N")) { this.STARTDATE = null; } else {
      this.STARTDATE = __cur_str;
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N")) { this.ENDDATE = null; } else {
      this.ENDDATE = __cur_str;
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N")) { this.MONTH_NUM = null; } else {
      this.MONTH_NUM = __cur_str;
    }

    } catch (RuntimeException e) {    throw new RuntimeException("Can't parse input data: '" + __cur_str + "'", e);    }  }

  public Object clone() throws CloneNotSupportedException {
    T_PERSONAL_CREDIT_REPORT_INFO o = (T_PERSONAL_CREDIT_REPORT_INFO) super.clone();
    return o;
  }

  public void clone0(T_PERSONAL_CREDIT_REPORT_INFO o) throws CloneNotSupportedException {
  }

  public Map<String, Object> getFieldMap() {
    Map<String, Object> __sqoop$field_map = new TreeMap<String, Object>();
    __sqoop$field_map.put("MOBILE_NUM", this.MOBILE_NUM);
    __sqoop$field_map.put("REG_DATE", this.REG_DATE);
    __sqoop$field_map.put("ZW_TIME", this.ZW_TIME);
    __sqoop$field_map.put("CREDIT_COUNT", this.CREDIT_COUNT);
    __sqoop$field_map.put("DEBIT_COUNT", this.DEBIT_COUNT);
    __sqoop$field_map.put("CREDIT_BJHK", this.CREDIT_BJHK);
    __sqoop$field_map.put("CREDIT_HKMONTH", this.CREDIT_HKMONTH);
    __sqoop$field_map.put("CREDIT_YJHK", this.CREDIT_YJHK);
    __sqoop$field_map.put("DEBIT_IN", this.DEBIT_IN);
    __sqoop$field_map.put("DEBIT_OUT", this.DEBIT_OUT);
    __sqoop$field_map.put("ZZAMOUNT", this.ZZAMOUNT);
    __sqoop$field_map.put("ZZNUM", this.ZZNUM);
    __sqoop$field_map.put("DEBIT_ZZMONTH", this.DEBIT_ZZMONTH);
    __sqoop$field_map.put("DEBIT_YJZZ", this.DEBIT_YJZZ);
    __sqoop$field_map.put("DEBIT_CXMONTH", this.DEBIT_CXMONTH);
    __sqoop$field_map.put("CREDIT_CXMONTH", this.CREDIT_CXMONTH);
    __sqoop$field_map.put("ZDFQ_FLAG", this.ZDFQ_FLAG);
    __sqoop$field_map.put("XD_COUNT", this.XD_COUNT);
    __sqoop$field_map.put("XD_SUM", this.XD_SUM);
    __sqoop$field_map.put("XD_AVE", this.XD_AVE);
    __sqoop$field_map.put("XD_OUT_DAY_SUM", this.XD_OUT_DAY_SUM);
    __sqoop$field_map.put("XD_NOSETTLE_SUM", this.XD_NOSETTLE_SUM);
    __sqoop$field_map.put("XD_YQ_CNT", this.XD_YQ_CNT);
    __sqoop$field_map.put("ADDR_TOP1", this.ADDR_TOP1);
    __sqoop$field_map.put("JY_CNT_TOP1", this.JY_CNT_TOP1);
    __sqoop$field_map.put("ADDR_TOP2", this.ADDR_TOP2);
    __sqoop$field_map.put("JY_CNT_TOP2", this.JY_CNT_TOP2);
    __sqoop$field_map.put("ADDR_TOP3", this.ADDR_TOP3);
    __sqoop$field_map.put("JY_CNT_TOP3", this.JY_CNT_TOP3);
    __sqoop$field_map.put("TOTAL_AM", this.TOTAL_AM);
    __sqoop$field_map.put("CNT", this.CNT);
    __sqoop$field_map.put("BJ_AM", this.BJ_AM);
    __sqoop$field_map.put("ELE_TOTAL_AM", this.ELE_TOTAL_AM);
    __sqoop$field_map.put("ELE_CNT", this.ELE_CNT);
    __sqoop$field_map.put("ELE_BJ_AM", this.ELE_BJ_AM);
    __sqoop$field_map.put("W_TOTAL_AM", this.W_TOTAL_AM);
    __sqoop$field_map.put("W_CNT", this.W_CNT);
    __sqoop$field_map.put("W_BJ_AM", this.W_BJ_AM);
    __sqoop$field_map.put("GAS_TOTAL_AM", this.GAS_TOTAL_AM);
    __sqoop$field_map.put("GAS_CNT", this.GAS_CNT);
    __sqoop$field_map.put("GAS_BJ_AM", this.GAS_BJ_AM);
    __sqoop$field_map.put("COMM_TOTAL_AM", this.COMM_TOTAL_AM);
    __sqoop$field_map.put("COMM_CNT", this.COMM_CNT);
    __sqoop$field_map.put("COMM_BJ_AM", this.COMM_BJ_AM);
    __sqoop$field_map.put("TV_TOTAL_AM", this.TV_TOTAL_AM);
    __sqoop$field_map.put("TV_CNT", this.TV_CNT);
    __sqoop$field_map.put("TV_BJ_AM", this.TV_BJ_AM);
    __sqoop$field_map.put("STARTDATE", this.STARTDATE);
    __sqoop$field_map.put("ENDDATE", this.ENDDATE);
    __sqoop$field_map.put("MONTH_NUM", this.MONTH_NUM);
    return __sqoop$field_map;
  }

  public void getFieldMap0(Map<String, Object> __sqoop$field_map) {
    __sqoop$field_map.put("MOBILE_NUM", this.MOBILE_NUM);
    __sqoop$field_map.put("REG_DATE", this.REG_DATE);
    __sqoop$field_map.put("ZW_TIME", this.ZW_TIME);
    __sqoop$field_map.put("CREDIT_COUNT", this.CREDIT_COUNT);
    __sqoop$field_map.put("DEBIT_COUNT", this.DEBIT_COUNT);
    __sqoop$field_map.put("CREDIT_BJHK", this.CREDIT_BJHK);
    __sqoop$field_map.put("CREDIT_HKMONTH", this.CREDIT_HKMONTH);
    __sqoop$field_map.put("CREDIT_YJHK", this.CREDIT_YJHK);
    __sqoop$field_map.put("DEBIT_IN", this.DEBIT_IN);
    __sqoop$field_map.put("DEBIT_OUT", this.DEBIT_OUT);
    __sqoop$field_map.put("ZZAMOUNT", this.ZZAMOUNT);
    __sqoop$field_map.put("ZZNUM", this.ZZNUM);
    __sqoop$field_map.put("DEBIT_ZZMONTH", this.DEBIT_ZZMONTH);
    __sqoop$field_map.put("DEBIT_YJZZ", this.DEBIT_YJZZ);
    __sqoop$field_map.put("DEBIT_CXMONTH", this.DEBIT_CXMONTH);
    __sqoop$field_map.put("CREDIT_CXMONTH", this.CREDIT_CXMONTH);
    __sqoop$field_map.put("ZDFQ_FLAG", this.ZDFQ_FLAG);
    __sqoop$field_map.put("XD_COUNT", this.XD_COUNT);
    __sqoop$field_map.put("XD_SUM", this.XD_SUM);
    __sqoop$field_map.put("XD_AVE", this.XD_AVE);
    __sqoop$field_map.put("XD_OUT_DAY_SUM", this.XD_OUT_DAY_SUM);
    __sqoop$field_map.put("XD_NOSETTLE_SUM", this.XD_NOSETTLE_SUM);
    __sqoop$field_map.put("XD_YQ_CNT", this.XD_YQ_CNT);
    __sqoop$field_map.put("ADDR_TOP1", this.ADDR_TOP1);
    __sqoop$field_map.put("JY_CNT_TOP1", this.JY_CNT_TOP1);
    __sqoop$field_map.put("ADDR_TOP2", this.ADDR_TOP2);
    __sqoop$field_map.put("JY_CNT_TOP2", this.JY_CNT_TOP2);
    __sqoop$field_map.put("ADDR_TOP3", this.ADDR_TOP3);
    __sqoop$field_map.put("JY_CNT_TOP3", this.JY_CNT_TOP3);
    __sqoop$field_map.put("TOTAL_AM", this.TOTAL_AM);
    __sqoop$field_map.put("CNT", this.CNT);
    __sqoop$field_map.put("BJ_AM", this.BJ_AM);
    __sqoop$field_map.put("ELE_TOTAL_AM", this.ELE_TOTAL_AM);
    __sqoop$field_map.put("ELE_CNT", this.ELE_CNT);
    __sqoop$field_map.put("ELE_BJ_AM", this.ELE_BJ_AM);
    __sqoop$field_map.put("W_TOTAL_AM", this.W_TOTAL_AM);
    __sqoop$field_map.put("W_CNT", this.W_CNT);
    __sqoop$field_map.put("W_BJ_AM", this.W_BJ_AM);
    __sqoop$field_map.put("GAS_TOTAL_AM", this.GAS_TOTAL_AM);
    __sqoop$field_map.put("GAS_CNT", this.GAS_CNT);
    __sqoop$field_map.put("GAS_BJ_AM", this.GAS_BJ_AM);
    __sqoop$field_map.put("COMM_TOTAL_AM", this.COMM_TOTAL_AM);
    __sqoop$field_map.put("COMM_CNT", this.COMM_CNT);
    __sqoop$field_map.put("COMM_BJ_AM", this.COMM_BJ_AM);
    __sqoop$field_map.put("TV_TOTAL_AM", this.TV_TOTAL_AM);
    __sqoop$field_map.put("TV_CNT", this.TV_CNT);
    __sqoop$field_map.put("TV_BJ_AM", this.TV_BJ_AM);
    __sqoop$field_map.put("STARTDATE", this.STARTDATE);
    __sqoop$field_map.put("ENDDATE", this.ENDDATE);
    __sqoop$field_map.put("MONTH_NUM", this.MONTH_NUM);
  }

  public void setField(String __fieldName, Object __fieldVal) {
    if ("MOBILE_NUM".equals(__fieldName)) {
      this.MOBILE_NUM = (String) __fieldVal;
    }
    else    if ("REG_DATE".equals(__fieldName)) {
      this.REG_DATE = (String) __fieldVal;
    }
    else    if ("ZW_TIME".equals(__fieldName)) {
      this.ZW_TIME = (java.math.BigDecimal) __fieldVal;
    }
    else    if ("CREDIT_COUNT".equals(__fieldName)) {
      this.CREDIT_COUNT = (java.math.BigDecimal) __fieldVal;
    }
    else    if ("DEBIT_COUNT".equals(__fieldName)) {
      this.DEBIT_COUNT = (java.math.BigDecimal) __fieldVal;
    }
    else    if ("CREDIT_BJHK".equals(__fieldName)) {
      this.CREDIT_BJHK = (java.math.BigDecimal) __fieldVal;
    }
    else    if ("CREDIT_HKMONTH".equals(__fieldName)) {
      this.CREDIT_HKMONTH = (java.math.BigDecimal) __fieldVal;
    }
    else    if ("CREDIT_YJHK".equals(__fieldName)) {
      this.CREDIT_YJHK = (java.math.BigDecimal) __fieldVal;
    }
    else    if ("DEBIT_IN".equals(__fieldName)) {
      this.DEBIT_IN = (java.math.BigDecimal) __fieldVal;
    }
    else    if ("DEBIT_OUT".equals(__fieldName)) {
      this.DEBIT_OUT = (java.math.BigDecimal) __fieldVal;
    }
    else    if ("ZZAMOUNT".equals(__fieldName)) {
      this.ZZAMOUNT = (java.math.BigDecimal) __fieldVal;
    }
    else    if ("ZZNUM".equals(__fieldName)) {
      this.ZZNUM = (java.math.BigDecimal) __fieldVal;
    }
    else    if ("DEBIT_ZZMONTH".equals(__fieldName)) {
      this.DEBIT_ZZMONTH = (java.math.BigDecimal) __fieldVal;
    }
    else    if ("DEBIT_YJZZ".equals(__fieldName)) {
      this.DEBIT_YJZZ = (java.math.BigDecimal) __fieldVal;
    }
    else    if ("DEBIT_CXMONTH".equals(__fieldName)) {
      this.DEBIT_CXMONTH = (java.math.BigDecimal) __fieldVal;
    }
    else    if ("CREDIT_CXMONTH".equals(__fieldName)) {
      this.CREDIT_CXMONTH = (java.math.BigDecimal) __fieldVal;
    }
    else    if ("ZDFQ_FLAG".equals(__fieldName)) {
      this.ZDFQ_FLAG = (String) __fieldVal;
    }
    else    if ("XD_COUNT".equals(__fieldName)) {
      this.XD_COUNT = (java.math.BigDecimal) __fieldVal;
    }
    else    if ("XD_SUM".equals(__fieldName)) {
      this.XD_SUM = (java.math.BigDecimal) __fieldVal;
    }
    else    if ("XD_AVE".equals(__fieldName)) {
      this.XD_AVE = (java.math.BigDecimal) __fieldVal;
    }
    else    if ("XD_OUT_DAY_SUM".equals(__fieldName)) {
      this.XD_OUT_DAY_SUM = (java.math.BigDecimal) __fieldVal;
    }
    else    if ("XD_NOSETTLE_SUM".equals(__fieldName)) {
      this.XD_NOSETTLE_SUM = (java.math.BigDecimal) __fieldVal;
    }
    else    if ("XD_YQ_CNT".equals(__fieldName)) {
      this.XD_YQ_CNT = (java.math.BigDecimal) __fieldVal;
    }
    else    if ("ADDR_TOP1".equals(__fieldName)) {
      this.ADDR_TOP1 = (String) __fieldVal;
    }
    else    if ("JY_CNT_TOP1".equals(__fieldName)) {
      this.JY_CNT_TOP1 = (java.math.BigDecimal) __fieldVal;
    }
    else    if ("ADDR_TOP2".equals(__fieldName)) {
      this.ADDR_TOP2 = (String) __fieldVal;
    }
    else    if ("JY_CNT_TOP2".equals(__fieldName)) {
      this.JY_CNT_TOP2 = (java.math.BigDecimal) __fieldVal;
    }
    else    if ("ADDR_TOP3".equals(__fieldName)) {
      this.ADDR_TOP3 = (String) __fieldVal;
    }
    else    if ("JY_CNT_TOP3".equals(__fieldName)) {
      this.JY_CNT_TOP3 = (java.math.BigDecimal) __fieldVal;
    }
    else    if ("TOTAL_AM".equals(__fieldName)) {
      this.TOTAL_AM = (java.math.BigDecimal) __fieldVal;
    }
    else    if ("CNT".equals(__fieldName)) {
      this.CNT = (java.math.BigDecimal) __fieldVal;
    }
    else    if ("BJ_AM".equals(__fieldName)) {
      this.BJ_AM = (java.math.BigDecimal) __fieldVal;
    }
    else    if ("ELE_TOTAL_AM".equals(__fieldName)) {
      this.ELE_TOTAL_AM = (java.math.BigDecimal) __fieldVal;
    }
    else    if ("ELE_CNT".equals(__fieldName)) {
      this.ELE_CNT = (java.math.BigDecimal) __fieldVal;
    }
    else    if ("ELE_BJ_AM".equals(__fieldName)) {
      this.ELE_BJ_AM = (java.math.BigDecimal) __fieldVal;
    }
    else    if ("W_TOTAL_AM".equals(__fieldName)) {
      this.W_TOTAL_AM = (java.math.BigDecimal) __fieldVal;
    }
    else    if ("W_CNT".equals(__fieldName)) {
      this.W_CNT = (java.math.BigDecimal) __fieldVal;
    }
    else    if ("W_BJ_AM".equals(__fieldName)) {
      this.W_BJ_AM = (java.math.BigDecimal) __fieldVal;
    }
    else    if ("GAS_TOTAL_AM".equals(__fieldName)) {
      this.GAS_TOTAL_AM = (java.math.BigDecimal) __fieldVal;
    }
    else    if ("GAS_CNT".equals(__fieldName)) {
      this.GAS_CNT = (java.math.BigDecimal) __fieldVal;
    }
    else    if ("GAS_BJ_AM".equals(__fieldName)) {
      this.GAS_BJ_AM = (java.math.BigDecimal) __fieldVal;
    }
    else    if ("COMM_TOTAL_AM".equals(__fieldName)) {
      this.COMM_TOTAL_AM = (java.math.BigDecimal) __fieldVal;
    }
    else    if ("COMM_CNT".equals(__fieldName)) {
      this.COMM_CNT = (java.math.BigDecimal) __fieldVal;
    }
    else    if ("COMM_BJ_AM".equals(__fieldName)) {
      this.COMM_BJ_AM = (java.math.BigDecimal) __fieldVal;
    }
    else    if ("TV_TOTAL_AM".equals(__fieldName)) {
      this.TV_TOTAL_AM = (java.math.BigDecimal) __fieldVal;
    }
    else    if ("TV_CNT".equals(__fieldName)) {
      this.TV_CNT = (java.math.BigDecimal) __fieldVal;
    }
    else    if ("TV_BJ_AM".equals(__fieldName)) {
      this.TV_BJ_AM = (java.math.BigDecimal) __fieldVal;
    }
    else    if ("STARTDATE".equals(__fieldName)) {
      this.STARTDATE = (String) __fieldVal;
    }
    else    if ("ENDDATE".equals(__fieldName)) {
      this.ENDDATE = (String) __fieldVal;
    }
    else    if ("MONTH_NUM".equals(__fieldName)) {
      this.MONTH_NUM = (String) __fieldVal;
    }
    else {
      throw new RuntimeException("No such field: " + __fieldName);
    }
  }
  public boolean setField0(String __fieldName, Object __fieldVal) {
    if ("MOBILE_NUM".equals(__fieldName)) {
      this.MOBILE_NUM = (String) __fieldVal;
      return true;
    }
    else    if ("REG_DATE".equals(__fieldName)) {
      this.REG_DATE = (String) __fieldVal;
      return true;
    }
    else    if ("ZW_TIME".equals(__fieldName)) {
      this.ZW_TIME = (java.math.BigDecimal) __fieldVal;
      return true;
    }
    else    if ("CREDIT_COUNT".equals(__fieldName)) {
      this.CREDIT_COUNT = (java.math.BigDecimal) __fieldVal;
      return true;
    }
    else    if ("DEBIT_COUNT".equals(__fieldName)) {
      this.DEBIT_COUNT = (java.math.BigDecimal) __fieldVal;
      return true;
    }
    else    if ("CREDIT_BJHK".equals(__fieldName)) {
      this.CREDIT_BJHK = (java.math.BigDecimal) __fieldVal;
      return true;
    }
    else    if ("CREDIT_HKMONTH".equals(__fieldName)) {
      this.CREDIT_HKMONTH = (java.math.BigDecimal) __fieldVal;
      return true;
    }
    else    if ("CREDIT_YJHK".equals(__fieldName)) {
      this.CREDIT_YJHK = (java.math.BigDecimal) __fieldVal;
      return true;
    }
    else    if ("DEBIT_IN".equals(__fieldName)) {
      this.DEBIT_IN = (java.math.BigDecimal) __fieldVal;
      return true;
    }
    else    if ("DEBIT_OUT".equals(__fieldName)) {
      this.DEBIT_OUT = (java.math.BigDecimal) __fieldVal;
      return true;
    }
    else    if ("ZZAMOUNT".equals(__fieldName)) {
      this.ZZAMOUNT = (java.math.BigDecimal) __fieldVal;
      return true;
    }
    else    if ("ZZNUM".equals(__fieldName)) {
      this.ZZNUM = (java.math.BigDecimal) __fieldVal;
      return true;
    }
    else    if ("DEBIT_ZZMONTH".equals(__fieldName)) {
      this.DEBIT_ZZMONTH = (java.math.BigDecimal) __fieldVal;
      return true;
    }
    else    if ("DEBIT_YJZZ".equals(__fieldName)) {
      this.DEBIT_YJZZ = (java.math.BigDecimal) __fieldVal;
      return true;
    }
    else    if ("DEBIT_CXMONTH".equals(__fieldName)) {
      this.DEBIT_CXMONTH = (java.math.BigDecimal) __fieldVal;
      return true;
    }
    else    if ("CREDIT_CXMONTH".equals(__fieldName)) {
      this.CREDIT_CXMONTH = (java.math.BigDecimal) __fieldVal;
      return true;
    }
    else    if ("ZDFQ_FLAG".equals(__fieldName)) {
      this.ZDFQ_FLAG = (String) __fieldVal;
      return true;
    }
    else    if ("XD_COUNT".equals(__fieldName)) {
      this.XD_COUNT = (java.math.BigDecimal) __fieldVal;
      return true;
    }
    else    if ("XD_SUM".equals(__fieldName)) {
      this.XD_SUM = (java.math.BigDecimal) __fieldVal;
      return true;
    }
    else    if ("XD_AVE".equals(__fieldName)) {
      this.XD_AVE = (java.math.BigDecimal) __fieldVal;
      return true;
    }
    else    if ("XD_OUT_DAY_SUM".equals(__fieldName)) {
      this.XD_OUT_DAY_SUM = (java.math.BigDecimal) __fieldVal;
      return true;
    }
    else    if ("XD_NOSETTLE_SUM".equals(__fieldName)) {
      this.XD_NOSETTLE_SUM = (java.math.BigDecimal) __fieldVal;
      return true;
    }
    else    if ("XD_YQ_CNT".equals(__fieldName)) {
      this.XD_YQ_CNT = (java.math.BigDecimal) __fieldVal;
      return true;
    }
    else    if ("ADDR_TOP1".equals(__fieldName)) {
      this.ADDR_TOP1 = (String) __fieldVal;
      return true;
    }
    else    if ("JY_CNT_TOP1".equals(__fieldName)) {
      this.JY_CNT_TOP1 = (java.math.BigDecimal) __fieldVal;
      return true;
    }
    else    if ("ADDR_TOP2".equals(__fieldName)) {
      this.ADDR_TOP2 = (String) __fieldVal;
      return true;
    }
    else    if ("JY_CNT_TOP2".equals(__fieldName)) {
      this.JY_CNT_TOP2 = (java.math.BigDecimal) __fieldVal;
      return true;
    }
    else    if ("ADDR_TOP3".equals(__fieldName)) {
      this.ADDR_TOP3 = (String) __fieldVal;
      return true;
    }
    else    if ("JY_CNT_TOP3".equals(__fieldName)) {
      this.JY_CNT_TOP3 = (java.math.BigDecimal) __fieldVal;
      return true;
    }
    else    if ("TOTAL_AM".equals(__fieldName)) {
      this.TOTAL_AM = (java.math.BigDecimal) __fieldVal;
      return true;
    }
    else    if ("CNT".equals(__fieldName)) {
      this.CNT = (java.math.BigDecimal) __fieldVal;
      return true;
    }
    else    if ("BJ_AM".equals(__fieldName)) {
      this.BJ_AM = (java.math.BigDecimal) __fieldVal;
      return true;
    }
    else    if ("ELE_TOTAL_AM".equals(__fieldName)) {
      this.ELE_TOTAL_AM = (java.math.BigDecimal) __fieldVal;
      return true;
    }
    else    if ("ELE_CNT".equals(__fieldName)) {
      this.ELE_CNT = (java.math.BigDecimal) __fieldVal;
      return true;
    }
    else    if ("ELE_BJ_AM".equals(__fieldName)) {
      this.ELE_BJ_AM = (java.math.BigDecimal) __fieldVal;
      return true;
    }
    else    if ("W_TOTAL_AM".equals(__fieldName)) {
      this.W_TOTAL_AM = (java.math.BigDecimal) __fieldVal;
      return true;
    }
    else    if ("W_CNT".equals(__fieldName)) {
      this.W_CNT = (java.math.BigDecimal) __fieldVal;
      return true;
    }
    else    if ("W_BJ_AM".equals(__fieldName)) {
      this.W_BJ_AM = (java.math.BigDecimal) __fieldVal;
      return true;
    }
    else    if ("GAS_TOTAL_AM".equals(__fieldName)) {
      this.GAS_TOTAL_AM = (java.math.BigDecimal) __fieldVal;
      return true;
    }
    else    if ("GAS_CNT".equals(__fieldName)) {
      this.GAS_CNT = (java.math.BigDecimal) __fieldVal;
      return true;
    }
    else    if ("GAS_BJ_AM".equals(__fieldName)) {
      this.GAS_BJ_AM = (java.math.BigDecimal) __fieldVal;
      return true;
    }
    else    if ("COMM_TOTAL_AM".equals(__fieldName)) {
      this.COMM_TOTAL_AM = (java.math.BigDecimal) __fieldVal;
      return true;
    }
    else    if ("COMM_CNT".equals(__fieldName)) {
      this.COMM_CNT = (java.math.BigDecimal) __fieldVal;
      return true;
    }
    else    if ("COMM_BJ_AM".equals(__fieldName)) {
      this.COMM_BJ_AM = (java.math.BigDecimal) __fieldVal;
      return true;
    }
    else    if ("TV_TOTAL_AM".equals(__fieldName)) {
      this.TV_TOTAL_AM = (java.math.BigDecimal) __fieldVal;
      return true;
    }
    else    if ("TV_CNT".equals(__fieldName)) {
      this.TV_CNT = (java.math.BigDecimal) __fieldVal;
      return true;
    }
    else    if ("TV_BJ_AM".equals(__fieldName)) {
      this.TV_BJ_AM = (java.math.BigDecimal) __fieldVal;
      return true;
    }
    else    if ("STARTDATE".equals(__fieldName)) {
      this.STARTDATE = (String) __fieldVal;
      return true;
    }
    else    if ("ENDDATE".equals(__fieldName)) {
      this.ENDDATE = (String) __fieldVal;
      return true;
    }
    else    if ("MONTH_NUM".equals(__fieldName)) {
      this.MONTH_NUM = (String) __fieldVal;
      return true;
    }
    else {
      return false;    }
  }
}
