#!/bin/sh



echo "######################################################################################################################"
echo "#                                             执行本脚本前，请完成下面操作                                           #"
echo "#  1.在Hive中创建creditck数据库                                                                                      #"
echo "#                                                                                                                    #"
echo "#  2.crontab为0 21 * * * sh /home/hdfs/xiaodai/loan_model.sh > /home/hdfs/xiaodai/logs/'date +\%Y-\%m-\%d'.log 2>&1  #"
echo "#                                                                                                                    #"
echo "#  3.依赖Hive中下面表                                                                                                #"
echo "#      edm.f_fact_trans_success_new                                                                                  #"
echo "#      edm.d_cfg_agent                                                                                               #"
echo "#      hds.xd_c_receipt                                                                                              #"
echo "#      hds.xd_c_loan_contract                                                                                        #"
echo "#      hds.xd_c_apply_user                                                                                           #"
echo "#      creditck.var_user                                                                                             #"
echo "#      edm.d_mobile_info                                                                                             #"
echo "######################################################################################################################"





TODAY_0D=$(date +%Y-%m-%d)





hive_sql="





drop table creditck.zx_loan_model_mid_trans;
create table if not exists creditck.zx_loan_model_mid_trans as 
    select b1.*,
           b2.TCAT_LV3_NAME,
           b2.TCAT_LV4_NAME,
           b2.TCAT_LV5_NAME,
           b2.TCAT_LV6_NAME 
    from edm.f_fact_trans_success_new b1
    left outer join edm.d_cfg_agent b2 
         on b1.merno=b2.agent_no
;









drop table creditck.zx_loan_model_tmp_last2_loan;
create table if not exists  creditck.zx_loan_model_tmp_last2_loan as
    select a1.*, 
           Row_Number() OVER (partition by mobile ORDER BY start_date desc) rn 
    FROM (
    select t3.mobile,
           t1.* 
    from hds.xd_c_receipt t1 
    join hds.xd_c_loan_contract t2 
         on t1.contract_id=t2.id 
            and t1.product_no in ('PA01','PA02','PA03','PA04') 
            and t2.start_date <= current_date
    join hds.xd_c_apply_user t3 
         on t2.apply_id=t3.id
) a1
;

drop table creditck.zx_loan_model_mid_last2_loan;
create table if not exists creditck.zx_loan_model_mid_last2_loan as 
    select b1.mobile, 
           b1.start_datelast, 
           b1.loan_capitallast, 
           if(isnull(b2.due_datelast2),'2001-01-01',b2.due_datelast2) due_datelast2,
           if(isnull(b2.return_datelast2),'2002-01-01',b2.return_datelast2) return_datelast2, 
           if(isnull(b2.loan_capitallast2), 1,b2.return_datelast2) loan_capitallast2 
    from (select mobile, 
                 start_date start_datelast, 
                 loan_capital/100 loan_capitallast 
          from creditck.zx_loan_model_tmp_last2_loan 
          where rn=1
         ) b1
    left outer join (select mobile, 
                            due_date due_datelast2, 
                            to_date(settle_time) return_datelast2, 
                            loan_capital/100 loan_capitallast2 
                     from creditck.zx_loan_model_tmp_last2_loan
                     where rn=2
                    ) b2
         on b1.mobile=b2.mobile
;









drop table creditck.zx_loan_model_tmp_user_old;
create table if not exists creditck.zx_loan_model_tmp_user_old as
select mobile_num,
       max(id) as id
from(
    select t3.mobile as mobile_num,
           t3.user_id as id
    from hds.xd_c_receipt t1
    join hds.xd_c_loan_contract t2
         on t1.contract_id=t2.id
            and t1.is_settle='1'

    join hds.xd_c_apply_user t3
         on t2.apply_id=t3.id
) t
group by t.mobile_num
;


drop table creditck.zx_loan_model_mid_user;
create table if not exists creditck.zx_loan_model_mid_user as
select t1.mobile_num,
       t1.start_date as create_time,
       if(isnull(t2.mobile_num),0,1) as isold,
       if(isnull(t2.mobile_num),null,t2.id) as id
--from hdm.u_user_status t1
from creditck.var_user t1
left outer join creditck.zx_loan_model_tmp_user_old t2
on t1.mobile_num = t2.mobile_num
;






drop table creditck.zx_loan_model_mid_registration;
create table if not exists creditck.zx_loan_model_mid_registration as 
    select prov, 
           city,
           ds, 
           count(distinct mobile_num) total_cnt, 
           count(distinct case when date_diff <=3 then mobile_num end) / count(distinct mobile_num) new_rate
    from (select distinct a2.mobile_num, 
                 substr(a2.mobile_num,1,7) mobile_code, 
                 to_date(a1.insert_time) ds, 
                 datediff(to_date(trim(a1.insert_time)),to_date(trim(a2.create_date))) date_diff , 
                 a3.prov, 
                 a3.city
          from hds.xd_c_loan_apply a1
          join (select mobile_num, 
                       id, 
                       min(to_date(create_time)) create_date 
                from creditck.zx_loan_model_mid_user 
                group by mobile_num, id
               ) a2
              on a1.user_id=a2.id and a1.business_no ='BID'
          join edm.d_mobile_info a3
              on substr(a2.mobile_num,1,7)=a3.mobile_code 
         ) b 
    group by prov,city, ds 

;







create table if not exists creditck.zx_loan_model_dataset (

mobile                 string,
sex                    string,
cadate_diff            int,
maxedu                 int,
minedu                 int,
maxincome              int,
freq_a                 bigint,
avgm_a                 double,
freq_s                 bigint,
avgm_s                 double,
rldate_diff            int,
last_ratio             double,
ahead_or_not           int,
email_qq               int,
email_163              int,
village                int,
device_ss              int,
device_skb             int,
jjk_num                bigint,
xyk_num                bigint,
active_mons            bigint,
repay_sum              double,
repay_mon              bigint,
repay_avg              double,
trans_num              bigint,
trans_avg              double,
query_num              bigint,
pub_avg                double,
tel_mon                bigint,
tel_avg                double,
walout_sum             double,
new_rate               double,
total_cnt              bigint,

isold                  int

) 
partitioned by (ymd string)
row format delimited 
fields terminated by ',' 
stored as textfile
;

insert overwrite table creditck.zx_loan_model_dataset partition (ymd = '${TODAY_0D}') 
    select distinct t1.mobile_num as mobile, 
           case when isnull(t2.cert_no) then null when substr(t2.cert_no,-2,1) in (1,3,5,7,9) then 'm' when substr(t2.cert_no,-2,1) in (2,4,6,8,0) then 'f' else 'x' end sex, 
           case when isnull(t4a.apply1st_date) or isnull(t1.create_date) then null else datediff(to_date(trim(t4a.apply1st_date)),to_date(trim(t1.create_date))) end CADATE_DIFF, 
           t3.maxedu, 
           t3.minedu, 
           t3.maxincome, 
           t4a.freq_a, 
           t4a.avgm_a, 
           t4b.freq_s, 
           t4b.avgm_s,
           case when isnull(t8.start_datelast) or isnull(t8.return_datelast2) then null else datediff(to_date(trim(t8.start_datelast)),to_date(trim(t8.return_datelast2))) end rldate_diff, 
           case when isnull(t8.loan_capitallast) or isnull(t8.loan_capitallast2) then null else t8.loan_capitallast/t8.loan_capitallast2 end last_ratio, 
           case when isnull(t8.due_datelast2) or isnull(t8.return_datelast2) then null else datediff(to_date(trim(t8.due_datelast2)),to_date(trim(t8.return_datelast2))) end ahead_or_not, 
           if(isnotnull(t9.mobile),1,0) EMAIL_QQ, 
           if(isnotnull(t10.mobile),1,0) EMAIL_163, 
           if(isnotnull(t25.mobile_num),1,0) VILLAGE, 
           if(isnotnull(t22.mobile_num),1,0) DEVICE_SS, 
           if(isnotnull(t23.mobile_num),1,0) DEVICE_SKB, 
           nvl(t11.JJK_NUM,0) JJK_NUM, 
           nvl(t11.XYK_NUM,0) XYK_NUM,
           nvl(t13.ACTIVE_MONS,0) ACTIVE_MONS, 
           nvl(t13.REPAY_SUM,0) REPAY_SUM,
           nvl(t13.REPAY_MON,0) REPAY_MON, 
           nvl(t13.REPAY_AVG,0) REPAY_AVG, 
           nvl(t13.TRANS_NUM,0) TRANS_NUM, 
           nvl(t13.TRANS_AVG,0) TRANS_AVG, 
           nvl(t13.QUERY_NUM,0) QUERY_NUM, 
           nvl(t13.PUB_AVG,0) PUB_AVG, 
           nvl(t13.TEL_MON,0) TEL_MON, 
           nvl(t13.TEL_AVG,0) TEL_AVG, 
           nvl(t13.WALOUT_SUM,0) WALOUT_SUM,

           t26.new_rate,
           t26.total_cnt,

           t1.isold



    from (select mobile_num, 
                 min(to_date(create_time)) as create_date,
                 max(isold) as isold 
          from creditck.zx_loan_model_mid_user
          group by mobile_num
         ) t1

    left outer join (
          select mobile,
                 max(cert_no) as cert_no
          from(
               select a1.user_id,
                      a1.mobile, 
                      a1.cert_no 
               from hds.xd_c_apply_user a1
               join (select distinct user_id 
                     from hds.xd_c_loan_apply 
                     where business_no ='BID'
                    ) a2
               on a1.user_id=a2.user_id
          ) tmp
          group by tmp.mobile
         ) t2
         on t1.mobile_num=t2.mobile



    left outer join (select mobile, 
                 max(user_id) as user_id, 
                 count(distinct lower(email)) email_num, 
                 max(cast (trim(education) as int)) maxedu, 
                 min(cast (trim(education) as int)) minedu, 
                 max(cast (trim(income_range) as int)) maxincome, 
                 min(cast (trim(income_range) as int)) minincome 
          from hds.xd_c_apply_user 
          group by mobile
         ) t3
         on t1.mobile_num=t3.mobile


    left outer join (select user_id, 
                 min(capital_amount)/100 minm_a, 
                 max(capital_amount)/100 maxm_a, 
                 count(1) freq_a, 
                 avg(capital_amount)/100 avgm_a, 
                 count(distinct loan_pan) CARDNUM_A, 
                 min(loan_date) apply1st_date
          from hds.xd_c_loan_apply
          where business_no ='BID' 
          group by user_id
         ) t4a
         on t3.user_id=t4a.user_id


    left outer join (select user_id, 
                 count(1) freq_s, 
                 avg(capital_amount)/100 avgm_s 
          from hds.xd_c_loan_apply
          where business_no ='BID' and status='T'
          group by user_id
         ) t4b
         on t3.user_id=t4b.user_id


    left outer join creditck.zx_loan_model_mid_last2_loan t8
        on t1.mobile_num=t8.mobile


    left outer join (select distinct mobile 
                     from hds.xd_c_apply_user 
                     where lower(email) like '%qq.com'
                    ) t9
         on t1.mobile_num=t9.mobile


    left outer join (select distinct mobile 
                     from hds.xd_c_apply_user 
                     where lower(email) like '%163.com' or lower(email) like '%126.com'
                    ) t10 
        on t1.mobile_num=t10.mobile

    left outer join (select a1.mobile_num, 
                            count(distinct case when a2.card_type_name='借记卡' then a1.outcdno end) JJK_NUM,
                            count(distinct case when a2.card_type_name='信用卡' then a1.outcdno end) XYK_NUM
                     from creditck.zx_loan_model_mid_trans a1
                     join edm.d_card_info a2
                         on a1.outcdno_bin=a2.card_bin 
                     group by a1.mobile_num
                    ) t11
                    on t1.mobile_num=t11.mobile_num


    left outer join (select mobile_num, 
                            count(distinct substr(data_date,1,7)) ACTIVE_MONS, 
                            sum(case when TCAT_LV4_NAME='信用卡还款' then pro_am end)/100 REPAY_SUM,count(distinct case when TCAT_LV4_NAME='信用卡还款' then substr(data_date,1,7) end) REPAY_MON,
                            (sum(case when TCAT_LV4_NAME='信用卡还款' then pro_am end)/100)/count(distinct case when TCAT_LV4_NAME='信用卡还款' then substr(data_date,1,7) end) REPAY_AVG,
                            count(case when TCAT_LV3_NAME='转账汇款' then 1 end) TRANS_NUM, 
                            (sum(case when TCAT_LV3_NAME='转账汇款' then pro_am end)/100)/count(case when TCAT_LV3_NAME='转账汇款' then 1 end) TRANS_AVG,
                            count(case when TCAT_LV4_NAME='银行余款查询' then 1 end) QUERY_NUM,
                            count(case when TCAT_LV3_NAME='公缴类' then 1 end) PUB_NUM, 
                            (sum(case when TCAT_LV3_NAME='公缴类' then pro_am end)/100)/count(case when TCAT_LV3_NAME='转账汇款' then 1 end) PUB_AVG,
                            count(distinct case when TCAT_LV3_NAME='话费充值' then substr(data_date,1,7) end) TEL_MON,
                            (sum(case when TCAT_LV3_NAME='话费充值' then pro_am end)/100)/count(distinct case when TCAT_LV3_NAME='话费充值' then substr(data_date,1,7) end) TEL_AVG,
                            sum(case when TCAT_LV6_NAME='拉卡拉钱包充值' then pro_am end)/100 WALIN_SUM,
                            sum(case when TCAT_LV3_NAME='钱包提现' then pro_am end)/100 WALOUT_SUM, count(case when TCAT_LV3_NAME='钱包提现' then 1 end) WALIN_NUM
                     from creditck.zx_loan_model_mid_trans b
                     group by mobile_num
                    ) t13
                    on t1.mobile_num=t13.mobile_num


    left outer join (select distinct mobile_num 
                     from creditck.zx_loan_model_mid_trans
                     where psam_code='CBC3A3BF'
                    ) t22
                    on t1.mobile_num=t22.mobile_num


    left outer join (select distinct mobile_num 
                     from creditck.zx_loan_model_mid_trans
                     where psam_code='CBC3A3B2'
                    ) t23
                    on t1.mobile_num=t23.mobile_num

    left outer join (select distinct a1.mobile_num 
                     from creditck.zx_loan_model_mid_trans a1
                     join edm.d_card_info a2
                         on a1.outcdno_bin=a2.card_bin 
                     where a2.bank_short_name like '%农村信用社%' or bank_short_name like '%村镇银行%'
                    ) t25
                   on t1.mobile_num=t25.mobile_num


    left outer join edm.d_mobile_info t7
        on substr(t1.mobile_num,1,7)=t7.mobile_code 


    left outer join creditck.zx_loan_model_mid_registration t26
        on t7.prov=t26.prov and t7.city=t26.city and t26.ds=date_add(current_date, -1)

;







drop table creditck.zx_loan_model_tmp_scoreset_0;
create table if not exists creditck.zx_loan_model_tmp_scoreset_0 as
    select MOBILE,
           case when SEX='m' then 691.41 else 737.11 end score_sex,
           case when MAXEDU=1 then 548.49 when MAXEDU=2 then 661.19 when MAXEDU=3 then 735.87 when MAXEDU=4 then 702.77 else 685.34 end score_maxedu,
           case when MINEDU=1 then 671.25 when MINEDU=2 then 697.19 when MINEDU=3 then 731.46 when MINEDU=4 then 697.19 else 671.25 end score_minedu,
           case when MAXINCOME=1 then 709.59 when MAXINCOME=2 then 667.08 when MAXINCOME=3 then 692.36 when MAXINCOME=4 then 709.59
                when MAXINCOME=5 then 737.34 else 737.34 end score_maxincome,
           case when EMAIL_QQ=0 then 731.55 else 693.93 end score_qq,
           case when EMAIL_163=0 then 694.09 else 737.65 end score_163,
           case when DEVICE_SS=0 then 680.62 else 738.5 end score_devise_ss,
           case when DEVICE_SKB=0 then 686.15 else 747.82 end score_device_skb,
           case when VILLAGE=0 then 698.42 else 727.38 end score_village,
           case when RLDATE_DIFF=0 then 744.33 when RLDATE_DIFF<=3 then 751.88 when RLDATE_DIFF>1000 then 641.1 else 769.92 end score_rldate,
           case when AHEAD_OR_NOT<0 then 767.07  when AHEAD_OR_NOT>300 then 641.11 else 748.47 end score_ahead,
           case when CADATE_DIFF=0 then 633.57 when CADATE_DIFF=1 then 671.93 when CADATE_DIFF<=4 then 688.6 when CADATE_DIFF<=320 then 715.92 else 745.48 end score_cadate,  
           case when FREQ_A=1 then 594.89 when FREQ_A=2 then 619.91 when FREQ_A=3 then 670.09 else 742.65 end score_freq_a,
           case when FREQ_S=1 then 570.98 when FREQ_S=2 then 702.92 when FREQ_S<=4 then 775.76 else 814.96 end score_freq_s,
           case when AVGM_S<=5000 then 687.55 else 736.4 end score_avgm_s,

           case when LAST_RATIO<1 then 826.74 when LAST_RATIO=1 then 635.48 when LAST_RATIO<1.33 then 772.44 else 717.39 end score_last_ratio,
           case when JJK_NUM<=1 then 463.11 when JJK_NUM<=2 then 649.67 when JJK_NUM<=6 then 703.05 else 731.11 end score_jjk_num,
           case when XYK_NUM=0 then 673.74 when XYK_NUM=1 then 704.76 when XYK_NUM=2 then 717.37 when XYK_NUM=3 then 717.37 when XYK_NUM<=18 then 734.4 else 704.76 end score_xyk_num,
           case when ACTIVE_MONS<=1 then 491.89 when ACTIVE_MONS<=2 then 602.3 when ACTIVE_MONS<=3 then 653.53 when ACTIVE_MONS<=9 then 681.21 when ACTIVE_MONS<=14 then 712.16 
                when ACTIVE_MONS<=18 then 729.3 when ACTIVE_MONS<=22 then 756.73 else 797.96 end score_active_mon,
           case when REPAY_SUM<=1600 then 650.45 when REPAY_SUM<=15000 then 673.88 when REPAY_SUM<=35000 then 687.73 when REPAY_SUM<=75000 then 714.06 
                when REPAY_SUM<=180000 then 731.34 when REPAY_SUM<=500000 then 755.7 else 783.63 end score_repay_sum,  
           case when REPAY_MON<=1 then 653.66 when REPAY_MON<=7 then 678.87 when REPAY_MON<=14 then 715.2 when REPAY_MON<=17 then 741.86 
                when REPAY_MON<=23 then 769.08 else 814.03 end score_repay_mon,
           case when REPAY_AVG<=500 then 650.1 when REPAY_AVG<=2000 then 684.3 when REPAY_AVG<=16000 then 710.7 else 742.17 end score_repay_avg,
           case when TRANS_NUM=0 then 684.86 when TRANS_NUM<=9 then 731.12 else 754.65 end score_trans_num,
           case when TRANS_AVG<=350 then 685.45 when TRANS_AVG<=2400 then 737.39 else 762.6 end score_trans_avg,
           case when QUERY_NUM=0 then 667.74 when QUERY_NUM<=2 then 694.02 else 734.54 end score_query_num,
           case when PUB_AVG<=30 then 697.93 else 753.17 end score_pub_avg,
           case when TEL_MON=0 then 693.83 when TEL_MON<=4 then 704.56 when TEL_MON<=8 then 720.33 when TEL_MON<=17 then 736.15 else 793.25 end score_tel_mon,
           case when TEL_AVG<=30 then 693.83 when TEL_AVG<=70 then 683.39 when TEL_AVG<=130 then 714.42 else 731.13 end score_tel_avg,
           case when WALOUT_SUM<=50 then 699.36 when WALOUT_SUM<=500 then 735.42 else 800.73 end score_walout_sum,
           case when NEW_RATE<=0.05 then 666.54 when NEW_RATE<=0.3 then 718 when NEW_RATE<=0.5 then 680.55 when NEW_RATE<=0.75 then 637.88 
                when NEW_RATE<=0.95 then 415.32 else 637.88 end score_new_rate,
           case when TOTAL_CNT<=2 then 655.75 when TOTAL_CNT<=22 then 675.91 when TOTAL_CNT<=125 then 710.64 when TOTAL_CNT<=150 then 756.7 
                else 798.16 end score_total_cnt,

           isold

    from creditck.zx_loan_model_dataset
    where isold=1 and ymd='${TODAY_0D}'
;

insert into table creditck.zx_loan_model_tmp_scoreset_0 
    select MOBILE,
           case when SEX is null then 691.41 when SEX='m' then 691.41 else 737.11 end score_sex,
           case when MAXEDU IS NULL THEN 661.19 when MAXEDU=1 then 548.49 when MAXEDU=2 then 661.19 when MAXEDU=3 then 735.87 when MAXEDU=4 then 702.77 else 685.34 end score_maxedu,
           case when MINEDU IS NULL THEN 671.25 when MINEDU=1 then 671.25 when MINEDU=2 then 697.19 when MINEDU=3 then 731.46 when MINEDU=4 then 697.19 else 671.25 end score_minedu,
           case when MAXINCOME IS NULL then 667.08 when MAXINCOME=1 then 709.59 when MAXINCOME=2 then 667.08 when MAXINCOME=3 then 692.36 when MAXINCOME=4 then 709.59
                when MAXINCOME=5 then 737.34 else 737.34 end score_maxincome,
           case when EMAIL_QQ is null then 693.93 when EMAIL_QQ=0 then 731.55 else 693.93 end score_qq,
           case when EMAIL_QQ is null then 694.09 when EMAIL_163=0 then 694.09 else 737.65 end score_163,
           case when DEVICE_SS=0 then 680.62 else 738.5 end score_devise_ss,
           case when DEVICE_SKB=0 then 686.15 else 747.82 end score_device_skb,
           case when VILLAGE is null then 698.42 when VILLAGE=0 then 698.42 else 727.38 end score_village,
           0.0 as score_rldate,
           0.0 as score_ahead,
           case when CADATE_DIFF is null then 688.6 when CADATE_DIFF=0 then 633.57 when CADATE_DIFF=1 then 671.93 when CADATE_DIFF<=4 then 688.6 when CADATE_DIFF<=320 then 715.92 else 745.48 end score_cadate, 
           0.0 as score_freq_a, 
           0.0 as score_freq_s,
           0.0 as score_avgm_s,
           0.0 as score_last_ratio,
           case when JJK_NUM<=1 then 463.11 when JJK_NUM<=2 then 649.67 when JJK_NUM<=6 then 703.05 else 731.11 end score_jjk_num,
           case when XYK_NUM=0 then 673.74 when XYK_NUM=1 then 704.76 when XYK_NUM=2 then 717.37 when XYK_NUM=3 then 717.37 when XYK_NUM<=18 then 734.4 else 704.76 end score_xyk_num,
           case when ACTIVE_MONS<=1 then 491.89 when ACTIVE_MONS<=2 then 602.3 when ACTIVE_MONS<=3 then 653.53 when ACTIVE_MONS<=9 then 681.21 when ACTIVE_MONS<=14 then 712.16 
                when ACTIVE_MONS<=18 then 729.3 when ACTIVE_MONS<=22 then 756.73 else 797.96 end score_active_mon,
           case when REPAY_SUM<=1600 then 650.45 when REPAY_SUM<=15000 then 673.88 when REPAY_SUM<=35000 then 687.73 when REPAY_SUM<=75000 then 714.06 
                when REPAY_SUM<=180000 then 731.34 when REPAY_SUM<=500000 then 755.7 else 783.63 end score_repay_sum,  
           case when REPAY_MON<=1 then 653.66 when REPAY_MON<=7 then 678.87 when REPAY_MON<=14 then 715.2 when REPAY_MON<=17 then 741.86 
                when REPAY_MON<=23 then 769.08 else 814.03 end score_repay_mon,
           case when REPAY_AVG<=500 then 650.1 when REPAY_AVG<=2000 then 684.3 when REPAY_AVG<=16000 then 710.7 else 742.17 end score_repay_avg,
           case when TRANS_NUM=0 then 684.86 when TRANS_NUM<=9 then 731.12 else 754.65 end score_trans_num,
           case when TRANS_AVG<=350 then 685.45 when TRANS_AVG<=2400 then 737.39 else 762.6 end score_trans_avg,
           case when QUERY_NUM=0 then 667.74 when QUERY_NUM<=2 then 694.02 else 734.54 end score_query_num,
           case when PUB_AVG<=30 then 697.93 else 753.17 end score_pub_avg,
           case when TEL_MON=0 then 693.83 when TEL_MON<=4 then 704.56 when TEL_MON<=8 then 720.33 when TEL_MON<=17 then 736.15 else 793.25 end score_tel_mon,
           case when TEL_AVG<=30 then 693.83 when TEL_AVG<=70 then 683.39 when TEL_AVG<=130 then 714.42 else 731.13 end score_tel_avg,
           case when WALOUT_SUM<=50 then 699.36 when WALOUT_SUM<=500 then 735.42 else 800.73 end score_walout_sum,
           case when NEW_RATE<=0.05 then 666.54 when NEW_RATE<=0.3 then 718 when NEW_RATE<=0.5 then 680.55 when NEW_RATE<=0.75 then 637.88 
                when NEW_RATE<=0.95 then 415.32 else 637.88 end score_new_rate,
           case when TOTAL_CNT<=2 then 655.75 when TOTAL_CNT<=22 then 675.91 when TOTAL_CNT<=125 then 710.64 when TOTAL_CNT<=150 then 756.7 
                else 798.16 end score_total_cnt,

           isold

    from creditck.zx_loan_model_dataset
    where isold=0 and ymd='${TODAY_0D}'
;









drop table creditck.zx_loan_model_tmp_scoreset_1;
create table if not exists creditck.zx_loan_model_tmp_scoreset_1 as
select 
mobile,
floor(score_sex)            score_sex,
floor(score_maxedu)         score_maxedu,
floor(score_minedu)         score_minedu,
floor(score_maxincome)      score_maxincome,
floor(score_qq)             score_qq,
floor(score_163)            score_163,
floor(score_devise_ss)      score_devise_ss,
floor(score_device_skb)     score_device_skb,
floor(score_village)        score_village,
floor(score_rldate)         score_rldate,
floor(score_ahead)          score_ahead,
floor(score_cadate)         score_cadate,
floor(score_freq_a)         score_freq_a,
floor(score_freq_s)         score_freq_s,
floor(score_avgm_s)         score_avgm_s,
floor(score_last_ratio)     score_last_ratio,
floor(score_jjk_num)        score_jjk_num,
floor(score_xyk_num)        score_xyk_num,
floor(score_active_mon)     score_active_mon,
floor(score_repay_sum)      score_repay_sum,
floor(score_repay_mon)      score_repay_mon,
floor(score_repay_avg)      score_repay_avg,
floor(score_trans_num)      score_trans_num,
floor(score_trans_avg)      score_trans_avg,
floor(score_query_num)      score_query_num,
floor(score_pub_avg)        score_pub_avg,
floor(score_tel_mon)        score_tel_mon,
floor(score_tel_avg)        score_tel_avg,
floor(score_walout_sum)     score_walout_sum,
floor(score_new_rate)       score_new_rate,
floor(score_total_cnt)      score_total_cnt,

floor(score_sex*0.63
+score_maxedu*0.72
+score_minedu*(-0.17)
+score_maxincome*0.33
+score_rldate*1.38
+score_qq*(-0.52)
+score_163*0.33
+score_devise_ss*(-0.33)
+score_device_skb*0.44
+score_village*(-2.56)
+score_cadate*0.69
+score_freq_s*0.96
+score_jjk_num*(-0.67)
+score_xyk_num*(-0.3)
+score_active_mon*0.57
+score_repay_sum*0.27
+score_repay_mon*0.21
+score_repay_avg*(-0.3)
+score_trans_num*(-0.65)
+score_trans_avg*0.59
+score_query_num*0.25
+score_pub_avg*(-0.3)
+score_tel_mon*(-0.8)
+score_tel_avg*(-0.36)
+score_walout_sum*0.32
+score_new_rate*(-0.18)
+score_total_cnt*0.45) as score_sum,


floor(score_sex*0.632+score_maxedu*0.29+score_minedu*0.085+score_maxincome*0.196+score_qq*(-0.161)+score_163*0.219) as s1,

floor(score_ahead*(-1.279) +score_freq_a*(-0.266) +score_freq_s*1.417 +score_avgm_s*(-0.402) +score_rldate*0.15 +score_last_ratio*0.211) as s2,

floor(score_devise_ss*0.222+score_device_skb*0.26+score_village*(-0.727)+score_cadate*0.483+score_jjk_num*0.192+score_xyk_num*(-0.123)+score_active_mon*0.405) as s3,

floor(score_repay_sum*0.518 +score_repay_mon*0.127 +score_repay_avg*(-0.633) +score_trans_num*(-0.482)+score_trans_avg*0.525 
+score_query_num*0.104 +score_pub_avg*(-0.109) +score_tel_mon*(-0.962) +score_tel_avg*0.027 +score_walout_sum*0.073) as s4,

floor(score_new_rate*(-0.098) +score_total_cnt*0.249) as s5,


isold
from creditck.zx_loan_model_tmp_scoreset_0 
where isold=1
;


insert into table creditck.zx_loan_model_tmp_scoreset_1
select 
mobile,
floor(score_sex)            score_sex,
floor(score_maxedu)         score_maxedu,
floor(score_minedu)         score_minedu,
floor(score_maxincome)      score_maxincome,
floor(score_qq)             score_qq,
floor(score_163)            score_163,
floor(score_devise_ss)      score_devise_ss,
floor(score_device_skb)     score_device_skb,
floor(score_village)        score_village,
floor(score_rldate)         score_rldate,
floor(score_ahead)          score_ahead,
floor(score_cadate)         score_cadate,
floor(score_freq_a)         score_freq_a,
floor(score_freq_s)         score_freq_s,
floor(score_avgm_s)         score_avgm_s,
floor(score_last_ratio)     score_last_ratio,
floor(score_jjk_num)        score_jjk_num,
floor(score_xyk_num)        score_xyk_num,
floor(score_active_mon)     score_active_mon,
floor(score_repay_sum)      score_repay_sum,
floor(score_repay_mon)      score_repay_mon,
floor(score_repay_avg)      score_repay_avg,
floor(score_trans_num)      score_trans_num,
floor(score_trans_avg)      score_trans_avg,
floor(score_query_num)      score_query_num,
floor(score_pub_avg)        score_pub_avg,
floor(score_tel_mon)        score_tel_mon,
floor(score_tel_avg)        score_tel_avg,
floor(score_walout_sum)     score_walout_sum,
floor(score_new_rate)       score_new_rate,
floor(score_total_cnt)      score_total_cnt,


floor(score_sex*0.53
+score_maxedu*0.05
+score_minedu*0.17
+score_maxincome*0.27
+score_qq*(-0.04)
+score_163*(-0.21)
+score_devise_ss*0.14
+score_device_skb*0.04
+score_village*(-0.71)
+score_cadate*0.65
+score_jjk_num*(-0.28)
+score_xyk_num*0.4
+score_active_mon*(-0.14)
+score_repay_sum*0.14
+score_repay_mon*0.57
+score_repay_avg*(-0.21)
+score_trans_num*0.19
+score_trans_avg*0.04
+score_query_num*0.17
+score_pub_avg*(-0.4)
+score_tel_mon*(-0.27)
+score_tel_avg*(-0.28)
+score_walout_sum*(-0.86)
+score_new_rate*0.67
+score_total_cnt*0.37) as score_sum , 


floor(score_sex*0.65683
+score_maxedu*(-0.14741)
+score_minedu*0.27157
+score_maxincome*0.02976
+score_qq*(-0.16339)
+score_163*(-0.21432)) as s1,

floor(550) as s2,

floor(score_devise_ss*0.29146
+score_device_skb*0.24349
+score_village*(-1.20344)
+score_cadate*0.98396
+score_jjk_num*(-0.19769)
+score_xyk_num*0.37193
+score_active_mon*(-0.53859)) as s3,

floor(score_repay_sum*0.4452
+score_repay_mon*1.07614
+score_repay_avg*(-0.49233)
+score_trans_num*(0.5032)
+score_trans_avg*0.39268
+score_query_num*0.27858
+score_pub_avg*(-0.44552)
+score_tel_mon*(-0.41477)
+score_tel_avg*(-0.21999)
+score_walout_sum*(-1.26336)) as s4,

floor(score_new_rate*0.28506 +score_total_cnt*0.22234) as s5,


isold
from creditck.zx_loan_model_tmp_scoreset_0 
where isold=0
;





drop table creditck.zx_loan_model_tmp_scoreset_1_statistics;
create table if not exists creditck.zx_loan_model_tmp_scoreset_1_statistics as
select 
min(s1) min1,
max(s1)-min(s1) max_min1,
min(s2) min2,
max(s2)-min(s2) max_min2,
min(s3) min3,
max(s3)-min(s3) max_min3,
min(s4) min4,
max(s4)-min(s4) max_min4,
min(s5) min5,
max(s5)-min(s5) max_min5,

isold
from creditck.zx_loan_model_tmp_scoreset_1
group by isold
;






drop table creditck.zx_loan_model_tmp_scoreset_2;
create table if not exists creditck.zx_loan_model_tmp_scoreset_2 as
select 
mobile                ,
score_sex             ,
score_maxedu          ,
score_minedu          ,
score_maxincome       ,
score_qq              ,
score_163             ,
score_devise_ss       ,
score_device_skb      ,
score_village         ,
score_rldate          ,
score_ahead           ,
score_cadate          ,
score_freq_a          ,
score_freq_s          ,
score_avgm_s          ,
score_last_ratio      ,
score_jjk_num         ,
score_xyk_num         ,
score_active_mon      ,
score_repay_sum       ,
score_repay_mon       ,
score_repay_avg       ,
score_trans_num       ,
score_trans_avg       ,
score_query_num       ,
score_pub_avg         ,
score_tel_mon         ,
score_tel_avg         ,
score_walout_sum      ,
score_new_rate        ,
score_total_cnt       ,

score_sum,

case when max_min1=0 then floor(s1) else floor((s1-min1)/max_min1 *550+300) end score_class1,
case when max_min2=0 then floor(s2) else floor((s2-min2)/max_min2 *550+300) end score_class2,
case when max_min3=0 then floor(s3) else floor((s3-min3)/max_min3 *550+300) end score_class3,
case when max_min4=0 then floor(s4) else floor((s4-min4)/max_min4 *550+300) end score_class4,
case when max_min5=0 then floor(s5) else floor((s5-min5)/max_min5 *550+300) end score_class5,

t1.isold as isold
from creditck.zx_loan_model_tmp_scoreset_1 t1
join creditck.zx_loan_model_tmp_scoreset_1_statistics t2
on t1.isold=t2.isold
;





drop table creditck.zx_loan_model_tmp_scoreset_3;
create table if not exists creditck.zx_loan_model_tmp_scoreset_3 as
select
t1.mobile             ,
score_sex             ,
score_maxedu          ,
score_minedu          ,
score_maxincome       ,
score_qq              ,
score_163             ,
score_devise_ss       ,
score_device_skb      ,
score_village         ,
score_rldate          ,
score_ahead           ,
score_cadate          ,
score_freq_a          ,
score_freq_s          ,
score_avgm_s          ,
score_last_ratio      ,
score_jjk_num         ,
score_xyk_num         ,
score_active_mon      ,
score_repay_sum       ,
score_repay_mon       ,
score_repay_avg       ,
score_trans_num       ,
score_trans_avg       ,
score_query_num       ,
score_pub_avg         ,
score_tel_mon         ,
score_tel_avg         ,
score_walout_sum      ,
score_new_rate        ,
score_total_cnt       ,

case when isnull(t2.mobile) then t1.score_sum when t2.black_type like '%信用%' then 300 else t1.score_sum-100 end score_sum,

score_class1,
score_class2,
score_class3,
score_class4,
score_class5, 

case when isnull(t2.mobile) then 0 else t2.is_black end is_black,
case when isnull(t2.mobile) then '' else t2.black_type end black_type,

isold
from creditck.zx_loan_model_tmp_scoreset_2 t1
left outer join creditck.var_blacklist_end t2
on t1.mobile=t2.mobile and t2.is_black=1
;




drop table creditck.zx_loan_model_tmp_scoreset_4;
create table if not exists creditck.zx_loan_model_tmp_scoreset_4 as
select 

mobile                ,
score_sex             ,
score_maxedu          ,
score_minedu          ,
score_maxincome       ,
score_qq              ,
score_163             ,
score_devise_ss       ,
score_device_skb      ,
score_village         ,
score_rldate          ,
score_ahead           ,
score_cadate          ,
score_freq_a          ,
score_freq_s          ,
score_avgm_s          ,
score_last_ratio      ,
score_jjk_num         ,
score_xyk_num         ,
score_active_mon      ,
score_repay_sum       ,
score_repay_mon       ,
score_repay_avg       ,
score_trans_num       ,
score_trans_avg       ,
score_query_num       ,
score_pub_avg         ,
score_tel_mon         ,
score_tel_avg         ,
score_walout_sum      ,
score_new_rate        ,
score_total_cnt       ,

case when score_sum>850 then 850 when score_sum<300 then 300 else score_sum end score_sum,
case when score_class1>850 then 850 when score_class1<300 then 300 else score_class1 end score_class1,
case when score_class2>850 then 850 when score_class2<300 then 300 else score_class2 end score_class2,
case when score_class3>850 then 850 when score_class3<300 then 300 else score_class3 end score_class3,
case when score_class4>850 then 850 when score_class4<300 then 300 else score_class4 end score_class4,
case when score_class5>850 then 850 when score_class5<300 then 300 else score_class5 end score_class5,
   
is_black,
black_type,

isold
FROM creditck.zx_loan_model_tmp_scoreset_3
; 







create table if not exists creditck.zx_loan_model_scoreset (

mobile               string,
score_sex            BIGINT,
score_maxedu         BIGINT,
score_minedu         BIGINT,
score_maxincome      BIGINT,
score_qq             BIGINT,
score_163            BIGINT,
score_devise_ss      BIGINT,
score_device_skb     BIGINT,
score_village        BIGINT,
score_rldate         BIGINT,
score_ahead          BIGINT,
score_cadate         BIGINT,
score_freq_a         BIGINT,
score_freq_s         BIGINT,
score_avgm_s         BIGINT,
score_last_ratio     BIGINT,
score_jjk_num        BIGINT,
score_xyk_num        BIGINT,
score_active_mon     BIGINT,
score_repay_sum      BIGINT,
score_repay_mon      BIGINT,
score_repay_avg      BIGINT,
score_trans_num      BIGINT,
score_trans_avg      BIGINT,
score_query_num      BIGINT,
score_pub_avg        BIGINT,
score_tel_mon        BIGINT,
score_tel_avg        BIGINT,
score_walout_sum     BIGINT,
score_new_rate       BIGINT,
score_total_cnt      BIGINT,

score_sum            BIGINT,
SCORE_RANK           string,
SCORE_SUM1           BIGINT,
SCORE_RANK1          string,
SCORE_SUM2           BIGINT,
SCORE_RANK2          string,
SCORE_SUM3           BIGINT,
SCORE_RANK3          string,
SCORE_SUM4           BIGINT,
SCORE_RANK4          string,
SCORE_SUM5           BIGINT,
SCORE_RANK5          string,

is_black             int   ,
black_type           string,

isold                int
)
partitioned by (ymd string) 
stored as textfile
;

insert overwrite table creditck.zx_loan_model_scoreset partition (ymd = '${TODAY_0D}')
select

mobile                ,
score_sex             ,
score_maxedu          ,
score_minedu          ,
score_maxincome       ,
score_qq              ,
score_163             ,
score_devise_ss       ,
score_device_skb      ,
score_village         ,
score_rldate          ,
score_ahead           ,
score_cadate          ,
score_freq_a          ,
score_freq_s          ,
score_avgm_s          ,
score_last_ratio      ,
score_jjk_num         ,
score_xyk_num         ,
score_active_mon      ,
score_repay_sum       ,
score_repay_mon       ,
score_repay_avg       ,
score_trans_num       ,
score_trans_avg       ,
score_query_num       ,
score_pub_avg         ,
score_tel_mon         ,
score_tel_avg         ,
score_walout_sum      ,
score_new_rate        ,
score_total_cnt       ,

score_sum,
case when score_sum BETWEEN 300 and 399 then 'C' when score_sum BETWEEN 400 and 449 then 'CC' when score_sum BETWEEN 450 and 499 then 'CCC' 
     when score_sum BETWEEN 500 and 549 then 'B' when score_sum BETWEEN 550 and 599 then 'BB' when score_sum BETWEEN 600 and 649 then 'BBB'
     when score_sum BETWEEN 650 and 699 then 'A' when score_sum BETWEEN 700 and 749 then 'AA' when score_sum BETWEEN 750 and 850 then 'AAA'
end  SCORE_RANK,
score_class1 as SCORE_SUM1,
case when score_class1 BETWEEN 300 and 399 then 'C' when score_class1 BETWEEN 400 and 449 then 'CC' when score_class1 BETWEEN 450 and 499 then 'CCC' 
     when score_class1 BETWEEN 500 and 549 then 'B' when score_class1 BETWEEN 550 and 599 then 'BB' when score_class1 BETWEEN 600 and 649 then 'BBB'
     when score_class1 BETWEEN 650 and 699 then 'A' when score_class1 BETWEEN 700 and 749 then 'AA' when score_class1 BETWEEN 750 and 850 then 'AAA'
end  SCORE_RANK1,
score_class2 as SCORE_SUM2,
case when score_class2 BETWEEN 300 and 399 then 'C' when score_class2 BETWEEN 400 and 449 then 'CC' when score_class2 BETWEEN 450 and 499 then 'CCC' 
     when score_class2 BETWEEN 500 and 549 then 'B' when score_class2 BETWEEN 550 and 599 then 'BB' when score_class2 BETWEEN 600 and 649 then 'BBB'
     when score_class2 BETWEEN 650 and 699 then 'A' when score_class2 BETWEEN 700 and 749 then 'AA' when score_class2 BETWEEN 750 and 850 then 'AAA'
end  SCORE_RANK2,
score_class3 as SCORE_SUM3,
case when score_class3 BETWEEN 300 and 399 then 'C' when score_class3 BETWEEN 400 and 449 then 'CC' when score_class3 BETWEEN 450 and 499 then 'CCC' 
     when score_class3 BETWEEN 500 and 549 then 'B' when score_class3 BETWEEN 550 and 599 then 'BB' when score_class3 BETWEEN 600 and 649 then 'BBB'
     when score_class3 BETWEEN 650 and 699 then 'A' when score_class3 BETWEEN 700 and 749 then 'AA' when score_class3 BETWEEN 750 and 850 then 'AAA'
end  SCORE_RANK3,
score_class4 as SCORE_SUM4,
case when score_class4 BETWEEN 300 and 399 then 'C' when score_class4 BETWEEN 400 and 449 then 'CC' when score_class4 BETWEEN 450 and 499 then 'CCC' 
     when score_class4 BETWEEN 500 and 549 then 'B' when score_class4 BETWEEN 550 and 599 then 'BB' when score_class4 BETWEEN 600 and 649 then 'BBB'
     when score_class4 BETWEEN 650 and 699 then 'A' when score_class4 BETWEEN 700 and 749 then 'AA' when score_class4 BETWEEN 750 and 850 then 'AAA'
end  SCORE_RANK4,
score_class5 as SCORE_SUM5,
case when score_class5 BETWEEN 300 and 399 then 'C' when score_class5 BETWEEN 400 and 449 then 'CC' when score_class5 BETWEEN 450 and 499 then 'CCC' 
     when score_class5 BETWEEN 500 and 549 then 'B' when score_class5 BETWEEN 550 and 599 then 'BB' when score_class5 BETWEEN 600 and 649 then 'BBB'
     when score_class5 BETWEEN 650 and 699 then 'A' when score_class5 BETWEEN 700 and 749 then 'AA' when score_class5 BETWEEN 750 and 850 then 'AAA'
end  SCORE_RANK5,

is_black,
black_type,


isold
from(
    select *, 
           Row_Number() OVER (partition by mobile ORDER BY score_sum asc) rn 
    FROM creditck.zx_loan_model_tmp_scoreset_4
) t
where t.rn=1
;





"

















echo; echo; echo; echo
echo "###################################`date '+%Y-%m-%d %H:%M:%S'`  小贷评分开始 ##########################################"
echo; echo; echo; echo


IFS=\;
for sql in $hive_sql 
do
    while true
    do
        echo -ne "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n"
        echo "======================================================================================================================================"
        echo -ne "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n"



sudo -u hdfs hive <<EOF

set hive.auto.convert.join=false;
set mapreduce.reduce.shuffle.input.buffer.percent=0.1;
set mapreduce.reduce.shuffle.parallelcopies=9;


$sql;

EOF



       if [ $? -eq 0 ];then
           break
       else
           sleep 5m
       fi

    done
done


echo; echo; echo; echo
echo "###################################`date '+%Y-%m-%d %H:%M:%S'`  小贷评分结束 ##########################################"
echo; echo; echo; echo




###########################################################################################################################################################################
#
#                                 export hive to oracle
#
###########################################################################################################################################################################









sudo -u hdfs hive <<EOF

set hive.auto.convert.join=false;
set mapreduce.reduce.shuffle.input.buffer.percent=0.1;
set mapreduce.reduce.shuffle.parallelcopies=9;

drop table  creditck.zx_loan_model_scoreset_export;
create table if not exists creditck.zx_loan_model_scoreset_export as
select 
mobile                ,
score_sex             ,
score_maxedu          ,
score_minedu          ,
score_maxincome       ,
score_qq              ,
score_163             ,
score_devise_ss       ,
score_device_skb      ,
score_village         ,
score_rldate          ,
score_ahead           ,
score_cadate          ,
score_freq_a          ,
score_freq_s          ,
score_avgm_s          ,
score_last_ratio      ,
score_jjk_num         ,
score_xyk_num         ,
score_active_mon      ,
score_repay_sum       ,
score_repay_mon       ,
score_repay_avg       ,
score_trans_num       ,
score_trans_avg       ,
score_query_num       ,
score_pub_avg         ,
score_tel_mon         ,
score_tel_avg         ,
score_walout_sum      ,
score_new_rate        ,
score_total_cnt       ,

score_sum             ,             
SCORE_RANK            ,
SCORE_SUM1            ,
SCORE_RANK1           ,
SCORE_SUM2            ,
SCORE_RANK2           ,
SCORE_SUM3            ,
SCORE_RANK3           ,
SCORE_SUM4            ,
SCORE_RANK4           ,
SCORE_SUM5            ,
SCORE_RANK5           ,

is_black


FROM creditck.zx_loan_model_scoreset
where ymd='${TODAY_0D}' and length(mobile)=11

;


EOF





















#oracle_jdbc=jdbc:oracle:thin:@10.5.19.26:1521/ZXDB
oracle_jdbc=jdbc:oracle:thin:@10.1.2.187:1521/CRDNEWDB

#oracle_username=zxuser
oracle_username=EM

#oracle_password=zxuser
oracle_password=em_1234

oracle_table=CDT_NEW_SCORE_FINAL

hadoop_dir=/user/hive/warehouse/creditck.db/zx_loan_model_scoreset_export





sudo -u hdfs sqoop export --connect ${oracle_jdbc} --username ${oracle_username} --password ${oracle_password} --table ${oracle_table} --export-dir ${hadoop_dir} --update-key MOBILE --update-mode allowinsert --input-fields-terminated-by '\001' --input-null-string '\\N' --input-null-non-string '\\N' -m 8 

if [ $? -eq 0 ];then
    echo;echo;echo "导入数据成功: from ${hadoop_dir} to ${oracle_table}";echo;echo
else
    echo;echo;echo "导入数据失败: from ${hadoop_dir} to ${oracle_table}";echo;echo
fi

















