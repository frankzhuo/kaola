#!/bin/sh
TODAY_1D=$(date --date='1 day ago' +%Y%m%d)
TODAY_2D=$(date --date='2 day ago' +%Y%m%d)
TODAY_3D=$(date --date='3 day ago' +%Y%m%d)


echo  '-------------------下面开始整合---------------'
sudo -u hdfs hive <<EOF
use creditck;
create table if not exists cdt_result_source(
mobile_num string,
zw_time int,
person_status string,
credit_count bigint,
debit_count bigint,
credit_class string,
debit_class string,
debit_kind string,
credit_bjhk double,
credit_yjhk double,
credit_hkmonth bigint,
credit_hklast int,
debit_in bigint,
debit_out bigint,
debit_bjzz double,
debit_zzmonth bigint,
debit_yjzz double,
zdfq_count string,
mobile_kind string,
phonecharge_permonth double,
pubpay_sum double,
pubpay_count bigint,
pubpay_ave double,
ebus_sum double,
ebus_count bigint,
virtual_sum double,
jy_time bigint,
device_ss string,
device_skb string,
person_living string,
person_sex int,
person_age int,
person_hukou string,
person_income string,
person_position int,
person_education string,
xd_count bigint,
xd_sum double,
xd_ave double,
xd_status int,
debit_cxmonth bigint,
credit_limit double,
credit_rate double,
credit_balance double,
credit_cxmonth bigint,
debit_balance double ,
credit_hkwdx double,
credit_hkcs bigint,
sharebal_day double,
qb_balance_max double,
donation double,
baoxian double,
is_black int,
black_type string)
partitioned by(ymd string);

insert overwrite table cdt_result_source partition (ymd='${TODAY_1D}')
select t1.mobile_num,t1.zw_time,t1.person_status,
t2.credit_count,t2.debit_count,t2.credit_class,t2.debit_class,t2.debit_kind,
t6.credit_bjhk,t6.credit_yjhk,t6.credit_hkmonth,t6.credit_hklast,t6.debit_in,t6.debit_out,t6.debit_bjzz,t6.debit_zzmonth,t6.debit_yjzz,
t8.zdfq_count,
t10.mobile_kind,
t6.phonecharge_permonth,t6.pubpay_sum,t6.pubpay_count,t6.pubpay_ave,t6.ebus_sum,t6.ebus_count,t6.virtual_sum,t6.jy_time,
t16.device_ss,t16.device_skb,
t1.person_living,
t19.person_sex,t19.person_age,t19.person_hukou,
t21.person_income,t21.person_position,t21.person_education,
t24.xd_count,t24.xd_sum,t24.xd_ave,t24.xd_status,
t25.debit_cxmonth,
t26.credit_limit,t26.credit_rate,
t27.credit_balance,t27.credit_cxmonth,
t28.debit_balance,
t29.credit_hkwdx,
t6.credit_hkcs,
t31.sharebal_day,
t32.qb_balance_max,
t33.donation,
t33.baoxian,
t9.is_black,nvl(t9.black_type,'')
from var_user t1
left join var_cardno_lv_cnt t2 on t1.mobile_num=t2.mobile_num
left join var_trans t6 on t1.mobile_num=t6.mobile_num
left join var_zdfq_count t8 on t1.mobile_num=t8.mobile_num
left join var_blacklist_end t9 on t1.mobile_num=t9.mobile
left join var_mobile_kind t10 on t1.mobile_num=t10.mobile_num
left join var_device_ss_skb t16 on t1.mobile_num=t16.mobile_num
left join var_person_identity t19 on t1.mobile_num=t19.mobile_num
left join var_person_edu_in_pos t21 on t1.mobile_num=t21.mobile
left join var_wll_xd_loan t24 on t1.mobile_num=t24.mobile
left join var_debit_cxmonth t25 on t1.mobile_num=t25.mobile_num
left join var_credit_limit_rate t26 on t1.mobile_num=t26.mobile_num
left join var_credit_balance_cxmonth t27 on t1.mobile_num=t27.mobile_num
left join var_debit_balance t28 on t1.mobile_num=t28.mobile_num
left join var_credit_hkwdx t29 on t1.mobile_num=t29.mobile_num
left join var_sharebal_day t31 on t1.mobile_num=t31.mobile_num
left join var_qb_balance_max t32 on t1.mobile_num=t32.mobile
left join var_donation_baoxian t33 on t1.mobile_num=t33.mobile_num
;
EOF
tmp=$?
NOWTIME=$(date +%Y-%m-%d/%H:%M:%S)
if test $tmp -eq 0; then
echo $NOWTIME'--------------整合宽表cdt_result_source分区'$TODAY_1D'整合成功'$tmp >> logs/logs_cdt_datasource_result
else
echo $NOWTIME'--------------整合宽表cdt_result_source分区'$TODAY_1D'整合失败'$tmp >> logs/logs_cdt_datasource_result
exit -2;
fi


echo '----------------开始打分----------------'
sudo -u hdfs hive <<EOF
use creditck;
create table if not exists cdt_result_source_lvscore(
mobile_num string,
zw_time int,
person_status int,
credit_count int,
debit_count int,
credit_class int,
debit_class int,
debit_kind int,
credit_bjhk int,
credit_yjhk int,
credit_hkmonth int,
credit_hklast int,
debit_in int,
debit_out int,
debit_bjzz int,
debit_zzmonth int,
debit_yjzz int,
zdfq_count int,
is_black int,
black_type string,
mobile_kind int,
phonecharge_permonth int,
pubpay_sum int,
pubpay_count int,
pubpay_ave int,
ebus_sum int,
ebus_count int,
virtual_sum int,
jy_time int,
device_ss int,
device_skb int,
person_living int,
person_sex int,
person_age int,
person_hukou int,
person_income int,
person_position int,
person_education int,
xd_count int,
xd_sum int,
xd_ave int,
xd_status int,
debit_cxmonth int,
credit_limit int,
credit_rate int,
credit_balance int,
credit_cxmonth int,
debit_balance int,
credit_hkwdx int,
credit_hkcs int,
sharebal_day int,
qb_balance_max int,
donation int,
baoxian int)
partitioned by (ymd string);

insert overwrite table cdt_result_source_lvscore partition (ymd='${TODAY_1D}')
select mobile_num,
case when zw_time>=730 then 850
  when zw_time>=180 and zw_time<365 then 750
  when zw_time>=365 and zw_time<730 then 650
  when zw_time>=90 and zw_time<180 then 550
  when zw_time<30 then 420
  when zw_time>=30 and zw_time<90 then 300
  end as zw_time,
case when person_status=1 then 850
  when person_status=2 then 400
  when person_status=3 then 600
  end as person_status,
case when credit_count>=4 and credit_count<=15 then 850
  when credit_count>=2 and credit_count<4 then 740
  when credit_count>15 then 740
  when credit_count=1 then 630
  else 575
  end as credit_count,
case when debit_count>=5 and debit_count<=16 then 850
  when debit_count=4 then 795
  when debit_count>=17 then 740
  when debit_count>=2 and debit_count<4 then 685
  when debit_count=1 then 630
  else 300
  end as debit_count,
case when credit_class='0' then 850
  when credit_class='1' then 850
  when credit_class='2'then 700
  when credit_class='3' or credit_class='4'  then 600
  when credit_class='5' then 500
  else 500
  end as credit_class,
case when debit_class='1' then 850
  when debit_class='2' then 800
  when debit_class='3' then 740
  when debit_class='4' then 630
  when debit_class='5' then 500
  else 500
  end as debit_class,
case when debit_kind='1' then 850
  when debit_kind='0' then 630
  else 630
  end as debit_kind,
case when credit_bjhk>10000 then 850
  when credit_bjhk>5000 and credit_bjhk<=10000 then 795
  when credit_bjhk>2000 and credit_bjhk<=5000 then 740
  when credit_bjhk>0 and credit_bjhk<=2000 then 630
  else 300
  end as credit_bjhk,
case when credit_yjhk>9000 and credit_yjhk<=15000 then 740
  when credit_yjhk>15000 and credit_yjhk<=40000 then 795
  when credit_yjhk>40000 then 850
  when credit_yjhk>0 and credit_yjhk<=9000 then 630
  else 300
  end as credit_yjhk,
case when credit_hkmonth>=15 then 850
  when credit_hkmonth>=10 and credit_hkmonth<=14 then 800
  when credit_hkmonth>=5 and credit_hkmonth<=9 then 750
  when credit_hkmonth>=3 and credit_hkmonth<=4 then 700
  when credit_hkmonth>=1 and credit_hkmonth<=2 then 600
  else 300
  end as credit_hkmonth,
case when credit_hklast<=30 then 850
  when credit_hklast>=31 and credit_hklast<=90 then 700
  when credit_hklast>=91 and credit_hklast<=180 then 600
  when credit_hklast>=181 and credit_hklast<=360 then 500
  when credit_hklast>=361 and credit_hklast<=637 then 400
  else 300
  end as credit_hklast,
case when debit_in=1 then 750
  when debit_in=2 then 600
  when debit_in>=3 and debit_in<=6 then 500
  when debit_in>=7 and debit_in<=10 then 450
  when debit_in>10 then 300
  else 500
  end as debit_in,
case when debit_out=1 then 750
  when debit_out=2 then 600
  when debit_out=3 then 550
  when debit_out>=4 and debit_out<=6 then 500
  when debit_out>=7 and debit_out<=9 then 400
  when debit_out>9 then 300
  else 500
  end as debit_out,
case when debit_bjzz<=500 then 550
  when debit_bjzz>500 and debit_bjzz<=1500 then 750
  when debit_bjzz>1500 and debit_bjzz<=2500 then 600
  when debit_bjzz>2500 and debit_bjzz<=10000 then 550
  when debit_bjzz>10000 and debit_bjzz<=10500 then 850
  when debit_bjzz>10500 and debit_bjzz<=17000 then 350
  when debit_bjzz>17000 and debit_bjzz<=20500 then 400
 when debit_bjzz>20500  then 650
  else 630
  end as debit_bjzz,
case when debit_zzmonth>=4 then 850
  when debit_zzmonth>=2 and debit_zzmonth<4 then 795
  when debit_zzmonth>0 and debit_zzmonth<2 then 767
  else 300
  end as debit_zzmonth,
case when debit_yjzz<500 then 650
  when debit_yjzz>500 and debit_yjzz<=4500 then 750
  when debit_yjzz>4500 and debit_yjzz<=10000 then 600
  when debit_yjzz>10000 and debit_yjzz<=10500 then 850
  when debit_yjzz>10500 and debit_yjzz<=20500 then 550
  when debit_yjzz>20500 and debit_yjzz<=22500 then 400
  when debit_yjzz>22500 and debit_yjzz<=50500 then 350
  when debit_yjzz>50500 then 500
  else 630
  end as debit_yjzz,
case when zdfq_count='1' then 850
  else 300
  end as zdfq_count,
case when a.is_black=1 then 1
  else 0
  end as is_black,
case when a.is_black=1 then a.black_type end as black_type,
case when mobile_kind='3.4' then 850
  when mobile_kind='3.2' then 800
  when mobile_kind='2.8' then 700
  when mobile_kind='2.4' then 600
  when mobile_kind='1.8' then 300
  else 300
  end as mobile_kind,
case when phonecharge_permonth>=50 and phonecharge_permonth<=100 then 850
  when phonecharge_permonth<50 then 700
  when phonecharge_permonth>100 and phonecharge_permonth<=200 then 600
  when phonecharge_permonth>200 and phonecharge_permonth<=500 then 500
  when phonecharge_permonth>500 then 300
  when phonecharge_permonth is null and cast(b.charge as int)=1 then 850
  when phonecharge_permonth is null and cast(b.charge as int)=2 then 700
  when phonecharge_permonth is null and cast(b.charge as int)=3 then 600
  when phonecharge_permonth is null and cast(b.charge as int)=4 then 500
  when phonecharge_permonth is null and cast(b.charge as int)=5 then 300
  else 300
  end as phonecharge_permonth,
case when pubpay_sum>200 and pubpay_sum<=500 then 850
  when pubpay_sum>500 and pubpay_sum<=1000 then 750
  when pubpay_sum>=0 and pubpay_sum<=200 then 600
  when pubpay_sum>1000 and pubpay_sum<=2000 then 550
  when pubpay_sum>2000 and pubpay_sum<=5000 then 500
  when pubpay_sum>5000 then 450
  else 300
  end as pubpay_sum,
case when pubpay_count>=11 then 850
  when pubpay_count>=8 and pubpay_count<=10 then 750
  when pubpay_count>=5 and pubpay_count<=7 then 650
  when pubpay_count>=3 and pubpay_count<=4 then 550
  when pubpay_count=2 then 450
  when pubpay_count=1 then 350
  else 300
  end as pubpay_count,
case when pubpay_ave>100 and pubpay_ave<=200 then 850
  when pubpay_ave>=0 and pubpay_ave<=100 then 700
  when pubpay_ave>200 and pubpay_ave<=500 then 650
  when pubpay_ave>500 and pubpay_ave<=1000 then 600
  when pubpay_ave>2000 then 550
  when pubpay_ave>1000 and pubpay_ave<=2000 then 500
  else 300
  end as pubpay_ave,
case when ebus_sum>10000 then 850
  when ebus_sum>500 and ebus_sum<=2000 then 700
  when ebus_sum>=0 and ebus_sum<=200 then 600
  when ebus_sum>200 and ebus_sum<=500 then 580
  when ebus_sum>2000 and ebus_sum<=10000 then 550
  else 300
  end as ebus_sum,
case when ebus_count>=11 then 850
  when ebus_count>=8 and ebus_count<=10 then 750
  when ebus_count>=5 and ebus_count<=7 then 650
  when ebus_count>=3 and ebus_count<=4 then 550
  when ebus_count=2 then 450
  when ebus_count=1 then 350
  else 300
  end as ebus_count,
case when virtual_sum>100 and virtual_sum<=200 then 850
  when virtual_sum>30 and virtual_sum<=100 then 750
  when virtual_sum>=0 and virtual_sum<=30 then 600
  when virtual_sum>200 and virtual_sum<=500 then 580
  when virtual_sum>1000 and virtual_sum<=2000 then 550
  when virtual_sum>500 and virtual_sum<=1000 then 500
  when virtual_sum>2000 then 400
  else 300
  end as virtual_sum,
case when jy_time>8 then 850
  when jy_time>=5 and jy_time<=8 then 750
  when jy_time>=3 and jy_time<=4 then 650
  when jy_time=2 then 550
  when jy_time=1 then 450
  else 300
  end as jy_time,
case when device_ss='1' then 700
  else 450
  end as device_ss,
case when device_skb='1' then 700
  else 450
  end as device_skb,
case when person_living=1 then 750
  when person_living=2 then 500
  when person_living=3 then 550
  when person_living=4 then 500
  when person_living=5 then 450
  when person_living=6 then 300
  else 300
  end as person_living,
case when a.person_sex=0 then 600
  when a.person_sex=1 then 500
  when a.person_sex is null and cast(b.s_gender as int)=1 then 600
  when a.person_sex is null and cast(b.s_gender as int)=2 then 500
  else 550
  end as person_sex,
case when a.person_age<=18 then 300
  when a.person_age>=19 and a.person_age<=25 then 450
  when a.person_age>=26 and a.person_age<=30 then 700
  when a.person_age>=31 and a.person_age<=40 then 800
  when a.person_age>=41 and a.person_age<=50 then 850
  when a.person_age>=51 then 550
  when a.person_age is null and cast(b.s_age as int)=1 then 850
  when a.person_age is null and cast(b.s_age as int)=2 then 800
  when a.person_age is null and cast(b.s_age as int)=3 then 700
  when a.person_age is null and cast(b.s_age as int)=4 then 550
  when a.person_age is null and cast(b.s_age as int)=5 then 450
  when a.person_age is null and cast(b.s_age as int)=6 then 300
  else 625
  end as person_age,
case when a.person_hukou='1' then 850
  when a.person_hukou='2' then 750
  when a.person_hukou='3' then 650
  when a.person_hukou='4' then 550
  when a.person_hukou='5' then 500
  when a.person_hukou='6' then 300
  when a.person_hukou is null and cast(b.s_hukou_city as int)=1 then 850
  when a.person_hukou is null and cast(b.s_hukou_city as int)=2 then 750
  when a.person_hukou is null and cast(b.s_hukou_city as int)=3 then 650
  when a.person_hukou is null and cast(b.s_hukou_city as int)=4 then 550
  when a.person_hukou is null and cast(b.s_hukou_city as int)=5 then 500
  when a.person_hukou is null and cast(b.s_hukou_city as int)=6 then 300
  else 600
  end as person_hukou,
case when a.person_income='6' then 850
  when a.person_income='5' then 750
  when a.person_income='4' then 650
  when a.person_income='3' then 550
  when a.person_income='2' then 450
  when a.person_income='1' then 350
  when a.person_income is null and cast(b.income as int)=1 then 850
  when a.person_income is null and cast(b.income as int)=2 then 750
  when a.person_income is null and cast(b.income as int)=3 then 650
  when a.person_income is null and cast(b.income as int)=4 then 550
  when a.person_income is null and cast(b.income as int)=5 then 450
  when a.person_income is null and cast(b.income as int)=6 then 350
  else 300
  end as person_income,
case when a.person_position<=4 then 850
  when a.person_position>=5 and a.person_position<=7 then 750
  when a.person_position>=8 and a.person_position<=11 then 650
  when a.person_position>=12 and a.person_position<=14 or a.person_position=21 then 500
  when a.person_position>=15 and a.person_position<=19 then 350
  when a.person_position=20 then 300
  when a.person_position is null and cast(b.position as int)=1 then 850
  when a.person_position is null and cast(b.position as int)=2 then 750
  when a.person_position is null and cast(b.position as int)=3 then 650
  when a.person_position is null and cast(b.position as int)=4 then 500
  when a.person_position is null and cast(b.position as int)=5 then 350
  when a.person_position is null and cast(b.position as int)=6 then 300
  else 600
  end as person_position,
case when a.person_education='1' then 850
  when a.person_education='2' then 750
  when a.person_education='3' then 600
  when a.person_education='4' then 500
  when a.person_education='5' then 300
  when a.person_education is null and cast(b.education as int)=1 then 850
  when a.person_education is null and cast(b.education as int)=2 then 750
  when a.person_education is null and cast(b.education as int)=3 then 600
  when a.person_education is null and cast(b.education as int)=4 then 500
  when a.person_education is null and cast(b.education as int)=5 then 300
  else 500
  end as person_education,
case when xd_count>=4 then 850
when xd_count=3 then 700
when xd_count=2 then 600
when xd_count=1 then 400
else 300
end as xd_count,
case when xd_sum>10000 then 850
when xd_sum>5000 and xd_sum<=10000 then 700
when xd_sum>2000 and xd_sum<=5000  then 600
when xd_sum<=2000 then 500
else 300
end as xd_sum,
case when xd_ave>5000 and xd_ave<=10000 then 850
when xd_ave>2000 and xd_ave<=5000 then 700
when xd_ave>1000 and xd_ave<=2000 then 600
when xd_ave<=1000 then 500
else 300
end as xd_ave,
case when xd_status=1 then 850
    when xd_status=2 then 700
    when xd_status=3 then 600
    when xd_status=4 then 500
    when xd_status=5 then 400
    when xd_status=6 then 300
else 600
end as xd_status,
case when debit_cxmonth=6 then 850
when debit_cxmonth=7 then 750
when debit_cxmonth=2 then 650
when debit_cxmonth=3 then 600
when debit_cxmonth=5 then 550
when debit_cxmonth=1 then 450
when debit_cxmonth=4 then 400
else 300
end as debit_cxmonth,
case when credit_limit>200000 then 850
when credit_limit>100000 and credit_limit<=200000 then 740
when credit_limit>50000 and credit_limit<=100000 then 685
when credit_limit>20000 and credit_limit<=50000  then 657
when credit_limit>15000 and credit_limit<=20000  then 630
when credit_limit>10000 and credit_limit<=15000  then 602
when credit_limit>5000 and credit_limit<=10000  then 575
when credit_limit<=5000 then 465
else 657
end as credit_limit,
case when credit_rate>0.2 and credit_rate<=0.4 then 850
when credit_rate>0.4 and credit_rate<=0.5 then 671
when credit_rate>0.9 and credit_rate<=1.0 then 614
when credit_rate>0.6 and credit_rate<=0.9 then 549
when credit_rate>0.5 and credit_rate<=0.6 then 514
when credit_rate>=0.0 and credit_rate<=0.2 then 470
when credit_rate>1.0 then 300
else 514
end as credit_rate,
case when credit_balance>50000 then 850
when credit_balance>19000 and credit_balance<=50000 then 767
when credit_balance>13000 and credit_balance<=19000 then 712
when credit_balance>7000 and credit_balance<=13000 then 657
when credit_balance>1000 and credit_balance<=7000  then 575
when credit_balance>=0 and credit_balance<=1000  then 520
else 575
end as credit_balance,
case when credit_cxmonth=7 then 850
when credit_cxmonth=6 then 750
when credit_cxmonth=5 then 700
when credit_cxmonth=4 then 650
when credit_cxmonth=3 then 600
when credit_cxmonth=2 then 500
when credit_cxmonth=1 then 400
else 300
end as credit_cxmonth,
case when debit_balance>50000 then 850
when debit_balance>19000 and debit_balance<=50000 then 740
when debit_balance>13000 and debit_balance<=19000 then 685
when debit_balance>7000 and debit_balance<=13000 then 630
when debit_balance>1000 and debit_balance<=7000  then 520
when debit_balance>=0 and debit_balance<=1000  then 465
else 300
end as debit_balance,
case when credit_hkwdx<=0.1 and credit_hkwdx>0.0 then 393
when credit_hkwdx>0.1 and credit_hkwdx<=0.2 then 557
when credit_hkwdx>0.2 and credit_hkwdx<=0.3 then 659
when credit_hkwdx>0.3 and credit_hkwdx<=0.8 then 612
when credit_hkwdx>0.8 and credit_hkwdx<=1.2 then 573
when credit_hkwdx>1.2 and credit_hkwdx<=1.5 then 527
when credit_hkwdx>1.5 then 561
else 438
end as credit_hkwdx,
case when credit_hkcs=1 then 502
when credit_hkcs>=2 and credit_hkcs<=3 then 473
when credit_hkcs>=4 and credit_hkcs<=5 then 512
when credit_hkcs>=6 and credit_hkcs<=10 then 594
when credit_hkcs>=11 and credit_hkcs<=20 then 588
when credit_hkcs>=21 and credit_hkcs<=30 then 646
when credit_hkcs>=31 and credit_hkcs<=40 then 704
when credit_hkcs>=41 and credit_hkcs<=50 then 629
when credit_hkcs>=51 then 673
else 445
end as credit_hkcs,
case when sharebal_day>=0 and sharebal_day<=20 then 596
when sharebal_day>20 and sharebal_day<=50 then 300
when sharebal_day>50 and sharebal_day<=450 then 681
when sharebal_day>450 and sharebal_day<=800 then 786
when sharebal_day>800 and sharebal_day<=2500 then 825
when sharebal_day>2500 and sharebal_day<=10000 then 833
when sharebal_day>10000 then 843
else 622 end as sharebal_day,
case when qb_balance_max>=0 and qb_balance_max<=1 then 300
when qb_balance_max>1 and qb_balance_max<=10 then 626
when qb_balance_max>10 and qb_balance_max<=100 then 667
when qb_balance_max>100 and qb_balance_max<=200 then 566
when qb_balance_max>200 then 624
else 594 end as qb_balance_max,
case when a.donation>10 then 25 else 0 end as donation,
case when a.baoxian>0 then 10 else 0 end as baoxian
from cdt_result_source a 
left join mid_fuzhi_user b on a.mobile_num=b.mobile 
where a.ymd='${TODAY_1D}';
EOF
tmp=$?
NOWTIME=$(date +%Y-%m-%d/%H:%M:%S)
if test $tmp -eq 0; then 
echo $NOWTIME'--------------打分表 cdt_result_source_lvscore 分区'$TODAY_1D'创建成功'$tmp >> logs/logs_cdt_datasource_result
else
echo $NOWTIME'--------------打分表 cdt_result_source_lvscore 分区'$TODAY_1D'创建失败'$tmp >> logs/logs_cdt_datasource_result
exit -2;
fi


echo '-------------加权重分数计算-----------------'
sudo -u hdfs hive <<EOF
use creditck;
create table if not exists cdt_result_source_wtscore (
mobile_num string,
score_sum double comment '总分',
score_jy double comment '交易行为分',
score_ly double comment '履约能力分',
score_id double comment '身份属性分',
score_credit double comment '信用记录分',
score_social int comment '社交关系分',
is_black int comment '是否黑名单',
black_type string comment '黑名单类型') 
partitioned by (ymd string);

insert overwrite table cdt_result_source_wtscore partition (ymd='${TODAY_1D}')
select mobile_num,
(person_status*0.0173+
device_ss*0.0208+
zw_time*0.0206+
debit_count*0.0243+
jy_time*0.0178+
credit_count*0.0196+
debit_cxmonth*0.0211+
debit_zzmonth*0.0204+
device_skb*0.0201+
debit_out*0.0209+
debit_in*0.0218+
credit_cxmonth*0.0205+
mobile_kind*0.0198+
person_income*0.0206+
phonecharge_permonth*0.0205+
credit_class*0.0215+
debit_class*0.0212+
debit_yjzz*0.026+
debit_bjzz*0.018+
ebus_sum*0.0154+
debit_balance*0.0211+
qb_balance_max*0.0217+
debit_kind*0.0217+
ebus_count*0.017+
pubpay_count*0.0229+
pubpay_sum*0.0206+
credit_balance*0.0216+
pubpay_ave*0.0205+
sharebal_day*0.0211+
virtual_sum*0.0051+
credit_limit*0.0059+
person_age*0.0221+
person_hukou*0.0216+
person_position*0.0217+
person_sex*0.0234+
person_living*0.0217+
person_education*0.0201+
xd_ave*0.0219+
xd_status*0.0449+
xd_sum*0.0326+
xd_count*0.0278+
credit_yjhk*0.023+
credit_bjhk*0.0191+
credit_hklast*0.0211+
credit_hkmonth*0.0232+
credit_hkwdx*0.0224+
credit_hkcs*0.0181+
credit_rate*0.004+
zdfq_count*0.0039+
donation*0.2452+
baoxian*0.2452) as score_sum,
case when is_black=1 and black_type like '交易行为' then 300 
else
(person_status*0.0706+
device_ss*0.0848+
zw_time*0.084+
debit_count*0.0991+
jy_time*0.0726+
credit_count*0.0799+
debit_cxmonth*0.0861+
debit_zzmonth*0.0832+
device_skb*0.082+
debit_out*0.0852+
debit_in*0.0889+
credit_cxmonth*0.0836+
donation+
baoxian)+100 
end as score_jy,
case when is_black=1 and black_type like '履约能力' then 300 
else
(mobile_kind*0.0547+
person_income*0.0569+
phonecharge_permonth*0.0566+
credit_class*0.0594+
debit_class*0.0585+
debit_yjzz*0.0718+
debit_bjzz*0.0497+
ebus_sum*0.0425+
debit_balance*0.0583+
qb_balance_max*0.0599+
debit_kind*0.0599+
ebus_count*0.0469+
pubpay_count*0.0632+
pubpay_sum*0.0569+
credit_balance*0.0596+
pubpay_ave*0.0566+
sharebal_day*0.0583+
virtual_sum*0.0141+
credit_limit*0.0163)+100 
end as score_ly,
case when is_black=1 and black_type like '身份属性' then 300 
else
(person_age*0.1692+
person_hukou*0.1654+
person_position*0.1662+
person_sex*0.1792+
person_living*0.1662+
person_education*0.1539)+100 
end as score_id,
case when is_black=1 and black_type like '信用记录' then 300 
else
(xd_ave*0.0836+
xd_status*0.1714+
xd_sum*0.1244+
xd_count*0.1061+
credit_yjhk*0.0878+
credit_bjhk*0.0729+
credit_hklast*0.0805+
credit_hkmonth*0.0885+
credit_hkwdx*0.0855+
credit_hkcs*0.0691+
credit_rate*0.0153+
zdfq_count*0.0149)+100
end as score_credit,
573 as score_social,
is_black,black_type
from cdt_result_source_lvscore where ymd='${TODAY_1D}';
EOF
tmp=$?
NOWTIME=$(date +%Y-%m-%d/%H:%M:%S)
if test $tmp -eq 0; then 
echo $NOWTIME'--------------征信加权重小分表cdt_result_wtscore分区'$TODAY_1D'创建成功'$tmp >> logs/logs_cdt_datasource_result
else
echo $NOWTIME'--------------征信加权重小分表cdt_result_wtscore分区'$TODAY_1D'创建失败'$tmp >> logs/logs_cdt_datasource_result
exit -2;
fi


echo '-------------分数调整,计算总分-----------------'
sudo -u hdfs hive <<EOF
use creditck;

create table if not exists cdt_result_source_tzscore (
mobile_num string,
is_black int comment '是否黑名单',
black_type string comment '黑名单类型',
score_sum int comment '总分',
score_jy int comment '交易行为分',
score_ly int comment '履约能力分',
score_id int comment '身份属性分',
score_credit int comment '信用记录分',
score_social int comment '社交关系分',
score_max int comment '历史最大分',
is_gt570 int comment '是否有历史分数大于570') 
partitioned by (ymd string);

--首次计算
create table if not exists cdt_user_is_gt570 (
mobile_num string,
score_max int comment '历史最大分',
is_gt570 int comment '是否有历史分数大于570',
is_black int comment '是否黑名单',
black_type string comment '黑名单类型') ;

--首次计算
--insert overwrite table cdt_user_is_gt570
--select a.mobile_num,
--b.score_max,
--b.is_gt570,
--a.is_black,
--a.black_type
--from (select distinct mobile_num,is_black,black_type from cdt_result_source_wtscore where ymd='20150831') a
--left join 
--(select mobile_num,
        --max(score) as score_max,
        --case when max(score)>=570 then 1 else 0 end as is_gt570
--from cdt_final_score_grade_limit where ymd<='20150809' group by mobile_num) b on a.mobile_num=b.mobile_num


insert overwrite table cdt_result_source_tzscore partition (ymd='${TODAY_1D}')
select a.mobile_num,a.is_black,a.black_type,
case when floor(a.score_jy*24.52/100+a.score_ly*36.22/100+a.score_id*13.06/100+a.score_credit*26.20/100)>845 then 845
     when b.is_gt570=1 and a.is_black=0 and floor(a.score_jy*24.52/100+a.score_ly*36.22/100+a.score_id*13.06/100+a.score_credit*26.20/100)<570 then 570 
     else floor(a.score_jy*24.52/100+a.score_ly*36.22/100+a.score_id*13.06/100+a.score_credit*26.20/100)
     end as score_sum,
case when floor(a.score_jy)>845 then 845
     else floor(a.score_jy) end as score_jy,
case when floor(a.score_ly)>845 then 845
     else floor(a.score_ly) end as score_ly,
case when floor(a.score_id)>845 then 845
     else floor(a.score_id) end as score_id,
case when floor(a.score_credit)>845 then 845
     else floor(a.score_credit) end as score_credit,
a.score_social,
case when floor(a.score_jy*24.52/100+a.score_ly*36.22/100+a.score_id*13.06/100+a.score_credit*26.20/100)<=b.score_max then b.score_max
     else floor(a.score_jy*24.52/100+a.score_ly*36.22/100+a.score_id*13.06/100+a.score_credit*26.20/100) end as score_max,
case when b.is_gt570=0 and floor(a.score_jy*24.52/100+a.score_ly*36.22/100+a.score_id*13.06/100+a.score_credit*26.20/100)>=570 then 1
     else b.is_gt570 end as is_gt570
from (select * from cdt_result_source_wtscore where ymd='${TODAY_1D}') a
left join cdt_user_is_gt570 b on a.mobile_num=b.mobile_num;

--更新is_gt570
insert overwrite table cdt_user_is_gt570
select a.mobile_num,
a.score_max,
a.is_gt570,
a.is_black,
a.black_type
from cdt_result_source_tzscore a where a.ymd='${TODAY_1D}';
EOF
tmp=$?
NOWTIME=$(date +%Y-%m-%d/%H:%M:%S)
if test $tmp -eq 0; then 
echo $NOWTIME'--------------征信调整后总分表cdt_result_wtscore_tz分区'$TODAY_1D'创建成功'$tmp >> logs/logs_cdt_datasource_result
else
echo $NOWTIME'--------------征信调整后总分表cdt_result_wtscore_tz分区'$TODAY_1D'创建失败'$tmp >> logs/logs_cdt_datasource_result
exit -2;
fi



echo '-------------评级&授信额度-----------------'
sudo -u hdfs hive <<EOF
use creditck;

create table if not exists cdt_final_score_grade_limit(
mobile_num string comment '手机号码',
score int comment '总分',
grade string comment '等级',
line int comment '额度',
score_jy int comment '交易行为分',
grade_jy string comment '交易行为等级',
score_ly int comment '履约能力分',
grade_ly string comment '履约能力等级',
score_id int comment '身份属性分',
grade_id string comment '身份属性等级',
score_credit int comment '信用记录分',
grade_credit string comment '信用记录等级',
score_social int comment '社会关系分',
grade_social string comment '社会关系等级',
is_black int comment '是否黑名单',
black_type string comment '黑名单类型') 
partitioned by (ymd string);

insert overwrite table cdt_final_score_grade_limit
partition (ymd='${TODAY_1D}')
select mobile_num,
cast(floor(score_sum) as int) as score,
case when score_sum>=750 and score_sum<=850 then 'AAA'
        when score_sum>=700 and score_sum<750 then 'AA'
        when score_sum>=650 and score_sum<700 then 'A'
        when score_sum>=600 and score_sum<650 then 'BBB'
        when score_sum>=550 and score_sum<600 then 'BB'
        when score_sum>=500 and score_sum<550 then 'B'
        when score_sum>=450 and score_sum<500 then 'CCC'
        when score_sum>=400 and score_sum<450 then 'CC'
        when score_sum>=300 and score_sum<400 then 'C'
        end as grade,
case when score_sum>=750 and score_sum<=850 then 50000000
        when score_sum>=700 and score_sum<750 then 40000000
        when score_sum>=650 and score_sum<700 then 30000000
        when score_sum>=600 and score_sum<650 then 20000000
        when score_sum>=550 and score_sum<600 then 10000000
        when score_sum>=500 and score_sum<550 then 5000000
        when score_sum>=450 and score_sum<500 then 1000000
        when score_sum>=400 and score_sum<450 then 500000
        when score_sum>=300 and score_sum<400 then 0
        end as line,
score_jy,
case when score_jy>=750 and score_jy<=850 then 'AAA'
        when score_jy>=700 and score_jy<750 then 'AA'
        when score_jy>=650 and score_jy<700 then 'A'
        when score_jy>=600 and score_jy<650 then 'BBB'
        when score_jy>=550 and score_jy<600 then 'BB'
        when score_jy>=500 and score_jy<550 then 'B'
        when score_jy>=450 and score_jy<500 then 'CCC'
        when score_jy>=400 and score_jy<450 then 'CC'
        when score_jy>=300 and score_jy<400 then 'C'
        end as grade_jy,
score_ly,
case when score_ly>=750 and score_ly<=850 then 'AAA'
        when score_ly>=700 and score_ly<750 then 'AA'
        when score_ly>=650 and score_ly<700 then 'A'
        when score_ly>=600 and score_ly<650 then 'BBB'
        when score_ly>=550 and score_ly<600 then 'BB'
        when score_ly>=500 and score_ly<550 then 'B'
        when score_ly>=450 and score_ly<500 then 'CCC'
        when score_ly>=400 and score_ly<450 then 'CC'
        when score_ly>=300 and score_ly<400 then 'C'
        end as grade_ly,
score_id,
case when score_id>=750 and score_id<=850 then 'AAA'
        when score_id>=700 and score_id<750 then 'AA'
        when score_id>=650 and score_id<700 then 'A'
        when score_id>=600 and score_id<650 then 'BBB'
        when score_id>=550 and score_id<600 then 'BB'
        when score_id>=500 and score_id<550 then 'B'
        when score_id>=450 and score_id<500 then 'CCC'
        when score_id>=400 and score_id<450 then 'CC'
        when score_id>=300 and score_id<400 then 'C'
        end as grade_id,
score_credit,
case when score_credit>=750 and score_credit<=850 then 'AAA'
        when score_credit>=700 and score_credit<750 then 'AA'
        when score_credit>=650 and score_credit<700 then 'A'
        when score_credit>=600 and score_credit<650 then 'BBB'
        when score_credit>=550 and score_credit<600 then 'BB'
        when score_credit>=500 and score_credit<550 then 'B'
        when score_credit>=450 and score_credit<500 then 'CCC'
        when score_credit>=400 and score_credit<450 then 'CC'
        when score_credit>=300 and score_credit<400 then 'C'
        end as grade_credit,
score_social,
case when score_social>=750 and score_social<=850 then 'AAA'
        when score_social>=700 and score_social<750 then 'AA'
        when score_social>=650 and score_social<700 then 'A'
        when score_social>=600 and score_social<650 then 'BBB'
        when score_social>=550 and score_social<600 then 'BB'
        when score_social>=500 and score_social<550 then 'B'
        when score_social>=450 and score_social<500 then 'CCC'
        when score_social>=400 and score_social<450 then 'CC'
        when score_social>=300 and score_social<400 then 'C'
        end as grade_social,
is_black,black_type
from cdt_result_source_tzscore where ymd='${TODAY_1D}';
EOF
tmp=$?
NOWTIME=$(date +%Y-%m-%d/%H:%M:%S)
if test $tmp -eq 0; then
echo $NOWTIME'--------------征信评级额度表cdt_final_score_grade_limit分区'$TODAY_1D'成功'$tmp >> logs/logs_cdt_datasource_result
else
echo $NOWTIME'--------------征信评级额度表cdt_final_score_grade_limit分区'$TODAY_1D'失败'$tmp >> logs/logs_cdt_datasource_result
exit -2;
fi

week=$(date +%w)
if test $week -eq 1;then 
   echo '-------------推送表创建-----------------'
   sudo -u hdfs hive <<EOF
   use creditck;
   
   create table if not exists cdt_final_score_grade_limit_push (
   mobile string,
   score int comment '征信总分',
   grade string comment '征信分数等级',
   line int comment '额度',
   proportion int,
   rank1 string comment '履约能力等级',
   rank2 string comment '身份属性等级',
   rank3 string comment '信用记录等级',
   rank4 string comment '社会关系等级',
   rank5 string comment '交易行为等级',
   rank6 string comment '黑名单类型',
   rank7 string,
   is_promise string comment '是否黑名单',
   score1 int comment '履约能力分',
   score2 int comment '身份属性分',
   score3 int comment '信用记录分',
   score4 int comment '社会关系分',
   score5 int comment '交易行为分'
   );

   insert overwrite table cdt_final_score_grade_limit_push
   select a.mobile_num as mobile,
          a.score,
          a.grade,
          a.line,
          0 as proportion,
          a.grade_ly as rank1,
          a.grade_id as rank2,
          a.grade_credit as rank3,
          a.grade_social as rank4,
          a.grade_jy as rank5,
          ' ' as rank6,
          ' ' as rank7,
          cast(a.is_black as string) as is_promise,
          a.score_ly as score1,
          a.score_id as score2,
          a.score_credit as score3,
          a.score_social as score4,
          a.score_jy as score5
   from (select * from cdt_final_score_grade_limit where ymd='${TODAY_1D}') a 
   left join (select * from cdt_final_score_grade_limit where ymd='${TODAY_4D}') b on a.mobile_num=b.mobile_num 
   where b.mobile_num is null 
        or b.score<>a.score
        or b.score_ly<>a.score_ly
        or b.score_id<>a.score_id
        or b.score_credit<>a.score_credit
        or b.score_social<>a.score_social
        or b.score_jy<>a.score_jy;
   EOF
   tmp=$?
   NOWTIME=$(date +%Y-%m-%d/%H:%M:%S)
   if test $tmp -eq 0; then
      echo $NOWTIME'--------------cdt_final_score_grade_limit_push创建成功'$tmp >> logs/logs_cdt_datasource_result
   else
      echo $NOWTIME'--------------cdt_final_score_grade_limit_push创建失败'$tmp >> logs/logs_cdt_datasource_result
      exit -2;
   fi
else
   echo '-------------推送表创建-----------------'
   sudo -u hdfs hive <<EOF
   use creditck;
   insert overwrite table cdt_final_score_grade_limit_push
   select a.mobile_num,
          a.score,
          a.grade,
          a.line,
          0 as proportion,
          a.grade_ly as rank1,
          a.grade_id as rank2,
          a.grade_credit as rank3,
          a.grade_social as rank4,
          a.grade_jy as rank5,
          ' ' as rank6,
          ' ' as rank7,
          cast(a.is_black as string) as is_promise,
          a.score_ly as score1,
          a.score_id as score2,
          a.score_credit as score3,
          a.score_social as score4,
          a.score_jy as score5
   from (select * from cdt_final_score_grade_limit where ymd='${TODAY_1D}') a 
   left join (select * from cdt_final_score_grade_limit where ymd='${TODAY_2D}') b on a.mobile_num=b.mobile_num 
   where b.mobile_num is null 
        or b.score<>a.score
        or b.score_ly<>a.score_ly
        or b.score_id<>a.score_id
        or b.score_credit<>a.score_credit
        or b.score_social<>a.score_social
        or b.score_jy<>a.score_jy;
   EOF
   tmp=$?
   NOWTIME=$(date +%Y-%m-%d/%H:%M:%S)
   if test $tmp -eq 0; then
      echo $NOWTIME'--------------cdt_final_score_grade_limit_push创建成功'$tmp >> logs/logs_cdt_datasource_result
   else
      echo $NOWTIME'--------------cdt_final_score_grade_limit_push创建失败'$tmp >> logs/logs_cdt_datasource_result
      exit -2;
   fi
fi

echo '-------------现在开始导出征信最新结果--------------'
sudo -u hdfs  sqoop export --connect jdbc:oracle:thin:@10.1.2.131:1521/bigdb --username spss --password lakala_1234 --table SPSS.CDT_SCORE_GRADE_LIMIT_V2_WM1 --export-dir /user/hive/warehouse/creditck.db/cdt_final_score_grade_limit/ymd=$TODAY_1D --fields-terminated-by '\001' --input-null-string '\\N' --input-null-non-string '\\N'

