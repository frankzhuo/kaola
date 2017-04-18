package com.kaola.edata.common;

import java.io.IOException;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

public class GlobalConstantFileServer {


	private static Logger logger = Logger.getLogger(GlobalConstantFileServer.class);



	/**
	 * 连接FTP服务器，返回连接的对象
	 * @param hostname
	 * @param port
	 * @param userName
	 * @param password
	 * @return
	 * @throws SocketException
	 * @throws IOException
	 */
	public static FTPClient connectServer(String hostname, int port, String userName, String password)
			throws SocketException, IOException {
		FTPClient ftpClient = new FTPClient();
		ftpClient.setControlEncoding("utf-8"); // 文件名乱码,默认ISO8859-1，不支持中文
		ftpClient.setDefaultPort(port);
		ftpClient.connect(hostname);
		boolean result = ftpClient.login(userName, password);
		int reply = ftpClient.getReplyCode();
		if (!FTPReply.isPositiveCompletion(reply)) {
			try {
				ftpClient.disconnect();
			} catch (IOException e) {
				logger.error(e);
			}
			return null;
		}
		if (result) {
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			return ftpClient;
		} else {
			return null;
		}
	}



	/**
	 * @see 指定服务器地址获取文件，返回文件流，不存在则返回为NULL
	 * @param hostname
	 * @param sourcePath
	 * @return
	 */
	public static FTPClient getFTPClient(String hostname, String sourcePath) {
		logger.info("从文件服务器地址 " + hostname + " 上获取路径 " + sourcePath);
		if (hostname == null || "".equals(hostname)) {
			logger.info("服务器地址为空!");
			return null;
		}
					
		FTPClient ftpClient = null;
		try {
//			String hostName = PropertyReader.getPropertyValueByKey("ftp_host");
			int port = 21;
			String userName = PropertyReader.getPropertyValueByKey("ftp_user");
			String password = PropertyReader.getPropertyValueByKey("ftp_password");
			ftpClient = GlobalConstantFileServer.connectServer(hostname, port,
					userName, password);
			logger.info("下载文件默认目录：" + ftpClient.printWorkingDirectory());
			if (sourcePath != null && !"".equals(sourcePath)) {
				boolean tmp = ftpClient.changeWorkingDirectory(sourcePath);
				if (!tmp) {
					logger.info("切换目录到" + sourcePath + "失败");
					return null;
				}
				logger.info("切换目录到：" + ftpClient.printWorkingDirectory());
			}
			return ftpClient;
		} catch (SocketException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
			
		
		return null;
	}


}
