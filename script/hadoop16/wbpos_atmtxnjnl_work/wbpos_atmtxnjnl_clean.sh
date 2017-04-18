#!/bin/sh

cd /home/hdfs/wbpos_atmtxnjnl_work

sh wbpos.sql
sudo -u hdfs hive -f wbpos_atmtxnjnl_clean.sql

