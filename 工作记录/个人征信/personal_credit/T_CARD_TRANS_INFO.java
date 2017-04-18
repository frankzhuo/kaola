// ORM class for table 'T_CARD_TRANS_INFO'
// WARNING: This class is AUTO-GENERATED. Modify at your own risk.
//
// Debug information:
// Generated date: Fri Aug 21 22:23:36 CST 2015
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

public class T_CARD_TRANS_INFO extends SqoopRecord  implements DBWritable, Writable {
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
  public T_CARD_TRANS_INFO with_MOBILE_NUM(String MOBILE_NUM) {
    this.MOBILE_NUM = MOBILE_NUM;
    return this;
  }
  private String OUTCDNO;
  public String get_OUTCDNO() {
    return OUTCDNO;
  }
  public void set_OUTCDNO(String OUTCDNO) {
    this.OUTCDNO = OUTCDNO;
  }
  public T_CARD_TRANS_INFO with_OUTCDNO(String OUTCDNO) {
    this.OUTCDNO = OUTCDNO;
    return this;
  }
  private String OUT_BANK_NAME;
  public String get_OUT_BANK_NAME() {
    return OUT_BANK_NAME;
  }
  public void set_OUT_BANK_NAME(String OUT_BANK_NAME) {
    this.OUT_BANK_NAME = OUT_BANK_NAME;
  }
  public T_CARD_TRANS_INFO with_OUT_BANK_NAME(String OUT_BANK_NAME) {
    this.OUT_BANK_NAME = OUT_BANK_NAME;
    return this;
  }
  private java.math.BigDecimal ZZ_AMT;
  public java.math.BigDecimal get_ZZ_AMT() {
    return ZZ_AMT;
  }
  public void set_ZZ_AMT(java.math.BigDecimal ZZ_AMT) {
    this.ZZ_AMT = ZZ_AMT;
  }
  public T_CARD_TRANS_INFO with_ZZ_AMT(java.math.BigDecimal ZZ_AMT) {
    this.ZZ_AMT = ZZ_AMT;
    return this;
  }
  private java.math.BigDecimal ZZ_CNT;
  public java.math.BigDecimal get_ZZ_CNT() {
    return ZZ_CNT;
  }
  public void set_ZZ_CNT(java.math.BigDecimal ZZ_CNT) {
    this.ZZ_CNT = ZZ_CNT;
  }
  public T_CARD_TRANS_INFO with_ZZ_CNT(java.math.BigDecimal ZZ_CNT) {
    this.ZZ_CNT = ZZ_CNT;
    return this;
  }
  private java.math.BigDecimal YJZZ_AMT;
  public java.math.BigDecimal get_YJZZ_AMT() {
    return YJZZ_AMT;
  }
  public void set_YJZZ_AMT(java.math.BigDecimal YJZZ_AMT) {
    this.YJZZ_AMT = YJZZ_AMT;
  }
  public T_CARD_TRANS_INFO with_YJZZ_AMT(java.math.BigDecimal YJZZ_AMT) {
    this.YJZZ_AMT = YJZZ_AMT;
    return this;
  }
  private java.math.BigDecimal HK_MONTH_NUM;
  public java.math.BigDecimal get_HK_MONTH_NUM() {
    return HK_MONTH_NUM;
  }
  public void set_HK_MONTH_NUM(java.math.BigDecimal HK_MONTH_NUM) {
    this.HK_MONTH_NUM = HK_MONTH_NUM;
  }
  public T_CARD_TRANS_INFO with_HK_MONTH_NUM(java.math.BigDecimal HK_MONTH_NUM) {
    this.HK_MONTH_NUM = HK_MONTH_NUM;
    return this;
  }
  private java.math.BigDecimal YJHK_AMT;
  public java.math.BigDecimal get_YJHK_AMT() {
    return YJHK_AMT;
  }
  public void set_YJHK_AMT(java.math.BigDecimal YJHK_AMT) {
    this.YJHK_AMT = YJHK_AMT;
  }
  public T_CARD_TRANS_INFO with_YJHK_AMT(java.math.BigDecimal YJHK_AMT) {
    this.YJHK_AMT = YJHK_AMT;
    return this;
  }
  private java.math.BigDecimal BJHK_AMT;
  public java.math.BigDecimal get_BJHK_AMT() {
    return BJHK_AMT;
  }
  public void set_BJHK_AMT(java.math.BigDecimal BJHK_AMT) {
    this.BJHK_AMT = BJHK_AMT;
  }
  public T_CARD_TRANS_INFO with_BJHK_AMT(java.math.BigDecimal BJHK_AMT) {
    this.BJHK_AMT = BJHK_AMT;
    return this;
  }
  private String STARTDATE;
  public String get_STARTDATE() {
    return STARTDATE;
  }
  public void set_STARTDATE(String STARTDATE) {
    this.STARTDATE = STARTDATE;
  }
  public T_CARD_TRANS_INFO with_STARTDATE(String STARTDATE) {
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
  public T_CARD_TRANS_INFO with_ENDDATE(String ENDDATE) {
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
  public T_CARD_TRANS_INFO with_MONTH_NUM(String MONTH_NUM) {
    this.MONTH_NUM = MONTH_NUM;
    return this;
  }
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof T_CARD_TRANS_INFO)) {
      return false;
    }
    T_CARD_TRANS_INFO that = (T_CARD_TRANS_INFO) o;
    boolean equal = true;
    equal = equal && (this.MOBILE_NUM == null ? that.MOBILE_NUM == null : this.MOBILE_NUM.equals(that.MOBILE_NUM));
    equal = equal && (this.OUTCDNO == null ? that.OUTCDNO == null : this.OUTCDNO.equals(that.OUTCDNO));
    equal = equal && (this.OUT_BANK_NAME == null ? that.OUT_BANK_NAME == null : this.OUT_BANK_NAME.equals(that.OUT_BANK_NAME));
    equal = equal && (this.ZZ_AMT == null ? that.ZZ_AMT == null : this.ZZ_AMT.equals(that.ZZ_AMT));
    equal = equal && (this.ZZ_CNT == null ? that.ZZ_CNT == null : this.ZZ_CNT.equals(that.ZZ_CNT));
    equal = equal && (this.YJZZ_AMT == null ? that.YJZZ_AMT == null : this.YJZZ_AMT.equals(that.YJZZ_AMT));
    equal = equal && (this.HK_MONTH_NUM == null ? that.HK_MONTH_NUM == null : this.HK_MONTH_NUM.equals(that.HK_MONTH_NUM));
    equal = equal && (this.YJHK_AMT == null ? that.YJHK_AMT == null : this.YJHK_AMT.equals(that.YJHK_AMT));
    equal = equal && (this.BJHK_AMT == null ? that.BJHK_AMT == null : this.BJHK_AMT.equals(that.BJHK_AMT));
    equal = equal && (this.STARTDATE == null ? that.STARTDATE == null : this.STARTDATE.equals(that.STARTDATE));
    equal = equal && (this.ENDDATE == null ? that.ENDDATE == null : this.ENDDATE.equals(that.ENDDATE));
    equal = equal && (this.MONTH_NUM == null ? that.MONTH_NUM == null : this.MONTH_NUM.equals(that.MONTH_NUM));
    return equal;
  }
  public boolean equals0(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof T_CARD_TRANS_INFO)) {
      return false;
    }
    T_CARD_TRANS_INFO that = (T_CARD_TRANS_INFO) o;
    boolean equal = true;
    equal = equal && (this.MOBILE_NUM == null ? that.MOBILE_NUM == null : this.MOBILE_NUM.equals(that.MOBILE_NUM));
    equal = equal && (this.OUTCDNO == null ? that.OUTCDNO == null : this.OUTCDNO.equals(that.OUTCDNO));
    equal = equal && (this.OUT_BANK_NAME == null ? that.OUT_BANK_NAME == null : this.OUT_BANK_NAME.equals(that.OUT_BANK_NAME));
    equal = equal && (this.ZZ_AMT == null ? that.ZZ_AMT == null : this.ZZ_AMT.equals(that.ZZ_AMT));
    equal = equal && (this.ZZ_CNT == null ? that.ZZ_CNT == null : this.ZZ_CNT.equals(that.ZZ_CNT));
    equal = equal && (this.YJZZ_AMT == null ? that.YJZZ_AMT == null : this.YJZZ_AMT.equals(that.YJZZ_AMT));
    equal = equal && (this.HK_MONTH_NUM == null ? that.HK_MONTH_NUM == null : this.HK_MONTH_NUM.equals(that.HK_MONTH_NUM));
    equal = equal && (this.YJHK_AMT == null ? that.YJHK_AMT == null : this.YJHK_AMT.equals(that.YJHK_AMT));
    equal = equal && (this.BJHK_AMT == null ? that.BJHK_AMT == null : this.BJHK_AMT.equals(that.BJHK_AMT));
    equal = equal && (this.STARTDATE == null ? that.STARTDATE == null : this.STARTDATE.equals(that.STARTDATE));
    equal = equal && (this.ENDDATE == null ? that.ENDDATE == null : this.ENDDATE.equals(that.ENDDATE));
    equal = equal && (this.MONTH_NUM == null ? that.MONTH_NUM == null : this.MONTH_NUM.equals(that.MONTH_NUM));
    return equal;
  }
  public void readFields(ResultSet __dbResults) throws SQLException {
    this.__cur_result_set = __dbResults;
    this.MOBILE_NUM = JdbcWritableBridge.readString(1, __dbResults);
    this.OUTCDNO = JdbcWritableBridge.readString(2, __dbResults);
    this.OUT_BANK_NAME = JdbcWritableBridge.readString(3, __dbResults);
    this.ZZ_AMT = JdbcWritableBridge.readBigDecimal(4, __dbResults);
    this.ZZ_CNT = JdbcWritableBridge.readBigDecimal(5, __dbResults);
    this.YJZZ_AMT = JdbcWritableBridge.readBigDecimal(6, __dbResults);
    this.HK_MONTH_NUM = JdbcWritableBridge.readBigDecimal(7, __dbResults);
    this.YJHK_AMT = JdbcWritableBridge.readBigDecimal(8, __dbResults);
    this.BJHK_AMT = JdbcWritableBridge.readBigDecimal(9, __dbResults);
    this.STARTDATE = JdbcWritableBridge.readString(10, __dbResults);
    this.ENDDATE = JdbcWritableBridge.readString(11, __dbResults);
    this.MONTH_NUM = JdbcWritableBridge.readString(12, __dbResults);
  }
  public void readFields0(ResultSet __dbResults) throws SQLException {
    this.MOBILE_NUM = JdbcWritableBridge.readString(1, __dbResults);
    this.OUTCDNO = JdbcWritableBridge.readString(2, __dbResults);
    this.OUT_BANK_NAME = JdbcWritableBridge.readString(3, __dbResults);
    this.ZZ_AMT = JdbcWritableBridge.readBigDecimal(4, __dbResults);
    this.ZZ_CNT = JdbcWritableBridge.readBigDecimal(5, __dbResults);
    this.YJZZ_AMT = JdbcWritableBridge.readBigDecimal(6, __dbResults);
    this.HK_MONTH_NUM = JdbcWritableBridge.readBigDecimal(7, __dbResults);
    this.YJHK_AMT = JdbcWritableBridge.readBigDecimal(8, __dbResults);
    this.BJHK_AMT = JdbcWritableBridge.readBigDecimal(9, __dbResults);
    this.STARTDATE = JdbcWritableBridge.readString(10, __dbResults);
    this.ENDDATE = JdbcWritableBridge.readString(11, __dbResults);
    this.MONTH_NUM = JdbcWritableBridge.readString(12, __dbResults);
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
    JdbcWritableBridge.writeString(OUTCDNO, 2 + __off, 12, __dbStmt);
    JdbcWritableBridge.writeString(OUT_BANK_NAME, 3 + __off, 12, __dbStmt);
    JdbcWritableBridge.writeBigDecimal(ZZ_AMT, 4 + __off, 2, __dbStmt);
    JdbcWritableBridge.writeBigDecimal(ZZ_CNT, 5 + __off, 2, __dbStmt);
    JdbcWritableBridge.writeBigDecimal(YJZZ_AMT, 6 + __off, 2, __dbStmt);
    JdbcWritableBridge.writeBigDecimal(HK_MONTH_NUM, 7 + __off, 2, __dbStmt);
    JdbcWritableBridge.writeBigDecimal(YJHK_AMT, 8 + __off, 2, __dbStmt);
    JdbcWritableBridge.writeBigDecimal(BJHK_AMT, 9 + __off, 2, __dbStmt);
    JdbcWritableBridge.writeString(STARTDATE, 10 + __off, 12, __dbStmt);
    JdbcWritableBridge.writeString(ENDDATE, 11 + __off, 12, __dbStmt);
    JdbcWritableBridge.writeString(MONTH_NUM, 12 + __off, 12, __dbStmt);
    return 12;
  }
  public void write0(PreparedStatement __dbStmt, int __off) throws SQLException {
    JdbcWritableBridge.writeString(MOBILE_NUM, 1 + __off, 12, __dbStmt);
    JdbcWritableBridge.writeString(OUTCDNO, 2 + __off, 12, __dbStmt);
    JdbcWritableBridge.writeString(OUT_BANK_NAME, 3 + __off, 12, __dbStmt);
    JdbcWritableBridge.writeBigDecimal(ZZ_AMT, 4 + __off, 2, __dbStmt);
    JdbcWritableBridge.writeBigDecimal(ZZ_CNT, 5 + __off, 2, __dbStmt);
    JdbcWritableBridge.writeBigDecimal(YJZZ_AMT, 6 + __off, 2, __dbStmt);
    JdbcWritableBridge.writeBigDecimal(HK_MONTH_NUM, 7 + __off, 2, __dbStmt);
    JdbcWritableBridge.writeBigDecimal(YJHK_AMT, 8 + __off, 2, __dbStmt);
    JdbcWritableBridge.writeBigDecimal(BJHK_AMT, 9 + __off, 2, __dbStmt);
    JdbcWritableBridge.writeString(STARTDATE, 10 + __off, 12, __dbStmt);
    JdbcWritableBridge.writeString(ENDDATE, 11 + __off, 12, __dbStmt);
    JdbcWritableBridge.writeString(MONTH_NUM, 12 + __off, 12, __dbStmt);
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
        this.OUTCDNO = null;
    } else {
    this.OUTCDNO = Text.readString(__dataIn);
    }
    if (__dataIn.readBoolean()) { 
        this.OUT_BANK_NAME = null;
    } else {
    this.OUT_BANK_NAME = Text.readString(__dataIn);
    }
    if (__dataIn.readBoolean()) { 
        this.ZZ_AMT = null;
    } else {
    this.ZZ_AMT = com.cloudera.sqoop.lib.BigDecimalSerializer.readFields(__dataIn);
    }
    if (__dataIn.readBoolean()) { 
        this.ZZ_CNT = null;
    } else {
    this.ZZ_CNT = com.cloudera.sqoop.lib.BigDecimalSerializer.readFields(__dataIn);
    }
    if (__dataIn.readBoolean()) { 
        this.YJZZ_AMT = null;
    } else {
    this.YJZZ_AMT = com.cloudera.sqoop.lib.BigDecimalSerializer.readFields(__dataIn);
    }
    if (__dataIn.readBoolean()) { 
        this.HK_MONTH_NUM = null;
    } else {
    this.HK_MONTH_NUM = com.cloudera.sqoop.lib.BigDecimalSerializer.readFields(__dataIn);
    }
    if (__dataIn.readBoolean()) { 
        this.YJHK_AMT = null;
    } else {
    this.YJHK_AMT = com.cloudera.sqoop.lib.BigDecimalSerializer.readFields(__dataIn);
    }
    if (__dataIn.readBoolean()) { 
        this.BJHK_AMT = null;
    } else {
    this.BJHK_AMT = com.cloudera.sqoop.lib.BigDecimalSerializer.readFields(__dataIn);
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
    if (null == this.OUTCDNO) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    Text.writeString(__dataOut, OUTCDNO);
    }
    if (null == this.OUT_BANK_NAME) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    Text.writeString(__dataOut, OUT_BANK_NAME);
    }
    if (null == this.ZZ_AMT) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    com.cloudera.sqoop.lib.BigDecimalSerializer.write(this.ZZ_AMT, __dataOut);
    }
    if (null == this.ZZ_CNT) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    com.cloudera.sqoop.lib.BigDecimalSerializer.write(this.ZZ_CNT, __dataOut);
    }
    if (null == this.YJZZ_AMT) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    com.cloudera.sqoop.lib.BigDecimalSerializer.write(this.YJZZ_AMT, __dataOut);
    }
    if (null == this.HK_MONTH_NUM) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    com.cloudera.sqoop.lib.BigDecimalSerializer.write(this.HK_MONTH_NUM, __dataOut);
    }
    if (null == this.YJHK_AMT) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    com.cloudera.sqoop.lib.BigDecimalSerializer.write(this.YJHK_AMT, __dataOut);
    }
    if (null == this.BJHK_AMT) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    com.cloudera.sqoop.lib.BigDecimalSerializer.write(this.BJHK_AMT, __dataOut);
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
    if (null == this.OUTCDNO) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    Text.writeString(__dataOut, OUTCDNO);
    }
    if (null == this.OUT_BANK_NAME) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    Text.writeString(__dataOut, OUT_BANK_NAME);
    }
    if (null == this.ZZ_AMT) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    com.cloudera.sqoop.lib.BigDecimalSerializer.write(this.ZZ_AMT, __dataOut);
    }
    if (null == this.ZZ_CNT) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    com.cloudera.sqoop.lib.BigDecimalSerializer.write(this.ZZ_CNT, __dataOut);
    }
    if (null == this.YJZZ_AMT) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    com.cloudera.sqoop.lib.BigDecimalSerializer.write(this.YJZZ_AMT, __dataOut);
    }
    if (null == this.HK_MONTH_NUM) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    com.cloudera.sqoop.lib.BigDecimalSerializer.write(this.HK_MONTH_NUM, __dataOut);
    }
    if (null == this.YJHK_AMT) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    com.cloudera.sqoop.lib.BigDecimalSerializer.write(this.YJHK_AMT, __dataOut);
    }
    if (null == this.BJHK_AMT) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    com.cloudera.sqoop.lib.BigDecimalSerializer.write(this.BJHK_AMT, __dataOut);
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
    __sb.append(FieldFormatter.escapeAndEnclose(OUTCDNO==null?"null":OUTCDNO, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(OUT_BANK_NAME==null?"null":OUT_BANK_NAME, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(ZZ_AMT==null?"null":ZZ_AMT.toPlainString(), delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(ZZ_CNT==null?"null":ZZ_CNT.toPlainString(), delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(YJZZ_AMT==null?"null":YJZZ_AMT.toPlainString(), delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(HK_MONTH_NUM==null?"null":HK_MONTH_NUM.toPlainString(), delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(YJHK_AMT==null?"null":YJHK_AMT.toPlainString(), delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(BJHK_AMT==null?"null":BJHK_AMT.toPlainString(), delimiters));
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
    __sb.append(FieldFormatter.escapeAndEnclose(OUTCDNO==null?"null":OUTCDNO, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(OUT_BANK_NAME==null?"null":OUT_BANK_NAME, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(ZZ_AMT==null?"null":ZZ_AMT.toPlainString(), delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(ZZ_CNT==null?"null":ZZ_CNT.toPlainString(), delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(YJZZ_AMT==null?"null":YJZZ_AMT.toPlainString(), delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(HK_MONTH_NUM==null?"null":HK_MONTH_NUM.toPlainString(), delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(YJHK_AMT==null?"null":YJHK_AMT.toPlainString(), delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(BJHK_AMT==null?"null":BJHK_AMT.toPlainString(), delimiters));
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
    if (__cur_str.equals("\\N")) { this.OUTCDNO = null; } else {
      this.OUTCDNO = __cur_str;
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N")) { this.OUT_BANK_NAME = null; } else {
      this.OUT_BANK_NAME = __cur_str;
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N") || __cur_str.length() == 0) { this.ZZ_AMT = null; } else {
      this.ZZ_AMT = new java.math.BigDecimal(__cur_str);
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N") || __cur_str.length() == 0) { this.ZZ_CNT = null; } else {
      this.ZZ_CNT = new java.math.BigDecimal(__cur_str);
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N") || __cur_str.length() == 0) { this.YJZZ_AMT = null; } else {
      this.YJZZ_AMT = new java.math.BigDecimal(__cur_str);
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N") || __cur_str.length() == 0) { this.HK_MONTH_NUM = null; } else {
      this.HK_MONTH_NUM = new java.math.BigDecimal(__cur_str);
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N") || __cur_str.length() == 0) { this.YJHK_AMT = null; } else {
      this.YJHK_AMT = new java.math.BigDecimal(__cur_str);
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N") || __cur_str.length() == 0) { this.BJHK_AMT = null; } else {
      this.BJHK_AMT = new java.math.BigDecimal(__cur_str);
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
    if (__cur_str.equals("\\N")) { this.OUTCDNO = null; } else {
      this.OUTCDNO = __cur_str;
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N")) { this.OUT_BANK_NAME = null; } else {
      this.OUT_BANK_NAME = __cur_str;
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N") || __cur_str.length() == 0) { this.ZZ_AMT = null; } else {
      this.ZZ_AMT = new java.math.BigDecimal(__cur_str);
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N") || __cur_str.length() == 0) { this.ZZ_CNT = null; } else {
      this.ZZ_CNT = new java.math.BigDecimal(__cur_str);
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N") || __cur_str.length() == 0) { this.YJZZ_AMT = null; } else {
      this.YJZZ_AMT = new java.math.BigDecimal(__cur_str);
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N") || __cur_str.length() == 0) { this.HK_MONTH_NUM = null; } else {
      this.HK_MONTH_NUM = new java.math.BigDecimal(__cur_str);
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N") || __cur_str.length() == 0) { this.YJHK_AMT = null; } else {
      this.YJHK_AMT = new java.math.BigDecimal(__cur_str);
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N") || __cur_str.length() == 0) { this.BJHK_AMT = null; } else {
      this.BJHK_AMT = new java.math.BigDecimal(__cur_str);
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
    T_CARD_TRANS_INFO o = (T_CARD_TRANS_INFO) super.clone();
    return o;
  }

  public void clone0(T_CARD_TRANS_INFO o) throws CloneNotSupportedException {
  }

  public Map<String, Object> getFieldMap() {
    Map<String, Object> __sqoop$field_map = new TreeMap<String, Object>();
    __sqoop$field_map.put("MOBILE_NUM", this.MOBILE_NUM);
    __sqoop$field_map.put("OUTCDNO", this.OUTCDNO);
    __sqoop$field_map.put("OUT_BANK_NAME", this.OUT_BANK_NAME);
    __sqoop$field_map.put("ZZ_AMT", this.ZZ_AMT);
    __sqoop$field_map.put("ZZ_CNT", this.ZZ_CNT);
    __sqoop$field_map.put("YJZZ_AMT", this.YJZZ_AMT);
    __sqoop$field_map.put("HK_MONTH_NUM", this.HK_MONTH_NUM);
    __sqoop$field_map.put("YJHK_AMT", this.YJHK_AMT);
    __sqoop$field_map.put("BJHK_AMT", this.BJHK_AMT);
    __sqoop$field_map.put("STARTDATE", this.STARTDATE);
    __sqoop$field_map.put("ENDDATE", this.ENDDATE);
    __sqoop$field_map.put("MONTH_NUM", this.MONTH_NUM);
    return __sqoop$field_map;
  }

  public void getFieldMap0(Map<String, Object> __sqoop$field_map) {
    __sqoop$field_map.put("MOBILE_NUM", this.MOBILE_NUM);
    __sqoop$field_map.put("OUTCDNO", this.OUTCDNO);
    __sqoop$field_map.put("OUT_BANK_NAME", this.OUT_BANK_NAME);
    __sqoop$field_map.put("ZZ_AMT", this.ZZ_AMT);
    __sqoop$field_map.put("ZZ_CNT", this.ZZ_CNT);
    __sqoop$field_map.put("YJZZ_AMT", this.YJZZ_AMT);
    __sqoop$field_map.put("HK_MONTH_NUM", this.HK_MONTH_NUM);
    __sqoop$field_map.put("YJHK_AMT", this.YJHK_AMT);
    __sqoop$field_map.put("BJHK_AMT", this.BJHK_AMT);
    __sqoop$field_map.put("STARTDATE", this.STARTDATE);
    __sqoop$field_map.put("ENDDATE", this.ENDDATE);
    __sqoop$field_map.put("MONTH_NUM", this.MONTH_NUM);
  }

  public void setField(String __fieldName, Object __fieldVal) {
    if ("MOBILE_NUM".equals(__fieldName)) {
      this.MOBILE_NUM = (String) __fieldVal;
    }
    else    if ("OUTCDNO".equals(__fieldName)) {
      this.OUTCDNO = (String) __fieldVal;
    }
    else    if ("OUT_BANK_NAME".equals(__fieldName)) {
      this.OUT_BANK_NAME = (String) __fieldVal;
    }
    else    if ("ZZ_AMT".equals(__fieldName)) {
      this.ZZ_AMT = (java.math.BigDecimal) __fieldVal;
    }
    else    if ("ZZ_CNT".equals(__fieldName)) {
      this.ZZ_CNT = (java.math.BigDecimal) __fieldVal;
    }
    else    if ("YJZZ_AMT".equals(__fieldName)) {
      this.YJZZ_AMT = (java.math.BigDecimal) __fieldVal;
    }
    else    if ("HK_MONTH_NUM".equals(__fieldName)) {
      this.HK_MONTH_NUM = (java.math.BigDecimal) __fieldVal;
    }
    else    if ("YJHK_AMT".equals(__fieldName)) {
      this.YJHK_AMT = (java.math.BigDecimal) __fieldVal;
    }
    else    if ("BJHK_AMT".equals(__fieldName)) {
      this.BJHK_AMT = (java.math.BigDecimal) __fieldVal;
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
    else    if ("OUTCDNO".equals(__fieldName)) {
      this.OUTCDNO = (String) __fieldVal;
      return true;
    }
    else    if ("OUT_BANK_NAME".equals(__fieldName)) {
      this.OUT_BANK_NAME = (String) __fieldVal;
      return true;
    }
    else    if ("ZZ_AMT".equals(__fieldName)) {
      this.ZZ_AMT = (java.math.BigDecimal) __fieldVal;
      return true;
    }
    else    if ("ZZ_CNT".equals(__fieldName)) {
      this.ZZ_CNT = (java.math.BigDecimal) __fieldVal;
      return true;
    }
    else    if ("YJZZ_AMT".equals(__fieldName)) {
      this.YJZZ_AMT = (java.math.BigDecimal) __fieldVal;
      return true;
    }
    else    if ("HK_MONTH_NUM".equals(__fieldName)) {
      this.HK_MONTH_NUM = (java.math.BigDecimal) __fieldVal;
      return true;
    }
    else    if ("YJHK_AMT".equals(__fieldName)) {
      this.YJHK_AMT = (java.math.BigDecimal) __fieldVal;
      return true;
    }
    else    if ("BJHK_AMT".equals(__fieldName)) {
      this.BJHK_AMT = (java.math.BigDecimal) __fieldVal;
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
