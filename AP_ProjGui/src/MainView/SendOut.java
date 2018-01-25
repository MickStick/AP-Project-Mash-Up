package MainView;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by micks on 3/31/2017.
 */
public class SendOut implements Runnable {
    DataOutputStream dout;
    String msg;
    Scanner out;
    Socket con;
    public void sendData(){
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
                }
            }

    }
    SendOut(Socket c){
        con = c;
        out = new Scanner(System.in);
        Thread sending = new Thread(this);
        sending.start();
    }
    @Override
    public void run() {
        sendData();
    }
}
