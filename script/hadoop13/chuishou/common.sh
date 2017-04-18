#!/bin/sh
cd /home/hdfs/cuishou
source  /etc/profile
TODAY_2Y=$(date --date='2 year ago' +%Y%m%d)
#YMDDAY='20151111'
YMDDAY=$(date --date='2 day ago' +%Y%m%d)
echo $YMDDAY
echo '--------个人信息数据-------'
sudo -u hdfs hive <<EOF
insert overwrite table  zhengxin.person_info_common_result
select  mobile,mobile_num,num  from (
select  mobile,mobile_num, count(*),
row_number() over(partition by mobile order by count(*) desc) as  num
from  zhengxin.person_info_common 
where mobile <> mobile_num and mobile_num  is not null  
group by mobile,mobile_num 
) t  where   num<4;
EOF
tmp=$?
NOWTIME=$(date +%Y-%m-%d/%H:%M:%S)
if test $tmp -eq 0; then
echo $NOWTIME'-------------个人信息数据表成功'$tmp>logs/logs_demands_failed 
else
echo $NOWTIME'-------------个人信息数据表失败'$tmp > logs/logs_demands_failed
exit -2;
fi

echo '--------信用卡交易数据-------'
sudo -u hdfs hive <<EOF
drop table zhengxin.mid_fact_trans_success;
create table zhengxin.mid_fact_trans_success
as 
select t1.mobile_num,
t1.data_date,
t1.outcdno,
t1.incdno,
t2.mobile,
t1.total_am,
t1.s_no,
t1.address,
t1.tcat_lv4_name,
t1.tcat_lv3_name,
t1.ymd 
from hdm.f_fact_trans_success_new_details t1 
left join (
select cardno,
mobile,
(in_usedcount+out_usedcount) as trans_cnt,
(case when out_lastuseddate is null then in_lastuseddate
     when in_lastuseddate is null then out_lastuseddate
     when out_lastuseddate is not null and in_lastuseddate is not null and out_lastuseddate>in_lastuseddate then out_lastuseddate
     when out_lastuseddate is not null and in_lastuseddate is not null and out_lastuseddate<in_lastuseddate then in_lastuseddate
     end) as last_trans_date,
row_number() over(partition by cardno order by in_usedcount+out_usedcount desc,
case when out_lastuseddate is null then in_lastuseddate
     when in_lastuseddate is null then out_lastuseddate
     when out_lastuseddate is not null and in_lastuseddate is not null and out_lastuseddate>in_lastuseddate then out_lastuseddate
     when out_lastuseddate is not null and in_lastuseddate is not null and out_lastuseddate<in_lastuseddate then in_lastuseddate
     end desc) as id
from  hdm.mobile_cardno_relation   c
where c.ymd='$YMDDAY' 
) t2 on t1.incdno=t2.cardno
where (t1.tcat_lv4_name='信用卡还款' or t1.tcat_lv3_name='转账汇款') and t1.ymd>='${TODAY_2Y}' and t2.id=1 ;

drop table zhengxin.trans_info_hk;
create table zhengxin.trans_info_hk ----被还款的3笔交易
as
select mobile,
hk_date,
case when trim(hk_addr) not rlike '^[0-9]$' then hk_addr else '未登记地址' end as hk_addr,
hk_amt/100 as hk_amt,
real_name,
mobile_num,
flag,
hk_id
from (
select a.mobile, --银行提供用户
       b.ymd as hk_date, --还款日期
       nvl(b.address,'APP') as hk_addr, --还款地点
       b.total_am as hk_amt, --还款金额
       d.real_name, --还款人姓名
       a.mobile_num, --还款人手机号
       '被还款' as flag,
       hk_id
from (
select a.mobile, --银行提供用户
       max(b.data_date) as hkdate_last, --还款最近日期
       count(*) as hk_cnt, --还款次数
       b.mobile_num, --还款人手机号
       row_number() over(partition by a.mobile order by count(*) desc,max(b.data_date) desc) as hk_id
from zhengxin.person_info_common  a
left join (select * from zhengxin.mid_fact_trans_success where tcat_lv4_name='信用卡还款') b on a.cardno=b.incdno
where a.card_type=1 and a.mobile<>b.mobile_num and b.mobile_num is not null
group by a.mobile,b.mobile_num
) a
left join (select * from zhengxin.mid_fact_trans_success where tcat_lv4_name='信用卡还款') b on a.mobile_num=b.mobile_num and a.hkdate_last=b.data_date
left join hdm.u_user_identity_info d on a.mobile_num=d.mobile
where a.hk_id<=3
group by a.mobile,b.ymd,nvl(b.address,'APP'),d.real_name,b.total_am,a.mobile_num,a.hk_id
union all ----替人还款的3笔交易
select a.mobile, --银行提供用户
       b.ymd as hk_date, --还款日期
       nvl(b.address,'APP') as hk_addr, --还款地点
       b.total_am as hk_amt, --还款金额
       d.real_name, --被还款人姓名
       a.mobile_num, --被还款人手机号
       '替人还款' as flag,
       hk_id
from (
select a.mobile, --银行提供用户
       max(b.data_date) as hkdate_last, --还款最近日期
       count(*) as hk_cnt, --被还款次数
       b.mobile as mobile_num, --被还款人手机号
       row_number() over(partition by a.mobile order by count(*) desc,max(b.data_date) desc) as hk_id
from zhengxin.person_info_common  a
left join (select * from zhengxin.mid_fact_trans_success where tcat_lv4_name='信用卡还款') b on a.cardno=b.outcdno
where a.card_type=0 and a.mobile<>b.mobile and b.mobile is not null
group by a.mobile,b.mobile
) a
left join (select * from zhengxin.mid_fact_trans_success where tcat_lv4_name='信用卡还款') b on a.mobile_num=b.mobile and a.hkdate_last=b.data_date
left join hdm.u_user_identity_info d on a.mobile_num=d.mobile
where a.hk_id<=3
group by a.mobile,b.ymd,nvl(b.address,'APP'),d.real_name,b.total_am,a.mobile_num,a.hk_id
) t;
EOF
tmp=$?
NOWTIME=$(date +%Y-%m-%d/%H:%M:%S)
if test $tmp -eq 0; then
echo $NOWTIME'--------------信用卡交易数据表成功'$tmp 
else
echo $NOWTIME'--------------信用卡交易数据表失败'$tmp >> logs/logs_demands_failed
exit -2;
fi

echo '--------转账交易数据--------'
sudo -u hdfs hive <<EOF
drop table zhengxin.trans_info_zz;
create table zhengxin.trans_info_zz 
as
select mobile,
zz_date,
case when trim(zz_addr) not rlike '^[0-9]$' then zz_addr else '未登记地址' end as zz_addr,
zz_amt/100 as zz_amt,
real_name,
mobile_num,
flag,
zz_id
from (  ----被转账的3笔交易
select a.mobile, --银行提供用户
       b.ymd as zz_date, --转入日期
       nvl(b.address,'APP') as zz_addr, --转入地点
       b.total_am as zz_amt, --转入金额
       d.real_name, --转账人姓名
       a.mobile_num, --转账人手机号
       '被转账' as flag,
       zz_id
from (
select a.mobile, --银行提供用户
       max(b.data_date) as zzdate_last, --转账最近日期
       count(*) as zz_cnt, --转账次数
       b.mobile_num, --转账人手机号
       row_number() over(partition by a.mobile order by count(*) desc,max(b.data_date) desc) as zz_id
from zhengxin.person_info_common  a
left join (select * from zhengxin.mid_fact_trans_success where tcat_lv3_name='转账汇款') b on a.cardno=b.incdno
where a.card_type=0 and a.mobile<>b.mobile_num and b.mobile_num is not null
group by a.mobile,b.mobile_num 
) a
left join (select * from zhengxin.mid_fact_trans_success where tcat_lv3_name='转账汇款') b on a.mobile_num=b.mobile_num and a.zzdate_last=b.data_date
left join hdm.u_user_identity_info d on a.mobile_num=d.mobile
where a.zz_id<=3
group by a.mobile,b.ymd,nvl(b.address,'APP'),d.real_name,b.total_am,a.mobile_num,a.zz_id
union all ----替人转账的3笔交易
select a.mobile, --银行提供用户
       b.ymd as zz_date, --转出日期
       nvl(b.address,'APP') as zz_addr, --转出地点
       b.total_am as zz_amt, --转出金额
       d.real_name, --被转账人姓名
       a.mobile_num, --被转账人手机号
       '替人转账' as flag,
       zz_id
from (
select a.mobile, --银行提供用户
       max(b.data_date) as zzdate_last, --转出最近日期
       count(*) as zz_cnt, --转出次数
       b.mobile as mobile_num, --被转账人手机号
       row_number() over(partition by a.mobile order by count(*) desc,max(b.data_date) desc) as zz_id
from zhengxin.person_info_common  a
left join (select * from zhengxin.mid_fact_trans_success where tcat_lv3_name='转账汇款') b on a.cardno=b.outcdno
where a.card_type=0 and a.mobile<>b.mobile and b.mobile is not null
group by a.mobile,b.mobile
) a
left join (select * from zhengxin.mid_fact_trans_success where tcat_lv3_name='转账汇款') b on a.mobile_num=b.mobile and a.zzdate_last=b.data_date
left join hdm.u_user_identity_info d on a.mobile_num=d.mobile
where a.zz_id<=3
group by a.mobile,b.ymd,nvl(b.address,'APP'),d.real_name,b.total_am,a.mobile_num,a.zz_id
) t;
EOF
tmp=$?
NOWTIME=$(date +%Y-%m-%d/%H:%M:%S)
if test $tmp -eq 0; then
echo $NOWTIME'--------------转账交易数据表成功'$tmp 
else
echo $NOWTIME'--------------转账交易数据表失败'$tmp >> logs/logs_demands_failed
exit -2;
fi


echo '----------充值交易数据--------'
sudo -u hdfs hive <<EOF
add jar /home/hdfs/lakala_udf.jar;
create temporary function IDCard as 'com.lkl.hive.udf.IDCard';
create temporary function mobile as 'com.lkl.hive.udf.Mobile';

drop table zhengxin.trans_info_cz;
create table zhengxin.trans_info_cz
as
select a.mobile, --银行提供用户
       b.ymd as cz_date, --充值日期
       nvl(b.address,'APP') as cz_addr, --充值地点
       b.total_am/100 as cz_amt, --充值金额
       d.real_name, --被充值人姓名
       a.incdno as mobile_num, --被充值人手机号
       cz_id
from (
select a.mobile, --银行提供用户
       max(b.data_date) as czdate_last, --充值最近日期
       count(*) as cz_cnt, --充值次数
       b.incdno, --被充值人手机号
       row_number() over(partition by a.mobile order by count(*) desc,max(b.data_date) desc) as cz_id
from  zhengxin.person_info_common  a
left join (select mobile_num,data_date,outcdno,incdno,total_am,s_no,ymd from hdm.f_fact_trans_success_new_details 
           where (tcat_lv3_name='话费充值' or (tcat_lv3_name='公缴类' and agent_name like '%手机%')) and ymd>='${TODAY_2Y}') b on a.cardno=b.outcdno
where a.mobile<>b.incdno and mobile(b.incdno)=1
group by a.mobile,b.incdno 
) a
left join (select mobile_num,data_date,outcdno,incdno,total_am,s_no,case when trim(address) not rlike '^[0-9]$' then address else '未登记地址' end as address,ymd 
           from hdm.f_fact_trans_success_new_details 
           where (tcat_lv3_name='话费充值' or (tcat_lv3_name='公缴类' and agent_name like '%手机%')) and ymd>='${TODAY_2Y}') b on a.incdno=b.incdno and a.czdate_last=b.data_date
left join hdm.u_user_identity_info d on a.incdno=d.mobile
where a.cz_id<=3
group by a.mobile,b.ymd,nvl(b.address,'APP'),d.real_name,b.total_am,a.incdno,a.cz_id;
EOF
tmp=$?
NOWTIME=$(date +%Y-%m-%d/%H:%M:%S)
if test $tmp -eq 0; then
echo $NOWTIME'--------------充值交易数据表成功'$tmp 
else
echo $NOWTIME'--------------充值交易数据表失败'$tmp >> logs/logs_demands_failed
exit -2;
fi


echo '-----------水电气缴费交易数据--------'
sudo -u hdfs hive <<EOF
drop table zhengxin.trans_info_gj;
create table zhengxin.trans_info_gj ----水电气缴费的3笔交易
as
select a.mobile, --银行提供用户
       b.ymd as gj_date, --公缴日期
       nvl(b.address,'APP') as gj_addr_cz, --操作地点
       product_name  as gj_addr_jf,--缴费地址
       gj_id
from 
(select * from (
select a.mobile, --银行提供用户
       max(b.data_date) as gjdate_last, --公缴最近日期
       count(*) as gj_cnt, --公缴次数
       b.incdno, --公缴家庭号
       row_number() over(partition by a.mobile order by count(*) desc,max(b.data_date) desc) as gj_id
from  zhengxin.person_info_common  a
left join (select mobile_num,data_date,outcdno,incdno,total_am,s_no,ymd from hdm.f_fact_trans_success_new_details where tcat_lv4_name='公缴费-煤气水电' and ymd>='${TODAY_2Y}') b on a.cardno=b.outcdno
where b.incdno is not null
group by a.mobile,b.incdno) t1 where gj_id<=3
) a
left join (select mobile_num,data_date,outcdno,incdno,total_am,s_no,case when trim(address) not rlike '^[0-9]$' then address else '未登记地址' end as address, f.product_name,e.ymd 
           from hdm.f_fact_trans_success_new_details  e join   hds.nr_bill  f on  e.sid=f.qsid  where tcat_lv4_name='公缴费-煤气水电' and e.ymd>='${TODAY_2Y}' and  f.ymd>='${TODAY_2Y}') b on a.incdno=b.incdno and a.gjdate_last=b.data_date 
group by a.mobile,b.ymd,nvl(b.address,'APP'),product_name, a.gj_id;
EOF
tmp=$?
NOWTIME=$(date +%Y-%m-%d/%H:%M:%S)
if test $tmp -eq 0; then
echo $NOWTIME'--------------水电气缴费交易数据表成功'$tmp 
else
echo $NOWTIME'--------------水电气缴费交易数据表失败'$tmp >> logs/logs_demands_failed
exit -2;
fi
echo '--------新宽表-------'
sudo -u hdfs hive <<EOF
--新宽表个人信息
drop table zhengxin.mid_person_info_common;
create table zhengxin.mid_person_info_common 
as
select  
mobile ,
max(case  when num =1  then   mobile_num  end ) as mobile_num1,
max(case  when num =2  then   mobile_num  end ) as mobile_num2,
max(case  when num =3  then   mobile_num  end ) as mobile_num3
from  zhengxin.person_info_common_result   group by mobile;

drop table zhengxin.mid_trans_info_hk;
create table zhengxin.mid_trans_info_hk ----信用卡还款
as
select mobile,
max(case  when hk_id=1 and flag='被还款'   then   hk_date  end ) as hk_date1,
max(case  when hk_id=2 and flag='被还款'   then   hk_date  end ) as hk_date2,
max(case  when hk_id=3 and flag='被还款'   then   hk_date  end ) as hk_date3,
max(case  when hk_id=1 and flag='被还款'   then   hk_addr  end ) as hk_addr1,
max(case  when hk_id=2 and flag='被还款'   then   hk_addr  end ) as hk_addr2,
max(case  when hk_id=3 and flag='被还款'   then   hk_addr  end ) as hk_addr3,
max(case  when hk_id=1 and flag='被还款'   then   hk_amt  end ) as hk_amt1,
max(case  when hk_id=2 and flag='被还款'   then   hk_amt  end ) as hk_amt2,
max(case  when hk_id=3 and flag='被还款'   then   hk_amt  end ) as hk_amt3,
max(case  when hk_id=1 and flag='被还款'   then   real_name  end ) as hk_real_name1,
max(case  when hk_id=2 and flag='被还款'   then   real_name  end ) as hk_real_name2,
max(case  when hk_id=3 and flag='被还款'   then   real_name  end ) as hk_real_name3,
max(case  when hk_id=1 and flag='被还款'   then   mobile_num  end ) as hk_mobile_num1,
max(case  when hk_id=2 and flag='被还款'   then   mobile_num  end ) as hk_mobile_num2,
max(case  when hk_id=3 and flag='被还款'   then   mobile_num  end ) as hk_mobile_num3,
max(case  when hk_id=1 and flag='替人还款'   then   hk_date  end ) as hk_date12,
max(case  when hk_id=2 and flag='替人还款'   then   hk_date  end ) as hk_date22,
max(case  when hk_id=3 and flag='替人还款'   then   hk_date  end ) as hk_date32,
max(case  when hk_id=1 and flag='替人还款'   then   hk_addr  end ) as hk_addr12,
max(case  when hk_id=2 and flag='替人还款'   then   hk_addr  end ) as hk_addr22,
max(case  when hk_id=3 and flag='替人还款'   then   hk_addr  end ) as hk_addr32,
max(case  when hk_id=1 and flag='替人还款'   then   hk_amt  end ) as hk_amt12,
max(case  when hk_id=2 and flag='替人还款'   then   hk_amt  end ) as hk_amt22,
max(case  when hk_id=3 and flag='替人还款'   then   hk_amt  end ) as hk_amt32,
max(case  when hk_id=1 and flag='替人还款'   then   real_name  end ) as hk_real_name12,
max(case  when hk_id=2 and flag='替人还款'   then   real_name  end ) as hk_real_name22,
max(case  when hk_id=3 and flag='替人还款'   then   real_name  end ) as hk_real_name32,
max(case  when hk_id=1 and flag='替人还款'   then   mobile_num  end ) as hk_mobile_num12,
max(case  when hk_id=2 and flag='替人还款'   then   mobile_num  end ) as hk_mobile_num22,
max(case  when hk_id=3 and flag='替人还款'   then   mobile_num  end ) as hk_mobile_num32
from   zhengxin.trans_info_hk  group by mobile;


drop table zhengxin.mid_trans_info_zz;
create table zhengxin.mid_trans_info_zz ----银行转账
as
select mobile,
max(case  when zz_id=1 and flag='被转账'   then   zz_date  end ) as zz_date1,
max(case  when zz_id=2 and flag='被转账'   then   zz_date  end ) as zz_date2,
max(case  when zz_id=3 and flag='被转账'   then   zz_date  end ) as zz_date3,
max(case  when zz_id=1 and flag='被转账'   then   zz_addr  end ) as zz_addr1,
max(case  when zz_id=2 and flag='被转账'   then   zz_addr  end ) as zz_addr2,
max(case  when zz_id=3 and flag='被转账'   then   zz_addr  end ) as zz_addr3,
max(case  when zz_id=1 and flag='被转账'   then   zz_amt  end ) as zz_amt1,
max(case  when zz_id=2 and flag='被转账'   then   zz_amt  end ) as zz_amt2,
max(case  when zz_id=3 and flag='被转账'   then   zz_amt  end ) as zz_amt3,
max(case  when zz_id=1 and flag='被转账'   then   real_name  end ) as zz_real_name1,
max(case  when zz_id=2 and flag='被转账'   then   real_name  end ) as zz_real_name2,
max(case  when zz_id=3 and flag='被转账'   then   real_name  end ) as zz_real_name3,
max(case  when zz_id=1 and flag='被转账'   then   mobile_num  end ) as zz_mobile_num1,
max(case  when zz_id=2 and flag='被转账'   then   mobile_num  end ) as zz_mobile_num2,
max(case  when zz_id=3 and flag='被转账'   then   mobile_num  end ) as zz_mobile_num3,
max(case  when zz_id=1 and flag='替人转账'   then   zz_date  end ) as zz_date12,
max(case  when zz_id=2 and flag='替人转账'   then   zz_date  end ) as zz_date22,
max(case  when zz_id=3 and flag='替人转账'   then   zz_date  end ) as zz_date32,
max(case  when zz_id=1 and flag='替人转账'   then   zz_addr  end ) as zz_addr12,
max(case  when zz_id=2 and flag='替人转账'   then   zz_addr  end ) as zz_addr22,
max(case  when zz_id=3 and flag='替人转账'   then   zz_addr  end ) as zz_addr32,
max(case  when zz_id=1 and flag='替人转账'   then   zz_amt  end ) as zz_amt12,
max(case  when zz_id=2 and flag='替人转账'   then   zz_amt  end ) as zz_amt22,
max(case  when zz_id=3 and flag='替人转账'   then   zz_amt  end ) as zz_amt32,
max(case  when zz_id=1 and flag='替人转账'   then   real_name  end ) as zz_real_name12,
max(case  when zz_id=2 and flag='替人转账'   then   real_name  end ) as zz_real_name22,
max(case  when zz_id=3 and flag='替人转账'   then   real_name  end ) as zz_real_name32,
max(case  when zz_id=1 and flag='替人转账'   then   mobile_num  end ) as zz_mobile_num12,
max(case  when zz_id=2 and flag='替人转账'   then   mobile_num  end ) as zz_mobile_num22,
max(case  when zz_id=3 and flag='替人转账'   then   mobile_num  end ) as zz_mobile_num32
from  zhengxin.trans_info_zz   group by mobile;


drop table zhengxin.mid_trans_info_cz;
create table zhengxin.mid_trans_info_cz ---充值
as
select mobile,
max(case  when cz_id=1   then   cz_date  end ) as cz_date1,
max(case  when cz_id=2   then   cz_date  end ) as cz_date2,
max(case  when cz_id=3   then   cz_date  end ) as cz_date3,
max(case  when cz_id=1   then   cz_addr  end ) as cz_addr1,
max(case  when cz_id=2   then   cz_addr  end ) as cz_addr2,
max(case  when cz_id=3   then   cz_addr  end ) as cz_addr3,
max(case  when cz_id=1   then   cz_amt  end ) as cz_amt1,
max(case  when cz_id=2   then   cz_amt  end ) as cz_amt2,
max(case  when cz_id=3   then   cz_amt  end ) as cz_amt3,
max(case  when cz_id=1   then   real_name  end ) as cz_real_name1,
max(case  when cz_id=2   then   real_name  end ) as cz_real_name2,
max(case  when cz_id=3   then   real_name  end ) as cz_real_name3,
max(case  when cz_id=1   then   mobile_num  end ) as cz_mobile_num1,
max(case  when cz_id=2   then   mobile_num  end ) as cz_mobile_num2,
max(case  when cz_id=3   then   mobile_num  end ) as cz_mobile_num3
from zhengxin.trans_info_cz group by mobile;


drop table zhengxin.mid_trans_info_gj;
create table zhengxin.mid_trans_info_gj ---公缴
as
select mobile,
max(case  when gj_id=1   then   gj_date  end ) as gj_date1,
max(case  when gj_id=2   then   gj_date  end ) as gj_date2,
max(case  when gj_id=3   then   gj_date  end ) as gj_date3,
max(case  when gj_id=1   then   gj_addr_cz  end ) as gj_addr_czs1,
max(case  when gj_id=2   then   gj_addr_cz  end ) as gj_addr_czs2,
max(case  when gj_id=3   then   gj_addr_cz  end ) as gj_addr_czs3
from zhengxin.trans_info_gj  group by mobile;
EOF
tmp=$?
NOWTIME=$(date +%Y-%m-%d/%H:%M:%S)
if test $tmp -eq 0; then
echo $NOWTIME'--------------宽表中间表成功'$tmp 
else
echo $NOWTIME'--------------宽表中间表失败'$tmp >> logs/logs_demands_failed
exit -2;
fi
