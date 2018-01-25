package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
import javax.swing.JTextArea;

public class EnquiriesFrame extends JInternalFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JScrollPane sclEnquiry;
	private JTextArea txaEnquiry;
	private JPanel pnlNorth, pnlSouth;
	private JComboBox<String> cbxEnquiry;
	private DefaultComboBoxModel<String> enquiries;
	private JLabel lblEnquiry;
	private JButton btnRun;
	
	private JMenuBar menuBar;
	private JMenu view, file;
	private JMenuItem archive, attachment;
	
	private File afile;
	
	private void init(){
		
		menuBar = new JMenuBar();
		view = new JMenu("View");
		file = new JMenu("File");
		archive = new JMenuItem("Archived Enquiries");
		attachment = new JMenuItem("Attach File");
		attachment.setIcon(new ImageIcon("src\\images\\attach.png"));
		file.add(attachment);
		view.add(archive);
		menuBar.add(file);
		menuBar.add(view);
		
		txaEnquiry = new JTextArea(" [ Please state you enquiries here ]..."); //change this area to grab all enquiries from the database
		txaEnquiry.setLineWrap(true);
		txaEnquiry.setWrapStyleWord(true);
		txaEnquiry.setBackground(Color.white);
		sclEnquiry = new JScrollPane(txaEnquiry);
		//sclEnquiry.setPreferredSize(new Dimension(300,180));
		
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
		
		pnlSouth = new JPanel();
		pnlSouth.setPreferredSize(new Dimension(500,450));
		pnlSouth.setBorder(
				BorderFactory.createTitledBorder("Query Result"));
		
	}
	
	private void addComponentsToPanels(){
		pnlNorth.add(lblEnquiry);
		pnlNorth.add(cbxEnquiry);
		pnlNorth.add(btnRun);
		pnlNorth.add(sclEnquiry);
	}
	
	private void addComponentsToWindow(){
		this.getContentPane().add(pnlNorth,BorderLayout.NORTH);
		this.getContentPane().add(sclEnquiry, BorderLayout.CENTER);
		this.getContentPane().add(pnlSouth ,BorderLayout.SOUTH);
	}
	
	private void setWindowProperties(){
		this.setJMenuBar(menuBar);
		this.setSize(520,600);
		this.setVisible(true);
		this.setLocation(300, 0);
	}
	
	private void registerListeners(){
		this.btnRun.addActionListener(this);
		this.cbxEnquiry.addActionListener(this);
		this.attachment.addActionListener(this);
		this.archive.addActionListener(this);
	}
	
	public EnquiriesFrame(){
		super("Enquiries",false,true,true,true);
		init();
		addComponentsToPanels();
		addComponentsToWindow();
		setWindowProperties();
		registerListeners();
		btnRun.setEnabled(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == cbxEnquiry){
			if(cbxEnquiry.getSelectedIndex()==0){
				btnRun.setEnabled(false);
			}
			
			if(cbxEnquiry.getSelectedIndex()==1){
				btnRun.setEnabled(true);
			}
			
			if(cbxEnquiry.getSelectedIndex()==2){
				btnRun.setEnabled(true);
			}
			
			if(cbxEnquiry.getSelectedIndex()==3){
				btnRun.setEnabled(true);
			}
			if(cbxEnquiry.getSelectedIndex()==4){
				btnRun.setEnabled(false);
			}
			
			if(cbxEnquiry.getSelectedIndex()==5){
				btnRun.setEnabled(true);
			}
			
			if(cbxEnquiry.getSelectedIndex()==6){
				btnRun.setEnabled(true);
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
		
	}
	
	private void attachFile(){
		JFileChooser fileChooser = new JFileChooser();
		int returnVal = fileChooser.showDialog(null, "Attach File");
		if(returnVal == JFileChooser.APPROVE_OPTION){
			this.afile = fileChooser.getSelectedFile();
			JOptionPane.showMessageDialog(this, "File: "+afile.getName()+" attached successfully.");
		}else
			JOptionPane.showMessageDialog(this,"File Attachment Unsuccessful!","Attachment Error",JOptionPane.ERROR_MESSAGE);
	}

}
