package models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.Session;
import org.hibernate.Transaction;

import factories.DatabaseFactory;


@Entity
public class Student_model implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	private int id;
	private String firstName;
	private String lastName;
	private String email;
	private float accountBal;
	private String telephoneNum;
	private String clearStatus;
	
	public Student_model()
	{
		this.id = 0;
		this.firstName = "";
		this.lastName = "";
		this.email = "";
		this.accountBal = 0.0f;
		this.telephoneNum = "";
		this.clearStatus = "";
	}
	
	public Student_model(int id,String firstName,String lastName,String email,float account,String telephoneNum,String clearStatus)
	{
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.accountBal = account;
		this.telephoneNum = telephoneNum;
		this.clearStatus = clearStatus;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public float getAccountBal() {
		return accountBal;
	}

	public void setAccountBal(float accountBal) {
		this.accountBal = accountBal;
	}

	public String getTelephoneNum() {
		return telephoneNum;
	}

	public void setTelephoneNum(String telephoneNum) {
		this.telephoneNum = telephoneNum;
	}

	public String getClearStatus() {
		return clearStatus;
	}

	public void setClearStatus(String clearStatus) {
		this.clearStatus = clearStatus;
	}

	@Override
	public String toString() {
		return "Student id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", accountBal=" + accountBal + ", telephoneNum=" + telephoneNum + ", clearStatus=" + clearStatus
				+ "\n";
	}
	
	public static Student_model GetStudents(int id)
	{
		Session session = DatabaseFactory.getFactory().openSession();
		Transaction tx = session.beginTransaction();
		Student_model studs = (Student_model) session.get(Student_model.class, id);
		tx.commit();
		session.close();
		return studs;
	}
	
	public static void UpdateStudent(int id,float amount)
	{
		Session session = DatabaseFactory.getFactory().openSession();
		Transaction tx = session.beginTransaction();
		Student_model studs = (Student_model) session.get(Student_model.class, id);
		studs.setAccountBal(amount);
		session.update(studs);
		tx.commit();
		session.close();
	}
	
	
	
	
	

}
