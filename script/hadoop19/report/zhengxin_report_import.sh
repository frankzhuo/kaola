#!/bin/sh
cd /home/hdfs/report

TODAYF_1D=$(date -d "$1 -1 days" "+%Y-%m-%d")
TODAY_1D=$(date -d "$1 -1 days" "+%Y%m%d")

echo ${TODAYF_1D} ${TODAY_1D}
exit;

#sudo -u hdfs hive <<EOF
#alert table zhengxin.euser add partition(ymd='${TODAY_1D}');
#alert table zhengxin.accesslog add partition(ymd='${TODAY_1D}');
#alert table zhengxin.procdt_scorelog add partition(ymd='${TODAY_1D}');
#EOF

sudo -u hdfs sqoop import \
 --connect jdbc:oracle:thin:@10.1.2.197:1521/creditdg2 --username cisdb --password 'amberdb!#$1234' \
 -m 1 \
 --hive-table zhengxin.euser \
 --hive-partition-key ymd \
 --hive-partition-value ${TODAY_1D} \
 --hive-import \
 --delete-target-dir \
 --hive-drop-import-delims \
 --hive-overwrite \
 --fields-terminated-by '\001'  \
 --null-string '\\N' \
 --null-non-string '\\N' \
 --target-dir /user/hive/warehouse/zhengxin.db/euser/ymd=${TODAY_1D} \
 --query "select a.userid,a.customerid,a.mobilephone,a.opentime,a.coreuerid,a.userflag from ctsdb.euser a where 1=1 and to_char(a.opentime,'yyyy-MM-dd')='${TODAYF_1D}' and \$CONDITIONS"

sudo -u hdfs sqoop import \
 --connect jdbc:oracle:thin:@10.1.2.197:1521/creditdg2 --username cisdb --password 'amberdb!#$1234' \
 -m 1 \
 --hive-table zhengxin.accesslog \
 --hive-partition-key ymd \
 --hive-partition-value ${TODAY_1D} \
 --hive-import \
 --delete-target-dir \
 --hive-drop-import-delims \
 --hive-overwrite \
 --fields-terminated-by '\001'  \
 --null-string '\\N' \
 --null-non-string '\\N' \
 --target-dir /user/hive/warehouse/zhengxin.db/accesslog/ymd=${TODAY_1D} \
 --query "select a.id,a.channelid,a.state,a.requestid,a.time,a.userid,a.marketchannel from ctsdb.accesslog a where 1=1 and to_char(a.time,'yyyy-MM-dd')='${TODAYF_1D}' and \$CONDITIONS"

sudo -u hdfs sqoop import \
 --connect jdbc:oracle:thin:@10.1.2.197:1521/creditdg2 --username cisdb --password 'amberdb!#$1234' \
 -m 1 \
 --hive-table zhengxin.procdt_scorelog \
 --hive-partition-key ymd \
 --hive-partition-value ${TODAY_1D} \
 --hive-import \
 --delete-target-dir \
 --hive-drop-import-delims \
 --hive-overwrite \
 --fields-terminated-by '\001'  \
 --null-string '\\N' \
 --null-non-string '\\N' \ 
 --target-dir /user/hive/warehouse/zhengxin.db/procdt_scorelog/ymd=${TODAY_1D} \
 --query "select a.mobile,a.customerid,a.customer_id,source,a.date_time from cisdb.procdt_scorelog a where 1=1 and to_char(a.date_time,'yyyy-MM-dd')='${TODAYF_1D}' and \$CONDITIONS"
