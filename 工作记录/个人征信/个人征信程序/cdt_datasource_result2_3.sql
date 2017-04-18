#!/bin/sh
TODAY_2Y=$(date --date='2 year ago' +%Y%m%d)
TODAY_0D=$(date +%Y%m%d)
TODAY_0d=$(date +%Y-%m-%d)
TODAY_1D=$(date --date='1 day ago' +%Y%m%d)
TODAY_1d=$(date --date='1 day ago' +%Y-%m-%d)
TODAY_2D=$(date --date='2 day ago' +%Y%m%d)
TODAY_2d=$(date --date='2 day ago' +%Y-%m-%d)
DATE_0m=$(date --date='0 month ago' +%Y-%m-)01
DATE_0M=$(date --date='0 month ago' +%Y%m)01
day=$(date +%d)
if test $day -lt 28;then
DATE_3m=$(date --date='3 month ago' +%Y-%m-)01
DATE_6m=$(date --date='6 month ago' +%Y-%m-)01
DATE_12m=$(date --date='1 year ago' +%Y-%m-)01
DATE_12M=$(date --date='1 year ago' +%Y%m)01
DATE_24M=$(date --date='2 year ago' +%Y%m)01
else
DATE_3m=$(date --date='3 month ago 10 day ago' +%Y-%m-)01
DATE_6m=$(date --date='6 month ago 10 day ago' +%Y-%m-)01
DATE_12m=$(date --date='1 year ago 10 day ago' +%Y-%m-)01
DATE_12M=$(date --date='1 year ago 10 day ago' +%Y%m)01
DATE_24M=$(date --date='2 year ago 10 day ago' +%Y%m)01
fi

echo '--------------------在网时间 zw_time,注册时间 person_status 1.有注册有交易 2.有注册无交易 3.无注册有交易--------------'
sudo -u hdfs hive <<EOF
add jar /home/hdfs/lakala_udf.jar;
create temporary function IDCard as 'com.lkl.hive.udf.IDCard';
create temporary function mobile as 'com.lkl.hive.udf.Mobile';

insert overwrite table creditck.mid_user_all_temp partition (ymd='${TODAY_1D}')
select 
nvl(mobilephone,userid) as mobile_num,
nvl(min(createtime),min(updatetime)) as start_date,
max(lastlogintime) as end_date,
'1' as flag
from hds.nuc_ecusr 
where mobile(nvl(mobilephone,userid))=1 
      and (from_unixtime(unix_timestamp(createtime),'yyyyMMdd')='${TODAY_1D}' 
      or from_unixtime(unix_timestamp(lastlogintime),'yyyyMMdd')='${TODAY_1D}') 
group by nvl(mobilephone,userid);
 
insert into table creditck.mid_user_all_temp partition (ymd=$TODAY_1D)
select 
mobile_num,
min(data_date),
max(data_date),
'2' as flag
from edw.dw_fact_trans_new 
where mobile(mobile_num)=1 and ymd='${TODAY_1D}'
group by mobile_num;

drop table creditck.var_user;
create table creditck.var_user
as
select t.mobile_num,
t.start_date as start_date,
datediff(t.end_date,t.start_date) as zw_time,
t.person_status,
t.reg_date,
t2.prov,t2.city,
t3.city_lv as person_living
from(
    select mobile_num,
           min(start_date) as start_date,
           case when max(end_date) is not null then max(end_date) else from_unixtime(unix_timestamp(),'yyyy-MM-dd HH:mm:ss') end as end_date,
           case when min(flag)<max(flag) then '1'
                when max(flag)='1' then '2'
                when min(flag)='2' then '3' end as person_status,
           min(case when flag='1' then start_date end) as reg_date --注册时间
    from creditck.mid_user_all_temp 
    where mobile(mobile_num)=1 group by mobile_num) t
left join edm.d_mobile_info t2 on cast(substr(t.mobile_num,1,7) as int)=cast(t2.mobile_code as int)
left join sas.d_city_level t3 on split(t2.city,'[市(地区)+(林区)+]+')[0]=t3.city_name;
--DROP表
drop table creditck.var_person_edu_in_pos;
drop table creditck.var_xd_delay_7day_mobile;
drop table creditck.var_fayuan_blacklist;
drop table creditck.var_gd_blacklist;
drop table creditck.var_p2p_blacklist;
drop table creditck.var_user_blacklist;
drop table creditck.var_blacklist_new;
EOF
tmp=$?
NOWTIME=$(date +%Y-%m-%d/%H:%M:%S)
if test $tmp -eq 0; then
echo $NOWTIME'-------3-------目标用户var_user创建成功------'$tmp >> logs/logs_cdt_datasource_result
else
echo $NOWTIME'-------3-------目标用户var_user创建失败------'$tmp >> logs/logs_cdt_datasource_result
fi



echo '---------------------学历收入职位person_education,person_income,person_position------------------'
impala-shell -r <<EOF
create table creditck.var_person_edu_in_pos
as
select mobile,
       max(case when edu_id=1 then education end) as person_education,
       max(case when income_id=1 then income_range end) as person_income,
       min(case when position_id=1 then position end) as person_position
from
(select mobile,
        education,
        row_number() over(partition by (case when education is not null then mobile end) order by (case when education is not null then insert_time end) desc) as edu_id,
        income_range,
        row_number() over(partition by (case when income_range is not null then mobile end) order by (case when income_range is not null then insert_time end) desc) as income_id,
        case when position in ('1','副总经理及以上级别管理人员') then  1
             when position in ('2','高级经理及同等级别管理人员') then  2
             when position in ('3','厅级以上行政领导') then  3
             when position in ('4','工会主席、党委书记/副书记') then  4
             when position in ('5','总工程师、副总工程师等技能人员') then  5
             when position in ('6','处级以上行政领导') then  6
             when position in ('7','经理及同等级别管理人员','经理') then  7
             when position in ('8','高级工程师、高级设计师、it架构师') then  8
             when position in ('9','国家级研究所研究员等技术人员') then  9
             when position in ('10','科级以上行政领导') then  10
             when position in ('11','主任、主管、经理助理、助理、店长') then  11
             when position in ('12','工程师、研究员、项目助理') then  12
             when position in ('13','一般公务员') then  13
             when position in ('14','普通职员、技师') then  14
             when position in ('15','营业员、收银员、商场导购') then  15
             when position in ('16','厨师、司机、保安、修理工人') then  16
             when position in ('17','业务经纪人、中介代理人员') then  17
             when position in ('18','操作工') then   18
             when position in ('19','搬运工、清洁工、勤杂工、矿工') then  19
             when position in ('20','内退、停薪留职、实习生') then  20
             when position in ('21','其他') then  21
             else (case when position is not null then 21 end)
             end as position,
        row_number() over(partition by (case when position is not null then mobile end) order by (case when position is not null then insert_time end) desc) as position_id
 from ODS.XD_C_LOAN_ORDER
) tmp group by mobile;
EOF
tmp=$?
NOWTIME=$(date +%Y-%m-%d/%H:%M:%S)
if test $tmp -eq 0; then 
echo $NOWTIME'-------3-------学历收入职位var_person_edu_in_pos创建成功------'$tmp >> logs/logs_cdt_datasource_result
else
echo $NOWTIME'-------3-------学历收入职位var_person_edu_in_pos创建失败------'$tmp >> logs/logs_cdt_datasource_result
fi


echo  '-------------手机型号 mobile_kind-------------'
sudo -u hdfs hive <<EOF
drop table creditck.var_mobile_kind;
create table creditck.var_mobile_kind as
select a.mobile as mobile_num,
       a.ori_model,
       b.origin_brand,
       b.type_level as mobile_kind
from 
(select mobile,ori_model,upd_date,row_number() over(partition by mobile order by upd_date desc) as date_rk 
 from 
   (select user_id as mobile, imei_code as ori_model, upload_date as upd_date
    from ods.tab_mobile_info  --设备基础信息表
    where length(user_id)=11 and upload_date is not null group by user_id, imei_code, upload_date
    union all
    select userid as mobile, imei_code as ori_model, upload_date as upd_date
    from ods.tab_mobile_open_times  --设备打开表
    where length(userid)=11 and upload_date is not null group by userid, imei_code, upload_date
    union all
    select userid as mobile, nvl(brand, model) as ori_model, upload_time as upd_date
    from ods.tab_mobile_register_info  --用户注册表
    where length(userid)=11 and upload_time is not null group by userid, nvl(brand, model), upload_time) t
) a 
left join shangs.smart_terminal_model_20141114 b on a.ori_model = b.origin_model
where a.date_rk=1;
EOF
tmp=$?
NOWTIME=$(date +%Y-%m-%d/%H:%M:%S)
if test $tmp -eq 0; then
echo $NOWTIME'--------3------var_mobile_kind创建成功------'$tmp >> logs/logs_cdt_datasource_result
else
echo $NOWTIME'--------3------var_mobile_kind创建失败------'$tmp >> logs/logs_cdt_datasource_result
fi


echo  '---------------性别 person_sex 0女1男,年龄 person_age,户籍地 person_hukou--------'
sudo -u hdfs hive<<EOF
drop table creditck.var_person_identity;
create table creditck.var_person_identity  
as
select t2.mobile as mobile_num,
t2.sex as person_sex, 
t2.age as person_age,
t2.household_city,
t4.city_lv as person_hukou 
from hdm.u_user_identity_info t2
left join sas.d_city_level t4 on split(t2.household_city,'(市)+|(盟)+|(地区)+')[0]=t4.city_name;
EOF
tmp=$?
NOWTIME=$(date +%Y-%m-%d/%H:%M:%S)
if test $tmp -eq 0; then
echo $NOWTIME'--------3------性别年龄户籍 var_person_identity 创建成功------'$tmp >> logs/logs_cdt_datasource_result
else
echo $NOWTIME'--------3------性别年龄户籍 var_person_identity 创建失败------'$tmp >> logs/logs_cdt_datasource_result
fi


echo '-----------------各类黑名单 blacklist--------------------'
impala-shell -r <<EOF
-------------小贷黑名单--------逾期7天以上用户
create table creditck.var_xd_delay_7day_mobile
as
select mobile, real_name, cert_no,max(black_date) as black_date,'xd' as blacklist_type,'信用记录' as black_type
from 
(
select t1.*, 
(case when trim(t4.is_settle) ='0' then  datediff('${TODAY_1d}',to_date(t2.return_date)) 
      when trim(t4.is_settle) ='1' then  datediff(to_date(t4.settle_time),to_date(t2.return_date)) end) as delay_day_num,t4.is_settle,
(case when trim(t4.is_settle)='0' then '${TODAY_1d}'
      when trim(t4.is_settle)='1' then to_date(t4.settle_time) end) as black_date
  from ods.xd_c_apply_user    t1,
       ods.xd_c_loan_apply    t2,
       ods.xd_c_loan_contract t3,
       ods.xd_c_receipt       t4
 where t1.id = t2.id
   and trim(t2.status)='T'
   and t2.id = t3.apply_id
   and t3.id = t4.contract_id
   and t2.loan_date >='2014-07-08'
   and t2.business_no in ('BID')
   and t2.return_date<'${TODAY_1d}'
   and (t2.return_date < to_date(t4.settle_time) or trim(t4.is_settle) ='0')) tmp
group by mobile, real_name, cert_no
having max(delay_day_num) > 7;
EOF
tmp=$?
NOWTIME=$(date +%Y-%m-%d/%H:%M:%S)
if test $tmp -eq 0; then
echo $NOWTIME'------3--------var_xd_delay_7day_mobile创建成功------'$tmp >> logs/logs_cdt_datasource_result
else
echo $NOWTIME'------3--------var_xd_delay_7day_mobile创建失败------'$tmp >> logs/logs_cdt_datasource_result
fi   


impala-shell -r <<EOF
--法院黑名单
---18位数字
create table creditck.var_fayuan_blacklist
as
select distinct mobile,identity_card,real_name,blacklist_type,black_date,black_type
from 
(select t1.mobile,t1.identity_card,t1.real_name,'fa' as blacklist_type,'${TODAY_1d}' as black_date,'身份属性'  as black_type
  from hdm.u_user_identity_info t1,
       (select cardnum,name
          from spss.c_personmore t2
         where length(cardnum) = 18
           and substr(cardnum, 11, 4) = '****'
           and length(cardnum) - length(regexp_replace(cardnum, '[*]+', '')) = 4) t2
 where trim(t1.real_name) = trim(t2.name)
   and substr(t1.identity_card, 1, 10) = substr(t2.cardnum, 1, 10)
   and substr(t1.identity_card, 15, 4) = substr(t2.cardnum, 15, 4)
union all ---15位数字
select t1.mobile,t1.identity_card,t1.real_name,'fa' as blacklist_type,'${TODAY_1d}' as black_date,'身份属性' as black_type
  from hdm.u_user_identity_info t1,
       (select cardnum,name
          from spss.c_personmore t2
         where length(cardnum) = 15
           and substr(cardnum, 9, 4) = '****'
           and length(cardnum) - length(regexp_replace(cardnum, '[*]+', '')) = 4
           and cast(age as int) >= 15) t2
 where trim(t1.real_name) = trim(t2.name)
   and substr(t1.identity_card, 7, 2) = '19'
   and substr(t1.identity_card, 1, 6) = substr(t2.cardnum, 1, 6)
   and substr(t1.identity_card, 9, 2) = substr(t2.cardnum, 7, 2)
   and substr(t1.identity_card, 15, 2) = substr(t2.cardnum, 13, 2)
   and substr(t1.identity_card, 18, 1) = substr(t2.cardnum, 15, 1)) tmp;
EOF
tmp=$?
NOWTIME=$(date +%Y-%m-%d/%H:%M:%S)
if test $tmp -eq 0; then
echo $NOWTIME'------3--------var_fayuan_blacklist创建成功------'$tmp >> logs/logs_cdt_datasource_result
else
echo $NOWTIME'------3--------var_fayuan_blacklist创建失败------'$tmp >> logs/logs_cdt_datasource_result
fi  


impala-shell -r <<EOF
--光大黑名单
create table creditck.var_gd_blacklist
as
select distinct mobile,cr_name,id_card_id,blacklist_type,black_date,black_type
from  
(
  select distinct mobile, cr_name, id_card_id, 'gd' as blacklist_type,'2015-05-07' as black_date,'信用记录' as black_type
  from creditck.c_person t
  where length(mobile) = 11
   and length(id_card_id) = 18
   and length(cr_name) >= 6
   and length(cr_name) <= 9
union all
  select distinct t2.mobile, cr_name, id_card_id, 'gd' as blacklist_type,'2015-05-07' as black_date,'信用记录' as black_type
  from creditck.c_person t1, hdm.u_user_identity_info t2
  where t1.id_card_id = t2.identity_card
   and length(id_card_id) = 18
   and length(cr_name) >= 6
   and length(cr_name) <= 9) tmp;
EOF
tmp=$?
NOWTIME=$(date +%Y-%m-%d/%H:%M:%S)
if test $tmp -eq 0; then
echo $NOWTIME'------3--------var_gd_blacklist创建成功------'$tmp >> logs/logs_cdt_datasource_result
else
echo $NOWTIME'------3--------var_gd_blacklist创建失败------'$tmp >> logs/logs_cdt_datasource_result
fi  
  
  
sudo -u hdfs hive <<EOF
add jar /home/hdfs/udfs_hive_0.13_cdh5.4.4_lkl_ver001.jar;
create temporary function add_months as 'com.lkl.hiveudfs.udf_add_months';
--p2p黑名单
create table creditck.var_p2p_blacklist
as
select  distinct mobile,real_name,identity_card,blacklist_type,black_date,black_type
from 
(select distinct t2.mobile,t2.real_name,t2.identity_card ,'p2p_dailm' as blacklist_type,
(case when loan_period is null or loan_period='' then '2015-08-01'
      when loan_period is not null and loan_period like'%年%' then add_months(t1.loan_date,nvl(cast(trim(substr(loan_period,0,instr(loan_period,'年')-1))  as int)*12,0))
      when loan_period is not null and loan_period like'%月%' then add_months(t1.loan_date,nvl(cast(trim(substr(loan_period,0,instr(loan_period,'个月')-1)) as int),0)) end) as black_date,'信用记录' as black_type
from spss.c_dailm_blacklist t1,
     hdm.u_user_identity_info t2
where t1.identity_card_number=t2.identity_card and t1.vname=t2.real_name 
union all
select distinct t2.mobile,t2.real_name,t2.identity_card ,'p2p_ppai' as blacklist_type,
(case when loan_period is null or loan_period=''  then '2015-08-01'
      when loan_period is not null and loan_period like'%年%' then add_months(t1.loan_date,nvl(cast(trim(substr(loan_period,0,instr(loan_period,'年')-1)) as int)*12,0))
      when loan_period is not null and loan_period like'%月%' then add_months(t1.loan_date,nvl(cast(trim(substr(loan_period,0,instr(loan_period,'个月')-1)) as int),0)) end) as black_date,'信用记录' as black_type
from spss.c_ppai_blacklist t1,
     hdm.u_user_identity_info t2
where t1.identity_card_number=concat(substr(t2.identity_card ,0,14),'****' ) and t1.vname=t2.real_name
      and t1.telephone=concat(substr(t2.mobile,0,8),'***')
union all
select distinct t2.mobile,t1.vname as real_name ,t1.id_number as identity_card,'p2p_wd' as blacklist_type,to_date(update_date)  as black_date,'信用记录' as black_type
from spss.c_wd_blacklist t1,
hdm.u_user_identity_info t2
where t1.id_number=t2.identity_card and t1.vname=t2.real_name
) tmp;
EOF
tmp=$?
NOWTIME=$(date +%Y-%m-%d/%H:%M:%S)
if test $tmp -eq 0; then
echo $NOWTIME'------3--------var_p2p_blacklist创建成功------'$tmp >> logs/logs_cdt_datasource_result
else
echo $NOWTIME'------3--------var_p2p_blacklist创建失败------'$tmp >> logs/logs_cdt_datasource_result
fi  
 
 
impala-shell -r <<EOF
--用户中心黑名单
create table creditck.var_user_blacklist
as
select mobile_num,real_name,id_card_id,'u_user' as blacklist_type,(case when update_time is null then create_time else update_time end) as black_date,'交易行为' as black_type
from hds.u_user t
where enabled =0 and length(mobile_num)=11;

EOF
tmp=$?
NOWTIME=$(date +%Y-%m-%d/%H:%M:%S)
if test $tmp -eq 0; then
echo $NOWTIME'------3--------var_user_blacklist创建成功------'$tmp >> logs/logs_cdt_datasource_result
else
echo $NOWTIME'------3--------var_user_blacklist创建失败------'$tmp >> logs/logs_cdt_datasource_result
fi  


impala-shell -r <<EOF
--黑名单整合
create table creditck.var_blacklist_new
as
select distinct mobile,real_name,cert_no,black_date,blacklist_type,black_type
from
(
select distinct mobile, real_name, cert_no,black_date,blacklist_type,black_type
  from creditck.var_xd_delay_7day_mobile t1
union all
select distinct mobile, real_name, identity_card as cert_no,black_date,blacklist_type,black_type
  from creditck.var_fayuan_blacklist t2
union all
select distinct mobile, cr_name as real_name, id_card_id  as cert_no,black_date,blacklist_type,black_type
from creditck.var_gd_blacklist t3
union all
select distinct mobile,real_name,identity_card as cert_no,black_date,blacklist_type,black_type
from creditck.var_p2p_blacklist
union all
select distinct mobile_num as mobile,real_name,id_card_id as cert_no,black_date,blacklist_type,black_type
from creditck.var_user_blacklist) tmp;
EOF
tmp=$?
NOWTIME=$(date +%Y-%m-%d/%H:%M:%S)
if test $tmp -eq 0; then
echo $NOWTIME'------3--------var_blacklist_new创建成功------'$tmp >> logs/logs_cdt_datasource_result
else
echo $NOWTIME'------3--------var_blacklist_new创建失败------'$tmp >> logs/logs_cdt_datasource_result
fi  


sudo -u hdfs hive <<EOF
--最后黑名单
drop table creditck.var_blacklist_end;
create table creditck.var_blacklist_end
as
select mobile,
concat_ws(',',collect_set(black_type)) as black_type,
max(is_black) as is_black
from
   (select mobile, 
           black_type,
           case when black_type in('信用记录','履约能力','身份属性') and datediff(from_unixtime(unix_timestamp(),'yyyy-MM-dd'),to_date(max(black_date)))<=1825 then 1
                when black_type in('交易行为') and datediff(from_unixtime(unix_timestamp(),'yyyy-MM-dd'),to_date(max(black_date)))<=730 then 1 else 0 end as is_black
    from creditck.var_blacklist_new
    group by mobile,black_type) t 
where is_black=1 
group by mobile;
EOF
tmp=$?
NOWTIME=$(date +%Y-%m-%d/%H:%M:%S)
if test $tmp -eq 0; then
echo $NOWTIME'------3--------var_blacklist_end-'${TODAY_0D}'创建成功------'$tmp >> logs/logs_cdt_datasource_result
echo 'cdt_datasource_result2_3------ok------'${TODAY_0D}
else
echo $NOWTIME'------3--------var_blacklist_end-'${TODAY_0D}'创建失败------'$tmp >> logs/logs_cdt_datasource_result
fi  
