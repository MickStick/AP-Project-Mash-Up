package factories;

import java.io.File;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

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
	
	public static ArrayList<String> feesPaid(int id){
		ArrayList<String> feeslist = new ArrayList<>();
		try{
			stt = con.createStatement();
			String query = "SELECT *FROM feepayment WHERE student_ID = '"+id+"';";
			res = stt.executeQuery(query);
			while(res.next()){
				String record = null;
				record = res.getInt(1)+" "+res.getFloat(3);
				feeslist.add(record);
			}
		}catch(SQLException sqle){
			System.out.println(sqle.getMessage());
		}
		return feeslist;
	}
	
	public static void saveEnquiry(int studentID, String enquiry, File file){
		//String ext = FilenameUtils.getExtension(file.getName());
		try{
			stt = con.createStatement();
			
			
			String query = "INSERT INTO enquiry (ticket_ID, dateCreated, dateResponded, status, message)"
					+"VALUES ('"+studentID+"','"+file+"');";
			//stt.execute(query);
		}catch(SQLException sqle){
			JOptionPane.showMessageDialog(null, sqle.getMessage());
		}
		System.out.println(">> SaveEnquiry Method need to be completed!.");
		System.out.println(enquiry);//testing purposes. need to be written to db
		if(file != null)
			System.out.println("Attached File: "+file.getName());//testing purposes. need to be written to db
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
