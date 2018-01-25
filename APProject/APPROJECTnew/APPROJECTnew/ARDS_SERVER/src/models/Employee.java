package models;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.math.BigInteger;
import java.net.Socket;
import java.net.*;
import java.rmi.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.swing.JOptionPane;

public class Employee implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int empID;
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNumber;
	private String query;
	private double accountBalance;
	private String password;
	private String dob;
	private String address;
	public Student sobj;
	//============================//
	static Socket empsoc;
	static ServerSocket empssoc;  //So client can connect to more than one students at a time
    static ObjectOutputStream output;
    static ObjectInputStream input;
    static Response receivedMessage;
    
	public Employee(){
		this.empID = 0;
		this.firstName = null;
		this.lastName = null;
		this.email = null;
		this.phoneNumber = null;
		this.query = null;
		this.accountBalance = 0;
		this.password = null;
		this.dob = null;
		this.address = null;
		this.sobj = null;
		//======================//
		//this.con = DBConnectorFactory.getDatabaseConnection();
	}

	public int getEmpID(){return empID;}
	public void setEmpID(int eID){this.empID = eID;}
	
	public String getFirstName(){return firstName;}
	public void setFirstName(String firstName){this.firstName = firstName;}

	public String getLastName(){return lastName;}
	public void setLastName(String lastName){this.lastName = lastName;}

	public String getEmail(){return email;}
	public void setEmail(String email){this.email = email;}

	public String getPhoneNumber(){return phoneNumber;}
	public void setPhoneNumber(String phoneNumber){this.phoneNumber = phoneNumber;}

	public String getQuery(){return query;}
	public void setQuery(String query){this.query = query;}

	public double getAccountBalance(){return accountBalance;}
	public void setAccountBalance(double accountBalance){this.accountBalance = accountBalance;}
	
	public String getPassword(){return password;}
	public void setPassword(String password){this.password = password;}

	public String getDob(){return dob;}
	public void setDob(String dob){this.dob = dob;}

	public String getAddress(){return address;}
	public void setAddress(String address){this.address = address;}

	//...........................................................................................//
	//Change all of this to work for the client
	
}
