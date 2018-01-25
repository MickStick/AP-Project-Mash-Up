package tools;

import java.io.Serializable;

public class Message implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String client;
	private String action;
	private String username;
	private String password;
	private String message;
	private String query;
	
	public Message(){
		this.client = null;
		this.action = null;
		this.username = null;
		this.password = null;
		this.message = null;
		this.query = null;
	}
	
	public Message(String client, String action, String username, String password){
		this.client = client;
		this.action = action;
		this.username = username;
		this.password = password;
	}
	
	public void setClient(String client){
		this.client = client;
	}
	
	public void setAction(String action){
		this.action = action;
	}
	
	public void setUsername(String username){
		this.username = username;
	}
	
	public void setPassword(String password){
		this.password = password;
	}
	
	public void setMessage(String message){
		this.message = message;
	}
	
	public void setQuery(String query){
		this.query = query;
	}
	
	public String getClient(){
		return  this.client;
	}
	
	public String getAction(){
		return  this.action;
	}
	
	public String getUsername(){
		return this.username;
	}
	
	public String getPassword(){
		return this.password;
	}
	
	public String getMessage(){
		return this.message;
	}
	
	public String getQuery(){
		return this.query;
	}
	
}
