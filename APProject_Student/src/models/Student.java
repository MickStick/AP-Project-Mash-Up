package models;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.rmi.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import controller.Controller;

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
	private float semCost;
	private static String host = "Mik";//MIK
	
	private String clearanceStatus;
	//============================//
	static Socket client;
    static ObjectOutputStream output;
    static ObjectInputStream input;
    static Response receivedMessage;
    
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
		this.semCost = 0;
		
		this.clearanceStatus = null;
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
	
	
public float getSemCost() {
		return semCost;
	}

	public void setSemCost(float semCost) {
		this.semCost = semCost;
	}

	//...........................................................................................//
	public static Student studentLogin(String action, int studentID, String password){
		Student stud = null;
		try{
			Response message = new Response();
			//Connect to server class
			client = new Socket();
			//client = new Socket(host,1234);//localhost
			client.connect(new InetSocketAddress(host,1234), 3000);
			System.out.println("Connected to : " + client.getInetAddress().getHostName());
			//Send message to server
			output = new ObjectOutputStream(client.getOutputStream());
			message.setUser("STUDENT");
			message.setAction("Login");
			message.setMessage("need to login");
			message.setSource(client.getInetAddress().getHostName());
			message.setUserID(studentID);
			message.setPassword(getSHA_256Hash(password));
			output.writeObject(message);
			output.flush();
			//output.close();
			//Reading message from server
					input = new ObjectInputStream(client.getInputStream());
					receivedMessage = (Response)input.readObject();
					stud = new Student();
					stud = receivedMessage.getStudentObject();
					//System.out.println("Response source: "+ receivedMessage.getRepsonseSource());
					//System.out.println("" + receivedMessage.getMessage());
		}catch(UnknownHostException e){
			Controller.logger("(UnknownHostException): "+e.getMessage()+" - "+trace(e.getStackTrace()));
			JOptionPane.showMessageDialog(null, e.getMessage()); 
		}
		catch(ClassNotFoundException e){
			Controller.logger("(ClassNotFoundException): "+e.getMessage()+" - "+trace(e.getStackTrace()));
			JOptionPane.showMessageDialog(null, e.getMessage()); 
		}catch(IOException e){
			Controller.logger("(IOException): "+e.getMessage()+" - "+trace(e.getStackTrace()));
			JOptionPane.showMessageDialog(null, "I/O Excetion: "+e.getMessage()); 
		}finally{
			try{
				try{
					input.close();
					output.close();
					client.close();
				}catch(NullPointerException npe){
					Controller.logger("(NullPointertException): "+npe.getMessage()+" - "+trace(npe.getStackTrace()));
					System.out.println("error: "+npe.getMessage());
				}
				
			}catch(IOException e){
				Controller.logger("(IOException): "+e.getMessage()+" - "+trace(e.getStackTrace()));
				JOptionPane.showMessageDialog(null, e.getMessage()); 
			}
		}
		return stud;
	}
	
	public static int studentLogout(){
		int sessionID = 404;
		if(receivedMessage.getSession() != 0){
			try{
				//client = new Socket(host,1234);//InetAddress.getLocalHost()
				client = new Socket();
				client.connect(new InetSocketAddress(host,1234), 3000);
				output = new ObjectOutputStream(client.getOutputStream());
				//JOptionPane.showMessageDialog(null, "Current SessionID: "+receivedMessage.getSession());
				receivedMessage.setAction("Logout");
				output.writeObject(receivedMessage);
				output.flush();
					input = new ObjectInputStream(client.getInputStream());
					receivedMessage = (Response)input.readObject();
					sessionID = receivedMessage.getSession();
			}catch(IOException ioe){
				Controller.logger("(IOException): "+ioe.getMessage()+" - "+trace(ioe.getStackTrace()));
				JOptionPane.showMessageDialog(null, ioe.getMessage());
			}catch(ClassNotFoundException cnfe){
				Controller.logger("(ClassNotFoundException): "+cnfe.getMessage()+" - "+trace(cnfe.getStackTrace()));
				JOptionPane.showMessageDialog(null, cnfe.getMessage());
			}finally{
				try{
					input.close();
					output.close();
					client.close();
				}catch(IOException ioe){
					Controller.logger("(IOException): "+ioe.getMessage()+" - "+trace(ioe.getStackTrace()));
					JOptionPane.showMessageDialog(null, ioe.getMessage());
				}
			}
		}
		return sessionID;
	}
	
	public static int sessionStatus(){
		//System.out.println("Session ID: "+receivedMessage.getSession());
		return receivedMessage.getSession();
	}
	
	@SuppressWarnings("unchecked")
	public static ArrayList<EmpOnServer> employeesOnline(){
		ArrayList<EmpOnServer> online = null;
		try{
			//client = new Socket(host,1234);
			client = new Socket();
			client.connect(new InetSocketAddress(host,1234), 3000);
			output = new ObjectOutputStream(client.getOutputStream());
			receivedMessage.setAction("Online");
			receivedMessage.setMessage(null);
			output.writeObject(receivedMessage);
			output.flush();
				input = new ObjectInputStream(client.getInputStream());
				online = new ArrayList<EmpOnServer>();
				online = (ArrayList<EmpOnServer>)input.readObject();
				//if(receivedMessage.getMessage() != null)
					//JOptionPane.showMessageDialog(null, receivedMessage.getMessage());
		}catch(IOException ioe){
			Controller.logger("(IOException): "+ioe.getMessage()+" - "+trace(ioe.getStackTrace()));
			JOptionPane.showMessageDialog(null, "IO Exception: "+ioe.getMessage());
		}catch(ClassNotFoundException cnfe){
			Controller.logger("(ClassNotFoundException): "+cnfe.getMessage()+" - "+trace(cnfe.getStackTrace()));
			JOptionPane.showMessageDialog(null, "Class NotFoundException: "+cnfe.getMessage());
		}finally{
			try{
				input.close();
				output.close();
				client.close();
			}catch(IOException ioe){
				Controller.logger("(IOException): "+ioe.getMessage()+" - "+trace(ioe.getStackTrace()));
				JOptionPane.showMessageDialog(null, "couldn't close stuff");
			}
		}
		return online;
	}
	
	@SuppressWarnings("unchecked")
	public static ArrayList<String> feesPaid(int id){
		ArrayList<String> fees = null;
		try{
			//client = new Socket(host,1234);
			client = new Socket();
			client.connect(new InetSocketAddress(host,1234), 3000);
			output = new ObjectOutputStream(client.getOutputStream());
			receivedMessage.setAction("Refund");
			output.writeObject(receivedMessage);
			output.flush();
				input = new ObjectInputStream(client.getInputStream());
				fees = (ArrayList<String>)input.readObject();
		}catch(IOException ioe){
			Controller.logger("(IOException): "+ioe.getMessage()+" - "+trace(ioe.getStackTrace()));
			JOptionPane.showMessageDialog(null, ioe.getMessage());
		}catch(ClassNotFoundException cnfe){
			Controller.logger("(ClassNotFoundException): "+cnfe.getMessage()+" - "+trace(cnfe.getStackTrace()));
			JOptionPane.showMessageDialog(null, cnfe.getMessage());
		}finally{
			try{
				input.close();
				output.close();
				client.close();
			}catch(IOException ioe){
				Controller.logger("(IOException): "+ioe.getMessage()+" - "+trace(ioe.getStackTrace()));
				JOptionPane.showMessageDialog(null, ioe.getMessage());
			}
		}
		return fees;
	}
	
	public static void sendEnquiry(String enquiry, File fileAttachment){
		try{
			//client = new Socket(host,1234);
			client = new Socket();
			client.connect(new InetSocketAddress(host,1234), 3000);
			output = new ObjectOutputStream(client.getOutputStream());
			receivedMessage.setAction("SaveEnquiry");
			receivedMessage.setMessage(enquiry);
			receivedMessage.setFileAttachment(fileAttachment);
			output.writeObject(receivedMessage);
			output.flush();
				input = new ObjectInputStream(client.getInputStream());
				receivedMessage = (Response)input.readObject();
		}catch(IOException ioe){
			Controller.logger("(IOException): "+ioe.getMessage()+" - "+trace(ioe.getStackTrace()));
			JOptionPane.showMessageDialog(null, ioe.getMessage());
		}catch(ClassNotFoundException cnfe){
			Controller.logger("(ClassNotFoundException): "+cnfe.getMessage()+" - "+trace(cnfe.getStackTrace()));
			JOptionPane.showMessageDialog(null, cnfe.getMessage());
		}finally{
			try{
				input.close();
				output.close();
				client.close();
			}catch(IOException ioe){
				Controller.logger("(IOException): "+ioe.getMessage()+" - "+trace(ioe.getStackTrace()));
				JOptionPane.showMessageDialog(null, ioe.getMessage());
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public static ArrayList<String> previousEnqs(){
		ArrayList<String> enqs = null;
		try{
			//client = new Socket(host,1234);
			client = new Socket();
			client.connect(new InetSocketAddress(host,1234), 3000);
			output = new ObjectOutputStream(client.getOutputStream());
			receivedMessage.setAction("PrevEnquiries");
			output.writeObject(receivedMessage);
			output.flush();
				input = new ObjectInputStream(client.getInputStream());
				enqs = (ArrayList<String>)input.readObject();
		}catch(IOException ioe){
			Controller.logger("(IOException): "+ioe.getMessage()+" - "+trace(ioe.getStackTrace()));
			JOptionPane.showMessageDialog(null, ioe.getMessage());
		}catch(ClassNotFoundException cnfe){
			Controller.logger("(ClassNotFoundException): "+cnfe.getMessage()+" - "+trace(cnfe.getStackTrace()));
			JOptionPane.showMessageDialog(null, cnfe.getMessage());
		}finally{
			try{
				input.close();
				output.close();
				client.close();
			}catch(IOException ioe){
				Controller.logger("(IOException): "+ioe.getMessage()+" - "+trace(ioe.getStackTrace()));
				JOptionPane.showMessageDialog(null, ioe.getMessage());
			}
		}
		return enqs;
	}
	
	@SuppressWarnings("unchecked")
	public static ArrayList<String> moniesOwed(){
		ArrayList<String> monies = null;
		try{
			//client = new Socket(host,1234);
			client = new Socket();
			client.connect(new InetSocketAddress(host,1234), 3000);
			output = new ObjectOutputStream(client.getOutputStream());
			receivedMessage.setAction("moniesOwed");
			output.writeObject(receivedMessage);
			output.flush();
				input = new ObjectInputStream(client.getInputStream());
				monies = (ArrayList<String>)input.readObject();
		}catch(IOException ioe){
			Controller.logger("(IOException): "+ioe.getMessage()+" - "+trace(ioe.getStackTrace()));
			JOptionPane.showMessageDialog(null, ioe.getMessage());
		}catch(ClassNotFoundException cnfe){
			Controller.logger("(ClassNotFoundException): "+cnfe.getMessage()+" - "+trace(cnfe.getStackTrace()));
			JOptionPane.showMessageDialog(null, cnfe.getMessage());
		}finally{
			try{
				input.close();
				output.close();
				client.close();
			}catch(IOException ioe){
				Controller.logger("(IOException): "+ioe.getMessage()+" - "+trace(ioe.getStackTrace()));
				JOptionPane.showMessageDialog(null, ioe.getMessage());
			}
		}
		return monies;
	}
	
	//=======================================================================//
	private static String trace(StackTraceElement[] ste){
		String message = "";
		message += ste[0].getFileName()+" -> "+ste[0].getClassName()+" -> "+ste[0].getMethodName()+" -> "+ste[0].getLineNumber();
		return message;
	}
	
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


