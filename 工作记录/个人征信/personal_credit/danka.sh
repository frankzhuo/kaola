TODAY_2Y=$(date --date='2 year ago' +%Y%m%d)


echo '----------最近2年交易中间表--------------'
sudo -u hdfs hive <<EOF
drop table  zhengxin.mid_merge_card_2y;
create table zhengxin.mid_merge_card_2y
as 
select  mobile_num,cardno ,out_bank_name,total_am,data_date,tcat_lv3_name,card_type  from (
select   mobile_num,outcdno as cardno,out_bank_name,total_am,data_date,tcat_lv3_name,out_card_type  as card_type
from   hdm.f_fact_trans_success_new_details  
where  length(mobile_num)=11 and ymd >='${TODAY_2Y}'  and  (tcat_lv3_name='转账汇款'  or  tcat_lv3_name='还款')
union all
select   mobile_num,incdno  as cardno ,out_bank_name,total_am,data_date,tcat_lv3_name,in_card_type  as card_type
from   hdm.f_fact_trans_success_new_details  
where  length(mobile_num)=11 and ymd >='${TODAY_2Y}'  and  (tcat_lv3_name='转账汇款'  or  tcat_lv3_name='还款')
) a;
EOF
tmp=$?
NOWTIME=$(date +%Y-%m-%d/%H:%M:%S)
if test $tmp -eq 0; then
echo $NOWTIME'--------------最近2年交易中间表创建成功'$tmp >>  logs/logs_personal_credit_report_result
else
echo $NOWTIME'--------------最近2年交易中间表表创建失败'$tmp >>  logs/logs_personal_credit_report_result
fi

echo '----------最近2年信用卡、借记卡交易金额、交易次数排名前三的用户和卡号中间表--------------'
sudo -u hdfs hive <<EOF
drop table  zhengxin.mid_trans_2y_top;
create table zhengxin.mid_trans_2y_top
as 
select  mobile_num,cardno ,rank_desc,stat_type
from (
select  mobile_num,cardno ,rank_desc ,1 as stat_type
from (
select  mobile_num,cardno ,sum(total_am)  ,row_number() over(partition by mobile_num order by sum(total_am)/100 desc)   as  rank_desc from  zhengxin.mid_merge_card_2y
where  card_type='XYK'
group  by  mobile_num,cardno
)
b
where rank_desc<=3
union all
select  mobile_num,cardno ,rank_desc,2 as stat_type
from (
select  mobile_num,cardno ,count(1)  ,row_number() over(partition by mobile_num order by count(1) desc)   as  rank_desc from  zhengxin.mid_merge_card_2y
where  card_type='XYK'
group  by  mobile_num,cardno
)
b
where rank_desc<=3
union all
select  mobile_num,cardno ,rank_desc ,3 as stat_type
from (
select  mobile_num,cardno ,sum(total_am)  ,row_number() over(partition by mobile_num order by sum(total_am)/100 desc)   as  rank_desc from  zhengxin.mid_merge_card_2y
where  card_type='JJK'
group  by  mobile_num,cardno
)
b
where rank_desc<=3
union all
select  mobile_num,cardno ,rank_desc,4 as stat_type
from (
select  mobile_num,cardno ,count(1)  ,row_number() over(partition by mobile_num order by count(1) desc)   as  rank_desc from  zhengxin.mid_merge_card_2y
where  card_type='JJK'
group  by  mobile_num,cardno
)
b
where rank_desc<=3
)e ;
EOF
tmp=$?
NOWTIME=$(date +%Y-%m-%d/%H:%M:%S)
if test $tmp -eq 0; then
echo $NOWTIME'--------------最近2年信用卡、借记卡交易金额、交易次数排名前三的用户和卡号中间表创建成功'$tmp >>  logs/logs_personal_credit_report_result
else
echo $NOWTIME'--------------最近2年信用卡、借记卡交易金额、交易次数排名前三的用户和卡号中间表创建失败'$tmp >>  logs/logs_personal_credit_report_result
fi



echo '------单卡交易信息-------------------------'
sudo -u hdfs hive <<EOF
drop table zhengxin.t_top3_card_trans_new;
create table zhengxin.t_top3_card_trans_new
as
select  
a.mobile_num,
a.stat_type,--统计类型1信用卡最多金额、2信用卡最多次数、3借记卡最多金额、4借记卡最多次数
a.cardno,--卡号
(case when b.out_bank_name is null then c.out_bank_name  else  b.out_bank_name  end ) as out_bank_name,--卡银行
 round(b.zz_amt,2)  as  zz_amt,--转账交易金额
 b.zz_cnt,--转账交易次数
 round(b.yjzz_amt,2) as yjzz_amt,--月均转账金额
 c.hk_month_num,--还款分布月份数
 round(c.yjhk_amt,2) as yjhk_amt,--月均还款金额
 round(c.bjhk_amt,2) as bjhk_amt, --笔均还款金额
 '${TODAY_2Y}'  as startdate,--开始日期
 from_unixtime(unix_timestamp(),'yyyyMMdd')  as  enddate, --截止日期
 '24' as month_num --报告周期（月）
from 
zhengxin.mid_trans_2y_top a
left join (
select  
mobile_num,
cardno,
out_bank_name,
sum(total_am)/100 as  zz_amt,
count(1) as zz_cnt,
sum(total_am)/(100*count(distinct substr(data_date,0,7))) as yjzz_amt 
from   zhengxin.mid_merge_card_2y
where tcat_lv3_name='转账汇款'  
group by mobile_num,cardno,out_bank_name ) b  on  a.mobile_num=b.mobile_num  and  a.cardno=b.cardno
left join (
select  
mobile_num,
cardno,
out_bank_name,
count(distinct substr(data_date,0,7)) as hk_month_num,
sum(total_am)/(100*count(distinct substr(data_date,0,7))) as yjhk_amt,
sum(total_am)/(100*count(1)) as bjhk_amt 
from  
zhengxin.mid_merge_card_2y
where tcat_lv3_name='还款' 
group by  mobile_num,cardno,out_bank_name) c  on   a.mobile_num=c.mobile_num  and  a.cardno=c.cardno;
EOF
tmp=$?
NOWTIME=$(date +%Y-%m-%d/%H:%M:%S)
if test $tmp -eq 0; then
echo $NOWTIME'--------------t_top3_card_trans创建成功'$tmp >>  logs/logs_personal_credit_report_result
else
echo $NOWTIME'--------------t_top3_card_trans创建失败'$tmp >>  logs/logs_personal_credit_report_result
fi


echo '------验证用户后的单卡交易信息-------------------------'
sudo -u hdfs hive <<EOF
drop table zhengxin.t_card_trans_info_new;
create table zhengxin.t_card_trans_info_new
as
select  
t1.mobile_num,
t2.stat_type, --统计类型1信用卡最多金额、2信用卡最多次数、3借记卡最多金额、4借记卡最多次数
t2.outcdno,--卡号
t2.out_bank_name,--卡银行
t2.zz_amt,--转账交易金额
t2.zz_cnt,--转账交易次数
t2.yjzz_amt,--月均转账金额
t2.hk_month_num,--还款分布月份数
t2.yjhk_amt,--月均还款金额
t2.bjhk_amt, --笔均还款金额
t2.startdate,--开始日期
t2.enddate, --截止日期
t2.month_num --报告周期（月）
from  creditck.var_user  t1
left join zhengxin.t_top3_card_trans_new t2   on  t1.mobile_num=t2.mobile_num;
EOF
tmp=$?
NOWTIME=$(date +%Y-%m-%d/%H:%M:%S)
if test $tmp -eq 0; then
echo $NOWTIME'--------------t_card_trans_info创建成功'$tmp >>  logs/logs_personal_credit_report_result
else
echo $NOWTIME'--------------t_card_trans_info创建失败'$tmp >>  logs/logs_personal_credit_report_result
fi

