package factories;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import models.Enquiry;

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
				enq.setType(res.getInt(3));
				enq.setStudID(res.getInt(2));
				enq.setResponse(res.getString(6));
				enq.setAttached(res.getString(9));
				enqs.add(enq);
			}
			System.out.println(">> Enquiry object loaded...");
			return enqs;
		}catch(Exception e){
			e.printStackTrace();
		}
		return enqs;
	}
}
