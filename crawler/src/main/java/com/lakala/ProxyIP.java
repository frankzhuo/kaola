package com.lakala;

public class ProxyIP {
	private  String ip="";
	private  int  port =80;
	public ProxyIP(String ip,int  port){
		this.ip=ip;
		this.port=port;
	}
	public ProxyIP(){
		this.ip="";
		this.port=0;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
}
