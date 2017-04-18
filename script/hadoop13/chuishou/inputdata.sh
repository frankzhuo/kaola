#!/bin/sh
cd /home/hdfs/cuishou
TODAY_2Y=$(date --date='2 year ago' +%Y%m%d)
YMDDAY=$(date --date='2 day ago' +%Y%m%d)
echo $YMDDAY
echo '---------个人卡信息汇总----------'
sudo -u hdfs hive <<EOF
insert overwrite table  zhengxin.person_info_common
select mobile,mobile_num,cardno,card_type from ( --手机号查找其它卡号
select a.mobilephone as mobile,
       a.mobilephone as mobile_num,
       case when a.account  is    null  or (a.account  is  not  null  and  a.account<>b.cardno ) then b.cardno end as cardno,
       case when a.account  is    null  or (a.account  is  not  null  and  a.account<>b.cardno ) then b.cardtype end as card_type
from zhengxin.cuishou_input a
join hdm.mobile_cardno_relation b on a.mobilephone=b.mobile 
where b.ymd='$YMDDAY'
union all ----卡号找其它手机号，手机号再找卡号
select a.mobilephone as mobile,
       case when a.mobilephone<>c.mobile then c.mobile end as mobile_num,
       case when  a.account  is    null  or (a.account  is  not  null  and  a.account<>d.cardno )  and  a.mobilephone<>c.mobile then d.cardno end as cardno,
       case when  a.account  is    null  or (a.account  is  not  null  and  a.account<>d.cardno ) and   a.mobilephone<>c.mobile then d.cardtype end as card_type
from zhengxin.cuishou_input  a
join hdm.mobile_cardno_relation c on a.account=c.cardno
join hdm.mobile_cardno_relation d on c.mobile=d.mobile
where c.ymd='$YMDDAY'  and  d.ymd='$YMDDAY'
union all ----身份证找出手机号，手机号再找卡号
select a.mobilephone as mobile,
       case when a.mobilephone<>b.mobile then b.mobile end as mobile_num,
       case when  a.account  is    null  or (a.account  is  not  null  and  a.account<>c.cardno )  and  a.mobilephone<>c.mobile then c.cardno end as cardno,
       case when  a.account  is    null  or (a.account  is  not  null  and  a.account<>c.cardno ) and   a.mobilephone<>c.mobile then c.cardtype end as card_type
from zhengxin.cuishou_input   a
join hdm.u_user_identity_info b on a.idcard=b.identity_card
join  hdm.mobile_cardno_relation c on b.mobile=c.mobile
where c.ymd='$YMDDAY'
) t group by mobile,mobile_num,cardno,card_type;
EOF
tmp=$?
NOWTIME=$(date +%Y-%m-%d/%H:%M:%S)
if test $tmp -eq 0; then
echo $NOWTIME'--------------个人卡信息汇总表成功'$tmp 
else
echo $NOWTIME'--------------个人卡信息汇总表失败'$tmp > logs/logs_input_failed
exit -2
fi
echo '---------个人疑似信息匹配汇总----------'
sudo -u hdfs hive <<EOF
drop table zhengxin.self_info;
create table zhengxin.self_info
as
select mobile,mobile_num,min(num) as num  from ( 
select a.mobilephone as mobile,
       case when a.mobilephone<>b.mobile then b.mobile end as mobile_num,
       0 as num
from zhengxin.cuishou_input   a
join hdm.u_user_identity_info b on a.idcard=b.identity_card
union all ----卡找其它手机号，手机号再找卡号
select  mobile,mobile_num,num from (
select a.mobilephone  as mobile,
       case when a.mobilephone<>c.mobile then c.mobile end as mobile_num,
       count(*),
       row_number() over(partition by a.mobilephone  order by count(*)  desc) as num
from zhengxin.cuishou_input  a
join hdm.mobile_cardno_relation   c on a.account=c.cardno  and  c.ymd=$YMDDAY
where  a.mobilephone <> c.mobile
group by  a.mobilephone,c.mobile
)a  where  num <4
) t
where   mobile_num is not  null
group by  mobile, mobile_num
order by num
;
EOF
tmp=$?
NOWTIME=$(date +%Y-%m-%d/%H:%M:%S)
if test $tmp -eq 0; then
echo $NOWTIME'--------------个人疑似信息匹配汇总表成功'$tmp 
else
echo $NOWTIME'--------------个人疑似信息匹配汇总表失败'$tmp > logs/logs_self_failed
exit -2;
fi
