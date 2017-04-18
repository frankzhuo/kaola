#!/bin/sh

TODAY=$(date +%Y%m%d)

cd /home/hdfs/wbpos_atmtxnjnl_work/

#数据清洗

sh wbpos_atmtxnjnl_clean.sh>logs/wbpos_atmtxnjnl_clean.log 2>&1

TMP=$?

if test ! ${TMP} -eq 0; then
   echo ${TODAY}"------清洗数据失败">>logs/wbpos_atmtxnjnl_clean_failed.log
   exit 1
else
  echo ${TODAY}"-----清洗数据成功">>logs/wbpos_atmtxnjnl_clean_success.log
fi

#将数据导入到Hbase中去


sh wbpos_import_to_hbase.sh>logs/wbpos_atmtxnjnl_import_to_hbase.log 2>&1

TMP=$?

if test ! ${TMP} -eq 0;then 
 echo ${TODAY}"--------将清洗的数据导入到Hbase中失败">>logs/wbpos_atmtxnjnl_import_to_hbase_failed.log
 else
 echo ${TODAY}"--------将清洗的数据导入到Hbase中成功">>logs/wbpos_atmtxnjnl_import_to_hbase_success.log
fi
