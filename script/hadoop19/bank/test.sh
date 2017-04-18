#!/bin/bash
TODAY_1D=$(date --date='1 day ago' +%Y%m%d)
TODAY_0D=$(date +%Y%m%d)
TODAY_YM=$(date +%Y%m)

echo "'${TODAY_1D}'---${TODAY_0D}"
echo $(date)

time1=$(date +%s -d'2016-11-11 17:02:00')
time11=$(date +%s -d'2016-11-10 17:02:00')

echo ${time1}

time2=$(date +%s)

echo ${time2}

time_add1=$((time1-time11))
time_add=$((time1-time2))
echo $((time1-time11)) $(((time1-time11)/60/60))
echo $((time1-time2)) $(((time1-time2)/60))
echo $((12+1*24*60*60))
sleep ${time_add}s
echo 'end'
exit;

hive <<EOF
set hive.auto.convert.join=false;
set mapreduce.reduce.shuffle.input.buffer.percent=0.1;
set mapreduce.reduce.shuffle.parallelcopies=9;
set hive.map.aggr=true;
set hive.groupby.skewindata=true;
set hive.exec.reducers.bytes.per.reducer=1000000000;
set hive.optimize.skewjoin=false;
set hive.skewjoin.key=2000000;

--匹配拉卡拉交易流水
drop table zhengxin.gd_trans_1031;
create table zhengxin.gd_trans_1031
as
select  t4.*,t3.ymd_max
from
zhengxin.gd_apply_ymd_1031 t3,
edm.f_fact_trans_success_new t4
where t3.mobile=t4.mobile_num
and t3.ymd_max>=t4.ymd
and t4.ymd>='20120101';
EOF
