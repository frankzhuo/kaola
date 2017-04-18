

#!/bin/bash


###########################################################################################################################################################################
#
#                                 export hive to oracle
#
###########################################################################################################################################################################



#JDBC=jdbc:oracle:thin:@10.5.19.26:1521
#SID=ZXDB
#USERNAME=merscore
#PASSWORD=merscore

JDBC=jdbc:oracle:thin:@10.1.2.187:1521
SID=crdnewdb
USERNAME=EM
PASSWORD=em_1234


#USER=zxuser
#PASSWD=zxuser
#TODAY=$(date +%Y%m%d)
#TABLENAME=LOANMANAGEBATCHDETAIL
#JDBC=jdbc:oracle:thin:@10.5.19.26:1521/ZXDB



echo; echo; echo
echo '-------------------------------------------中安信业推送表-------------------------------------------'
echo; echo; echo


echo "sudo -u hdfs  sqoop export --connect $JDBC/$SID --username $USERNAME --password $PASSWORD --table CDT_MERPRECREDIT  --export-dir   /user/hive/warehouse/merzx.db/mer_limit_result_zhongan_push  --fields-terminated-by '\001' --input-null-string '\\N' --input-null-non-string '\\N' --update-key MERID,ORGID --update-mode allowinsert -m 1"
echo; echo; echo

sudo -u hdfs  sqoop export --connect $JDBC/$SID --username $USERNAME --password $PASSWORD --table CDT_MERPRECREDIT  --export-dir  /user/hive/warehouse/merzx.db/mer_limit_result_zhongan_push  --fields-terminated-by '\001' --input-null-string '\\N' --input-null-non-string '\\N' --update-key MERID,ORGID --update-mode allowinsert -m 2
tmp=$?
NOWTIME=$(date +%Y-%m-%d/%H:%M:%S)
if test $tmp -eq 0 ; then
echo $NOWTIME '------------导出中安信业推送表推送表到征信数据库成功--------'$tmp
else
echo $NOWTIME '------------导出中安信业推送表到征信数据库失败--------'$tmp
fi
