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
	public static Employee empLogin(String action, int empID, String password){
		Employee emp = new Employee();
		try{
			Response message = new Response();
			//Response receivedMessage;
			//Connect to server class
			empsoc = new Socket("localhost",1234);
			//empssoc = new ServerSocket(4413, 3);
			//Send message to server
			output = new ObjectOutputStream(empsoc.getOutputStream());
			message.setAction("Login");
			message.setMessage("need to login");
			message.setSource("Employee");
			message.setUser("EMPLOYEE");
			message.setUserID(empID);
			message.setPassword(getSHA_256Hash(password));
			output.writeObject(message);
			output.flush();
				input = new ObjectInputStream(empsoc.getInputStream());
				receivedMessage = (Response) input.readObject();
				emp = receivedMessage.getEmployeeObject();
				
		}catch(UnknownHostException e){
			JOptionPane.showMessageDialog(null, e.getMessage()); 
		}
		catch(ClassNotFoundException e){                                                                                                                                                                                                                                                                                     
			JOptionPane.showMessageDialog(null, e.getMessage()); 
		}catch(IOException e){
			JOptionPane.showMessageDialog(null, e.getMessage()); 
		}finally{
			try{
				input.close();
				output.close();
				//client.close();
			}catch(IOException e){
				JOptionPane.showMessageDialog(null, e.getMessage()); 
			}
		}
		return emp;
	}
	
	//=======================================================================//
	private static String getSHA_256Hash(String password){
		String hash = null;
		try{
			MessageDigest m = MessageDigest.getInstance("SHA-256");
			m.reset();
			m.update(password.getBytes());
			byte[] digest = m.digest();
			BigInteger bigInt = new BigInteger(1,digest);
			hash = bigInt.toString(16);
			// Now we need to zero pad it if you actually want the full 32 chars.
			while(hash.length() <  64){
			  hash = "0"+hash;
			}
		}catch(NoSuchAlgorithmException e){
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return hash;
	}
}
