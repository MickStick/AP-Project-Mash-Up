package models;

import java.io.Serializable;

/**
 * Created by micks on 3/30/2017.
 */
public class Person implements Serializable {
    String name;
    int id;

    public Person(){
        name = new String("");
        id = 0;
    }

    public Person(String name, int id){
        this.name = new String(name);
        this.id = id;
    }

    public Person(Person P){
        this.name = new String(P.name);
        this.id = P.id;
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

    @Override
    public String toString(){
        return String.format(name);
    }
}
