TODAY_2Y=$(date --date='2 year ago' +%Y%m%d)

echo '----------最近2年交易中间表--------------'
sudo -u hdfs hive <<EOF
drop table  zhengxin.mid_merge_card_2y;
create table zhengxin.mid_merge_card_2y
as 
select  mobile_num,cardno ,bank_name,total_am,data_date,tcat_lv3_name,card_type  from (
select   mobile_num,outcdno as cardno,out_bank_name as bank_name,total_am,data_date,tcat_lv3_name,out_card_type  as card_type
from   hdm.f_fact_trans_success_new_details  
where  length(mobile_num)=11 and ymd >='${TODAY_2Y}'  and  tcat_lv3_name <> '查询' 
union all
select   mobile_num,incdno  as cardno ,in_bank_name as  bank_name,total_am,data_date,tcat_lv3_name,in_card_type  as card_type
from   hdm.f_fact_trans_success_new_details  
where  length(mobile_num)=11 and ymd >='${TODAY_2Y}'  and  tcat_lv3_name <> '查询' 
) a;
EOF
tmp=$?
NOWTIME=$(date +%Y-%m-%d/%H:%M:%S)
if test $tmp -eq 0; then
echo $NOWTIME'--------------最近2年交易中间表创建成功'$tmp >>  logs/logs_personal_credit_report_result
else
echo $NOWTIME'--------------最近2年交易中间表表创建失败'$tmp >>  logs/logs_personal_credit_report_result
fi

echo '------单卡交易信息-------------------------'
sudo -u hdfs hive <<EOF
drop table zhengxin.t_top3_card_trans_new;
create table zhengxin.t_top3_card_trans_new
as 
select
mobile_num,
'${TODAY_2Y}'  as startdate,--开始日期
from_unixtime(unix_timestamp(),'yyyyMMdd')  as  enddate, --截止日期
'24' as month_num, --报告周期（月）
--信用卡金额最大
max(case when id1=1 and card_type='XYK' then cardno end) as cardno1, --卡号
max(case when id1=1 and card_type='XYK' then bank_name end) as bank_name1, --开户银行
max(case when id1=1 and card_type='XYK' then hk_month_num end) as hk_month_num1, --还款分布月份数
max(case when id1=1 and card_type='XYK' then  round(yjhk_amt,2) end) as yjhk_amt1,--月均还款金额
max(case when id1=1 and card_type='XYK' then round(bjhk_amt,2) end) as bjhk_amt1,--笔均还款金额

max(case when id1=2 and card_type='XYK' then cardno end) as cardno2, --卡号
max(case when id1=2 and card_type='XYK' then bank_name end) as bank_name2, --开户银行
max(case when id1=2 and card_type='XYK' then hk_month_num end) as hk_month_num2, --还款分布月份数
max(case when id1=2 and card_type='XYK' then  round(yjhk_amt,2) end) as yjhk_amt2,--月均还款金额
max(case when id1=2 and card_type='XYK' then round(bjhk_amt,2) end) as bjhk_amt2,--笔均还款金额

max(case when id1=3 and card_type='XYK' then cardno end) as cardno3, --卡号
max(case when id1=3 and card_type='XYK' then bank_name end) as bank_name3, --开户银行
max(case when id1=3 and card_type='XYK' then hk_month_num end) as hk_month_num3, --还款分布月份数
max(case when id1=3 and card_type='XYK' then  round(yjhk_amt,2) end) as yjhk_amt3,--月均还款金额
max(case when id1=3 and card_type='XYK' then round(bjhk_amt,2) end) as bjhk_amt3,--笔均还款金额

--信用卡次数最多
max(case when id2=1 and card_type='XYK' then cardno end) as cardno21, --卡号
max(case when id2=1 and card_type='XYK' then bank_name end) as bank_name21, --开户银行
max(case when id2=1 and card_type='XYK' then hk_month_num end) as hk_month_num21, --还款分布月份数
max(case when id2=1 and card_type='XYK' then  round(yjhk_amt,2) end) as yjhk_amt21,--月均还款金额
max(case when id2=1 and card_type='XYK' then round(bjhk_amt,2) end) as bjhk_amt21,--笔均还款金额

max(case when id2=2 and card_type='XYK' then cardno end) as cardno22, --卡号
max(case when id2=2 and card_type='XYK' then bank_name end) as bank_name22, --开户银行
max(case when id2=2 and card_type='XYK' then hk_month_num end) as hk_month_num22, --还款分布月份数
max(case when id2=2 and card_type='XYK' then  round(yjhk_amt,2) end) as yjhk_amt22,--月均还款金额
max(case when id2=2 and card_type='XYK' then round(bjhk_amt,2) end) as bjhk_amt22,--笔均还款金额

max(case when id2=3 and card_type='XYK' then cardno end) as cardno23, --卡号
max(case when id2=3 and card_type='XYK' then bank_name end) as bank_name23, --开户银行
max(case when id2=3 and card_type='XYK' then hk_month_num end) as hk_month_num23, --还款分布月份数
max(case when id2=3 and card_type='XYK' then  round(yjhk_amt,2) end) as yjhk_amt23,--月均还款金额
max(case when id2=3 and card_type='XYK' then round(bjhk_amt,2) end) as bjhk_amt23,--笔均还款金额

--借记卡金额最大
max(case when id1=1 and card_type='JJK' then cardno end) as cardno31, --卡号
max(case when id1=1 and card_type='JJK' then bank_name end) as bank_name31, --开户银行
max(case when id1=1 and card_type='JJK' then  round(zz_amt,2)  end) as zz_amt31, --转账交易金额
max(case when id1=1 and card_type='JJK' then  zz_cnt  end) as zz_cnt31, --转账交易次数
max(case when id1=1 and card_type='JJK' then   round(yjzz_amt,2) end) as yjzz_amt31, --月均转账金额
max(case when id1=1 and card_type='JJK' then hk_month_num end) as hk_month_num31, --还款分布月份数
max(case when id1=1 and card_type='JJK' then  round(yjhk_amt,2) end) as yjhk_amt31,--月均还款金额
max(case when id1=1 and card_type='JJK' then round(bjhk_amt,2) end) as bjhk_amt31,--笔均还款金额

max(case when id1=2 and card_type='JJK' then cardno end) as cardno32, --卡号
max(case when id1=2 and card_type='JJK' then bank_name end) as bank_name32, --开户银行
max(case when id1=2 and card_type='JJK' then  round(zz_amt,2)  end) as zz_amt32, --转账交易金额
max(case when id1=2 and card_type='JJK' then  zz_cnt  end) as zz_cnt32, --转账交易次数
max(case when id1=2 and card_type='JJK' then   round(yjzz_amt,2) end) as yjzz_amt32, --月均转账金额
max(case when id1=2 and card_type='JJK' then hk_month_num end) as hk_month_num32, --还款分布月份数
max(case when id1=2 and card_type='JJK' then  round(yjhk_amt,2) end) as yjhk_amt32,--月均还款金额
max(case when id1=2 and card_type='JJK' then round(bjhk_amt,2) end) as bjhk_amt32,--笔均还款金额

max(case when id1=3 and card_type='JJK' then cardno end) as cardno33, --卡号
max(case when id1=3 and card_type='JJK' then bank_name end) as bank_name33, --开户银行
max(case when id1=3 and card_type='JJK' then  round(zz_amt,2)  end) as zz_amt33, --转账交易金额
max(case when id1=3 and card_type='JJK' then  zz_cnt  end) as zz_cnt33, --转账交易次数
max(case when id1=3 and card_type='JJK' then   round(yjzz_amt,2) end) as yjzz_amt33, --月均转账金额
max(case when id1=3 and card_type='JJK' then hk_month_num end) as hk_month_num33, --还款分布月份数
max(case when id1=3 and card_type='JJK' then  round(yjhk_amt,2) end) as yjhk_amt33,--月均还款金额
max(case when id1=3 and card_type='JJK' then round(bjhk_amt,2) end) as bjhk_amt33,--笔均还款金额

--借记卡次数最多

max(case when id2=1 and card_type='JJK' then cardno end) as cardno41, --卡号
max(case when id2=1 and card_type='JJK' then bank_name end) as bank_name41, --开户银行
max(case when id2=1 and card_type='JJK' then  round(zz_amt,2)  end) as zz_amt41, --转账交易金额
max(case when id2=1 and card_type='JJK' then  zz_cnt  end) as zz_cnt41, --转账交易次数
max(case when id2=1 and card_type='JJK' then   round(yjzz_amt,2) end) as yjzz_amt41, --月均转账金额
max(case when id2=1 and card_type='JJK' then hk_month_num end) as hk_month_num41, --还款分布月份数
max(case when id2=1 and card_type='JJK' then  round(yjhk_amt,2) end) as yjhk_amt41,--月均还款金额
max(case when id2=1 and card_type='JJK' then round(bjhk_amt,2) end) as bjhk_amt41,--笔均还款金额

max(case when id2=2 and card_type='JJK' then cardno end) as cardno42, --卡号
max(case when id2=2 and card_type='JJK' then bank_name end) as bank_name42, --开户银行
max(case when id2=2 and card_type='JJK' then  round(zz_amt,2)  end) as zz_amt42, --转账交易金额
max(case when id2=2 and card_type='JJK' then  zz_cnt  end) as zz_cnt42, --转账交易次数
max(case when id2=2 and card_type='JJK' then   round(yjzz_amt,2) end) as yjzz_amt42, --月均转账金额
max(case when id2=2 and card_type='JJK' then hk_month_num end) as hk_month_num42, --还款分布月份数
max(case when id2=2 and card_type='JJK' then  round(yjhk_amt,2) end) as yjhk_amt42,--月均还款金额
max(case when id2=2 and card_type='JJK' then round(bjhk_amt,2) end) as bjhk_amt42,--笔均还款金额

max(case when id2=3 and card_type='JJK' then cardno end) as cardno43, --卡号
max(case when id2=3 and card_type='JJK' then bank_name end) as bank_name43, --开户银行
max(case when id2=3 and card_type='JJK' then  round(zz_amt,2)  end) as zz_amt43, --转账交易金额
max(case when id2=3 and card_type='JJK' then  zz_cnt  end) as zz_cnt43, --转账交易次数
max(case when id2=3 and card_type='JJK' then   round(yjzz_amt,2) end) as yjzz_amt43, --月均转账金额
max(case when id2=3 and card_type='JJK' then hk_month_num end) as hk_month_num43, --还款分布月份数
max(case when id2=3 and card_type='JJK' then  round(yjhk_amt,2) end) as yjhk_amt43,--月均还款金额
max(case when id2=3 and card_type='JJK' then round(bjhk_amt,2) end) as bjhk_amt43  --笔均还款金额
from (
select 
mobile_num,
cardno,
max(bank_name) as bank_name,
card_type,
count(*),
sum(total_am),
sum(case when tcat_lv3_name='转账汇款' then total_am/100 end ) as zz_amt,
count(case when tcat_lv3_name='转账汇款' then 1 end)  as zz_cnt,
sum(case when tcat_lv3_name='转账汇款' then total_am/100 end ) /count(distinct(case when tcat_lv3_name='转账汇款' then  substr(data_date,0,7) end ))as yjzz_amt,

count(distinct (case when tcat_lv3_name='还款' then  substr(data_date,0,7)  end ) )as hk_month_num,
sum(case when tcat_lv3_name='还款' then total_am/100 end ) / count(distinct (case when tcat_lv3_name='还款' then substr(data_date,0,7) end )) as yjhk_amt,
sum(case when tcat_lv3_name='还款' then total_am/100 end ) / count (case when tcat_lv3_name='还款' then  1 end ) as bjhk_amt,

row_number() over(partition by mobile_num,card_type order by sum(total_am) desc) id1,
row_number() over(partition by mobile_num,card_type order by count(*) desc) id2
from zhengxin.mid_merge_card_2y
group by mobile_num,cardno,card_type
) a
group by mobile_num;
EOF
tmp=$?
NOWTIME=$(date +%Y-%m-%d/%H:%M:%S)
if test $tmp -eq 0; then
echo $NOWTIME'--------------t_top3_card_trans_new创建成功'$tmp >>  logs/logs_personal_credit_report_result
else
echo $NOWTIME'--------------t_top3_card_trans_new创建失败'$tmp >>  logs/logs_personal_credit_report_result
fi


echo '------验证用户后的单卡交易信息-------------------------'
sudo -u hdfs hive <<EOF
drop table zhengxin.t_user_card_trans_info;
create table zhengxin.t_user_card_trans_info
as
select  
t1.mobile_num as mobile,
startdate,--开始日期
enddate, --截止日期
month_num, --报告周期（月）
cardno1, bank_name1,hk_month_num1, yjhk_amt1,bjhk_amt1, --信用卡金额最大1
cardno2, bank_name2,hk_month_num2, yjhk_amt2,bjhk_amt2, --信用卡金额最大2
cardno3, bank_name3,hk_month_num3, yjhk_amt3,bjhk_amt3, --信用卡金额最大3
cardno21, bank_name21,hk_month_num21, yjhk_amt21,bjhk_amt21, --信用卡次数最多1
cardno22, bank_name22,hk_month_num22, yjhk_amt22,bjhk_amt22, --信用卡次数最多2
cardno23, bank_name23,hk_month_num23, yjhk_amt23,bjhk_amt23, --信用卡次数最多3
cardno31, bank_name31,hk_month_num31, yjhk_amt31,bjhk_amt31,zz_amt31,zz_cnt31,yjzz_amt31,--借记卡金额最大1
cardno32, bank_name32,hk_month_num32, yjhk_amt32,bjhk_amt32,zz_amt32,zz_cnt32,yjzz_amt32,--借记卡金额最大2
cardno33, bank_name33,hk_month_num33, yjhk_amt33,bjhk_amt33,zz_amt33,zz_cnt33,yjzz_amt33,--借记卡金额最大3
cardno41, bank_name41,hk_month_num41, yjhk_amt41,bjhk_amt41,zz_amt41,zz_cnt41,yjzz_amt41,--借记卡次数最多1
cardno42, bank_name42,hk_month_num42, yjhk_amt42,bjhk_amt42,zz_amt42,zz_cnt42,yjzz_amt42,--借记卡次数最多2
cardno43, bank_name43,hk_month_num43, yjhk_amt43,bjhk_amt43,zz_amt43,zz_cnt43,yjzz_amt43 --借记卡次数最多3
from  creditck.var_user  t1
left join zhengxin.t_top3_card_trans_new t2   on  t1.mobile_num=t2.mobile_num;
EOF
tmp=$?
NOWTIME=$(date +%Y-%m-%d/%H:%M:%S)
if test $tmp -eq 0; then
echo $NOWTIME'--------------t_user_card_trans_info创建成功'$tmp >>  logs/logs_personal_credit_report_result
else
echo $NOWTIME'--------------t_user_card_trans_info创建失败'$tmp >>  logs/logs_personal_credit_report_result
fi

