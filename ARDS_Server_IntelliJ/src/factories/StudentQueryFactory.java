package factories;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

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
				student.setSemCost(res.getFloat(8));
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

	public static void saveEnquiry(int studentID, String Enquiry, File file){
		Integer newfilename = 1;
		String[] enquiryType = Enquiry.split(">");
		String enquiry =null;
		String filename = null;
		final String status = "open";
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		String datecreated = dateFormat.format(date);

		//trimming enquiry of characters that cause sql errors;
		enquiry = enquiryType[1];
		enquiry = enquiry.trim().replace("'", " ");
		enquiry = enquiry.trim().replace(";", "");
		enquiry = enquiry.trim().replace('"', (char)0);
		enquiry = enquiry.trim().replaceAll("=", "is equal to");
		enquiry = enquiry.trim().replace('*', (char)0);

		//determine ticket id and hash it for file name
		if(file != null){
			try{
				stt = con.createStatement();
				String query = "SELECT *FROM enquiries";
				res = stt.executeQuery(query);
				if(res.next()){
					while(res.next())
						newfilename++;
				}
			}catch(SQLException sqle){
				System.out.println("Error: "+sqle.getMessage());
			}
			filename = String.valueOf(newfilename.hashCode())+".ards";
		}else
			filename = "n/a";

		try{
			stt = con.createStatement();
			String query = "INSERT INTO enquiries (stud_ID, type, enquiry, dateCreated, status, file_attachment)"
					+"VALUES ('"+studentID+"','"+enquiryType[0]+"','"+enquiry+"','"+datecreated+"','"+status+"','"+filename+"');";
			stt.execute(query);
			System.out.println(">> Enquiry Saved Successfully...");
		}catch(SQLException sqle){
			JOptionPane.showMessageDialog(null, "making trouble "+sqle.getMessage());
		}
		if(file != null){
			System.out.println("Attached File: "+file.getName());//testing purposes. need to be written to db
			saveAttachment(filename,file);
		}
	}

	public static ArrayList<String> previousEnquiries(int studentID){
		ArrayList<String> prevEnqs = new ArrayList<>();
		try{
			stt = con.createStatement();
			String query = "SELECT *FROM enquiries WHERE stud_ID ='"+studentID+"';";
			res = stt.executeQuery(query);
			while(res.next()){
				String penq = null;
				//appending response if any to enquiry
				if(res.getString(6) == null){
					penq = res.getString(3)+">"+res.getString(4)+
							"\n\n[RESPONSE]\n"+"No response yet.";
				}else{
					penq = res.getString(3)+">"+res.getString(4)+
							"\n\n[RESPONSE]\n"+res.getString(6);
				}
				prevEnqs.add(penq);
			}
		}catch(SQLException sqle){
			System.out.println("Error: "+sqle.getMessage());
		}
		return prevEnqs;
	}

	public static ArrayList<String> moniesOwed(int id)
	{
		ArrayList<String> monies = new ArrayList<>();
		try
		{
			stt = con.createStatement();
			String query = "SELECT * FROM Owed_Payment WHERE stud_ID = '"+id+"';";
			res = stt.executeQuery(query);
			while(res.next()){
				monies.add(String.valueOf(res.getFloat(2)));
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return monies;
	}
	
	//files are saved as objects but not actual files.
	private static void saveAttachment(String filename, File file){
		try{
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename));
			oos.writeObject(file);
			oos.flush();
			oos.close();
		}catch(IOException ioe){
			System.out.println("Error: "+ioe.getMessage());
		}
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
				if(!res.getString(9).equals("n/a"))
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
				if(!res.getString(9).equals("n/a")) 
					files.add(getFile(res.getString(9)));
			}
		}catch(SQLException sqle){
			JOptionPane.showMessageDialog(null, sqle.getMessage());
		}
		return files;
	}
}
