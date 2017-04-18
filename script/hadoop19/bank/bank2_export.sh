#!/bin/bash

echo $(date +%Y-%m-%d/%H:%M:%S) "-------------光大银行交易变量 -----------开始----"
TODAY_1D=`hadoop fs -ls /user/hive/warehouse/zhengxin.db/gd_var_1031 |tail -n 1 |awk -F'=' '{print $2}'`

#推送 光大银行交易变量 
JDBC=jdbc:oracle:thin:@10.1.2.187:1521
SID=crdnewdb
USERNAME=EM
PASSWORD=em_1234

echo '-------------------------------------------光大银行交易变量-------------------------------------------'
echo "sudo -u hdfs sqoop export --connect $JDBC/$SID --username $USERNAME --password $PASSWORD --table CDT_KL_GD_TRANS --export-dir /user/hive/warehouse/zhengxin.db/gd_var_1031/ymd=${TODAY_1D} --fields-terminated-by '\001' --input-null-string '\\N' --input-null-non-string '\\N' --update-key MOBILE --update-mode allowinsert -m 10"
echo; echo; echo
sudo -u hdfs sqoop export --connect $JDBC/$SID --username $USERNAME --password $PASSWORD --table CDT_KL_GD_TRANS --export-dir /user/hive/warehouse/zhengxin.db/gd_var_1031/ymd=${TODAY_1D} --fields-terminated-by '\001' --input-null-string '\\N' --input-null-non-string '\\N' --update-key MOBILE --update-mode allowinsert -m 10
tmp=$?
NOWTIME=$(date +%Y-%m-%d/%H:%M:%S)
if test $tmp -eq 0 ; then
    echo $NOWTIME '------------CDT_KL_GD_TRANS 光大银行交易变量-------'$tmp 
else
    echo $NOWTIME '------------CDT_KL_GD_TRANS 光大银行交易变量--------'$tmp 
    exit -1
fi