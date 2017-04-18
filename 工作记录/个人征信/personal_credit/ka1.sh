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
