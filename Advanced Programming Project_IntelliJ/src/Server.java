import javax.swing.*;
import java.io.IOException;
import java.net.*;

import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Server {
	private static ServerSocket server; 
	private static Socket connection;
	
	public static void main(String[]args)
	{
		try {
			server = new ServerSocket(1337,2);
			
			System.out.println("Waiting on connections....");
			connection = server.accept();
			
			if(connection != null)
			{
				System.out.println("Connection made!");
			}
			
			connection.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
//=======


public class Server extends JFrame {

	private JTextField userText;
	private JTextArea chatWindow;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private ServerSocket server;
	private Socket connection;
	
	//constructor
	public Server(){
		super("David's Instant Messenger");
		userText = new JTextField();
		userText.setEditable(false); //makes it unable to type anything into the chat box
		userText.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent e){
 						sendMessage(e.getActionCommand()); //sends message
						userText.setText(""); //sets the text area to blank
						
					}
				}
		);
		add(userText, BorderLayout.NORTH); //fondle this to south
		chatWindow = new JTextArea();
		add(new JScrollPane(chatWindow));
		setSize(300, 150);
		setVisible(true);
	}
	
	//Set up and run the server
	public void initiateServer(){
		try{
			server = new ServerSocket(8888, 5); 
			while(true){
				try{
					waitForConnection();
					setupStreams();
					whileChatting();
				}catch(EOFException e){
					e.printStackTrace();
					showMessage("\nServer ended the connection!");
				}finally{
					//close everything here
					closeCrap();
				}
				
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//wait for connection, then display connection information
	private void waitForConnection() throws IOException{
		showMessage("Waiting for someone to connect..\n");
		connection = server.accept();
		showMessage("Now connected to " + connection.getInetAddress().getHostName());
	}
	
	//get stream to send and receive data
	private void setupStreams() throws IOException{
		oos = new ObjectOutputStream(connection.getOutputStream());
		oos.flush();
		ois = new ObjectInputStream(connection.getInputStream());
		showMessage("\nStreams are now setup\n");
	}
	
	//during the chat conversation
	private void whileChatting() throws IOException{
		String message = "You are now connected!";
		sendMessage(message);
		ableToType(true);
		do{
			//having conversation here, while client wants to
			try{
				message = (String) ois.readObject(); 
				showMessage("\n" + message);
			}catch(ClassNotFoundException e){
				e.printStackTrace();
				showMessage("\nSum ting wong");
			}
		}while(!message.equals("CLIENT - END"));
	}
	
	//clsoe streams and sockets after chat's done
	private void closeCrap(){
		showMessage("\n Closing connections...\n");
		ableToType(false);
		try{
			oos.close(); //closes stream to them
			ois.close(); //closes stream from them
			connection.close();			
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	//send our message to client
	private void sendMessage(String message){
		try{
			oos.writeObject("SERVER - " + message);
			oos.flush();
			showMessage("\nSERVER - " + message);
		}catch(IOException e){
			//e.printStackTrace();
			chatWindow.append("\n Error: Message unable to be sent.\n");
		}
	}
	
	//updates the chatWindow
	private void showMessage(final String text){
		SwingUtilities.invokeLater(
			new Runnable(){
				public void run(){
					chatWindow.append(text);
				}
			}
		);
	}
	
	//sets the text field so it is editable
	private void ableToType(final boolean tof){
		SwingUtilities.invokeLater(
			new Runnable(){
				public void run(){
					userText.setEditable(tof);
				}
			}
		);
	}
}
//>>>>>>> 4256f75e1ee43929f6d0ce1e3d9b9c467e8bc5a0
