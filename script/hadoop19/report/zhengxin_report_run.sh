#!/bin/sh
source  /etc/profile
#输入变量
TODAY_1D=`date -d "$1 -1 days" "+%Y%m%d"`
TODAY_2D=`date -d "$1 -2 days" "+%Y%m%d"`
TODAY_7D=`date -d "$1 -7 days" "+%Y%m%d"`
TODAY_30D=`date -d "$1 -30 days" "+%Y%m%d"`
echo '$TODAY_2D' $TODAY_2D 
echo '$TODAY_7D' $TODAY_7D
echo '$TODAY_30D' $TODAY_30D

TODAY_1D_01=`date -d "$1 -1 days" "+%Y%m01"`
TODAY_1D_31=`date -d "$1 -1 days" "+%Y%m31"`
echo '$TODAY_1D_01' $TODAY_1D_01
echo '$TODAY_1D_31' $TODAY_1D_31

TODAY_1M=`date -d "$1 -1 months" "+%Y%m%d"`
echo '$TODAY_1M' $TODAY_1M 

TODAY_1M_01=`date -d "$TODAY_1M -0 days" "+%Y%m01"`
TODAY_1M_31=`date -d "$TODAY_1M -0 days" "+%Y%m31"`
echo '$TODAY_1M_01' $TODAY_1M_01
echo '$TODAY_1M_31' $TODAY_1M_31

sudo -u hdfs hive <<EOF
set hive.auto.convert.join=false; 
set mapreduce.reduce.shuffle.input.buffer.percent=0.1;
set mapreduce.reduce.shuffle.parallelcopies=9;
insert overwrite table zhengxin.zx_report_mid_data select '${TODAY_1D}' as day,'day_act_num' type,count(distinct userid) ctnum from zhengxin.accesslog where ymd = '${TODAY_1D}' ;

insert into table zhengxin.zx_report_mid_data select '${TODAY_1D}' as day,'month_act_num' type,count(distinct userid) as ctnum from zhengxin.accesslog where ymd between '${TODAY_1M_01}' and '${TODAY_1M_31}';

insert into table zhengxin.zx_report_mid_data select '${TODAY_1D}' as day,'second_stay_num' type,count(distinct userid) ctnum  from zhengxin.accesslog l
where l.userid in (
select e.userid from zhengxin.euser  e where ymd = '${TODAY_2D}' 
)
and ymd = '${TODAY_1D}' ;

insert into table zhengxin.zx_report_mid_data select '${TODAY_1D}' as day,'seven_stay_num' type,count(distinct userid) ctnum  from zhengxin.accesslog l
where l.userid in (
select e.userid from zhengxin.euser  e where ymd = '${TODAY_7D}' 
)
and ymd = '${TODAY_1D}' ;

insert into table zhengxin.zx_report_mid_data select '${TODAY_1D}' as day,'thirty_stay_num' type,count(distinct userid) ctnum  from zhengxin.accesslog l
where l.userid in (
select e.userid from zhengxin.euser  e where ymd = '${TODAY_30D}' 
)
and ymd = '${TODAY_1D}' ;

insert into table zhengxin.zx_report_mid_data select '${TODAY_1D}' as day,'reg_num' type,count(distinct mobile) ctnum from zhengxin.procdt_scorelog  p,zhengxin.euser e 
where p.mobile = e.mobilephone
and p.ymd  between '${TODAY_1D_01}' and '${TODAY_1D_31}'
and source not in ('20005','60003','60004','30018')
and e.ymd = '${TODAY_1D}';

EOF
