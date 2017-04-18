#!/bin/sh

#cd /home/hdfs/bank_gd_work

#sudo -u hdfs hive -f create_bank_gd_lklpos_atmt.sql

TODAY_1D=$(date --date='1 day ago' +%Y%m%d)

sudo -u hdfs hive<<EOF

insert overwrite  table merzx.bank_gd_lklpos_atmtxnjnl partition(ymd=${TODAY_1D})  
select mer_id,card_type,bank_short_name,trans_amt,txn_data,ccycod,
case
   when txn_nm like '%退款%' then 1
   else 0 end as refund_identification,
log_no,txn_sts,txn_cd
from hdw.lklpos_atmtxnjnl where ymd="${TODAY_1D}";
EOF
