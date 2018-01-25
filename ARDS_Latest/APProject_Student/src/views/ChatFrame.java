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

public class ChatFrame extends JInternalFrame implements ActionListener, KeyListener, FocusListener{
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
	
	JTable tblOnline;
	DefaultTableModel tmodel;
	Controller controller;
	ArrayList<String> employeesOnline;
	String employeeName;
	
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
	
	public ChatFrame(Controller controller){
		super("Chat",false,true,true,true);
		this.controller = controller;
		init();
		addComponentsToPanels();
		addComponentsToWindow();
		setWindowProperties();
		registerListener();
		
		updateOnline(controller.getEmployeesOnline());//initialize table
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
				
			}
		}
		
		if(act.getSource() == btnRefresh){
			if(controller.checkSession() != 0){
				updateOnline(controller.getEmployeesOnline());
			}else
				JOptionPane.showMessageDialog(null, "Session Expired. Please re-login.");
		}
	}
	
	public void updateOnline(ArrayList<String> list){
		employeesOnline = list;
		tmodel.getDataVector().removeAllElements();
		tmodel.fireTableDataChanged();
		for(String item : list){
			String[] empso = item.split(" ");
			JLabel temp = new JLabel();
			employeeName = new String(empso[0]+" "+empso[1]);
			temp.setIcon(new ImageIcon("src\\images\\use.png"));
			tmodel.addRow(new Object[]{temp,employeeName});
		}
		
		tmodel.fireTableDataChanged();
	}
	
	public void setConnection(int index){
		String[] empso = employeesOnline.get(index).split(" ");
		txtHostname.setText(empso[2]);
		txtPort.setText(empso[3]);
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

}
