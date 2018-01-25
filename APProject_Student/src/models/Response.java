package models;

import java.io.File;
import java.io.Serializable;
//Response Object to be sent between clients and server
public class Response implements ResponseMethods, Serializable{
	//Attributes
	private static final long serialVersionUID = 1L;
	private String message;
	private String Action;
	private int Session;
	private int status;
	private String source;
	//Credentials for login 
	private Integer id;
	private String password;
	//Fee Payment information
	private int fee_id;
	private float amount;
	
	private int userID;
	private Student sobj;
	private String user;
	private File fileAttachment;
	//Constructors
	//DEFAULT
	public Response()
	{
		this.message = "";
		this.Action = "";
		this.status = 0;
		this.source = "";
		this.Session = 0;
		this.fee_id = 0;
		this.amount = 0.0f;
		this.id = 0;
		this.password = null;
		
		this.userID = -505;
		this.user = null;
		this.sobj = null;
	}
	//PRIMARY
	public Response(String message,String action,int status,String source,int session,int fee_id,float amount)
	{
		this.message = message;
		this.Action = action;
		this.status = status;
		this.source = source;
		this.Session = session;
		this.fee_id = fee_id;
		this.amount = amount;
	}
	//COPY
	public Response(Response A)
	{
		this.message = A.message;
		this.Action = A.Action;
		this.source = A.source;
		this.status = A.status;
		this.Session = A.Session;
		this.fee_id = A.fee_id;
		this.amount = A.amount;
	}
	
	public int getUserID(){
		return this.userID;
	}
	
	public void setUserID(int userID){
		this.userID = userID;
	}
	
	
	public int getFee_id() {
		return fee_id;
	}
	public void setFee_id(int fee_id) {
		this.fee_id = fee_id;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	//GETTERS AND SETTERS
	public String getMessage() {
		return message;
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
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getSession() {
		return Session;
	}
	public void setSession(int session) {
		Session = session;
	}
	
	public Student getStudentObject() {
		return sobj;
	}

	public void setStudentObj(Student sobj) {
		this.sobj = sobj;
	}
	
	public void setUser(String user){
		this.user = user;
	}
	
	public String getUser(){
		return user;
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
	
	public File getFileAttachment() {
		return fileAttachment;
	}
	public void setFileAttachment(File fileAttachment) {
		this.fileAttachment = fileAttachment;
	}
	
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
