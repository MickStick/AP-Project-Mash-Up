package views;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import models.Employee;

public class ProfileFrame extends JInternalFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextArea textpane;
	private JLabel lblProfilePicture, lblStudentID, lblStudentName, lblAddress, lblEmail, lblPhone;
	private JPanel pnlTop, pnlMid1, pnlMid2, pnlMid3, pnlMid4, pnlBottom;
	//private Student sobj;
	
	private void init(){
		textpane = new JTextArea();
		//textpane.setLineWrap(true);
		textpane.setOpaque(false);
		textpane.setEditable(false);
		Font font = textpane.getFont();
		textpane.setFont(font.deriveFont(Font.BOLD));
		//textpane.setWrapStyleWord(true);
//		textpane.setContentType("tekst/html");
//		textpane.setText("<html><center><b><font size=30 color=rgb(1,1,1)></font></b></center></html>");
		
		
		
		lblProfilePicture = new JLabel();
		lblStudentID = new JLabel();
		lblStudentName = new JLabel();
		lblAddress = new JLabel();
		lblEmail = new JLabel();
		lblPhone = new JLabel();
		
		lblProfilePicture.setIcon(new ImageIcon("src\\images\\profile.png"));
		
		pnlTop = new JPanel(new FlowLayout());
		pnlTop.add(lblProfilePicture);
		pnlMid1 = new JPanel(new FlowLayout());
		pnlMid2 = new JPanel(new FlowLayout());
		pnlMid3 = new JPanel(new FlowLayout());
		pnlMid4 = new JPanel(new FlowLayout());
		pnlBottom = new JPanel(new FlowLayout());
		
	}
	
	private void addComponentsToPanels(){
		pnlTop.add(lblProfilePicture);
		pnlMid1.add(lblStudentID);
		pnlMid2.add(lblStudentName);
		pnlMid3.add(lblAddress);
		pnlMid4.add(lblEmail);
		pnlBottom.add(lblPhone);
		
	}
	
	private void addPanelsToWindow(){
		this.add(pnlTop);
		this.add(pnlMid1);
		this.add(pnlMid2);
		this.add(pnlMid3);
		this.add(pnlMid4);
		this.add(pnlBottom);
	}
	
	private void addComponentsToWindow(){
		this.getContentPane().add(pnlTop, BorderLayout.NORTH);
		this.getContentPane().add(textpane, BorderLayout.CENTER);
	}
	
	private void setWindowProperties(){
		//this.setLayout(new GridLayout(6,1));
		this.setSize(300,350);
		this.setVisible(true);
		this.setDefaultCloseOperation(JInternalFrame.DO_NOTHING_ON_CLOSE);
	}
	
	public ProfileFrame(Employee eobj){
		super("Employee Profile",false,false,false,true);
		init();
		//addComponentsToPanels();
		//addPanelsToWindow();
		addComponentsToWindow();
		setWindowProperties();
		empInfo(eobj);
		/*lblStudentID.setText("Student ID: "+sobj.getStudentID());
		lblStudentName.setText("Name: "+sobj.getFirstName()+" "+sobj.getLastName());
		lblAddress.setText("Address: "+sobj.getAddress());
		lblEmail.setText("Email: "+sobj.getEmail());
		lblPhone.setText("Phone Number: "+sobj.getPhoneNumber());*/
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	private void empInfo(Employee eobj){
		String info = "\nEmployee ID: "+eobj.getEmpID()+"\n\n"
				+"Name: "+eobj.getFirstName()+" "+eobj.getLastName()+"\n\n"
				+"Address: "+eobj.getAddress()+"\n\n"
				+"Email: "+eobj.getEmail()+"\n\n"
				+"Phone Number: "+eobj.getPhoneNumber();
		textpane.setText(info);
	}
}
