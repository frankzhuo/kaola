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

echo '-----------------账单分期 zdfq_count------------'
sudo -u hdfs hive <<EOF
drop table creditck.var_device_ss_skb;
--有分期还款业务的为1，没有的补充为0
drop table creditck.var_zdfq_count;
create table  creditck.var_zdfq_count
as
select mobile as mobile_num,'1' as zdfq_count from ods.rep_ccd_bill group by mobile;  
EOF
tmp=$?
NOWTIME=$(date +%Y-%m-%d/%H:%M:%S)
if test $tmp -eq 0; then
echo $NOWTIME'------2--------账单分期var_zdfq_count创建成功'$tmp >> logs/logs_cdt_datasource_result
else
echo $NOWTIME'------2--------账单分期var_zdfq_count创建失败'$tmp >> logs/logs_cdt_datasource_result
fi



echo '-------------Mpos&手刷 device_ss_skb--------------'
impala-shell -r <<EOF
create table creditck.var_device_ss_skb
as
select t4.mobile_num,
max(case when t2.psam_ab_head='CBC3A3B2' then '1' end) as device_skb,
max(case when t2.busi_type='手机刷卡器' and t2.psam_ab_head<>'CBC3A3B2' then '1' end) as device_ss
from
 edm.d_psam_card_info t2,
 ods.qdb_ucenter_user_pasm t3,
 hds.u_user t4
where substr(t3.psam_no,1,8)=t2.psam_ab_head and t3.user_id=t4.id
group by mobile_num;
EOF
tmp=$?
NOWTIME=$(date +%Y-%m-%d/%H:%M:%S)
if test $tmp -eq 0; then 
echo $NOWTIME'------2--------Mpos 手刷 var_device_ss_skb创建成功'$tmp >> logs/logs_cdt_datasource_result
else
echo $NOWTIME'------2--------Mpos 手刷 var_device_ss_skb创建失败'$tmp >> logs/logs_cdt_datasource_result
fi


echo '-----------------信用卡级别 credit_class,借记卡级别 debit_class,借记卡类别 debit_kind,信用卡张数 credit_count,借记卡张数 debit_count-------------'
sudo -u hdfs hive <<EOF
drop table creditck.var_cardno_lv_cnt;
create table creditck.var_cardno_lv_cnt
as  
select a.mobile_num,
       min(case when a.out_card_type='XYK' and b.card_lv_code is not null then b.card_lv_code end) as credit_class,
       min(case when a.out_card_type='JJK' and b.card_lv_code is not null then b.card_lv_code end) as debit_class,
       max(case when a.out_card_type='JJK' and b.card_sort_code is not null then b.card_sort_code end) as debit_kind,
       count(distinct case when a.out_card_type = 'XYK' then a.outcdno end) as credit_count,
       count(distinct case when a.out_card_type = 'JJK' then a.outcdno end) as debit_count
from creditck.mid_zx_xyk_card a
left join spss.d_card_info_zx b on a.outcdno_bin=b.card_bin and length(a.outcdno)=cast(b.card_len as int)
group by a.mobile_num;
EOF
tmp=$?
NOWTIME=$(date +%Y-%m-%d/%H:%M:%S)
if test $tmp -eq 0; then
echo $NOWTIME'------2--------信用卡/借记卡级别类别张数var_cardno_lv_cnt创建成功'$tmp >> logs/logs_cdt_datasource_result
else
echo $NOWTIME'------2--------信用卡/借记卡级别类别张数var_cardno_lv_cnt创建失败'$tmp >> logs/logs_cdt_datasource_result
fi


echo '----------------钱包余额---------------'
sudo -u hdfs hive <<EOF
use creditck;
add jar /home/hdfs/udfs_hive_0.13_cdh5.4.4_lkl_ver001.jar;
create temporary function add_months as 'com.lkl.hiveudfs.udf_add_months';

drop table creditck.var_qb_balance_max;
create table creditck.var_qb_balance_max
as
select a.mobile,
       max(b.qb_blan/100) as qb_balance_max
from 
(select * from hdm.qb_blan_perday where ymd='${TODAY_1D}') a 
left join hdm.qb_blan_perday b on a.mobile=b.mobile
where substr(a.host_date,1,6)>=substr(add_months(b.host_date,-3),1,6) and a.host_date is not null
group by a.mobile;
EOF
tmp=$?
NOWTIME=$(date +%Y-%m-%d/%H:%M:%S)
if test $tmp -eq 0; then
echo $NOWTIME'------2--------钱包余额 var_qb_balance_max 创建成功'$tmp >> logs/logs_cdt_datasource_result
else
echo $NOWTIME'------2--------钱包余额 var_qb_balance_max 创建失败'$tmp >> logs/logs_cdt_datasource_result
fi



echo '----------------保险&公益捐款---------------'
sudo -u hdfs hive <<EOF
drop table creditck.var_donation_baoxian;
create table creditck.var_donation_baoxian
as
select mobile_num,
sum(case when tcat_lv4_name='公益捐款' then total_am/100 end) as donation,
sum(case when agent_name like'%保险%' then total_am/100 end) as baoxian
from hdm.f_fact_trans_success_new_details
where ymd>='${DATE_24M}' and ymd<'${DATE_0M}'
group by mobile_num;
EOF
tmp=$?
NOWTIME=$(date +%Y-%m-%d/%H:%M:%S)
if test $tmp -eq 0; then
echo $NOWTIME'------2--------保险 var_donation_baoxian 创建成功'$tmp >> logs/logs_cdt_datasource_result
echo 'cdt_datasource_result2_2------ok------'${TODAY_0D}
else
echo $NOWTIME'------2--------保险 var_donation_baoxian 创建失败'$tmp >> logs/logs_cdt_datasource_result
fi
