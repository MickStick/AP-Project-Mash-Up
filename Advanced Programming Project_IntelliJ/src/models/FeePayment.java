package models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.Session;
import org.hibernate.Transaction;

import factories.DatabaseFactory;

@Entity
public class FeePayment implements Serializable{
	private static final long serialVersionUID = -6413742080114631732L;
	
	@Id
	private int id;
	private int stud_ID;
	private float amount;
	
	public FeePayment()
	{
		this.id = 0;
		this.stud_ID = 0;
		this.amount = 0.0f;
	}
	
	public FeePayment(int id,int stud_ID,float amount)
	{
		this.id = id;
		this.stud_ID = stud_ID;
		this.amount = amount;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getStud_ID() {
		return stud_ID;
	}

	public void setStud_ID(int stud_ID) {
		this.stud_ID = stud_ID;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "Transaction ID=" + id + ", Student=" + stud_ID + ", Amount=" + amount + "\n";
	}
	
	public static FeePayment GetFeesByID(int id)
	{
		Session session = DatabaseFactory.getFactory().openSession();
		Transaction tx = session.beginTransaction();
		FeePayment fees = (FeePayment) session.get(FeePayment.class, id);
		tx.commit();
		session.close();
		return fees;
	}
	public static void RemoveFeePaymentByID(int id)
	{
		Session session = DatabaseFactory.getFactory().openSession();
		Transaction tx = session.beginTransaction();
		FeePayment fee = (FeePayment) session.get(FeePayment.class, id);
		session.delete(fee);
		tx.commit();
		session.close();
	}
	

	
	

}
