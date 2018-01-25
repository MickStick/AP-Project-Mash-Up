package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class ChatFrame extends JInternalFrame implements ActionListener, KeyListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextArea textarea, txaMessage, txaOnline;
	private JPanel pnlSouth, pnlNorth, pnlEast;//pnlCenter
	private JScrollPane sclChatPane, sclMessage, sclOnline;
	private JButton btnSend, btnConnect, btnRefresh;
	private JTextField txtHostname, txtPort;
	private JLabel lblOnlineTitle, lblHostPort, lblHostname, lblPort;
	
	private void init(){
		textarea = new JTextArea();
		textarea.setLineWrap(true);
		textarea.setBackground(Color.white);
		textarea.setEditable(false);
		textarea.setWrapStyleWord(true);
		sclChatPane = new JScrollPane(textarea);
		
		pnlSouth = new JPanel(new FlowLayout());
		btnSend = new JButton("Send");
		btnSend.setPreferredSize(new Dimension(90,45));
		txaMessage = new JTextArea();
		txaMessage.setLineWrap(true);
		txaMessage.setWrapStyleWord(true);
		sclMessage = new JScrollPane(txaMessage);
		sclMessage.setPreferredSize(new Dimension(380,45));
		
		pnlEast = new JPanel(new BorderLayout());
		lblOnlineTitle = new JLabel("Employees Online");
		lblOnlineTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblOnlineTitle.setOpaque(true);
		//lblOnlineTitle.setBackground(Color.getHSBColor((float)220.09, (float)89.8, 100));
		Border padBorder = BorderFactory.createEmptyBorder(5,0,5,0);
		lblOnlineTitle.setBorder(padBorder);
		lblOnlineTitle.setBackground(Color.darkGray);
		lblOnlineTitle.setForeground(Color.white);
		lblHostPort = new JLabel("Address : Port Number");
		lblHostPort.setHorizontalAlignment(SwingConstants.CENTER);
		txaOnline = new JTextArea();
		txaOnline.setEditable(false);
		txaOnline.setLineWrap(true);
		txaOnline.setWrapStyleWord(true);
		sclOnline = new JScrollPane(txaOnline);txaOnline.append("localhost\t:3306\n\n");txaOnline.append("local\t:3106\n\n");
		txaOnline.append("foreignhost\t:9646\n\n");
		sclOnline.setPreferredSize(new Dimension(185,425));
		
		pnlNorth = new JPanel(new FlowLayout());
		btnRefresh = new JButton(new ImageIcon("src\\images\\refresh.png"));
		lblHostname = new JLabel("Address: ");
		txtHostname = new JTextField();
		lblPort = new JLabel("Port: ");
		txtPort = new JTextField();
		btnConnect = new JButton("Connect");
		txtHostname.setPreferredSize(new Dimension(200,30));
		txtPort.setPreferredSize(new Dimension(50,30));
		
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
		pnlEast.add(lblHostPort, BorderLayout.CENTER);
		pnlEast.add(sclOnline, BorderLayout.SOUTH);
	}
	
	private void addComponentsToWindow(){
		this.getContentPane().add(pnlNorth, BorderLayout.NORTH);
		this.getContentPane().add(sclChatPane, BorderLayout.CENTER);
		this.getContentPane().add(pnlSouth, BorderLayout.SOUTH);
		this.getContentPane().add(pnlEast, BorderLayout.EAST);
		
	}
	
	private void setWindowProperties(){
		this.setSize(520,600);
		this.setVisible(true);
		this.setLocation(820, 0);
		//this.setLayout(new BorderLayout());
	}
	
	private void registerListener(){
		this.btnSend.addActionListener(this);
		this.btnConnect.addActionListener(this);
		this.btnRefresh.addActionListener(this);
		this.txaMessage.addKeyListener(this);
	}
	
	public ChatFrame(){
		super("Chat",false,true,true,true);
		init();
		addComponentsToPanels();
		addComponentsToWindow();
		setWindowProperties();
		registerListener();
	}

	@Override
	public void actionPerformed(ActionEvent act) {
		if(act.getSource() == btnSend){
			String msg = " [You] : "+txaMessage.getText()+"\n\n";
			if(msg != null || !msg.isEmpty()){
				textarea.append(msg);
				txaMessage.setText(null);
			}
		}
		
		if(act.getSource() == btnConnect){
			if(txtHostname.getText().isEmpty() && txtPort.getText().isEmpty()){
				//(txtHostname.getText(),Integer.parseInt(txtPort.getText());
				JOptionPane.showMessageDialog(this, "Address & Port Missing");
			}
		}
		
		if(act.getSource() == btnRefresh){
			
		}
	}

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
	
	

}
