#!/bin/sh

sudo -u hbase hadoop fs -rm -r /user/hbase/rcc


sudo -u hbase hadoop jar /opt/cloudera/parcels/CDH/lib/hbase/hbase-server-1.0.0-cdh5.6.0.jar importtsv -Dimporttsv.rowkey.index=1,0,9 -Dhbase.zookeeper.quorum=hadoop01,hadoop06,hadoop02,hadoop16,hadoop22 -Dimporttsv.avro=false -Dimporttsv.columns=HBASE_ROW_KEY,cf:col -Dimporttsv.bulk.output=/user/hbase/rcc -Dimporttsv.timestamp.index=2 -Dimporttsv.timestamp.format=yyyyMMdd -Dimporttsv.separator=, -Dimporttsv.rowkey.append.str=rk -Dimporttsv.mapper.class=com.lkl.hbase.HFileMapper lklpos_atm_risk_control /user/hive/warehouse/merzx.db/risk_control_consulation/

sudo -u hbase hadoop jar /opt/cloudera/parcels/CDH/lib/hbase/hbase-server-1.0.0-cdh5.6.0.jar   completebulkload /user/hbase/rcc lklpos_atm_risk_control
