#!/bin/bash
TODAY_1D=$(date --date='1 day ago' +%Y%m%d)
TODAY_0D=$(date +%Y%m%d)

echo $(date +%Y-%m-%d/%H:%M:%S) "-------------小贷央行报告-----------开始----"

sudo -u hdfs hive -e "
set hive.auto.convert.join=false;
set mapreduce.reduce.shuffle.input.buffer.percent=0.1;
set mapreduce.reduce.shuffle.parallelcopies=9;

-------------------------------------------------------------以下是小贷系统直接调取央行征信记录表
-------------------------------基础数据
drop table creditck.xd_auth_history_custinfo_new_xz;
create table creditck.xd_auth_history_custinfo_new_xz
as
select t1.*
from hds.xd_auth_history_custinfo t1,
(select  cert_no,name,max(insert_time) as insert_time
from hds.xd_auth_history_custinfo t1
group by cert_no,name) t2
where t1.cert_no=t2.cert_no and t1.name=t2.name and t1.insert_time=t2.insert_time;

drop table  creditck.xd_auth_history_custinfo_xz;
create table creditck.xd_auth_history_custinfo_xz
as
select distinct cert_no,name,mobile,
(case when cast(substr(cert_no,17,1) as int)%2=0 then '女' else '男' end) as sex,
2016-cast(substr(cert_no,7,4) as int) as age,
marital_state
from creditck.xd_auth_history_custinfo_new_xz t1;

------------------------------逾期表
drop table creditck.xd_auth_history_overduerecord_xz;
create table creditck.xd_auth_history_overduerecord_xz
as 
select id,sid,cid,merchant,contract_no,t1.insert_time,work_date,agent_serial_no,fid,cert_type,t1.cert_no,
t1.name,cast(num as int) as num,regexp_replace(monthdjk,'.','') as months,cast(last_monthsdjk as int) as last_months,cast(regexp_replace(amountdjk,',','') as float) as amount,load_date,sys_no,'贷记卡' as type
from hds.xd_auth_history_overduerecorddjk t1,
(select cert_no,name,max(insert_time) as insert_time
from  hds.xd_auth_history_overduerecorddjk t1
group by cert_no,name)t2
where t1.cert_no=t2.cert_no and t1.name=t2.name and t1.insert_time=t2.insert_time
union all
select id,sid,cid,merchant,contract_no,t1.insert_time,work_date,agent_serial_no,fid,cert_type,t1.cert_no,
t1.name,cast(num as int) as num,regexp_replace(monthzdjk,'.','') as months,cast(last_monthszdjk as int) as last_months,cast(regexp_replace(amountzdjk,',','') as float) as amount,load_date,sys_no,'准贷记卡' as type
from hds.xd_auth_history_overduerecordzdjk t1,
(select cert_no,name,max(insert_time) as insert_time
from  hds.xd_auth_history_overduerecordzdjk t1
group by cert_no,name)t2
where t1.cert_no=t2.cert_no and t1.name=t2.name and t1.insert_time=t2.insert_time 
union all
select id,sid,cid,merchant,contract_no,t1.insert_time,work_date,agent_serial_no,fid,cert_type,t1.cert_no,
t1.name,cast(num as int) as num,regexp_replace(monthdk,'.','') as months,cast(last_monthsdk as int) as last_months,cast(regexp_replace(amountdk,',','') as float) as amount,load_date,sys_no,'贷款' as type
from hds.xd_auth_history_overduerecorddk t1,
(select cert_no,name,max(insert_time) as insert_time
from  hds.xd_auth_history_overduerecorddk t1
group by cert_no,name)t2
where t1.cert_no=t2.cert_no and t1.name=t2.name and t1.insert_time=t2.insert_time;

-----------------------------黑名单表
drop table creditck.xd_auth_history_black_xz;
create table creditck.xd_auth_history_black_xz
as
select distinct cert_no,name
from creditck.xd_auth_history_overduerecord_xz t
where last_months>=6 and amount>1000;

----------------------------合并贷记卡和准贷记卡数据
drop table creditck.xd_auth_history_card_xz;
create table creditck.xd_auth_history_card_xz
as
select id,sid,cid,merchant,contract_no,t1.insert_time,work_date,agent_serial_no,cert_type,t1.cert_no,t1.name,cuedjk,finance_orgdjk,finance_typedjk,accountdjk,currencydjk,
open_datedjk,cast(regexp_replace(credit_limit_amountdjk,',','') as int) as credit_limit_amountdjk,
guarantee_typedjk,statedjk,state_end_datedjk,state_end_monthdjk,
cast(regexp_replace(share_credit_limit_amountdjk,',','') as int) as share_credit_limit_amountdjk,
cast(regexp_replace(used_credit_limit_amountdjk,',','') as int) as used_credit_limit_amountdjk,
cast(regexp_replace(latest6m_used_avg_amount,',','') as int) as latest6m_used_avg_amountdjk,
cast(regexp_replace(used_highest_amountdjk,',','') as int) as used_highest_amountdjk,
scheduled_payment_datedjk,
cast(regexp_replace(scheduled_payment_amountdjk,',','') as int) as scheduled_payment_amountdjk,
cast(regexp_replace(actual_payment_amountdjk,',','') as int) as actual_payment_amountdjk,
recent_payd_atedjk,curr_overdue_cycdjk,
cast(regexp_replace(curr_overdue_amountdjk,',','') as int) as curr_overdue_amountdjk,
'' as overdue_over180_amountdjk,
begin_month24djk,end_month24djk,latest24_statedjk,begin_month5djk,end_month5djk,balancedjk,load_date,sys_no,'贷记卡' as card_type
from hds.xd_auth_history_loancard t1,
(select cert_no,name,max(insert_time) as insert_time
from hds.xd_auth_history_loancard t1
group by cert_no,name) t2
where t1.cert_no=t2.cert_no and t1.name=t2.name and t1.insert_time=t2.insert_time
union all
select id as id,sid as sid,cid as cid,merchant as merchant,contract_no as contract_no,t1.insert_time as insert_time,work_date as work_date,agent_serial_no as agent_serial_no,
cert_type as cert_type,t1.cert_no as cert_no,t1.name as name,cuezdjk as cuedjk,finance_orgzdjk as finance_orgdjk,finance_typezdjk as finance_typedjk,accountzdjk as accountdjk,currencyzdjk as currencydjk,
open_datezdjk as open_datedjk,
cast(regexp_replace(credit_limit_amountzdjk,',','') as int) as credit_limit_amountdjk,
guarantee_typezdjk as guarantee_typedjk,statezdjk as statedjk,state_end_datezdjk as state_end_datedjk,
state_end_monthzdjk as state_end_monthdjk,
cast(regexp_replace(share_credit_limit_amountzdjk,',','') as int) as share_credit_limit_amountdjk,
cast(regexp_replace(used_credit_limit_amountzdjk,',','') as int) as used_credit_limit_amountdjk,
cast(regexp_replace(latest6m_used_avg_amountzdjk,',','') as int) as latest6m_used_avg_amountdjk,
cast(regexp_replace(used_highest_amountzdjk,',','') as int) as used_highest_amountdjk,
scheduled_payment_datezdjk as scheduled_payment_datedjk,
cast(regexp_replace(scheduled_payment_amountzdjk,',','') as int) as scheduled_payment_amountdjk,
cast(regexp_replace(actual_payment_amountzdjk,',','') as int) as actual_payment_amountdjk,
recent_pay_datezdjk as recent_payd_atedjk,
'' as curr_overdue_cycdjk,
(case when cast(regexp_replace(scheduled_payment_amountzdjk,',','') as int)-cast(regexp_replace(actual_payment_amountzdjk,',','') as int)<=0 
and cast(regexp_replace(overdue_over180_amountzdjk,',','') as int)>0 then cast(regexp_replace(overdue_over180_amountzdjk,',','') as int)
when  cast(regexp_replace(scheduled_payment_amountzdjk,',','') as int)-cast(regexp_replace(actual_payment_amountzdjk,',','') as int)<=0 then 0 
else cast(regexp_replace(scheduled_payment_amountzdjk,',','') as int)-cast(regexp_replace(actual_payment_amountzdjk,',','') as int)end)  as curr_overdue_amountdjk,
cast(regexp_replace(overdue_over180_amountzdjk,',','') as int) as overdue_over180_amountdjk,
begin_month24zdjk as begin_month24djk,end_month24zdjk as end_month24djk,latest24_statezdjk as latest24_statedjk,
begin_month5zdjk as begin_month5djk,end_month5zdjk as end_month5djk,balancezdjk as balancedjk,load_date as load_date,sys_no as sys_no,'准贷记卡' as card_type
from hds.xd_auth_history_standardloancard t1,
(select  cert_no,name,max(insert_time) as insert_time
from hds.xd_auth_history_standardloancard t1
group by cert_no,name) t2
where t1.cert_no=t2.cert_no and t1.name=t2.name and t1.insert_time=t2.insert_time;

drop table creditck.xd_auth_history_overduerecord_alter_xz;
create table creditck.xd_auth_history_overduerecord_alter_xz
as
select t.fid,t.type,count(1) as cn,count(case when last_months>=3 then 1 end) as delay_90_cn
from creditck.xd_auth_history_overduerecord_xz t
group by t.fid,t.type;

---------------------------信用卡记录变量表
drop table creditck.xd_auth_history_card_variable_xz;
create table creditck.xd_auth_history_card_variable_xz
as
select cert_no,name,
count(distinct finance_orgdjk) as bank_num,
count(distinct case when is_regular_service=1 then finance_orgdjk end)/count(distinct finance_orgdjk) as regular_bank_zb,
count(distinct case when is_all_zhuxiao=1 then finance_orgdjk end)/count(distinct finance_orgdjk) as zhuxiao_bank_zb,
count(distinct case when is_delay=1 then finance_orgdjk end)/count(distinct finance_orgdjk) as delay_bank_zb,
max(case when is_regular_service=1 then first_open_days end) as first_open_days,
min(case when is_regular_service=1 then last_open_days end) as last_open_days,  
datediff(from_unixtime(unix_timestamp(substr(max(insert_time),1,10),'yyyy-MM-dd'),'yyyy-MM-dd'),
split(max(concat(lpad(cast((case when  is_regular_service=1 then credit_limit_amountdjk end) as string),10,'0'),',',max_amount_open_date)),',')[1]) as max_amount_days,
sum(case when is_regular_service=1 then credit_limit_amountdjk end) as credit_limit_amount_all,
max(case when is_regular_service=1 then credit_limit_amountdjk end) as credit_limit_amount_max,
min(case when is_regular_service=1 then credit_limit_amountdjk end) as credit_limit_amount_min,
avg(case when is_regular_service=1 then credit_limit_amountdjk end) as credit_limit_amount_avg,
(case when sum(credit_limit_amountdjk)=0 then -1 else sum(case when is_regular_service=1 then used_credit_limit_amountdjk end)/sum(case when is_regular_service=1 then credit_limit_amountdjk end) end)
  as usedamount_rate,
(case when sum(credit_limit_amountdjk)=0 then -1 when sum(used_credit_limit_amountdjk)=0 then 0 else sum(case when is_regular_service=1 then delay_amountdjk end)
/sum(case when is_regular_service=1 then used_credit_limit_amountdjk end) end)  as delayamount_rate,
sum(delay_cha) as delay_cha_num
from (
select cert_no,name,finance_orgdjk,max(insert_time) as insert_time,
max(case when statedjk='正常' then 1 else 0 end) as is_regular_service,
min(case when statedjk='销户' then 1 else 0 end) as is_all_zhuxiao,
max(case when t2.fid is null then 0 else 1 end) as is_delay,
max(case when statedjk='正常' then datediff(from_unixtime(unix_timestamp(substr(insert_time,1,10),'yyyy-MM-dd'),'yyyy-MM-dd')
,from_unixtime(unix_timestamp(open_datedjk,'yyyy年MM月dd日'),'yyyy-MM-dd')) else 0 end) as first_open_days,
min(case when statedjk='正常' then datediff(from_unixtime(unix_timestamp(substr(insert_time,1,10),'yyyy-MM-dd'),'yyyy-MM-dd')
,from_unixtime(unix_timestamp(open_datedjk,'yyyy年MM月dd日'),'yyyy-MM-dd')) else 999999999 end) as last_open_days,
from_unixtime(unix_timestamp(split(max(concat(lpad(cast((case when statedjk='正常' then credit_limit_amountdjk else 0 end)as string),10,'0'),',',open_datedjk))
,',')[1],'yyyy年MM月dd日'),'yyyy-MM-dd') as max_amount_open_date,
max(case when statedjk='正常' and credit_limit_amountdjk is not null then credit_limit_amountdjk  else 0 end) as credit_limit_amountdjk,
max(case when statedjk='正常' and used_credit_limit_amountdjk is not null then used_credit_limit_amountdjk  else 0 end) as used_credit_limit_amountdjk,
max(case when statedjk='正常' and used_credit_limit_amountdjk is not null and curr_overdue_amountdjk is not null then curr_overdue_amountdjk  else 0 end) as delay_amountdjk,
max(case when statedjk='正常' and actual_payment_amountdjk is not null then actual_payment_amountdjk  else 0 end) as actual_payment_amountdjk,
max(case when statedjk='正常' and scheduled_payment_amountdjk is not null then scheduled_payment_amountdjk else 0 end) as scheduled_payment_amountdjk,
max(case when currencydjk<>'人民币账户' and actual_payment_amountdjk is not null and actual_payment_amountdjk>0 then 1 else 0 end) as abroad_actualpaymentnum,
max(case when actual_payment_amountdjk is not null and actual_payment_amountdjk>0 then 1 else 0 end) as actualpaymentnum,
max(case when currencydjk<>'人民币账户' and scheduled_payment_amountdjk is not null  and scheduled_payment_amountdjk>0 then 1 else 0 end) as abroad_schedulepaymentnum,
sum(case when t2.cn is null then 0 else cn end) as delay_num,
sum(case when t2.cn is null then 0 
when t2.delay_90_cn=0 or t2.delay_90_cn is null then t2.cn
when t2.delay_90_cn is not null and t2.delay_90_cn>0 and t2.cn-(t2.delay_90_cn+3)<0 then 0
else  t2.cn-(t2.delay_90_cn+3) end) as delay_cha
from creditck.xd_auth_history_card_xz t1
left join 
creditck.xd_auth_history_overduerecord_alter_xz t2
on cast(t2.fid as int)=cast(t1.id as int) and t1.card_type=t2.type
group by  cert_no,name,finance_orgdjk) t
group by cert_no,name;

-----------------------查询记录表
drop table creditck.xd_auth_history_recorddetail_xz;
create table  creditck.xd_auth_history_recorddetail_xz
as
select id,sid,cid,merchant,contract_no,t1.insert_time,
work_date,agent_serial_no,fid,cert_type,
t1.cert_no,t1.name,num,query_date,
querier,query_reasonmx,load_date,sys_no
from hds.xd_auth_history_recorddetail t1,
(select cert_no,name,max(insert_time) as insert_time
from  hds.xd_auth_history_recorddetail t1
group by cert_no,name) t2
where t1.cert_no=t2.cert_no and t1.name=t2.name and t1.insert_time=t2.insert_time;

----------------------查询记录变量表
drop table creditck.xd_auth_history_query_variable_xz;
create table  creditck.xd_auth_history_query_variable_xz
as
select cert_no,name,count(1) as query_num,
min( datediff(from_unixtime(unix_timestamp(substr(insert_time,1,10),'yyyy-MM-dd'),'yyyy-MM-dd'),
from_unixtime(unix_timestamp(query_date,'yyyy.MM.dd'),'yyyy-MM-dd')) ) as last_query_days,
count(case when query_reasonmx in('贷款审批','信用卡审批') then 1 end) as loan_credit_query_num,
min( case when query_reasonmx in('贷款审批','信用卡审批') then datediff(from_unixtime(unix_timestamp(substr(insert_time,1,10),'yyyy-MM-dd'),'yyyy-MM-dd'),
from_unixtime(unix_timestamp(query_date,'yyyy.MM.dd'),'yyyy-MM-dd')) else 99999999 end )  as last_loan_credit_query_days,
count(case when query_reasonmx in('贷款审批','信用卡审批') then 1 end)/count(1) as loan_credit_query_num_zb,
count(case when datediff(from_unixtime(unix_timestamp(substr(insert_time,1,10),'yyyy-MM-dd'),'yyyy-MM-dd'),
from_unixtime(unix_timestamp(query_date,'yyyy.MM.dd'),'yyyy-MM-dd'))<=90 then 1 end) as last_3month_query_num,
count(case when datediff(from_unixtime(unix_timestamp(substr(insert_time,1,10),'yyyy-MM-dd'),'yyyy-MM-dd'),
from_unixtime(unix_timestamp(query_date,'yyyy.MM.dd'),'yyyy-MM-dd'))<=90 
and query_reasonmx in('贷款审批','信用卡审批') then 1 end) as last_3month_loan_credit_query_num,
(Case when count(case when datediff(from_unixtime(unix_timestamp(substr(insert_time,1,10),'yyyy-MM-dd'),'yyyy-MM-dd'),
from_unixtime(unix_timestamp(query_date,'yyyy.MM.dd'),'yyyy-MM-dd'))<=90 then 1 end)=0 then -1 else 
count(case when datediff(from_unixtime(unix_timestamp(substr(insert_time,1,10),'yyyy-MM-dd'),'yyyy-MM-dd'),
from_unixtime(unix_timestamp(query_date,'yyyy.MM.dd'),'yyyy-MM-dd'))<=90 
and query_reasonmx in('贷款审批','信用卡审批') then 1 end) /count(case when datediff(from_unixtime(unix_timestamp(substr(insert_time,1,10),'yyyy-MM-dd'),'yyyy-MM-dd'),
from_unixtime(unix_timestamp(query_date,'yyyy.MM.dd'),'yyyy-MM-dd'))<=90 then 1 end) end) as last_3month_loan_credit_query_num_zb
from creditck.xd_auth_history_recorddetail_xz t
group by cert_no,name;

drop table creditck.xd_auth_history_all_info_all_xz;
create table creditck.xd_auth_history_all_info_all_xz
as
select distinct (case when length(t1.mobile)>11 then '' else t1.mobile end) as mobile,
t1.cert_no,t1.name,sex,age, marital_state,bank_num,regular_bank_zb,zhuxiao_bank_zb,delay_bank_zb,first_open_days,last_open_days,
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
from creditck.xd_auth_history_custinfo_xz t1
left join 
creditck.xd_auth_history_card_variable_xz t2
on t1.cert_no=t2.cert_no and t1.name=t2.name
left join
creditck.xd_auth_history_query_variable_xz t4
on t1.cert_no=t4.cert_no and t1.name=t4.name 
left join 
creditck.xd_auth_history_black_xz t5
on t1.cert_no=t5.cert_no and t1.name=t5.name
where bank_num is not null;
"

tmp=$?
NOWTIME=$(date +%Y-%m-%d/%H:%M:%S)
if test $tmp -eq 0; then
   echo $NOWTIME'--------------小贷系统直接调取央行征信记录表 creditck.xd_auth_history_all_info_all_xz-------------'$tmp
   break
else
   echo $NOWTIME'--------------小贷系统直接调取央行征信记录表 creditck.xd_auth_history_all_info_all_xz-------------'$tmp
fi

#exit;

#推送 小贷央行报告推送表
JDBC=jdbc:oracle:thin:@10.1.2.187:1521
SID=crdnewdb
USERNAME=EM
PASSWORD=em_1234

echo '-------------------------------------------小贷央行报告推送表-------------------------------------------'

echo "sudo -u hdfs sqoop export --connect $JDBC/$SID --username $USERNAME --password $PASSWORD --table CDT_XD_IPCRS_REPORT --export-dir /user/hive/warehouse/creditck.db/xd_auth_history_all_info_all_xz --fields-terminated-by '\001' --input-null-string '\\N' --input-null-non-string '\\N' --update-key NAME,CERT_NO --update-mode allowinsert -m 10"
echo; echo; echo
sudo -u hdfs sqoop export --connect $JDBC/$SID --username $USERNAME --password $PASSWORD --table CDT_XD_IPCRS_REPORT --export-dir /user/hive/warehouse/creditck.db/xd_auth_history_all_info_all_xz --fields-terminated-by '\001' --input-null-string '\\N' --input-null-non-string '\\N' --update-key NAME,CERT_NO --update-mode allowinsert -m 10
tmp=$?
NOWTIME=$(date +%Y-%m-%d/%H:%M:%S)
if test $tmp -eq 0 ; then
    echo $NOWTIME'------------CDT_XD_IPCRS_REPORT 小贷央行报告推送表--------'$tmp 
else
    echo $NOWTIME'------------CDT_XD_IPCRS_REPORT 小贷央行报告推送表--------'$tmp 
    echo $NOWTIME'------------小贷央行报告推送表失败'$tmp >> /home/hdfs/xiaodai/logs/xiaodai2_cron_xdreport_critical
    exit -1
fi