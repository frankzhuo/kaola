create table if not exists merzx.bank_gd_lklpos_atmtxnjnl(mer_id string COMMENT '商户编号',
cardflg string COMMENT '交易银行卡类型',
bank_code string COMMENT '发卡方',
trans_amt int COMMENT '交易金额',
txn_data string COMMENT '交易时间戳',
ccycod string COMMENT '清算币种',
refund_identification int COMMENT '退单交易标识,退款用1标识，没有退款用0标识',
log_no string COMMENT '交易流水号',
txn_sts string COMMENT '交易状态代码',
txn_cd string COMMENT '交易代码')
COMMENT '光大信盈卡交易流水表'
partitioned by (ymd int)
row format delimited 
fields terminated by ',';


insert overwrite  table merzx.bank_gd_lklpos_atmtxnjnl partition(ymd=20151024)
select mer_id,card_type,bank_short_name,trans_amt,txn_data,ccycod,
case
   when txn_nm like '%退款%' then 1
   else 0 end as refund_identification,
log_no,txn_sts,txn_cd
from hdw.lklpos_atmtxnjnl where ymd<="20151024" ;
