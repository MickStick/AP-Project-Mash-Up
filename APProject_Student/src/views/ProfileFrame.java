package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import models.Student;

public class ProfileFrame extends JInternalFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextArea textarea;
	private JLabel lblProfilePicture;
	private JPanel pnlTop;
	//private Student sobj;
	
	private void init(){
		textarea = new JTextArea();
		textarea.setLineWrap(true);
		textarea.setWrapStyleWord(true);
		textarea.setOpaque(false);
		textarea.setEditable(false);
		Font font = textarea.getFont();
		textarea.setFont(font.deriveFont(Font.BOLD));
		
		lblProfilePicture = new JLabel();
		lblProfilePicture.setOpaque(false);
		lblProfilePicture.setIcon(new ImageIcon("src\\images\\profile.png"));
		
		pnlTop = new JPanel(new FlowLayout());
		pnlTop.setOpaque(false);
		pnlTop.add(lblProfilePicture);
	}
	
	private void addComponentsToWindow(){
		this.getContentPane().add(pnlTop, BorderLayout.NORTH);
		this.getContentPane().add(textarea, BorderLayout.CENTER);
	}
	
	private void setWindowProperties(){
		this.setFrameIcon(new ImageIcon("src\\images\\prof.png"));
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		//pack();
		this.setSize((int)(screenSize.getWidth()*0.2),(int)(screenSize.getHeight()*.4));// 520,600
		//this.setSize(300,350);
		this.setVisible(true);
		this.setBackground(Color.white);
		//this.setDefaultCloseOperation(JInternalFrame.DO_NOTHING_ON_CLOSE);
		this.setDefaultCloseOperation(JInternalFrame.EXIT_ON_CLOSE);
	}
	
	public ProfileFrame(Student sobj){
		super("Student Profile",false,false,false,true);
		init();
		addComponentsToWindow();
		setWindowProperties();
		studentInfo(sobj);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	private void studentInfo(Student sobj){
		String info = "\n Student ID: \t"+sobj.getStudentID()+"\n\n"
				+" Name: \t"+sobj.getFirstName()+" "+sobj.getLastName()+"\n\n"
				+" Address: \t"+sobj.getAddress()+"\n\n"
				+" Email: \t"+sobj.getEmail()+"\n\n"
				+" Phone Nos: \t"+sobj.getPhoneNumber();
		textarea.setText(info);
	}

}
