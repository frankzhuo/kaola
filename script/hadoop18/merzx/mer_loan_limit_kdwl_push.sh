#!/bin/bash
DATE_0m=$(date +%Y-%m-)01
DATE_0M=$(date +%Y%m)01
MON_0M=$(date +%Y%m)
MON_1M=$(date --date='1 month ago' +%Y%m)
MONTH_1M=$(date --date='1 month ago' +%Y%m)

day=$(date +%d)
if test $day -lt 28;then
DATE_1m=$(date --date='1 month ago' +%Y-%m-)01
DATE_1M=$(date --date='1 month ago' +%Y%m)01
DATE_2M=$(date --date='2 month ago' +%Y%m)01
DATE_3m=$(date --date='3 month ago' +%Y-%m-)01
DATE_3M=$(date --date='3 month ago' +%Y%m)01
DATE_4M=$(date --date='4 month ago' +%Y%m)01
DATE_5M=$(date --date='5 month ago' +%Y%m)01
DATE_6M=$(date --date='6 month ago' +%Y%m)01
DATE_7M=$(date --date='7 month ago' +%Y%m)01
DATE_8M=$(date --date='8 month ago' +%Y%m)01
DATE_9M=$(date --date='9 month ago' +%Y%m)01
DATE_10M=$(date --date='10 month ago' +%Y%m)01
DATE_11M=$(date --date='11 month ago' +%Y%m)01
DATE_13m=$(date --date='13 month ago' +%Y-%m)
DATE_13M=$(date --date='13 month ago' +%Y%m)
DATE_1y=$(date --date='1 year ago' +%Y-%m-)01
DATE_1Y=$(date --date='1 year ago' +%Y%m)01
DATE_2y=$(date --date='2 year ago' +%Y-%m-)01
DATE_2Y=$(date --date='2 year ago' +%Y%m)01
DATE_1Y_1M=$(date --date='1 year ago 1 month ago' +%Y%m)01
DATE_1Y_2M=$(date --date='1 year ago 2 month ago' +%Y%m)01
DATE_1Y_3M=$(date --date='1 year ago 3 month ago' +%Y%m)01
DATE_1Y_4M=$(date --date='1 year ago 4 month ago' +%Y%m)01
DATE_1Y_5M=$(date --date='1 year ago 5 month ago' +%Y%m)01
DATE_1Y_6M=$(date --date='1 year ago 6 month ago' +%Y%m)01
DATE_1Y_7M=$(date --date='1 year ago 7 month ago' +%Y%m)01
DATE_1Y_8M=$(date --date='1 year ago 8 month ago' +%Y%m)01
DATE_1Y_9M=$(date --date='1 year ago 9 month ago' +%Y%m)01
DATE_1Y_10M=$(date --date='1 year ago 10 month ago' +%Y%m)01
DATE_1Y_11M=$(date --date='1 year ago 11 month ago' +%Y%m)01
else
DATE_1m=$(date --date='1 month ago 10 day ago' +%Y-%m-)01
DATE_1M=$(date --date='1 month ago 10 day ago' +%Y%m)01
DATE_2M=$(date --date='2 month ago 10 day ago' +%Y%m)01
DATE_3m=$(date --date='3 month ago 10 day ago' +%Y-%m-)01
DATE_3M=$(date --date='3 month ago 10 day ago' +%Y%m)01
DATE_4M=$(date --date='4 month ago 10 day ago' +%Y%m)01
DATE_5M=$(date --date='5 month ago 10 day ago' +%Y%m)01
DATE_6M=$(date --date='6 month ago 10 day ago' +%Y%m)01
DATE_7M=$(date --date='7 month ago 10 day ago' +%Y%m)01
DATE_8M=$(date --date='8 month ago 10 day ago' +%Y%m)01
DATE_9M=$(date --date='9 month ago 10 day ago' +%Y%m)01
DATE_10M=$(date --date='10 month ago 10 day ago' +%Y%m)01
DATE_11M=$(date --date='11 month ago 10 day ago' +%Y%m)01
DATE_13m=$(date --date='13 month ago 10 day ago' +%Y-%m)
DATE_13M=$(date --date='13 month ago 10 day ago' +%Y%m)
DATE_1y=$(date --date='1 year ago 10 day ago' +%Y-%m-)01
DATE_1Y=$(date --date='1 year ago 10 day ago' +%Y%m)01
DATE_2y=$(date --date='2 year ago 10 day ago' +%Y-%m-)01
DATE_2Y=$(date --date='2 year ago 10 day ago' +%Y%m)01
DATE_1Y_1M=$(date --date='1 year ago 1 month ago 10 day ago' +%Y%m)01
DATE_1Y_2M=$(date --date='1 year ago 2 month ago 10 day ago' +%Y%m)01
DATE_1Y_3M=$(date --date='1 year ago 3 month ago 10 day ago' +%Y%m)01
DATE_1Y_4M=$(date --date='1 year ago 4 month ago 10 day ago' +%Y%m)01
DATE_1Y_5M=$(date --date='1 year ago 5 month ago 10 day ago' +%Y%m)01
DATE_1Y_6M=$(date --date='1 year ago 6 month ago 10 day ago' +%Y%m)01
DATE_1Y_7M=$(date --date='1 year ago 7 month ago 10 day ago' +%Y%m)01
DATE_1Y_8M=$(date --date='1 year ago 8 month ago 10 day ago' +%Y%m)01
DATE_1Y_9M=$(date --date='1 year ago 9 month ago 10 day ago' +%Y%m)01
DATE_1Y_10M=$(date --date='1 year ago 10 month ago 10 day ago' +%Y%m)01
DATE_1Y_11M=$(date --date='1 year ago 11 month ago 10 day ago' +%Y%m)01
fi 

DATE=$(date +%Y-%m-%d)
TODAY=$(date +%Y%m%d)




#####################   商户授信  ################################################################
#授信额度表: MERZX.MER_LOAN_LIMIT_PUSH    ( CDT_MERKDWLTRIALSTATE ) 
#近期交易表：MERZX.MER_LOAN_LIMIT_TRANS_PUSH  (CDT_MERKDWLSEVENTRA)

#1 卡得万利 产品编码：18



###### 输出的表 #####################################################################
#1 merzx.mer_limit_kdwl_var_mid 授信变量准备（中间表）
#2 merzx.mer_limit_kdwl_rule_mid  授信规则 （中间表）
#3 merzx.mer_limit_kdwl_bitmap_1m_mid 交易天数位图 （中间表）
#4  (推送表2-1) MERZX.MER_LOAN_LIMIT_PUSH  授信推送表 CDT_MERKDWLTRIALSTATE
#5 merzx.mer_limit_kdwl_trans_mid  近7月交易信息（中间表）
#6  (推送表2-2) MERZX.MER_LOAN_LIMIT_TRANS_PUSH 近期交易推送表  CDT_MERKDWLSEVENTRA


###### 依赖的表 #####################################################################
#1  merzx.v2_merzx_result_score_push
#2  merzx.v2_merzx_result_report_push 
#3  merzx.v2_mid_merchant_cnt_amt_day_mon
#4  hdw.lklpos_atmtxnjnl_success
#5  merzx.mer_limit_kdwl_mccforbid
#6  cm.cm_date_info
#7  merzx.v2_mid_merchant_cnt_amt_day_mon_fail








###################### 1  商户卡得万利初审状态表    CDT_MERKDWLTRIALSTATE   #######################


echo '--------------- 1.1 授信变量准备   -------------'

#while true 
#do 

sudo -u hdfs hive <<EOF 

set hive.auto.convert.join=false; 
set mapreduce.reduce.shuffle.input.buffer.percent=0.1;
set mapreduce.reduce.shuffle.parallelcopies=9;

drop table merzx.mer_limit_kdwl_var_mid;
create table merzx.mer_limit_kdwl_var_mid
as
  SELECT 
        t0.orgid,                      -- 机构代码
        t0.mer_id,                     -- 商编       
        t0.score,                      -- 评分
        t0.cr_licence_no ,             -- 身份证
        b.mcc_code AS mcc_code_ori ,   -- mcc
        e.amt_limit,                   -- mcc限额
        b.mannowtrehighriskindthan,    -- 高风险交易占比
        c.lj_day_1m,                   -- 近1月刷卡天数
        d.last_day,                    -- 末次交易日期
        c.trans_mon_3m  ,              -- 近3月交易月份数
        c.trans_amt_l1,                -- 近1月交易金额
        c.yj_amt_3m ,                  -- 近3月月均交易金额
        d.fst_day,                     -- 首次交易日期
       CASE WHEN  t0.score >=550 THEN 1 ELSE 0  END AS  score_flag  ,  ------------考拉商户分550分
       CASE WHEN  length(t0.cr_licence_no) = 18  AND (CAST((substr('${DATE_0M}', 1,4)  - substr(t0.cr_licence_no,7,4)) as int)   BETWEEN 18 AND 65 )  THEN 1  
              WHEN  length(t0.cr_licence_no) = 15  AND  ( CAST((substr('${DATE_0M}', 1,4) - concat('19',substr(t0.cr_licence_no,7,2))) as int) BETWEEN 18 AND 65 )  THEN 1 
               ELSE 0  END AS age_flag  ,
       CASE WHEN  b.mannowtrehighriskindthan > 0.4 THEN  0 ELSE 1 END  AS risk_flag,
       CASE WHEN  c.lj_day_1m >= 8 THEN 1 ELSE 0 END AS  day1m_flag, 
       CASE WHEN  DATEDIFF(FROM_UNIXTIME(unix_timestamp( '${DATE_0M}','yyyyMMdd'),'yyyy-MM-dd')  ,  FROM_UNIXTIME( unix_timestamp( d.last_day ,'yyyyMMdd'),'yyyy-MM-dd'))  <=15  THEN 1  
              ELSE 0 END   AS lastday_flag ,
       CASE WHEN  c.trans_mon_3m = 3  THEN 1 ELSE 0 END AS translst3m_flag , 
       CASE WHEN  forbid_flag = 0 THEN 1 ELSE 0 END AS  mccadmit_flag 
   FROM 
      (SELECT orgid, mreid AS mer_id,  score,   legalpersonidcard AS cr_licence_no   FROM   merzx.v2_merzx_result_score_push    where score>=550 and orgid = '110001' ) t0
    LEFT   JOIN 
       ( SELECT * FROM   merzx.v2_merzx_result_report_push   WHERE  orgid = '110001' ) b
     ON  t0.mer_id = b.mreid  
   LEFT  JOIN  
      (SELECT * FROM  merzx.v2_mid_merchant_cnt_amt_day_mon )   c 
     ON t0.mer_id = c.mer_id   
   LEFT JOIN 
       ( select orgid,mer_id, MIN(ymd) fst_day , MAX(ymd) last_day   FROM  hdw.lklpos_atmtxnjnl_success WHERE  trans_amt > 0 AND  ymd < '${DATE_0M}'  GROUP BY  orgid, mer_id ) d  
     ON t0.mer_id = d.mer_id  
   LEFT  JOIN
       ( SELECT * FROM  merzx.MER_LIMIT_KDWL_MCCFORBID ) e 
     ON  b.mcc_code  = e.mcc_code   ;
EOF
tmp=$?
NOWTIME=$(date +%Y-%m-%d/%H:%M:%S)
if test $tmp -eq 0; then
echo $NOWTIME '--------------授信变量准备 mer_limit_kdwl_var_mid 创建成功--------'$tmp  
break
else
echo $NOWTIME '--------------授信变量准备 mer_limit_kdwl_var_mid 创建失败--------'$tmp  
#sleep 5m 
fi 

#done

  


echo '--------------- 1.2 授信规则   -------------'

#while true 
#do 

sudo -u hdfs hive <<EOF 

set hive.auto.convert.join=false; 
set mapreduce.reduce.shuffle.input.buffer.percent=0.1;
set mapreduce.reduce.shuffle.parallelcopies=9;

drop table merzx.mer_limit_kdwl_rule_mid;
create table merzx.mer_limit_kdwl_rule_mid
as
    SELECT 
     orgid,
     mer_id, 
     '18' as prdid,  --产品编号
     admit_tag,      -- 预授信状态   
    -- CASE WHEN admit_tag = 1 THEN  least ( cast( limit_day_max as float) ,  cast( limit_mcc_max as float) ,    least ( cast( limit_amt_lower as float),cast( limit_score as float) ))  ELSE 0  END AS  loan_limit_lower ,
    -- CASE WHEN admit_tag = 1 THEN  least ( cast( limit_day_max as float) ,  cast( limit_mcc_max as float) , greatest ( cast( limit_amt_upper as float),cast( limit_score as float) ))  ELSE 0  END AS  loan_limit_upper ,
     case when admit_tag = 1 then greatest (least (cast( limit_day_max as float) ,  cast( limit_mcc_max as float) , cast( limit_amt_lower as float)) , cast( limit_score as float) ) else 0 end as loan_limit_lower ,
     case when admit_tag = 1 then greatest (least( cast( limit_day_max as float) ,  cast( limit_mcc_max as float) , cast( limit_amt_upper as float)) , cast( limit_score as float) ) else 0 end as loan_limit_upper ,
     limit_amt_lower, 
     limit_amt_upper, 
     limit_score, 
     limit_day_max, 
     limit_mcc_max 
     FROM
     (
     SELECT orgid,
            mer_id, 
            CASE WHEN ( score_flag*lastday_flag*age_flag*risk_flag*mccadmit_flag*translst3m_flag*day1m_flag) =1 THEN 1 ELSE 0 END  AS admit_tag, 
            least(cast(trans_amt_l1 as float),cast( yj_amt_3m as float))*1.05  AS limit_amt_lower ,
            least(cast(trans_amt_l1 as float),cast( yj_amt_3m as float))*1.50  AS limit_amt_upper ,
            CASE WHEN lj_day_1m <11 OR lj_day_1m IS NULL  THEN 20000000  ELSE 99999999999 END  AS  limit_day_max, 
            amt_limit AS limit_mcc_max , 
            CASE WHEN score >=600 AND score <650   THEN  4000000 
                 WHEN score >= 650 AND score < 700 THEN  6000000
                 WHEN score >= 700  THEN  8000000
            ELSE 0 END AS  limit_score 
    FROM  merzx.mer_limit_kdwl_var_mid   ) a  ;
EOF
tmp=$?
NOWTIME=$(date +%Y-%m-%d/%H:%M:%S)
if test $tmp -eq 0; then
echo $NOWTIME '--------------授信规则 mer_limit_kdwl_rule_mid 创建成功--------'$tmp  
break
else
echo $NOWTIME '--------------授信规则 mer_limit_kdwl_rule_mid 创建失败--------'$tmp  
#sleep 5m 
fi 

#done




echo '--------------- 1.3  上月有交易天数位图 (中间表)  -------------'


#while true 
#do 

sudo -u hdfs hive <<EOF 

set hive.auto.convert.join=false; 
set mapreduce.reduce.shuffle.input.buffer.percent=0.1;
set mapreduce.reduce.shuffle.parallelcopies=9;

drop table merzx.mer_limit_kdwl_bitmap_1m_mid ;
create table merzx.mer_limit_kdwl_bitmap_1m_mid as
 SELECT 
    b.orgid as orgid, 
    b.mer_id as mer_id, 
    concat_ws('',collect_list(  CASE WHEN  c.ymd IS NULL THEN '0' ELSE '1' END ))   AS  day_bitmap_1m      -----------上月有交易天数枚举
  FROM 
 (SELECT  dt_cd , concat( substr(dt_cd,1,4), substr(dt_cd,6,2), substr(dt_cd,9,2))   AS DAY_ymd,  '1' AS flag_a  FROM  EDW.CM_DATE_INFO   WHERE dt_cd >= '${DATE_1m}' AND dt_cd <'${DATE_0m}' order by day_ymd )  a
 JOIN 
 (select  orgid, mreid as mer_id , '1' AS flag_b   from merzx.v2_merzx_result_score_push    where score>=550 and orgid= '110001'  ) b
 ON  a.flag_a = b.flag_b 
 LEFT JOIN 
  (select DISTINCT orgid, mer_id , ymd   from hdw.lklpos_atmtxnjnl_success where trans_amt>0 and ymd>='${DATE_1M}' and ymd<'${DATE_0M}' AND orgid = '110001' ) c
  ON  b.mer_id = c.mer_id AND  b.orgid = c.orgid AND   a.DAY_ymd = c.ymd 
  GROUP BY   b.orgid ,  b.mer_id
;
EOF
tmp=$?
NOWTIME=$(date +%Y-%m-%d/%H:%M:%S)
if test $tmp -eq 0; then
echo $NOWTIME '--------------上月有交易天数枚举 mer_limit_kdwl_bitmap_1m_mid 创建成功--------'$tmp  
break
else
echo $NOWTIME '--------------上月有交易天数枚举 mer_limit_kdwl_bitmap_1m_mid 创建失败--------'$tmp  
#sleep 5m 
fi 

#done






echo '--------------- PUSH_1   商户卡得万利初审状态推送表   ---------------------------------------'

#while true 
#do 

sudo -u hdfs hive <<EOF 

set hive.auto.convert.join=false; 
set mapreduce.reduce.shuffle.input.buffer.percent=0.1;
set mapreduce.reduce.shuffle.parallelcopies=9;


create table if not exists merzx.mer_loan_limit_push(
id                      string   comment '主键',
orgid                   string   comment '机构编号',
merid                   string   comment '商户号',
prdid                   string   comment '产品编号',
creditstate             string   comment '预授信状态',
creditamountonline      string   comment '预授信额度上限',
creditamountoffline     string   comment '预授信额度下限',
firsttratime            string   comment '首次交易日期',
lasttratime             string   comment '末次交易日期',
lastmonthdays           string   comment '上月有交易天数枚举',
merscorewrong           string   comment '考拉商户分550分',
recsuctradaywrong       string   comment '最近一笔成功交易距今不超过15天',
legreoagewrong          string   comment '法人代表年龄18-65周岁',
suscashwrong            string   comment '疑似套现笔数占比低于40%',
industrywrong           string   comment '禁入行业不做',
merposwrong             string   comment '商户POS机刷卡流水需连续交易满90天',
legtrawrong             string   comment '最近15天内存在有效交易流水',
legmonpaycardwrong      string   comment '最近月刷卡天数大于8天',
createtime              string   comment '创建时间',
updatetime              string   comment '修改时间'
)  partitioned by (mon string);


insert overwrite table merzx.mer_loan_limit_push  partition (mon='${MON_1M}') 
select 
concat(a.orgid,a.mer_id)   as  id,         ------主键
a.orgid                    as orgid,       ------------机构编号
a.mer_id                   as merid,       -------------商户号
b.prdid                    as prdid,       -----------产品编号
b.admit_tag                                    as creditstate,         ----------预授信状态
cast(round(b.loan_limit_upper/100000)*100000 as bigint)   as creditamountonline,  ----------预授信额度上限
cast(round(b.loan_limit_lower/100000)*100000 as bigint)   as creditamountoffline, ----------预授信额度下限
a.fst_day                     as firsttratime,        ----------首次交易日期
a.last_day                    as lasttratime,         -----------末次交易日期
c.day_bitmap_1m               as lastmonthdays,       -----------上月有交易天数枚举
a.score_flag                  as merscorewrong,       ------------考拉商户分550分
a.lastday_flag                as recsuctradaywrong,   ---------最近一笔成功交易距今不超过15天
a.age_flag                    as legreoagewrong,      -----------法人代表年龄18-65周岁
a.risk_flag                   as suscashwrong,        -------疑似套现笔数占比低于40%
a.mccadmit_flag               as industrywrong,       -----禁入行业不做
a.translst3m_flag             as merposwrong,         -----------商户POS机刷卡流水需连续交易满90天
a.lastday_flag                as legtrawrong,         -----------最近15天内存在有效交易流水??????????
a.day1m_flag                  as legmonpaycardwrong,  ----------------最近月刷卡天数大于8天
from_unixtime(unix_timestamp(),'yyyy-MM-dd HH:mm:ss') as createtime,   ------------创建时间
from_unixtime(unix_timestamp(),'yyyy-MM-dd HH:mm:ss') as updatetime    ------------修改时间
from 
merzx.mer_limit_kdwl_var_mid a
join 
merzx.mer_limit_kdwl_rule_mid b
on a.orgid = b.orgid and a.mer_id = b.mer_id 
join 
merzx.mer_limit_kdwl_bitmap_1m_mid c
on a.orgid = c.orgid and a.mer_id = c.mer_id 
;

EOF


tmp=$?
NOWTIME=$(date +%Y-%m-%d/%H:%M:%S)
if test $tmp -eq 0; then
echo $NOWTIME "-------------- mer_loan_limit_push 创建成功--------"$tmp  
break
else
echo $NOWTIME "-------------- mer_loan_limit_push 创建失败--------"$tmp 
#sleep 5m
fi 

#done











####################### 2   近7个月每月交易信息  CDT_MERKDWLSEVENTRA   #######################

echo '--------------- 近7月交易中间表  -------------'

#while true
#do
sudo -u hdfs hive <<EOF

DROP TABLE   merzx.mer_limit_kdwl_trans_mid ;

CREATE TABLE  merzx.mer_limit_kdwl_trans_mid  AS 
SELECT  a.orgid as orgid, b.mer_id as mer_id, '${DATE_1M}' as date_mon,  lj_day_1m as trans_Day, trans_cnt_l1 as trans_cnt,
      trans_amt_l1 as trans_amt ,credit_cnt_l1 as credit_cnt, credit_amt_l1 as credit_amt, yj_card_1m as card_cnt , fail_cnt_1m as fail_cnt
FROM 
  ( SELECT orgid, mreid as mer_id FROM   merzx.v2_merzx_result_score_push WHERE score>=550 AND orgid = '110001' ) a
  JOIN 
  ( SELECT orgid, mer_id, lj_day_1m, trans_cnt_l1 , trans_amt_l1 ,credit_cnt_l1,credit_amt_l1,yj_card_1m  FROM  merzx.v2_mid_merchant_cnt_amt_day_mon) b
  ON a.mer_id = b.mer_id AND a.orgid = b.orgid 
  JOIN 
  (SELECT  orgid, mer_id, fail_cnt_1m  FROM merzx.v2_mid_merchant_cnt_amt_day_mon_fail) c
   ON a.mer_id = c.mer_id AND a.orgid = c.orgid 
  
UNION ALL 

SELECT  a.orgid as orgid, b.mer_id as mer_id,  '${DATE_2M}' as date_mon, lj_day_f1m as trans_Day, yj_cnt_f1m as  trans_cnt, 
yj_amt_f1m  as trans_amt , credit_cnt_fl1  as credit_cnt, credit_amt_fl1 as credit_amt, yj_card_f1m as card_cnt, fail_cnt_f1m as fail_cnt
FROM 
  ( SELECT orgid, mreid as mer_id  FROM  merzx.v2_merzx_result_score_push WHERE score>=550 AND orgid = '110001' ) a
 JOIN 
  ( SELECT  orgid, mer_id, lj_day_f1m, yj_cnt_f1m , yj_amt_f1m ,credit_cnt_fl1,credit_amt_fl1,yj_card_f1m   FROM  merzx.v2_mid_merchant_cnt_amt_day_mon) b
  ON a.mer_id = b.mer_id AND a.orgid = b.orgid 
  JOIN 
  (SELECT orgid, mer_id, fail_cnt_f1m  FROM merzx.v2_mid_merchant_cnt_amt_day_mon_fail) c
  ON a.mer_id = c.mer_id AND a.orgid = c.orgid 
  UNION ALL
  
 SELECT a.orgid as orgid	, b.mer_id as mer_id, '${DATE_3M}'  as date_mon, lj_day_f2m as trans_Day, yj_cnt_f2m as  trans_cnt,
  yj_amt_f2m   as trans_amt, credit_cnt_fl2  as credit_cnt, credit_amt_fl2 as credit_amt, yj_card_f2m as card_cnt, fail_cnt_f2m as fail_cnt
FROM( SELECT orgid, mreid as mer_id  FROM   merzx.v2_merzx_result_score_push WHERE score>=550 AND orgid = '110001' ) a
 JOIN 
  ( SELECT  orgid, mer_id, lj_day_f2m, yj_cnt_f2m, yj_amt_f2m, credit_cnt_fl2, credit_amt_fl2, yj_card_f2m FROM  merzx.v2_mid_merchant_cnt_amt_day_mon) b
  ON a.mer_id = b.mer_id AND a.orgid = b.orgid 
  JOIN 
  (SELECT   orgid, mer_id, fail_cnt_f2m  FROM merzx.v2_mid_merchant_cnt_amt_day_mon_fail) c
  ON a.mer_id = c.mer_id AND a.orgid = c.orgid 
  UNION ALL
  
   SELECT a.orgid as orgid	, b.mer_id as mer_id, '${DATE_4M}'  as date_mon, lj_day_f3m as trans_Day, yj_cnt_f3m as  trans_cnt,
  yj_amt_f3m   as trans_amt, credit_cnt_fl3  as credit_cnt, credit_amt_fl3 as credit_amt, yj_card_f3m as card_cnt, fail_cnt_f3m as fail_cnt
  FROM 
  ( SELECT orgid, mreid as mer_id FROM   merzx.v2_merzx_result_score_push WHERE score>=550 AND orgid = '110001' ) a
 JOIN
  ( SELECT orgid, mer_id, lj_day_f3m, yj_cnt_f3m, yj_amt_f3m, credit_cnt_fl3, credit_amt_fl3, yj_card_f3m  FROM  merzx.v2_mid_merchant_cnt_amt_day_mon) b
  ON a.mer_id = b.mer_id AND a.orgid = b.orgid 
  JOIN 
  (SELECT orgid, mer_id, fail_cnt_f3m  FROM merzx.v2_mid_merchant_cnt_amt_day_mon_fail) c
  ON a.mer_id = c.mer_id AND a.orgid = c.orgid
  UNION ALL
  
   SELECT a.orgid as orgid	, b.mer_id as mer_id, '${DATE_5M}'  as date_mon, lj_day_f4m as trans_Day, yj_cnt_f4m as  trans_cnt,
  yj_amt_f4m   as trans_amt, credit_cnt_fl4  as credit_cnt, credit_amt_fl4 as credit_amt, yj_card_f4m as card_cnt, fail_cnt_f4m as fail_cnt
 FROM 
  ( SELECT orgid, mreid as mer_id  FROM   merzx.v2_merzx_result_score_push WHERE score>=550 AND orgid = '110001' ) a
 JOIN 
  ( SELECT orgid, mer_id, lj_day_f4m, yj_cnt_f4m, yj_amt_f4m, credit_cnt_fl4, credit_amt_fl4, yj_card_f4m FROM merzx.v2_mid_merchant_cnt_amt_day_mon) b
  ON a.mer_id = b.mer_id AND a.orgid = b.orgid 
  JOIN 
  (SELECT orgid, mer_id, fail_cnt_f4m  FROM merzx.v2_mid_merchant_cnt_amt_day_mon_fail) c
  ON a.mer_id = c.mer_id AND a.orgid = c.orgid
  UNION ALL
  
   SELECT a.orgid as orgid	, b.mer_id as mer_id, '${DATE_6M}'  as date_mon, lj_day_f5m as trans_Day, yj_cnt_f5m as  trans_cnt,
  yj_amt_f5m   as trans_amt, credit_cnt_fl5  as credit_cnt, credit_amt_fl5 as credit_amt, yj_card_f5m as card_cnt, fail_cnt_f5m as fail_cnt
   FROM 
  ( SELECT orgid, mreid  as mer_id FROM   merzx.v2_merzx_result_score_push WHERE score>=550 AND orgid = '110001' ) a
 JOIN 
  ( SELECT orgid, mer_id, lj_day_f5m, yj_cnt_f5m, yj_amt_f5m, credit_cnt_fl5, credit_amt_fl5, yj_card_f5m FROM  merzx.v2_mid_merchant_cnt_amt_day_mon) b
  ON a.mer_id = b.mer_id AND a.orgid = b.orgid 
  JOIN 
  (SELECT  orgid, mer_id, fail_cnt_f5m FROM merzx.v2_mid_merchant_cnt_amt_day_mon_fail) c
  ON a.mer_id = c.mer_id AND a.orgid = c.orgid
  UNION ALL
  
   SELECT a.orgid as orgid	, b.mer_id as mer_id, '${DATE_7M}'  as date_mon, lj_day_f6m as trans_Day, yj_cnt_f6m as  trans_cnt,
  yj_amt_f6m   as trans_amt, credit_cnt_fl6  as credit_cnt, credit_amt_fl6 as credit_amt, yj_card_f6m as card_cnt, fail_cnt_f6m as fail_cnt
  FROM 
  ( SELECT orgid, mreid as mer_id  FROM   merzx.v2_merzx_result_score_push WHERE score>=550 AND orgid = '110001' ) a
 JOIN 
  ( SELECT orgid, mer_id, lj_day_f6m, yj_cnt_f6m, yj_amt_f6m, credit_cnt_fl6, credit_amt_fl6, yj_card_f6m  FROM  merzx.v2_mid_merchant_cnt_amt_day_mon) b
  ON a.mer_id = b.mer_id AND a.orgid = b.orgid 
  JOIN 
  (SELECT orgid,  mer_id, fail_cnt_f6m FROM merzx.v2_mid_merchant_cnt_amt_day_mon_fail) c
  ON a.mer_id = c.mer_id AND a.orgid = c.orgid;
EOF
tmp=$?
NOWTIME=$(date +%Y-%m-%d/%H:%M:%S)
if test $tmp -eq 0; then
echo $NOWTIME '--------------近7月交易中间表 merzx.mer_limit_kdwl_trans_mid 创建成功--------'$tmp
break
else
echo $NOWTIME '--------------近7月交易中间表 merzx.mer_limit_kdwl_trans_mid 创建失败--------'$tmp
fi
#done





echo '--------------- PUSH_2  商户卡得万利交易推送表   -------------'
#while true 
#do 

sudo -u hdfs hive <<EOF 

set hive.auto.convert.join=false; 
set mapreduce.reduce.shuffle.input.buffer.percent=0.1;
set mapreduce.reduce.shuffle.parallelcopies=9;



create table if not exists merzx.mer_loan_limit_trans_push(
id                      string   comment '主键',
orgid                   string   comment '机构编号',
prdid                   string   comment '产品编号',
merid                   string   comment '商户号',
tratime                 string   comment '交易日期',
effdays                 int      comment '有交易天数',
tranum                  float    comment '交易笔数',
traamount               float    comment '交易金额',
creditcardtranum        float    comment '贷记卡交易笔数',
creditcardtraamount     float    comment '贷记卡交易金额',
tracardsum              float    comment '交易卡数',
errtrasum               float    comment '失败交易笔数',
createtime              string   comment '创建时间',
updatetime              string   comment '修改时间'
);



insert overwrite table merzx.mer_loan_limit_trans_push
select 
concat(concat(orgid,mer_id),substr(a.date_mon,1,6))     as id,
a.orgid                  as orgid,     -------机构编号
'18'                     as prdid,     -------产品编号
a.mer_id                 as merid,     -------商户号
substr(a.date_mon,1,6)   as tratime,   -------交易日期
nvl(a.trans_day,0)       as effdays,   -------有交易天数
nvl(a.trans_cnt,0)       as tranum,    ------交易笔数
nvl(a.trans_amt,0)       as traamount, -------交易金额
nvl(a.credit_cnt,0)      as creditcardtranum, ---贷记卡交易笔数
nvl(a.credit_amt,0)      as creditcardtraamount, ----贷记卡交易金额
nvl(a.card_cnt,0)        as tracardsum, ----交易卡数
nvl(a.fail_cnt,0)        as errtrasum, -----失败交易笔数
from_unixtime(unix_timestamp(),'yyyy-MM-dd HH:mm:ss') as createtime,
from_unixtime(unix_timestamp(),'yyyy-MM-dd HH:mm:ss') as updatetime
from merzx.mer_limit_kdwl_trans_mid a ; 

EOF


tmp=$?
NOWTIME=$(date +%Y-%m-%d/%H:%M:%S)
if test $tmp -eq 0; then
echo $NOWTIME "-------------- mer_loan_limit_trans_push 创建成功--------"$tmp  
break
else
echo $NOWTIME "-------------- mer_loan_limit_trans_push 创建失败--------"$tmp 
#sleep 5m
fi 

#done

