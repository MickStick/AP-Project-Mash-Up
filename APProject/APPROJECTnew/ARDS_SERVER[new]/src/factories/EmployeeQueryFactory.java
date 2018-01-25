package factories;

import java.sql.SQLException;

import javax.swing.JOptionPane;

import models.Employee;
//import models.Student;

public class EmployeeQueryFactory {
	private  static java.sql.Connection con =  DBConnectorFactory.getDatabaseConnection();
	private static java.sql.Statement stt = null;
	private static java.sql.ResultSet res = null;
	
	public static boolean Authenticate(int id, String password){
		try{
			stt = con.createStatement();
			String query = "SELECT * FROM employee_password WHERE emp_ID = '"+id+"' AND password ='"+password+"';";
			res = stt.executeQuery(query);
			if(res.next()){
				System.out.println(id+": Authenticated");
				return true;
			}else
				System.out.println(id+": Authentication Failed>>Incorrect Username/Password.");
		}catch(SQLException sqle){
			JOptionPane.showMessageDialog(null, sqle.getMessage());
			sqle.printStackTrace();
		}
		return false;
	}

	public static Employee loadEmployee(int id){
		Employee employee = null;
		try{
			stt = con.createStatement();
			String query = "SELECT *FROM employee WHERE Emp_ID ='"+id+"';";
			res = stt.executeQuery(query);
			if(res.next()){
				employee = new Employee();
				employee.setEmpID(res.getInt(1));
				employee.setFirstName(res.getString(2));
				employee.setLastName(res.getString(3));
				employee.setEmail(res.getString(4));
				System.out.println(">> Employee object loaded...");
				return employee;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return employee;
	}
	
	public static String loadAttachment(int id){
		String filename = null;
		try{
			stt = con.createStatement();
			String query = "SELECT *FROM attachments WHERE stud_ID = '"+id+"';";
			res = stt.executeQuery(query);
			if(res.next()){
				filename = res.getString(2);
			}
		}catch(SQLException sqle){
			System.out.println(">> Failed to load attachment...");
			//JOptionPane.showMessageDialog(null, sqle.getMessage());
		}
		return filename;
	}
}
