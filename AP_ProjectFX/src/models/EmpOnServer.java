package models;

import java.io.*;
import java.net.*;

public class EmpOnServer implements Serializable{
	private static final long serialVersionUID = 1L;
	private String name, host;
	int port0, port1, port2;
	
	
	public void setName(String name){
		this.name = name;
	}
	public String getName(){
		return name;
	}
	
	public void setFirstPort(int port0){
		this.port0 = port0;
	}
	public int getFirstPort(){
		return port0;
	}
	
	public void setSecondPort(int port1){
		this.port1 = port1;
	}
	public int getSecondPort(){
		return port1;
	}
	
	public void setThirdPort(int port2){
		this.port2 = port2;
	}
	public int getThirdPort(){
		return port2;
	}
	

	
	
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		/*try{
			InetAddress temp = InetAddress.getLocalHost();
			this.host = String.valueOf(temp.getHostName());
		}catch(Exception e){
			System.out.println("error: "+e.getMessage());
		}*/
		this.host = host;
		
	}
	public EmpOnServer(){
		this.name = new String();
		this.host = new String();
		this.port0 = 0;
		this.port1 = 0;
		this.port2 = 0;
	}
	
	public EmpOnServer(String name, int port0, int port1, int port2){
		this.name = name;
		this.port0 = port0;
		this.port1 = port1;
		this.port2 = port2;
	}
	
}
