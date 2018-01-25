package models;

import java.io.Serializable;

/**
 * Created by micks on 3/30/2017.
 */
public class Person implements Serializable {
    String name;
    int id;
    EmpOnServer eos;

    public Person(){
        this.name = new String("");
        this.id = 0;
        this.eos = null;
    }

    public Person(String name, int id, EmpOnServer eos){
        this.name = new String(name);
        this.id = id;
        this.eos = eos;
    }

    public Person(Person P){
        this.name = new String(P.name);
        this.id = P.id;
        this.eos = P.eos;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }
    
    public void setEOS(EmpOnServer eos){
    	this.eos = eos;
    }
    public EmpOnServer getEOS(){
    	return eos;
    }

    @Override
    public String toString(){
        return String.format(name);
    }
}
