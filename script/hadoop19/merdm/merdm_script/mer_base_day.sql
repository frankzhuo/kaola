#!/bin/sh
DATE_0m=$(date +%Y-%m-%d/%H:%M:%S)
DATE_0M=$(date +%Y-%m-%d/%H:%M:%S )


while true
do
echo '--------------------- T1_商户交易MCC  MERDM.MER_MCC_TRANS_MID  -------------------'
sudo -u hdfs hive <<EOF

set hive.auto.convert.join=false; 
set mapreduce.reduce.shuffle.input.buffer.percent=0.1;
set mapreduce.reduce.shuffle.parallelcopies=9;

add jar /home/hdfs/lakala_udf.jar;
create temporary function IDCard as 'com.lkl.hive.udf.IDCard';

--T1 商户交易MCC （拉卡拉）
DROP TABLE  MERDM.MER_MCC_TRANS_MID ;

CREATE TABLE  MERDM.MER_MCC_TRANS_MID AS 
SELECT 
 '110001' AS ORGID ,
 MERCID AS MER_ID ,
 TXNTIM AS TRANS_TIME_LST,
 MERTYP AS MCC_TRANS,
 TXNRSV1 AS MERID_UP1,
 TXNRSV2 AS MERID_UP2 ,
 YMD 
FROM 
(SELECT  MERCID ,  TXNTIM , MERTYP,  TXNRSV1, TXNRSV2, YMD, ROW_NUMBER() OVER(PARTITION BY  MERCID ORDER BY YMD DESC  ) RK  
 FROM HDS.POS_ATMTXNJNL  ) T
WHERE RK=1 ;
EOF
tmp=$?
NOWTIME=$(date +%Y-%m-%d/%H:%M:%S)
if test $tmp -eq 0; then
   echo $NOWTIME '--------------T1_商户交易MCC整合成功 MERDM.MER_MCC_TRANS_MID  -------------'$tmp
   break
else
   echo $NOWTIME '--------------T1_商户交易MCC整合失败 MERDM.MER_MCC_TRANS_MID  -------------'$tmp
fi
done


while true
do
echo '--------------------- M1_商户基本信息整合 MERDM.DM_MER_MERCHANT_MID -------------------'
sudo -u hdfs hive <<EOF

set hive.auto.convert.join=false; 
set mapreduce.reduce.shuffle.input.buffer.percent=0.1;
set mapreduce.reduce.shuffle.parallelcopies=9;

add jar /home/hdfs/lakala_udf.jar;
create temporary function IDCard as 'com.lkl.hive.udf.IDCard';

--M1.1 整合商户基本信息 （拉卡拉）

DROP TABLE MERDM.DM_MER_MERCHANT_MID  ; 

CREATE  TABLE  MERDM.DM_MER_MERCHANT_MID  AS 
SELECT 
 '110001' AS ORGID, 
 A.SHOP_NO        AS MER_ID,             
 CEIL(A.MERCHANT_ID)    AS  MERCHANT_ID ,     
 CASE WHEN A.MER_LICENCE_NO RLIKE '^[0-9]{15}$' THEN A.MER_LICENCE_NO                                           
      WHEN A.MER_LICENCE_NO RLIKE '^[0-9]{15}[\\\(\\\（-]+' THEN SPLIT(A.MER_LICENCE_NO,'[\\\(\\\（-]+')[0]       
      WHEN LENGTH(A.MER_LICENCE_NO)<15 THEN NULL                                                                
      ELSE A.MER_LICENCE_NO END  AS MER_LICENCE_NO ,                                                              
 
 CASE WHEN  IDCard(B.CR_LICENCE_NO) = 1 OR  IDCard(A.CR_LICENCE_NO) = 1 THEN NVL(B.CR_LICENCE_NO , A.CR_LICENCE_NO) ELSE NULL END  AS IDCARD_NO ,   -- 身份证验证 
 
 NVL(B.MER_BUSINESS_NAME, A.MER_BUSINESS_NAME)   AS  MER_BUSINESS_NAME ,  
 NVL(B.MER_REGISTER_NAME, A.MER_REGISTER_NAME)   AS  MER_REGISTER_NAME ,  
  
 CEIL(A.CORPORATION_PROPERTY)  AS CORPORATION_PROPERTY,   
 D1.PARAM_VALUE AS    CORPORATION_PROPERTY_NAME,   
 
 CASE  WHEN   LENGTH(A.SHOP_NO) = 15 THEN  SUBSTR(A.SHOP_NO, 8,4)  ELSE NULL END AS  MCC_MERID ,
 E.MCC_TRANS AS MCC_TRANS ,
 A.BUSINESS_CONTENT ,  
 
  NVL(B.MER_REG_ADDR, A.MER_REG_ADDR ) AS MER_REG_ADDR ,     
  A.MER_REG_ADDR_DIST ,       
  A.ZIPCODE ,
  CEIL(A.CUPS_AREA) AS CUPS_AREA  ,   
  B.SHOP_ACT_ADDR  AS SHOP_ACT_ADDR_MPOS , 
  CASE WHEN  LENGTH(A.SHOP_NO) = 15 THEN  SUBSTR(A.SHOP_NO, 4,4) ELSE NULL   END AS  AREA_MERID ,
 
  SUBSTR(A.CREATE_TM,1,10) AS MER_CREATE_DATE ,
  SUBSTR(A.LAST_MODIFY_TM,1,10) AS MER_LAST_MODIFY_DATE ,
 
   CASE WHEN LENGTH(NVL(B.CONTACT_PERSON, A.FINANCE_CONTACT ))>=2 THEN (NVL(B.CONTACT_PERSON, A.FINANCE_CONTACT )) ELSE NULL END  AS MER_CONTACT_PERSON  ,                    
   CASE WHEN LENGTH(NVL(B.CONTACT_TELE, A.FINANCE_CONTACT_TELE )) BETWEEN  6 AND  15 THEN NVL(B.CONTACT_TELE, A.FINANCE_CONTACT_TELE ) ELSE NULL END  AS  MER_CONTACT_TELE  ,
   A.FINANCE_MAIL ,
   A.MERCHANT_EMAIL ,       
   A.WECHAT_SUBMER_CHANTNO  ,  
   A.ALIPAY_SUBMER_CHANTNO ,   
   CASE WHEN LENGTH(C.MOBILE_NO) = 11 THEN C.MOBILE_NO ELSE NULL END  AS MOBILE_WECHAT ,
   C.OPEN_ID AS OPEN_ID ,
   CASE WHEN C.STATUS = 'VALID' THEN 1 ELSE 0 END AS  IS_WECHAT ,
   
   CASE WHEN A.STATUS = 'VALID' OR A.STATUS IS NULL THEN 1 ELSE 0 END  AS  MER_STATUS ,
  A.MER_LEVEL ,
  CASE WHEN LENGTH(TRIM(A.CORPORATE_REPRESENTATIVE)) >1 THEN A.CORPORATE_REPRESENTATIVE ELSE NULL END AS LEGAL_PERSON  ,
  CASE WHEN  B.AUDIT_RESULT= 'PASSED' THEN 1 WHEN B.AUDIT_RESULT= 'REJECTED' THEN 0 END   AS AUDIT_RESULT_MPOS ,
   
  A.DISCOUNT_STANDARD ,
  D2.PARAM_VALUE AS DISCOUNT_STANDARD_NAME ,
  '${DATE_0M}' AS LOAD_DATE 

FROM  
 HDS.BMCP_T01_MERCHANT_INFO	 A  
 LEFT JOIN 
 (
   SELECT * FROM (
   SELECT  A1.* ,   ROW_NUMBER() OVER(PARTITION  BY  SHOP_NO   ORDER BY  AUDIT_TM DESC  )  RK 
   FROM   HDS.BMCP_T01_BUSINESS_SYNC_INFO A1   )  T
   WHERE RK = 1  
   ) B       -- MPOS商户信息表（取最新一条）
 ON A.SHOP_NO = B.SHOP_NO
 LEFT  JOIN 
  (
   SELECT * FROM (
   SELECT  A1.* ,   ROW_NUMBER() OVER(PARTITION  BY  SHOP_NO   ORDER BY  CREATE_TM DESC  )  RK 
   FROM    HDS.BMCP_T01_WEIXIN_INFO   A1   )  T
   WHERE RK = 1  
   ) C    -- 微信信息表 (取最新一条)
  ON A.SHOP_NO = C.SHOP_NO 
   LEFT JOIN 
  ( SELECT * FROM   MERDM.MER_MCC_TRANS_MID )  E
  ON  A.SHOP_NO = E.MER_ID 
  LEFT JOIN 
  ( SELECT * FROM HDS.BMCP_T99_SYS_PARAMETERS WHERE  STATUS = 'VALID' AND  PARAM_KIND = 'CORPORATION_PROPERTY' ) D1
 ON  A.CORPORATION_PROPERTY = D1.PARAM_ID
 LEFT JOIN 
  ( SELECT * FROM HDS.BMCP_T99_SYS_PARAMETERS WHERE  STATUS = 'VALID' AND  PARAM_KIND = 'DISCOUNT_STANDARD' ) D2
 ON  A.DISCOUNT_STANDARD = D2.PARAM_ID
 ;
 
 
--M1.2 整合商户基本信息 （外部） 
 
 INSERT INTO MERDM.DM_MER_MERCHANT_MID
 SELECT 
 A.ORGID          AS  ORGID, 
 A.merchantid    AS MER_ID,             
 A.MERCHANTSEQ      AS  MERCHANT_ID ,     
  CASE WHEN A.businesslicensenum RLIKE '^[0-9]{15}$' THEN A.businesslicensenum                                                
       WHEN LENGTH(A.businesslicensenum)<15 THEN NULL                                                                
      ELSE A.businesslicensenum END  AS MER_LICENCE_NO  ,                                                        
 
 CASE WHEN IDCard(A.legalpersonidcard)=1 THEN A.legalpersonidcard ELSE NULL END  AS IDCARD_NO ,   -- 身份证验证 
 
 A.merchantname      AS  MER_BUSINESS_NAME ,  
 A.registrationname  AS  MER_REGISTER_NAME ,  
  
 cast(A.merchanttype as int)  AS CORPORATION_PROPERTY,   
 D1.PARAM_VALUE   AS CORPORATION_PROPERTY_NAME,   
 
 CASE WHEN  LENGTH(A.merchantid) = 15 THEN  SUBSTR(A.merchantid, 8,4)  END AS  MCC_MERID ,
 A.merchantmcccode AS MCC_TRANS ,
 A.merentitymanagement as BUSINESS_CONTENT ,  
 
  NVL(A.registrationaddr, A.mercontactaddr ) AS MER_REG_ADDR ,     
  null AS  MER_REG_ADDR_DIST ,       
  mercontactzipcode   as  ZIPCODE ,
  null  AS CUPS_AREA  ,   
  null  AS SHOP_ACT_ADDR_MPOS , 
  CASE WHEN  LENGTH(A.merchantid) = 15 THEN  SUBSTR(A.merchantid, 4,4) ELSE NULL   END AS  AREA_MERID ,
 
  SUBSTR(A.merincludedtime ,1,10) AS MER_CREATE_DATE ,
  SUBSTR(A.createdate ,1,10) AS MER_LAST_MODIFY_DATE ,
 
   CASE WHEN LENGTH(A.mercontactname)>=2 THEN A.mercontactname  ELSE NULL END  AS MER_CONTACT_PERSON  ,                    
   CASE WHEN LENGTH(NVL(A.legalpersontel , nvl(A.mercontactmobile, A.mercontacttel)))  BETWEEN  6 AND  15 THEN NVL(A.legalpersontel, nvl(A.mercontactmobile, A.mercontacttel)) ELSE NULL END  AS  MER_CONTACT_TELE  ,
   NULL AS FINANCE_MAIL ,
   A.mercontactemail   AS MERCHANT_EMAIL ,       
   NULL  AS  WECHAT_SUBMER_CHANTNO  ,  
   NULL  AS  ALIPAY_SUBMER_CHANTNO ,   
   NULL  AS  MOBILE_WECHAT ,
   NULL  AS  OPEN_ID ,
   NULL  AS  IS_WECHAT ,
   
   CASE WHEN A.merchantstate = 'VALID' OR A.merchantstate IS NULL THEN 1 ELSE 0 END  AS  MER_STATUS ,
   NULL AS MER_LEVEL ,
  CASE WHEN LENGTH(TRIM(A.legalpersonname)) >1 THEN A.legalpersonname ELSE NULL END AS LEGAL_PERSON  ,
  NULL  AS AUDIT_RESULT_MPOS ,
   
  NULL AS DISCOUNT_STANDARD ,
  NULL AS DISCOUNT_STANDARD_NAME,
  '${DATE_0M}' AS LOAD_DATE 

FROM  
  merdm.WBPOS_MSMERCHANT  A  
  LEFT JOIN 
  ( SELECT * FROM HDS.BMCP_T99_SYS_PARAMETERS WHERE  STATUS = 'VALID' AND  PARAM_KIND = 'CORPORATION_PROPERTY' ) D1
 ON  CAST(A.merchanttype as int) = D1.PARAM_ID
 ;
EOF
tmp=$?
NOWTIME=$(date +%Y-%m-%d/%H:%M:%S)
if test $tmp -eq 0; then
   echo $NOWTIME '--------------M1_商户基本信息整合成功 MERDM.DM_MER_MERCHANT_MID  -------------'$tmp
   break
else
   echo $NOWTIME '--------------M1_商户基本信息整合失败 MERDM.DM_MER_MERCHANT_MID  -------------'$tmp
fi
done




while true
do
echo '--------------------- M2_商户网点信息整合 MERDM.DM_MER_SHOP_MID -------------------'
sudo -u hdfs hive <<EOF

set hive.auto.convert.join=false; 
set mapreduce.reduce.shuffle.input.buffer.percent=0.1;
set mapreduce.reduce.shuffle.parallelcopies=9;

add jar /home/hdfs/lakala_udf.jar;
create temporary function IDCard as 'com.lkl.hive.udf.IDCard';

--M2.1 整合商户网点信息 （拉卡拉）
 DROP TABLE  MERDM.DM_MER_SHOP_MID ;
 
 CREATE  TABLE  MERDM.DM_MER_SHOP_MID  AS 
 SELECT 
   '110001' AS ORGID,
   B.SHOP_NO AS MER_ID ,
  max(case when rk = 1 then cast(CEIL(A.MERCHANT_ID) AS STRING) else null end ) AS MERCHANT_ID ,
   
   COUNT(*)  AS SHOP_CNT ,
   SUM(CASE WHEN STATUS = 'VALID' OR STATUS IS NULL  THEN 1 ELSE 0  END  ) AS SHOP_CNT_VALID ,
   MAX(CASE WHEN STATUS = 'VALID' OR STATUS IS NULL  THEN 1 ELSE 0  END )  AS SHOP_STATUS ,
   
   MAX(CASE WHEN RK = 1 THEN  CEIL(SHOP_ACT_ADDR_DIST)  ELSE NULL END ) AS SHOP_ACT_ADDR_DIST_LST,   
   MAX(CASE WHEN RK = 1 THEN  SHOP_ACT_ADDR   ELSE NULL END )  AS SHOP_ACT_ADDR_LST,        
   MAX(CASE WHEN RK = 1 THEN  INSTALL_CONTACT_TELE  ELSE NULL END ) AS  INSTALL_CONTACT_TELE_LST ,
   MAX(CASE WHEN RK = 1 THEN  SHOP_CONTACT_PERSON   ELSE NULL END ) AS  SHOP_CONTACT_PERSON_LST ,    
   MAX(CASE WHEN RK = 1 THEN  CEIL(SHOP_AREA_CODE)  ELSE NULL END ) AS SHOP_AREA_CODE_LST  ,      
   MAX(CASE WHEN RK = 1 THEN  SHOP_NAME    ELSE NULL END ) AS SHOP_NAME_LST ,              
   MAX(CASE WHEN RK = 1 AND   LENGTH(SHOP_CONTACT) BETWEEN 2 AND 10 THEN SHOP_CONTACT ELSE NULL END ) AS SHOP_CONTACT_LST ,          
   MAX(CASE WHEN RK = 1 THEN  SHOP_EMAIL ELSE NULL END ) AS  SHOP_EMAIL_LST ,            
   MAX(CASE WHEN RK = 1 THEN  SHOP_CONTACT_TELE  ELSE NULL END ) AS SHOP_CONTACT_TELE_LST ,      
   MAX(CASE WHEN RK = 1 AND   LENGTH(SHOP_MOBILE_PHONE) BETWEEN  6 AND 15  THEN SHOP_MOBILE_PHONE  ELSE NULL END ) AS SHOP_MOBILE_PHONE_LST ,    
   MAX(CASE WHEN RK = 1 THEN  CEIL(CUPS_AREA) ELSE NULL END ) AS CUPS_AREA_LST ,
   
   SUBSTR(MIN( CREATE_TM) ,1,10)  AS SHOP_CREATE_DATE_MIN ,
   SUBSTR(MAX( CREATE_TM) ,1,10 ) AS SHOP_CREATE_DATE_MAX ,
   SUBSTR(MAX( LAST_MODIFY_TM),1,10) AS SHOP_LAST_MODIFY_DATE ,

   '${DATE_0M}' AS LOAD_DATE    
   
  FROM 
   (SELECT  A1.* ,   ROW_NUMBER() OVER(PARTITION  BY  MERCHANT_ID   ORDER BY  STATUS DESC ,  CREATE_TM  DESC  )   RK 
    FROM   HDS.BMCP_T01_SHOP_INFO A1 ) A
    JOIN 
    (SELECT SHOP_NO, MERCHANT_ID FROM HDS.BMCP_T01_MERCHANT_INFO  ) B
  ON A.MERCHANT_ID = B.MERCHANT_ID  
   GROUP BY   B.SHOP_NO  ;
 
--M2.2 整合商户网点信息 （外部） 
   
  INSERT INTO  MERDM.DM_MER_SHOP_MID
   SELECT 
   A.ORGID AS ORGID,
   A.MERCHANTID AS MER_ID ,
   null  AS  MERCHANT_ID ,
   
   COUNT(*)  AS SHOP_CNT ,
   SUM(CASE WHEN branchstate = 'VALID' OR branchstate IS NULL  THEN 1 ELSE 0  END  ) AS SHOP_CNT_VALID ,
   MAX(CASE WHEN branchstate = 'VALID' OR branchstate IS NULL  THEN 1 ELSE 0  END )  AS SHOP_STATUS ,
   
   NULL AS SHOP_ACT_ADDR_DIST_LST,   
   MAX(CASE WHEN RK = 1 THEN  branchaddr   ELSE NULL END )  AS SHOP_ACT_ADDR_LST,        
   null AS  INSTALL_CONTACT_TELE_LST ,
   NULL AS  SHOP_CONTACT_PERSON_LST ,    
   MAX(CASE WHEN RK = 1 THEN  NVL(branchcity,branchprovince)  ELSE NULL END ) AS  SHOP_AREA_CODE_LST ,      
   MAX(CASE WHEN RK = 1 THEN  branchname    ELSE NULL END ) AS SHOP_NAME_LST ,              
   MAX(CASE WHEN RK = 1 AND   LENGTH(brcontactname) BETWEEN 2 AND 10 THEN brcontactname ELSE NULL END ) AS SHOP_CONTACT_LST ,          
   MAX(CASE WHEN RK = 1 THEN  brcontactemail  ELSE NULL END ) AS  SHOP_EMAIL_LST ,            
   MAX(CASE WHEN RK = 1 THEN  brcontacttel  ELSE NULL END ) AS SHOP_CONTACT_TELE_LST ,      
   MAX(CASE WHEN RK = 1 AND   LENGTH(brcontactmobile ) BETWEEN  6 AND 15  THEN brcontactmobile  ELSE NULL END ) AS SHOP_MOBILE_PHONE_LST ,    
   NULL AS CUPS_AREA_LST ,
   
   SUBSTR(MIN( brmanagestarttime) ,1,10)  AS SHOP_CREATE_DATE_MIN ,
   SUBSTR(MAX( brmanagestarttime) ,1,10)  AS SHOP_CREATE_DATE_MAX ,
   SUBSTR(MAX( createdate),1,10) AS SHOP_LAST_MODIFY_DATE ,

   '${DATE_0M}' AS LOAD_DATE    
   
  FROM 
   (SELECT  A1.* , ROW_NUMBER() OVER(PARTITION  BY  orgid, MERCHANTID   ORDER BY  NVL(branchstate,'VALID') DESC ,  BRANCHID  DESC  )   RK 
    FROM   merdm.WBPOS_MSMERCHANTBRANCH A1 ) A
   GROUP BY   A.orgid, A.MERCHANTID   ;

EOF
tmp=$?
NOWTIME=$(date +%Y-%m-%d/%H:%M:%S)
if test $tmp -eq 0; then
   echo $NOWTIME '--------------M2_商户网点信息整合成功 MERDM.DM_MER_SHOP_MID -------------'$tmp
   break
else
   echo $NOWTIME '--------------M2_商户网点信息整合失败 MERDM.DM_MER_SHOP_MID -------------'$tmp
fi
done




while true
do
echo '--------------------- M3_商户终端信息整合 -------------------'
sudo -u hdfs hive <<EOF

set hive.auto.convert.join=false; 
set mapreduce.reduce.shuffle.input.buffer.percent=0.1;
set mapreduce.reduce.shuffle.parallelcopies=9;

add jar /home/hdfs/lakala_udf.jar;
create temporary function IDCard as 'com.lkl.hive.udf.IDCard';

--M3.1 商户终端信息整合 （拉卡拉）
  DROP TABLE MERDM.DM_MER_TERM_MID  ; 
  
  CREATE  TABLE  MERDM.DM_MER_TERM_MID  AS 
  SELECT
     '110001' AS ORGID,   
     SHOP_NO AS MER_ID , 
 
     COUNT(*)  AS  TERM_CNT ,
     SUM (CASE  WHEN  A.STATUS = 'VALID'  OR A.STATUS IS NULL THEN   1 ELSE  0 END)  AS  TERM_CNT_VALID ,
     MAX (CASE  WHEN  A.STATUS = 'VALID'  OR A.STATUS IS NULL THEN   1 ELSE  0 END) AS   TERM_STATUS ,

     SUBSTR(MIN(A.CREATE_TM),1,10) AS  TERM_CREATE_DATE_MIN,
     SUBSTR(MAX(A.CREATE_TM),1,10) AS  TERM_CREATE_DATE_MAX,
     SUBSTR(MAX( A.LAST_MODIFY_TM),1,10) AS  TERM_LAST_MODIFY_DATE,

     COUNT(DISTINCT DEVICE_TYPE) AS DEVICE_TYPE_CNT,
     COUNT(DISTINCT CASE WHEN A.STATUS= 'VALID' THEN DEVICE_TYPE ELSE NULL END ) AS DEVICE_TYPE_CNT_VALID,
     CONCAT_WS('*', SORT_ARRAY(COLLECT_SET(DISTINCT  CAST(CEIL(A.DEVICE_TYPE) AS STRING ) ))) AS DEVICE_TYPE_CON,
     CONCAT_WS('*', SORT_ARRAY(COLLECT_SET(DISTINCT  NVL(D1.DEVICE_TYPE_CODE_LV1, D1.DEVICE_TYPE_CODE_LV2  ))))  AS DEVICE_TYPE_NAME_CON,
     CONCAT_WS('*', SORT_ARRAY(COLLECT_SET(DISTINCT  D1.DEVICE_KIND_NAME))) AS DEVICE_KIND_CON  ,
     CONCAT_WS('*', SORT_ARRAY(COLLECT_SET(DISTINCT  CASE WHEN A.STATUS= 'VALID' THEN CAST(CEIL(A.DEVICE_TYPE) AS STRING ) ELSE NULL END   ))) AS  DEVICE_TYPE_VALID_CON,
     CONCAT_WS('*', SORT_ARRAY(COLLECT_SET(DISTINCT  CASE WHEN A.STATUS= 'VALID' THEN NVL(D1.DEVICE_TYPE_CODE_LV1, D1.DEVICE_TYPE_CODE_LV2  ) ELSE NULL END  ))) AS DEVICE_TYPE_NAME_VALID_CON, 
     CONCAT_WS('*', SORT_ARRAY(COLLECT_SET(DISTINCT  CASE WHEN A.STATUS='VALID' THEN D1.DEVICE_KIND_NAME  ELSE NULL END ))) AS DEVICE_KIND_VALID_CON , 
     
    '${DATE_0M}' AS LOAD_DATE
   FROM  
    HDS.BMCP_T01_TERMINAL_INFO A
    JOIN 
    (SELECT SHOP_ID, MERCHANT_ID   FROM  HDS.BMCP_T01_SHOP_INFO ) B
    ON A.SHOP_ID = B.SHOP_ID 
    JOIN 
    (SELECT  MERCHANT_ID,  SHOP_NO  FROM  HDS.BMCP_T01_MERCHANT_INFO ) C 
    ON B.MERCHANT_ID = C.MERCHANT_ID  
    LEFT JOIN 
    (SELECT * FROM   merdm.BMCP_T01_DEVICE_TYPES_P ) D1
    ON  A.DEVICE_TYPE = D1.DEVICE_TYPE_ID  
    GROUP BY  C.SHOP_NO  ;

--M3.2 商户终端信息整合 （外部） 
   
   INSERT INTO MERDM.DM_MER_TERM_MID
   SELECT
     A.ORGID AS ORGID,   
     A.merchantid  AS MER_ID , 
 
     COUNT(*)  AS  TERM_CNT ,
     SUM (CASE  WHEN  A. terminalstate = 'VALID'  OR A.terminalstate IS NULL THEN   1 ELSE  0 END)  AS  TERM_CNT_VALID ,
     MAX (CASE  WHEN  A.terminalstate = 'VALID'  OR A.terminalstate IS NULL THEN   1 ELSE  0 END) AS   TERM_STATUS ,

     SUBSTR(MIN(A.teropentime ),1,10)  AS  TERM_CREATE_DATE_MIN,
     SUBSTR(MAX(A.teropentime),1,10)   AS  TERM_CREATE_DATE_MAX,
     SUBSTR(MAX( A.createdate),1,10)   AS  TERM_LAST_MODIFY_DATE,

    null AS DEVICE_TYPE_CNT,
    null AS DEVICE_TYPE_CNT_VALID,
    null AS DEVICE_TYPE_CON,
    null AS DEVICE_TYPE_NAME_CON,
    null AS DEVICE_KIND_CON  ,
    null AS  DEVICE_TYPE_VALID_CON,
    null AS DEVICE_TYPE_NAME_VALID_CON, 
    null AS DEVICE_KIND_VALID_CON , 

    '${DATE_0M}' AS LOAD_DATE
   FROM  
     merdm.WBPOS_MSTERMINALINFO A
    GROUP BY A.ORGID, A.merchantid ; 
    
EOF
tmp=$?
NOWTIME=$(date +%Y-%m-%d/%H:%M:%S)
if test $tmp -eq 0; then
   echo $NOWTIME '--------------M3_商户终端信息整合成功 MERDM.DM_MER_TERM_MID -------------'$tmp
   break
else
   echo $NOWTIME '--------------M3_商户终端信息整合失败  MERDM.DM_MER_TERM_MID -------------'$tmp
fi
done



while true
do
echo '--------------------- M4_商户卡应用信息整合 MERDM.DM_MER_CARDAPP_MID_WB -------------------'
sudo -u hdfs hive <<EOF

set hive.auto.convert.join=false; 
set mapreduce.reduce.shuffle.input.buffer.percent=0.1;
set mapreduce.reduce.shuffle.parallelcopies=9;

add jar /home/hdfs/lakala_udf.jar;
create temporary function IDCard as 'com.lkl.hive.udf.IDCard';

--M4.1 商户卡应用信息整合 （拉卡拉）
 DROP TABLE MERDM.DM_MER_CARDAPP_MID  ;
 
 CREATE  TABLE  MERDM.DM_MER_CARDAPP_MID  AS 
 SELECT 
   '110001' AS ORGID,  
   SHOP_NO  AS MER_ID,
   
   COUNT(DISTINCT CASE WHEN IDCard(ID_CARD)=1 THEN ID_CARD ELSE NULL END )  AS IDCARD_CNT_CARDAPP ,   -- 身份证验证
   COUNT(DISTINCT CASE WHEN A.STATUS = 'VALID' AND IDCard(ID_CARD)=1 THEN ID_CARD ELSE NULL END )  AS IDCARD_CNT_VALID_CARDAPP , 
   CONCAT_WS('*', SORT_ARRAY(COLLECT_SET(DISTINCT CASE WHEN IDCard(ID_CARD) =1 THEN   CAST(ID_CARD AS STRING) ELSE NULL END ))) AS IDCARD_CON_CARDAPP , 
   CONCAT_WS('*', SORT_ARRAY(COLLECT_SET(DISTINCT CASE WHEN A.STATUS = 'VALID' AND IDCard(ID_CARD) =1  THEN CAST(ID_CARD AS STRING)   ELSE NULL END  ))) AS IDCARD_VALID_CON_CARDAPP ,  
   
   COUNT(CARDAPP_ID)  AS CARDAPP_CNT ,
   COUNT( CASE WHEN A.STATUS = 'VALID' THEN CARDAPP_ID  ELSE NULL END  )  CARDAPP_CNT_VALID,
   
   MAX(CASE  WHEN  A.STATUS = 'VALID'  OR A.STATUS IS NULL THEN   1 ELSE  0 END) AS   CARDAPP_STATUS ,
   COUNT(DISTINCT CARD_APP_TYPE )  CARDAPP_TYPE_CNT ,
   COUNT(DISTINCT CASE WHEN A.STATUS = 'VALID'  THEN CARD_APP_TYPE ELSE NULL END )  CARDAPP_TYPE_CNT_VALID , 
   CONCAT_WS('*', SORT_ARRAY(COLLECT_SET(DISTINCT   CASE WHEN A.STATUS = 'VALID'  THEN CAST(CEIL(CARD_APP_TYPE) AS STRING)  ELSE NULL END   ))) CARDAPP_TYPE_CD_VALID_CON,
   CONCAT_WS('*', SORT_ARRAY(COLLECT_SET(DISTINCT   CASE WHEN A.STATUS = 'VALID'  THEN CAST(B.CARD_APP_TYPE_NAME AS STRING) ELSE NULL END   ))) CARDAPP_TYPE_NAME_VALID_CON,
   MAX(CASE WHEN  (CARD_APP_TYPE = '200127' or CARD_APP_TYPE = '200247') THEN 1 ELSE 0  END ) AS  IS_CARDAPP_MPOS , 
   MAX(CASE WHEN  CARD_APP_TYPE = '200127' THEN  1 ELSE 0 END ) AS  IS_CARDAPP_MPOS_PERSON ,
   MAX(CASE WHEN  CARD_APP_TYPE = '200247' THEN  1 ELSE 0 END ) AS  IS_CARDAPP_MPOS_MER,   
   
   COUNT(DISTINCT MCC_CODE) AS MCC_CNT,
   COUNT(DISTINCT CASE WHEN A.STATUS = 'VALID'  THEN MCC_CODE ELSE NULL END) AS MCC_CNT_VALID,
   CONCAT_WS('*', SORT_ARRAY(COLLECT_SET(DISTINCT   CAST(MCC_CODE AS STRING)  ))) MCC_CODE_CON, 
   CONCAT_WS('*', SORT_ARRAY(COLLECT_SET(DISTINCT   CASE WHEN A.STATUS = 'VALID'  THEN CAST(MCC_CODE AS STRING)   ELSE NULL END  ))) MCC_CODE_VALID_CON, 
   
   SUBSTR(MIN(CONTRACT_DT),1,10) AS CARDAPP_CONTRACT_DATE_MIN, 
   SUBSTR(MAX(CONTRACT_DT),1,10) AS CARDAPP_CONTRACT_DATE_MAX, 
   SUBSTR(MIN(CREATE_TM),1,10) AS  CARDAPP_CREATE_DATE_MIN,
   SUBSTR(MAX(CREATE_TM),1,10) AS  CARDAPP_CREATE_DATE_MAX,
   SUBSTR(MAX( LAST_MODIFY_TM),1,10) AS  CARDAPP_LAST_MODIFY_DATE, 
   
   COUNT( DISTINCT  ACCOUNT_KIND ) AS ACCOUNT_KIND_CNT , 
   COUNT( DISTINCT  CASE WHEN  A.STATUS = 'VALID'  THEN  ACCOUNT_KIND ELSE NULL END ) AS ACCOUNT_KIND_CNT_VALID , 
   CONCAT_WS('*', SORT_ARRAY(COLLECT_SET(DISTINCT  CASE WHEN A.STATUS = 'VALID'  THEN CAST(CEIL(ACCOUNT_KIND) AS STRING)  ELSE NULL END  ))) ACCOUNT_KIND_CD_VALID_CON ,
   CONCAT_WS('*', SORT_ARRAY(COLLECT_SET(DISTINCT  CASE WHEN A.STATUS = 'VALID'  THEN CAST( D2.PARAM_VALUE  AS STRING)  ELSE NULL END  ))) ACCOUNT_KIND_NAME_VALID_CON ,
   
   COUNT(DISTINCT ACCOUNT_NO) AS ACCOUNT_CNT ,
   COUNT(DISTINCT CASE  WHEN  A.STATUS = 'VALID' THEN  ACCOUNT_NO ELSE NULL END ) AS ACCOUNT_CNT_VALID ,
   CONCAT_WS('*', SORT_ARRAY(COLLECT_SET(DISTINCT CASE WHEN A.STATUS = 'VALID'  THEN CAST(ACCOUNT_NAME AS STRING)  ELSE NULL END  ))) ACCOUNT_NAME_CON ,  
   CONCAT_WS('*', SORT_ARRAY(COLLECT_SET(DISTINCT CASE WHEN A.STATUS = 'VALID'  THEN CAST(ACCOUNT_NO AS  STRING)   ELSE NULL END  ))) ACCOUNT_NO_CON ,    
   
   COUNT(DISTINCT SETTLE_PERIOD) AS SETTLE_PERIOD_TYPE_CNT ,
   COUNT(DISTINCT CASE  WHEN  A.STATUS = 'VALID' THEN  SETTLE_PERIOD ELSE NULL END ) AS SETTLE_PERIOD_TYPE_CNT_VALID ,
   CONCAT_WS('*', SORT_ARRAY(COLLECT_SET(DISTINCT CASE WHEN A.STATUS = 'VALID'  THEN CAST(CEIL(SETTLE_PERIOD) AS STRING )    ELSE NULL END  ))) SETTLE_PERIOD_CD_CON ,
   CONCAT_WS('*', SORT_ARRAY(COLLECT_SET(DISTINCT CASE WHEN A.STATUS = 'VALID'  THEN CAST( D1.PARAM_VALUE AS STRING )    ELSE NULL END  ))) SETTLE_PERIOD_NAME_CON ,
   
    CASE WHEN SUM(CASE WHEN A.STATUS= 'VALID' THEN MONTH_TRANS_LIMIT  ELSE 0 END ) >0  THEN 1 ELSE NULL END AS IS_MONTH_TRANS_LIMIT  ,
    CASE WHEN SUM(CASE WHEN A.STATUS= 'VALID' THEN PER_TRANS_LIMIT    ELSE 0 END ) >0  THEN 1 ELSE NULL END AS IS_PER_TRANS_LIMIT    ,  
    CASE WHEN SUM(CASE WHEN A.STATUS= 'VALID' THEN DAY_TRANS_LIMIT    ELSE 0 END ) >0  THEN 1 ELSE NULL END AS IS_DAY_TRANS_LIMIT    ,
    CASE WHEN SUM(CASE WHEN A.STATUS= 'VALID' THEN CREDIT_PER_LIMIT   ELSE 0 END ) >0  THEN 1 ELSE NULL END AS IS_CREDIT_PER_LIMIT   ,
    CASE WHEN SUM(CASE WHEN A.STATUS= 'VALID' THEN CREDIT_DAY_LIMIT   ELSE 0 END ) >0  THEN 1 ELSE NULL END AS IS_CREDIT_DAY_LIMIT   ,
    CASE WHEN SUM(CASE WHEN A.STATUS= 'VALID' THEN CREDIT_MONTH_LIMIT ELSE 0 END ) >0  THEN 1 ELSE NULL END AS IS_CREDIT_MONTH_LIMIT ,
    CASE WHEN SUM(CASE WHEN A.STATUS= 'VALID' THEN WCARD_PER_LIMIT    ELSE 0 END ) >0  THEN 1 ELSE NULL END AS IS_WCARD_PER_LIMIT    ,
    CASE WHEN SUM(CASE WHEN A.STATUS= 'VALID' THEN WCARD_DAY_LIMIT    ELSE 0 END ) >0  THEN 1 ELSE NULL END AS IS_WCARD_DAY_LIMIT    ,
    CASE WHEN SUM(CASE WHEN A.STATUS= 'VALID' THEN WCARD_MONTH_LIMIT  ELSE 0 END ) >0  THEN 1 ELSE NULL END AS IS_WCARD_MONTH_LIMIT  ,
    CASE WHEN SUM(CASE WHEN A.STATUS= 'VALID' THEN BCARD_PER_LIMIT    ELSE 0 END ) >0  THEN 1 ELSE NULL END AS IS_BCARD_PER_LIMIT    ,
    CASE WHEN SUM(CASE WHEN A.STATUS= 'VALID' THEN BCARD_DAY_LIMIT    ELSE 0 END ) >0  THEN 1 ELSE NULL END AS IS_BCARD_DAY_LIMIT    ,
    CASE WHEN SUM(CASE WHEN A.STATUS= 'VALID' THEN BCARD_MONTH_LIMIT  ELSE 0 END ) >0  THEN 1 ELSE NULL END AS IS_BCARD_MONTH_LIMIT  ,
 
   MAX(BILL_DELIVERY_EMAIL) BILL_DELIVERY_EMAIL ,
   
   '${DATE_0M}' AS LOAD_DATE   
  
  FROM 
   HDS.BMCP_T01_CARDAPP_INFO A
  LEFT JOIN 
   (SELECT CARDAPP_TYPE_ID,  CARD_APP_TYPE_NAME  FROM  HDS.BMCP_T01_CARDAPP_TYPES ) B
   ON A.CARD_APP_TYPE = B.CARDAPP_TYPE_ID 
  LEFT JOIN 
   (SELECT PARAM_ID, PARAM_VALUE   FROM  Hds.BMCP_T99_SYS_PARAMETERS WHERE  PARAM_KIND = 'SETTLE_PERIOD' ) D1
   ON A.SETTLE_PERIOD = D1.PARAM_ID  
   LEFT JOIN 
   (SELECT PARAM_ID, PARAM_VALUE   FROM  Hds.BMCP_T99_SYS_PARAMETERS WHERE  PARAM_KIND = 'ACCOUNT_KIND' ) D2
   ON A.ACCOUNT_KIND = D2.PARAM_ID 
  GROUP BY A.SHOP_NO   ;
 
 
--M4.2 商户卡应用信息整合 （外部） 
 INSERT INTO   MERDM.DM_MER_CARDAPP_MID  
 SELECT 
   ORGID AS ORGID,  
   A.merchantid   AS MER_ID,
   
   NULL AS IDCARD_CNT_CARDAPP ,   -- 身份证验证
   NULL AS IDCARD_CNT_VALID_CARDAPP , 
   NULL AS IDCARD_CON_CARDAPP , 
   NULL AS IDCARD_VALID_CON_CARDAPP ,  
   
   NULL  AS CARDAPP_CNT ,
   NULL  AS CARDAPP_CNT_VALID, 
   
   NULL AS CARDAPP_STATUS ,
   NULL AS CARDAPP_TYPE_CNT ,
   NULL AS CARDAPP_TYPE_CNT_VALID , 
   NULL AS CARDAPP_TYPE_CD_VALID_CON ,
   NULL AS CARDAPP_TYPE_NAME_VALID_CON ,
   NULL AS IS_CARDAPP_MPOS , 
   NULL AS IS_CARDAPP_MPOS_PERSON ,
   NULL AS IS_CARDAPP_MPOS_MER ,   
   
   COUNT(DISTINCT terminalmcc) AS MCC_CNT ,
   COUNT(DISTINCT CASE WHEN NVL(A.terminalstate, 'VALID') = 'VALID'  THEN terminalmcc ELSE NULL END) AS MCC_CNT_VALID ,
   CONCAT_WS('*', SORT_ARRAY(COLLECT_SET(DISTINCT  CAST(terminalmcc AS STRING)  ))) MCC_CODE_CON , 
   CONCAT_WS('*', SORT_ARRAY(COLLECT_SET(DISTINCT  CASE WHEN NVL(A.terminalstate, 'VALID') = 'VALID'  THEN CAST(terminalmcc AS STRING)   ELSE NULL END  ))) MCC_CODE_VALID_CON , 
   
   NULL AS CARDAPP_CONTRACT_DATE_MIN, 
   NULL AS CARDAPP_CONTRACT_DATE_MAX, 
   NULL AS CARDAPP_CREATE_DATE_MIN,
   NULL AS CARDAPP_CREATE_DATE_MAX,
   NULL AS CARDAPP_LAST_MODIFY_DATE, 
   
   COUNT( DISTINCT   tersettlementacctype  ) AS ACCOUNT_KIND_CNT , 
   COUNT( DISTINCT  CASE WHEN  NVL(A.terminalstate , 'VALID') = 'VALID'  THEN   tersettlementacctype  ELSE NULL END ) AS ACCOUNT_KIND_CNT_VALID , 
   CONCAT_WS('*', SORT_ARRAY(COLLECT_SET(DISTINCT  CASE WHEN  NVL(A.terminalstate , 'VALID') = 'VALID'  THEN CAST(CEIL(tersettlementacctype) AS STRING)  ELSE NULL END  ))) ACCOUNT_KIND_CD_VALID_CON ,
   CONCAT_WS('*', SORT_ARRAY(COLLECT_SET(DISTINCT  CASE WHEN  NVL(A.terminalstate , 'VALID') = 'VALID'  THEN CAST( D2.PARAM_VALUE  AS STRING)  ELSE NULL END  ))) ACCOUNT_KIND_NAME_VALID_CON ,
   
   COUNT(DISTINCT tersettlementaccount ) AS ACCOUNT_CNT ,
   COUNT(DISTINCT CASE  WHEN   NVL(A.terminalstate , 'VALID') = 'VALID' THEN  tersettlementaccount  ELSE NULL END ) AS ACCOUNT_CNT_VALID ,
   CONCAT_WS('*', SORT_ARRAY(COLLECT_SET(DISTINCT CASE WHEN  NVL(A.terminalstate , 'VALID') = 'VALID'  THEN CAST(tersettlementaccname AS STRING)  ELSE NULL END  ))) ACCOUNT_NAME_CON ,  
   CONCAT_WS('*', SORT_ARRAY(COLLECT_SET(DISTINCT CASE WHEN  NVL(A.terminalstate , 'VALID') = 'VALID'  THEN CAST(tersettlementaccount  AS  STRING)   ELSE NULL END  ))) ACCOUNT_NO_CON ,    
   
   COUNT(DISTINCT tersettlementcycle ) AS SETTLE_PERIOD_TYPE_CNT ,
   COUNT(DISTINCT CASE  WHEN   NVL(A.terminalstate , 'VALID') = 'VALID'  THEN  tersettlementcycle  ELSE NULL END ) AS SETTLE_PERIOD_TYPE_CNT_VALID ,
   CONCAT_WS('*', SORT_ARRAY(COLLECT_SET(DISTINCT CASE WHEN  NVL(A.terminalstate , 'VALID') = 'VALID' THEN CAST(CEIL(tersettlementcycle ) AS STRING )    ELSE NULL END  ))) SETTLE_PERIOD_CD_CON ,
   CONCAT_WS('*', SORT_ARRAY(COLLECT_SET(DISTINCT CASE WHEN  NVL(A.terminalstate , 'VALID') = 'VALID'  THEN CAST( D1.PARAM_VALUE AS STRING )    ELSE NULL END  ))) SETTLE_PERIOD_NAME_CON ,
   
    CASE WHEN SUM(CASE WHEN  NVL(A.terminalstate , 'VALID') = 'VALID' THEN  istradinglimit  ELSE 0 END ) >0  THEN 1 ELSE NULL END AS IS_MONTH_TRANS_LIMIT  ,
    CASE WHEN SUM(CASE WHEN  NVL(A.terminalstate , 'VALID') = 'VALID' THEN singletradinglimit  ELSE 0 END ) >0  THEN 1 ELSE NULL END AS IS_PER_TRANS_LIMIT    ,  
    CASE WHEN SUM(CASE WHEN  NVL(A.terminalstate , 'VALID') = 'VALID' THEN onedaytradinglimit ELSE 0 END ) >0  THEN 1 ELSE NULL END AS IS_DAY_TRANS_LIMIT    ,
    NULL AS IS_CREDIT_PER_LIMIT   ,
    NULL AS IS_CREDIT_DAY_LIMIT   ,
    NULL AS IS_CREDIT_MONTH_LIMIT ,
    NULL AS IS_WCARD_PER_LIMIT    ,
    NULL AS IS_WCARD_DAY_LIMIT    ,
    NULL AS IS_WCARD_MONTH_LIMIT  ,
    NULL AS IS_BCARD_PER_LIMIT    ,
    NULL AS IS_BCARD_DAY_LIMIT    ,
    NULL AS IS_BCARD_MONTH_LIMIT  ,
 
    NULL AS BILL_DELIVERY_EMAIL   ,
  
   '${DATE_0M}' AS LOAD_DATE	
 
  FROM 
   merdm.WBPOS_MSTERMINALINFO A 
  LEFT JOIN 
    (SELECT PARAM_ID, PARAM_VALUE   FROM  Hds.BMCP_T99_SYS_PARAMETERS WHERE  PARAM_KIND = 'SETTLE_PERIOD' ) D1
  ON A.tersettlementcycle  = D1.PARAM_ID  
  LEFT JOIN 
  (SELECT PARAM_ID, PARAM_VALUE   FROM  Hds.BMCP_T99_SYS_PARAMETERS WHERE  PARAM_KIND = 'ACCOUNT_KIND' ) D2
  ON A.tersettlementacctype = D2.PARAM_ID 
 GROUP BY A.ORGID, A.merchantid    ;

EOF
tmp=$?
NOWTIME=$(date +%Y-%m-%d/%H:%M:%S)
if test $tmp -eq 0; then
   echo $NOWTIME '--------------M4_商户卡应用信息整合成功 MERDM.DM_MER_CARDAPP_MID_WB -------------'$tmp
   break
else
   echo $NOWTIME '--------------M4_商户卡应用信息整合失败 MERDM.DM_MER_CARDAPP_MID_WB -------------'$tmp
fi
done


while true
do
echo '--------------------- C1_商户属性集市 -------------------'
sudo -u hdfs hive <<EOF

set hive.auto.convert.join=false; 
set mapreduce.reduce.shuffle.input.buffer.percent=0.1;
set mapreduce.reduce.shuffle.parallelcopies=9;

add jar /home/hdfs/lakala_udf.jar;
create temporary function IDCard as 'com.lkl.hive.udf.IDCard';

--C1  整合商户基本信息 

 DROP TABLE MERDM.DM_MER_BASE_DAY ;

 CREATE TABLE  MERDM.DM_MER_BASE_DAY AS 
 SELECT  
-- PK
  T1.ORGID ,
  T1.MER_ID ,             
 
-- AB01: 标识
  T1.MERCHANT_ID ,
  T1.MER_LICENCE_NO ,    

  T1.MER_BUSINESS_NAME ,   
  T1.MER_REGISTER_NAME ,
  T2.SHOP_NAME_LST ,  
  NVL(T2.SHOP_NAME_LST, NVL(T1.MER_BUSINESS_NAME, T1.MER_REGISTER_NAME)) AS MER_NAME_COL,   

-- AB02: 商户性质
  T1.CORPORATION_PROPERTY ,
  T1.CORPORATION_PROPERTY_NAME ,
 
-- AB03: 商户类型
  T1.BUSINESS_CONTENT , 
  T1.MCC_MERID , 
  D1.MCC_NAME_LV0 AS MCC_MERID_LV0 ,
  D1.MCC_NAME_LV1 AS MCC_MERID_LV1 ,
  D1.MCC_NAME_LV2 AS MCC_MERID_LV2 ,
  D1.MEMO AS MCC_MERID_MEMO ,
  
  T5.MCC_TRANS,
  D2.MCC_NAME_LV0 AS MCC_TRANS_LV0 ,
  D2.MCC_NAME_LV1 AS MCC_TRANS_LV1 ,
  D2.MCC_NAME_LV2 AS MCC_TRANS_LV2 ,
  D2.MEMO AS MCC_TRANS_MEMO ,
  
  T4.MCC_CNT ,             
  T4.MCC_CNT_VALID , 
  T4.MCC_CODE_CON ,           
  T4.MCC_CODE_VALID_CON , 
  
  NVL(T5.MCC_TRANS, NVL(T1.MCC_MERID, SUBSTR(T4.MCC_CODE_VALID_CON,1,4)))  AS MCC_COL,
  D8.MCC_NAME_LV0    AS MCC_COL_LV0 ,
  D8.MCC_NAME_LV1    AS MCC_COL_LV1 ,
  D8.MCC_NAME_LV2    AS MCC_COL_LV2 ,
  D8.MEMO as MCC_COL_MEMO ,
  
--AB04: 区域 (后续补法人、手机号、商户号等区域)
  T1.MER_REG_ADDR ,       
  T1.MER_REG_ADDR_DIST ,
  D3.PROV_CODE AS  MER_REG_ADDR_CD_PROV , 
  D3.PROV_NAME AS  MER_REG_ADDR_PROV , 
  D3.CITY_CODE AS  MER_REG_ADDR_CD_CITY ,
  D3.CITY_NAME AS  MER_REG_ADDR_CITY ,
  D3.AREA_CODE AS  MER_REG_ADDR_CD_AREA ,
  D3.AREA_NAME AS  MER_REG_ADDR_AREA ,
  D3.CITY_LV AS MER_REG_ADDR_CITYLV,  
  T2.SHOP_ACT_ADDR_LST  , 
  T1.SHOP_ACT_ADDR_MPOS ,
  T2.SHOP_ACT_ADDR_DIST_LST ,
  D4.PROV_CODE AS  SHOP_ACT_ADDR_CD_PROV ,
  D4.PROV_NAME AS  SHOP_ACT_ADDR_PROV ,
  D4.CITY_CODE AS  SHOP_ACT_ADDR_CD_CITY ,
  D4.CITY_NAME AS  SHOP_ACT_ADDR_CITY ,
  D4.AREA_CODE AS  SHOP_ACT_ADDR_CD_AREA ,  
  D4.AREA_NAME AS  SHOP_ACT_ADDR_AREA ,  
  D4.CITY_LV AS SHOP_ACT_ADDR_CITYLV ,  
  T2.SHOP_AREA_CODE_LST ,
  D5.PROV_CODE AS  SHOP_AREA_CD_PROV ,
  D5.PROV_NAME AS  SHOP_AREA_PROV ,
  D5.CITY_CODE AS  SHOP_AREA_CD_CITY ,
  D5.CITY_NAME AS  SHOP_AREA_CITY ,
  D5.AREA_CODE AS  SHOP_AREA_CD_AREA , 
  D5.AREA_NAME AS  SHOP_AREA_AREA , 
  D5.CITY_LV AS SHOP_AREA_CITYLV ,
  T1.CUPS_AREA  ,
  T2.CUPS_AREA_LST  ,
  T1.ZIPCODE ,
  T1.AREA_MERID,
  D6.PROV_CODE AS  MER_AREA_MERID_CD_PROV , 
  D6.PROV_NAME AS  MER_AREA_MERID_PROV , 
  D6.CITY_CODE AS  MER_AREA_MERID_CD_CITY ,
  D6.CITY_NAME AS  MER_AREA_MERID_CITY ,
  D6.AREA_CODE AS  MER_AREA_MERID_CD_AREA , 
  D6.AREA_NAME AS  MER_AREA_MERID_AREA , 
  D6.CITY_LV AS MER_AREA_MERID_CITYLV ,
  
  NVL( T2.SHOP_ACT_ADDR_DIST_LST  , NVL(T2.SHOP_AREA_CODE_LST, NVL(T1.MER_REG_ADDR_DIST, T1.AREA_MERID ))) AS MER_DIST_COL ,
  D7.PROV_CODE AS  MER_DIST_COL_CD_PROV , 
  D7.PROV_NAME AS  MER_DIST_COL_PROV , 
  D7.CITY_CODE AS  MER_DIST_COL_CD_CITY ,
  D7.CITY_NAME AS  MER_DIST_COL_CITY ,
  D7.AREA_CODE AS  MER_DIST_COL_CD_AREA ,
  D7.AREA_NAME AS  MER_DIST_COL_AREA , 
  D7.CITY_LV AS MER_DIST_COL_CITYLV ,
 
--AB05: 时间 
   T1.MER_CREATE_DATE ,         
   T1.MER_LAST_MODIFY_DATE , 
   T2.SHOP_CREATE_DATE_MIN ,
   T2.SHOP_CREATE_DATE_MAX ,
   T2.SHOP_LAST_MODIFY_DATE ,
   T3.TERM_CREATE_DATE_MIN ,
   T3.TERM_CREATE_DATE_MAX ,
   T3.TERM_LAST_MODIFY_DATE ,
   T4.CARDAPP_CONTRACT_DATE_MIN ,   
   T4.CARDAPP_CONTRACT_DATE_MAX ,   
   T4.CARDAPP_CREATE_DATE_MIN ,     
   T4.CARDAPP_CREATE_DATE_MAX ,     
   T4.CARDAPP_LAST_MODIFY_DATE , 
   
   LEAST(T1.MER_CREATE_DATE, T2.SHOP_CREATE_DATE_MIN , T3.TERM_CREATE_DATE_MIN,  T4.CARDAPP_CONTRACT_DATE_MIN , T4.CARDAPP_CREATE_DATE_MIN )  AS MER_DATE_MIN_COL ,

   
--AB06:联系方式 
    T1.MER_CONTACT_PERSON ,
    T2.SHOP_CONTACT_LST,
    T2.SHOP_CONTACT_PERSON_LST ,
    NVL(T1.LEGAL_PERSON, NVL(T1.MER_CONTACT_PERSON , NVL(T2.SHOP_CONTACT_LST , T2.SHOP_CONTACT_PERSON_LST  ))) AS MER_PERSON_NAME_COL ,
    
    T1.MOBILE_WECHAT,
    T2.SHOP_MOBILE_PHONE_LST, 
    T1.MER_CONTACT_TELE ,  
    T2.SHOP_CONTACT_TELE_LST,
    T2.INSTALL_CONTACT_TELE_LST,
    NVL( T1.MER_CONTACT_TELE, NVL(T1.MOBILE_WECHAT, T2.SHOP_MOBILE_PHONE_LST )) AS  MER_MOBILE_COL ,
    
    T1.FINANCE_MAIL,        
    T1.MERCHANT_EMAIL,   
    T2.SHOP_EMAIL_LST,
    T4.BILL_DELIVERY_EMAIL, 
    NVL(T4.BILL_DELIVERY_EMAIL, NVL(T2.SHOP_EMAIL_LST, NVL(T1.FINANCE_MAIL,T1.MERCHANT_EMAIL ))) AS MER_MAIL_COL ,
    
    T1.OPEN_ID ,
    T1.WECHAT_SUBMER_CHANTNO ,
    T1.ALIPAY_SUBMER_CHANTNO ,    
    T1.IS_WECHAT , 
 
--AB07:状态  
    T1.MER_STATUS ,              
    T2.SHOP_STATUS  , 
    T3.TERM_STATUS ,
    T4.CARDAPP_STATUS ,  
    T1.MER_LEVEL ,
    T1.AUDIT_RESULT_MPOS ,
  
-- AP:  法人
    T1.LEGAL_PERSON AS LEGAL_PERSON_NAME ,
    T4.IDCARD_CNT_CARDAPP ,         
    T4.IDCARD_CNT_VALID_CARDAPP , 
    T1.IDCARD_NO ,
    T4.IDCARD_CON_CARDAPP ,              
    T4.IDCARD_VALID_CON_CARDAPP ,
  
-- AS: 资产设备
    T2.SHOP_CNT ,           
    T2.SHOP_CNT_VALID ,
    T3.TERM_CNT ,            
    T3.TERM_CNT_VALID ,    
    T3.DEVICE_TYPE_CNT ,     
    T3.DEVICE_TYPE_CNT_VALID ,
    T3.DEVICE_TYPE_CON ,         
    T3.DEVICE_TYPE_NAME_CON ,    
    T3.DEVICE_KIND_CON ,  
    T3.DEVICE_TYPE_VALID_CON ,   
    T3.DEVICE_TYPE_NAME_VALID_CON ,
    T3.DEVICE_KIND_VALID_CON ,
    T4.CARDAPP_CNT ,         
    T4.CARDAPP_CNT_VALID ,   
    T4.CARDAPP_TYPE_CNT ,    
    T4.CARDAPP_TYPE_CNT_VALID ,
    T4.CARDAPP_TYPE_CD_VALID_CON ,
    T4.CARDAPP_TYPE_NAME_VALID_CON ,
    T4.IS_CARDAPP_MPOS ,
    T4.IS_CARDAPP_MPOS_PERSON ,
    T4.IS_CARDAPP_MPOS_MER ,
  
-- AC: 协议
     T1.DISCOUNT_STANDARD  ,  
     T4.IS_MONTH_TRANS_LIMIT ,
     T4.IS_PER_TRANS_LIMIT ,  
     T4.IS_DAY_TRANS_LIMIT ,  
     T4.IS_CREDIT_PER_LIMIT , 
     T4.IS_CREDIT_DAY_LIMIT , 
     T4.IS_CREDIT_MONTH_LIMIT ,
     T4.IS_WCARD_PER_LIMIT ,  
     T4.IS_WCARD_DAY_LIMIT ,  
     T4.IS_WCARD_MONTH_LIMIT ,
     T4.IS_BCARD_PER_LIMIT ,  
     T4.IS_BCARD_DAY_LIMIT ,  
     T4.IS_BCARD_MONTH_LIMIT ,
     T4.SETTLE_PERIOD_TYPE_CNT ,
     T4.SETTLE_PERIOD_TYPE_CNT_VALID ,
     T4.SETTLE_PERIOD_CD_CON ,   
     T4.SETTLE_PERIOD_NAME_CON ,

   
-- AA: 账户
     T4.ACCOUNT_KIND_CNT ,    
     T4.ACCOUNT_KIND_CNT_VALID ,
     T4.ACCOUNT_KIND_CD_VALID_CON ,
     T4.ACCOUNT_KIND_NAME_VALID_CON ,
 
     T4.ACCOUNT_CNT ,        
     T4.ACCOUNT_CNT_VALID ,      
     T4.ACCOUNT_NO_CON,
     T4.ACCOUNT_NAME_CON ,

      '${DATE_0M}' AS LOAD_DATE		 
 
FROM 
  MERDM.DM_MER_MERCHANT_MID  T1
 LEFT JOIN 
  MERDM.DM_MER_SHOP_MID T2
 ON T1.ORGID = T2.ORGID AND T1.MER_ID = T2.MER_ID 
 LEFT JOIN 
  MERDM.DM_MER_TERM_MID T3 
 ON T1.ORGID = T3.ORGID AND T1.MER_ID = T3.MER_ID 
 LEFT JOIN 
  MERDM.DM_MER_CARDAPP_MID T4
 ON T1.ORGID = T4.ORGID AND T1.MER_ID = T4.MER_ID 
 LEFT JOIN 
  MERDM.MER_MCC_TRANS_MID  T5
  ON T1.ORGID = T5.ORGID AND T1.MER_ID = T5.MER_ID 
 LEFT JOIN 
  (SELECT * FROM  MERDM.BMCP_T99_MCC_CODE_INFO_P) D1
  ON T1.MCC_MERID = D1.MCC_LV2
 LEFT JOIN 
  (SELECT * FROM  MERDM.BMCP_T99_MCC_CODE_INFO_P) D2
  ON T5.MCC_TRANS = D2.MCC_LV2      
 LEFT JOIN 
  (SELECT * FROM  MERDM.BMCP_T99_CHN_DISTRICT_CDE_P) D3
  ON T1.MER_REG_ADDR_DIST = D3.DISTRICT_ID
 LEFT JOIN 
  (SELECT * FROM  MERDM.BMCP_T99_CHN_DISTRICT_CDE_P) D4
  ON T2.SHOP_ACT_ADDR_DIST_LST  = D4.DISTRICT_ID
  LEFT JOIN 
  (SELECT * FROM  MERDM.BMCP_T99_CHN_DISTRICT_CDE_P) D5
  ON  T2.SHOP_AREA_CODE_LST = D5.DISTRICT_ID  
  LEFT JOIN 
  (SELECT * FROM  MERDM.BMCP_T99_CHN_DISTRICT_CDE_P) D6
  ON  T1.AREA_MERID = D6.DISTRICT_ID    
  LEFT JOIN 
  (SELECT * FROM  MERDM.BMCP_T99_CHN_DISTRICT_CDE_P) D7
  ON  NVL(  T2.SHOP_ACT_ADDR_DIST_LST , NVL(T2.SHOP_AREA_CODE_LST,   NVL(T1.MER_REG_ADDR_DIST , T1.AREA_MERID))) = D7.DISTRICT_ID
  LEFT JOIN 
  (SELECT * FROM  MERDM.BMCP_T99_MCC_CODE_INFO_P) D8
  ON NVL(T5.MCC_TRANS, NVL(T1.MCC_MERID, SUBSTR(T4.MCC_CODE_VALID_CON,1,4))) = D8.MCC_LV2 ; 
EOF
tmp=$?
NOWTIME=$(date +%Y-%m-%d/%H:%M:%S)
if test $tmp -eq 0; then
   echo $NOWTIME '--------------C1_商户属性集市整合成功 MERDM.DM_MER_MERCHANT_MID-------------'$tmp
   break
else
   echo $NOWTIME '--------------C1_商户属性集市整合失败 MERDM.DM_MER_MERCHANT_MID-------------'$tmp
fi
done

