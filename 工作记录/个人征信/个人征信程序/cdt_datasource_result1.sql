#!/bin/sh
TODAY_2Y=$(date --date='2 year ago' +%Y%m%d)
TODAY=$(date +%Y%m%d)
row_cnt_0=$(sudo -u hdfs hdfs dfs -cat /user/hive/warehouse/creditck.db/mid_zx_xyk_card/* | wc -l)
row_cnt2_0=$(sudo -u hdfs hdfs dfs -cat /user/hive/warehouse/creditck.db/mid_mobile_outcdno/* | wc -l)


echo '---------------------创建中间表-------------------'
sudo -u hdfs hive <<EOF
use creditck;
drop table creditck.mid_zx_xyk_card;
drop table creditck.mid_mobile_outcdno;
EOF

impala-shell -r <<EOF
create table creditck.mid_zx_xyk_card
as
select mobile_num,
outcdno,
outcdno_bin,
out_card_type,
out_bank_name 
from hdm.f_fact_trans_success_new_details
where length(mobile_num)=11 and ymd>='${TODAY_2Y}' 
group by mobile_num,outcdno,outcdno_bin,out_card_type,out_bank_name;

insert into table creditck.mid_zx_xyk_card
select mobile_num,
incdno as outcdno,
incdno_bin as outcdno_bin, 
in_card_type as out_card_type,
in_bank_name
from hdm.f_fact_trans_success_new_details
where tcat_lv4_name = '信用卡还款' and length(mobile_num)=11 and ymd>='${TODAY_2Y}' 
group by mobile_num, incdno, incdno_bin, in_card_type,in_bank_name;

create table creditck.mid_mobile_outcdno
as
select a.outcdno,a.mobile_num,'${TODAY}' as data_date
from
(select outcdno,mobile_num,count(1) as jynum,row_number()over(partition by outcdno order by count(1) desc) as id
from edm.f_fact_trans_success_new where length(mobile_num)=11 and length(outcdno)>10 group by mobile_num,outcdno) a
where a.id=1;
EOF
tmp=$?
NOWTIME=$(date +%Y-%m-%d/%H:%M:%S)
row_cnt=$(sudo -u hdfs hdfs dfs -cat /user/hive/warehouse/creditck.db/mid_zx_xyk_card/* | wc -l)
row_cnt2=$(sudo -u hdfs hdfs dfs -cat /user/hive/warehouse/creditck.db/mid_mobile_outcdno/* | wc -l)
if test $tmp -eq 0 -a $row_cnt -gt $row_cnt_0 -a $row_cnt2 -gt $row_cnt2_0; then
echo $NOWTIME'--------------创建中间表成功'$tmp >> logs/logs_cdt_datasource_result
exit 1;
else
echo $NOWTIME'--------------创建中间表失败'$tmp >> logs/logs_cdt_datasource_result
exit -2;
fi
