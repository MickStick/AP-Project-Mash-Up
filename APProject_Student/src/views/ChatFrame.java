package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import com.sun.glass.ui.Cursor;

import controller.Controller;
import models.ChatResponse;
import models.EmpOnServer;
import models.StudOnServer;
import models.Student;

public class ChatFrame extends JInternalFrame implements ActionListener, KeyListener, FocusListener,Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextArea textarea, txaMessage;
	private JPanel pnlSouth, pnlNorth, pnlEast;//pnlCenter
	private JScrollPane sclChatPane, sclMessage, sclOnline;
	private JButton btnSend, btnConnect, btnRefresh;
	private JTextField txtHostname, txtPort;
	private JLabel lblOnlineTitle, lblHostname, lblPort;
	private int empToConnect = 0;
	
	JTable tblOnline;
	DefaultTableModel tmodel;
	Controller controller;
	ArrayList<EmpOnServer> employeesOnline;
	String employeeName;
	Student sobj;
	
	private Border border = BorderFactory.createLineBorder(Color.black, 1, false);
	
	private void init(){
		
		textarea = new JTextArea();
		textarea.setLineWrap(true);
		textarea.setBackground(Color.white);
		textarea.setEditable(false);
		textarea.setWrapStyleWord(true);
		sclChatPane = new JScrollPane(textarea);
		sclChatPane.setBorder(border);
		
		pnlSouth = new JPanel(new FlowLayout());
		//pnlSouth.setBackground(Color.white);
		btnSend = new JButton();
		btnSend.setIcon(new ImageIcon("src\\images\\send.png"));
		btnSend.setPreferredSize(new Dimension(90,50));
		//btnSend.setBackground(Color.white);
		//btnSend.setBorder(border);
		txaMessage = new JTextArea();
		txaMessage.setLineWrap(true);
		txaMessage.setWrapStyleWord(true);
		sclMessage = new JScrollPane(txaMessage);
		sclMessage.setPreferredSize(new Dimension(290,50));
		//sclMessage.setBorder(border);
		
		pnlEast = new JPanel(new BorderLayout());
		lblOnlineTitle = new JLabel("Employees Online");
		lblOnlineTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblOnlineTitle.setOpaque(true);
		
		Border padBorder = BorderFactory.createEmptyBorder(5,0,5,0);
		lblOnlineTitle.setBorder(padBorder);
		lblOnlineTitle.setBackground(Color.black);
		lblOnlineTitle.setForeground(Color.white);
		
		pnlNorth = new JPanel(new FlowLayout());
		btnRefresh = new JButton(new ImageIcon("src\\images\\refresh.png"));
		lblHostname = new JLabel("Address: ");
		txtHostname = new JTextField();
		txtHostname.setEditable(false);
		lblPort = new JLabel("Port: ");
		txtPort = new JTextField();
		txtPort.setEditable(false);
		btnConnect = new JButton("Connect");
		btnConnect.setIcon(new ImageIcon("src\\images\\connect.png"));
		txtHostname.setPreferredSize(new Dimension(50,30));
		txtPort.setPreferredSize(new Dimension(50,30));
		
		tmodel = new DefaultTableModel(){
			private static final long serialVersionUID = 1L;
			@Override
			public boolean isCellEditable(int row, int column){
				return false;
			}
			/*@Override
			public Class<?> getColumnClass(int column){
				if(column == 0)
					return JLabel.class;
				return JLabel.class;
			}*/
		};
		tmodel.addColumn("");
		tmodel.addColumn("");
		tblOnline = new JTable(tmodel){
			private static final long serialVersionUID = 1L;

			//void setCursor(()Cursor.CURSOR_POINTING_HAND);
		};
		tblOnline.setRowHeight(28);
		//tblOnline.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tblOnline.getColumnModel().getColumn(0).setPreferredWidth(22);
		tblOnline.getColumnModel().getColumn(1).setPreferredWidth(150);//63
		tblOnline.setShowVerticalLines(false);
		//tblOnline.setOpaque(false);
		tblOnline.getColumnModel().getColumn(0).setCellRenderer(new tblOnlineRenderer());
		//tblOnline.setDefaultRenderer(JLabel.class, new tblOnlineRenderer());
		sclOnline = new JScrollPane(tblOnline);
		sclOnline.setPreferredSize(new Dimension(150,425));//185
		sclOnline.setBorder(border);
	}
	
	private void addComponentsToPanels(){
		pnlNorth.add(lblHostname);
		pnlNorth.add(txtHostname);
		pnlNorth.add(lblPort);
		pnlNorth.add(txtPort);
		pnlNorth.add(btnConnect);
		pnlNorth.add(btnRefresh);
		
		pnlSouth.add(sclMessage);
		pnlSouth.add(btnSend);
		
		pnlEast.add(lblOnlineTitle, BorderLayout.NORTH);
		pnlEast.add(sclOnline, BorderLayout.CENTER);
	}
	
	private void addComponentsToWindow(){
		this.getContentPane().add(pnlNorth, BorderLayout.NORTH);
		this.getContentPane().add(sclChatPane, BorderLayout.CENTER);
		this.getContentPane().add(pnlSouth, BorderLayout.SOUTH);
		this.getContentPane().add(pnlEast, BorderLayout.EAST);
		
	}
	
	private void setWindowProperties(){
		this.setFrameIcon(new ImageIcon("src\\images\\chat.png"));
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		//pack();
		this.setSize((int)(screenSize.getWidth()*0.3),(int)(screenSize.getHeight()*.5));// 520,600
		//this.setSize(520,600);
		this.setVisible(true);
		//this.setLocation(820, 0);
		this.setLocation((int)(screenSize.getWidth()*0.7), 0);
		//this.setDefaultCloseOperation(JInternalFrame.EXIT_ON_CLOSE);
	}
	
	private void registerListener(){
		this.btnSend.addActionListener(this);
		this.btnConnect.addActionListener(this);
		this.btnRefresh.addActionListener(this);
		this.txaMessage.addKeyListener(this);
		
		this.tblOnline.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				if(e.getClickCount() == 1){ 
					setConnection(tblOnline.getSelectedRow());
				}
			}
		});
	}
	
	public ChatFrame(Student sobj,Controller controller){
		super("Chat",false,true,true,true);
		this.controller = controller;
		this.sobj =sobj;
		init();
		addComponentsToPanels();
		addComponentsToWindow();
		setWindowProperties();
		registerListener();
		//Controller.logger(" Chat frame opened...");
		updateOnline(controller.getEmployeesOnline());//initialize table
	}

	@Override
	public void actionPerformed(ActionEvent act) {
		if(act.getSource() == btnSend){
			String msg = " [You] : "+txaMessage.getText()+"\n\n";
			if(msg != null || !msg.isEmpty()){
				textarea.append(msg);
				sendMessages(sobj.getFirstName()+" "+sobj.getLastName(), txaMessage.getText());
				txaMessage.setText(null);
			}
		}
		
		if(act.getSource() == btnConnect){
			//initializeClient(txtHostname.getText(), empToConnect);
			this.connectToServer(txtHostname.getText(), empToConnect);
			if(!txtHostname.getText().isEmpty() && !txtPort.getText().isEmpty()){
				//initializeClient(txtHostname.getText(), empToConnect);
				
			}
		}
		
		if(act.getSource() == btnRefresh){
			if(controller.checkSession() != 0){
				updateOnline(controller.getEmployeesOnline());
			}else
				JOptionPane.showMessageDialog(null, "Session Expired. Please re-login.");
		}
	}
	
	public void updateOnline(ArrayList<EmpOnServer> list){
		employeesOnline = list;
		tmodel.getDataVector().removeAllElements();
		tmodel.fireTableDataChanged();
		for(int x=0; x<list.size(); x++){
			//String[] empso = item.split(" ");
			JLabel temp = new JLabel();
			employeeName = new String(list.get(x).getName());
			temp.setIcon(new ImageIcon("src\\images\\use.png"));
			tmodel.addRow(new Object[]{temp,employeeName});
		}
		
		tmodel.fireTableDataChanged();
	}
	
	public void setConnection(int index){
		//String[] empso = employeesOnline.get(index).split(" ");
		txtHostname.setText(employeesOnline.get(index).getHost());
		empToConnect = employeesOnline.get(index).getFirstPort();
		txtPort.setText(String.valueOf(employeesOnline.get(index).getFirstPort()));
	}
	
	
//..........................................................................
	@Override
	public void keyPressed(KeyEvent ev) {
		if(ev.getKeyCode() == KeyEvent.VK_ENTER){
			this.btnSend.doClick();
		}		
	}

	@Override
	public void keyReleased(KeyEvent ev) {
		if(ev.getKeyCode() == KeyEvent.VK_ENTER){
			txaMessage.setText(null);
		}
		
	}

	@Override
	public void keyTyped(KeyEvent ev) {
		
	}
	//................................................

	@Override
	public void focusGained(FocusEvent fe) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void focusLost(FocusEvent fe) {
		// TODO Auto-generated method stub
		
	}
	
	class tblOnlineRenderer extends DefaultTableCellRenderer{
		private static final long serialVersionUID = 1L;
		JLabel lbl = new JLabel();
		@Override
		public Component getTableCellRendererComponent(JTable tblOnline, Object obj, boolean isSelected, boolean hasFocus, int row,
				int column) {
			lbl.setOpaque(false);
			lbl.setIcon(new ImageIcon("src\\images\\user.png"));
			return lbl;
		}
	}
	//CHAT IMPLEMENTATION
	//=============================================================================================================================
	ObjectInputStream ois = null;
	ObjectOutputStream oos = null;
	private Socket connection; //connection itself
	private String message = ""; //the message

	
	public void sendMessages(String name, String msg){
		ChatResponse resp = new ChatResponse();
		StudOnServer sos = new StudOnServer(name, msg);
		resp.setSOS(sos);
		try{
			oos.writeObject(resp);
			oos.flush();
		}catch(IOException ioe){
			System.out.println("Can't send message");
		}
		//txaMessage.append("[You] : "+txaMessage.getText()+"\n\n");
		//showMessage("\nCLIENT - " + msg);
	}
	
	//connect to the server
	public void initializeClient(String hostname, Integer port){
		//try{
			connectToServer(hostname,port);
			setupStreams();
			whileChatting();
		/*}catch(EOFException e){
			//showMessage("\nClient terminated connection");
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			closeCrap();
		}*/
	}
	
	//connect to server
	private void connectToServer(String hostname, int port){
		try{
			connection = new Socket();
			connection.connect(new InetSocketAddress(hostname,port),1000);
			this.setTitle("Chat [Connected to "+employeeName+"]");
			setupStreams();
			whileChatting();
		}catch(IOException ioe){
			
		}
		///connection = new Socket(InetAddress.getByName(serverIP),4360); //2774//passes in IPAddress
	}
	
	//setting up streams here
	private void setupStreams() /*throws IOException*/{
		try{
			oos = new ObjectOutputStream(connection.getOutputStream());
			oos.flush();
			ois = new ObjectInputStream(connection.getInputStream());
		}catch(IOException e){
			
		}
		
	}
	
	//while chatting with the server
	private void whileChatting(){
		new Thread(new Runnable(){
			@Override
			public void run() {
				while(true){
					try{
						message = (String) ois.readObject();
						textarea.append("[EMP]: "+message+"\n");
					}catch(ClassNotFoundException e){
						
					}catch(IOException e){
						
					}
				}//while(!message.equals("SEVER - END"));	
			}	
		}).start();	
		//closeServices();
	}
	
	//closes the streams and sockets
	private void closeServices(){
		try{
			oos.close();
			ois.close();
			connection.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	//sends messages to server
	/*private void sendMessage(String message) {
		try{
			oos.writeObject("CLIENT - " + message);
			oos.flush();
		}catch(IOException e){
			//e.printStackTrace();
			///chatWindow.append("\nMessage unable to send..");
		}
	}*/
	
	//=============================================================================================================================
}
