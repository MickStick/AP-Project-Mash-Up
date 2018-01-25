package server;

import java.io.*;
//import java.io.FileOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
//import java.util.ArrayList;
import java.net.SocketException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
//import java.util.ArrayList;

import javax.swing.JOptionPane;

import factories.EmployeeQueryFactory;
import factories.EnquiryQueryFactory;
import factories.StudentQueryFactory;
//import factories.DBConnectorFactory;
import models.EmpOnServer;
import models.Employee;
import models.Response;

public class Server {
	public static final int PORT = 1234;
	private ServerSocket serverSocket = null;
	private Socket socket = null;
	//........................................
	//...........................................
	//StudentSQLFactory studentSQL;
	File attachment;
	ObjectOutputStream oos;
	ObjectInputStream ois;
	Response mail;
	ArrayList<String> activeSessions = new ArrayList<String>();
	ArrayList<EmpOnServer> employeesOnline = new ArrayList<EmpOnServer>();
	ArrayList<String> prevenqs = new ArrayList<>();//for testing purposes. prev enqs should be from db
	ArrayList<Integer> EmpPorts = new ArrayList<Integer>();
	static EmpOnServer seos;
	public static File logfile;
	
	public Server(){
		//con = DBConnectorFactory.getDatabaseConnection();
		/*employeesOnline.add("John Boyle JOBO 4321");
		employeesOnline.add("Rose Hart RoHA 4569");
		employeesOnline.add("Aisha Salaam Asalaam 5555");*/
	}

	private static String trace(StackTraceElement[] ste){
		String message = "";
		message += ste[0].getFileName()+" -> "+ste[0].getClassName()+" -> "+ste[0].getMethodName()+" -> "+ste[0].getLineNumber();
		return message;
	}

	private static String getTimeStamp(){
		java.util.Date date = new java.util.Date();
		java.text.DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String timestamp = "[ "+dateFormat.format(date)+" ] > ";
		return timestamp;
	}

	public static void logger(String log){
		FileWriter fw = null;
		BufferedWriter bw = null;
		try{
			fw = new FileWriter(logfile.getAbsoluteFile(),true);
			bw = new BufferedWriter(fw);
			bw.write("\n"+getTimeStamp()+log);
			bw.flush();
		}catch(IOException ioe){
			Server.logger("(IOException) : " + ioe.getMessage() + " " + trace(ioe.getStackTrace()));
			System.out.println("logger Error: "+ioe.getMessage());
			ioe.printStackTrace();
		}finally{
			try{
				//if(fw != null)
				fw.close();
				//if(bw != null)
				bw.close();
			}catch(IOException ioe){
				Server.logger("(IOException) : " + ioe.getMessage() + " " + trace(ioe.getStackTrace()));
				System.out.println("logger file close: "+ioe.getMessage());
				ioe.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		logfile = new File("ards_s_Log.txt");
		try{
			logfile.createNewFile();
		}catch(IOException ioe){

		}
		String log = "Server Started.";
		Server.logger(log);
		new Server().runServer();
	}
	
	public void runServer(){
		try{
			serverSocket = new ServerSocket(PORT);
			System.out.println("Server ready for connection...");
			while(true){
				socket = serverSocket.accept();
				System.out.println("Connection accepted...");
				new ServerThread(socket).start();
				System.out.println("socket passed to thread...");
			}
		}catch (IOException ioe){
			Server.logger("(IOException) : " + ioe.getMessage() + " " + trace(ioe.getStackTrace()));
			JOptionPane.showMessageDialog(null, ioe.getMessage());
			ioe.printStackTrace();
		}finally{
			try{
				serverSocket.close();
				//System.out.println("Server shutdown");
				//socket.close();
			}catch (Exception e){
				Server.logger("(Exception) : " + e.getMessage() + " " + trace(e.getStackTrace()));
				JOptionPane.showMessageDialog(null, e.getMessage());
				e.printStackTrace();
			}
			
		}
	}
	
	public class ServerThread extends Thread{
		Socket socket;


		ServerThread(Socket socket){
			this.socket = socket;
			System.out.println("thread initialized...");
			Server.logger("Server Thread Initialized");
		}

		public int getEmpSock(){
			int port;
			Random rand = new Random();
			port = rand.nextInt(3008) + 2000;
			for(int x = 0; x < EmpPorts.size(); x++){
				if(EmpPorts.get(x) != port){
					break;
				}else{
					getEmpSock();
					break;
				}
			}

			Server.logger("Employee Sockets Generated");
			return port;
		}

		@Override
		public void run(){
			try{
					ois = new ObjectInputStream(socket.getInputStream());
					oos = new ObjectOutputStream(socket.getOutputStream());
					mail = (Response) ois.readObject();
				DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				Date date = new Date();

				if(mail.getUser().equals("STUDENT")){
					System.out.println("[ "+dateFormat.format(date)+" ]");
					System.out.println("incoming client: "+mail.getUser()+" ID#: "+mail.getUserID());
					System.out.println("incoming client action: "+mail.getAction());

					switch(mail.getAction()){

						case "Login":
							System.out.println("id: "+mail.getUserID()+" password: xxxxx");

							if(StudentQueryFactory.Authenticate(mail.getUserID(),mail.getPassword())){
								int sessionID = String.valueOf(mail.getUserID()).hashCode();
								activeSessions.add(String.valueOf(sessionID));
								mail.setSession(sessionID);
								mail.setStudentObj(StudentQueryFactory.loadStudent(mail.getUserID()));
								oos.writeObject(mail);
								oos.flush();
								Server.logger("Student Logged In");
							}else{
								mail.setSession(0);
								mail.setStudentObj(StudentQueryFactory.loadStudent(0));
								oos.writeObject(mail);
								oos.flush();
								System.out.println(">> Failed to load Student object...");
								Server.logger("Student Failed to Log In");
							}
							break;

						case "Online":
							if(sessionLive(mail.getSession())){
								oos.writeObject(employeesOnline);
								oos.flush();
								System.out.println(">> Online list sent...");
								Server.logger("Employees Online List Sent To Student");
								//.......
								//employeesOnline.add("Jane Eyre JaNE 2000");
							}else{
								mail.setMessage("Your session has expired. Please re-login.");
								oos.writeObject(new ArrayList<EmpOnServer>());
								oos.flush();
								System.out.println(mail.getUserID()+":>> Query Failed! <<SessionID Expired>>");
								Server.logger("Query Failed <<Session Expired>>");
								//JOptionPane.showMessageDialog(null, "Session Expired! Please Re-login");
							}
							break;

						case "Refund":
							if(sessionLive(mail.getSession())){
								oos.writeObject(StudentQueryFactory.feesPaid(mail.getUserID()));
								oos.flush();
								System.out.println(">> Refund info sent...");
								Server.logger("Refund Sent From Student");
							}else
								System.out.println(mail.getUserID()+": Query Failed! <<SessionID Expired>>");
								Server.logger("Query Failed <<Session Expired>>");
							//JOptionPane.showMessageDialog(null, "Session Expired! Please Re-login");
							break;

						case "Clearance":
							if(sessionLive(mail.getSession())){
								//.......
							}else
								System.out.println(mail.getUserID()+":>> Query Failed! <<SessionID Expired>>");
							//JOptionPane.showMessageDialog(null, "Session Expired! Please Re-login");
							break;

						case "Statement":
							if(sessionLive(mail.getSession())){
								//.......
							}else
								System.out.println(mail.getUserID()+":>> Query Failed! <<SessionID Expired>>");
							//JOptionPane.showMessageDialog(null, "Session Expired! Please Re-login");
							break;

						case "Logout":
							if(sessionLive(mail.getSession())){
								//activeSessions.remove(String.valueOf(mail.getSession()));
								killSession(String.valueOf(mail.getSession()));
								mail.setSession(0);
								oos.writeObject(mail);
								oos.flush();
								System.out.println(mail.getUserID()+">> Logged out Successfully");
								Server.logger("Student Logged Out");
							}else{
								mail.setSession(0);
								oos.writeObject(mail);
								oos.flush();
								System.out.println(mail.getUserID()+">> Logged out Successfully");
								Server.logger("Student Failed to Logout");
							}
							break;

						case "SaveEnquiry":
							if(sessionLive(mail.getSession())){
								StudentQueryFactory.saveEnquiry(mail.getUserID(), mail.getMessage(), mail.getFileAttachment());
								prevenqs.add(mail.getMessage());//for test. prev enq should come from db according to stud id
								mail.setMessage(null);
								oos.writeObject(mail);
								oos.flush();
								Server.logger("Query Sent!");
							}else{
								System.out.println(mail.getUserID()+":>> Query Failed! <<SessionID Expired>>");
								Server.logger("Query Failed <<Session Expired>>");
							}

							break;

						case "PrevEnquiries":
							if(sessionLive(mail.getSession())){
								oos.writeObject(StudentQueryFactory.previousEnquiries(mail.getUserID()));
								oos.flush();
								System.out.println(">> Previous Enquiry(ies) sent...");//for test
								Server.logger("Previous Queries Sent So Student");
							}else{
								System.out.println(mail.getUserID()+":>> Query Failed! <<SessionID Expired>>");
								Server.logger("Query Failed <<Session Expired>>");
							}

							break;

						case "moniesOwed":
							if(sessionLive(mail.getSession())){
								oos.writeObject(StudentQueryFactory.moniesOwed(mail.getUserID()));
								oos.flush();
								System.out.println(">> Monies owed list sent...");//for test
							}else {
								System.out.println(mail.getUserID() + ":>> Query Failed! <<SessionID Expired>>");
								Server.logger("Query Failed <<Session Expired>>");
							}
							break;

						default:
							System.out.println(mail.getUserID()+": Query Failed! <<Invalid Query>>");
							Server.logger("Query Failed <<Invalid Query>>");
							//JOptionPane.showMessageDialog(null, "Invalid Query!");
							break;
					}
					}
					
					if(mail.getUser().equals("EMPLOYEE")){
						
						System.out.println("incoming client: "+mail.getUser()+" ID#: "+mail.getUserID());
						System.out.println("incoming client action: "+mail.getAction());
						
						switch(mail.getAction()){

						case "Login":
							System.out.println("[ "+dateFormat.format(date)+" ]");
							System.out.println("id: "+mail.getUserID()+" password: xxxxx");
							if(EmployeeQueryFactory.Authenticate(mail.getUserID(), mail.getPassword())){
								int sessionID = String.valueOf(mail.getUserID()).hashCode();
								activeSessions.add(String.valueOf(sessionID));
								mail.setSession(sessionID);
								mail.setEmployeeObj(EmployeeQueryFactory.loadEmployee(mail.getUserID()));
								String name = mail.getEmployeeObject().getFirstName() +  " " + mail.getEmployeeObject().getLastName();
								int port0, port1, port2;
								port0 = getEmpSock();
								EmpPorts.add(port0);
								port1 = getEmpSock();
								EmpPorts.add(port1);
								port2 = getEmpSock();
								EmpPorts.add(port2);
								seos = new EmpOnServer(name,port0,port1,port2);
								System.out.println(mail.getEmployeeObject().getFirstName()+ "'s ports are " + port0 + ", " + port1 + " and " + port2);
								mail.getEmployeeObject().setEOS(seos);
								oos.writeObject(mail);
								oos.flush();
								Server.logger("Employee Successfullly Logged In");
							}else{
								mail.setSession(0);
								mail.setEmployeeObj(EmployeeQueryFactory.loadEmployee(0));
								oos.writeObject(mail);
								oos.flush();
								System.out.println(">> Failed to load Employee object...");
								Server.logger("Employe Failed To Log In");
							}
							break;
							case "Students":
								System.out.println("[ "+dateFormat.format(date)+" ]");
								System.out.println("id: "+mail.getUserID());
								mail.setStudentObj(StudentQueryFactory.loadStudent(mail.getStudentObject().getStudentID()));
								oos.writeObject(mail);
								oos.flush();
								Server.logger("Student Information Loaded and Sent To Employee");
								break;

						case "Enquiries":
							System.out.println("[ "+dateFormat.format(date)+" ]");
							System.out.println("id: "+mail.getUserID());
							mail.setEnquiries(EnquiryQueryFactory.loadEnquiries());
							oos.writeObject(mail);
							oos.flush();
							Server.logger("Enquieries Loded and Sent To The Employee");
							break;
						case "saveReply":
								System.out.println("[ "+dateFormat.format(date)+" ]");
								if(sessionLive(mail.getSession())){
									System.out.println(mail.getEnquiryObject().getTicketID());
									System.out.println(mail.getEnquiryObject().getResponse());
									mail.setMessage(EnquiryQueryFactory.saveEnquiry(mail.getEnquiryObject().getTicketID(), mail.getEnquiryObject().getResponse(), mail.getEnquiryObject().getAttach()).getMessage());
									Server.logger("Response Sent!");
								}
							break;
						case "Refund":
								System.out.println("[ "+dateFormat.format(date)+" ]");
								if(sessionLive(mail.getSession())){
									mail.setMessage(EmployeeQueryFactory.refundFee(mail.getStudentObject()).getMessage());
									oos.writeObject(mail);
									oos.flush();
									Server.logger("Student Refunded!");
								}
								break;

						case "updateStudent":
								System.out.println("[ "+dateFormat.format(date)+" ]");
									mail.setMessage(EmployeeQueryFactory.updateStudent(mail.getStudentObject()).getMessage());
									oos.writeObject(mail);
									oos.flush();
									Server.logger("Student Updated");
								break;
						case "get File":
								System.out.println("[ "+dateFormat.format(date)+" ]");
								mail.getEnquiryObject().setAttach(EmployeeQueryFactory.loadAttachment(mail.getEnquiryObject().getAttached()));
								oos.writeObject(mail);
								oos.flush();
								Server.logger("File Retrieved");
								break;

						case "goOnline":
							System.out.println("[ "+dateFormat.format(date)+" ]");
							if(sessionLive(mail.getSession())) {
								mail.getEmployeeObject().getEOS().setHost(mail.getMessage());
								employeesOnline.add(mail.getEmployeeObject().getEOS());//Add Firstname Lastname Hostname & Ports;
								System.out.println(mail.getEmployeeObject().getEOS().getName() + " on " + mail.getEmployeeObject().getEOS().getHost() + " has went online");
								for (int x = 0; x < employeesOnline.size(); x++) {
									System.out.println(employeesOnline.get(x).getName());
								}
								mail.setMessage("Employee Online");
								oos.writeObject(mail);
								oos.flush();
								Server.logger(mail.getEmployeeObject().getEOS().getName() + " on " + mail.getEmployeeObject().getEOS().getHost() + " has went online");
							}
							break;
							

						case "goOffline":
							System.out.println("[ "+dateFormat.format(date)+" ]");
							if(sessionLive(mail.getSession())){
								//mail.getEmployeeObject().getEOS().setHost();
								for(int x = 0; x < employeesOnline.size(); x++){
									if(employeesOnline.get(x).getName().equals(mail.getEmployeeObject().getEOS().getName())){
										System.out.println(employeesOnline.get(x).getName() + " on " + employeesOnline.get(x).getHost() + " has went offline");
										employeesOnline.remove(employeesOnline.get(x));//Remove from list
									}
								}
								mail.setMessage("Employee Offline");
								oos.writeObject(mail);
								oos.flush();
								Server.logger(mail.getEmployeeObject().getEOS().getName() + " on " + mail.getEmployeeObject().getEOS().getHost() + " has went offline");
							}
							break;
						

						case "Logout":
							System.out.println("[ "+dateFormat.format(date)+" ]");
							if(sessionLive(mail.getSession())){
								killSession(String.valueOf(mail.getSession()));
								mail.setSession(0);
								oos.writeObject(mail);
								oos.flush();
							}
							break;
							
							default:
								System.out.println("[ "+dateFormat.format(date)+" ]");
								System.out.println(">> Query Failed");
								Server.logger("Query Failed <<Invalid Query>>");
								break;
						}
					}
				socket.close();
				this.stop();
			}catch (Exception e){
				e.printStackTrace();
				Server.logger("(Exception) : " + e.getMessage() + " " + trace(e.getStackTrace()));
			}
		}
	}
	//.................................................................................................................
	/*private void saveAttachment(int id, File file){
		if(file != null){
			StudentQueryFactory.insertAttachment(id, file.getName());
			try{
				ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file.getName()));
				out.writeObject(file);
				out.flush();
				out.close();
			}catch(IOException ioe){
				ioe.printStackTrace();
			}
		}
	}
	
	private File loadAttachment(){
		File file = null;
		
		return file;
	}*/
	
	private boolean sessionLive(int sessionID){
		for(String sid : activeSessions){
			if(sid.equals(String.valueOf(sessionID)))
				return true;
		}
		return false;
	}
	
	private void killSession(String sessionID){
		for(int i = 0; i < activeSessions.size(); i++){
			if(activeSessions.get(i).equals(sessionID)){
				activeSessions.remove(sessionID);
			}
		}
	}
	//.................................................................................................................
}