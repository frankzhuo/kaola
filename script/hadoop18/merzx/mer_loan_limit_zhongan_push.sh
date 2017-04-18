




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




echo '--------------- 授信额度结果推送   -------------'
#while true 
#do 

sudo -u zx_t hive <<EOF 

set hive.auto.convert.join=false; 
set mapreduce.reduce.shuffle.input.buffer.percent=0.1;
set mapreduce.reduce.shuffle.parallelcopies=9;



create table if not exists merzx.mer_limit_result_zhongan_push(
id                      string   comment '主键',
orgid                   string   comment '机构编号',
merid                   string   comment '商户号',
prdid                   string   comment '产品编号',
creditstate             string   comment '预授信状态',  
creditamountonline      bigint   comment '预授信额度上限', 
creditamountoffline     bigint   comment '预授信额度下限',
yj_cnt_1m               bigint   comment '最近1个月的月均笔数',-------------最近1个月
yj_amt_1m               float   comment '最近1个月的月均交易金额',
lj_day_1m               bigint   comment '最近1个月的交易天数',
yj_card_1m              bigint   comment '最近1个月的交易卡数',
yj_cnt_3m               bigint   comment '最近3个月的月均笔数',-------------------最近3个月
yj_amt_3m               float   comment '最近3个月的月均交易额',        
yj_card_3m              bigint   comment '最近3个月的月均交易卡数',
yj_cnt_6m               bigint   comment '最近6个月的月均笔数',-------------------最近6个月
yj_amt_6m               float   comment '最近6个月的月均交易额',         
yj_card_6m              bigint   comment '最近6个月的月均交易卡数',
yj_cnt_1y               bigint   comment '最近1年的月均笔数',-------------------最近1年
yj_amt_1y               float   comment '最近1年的月均交易额',        
yj_card_1y              bigint   comment '最近1年的月均交易卡数',
createtime              string   comment '创建时间',
updatetime              string   comment '修改时间'
);


insert overwrite table merzx.mer_limit_result_zhongan_push
select 
concat(t1.orgid,t1.mer_id)     as id,
t1.orgid                 as orgid,       ------------机构编号
t1.mer_id                as merid,       -------------商户号
'6'                      as prdid,       -----------产品编号
case when loan_limit_lower >0 then '1' else 0  end as creditstate,         ----------预授信状态
nvl(loan_limit_upper,0)  as creditamountonline,  ----------预授信额度上限
nvl(loan_limit_lower,0)  as creditamountoffline, ----------预授信额度下限
nvl(yj_cnt_1m,0)         as yj_cnt_1m,--最近1个月的月均笔数-------------最近1个月
nvl(yj_amt_1m,0)         as yj_amt_1m,--最近1个月的月均交易金额
nvl(lj_day_1m,0)         as lj_day_1m,--最近1个月的交易天数
nvl(yj_card_1m,0)        as yj_card_1m,--最近1个月的交易卡数
nvl(yj_cnt_3m,0)         as yj_cnt_3m,--最近3个月的月均笔数-----------------最近3个月
nvl(yj_amt_3m,0)         as yj_amt_3m,--最近3个月的月均交易额         
nvl(yj_card_3m,0)        as yj_card_3m,--最近3个月的月均交易卡数
nvl(yj_cnt_6m,0)         as yj_cnt_6m,--最近6个月的月均笔数-----------------最近6个月
nvl(yj_amt_6m,0)         as yj_amt_6m,--最近6个月的月均交易额         
nvl(yj_card_6m,0)        as yj_card_6m,--最近6个月的月均交易卡数
nvl(yj_cnt_1y,0)         as yj_cnt_1y,--最近1年的月均笔数-----------------最近1年
nvl(yj_amt_1y,0)         as yj_amt_1y,--最近1年的月均交易额         
nvl(yj_card_1y,0)        as yj_card_1y,--最近1年的月均交易卡数
from_unixtime(unix_timestamp(),'yyyy-MM-dd HH:mm:ss') as createtime,
from_unixtime(unix_timestamp(),'yyyy-MM-dd HH:mm:ss') as updatetime
from 
(select * 
from merzx.mer_limit_result 
where flag=1) t1 
left join merzx.v2_mid_merchant_cnt_amt_day_mon t2 on t1.orgid=t2.orgid and t1.mer_id=t2.mer_id
join 
(select * from merdm.dm_mer_flag_day  
          where score_flag_1=1 
            and age_flag_1=1 
            and mcc_flag=1 
            and yj_cnt_3m_flag_2 <>'null') t3 
on t1.orgid=t3.orgid and t1.mer_id=t3.mer_id ;

EOF


tmp=$?
NOWTIME=$(date +%Y-%m-%d/%H:%M:%S)
if test $tmp -eq 0; then
echo $NOWTIME "-------------- 授信额度结果推送 创建成功--------"$tmp  
break
else
echo $NOWTIME "-------------- 授信额度结果推送 创建失败--------"$tmp 
#sleep 5m
fi 

#done

