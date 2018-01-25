package factories;

import java.sql.SQLException;

import javax.swing.JOptionPane;

import models.Student;

public class StudentQueryFactory {
	private  static java.sql.Connection con =  DBConnectorFactory.getDatabaseConnection();
	private static java.sql.Statement stt = null;
	private static java.sql.ResultSet res = null;
	
	public StudentQueryFactory(){
		//con = DBConnectorFactory.getDatabaseConnection();
	}
	
	public static boolean Authenticate(int id, String password){
		try{
			stt = con.createStatement();
			String query = "SELECT * FROM student_password WHERE stud_ID = '"+id+"' AND password ='"+password+"';";
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

	public static Student loadStudent(int id){
		Student student = null;
		try{
			stt = con.createStatement();
			String query = "SELECT *FROM student WHERE stud_ID ='"+id+"';";
			res = stt.executeQuery(query);
			if(res.next()){
				student = new Student();
				student.setStudentID(res.getInt(1));
				student.setFirstName(res.getString(2));
				student.setLastName(res.getString(3));
				student.setEmail(res.getString(4));
				student.setAccountBalance(res.getFloat(5));
				student.setPhoneNumber(res.getString(6));
				student.setClearanceStatus(res.getString(7));
				System.out.println(">> Student object loaded...");
				return student;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return student;
	}
	
	public static void insertAttachment(int id,String filename){
		try{
			stt = con.createStatement();
			String query = "INSERT INTO attachments (stud_ID, files)"
					+"VALUES ('"+id+"','"+filename+"');";
			stt.execute(query);
		}catch(SQLException sqle){
			JOptionPane.showMessageDialog(null, sqle.getMessage());
		}
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
			JOptionPane.showMessageDialog(null, sqle.getMessage());
		}
		return filename;
	}
}
