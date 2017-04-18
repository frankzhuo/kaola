#!/bin/bash

cd /opt/cloudera/parcels/CDH/lib/hadoop/
#sudo -u hbase hadoop jar importtsv.jar com.lkl.hbase.CreateTable lklpos_atm_risk_control,cf,0000,e9999,60,0,10.1.80.171,2181


sudo -u hbase hadoop fs -rm -r /user/hbase/wbpos


 
sudo -u hbase hadoop jar /opt/cloudera/parcels/CDH/lib/hbase/hbase-server-1.0.0-cdh5.6.0.jar importtsv -Dimporttsv.rowkey.index=0,4 -Dhbase.zookeeper.quorum=hadoop01,hadoop06,hadoop16,hadoop22,hadoop02 -Dimporttsv.avro=false -Dimporttsv.columns=HBASE_ROW_KEY,cf:col -Dimporttsv.bulk.output=/user/hbase/wbpos -Dimporttsv.timestamp.index=4 -Dimporttsv.rowkey.append.str=detail  -Dimporttsv.separator=, -Dimporttsv.mapper.class=com.lkl.hbase.HFileMapper lklpos_atm_risk_control /user/hive/warehouse/merzx.db/wbpos_atmtxnjnl

sudo -u hbase hadoop jar /opt/cloudera/parcels/CDH/lib/hbase/hbase-server-1.0.0-cdh5.6.0.jar   completebulkload /user/hbase/wbpos lklpos_atm_risk_control

