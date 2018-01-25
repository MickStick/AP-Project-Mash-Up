package sdsmh_server;

import java.awt.Color;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;

import sdsmh_server.Controller;

public class LoginView extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Controller controller;// = null;
	private JPanel pnlTop,pnlTitle, pnlMid1, pnlMid2, pnlBottom;
	private JLabel lblAvatar, lblUsername, lblPassword, lblTitle;
	private JTextField txtUsername;
	private JPasswordField txtPassword;
	private JButton btnLogin, btnCancel;
	
	private Border border = BorderFactory.createLineBorder(Color.black, 1, false);
	
	private void initComponents(){
		lblAvatar = new JLabel();
		lblTitle = new JLabel("Student Login");
		lblUsername = new JLabel("USERNAME: ");
		lblPassword = new JLabel("PASSWORD: ");
		txtUsername = new JTextField();
		txtPassword = new JPasswordField();
		btnLogin = new JButton("LOGIN");
		btnCancel = new JButton("CANCEL");
		pnlTop = new JPanel();
		pnlTitle = new JPanel(new FlowLayout());
		pnlMid1 = new JPanel(new FlowLayout());
		pnlMid2 = new JPanel(new FlowLayout());
		pnlBottom = new JPanel(new FlowLayout(FlowLayout.CENTER));
		
		lblAvatar.setIcon(new ImageIcon("src\\images\\header.png"));
		txtUsername.setBorder(border);
		txtUsername.setPreferredSize(new Dimension(190,30));
		txtPassword.setBorder(border);
		txtPassword.setPreferredSize(new Dimension(190,30));
		
		btnLogin.setBorder(border);
		btnLogin.setAlignmentX(LEFT_ALIGNMENT);
		btnLogin.setBackground(Color.lightGray);
		btnLogin.setPreferredSize(new Dimension(90,30));
		
		btnCancel.setBorder(border);
		btnCancel.setAlignmentX(LEFT_ALIGNMENT);
		btnCancel.setBackground(Color.lightGray);
		btnCancel.setPreferredSize(new Dimension(90,30));
		
	}
	
	private void addComponentsToPanels(){
		pnlTop.add(lblAvatar);
		pnlTitle.add(lblTitle);
		pnlMid1.add(lblUsername);
		pnlMid1.add(txtUsername);
		pnlMid2.add(lblPassword);
		pnlMid2.add(txtPassword);
		pnlBottom.add(btnLogin);
		pnlBottom.add(btnCancel);
	}
	
	private void addPanelsToWindow(){
		this.add(pnlTop);
		this.add(pnlTitle);
		this.add(pnlMid1);
		this.add(pnlMid2);
		this.add(pnlBottom);
	}
	
	private void setWindowProperties(){
		this.setLayout(new GridLayout(5,1));
		this.setTitle("Student Login");
		this.setVisible(true);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(450,280);
		this.setBackground(Color.black);
	}
	
	private void registerListeners(){
		btnLogin.addActionListener(this);
		btnCancel.addActionListener(this);
	}
	
	public LoginView(Controller controller){
		this.controller = controller;
		initComponents();
		addComponentsToPanels();
		addPanelsToWindow();
		setWindowProperties();
		registerListeners();
	}
	
	public void loginFailed(){
		JOptionPane.showMessageDialog(null, "Incorrect Username or Password!","Login Failed",JOptionPane.ERROR_MESSAGE);
	}
	
	public void closeLoginView(){
		this.dispose();
	}
	

	@Override
	public void actionPerformed(ActionEvent act) {
		if(act.getSource() == btnLogin){
			String action = "login";
			controller.attemptLogin(action, Integer.valueOf(txtUsername.getText()), String.valueOf(txtPassword.getPassword()));
			
		}
		if(act.getSource() == btnCancel)
			closeLoginView();
		
	}
}
