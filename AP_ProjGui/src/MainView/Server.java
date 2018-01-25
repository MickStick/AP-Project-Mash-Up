package MainView;


/**
 * Created by micks on 3/27/2017.
 */
import javafx.application.Platform;
import javafx.concurrent.Task;

import java.net.*;
import java.io.*;
import java.util.*;
public class Server{
    ServerSocket servSock, chatSock;
    Socket con, chatCon;
    ObjectOutputStream os;
    ObjectInputStream is;
    String data, msg;
    Scanner out;
    DataOutputStream dout;
    DataInputStream din;
    boolean br = false;
    InetAddress client;


    public void createConnection() {
        /*new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {*/
                    try {
                        servSock = new ServerSocket(4000);
                        chatSock = new ServerSocket(4001);
                        //while (true) {

                        //}
                    } catch (IOException e) {
                        System.out.println("Server Did Not Connect!!");
                        e.printStackTrace();
                        //break;
                    }
                /*}
            }
        }).start();*/

    }

    public void getStreams(InetAddress client) {
        try {
            os = new ObjectOutputStream(con.getOutputStream());
            is = new ObjectInputStream(con.getInputStream());
            System.out.println("Getting Client Streams");
        } catch (IOException e) {
            System.out.println("ERROR!!\nEither the Streams Failed or the Client " + client.getHostName() + " has Disconnected");
            //e.printStackTrace();
        }
    }

    public void getChatStreams() {
        try {
            os = new ObjectOutputStream(chatCon.getOutputStream());
            is = new ObjectInputStream(chatCon.getInputStream());
            System.out.println("Getting Chat Streams");
        } catch (IOException e) {
            System.out.println("ERROR!!\nEither the Streams Failed or the Chat has Disconnected");
            //e.printStackTrace();
        }
    }

    public void closeConnection() {
        try {
            is.close();
            os.close();
            con.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /*public void waitForRequest(){
        String action = new String("");

        //try{
            System.out.println("Server is waiting.");
            while(true){
                try {
                    con = servSock.accept();
                    System.out.println("Application connected");
                    /*dout = new DataOutputStream(con.getOutputStream());
                    data = "Welcome to The Server TM.";*/
    // try {
                       /* while(true) {
                            sendData();
                            receiveData();
                            if(br){
                                break;
                            }
                        }

                    /*} catch (IOException e) {
                        System.out.println("Chat Problems");
                        break;
                    }
                    try{
                                dout.writeUTF(data);
                                msg = out.nextLine();
                                dout.writeUTF(msg);
                            }catch (IOException e){
                                System.out.println("Out Issues");
                            }
                            try{
                                DataInputStream din = new DataInputStream(con.getInputStream());
                                data = din.readUTF();
                                System.out.println("Received Message");
                                System.out.println(data);
                            }catch (IOException e){
                                System.out.println("In Issues");
                            }*/
                /*} catch (IOException e) {
                    System.out.println("Chat Problems");
                }
            /*try{

                //waitForRequest();
            }catch(IOException e){
                System.out.println("Can't Send");
            }*/
    //}
       /* } catch(EOFException	e){
            System.out.println("Client has terminted connections with the server");
        } catch(IOException e){
            System.out.println("ERROR");
        }/*catch(IOException e){
            e.printStackTrace();
        }
    }*/

    public void waitForClient(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        con = servSock.accept();
                        client = con.getInetAddress();
                        //getStreams(client);
                        System.out.println("Application at " + client.getHostAddress() + " has connected");
                        dout = new DataOutputStream(con.getOutputStream());
                        data = "Welcome to The Server TM.";
                        //dout.writeUTF(data);
                    } catch (IOException e) {

                    } finally {
                        if (con == null) {
                            try {
                                con.close();
                            } catch (IOException ignored) {

                            }
                        }
                    }
                }

            }
        }).start();
    }

    public void sendData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    try{
                        dout = new DataOutputStream(con.getOutputStream());
                        try{
                            msg = out.nextLine();
                        }catch (NullPointerException n){

                        }
                        dout.writeUTF(msg);
                    }catch(IOException ex){
                        System.out.println("Can't send data");
                        break;
                    }catch (NullPointerException ex){

                    }
                }
            }
        }).start();
    }

    public void receiveData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try{
                        din = new DataInputStream(con.getInputStream());
                        data = din.readUTF();
                        System.out.println(data);
                    }catch(IOException ex){
                        System.out.println("Couldn't receive message");
                        break;
                    }catch (NullPointerException ex){

                    }
                }
            }
        }).start();
    }

    Server() {

        out = new Scanner(System.in);
        this.createConnection();
        this.waitForClient();
        this.sendData();
        this.receiveData();
        //SendOut sendOut = new SendOut(con);
        //GetIn getIn = new GetIn(con);
        //this.waitForRequest();
    }


    public static void main(String [] args){
        new Server();
    }

    private class clientThread extends Thread{
        public int clinetId;
        Socket client;
        clientThread(int id, Socket client){
            this.clinetId = id;
            this.client = client;
        }
    }

}