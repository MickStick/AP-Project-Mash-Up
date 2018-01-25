package controller;

import java.util.ArrayList;

import models.Student;
import views.Dashboard;
import views.LoginView;

public class Controller  {
	
	private LoginView loginView = null;
	private Student sobj;
	//private Student smod;
	
	private Dashboard dashboard;
	public Controller(){
		sobj = new Student();
		loginView = new LoginView(this);
		//new Dashboard(sobj,this);
	}
	
	public static void main(String[] args) {
		new Controller();
		
	}
	
	public void attemptLogin(String action, int studID, String password){
		sobj = Student.studentLogin(action, studID, password);
		
		if(sobj != null){
			loginView.closeLoginView();
			dashboard = new Dashboard(sobj, this);
		}else{
			loginView.loginFailed();
		}
	}
	
	public void attemptLogout(){
		dashboard.logout(Student.studentLogout()); 
	}
	
	public int checkSession(){
		return Student.sessionStatus();
	}
	
	public ArrayList<String> getEmployeesOnline(){
		return Student.employeesOnline();
	}
}
