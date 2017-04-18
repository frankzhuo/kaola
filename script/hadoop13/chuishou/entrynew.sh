#!/bin/sh
cd /home/hdfs/cuishou
IDPATH='/home/hdfs/cuishou/id.txt'
#GETPATH='http://10.5.19.26:9082/dv/qryLoanManageBatchNum.do?_t=json'
GETPATH='http://10.1.80.115:443/dv/qryLoanManageBatchNum.do?_t=json'

#POSTPATH='http://10.5.19.26:9082/dv/updateLoanManageStatus.do?_t=json&batchNum=0&realNum=2'
#POSTPATH='http://10.5.19.26:9082/dv/updateLoanManageStatus.do?_t=json'

POSTPATH='http://10.1.80.115:443/dv/updateLoanManageStatus.do?_t=json'


if [ ! -f $IDPATH ];then
echo  0 >$IDPATH
fi

ID=`cat $IDPATH`
echo  $ID
if test $ID -ne 0; then
echo "正在运行"  >>logs/info.txt
exit;
fi

echo  '--------获取批次号--------'
TMP=`curl  $GETPATH`
echo  "sd"
echo $BATCHID
BATCHID=`echo  $TMP|awk  -F "\"" '{print  $12 }' `
echo  $BATCHID

if test $BATCHID -eq 0; then
echo  "没有批次需要运行" >>logs/info.txt
echo "0">$IDPATH
exit;
else
echo  "有批次需要运行" >>logs/info.txt
echo  $BATCHID >$IDPATH 
fi



echo '--------------现在开始导入数据-----------------'
USER=EM
PASSWD=em_1234
TODAY=$(date +%Y%m%d)
JDBC=jdbc:oracle:thin:@10.1.2.187:1521/crdnewdb

TABLENAME=LOANMANAGEBATCHDETAIL



sudo -u hdfs sqoop import \
 --connect ${JDBC}  --username ${USER} --password ${PASSWD} \
 -m 1 \
 --hive-table zhengxin.cuishou_input \
 --hive-import \
 --target-dir /user/hive/warehouse/zhengxin.db/${TABLENAME}_tmp \
 --delete-target-dir \
 --hive-overwrite \
 --hive-drop-import-delims \
 --fields-terminated-by '\001' \
 --null-string '\\N' \
 --null-non-string '\\N' \
 --query "select * from ${TABLENAME}  where  BATCHNUM=$BATCHID and \$CONDITIONS"
tmp=$?
NOWTIME=$(date +%Y-%m-%d/%H:%M:%S)
if test ! $tmp -eq 0 ; then
echo $NOWTIME '导入更新的'$TABLENAME' 失败--------'$tmp > logs/logs_oracle_tohive_bmcp_failed
exit -2;
fi



echo '---计算催收业务----'  >>logs/info.txt

sh run.sh


echo  '----------判断催收结果表-------------' >>logs/info.txt
sudo -u hdfs hdfs dfs -test -e /user/hive/warehouse/zhengxin.db/person_wide
existsflag=$?
if test ! $existsflag -eq 0; then
echo $NOWTIME'--------------催收结果表不存在'$tmp > logs/logs_cuishou_datasource_result
exit -2;
fi

echo  '----导出数据到ORACLE---------' >>logs/info.txt
sudo -u hdfs  sqoop export --connect ${JDBC} --username ${USER} --password ${PASSWD} -m 1 --table PERSON_WIDE --export-dir /user/hive/warehouse/zhengxin.db/person_wide --fields-terminated-by '\001' --input-null-string '\\N' --input-null-non-string '\\N' --update-key ID --update-mode allowinsert
tmp=$?
NOWTIME=$(date +%Y-%m-%d/%H:%M:%S)
if test $tmp -eq 0; then
echo $NOWTIME'--------------导出催收结果表成功'$tmp >>logs/logs_cuishou_datasource_result 
else
echo $NOWTIME'--------------导出催收结果表失败'$tmp >>logs/logs_cuishou_datasource_result
exit -2;
fi

echo  '------更新批次号-----'  >>logs/info.txt
hive -e "select  count(*)  from  zhengxin.person_wide  where (mobile_num1 is not null)  or (mobile_num2 is not null)  or   (hk_mobile_num1 is not null) or (hk_mobile_num12 is not null) or  (zz_mobile_num1 is not null ) or (zz_mobile_num12 is not null) or (cz_mobile_num1 is not null) or ( gj_addr_czs1 is not null)" >/home/hdfs/cuishou/num.txt
NUM=`cat  /home/hdfs/cuishou/num.txt`

echo $NUM
#---完成批次号的更新

POSTPATHARG=$POSTPATH"&batchNum="$BATCHID"&realNum="$NUM
echo  $POSTPATH  >>logs/info.txt 
echo $POSTPATHARG  >>logs/info.txt


while true
do
tresult=`curl  "$POSTPATHARG"`
echo  $tresult
result=`echo  $tresult|awk  -F "\"" '{print  $4 }' `
echo  $result
if test $result -eq 1; then
echo $NOWTIME'--------------更新批次表成功'$result  > logs/logs_update_batch_result
break
else
echo $NOWTIME'--------------更新批次表失败'$result > logs/logs_update_batch_result
exit -2;
fi
sleep 5m
done

echo "0">$IDPATH
