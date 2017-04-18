TODAY_2Y=$(date --date='2 year ago' +%Y%m%d)


echo '----------最近2年交易中间表--------------'
sudo -u hdfs hive <<EOF
drop table zhengxin.t_top3_card_trans_new;
create table zhengxin.t_top3_card_trans_new
as 
select
mobile_num,
'${TODAY_2Y}'  as startdate,--开始日期
from_unixtime(unix_timestamp(),'yyyyMMdd')  as  enddate, --截止日期
'24' as month_num --报告周期（月）
--信用卡金额最大
max(case when id1=1 and cardtype='XYK' then cardno end) as cardno1, --卡号
max(case when id1=1 and cardtype='XYK' then bank_name end) as bank_name1, --开户银行
max(case when id1=1 and cardtype='XYK' then hk_month_num end) as hk_month_num1, --还款分布月份数
max(case when id1=1 and cardtype='XYK' then  round(c.yjhk_amt,2) end) as yjhk_amt1,--月均还款金额
max(case when id1=1 and cardtype='XYK' then round(c.bjhk_amt,2) end) as bjhk_amt1,--笔均还款金额

max(case when id1=2 and cardtype='XYK' then cardno end) as cardno2, --卡号
max(case when id1=2 and cardtype='XYK' then bank_name end) as bank_name2, --开户银行
max(case when id1=2 and cardtype='XYK' then hk_month_num end) as hk_month_num2, --还款分布月份数
max(case when id1=2 and cardtype='XYK' then  round(c.yjhk_amt,2) end) as yjhk_amt2,--月均还款金额
max(case when id1=2 and cardtype='XYK' then round(c.bjhk_amt,2) end) as bjhk_amt2,--笔均还款金额

max(case when id1=3 and cardtype='XYK' then cardno end) as cardno3, --卡号
max(case when id1=3 and cardtype='XYK' then bank_name end) as bank_name3, --开户银行
max(case when id1=3 and cardtype='XYK' then hk_month_num end) as hk_month_num3, --还款分布月份数
max(case when id1=3 and cardtype='XYK' then  round(c.yjhk_amt,2) end) as yjhk_amt3,--月均还款金额
max(case when id1=3 and cardtype='XYK' then round(c.bjhk_amt,2) end) as bjhk_amt3,--笔均还款金额

--信用卡次数最多
max(case when id2=1 and cardtype='XYK' then cardno end) as cardno21, --卡号
max(case when id2=1 and cardtype='XYK' then bank_name end) as bank_name21, --开户银行
max(case when id2=1 and cardtype='XYK' then hk_month_num end) as hk_month_num21, --还款分布月份数
max(case when id2=1 and cardtype='XYK' then  round(c.yjhk_amt,2) end) as yjhk_amt21,--月均还款金额
max(case when id2=1 and cardtype='XYK' then round(c.bjhk_amt,2) end) as bjhk_amt21,--笔均还款金额

max(case when id2=2 and cardtype='XYK' then cardno end) as cardno22, --卡号
max(case when id2=2 and cardtype='XYK' then bank_name end) as bank_name22, --开户银行
max(case when id2=2 and cardtype='XYK' then hk_month_num end) as hk_month_num22, --还款分布月份数
max(case when id2=2 and cardtype='XYK' then  round(c.yjhk_amt,2) end) as yjhk_amt22,--月均还款金额
max(case when id2=2 and cardtype='XYK' then round(c.bjhk_amt,2) end) as bjhk_amt22,--笔均还款金额

max(case when id2=3 and cardtype='XYK' then cardno end) as cardno23, --卡号
max(case when id2=3 and cardtype='XYK' then bank_name end) as bank_name23, --开户银行
max(case when id2=3 and cardtype='XYK' then hk_month_num end) as hk_month_num23, --还款分布月份数
max(case when id2=3 and cardtype='XYK' then  round(c.yjhk_amt,2) end) as yjhk_amt23,--月均还款金额
max(case when id2=3 and cardtype='XYK' then round(c.bjhk_amt,2) end) as bjhk_amt23,--笔均还款金额

--借记卡金额最大
max(case when id1=1 and cardtype='JJK' then cardno end) as cardno31, --卡号
max(case when id1=1 and cardtype='JJK' then bank_name end) as bank_name31, --开户银行
max(case when id1=1 and cardtype='JJK' then  round(b.zz_amt,2)  end) as zz_amt31, --转账交易金额
max(case when id1=1 and cardtype='JJK' then  zz_cnt  end) as zz_cnt31, --转账交易次数
max(case when id1=1 and cardtype='JJK' then   round(b.yjzz_amt,2) end) as yjzz_amt31, --月均转账金额
max(case when id1=1 and cardtype='JJK' then hk_month_num end) as hk_month_num31, --还款分布月份数
max(case when id1=1 and cardtype='JJK' then  round(c.yjhk_amt,2) end) as yjhk_amt31,--月均还款金额
max(case when id1=1 and cardtype='JJK' then round(c.bjhk_amt,2) end) as bjhk_amt31,--笔均还款金额

max(case when id1=2 and cardtype='JJK' then cardno end) as cardno32, --卡号
max(case when id1=2 and cardtype='JJK' then bank_name end) as bank_name32, --开户银行
max(case when id1=2 and cardtype='JJK' then  round(b.zz_amt,2)  end) as zz_amt32, --转账交易金额
max(case when id1=2 and cardtype='JJK' then  zz_cnt  end) as zz_cnt32, --转账交易次数
max(case when id1=2 and cardtype='JJK' then   round(b.yjzz_amt,2) end) as yjzz_amt32, --月均转账金额
max(case when id1=2 and cardtype='JJK' then hk_month_num end) as hk_month_num32, --还款分布月份数
max(case when id1=2 and cardtype='JJK' then  round(c.yjhk_amt,2) end) as yjhk_amt32,--月均还款金额
max(case when id1=2 and cardtype='JJK' then round(c.bjhk_amt,2) end) as bjhk_amt32,--笔均还款金额

max(case when id1=3 and cardtype='JJK' then cardno end) as cardno33, --卡号
max(case when id1=3 and cardtype='JJK' then bank_name end) as bank_name33, --开户银行
max(case when id1=3 and cardtype='JJK' then  round(b.zz_amt,2)  end) as zz_amt33, --转账交易金额
max(case when id1=3 and cardtype='JJK' then  zz_cnt  end) as zz_cnt33, --转账交易次数
max(case when id1=3 and cardtype='JJK' then   round(b.yjzz_amt,2) end) as yjzz_amt33, --月均转账金额
max(case when id1=3 and cardtype='JJK' then hk_month_num end) as hk_month_num33, --还款分布月份数
max(case when id1=3 and cardtype='JJK' then  round(c.yjhk_amt,2) end) as yjhk_amt33,--月均还款金额
max(case when id1=3 and cardtype='JJK' then round(c.bjhk_amt,2) end) as bjhk_amt33,--笔均还款金额

--借记卡次数最多

max(case when id2=1 and cardtype='JJK' then cardno end) as cardno41, --卡号
max(case when id2=1 and cardtype='JJK' then bank_name end) as bank_name41, --开户银行
max(case when id2=1 and cardtype='JJK' then  round(b.zz_amt,2)  end) as zz_amt41, --转账交易金额
max(case when id2=1 and cardtype='JJK' then  zz_cnt  end) as zz_cnt41, --转账交易次数
max(case when id2=1 and cardtype='JJK' then   round(b.yjzz_amt,2) end) as yjzz_amt41, --月均转账金额
max(case when id2=1 and cardtype='JJK' then hk_month_num end) as hk_month_num41, --还款分布月份数
max(case when id2=1 and cardtype='JJK' then  round(c.yjhk_amt,2) end) as yjhk_amt41,--月均还款金额
max(case when id2=1 and cardtype='JJK' then round(c.bjhk_amt,2) end) as bjhk_amt41,--笔均还款金额

max(case when id2=2 and cardtype='JJK' then cardno end) as cardno42, --卡号
max(case when id2=2 and cardtype='JJK' then bank_name end) as bank_name42, --开户银行
max(case when id2=2 and cardtype='JJK' then  round(b.zz_amt,2)  end) as zz_amt42, --转账交易金额
max(case when id2=2 and cardtype='JJK' then  zz_cnt  end) as zz_cnt42, --转账交易次数
max(case when id2=2 and cardtype='JJK' then   round(b.yjzz_amt,2) end) as yjzz_amt42, --月均转账金额
max(case when id2=2 and cardtype='JJK' then hk_month_num end) as hk_month_num42, --还款分布月份数
max(case when id2=2 and cardtype='JJK' then  round(c.yjhk_amt,2) end) as yjhk_amt42,--月均还款金额
max(case when id2=2 and cardtype='JJK' then round(c.bjhk_amt,2) end) as bjhk_amt42,--笔均还款金额

max(case when id2=3 and cardtype='JJK' then cardno end) as cardno43, --卡号
max(case when id2=3 and cardtype='JJK' then bank_name end) as bank_name43, --开户银行
max(case when id2=3 and cardtype='JJK' then  round(b.zz_amt,2)  end) as zz_amt43, --转账交易金额
max(case when id2=3 and cardtype='JJK' then  zz_cnt  end) as zz_cnt43, --转账交易次数
max(case when id2=3 and cardtype='JJK' then   round(b.yjzz_amt,2) end) as yjzz_amt43, --月均转账金额
max(case when id2=3 and cardtype='JJK' then hk_month_num end) as hk_month_num43, --还款分布月份数
max(case when id2=3 and cardtype='JJK' then  round(c.yjhk_amt,2) end) as yjhk_amt43,--月均还款金额
max(case when id2=3 and cardtype='JJK' then round(c.bjhk_amt,2) end) as bjhk_amt43  --笔均还款金额
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
from mid_merge_card_2y
group by mobile_num,cardno,card_type
) a
group by mobile;
EOF
