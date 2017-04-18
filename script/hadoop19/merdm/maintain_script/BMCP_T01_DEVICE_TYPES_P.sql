REATE TABLE merdm.BMCP_T01_DEVICE_TYPES_P AS 
 SELECT 

 T3.device_type_id  AS device_type_id  ,   T3.device_type_name AS device_type_name,      T3.device_type_code AS device_type_code,   T3.device_kind AS device_kind ,    T3.device_kind_name,  T3.pos_type,  T3.POS_KIND_NAME ,
 T2.device_type_id  AS device_type_id_lv2 ,   T2.device_type_name AS device_type_name_LV2,      T2.device_type_code AS device_type_code_LV2,   T2.device_kind AS device_kind_LV2,    T2.device_kind_name AS device_kind_name_LV2,  T2.pos_type AS pos_type_LV2 ,  T2.POS_KIND_NAME  AS POS_KIND_NAME_lv2 ,
 T1.device_type_id  AS device_type_id_LV1 ,   T1.device_type_name AS device_type_name_LV1,      T1.device_type_code AS device_type_code_LV1,   T1.device_kind AS device_kind_LV1,    T1.device_kind_name AS device_kind_name_LV1 ,  T1.pos_type  AS pos_type_LV1 ,  T1.POS_KIND_NAME  AS POS_KIND_NAME_lv1

 FROM 
 (
  SELECT * FROM  
     ( SELECT * FROM   hds.BMCP_T01_DEVICE_TYPES )   a
  LEFT  JOIN 
     ( SELECT PARAM_ID, PARAM_VALUE AS DEVICE_KIND_NAME   FROM  hds.BMCP_T99_SYS_PARAMETERS WHERE  PARAM_KIND = 'DEVICE_KIND'  ) b
   ON  a.device_kind = b.param_id  
   LEFT  JOIN 
      ( SELECT PARAM_ID PARAM_ID_c, PARAM_VALUE AS POS_KIND_NAME    FROM  hds.BMCP_T99_SYS_PARAMETERS WHERE  PARAM_KIND = 'POS_TYPE'  ) c
   ON  a.pos_type = c.PARAM_ID_c  
   ) T3  
 LEFT JOIN 
   (
  SELECT * FROM  
     ( SELECT * FROM   hds.BMCP_T01_DEVICE_TYPES )   a
  LEFT  JOIN 
     ( SELECT PARAM_ID, PARAM_VALUE AS DEVICE_KIND_NAME   FROM  hds.BMCP_T99_SYS_PARAMETERS WHERE  PARAM_KIND = 'DEVICE_KIND'  ) b
   ON  a.device_kind = b.param_id  
   LEFT  JOIN 
      ( SELECT PARAM_ID PARAM_ID_c, PARAM_VALUE AS POS_KIND_NAME    FROM  hds.BMCP_T99_SYS_PARAMETERS WHERE  PARAM_KIND = 'POS_TYPE'  ) c
   ON  a.pos_type = c.PARAM_ID_c  
   ) T2 
  ON  T3.device_type_parent  = T2.device_type_id
  LEFT JOIN 
   (
  SELECT * FROM  
     ( SELECT * FROM   hds.BMCP_T01_DEVICE_TYPES )   a
  LEFT  JOIN 
     ( SELECT PARAM_ID, PARAM_VALUE AS DEVICE_KIND_NAME   FROM  hds.BMCP_T99_SYS_PARAMETERS WHERE  PARAM_KIND = 'DEVICE_KIND'  ) b
   ON  a.device_kind = b.param_id  
   LEFT  JOIN 
      ( SELECT PARAM_ID AS PARAM_ID_c , PARAM_VALUE AS POS_KIND_NAME    FROM  hds.BMCP_T99_SYS_PARAMETERS WHERE  PARAM_KIND = 'POS_TYPE'  ) c
   ON  a.pos_type = c.PARAM_ID_c   
   ) T1
     ON  T2.device_type_parent  = T1.device_type_id ;
