#!/bin/bash
TODAY_1D=$(date --date='1 day ago' +%Y%m%d)
TODAY_2D=$(date --date='2 day ago' +%Y%m%d)
TODAY_0D=$(date +%Y%m%d)
TODAY_YM=$(date +%Y%m)

echo "'${TODAY_1D}'---${TODAY_0D}"

sudo -u hdfs hive -e "
set hive.auto.convert.join=false;
set mapreduce.reduce.shuffle.input.buffer.percent=0.1;
set mapreduce.reduce.shuffle.parallelcopies=9;
set hive.map.aggr=true;
set hive.groupby.skewindata=true;
set hive.exec.reducers.bytes.per.reducer=1000000000;
set hive.optimize.skewjoin=true;
set hive.skewjoin.key=2000000;

--此次提取的数据是以申请日为基准的最后一笔交易近两年数据
--以下提取用户申请时间以前的最后一笔交易时间
drop table zhengxin.gd_apply_ymd_1031;
create table zhengxin.gd_apply_ymd_1031 as
select mobile_num as mobile,max(ymd) as ymd_max
from edm.f_fact_trans_success_new t1 
group by t1.mobile_num;

set hive.optimize.skewjoin=false;
drop table zhengxin.gd_trans_1031;
create table zhengxin.gd_trans_1031
as
select  t4.*,t3.ymd_max
from
zhengxin.gd_apply_ymd_1031 t3,
edm.f_fact_trans_success_new t4
where t3.mobile=t4.mobile_num
and (cast(substr(t3.ymd_max,1,6) as int)-cast(substr(t4.ymd,1,6) as int))%88+1 <= 24 
and t3.ymd_max>=t4.ymd
and t4.ymd>='20120101';

set hive.optimize.skewjoin=true;
drop table zhengxin.gd_trans_all_1031_1;
create table  zhengxin.gd_trans_all_1031_1
as 
select t1.*,
t2.tcat_lv3_name,tcat_lv4_name,tcat_lv5_name
from zhengxin.gd_trans_1031 t1
left join
edm.d_cfg_agent t2
on (case when t1.merno is null then concat('hive',rand()) else t1.merno end) =t2.agent_no;

drop table zhengxin.gd_trans_all_1031_2;
create table  zhengxin.gd_trans_all_1031_2
as 
select t1.*,t3.card_type as card_type_out
from zhengxin.gd_trans_all_1031_1 t1
left join 
edm.d_card_info t3
on (case when t1.outcdno_bin is null then concat('hive',rand()) else t1.outcdno_bin end)=t3.card_bin
and length(trim(t1.outcdno))=t3.card_len;

drop table zhengxin.gd_trans_all_1031;
create table zhengxin.gd_trans_all_1031
as 
select t1.*,t4.card_type as card_type_in
from zhengxin.gd_trans_all_1031_2 t1
left join 
edm.d_card_info t4
on (case when t1.incdno_bin is null then concat('hive',rand()) else t1.incdno_bin end)=t4.card_bin
and length(trim(t1.incdno))=t4.card_len;;

set hive.groupby.skewindata=false;
drop table zhengxin.gd_zongti_1031;
create table zhengxin.gd_zongti_1031 AS
select mobile_num,count(DISTINCT SUBSTR(ymd,1,6)) AS jy_mm,
COUNT(DISTINCT tcat_lv3_name) AS jy_type_num,
min(${TODAY_YM}-cast(substr(ymd_max,1,6) AS INT))%88 AS ymd_min_len,------'20161031'改成更新当天的日期
max(${TODAY_YM}-cast(substr(ymd_max,1,6) AS INT))%88 AS ymd_max_len,
COUNT(1)  AS all_num,
SUM(total_am/100) AS all_am_sum,
MAX(total_am/100) AS all_am_max,
COUNT(CASE WHEN tcat_lv4_name='信用卡还款' THEN 1 END) AS hk_num,
SUM(CASE WHEN tcat_lv4_name='信用卡还款' THEN total_am/100 END) AS hk_am_sum,
SUM(CASE WHEN tcat_lv4_name='信用卡还款' THEN total_am/100 END)/SUM(total_am/100) as hk_am_zb,
COUNT(CASE WHEN tcat_lv4_name='信用卡还款' THEN 1 END)/COUNT(1) as hk_num_zb,
MAX(CASE WHEN tcat_lv4_name='信用卡还款' THEN total_am/100 END) AS hk_am_max,
SUM(CASE WHEN tcat_lv4_name='信用卡还款' THEN total_am/100 END)/COUNT(CASE WHEN tcat_lv4_name='信用卡还款' THEN 1 END) as hk_bjje,
COUNT(CASE WHEN tcat_lv3_name='转账汇款' THEN 1 END) AS zz_num,
SUM(CASE WHEN tcat_lv3_name='转账汇款' THEN total_am/100 END) AS zz_am_sum,
MAX(CASE WHEN tcat_lv3_name='转账汇款' THEN total_am/100 END) AS zz_am_max,
SUM(CASE WHEN tcat_lv3_name='转账汇款' THEN total_am/100 END)/COUNT(CASE WHEN tcat_lv3_name='转账汇款' THEN 1 END) as zz_bjje,
SUM(CASE WHEN tcat_lv3_name='转账汇款' THEN total_am/100 END)/SUM(total_am/100) as zz_am_zb,
COUNT(CASE WHEN tcat_lv3_name='转账汇款' THEN 1 END)/COUNT(1) as zz_num_zb,
COUNT(CASE WHEN tcat_lv3_name='查询' THEN 1 END) AS cx_num,
COUNT(CASE WHEN tcat_lv3_name='查询' THEN 1 END)/COUNT(1) as cx_num_zb,
COUNT(CASE WHEN  tcat_lv4_name IN('公缴费-通讯缴费','直充') THEN 1 END) AS hf_num,
SUM(CASE WHEN  tcat_lv4_name IN('公缴费-通讯缴费','直充') THEN total_am/100 END) AS hf_am_sum,
MAX(CASE WHEN  tcat_lv4_name IN('公缴费-通讯缴费','直充') THEN total_am/100 END) AS hf_am_max,
SUM(CASE WHEN tcat_lv4_name IN('公缴费-通讯缴费','直充') THEN total_am/100 END)/SUM(total_am/100) as hf_am_zb,
COUNT(CASE WHEN tcat_lv4_name IN('公缴费-通讯缴费','直充') THEN 1 END)/COUNT(1) as hf_num_zb,
COUNT(CASE WHEN  tcat_lv4_name <>'公缴费-通讯缴费' AND tcat_lv3_name='公缴类' THEN 1 END) AS gj_num,
SUM(CASE WHEN  tcat_lv4_name <>'公缴费-通讯缴费' AND tcat_lv3_name='公缴类' THEN total_am/100 END) AS gj_am_sum,
MAX(CASE WHEN  tcat_lv4_name <>'公缴费-通讯缴费' AND tcat_lv3_name='公缴类' THEN total_am/100 END) AS gj_am_max,
SUM(CASE WHEN tcat_lv4_name <>'公缴费-通讯缴费' AND tcat_lv3_name='公缴类' THEN total_am/100 END)/SUM(total_am/100) as gj_am_zb,
COUNT(CASE WHEN tcat_lv4_name <>'公缴费-通讯缴费' AND tcat_lv3_name='公缴类' THEN 1 END)/COUNT(1) as gj_num_zb
FROM zhengxin.gd_trans_all_1031 t
GROUP BY mobile_num;

---由交易表制作月份变量以及单日最大转账金额

drop table zhengxin.gd_cl_var_yf_1031;
create table zhengxin.gd_cl_var_yf_1031 as------------------------------------------------------得到各类交易的交易月份数以及每日最大转账笔数，只有18269条
select mobile_num,count(distinct hk_ymd) as hk_month,count(distinct zz_ymd) as zz_month,count(distinct hf_ymd) as hf_month,
count(distinct gj_ymd) as gj_month,count(distinct cx_ymd) as cx_month
from
(
select mobile_num,
case when tcat_lv4_name='信用卡还款' then substr(ymd,1,6)  else null end as hk_ymd,
case when tcat_lv3_name='转账汇款' then substr(ymd,1,6) else null end as zz_ymd,
case when tcat_lv4_name IN('公缴费-通讯缴费','直充') then substr(ymd,1,6) else null end as hf_ymd,
case when tcat_lv4_name <>'公缴费-通讯缴费' AND tcat_lv3_name='公缴类' then substr(ymd,1,6) else null end as gj_ymd,
case when tcat_lv3_name='查询' then substr(ymd,1,6) else null end as cx_ymd
from zhengxin.gd_trans_all_1031
) t
group by mobile_num;

---由交易表产生的两个表制作月均指标
DROP TABLE zhengxin.gd_zongti_yj_1031;
create table zhengxin.gd_zongti_yj_1031 as------------------------------------------增加各个业务类型根据当前业务交易月份计算的月均指标
select t1.mobile_num,
t1.hk_num/t2.hk_month as hk_yjcs,t2.hk_month,
t1.hk_am_sum/t2.hk_month as hk_yjje,
t1.zz_num/t2.zz_month as zz_yjcs,
t1.zz_am_sum/t2.zz_month as zz_yjje,
t1.hf_num/t2.hf_month as hf_yjcs,
t1.hf_am_sum/t2.hf_month as hf_yjje,
t1.gj_num/t2.gj_month as gj_yjcs,
t1.gj_am_sum/t2.gj_month as gj_yjje
from
zhengxin.gd_zongti_1031 t1
left join
zhengxin.gd_cl_var_yf_1031 t2
on t1.mobile_num=t2.mobile_num
;

---由交易表制作稳定性变量
DROP TABLE zhengxin.kl_cl_wendingxing_1031;
CREATE TABLE zhengxin.kl_cl_wendingxing_1031 AS
SELECT mobile_num,STDDEV(ri_weighted)/avg(ri_weighted) rqwd_wt,stddev(ri_pure)/avg(ri_pure) rqwd_pure,
STDDEV(je_avg)/avg(je_avg) jewd_avg,STDDEV(je_sum)/avg(je_sum) jewd_sum,STDDEV(hk_am_sum)/avg(hk_am_sum) jewd_hk FROM
(
  select mobile_num,substr(ymd,1,6) yue,avg(cast(substr(ymd,7,2) as int)) ri_weighted,avg(distinct(CAST(substr(ymd,7,2) as int))) ri_pure,
  AVG(total_am/100) je_avg,SUM(total_am/100) je_sum,SUM(CASE WHEN tcat_lv4_name='信用卡还款' THEN total_am/100 END) AS hk_am_sum
  from zhengxin.gd_trans_all_1031
  group by mobile_num,substr(ymd,1,6)
 ) t
GROUP BY mobile_num
;

---由交易表制作最频繁交易时间段变量
drop table zhengxin.kl_cl_most_time_1031;
CREATE TABLE zhengxin.kl_cl_most_time_1031 AS
SELECT mobile_num,time_duan FROM--------------------------------------------------------------------------------------------------------------------------
(
SELECT mobile_num,time_duan,cishu,row_number() OVER(PARTITION BY mobile_num ORDER BY cishu DESC) AS cishu_id
FROM
       (SELECT mobile_num,time_duan,COUNT(time_duan) cishu  FROM
              (SELECT mobile_num,CASE WHEN (SUBSTR(data_date,12,2)>7 AND SUBSTR(data_date,12,2)<12) OR(SUBSTR(data_date,12,2)>14 AND
              SUBSTR(data_date,12,2)<18) THEN 'worktime'
              WHEN SUBSTR(data_date,12,2)<22 THEN 'leisuretime' ELSE 'night' END AS time_duan FROM zhengxin.gd_trans_all_1031) t
         GROUP BY mobile_num,time_duan
         ) t2
) t3
WHERE cishu_id=1;

---由用户pt_user_psam_rela表制作是否有手刷的表，该表总是更新到用户最新数据，不标注时间
drop table zhengxin.gd_shoushua_1031;
create table zhengxin.gd_shoushua_1031 as
select t.mobile,MAX(t.term_type) as is_shoushua from
(select t1.mobile,CASE WHEN SUBSTR(t2.psam,1,8) IN('CBC3A3BF','CBC9333F','CBC3A3B2') THEN 1 ELSE 0 END as term_type
from 
zhengxin.gd_apply_ymd_1031 t1
left join 
edw.pt_user_psam_rela t2
on t1.mobile=t2.mobile_num
) t
group by mobile
;

---计算在网时间相关变量，在网时长计算方法待定
drop table zhengxin.gd_zw_time_1031;
create table zhengxin.gd_zw_time_1031 as
select mobile_num mobile,(CAST(update_ym AS INT)-CAST(create_ym AS INT))%88+1 as zw_month from
(select t1.mobile_num,
CASE WHEN t1.trans_create<=t2.pt_user_create or t2.pt_user_create is null THEN t1.trans_create ELSE t2.pt_user_create END as create_ym,
CASE WHEN t1.trans_update>=t2.pt_user_update or t2.pt_user_update is null THEN t1.trans_update ELSE t2.pt_user_update END as update_ym
from
(select mobile_num,min(substr(ymd,1,6)) as trans_create,max(substr(ymd,1,6)) as trans_update
from zhengxin.gd_trans_all_1031 group by mobile_num) t1   
left join 
(select mobile_num,min(concat(substr(create_tm,1,4),substr(create_tm,6,2))) as pt_user_create,
max(concat(substr(update_tm,1,4),substr(update_tm,6,2))) as pt_user_update 
from edw.pt_user group by mobile_num) t2
on t1.mobile_num=t2.mobile_num) t3;


---计算credit_count、debit_count、debit_in、debit_out、credit_hklast
---credit_count
drop table zhengxin.gd_credit_count_1031;
create table zhengxin.gd_credit_count_1031 as
select mobile_num,count(distinct card_no) as credit_count
from 
(select mobile_num,outcdno as card_no from zhengxin.gd_trans_all_1031 where card_type_out='XYK'
union all
select mobile_num,incdno as card_no from zhengxin.gd_trans_all_1031 where card_type_in='XYK'
) t
group by mobile_num;


---debit_out
drop table zhengxin.gd_debit_out_1031;
create table zhengxin.gd_debit_out_1031 as
select mobile_num,count(distinct t1.card_no) as debit_out
from
(
select mobile_num,outcdno as card_no from zhengxin.gd_trans_all_1031 where card_type_out='JJK'
) t1 
group by mobile_num;

---credit_in
drop table zhengxin.gd_credit_in_1031;
create table zhengxin.gd_credit_in_1031 as
select mobile_num,count(distinct t4.card_no) as credit_in
from
(
select mobile_num,incdno as card_no from zhengxin.gd_trans_all_1031 where card_type_in='XYK'
) t4
group by mobile_num;

----credit_hklast
drop table zhengxin.gd_credit_hklast_1031;
create table zhengxin.gd_credit_hklast_1031 AS
select mobile_num,max(${TODAY_YM}-cast(substr(ymd_max,1,6) AS INT))%88 AS credit_hklast
from zhengxin.gd_trans_all_1031 where tcat_lv4_name='信用卡还款'
group by mobile_num;


----匹配已经存在的变量
drop table zhengxin.kl_user_old_var_1031;
create table zhengxin.kl_user_old_var_1031
as
select t2.mobile_num,t2.credit_class,t2.debit_class,t2.debit_kind,t2.person_living,t2.person_status
from
creditck.cdt_result_source t2
where t2.ymd='${TODAY_2D}';


---debit_count
drop table zhengxin.gd_debit_count_1031;
create table zhengxin.gd_debit_count_1031 as
select mobile_num,count(distinct card_no) as debit_count
from 
(select mobile_num,outcdno as card_no from zhengxin.gd_trans_all_1031 where card_type_out='JJK'
union all
select mobile_num,incdno as card_no from zhengxin.gd_trans_all_1031 where card_type_in='JJK'
) t
group by mobile_num;

---合并表：gd_var_1031
create table if not exists zhengxin.gd_var_1031(
mobile_num          string,
jy_mm               bigint,
jy_type_num         bigint,
ymd_min_len         int   ,
ymd_max_len         int   ,
all_num             bigint,
all_am_sum          double,
all_am_max          double,
hk_num              bigint,
hk_am_sum           double,
hk_am_zb            double,
hk_num_zb           double,
hk_am_max           double,
hk_bjje             double,
zz_num              bigint,
zz_am_sum           double,
zz_am_max           double,
zz_bjje             double,
zz_am_zb            double,
zz_num_zb           double,
cx_num              bigint,
cx_num_zb           double,
hf_num              bigint,
hf_am_sum           double,
hf_am_max           double,
hf_am_zb            double,
hf_num_zb           double,
gj_num              bigint,
gj_am_sum           double,
gj_am_max           double,
gj_am_zb            double,
gj_num_zb           double,
hk_month            bigint,
zz_month            bigint,
hf_month            bigint,
gj_month            bigint,
hk_yjcs             double,
hk_yjje             double,
zz_yjcs             double,
zz_yjje             double,
hf_yjcs             double,
hf_yjje             double,
gj_yjcs             double,
gj_yjje             double,
rqwd_wt             double,
rqwd_pure           double,
jewd_avg            double,
jewd_sum            double,
jewd_hk             double,
time_duan           string,
is_shoushua         int   ,
zw_month            int   ,
credit_count        bigint,
debit_out           bigint,
credit_in           bigint,
credit_hklast       int   ,
credit_class        string,
debit_class         string,
debit_kind          string,
person_living       string,
person_status       string,
debit_count         string
) partitioned by (
ymd string
);

insert overwrite table zhengxin.gd_var_1031 partition (ymd='${TODAY_1D}')
select 
t1.*,
t2.hk_month,t2.zz_month,t2.hf_month,t2.gj_month,
t3.hk_yjcs,t3.hk_yjje,t3.zz_yjcs,t3.zz_yjje,t3.hf_yjcs,t3.hf_yjje,t3.gj_yjcs,t3.gj_yjje,
t4.rqwd_wt,t4.rqwd_pure,t4.jewd_avg,t4.jewd_sum,t4.jewd_hk,
t5.time_duan,
t6.is_shoushua,
t7.zw_month,
t11.credit_count,
t13.debit_out,
t16.credit_in,
t17.credit_hklast,
t18.credit_class,t18.debit_class,t18.debit_kind,t18.person_living,t18.person_status,t19.debit_count
from
 zhengxin.gd_zongti_1031 t1
left join
 zhengxin.gd_cl_var_yf_1031 t2
 on t1.mobile_num=t2.mobile_num
left join
 zhengxin.gd_zongti_yj_1031 t3
 on t1.mobile_num=t3.mobile_num
left join 
 zhengxin.kl_cl_wendingxing_1031 t4
 on t1.mobile_num=t4.mobile_num
left join
 zhengxin.kl_cl_most_time_1031 t5
 on t1.mobile_num=t5.mobile_num
left join
 zhengxin.gd_shoushua_1031 t6
 on t1.mobile_num=t6.mobile
left join
 zhengxin.gd_zw_time_1031 t7
 on t1.mobile_num=t7.mobile
left join
 zhengxin.gd_credit_count_1031 t11
 on t1.mobile_num=t11.mobile_num
left join
 zhengxin.gd_debit_out_1031 t13
 on t1.mobile_num=t13.mobile_num
left join
 zhengxin.gd_credit_in_1031 t16
 on t1.mobile_num=t16.mobile_num
left join
 zhengxin.gd_credit_hklast_1031 t17
 on t1.mobile_num=t17.mobile_num
left join
  zhengxin.kl_user_old_var_1031 t18 
  on t1.mobile_num = t18.mobile_num
left join 
  zhengxin.gd_debit_count_1031 t19
  on t1.mobile_num = t19.mobile_num
where length(t1.mobile_num) <12
;
"

tmp=$?
NOWTIME=$(date +%Y-%m-%d/%H:%M:%S)
if test $tmp -eq 0; then
   echo $NOWTIME '--------------光大银行交易变量 zhengxin.gd_var_1031-------------'$tmp
   break
else
   echo $NOWTIME '--------------光大银行交易变量 zhengxin.gd_var_1031-------------'$tmp
fi

#计算时间差,周日计算，周一早上推数
TODAY_0D_F=$(date +%Y-%m-%d)
time1=$(date +%s -d"${TODAY_0D_F} 05:00:00")

time_exc=$((time1+1*24*60*60))
time_now=$(date +%s)

time_sleep=$((time_exc-time_now))

sleep ${time_sleep}s


#推送 光大银行交易变量 
JDBC=jdbc:oracle:thin:@10.1.2.187:1521
SID=crdnewdb
USERNAME=EM
PASSWORD=em_1234

echo '-------------------------------------------光大银行交易变量-------------------------------------------'
echo "sudo -u hdfs sqoop export --connect $JDBC/$SID --username $USERNAME --password $PASSWORD --table CDT_KL_GD_TRANS --export-dir /user/hive/warehouse/zhengxin.db/gd_var_1031/ymd=${TODAY_1D} --fields-terminated-by '\001' --input-null-string '\\N' --input-null-non-string '\\N' --update-key MOBILE --update-mode allowinsert -m 10"
echo; echo; echo
sudo -u hdfs sqoop export --connect $JDBC/$SID --username $USERNAME --password $PASSWORD --table CDT_KL_GD_TRANS --export-dir /user/hive/warehouse/zhengxin.db/gd_var_1031/ymd=${TODAY_1D} --fields-terminated-by '\001' --input-null-string '\\N' --input-null-non-string '\\N' --update-key MOBILE --update-mode allowinsert -m 10
tmp=$?
NOWTIME=$(date +%Y-%m-%d/%H:%M:%S)
if test $tmp -eq 0 ; then
    echo $NOWTIME '------------CDT_KL_GD_TRANS 光大银行交易变量-------'$tmp 
else
    echo $NOWTIME '------------CDT_KL_GD_TRANS 光大银行交易变量--------'$tmp
    echo $NOWTIME'------------光大银行交易变量'$tmp >> /home/hdfs/bank/logs/bank_cron_trans_critical	
    exit -1
fi