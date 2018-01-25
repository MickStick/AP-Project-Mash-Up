package server;

import java.io.File;
//import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
//import java.util.ArrayList;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JOptionPane;

import factories.EmployeeQueryFactory;
import factories.StudentQueryFactory;
//import factories.DBConnectorFactory;
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
	ArrayList<String> employeesOnline = new ArrayList<String>();
	ArrayList<String> prevenqs = new ArrayList<>();//for testing purposes. prev enqs should be from db
	
	public Server(){
		//con = DBConnectorFactory.getDatabaseConnection();
		employeesOnline.add("John Boyle JOBO 4321");
		employeesOnline.add("Rose Hart RoHA 4569");
		employeesOnline.add("Aisha Salaam Asalaam 5555");
	}
	
	public static void main(String[] args) {
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
			JOptionPane.showMessageDialog(null, ioe.getMessage());
			ioe.printStackTrace();
		}finally{
			try{
				serverSocket.close();
				//System.out.println("Server shutdown");
				//socket.close();
			}catch (Exception e){
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
		}

		@Override
		public void run(){
			try{
					ois = new ObjectInputStream(socket.getInputStream());
					oos = new ObjectOutputStream(socket.getOutputStream());
					mail = (Response)ois.readObject();
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
							}else{
								mail.setSession(0);
								mail.setStudentObj(StudentQueryFactory.loadStudent(0));
								oos.writeObject(mail);
								oos.flush();
								System.out.println(">> Failed to load Student object...");
							}
							break;
							
						case "Online":
							if(sessionLive(mail.getSession())){
								oos.writeObject(employeesOnline);
								oos.flush();
								System.out.println(">> Online list sent...");
								//.......
								employeesOnline.add("Jane Eyre JaNE 2000");
							}else{
								mail.setMessage("Your session has expired. Please re-login.");
								oos.writeObject(mail);
								oos.flush();
								System.out.println(mail.getUserID()+":>> Query Failed! <<SessionID Expired>>");
								//JOptionPane.showMessageDialog(null, "Session Expired! Please Re-login");
							}
							break;
							
						case "Refund":
							if(sessionLive(mail.getSession())){
								oos.writeObject(StudentQueryFactory.feesPaid(mail.getUserID()));
								oos.flush();
								System.out.println(">> Refund info sent...");
							}else
								System.out.println(mail.getUserID()+": Query Failed! <<SessionID Expired>>");
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
							}else{
								mail.setSession(0);
								oos.writeObject(mail);
								oos.flush();
								System.out.println(mail.getUserID()+">> Logged out Successfully");
							}
							break;
							
						case "SaveEnquiry":
							if(sessionLive(mail.getSession())){
								StudentQueryFactory.saveEnquiry(mail.getUserID(), mail.getMessage(), mail.getFileAttachment());
								prevenqs.add(mail.getMessage());//for test. prev enq should come from db according to stud id
								mail.setMessage(null);
								oos.writeObject(mail);
								oos.flush();
							}else
								System.out.println(mail.getUserID()+":>> Query Failed! <<SessionID Expired>>");
							break;
							
						case "PrevEnquiries":
							if(sessionLive(mail.getSession())){
								oos.writeObject(StudentQueryFactory.previousEnquiries(mail.getUserID()));
								oos.flush();
								System.out.println(">> Previous Enquiry(ies) sent...");//for test
							}else
								System.out.println(mail.getUserID()+":>> Query Failed! <<SessionID Expired>>");
							break;
							
							default:
								System.out.println(mail.getUserID()+": Query Failed! <<Invalid Query>>");
								//JOptionPane.showMessageDialog(null, "Invalid Query!");
								break;
						}
					}
					
					if(mail.getUser().equals("EMPLOYEE")){
						
						System.out.println("incoming client: "+mail.getUser()+" ID#: "+mail.getUserID());
						System.out.println("incoming client action: "+mail.getAction());
						
						switch(mail.getAction()){
						
						case "Login":
							System.out.println("id: "+mail.getUserID()+" password: xxxxx");
							if(EmployeeQueryFactory.Authenticate(mail.getUserID(), mail.getPassword())){
								int sessionID = String.valueOf(mail.getUserID()).hashCode();
								activeSessions.add(String.valueOf(sessionID));
								mail.setSession(sessionID);
								mail.setEmployeeObj(EmployeeQueryFactory.loadEmployee(mail.getUserID()));
								oos.writeObject(mail);
								oos.flush();
							}else{
								mail.setSession(0);
								mail.setEmployeeObj(EmployeeQueryFactory.loadEmployee(0));
								oos.writeObject(mail);
								oos.flush();
								System.out.println(">> Failed to load Employee object...");
							}
							break;
							
						case "Enquiries":
							if(sessionLive(mail.getSession())){
								System.out.println("I'm working now");
								mail.setMessage("Dude you made it");
								oos.writeObject(mail);
								oos.flush();
							}else
								System.out.println(mail.getUserID()+":>> Query Failed! <<SessionID Expired>>");
							break;
							
						case "goOnline":
							if(sessionLive(mail.getSession())){
								employeesOnline.add("");//Add Firstname Lastname Hostname Port; as one string.
							}
							
						case "goOffline":
							if(sessionLive(mail.getSession())){
								employeesOnline.remove("");//Remove from list
							}
							
						case "Logout":
							if(sessionLive(mail.getSession())){
								killSession(String.valueOf(mail.getSession()));
								mail.setSession(0);
								oos.writeObject(mail);
								oos.flush();
							}
							break;
							
							default:
								System.out.println(">> Invalid Query!");
								break;
						}
					}
				socket.close();
			}catch (Exception e){
				e.printStackTrace();
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
