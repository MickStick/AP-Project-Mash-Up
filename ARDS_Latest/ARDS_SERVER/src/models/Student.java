package models;

import java.io.Serializable;

public class Student implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer studentID;
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNumber;
	private String query;
	private float accountBalance;
	private String password;
	private String dob;
	private String address;
	
	private String clearanceStatus;
	//============================//
    
	public Student(){
		this.studentID = 0;
		this.firstName = null;
		this.lastName = null;
		this.email = null;
		this.phoneNumber = null;
		this.query = null;
		this.accountBalance = 0;
		this.password = null;
		this.dob = null;
		this.address = null;
		
		clearanceStatus = null;
		//======================//
		//this.con = DBConnectorFactory.getDatabaseConnection();
	}

	public Integer getStudentID() {
		return studentID;
	}

	public void setStudentID(Integer studID) {
		this.studentID = studID;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public float getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(float accountBalance) {
		this.accountBalance = accountBalance;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
	
	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	
	public void setClearanceStatus(String clearanceStatus){
		this.clearanceStatus = clearanceStatus;
	}
	public String getClearanceStatus(){
		return clearanceStatus;
	}

	//...........................................................................................//
	
}


