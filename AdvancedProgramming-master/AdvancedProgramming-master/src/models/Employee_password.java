package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.Session;
import org.hibernate.Transaction;

import factories.DatabaseFactory;

@Entity
public class Employee_password {
	@Id
	private int id;
	private String password;
	
	public Employee_password()
	{
		this.id = 0;
		this.password = "";
	}
	
	public Employee_password(int id, String password)
	{
		this.id = id;
		this.password = password;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Employee id=" + id + ", Employee password=" + password + "\n";
	}
	
	public static List<Employee_password> readPassword()
	{
		Session session = DatabaseFactory.getFactory().openSession();
		Transaction tx = session.beginTransaction();
		List<Employee_password> emps = session.createQuery("FROM Employee_password").list();
		tx.commit();
		session.close();
		return emps;
	}

}
