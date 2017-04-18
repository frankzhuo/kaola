#!/bin/sh
for date in 201310 201311 201312	201401	201402	201403	201404	201405	201406	201407	201408	201409	201410	201411	201412	201501
do
startDate=$date'28'
endDate=20150401
startSec=$(date -d $startDate +%s)
endSec=$(date -d $endDate +%s)
for((i=$startSec;i<$endSec;i+=86400*30))
do
Mon=$(date -d @$i +%Y%m)
impala-shell -r <<EOF
use lakala;
insert into table mpos_xyk_cardno_result 
select '${date}',cast(count(*) as int),'${Mon}'
from 
(select * from lakala.credit_card_no_permonth where ym='${date}' and transe_type='18X') a,
(select * from lakala.credit_card_no_permonth where ym='${Mon}' and transe_type='18X') b
where a.cardno=b.cardno;
EOF
done
done
