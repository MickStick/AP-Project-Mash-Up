package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.Session;
import org.hibernate.Transaction;

import factories.DatabaseFactory;

@Entity
@javax.persistence.Table(name = "student_password")
public class Student_password {
	
	@Id
	private int id;
	private String password;
	
	
	public Student_password()
	{
		id = 0;
		password = "";
	}
	
	public Student_password(int id,String password)
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
		return "Student id=" + id + ", student password=" + password + "\n";
	}
	
	public static List<Student_password> readPassword()
	{
		Session session = DatabaseFactory.getFactory().openSession();
		Transaction tx = session.beginTransaction();
		List<Student_password> studs = session.createQuery("FROM Student_password").list();
		tx.commit();
		session.close();
		return studs;
	}
	
	

}
