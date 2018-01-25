package models;

import java.net.Socket;
import java.io.*;
/**
 * Created by micks on 4/13/2017.
 */
public class Enquiry implements Serializable{
    int enqID;
    String sDate, rDate, status, message;

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

    public Enquiry(){
        this.enqID = 0;
        this.sDate = null;
        this.rDate = null;
        this.status = null;
        this.message = null;
    }
}
