#!/bin/bash


TODAY_1D=$(date --date='1 day ago' +%Y%m%d)
TODAY_1d=$(date --date='1 day ago' +%Y-%m-%d)




############################################################################################################################################


while true
do
echo '---------------------创建内外部商户pos流水表-------------------'
sudo -u hdfs hive <<EOF

set hive.auto.convert.join=false; 
set mapreduce.reduce.shuffle.input.buffer.percent=0.1;
set mapreduce.reduce.shuffle.parallelcopies=9;

use hdw;
create table if not exists hdw.lklpos_atmtxnjnl(
orgid string comment '组织编码',
branch_id string comment '网点号',
log_no string comment '流水号',
logdate string comment '逻辑日期',
acc_date string comment '会计日期',
txntime string comment '交易发生时间',
txn_data string comment '交易时间',
txn_cd string comment '交易码',
txn_nm string comment '交易名称',
mer_id string comment '商户号',
mer_mcc string comment '商户类别mcc',
mer_name string comment '商户名称 8583报文第43域',
cardno string comment '卡号',
cardflg string comment '卡类型 99 上海分行移动手机支付',
bank_code string comment '发卡机构',
card_bin string comment '卡bin',
card_kind string comment '卡类型代码,C,D... ',
card_kind_name string comment '卡类型名称',
card_type string comment '卡类型XYK,JJK ',
card_name string comment '卡名称',
bank_short_name string comment '银行简称',
ccycod string comment '币种',
txnamt int comment '金额:分 ',
refamt int comment '已退货金额:分 ',
trans_amt int comment '交易金额:分 ',
fee int comment '手续费,小费,调整金额:分 ',
term_no string comment '终端号',
card_app_type int comment '卡应用类型',
cpscod string comment '返回码 银联标准应答码',
frsp_cd string comment '前置响应码',
trsp_cd string comment '第三方响应码',
txn_sts string comment '交易状态 S:成功 F:失败 C:被冲正 U:预记状态 X:发送失败 T: 发送超时 E: 其他错误',
txn_typ string comment '交易类型 N:正常交易 C:被冲正交易 D:被撤销交易',
rank1 string comment '预留1',
rank2 string comment '预留2',
rank3 string comment '预留3'
) partitioned by (ymd string);

alter table lklpos_atmtxnjnl drop partition (ymd='${TODAY_1D}');

EOF
tmp=$?
NOWTIME=$(date +%Y-%m-%d/%H:%M:%S)
if test $tmp -eq 0; then
    echo $NOWTIME'--------------创建内外部商户pos流水表成功-----------'$tmp 
    break
else
    echo $NOWTIME'--------------创建内外部商户pos流水表失败-----------'$tmp 
    sleep 5m
fi
done


while true
do
echo '---------------------添加内部商户pos流水表-------------------'
sudo  -u  hdfs impala-shell -r <<EOF
insert overwrite table hdw.lklpos_atmtxnjnl partition (ymd='${TODAY_1D}')
select orgid,
branch_id,
log_no,
logdate,
acc_date,
txntime,
txn_data,
txn_cd,
txn_nm,
mer_id,
mer_mcc,
mer_name,
cardno,
cardflg,
bank_code,
card_bin,
card_kind,
card_kind_name,
card_type,
card_name,
bank_short_name,
ccycod,
txnamt,
refamt,
cast(trans_amt as int) as trans_amt,
fee,
term_no,
card_app_type,
cpscod,
frsp_cd,
trsp_cd,
txn_sts,
txn_typ,
null as rank1,
null as rank2,
null as rank3
from (
select '110001' as orgid,
b.branch_id,
a.log_no,
a.logdat as logdate,
a.acc_dt as acc_date,
a.txntim as txntime,
from_unixtime(unix_timestamp(a.txntim,'yyyyMMddHHmmss'),'yyyy-MM-dd HH:mm:ss') as txn_data,
a.txn_cd,
c.txn_nm,
a.mercid as mer_id,
a.mertyp as mer_mcc,
a.insadr as mer_name,
a.crd_no as cardno,
a.crdflg as cardflg,
a.issino as bank_code,
d.card_bin,
d.card_kind,
d.card_kind_name,
d.card_type,
d.card_name,
d.bank_short_name,
a.ccycod,
cast(a.txnamt as int) as txnamt,
cast(a.refamt as int) as refamt,
cast(a.txnamt as int)-cast(a.refamt as int) as trans_amt,
cast(a.fee as int) as fee,
a.termid as term_no,
cast(b.card_app_type as int) as card_app_type,
a.cpscod,
a.frsp_cd,
a.trsp_cd,
a.txn_sts,
a.txn_typ,
row_number() over(partition by a.log_no order by length(d.card_bin) desc) as cardbin_id 
from hds.pos_atmtxnjnl a 
left join hdw.lklpos_t01_terminal_info b on a.termid=b.term_no
left join hds.pos_pubtxnpur c on a.txn_cd=c.txn_cd
left join edm.d_cardinfo_apply d on length(a.crd_no)=cast(d.card_len as int) and substr(a.crd_no,1,cast(d.bin_len as int))=d.card_bin and length(d.card_bin)=cast(d.bin_len as int)
where a.ymd='${TODAY_1D}'
) t where cardbin_id=1;

EOF
tmp=$?
NOWTIME=$(date +%Y-%m-%d/%H:%M:%S)
if test $tmp -eq 0; then
    echo $NOWTIME'--------------内部商户pos流水表清洗分区'${TODAY_1D}'成功-----------'$tmp 
    break
else
    echo $NOWTIME'--------------内部商户pos流水表清洗分区'${TODAY_1D}'失败-----------'$tmp
    sleep 5m
fi
done




############################################################################################################################################


while true
do
echo '---------------------添加外部商户pos流水表-------------------'
sudo -u hdfs hive <<EOF

set hive.auto.convert.join=false; 
set mapreduce.reduce.shuffle.input.buffer.percent=0.1;
set mapreduce.reduce.shuffle.parallelcopies=9;

use hdw;
set hive.exec.dynamic.partition.mode=nonstrict; 
set hive.exec.dynamic.partition=true;
set hive.exec.max.dynamic.partitions=100000;
set hive.exec.max.dynamic.partitions.pernode=100000;

drop table hdw.wbpos_atmtxnjnl;
create table if not exists hdw.wbpos_atmtxnjnl like hdw.lklpos_atmtxnjnl;

--外部商户pos流水,手动添加
insert overwrite table hdw.wbpos_atmtxnjnl partition (ymd)
select orgid,
BRANCHID as branch_id,
TRANSACTIONID as log_no,
TRANSACTIONTIMESTAMP as logdate,
null as acc_date,
from_unixtime(unix_timestamp(TRANSACTIONTIMESTAMP),'yyyyMMddHHmmss') as txntime,
TRANSACTIONTIMESTAMP as txn_data,
TRADINGCODE as txn_cd,
null as txn_nm,
MERCHANTID as mer_id,
MERCHANTMCC as mer_mcc,
null as mer_name,
TRADINGBANKCARD as cardno,
null as cardflg,
null as bank_code,
null as card_bin,
BANKCARDTYPE as card_kind,
case when BANKCARDTYPE='C' then '信用卡' else '借记卡' end as card_kind_name,
case when BANKCARDTYPE='C' then 'XYK' else 'JJK' end as card_type,
null as card_name,
null as bank_short_name,
CURRENCY as ccycod,
cast(TRANSACTIONAMOUNT as int) as txnamt,
0 as refamt,
cast(TRANSACTIONAMOUNT as int) as trans_amt,
cast(COUNTERFEE as int) as fee,
TERMINALID as term_no,
null as card_app_type,
CPSCODE as cpscod,
null as frsp_cd,
null as trsp_cd,
TRADINGSTATE as txn_sts,
TRADINGTYPE as txn_typ,
null as rank1,
null as rank2,
null as rank3,
from_unixtime(unix_timestamp(TRANSACTIONTIMESTAMP),'yyyyMMdd') as ymd
from 
(select * from merdm.wbpos_msmertransactionflow
----union all 
----select * from hds.wbpos_msmertransactionflow_m
) t
;
EOF
tmp=$?
NOWTIME=$(date +%Y-%m-%d/%H:%M:%S)
if test $tmp -eq 0; then
    echo $NOWTIME'--------------外部商户pos流水清洗成功-----------'$tmp 
    break
else
    echo $NOWTIME'--------------外部商户pos流水清洗失败-----------'$tmp 
    sleep 5m
fi
done




############################################################################################################################################


while true
do
echo '---------------内外部商户pos流水承兑交易表-------------'
sudo -u hdfs hive <<EOF

set hive.auto.convert.join=false; 
set mapreduce.reduce.shuffle.input.buffer.percent=0.1;
set mapreduce.reduce.shuffle.parallelcopies=9;

use hdw;
set hive.exec.dynamic.partition.mode=nonstrict; 
set hive.exec.dynamic.partition=true;
set hive.exec.max.dynamic.partitions=100000;
set hive.exec.max.dynamic.partitions.pernode=100000;

create table if not exists hdw.lklpos_atmtxnjnl_success like hdw.lklpos_atmtxnjnl;
create table if not exists hdw.wbpos_atmtxnjnl_success like hdw.wbpos_atmtxnjnl;

--内部流水
insert overwrite table hdw.lklpos_atmtxnjnl_success partition (ymd)
select * from hdw.lklpos_atmtxnjnl where txn_cd in ('012001','012006','022001','022006') and txn_typ='N' and txn_sts='S' and ymd='${TODAY_1D}';
--外部流水
insert overwrite table hdw.wbpos_atmtxnjnl_success partition (ymd)
select * from hdw.wbpos_atmtxnjnl where txn_cd in ('02001') and txn_typ='N' and txn_sts='S';

EOF
tmp=$?
NOWTIME=$(date +%Y-%m-%d/%H:%M:%S)
if test $tmp -eq 0; then
    echo $NOWTIME'--------------内外部商户pos流水承兑交易表成功-----------'$tmp 
    break
else
    echo $NOWTIME'--------------内外部商户pos流水承兑交易表失败-----------'$tmp 
    sleep 5m
fi
done