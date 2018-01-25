package models;

import java.io.*;

public class StudOnServer implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String name;
	String message;
	
	public StudOnServer(){
		this.name = null;
		this.message = null;
	}
	
	public StudOnServer(String name, String message){
		this.name = name;
		this.message = message;
	}
	
	public void setName(String name){
		this.name = name;
	}
	public String getName(){
		return name;
	}
	
	public void setMessage(String message){
		this.message = message;
	}
	public String getMessage(){
		return message;
	}

}
