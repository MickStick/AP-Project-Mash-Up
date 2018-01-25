package models;

import models.Student;
import models.Employee;

public class Enquiry implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Student stud = null;
	Employee emp = null;
	private int ticketID;
	private String dateC;
	private String dateR;
	private int status;
	private String msg;
	private int type;
	
	public Enquiry(){
		this.ticketID = 0;
		this.dateC = null;
		this.dateR = null;
		this.status = 0;
		this.msg = "";
		this.type = 0;
	}
	
	/*
	public int getStud_ID() {
		return stud_ID;
	}
	public void setStud_ID(int stud_ID) {
		this.stud_ID = stud_ID;
	}
	public int getEmp_ID() {
		return emp_ID;
	}
	public void setEmp_ID(int emp_ID) {
		this.emp_ID = emp_ID;
	}
	*/
	public int getTicketID() {
		return ticketID;
	}
	public void setTicketID(int ticketID) {
		this.ticketID = ticketID;
	}
	public String getDateC() {
		return dateC;
	}
	public void setDateC(String dateC) {
		this.dateC = dateC;
	}
	public String getDateR() {
		return dateR;
	}
	public void setDateR(String dateR) {
		this.dateR = dateR;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
}