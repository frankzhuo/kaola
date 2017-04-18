package com.chanct.idap.es.dao.common;

import java.net.InetAddress;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;



/**
 * 
 * @Description	获取Esclient实例工厂类
 * @ClassName	EsClientFactory
 * @Date		2016年11月16日 上午11:38:14
 * @Author		zhangjunsheng
 */
public class EsClientFactory {

	private static TransportClient client;
	private static boolean sniff = true;
	private static int esPort = 9300;

	/**
	 * 
	 * @Title: createClient 
	 * @Description: 创建 EsClient
	 * @return TransportClient 
	 * @throws
	 */
	private static TransportClient createClient() {
		// 对象实例化时与否判断
		if (client == null) {
			// 同步代码块
			synchronized (EsClientFactory.class) {
				// 未初始化，则初始instance变量
				if (client == null) {
					try {
						Settings settings = Settings.settingsBuilder()
								.put("cluster.name", ConFigFactory.getDirectory(ConfigConstant.CLUSTER_NAME))
								.put("client.transport.sniff", sniff).build();
						client = TransportClient.builder().settings(settings).build();
						client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(ConFigFactory.getDirectory(ConfigConstant.SOCKET_IP)), esPort));
					} catch (Exception e) {
						e.printStackTrace();
					}
					return client;
				}
			}
		}
		return client;
	}
	/**
	 * @Title: getTransportClient 
	 * @Description: 获取transport实例
	 * @return TransportClient 
	 * @throws
	 */
	public static TransportClient getTransportClient() {
		return createClient();
		}
	
}
