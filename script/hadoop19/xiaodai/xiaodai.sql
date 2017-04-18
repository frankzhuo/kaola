

TODAY_0D=$(date +%Y-%m-%d)


while true
do
    hive <<EOF

    set hive.auto.convert.join=false;
    set mapreduce.reduce.shuffle.input.buffer.percent=0.1;
    set mapreduce.reduce.shuffle.parallelcopies=9;

    drop table creditck.v2_xd_user;
    create table creditck.v2_xd_user as 
    select distinct t.* from
    (select a2.mobile, 
           a1.*, 
           datediff(to_date(a1.return_date), to_date(a1.loan_date))+1 dateloan
           from hds.xd_c_loan_apply a1
           join hds.xd_c_apply_user a2
           on a1.user_id = a2.user_id and a1.business_no in ('BID','CASH') 
    ) t
;

EOF

    if [ $? -eq 0 ];then
       break
    else
       sleep 5m
    fi
done



while true
do
    hive <<EOF

    set hive.auto.convert.join=false;
    set mapreduce.reduce.shuffle.input.buffer.percent=0.1;
    set mapreduce.reduce.shuffle.parallelcopies=9;

drop table creditck.v2_xd_apply_var;
create table  creditck.v2_xd_apply_var as
    select distinct t1.mobile,
                    to_date(t1.apply_1st) apply_1st, 
                    to_date(t1.apply_last) apply_last, 
                    datediff(to_date(t1.apply_1st), to_date(t4.create_time)) reg_apply1st, 
                    datediff('${TODAY_0D}', to_date(t4.create_time)) reg_days, 
                    floor(t1.tnh_avg_cap) tnh_avg_cap, 
                    floor(t1.tnh_max_cap) tnh_max_cap, 
                    floor(t1.tnh_min_cap) tnh_min_cap,
                    floor(t1.yfq_avg_cap) yfq_avg_cap, 
                    floor(t1.yfq_max_cap) yfq_max_cap, 
                    floor(t1.yfq_min_cap) yfq_min_cap, 
                    floor(t1.tnh_avg_date) tnh_avg_date, 
                    floor(t1.yfq_avg_date) yfq_avg_date, 
                    t1.tnh_return_suc, 
                    t1.yfq_return_suc,
                    t2.product_no product_1st, 
                    floor(t2.capital_amount/100) cap_1st, 
                    t2.dateloan dateloan_1st,
                    
                    case when t3.tnh_cv is null then 99 when t3.tnh_cv=0 and tnh_freq=1 then 88 else t3.tnh_cv end  tnh_cv , 
                    case when t3.yfq_cv is null then 99 when t3.yfq_cv=0 and yfq_freq=1 then 88 else t3.yfq_cv end yfq_cv ,
                    nvl(t3.tnh_freq,0) tnh_freq, 
                    nvl(t3.yfq_freq,0) yfq_freq,
                    
                    t5.maxedu, 
                    t5.minedu, 
                    t5.maxincome, 
                    t5.minincome, 
                    if(isnotnull(t6.mobile),1,0) email_qq, 
                    if(isnotnull(t7.mobile),1,0) email_163
from 
(select mobile, 
        max(insert_time) apply_last, 
        min(insert_time) apply_1st,
        avg(case when business_no='BID' then capital_amount/100 end) tnh_avg_cap, 
        max(case when business_no='BID' then capital_amount/100 end) tnh_max_cap, 
        min(case when business_no='BID' then capital_amount/100 end) tnh_min_cap,
        avg(case when business_no='CASH' then capital_amount/100 end) yfq_avg_cap, 
        max(case when business_no='CASH' then capital_amount/100 end) yfq_max_cap, 
        min(case when business_no='CASH' then capital_amount/100 end) yfq_min_cap,
        avg(case when business_no='BID' then dateloan end) tnh_avg_date,
        avg(case when business_no='CASH' then dateloan end) yfq_avg_date,
        count(distinct case when business_no='BID' and status='T' then id end)-1 tnh_return_suc,
        count(distinct case when business_no='CASH' and status='T' then id end)-1 yfq_return_suc
        from creditck.v2_xd_user 
        group by mobile
) t1

join creditck.v2_xd_user t2
     on t1.mobile=t2.mobile and t1.apply_1st=t2.insert_time





join (select mobile, 
             stddev(case when business_no='BID' then capital_amount/100 end) / avg(case when business_no='BID' then capital_amount/100 end) tnh_cv,
             stddev(case when business_no='CASH' then capital_amount/100 end) / avg(case when business_no='CASH' then capital_amount/100 end) yfq_cv, 
             count(distinct case when business_no='BID' then id end) tnh_freq,
             count(distinct case when business_no='CASH' then id end) yfq_freq
             from creditck.v2_xd_user 
             group by mobile 
     ) t3
     on t1.mobile=t3.mobile




join (select mobile_num,
             min(create_time) create_time 
             from hds.u_user
             where create_time is not null
             group by mobile_num
     ) t4 
     on t1.mobile=t4.mobile_num 

join (select mobile, 
             max(cast (trim(education) as int)) maxedu, 
             min(cast (trim(education) as int)) minedu, 
             max(cast (trim(income_range) as int)) maxincome, 
             min(cast (trim(income_range) as int)) minincome 
             from hds.xd_c_apply_user 
             group by mobile 
     ) t5
     on t1.mobile=t5.mobile

left outer join (select distinct mobile 
                        from hds.xd_c_apply_user 
                        where lower(email) like '%qq.com'
                ) t6        
     on t1.mobile=t6.mobile
  
left outer join (select distinct mobile 
                        from hds.xd_c_apply_user 
                        where lower(email) like '%163.com' or lower(email) like '%126.com'
                ) t7        
     on t1.mobile=t7.mobile
;

EOF

    if [ $? -eq 0 ];then
       break
    else
       sleep 5m
    fi
done





max_ymd=`hive -e "select max(ymd) from creditck.cdt_result_source;"`

while true
do
    hive <<EOF

    set hive.auto.convert.join=false;
    set mapreduce.reduce.shuffle.input.buffer.percent=0.1;
    set mapreduce.reduce.shuffle.parallelcopies=9;


create table if not exists creditck.v2_xd_dataset_new (
mobile_num                  string,
zw_time                     int,
person_status               string,
credit_count                bigint,
debit_count                 bigint,
credit_class                string,
debit_class                 string,
debit_kind                  string,
credit_bjhk                 bigint,
credit_yjhk                 bigint,
credit_hkmonth              bigint,
credit_hklast               int,
debit_in                    bigint,
debit_out                   bigint,
debit_bjzz                  bigint,
debit_zzmonth               bigint,
debit_yjzz                  bigint,
zdfq_count                  string,
mobile_kind                 string,
phonecharge_permonth        bigint,
pubpay_sum                  bigint,
pubpay_count                bigint,
pubpay_ave                  bigint,
ebus_sum                    bigint,
ebus_count                  bigint,
virtual_sum                 bigint,
jy_time                     bigint,
device_ss                   string,
device_skb                  string,
person_living               string,
person_sex                  int,
person_age                  int,
person_hukou                string,
person_position             int,
debit_cxmonth               bigint,
credit_limit                bigint,
credit_rate                 bigint,
credit_balance              bigint,
credit_cxmonth              bigint,
debit_balance               bigint,
credit_hkwdx                float,
credit_hkcs                 bigint,
sharebal_day                bigint,
qb_balance_max              bigint,
donation                    bigint,
baoxian                     bigint,
reg_apply1st                int,
reg_days                    int,
tnh_avg_cap                 bigint,
tnh_max_cap                 bigint,
tnh_min_cap                 bigint,
yfq_avg_cap                 bigint,
yfq_max_cap                 bigint,
yfq_min_cap                 bigint,
tnh_avg_date                bigint,
yfq_avg_date                bigint,
tnh_return_suc              bigint,
yfq_return_suc              bigint,
product_1st                 string,
cap_1st                     bigint,
dateloan_1st                int,
tnh_cv                      float,
yfq_cv                      float,
tnh_freq                    bigint,
yfq_freq                    bigint,
maxedu                      int,
minedu                      int,
maxincome                   int,
minincome                   int,
email_qq                    int,
email_163                   int
)
partitioned by (ymd string)
;

insert overwrite table creditck.v2_xd_dataset_new partition (ymd='${TODAY_0D}') 
select distinct t1.mobile_num mobile_num,
                nvl(t1.zw_time,0) zw_time,
                nvl(t1.person_status,0) person_status,
                nvl(t1.credit_count,0) credit_count,
                nvl(t1.debit_count,0) debit_count,
                nvl(t1.credit_class,0) credit_class,
                nvl(t1.debit_class,0) debit_class,
                nvl(t1.debit_kind,0) debit_kind,
                floor(nvl(t1.credit_bjhk,0)) credit_bjhk,
                floor(nvl(t1.credit_yjhk,0)) credit_yjhk,
                nvl(t1.credit_hkmonth,0) credit_hkmonth,
                nvl(t1.credit_hklast,0) credit_hklast,
                nvl(t1.debit_in,0) debit_in,
                nvl(t1.debit_out,0)  debit_out,
                floor(nvl(t1.debit_bjzz,0)) debit_bjzz,
                nvl(t1.debit_zzmonth,0) debit_zzmonth,
                floor(nvl(t1.debit_yjzz,0)) debit_yjzz,
                nvl(t1.zdfq_count,0) zdfq_count,
                nvl(t1.mobile_kind,0) mobile_kind,
                floor(nvl(t1.phonecharge_permonth,0)) phonecharge_permonth,
                floor(nvl(t1.pubpay_sum,0)) pubpay_sum,
                nvl(t1.pubpay_count,0) pubpay_count,
                floor(nvl(t1.pubpay_ave,0)) pubpay_ave,
                floor(nvl(t1.ebus_sum,0)) ebus_sum,
                nvl(t1.ebus_count,0) ebus_count,
                floor(nvl(t1.virtual_sum,0)) virtual_sum,
                nvl(t1.jy_time,0) jy_time,
                nvl(t1.device_ss,0) device_ss,
                nvl(t1.device_skb,0) device_skb,
                nvl(t1.person_living,0) person_living,
                nvl(t1.person_sex,0) person_sex,
                nvl(t1.person_age,0) person_age,
                nvl(t1.person_hukou,0) person_hukou,
                nvl(t1.person_position,0) person_position,
                nvl(t1.debit_cxmonth,0) debit_cxmonth,
                floor(nvl(t1.credit_limit,0)) credit_limit,
                floor(nvl(t1.credit_rate,0)) credit_rate,
                floor(nvl(t1.credit_balance,0)) credit_balance,
                nvl(t1.credit_cxmonth,0) credit_cxmonth,
                floor(nvl(t1.debit_balance,0)) debit_balance,
                nvl(t1.credit_hkwdx,0)  credit_hkwdx,
                nvl(t1.credit_hkcs,0) credit_hkcs,
                floor(nvl(t1.sharebal_day,0)) sharebal_day,
                floor(nvl(t1.qb_balance_max,0)) qb_balance_max,
                floor(nvl(t1.donation,0)) donation,
                floor(nvl(t1.baoxian,0)) baoxian,
                t2.reg_apply1st, 
                t2.reg_days, 
                t2.tnh_avg_cap, 
                t2.tnh_max_cap, 
                t2.tnh_min_cap,
                t2.yfq_avg_cap, 
                t2.yfq_max_cap, 
                t2.yfq_min_cap, 
                t2.tnh_avg_date, 
                t2.yfq_avg_date, 
                t2.tnh_return_suc, 
                t2.yfq_return_suc,
                t2.product_1st, 
                t2.cap_1st, 
                t2.dateloan_1st,
                

                t2.tnh_cv, 
                t2.yfq_cv, 
                t2.tnh_freq, 
                t2.yfq_freq,

                t2.maxedu, 
                t2.minedu, 
                t2.maxincome, 
                t2.minincome, 
                t2.email_qq, 
                t2.email_163
FROM (select * from creditck.cdt_result_source t1 where ymd='${max_ymd}') t1 
left outer join creditck.v2_xd_apply_var t2 
on t1.mobile_num=t2.mobile
;


EOF

    if [ $? -eq 0 ];then
       break
    else
       sleep 5m
    fi
done




while true
do
    hive <<EOF

    set hive.auto.convert.join=false;
    set mapreduce.reduce.shuffle.input.buffer.percent=0.1;
    set mapreduce.reduce.shuffle.parallelcopies=9;


drop table creditck.v2_xd_dataset_export;
create table creditck.v2_xd_dataset_export as select 
mobile_num                   ,
zw_time                      ,
person_status                ,
credit_count                 ,
debit_count                  ,
credit_class                 ,
debit_class                  ,
debit_kind                   ,
credit_bjhk                  ,
credit_yjhk                  ,
credit_hkmonth               ,
credit_hklast                ,
debit_in                     ,
debit_out                    ,
debit_bjzz                   ,
debit_zzmonth                ,
debit_yjzz                   ,
zdfq_count                   ,
mobile_kind                  ,
phonecharge_permonth         ,
pubpay_sum                   ,
pubpay_count                 ,
pubpay_ave                   ,
ebus_sum                     ,
ebus_count                   ,
virtual_sum                  ,
jy_time                      ,
device_ss                    ,
device_skb                   ,
person_living                ,
person_sex                   ,
person_age                   ,
person_hukou                 ,
person_position              ,
debit_cxmonth                ,
credit_limit                 ,
credit_rate                  ,
credit_balance               ,
credit_cxmonth               ,
debit_balance                ,
credit_hkwdx                 ,
credit_hkcs                  ,
sharebal_day                 ,
qb_balance_max               ,
donation                     ,
baoxian                      ,
reg_apply1st                 ,
reg_days                     ,
tnh_avg_cap                  ,
tnh_max_cap                  ,
tnh_min_cap                  ,
yfq_avg_cap                  ,
yfq_max_cap                  ,
yfq_min_cap                  ,
tnh_avg_date                 ,
yfq_avg_date                 ,
tnh_return_suc               ,
yfq_return_suc               ,
product_1st                  ,
cap_1st                      ,
dateloan_1st                 ,
tnh_cv                       ,
yfq_cv                       ,
tnh_freq                     ,
yfq_freq                     ,
maxedu                       ,
minedu                       ,
maxincome                    ,
minincome                    ,
email_qq                     ,
email_163                    

from creditck.v2_xd_dataset_new
where ymd='${TODAY_0D}' and length(mobile_num)=11
;
EOF

    if [ $? -eq 0 ];then
       break
    else
       sleep 5m
    fi
done



hive <<EOF

    set hive.auto.convert.join=false;
    set mapreduce.reduce.shuffle.input.buffer.percent=0.1;
    set mapreduce.reduce.shuffle.parallelcopies=9;


drop table creditck.var_blacklist_end_export;
create table creditck.var_blacklist_end_export as 
select 
    mobile,
    black_type,
    is_black
from creditck.var_blacklist
where length(mobile)=11
;
EOF

oracle_jdbc=jdbc:oracle:thin:@10.1.2.187:1521/CRDNEWDB
oracle_username=EM
oracle_password=em_1234
oracle_table=CDT_LOAN_SCORE_FINAL
oracle_black_table=CDT_BLACKLIST_END

sqoop export --connect ${oracle_jdbc} --username ${oracle_username} --password ${oracle_password} --table ${oracle_black_table} --export-dir /user/hive/warehouse/creditck.db/var_blacklist_end_export/ --update-key MOBILE --update-mode allowinsert --input-fields-terminated-by '\001' --input-null-string '\\N' --input-null-non-string '\\N' -m 1

hadoop_dir=/user/hive/warehouse/creditck.db/v2_xd_dataset_export

sqoop export --connect ${oracle_jdbc} --username ${oracle_username} --password ${oracle_password} --table ${oracle_table} --export-dir ${hadoop_dir} --update-key MOBILE --update-mode allowinsert --input-fields-terminated-by '\001' --input-null-string '\\N' --input-null-non-string '\\N' -m 10

if [ $? -eq 0 ];then
    echo;echo;echo "导入数据成功: from ${hadoop_dir} to ${oracle_table}";echo;echo
else
    echo;echo;echo "导入数据失败: from ${hadoop_dir} to ${oracle_table}";echo;echo
fi



