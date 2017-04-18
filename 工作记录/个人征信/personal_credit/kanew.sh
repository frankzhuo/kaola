TODAY_2Y=$(date --date='2 year ago' +%Y%m%d)


echo '----------最近2年交易中间表--------------'
sudo -u hdfs hive <<EOF
drop table  zhengxin.top3;
create table zhengxin.top3
as 
select  mobile_num,cardno ,total_am,card_type  from (
select   mobile_num,outcdno as cardno,total_am,out_card_type  as card_type
from   hdm.f_fact_trans_success_new_details  where ymd >='${TODAY_2Y}'
union all
select   mobile_num,incdno  as cardno ,total_am,in_card_type  as card_type
from   hdm.f_fact_trans_success_new_details  where ymd >='${TODAY_2Y}'
) a；
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
select  mobile_num,cardno ,sum(total_am)  ,row_number() over(partition by mobile_num order by sum(total_am)/100 desc)   as  rank_desc from  zhengxin.top32
where  card_type='XYK'
group  by  mobile_num,cardno
)
b
where rank_desc<=3
union all
select  mobile_num,cardno ,rank_desc,2 as stat_type
from (
select  mobile_num,cardno ,count(1)  ,row_number() over(partition by mobile_num order by count(1) desc)   as  rank_desc from  zhengxin.top32
where  card_type='XYK'
group  by  mobile_num,cardno
)
b
where rank_desc<=3;
union all
select  mobile_num,cardno ,rank_desc ,3 as stat_type
from (
select  mobile_num,cardno ,sum(total_am)  ,row_number() over(partition by mobile_num order by sum(total_am)/100 desc)   as  rank_desc from  zhengxin.top32
where  card_type='JJK'
group  by  mobile_num,cardno
)
b
where rank_desc<=3
union all
select  mobile_num,cardno ,rank_desc,4 as stat_type
from (
select  mobile_num,cardno ,count(1)  ,row_number() over(partition by mobile_num order by count(1) desc)   as  rank_desc from  zhengxin.top32
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


