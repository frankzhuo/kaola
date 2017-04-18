#!/bin/sh
TODAY_2Y=$(date --date='2 year ago' +%Y%m%d)
TODAY_0D=$(date +%Y%m%d)
TODAY_0d=$(date +%Y-%m-%d)
TODAY_1D=$(date --date='1 day ago' +%Y%m%d)
TODAY_1d=$(date --date='1 day ago' +%Y-%m-%d)
TODAY_2D=$(date --date='2 day ago' +%Y%m%d)
TODAY_2d=$(date --date='2 day ago' +%Y-%m-%d)
DATE_0m=$(date --date='0 month ago' +%Y-%m-)01
DATE_0M=$(date --date='0 month ago' +%Y%m)01
day=$(date +%d)
if test $day -lt 28;then
DATE_3m=$(date --date='3 month ago' +%Y-%m-)01
DATE_6m=$(date --date='6 month ago' +%Y-%m-)01
DATE_12m=$(date --date='1 year ago' +%Y-%m-)01
DATE_12M=$(date --date='1 year ago' +%Y%m)01
DATE_24M=$(date --date='2 year ago' +%Y%m)01
else
DATE_3m=$(date --date='3 month ago 10 day ago' +%Y-%m-)01
DATE_6m=$(date --date='6 month ago 10 day ago' +%Y-%m-)01
DATE_12m=$(date --date='1 year ago 10 day ago' +%Y-%m-)01
DATE_12M=$(date --date='1 year ago 10 day ago' +%Y%m)01
DATE_24M=$(date --date='2 year ago 10 day ago' +%Y%m)01
fi


echo '------------------信用卡总额度credit_limit   额度使用率credit_rate-------'
sudo -u hdfs hive <<EOF
drop table creditck.var_credit_limit_rate;
create table creditck.var_credit_limit_rate as
select mobile_num,sum(credit_total_limit)/count(distinct data_month) as credit_limit,sum(credit_rate_oth)/count(distinct data_month) as credit_rate
from(select mobile_num,from_unixtime(unix_timestamp(billdate),'yyyyMM')  as data_month,sum(creditlimit) as credit_total_limit,sum(case when newbalance>0 then newbalance else 0 end) as this_pay_cash,
sum(case when newbalance>0 then newbalance else 0 end) /sum(creditlimit) as credit_rate_oth,
sum(case when newbalance>0 then newbalance else 0 end) /count(distinct bankid) as sigle_bank_pjpay_cash,
count(distinct bankid) as bank_count
from(
select t1.*
from
(select t1.*,t2.appemailkey,t2.user_id,t3.real_name,t3.mobile_num
from ods.zd51_creditcard_bill_detail t1,
ods.zd51_tab_creditcard_user_email t2,
ods.qdb_ucenter_user t3,
(select appemailkey,count(distinct user_id)
from ods.zd51_tab_creditcard_user_email t
group by appemailkey
having count(distinct user_id)=1) t4
where concat('7_',t1.emailkey)=t2.appemailkey and t2.user_id=t3.id and t2.appemailkey=t4.appemailkey
and billdate>='${DATE_6m}'
and billdate<'${DATE_0m}'
and creditlimit>0
union all
select t1.*,t2.appemailkey,t2.user_id,t3.real_name,t3.mobile_num
from ods.zd51_creditcard_bill_detail t1,
ods.zd51_tab_creditcard_user_email t2,
ods.qdb_ucenter_user t3,
(select appemailkey,count(distinct user_id)
from ods.zd51_tab_creditcard_user_email t
group by appemailkey
having count(distinct user_id)>=2) t4
where concat('7_',t1.emailkey)=t2.appemailkey and t2.user_id=t3.id and t2.appemailkey=t4.appemailkey
and t1.nameoncard=t3.real_name and t3.real_name is not null
and billdate>='${DATE_6m}'
and billdate<'${DATE_0m}'
and creditlimit>0 ) t1,
(select emailkey,count(distinct nameoncard)
from ods.zd51_creditcard_bill_detail t1
where billdate>='${DATE_6m}'
and billdate<'${DATE_0m}'
group by emailkey
having  count(distinct nameoncard)=1) t2
where t1.emailkey=t2.emailkey)  tmp
group by mobile_num,from_unixtime(unix_timestamp(billdate),'yyyyMM') ) t
group by mobile_num;
EOF
tmp=$?
NOWTIME=$(date +%Y-%m-%d/%H:%M:%S)
if test $tmp -eq 0; then
echo $NOWTIME'-------4-------var_credit_limit_rate创建成功'$tmp >> logs/logs_cdt_datasource_result
else
echo $NOWTIME'-------4-------var_credit_limit_rate创建失败'$tmp >> logs/logs_cdt_datasource_result
fi

echo '-----------------贷记卡当期消费额度 credit_balance  信用卡查询月份credit_cxmonth--------'
sudo -u hdfs hive <<EOF
drop table  creditck.var_debit_balance;
drop table  creditck.var_credit_balance_cxmonth;
create table creditck.var_credit_balance_cxmonth 
as
select a.mobile_num,
       sum(b.max_am)/count(distinct b.data_month) as credit_balance,
       count(distinct b.data_month) as credit_cxmonth
from creditck.mid_mobile_outcdno a,
(select t4.de2,from_unixtime(unix_timestamp(t4.systemtime),'yyyyMM') as data_month,max(abs(t4.blan_1)) as max_am
from edw.dw_px_de54_blance_his t4,
edm.d_cardinfo_apply t5
where t4.de2 like concat(t5.card_bin,'%') and length(t4.de2)=t5.card_len and t5.card_type='XYK' and t4.ymd>='${DATE_6m}' and t4.ymd<'${TODAY_0d}' 
and t4.acc_type='30' and ((t5.bank_short_name in('中国银行','建设银行','中国工商银行','农业银行','光大银行') and t4.blan_status_code_1='D')
or (t5.bank_short_name in('民生银行','华夏银行','平安银行','兴业银行','北京银行','南京银行','宁波银行','邮储银行',
'上海银行','甘肃省农村信用社联合社','包头市商业银行','河北银行','上海农村商业银行','内蒙古银行',
'北京农村商业银行','江苏银行','长沙市商业银行','天津农村合作银行','广东顺德农村商业银行','赣州银行',
'东莞商行','大连银行','重庆农村商业银行','徽商银行','临汾市尧都区农村信用合作联社','贵阳市商业商行',
'湖南农村信用社联合社','云南省农村信用社','台州市商业银行','浙江民泰商业银行','广州农村商业银行',
'宁夏银行','江苏省农村信用社联合社','成都市农村信用合作社联合社','汉口银行','哈尔滨商行',
'浙江泰隆商业银行','江阴市农村商业银行','杭州市商业银行','东亚银行','南昌银行','周口市商业银行',
'广州市商业银行','华融湘江银行','天津市商业银行','宁波鄞州农村合作银行','福建省农村信用社联合社','渣打银行'
) and t4.blan_status_code_1='C'))
group by t4.de2,from_unixtime(unix_timestamp(t4.systemtime),'yyyyMM')) b
where a.outcdno=b.de2 group by a.mobile_num; 
EOF
tmp=$?
NOWTIME=$(date +%Y-%m-%d/%H:%M:%S)
if test $tmp -eq 0; then 
echo $NOWTIME'-------4-------var_credit_balance_cxmonth创建成功'$tmp >> logs/logs_cdt_datasource_result
else
echo $NOWTIME'-------4-------var_credit_balance_cxmonth创建失败'$tmp >> logs/logs_cdt_datasource_result
fi



echo '--------------------借记卡最新余额 debit_balance----------'
impala-shell -r <<EOF
create table creditck.var_debit_balance
as 
select a.mobile_num,
sum(b.blan_1) as debit_balance
from creditck.mid_mobile_outcdno a,
(select de2,
blan_1,
systemtime,
row_number() over(partition by de2 order by systemtime desc) as new_timeid
from edw.dw_px_de54_blance_his 
where ymd>='${DATE_3m}' and ymd<'${TODAY_0d}' and acc_type='10' and blan_1=blan_2 and blan_1>=0) b
where a.outcdno=b.de2 and b.new_timeid=1 group by a.mobile_num;
EOF
tmp=$?
NOWTIME=$(date +%Y-%m-%d/%H:%M:%S)
if test $tmp -eq 0; then
echo $NOWTIME'------4--------var_debit_balance创建成功'$tmp >> logs/logs_cdt_datasource_result
else
echo $NOWTIME'------4--------var_debit_balance创建失败'$tmp >> logs/logs_cdt_datasource_result
fi



echo '----------------借记卡查询月份debit_cxmonth--------------'
sudo -u hdfs hive <<EOF
drop table  creditck.var_debit_cxmonth;
create table creditck.var_debit_cxmonth
as  
select a.mobile_num,
count(distinct b.data_month) as debit_cxmonth
from creditck.mid_mobile_outcdno a,
(select de2,
from_unixtime(unix_timestamp(systemtime),'yyyyMM') as data_month
from edw.dw_px_de54_blance_his
where ymd>='${DATE_6m}'
and ymd<'${TODAY_0d}'
and acc_type='10' and blan_1>0
group by de2,from_unixtime(unix_timestamp(systemtime),'yyyyMM') ) b
where a.outcdno=b.de2 group by a.mobile_num; 
EOF
tmp=$?
NOWTIME=$(date +%Y-%m-%d/%H:%M:%S)
if test $tmp -eq 0; then
echo $NOWTIME'-------4-------var_debit_cxmonth-'${TODAY_0D}'创建成功'$tmp >> logs/logs_cdt_datasource_result
echo 'cdt_datasource_result2_4------ok------'${TODAY_0D}
else
echo $NOWTIME'-------4-------var_debit_cxmonth-'${TODAY_0D}'创建失败'$tmp >> logs/logs_cdt_datasource_result
fi
