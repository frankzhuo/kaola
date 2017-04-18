cd /home/hdfs/mobile

TODAYF_1D=$(date --date='1 day ago' +%Y-%m-%d)

#sudo -u hdfs sqoop import --connect jdbc:oracle:thin:@10.1.2.197:1521/creditdg2 --username cisdb --password 'amberdb!#$1234' --table COURTLOSECREDITED --hive-table zhengxin.COURTLOSECREDITED --where "CREATETIME >=\\'${TODAYF_1D}\\'" --hive-import --hive-drop-import-delims --hive-overwrite  --fields-terminated-by '\001'  --null-string '\\N' --null-non-string '\\N'  -m 1; 

sudo -u hdfs sqoop import \
 --connect jdbc:oracle:thin:@10.1.2.197:1521/creditdg2 --username cisdb --password 'amberdb!#$1234' \
 -m 1 \
 --hive-table zhengxin.COURTLOSECREDITED \
 --delete-target-dir \
 --hive-import \
 --hive-drop-import-delims \
 --hive-overwrite  \
 --fields-terminated-by '\001'  \
 --null-string '\\N' \
 --null-non-string '\\N' \
 --target-dir /user/hive/warehouse/zhengxin.db/COURTLOSECREDITED \
 --query "select a.* from cisdb.COURTLOSECREDITED a where 1=1 and CREATETIME >=to_date('${TODAYF_1D}','yyyy-MM-dd') and \$CONDITIONS"

#sudo -u root hadoop fs -copyToLocal -p /user/hive/warehouse/zhengxin.db/courtlosecredited /home/hdfs/shixin/courtlosecredited_"${TODAYF_1D}"
sudo -u root hadoop fs -cat /user/hive/warehouse/zhengxin.db/courtlosecredited/* > /home/hdfs/shixin/courtlosecredited_"${TODAYF_1D}"

scp -r /home/hdfs/shixin/courtlosecredited_"${TODAYF_1D}" root@10.5.28.14:/data/crawler/shixin/courtlosecredited_"${TODAYF_1D}"


10 12 * * *  sh /home/hdfs/mobile/cpshixin.sh  > /home/hdfs/mobile/log/log_shixin_`date +\%Y-\%m-\%d`.log 2>&1