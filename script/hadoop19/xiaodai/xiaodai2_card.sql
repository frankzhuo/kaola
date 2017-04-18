#!/bin/bash
TODAY_1D=$(date --date='1 day ago' +%Y%m%d)
TODAY_0D=$(date +%Y%m%d)

echo $(date +%Y-%m-%d/%H:%M:%S) "-------------一键还款和理财绑定-----------开始----"

sudo -u hdfs hive <<EOF
set hive.auto.convert.join=false;
set mapreduce.reduce.shuffle.input.buffer.percent=0.1;
set mapreduce.reduce.shuffle.parallelcopies=9;

drop table creditck.model_card_bind;
create table creditck.model_card_bind
as
select  t1.idno, t1.acname,t1.acno,substr(t2.mobilephone,1,11) as mobilephone,max(modifydate) as modifydate
from hds.ydjr_nocard4pay t1,
hds.ydjrc_euser t2
where t1.actype='01' and t1.authstate='1' and t1.state='1' and t1.idno is not null
and cast(t1.userid as bigint)=cast(t2.userid as bigint)
group by t1.idno, t1.acname,t1.acno,t2.mobilephone
union all
SELECT  distinct t1.certid as idno,t1.custname as acname,t1.cardno as acno,substr(t1.mobileno,1,11) as mobilephone,from_unixtime(unix_timestamp(t1.applytime,'yyyyMMddHHmmss'),'yyyy-MM-dd HH:mm:ss') as modifydate
FROM  hds.xsl_FPAY_PERSONSIGN t1,
(select cardno,max(applytime) as applytime
from  hds.xsl_FPAY_PERSONSIGN t
where  status = 'A' and certtype='01'
group by cardno
) t2
WHERE  (t1.cardno=t2.cardno and t1.applytime=t2.applytime) and
status = 'A' and  certtype='01' and  t1.cardno is not null
;
EOF
tmp=$?
NOWTIME=$(date +%Y-%m-%d/%H:%M:%S)
if test $tmp -eq 0; then
   echo $NOWTIME'--------------一键还款和理财绑定 creditck.model_card_bind-------------'$tmp
   break
else
   echo $NOWTIME'--------------一键还款和理财绑定 creditck.model_card_bind-------------'$tmp
fi


#推送 一键还款和理财绑定
JDBC=jdbc:oracle:thin:@10.1.2.187:1521
SID=crdnewdb
USERNAME=EM
PASSWORD=em_1234

echo '-------------------------------------------一键还款和理财绑定-------------------------------------------'

echo "sudo -u hdfs sqoop export --connect $JDBC/$SID --username $USERNAME --password $PASSWORD --table CDT_LKL_CARD --export-dir /user/hive/warehouse/creditck.db/model_card_bind --fields-terminated-by '\001' --input-null-string '\\N' --input-null-non-string '\\N' --update-key ACNO,MOBILE --update-mode allowinsert -m 10"
echo; echo; echo
sudo -u hdfs sqoop export --connect $JDBC/$SID --username $USERNAME --password $PASSWORD --table CDT_LKL_CARD --export-dir /user/hive/warehouse/creditck.db/model_card_bind --fields-terminated-by '\001' --input-null-string '\\N' --input-null-non-string '\\N' --update-key ACNO,MOBILE --update-mode allowinsert -m 10
tmp=$?
NOWTIME=$(date +%Y-%m-%d/%H:%M:%S)
if test $tmp -eq 0 ; then
    echo $NOWTIME'------------CDT_LKL_CARD 拉卡拉绑定推送表--------'$tmp 
else
    echo $NOWTIME'------------CDT_LKL_CARD 拉卡拉绑定推送表--------'$tmp 
    echo $NOWTIME'------------拉卡拉还款和理财绑定推送表失败'$tmp >> /home/hdfs/xiaodai/logs/xiaodai2_cron_card_critical
    exit -1
fi