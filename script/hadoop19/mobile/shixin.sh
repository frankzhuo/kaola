http://10.1.80.166:8080/gd?merid=822584055411973&type=1&starttime=2016-01-12&endtime=2016-12-26&page=2&size=100


compName,area,publicYear,publicState,creditNum,compScope,detailURL



#工商总局：守合同重信用企业
one.channels.channeltmp-11.checkpointDir=/es/flume/data/tmpCheckpointDir11
one.channels.channeltmp-11.dataDirs=/es/flume/data/tmpDataTmpDirs11
one.channels.channeltmp-11.type=file
one.channels.channeltmp-11.capacity = 2000000
one.channels.channeltmp-11.transactionCapacity = 13000

one.sources.srctmp-11.type=spooldir
one.sources.srctmp-11.ignorePattern=^$
one.sources.srctmp-11.spoolDir=/data/edata/company/zxy_company/
one.sources.srctmp-11.deletePolicy=NEVER
one.sources.srctmp-11.channels=channeltmp-11
one.sources.srctmp-11.inputCharset=UTF-8
one.sources.srctmp-11.deserializer.maxLineLength=2048000
one.sources.deserializer=LINE

one.sinks.sinktmp-11.type = org.apache.flume.sink.elasticsearch.ElasticSearchSink
one.sinks.sinktmp-11.hostNames = 10.1.80.181:9300,10.1.80.182:9300
one.sinks.sinktmp-11.indexName = company
one.sinks.sinktmp-11.jsonSplitFlag = \\001
one.sinks.sinktmp-11.jsonField = compName,area,publicYear,publicState,creditNum,compScope,detailURL

one.sinks.sinktmp-11.indexType = zxy_company
one.sinks.sinktmp-11.clusterName = edata
one.sinks.sinktmp-11.batchSize = 50
one.sinks.sinktmp-11.serializer = org.apache.flume.sink.elasticsearch.ElasticSearchDynamicSerializer
one.sinks.sinktmp-11.channel = channeltmp-11

10.1.80.60      hadoop01
10.1.80.61      hadoop02
10.1.80.62      hadoop03
10.1.80.63      hadoop04
10.1.80.64      hadoop05
10.1.80.65      hadoop06
10.1.80.66      hadoop07
10.1.80.67      hadoop08
10.1.80.68      hadoop09
10.1.80.69      hadoop10
10.1.80.161     hadoop11
10.1.80.162     hadoop12
10.1.80.163     hadoop13
10.1.80.164     hadoop14
10.1.80.165     hadoop15

10.1.80.171     hadoop16
10.1.80.172     hadoop17
10.1.80.173     hadoop18
10.1.80.174     hadoop19
10.1.80.175     hadoop20
10.1.80.176     hadoop21
10.1.80.177     hadoop22
10.1.80.178     hadoop23

10.1.80.183     hadoop24
10.1.80.184     hadoop25
10.1.80.185     hadoop26
10.1.80.186     hadoop27
10.1.80.187     hadoop28
10.1.80.188     hadoop29
10.1.80.189     hadoop30

10.1.80.190     hadoop31
10.1.80.191     hadoop32
10.1.80.192     hadoop33
10.1.80.193     hadoop34
10.1.80.194     hadoop35
10.1.80.195     hadoop36
10.1.80.196     hadoop37