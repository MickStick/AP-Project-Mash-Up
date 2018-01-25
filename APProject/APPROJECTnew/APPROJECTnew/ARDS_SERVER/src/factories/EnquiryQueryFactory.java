package factories;

import java.sql.SQLException;

import javax.swing.JOptionPane;

import models.Enquiry;

public class EnquiryQueryFactory {
	private  static java.sql.Connection con =  DBConnectorFactory.getDatabaseConnection();
	private static java.sql.Statement stt = null;
	private static java.sql.ResultSet res = null;
	
	public static Enquiry loadEnquiries(){
		Enquiry enq = null;
		try{
			stt = con.createStatement();
			String query = "SELECT *FROM enquiry";// WHERE type ='"+ type +"';";
			res = stt.executeQuery(query);
			if(res.next()){
				enq = new Enquiry();
				enq.setTicketID(res.getInt(1));
				enq.setDateC(res.getString(2));
				enq.setDateR(res.getString(3));
				enq.setStatus(res.getInt(4));
				enq.setDateC(res.getString(5));
				enq.setType(res.getInt(6));
				System.out.println(">> Enquiry object loaded...");
				return enq;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return enq;
	}
}
