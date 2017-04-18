#!/bin/bash
JDBC=jdbc:oracle:thin:@10.1.2.197:1521
SID=creditdg2
USERNAME=cisdb
PASSWORD=amberdb!#$1234

TABLE=zx_report_mid_data
UPDATEKEY=day,type

sudo -u hdfs sqoop export --connect $JDBC/$SID --username $USERNAME --password '${PASSWORD}' --table ${TABLE} --export-dir /user/hive/warehouse/zhengxin.db/zx_report_mid_data --fields-terminated-by '\001' --input-null-string '\\N' --input-null-non-string '\\N' --update-key ${UPDATEKEY} --update-mode allowinsert -m 10
tmp=$?
NOWTIME=$(date +%Y-%m-%d/%H:%M:%S)
if test $tmp -eq 0 ; then
    echo $NOWTIME'------------征信日志用户推送表成功--------'$tmp 
else
    echo $NOWTIME'------------征信日志用户推送表失败--------'$tmp 
    exit -1
fi
