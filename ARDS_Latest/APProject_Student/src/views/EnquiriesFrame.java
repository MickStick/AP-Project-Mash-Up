package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
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
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import controller.Controller;
import models.Student;
import views.ChatFrame.tblOnlineRenderer;

public class EnquiriesFrame extends JInternalFrame implements ActionListener {
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
	
	private JTable tblPreviousEnquiries, tblRefund;
	private DefaultTableModel tmodel;
	
	private JMenuBar menuBar;
	private JMenu view, file;
	private JMenuItem archive, attachment;
	
	private File afile;
	private Controller controller;
	Student sobj;
	private ArrayList<String> feeslist, prevEnqList;
	
	private void init(){
		pnlTop = new JPanel(new GridLayout(1,1));
		
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
		
		txaEnquiry = new JTextArea(" [ Please state your enquiries here ]...");
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
		tblPreviousEnquiries.getColumnModel().getColumn(0).setCellRenderer(new tblEnqRenderer());
		
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
		//pnlTop.add(sclEnquiry, BorderLayout.CENTER);
		//pnlTop.add(pnlFile, BorderLayout.CENTER);
		
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
		this.tblPreviousEnquiries.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				if(e.getClickCount() == 2){ 
					showPrevEnq(tblPreviousEnquiries.getSelectedRow());
				}
			}
		});
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
		setPreviousEnquiries(controller.prevEnquiries());
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
				if(controller.checkSession() !=0)
					refundPrep(controller.prepRefund(sobj.getStudentID()));
				else
					JOptionPane.showMessageDialog(this, "Session Expired!. Please re-login.");
				
			}
			
			if(cbxEnquiry.getSelectedIndex()==2){
				btnRun.setEnabled(true);
				txaEnquiry.setEnabled(true);
				if(controller.checkSession() !=0)
					clearancePrep();
				else
					JOptionPane.showMessageDialog(this, "Session Expired!. Please re-login.");
			}
			
			if(cbxEnquiry.getSelectedIndex()==3){
				btnRun.setEnabled(true);
				txaEnquiry.setEnabled(true);
				if(controller.checkSession() !=0)
					feeStatementPrep();
				else
					JOptionPane.showMessageDialog(this, "Session Expired!. Please re-login.");
			}
			if(cbxEnquiry.getSelectedIndex()==4){
				btnRun.setEnabled(false);
				txaEnquiry.setEnabled(false);
				pnlQueryLegend.removeAll();
				pnlQueryLegend.updateUI();
			}
			
			if(cbxEnquiry.getSelectedIndex()==5){
				btnRun.setEnabled(true);
				txaEnquiry.setEnabled(false);
				moniesOwedPrep();
			}
			
			if(cbxEnquiry.getSelectedIndex()==6){
				btnRun.setEnabled(true);
				txaEnquiry.setEnabled(false);
				accountBalancePrep();
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
			if(controller.checkSession() != 0){
				setPreviousEnquiries(controller.prevEnquiries());
				if(prevEnqList.isEmpty())
					JOptionPane.showMessageDialog(this, "No archived enquiries found.");
			}else
				JOptionPane.showMessageDialog(this, "Session Expired. Please re-login.");
		}
		
		if(e.getSource() == btnRun){
			
			if(controller.checkSession() !=0 ){
				if(cbxEnquiry.getSelectedIndex() == 1){
					//pnlQueryLegend.removeAll();
					//pnlQueryLegend.updateUI();
					sendRefundEnquiry();
					setPreviousEnquiries(controller.prevEnquiries());
				}
				
				if(cbxEnquiry.getSelectedIndex() == 2){
					sendClearanceEnquiry();
					setPreviousEnquiries(controller.prevEnquiries());
				}
				
				if(cbxEnquiry.getSelectedIndex() == 3){
					sendFeeStatementEnquiry();
					setPreviousEnquiries(controller.prevEnquiries());
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
	
	//Updates Enquiry Response Pane when Apply for Refund is selected.
	public void refundPrep(ArrayList<String> list){
		feeslist = new ArrayList<>(list);
		JPanel pnl = new JPanel(new GridLayout(3,1));
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("Fee ID");
		model.addColumn("Fee");
		model.addColumn("Refund");
		tblRefund = new JTable(model){
			private static final long serialVersionUID = 1L;
			@SuppressWarnings("unchecked")
			@Override
			public Class getColumnClass(int column){
				switch(column){
				case 2: return Boolean.class;
				default: return String.class;
				}
			}
		};
		for(String item : list){
			String[] items = item.split(" ");
			model.addRow(new Object[]{items[0],items[1],false});
		}
		pnl.add(new JScrollPane(tblRefund));
		pnl.add(sclEnquiry);//Scroll pane with txaEnquiry(text area component) for additional inquiry statements
		pnl.add(pnlFile);
		afile = null;this.lblSelectedFile.setText("no file selected.");
		pnlQueryLegend.removeAll();
		pnlQueryLegend.add(pnl);
		pnlQueryLegend.updateUI();
	}
	
	//sends refund inquiry to student model through controller when run is clicked
	private void sendRefundEnquiry(){
		
		String message = null;
		int checker[] = {0,0};
		//Checker[0] will will store the row number where Refund is ticked.
		//Checker[1] will check if multiple rows are ticked. (if > 1: reject)
		for(int i=0; i<tblRefund.getRowCount(); i++){
			if((Boolean)tblRefund.getValueAt(i,2)){//check if value returned from col3 is true == ticked for each row
				checker[0] = i;
				checker[1]++;
			}		
		}
		switch(checker[1]){
		case 0:
			if(tblRefund.getRowCount()>0)
				JOptionPane.showMessageDialog(this, "No Refund selected.");
			break;
		case 1:
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			//System.out.println(dateFormat.format(date)); //2016/11/16 12:08:43
			String info[] = feeslist.get(checker[0]).split(" ");
			message ="REFUND APPLICATON> [ "+dateFormat.format(date)+" ]\n"
			+sobj.getFirstName()+" "+sobj.getLastName()+" with ID#: "+sobj.getStudentID()
			+" want a refund on the amount $"+info[1]+" (FEE ID#: "+info[0]+")\n"
			+"Account Balance: $"+sobj.getAccountBalance()+"\n"
			+"Email: "+sobj.getEmail()+"\n"
			+"Phone Num: "+sobj.getPhoneNumber()+"\n\n"
			+"[FURTHER INFORMATION]\n"+txaEnquiry.getText();
			controller.sendEnquiry(message, afile);
			JOptionPane.showMessageDialog(this, message);
			JOptionPane.showMessageDialog(this, "Enquiry sent!.");
			break;
		default:
			JOptionPane.showMessageDialog(this, "Multiple Refund selection not allowed.");
			break;
		}
	}
	
	//preps enquiry response frame
	public void clearancePrep(){
		JPanel pnl = new JPanel(new GridLayout(2,1));
		txaEnquiry.setText(" [ Please state your enquiries here ]...");
		afile = null;this.lblSelectedFile.setText("no file selected.");
		pnl.add(sclEnquiry);
		pnl.add(pnlFile);
		pnlQueryLegend.removeAll();
		pnlQueryLegend.add(pnl);
		pnlQueryLegend.updateUI();
	}
	//to be called when run is clicked
	private void sendClearanceEnquiry(){
		String message = null;
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		//System.out.println(dateFormat.format(date)); //2016/11/16 12:08:43
		message ="FINANCIAL CLEARANCE APPLICATON> [ "+dateFormat.format(date)+" ]\n"
		+sobj.getFirstName()+" "+sobj.getLastName()+" with ID#: "+sobj.getStudentID()
		+" want to apply for  financial clearance.\n"
		+"Account Balance: $"+sobj.getAccountBalance()+"\n"
		+"Email: "+sobj.getEmail()+"\n"
		+"Phone Num: "+sobj.getPhoneNumber()+"\n\n"
		+"[FURTHER INFORMATION]\n"+txaEnquiry.getText();
		controller.sendEnquiry(message.trim(), afile);
		JOptionPane.showMessageDialog(this, message);
		JOptionPane.showMessageDialog(this, "Enquiry sent!.");
	}
	//preps enquiry response frame
	private void feeStatementPrep(){
		JPanel pnl = new JPanel(new GridLayout(2,1));
		txaEnquiry.setText(" [ Please state your enquiries here ]...");
		afile = null;this.lblSelectedFile.setText("no file selected.");
		pnl.add(sclEnquiry);
		pnl.add(pnlFile);
		pnlQueryLegend.removeAll();
		pnlQueryLegend.add(pnl);
		pnlQueryLegend.updateUI();
	}
	//to be called when run is clicked
	private void sendFeeStatementEnquiry(){
		String message = null;
	
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			//System.out.println(dateFormat.format(date)); //2016/11/16 12:08:43
			message ="SEMESTER FEE APPLICATON> [ "+dateFormat.format(date)+" ]\n"
			+sobj.getFirstName()+" "+sobj.getLastName()+" with ID#: "+sobj.getStudentID()
			+" want to generate his/her  semester fee statement.\n"
			+"Account Balance: $"+sobj.getAccountBalance()+"\n"
			+"Email: "+sobj.getEmail()+"\n"
			+"Phone Num: "+sobj.getPhoneNumber()+"\n\n"
			+"[FURTHER INFORMATION]\n"+txaEnquiry.getText();
			controller.sendEnquiry(message.trim(), afile);
			JOptionPane.showMessageDialog(this, message);
			JOptionPane.showMessageDialog(this, "Enquiry sent!.");
	}
	
	
	private void moniesOwedPrep(){
		pnlQueryLegend.removeAll();
		pnlQueryLegend.updateUI();
	}
	
	private void accountBalancePrep(){
		pnlQueryLegend.removeAll();
		pnlQueryLegend.updateUI();
	}
	
	//to populate the previous enquiry table
	private void setPreviousEnquiries(ArrayList<String> enqlist){
		prevEnqList = new ArrayList<>(enqlist);
		tmodel.getDataVector().removeAllElements();
		tmodel.fireTableDataChanged();
		for(String item : enqlist){
			String[] enq = item.split(">");
			tmodel.addRow(new Object[]{new JLabel(),enq[0]});
		}
		tmodel.fireTableDataChanged();
	}
	//to display previous enquiry in new jframe
	private void showPrevEnq(int row){
		JFrame temp = new JFrame();
		temp.setTitle("Previous Enquiry");
		temp.setIconImage(new ImageIcon("src\\images\\archive.png").getImage());
		temp.setSize(new Dimension(600,400));
		temp.setLocationRelativeTo(null);
		temp.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		JTextArea txt = new JTextArea(prevEnqList.get(row));
		txt.setLineWrap(true);
		txt.setWrapStyleWord(true);
		txt.setEditable(false);
		txt.setBackground(Color.lightGray);
		txt.setMargin(new Insets(10,10,10,10));
		JScrollPane spane = new JScrollPane(txt);
		temp.add(spane,BorderLayout.CENTER);
		temp.setVisible(true);
	}
	//....................................................
	class tblEnqRenderer extends DefaultTableCellRenderer{
		private static final long serialVersionUID = 1L;
		JLabel lbl = new JLabel();
		@Override
		public Component getTableCellRendererComponent(JTable tblOnline, Object obj, boolean isSelected, boolean hasFocus, int row,
				int column) {
			lbl.setOpaque(false);
			lbl.setIcon(new ImageIcon("src\\images\\archive.png"));
			return lbl;
		}
	}
	//...................................................

}
