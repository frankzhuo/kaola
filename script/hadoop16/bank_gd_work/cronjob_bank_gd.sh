#!/bin/sh

#首先应用hive进行数据清洗
TODAY=$(date +%Y%m%d)
cd /home/hdfs/bank_gd_work

sh hdw_nwpos_agdbank_clean.sh>logs/hdw_nwpos_agdbank_clean.log 2>&1
TMP=$?

if test ! ${TMP} -eq 0; then 
 echo ${TODAY}"--------清洗数据失败">>logs/hdw_nwpos_agdbank_clean_failed.log
 exit 1
else
  echo ${TODAY}"-------清洗数据成功">>logs/hdw_nwpos_agdbank_clean_success.log
fi

#休眠1分钟
sleep 1m

#将数据导入到hbase中去
sh bank_gd_import_to_hbase.sh>logs/bank_gd_import_to_hbase.log 2>&1

TMP=$?
if test ! ${TMP} -eq 0; then
 echo ${TODAY}"---------将清洗出的数据导入到hbase中失败">>logs/bank_gd_import_to_hbase_failed.log
else
  echo ${TODAY}"-------成功将清洗的数据导入到hbase">>logs/bank_gd_import_to_hbase_success.log
fi
  
