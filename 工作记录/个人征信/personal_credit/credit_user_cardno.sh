#!/bin/sh
TODAY_1Y=$(date --date='1 year ago' +%Y%m%d)

sudo -u hdfs hive <<EOF
use lakala;
drop table mid_mobile_card_details;
create table mid_mobile_card_details as
select mobile_num,outcdno as cardno,out_card_type as cardtype,max(data_date) as modifytime,
count(1) as hkjycs,row_number()over(partition by mobile_num order by max(data_date) desc) as thelast_id
from lkl_trans_agent_perday 
where tcat_lv4_code='A040001' and ymd>='${TODAY_1Y}' 
group by mobile_num,outcdno,out_card_type;

insert into table mid_mobile_card_details 
select mobile_num,incdno,in_card_type,max(data_date) as modifytime,
count(1) as hkjycs,row_number()over(partition by mobile_num order by max(data_date) desc) as thelast_id
from lkl_trans_agent_perday
where tcat_lv4_code='A040001' and ymd>='${TODAY_1Y}' 
group by mobile_num,incdno,in_card_type;

drop table credit_userinfo;
create table credit_userinfo as
select mobileno,mobileid from
(select mobile_num as mobileno,row_number() over(order by mobile_num desc) as mobileid,
sum(hkjycs) as hkjycs,sum(case when cardtype='XYK' then 1 else 0 end) as xyknum,sum(case when cardtype='JJK' then 1 else 0 end) as jjknum
from mid_mobile_card_details group by mobile_num) t
where hkjycs<120 and jjknum<15 and xyknum<30 ;

drop table credit_bankcardinfo;
create table credit_bankcardinfo as
select a.cardno,
case when a.cardtype='JJK' then '1' else '2' end as cardtype,
b.mobileid,
0 as cardstate,
a.modifytime,
case when a.thelast_id=1 then 1 else 0 end as thelast
from mid_mobile_card_details a,credit_userinfo b 
where a.mobile_num=b.mobileno and a.thelast_id<=10;
EOF

