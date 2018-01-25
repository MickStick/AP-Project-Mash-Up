package controller;

import models.Employee;
import views.Dashboard;
import views.LoginView;

public class Controller{
	
	private LoginView loginView = null;
	static Employee eobj;
	
	public Controller(){
		eobj = new Employee();
		loginView = new LoginView(this);
	}
	
	public static void main(String[] args) {
		new Controller();
	}
	
	public void attemptLogin(String action, int empID, String password){
		eobj = Employee.empLogin(action, empID, password);
		if(eobj != null){
			loginView.closeLoginView();
			new Dashboard(eobj);
		}else{
			loginView.loginFailed();
		}
	}
}
