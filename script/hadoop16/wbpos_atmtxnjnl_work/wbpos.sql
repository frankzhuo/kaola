#!/bin/sh
TODAY_1D=`date -d "$1 -1 days" "+%Y%m%d"`
TODAY_1d=`date -d "$1 -1 days" "+%Y-%m-%d"`
echo '---------------------添加外部商户pos流水表-------------------'
echo  $TODAY_1D
sudo -u hdfs hive <<EOF
use hdw;
set hive.exec.dynamic.partition.mode=nonstrict; 
set hive.exec.dynamic.partition=true;
set hive.exec.max.dynamic.partitions=100000;
set hive.exec.max.dynamic.partitions.pernode=100000;

set mapreduce.map.memory.mb=5000;
set mapreduce.reduce.memory.mb=5000;
set mapreduce.map.java.opts=-Djava.net.preferIPv4Stack=true -Xms3000m -Xmx3000m;

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
from merdm.wbpos_msmertransactionflow t
;
EOF
tmp=$?
NOWTIME=$(date +%Y-%m-%d/%H:%M:%S)
if test $tmp -eq 0; then
echo $NOWTIME'--------------外部商户pos流水清洗成功-----------'$tmp 
else
echo $NOWTIME'--------------外部商户pos流水清洗失败-----------'$tmp >> /home/zx_t/merdm/logs/logs_hdw_failed
exit -2;
fi

echo '---------------外部商户pos流水承兑交易表-------------'
sudo -u hdfs hive <<EOF
use hdw;
set hive.exec.dynamic.partition.mode=nonstrict; 
set hive.exec.dynamic.partition=true;
set hive.exec.max.dynamic.partitions=100000;
set hive.exec.max.dynamic.partitions.pernode=100000;

create table if not exists hdw.wbpos_atmtxnjnl_success like hdw.wbpos_atmtxnjnl;
insert overwrite table hdw.wbpos_atmtxnjnl_success partition (ymd)
select * from hdw.wbpos_atmtxnjnl where txn_cd in ('02001') and txn_typ='N' and txn_sts='S';
EOF
tmp=$?
NOWTIME=$(date +%Y-%m-%d/%H:%M:%S)
if test $tmp -eq 0; then
echo $NOWTIME'--------------内外部商户pos流水承兑交易表成功-----------'$tmp 
else
echo $NOWTIME'--------------内外部商户pos流水承兑交易表失败-----------'$tmp >> /home/zx_t/merdm/logs/logs_hdw_failed
fi
