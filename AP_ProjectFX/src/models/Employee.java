package models;

import java.io.File;
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
import java.util.ArrayList;

import javax.swing.JOptionPane;

import view.DashBoard;

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
	private Student sobj;
	private EmpOnServer eosobj;
	//============================//
	static Socket empsoc;
	static ServerSocket empssoc;  //So client can connect to more than one students at a time
    static ObjectOutputStream output;
    static ObjectInputStream input;
    static Response receivedMessage;
    private static String host = "Mik";
    private static int timeout = 100;
    
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
		this.eosobj = null;
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

	public Student getStudent(){
		return sobj;
	}
	public void setStudent(Student s){
		sobj = s;
	}
	
	public EmpOnServer getEOS(){
		return eosobj;
	}
	public void setEOS(EmpOnServer eos){
		eosobj = eos;
	}

	//...........................................................................................//
	//Change all of this to work for the client
	public static Employee empLogin(String action, int empID, String password){
		Employee emp = new Employee();
		try{
			Response message = new Response();
			empsoc = new Socket();
			empsoc.connect(new InetSocketAddress(host,1234),timeout);
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
			DashBoard.logger("(UnknowHostException) : " + e.getMessage() + " " + trace(e.getStackTrace()));
			JOptionPane.showMessageDialog(null, e.getMessage()); 
		}
		catch(ClassNotFoundException e){ 
			DashBoard.logger("(ClassNotFoundException) : " + e.getMessage() + " " + trace(e.getStackTrace()));
			JOptionPane.showMessageDialog(null, e.getMessage()); 
		}catch(IOException e){
			DashBoard.logger("(IOException) : " + e.getMessage() + " " + trace(e.getStackTrace()));
			JOptionPane.showMessageDialog(null, e.getMessage()); 
		}finally{

			try{


				output.close();
				//client.close();
			}catch(IOException e){
				DashBoard.logger("(IOException) : " + e.getMessage() + " " + trace(e.getStackTrace()));
				JOptionPane.showMessageDialog(null, e.getMessage()); 
			}
		}
		return emp;
	}
	
	public static void sendReply(int ticket, String enq, File file){
		Enquiry enquiry = new Enquiry();
		/*Response message = new Response();
		message.setSession(receivedMessage.getSession());
		message.setUserID(receivedMessage.getUserID());*/
		enquiry.setTicketID(ticket);
		enquiry.setResponse(enq);
		enquiry.setAttach(file);
		try{
			//Response message = new Response();
			//Response receivedMessage;
			//Connect to server class
			empsoc = new Socket();
			empsoc.connect(new InetSocketAddress(host,1234),timeout);
			//empssoc = new ServerSocket(4413, 3);
			//Send message to server
			output = new ObjectOutputStream(empsoc.getOutputStream());
			receivedMessage.setAction("saveReply");
			receivedMessage.setMessage("sending a Response");
			receivedMessage.setSource("Employee");
			receivedMessage.setUser("EMPLOYEE");
			receivedMessage.setEnquiryObj(enquiry);
			output.writeObject(receivedMessage);
			output.flush();
			input = new ObjectInputStream(empsoc.getInputStream());
			receivedMessage = (Response) input.readObject();
			JOptionPane.showMessageDialog(null,receivedMessage.getMessage(), "Message From  Server",3);

		}catch(UnknownHostException e){
			DashBoard.logger("(UnknowHostException) : " + e.getMessage() + " " + trace(e.getStackTrace()));
			JOptionPane.showMessageDialog(null, e.getMessage()); 
		}
		catch(ClassNotFoundException e){ 
			DashBoard.logger("(ClassNotFoundException) : " + e.getMessage() + " " + trace(e.getStackTrace()));
			JOptionPane.showMessageDialog(null, e.getMessage()); 
		}catch(IOException e){
			DashBoard.logger("(IOException) : " + e.getMessage() + " " + trace(e.getStackTrace()));
			JOptionPane.showMessageDialog(null, e.getMessage()); 
		}finally{

			try{
				input.close();
				output.close();
				//client.close();
			}catch(IOException e){
				DashBoard.logger("(IOException) : " + e.getMessage() + " " + trace(e.getStackTrace()));
				JOptionPane.showMessageDialog(null, e.getMessage()); 
			}
		}
	}
	
	public static void updateStudent(int id, String fname, String lname, String email, float bal, String num, String clr, float sc){
		Student stud = new Student();
		stud.setStudentID(id);
		stud.setFirstName(fname);
		stud.setLastName(lname);
		stud.setEmail(email);
		stud.setAccountBalance(bal);
		stud.setPhoneNumber(num);
		stud.setClearanceStatus(clr);
		stud.setSemCost(sc);
		try{
			//Response message = new Response();
			//Response receivedMessage;
			//Connect to server class
			empsoc = new Socket();
			empsoc.connect(new InetSocketAddress(host,1234),timeout);
			//empssoc = new ServerSocket(4413, 3);
			//Send message to server
			output = new ObjectOutputStream(empsoc.getOutputStream());
			receivedMessage.setAction("updateStudent");
			receivedMessage.setMessage("updating a student");
			receivedMessage.setSource("Employee");
			receivedMessage.setUser("EMPLOYEE");
			output.writeObject(receivedMessage);
			output.flush();
			input = new ObjectInputStream(empsoc.getInputStream());
			receivedMessage = (Response) input.readObject();
			JOptionPane.showMessageDialog(null, receivedMessage.getMessage(),"Message From Server", 3);

		}catch(UnknownHostException e){
			DashBoard.logger("(UnknowHostException) : " + e.getMessage() + " " + trace(e.getStackTrace()));
			JOptionPane.showMessageDialog(null, e.getMessage()); 
		}
		catch(ClassNotFoundException e){ 
			DashBoard.logger("(ClassNotFoundException) : " + e.getMessage() + " " + trace(e.getStackTrace()));
			JOptionPane.showMessageDialog(null, e.getMessage()); 
		}catch(IOException e){
			DashBoard.logger("(IOException) : " + e.getMessage() + " " + trace(e.getStackTrace()));
			JOptionPane.showMessageDialog(null, e.getMessage()); 
		}finally{

			try{
				input.close();
				output.close();
				//client.close();
			}catch(IOException e){
				DashBoard.logger("(IOException) : " + e.getMessage() + " " + trace(e.getStackTrace()));
				JOptionPane.showMessageDialog(null, e.getMessage()); 
			}
		}
	}
	
	public static void refundFee(int id){
		Student stud = new Student();
		stud.setFeeID(id);
		
		try{
			//Response message = new Response();
			//Response receivedMessage;
			//Connect to server class
			empsoc = new Socket();
			empsoc.connect(new InetSocketAddress(host,1234),timeout);
			//empssoc = new ServerSocket(4413, 3);
			//Send message to server
			output = new ObjectOutputStream(empsoc.getOutputStream());
			receivedMessage.setAction("Refund");
			receivedMessage.setMessage("refund student fee");
			receivedMessage.setSource("Employee");
			receivedMessage.setUser("EMPLOYEE");
			receivedMessage.setStudentObj(stud);
			output.writeObject(receivedMessage);
			output.flush();
			input = new ObjectInputStream(empsoc.getInputStream());
			receivedMessage = (Response) input.readObject();
			JOptionPane.showMessageDialog(null,receivedMessage.getMessage(),"Message From Server",3);

		}catch(UnknownHostException e){
			DashBoard.logger("(UnknowHostException) : " + e.getMessage() + " " + trace(e.getStackTrace()));
			JOptionPane.showMessageDialog(null, e.getMessage()); 
		}
		catch(ClassNotFoundException e){ 
			DashBoard.logger("(ClassNotFoundException) : " + e.getMessage() + " " + trace(e.getStackTrace()));
			JOptionPane.showMessageDialog(null, e.getMessage()); 
		}catch(IOException e){
			DashBoard.logger("(IOException) : " + e.getMessage() + " " + trace(e.getStackTrace()));
			JOptionPane.showMessageDialog(null, e.getMessage()); 
		}finally{

			try{
				input.close();
				output.close();
				//client.close();
			}catch(IOException e){
				DashBoard.logger("(IOException) : " + e.getMessage() + " " + trace(e.getStackTrace()));
				JOptionPane.showMessageDialog(null, e.getMessage()); 
			}
		}
	}

	public static Student studentLoad(int studID){
		Student stud = new Student();
		try{
			//Response message = new Response();
			//Response receivedMessage;
			//Connect to server class
			empsoc = new Socket();
			empsoc.connect(new InetSocketAddress(host,1234),timeout);
			//empssoc = new ServerSocket(4413, 3);
			//Send message to server
			output = new ObjectOutputStream(empsoc.getOutputStream());
			receivedMessage.setAction("Students");
			receivedMessage.setMessage("get a Student");
			receivedMessage.setSource("Employee");
			receivedMessage.setUser("EMPLOYEE");
			receivedMessage.setUserID(studID);
			output.writeObject(receivedMessage);
			output.flush();
			input = new ObjectInputStream(empsoc.getInputStream());
			receivedMessage = (Response) input.readObject();
			stud = receivedMessage.getStudentObject();

		}catch(UnknownHostException e){
			DashBoard.logger("(UnknowHostException) : " + e.getMessage() + " " + trace(e.getStackTrace()));
			JOptionPane.showMessageDialog(null, e.getMessage()); 
		}
		catch(ClassNotFoundException e){ 
			DashBoard.logger("(ClassNotFoundException) : " + e.getMessage() + " " + trace(e.getStackTrace()));
			JOptionPane.showMessageDialog(null, e.getMessage()); 
		}catch(IOException e){
			DashBoard.logger("(IOException) : " + e.getMessage() + " " + trace(e.getStackTrace()));
			JOptionPane.showMessageDialog(null, e.getMessage()); 
		}finally{

			try{
				input.close();
				output.close();
				//client.close();
			}catch(IOException e){
				DashBoard.logger("(IOException) : " + e.getMessage() + " " + trace(e.getStackTrace()));
				JOptionPane.showMessageDialog(null, e.getMessage()); 
			}
		}
		return stud;
	}
	
	public static File getAttachment(Enquiry enq){
		Enquiry enquiry = enq;
		try{
			//Response message = new Response();
			//Response receivedMessage;
			//Connect to server class
			empsoc = new Socket();
			empsoc.connect(new InetSocketAddress(host,1234),timeout);
			//empssoc = new ServerSocket(4413, 3);
			//Send message to server
			output = new ObjectOutputStream(empsoc.getOutputStream());
			receivedMessage.setAction("get File");
			receivedMessage.setMessage("getting a file");
			receivedMessage.setSource("Employee");
			receivedMessage.setUser("EMPLOYEE");
			receivedMessage.setEnquiryObj(enquiry);;
			output.writeObject(receivedMessage);
			output.flush();
			input = new ObjectInputStream(empsoc.getInputStream());
			receivedMessage = (Response) input.readObject();

		}catch(UnknownHostException e){
			DashBoard.logger("(UnknowHostException) : " + e.getMessage() + " " + trace(e.getStackTrace()));
			JOptionPane.showMessageDialog(null, e.getMessage()); 
		}
		catch(ClassNotFoundException e){ 
			DashBoard.logger("(ClassNotFoundException) : " + e.getMessage() + " " + trace(e.getStackTrace()));
			JOptionPane.showMessageDialog(null, e.getMessage()); 
		}catch(IOException e){
			DashBoard.logger("(IOException) : " + e.getMessage() + " " + trace(e.getStackTrace()));
			JOptionPane.showMessageDialog(null, e.getMessage()); 
		}finally{

			try{
				input.close();
				output.close();
				//client.close();
			}catch(IOException e){
				DashBoard.logger("(IOException) : " + e.getMessage() + " " + trace(e.getStackTrace()));
				JOptionPane.showMessageDialog(null, e.getMessage()); 
			}
		}
		return receivedMessage.getEnquiryObject().getAttach();
	}

	public static ArrayList<Enquiry> getEnquiries(String action, int empID){
		ArrayList<Enquiry> enq = new ArrayList<Enquiry>();
		try{

			//Response receivedMessage;
			//Connect to server class
			empsoc = new Socket();
			empsoc.connect(new InetSocketAddress(host,1234),timeout);
			//empssoc = new ServerSocket(4413, 3);
			//Send message to server
			output = new ObjectOutputStream(empsoc.getOutputStream());
			receivedMessage.setAction("Enquiries");
			receivedMessage.setMessage("need to view enquiries");
			receivedMessage.setSource("Employee");
			receivedMessage.setUser("EMPLOYEE");
			receivedMessage.setUserID(empID);
			output.writeObject(receivedMessage);
			output.flush();
			input = new ObjectInputStream(empsoc.getInputStream());
			receivedMessage = (Response) input.readObject();
			enq = receivedMessage.getEnquiries();
			return receivedMessage.getEnquiries();

		}catch(UnknownHostException e){
			DashBoard.logger("(UnknowHostException) : " + e.getMessage() + " " + trace(e.getStackTrace()));
			JOptionPane.showMessageDialog(null, e.getMessage()); 
		}
		catch(ClassNotFoundException e){ 
			DashBoard.logger("(ClassNotFoundException) : " + e.getMessage() + " " + trace(e.getStackTrace()));
			JOptionPane.showMessageDialog(null, e.getMessage()); 
		}catch(IOException e){
			DashBoard.logger("(IOException) : " + e.getMessage() + " " + trace(e.getStackTrace()));
			JOptionPane.showMessageDialog(null, e.getMessage()); 
		}finally{

			try{
				input.close();
				output.close();
				//client.close();
			}catch(IOException e){
				DashBoard.logger("(IOException) : " + e.getMessage() + " " + trace(e.getStackTrace()));
				JOptionPane.showMessageDialog(null, e.getMessage()); 
			}
		}

		return enq;
	}

	public static void goOnline(){
		try{
			//Response message = new Response();
			//Response receivedMessage;
			//Connect to server class
			empsoc = new Socket();
			empsoc.connect(new InetSocketAddress(host,1234),timeout);
			String host = InetAddress.getLocalHost().getHostName();
			//empssoc = new ServerSocket(4413, 3);
			//Send message to server
			output = new ObjectOutputStream(empsoc.getOutputStream());
			receivedMessage.setAction("goOnline");
			receivedMessage.setMessage(host);
			receivedMessage.setSource("Employee");
			receivedMessage.setUser("EMPLOYEE");
			output.writeObject(receivedMessage);
			output.flush();
			input = new ObjectInputStream(empsoc.getInputStream());
			receivedMessage = (Response) input.readObject();
			JOptionPane.showMessageDialog(null,receivedMessage.getMessage(),"Message From Server",3);

		}catch(UnknownHostException e){
			DashBoard.logger("(UnknowHostException) : " + e.getMessage() + " " + trace(e.getStackTrace()));
			JOptionPane.showMessageDialog(null, e.getMessage()); 
		}
		catch(ClassNotFoundException e){ 
			DashBoard.logger("(ClassNotFoundException) : " + e.getMessage() + " " + trace(e.getStackTrace()));
			JOptionPane.showMessageDialog(null, e.getMessage()); 
		}catch(IOException e){
			DashBoard.logger("(IOException) : " + e.getMessage() + " " + trace(e.getStackTrace()));
			JOptionPane.showMessageDialog(null, e.getMessage()); 
		}finally{

			try{
				input.close();
				output.close();
				//client.close();
			}catch(IOException e){
				DashBoard.logger("(IOException) : " + e.getMessage() + " " + trace(e.getStackTrace()));
				JOptionPane.showMessageDialog(null, e.getMessage()); 
			}
		}
	}
	
	public static void goOffline(){
		try{
			//Response message = new Response();
			//Response receivedMessage;
			//Connect to server class
			empsoc = new Socket();
			empsoc.connect(new InetSocketAddress(host,1234),timeout);
			//empssoc = new ServerSocket(4413, 3);
			//Send message to server
			output = new ObjectOutputStream(empsoc.getOutputStream());
			receivedMessage.setAction("goOffline");
			receivedMessage.setMessage("Employee Goinf Offline");
			receivedMessage.setSource("Employee");
			receivedMessage.setUser("EMPLOYEE");
			output.writeObject(receivedMessage);
			output.flush();
			input = new ObjectInputStream(empsoc.getInputStream());
			receivedMessage = (Response) input.readObject();
			JOptionPane.showMessageDialog(null,receivedMessage.getMessage(),"Message From Server",3);

		}catch(UnknownHostException e){
			DashBoard.logger("(UnknowHostException) : " + e.getMessage() + " " + trace(e.getStackTrace()));
			JOptionPane.showMessageDialog(null, e.getMessage()); 
		}
		catch(ClassNotFoundException e){ 
			DashBoard.logger("(ClassNotFoundException) : " + e.getMessage() + " " + trace(e.getStackTrace()));
			JOptionPane.showMessageDialog(null, e.getMessage()); 
		}catch(IOException e){
			DashBoard.logger("(IOException) : " + e.getMessage() + " " + trace(e.getStackTrace()));
			JOptionPane.showMessageDialog(null, e.getMessage()); 
		}finally{

			try{
				input.close();
				output.close();
				//client.close();
			}catch(IOException e){
				DashBoard.logger("(IOException) : " + e.getMessage() + " " + trace(e.getStackTrace()));
				JOptionPane.showMessageDialog(null, e.getMessage()); 
			}
		}
	}
	
	private static String trace(StackTraceElement[] ste){
		String message = "";
		message += ste[0].getFileName()+" -> "+ste[0].getClassName()+" -> "+ste[0].getMethodName()+" -> "+ste[0].getLineNumber();
		return message;
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
			DashBoard.logger("(NoSuchAlgorithmException) : " + e.getMessage() + " " + trace(e.getStackTrace()));
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return hash;
	}
}
