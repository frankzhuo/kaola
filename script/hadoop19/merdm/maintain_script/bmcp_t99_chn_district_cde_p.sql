
-- 区域表
CREATE TABLE  merdm.bmcp_t99_chn_district_cde_p_mid  AS 
  SELECT 
   ceil(t1.district_id)  AS district_id, 
  TRIM( t1.district_name) AS district_name ,
   t1.district_level,
   t1.status,
   t1.is_cups_area,
  ceil( t1.area_code) AS distcode,
   t1.load_date,
      CASE WHEN t1.district_level  =0 THEN  ceil(t1.district_id )
             WHEN t1.district_level = 1 THEN  ceil(t2.district_id )
              WHEN t1.district_level=2 THEN ceil(t3.district_id )
   END  AS  prov_code,
   
   CASE WHEN t1.district_level  =0 THEN  trim(regexp_replace( t1.district_name , '　', ''))
             WHEN t1.district_level = 1 THEN  trim(regexp_replace( t2.district_name , '　', ''))
              WHEN t1.district_level=2 THEN trim(regexp_replace( t3.district_name , '　', ''))
   END  AS  prov_name,
   
         CASE WHEN t1.district_level  =0 THEN  NULL
             WHEN t1.district_level = 1 THEN  ceil(t1.district_id )
              WHEN t1.district_level=2 THEN ceil(t2.district_id) 
   END  AS  city_code,
   
      CASE WHEN t1.district_level  =0 THEN  NULL
             WHEN t1.district_level = 1 THEN trim( regexp_replace( t1.district_name, '　', ''))
              WHEN t1.district_level=2 THEN trim( regexp_replace( t2.district_name, '　', '')) 
   END  AS  city_name ,
   
   
      CASE WHEN t1.district_level  =0 THEN  NULL
             WHEN t1.district_level = 1 THEN   NULL 
              WHEN t1.district_level=2 THEN   ceil(t1.district_id) 
   END  AS  area_code,
   
        CASE WHEN t1.district_level  =0 THEN  NULL
             WHEN t1.district_level = 1 THEN  NULL
              WHEN t1.district_level=2 THEN trim( regexp_replace( t1.district_name, '　', '')) 
   END  AS  area_name 
    FROM 
     ( SELECT * FROM  hds.bmcp_t99_chn_district_cde  ) t1 
     LEFT  JOIN 
     (SELECT * FROM  hds.bmcp_t99_chn_district_cde   ) t2
     ON t1.district_parent = t2.district_id
     LEFT JOIN 
     (SELECT * FROM hds.bmcp_t99_chn_district_cde  ) t3 
     ON t2.district_parent = t3.district_id ;
 
 

-- 添加城市级别
   CREATE TABLE merdm.bmcp_t99_chn_district_cde_p AS 
   SELECT a.*,
   CASE WHEN  a.city_name LIKE '%张家口%'  THEN  '4'
             WHEN a.city_name LIKE '%张家港%' THEN '3'
             WHEN  b.city_lv IS NOT NULL THEN b.city_lv
             WHEN  a.city_name IS NOT NULL AND b.city_lv IS NULL THEN  '6'   END AS  city_lv,
    CASE WHEN  a.city_name LIKE '%张家口%'  THEN  '1'
             WHEN a.city_name LIKE '%张家港%' THEN '1'
             WHEN  b.city_lv IS NOT NULL THEN b.city_sub_lv
             WHEN  a.city_name IS NOT NULL AND b.city_lv IS NULL THEN '1'  END AS  city_sub_lv
 FROM 
  merdm.bmcp_t99_chn_district_cde_p_mid a
 LEFT JOIN 
  (SELECT * FROM   merdm.D_CITY_LEVEL WHERE city_name NOT LIKE '%张家%') b
 ON  substr(a.city_name,1,2) =  substr(b. city_name,1,2)   ; 
 
 
 
