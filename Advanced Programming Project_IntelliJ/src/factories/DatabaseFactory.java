package factories;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class DatabaseFactory {
private static SessionFactory sessionFact;
	
	public static SessionFactory getFactory()
	{
		if(sessionFact == null)
		{
			sessionFact = new Configuration().configure().buildSessionFactory();
		}
		
		
		return sessionFact;
	}
	
	public static void closeFactory()
	{
		if(sessionFact == null)
		{
			sessionFact.close();
		}
	}

}
