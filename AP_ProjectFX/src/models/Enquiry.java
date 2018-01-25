package models;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.io.*;
/**
 * Created by micks on 4/13/2017.
 */
public class Enquiry implements Serializable{
	private static final long serialVersionUID = 1L;
    int enqID, ticketID, studID;
    String sDate, rDate, status, message, subject, response, type,attached;
    File attach;

    Socket enSock;
    ObjectOutputStream out;
    ObjectInputStream in;
    private static String host = "localhost";
    private static int timeout = 100;

    public int getEnqID() {
        return enqID;
    }

    public void setEnqID(int enqID) {
        this.enqID = enqID;
    }
    public int getStudID(){return studID;}

    public void setStudID(int studID){this.studID = studID;}

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
    public String getResponse(){return response;}

    public void setResponse(String response){this.response = response;}
    
    public void setSubject(String subject){
        this.subject = subject;
    }

    public String getSubject(){
        return subject;
    }

    public void setType(String type){
    	this.type = type;
        setSubject(type);
    }

    public String getType(){
        return type;
    }

    public void setTicketID(int ticketID){
        this.ticketID = ticketID;
    }

    public int getTicketID(){
        return ticketID;
    }
    public void setAttached(String attached){
        this.attached = attached;
    }
    public String getAttached(){
        return attached;
    }
    
    public void setAttach(File attach){
        this.attach = attach;
    }
    public File getAttach(){
        return attach;
    }
    
    
    

    public Enquiry(){
    	this.enqID = 0;
        this.studID = 0;
        this.sDate = null;
        this.rDate = null;
        this.status = null;
        this.message = null;
        this.response = null;
        this.type = null;
        this.ticketID = 0;
        this.attached = null;
        this.attach = null;
        this.subject = null;
    }
}
