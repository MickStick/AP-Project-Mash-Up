package MainView;



import java.net.Socket;
import java.io.*;
import java.util.*;

/**
 * Created by micks on 3/30/2017.
 */
public class TestClient {
    Socket con;
//    DataInputStream din;
    String data, msg;
    Scanner out;
    GetIn getIn;
    SendOut sendOut;

    public void getConnected(){
       /* new Thread(new Runnable() {
            @Override
            public void run(){
                while(true) {*/
                    try {
                        //con = new Socket("127.0.0.5",3000);
                        con = new Socket("127.0.0.6", 4000);
                        System.out.println("TestClient Connected");
                        DataOutputStream dout = new DataOutputStream(con.getOutputStream());
                        msg = "Hi Server ^_^";
                        dout.writeUTF(msg);
                    } catch (IOException e) {
                        System.out.println("Can't connect to Server!!!");
                        //e.printStackTrace();
                    }
               /* }
            }

        }).start();*/

    }

    public void waitForRequest(){
        while (true) {
            try {
                while(true) {
                    DataInputStream din = new DataInputStream(con.getInputStream());
                    DataOutput dout = new DataOutputStream(con.getOutputStream());
                    data = din.readUTF();
                    System.out.println("Received Message");
                    System.out.println(data);
                    msg = out.nextLine();
                    dout.writeUTF(msg);
                }
            } catch (IOException e) {
                System.out.println("Chat Problems");
                break;
            }
            /*try{

                //waitForRequest();
            }catch(IOException e){
                System.out.println("Can't Send");
            }*/

        }
    }

    TestClient(){
        out = new Scanner(System.in);
        this.getConnected();
        try{
            getIn = new GetIn(con);
            sendOut = new SendOut(con);
        }catch (NullPointerException e){

        }

        //this.waitForRequest();
    }


    public static void main(String [] args){
        new TestClient();
    }


}


