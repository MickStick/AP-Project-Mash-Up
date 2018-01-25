package factories;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.SQLException;
import java.util.ArrayList;

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
	
	private static File getFile(String filename){
		File file = null;
		try{
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename));
			file = (File)ois.readObject();
			ois.close();
		}catch(IOException ioe){
			System.out.println("Error: "+ioe.getMessage());
		}catch(ClassNotFoundException cnfe){
			System.out.println("Error: "+cnfe.getMessage());
		}
		return file;
	}
	
	//load single attachment using ticket ID
	public static File loadAttachment(int ticketID){
		File file = null;
		try{
			stt = con.createStatement();
			String query = "SELECT *FROM enquiries WHERE ticket_ID = '"+ticketID+"';";
			res = stt.executeQuery(query);
			if(res.next()){
				file = getFile(res.getString(9));
			}
		}catch(SQLException sqle){
			JOptionPane.showMessageDialog(null, sqle.getMessage());
		}
		return file;
	}
	
	//load attachment(s) belonging to a student using student id
	public static ArrayList<File> loadAttachments(int studentID){
		ArrayList<File> files = new ArrayList<>();
		try{
			stt = con.createStatement();
			String query = "SELECT *FROM enquiries WHERE stud_ID = '"+studentID+"';";
			res = stt.executeQuery(query);
			while(res.next()){
				 files.add(getFile(res.getString(9)));
			}
		}catch(SQLException sqle){
			JOptionPane.showMessageDialog(null, sqle.getMessage());
		}
		return files;
	}
}
