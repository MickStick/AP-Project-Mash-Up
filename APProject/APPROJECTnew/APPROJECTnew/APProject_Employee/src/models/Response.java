package models;

import java.io.Serializable;
//Response Object to be sent between clients and server
public class Response implements ResponseMethods,Serializable{
	//Attributes
	private static final long serialVersionUID = 1L;
	private String message;
	private String Action;
	private int status;
	private String source;
	private Integer Id;
	private String password;
	
	private Employee eobj;
	private int userID;
	private String user;
	private int Session;
	//Constructors
	//DEFAULT
	public Response()
	{
		this.message = "";
		this.Action = "";
		this.status = 0;
		this.source = "";
		this.Id = 0;
		this.password = null;
		
		this.userID = 0;
		this.Session = 0;
		this.eobj = null;
		this.user = null;
	}
	//PRIMARY
	public Response(String message,String action,int status,String source)
	{
		this.message = message;
		this.Action = action;
		this.status = status;
		this.source = source;
	}
	//COPY
	public Response(Response A)
	{
		this.message = A.message;
		this.Action = A.Action;
		this.source = A.source;
		this.status = A.status;
		this.Id = A.Id;
		this.password = A.password;
	}
	//GETTERS AND SETTERS
	
	public int getUserID(){
		return this.userID;
	}
	
	public void setUserID(int userID){
		this.userID = userID;
	}
	
	public String getMessage() {
		return message;
	}
	public Integer getId() {
		return Id;
	}
	public void setId(Integer studentID) {
		Id = studentID;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getAction() {
		return Action;
	}
	public void setAction(String action) {
		Action = action;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getSource()
	{
		return this.source;
	}
	
	public Employee getEmployeeObject() {
		return eobj;
	}

	public void setEmployeeObj(Employee eobj) {
		this.eobj = eobj;
	}
	
	public void setUser(String user){
		this.user = user;
	}
	
	public String getUser(){
		return user;
	}
	
	public int getSession() {
		return Session;
	}
	public void setSession(int session) {
		Session = session;
	}
	
	//METHODS FROM INTERFACE
	@Override
	public Response getResponseObject() {
		return this;
	}

	@Override
	public String getResponseAction() {
		String action;
		action = this.getAction();
		return action;
		
	}

	@Override
	public String getResponseMessage() {
		String message;
		message = this.getMessage();
		return message;
		
	}

	@Override
	public String getRepsonseSource() {
		String source;
		source = this.getSource();
		return source;
		
	}
	
	@Override
	public String toString()
	{
		return new String("Response Source: " +
				this.source + "\n" + "Response status: " +
				this.status + "\n" + "Reponse Message: " + 
				this.message + "\n" + "Response action: " + 
				this.Action + "\n"
				);
	}

}
