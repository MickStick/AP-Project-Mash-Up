package models;

import java.net.Socket;
import java.io.*;
/**
 * Created by micks on 4/13/2017.
 */
public class Enquiry implements Serializable{
    private static final long serialVersionUID = 1L;
    int enqID, ticketID, type;
    String sDate, rDate, status, message, subject;
    File attached;

    Socket enScock;
    ObjectOutputStream out;
    ObjectInputStream in;

    public int getEnqID() {
        return enqID;
    }

    public void setEnqID(int enqID) {
        this.enqID = enqID;
    }

    public String getsDate() {
        return sDate;
    }

    public void setsDate(String sDate) {
        this.sDate = sDate;
    }

    public String getrDate() {
        return rDate;
    }

    public void setrDate(String rDate) {
        this.rDate = rDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setSubject(String subject){
        this.subject = subject;
    }

    public String getSubject(){
        return subject;
    }

    public void setType(int type){
        this.type = type;
        if(type == 1){
            setSubject("Refunds");
        }else if(type == 2){
            setSubject("Financial Clearance");
        }else if(type == 3){
            setSubject("Semester Fee Statement");
        }else if(type == 4){
            setSubject("Monies Owned");
        }else if(type == 5){
            setSubject("Account Balance");
        }else{
            setSubject("No Subject");
        }
    }

    public int getType(){
        return type;
    }

    public void setTicketID(int ticketID){
        this.ticketID = ticketID;
    }

    public int getTicketID(){
        return ticketID;
    }

    public void setAttached(File attached){
        this.attached = attached;
    }
    public File getAttached(){
        return attached;
    }

    public Enquiry(){
        this.enqID = 0;
        this.sDate = null;
        this.rDate = null;
        this.status = null;
        this.message = null;
        this.type = 0;
        this.ticketID = 0;
        this.attached = null;
        this.subject = null;
    }
}
