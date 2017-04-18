#!/bin/bash
TODAY_1D=$(date --date='1 day ago' +%Y%m%d)
TODAY_0D=$(date +%Y%m%d)

echo $(date +%Y-%m-%d/%H:%M:%S) "-------------拉卡拉 app 央行报告-----------开始----"

sudo -u hdfs hive -e "
set hive.auto.convert.join=false;
set mapreduce.reduce.shuffle.input.buffer.percent=0.1;
set mapreduce.reduce.shuffle.parallelcopies=9;

drop table creditck.ydjr_zx_report_xz_a;
create table creditck.ydjr_zx_report_xz_a
as
select t5.mobilephone,t2.identifier as cert_no,substr(t1.name,4,length(t1.name)-3) as name,
(case when substr(t2.identifier,17,1)%2=1 then '男' when substr(t2.identifier,17,1)%2=0 then '女' end) as sex,
 2016-substr(t2.identifier,7,4) as age,t1.marrystatus,id
from hds.ydjr_zx_report t1,
hds.ydjrc_epersoninfo t2,
hds.ydjrc_euser t5
where t1.userid=t2.customerid and substr(t1.certnum,length(certnum)-3,4)=substr(identifier,15,4) 
and t1.userid=t5.userid;

drop table creditck.ydjr_zx_report_xz;
create table creditck.ydjr_zx_report_xz
as
select distinct t3.*
from creditck.ydjr_zx_report_xz_a t3
inner join
(select cert_no,name,max(id) as id
from creditck.ydjr_zx_report_xz_a t
group by cert_no,name) t4
on  t3.cert_no=t4.cert_no and t3.name=t4.name and t3.id=t4.id;

drop table  creditck.ydjr_zx_report_recorddesc_credit_xz;
create table creditck.ydjr_zx_report_recorddesc_credit_xz
as
select mobilephone,cert_no,name,repordid,
substr(recorddesc,1,instr(recorddesc,'日')) as XYK_payment_date,
substr(recorddesc,instr(recorddesc,'日')+1,instr(recorddesc,'发放')-instr(recorddesc,'日')-1) as XYK_payment_institu,
cast(regexp_replace(
(case when recorddesc like'%人民币账户%' and  split(recorddesc,'信用额度')[1] not like '%截至%' then substr(split(recorddesc,'信用额度')[1],1,instr(split(recorddesc,'信用额度')[1],'，')-1)

when recorddesc like'%人民币账户%' and  split(recorddesc,'信用额度')[1] like '%截至%'  then substr(split(recorddesc,'信用额度')[1],1,instr(split(recorddesc,'信用额度')[1],'。截至')-1)
when recorddesc not like'%人民币账户%' and  split(recorddesc,'信用额度')[1] not like '%截至%' then substr(split(recorddesc,'信用额度')[1],6,instr(split(recorddesc,'信用额度')[1],'，')-6)
when recorddesc not like'%人民币账户%' and  split(recorddesc,'信用额度')[1] like '%截至%' then substr(split(recorddesc,'信用额度')[1],6,instr(split(recorddesc,'信用额度')[1],'。截至')-6) end)
,',','') as float) as credit_limit,

cast(regexp_replace((case when recorddesc like'%已使用额度%' and recorddesc not like'%逾期金额%' then substr(split(recorddesc,'已使用额度')[1],1,instr(split(recorddesc,'已使用额度')[1],'。')-1)
when recorddesc like'%已使用额度%' and recorddesc like'%逾期金额%' then substr(split(recorddesc,'已使用额度')[1],1,instr(split(recorddesc,'已使用额度')[1],'逾期金额')-2) end)
,',','') as float) as used_amount,
cast(regexp_replace((case when recorddesc like'%逾期金额%' then substr(recorddesc,instr(recorddesc,'逾期金额')+4,instr(recorddesc,'最近5年内有')-1-instr(recorddesc,'逾期金额')-4)
else 0 end),',','') as float) as delay_am,
(case when recorddesc like'%已销户%' then '已销户' when recorddesc like'%未激活%' then '未激活'  when recorddesc like'%呆账%' then '呆账'  else '正常使用' end) as xyk_status,
(case when recorddesc like '%最近5年内%' then substr(recorddesc,instr(recorddesc,'最近5年内有')+6,
                                                 instr(recorddesc,'个月处于逾期状态')-instr(recorddesc,'最近5年内有')-6) else 0 end) as delay_month,
(case when recorddesc like'%逾期超过90天%' then  substr( recorddesc,instr( recorddesc,'其中')+2,
                                                  instr(recorddesc,'个月逾期超过90天')-instr(recorddesc,'其中')-2) else 0 end) as delay_90_month
from hds.ydjr_zx_report_recorddesc t1,
creditck.ydjr_zx_report_xz t2
where repordtype in('reditRecodeYuqi','reditRecodeYuqiM2','reditRecodeNormal') and t1.repordid=t2.id;

drop table  creditck.ydjr_zx_report_recorddesc_credit_var_xz;
create table creditck.ydjr_zx_report_recorddesc_credit_var_xz
as
select mobilephone,cert_no,name,
count(distinct finance_orgdjk) as bank_num,
count(distinct case when is_regular_service=1 then finance_orgdjk end)/count(distinct finance_orgdjk) as regular_bank_zb,
count(distinct case when is_regular_service=1 and (finance_orgdjk like'%中国银行%'
 or finance_orgdjk like'%建设银行%' or finance_orgdjk like'%工商银行%' or finance_orgdjk like'%农业银行%')then finance_orgdjk end)/count(distinct finance_orgdjk) as regular_4bank_zb,
count(distinct case when is_all_zhuxiao=1 then finance_orgdjk end)/count(distinct finance_orgdjk) as zhuxiao_bank_zb,
count(distinct case when is_delay=1 then finance_orgdjk end)/count(distinct finance_orgdjk) as delay_bank_zb,
max(case when is_regular_service=1 then first_open_days end) as first_open_days,
min(case when is_regular_service=1 then last_open_days end) as last_open_days, 
datediff(from_unixtime(unix_timestamp(substr(max(repordid),1,8),'yyyyMMdd'),'yyyy-MM-dd'),split(max(concat(lpad(cast((case when  is_regular_service=1 then credit_limit_amountdjk end) as string),10,'0'),',',max_amount_open_date)),',')[1]) as max_amount_days,
sum(case when is_regular_service=1 then credit_limit_amountdjk end) as credit_limit_amount_all,
max(case when is_regular_service=1 then credit_limit_amountdjk end) as credit_limit_amount_max,
min(case when is_regular_service=1 then credit_limit_amountdjk end) as credit_limit_amount_min,
avg(case when is_regular_service=1 then credit_limit_amountdjk end) as credit_limit_amount_avg,
(case when sum(credit_limit_amountdjk)=0 then -1 else sum(case when is_regular_service=1 then used_credit_limit_amountdjk end)/sum(case when is_regular_service=1 then credit_limit_amountdjk end) end)  as usedamount_rate,
(case when sum(credit_limit_amountdjk)=0 then -1 when sum(used_credit_limit_amountdjk)=0 then 0 else sum(case when is_regular_service=1 then delay_amountdjk end)
/sum(case when is_regular_service=1 then used_credit_limit_amountdjk end) end)  as delayamount_rate,
sum(delay_num) as delay_num,
min(is_all_delay) as is_all_delay,
sum(delay_cha) as delay_cha_num
from(
select mobilephone,cert_no,name,max(repordid) as repordid, XYK_payment_institu  as finance_orgdjk,max(case when xyk_status='正常使用'  then 1 else 0 end) as is_regular_service,
min(case when xyk_status='已销户'  then 1 else 0 end) as is_all_zhuxiao,
max(case when delay_month>0 then 1 else 0 end) as is_delay,
min(case when delay_month>0 then 1 else 0 end) as is_all_delay,
max(case when xyk_status='正常使用' then datediff(from_unixtime(unix_timestamp(substr(repordid,1,8),'yyyyMMdd'),'yyyy-MM-dd')
,from_unixtime(unix_timestamp(XYK_payment_date,'yyyy年MM月dd日'),'yyyy-MM-dd')) else 0 end) as first_open_days,
min(case when  xyk_status='正常使用' then datediff(from_unixtime(unix_timestamp(substr(repordid,1,8),'yyyyMMdd'),'yyyy-MM-dd')
,from_unixtime(unix_timestamp(XYK_payment_date,'yyyy年MM月dd日'),'yyyy-MM-dd')) else 999999999 end) as last_open_days,
from_unixtime(unix_timestamp(split(max(concat(lpad(cast((case when xyk_status='正常使用' then credit_limit else 0 end)as string),10,'0'),',',XYK_payment_date))
,',')[1],'yyyy年MM月dd日'),'yyyy-MM-dd') as max_amount_open_date,
max(case when xyk_status='正常使用' and credit_limit is not null then credit_limit  else 0 end) as credit_limit_amountdjk,
max(case when xyk_status='正常使用' and used_amount is not null then used_amount  else 0 end) as used_credit_limit_amountdjk,
max(case when xyk_status='正常使用' and used_amount is not null and delay_am is not null then delay_am  else 0 end) as delay_amountdjk,
sum(delay_month) as delay_num,
sum(delay_90_month) as delay_90_num,
sum(case when delay_90_month=0 then delay_month when delay_90_month>0 and delay_month-(delay_90_month+3)<0 then 0 else delay_month-(delay_90_month+3) end) as delay_cha
from creditck.ydjr_zx_report_recorddesc_credit_xz t1
group by mobilephone,cert_no,name,XYK_payment_institu) t
group by mobilephone,cert_no,name;

drop table  creditck.ydjr_zx_report_recordnum_xz;
create table creditck.ydjr_zx_report_recordnum_xz
as
select mobilephone,cert_no,name,t1.*
from hds.ydjr_zx_report_recordnum  t1,
creditck.ydjr_zx_report_xz t2
where t1.repordid=t2.id;

----------------查询变量表
drop table creditck.ydjr_zx_report_recordnum_var_xz;
create table creditck.ydjr_zx_report_recordnum_var_xz
as
select mobilephone,cert_no,name,count(1) as query_num,
min( datediff(from_unixtime(unix_timestamp(substr(repordid,1,8),'yyyyMMdd'),'yyyy-MM-dd'),
from_unixtime(unix_timestamp(qrydate,'yyyy年MM月dd日'),'yyyy-MM-dd')) ) as last_query_days,
count(case when qryreason in('贷款审批','信用卡审批') then 1 end) as loan_credit_query_num,
min( case when qryreason in('贷款审批','信用卡审批') then datediff(from_unixtime(unix_timestamp(substr(repordid,1,8),'yyyyMMdd'),'yyyy-MM-dd'),
from_unixtime(unix_timestamp(qrydate,'yyyy年MM月dd日'),'yyyy-MM-dd')) else 999999999 end )  as last_loan_credit_query_days,
count(case when qryreason in('贷款审批','信用卡审批') then 1 end)/count(1) as loan_credit_query_num_zb,
count(case when datediff(from_unixtime(unix_timestamp(substr(repordid,1,8),'yyyyMMdd'),'yyyy-MM-dd'),
from_unixtime(unix_timestamp(qrydate,'yyyy年MM月dd日'),'yyyy-MM-dd'))<=90 then 1 end) as last_3month_query_num,
count(case when datediff(from_unixtime(unix_timestamp(substr(repordid,1,8),'yyyyMMdd'),'yyyy-MM-dd'),
from_unixtime(unix_timestamp(qrydate,'yyyy年MM月dd日'),'yyyy-MM-dd'))<=90 
and qryreason in('贷款审批','信用卡审批') then 1 end) as last_3month_loan_credit_query_num,
(Case when count(case when datediff(from_unixtime(unix_timestamp(substr(repordid,1,8),'yyyyMMdd'),'yyyy-MM-dd'),
from_unixtime(unix_timestamp(qrydate,'yyyy年MM月dd日'),'yyyy-MM-dd'))<=90 then 1 end)=0 then -1 else 
count(case when datediff(from_unixtime(unix_timestamp(substr(repordid,1,8),'yyyyMMdd'),'yyyy-MM-dd'),
from_unixtime(unix_timestamp(qrydate,'yyyy年MM月dd日'),'yyyy-MM-dd'))<=90 
and qryreason in('贷款审批','信用卡审批') then 1 end) /count(case when datediff(from_unixtime(unix_timestamp(substr(repordid,1,8),'yyyyMMdd'),'yyyy-MM-dd'),
from_unixtime(unix_timestamp(qrydate,'yyyy年MM月dd日'),'yyyy-MM-dd'))<=90 then 1 end) end) as last_3month_loan_credit_query_num_zb
from creditck.ydjr_zx_report_recordnum_xz t
group by mobilephone,cert_no,name;

drop table creditck.ydjr_zx_report_info_black_xz;
create table creditck.ydjr_zx_report_info_black_xz
as
select distinct mobilephone,t1.cert_no,t1.name,'央行信用卡黑名单' as black_type
from creditck.ydjr_zx_report_recorddesc_credit_xz t1
where delay_90_month>=3
union all
select distinct t2.mobilephone,t2.cert_no,t2.name,'央行贷款黑名单' as black_type
from hds.ydjr_zx_report_recorddesc t1,
creditck.ydjr_zx_report_xz t2
where repordtype in('loanRecodeNormal','loanRecodeYuqi') and t1.repordid=t2.id and 
(case when recorddesc like'%逾期超过90天%' then  substr( recorddesc,instr( recorddesc,'其中')+2,instr(recorddesc,'个月逾期超过90天')-instr(recorddesc,'其中')-2) else 0 end)>=3;

drop table creditck.ydjr_zx_report_info_xz;
create table creditck.ydjr_zx_report_info_xz
as
select distinct substr(t1.mobilephone,1,11) mobilephone,t1.cert_no, t1.name,sex,age, marrystatus as marital_state,bank_num,regular_bank_zb,zhuxiao_bank_zb,delay_bank_zb,first_open_days,last_open_days,
max_amount_days,credit_limit_amount_all,credit_limit_amount_max,credit_limit_amount_min,credit_limit_amount_avg,usedamount_rate,delayamount_rate,delay_cha_num,
(case when query_num is null then 0 else query_num end) as query_num,
(case when last_query_days is null then 1000 else last_query_days end) as last_query_days,
(case when loan_credit_query_num is null then 0 else loan_credit_query_num end) as loan_credit_query_num,
(case when last_loan_credit_query_days is null or last_loan_credit_query_days=999999999 then 1000 else last_loan_credit_query_days end) as last_loan_credit_query_days,
(case when loan_credit_query_num_zb is null then 0 else loan_credit_query_num_zb end) as loan_credit_query_num_zb,
(case when last_3month_query_num is null then 0 else last_3month_query_num end) as last_3month_query_num,
(case when last_3month_loan_credit_query_num is null then 0 else last_3month_loan_credit_query_num end) as last_3month_loan_credit_query_num,
(case when last_3month_loan_credit_query_num_zb=-1 or last_3month_loan_credit_query_num_zb is null then 0 else last_3month_loan_credit_query_num_zb end) as last_3month_loan_credit_query_num_zb,
(case when t5.cert_no is not null then 1 else 0 end) as is_black
from  creditck.ydjr_zx_report_xz t1
left join 
creditck.ydjr_zx_report_recorddesc_credit_var_xz t2
on t1.cert_no=t2.cert_no and t1.name=t2.name
left join
creditck.ydjr_zx_report_recordnum_var_xz t4
on t1.cert_no=t4.cert_no and t1.name=t4.name 
left join 
creditck.ydjr_zx_report_info_black_xz  t5
on t1.cert_no=t5.cert_no 
where t2.bank_num is not null;
"

tmp=$?
NOWTIME=$(date +%Y-%m-%d/%H:%M:%S)
if test $tmp -eq 0; then
   echo $NOWTIME'--------------拉卡拉 app 央行报告 creditck.ydjr_zx_report_info_xz-------------'$tmp
   break
else
   echo $NOWTIME'--------------拉卡拉 app 央行报告 creditck.ydjr_zx_report_info_xz-------------'$tmp
fi


#推送 拉卡拉央行报告推送表
JDBC=jdbc:oracle:thin:@10.1.2.187:1521
SID=crdnewdb
USERNAME=EM
PASSWORD=em_1234

echo '-------------------------------------------拉卡拉央行报告推送表-------------------------------------------'

echo "sudo -u hdfs sqoop export --connect $JDBC/$SID --username $USERNAME --password $PASSWORD --table CDT_LKL_IPCRS_REPORT --export-dir /user/hive/warehouse/creditck.db/ydjr_zx_report_info_xz --fields-terminated-by '\001' --input-null-string '\\N' --input-null-non-string '\\N' --update-key MOBILE,NAME,CERT_NO --update-mode allowinsert -m 10"
echo; echo; echo
sudo -u hdfs sqoop export --connect $JDBC/$SID --username $USERNAME --password $PASSWORD --table CDT_LKL_IPCRS_REPORT --export-dir /user/hive/warehouse/creditck.db/ydjr_zx_report_info_xz --fields-terminated-by '\001' --input-null-string '\\N' --input-null-non-string '\\N' --update-key MOBILE,NAME,CERT_NO --update-mode allowinsert -m 10
tmp=$?
NOWTIME=$(date +%Y-%m-%d/%H:%M:%S)
if test $tmp -eq 0 ; then
    echo $NOWTIME'------------CDT_LKL_IPCRS_REPORT 拉卡拉央行报告推送表--------'$tmp 
else
    echo $NOWTIME'------------CDT_LKL_IPCRS_REPORT 拉卡拉央行报告推送表--------'$tmp 
    echo $NOWTIME'------------拉卡拉央行报告推送表失败'$tmp >> /home/hdfs/xiaodai/logs/xiaodai2_cron_lklreport_critical
    exit -1
fi