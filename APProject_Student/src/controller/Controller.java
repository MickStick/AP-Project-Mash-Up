package controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import models.EmpOnServer;
import models.Student;
import views.Dashboard;
import views.LoginView;

public class Controller  {
	
	private LoginView loginView = null;
	private Student sobj;
	private static File logfile;
	
	private Dashboard dashboard;
	public Controller(){
		sobj = new Student();
		loginView = new LoginView(this);
		
		//Log file initialization
		logfile = new File("ards_sc_Log.txt");
		try{
			logfile.createNewFile();
		}catch(IOException ioe){
			
		}
		String log = " ARDS Student Client Started.";
		Controller.logger(log);
	}
	
	public static void main(String[] args) {
		new Controller();
		
	}
	
	public void attemptLogin(String action, int studID, String password){
		Controller.logger(" Student client attempting to login...");
		sobj = Student.studentLogin(action, studID, password);
		
		if(sobj != null){
			Controller.logger(" Login Successfull...");
			loginView.closeLoginView();
			dashboard = new Dashboard(sobj, this);
		}else{
			Controller.logger(" Login Failed...");
			loginView.loginFailed();
		}
	}
	
	public void attemptLogout(){
		Controller.logger(" Student client attempting to log out...");
		dashboard.logout(Student.studentLogout()); 
	}
	
	public int checkSession(){
		Controller.logger(" Student client requests session ID: "+Student.sessionStatus());
		return Student.sessionStatus();
	}
	
	public ArrayList<EmpOnServer> getEmployeesOnline(){
		Controller.logger(" Student client requests for employees online...");
		return Student.employeesOnline();
	}
	
	//Retrieves list of fees paid from database
	public ArrayList<String> prepRefund(int id){
		Controller.logger(" Student client requests for fees paid...");
		return Student.feesPaid(id);
	}
	
	public void sendEnquiry(String enquiry, File fileAttachment){
		Controller.logger(" Student client sends enquiry...");
		Student.sendEnquiry(enquiry, fileAttachment);
	}
	
	public ArrayList<String> prevEnquiries(){
		Controller.logger(" Student client requests for previous enquiries...");
		return Student.previousEnqs();
	}
	
	public ArrayList<String> moniesOwed(){
		Controller.logger(" Student client requests for monies owed...");
		return Student.moniesOwed();
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
			System.out.println("logger Error: "+ioe.getMessage());
			ioe.printStackTrace();
		}finally{
			try{
				//if(fw != null)
					fw.close();
				//if(bw != null)
					bw.close();
			}catch(IOException ioe){
				System.out.println("logger file close: "+ioe.getMessage());
				ioe.printStackTrace();
			}
		}
	}
	
	
}
