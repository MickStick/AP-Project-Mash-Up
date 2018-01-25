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

import models.Enquiry;
import models.Response;
import models.Student;

public class EnquiryQueryFactory {
	private  static java.sql.Connection con =  DBConnectorFactory.getDatabaseConnection();
	private static java.sql.Statement stt = null;
	private static java.sql.ResultSet res = null;
	
	public static ArrayList<Enquiry> loadEnquiries(){
		ArrayList<Enquiry> enqs = new ArrayList<Enquiry>();
		try{
			stt = con.createStatement();
			String query = "SELECT *FROM enquiries";// WHERE type ='"+ type +"';";
			res = stt.executeQuery(query);
			while(res.next()){
				Enquiry enq = new Enquiry();
				enq = new Enquiry();
				enq.setTicketID(res.getInt(1));
				enq.setsDate(res.getString(5));
				enq.setrDate(res.getString(7));
				enq.setStatus(res.getString(8));
				enq.setMessage(res.getString(4));
				enq.setType(res.getString(3));
				enq.setStudID(res.getInt(2));
				enq.setResponse(res.getString(6));
				enq.setAttached(res.getString(9));
				/*enq.setTicketID(res.getInt(1));
				enq.setType(res.getInt(6));
				enq.setMessage(res.getString(5));
				enq.setStatus(res.getString(4));
				enq.setsDate(res.getString(2));
				enq.setrDate(res.getString(3));*/
				enqs.add(enq);
			}
			System.out.println(">> Enquiry object loaded...");
			return enqs;
		}catch(Exception e){
			e.printStackTrace();
		}
		return enqs;
	}

	public static Response saveEnquiry(int ticket, String Enquiry, File file){
		Response resp = new Response();
		Integer newfilename = 1;
		String filename;
		final String status = "Closed";
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		String datecreated = dateFormat.format(date);

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
		}else {
			filename = "n/a";
		}
		try{
			stt = con.createStatement();
			String query = "UPDATE enquiries "
					+"SET response='" + Enquiry + "', "
					+"dateResponded='" + datecreated + "', "
					+"status='" + status	+ "' "
					+"WHERE ticket_ID='" + ticket + "'";
			stt.executeUpdate(query);
			resp.setMessage(">> Enquiry Saved Successfully...");
			System.out.println(">> Enquiry Saved Successfully...");
		}catch(SQLException sqle){
			JOptionPane.showMessageDialog(null, "making trouble "+sqle.getMessage());
			resp.setMessage(">> Enquiry Failed...");
			return resp;
		}
		if(file != null){
			System.out.println("Attached File: "+file.getName());//testing purposes. need to be written to db
			saveAttachment(filename,file, ticket);
		}

		return resp;
	}

	//files are saved as objects but not actual files.
	private static void saveAttachment(String filename, File file, int id){
		Integer newfilename = 1;
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
		try{
			stt.getConnection();
			String query = "UPDATE enquiries "
					+"SET file_attachment='" + filename + "' "
					+"WHERE ticket_ID='" + id + "'";
			stt.executeUpdate(query);

			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename));
			oos.writeObject(file);
			oos.flush();
			oos.close();
		}catch(IOException ioe){
			System.out.println("Error: "+ioe.getMessage());
		}catch(SQLException e){

		}
	}
}
