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
DATE_6M=$(date --date='6 month ago' +%Y%m)01
DATE_12m=$(date --date='1 year ago' +%Y-%m-)01
DATE_12M=$(date --date='1 year ago' +%Y%m)01
DATE_24M=$(date --date='2 year ago' +%Y%m)01
else
DATE_3m=$(date --date='3 month ago 10 day ago' +%Y-%m-)01
DATE_6m=$(date --date='6 month ago 10 day ago' +%Y-%m-)01
DATE_6M=$(date --date='6 month ago 10 day ago' +%Y%m)01
DATE_12m=$(date --date='1 year ago 10 day ago' +%Y-%m-)01
DATE_12M=$(date --date='1 year ago 10 day ago' +%Y%m)01
DATE_24M=$(date --date='2 year ago 10 day ago' +%Y%m)01
fi

'row_cnt_0=$(sudo -u hdfs hdfs dfs -cat /user/hive/warehouse/creditck.db/var_wll_xd_loan/* | wc -l)'
echo '-----------------总借款次数 xd_count,总借款金额 xd_sum,笔均借款金额 xd_ave,逾期状态 xd_status--------'
sudo -u hdfs hive <<EOF
drop table  creditck.var_wll_xd_loan;
create table creditck.var_wll_xd_loan as
SELECT MOBILE,
       COUNT(*) AS XD_COUNT,
       SUM(LOAN_CAPITAL)/100 AS XD_SUM,
       SUM(LOAN_CAPITAL)/COUNT(*)/100 AS XD_AVE,
       MAX(STATUS) AS XD_STATUS_MAX,
       MAX(OUT_DAY) AS XD_OUT_MAX,
       sum(OUT_DAY) as xd_out_day_sum,
       sum(case when OUT_DAY>0 then LOAN_CAPITAL/100 end) as xd_nosettle_sum,
       sum(case when OUT_DAY>0 then 1 else 0 end)  as  xd_yq_cnt,  --逾期总次数
       (CASE WHEN MAX(STATUS)=3 AND MAX(OUT_DAY)<=7 THEN 4
             WHEN MAX(STATUS)=3 AND MAX(OUT_DAY)>7 THEN 5
             WHEN MAX(STATUS)=4 THEN 6
             ELSE MAX(STATUS)
             END ) AS XD_STATUS  
FROM
(SELECT D.MOBILE,C.LOAN_CAPITAL,
       CASE WHEN C.IS_SETTLE='1' AND C.OUT_DAY<=0 THEN 1
            WHEN C.IS_SETTLE='0' AND C.OUT_DAY<=0 THEN 2
            WHEN C.IS_SETTLE='1' AND C.OUT_DAY>0 THEN 3
            WHEN C.IS_SETTLE='0' AND C.OUT_DAY>0 THEN 4
            END AS STATUS,
       CASE WHEN C.OUT_DAY<=0 THEN NULL
            WHEN C.OUT_DAY>0 THEN C.OUT_DAY
              END AS OUT_DAY
from ods.XD_C_LOAN_APPLY A ,
     ods.XD_C_LOAN_CONTRACT B ,
     (SELECT T.contract_id,
             T.LOAN_CAPITAL,
             T.CAPITAL_UNREFUND,
             trim(T.IS_SETTLE) as IS_SETTLE,
             T.SETTLE_TIME,
             T.DUE_DATE,
             CASE WHEN T.SETTLE_TIME IS NULL THEN datediff(from_unixtime(unix_timestamp(),'yyyy-MM-dd'),from_unixtime(unix_timestamp(T.DUE_DATE),'yyyy-MM-dd')) 
             WHEN T.SETTLE_TIME IS NOT NULL THEN datediff(from_unixtime(unix_timestamp(T.SETTLE_TIME),'yyyy-MM-dd'),from_unixtime(unix_timestamp(T.DUE_DATE),'yyyy-MM-dd'))
             END AS OUT_DAY
             FROM ods.XD_C_RECEIPT T) C ,
ods.XD_C_APPLY_USER D
where cast(A.id as int)=cast(B.apply_id as int)
and cast(B.id as int)=cast(C.contract_id as int)
and cast(A.ID as int)=cast(D.ID as int)
and trim(A.status)='T') t
GROUP BY MOBILE;
EOF
tmp=$?
'row_cnt=$(sudo -u hdfs hdfs dfs -cat /user/hive/warehouse/creditck.db/var_wll_xd_loan/* | wc -l)'
NOWTIME=$(date +%Y-%m-%d/%H:%M:%S)
if test $tmp -eq 0 -a $row_cnt -ge $row_cnt_0; then
echo $NOWTIME'------1--------var_wll_xd_loan创建成功'$tmp >> logs/logs_cdt_datasource_result
else
echo $NOWTIME'------1--------var_wll_xd_loan创建失败'$tmp /$row_cnt/$row_cnt_0 >> logs/logs_cdt_datasource_result
fi


echo '----------------余额理财日均账户金额------------'
impala-shell -r <<EOF
insert overwrite table creditck.var_sharebal_day 
select mobile_num,
       sum(sharebal)/(datediff(max(duedate),min(startdate))+1) as sharebal_day
from (select nvl(t4.userid,t4.mobilephone) as mobile_num,
             sum(t3.sharebal) as sharebal,
             from_unixtime(unix_timestamp(cast(cast(min(t3.tradedate) as int) as string),'yyyyMMdd'),'yyyy-MM-dd') as startdate,
             from_unixtime(unix_timestamp(cast(cast(max(t3.tradedate) as int) as string),'yyyyMMdd'),'yyyy-MM-dd') as duedate
      from hds.kd_acct_cust t1,
           hds.kd_cfg_acct t2,
           hds.kd_h_acct_bal_detail t3,
           hds.nuc_ecusr t4
      where t1.custid=t2.custid and t2.finacctid=t3.finacctid and cast(t4.userseq as int)=cast(t1.custcode as int) 
            and cast(trim(t2.status) as int)=1
      group by nvl(t4.userid,t4.mobilephone)
      union all
      select nvl(t2.userid,t2.mobilephone) as mobile_num,
             sum(t1.amount) as sharebal,
             min(t1.start_date) as startdate,
             max(t1.apply_time) as duedate
      from (select t1.cust_id,
                   from_unixtime(unix_timestamp(t1.start_date),'yyyy-MM-dd') as start_date,--购买日期
                   from_unixtime(unix_timestamp(t2.apply_time),'yyyy-MM-dd') as apply_time,--赎回日期
                   (t2.amount/100)*datediff(substr(t2.apply_time,1,10),substr(t1.start_date,1,10)) as amount
            from ods.lfp_contract t1, ods.lfp_trade_list t2
            where t1.contract_id = t2.contract_id and t2.product_id = 'KL1H00002' and cast(t2.trade_type as int) = 2
            union all
            select t1.cust_id,
                   from_unixtime(unix_timestamp(t1.update_amount),'yyyy-MM-dd') as start_date,
                   case when substr(due_date,1,10)>'${TODAY_1d}' then '${TODAY_1d}' else substr(due_date,1,10) end as apply_time,
                   (t1.amount/100)*datediff(case when substr(due_date,1,10)>'${TODAY_1d}' then '${TODAY_1d}' else substr(due_date,1,10) end,substr(update_amount,1,10) ) as amount
            from ods.lfp_contract t1
            where amount>0) t1,
            hds.nuc_ecusr t2
      where cast(t1.cust_id as int)=cast(t2.userseq as int)
      group by nvl(t2.userid,t2.mobilephone)
      ) t
group by mobile_num;
EOF
tmp=$?
NOWTIME=$(date +%Y-%m-%d/%H:%M:%S)
if test $tmp -eq 0; then
echo $NOWTIME'------1--------余额理财var_sharebal_day 创建成功'$tmp >> logs/logs_cdt_datasource_result
else
echo $NOWTIME'------1--------余额理财var_sharebal_day 创建失败'$tmp >> logs/logs_cdt_datasource_result
fi



echo '------------------还款金额稳定性credit_hkwdx-----------------'
sudo -u hdfs hive <<EOF
drop table creditck.var_credit_hkwdx;
create table creditck.var_credit_hkwdx
as
select t1.mobile_num,sqrt((1/(min(hk_months)-1))*sum((hk_sum-hk_yj)*(hk_sum-hk_yj)))/min(hk_yj) as credit_hkwdx
from(
select mobile_num,sum(total_am/100) as hk_sum
from hdm.f_fact_trans_success_new_details
where tcat_lv4_name='信用卡还款' and ymd>='${DATE_12M}' and ymd<'${DATE_0M}'
group by mobile_num,from_unixtime(unix_timestamp(data_date),'yyyyMM')) t1,
(select mobile_num,sum(total_am/100)/count(distinct from_unixtime(unix_timestamp(data_date),'yyyyMM')) as hk_yj,
count(distinct from_unixtime(unix_timestamp(data_date),'yyyyMM')) as hk_months
from hdm.f_fact_trans_success_new_details
where tcat_lv4_name='信用卡还款' and ymd>='${DATE_12M}' and ymd<'${DATE_0M}'
group by mobile_num
having count(distinct from_unixtime(unix_timestamp(data_date),'yyyyMM'))>=2) t2
where t1.mobile_num=t2.mobile_num
group by t1.mobile_num;
EOF
tmp=$?
NOWTIME=$(date +%Y-%m-%d/%H:%M:%S)
if test $tmp -eq 0; then 
echo $NOWTIME'------1--------var_credit_hkwdx创建成功'$tmp >> logs/logs_cdt_datasource_result
else
echo $NOWTIME'------1--------var_credit_hkwdx创建失败'$tmp >> logs/logs_cdt_datasource_result
fi



echo '-------------------转账还款充值电商等交易信息-----------------------'
sudo -u hdfs hive <<EOF
drop table creditck.var_trans;
create table creditck.var_trans
as
select mobile_num,
       hkamount/hknum as credit_bjhk,--笔均还款
       hkmonths_num as credit_hkmonth,--还款分布月份数
       hkamount/hkmonths_num as  credit_yjhk,--月均还款
       datediff('${TODAY_1d}',hklast_date) as credit_hklast,--最后还款日
       hkcnt_6m as credit_hkcs, --最近半年还款次数
       zzincdnum as debit_in,--转账转入卡张数
       zzoutcdnum as debit_out,--转账转出卡张数
       zzamount,--转账总金额
       zznum,--转账笔数
       zzamount/zznum as debit_bjzz,--笔均转账金额
       zzmonths_num as debit_zzmonth,--转账分布月份数
       zzamount/zzmonths_num debit_yjzz,  --月均转账金额
       phonecharge_sum/phonecharge_cnt as phonecharge_permonth,--月均充值金额
       pubpay_sum,--总金额
       pubpay_count,--总笔数
       pubpay_sum/pubpay_count as pubpay_ave,--笔均金额
       ebus_sum,--电商消费金额
       ebus_count,--电商消费笔数
       virtual_sum,--虚拟交易
       jy_time --交易月份数
from (select mobile_num,
       min(case when tcat_lv4_name='信用卡还款' then data_date end) as hkfirst_date,
       max(case when tcat_lv4_name='信用卡还款' then data_date end) as hklast_date,
       count(case when tcat_lv4_name = '信用卡还款' then 1 end) as hknum,
       count(case when tcat_lv4_name = '信用卡还款' and ymd>='${DATE_6M}' then 1 end) as hkcnt_6m,
       sum(case when tcat_lv4_name = '信用卡还款' then total_am/100 end) as hkamount,
       count(distinct case when tcat_lv4_name = '信用卡还款' then substr(ymd,1,6) end) as hkmonths_num,
       count(distinct case when tcat_lv3_name='转账汇款' then (case when outcdno_bin is not null then outcdno else null end) end) as zzoutcdnum,
       count(distinct case when tcat_lv3_name='转账汇款' then (case when incdno_bin is not null then incdno else null end) end) as zzincdnum,
       count(case when tcat_lv3_name='转账汇款' then 1 end) as zznum,
       sum(case when tcat_lv3_name='转账汇款' then total_am/100 end) as zzamount,
       count(distinct case when tcat_lv3_name='转账汇款' then substr(ymd,1,6) end) as zzmonths_num,
       sum(case when mobile_num=incdno and (tcat_lv3_name='话费充值' or (tcat_lv3_name='公缴类' and agent_name like '%手机%')) then total_am/100 end) as phonecharge_sum,
       count(distinct case when mobile_num=incdno and (tcat_lv3_name='话费充值' or (tcat_lv3_name='公缴类' and agent_name like '%手机%')) then substr(ymd,1,6) end) as phonecharge_cnt,
       sum(case when tcat_lv3_name='公缴类' and agent_name not like '%手机%' then total_am/100 end) as pubpay_sum,
       count(case when tcat_lv3_name='公缴类' and agent_name not like '%手机%' then 1 end) as pubpay_count,
       sum(case when tcat_lv3_name='第三方支付账单' or agent_no='3H070001201' then total_am/100 end) as ebus_sum,
       count(case when tcat_lv3_name='第三方支付账单' or agent_no='3H070001201' then 1 end) as ebus_count,
       sum(case when tcat_lv3_name like '%点卡%' then total_am/100 end) as virtual_sum,
       count(distinct substr(ymd,1,6)) as jy_time
from hdm.f_fact_trans_success_new_details where length(mobile_num)=11 and ymd>='${TODAY_2Y}' group by mobile_num) t;
EOF
tmp=$?
NOWTIME=$(date +%Y-%m-%d/%H:%M:%S)
if test $tmp -eq 0; then
echo $NOWTIME'-------1-------转账还款充值电商等交易信息 var_trans-'${TODAY_0D}'创建成功'$tmp >> logs/logs_cdt_datasource_result
echo 'cdt_datasource_result2_1------ok------'${TODAY_0D}
else
echo $NOWTIME'-------1-------转账还款充值电商等交易信息 var_trans-'${TODAY_0D}'创建失败'$tmp >> logs/logs_cdt_datasource_result
fi
