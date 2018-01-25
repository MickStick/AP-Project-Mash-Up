package sdsmh_server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class Client2 {
private static Socket client;
private static ObjectInputStream input;
private static ObjectOutputStream output;
	
public static void main(String[] args)
{
	//connecting client to server
	try {
		Response message = new Response();
		Response receivedMessage;
		//Connect to server class
		client = new Socket(InetAddress.getLocalHost(),1234);
		System.out.println("Connected to : " + client.getInetAddress().getHostName());
		//Send message to server
		output = new ObjectOutputStream(client.getOutputStream());
		message.setAction("Login");
		message.setMessage("need to login");
		message.setSource("employee");
		message.setId(4000000);
		message.setPassword("password0000");
		output.writeObject(message);
		output.flush();
		//output.close();
		//Reading message from server
				input = new ObjectInputStream(client.getInputStream());
				receivedMessage = (Response) input.readObject();
				System.out.println("Response source: "+ receivedMessage.getRepsonseSource());
				System.out.println("" + receivedMessage.getMessage());
		
		
		
		
	} catch (IOException | ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	finally{
		try {
			client.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

}
