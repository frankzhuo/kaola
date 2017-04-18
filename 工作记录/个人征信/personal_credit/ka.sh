TODAY_2Y=$(date --date='2 year ago' +%Y%m%d)
YMDDAY='20150913'
echo '---------个人信息匹配汇总----------'
sudo -u hdfs hive <<EOF
drop table  zhengxin.top3;
create table zhengxin.top3
as 
select  mobile_num,cardno ,total_am,card_type  from (
select   mobile_num,outcdno as cardno,total_am,out_card_type  as card_type
from   hdm.f_fact_trans_success_new_details  where ymd >= '20131010'
union all
select   mobile_num,incdno  as cardno ,total_am,in_card_type  as card_type
from   hdm.f_fact_trans_success_new_details  where ymd >= '20131010'
) a

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
where rank_desc<=3
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


