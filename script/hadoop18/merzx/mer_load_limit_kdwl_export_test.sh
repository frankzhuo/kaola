#!/bin/bash


###########################################################################################################################################################################
#
#                                 export hive to oracle
#
###########################################################################################################################################################################



JDBC=jdbc:oracle:thin:@10.1.2.187:1521
SID=crdnewdb
USERNAME=EM
PASSWORD=em_1234




echo; echo; echo
echo '-------------------------------------------商户卡得万利初审状态推送表-------------------------------------------'
echo; echo; echo


echo "sudo -u hdfs  sqoop export --connect $JDBC/$SID --username $USERNAME --password $PASSWORD --table CDT_MERKDWLTRIALSTATE  --export-dir /user/hive/warehouse/merzx.db/mer_limit_kdwl_testp3  --fields-terminated-by '\001' --input-null-string '\\N' --input-null-non-string '\\N' --update-key MERID,ORGID --update-mode allowinsert -m 10"
echo; echo; echo

sudo -u hdfs  sqoop export  --connect $JDBC/$SID --username $USERNAME --password $PASSWORD --table CDT_MERKDWLTRIALSTATE   --export-dir /user/hive/warehouse/merzx.db/mer_limit_kdwl_testp3  --fields-terminated-by '\001' --input-null-string '\\N' --input-null-non-string '\\N' --update-key ID --update-mode allowinsert  -m 10
tmp=$?
NOWTIME=$(date +%Y-%m-%d/%H:%M:%S)
if test $tmp -eq 0 ; then
echo $NOWTIME '------------导出推商户卡得万利初审状态推送表到征信数据库成功--------'$tmp 
else
echo $NOWTIME '------------导出推商户卡得万利初审状态推送表到征信数据库失败--------'$tmp 
fi














