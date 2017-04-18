#!/bin/bash
DATE_0m=$(date +%Y-%m-)01
#DATE_0M=$(date +%Y%m)01
DATE_0M=$(date +%Y%m)01
DATE_1D=$(date --date='1 day ago'  +%Y%m%d)01
DATE_3D=$(date --date='3 day ago'  +%Y%m%d)01
DATE_7D=$(date --date='7 day ago'  +%Y%m%d)01
DATE_15D=$(date --date='15 day ago'  +%Y%m%d)01
DATE_1M_1D=$(date --date='1 month 9 day ago'  +%Y%m%d)01
DATE_1M_3D=$(date --date='1 month 11 day ago'   +%Y%m%d)01
DATE_1M_7D=$(date --date='1 month 15 day ago'   +%Y%m%d)01
DATE_1M_15D=$(date --date='1 month 23 day ago'   +%Y%m%d)01
DATE_2M_1D=$(date --date='2 month 4 day ago'  +%Y%m%d)01
DATE_2M_3D=$(date --date='2 month 6 day ago'   +%Y%m%d)01
DATE_2M_7D=$(date --date='2 month 10 day ago'   +%Y%m%d)01
DATE_2M_15D=$(date --date='2 month 18 day ago'   +%Y%m%d)01
DATE_3M_1D=$(date --date='3 month 7 day ago'  +%Y%m%d)01
DATE_3M_3D=$(date --date='3 month 9 day ago'   +%Y%m%d)01
DATE_3M_7D=$(date --date='3 month 13 day ago'   +%Y%m%d)01
DATE_3M_15D=$(date --date='3 month 21 day ago'   +%Y%m%d)01
DATE_4M_1D=$(date --date='4 month 3 day ago'  +%Y%m%d)01
DATE_4M_3D=$(date --date='4 month 5 day ago'   +%Y%m%d)01
DATE_4M_7D=$(date --date='4 month 9 day ago'   +%Y%m%d)01
DATE_4M_15D=$(date --date='4 month 17 day ago'   +%Y%m%d)01
DATE_5M_1D=$(date --date='5 month 5 day ago'  +%Y%m%d)01
DATE_5M_3D=$(date --date='5 month 7 day ago'   +%Y%m%d)01
DATE_5M_7D=$(date --date='5 month 11 day ago'   +%Y%m%d)01
DATE_5M_15D=$(date --date='5 month 19 day ago'   +%Y%m%d)01
MON_0M=$(date +%Y%m)
MON_1M=$(date --date='1 month ago' +%Y%m)
MONTH_1M=$(date --date='1 month ago' +%Y%m)
MON_2M=$(date --date='2 month ago' +%Y%m)
MONTH_2M=$(date --date='2 month ago' +%Y%m)


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
#DATE_2Y=$(date --date='2 year ago' +%Y%m)01
DATE_2Y=$(date --date='1 month ago' +%Y%m)01
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


echo '---------------------创建近2年内商户每个月的工作日交易情况表-------------------'
sudo -u hdfs hive <<EOF
create table if not exists merdm.dm_trans_workday_t1_mon( 
   mer_id  string,  
   cnt_m  bigint, 
   amt_m  bigint, 
   days_m  bigint, 
   cards_m  bigint, 
   max_amt_m  bigint, 
   m09_amt_m  double, 
   m08_amt_m  double, 
   m07_amt_m  double, 
   m06_amt_m  double, 
   m05_amt_m  double, 
   m04_amt_m  double, 
   m03_amt_m  double, 
   m02_amt_m  double, 
   m01_amt_m  double, 
   min_amt_m  bigint, 
   avg_amt_m  double, 
   max_ymd_m  string, 
   min_ymd_m  string, 
   cards_credit_m  bigint, 
   credit_amt_m  bigint, 
   credit_cnt_m  bigint, 
   credit_avg_amt_m  double, 
   credit_max_amt_m  bigint, 
   credit_m09_amt_m  double, 
   credit_m08_amt_m  double, 
   credit_m07_amt_m  double, 
   credit_m06_amt_m  double, 
   credit_m05_amt_m  double, 
   credit_m04_amt_m  double, 
   credit_m03_amt_m  double, 
   credit_m02_amt_m  double, 
   credit_m01_amt_m  double, 
   credit_min_amt_m  bigint, 
   cards_debit_m  bigint, 
   debit_amt_m  bigint, 
   debit_cnt_m  bigint, 
   debit_avg_amt_m  double, 
   debit_max_amt_m  bigint, 
   debit_m09_amt_m  double, 
   debit_m08_amt_m  double, 
   debit_m07_amt_m  double, 
   debit_m06_amt_m  double, 
   debit_m05_amt_m  double, 
   debit_m04_amt_m  double, 
   debit_m03_amt_m  double, 
   debit_m02_amt_m  double, 
   debit_m01_amt_m  double, 
   debit_min_amt_m  bigint, 
   nowork_amt_m  bigint, 
   nowork_cnt_m  bigint, 
   nowork_avg_amt_m  double, 
   nowork_max_amt_m  bigint, 
   nowork_m09_amt_m  double, 
   nowork_m08_amt_m  double, 
   nowork_m07_amt_m  double, 
   nowork_m06_amt_m  double, 
   nowork_m05_amt_m  double, 
   nowork_m04_amt_m  double, 
   nowork_m03_amt_m  double, 
   nowork_m02_amt_m  double, 
   nowork_m01_amt_m  double, 
   nowork_min_amt_m  bigint, 
   nowork_days_m  bigint, 
   nowork_cards_m  bigint, 
   nowork_cards_credit_m  bigint, 
   nowork_credit_amt_m  bigint, 
   nowork_credit_cnt_m  bigint, 
   nowork_credit_avg_amt_m  double, 
   nowork_credit_max_amt_m  bigint, 
   nowork_credit_m09_amt_m  double, 
   nowork_credit_m08_amt_m  double, 
   nowork_credit_m07_amt_m  double, 
   nowork_credit_m06_amt_m  double, 
   nowork_credit_m05_amt_m  double, 
   nowork_credit_m04_amt_m  double, 
   nowork_credit_m03_amt_m  double, 
   nowork_credit_m02_amt_m  double, 
   nowork_credit_m01_amt_m  double, 
   nowork_credit_min_amt_m  bigint, 
   nowork_cards_debit_m  bigint, 
   nowork_debit_amt_m  bigint, 
   nowork_debit_cnt_m  bigint, 
   nowork_debit_avg_amt_m  double, 
   nowork_debit_max_amt_m  bigint, 
   nowork_debit_m09_amt_m  double, 
   nowork_debit_m08_amt_m  double, 
   nowork_debit_m07_amt_m  double, 
   nowork_debit_m06_amt_m  double, 
   nowork_debit_m05_amt_m  double, 
   nowork_debit_m04_amt_m  double, 
   nowork_debit_m03_amt_m  double, 
   nowork_debit_m02_amt_m  double, 
   nowork_debit_m01_amt_m  double, 
   nowork_debit_min_amt_m  bigint, 
   mo_amt_m  bigint, 
   mo_cnt_m  bigint, 
   mo_avg_amt_m  double, 
   mo_max_amt_m  bigint, 
   mo_m09_amt_m  double, 
   mo_m08_amt_m  double, 
   mo_m07_amt_m  double, 
   mo_m06_amt_m  double, 
   mo_m05_amt_m  double, 
   mo_m04_amt_m  double, 
   mo_m03_amt_m  double, 
   mo_m02_amt_m  double, 
   mo_m01_amt_m  double, 
   mo_min_amt_m  bigint, 
   mo_days_m  bigint, 
   mo_cards_m  bigint, 
   mo_cards_credit_m  bigint, 
   mo_credit_amt_m  bigint, 
   mo_credit_cnt_m  bigint, 
   mo_credit_avg_amt_m  double, 
   mo_credit_max_amt_m  bigint, 
   mo_credit_m09_amt_m  double, 
   mo_credit_m08_amt_m  double, 
   mo_credit_m07_amt_m  double, 
   mo_credit_m06_amt_m  double, 
   mo_credit_m05_amt_m  double, 
   mo_credit_m04_amt_m  double, 
   mo_credit_m03_amt_m  double, 
   mo_credit_m02_amt_m  double, 
   mo_credit_m01_amt_m  double, 
   mo_credit_min_amt_m  bigint, 
   mo_cards_debit_m  bigint, 
   mo_debit_amt_m  bigint, 
   mo_debit_cnt_m  bigint, 
   mo_debit_avg_amt_m  double, 
   mo_debit_max_amt_m  bigint, 
   mo_debit_m09_amt_m  double, 
   mo_debit_m08_amt_m  double, 
   mo_debit_m07_amt_m  double, 
   mo_debit_m06_amt_m  double, 
   mo_debit_m05_amt_m  double, 
   mo_debit_m04_amt_m  double, 
   mo_debit_m03_amt_m  double, 
   mo_debit_m02_amt_m  double, 
   mo_debit_m01_amt_m  double, 
   mo_debit_min_amt_m  bigint, 
   af_amt_m  bigint, 
   af_cnt_m  bigint, 
   af_avg_amt_m  double, 
   af_max_amt_m  bigint, 
   af_m09_amt_m  double, 
   af_m08_amt_m  double, 
   af_m07_amt_m  double, 
   af_m06_amt_m  double, 
   af_m05_amt_m  double, 
   af_m04_amt_m  double, 
   af_m03_amt_m  double, 
   af_m02_amt_m  double, 
   af_m01_amt_m  double, 
   af_min_amt_m  bigint, 
   af_days_m  bigint, 
   af_cards_m  bigint, 
   af_cards_credit_m  bigint, 
   af_credit_amt_m  bigint, 
   af_credit_cnt_m  bigint, 
   af_credit_avg_amt_m  double, 
   af_credit_max_amt_m  bigint, 
   af_credit_m09_amt_m  double, 
   af_credit_m08_amt_m  double, 
   af_credit_m07_amt_m  double, 
   af_credit_m06_amt_m  double, 
   af_credit_m05_amt_m  double, 
   af_credit_m04_amt_m  double, 
   af_credit_m03_amt_m  double, 
   af_credit_m02_amt_m  double, 
   af_credit_m01_amt_m  double, 
   af_credit_min_amt_m  bigint, 
   af_cards_debit_m  bigint, 
   af_debit_amt_m  bigint, 
   af_debit_cnt_m  bigint, 
   af_debit_avg_amt_m  double, 
   af_debit_max_amt_m  bigint, 
   af_debit_m09_amt_m  double, 
   af_debit_m08_amt_m  double, 
   af_debit_m07_amt_m  double, 
   af_debit_m06_amt_m  double, 
   af_debit_m05_amt_m  double, 
   af_debit_m04_amt_m  double, 
   af_debit_m03_amt_m  double, 
   af_debit_m02_amt_m  double, 
   af_debit_m01_amt_m  double, 
   af_debit_min_amt_m  bigint, 
   ev_amt_m  bigint, 
   ev_cnt_m  bigint, 
   ev_avg_amt_m  double, 
   ev_max_amt_m  bigint, 
   ev_m09_amt_m  double, 
   ev_m08_amt_m  double, 
   ev_m07_amt_m  double, 
   ev_m06_amt_m  double, 
   ev_m05_amt_m  double, 
   ev_m04_amt_m  double, 
   ev_m03_amt_m  double, 
   ev_m02_amt_m  double, 
   ev_m01_amt_m  double, 
   ev_min_amt_m  bigint, 
   ev_days_m  bigint, 
   ev_cards_m  bigint, 
   ev_cards_credit_m  bigint, 
   ev_credit_amt_m  bigint, 
   ev_credit_cnt_m  bigint, 
   ev_credit_avg_amt_m  double, 
   ev_credit_max_amt_m  bigint, 
   ev_credit_m09_amt_m  double, 
   ev_credit_m08_amt_m  double, 
   ev_credit_m07_amt_m  double, 
   ev_credit_m06_amt_m  double, 
   ev_credit_m05_amt_m  double, 
   ev_credit_m04_amt_m  double, 
   ev_credit_m03_amt_m  double, 
   ev_credit_m02_amt_m  double, 
   ev_credit_m01_amt_m  double, 
   ev_credit_min_amt_m  bigint, 
   ev_cards_debit_m  bigint, 
   ev_debit_amt_m  bigint, 
   ev_debit_cnt_m  bigint, 
   ev_debit_avg_amt_m  double, 
   ev_debit_max_amt_m  bigint, 
   ev_debit_m09_amt_m  double, 
   ev_debit_m08_amt_m  double, 
   ev_debit_m07_amt_m  double, 
   ev_debit_m06_amt_m  double, 
   ev_debit_m05_amt_m  double, 
   ev_debit_m04_amt_m  double, 
   ev_debit_m03_amt_m  double, 
   ev_debit_m02_amt_m  double, 
   ev_debit_m01_amt_m  double, 
   ev_debit_min_amt_m  bigint, 
   mn_amt_m  bigint, 
   mn_cnt_m  bigint, 
   mn_avg_amt_m  double, 
   mn_max_amt_m  bigint, 
   mn_m09_amt_m  double, 
   mn_m08_amt_m  double, 
   mn_m07_amt_m  double, 
   mn_m06_amt_m  double, 
   mn_m05_amt_m  double, 
   mn_m04_amt_m  double, 
   mn_m03_amt_m  double, 
   mn_m02_amt_m  double, 
   mn_m01_amt_m  double, 
   mn_min_amt_m  bigint, 
   mn_days_m  bigint, 
   mn_cards_m  bigint, 
   mn_cards_credit_m  bigint, 
   mn_credit_amt_m  bigint, 
   mn_credit_cnt_m  bigint, 
   mn_credit_avg_amt_m  double, 
   mn_credit_max_amt_m  bigint, 
   mn_credit_m09_amt_m  double, 
   mn_credit_m08_amt_m  double, 
   mn_credit_m07_amt_m  double, 
   mn_credit_m06_amt_m  double, 
   mn_credit_m05_amt_m  double, 
   mn_credit_m04_amt_m  double, 
   mn_credit_m03_amt_m  double, 
   mn_credit_m02_amt_m  double, 
   mn_credit_m01_amt_m  double, 
   mn_credit_min_amt_m  bigint, 
   mn_cards_debit_m  bigint, 
   mn_debit_amt_m  bigint, 
   mn_debit_cnt_m  bigint, 
   mn_debit_avg_amt_m  double, 
   mn_debit_max_amt_m  bigint, 
   mn_debit_m09_amt_m  double, 
   mn_debit_m08_amt_m  double, 
   mn_debit_m07_amt_m  double, 
   mn_debit_m06_amt_m  double, 
   mn_debit_m05_amt_m  double, 
   mn_debit_m04_amt_m  double, 
   mn_debit_m03_amt_m  double, 
   mn_debit_m02_amt_m  double, 
   mn_debit_m01_amt_m  double, 
   mn_debit_min_amt_m  bigint, 
   no_amt_m  bigint, 
   no_cnt_m  bigint, 
   no_avg_amt_m  double, 
   no_max_amt_m  bigint, 
   no_m09_amt_m  double, 
   no_m08_amt_m  double, 
   no_m07_amt_m  double, 
   no_m06_amt_m  double, 
   no_m05_amt_m  double, 
   no_m04_amt_m  double, 
   no_m03_amt_m  double, 
   no_m02_amt_m  double, 
   no_m01_amt_m  double, 
   no_min_amt_m  bigint, 
   no_days_m  bigint, 
   no_cards_m  bigint, 
   no_cards_credit_m  bigint, 
   no_credit_amt_m  bigint, 
   no_credit_cnt_m  bigint, 
   no_credit_avg_amt_m  double, 
   no_credit_max_amt_m  bigint, 
   no_credit_m09_amt_m  double, 
   no_credit_m08_amt_m  double, 
   no_credit_m07_amt_m  double, 
   no_credit_m06_amt_m  double, 
   no_credit_m05_amt_m  double, 
   no_credit_m04_amt_m  double, 
   no_credit_m03_amt_m  double, 
   no_credit_m02_amt_m  double, 
   no_credit_m01_amt_m  double, 
   no_credit_min_amt_m  bigint, 
   no_cards_debit_m  bigint, 
   no_debit_amt_m  bigint, 
   no_debit_cnt_m  bigint, 
   no_debit_avg_amt_m  double, 
   no_debit_max_amt_m  bigint, 
   no_debit_m09_amt_m  double, 
   no_debit_m08_amt_m  double, 
   no_debit_m07_amt_m  double, 
   no_debit_m06_amt_m  double, 
   no_debit_m05_amt_m  double, 
   no_debit_m04_amt_m  double, 
   no_debit_m03_amt_m  double, 
   no_debit_m02_amt_m  double, 
   no_debit_m01_amt_m  double, 
   no_debit_min_amt_m  bigint) 
   partitioned by (orgid  string, mon string);

EOF
tmp=$?
NOWTIME=$(date +%Y-%m-%d/%H:%M:%S)
if test $tmp -eq 0; then
echo $NOWTIME'--------------创建近2年内商户每个月的工作日交易情况表-----------'$tmp 
else
echo $NOWTIME'--------------创建近2年内商户每个月的工作日交易情况表-----------'$tmp 
fi



echo '---------------近2年内商户每个月的工作日交易情况-------------'

#while true 
#do 

sudo -u hdfs hive <<EOF 

set hive.exec.dynamic.partition.mode=nonstrict; 
set hive.exec.dynamic.partition=true;
set hive.exec.max.dynamic.partitions=5000;
set hive.exec.max.dynamic.partitions.pernode=5000;
set hive.exec.parallel=true;
set hive.exec.parallel.thread.number=5;
set hive.exec.max.created.files=655350;
set mapreduce.map.memory.mb=6000;
set mapreduce.reduce.memory.mb=6000;
set mapreduce.map.java.opts=-Djava.net.preferIPv4Stack=true -Xms4000m -Xmx4000m;


insert overwrite table merdm.dm_trans_workday_t1_mon partition (orgid,mon)
select mer_id,           
           count(*) as cnt_m,
           sum(trans_amt) as amt_m,
           count(distinct ymd) as days_m, --交易天数
           count(distinct cardno) as cards_m, --卡数
           max(trans_amt) as max_amt_m,------最大交易额
           percentile(trans_amt,0.9) as m09_amt_m,------9分位交易额
           percentile(trans_amt,0.8) as m08_amt_m,------8分位交易额
           percentile(trans_amt,0.7) as m07_amt_m,------7分位交易额
           percentile(trans_amt,0.6) as m06_amt_m,------6分位交易额
           percentile(trans_amt,0.5) as m05_amt_m,------5分位交易额
           percentile(trans_amt,0.4) as m04_amt_m,------4分位交易额
           percentile(trans_amt,0.3) as m03_amt_m,------3分位交易额
           percentile(trans_amt,0.2) as m02_amt_m,------2分位交易额
           percentile(trans_amt,0.1) as m01_amt_m,------1分位交易额
           min(trans_amt) as min_amt_m, ------最小交易额
           avg(trans_amt) as avg_amt_m,------笔均交易额
           max(ymd) as max_ymd_m,------最大交易时间
           min(ymd) as min_ymd_m, ------最小交易时间 
           count(distinct case when card_kind='C' then cardno end) as cards_credit_m,  --信用卡数           
           sum(case when card_kind='C' then trans_amt end) as credit_amt_m,    -------------信用卡交易额
           count(case when card_kind='C' then trans_amt end) as credit_cnt_m,    -------------信用卡交易笔数
           avg(case when card_kind='C' then trans_amt end) as credit_avg_amt_m,------信用卡笔均交易额
           max(case when card_kind='C' then trans_amt end) as credit_max_amt_m,------信用卡最大交易额
           percentile(case when card_kind='C' then trans_amt end,0.9) as credit_m09_amt_m,------信用卡9分位交易额
           percentile(case when card_kind='C' then trans_amt end,0.8) as credit_m08_amt_m,------信用卡8分位交易额
           percentile(case when card_kind='C' then trans_amt end,0.7) as credit_m07_amt_m,------信用卡7分位交易额
           percentile(case when card_kind='C' then trans_amt end,0.6) as credit_m06_amt_m,------信用卡6分位交易额
           percentile(case when card_kind='C' then trans_amt end,0.5) as credit_m05_amt_m,------信用卡5分位交易额
           percentile(case when card_kind='C' then trans_amt end,0.4) as credit_m04_amt_m,------信用卡4分位交易额
           percentile(case when card_kind='C' then trans_amt end,0.3) as credit_m03_amt_m,------信用卡3分位交易额
           percentile(case when card_kind='C' then trans_amt end,0.2) as credit_m02_amt_m,------信用卡2分位交易额
           percentile(case when card_kind='C' then trans_amt end,0.1) as credit_m01_amt_m,------信用卡1分位交易额
           min(case when card_kind='C' then trans_amt end) as credit_min_amt_m, ------信用卡最小交易额           
           count(distinct case when card_kind='D' then cardno end) as cards_debit_m,  --借记卡数           
           sum(case when card_kind='D' then trans_amt end) as debit_amt_m,       -------------借记卡交易额
           count(case when card_kind='D' then trans_amt end) as debit_cnt_m,     -------------借记卡交易笔数
           avg(case when card_kind='D' then trans_amt end) as debit_avg_amt_m,------借记卡笔均交易额
           max(case when card_kind='D' then trans_amt end) as debit_max_amt_m,------借记卡最大交易额
           percentile(case when card_kind='D' then trans_amt end,0.9) as debit_m09_amt_m,------借记卡9分位交易额
           percentile(case when card_kind='D' then trans_amt end,0.8) as debit_m08_amt_m,------借记卡8分位交易额
           percentile(case when card_kind='D' then trans_amt end,0.7) as debit_m07_amt_m,------借记卡7分位交易额
           percentile(case when card_kind='D' then trans_amt end,0.6) as debit_m06_amt_m,------借记卡6分位交易额
           percentile(case when card_kind='D' then trans_amt end,0.5) as debit_m05_amt_m,------借记卡5分位交易额
           percentile(case when card_kind='D' then trans_amt end,0.4) as debit_m04_amt_m,------借记卡4分位交易额
           percentile(case when card_kind='D' then trans_amt end,0.3) as debit_m03_amt_m,------借记卡3分位交易额
           percentile(case when card_kind='D' then trans_amt end,0.2) as debit_m02_amt_m,------借记卡2分位交易额
           percentile(case when card_kind='D' then trans_amt end,0.1) as debit_m01_amt_m,------借记卡1分位交易额           
           min(case when card_kind='D' then trans_amt end) as debit_min_amt_m, ------借记卡最小交易额  
          
           sum(case when (from_unixtime(unix_timestamp(txn_data),'HHmmss')>'220000' or from_unixtime(unix_timestamp(txn_data),'HHmmss')<'090000') then trans_amt end) as nowork_amt_m,  -----非工作时间交易金额
           count(case when (from_unixtime(unix_timestamp(txn_data),'HHmmss')>'220000' or from_unixtime(unix_timestamp(txn_data),'HHmmss')<'090000') then trans_amt end) as nowork_cnt_m,  -----非工作时间交易笔数
           avg(case when (from_unixtime(unix_timestamp(txn_data),'HHmmss')>'220000' or from_unixtime(unix_timestamp(txn_data),'HHmmss')<'090000') then trans_amt end) as nowork_avg_amt_m,  -----非工作时间笔均交易金额
           max(case when (from_unixtime(unix_timestamp(txn_data),'HHmmss')>'220000' or from_unixtime(unix_timestamp(txn_data),'HHmmss')<'090000') then trans_amt end) as nowork_max_amt_m,  -----非工作时间最大交易金额
           percentile(case when (from_unixtime(unix_timestamp(txn_data),'HHmmss')>'220000' or from_unixtime(unix_timestamp(txn_data),'HHmmss')<'090000') then trans_amt end,0.9) as nowork_m09_amt_m,------非工作时间9分位交易额
           percentile(case when (from_unixtime(unix_timestamp(txn_data),'HHmmss')>'220000' or from_unixtime(unix_timestamp(txn_data),'HHmmss')<'090000') then trans_amt end,0.8) as nowork_m08_amt_m,------非工作时间8分位交易额
           percentile(case when (from_unixtime(unix_timestamp(txn_data),'HHmmss')>'220000' or from_unixtime(unix_timestamp(txn_data),'HHmmss')<'090000') then trans_amt end,0.7) as nowork_m07_amt_m,------非工作时间7分位交易额
           percentile(case when (from_unixtime(unix_timestamp(txn_data),'HHmmss')>'220000' or from_unixtime(unix_timestamp(txn_data),'HHmmss')<'090000') then trans_amt end,0.6) as nowork_m06_amt_m,------非工作时间6分位交易额
           percentile(case when (from_unixtime(unix_timestamp(txn_data),'HHmmss')>'220000' or from_unixtime(unix_timestamp(txn_data),'HHmmss')<'090000') then trans_amt end,0.5) as nowork_m05_amt_m,------非工作时间5分位交易额
           percentile(case when (from_unixtime(unix_timestamp(txn_data),'HHmmss')>'220000' or from_unixtime(unix_timestamp(txn_data),'HHmmss')<'090000') then trans_amt end,0.4) as nowork_m04_amt_m,------非工作时间4分位交易额
           percentile(case when (from_unixtime(unix_timestamp(txn_data),'HHmmss')>'220000' or from_unixtime(unix_timestamp(txn_data),'HHmmss')<'090000') then trans_amt end,0.3) as nowork_m03_amt_m,------非工作时间3分位交易额
           percentile(case when (from_unixtime(unix_timestamp(txn_data),'HHmmss')>'220000' or from_unixtime(unix_timestamp(txn_data),'HHmmss')<'090000') then trans_amt end,0.2) as nowork_m02_amt_m,------非工作时间2分位交易额
           percentile(case when (from_unixtime(unix_timestamp(txn_data),'HHmmss')>'220000' or from_unixtime(unix_timestamp(txn_data),'HHmmss')<'090000') then trans_amt end,0.1) as nowork_m01_amt_m,------非工作时间1分位交易额
           min(case when (from_unixtime(unix_timestamp(txn_data),'HHmmss')>'220000' or from_unixtime(unix_timestamp(txn_data),'HHmmss')<'090000') then trans_amt end) as nowork_min_amt_m,  -----非工作时间最小交易金额
           count(distinct case when (from_unixtime(unix_timestamp(txn_data),'HHmmss')>'220000' or from_unixtime(unix_timestamp(txn_data),'HHmmss')<'090000') then ymd end) as nowork_days_m, --非工作时间交易天数
           count(distinct case when (from_unixtime(unix_timestamp(txn_data),'HHmmss')>'220000' or from_unixtime(unix_timestamp(txn_data),'HHmmss')<'090000') then cardno end) as nowork_cards_m, --非工作时间交易卡数
           count(distinct case when card_kind='C' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>'220000' or from_unixtime(unix_timestamp(txn_data),'HHmmss')<'090000') then cardno end) as nowork_cards_credit_m,  --非工作时间信用卡数  
           sum(case when card_kind='C' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>'220000' or from_unixtime(unix_timestamp(txn_data),'HHmmss')<'090000') then trans_amt end) as nowork_credit_amt_m,    -------------非工作时间信用卡交易额
           count(case when card_kind='C' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>'220000' or from_unixtime(unix_timestamp(txn_data),'HHmmss')<'090000') then trans_amt end) as nowork_credit_cnt_m,    -------------非工作时间信用卡交易笔数
           avg(case when card_kind='C' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>'220000' or from_unixtime(unix_timestamp(txn_data),'HHmmss')<'090000') then trans_amt end) as nowork_credit_avg_amt_m,------非工作时间信用卡笔均交易额
           max(case when card_kind='C' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>'220000' or from_unixtime(unix_timestamp(txn_data),'HHmmss')<'090000') then trans_amt end) as nowork_credit_max_amt_m,------非工作时间信用卡最大交易额
           percentile(case when card_kind='C' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>'220000' or from_unixtime(unix_timestamp(txn_data),'HHmmss')<'090000') then trans_amt end,0.9) as nowork_credit_m09_amt_m,------非工作时间信用卡9分位交易额
           percentile(case when card_kind='C' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>'220000' or from_unixtime(unix_timestamp(txn_data),'HHmmss')<'090000') then trans_amt end,0.8) as nowork_credit_m08_amt_m,------非工作时间信用卡8分位交易额
           percentile(case when card_kind='C' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>'220000' or from_unixtime(unix_timestamp(txn_data),'HHmmss')<'090000') then trans_amt end,0.7) as nowork_credit_m07_amt_m,------非工作时间信用卡7分位交易额
           percentile(case when card_kind='C' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>'220000' or from_unixtime(unix_timestamp(txn_data),'HHmmss')<'090000') then trans_amt end,0.6) as nowork_credit_m06_amt_m,------非工作时间信用卡6分位交易额
           percentile(case when card_kind='C' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>'220000' or from_unixtime(unix_timestamp(txn_data),'HHmmss')<'090000') then trans_amt end,0.5) as nowork_credit_m05_amt_m,------非工作时间信用卡5分位交易额
           percentile(case when card_kind='C' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>'220000' or from_unixtime(unix_timestamp(txn_data),'HHmmss')<'090000') then trans_amt end,0.4) as nowork_credit_m04_amt_m,------非工作时间信用卡4分位交易额
           percentile(case when card_kind='C' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>'220000' or from_unixtime(unix_timestamp(txn_data),'HHmmss')<'090000') then trans_amt end,0.3) as nowork_credit_m03_amt_m,------非工作时间信用卡3分位交易额
           percentile(case when card_kind='C' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>'220000' or from_unixtime(unix_timestamp(txn_data),'HHmmss')<'090000') then trans_amt end,0.2) as nowork_credit_m02_amt_m,------非工作时间信用卡2分位交易额
           percentile(case when card_kind='C' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>'220000' or from_unixtime(unix_timestamp(txn_data),'HHmmss')<'090000') then trans_amt end,0.1) as nowork_credit_m01_amt_m,------非工作时间信用卡1分位交易额
           min(case when card_kind='C' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>'220000' or from_unixtime(unix_timestamp(txn_data),'HHmmss')<'090000') then trans_amt end) as nowork_credit_min_amt_m, ------非工作时间信用卡最小交易额           
           count(distinct case when card_kind='D' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>'220000' or from_unixtime(unix_timestamp(txn_data),'HHmmss')<'090000') then cardno end) as nowork_cards_debit_m,  --非工作时间借记卡数           
           sum(case when card_kind='D' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>'220000' or from_unixtime(unix_timestamp(txn_data),'HHmmss')<'090000') then trans_amt end) as nowork_debit_amt_m,       -------------非工作时间借记卡交易额
           count(case when card_kind='D' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>'220000' or from_unixtime(unix_timestamp(txn_data),'HHmmss')<'090000') then trans_amt end) as nowork_debit_cnt_m,     -------------非工作时间借记卡交易笔数
           avg(case when card_kind='D' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>'220000' or from_unixtime(unix_timestamp(txn_data),'HHmmss')<'090000') then trans_amt end) as nowork_debit_avg_amt_m,------非工作时间借记卡笔均交易额 
           max(case when card_kind='D' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>'220000' or from_unixtime(unix_timestamp(txn_data),'HHmmss')<'090000') then trans_amt end) as nowork_debit_max_amt_m,------非工作时间借记卡最大交易额
           percentile(case when card_kind='D' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>'220000' or from_unixtime(unix_timestamp(txn_data),'HHmmss')<'090000') then trans_amt end,0.9) as nowork_debit_m09_amt_m,------非工作时间借记卡9分位交易额
           percentile(case when card_kind='D' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>'220000' or from_unixtime(unix_timestamp(txn_data),'HHmmss')<'090000') then trans_amt end,0.8) as nowork_debit_m08_amt_m,------非工作时间借记卡8分位交易额
           percentile(case when card_kind='D' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>'220000' or from_unixtime(unix_timestamp(txn_data),'HHmmss')<'090000') then trans_amt end,0.7) as nowork_debit_m07_amt_m,------非工作时间借记卡7分位交易额
           percentile(case when card_kind='D' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>'220000' or from_unixtime(unix_timestamp(txn_data),'HHmmss')<'090000') then trans_amt end,0.6) as nowork_debit_m06_amt_m,------非工作时间借记卡6分位交易额
           percentile(case when card_kind='D' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>'220000' or from_unixtime(unix_timestamp(txn_data),'HHmmss')<'090000') then trans_amt end,0.5) as nowork_debit_m05_amt_m,------非工作时间借记卡5分位交易额
           percentile(case when card_kind='D' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>'220000' or from_unixtime(unix_timestamp(txn_data),'HHmmss')<'090000') then trans_amt end,0.4) as nowork_debit_m04_amt_m,------非工作时间借记卡4分位交易额
           percentile(case when card_kind='D' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>'220000' or from_unixtime(unix_timestamp(txn_data),'HHmmss')<'090000') then trans_amt end,0.3) as nowork_debit_m03_amt_m,------非工作时间借记卡3分位交易额
           percentile(case when card_kind='D' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>'220000' or from_unixtime(unix_timestamp(txn_data),'HHmmss')<'090000') then trans_amt end,0.2) as nowork_debit_m02_amt_m,------非工作时间借记卡2分位交易额
           percentile(case when card_kind='D' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>'220000' or from_unixtime(unix_timestamp(txn_data),'HHmmss')<'090000') then trans_amt end,0.1) as nowork_debit_m01_amt_m,------非工作时间借记卡1分位交易额
           min(case when card_kind='D' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>'220000' or from_unixtime(unix_timestamp(txn_data),'HHmmss')<'090000') then trans_amt end) as nowork_debit_min_amt_m, ------非工作时间借记卡最小交易额 
                  
           sum(case when (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='060000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'120000') then trans_amt end) as mo_amt_m,  -----6:12时间交易金额
           count(case when (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='060000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'120000') then trans_amt end) as mo_cnt_m,  -----6:12时间交易笔数
           avg(case when (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='060000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'120000') then trans_amt end) as mo_avg_amt_m,  -----6:12时间笔均交易金额           
           max(case when (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='060000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'120000') then trans_amt end) as mo_max_amt_m,  -----6:12时间最大交易金额
           percentile(case when (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='060000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'120000') then trans_amt end,0.9) as mo_m09_amt_m,------6:12时间9分位交易额
           percentile(case when (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='060000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'120000') then trans_amt end,0.8) as mo_m08_amt_m,------6:12时间8分位交易额
           percentile(case when (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='060000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'120000') then trans_amt end,0.7) as mo_m07_amt_m,------6:12时间7分位交易额
           percentile(case when (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='060000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'120000') then trans_amt end,0.6) as mo_m06_amt_m,------6:12时间6分位交易额
           percentile(case when (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='060000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'120000') then trans_amt end,0.5) as mo_m05_amt_m,------6:12时间5分位交易额
           percentile(case when (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='060000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'120000') then trans_amt end,0.4) as mo_m04_amt_m,------6:12时间4分位交易额
           percentile(case when (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='060000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'120000') then trans_amt end,0.3) as mo_m03_amt_m,------6:12时间3分位交易额
           percentile(case when (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='060000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'120000') then trans_amt end,0.2) as mo_m02_amt_m,------6:12时间2分位交易额
           percentile(case when (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='060000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'120000') then trans_amt end,0.1) as mo_m01_amt_m,------6:12时间1分位交易额           
           min(case when (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='060000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'120000') then trans_amt end) as mo_min_amt_m,  -----6:12时间最小交易金额
           count(distinct case when (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='060000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'120000') then ymd end) as mo_days_m, --6:12时间交易天数
           count(distinct case when (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='060000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'120000') then cardno end) as mo_cards_m, --6:12时间交易卡数           
           count(distinct case when card_kind='C' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='060000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'120000') then cardno end) as mo_cards_credit_m,  --6:12时间信用卡数           
           sum(case when card_kind='C' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='060000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'120000')  then trans_amt end) as mo_credit_amt_m,    -------------6:12时间信用卡交易额
           count(case when card_kind='C' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='060000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'120000')  then trans_amt end) as mo_credit_cnt_m,    -------------6:12时间信用卡交易笔数
           avg(case when card_kind='C' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='060000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'120000')  then trans_amt end) as mo_credit_avg_amt_m,------6:12时间信用卡笔均交易额
           max(case when card_kind='C' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='060000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'120000')  then trans_amt end) as mo_credit_max_amt_m,------6:12时间信用卡最大交易额
           percentile(case when card_kind='C' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='060000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'120000')  then trans_amt end,0.9) as mo_credit_m09_amt_m,------6:12时间信用卡9分位交易额
           percentile(case when card_kind='C' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='060000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'120000')  then trans_amt end,0.8) as mo_credit_m08_amt_m,------6:12时间信用卡8分位交易额
           percentile(case when card_kind='C' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='060000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'120000')  then trans_amt end,0.7) as mo_credit_m07_amt_m,------6:12时间信用卡7分位交易额
           percentile(case when card_kind='C' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='060000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'120000')  then trans_amt end,0.6) as mo_credit_m06_amt_m,------6:12时间信用卡6分位交易额
           percentile(case when card_kind='C' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='060000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'120000')  then trans_amt end,0.5) as mo_credit_m05_amt_m,------6:12时间信用卡5分位交易额
           percentile(case when card_kind='C' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='060000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'120000')  then trans_amt end,0.4) as mo_credit_m04_amt_m,------6:12时间信用卡4分位交易额
           percentile(case when card_kind='C' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='060000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'120000')  then trans_amt end,0.3) as mo_credit_m03_amt_m,------6:12时间信用卡3分位交易额
           percentile(case when card_kind='C' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='060000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'120000')  then trans_amt end,0.2) as mo_credit_m02_amt_m,------6:12时间信用卡2分位交易额
           percentile(case when card_kind='C' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='060000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'120000')  then trans_amt end,0.1) as mo_credit_m01_amt_m,------6:12时间信用卡1分位交易额
           min(case when card_kind='C' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='060000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'120000')  then trans_amt end) as mo_credit_min_amt_m, ------6:12时间信用卡最小交易额          
           count(distinct case when card_kind='D' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='060000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'120000')  then cardno end) as mo_cards_debit_m,  --6:12时间借记卡数           
           sum(case when card_kind='D' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='060000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'120000')  then trans_amt end) as mo_debit_amt_m,       -------------6:12时间借记卡交易额
           count(case when card_kind='D' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='060000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'120000')  then trans_amt end) as mo_debit_cnt_m,     -------------6:12时间借记卡交易笔数
           avg(case when card_kind='D' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='060000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'120000')  then trans_amt end) as mo_debit_avg_amt_m,------6:12时间借记卡笔均交易额 
           max(case when card_kind='D' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='060000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'120000')  then trans_amt end) as mo_debit_max_amt_m,------6:12时间借记卡最大交易额
           percentile(case when card_kind='D' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='060000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'120000')  then trans_amt end,0.9) as mo_debit_m09_amt_m,------6:12时间借记卡9分位交易额
           percentile(case when card_kind='D' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='060000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'120000')  then trans_amt end,0.8) as mo_debit_m08_amt_m,------6:12时间借记卡8分位交易额
           percentile(case when card_kind='D' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='060000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'120000')  then trans_amt end,0.7) as mo_debit_m07_amt_m,------6:12时间借记卡7分位交易额
           percentile(case when card_kind='D' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='060000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'120000')  then trans_amt end,0.6) as mo_debit_m06_amt_m,------6:12时间借记卡6分位交易额
           percentile(case when card_kind='D' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='060000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'120000')  then trans_amt end,0.5) as mo_debit_m05_amt_m,------6:12时间借记卡5分位交易额
           percentile(case when card_kind='D' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='060000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'120000')  then trans_amt end,0.4) as mo_debit_m04_amt_m,------6:12时间借记卡4分位交易额
           percentile(case when card_kind='D' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='060000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'120000')  then trans_amt end,0.3) as mo_debit_m03_amt_m,------6:12时间借记卡3分位交易额
           percentile(case when card_kind='D' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='060000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'120000')  then trans_amt end,0.2) as mo_debit_m02_amt_m,------6:12时间借记卡2分位交易额
           percentile(case when card_kind='D' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='060000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'120000')  then trans_amt end,0.1) as mo_debit_m01_amt_m,------6:12时间借记卡1分位交易额
           min(case when card_kind='D' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='060000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'120000')  then trans_amt end) as mo_debit_min_amt_m, ------6:12时间借记卡最小交易额 
           
           sum(case when (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='120000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'180000') then trans_amt end) as af_amt_m,  -----12:18时间交易金额
           count(case when (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='120000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'180000') then trans_amt end) as af_cnt_m,  -----12:18时间交易笔数
           avg(case when (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='120000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'180000') then trans_amt end) as af_avg_amt_m,  -----12:18时间笔均交易金额           
           max(case when (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='120000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'180000') then trans_amt end) as af_max_amt_m,  -----12:18时间最大交易金额
           percentile(case when (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='120000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'180000') then trans_amt end,0.9) as af_m09_amt_m,------12:18时间9分位交易额
           percentile(case when (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='120000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'180000') then trans_amt end,0.8) as af_m08_amt_m,------12:18时间8分位交易额
           percentile(case when (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='120000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'180000') then trans_amt end,0.7) as af_m07_amt_m,------12:18时间7分位交易额
           percentile(case when (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='120000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'180000') then trans_amt end,0.6) as af_m06_amt_m,------12:18时间6分位交易额
           percentile(case when (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='120000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'180000') then trans_amt end,0.5) as af_m05_amt_m,------12:18时间5分位交易额
           percentile(case when (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='120000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'180000') then trans_amt end,0.4) as af_m04_amt_m,------12:18时间4分位交易额
           percentile(case when (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='120000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'180000') then trans_amt end,0.3) as af_m03_amt_m,------12:18时间3分位交易额
           percentile(case when (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='120000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'180000') then trans_amt end,0.2) as af_m02_amt_m,------12:18时间2分位交易额
           percentile(case when (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='120000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'180000') then trans_amt end,0.1) as af_m01_amt_m,------12:18时间1分位交易额           
           min(case when (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='120000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'180000') then trans_amt end) as af_min_amt_m,  -----12:18时间最小交易金额
           count(distinct case when (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='120000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'180000') then ymd end) as af_days_m, --12:18时间交易天数
           count(distinct case when (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='120000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'180000') then cardno end) as af_cards_m, --12:18时间交易卡数           
           count(distinct case when card_kind='C' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='120000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'180000') then cardno end) as af_cards_credit_m,  --12:18时间信用卡数           
           sum(case when card_kind='C' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='120000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'180000')  then trans_amt end) as af_credit_amt_m,    -------------12:18时间信用卡交易额
           count(case when card_kind='C' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='120000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'180000')  then trans_amt end) as af_credit_cnt_m,    -------------12:18时间信用卡交易笔数
           avg(case when card_kind='C' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='120000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'180000')  then trans_amt end) as af_credit_avg_amt_m,------12:18时间信用卡笔均交易额
           max(case when card_kind='C' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='120000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'180000')  then trans_amt end) as af_credit_max_amt_m,------12:18时间信用卡最大交易额
           percentile(case when card_kind='C' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='120000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'180000')  then trans_amt end,0.9) as af_credit_m09_amt_m,------12:18时间信用卡9分位交易额
           percentile(case when card_kind='C' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='120000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'180000')  then trans_amt end,0.8) as af_credit_m08_amt_m,------12:18时间信用卡8分位交易额
           percentile(case when card_kind='C' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='120000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'180000')  then trans_amt end,0.7) as af_credit_m07_amt_m,------12:18时间信用卡7分位交易额
           percentile(case when card_kind='C' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='120000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'180000')  then trans_amt end,0.6) as af_credit_m06_amt_m,------12:18时间信用卡6分位交易额
           percentile(case when card_kind='C' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='120000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'180000')  then trans_amt end,0.5) as af_credit_m05_amt_m,------12:18时间信用卡5分位交易额
           percentile(case when card_kind='C' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='120000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'180000')  then trans_amt end,0.4) as af_credit_m04_amt_m,------12:18时间信用卡4分位交易额
           percentile(case when card_kind='C' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='120000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'180000')  then trans_amt end,0.3) as af_credit_m03_amt_m,------12:18时间信用卡3分位交易额
           percentile(case when card_kind='C' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='120000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'180000')  then trans_amt end,0.2) as af_credit_m02_amt_m,------12:18时间信用卡2分位交易额
           percentile(case when card_kind='C' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='120000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'180000')  then trans_amt end,0.1) as af_credit_m01_amt_m,------12:18时间信用卡1分位交易额
           min(case when card_kind='C' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='120000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'180000')  then trans_amt end) as af_credit_min_amt_m, ------12:18时间信用卡最小交易额          
           count(distinct case when card_kind='D' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='120000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'180000')  then cardno end) as af_cards_debit_m,  --12:18时间借记卡数           
           sum(case when card_kind='D' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='120000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'180000')  then trans_amt end) as af_debit_amt_m,       -------------12:18时间借记卡交易额
           count(case when card_kind='D' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='120000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'180000')  then trans_amt end) as af_debit_cnt_m,     -------------12:18时间借记卡交易笔数
           avg(case when card_kind='D' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='120000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'180000')  then trans_amt end) as af_debit_avg_amt_m,------12:18时间借记卡笔均交易额 
           max(case when card_kind='D' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='120000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'180000')  then trans_amt end) as af_debit_max_amt_m,------12:18时间借记卡最大交易额
           percentile(case when card_kind='D' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='120000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'180000')  then trans_amt end,0.9) as af_debit_m09_amt_m,------12:18时间借记卡9分位交易额
           percentile(case when card_kind='D' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='120000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'180000')  then trans_amt end,0.8) as af_debit_m08_amt_m,------12:18时间借记卡8分位交易额
           percentile(case when card_kind='D' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='120000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'180000')  then trans_amt end,0.7) as af_debit_m07_amt_m,------12:18时间借记卡7分位交易额
           percentile(case when card_kind='D' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='120000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'180000')  then trans_amt end,0.6) as af_debit_m06_amt_m,------12:18时间借记卡6分位交易额
           percentile(case when card_kind='D' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='120000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'180000')  then trans_amt end,0.5) as af_debit_m05_amt_m,------12:18时间借记卡5分位交易额
           percentile(case when card_kind='D' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='120000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'180000')  then trans_amt end,0.4) as af_debit_m04_amt_m,------12:18时间借记卡4分位交易额
           percentile(case when card_kind='D' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='120000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'180000')  then trans_amt end,0.3) as af_debit_m03_amt_m,------12:18时间借记卡3分位交易额
           percentile(case when card_kind='D' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='120000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'180000')  then trans_amt end,0.2) as af_debit_m02_amt_m,------12:18时间借记卡2分位交易额
           percentile(case when card_kind='D' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='120000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'180000')  then trans_amt end,0.1) as af_debit_m01_amt_m,------12:18时间借记卡1分位交易额
           min(case when card_kind='D' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='120000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'180000')  then trans_amt end) as af_debit_min_amt_m, ------12:18时间借记卡最小交易额 

           sum(case when (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='180000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'240000') then trans_amt end) as ev_amt_m,  -----18:24时间交易金额
           count(case when (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='180000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'240000') then trans_amt end) as ev_cnt_m,  -----18:24时间交易笔数
           avg(case when (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='180000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'240000') then trans_amt end) as ev_avg_amt_m,  -----18:24时间笔均交易金额           
           max(case when (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='180000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'240000') then trans_amt end) as ev_max_amt_m,  -----18:24时间最大交易金额
           percentile(case when (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='180000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'240000') then trans_amt end,0.9) as ev_m09_amt_m,------18:24时间9分位交易额
           percentile(case when (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='180000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'240000') then trans_amt end,0.8) as ev_m08_amt_m,------18:24时间8分位交易额
           percentile(case when (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='180000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'240000') then trans_amt end,0.7) as ev_m07_amt_m,------18:24时间7分位交易额
           percentile(case when (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='180000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'240000') then trans_amt end,0.6) as ev_m06_amt_m,------18:24时间6分位交易额
           percentile(case when (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='180000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'240000') then trans_amt end,0.5) as ev_m05_amt_m,------18:24时间5分位交易额
           percentile(case when (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='180000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'240000') then trans_amt end,0.4) as ev_m04_amt_m,------18:24时间4分位交易额
           percentile(case when (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='180000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'240000') then trans_amt end,0.3) as ev_m03_amt_m,------18:24时间3分位交易额
           percentile(case when (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='180000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'240000') then trans_amt end,0.2) as ev_m02_amt_m,------18:24时间2分位交易额
           percentile(case when (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='180000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'240000') then trans_amt end,0.1) as ev_m01_amt_m,------18:24时间1分位交易额           
           min(case when (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='180000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'240000') then trans_amt end) as ev_min_amt_m,  -----18:24时间最小交易金额
           count(distinct case when (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='180000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'240000') then ymd end) as ev_days_m, --18:24时间交易天数
           count(distinct case when (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='180000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'240000') then cardno end) as ev_cards_m, --18:24时间交易卡数           
           count(distinct case when card_kind='C' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='180000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'240000') then cardno end) as ev_cards_credit_m,  --18:24时间信用卡数           
           sum(case when card_kind='C' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='180000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'240000')  then trans_amt end) as ev_credit_amt_m,    -------------18:24时间信用卡交易额
           count(case when card_kind='C' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='180000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'240000')  then trans_amt end) as ev_credit_cnt_m,    -------------18:24时间信用卡交易笔数
           avg(case when card_kind='C' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='180000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'240000')  then trans_amt end) as ev_credit_avg_amt_m,------18:24时间信用卡笔均交易额
           max(case when card_kind='C' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='180000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'240000')  then trans_amt end) as ev_credit_max_amt_m,------18:24时间信用卡最大交易额
           percentile(case when card_kind='C' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='180000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'240000')  then trans_amt end,0.9) as ev_credit_m09_amt_m,------18:24时间信用卡9分位交易额
           percentile(case when card_kind='C' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='180000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'240000')  then trans_amt end,0.8) as ev_credit_m08_amt_m,------18:24时间信用卡8分位交易额
           percentile(case when card_kind='C' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='180000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'240000')  then trans_amt end,0.7) as ev_credit_m07_amt_m,------18:24时间信用卡7分位交易额
           percentile(case when card_kind='C' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='180000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'240000')  then trans_amt end,0.6) as ev_credit_m06_amt_m,------18:24时间信用卡6分位交易额
           percentile(case when card_kind='C' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='180000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'240000')  then trans_amt end,0.5) as ev_credit_m05_amt_m,------18:24时间信用卡5分位交易额
           percentile(case when card_kind='C' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='180000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'240000')  then trans_amt end,0.4) as ev_credit_m04_amt_m,------18:24时间信用卡4分位交易额
           percentile(case when card_kind='C' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='180000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'240000')  then trans_amt end,0.3) as ev_credit_m03_amt_m,------18:24时间信用卡3分位交易额
           percentile(case when card_kind='C' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='180000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'240000')  then trans_amt end,0.2) as ev_credit_m02_amt_m,------18:24时间信用卡2分位交易额
           percentile(case when card_kind='C' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='180000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'240000')  then trans_amt end,0.1) as ev_credit_m01_amt_m,------18:24时间信用卡1分位交易额
           min(case when card_kind='C' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='180000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'240000')  then trans_amt end) as ev_credit_min_amt_m, ------18:24时间信用卡最小交易额          
           count(distinct case when card_kind='D' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='180000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'240000')  then cardno end) as ev_cards_debit_m,  --18:24时间借记卡数           
           sum(case when card_kind='D' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='180000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'240000')  then trans_amt end) as ev_debit_amt_m,       -------------18:24时间借记卡交易额
           count(case when card_kind='D' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='180000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'240000')  then trans_amt end) as ev_debit_cnt_m,     -------------18:24时间借记卡交易笔数
           avg(case when card_kind='D' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='180000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'240000')  then trans_amt end) as ev_debit_avg_amt_m,------18:24时间借记卡笔均交易额 
           max(case when card_kind='D' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='180000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'240000')  then trans_amt end) as ev_debit_max_amt_m,------18:24时间借记卡最大交易额
           percentile(case when card_kind='D' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='180000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'240000')  then trans_amt end,0.9) as ev_debit_m09_amt_m,------18:24时间借记卡9分位交易额
           percentile(case when card_kind='D' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='180000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'240000')  then trans_amt end,0.8) as ev_debit_m08_amt_m,------18:24时间借记卡8分位交易额
           percentile(case when card_kind='D' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='180000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'240000')  then trans_amt end,0.7) as ev_debit_m07_amt_m,------18:24时间借记卡7分位交易额
           percentile(case when card_kind='D' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='180000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'240000')  then trans_amt end,0.6) as ev_debit_m06_amt_m,------18:24时间借记卡6分位交易额
           percentile(case when card_kind='D' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='180000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'240000')  then trans_amt end,0.5) as ev_debit_m05_amt_m,------18:24时间借记卡5分位交易额
           percentile(case when card_kind='D' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='180000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'240000')  then trans_amt end,0.4) as ev_debit_m04_amt_m,------18:24时间借记卡4分位交易额
           percentile(case when card_kind='D' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='180000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'240000')  then trans_amt end,0.3) as ev_debit_m03_amt_m,------18:24时间借记卡3分位交易额
           percentile(case when card_kind='D' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='180000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'240000')  then trans_amt end,0.2) as ev_debit_m02_amt_m,------18:24时间借记卡2分位交易额
           percentile(case when card_kind='D' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='180000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'240000')  then trans_amt end,0.1) as ev_debit_m01_amt_m,------18:24时间借记卡1分位交易额
           min(case when card_kind='D' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='180000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'240000')  then trans_amt end) as ev_debit_min_amt_m, ------18:24时间借记卡最小交易额 
                     
           sum(case when (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='000000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'060000') then trans_amt end) as mn_amt_m,  -----00:06时间交易金额
           count(case when (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='000000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'060000') then trans_amt end) as mn_cnt_m,  -----00:06时间交易笔数
           avg(case when (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='000000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'060000') then trans_amt end) as mn_avg_amt_m,  -----00:06时间笔均交易金额           
           max(case when (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='000000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'060000') then trans_amt end) as mn_max_amt_m,  -----00:06时间最大交易金额
           percentile(case when (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='000000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'060000') then trans_amt end,0.9) as mn_m09_amt_m,------00:06时间9分位交易额
           percentile(case when (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='000000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'060000') then trans_amt end,0.8) as mn_m08_amt_m,------00:06时间8分位交易额
           percentile(case when (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='000000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'060000') then trans_amt end,0.7) as mn_m07_amt_m,------00:06时间7分位交易额
           percentile(case when (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='000000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'060000') then trans_amt end,0.6) as mn_m06_amt_m,------00:06时间6分位交易额
           percentile(case when (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='000000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'060000') then trans_amt end,0.5) as mn_m05_amt_m,------00:06时间5分位交易额
           percentile(case when (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='000000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'060000') then trans_amt end,0.4) as mn_m04_amt_m,------00:06时间4分位交易额
           percentile(case when (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='000000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'060000') then trans_amt end,0.3) as mn_m03_amt_m,------00:06时间3分位交易额
           percentile(case when (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='000000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'060000') then trans_amt end,0.2) as mn_m02_amt_m,------00:06时间2分位交易额
           percentile(case when (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='000000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'060000') then trans_amt end,0.1) as mn_m01_amt_m,------00:06时间1分位交易额           
           min(case when (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='000000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'060000') then trans_amt end) as mn_min_amt_m,  -----00:06时间最小交易金额
           count(distinct case when (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='000000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'060000') then ymd end) as mn_days_m, --00:06时间交易天数
           count(distinct case when (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='000000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'060000') then cardno end) as mn_cards_m, --00:06时间交易卡数           
           count(distinct case when card_kind='C' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='000000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'060000') then cardno end) as mn_cards_credit_m,  --00:06时间信用卡数           
           sum(case when card_kind='C' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='000000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'060000')  then trans_amt end) as mn_credit_amt_m,    -------------00:06时间信用卡交易额
           count(case when card_kind='C' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='000000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'060000')  then trans_amt end) as mn_credit_cnt_m,    -------------00:06时间信用卡交易笔数
           avg(case when card_kind='C' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='000000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'060000')  then trans_amt end) as mn_credit_avg_amt_m,------00:06时间信用卡笔均交易额
           max(case when card_kind='C' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='000000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'060000')  then trans_amt end) as mn_credit_max_amt_m,------00:06时间信用卡最大交易额
           percentile(case when card_kind='C' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='000000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'060000')  then trans_amt end,0.9) as mn_credit_m09_amt_m,------00:06时间信用卡9分位交易额
           percentile(case when card_kind='C' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='000000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'060000')  then trans_amt end,0.8) as mn_credit_m08_amt_m,------00:06时间信用卡8分位交易额
           percentile(case when card_kind='C' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='000000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'060000')  then trans_amt end,0.7) as mn_credit_m07_amt_m,------00:06时间信用卡7分位交易额
           percentile(case when card_kind='C' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='000000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'060000')  then trans_amt end,0.6) as mn_credit_m06_amt_m,------00:06时间信用卡6分位交易额
           percentile(case when card_kind='C' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='000000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'060000')  then trans_amt end,0.5) as mn_credit_m05_amt_m,------00:06时间信用卡5分位交易额
           percentile(case when card_kind='C' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='000000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'060000')  then trans_amt end,0.4) as mn_credit_m04_amt_m,------00:06时间信用卡4分位交易额
           percentile(case when card_kind='C' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='000000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'060000')  then trans_amt end,0.3) as mn_credit_m03_amt_m,------00:06时间信用卡3分位交易额
           percentile(case when card_kind='C' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='000000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'060000')  then trans_amt end,0.2) as mn_credit_m02_amt_m,------00:06时间信用卡2分位交易额
           percentile(case when card_kind='C' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='000000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'060000')  then trans_amt end,0.1) as mn_credit_m01_amt_m,------00:06时间信用卡1分位交易额
           min(case when card_kind='C' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='000000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'060000')  then trans_amt end) as mn_credit_min_amt_m, ------00:06时间信用卡最小交易额          
           count(distinct case when card_kind='D' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='000000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'060000')  then cardno end) as mn_cards_debit_m,  --00:06时间借记卡数           
           sum(case when card_kind='D' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='000000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'060000')  then trans_amt end) as mn_debit_amt_m,       -------------00:06时间借记卡交易额
           count(case when card_kind='D' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='000000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'060000')  then trans_amt end) as mn_debit_cnt_m,     -------------00:06时间借记卡交易笔数
           avg(case when card_kind='D' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='000000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'060000')  then trans_amt end) as mn_debit_avg_amt_m,------00:06时间借记卡笔均交易额 
           max(case when card_kind='D' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='000000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'060000')  then trans_amt end) as mn_debit_max_amt_m,------00:06时间借记卡最大交易额
           percentile(case when card_kind='D' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='000000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'060000')  then trans_amt end,0.9) as mn_debit_m09_amt_m,------00:06时间借记卡9分位交易额
           percentile(case when card_kind='D' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='000000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'060000')  then trans_amt end,0.8) as mn_debit_m08_amt_m,------00:06时间借记卡8分位交易额
           percentile(case when card_kind='D' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='000000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'060000')  then trans_amt end,0.7) as mn_debit_m07_amt_m,------00:06时间借记卡7分位交易额
           percentile(case when card_kind='D' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='000000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'060000')  then trans_amt end,0.6) as mn_debit_m06_amt_m,------00:06时间借记卡6分位交易额
           percentile(case when card_kind='D' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='000000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'060000')  then trans_amt end,0.5) as mn_debit_m05_amt_m,------00:06时间借记卡5分位交易额
           percentile(case when card_kind='D' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='000000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'060000')  then trans_amt end,0.4) as mn_debit_m04_amt_m,------00:06时间借记卡4分位交易额
           percentile(case when card_kind='D' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='000000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'060000')  then trans_amt end,0.3) as mn_debit_m03_amt_m,------00:06时间借记卡3分位交易额
           percentile(case when card_kind='D' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='000000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'060000')  then trans_amt end,0.2) as mn_debit_m02_amt_m,------00:06时间借记卡2分位交易额
           percentile(case when card_kind='D' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='000000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'060000')  then trans_amt end,0.1) as mn_debit_m01_amt_m,------00:06时间借记卡1分位交易额
           min(case when card_kind='D' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='000000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'060000')  then trans_amt end) as mn_debit_min_amt_m, ------00:06时间借记卡最小交易额 
            
           sum(case when (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='113000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'133000') then trans_amt end) as no_amt_m,  -----1130:1330午餐时间时间交易金额
           count(case when (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='113000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'133000') then trans_amt end) as no_cnt_m,  -----1130:1330午餐时间交易笔数
           avg(case when (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='113000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'133000') then trans_amt end) as no_avg_amt_m,  -----1130:1330午餐时间笔均交易金额           
           max(case when (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='113000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'133000') then trans_amt end) as no_max_amt_m,  -----1130:1330午餐时间最大交易金额
           percentile(case when (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='113000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'133000') then trans_amt end,0.9) as no_m09_amt_m,------1130:1330午餐时间9分位交易额
           percentile(case when (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='113000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'133000') then trans_amt end,0.8) as no_m08_amt_m,------1130:1330午餐时间8分位交易额
           percentile(case when (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='113000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'133000') then trans_amt end,0.7) as no_m07_amt_m,------1130:1330午餐时间7分位交易额
           percentile(case when (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='113000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'133000') then trans_amt end,0.6) as no_m06_amt_m,------1130:1330午餐时间6分位交易额
           percentile(case when (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='113000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'133000') then trans_amt end,0.5) as no_m05_amt_m,------1130:1330午餐时间5分位交易额
           percentile(case when (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='113000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'133000') then trans_amt end,0.4) as no_m04_amt_m,------1130:1330午餐时间4分位交易额
           percentile(case when (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='113000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'133000') then trans_amt end,0.3) as no_m03_amt_m,------1130:1330午餐时间3分位交易额
           percentile(case when (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='113000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'133000') then trans_amt end,0.2) as no_m02_amt_m,------1130:1330午餐时间2分位交易额
           percentile(case when (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='113000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'133000') then trans_amt end,0.1) as no_m01_amt_m,------1130:1330午餐时间1分位交易额           
           min(case when (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='113000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'133000') then trans_amt end) as no_min_amt_m,  -----1130:1330午餐时间最小交易金额
           count(distinct case when (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='113000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'133000') then ymd end) as no_days_m, --1130:1330午餐时间交易天数
           count(distinct case when (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='113000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'133000') then cardno end) as no_cards_m, --1130:1330午餐时间交易卡数           
           count(distinct case when card_kind='C' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='113000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'133000') then cardno end) as no_cards_credit_m,  --1130:1330午餐时间信用卡数           
           sum(case when card_kind='C' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='113000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'133000')  then trans_amt end) as no_credit_amt_m,    -------------1130:1330午餐时间信用卡交易额
           count(case when card_kind='C' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='113000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'133000')  then trans_amt end) as no_credit_cnt_m,    -------------1130:1330午餐时间信用卡交易笔数
           avg(case when card_kind='C' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='113000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'133000')  then trans_amt end) as no_credit_avg_amt_m,------1130:1330午餐时间信用卡笔均交易额
           max(case when card_kind='C' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='113000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'133000')  then trans_amt end) as no_credit_max_amt_m,------1130:1330午餐时间信用卡最大交易额
           percentile(case when card_kind='C' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='113000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'133000')  then trans_amt end,0.9) as no_credit_m09_amt_m,------1130:1330午餐时间信用卡9分位交易额
           percentile(case when card_kind='C' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='113000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'133000')  then trans_amt end,0.8) as no_credit_m08_amt_m,------1130:1330午餐时间信用卡8分位交易额
           percentile(case when card_kind='C' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='113000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'133000')  then trans_amt end,0.7) as no_credit_m07_amt_m,------1130:1330午餐时间信用卡7分位交易额
           percentile(case when card_kind='C' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='113000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'133000')  then trans_amt end,0.6) as no_credit_m06_amt_m,------1130:1330午餐时间信用卡6分位交易额
           percentile(case when card_kind='C' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='113000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'133000')  then trans_amt end,0.5) as no_credit_m05_amt_m,------1130:1330午餐时间信用卡5分位交易额
           percentile(case when card_kind='C' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='113000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'133000')  then trans_amt end,0.4) as no_credit_m04_amt_m,------1130:1330午餐时间信用卡4分位交易额
           percentile(case when card_kind='C' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='113000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'133000')  then trans_amt end,0.3) as no_credit_m03_amt_m,------1130:1330午餐时间信用卡3分位交易额
           percentile(case when card_kind='C' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='113000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'133000')  then trans_amt end,0.2) as no_credit_m02_amt_m,------1130:1330午餐时间信用卡2分位交易额
           percentile(case when card_kind='C' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='113000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'133000')  then trans_amt end,0.1) as no_credit_m01_amt_m,------1130:1330午餐时间信用卡1分位交易额
           min(case when card_kind='C' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='113000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'133000')  then trans_amt end) as no_credit_min_amt_m, ------1130:1330午餐时间信用卡最小交易额          
           count(distinct case when card_kind='D' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='113000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'133000')  then cardno end) as no_cards_debit_m,  --1130:1330午餐时间借记卡数           
           sum(case when card_kind='D' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='113000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'133000')  then trans_amt end) as no_debit_amt_m,       -------------1130:1330午餐时间借记卡交易额
           count(case when card_kind='D' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='113000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'133000')  then trans_amt end) as no_debit_cnt_m,     -------------1130:1330午餐时间借记卡交易笔数
           avg(case when card_kind='D' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='113000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'133000')  then trans_amt end) as no_debit_avg_amt_m,------1130:1330午餐时间借记卡笔均交易额 
           max(case when card_kind='D' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='113000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'133000')  then trans_amt end) as no_debit_max_amt_m,------1130:1330午餐时间借记卡最大交易额
           percentile(case when card_kind='D' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='113000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'133000')  then trans_amt end,0.9) as no_debit_m09_amt_m,------1130:1330午餐时间借记卡9分位交易额
           percentile(case when card_kind='D' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='113000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'133000')  then trans_amt end,0.8) as no_debit_m08_amt_m,------1130:1330午餐时间借记卡8分位交易额
           percentile(case when card_kind='D' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='113000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'133000')  then trans_amt end,0.7) as no_debit_m07_amt_m,------1130:1330午餐时间借记卡7分位交易额
           percentile(case when card_kind='D' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='113000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'133000')  then trans_amt end,0.6) as no_debit_m06_amt_m,------1130:1330午餐时间借记卡6分位交易额
           percentile(case when card_kind='D' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='113000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'133000')  then trans_amt end,0.5) as no_debit_m05_amt_m,------1130:1330午餐时间借记卡5分位交易额
           percentile(case when card_kind='D' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='113000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'133000')  then trans_amt end,0.4) as no_debit_m04_amt_m,------1130:1330午餐时间借记卡4分位交易额
           percentile(case when card_kind='D' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='113000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'133000')  then trans_amt end,0.3) as no_debit_m03_amt_m,------1130:1330午餐时间借记卡3分位交易额
           percentile(case when card_kind='D' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='113000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'133000')  then trans_amt end,0.2) as no_debit_m02_amt_m,------1130:1330午餐时间借记卡2分位交易额
           percentile(case when card_kind='D' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='113000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'133000')  then trans_amt end,0.1) as no_debit_m01_amt_m,------1130:1330午餐时间借记卡1分位交易额
           min(case when card_kind='D' and (from_unixtime(unix_timestamp(txn_data),'HHmmss')>='113000' and from_unixtime(unix_timestamp(txn_data),'HHmmss')<'133000')  then trans_amt end) as no_debit_min_amt_m, ------1130:1330午餐时间借记卡最小交易额 
  orgid,substr(ymd,1,6) as mon
    from (
      select t1.*
      from 
      (select * from hdw.lklpos_atmtxnjnl_success 
      where trans_amt>0 and ymd>='${DATE_2Y}' and ymd<'${DATE_0M}'      
      ---union all
      ---select * from hdw.wbpos_atmtxnjnl_success 
      ---where trans_amt>0 and ymd>='${DATE_2Y}' and ymd<'${DATE_0M}' 
      )t1 
      join (select * from edm.d_base_t_date where workday=1) t2 
      on substr(t1.txn_data,1,10)=substr(t2.dateid,1,10) 
    ) a 
    group by orgid,mer_id,substr(ymd,1,6);
EOF
tmp=$?
NOWTIME=$(date +%Y-%m-%d/%H:%M:%S)
if test $tmp -eq 0; then
echo $NOWTIME '--------------近2年内商户每个月工作日交易情况 创建成功--------'$tmp  
else
echo $NOWTIME '--------------近2年内商户每个月工作日交易情况 创建失败--------'$tmp  
fi 
