// ORM class for table 'T_OS_CAR_INFO'
// WARNING: This class is AUTO-GENERATED. Modify at your own risk.
//
// Debug information:
// Generated date: Fri Aug 21 22:59:19 CST 2015
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

public class T_OS_CAR_INFO extends SqoopRecord  implements DBWritable, Writable {
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
  public T_OS_CAR_INFO with_MOBILE_NUM(String MOBILE_NUM) {
    this.MOBILE_NUM = MOBILE_NUM;
    return this;
  }
  private String OS;
  public String get_OS() {
    return OS;
  }
  public void set_OS(String OS) {
    this.OS = OS;
  }
  public T_OS_CAR_INFO with_OS(String OS) {
    this.OS = OS;
    return this;
  }
  private String PLATE_NO;
  public String get_PLATE_NO() {
    return PLATE_NO;
  }
  public void set_PLATE_NO(String PLATE_NO) {
    this.PLATE_NO = PLATE_NO;
  }
  public T_OS_CAR_INFO with_PLATE_NO(String PLATE_NO) {
    this.PLATE_NO = PLATE_NO;
    return this;
  }
  private String VOITURE_TYPE;
  public String get_VOITURE_TYPE() {
    return VOITURE_TYPE;
  }
  public void set_VOITURE_TYPE(String VOITURE_TYPE) {
    this.VOITURE_TYPE = VOITURE_TYPE;
  }
  public T_OS_CAR_INFO with_VOITURE_TYPE(String VOITURE_TYPE) {
    this.VOITURE_TYPE = VOITURE_TYPE;
    return this;
  }
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof T_OS_CAR_INFO)) {
      return false;
    }
    T_OS_CAR_INFO that = (T_OS_CAR_INFO) o;
    boolean equal = true;
    equal = equal && (this.MOBILE_NUM == null ? that.MOBILE_NUM == null : this.MOBILE_NUM.equals(that.MOBILE_NUM));
    equal = equal && (this.OS == null ? that.OS == null : this.OS.equals(that.OS));
    equal = equal && (this.PLATE_NO == null ? that.PLATE_NO == null : this.PLATE_NO.equals(that.PLATE_NO));
    equal = equal && (this.VOITURE_TYPE == null ? that.VOITURE_TYPE == null : this.VOITURE_TYPE.equals(that.VOITURE_TYPE));
    return equal;
  }
  public boolean equals0(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof T_OS_CAR_INFO)) {
      return false;
    }
    T_OS_CAR_INFO that = (T_OS_CAR_INFO) o;
    boolean equal = true;
    equal = equal && (this.MOBILE_NUM == null ? that.MOBILE_NUM == null : this.MOBILE_NUM.equals(that.MOBILE_NUM));
    equal = equal && (this.OS == null ? that.OS == null : this.OS.equals(that.OS));
    equal = equal && (this.PLATE_NO == null ? that.PLATE_NO == null : this.PLATE_NO.equals(that.PLATE_NO));
    equal = equal && (this.VOITURE_TYPE == null ? that.VOITURE_TYPE == null : this.VOITURE_TYPE.equals(that.VOITURE_TYPE));
    return equal;
  }
  public void readFields(ResultSet __dbResults) throws SQLException {
    this.__cur_result_set = __dbResults;
    this.MOBILE_NUM = JdbcWritableBridge.readString(1, __dbResults);
    this.OS = JdbcWritableBridge.readString(2, __dbResults);
    this.PLATE_NO = JdbcWritableBridge.readString(3, __dbResults);
    this.VOITURE_TYPE = JdbcWritableBridge.readString(4, __dbResults);
  }
  public void readFields0(ResultSet __dbResults) throws SQLException {
    this.MOBILE_NUM = JdbcWritableBridge.readString(1, __dbResults);
    this.OS = JdbcWritableBridge.readString(2, __dbResults);
    this.PLATE_NO = JdbcWritableBridge.readString(3, __dbResults);
    this.VOITURE_TYPE = JdbcWritableBridge.readString(4, __dbResults);
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
    JdbcWritableBridge.writeString(OS, 2 + __off, 12, __dbStmt);
    JdbcWritableBridge.writeString(PLATE_NO, 3 + __off, 12, __dbStmt);
    JdbcWritableBridge.writeString(VOITURE_TYPE, 4 + __off, 12, __dbStmt);
    return 4;
  }
  public void write0(PreparedStatement __dbStmt, int __off) throws SQLException {
    JdbcWritableBridge.writeString(MOBILE_NUM, 1 + __off, 12, __dbStmt);
    JdbcWritableBridge.writeString(OS, 2 + __off, 12, __dbStmt);
    JdbcWritableBridge.writeString(PLATE_NO, 3 + __off, 12, __dbStmt);
    JdbcWritableBridge.writeString(VOITURE_TYPE, 4 + __off, 12, __dbStmt);
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
        this.OS = null;
    } else {
    this.OS = Text.readString(__dataIn);
    }
    if (__dataIn.readBoolean()) { 
        this.PLATE_NO = null;
    } else {
    this.PLATE_NO = Text.readString(__dataIn);
    }
    if (__dataIn.readBoolean()) { 
        this.VOITURE_TYPE = null;
    } else {
    this.VOITURE_TYPE = Text.readString(__dataIn);
    }
  }
  public void write(DataOutput __dataOut) throws IOException {
    if (null == this.MOBILE_NUM) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    Text.writeString(__dataOut, MOBILE_NUM);
    }
    if (null == this.OS) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    Text.writeString(__dataOut, OS);
    }
    if (null == this.PLATE_NO) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    Text.writeString(__dataOut, PLATE_NO);
    }
    if (null == this.VOITURE_TYPE) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    Text.writeString(__dataOut, VOITURE_TYPE);
    }
  }
  public void write0(DataOutput __dataOut) throws IOException {
    if (null == this.MOBILE_NUM) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    Text.writeString(__dataOut, MOBILE_NUM);
    }
    if (null == this.OS) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    Text.writeString(__dataOut, OS);
    }
    if (null == this.PLATE_NO) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    Text.writeString(__dataOut, PLATE_NO);
    }
    if (null == this.VOITURE_TYPE) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    Text.writeString(__dataOut, VOITURE_TYPE);
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
    __sb.append(FieldFormatter.escapeAndEnclose(OS==null?"null":OS, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(PLATE_NO==null?"null":PLATE_NO, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(VOITURE_TYPE==null?"null":VOITURE_TYPE, delimiters));
    if (useRecordDelim) {
      __sb.append(delimiters.getLinesTerminatedBy());
    }
    return __sb.toString();
  }
  public void toString0(DelimiterSet delimiters, StringBuilder __sb, char fieldDelim) {
    __sb.append(FieldFormatter.escapeAndEnclose(MOBILE_NUM==null?"null":MOBILE_NUM, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(OS==null?"null":OS, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(PLATE_NO==null?"null":PLATE_NO, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(VOITURE_TYPE==null?"null":VOITURE_TYPE, delimiters));
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
    if (__cur_str.equals("\\N")) { this.OS = null; } else {
      this.OS = __cur_str;
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N")) { this.PLATE_NO = null; } else {
      this.PLATE_NO = __cur_str;
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N")) { this.VOITURE_TYPE = null; } else {
      this.VOITURE_TYPE = __cur_str;
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
    if (__cur_str.equals("\\N")) { this.OS = null; } else {
      this.OS = __cur_str;
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N")) { this.PLATE_NO = null; } else {
      this.PLATE_NO = __cur_str;
    }

    __cur_str = __it.next();
    if (__cur_str.equals("\\N")) { this.VOITURE_TYPE = null; } else {
      this.VOITURE_TYPE = __cur_str;
    }

    } catch (RuntimeException e) {    throw new RuntimeException("Can't parse input data: '" + __cur_str + "'", e);    }  }

  public Object clone() throws CloneNotSupportedException {
    T_OS_CAR_INFO o = (T_OS_CAR_INFO) super.clone();
    return o;
  }

  public void clone0(T_OS_CAR_INFO o) throws CloneNotSupportedException {
  }

  public Map<String, Object> getFieldMap() {
    Map<String, Object> __sqoop$field_map = new TreeMap<String, Object>();
    __sqoop$field_map.put("MOBILE_NUM", this.MOBILE_NUM);
    __sqoop$field_map.put("OS", this.OS);
    __sqoop$field_map.put("PLATE_NO", this.PLATE_NO);
    __sqoop$field_map.put("VOITURE_TYPE", this.VOITURE_TYPE);
    return __sqoop$field_map;
  }

  public void getFieldMap0(Map<String, Object> __sqoop$field_map) {
    __sqoop$field_map.put("MOBILE_NUM", this.MOBILE_NUM);
    __sqoop$field_map.put("OS", this.OS);
    __sqoop$field_map.put("PLATE_NO", this.PLATE_NO);
    __sqoop$field_map.put("VOITURE_TYPE", this.VOITURE_TYPE);
  }

  public void setField(String __fieldName, Object __fieldVal) {
    if ("MOBILE_NUM".equals(__fieldName)) {
      this.MOBILE_NUM = (String) __fieldVal;
    }
    else    if ("OS".equals(__fieldName)) {
      this.OS = (String) __fieldVal;
    }
    else    if ("PLATE_NO".equals(__fieldName)) {
      this.PLATE_NO = (String) __fieldVal;
    }
    else    if ("VOITURE_TYPE".equals(__fieldName)) {
      this.VOITURE_TYPE = (String) __fieldVal;
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
    else    if ("OS".equals(__fieldName)) {
      this.OS = (String) __fieldVal;
      return true;
    }
    else    if ("PLATE_NO".equals(__fieldName)) {
      this.PLATE_NO = (String) __fieldVal;
      return true;
    }
    else    if ("VOITURE_TYPE".equals(__fieldName)) {
      this.VOITURE_TYPE = (String) __fieldVal;
      return true;
    }
    else {
      return false;    }
  }
}
