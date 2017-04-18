create table if not exists merzx.risk_control_consulation(
case_id string COMMENT '案例号',
mon_id string COMMENT '商户号',
case_dt string COMMENT '交易日期',
rrc_typ string COMMENT '风险类型',
rsk_lvl string COMMENT '案例级别',
proc_rst string COMMENT '处理措施',
is_rsk string COMMENT '有无风险',
rrc_dsc string COMMENT '风险类型说明',
rsk_lvl_dsc string COMMENT '案例级别说明',
tm_smp string COMMENT '数据的处理时间,保留该字段主要是为了区分重复数据'
)
COMMENT '风控咨询表'
row format delimited 
fields terminated by ',';
