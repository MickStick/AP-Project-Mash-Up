package factories;

import java.sql.DriverManager;

public class DBConnectorFactory {
	private static java.sql.Connection con = null;
	private static final String DRIVER ="com.mysql.jdbc.Driver";
	
	
	public static java.sql.Connection getDatabaseConnection(){
		if(con == null){
			try{
				Class.forName(DRIVER).newInstance();
				String url = "jdbc:mysql://localhost:3306/ards";
				con = DriverManager.getConnection(url,"root","");
				return con;
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return con;
	}
}
