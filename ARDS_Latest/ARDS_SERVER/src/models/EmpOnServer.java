package models;

import java.io.*;
import java.net.*;

public class EmpOnServer implements Serializable{
	private String name;
	int port0, port1, port2;
	Socket con;
	
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
	
	public void setSocket(Socket con){
		this.con = con;
	}
	public Socket getSocket(){
		return con;
	}
	
	public EmpOnServer(){
		this.name = new String();
		this.port0 = 0;
		this.port1 = 0;
		this.port2 = 0;
		this.con = null;
	}
	
	public EmpOnServer(String name, int port0, int port1, int port2){
		this.name = name;
		this.port0 = port0;
		this.port1 = port1;
		this.port2 = port2;
		this.con = null;
	}

}
