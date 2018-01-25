package MainView;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by micks on 3/31/2017.
 */
public class GetIn implements Runnable {
    DataInputStream din;
    String data;
    Scanner out;
    Socket con;

    public void receiveData(){
        while(true){
            try{
                din = new DataInputStream(con.getInputStream());
                data = din.readUTF();
                System.out.println(data);
            }catch(IOException ex){
                System.out.println("Couldn't receive message");
                break;
            }
        }
    }

    GetIn(Socket c){
        con = c;
        out = new Scanner(System.in);
        Thread receiving = new Thread(this);
        receiving.start();
    }
    @Override
    public void run() {
        receiveData();
    }
}
