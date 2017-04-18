#!/bin/sh
#个人信用报告书需求
cd /home/hdfs/demands/personal_credit

TODAY_2Y=$(date --date='2 year ago' +%Y%m%d)
TODAY_6m=$(date --date='6 month ago' +%Y%m%d)

echo '------公缴类汇总:总金额、总笔数、笔均金额-------------------------'
sudo -u hdfs hive <<EOF
drop table creditck.t_public_payment_total;
create table creditck.t_public_payment_total
as 
select  mobile_num ,
sum(total_am)/100 as  total_am,--总金额
count(1)  as cnt,--总笔数
sum(total_am)/(100*count(1))as bj_am --笔均金额
from   edm.f_fact_trans_success_new_details
where  tcat_lv3_name='公缴类'
and length(mobile_num)=11
and ymd >='${TODAY_2Y}' 
group by mobile_num;
EOF
tmp=$?
NOWTIME=$(date +%Y-%m-%d/%H:%M:%S)
if test $tmp -eq 0; then
echo $NOWTIME'--------------t_public_payment_total创建成功'$tmp >>  logs/logs_personal_credit_report_result
else
echo $NOWTIME'--------------t_public_payment_total创建失败'$tmp >>  logs/logs_personal_credit_report_result
fi


echo '------公缴类细项(电费、水费、燃气费、通讯费、有线电视费):总金额、总笔数、笔均金额-------------------------'
sudo -u hdfs hive <<EOF
drop table creditck.t_public_payment_list;
create table creditck.t_public_payment_list
as
select mobile_num,
sum(case when public_payment_type='电费'  then  total_am end) as ele_total_am,
sum(case when public_payment_type='电费'  then  cnt end) as ele_cnt,
sum(case when public_payment_type='电费'  then  bj_am end) as ele_bj_am,
sum(case when public_payment_type='水费'  then  total_am end) as w_total_am,
sum(case when public_payment_type='水费'  then  cnt end) as w_cnt,
sum(case when public_payment_type='水费'  then  bj_am end) as w_bj_am,
sum(case when public_payment_type='燃气费'  then  total_am end) as gas_total_am,
sum(case when public_payment_type='燃气费'  then  cnt end) as gas_cnt,
sum(case when public_payment_type='燃气费'  then  bj_am end) as gas_bj_am,
sum(case when public_payment_type='通讯费'  then  total_am end) as comm_total_am,
sum(case when public_payment_type='通讯费'  then  cnt end) as comm_cnt,
sum(case when public_payment_type='通讯费'  then  bj_am end) as comm_bj_am,
sum(case when public_payment_type='有线电视费'  then  total_am end) as tv_total_am,
sum(case when public_payment_type='有线电视费'  then  cnt end) as tv_cnt,
sum(case when public_payment_type='有线电视费'  then  bj_am end) as tv_bj_am
from
(
select  mobile_num,
(case when tcat_lv5_name='公缴费-电力'  then  '电费'
when tcat_lv5_name='公缴费-水'  then  '水费'
when tcat_lv5_name='公缴费-煤气'  then  '燃气费'
when tcat_lv5_name='公缴费-联通话费' or  tcat_lv5_name='公缴费-移动话费'  or   tcat_lv5_name='公缴费-电信话费'  or tcat_lv5_name='公缴费-宽带'  then  '通讯费'
when tcat_lv5_name='公缴费-有线电视'  then  '有线电视费'  end)  as public_payment_type,--公缴类型
sum(total_am)/100  as  total_am,--总金额
count(1)  as cnt,--总笔数
sum(total_am)/(100*count(1)) as bj_am --笔均金额
from  edm.f_fact_trans_success_new_details
where length(mobile_num)=11 and ymd >='${TODAY_2Y}'  
group by mobile_num,
(case when tcat_lv5_name='公缴费-电力'  then  '电费'
when tcat_lv5_name='公缴费-水'  then  '水费'
when tcat_lv5_name='公缴费-煤气'  then  '燃气费'
when tcat_lv5_name='公缴费-联通话费' or  tcat_lv5_name='公缴费-移动话费'  or   tcat_lv5_name='公缴费-电信话费'  or tcat_lv5_name='公缴费-宽带'  then  '通讯费'
when tcat_lv5_name='公缴费-有线电视'  then  '有线电视费'  end)
) tmp
group by  mobile_num;
EOF
tmp=$?
NOWTIME=$(date +%Y-%m-%d/%H:%M:%S)
if test $tmp -eq 0; then
echo $NOWTIME'--------------t_public_payment_list创建成功'$tmp >>  logs/logs_personal_credit_report_result
else
echo $NOWTIME'--------------t_public_payment_list创建失败'$tmp >>  logs/logs_personal_credit_report_result
fi


echo '------公缴类整合-------------------------'
sudo -u hdfs hive <<EOF
drop table creditck.t_public_payment_all;
create table  creditck.t_public_payment_all
as 
select  a.mobile_num,
a.total_am,--总金额
a.cnt,--总笔数
a.bj_am,----笔均金额
b.ele_total_am,--电费(金额)
b.ele_cnt,--电费(笔数)
b.ele_bj_am,--电费(笔均金额)
b.w_total_am,--水费(金额)
b.w_cnt,--水费(笔数)
b.w_bj_am,--水费(笔均金额)
b.gas_total_am,--燃气费(金额)
b.gas_cnt,--燃气费笔数)
b.gas_bj_am,--燃气费(笔均金额)
b.comm_total_am,--通讯费(金额)
b.comm_cnt,----通讯费(笔数)
b.comm_bj_am,----通讯费(笔均金额)
b.tv_total_am,--有线电视费(金额)
b.tv_cnt,--有线电视费(笔数)
b.tv_bj_am--有线电视费(笔均金额)
from creditck.t_public_payment_total a ,
creditck.t_public_payment_list b where a.mobile_num=b.mobile_num;
EOF
tmp=$?
NOWTIME=$(date +%Y-%m-%d/%H:%M:%S)
if test $tmp -eq 0; then
echo $NOWTIME'--------------t_public_payment_all创建成功'$tmp >>  logs/logs_personal_credit_report_result
else
echo $NOWTIME'--------------t_public_payment_all创建失败'$tmp >>  logs/logs_personal_credit_report_result
fi



echo '----------最近2年转账汇款、还款业务交易金额排名前三的用户和卡号中间表--------------'
sudo -u hdfs hive <<EOF
drop table  creditck.t_trans_2y_top3_mid;
create table creditck.t_trans_2y_top3_mid
as 
select  mobile_num,outcdno,rank_desc from 
(
select  mobile_num,outcdno,sum(total_am)/100 as amt,row_number() over(partition by mobile_num order by sum(total_am)/100 desc)   as  rank_desc
from   edm.f_fact_trans_success_new_details
where  length(mobile_num)=11 and ymd >='${TODAY_2Y}'  and  (tcat_lv3_name='转账汇款'  or  tcat_lv3_name='还款')
group by  mobile_num,outcdno)  tmp
where rank_desc<=3;
EOF
tmp=$?
NOWTIME=$(date +%Y-%m-%d/%H:%M:%S)
if test $tmp -eq 0; then
echo $NOWTIME'--------------t_trans_2y_top3_mid创建成功'$tmp >>  logs/logs_personal_credit_report_result
else
echo $NOWTIME'--------------t_trans_2y_top3_mid创建失败'$tmp >>  logs/logs_personal_credit_report_result
fi


echo '------单卡交易信息-------------------------'
sudo -u hdfs hive <<EOF
drop table creditck.t_top3_card_trans;
create table creditck.t_top3_card_trans
as
select  
a.mobile_num,
a.outcdno,--卡号
(case when b.out_bank_name is null then c.out_bank_name  else  b.out_bank_name  end ) as out_bank_name,--卡银行
 round(b.zz_amt,2)  as  zz_amt,--转账交易金额
 b.zz_cnt,--转账交易次数
 round(b.yjzz_amt,2) as yjzz_amt,--月均转账金额
 c.hk_month_num,--还款分布月份数
 round(c.yjhk_amt,2) as yjhk_amt,--月均还款金额
 round(c.bjhk_amt,2) as bjhk_amt, --笔均还款金额
 '${TODAY_2Y}'  as startdate,--开始日期
 from_unixtime(unix_timestamp(),'yyyyMMdd')  as  enddate, --截止日期
 '24' as month_num --报告周期（月）
from 
creditck.t_trans_2y_top3_mid a
left join (
select  
mobile_num,
outcdno,
out_bank_name,
sum(total_am)/100 as  zz_amt,
count(1) as zz_cnt,
sum(total_am)/(100*count(distinct substr(data_date,0,7))) as yjzz_amt 
from  edm.f_fact_trans_success_new_details
where tcat_lv3_name='转账汇款' and  length(mobile_num)=11 and ymd >='${TODAY_2Y}'  
group by mobile_num,outcdno,out_bank_name ) b  on  a.mobile_num=b.mobile_num  and  a.outcdno=b.outcdno
left join (
select  
mobile_num,
outcdno,
out_bank_name,
count(distinct substr(data_date,0,7)) as hk_month_num,
sum(total_am)/(100*count(distinct substr(data_date,0,7))) as yjhk_amt,
sum(total_am)/(100*count(1)) as bjhk_amt 
from  
edm.f_fact_trans_success_new_details
where tcat_lv3_name='还款' and length(mobile_num)=11 and ymd >='${TODAY_2Y}'  
group by  mobile_num,outcdno,out_bank_name) c  on   a.mobile_num=c.mobile_num  and  a.outcdno=c.outcdno;
EOF
tmp=$?
NOWTIME=$(date +%Y-%m-%d/%H:%M:%S)
if test $tmp -eq 0; then
echo $NOWTIME'--------------t_top3_card_trans创建成功'$tmp >>  logs/logs_personal_credit_report_result
else
echo $NOWTIME'--------------t_top3_card_trans创建失败'$tmp >>  logs/logs_personal_credit_report_result
fi


echo '------验证用户后的单卡交易信息-------------------------'
sudo -u hdfs hive <<EOF
drop table creditck.t_card_trans_info;
create table creditck.t_card_trans_info
as
select  
t1.mobile_num,
t2.outcdno,--卡号
t2.out_bank_name,--卡银行
t2.zz_amt,--转账交易金额
t2.zz_cnt,--转账交易次数
t2.yjzz_amt,--月均转账金额
t2.hk_month_num,--还款分布月份数
t2.yjhk_amt,--月均还款金额
t2.bjhk_amt, --笔均还款金额
t2.startdate,--开始日期
t2.enddate, --截止日期
t2.month_num --报告周期（月）
from  creditck.var_user  t1
left join creditck.t_top3_card_trans t2   on  t1.mobile_num=t2.mobile_num;
EOF
tmp=$?
NOWTIME=$(date +%Y-%m-%d/%H:%M:%S)
if test $tmp -eq 0; then
echo $NOWTIME'--------------t_card_trans_info创建成功'$tmp >>  logs/logs_personal_credit_report_result
else
echo $NOWTIME'--------------t_card_trans_info创建失败'$tmp >>  logs/logs_personal_credit_report_result
fi


echo '------------------------个人信用报告需求整合----------------------------------'
sudo -u hdfs hive <<EOF
drop table  creditck.t_personal_credit_report_info;
create table   creditck.t_personal_credit_report_info
as
select a.mobile_num,
a.reg_date,--注册时间
a.zw_time,--在网时长
c.credit_count,--信用卡张数
c.debit_count,--借记卡张数
round(b.credit_bjhk,2) as credit_bjhk,--笔均还款
b.credit_hkmonth,--还款分布月份数
round(b.credit_yjhk,2) as credit_yjhk,--月均还款
b.debit_in,--转账转入卡张数
b.debit_out,--转账转出卡张数
round(b.zzamount,2) as  zzamount,--转账总金额
b.zznum,--转账笔数
b.debit_zzmonth,--转账分布月份数
round(b.debit_yjzz,2) as  debit_yjzz,--月均转账金额
d.debit_cxmonth,--借记卡查询月份数
e.credit_cxmonth,--信用卡查询月份数
nvl(f.zdfq_count,'0') as zdfq_flag,--是否有账单分期(1有0无)
g.xd_count,--小贷总笔数
round(g.xd_sum,2) as xd_sum,--小贷总金额
round(g.xd_ave,2) as xd_ave,--小贷笔均金额
g.xd_yq_cnt,--逾期总次数
round(g.xd_nosettle_sum,2) as xd_nosettle_sum,--逾期总金额
g.xd_out_day_sum,--逾期总天数
h.addr_top1,--最常出现位置TOP1
h.jy_cnt_top1,--TOP1出现次数
h.addr_top2,--最常出现位置TOP2
h.jy_cnt_top2,--TOP2出现次数
h.addr_top3,--最常出现位置TOP3
h.jy_cnt_top3, --TOP3出现次数
round(i.total_am,2)  as total_am,--总金额
i.cnt,--总笔数
round(i.bj_am,2) as  bj_am,----笔均金额
round(i.ele_total_am,2) as ele_total_am,--电费(金额)
i.ele_cnt,--电费(笔数)
round(i.ele_bj_am,2) as ele_bj_am,--电费(笔均金额)
round(i.w_total_am,2) as  w_total_am,--水费(金额)
i.w_cnt,--水费(笔数)
round(i.w_bj_am,2) as  w_bj_am,--水费(笔均金额)
round(i.gas_total_am,2) as  gas_total_am,--燃气费(金额)
i.gas_cnt,--燃气费(笔数)
round(i.gas_bj_am,2) as  gas_bj_am,--燃气费(笔均金额)
round(i.comm_total_am,2) as comm_total_am,--通讯费(金额)
i.comm_cnt,----通讯费(笔数)
round(i.comm_bj_am,2) as comm_bj_am,----通讯费(笔均金额)
round(i.tv_total_am,2) as tv_total_am,--有线电视费(金额)
i.tv_cnt,--有线电视费(笔数)
round(i.tv_bj_am,2) as tv_bj_am, --有线电视费(笔均金额)
'${TODAY_2Y}'  as startdate,--开始日期
from_unixtime(unix_timestamp(),'yyyyMMdd')  as  enddate, --截止日期
'24' as month_num --报告周期（月）
from creditck.var_user a
left join creditck.var_trans_hk_zz b on a.mobile_num=b.mobile_num
left join creditck.var_credit_debit_count c on a.mobile_num=c.mobile_num
left join creditck.var_debit_cxmonth d on a.mobile_num=d.mobile_num
left join creditck.var_credit_balance_cxmonth e on a.mobile_num=e.mobile_num
left join (select mobile as mobile_num,'1' as zdfq_count from ods.rep_ccd_bill where trade_time>='${DATE_6m}' group by mobile) f on a.mobile_num=f.mobile_num
left join creditck.var_wll_xd_loan g on a.mobile_num=g.mobile
left join (
select mobile_num,
min(case when cntid=1 then address end) as addr_top1,
min(case when cntid=1 then jy_cnt end) as jy_cnt_top1,
min(case when cntid=2 then address end) as addr_top2,
min(case when cntid=2 then jy_cnt end) as jy_cnt_top2,
min(case when cntid=3 then address end) as addr_top3,
min(case when cntid=3 then jy_cnt end) as jy_cnt_top3
from
(select t.mobile_num,
case when t1.address rlike '[路]' then split(regexp_replace(t1.address,'[路]','路-'),'[-]')[0]
when t1.address rlike '[街]' then split(regexp_replace(t1.address,'[街]','街-'),'[-]')[0]
when t1.address rlike '[-\\d]+' and t1.address not rlike '[路街]' then split(t1.address,'[-\\d]+')[0]
else t1.address end as address, --地理位置
count(*) as jy_cnt,
row_number() over(partition by t.mobile_num order by count(*) desc) as cntid
from edm.f_fact_trans_success_new t
left join ods.zy_store_common t1 on t.s_no=t1.s_no
where t.ymd>='${TODAY_2Y}'  and  t1.address <>'1'
group by t.mobile_num,
(case when t1.address rlike '[路]' then split(regexp_replace(t1.address,'[路]','路-'),'[-]')[0]
when t1.address rlike '[街]' then split(regexp_replace(t1.address,'[街]','街-'),'[-]')[0]
when t1.address rlike '[-\\d]+' and t1.address not rlike '[路街]' then split(t1.address,'[-\\d]+')[0]
else t1.address end)
 ) t where cntid<=3 group by mobile_num
) h on a.mobile_num=h.mobile_num
left join  creditck.t_public_payment_all  i  on  a.mobile_num=i.mobile_num;
EOF
tmp=$?
NOWTIME=$(date +%Y-%m-%d/%H:%M:%S)
if test $tmp -eq 0; then
echo $NOWTIME'--------------t_personal_credit_report_info创建成功'$tmp >>  logs/logs_personal_credit_report_result
else
echo $NOWTIME'--------------t_personal_credit_report_info创建失败'$tmp >>  logs/logs_personal_credit_report_result
fi


echo '----------------手机操作系统、车牌号、车辆类型--------------------------'
sudo -u hdfs hive <<EOF
drop table creditck.t_os_car_info;
create table creditck.t_os_car_info
as
select 
t1.mobile_num, --手机号
t2.os,--操作系统
t3.plate_no, --车牌号
t3.voiture_type --车辆类型
from  creditck.var_user t1
left join 
(select distinct mobile,os  from   mobileprobe.lklcli_probelog ) t2   on  t1.mobile_num=t2.mobile
left join 
(
select 
distinct b.mobile_num,
a.plate_no,
a.voiture_type
from
(select  user_id,plate_no,voiture_type  from 
(
select 
user_id,
plate_no,
voiture_type,
row_number() over(partition by user_id order by create_time desc) row_num
from ods.MQ_JF_CAR_HIS ) t  where row_num=1) a,
ods.u_user  b
where a.user_id=b.id )  t3   on t1.mobile_num=t3.mobile_num;
EOF
tmp=$?
NOWTIME=$(date +%Y-%m-%d/%H:%M:%S)
if test $tmp -eq 0; then
echo $NOWTIME'--------------t_os_car_info创建成功'$tmp >>  logs/logs_personal_credit_report_result
else
echo $NOWTIME'--------------t_os_car_info创建失败'$tmp >>  logs/logs_personal_credit_report_result
fi


#推送至征信
echo '----------------------推送单卡交易信息表-------------------------------'
sudo -u hdfs  sqoop export --connect jdbc:oracle:thin:@10.1.2.187:1521/crdshare  --username EM  --password em_1234  --table T_CARD_TRANS_INFO --export-dir /user/hive/warehouse/creditck.db/t_card_trans_info  --fields-terminated-by '\001'  --input-null-string '\\N' --input-null-non-string '\\N'  --update-key MOBILE_NUM,OUTCDNO  --update-mode allowinsert
tmp=$?
NOWTIME=$(date +%Y-%m-%d/%H:%M:%S)
if test $tmp -eq 0; then
echo $NOWTIME'-------------推送单卡交易信息表到征信数据库成功'$tmp >>  logs/logs_personal_credit_report_result
else
echo $NOWTIME'-------------推送单卡交易信息表到征信数据库失败'$tmp >>  logs/logs_personal_credit_report_result
fi

echo '----------------------推送基本信息表-------------------------------'
sudo -u hdfs  sqoop export --connect jdbc:oracle:thin:@10.1.2.187:1521/crdshare  --username EM  --password em_1234  --table T_PERSONAL_CREDIT_REPORT_INFO --export-dir /user/hive/warehouse/creditck.db/t_personal_credit_report_info  --fields-terminated-by '\001'  --input-null-string '\\N' --input-null-non-string '\\N'  --update-key MOBILE_NUM  --update-mode allowinsert 
tmp=$?
NOWTIME=$(date +%Y-%m-%d/%H:%M:%S)
if test $tmp -eq 0; then
echo $NOWTIME'-------------推送基本信息表到征信数据库成功'$tmp >>  logs/logs_personal_credit_report_result
else
echo $NOWTIME'-------------推送基本信息表到征信数据库失败'$tmp >>  logs/logs_personal_credit_report_result
fi

echo '----------------------推送手机操作系统、车牌号、车辆类型-------------------------------'
sudo -u hdfs  sqoop export --connect jdbc:oracle:thin:@10.1.2.187:1521/crdshare  --username EM  --password em_1234  --table T_OS_CAR_INFO --export-dir /user/hive/warehouse/creditck.db/t_os_car_info  --fields-terminated-by '\001'  --input-null-string '\\N' --input-null-non-string '\\N'  --update-key MOBILE_NUM,OS  --update-mode allowinsert 
tmp=$?
NOWTIME=$(date +%Y-%m-%d/%H:%M:%S)
if test $tmp -eq 0; then
echo $NOWTIME'-------------推送手机操作系统、车牌号、车辆类型到征信数据库成功'$tmp >>  logs/logs_personal_credit_report_result
else
echo $NOWTIME'-------------推送手机操作系统、车牌号、车辆类型到征信数据库失败'$tmp >>  logs/logs_personal_credit_report_result
fi

















