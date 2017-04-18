#!/bin/bash
TODAY_1D=$(date --date='1 day ago' +%Y%m%d)
TODAY_0D=$(date +%Y%m%d)

echo "www'${TODAY_1D}'---${TODAY_0D}dcc"

hive -e "
set hive.auto.convert.join=false;
set mapreduce.reduce.shuffle.input.buffer.percent=0.1;
set mapreduce.reduce.shuffle.parallelcopies=9;

----光大手机号去重提取最后一次交易的ymd
drop table zhengxin.tmp_t2;
create table zhengxin.tmp_t2 as
select  mobile_num as mobile,max(ymd) as ymd
from edm.f_fact_trans_success_new t  ---------------------------------------------------是不是有其他交易表，请程其江帮忙看一下他们是不是用其他的表。
group by mobile_num
;

----匹配拉卡拉交易流水
drop table zhengxin.kl_trans;
create table zhengxin.kl_trans
as
select  t4.*,t3.ymd as ymd_max
from
zhengxin.tmp_t2 t3
left join
edm.f_fact_trans_success_new t4
on t3.mobile=t4.mobile_num
where (cast(substr(t3.ymd,1,6) as int)-cast(substr(t4.ymd,1,6) as int))%88+1<=24 and t3.ymd>=t4.ymd  ----最后一笔交易上溯两年的交易流水
and t4.ymd>='20120101'   ----以近五年的交易流水为总体库，因为当前总共的数据都不到五年，所以直接从最开始提取就行了，需要修改
;

----匹配交易的商户信息
drop table zhengxin.kl_trans_all;
create table  zhengxin.kl_trans_all
as
select t1.*,agent_name,corp_name,tcat_lv0_name,tcat_lv1_name,tcat_lv2_name,tcat_lv3_name,tcat_lv4_name,tcat_lv5_name,tcat_lv6_name,tcat_lv7_name,agent_type
from zhengxin.kl_trans t1
left join
edm.d_cfg_agent t2
on t1.merno=t2.agent_no
;

----匹配已经存在的变量
drop table zhengxin.kl_user_var;
create table zhengxin.kl_user_var
as
select t2.*
from
zhengxin.tmp_t2 t1,
creditck.cdt_result_source t2
where t1.mobile=t2.mobile_num
and t2.ymd='${TODAY_1D}'  ----这里使用最新的已更新的creditck.cdt_result_source表中的数据，需要修改
;


drop table zhengxin.kl_zongti_2year_cl_1;
CREATE TABLE zhengxin.kl_zongti_2year_cl_1
AS
SELECT mobile_num,count(DISTINCT SUBSTR(ymd,1,6)) AS jy_mm,COUNT(DISTINCT tcat_lv3_name) AS jy_type_num,
('当前月份，格式如201610'-cast(MAX(substr(ymd,1,6)) AS INT))%88 AS ymd_max_len,--------------------------------------------------需要修改
COUNT(1)  AS all_num,
SUM(total_am/100) AS all_am_sum,
MAX(total_am/100) AS all_am_max,--------------------------------------------------------------------------------------------------------2\all_am_max
COUNT(CASE WHEN tcat_lv4_name='信用卡还款' THEN 1 END) AS hk_num,
SUM(CASE WHEN tcat_lv4_name='信用卡还款' THEN total_am/100 END) AS hk_am_sum,
MAX(CASE WHEN tcat_lv4_name='信用卡还款' THEN total_am/100 END) AS hk_am_max,
COUNT(CASE WHEN tcat_lv4_name='信用卡还款' THEN 1 END)/count(distinct substr(ymd,1,6)) as hk_yjcs1,
SUM(CASE WHEN tcat_lv4_name='信用卡还款' THEN total_am/100 END)/count(distinct substr(ymd,1,6)) as hk_yjje1,
SUM(CASE WHEN tcat_lv4_name='信用卡还款' THEN total_am/100 END)/COUNT(CASE WHEN tcat_lv4_name='信用卡还款' THEN 1 END) as hk_bjje,
COUNT(CASE WHEN tcat_lv3_name='转账汇款' THEN 1 END) AS zz_num,
SUM(CASE WHEN tcat_lv3_name='转账汇款' THEN total_am/100 END) AS zz_am_sum,
MAX(CASE WHEN tcat_lv3_name='转账汇款' THEN total_am/100 END) AS zz_am_max,
COUNT(CASE WHEN tcat_lv3_name='转账汇款' THEN 1 END)/count(distinct substr(ymd,1,6)) as zz_yjcs1,
SUM(CASE WHEN tcat_lv3_name='转账汇款' THEN total_am/100 END)/count(distinct substr(ymd,1,6)) as zz_yjje1,
SUM(CASE WHEN tcat_lv3_name='转账汇款' THEN total_am/100 END)/COUNT(CASE WHEN tcat_lv3_name='转账汇款' THEN 1 END) as zz_bjje,
COUNT(CASE WHEN tcat_lv3_name='查询' THEN 1 END) AS cx_num,
COUNT(CASE WHEN  tcat_lv4_name IN('公缴费-通讯缴费','直充') THEN 1 END) AS hf_num,
SUM(CASE WHEN  tcat_lv4_name IN('公缴费-通讯缴费','直充') THEN total_am/100 END) AS hf_am_sum,
MAX(CASE WHEN  tcat_lv4_name IN('公缴费-通讯缴费','直充') THEN total_am/100 END) AS hf_am_max,--------------------------------------------3\hf_am_max
SUM(CASE WHEN  tcat_lv4_name IN('公缴费-通讯缴费','直充') THEN total_am/100 END)/count(distinct substr(ymd,1,6)) as hf_yjje1,
COUNT(CASE WHEN  tcat_lv4_name IN('公缴费-通讯缴费','直充') THEN 1 END)/count(distinct substr(ymd,1,6)) as hf_yjcs1,
COUNT(CASE WHEN  tcat_lv4_name <>'公缴费-通讯缴费' AND tcat_lv3_name='公缴类' THEN 1 END) AS gj_num,
SUM(CASE WHEN  tcat_lv4_name <>'公缴费-通讯缴费' AND tcat_lv3_name='公缴类' THEN total_am/100 END) AS gj_am_sum,
MAX(CASE WHEN  tcat_lv4_name <>'公缴费-通讯缴费' AND tcat_lv3_name='公缴类' THEN total_am/100 END) AS gj_am_max
FROM zhengxin.kl_trans_all t
GROUP BY mobile_num
;

drop table zhengxin.kl_cl_var_yf;
create table zhengxin.kl_cl_var_yf as------------------------------------------------------各类交易的交易月份数以及每日最大转账笔数，只有18269条
select t1.mobile_num,t2.hk_month,t3.zz_month,t4.hf_month,t5.gj_month,t7.zz_mr_max_num
from
(select distinct mobile_num from zhengxin.kl_trans_all) t1
left join
(select mobile_num,count(distinct substr(ymd,1,6)) hk_month from zhengxin.kl_trans_all where tcat_lv4_name='信用卡还款' group by mobile_num) t2
on t1.mobile_num=t2.mobile_num
left join
(select mobile_num,count(distinct substr(ymd,1,6)) zz_month from zhengxin.kl_trans_all where tcat_lv3_name='转账汇款' group by mobile_num) t3
on t1.mobile_num=t3.mobile_num
left join
(select mobile_num,count(distinct substr(ymd,1,6)) hf_month from zhengxin.kl_trans_all where tcat_lv4_name IN('公缴费-通讯缴费','直充') group by mobile_num) t4
on t1.mobile_num=t4.mobile_num
left join
(select mobile_num,count(distinct substr(ymd,1,6)) gj_month from zhengxin.kl_trans_all where tcat_lv4_name <>'公缴费-通讯缴费' AND tcat_lv3_name='公缴类' group by mobile_num) t5
on t1.mobile_num=t5.mobile_num
left join
(select t6.mobile_num,max(t6.zz_mrcs) zz_mr_max_num from (select mobile_num,count(mobile_num) zz_mrcs,
substr(ymd,1,8) ymd2 from zhengxin.kl_trans_all where tcat_lv3_name='转账汇款' group by mobile_num,substr(ymd,1,8)) t6 group by mobile_num) t7
on t1.mobile_num=t7.mobile_num
;

DROP TABLE zhengxin.kl_zongti_2year_cl_2;
create table zhengxin.kl_zongti_2year_cl_2 as------------------------------------------各个业务类型根据当前业务交易月份计算的月均指标
select t1.*,
t1.hk_num/t2.hk_month as hk_yjcs2,t2.hk_month,-----------------------------------------------------------------------------------------------------4\hk_yjcs2
t1.hk_am_sum/t2.hk_month as hk_yjje2,
t1.zz_num/t2.zz_month as zz_yjcs2,
t1.zz_am_sum/t2.zz_month as zz_yjje2,t2.zz_mr_max_num,
t1.hf_num/t2.hf_month as hf_yjcs2,
t1.hf_am_sum/t2.hf_month as hf_yjje2,-------------------------------------------------------------------------------------------------------------5\hf_yjje2
t1.gj_num/t2.gj_month as gj_yjcs2,
t1.gj_am_sum/t2.gj_month as gj_yjje2
from
zhengxin.kl_zongti_2year_cl_1 t1
left join
zhengxin.kl_cl_var_yf t2
on t1.mobile_num=t2.mobile_num
;

DROP TABLE zhengxin.kl_cl_wendingxing;------------------------新建表有日期稳定，金额稳定变量
CREATE TABLE zhengxin.kl_cl_wendingxing AS
SELECT mobile_num,STDDEV(ri_weighted) rqwd_wt,stddev(ri_pure) rqwd_pure,STDDEV(je_avg) jewd_avg,STDDEV(je_sum) jewd_sum FROM------------------------------6\jewd_avg  7\jewd_sum
(
  select mobile_num,substr(ymd,1,6) yue,avg(cast(substr(ymd,7,2) as int)) ri_weighted,avg(distinct(CAST(substr(ymd,7,2) as int))) ri_pure,
  AVG(total_am/100) je_avg,SUM(total_am/100) je_sum
  from zhengxin.kl_trans_all
  group by mobile_num,substr(ymd,1,6)
 ) t
GROUP BY mobile_num
;

drop table zhengxin.kl_cl_most_time;
CREATE TABLE zhengxin.kl_cl_most_time AS---------------------新建表：最频繁交易时间段
SELECT mobile_num,time_duan FROM--------------------------------------------------------------------------------------------------------------------------
(
SELECT mobile_num,time_duan,cishu,row_number()OVER(PARTITION BY mobile_num ORDER BY cishu DESC) AS cishu_id
FROM
       (SELECT mobile_num,time_duan,COUNT(time_duan) cishu  FROM
              (SELECT mobile_num,CASE WHEN (SUBSTR(data_date,12,2)>7 AND SUBSTR(data_date,12,2)<12) OR(SUBSTR(data_date,12,2)>14 AND
              SUBSTR(data_date,12,2)<18) THEN 'worktime'
              WHEN SUBSTR(data_date,12,2)<22 THEN 'leisuretime' ELSE 'night' END AS time_duan FROM zhengxin.kl_trans_all) t
         GROUP BY mobile_num,time_duan
         ) t2
) t3
WHERE cishu_id=1
;

--******************* 合并表 ************************
drop table zhengxin.kl_cl_user_var;
create table zhengxin.kl_cl_user_var as
select t1.*,t3.rqwd_wt,t3.rqwd_pure,t3.jewd_avg,t3.jewd_sum,t4.time_duan
from
zhengxin.kl_zongti_2year_cl_2 t1
left join
zhengxin.kl_cl_wendingxing t3
on t1.mobile_num=t3.mobile_num
left join
zhengxin.kl_cl_most_time t4
on t1.mobile_num=t4.mobile_num
;

--************是否有手刷*************
drop table zhengxin.kl_USER_CEB_d_user_psam;
create table zhengxin.kl_USER_CEB_d_user_psam as
select t1.*,t1.u_mobile as mobile,t2.mobile_num,t2.psam,t2.psam_type_name
from wm.gs_user_all_bankno_card_last_alter_s_xz  t1,
edw.pt_user_psam_rela t2  --------------------------------看一下这个表是否更新，需要修改。
where t1.u_mobile=t2.mobile_num
;

drop table zhengxin.kl_is_shoushua;  ----------------------------------------------------------------------------------------------------------------------8\is_shoushua
CREATE TABLE zhengxin.kl_is_shoushua AS
SELECT t2.mobile,MAX(t2.term_type) is_shoushua FROM
(SELECT DISTINCT trim(mobile) mobile,(CASE WHEN SUBSTR(psam,1,8) IN('CBC3A3BF','CBC9333F','CBC3A3B2') THEN 1 ELSE 0 END) AS term_type
FROM zhengxin.kl_USER_CEB_d_user_psam t1) t2
GROUP BY mobile
;
--*********************合并考拉数据*************************
drop table zhengxin.kl_cl_var_all_kl;
CREATE TABLE zhengxin.kl_cl_var_all_kl AS
SELECT t1.*,
t4.is_shoushua,
t6.ZW_TIME,
t6.PERSON_STATUS,
t6.CREDIT_COUNT,
t6.DEBIT_COUNT,
t6.CREDIT_CLASS,
t6.DEBIT_CLASS,
t6.DEBIT_KIND,
t6.CREDIT_BJHK,
t6.CREDIT_YJHK,
t6.CREDIT_HKMONTH,
t6.CREDIT_HKLAST,
t6.DEBIT_IN,
t6.DEBIT_OUT,
t6.DEBIT_BJZZ,
t6.DEBIT_ZZMONTH,
t6.DEBIT_YJZZ,
t6.MOBILE_KIND,
t6.JY_TIME,
t6.DEVICE_SS,
t6.DEVICE_SKB,
t6.PERSON_LIVING,
t6.PERSON_HUKOU,
t6.CREDIT_HKWDX,
from
zhengxin.kl_cl_user_var t1
LEFT JOIN
zhengxin.kl_is_shoushua t4
ON t1.mobile_num=t4.mobile
LEFT JOIN
edm.d_mobile_info t5
ON substr(t1.mobile_num,1,7)=substr(t5.mobile_code,1,7)
LEFT JOIN
zhengxin.kl_user_var t6
ON t1.mobile_num=t6.mobile_num
;
"
