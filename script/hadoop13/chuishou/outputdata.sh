#!/bin/sh
cd /home/hdfs/cuishou
echo '--------新宽表-------'
sudo -u hdfs hive <<EOF
drop table zhengxin.mid_person_info_common;
create table zhengxin.mid_person_info_common 
as
select  
mobile ,
max(case  when num =0  then   mobile_num  end ) as mobile_num0,
max(case  when num =1  then   mobile_num  end ) as mobile_num1,
max(case  when num =2  then   mobile_num  end ) as mobile_num2,
max(case  when num =3  then   mobile_num  end ) as mobile_num3
from  zhengxin.self_info   group by mobile;

drop table zhengxin.person_wide;
create table zhengxin.person_wide ----汇总表
as
select a.id,a.batchnum,a.name,a.idcard,a.mobilephone,a.account, 
    b.mobile_num0         ,
    b.mobile_num1         ,              --关联出的手机号   
    b.mobile_num2         ,              --关联出的手机号 
    b.mobile_num3         ,              --关联出的手机号 
    c.hk_date1 , --还款日期
    c.hk_addr1 , --还款地点
    c.hk_amt1 , --还款金额
    c.hk_real_name1 , --还款人姓名
    c.hk_mobile_num1 , --还款人手机号
    c.hk_date2 , --还款日期
    c.hk_addr2 , --还款地点
    c.hk_amt2 , --还款金额
    c.hk_real_name2 , --还款人姓名
    c.hk_mobile_num2 , --还款人手机号
    c.hk_date3 , --还款日期
    c.hk_addr3 , --还款地点
    c.hk_amt3 , --还款金额
    c.hk_real_name3 , --还款人姓名
    c.hk_mobile_num3 , --还款人手机号
    c.hk_date12 , --还款日期
    c.hk_addr12 , --还款地点
    c.hk_amt12 , --还款金额
    c.hk_real_name12 , --还款人姓名
    c.hk_mobile_num12 , --还款人手机号
    c.hk_date22 , --还款日期
    c.hk_addr22 , --还款地点
    c.hk_amt22 , --还款金额
    c.hk_real_name22 , --还款人姓名
    c.hk_mobile_num22 , --还款人手机号
    c.hk_date32 , --还款日期
    c.hk_addr32 , --还款地点
    c.hk_amt32 , --还款金额
    c.hk_real_name32 , --还款人姓名
    c.hk_mobile_num32 , --还款人手机号
    d.zz_date1 , --转入日期
    d.zz_addr1 , --转入地点
    d.zz_amt1  , --转入金额
    d.zz_real_name1 , --转账人姓名
    d.zz_mobile_num1 , --转账人手机号
    d.zz_date2 , --转入日期
    d.zz_addr2 , --转入地点
    d.zz_amt2 , --转入金额
    d.zz_real_name2 , --转账人姓名
    d.zz_mobile_num2 , --转账人手机号
    d.zz_date3 , --转入日期
    d.zz_addr3 , --转入地点
    d.zz_amt3 , --转入金额
    d.zz_real_name3 , --转账人姓名
    d.zz_mobile_num3 , --转账人手机号
    d.zz_date12 , --转入日期
    d.zz_addr12 , --转入地点
    d.zz_amt12  , --转入金额
    d.zz_real_name12 , --转账人姓名
    d.zz_mobile_num12 , --转账人手机号
    d.zz_date22 , --转入日期
    d.zz_addr22 , --转入地点
    d.zz_amt22 , --转入金额
    d.zz_real_name22 , --转账人姓名
    d.zz_mobile_num22 , --转账人手机号
    d.zz_date32 , --转入日期
    d.zz_addr32 , --转入地点
    d.zz_amt32 , --转入金额
    d.zz_real_name32 , --转账人姓名
    d.zz_mobile_num32 , --转账人手机号
    e.cz_date1 , --充值日期
    e.cz_addr1 , --充值地点
    e.cz_amt1 , --充值金额
    e.cz_real_name1 , --被充值人姓名
    e.cz_mobile_num1 , --被充值人手机号
    e.cz_date2 , --充值日期
    e.cz_addr2 , --充值地点
    e.cz_amt2 , --充值金额
    e.cz_real_name2 , --被充值人姓名
    e.cz_mobile_num2 , --被充值人手机号
    e.cz_date3 , --充值日期
    e.cz_addr3 , --充值地点
    e.cz_amt3 , --充值金额
    e.cz_real_name3 , --被充值人姓名
    e.cz_mobile_num3 , --被充值人手机号
    f.gj_date1 , --公缴日期
    f.gj_addr_czs1 , --操作地点
    f.gj_date2 , --公缴日期
    f.gj_addr_czs2 , --操作地点
    f.gj_date3 , --公缴日期
    f.gj_addr_czs3  --操作地点
from  zhengxin.cuishou_input  a   
left join zhengxin.mid_person_info_common  b  on   a.mobilephone =b.mobile
left join zhengxin.mid_trans_info_hk   c  on a.mobilephone =c.mobile
left join zhengxin.mid_trans_info_zz   d  on a.mobilephone =d.mobile
left join zhengxin.mid_trans_info_cz   e  on a.mobilephone =e.mobile
left join zhengxin.mid_trans_info_gj   f  on a.mobilephone =f.mobile
;
EOF
tmp=$?
NOWTIME=$(date +%Y-%m-%d/%H:%M:%S)
if test $tmp -eq 0; then
echo $NOWTIME'--------------宽表生成成功'$tmp >logs/logs_wide_failed
else
echo $NOWTIME'--------------宽表生成失败'$tmp > logs/logs_wide_failed
exit -2;
fi
