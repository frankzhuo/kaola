#!/bin/sh

echo '-----------------信用卡用户表导出--------------------'
sudo -u hdfs  sqoop export --connect jdbc:oracle:thin:@10.1.2.156:1521/lmfsdb --username mfsuser --password amberdb --table MFSUSER.USERINFO --export-dir /user/hive/warehouse/lakala.db/credit_userinfo  --fields-terminated-by '\001'

echo '-----------------信用卡用户银行卡表导出--------------------'
sudo -u hdfs  sqoop export --connect jdbc:oracle:thin:@10.1.2.156:1521/lmfsdb --username mfsuser --password amberdb --table MFSUSER.BANKCARDINFO --export-dir /user/hive/warehouse/lakala.db/credit_bankcardinfo  --fields-terminated-by '\001'


