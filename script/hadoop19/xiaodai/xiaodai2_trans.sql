#!/bin/bash
YEAR_0Y=$(date +%Y)
TODAY_1D=$(date --date='1 day ago' +%Y%m%d)
TODAY_2D=$(date --date='2 day ago' +%Y%m%d)
TODAY_0D=$(date +%Y%m%d)
DATE_2Y_2D=$(date --date='2 year ago 2 day ago' +%Y%m%d)

echo $(date +%Y-%m-%d/%H:%M:%S) "-------------拉卡拉交易、小贷数据合集-----------开始----"

#cdt_result_source中ymd最大值
TODAY_1D_LAST=`hadoop fs -ls /user/hive/warehouse/creditck.db/cdt_result_source |tail -n 1 |awk -F'=' '{print $2}'`

sudo -u hdfs hive <<EOF
set hive.auto.convert.join=false;
set mapreduce.reduce.shuffle.input.buffer.percent=0.1;
set mapreduce.reduce.shuffle.parallelcopies=9;

-----用户表
drop table creditck.lklvar_v1_1x;
create table creditck.lklvar_v1_1x as
select distinct t1.mobile_num mobile, t3.idno, t2.create_time, 
datediff(to_date(from_unixtime(unix_timestamp())),t2.create_time) reg_days,
case when t3.idno is null then null
when substr(t3.idno,17,1)%2=1 then 'Male' else 'Female' end sex,
${YEAR_0Y}-substr(t3.idno,7,4) age
 from 
(select distinct mobile_num from creditck.cdt_result_source 
where ymd='${TODAY_2D}') t1
left outer join
(
select u_mobile, to_date(min(create_tm)) create_time 
from edw.pt_user
where create_tm is not null
group by u_mobile
) t2
on t1.mobile_num=t2.u_mobile
left outer join 
(select mobile,identity_card idno from hdm.u_user_identity_info 
where identity_card is not null) t3
on t1.mobile_num=t3.mobile
;

-----交易变量
drop table creditck.lklvar_v1_2x;
create table creditck.lklvar_v1_2x
as
select t2.mobile_num mobile,
sum(t2.total_am)/100 lkl_total_am, count(1) lkl_total_cnt,
datediff(from_unixtime(unix_timestamp(),'yyyy-MM-dd'),to_date(max(t2.data_date))) lkl_lastday,
count(distinct substr(t2.ymd,1,6)) lkl_trade_mon,
count(distinct t3.tcat_lv3_name) lkl_trade_type,

count(distinct case when t3.tcat_lv2_name='消费类' then substr(t2.ymd,1,6) end) as lkl_cost_month,
sum(case when t3.tcat_lv2_name='消费类' then t2.total_am/100 end)/count(distinct case when t3.tcat_lv2_name='消费类' then substr(t2.ymd,1,6) end) lkl_cost_permon,

sum(case when trim(t4.bank_short_name) in ('中国工商银行','中国工商银行加拿大分行','中国工商银行米兰分行','工商银行','中国建设银行','中国建设银行亚洲股份有限公司',
'中国建设银行澳门股份有限公司','建设银行','中国农业银行贷记卡','农业银行','中国银行','中国银行澳门分行','中国银行（香港）') then t2.total_am end)/sum(t2.total_am) lkl_big4_am_ratio,

count(case when trim(t4.bank_short_name) in ('中国工商银行','中国工商银行加拿大分行','中国工商银行米兰分行','工商银行','中国建设银行','中国建设银行亚洲股份有限公司',
'中国建设银行澳门股份有限公司','建设银行','中国农业银行贷记卡','农业银行','中国银行','中国银行澳门分行','中国银行（香港）') then 1 end)/count(1) lkl_big4_cnt_ratio,

sum(case when trim(t4.bank_short_name) in ('招商银行','浦东发展银行','中信嘉华银行','中信嘉华银行有限公司','中信银行','中信银行信用卡中心',
'中信银行股份有限公司信用卡中心','平安银行','民生银行','广发银行','光大银行','中国光大银行') then t2.total_am end)/sum(t2.total_am) lkl_mid_am_ratio,

count(case when trim(t4.bank_short_name) in ('招商银行','浦东发展银行','中信嘉华银行','中信嘉华银行有限公司','中信银行','中信银行信用卡中心',
'中信银行股份有限公司信用卡中心','平安银行','民生银行','广发银行','光大银行','中国光大银行') then 1 end)/count(1) lkl_mid_cnt_ratio,
  
count(case when t3.tcat_lv4_name='信用卡还款' then 1 end) lkl_repay_cnt,
count(distinct case when t3.tcat_lv4_name='信用卡还款' then substr(t2.ymd,1,6) end) lkl_repay_mon,
count(case when t3.tcat_lv4_name='信用卡还款' then 1 end)/count(distinct case when t3.tcat_lv4_name='信用卡还款' then substr(t2.ymd,1,6) end) lkl_repay_cnt_permon,
sum(case when t3.tcat_lv4_name='信用卡还款' then t2.total_am/100 else 0 end) as lkl_repay_am,
sum(case when t3.tcat_lv4_name='信用卡还款' then t2.total_am/100 else 0 end)/count(distinct case when t3.tcat_lv4_name='信用卡还款' then substr(t2.ymd,1,6) end) lkl_repay_am_permon,
sum(case when t3.tcat_lv4_name='信用卡还款' then t2.total_am/100 else 0 end)/count(case when t3.tcat_lv4_name='信用卡还款' then 1 end) as lkl_repay_am_percnt,

sum(case when (t3.tcat_lv3_name='话费充值' or  (t3.tcat_lv5_name like'%话费' and t3.tcat_lv4_name='公缴费-通讯缴费')) then t2.total_am/100 else 0 end)/count(distinct case when (t3.tcat_lv3_name='话费充值' or  (t3.tcat_lv5_name like'%话费' and t3.tcat_lv4_name='公缴费-通讯缴费')) then substr(t2.ymd,1,6) end) as lkl_phonechare_permon,

count(case when t3.tcat_lv3_name='转账汇款' then 1 end) lkl_trans_cnt,
count(distinct case when t3.tcat_lv3_name='转账汇款' then substr(t2.ymd,1,6) end) lkl_trans_mon,
count(case when t3.tcat_lv3_name='转账汇款' then 1 end)/count(distinct case when t3.tcat_lv3_name='转账汇款' then substr(t2.ymd,1,6) end) lkl_trans_cnt_permon,
sum(case when t3.tcat_lv3_name='转账汇款' then t2.total_am/100 else 0 end) as lkl_trans_am,
sum(case when t3.tcat_lv3_name='转账汇款' then t2.total_am/100 else 0 end)/count(distinct case when t3.tcat_lv3_name='转账汇款' then substr(t2.ymd,1,6) end) lkl_trans_am_permon,
sum(case when t3.tcat_lv3_name='转账汇款' then t2.total_am/100 else 0 end)/count(case when t3.tcat_lv3_name='转账汇款' then 1 end) as lkl_trans_am_percnt,

case when count(case when t3.tcat_lv3_name='公缴类' then 1 end) < 1 then 0 else 1 end lkl_pub_or_not

from 
(select * from edm.f_fact_trans_success_new
-----YMD每天更新，取2年的数据
where ymd>='${DATE_2Y_2D}' and ymd<'${TODAY_2D}') t2
-----
join edm.d_cfg_agent t3
on t2.merno=t3.agent_no
join edm.d_card_info t4
on t2.outcdno_bin=t4.card_bin
group by t2.mobile_num;;


-----信用卡每月还款金额稳定性
drop table creditck.lklvar_v1_3x;
create table creditck.lklvar_v1_3x
as
select mobile,stddev(hk_am)/avg(hk_am) as lkl_repay_cv
from(
select t2.mobile_num mobile,substr(t2.ymd,1,6) as jy_ym,sum(t2.total_am/100) as hk_am
from edm.f_fact_trans_success_new t2
join edm.d_cfg_agent t3
on t2.merno=t3.agent_no
-----YMD每天更新，取2年的数据
and t2.ymd>='${DATE_2Y_2D}' and t2.ymd<'${TODAY_2D}'
and t3.tcat_lv4_name='信用卡还款'
group by t2.mobile_num,substr(t2.ymd,1,6))t
group by mobile;

-----小贷用户
drop table creditck.xd_user_v1;
create table creditck.xd_user_v1 as
select * from
(select t1.mobile, t2.insert_time, t1.email,
t1.education, t1.income_range
from hds.xd_c_apply_user t1
join hds.xd_c_loan_apply t2
on t1.id = t2.id 
left semi join 
(select * from hds.xd_c_product 
where name like '%替你还%' or name like '%易分期%') t3
on t2.business_no=t3.business_type) a1
union all
select * from
(select t1.mobilephone mobile, t2.applytime insert_time, t1.email, 
case when trim(t1.educationstatus) in ('6','7','8') then 5 
else trim(t1.educationstatus) end education,
case when cast (trim(t1.workmonthincome) as int) <=3000 then 1 
when cast (trim(t1.workmonthincome) as int)<=6000 then 2 
when cast (trim(t1.workmonthincome) as int)<=10000 then 3 
when cast (trim(t1.workmonthincome) as int)<=15000 then 4 
when cast (trim(t1.workmonthincome) as int)<=20000 then 5 
else 6 end income_range  
from hds.cts_applypersoninfo t1
join hds.cts_loanapply t2
on t1.loanid=t2.loanid) a2;

-----小贷变量
drop table creditck.xdvar_v1;
create table creditck.xdvar_v1 as
select t1.mobile,                    
datediff(t1.apply_1st, to_date(t4.create_time)) xd_reg_apply1st,
t1.xd_maxedu, t1.xd_minedu, t1.xd_maxincome, 
t1.xd_minincome, t1.xd_edu_num, t1.xd_income_num,
if(isnotnull(t6.mobile),1,0) xd_email_qq, 
if(isnotnull(t7.mobile),1,0) xd_email_163
from 
(select mobile, 
to_date(min(insert_time)) apply_1st,
max(cast (trim(education) as int)) xd_maxedu, 
min(cast (trim(education) as int)) xd_minedu, 
max(cast (trim(income_range) as int)) xd_maxincome, 
min(cast (trim(income_range) as int)) xd_minincome,
count(distinct trim(education)) xd_edu_num,
count(distinct trim(income_range)) xd_income_num
from creditck.xd_user_v1
group by mobile
) t1
join creditck.lklvar_v1_1x t4 
on t1.mobile=t4.mobile 

left outer join (select distinct mobile from creditck.xd_user_v1
where lower(email) like '%qq.com') t6        
on t1.mobile=t6.mobile
  
left outer join (select distinct mobile from creditck.xd_user_v1
where lower(email) like '%163.com' or lower(email) like '%126.com') t7        
on t1.mobile=t7.mobile;


-----拉卡拉交易、小贷数据合集
drop table creditck.allvar_v1;
create table creditck.allvar_v1
as
select distinct t0.mobile, t0.sex, t0.age, 
nvl(t0.reg_days,0) lkl_reg_days,
nvl(t1.lkl_total_am,0) lkl_total_am,
nvl(t1.lkl_total_cnt,0) lkl_total_cnt,
nvl(t1.lkl_lastday,0) lkl_lastday,
nvl(t1.lkl_trade_mon,0) lkl_trade_mon,
nvl(t1.lkl_trade_type,0) lkl_trade_type,
nvl(t1.lkl_cost_month,0) lkl_cost_month,
nvl(t1.lkl_cost_permon,0) lkl_cost_permon,
nvl(t1.lkl_big4_am_ratio,0) lkl_big4_am_ratio,
nvl(t1.lkl_big4_cnt_ratio,0) lkl_big4_cnt_ratio,
nvl(t1.lkl_mid_am_ratio,0) lkl_mid_am_ratio,
nvl(t1.lkl_mid_cnt_ratio,0) lkl_mid_cnt_ratio,
nvl(t1.lkl_repay_mon,0) lkl_repay_mon,
nvl(t1.lkl_repay_am_permon,0) lkl_repay_am_permon,
nvl(t1.lkl_repay_am_percnt,0) lkl_repay_am_percnt,
nvl(t1.lkl_phonechare_permon,0) lkl_phonechare_permon,
nvl(t1.lkl_trans_mon,0) lkl_trans_mon,
nvl(t1.lkl_trans_am_permon,0) lkl_trans_am_permon,
nvl(t1.lkl_trans_am_percnt,0) lkl_trans_am_percnt,
nvl(t1.lkl_pub_or_not,0) lkl_pub_or_not, 
nvl(t2.lkl_repay_cv,99) lkl_repay_cv, 
nvl(t3.debit_count,0) lkl_debit_count, 
nvl(t3.credit_count,0) lkl_credit_count,
nvl(device_ss,0) lkl_device_ss, 
nvl(t3.device_skb,0) lkl_device_skb,
nvl(t3.zw_time,0) lkl_zw_time, 
nvl(t3.person_living,99) lkl_person_living, 
nvl(t3.person_hukou,99) lkl_person_hukou,
nvl(t4.price,2000) lkl_mobile_price,
t5.xd_reg_apply1st,
1 as xd_return_suc, 99.01 as xd_cv,
t5.xd_maxedu, t5.xd_minedu,
t5.xd_maxincome, t5.xd_minincome, 
t5.xd_edu_num, t5.xd_income_num,
t5.xd_email_qq, t5.xd_email_163
from creditck.lklvar_v1_1x t0
join creditck.lklvar_v1_2x t1
on t0.mobile=t1.mobile
left outer join creditck.lklvar_v1_3x t2
on t1.mobile=t2.mobile
join creditck.cdt_result_source t3
on t1.mobile=t3.mobile_num 
and t3.ymd='${TODAY_1D_LAST}'
left outer join 
(select a1.mobile_num, a3.price
from edw.pt_user a1
join hds.ydjrc_eusermapping a2
on a1.id=a2.coreuserid 
join wm.mobile_devicemodel_price a3
on a2.userid=a3.userid) t4
on t1.mobile=t4.mobile_num
left outer join
creditck.xdvar_v1 t5
on t1.mobile=t5.mobile;
EOF
tmp=$?
NOWTIME=$(date +%Y-%m-%d/%H:%M:%S)
if test $tmp -eq 0; then
   echo $NOWTIME'--------------拉卡拉交易、小贷数据合集 creditck.allvar_v1-------------'$tmp
   break
else
   echo $NOWTIME'--------------拉卡拉交易、小贷数据合集 creditck.allvar_v1-------------'$tmp
fi


#推送 拉卡拉交易、小贷数据合集
JDBC=jdbc:oracle:thin:@10.1.2.187:1521
SID=crdnewdb
USERNAME=EM
PASSWORD=em_1234

echo '-------------------------------------------拉卡拉交易、小贷数据合集-------------------------------------------'

echo "sudo -u hdfs sqoop export --connect $JDBC/$SID --username $USERNAME --password $PASSWORD --table CDT_LKL_XD_TRANS --export-dir /user/hive/warehouse/creditck.db/allvar_v1 --fields-terminated-by '\001' --input-null-string '\\N' --input-null-non-string '\\N' --update-key MOBILE --update-mode allowinsert -m 10"
echo; echo; echo
sudo -u hdfs sqoop export --connect $JDBC/$SID --username $USERNAME --password $PASSWORD --table CDT_LKL_XD_TRANS --export-dir /user/hive/warehouse/creditck.db/allvar_v1 --fields-terminated-by '\001' --input-null-string '\\N' --input-null-non-string '\\N' --update-key MOBILE --update-mode allowinsert -m 10
tmp=$?
NOWTIME=$(date +%Y-%m-%d/%H:%M:%S)
if test $tmp -eq 0 ; then
    echo $NOWTIME'------------CDT_LKL_XD_TRANS 拉卡拉小贷交易信息推送表--------'$tmp 
else
    echo $NOWTIME'------------CDT_LKL_XD_TRANS 拉卡拉小贷交易信息推送表--------'$tmp 
    echo $NOWTIME'------------拉卡拉小贷交易信息推送表失败'$tmp >> /home/hdfs/xiaodai/logs/xiaodai2_cron_trans_critical
    exit -1
fi