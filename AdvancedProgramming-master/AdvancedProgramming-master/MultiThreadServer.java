package sdsmh_server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import models.Employee_password;
import models.FeePayment;
import models.Student_model;
import models.Student_password;

//Server class
public class MultiThreadServer implements Runnable {
	private static Socket sock; // Client socket
	private static ObjectOutputStream os;// client output stream
	private static Connection con = null;// connection for database
	private static ResultSet res;
	private static Statement stt;
	static ServerSocket server; // Server socket

	MultiThreadServer(Socket csocket) {
		this.sock = csocket;
	}

	public void run() {
		try {

			ObjectInputStream in; // client input stream
//			String url = "jdbc:mysql://localhost:3306/ARDS";// URL for database
//			String username = "root";
//			Class.forName("com.mysql.jdbc.Driver");
//			con = DriverManager.getConnection(url, username, "");
//			if (con != null) {
//
//				System.out.println("Waiting on connections");
//				System.out.println("Connected to resources");
//			}

			in = new ObjectInputStream(sock.getInputStream());
			os = new ObjectOutputStream(sock.getOutputStream());
			Response response = new Response();
			response = (Response) in.readObject();
			System.out.println("Response source " + response.getSource());
			System.out.println("From client " + response.getMessage());
			String password = getSHA_256Hash(response.getPassword());
			int ID = response.getId();
			int transactionID = response.getFee_id();
			float transactionAmount;
			// Possible actions from clients
			//Include while loop here to wait for input from client
			if (response.getAction().equals("Login")) {
				if (response.getSource().equals("student")) {
					try {
						Student_password user = new Student_password(ID,password);
						List<Student_password> users = user.readPassword();
						for(Student_password passwords:users)
						{
							if(passwords.getPassword().equals(user.getPassword()) && passwords.getId() == user.getId())
							{
								System.out.println("Authenticated");
								response.setMessage("Authenticated");
								response.setSession(1);
								os.writeObject(response);
								os.flush();
								break;
							}
						}
							
	
						
					} catch (Exception e) {
						System.out.println("Error in SQL");
						e.printStackTrace();
					}
				}
				else
				{
					Employee_password user = new Employee_password(ID,password);
					List<Employee_password> users = user.readPassword();
					for(Employee_password passwords:users)
					{
						if(passwords.getPassword().equals(user.getPassword()) && passwords.getId() == user.getId())
						{
							System.out.println("Authenticated");
							response.setMessage("Authenticated");
							response.setSession(1);
							os.writeObject(response);
							os.flush();
							break;
						}
					}
				}
					


			}
				if (response.getAction().equals("view")) {
						// Query database for student fees
					Student_model stud = new Student_model();
					
					stud = Student_model.GetStudents(ID);
					System.out.println("Student retreived " + stud);
					os.writeObject(stud);
					os.flush();
					
					
					}
	

					if (response.getAction().equals("refund")) {
						// Query database for refund requested
						FeePayment fees = new FeePayment();
						
						fees = FeePayment.GetFeesByID(ID);
						System.out.println("Records retrieved\n" + fees);
						os.writeObject(fees);
						os.flush();
						}
					
					if (response.getAction().equals("select refund")) {
						// Remove fund from fee table and update student table
						FeePayment fees = new FeePayment();
						Student_model stud = new Student_model();
						fees = FeePayment.GetFeesByID(transactionID);
						transactionAmount = fees.getAmount();
						//->Update student object here 
						Student_model.UpdateStudent(ID, transactionAmount);
						FeePayment.RemoveFeePaymentByID(transactionID);
						
						os.writeObject(fees);
						os.flush();
						os.writeObject(stud);
						os.flush();
						
						
					}
					
					
					
					if (response.getAction().equals("clearance")) {
						// Query database and set financial clearance field of
						// student table
					}
					if(response.getAction().equals("enquire"))
					{
						//Query database for enquiry table and insert new enquiry 
					}
					
					if(response.getAction().equals("generate fees"))
					{
						//Query database for module and generate fee payment info 
					}
					
					if (response.getAction().equals("Exit")) {
						sock.close();
						System.out.println("Client connection closed!");
					}


					factories.DatabaseFactory.closeFactory();

		} catch (IOException e) {
			System.out.println(e);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

	}

	public static void main(String args[]) throws Exception {
		server = new ServerSocket(1234); // Server socket

		System.out.println("Server started on port: " + server.getLocalPort());
		while (true) {
			Socket sock = server.accept();
			System.out.println("Connected");
			new Thread(new MultiThreadServer(sock)).start();
		}
	}

	private static String getSHA_256Hash(String password) {
		String hash = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(password.getBytes());

			byte byteData[] = md.digest();

			// convert the byte to hex format method 1
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < byteData.length; i++) {
				sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
			}
			hash = sb.toString();
			// Now we need to zero pad it if you actually want the full 32
			// chars.
			while (hash.length() < 64) {
				hash = "0" + hash;
			}

		} catch (NoSuchAlgorithmException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return hash;
	}

}
