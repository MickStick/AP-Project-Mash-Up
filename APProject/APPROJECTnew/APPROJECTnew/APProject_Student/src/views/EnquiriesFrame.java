package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableModel;

import controller.Controller;
import models.Student;

public class EnquiriesFrame extends JInternalFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JScrollPane sclEnquiry, sclPrevEnqs;
	private JTextArea txaEnquiry;
	private JPanel pnlNorth, pnlQueryLegend, pnlFile, pnlTop;
	private JComboBox<String> cbxEnquiry;
	private DefaultComboBoxModel<String> enquiries;
	private JLabel lblEnquiry, lblFile, lblSelectedFile;
	private JButton btnRun;
	
	private JTable tblPreviousEnquiries;
	private DefaultTableModel tmodel;
	
	private JMenuBar menuBar;
	private JMenu view, file;
	private JMenuItem archive, attachment;
	
	private File afile;
	private Controller controller;
	Student sobj;
	
	private void init(){
		pnlTop = new JPanel(new GridLayout(3,1));
		
		menuBar = new JMenuBar();
		view = new JMenu("View");
		file = new JMenu("File");
		archive = new JMenuItem("Archived Enquiries");
		archive.setIcon(new ImageIcon("src\\images\\archive.png"));
		attachment = new JMenuItem("Attach File");
		attachment.setIcon(new ImageIcon("src\\images\\attach.png"));
		file.add(attachment);
		view.add(archive);
		menuBar.add(file);
		menuBar.add(view);
		
		txaEnquiry = new JTextArea(" [ Please state you enquiries here ]...");
		txaEnquiry.setLineWrap(true);
		txaEnquiry.setWrapStyleWord(true);
		txaEnquiry.setBackground(Color.white);
		sclEnquiry = new JScrollPane(txaEnquiry);
		//sclEnquiry.setPreferredSize(new Dimension(300,59));
		
		lblEnquiry = new JLabel("Service: ");
		btnRun = new JButton("Run");
		
		pnlNorth = new JPanel(new FlowLayout());
		//pnlNorth.setAlignmentY(SwingConstants.TOP);
		enquiries = new DefaultComboBoxModel<String>();
		enquiries.addElement("");
		enquiries.addElement("Apply for Refund");
		enquiries.addElement("Apply for Financial Clearance");
		enquiries.addElement("Generate Semester Fee Statement");
		enquiries.addElement("------- General Enquiries -------");
		enquiries.addElement("Monies owed");
		enquiries.addElement("Account Balance");
		cbxEnquiry = new JComboBox<String>(enquiries);
		cbxEnquiry.setAlignmentY(0);
		
		Border padBorder = BorderFactory.createEmptyBorder(5,5,5,10);
		pnlFile = new JPanel(new FlowLayout());
		lblFile = new JLabel("Selected File: ");
		lblFile.setBackground(Color.black);
		lblFile.setForeground(Color.white);
		lblFile.setOpaque(true);
		lblFile.setBorder(padBorder);
		lblSelectedFile = new JLabel("no file selected.");
		//lblSelectedFile.setBackground(Color.lightGray);
		//lblSelectedFile.setOpaque(true);
		//lblSelectedFile.setBorder(padBorder);
		
		pnlQueryLegend = new JPanel(new GridLayout(1,1));
		pnlQueryLegend.setPreferredSize(new Dimension(500,650));
		pnlQueryLegend.setBorder(
				BorderFactory.createTitledBorder("Enquiry Response"));
		
		tmodel = new DefaultTableModel(){
			private static final long serialVersionUID = 1L;
			@Override
			public boolean isCellEditable(int row, int column){
				return false;
			}
		};
		tmodel.addColumn("");
		tmodel.addColumn("Previous Enquiries");
		tblPreviousEnquiries = new JTable(tmodel);
		
		tblPreviousEnquiries.setRowHeight(20);
		tblPreviousEnquiries.getColumnModel().getColumn(0).setPreferredWidth(20);
		tblPreviousEnquiries.getColumnModel().getColumn(1).setPreferredWidth(150);//63
		tblPreviousEnquiries.setShowVerticalLines(false);
		
		sclPrevEnqs = new JScrollPane(tblPreviousEnquiries);
		sclPrevEnqs.setPreferredSize(new Dimension(170,80));
	}
	
	private void addComponentsToPanels(){
		pnlNorth.add(lblEnquiry);
		pnlNorth.add(cbxEnquiry);
		pnlNorth.add(btnRun);
		
		pnlFile.add(lblFile);
		pnlFile.add(lblSelectedFile);
		
		pnlTop.add(pnlNorth, BorderLayout.NORTH);
		pnlTop.add(sclEnquiry, BorderLayout.CENTER);
		pnlTop.add(pnlFile, BorderLayout.SOUTH);
		
	}
	
	private void addComponentsToWindow(){
		this.getContentPane().add(pnlTop,BorderLayout.NORTH);
		this.getContentPane().add(pnlQueryLegend, BorderLayout.CENTER);//sqlEnquiry;
		this.getContentPane().add(sclPrevEnqs, BorderLayout.WEST);
	}
	
	private void setWindowProperties(){
		this.setFrameIcon(new ImageIcon("src\\images\\enquiry.png"));
		this.setJMenuBar(menuBar);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		//pack();
		this.setSize((int)(screenSize.getWidth()*0.5),(int)(screenSize.getHeight()*.8));// 520,600
		this.setVisible(true);
		//this.setLocation(300, 0);
		this.setLocation((int)(screenSize.getWidth()*0.2), 0);
		//this.setDefaultCloseOperation(JInternalFrame.EXIT_ON_CLOSE);
	}
	
	private void registerListeners(){
		this.btnRun.addActionListener(this);
		this.cbxEnquiry.addActionListener(this);
		this.attachment.addActionListener(this);
		this.archive.addActionListener(this);
	}
	
	public EnquiriesFrame(Student sobj, Controller controller){
		super("Enquiries",false,true,true,true);
		this.controller = controller;
		this.sobj = sobj;
		init();
		addComponentsToPanels();
		addComponentsToWindow();
		setWindowProperties();
		registerListeners();
		btnRun.setEnabled(false);
		txaEnquiry.setEnabled(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == cbxEnquiry){
			if(cbxEnquiry.getSelectedIndex()==0){
				btnRun.setEnabled(false);
				txaEnquiry.setEnabled(false);
			}
			
			if(cbxEnquiry.getSelectedIndex()==1){
				btnRun.setEnabled(true);
				txaEnquiry.setEnabled(true);
			}
			
			if(cbxEnquiry.getSelectedIndex()==2){
				btnRun.setEnabled(true);
				txaEnquiry.setEnabled(true);
			}
			
			if(cbxEnquiry.getSelectedIndex()==3){
				btnRun.setEnabled(true);
				txaEnquiry.setEnabled(true);
			}
			if(cbxEnquiry.getSelectedIndex()==4){
				btnRun.setEnabled(false);
				txaEnquiry.setEnabled(false);
			}
			
			if(cbxEnquiry.getSelectedIndex()==5){
				btnRun.setEnabled(true);
				txaEnquiry.setEnabled(false);
			}
			
			if(cbxEnquiry.getSelectedIndex()==6){
				btnRun.setEnabled(true);
				txaEnquiry.setEnabled(false);
			}
		}
		
		if(e.getSource() == this.attachment){
			if(btnRun.isEnabled()){
				if(cbxEnquiry.getSelectedIndex() ==  5 || cbxEnquiry.getSelectedIndex() == 6)
					JOptionPane.showMessageDialog(this, "File attachment not available for the selected Service.");
				else
					attachFile();
			}else
				JOptionPane.showMessageDialog(this, "No Service Selected!");	
		}
		
		if(e.getSource() == archive){
			JOptionPane.showMessageDialog(this, "No archived enquiries found.");
		}
		
		if(e.getSource() == btnRun){
			
			if(controller.checkSession() !=0 ){
				if(cbxEnquiry.getSelectedIndex() == 1){
					pnlQueryLegend.removeAll();
					pnlQueryLegend.updateUI();
				}
				
				if(cbxEnquiry.getSelectedIndex() == 2){
					pnlQueryLegend.removeAll();
					pnlQueryLegend.updateUI();
				}
				
				if(cbxEnquiry.getSelectedIndex() == 3){
					pnlQueryLegend.removeAll();
					pnlQueryLegend.updateUI();
				}
				
				if(cbxEnquiry.getSelectedIndex() == 4){
					pnlQueryLegend.removeAll();
					pnlQueryLegend.updateUI();
				}
				
				if(cbxEnquiry.getSelectedIndex() == 5){
					pnlQueryLegend.removeAll();
					pnlQueryLegend.updateUI();
					JTextArea temp =  new JTextArea();
					temp.setEditable(false);
					temp.setWrapStyleWord(true);
					temp.setLineWrap(true);
					temp.setText(" MONIES OWED\n *****************\n\n Dear "
					+sobj.getFirstName()+" "+sobj.getLastName()+",\n\n The following are the "
					+"the monies you owe the university: \n"
					+"\n Please ensure you clear all arrears before end of semester.\n Thank you.");
					JScrollPane res = new JScrollPane(temp);
					pnlQueryLegend.removeAll();
					pnlQueryLegend.add(res);
					pnlQueryLegend.updateUI();
				}
				
				if(cbxEnquiry.getSelectedIndex() == 6){
					pnlQueryLegend.removeAll();
					pnlQueryLegend.updateUI();
					JTextArea temp =  new JTextArea();
					temp.setEditable(false);
					temp.setWrapStyleWord(true);
					temp.setLineWrap(true);
					temp.setText(" ACCOUNT BALANCE\n ************************\n\n Dear "
					+sobj.getFirstName()+" "+sobj.getLastName()+",\n\n Your account balance is $"
					+sobj.getAccountBalance()+"(JMD)."
					+" If your account is in arrears please settle that at the accounts office"
							+" before end of semester.\nThank you.");
					JScrollPane res = new JScrollPane(temp);
					pnlQueryLegend.removeAll();
					pnlQueryLegend.add(res);
					pnlQueryLegend.updateUI();
				}
			}else
				JOptionPane.showMessageDialog(this, "Session Expired. Please re-login.");
		}
		
	}
	
	private void attachFile(){
		JFileChooser fileChooser = new JFileChooser();
		FileFilter ff = new FileFilter(){

			@Override
			public boolean accept(File f) {
				if(f.getName().endsWith(".pdf") || f.getName().endsWith(".docx") || f.getName().endsWith(".doc")
						|| f.getName().endsWith(".png") || f.getName().endsWith(".jpg") || f.getName().endsWith(".jpeg"))
					return true;
				return false;
			}

			@Override
			public String getDescription() {
				// TODO Auto-generated method stub
				return "Accepted Files";
			}
			
		};
		fileChooser.setFileFilter(ff);
		//fileChooser.addChoosableFileFilter(ff);
		int returnVal = fileChooser.showDialog(null, "Attach File");
		if(returnVal == JFileChooser.APPROVE_OPTION){
			this.afile = fileChooser.getSelectedFile();
			this.lblSelectedFile.setText(afile.getName());
			JOptionPane.showMessageDialog(this, "File: "+afile.getName()+" attached successfully.");
		}else{
			this.lblSelectedFile.setText("no file selected.");
			JOptionPane.showMessageDialog(this,"File Attachment Unsuccessful!","Attachment Error",JOptionPane.ERROR_MESSAGE);
		}
	}

}
