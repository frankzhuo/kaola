#!/bin/bash
TODAY_1D=$(date --date='1 day ago' +%Y%m%d)
TODAYF_1D=$(date --date='1 day ago' +%Y-%m-%d)
TODAY_0D=$(date +%Y%m%d)

#替你还
echo $(date +%Y-%m-%d/%H:%M:%S) "-------------考拉征信app端申请和审批放款用户报表-----------开始----"

sudo -u hdfs hive -e "
set hive.auto.convert.join=false;
set mapreduce.reduce.shuffle.input.buffer.percent=0.1;
set mapreduce.reduce.shuffle.parallelcopies=9;

create table if not exists creditck.xd_loan_user_report(
load_date      string,
isnew          string,
business_type  string,
loan_usernum   bigint,
loan_num       bigint,
loan_amt       double,
pay_mobilenum  bigint,
pay_usernum    bigint,
pay_amt        double
) partitioned by (ymd string);

insert overwrite table creditck.xd_loan_user_report partition (ymd='${TODAY_1D}')
select substr(t1.loan_date,1,10) as loan_date,t4.isnew,(case when business_no in('BID','TNH') then 'tnh' when  business_no in('CASH','YFQ') then'yfq' end) as business_type,
count(distinct t4.coreuserid) as loan_usernum,
count(1) as loan_num,
sum(t1.capital_amount/100) as loan_amt,
count(distinct case when t1.status='T' and t3.id is not null and t3.is_payment=1 and (t3.end_type<>'2' or t3.end_type is null) then t4.coreuserid end) as pay_mobilenum,
count(case when t1.status='T' and t3.id is not null and t3.is_payment=1 and (t3.end_type<>'2' or t3.end_type is null) then 1 end) as pay_usernum,
sum( case when  t1.status='T' and t3.id is not null and t3.is_payment=1 and (t3.end_type<>'2' or t3.end_type is null) then t1.capital_amount/100 end) as pay_amt
from 
(select distinct t1.coreuserid,
(case when t1.isnew is not null then t1.isnew
 when t2.reg_sys_cd='LKL_CREDIT_CENTER' then 1
 else 0 end) as isnew
from hds.cts_euser t1
left join
edw.pt_user t2
on t1.coreuserid=t2.id
) t4
inner join 
hds.xd_c_loan_apply t1
on t4.coreuserid=t1.user_id
left join
hds.xd_c_loan_contract t2
on t1.id=t2.apply_id
left join 
hds.xd_c_receipt t3
on t2.id=t3.contract_id
where to_date(t1.loan_date)>=date_sub(to_date(from_unixtime(unix_timestamp())),1) and to_date(t1.loan_date)<to_date(from_unixtime(unix_timestamp())) and channel_source='Z'
group by t4.isnew,substr(t1.loan_date,1,10),(case when business_no in('BID','TNH') then 'tnh' when  business_no in('CASH','YFQ') then'yfq' end);
"

tmp=$?
NOWTIME=$(date +%Y-%m-%d/%H:%M:%S)
if test $tmp -eq 0; then
   echo $NOWTIME'--------------考拉征信app端申请和审批放款用户报表 creditck.xd_loan_user_report-------------'$tmp
   break
else
   echo $NOWTIME'--------------考拉征信app端申请和审批放款用户报表 creditck.xd_loan_user_report-------------'$tmp
fi

#exit;

#推送 考拉征信app端申请和审批放款用户报表
JDBC=jdbc:oracle:thin:@10.1.2.187:1521
SID=crdnewdb
USERNAME=EM
PASSWORD=em_1234

echo '-------------------------------------------考拉征信app端申请和审批放款用户报表-------------------------------------------'

echo "sudo -u hdfs sqoop export --connect $JDBC/$SID --username $USERNAME --password $PASSWORD --table CDT_CREDITLOAN_REPORT --export-dir /user/hive/warehouse/creditck.db/xd_loan_user_report/ymd=${TODAY_1D} --fields-terminated-by '\001' --input-null-string '\\N' --input-null-non-string '\\N' -m 1"
echo; echo; echo
sudo -u hdfs sqoop export --connect $JDBC/$SID --username $USERNAME --password $PASSWORD --table CDT_CREDITLOAN_REPORT --export-dir /user/hive/warehouse/creditck.db/xd_loan_user_report/ymd=${TODAY_1D} --fields-terminated-by '\001' --input-null-string '\\N' --input-null-non-string '\\N' -m 1
tmp=$?
NOWTIME=$(date +%Y-%m-%d/%H:%M:%S)
if test $tmp -eq 0 ; then
    echo $NOWTIME'------------CDT_XD_IPCRS_REPORT 考拉征信app端申请和审批放款用户报表--------'$tmp 
else
    echo $NOWTIME'------------CDT_XD_IPCRS_REPORT 考拉征信app端申请和审批放款用户报表--------'$tmp 
    echo $NOWTIME'------------考拉征信app端申请和审批放款用户报表失败'$tmp >> /home/hdfs/xiaodai/logs/xiaodai2_cron_loanreport_critical
    exit -1
fi