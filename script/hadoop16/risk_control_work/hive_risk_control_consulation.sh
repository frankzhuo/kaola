#!/bin/sh

cd /home/hdfs/risk_control_work

#首先创建风控咨询表
sudo -u hdfs hive -f risk_control_consulation.sql

#每天定期清洗数据,并将结果插入到目标表中

sudo -u hdfs hive -f load_risk_control_consulation.sql



