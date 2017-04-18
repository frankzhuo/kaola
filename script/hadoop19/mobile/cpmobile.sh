cd /home/hdfs/mobile
scp  root@10.5.28.14:/data/crawler/zol.txt /home/hdfs/mobile
scp  root@10.5.28.14:/data/crawler/zolpad.txt /home/hdfs/mobile
chmod 777 /home/hdfs/mobile/zol.txt
chmod 777 /home/hdfs/mobile/zolpad.txt
cp total.txt  total.txt.back
cat  zol.txt  >total.txt
cat zolpad.txt >>total.txt

#sudo -u hdfs hadoop fs -rm /user/hive/warehouse/zhengxin.db/mobile/total.txt
#sudo -u hdfs hadoop fs -put /home/hdfs/mobile/total.txt /user/hive/warehouse/zhengxin.db/mobile/

sudo -u hdfs hive <<EOF
use zhengxin;
load data local inpath '/home/hdfs/mobile/total.txt' overwrite into table mobile;
EOF
