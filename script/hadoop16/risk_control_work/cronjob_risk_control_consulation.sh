#!/bin/sh

TODAY=$(date +%Y%m%d)

cd /home/hdfs/risk_control_work
#首先执行数据的清洗工作
sh hive_risk_control_consulation.sh>logs/hive_risk_control_consulation.log 2>&1 
TMP=$?

if test ! ${TMP} -eq 0; then 
   echo "风控数据的清洗失败----"${TODAY}>>logs/hive_risk_control_consulation_failed.log
   exit 1
fi

sleep 1m

#将清洗好的数据推送到hbase库中
sh risk_control_import_to_hbase.sh>logs/risk_control_import_to_hbase.log 2>&1
TMP=$?
if test ! ${TMP} -eq 0; then
   echo "推送风控数据到Hbase库中失败"${TODAY}>>logs/risk_control_import_to_hbase_failed.log
fi

