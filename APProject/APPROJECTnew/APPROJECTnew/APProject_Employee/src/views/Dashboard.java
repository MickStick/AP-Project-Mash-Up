package views;

import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import models.Employee;

//DASHBOARD CODE GOES HERE
public class Dashboard extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JDesktopPane desktop;
	private JMenuBar menuBar;
	private JMenu action, about, help;
	private JMenuItem menuItemChat, menuItemEnquiries, menuItemAbout, menuItemHelp;
	ChatFrame chatFrame;
	EnquiriesFrame enquiryFrame;
	
	
	
	private void init(){
		//ImageIcon image = new ImageIcon(this.getClass().getResource("images\\droid.jpg"));
		//ImageIcon image = new ImageIcon("src\\images\\droid.png");
		//Image img = image.getImage();
		
		desktop = new JDesktopPane(){
			private static final long serialVersionUID = 1L;
			ImageIcon icon = new ImageIcon("src\\images\\ards.png");
			Image image = icon.getImage();
			Image newimage = image.getScaledInstance(1500, 1000, Image.SCALE_SMOOTH);
			@Override
			public void paintComponent(Graphics g){
				super.paintComponent(g);
				g.drawImage(newimage, 0, 0, getWidth(), getHeight(), this);
			}
		};
		menuBar = new JMenuBar();
		action = new JMenu("Actions");
		action.setMnemonic('A');
		about = new JMenu("About");
		about.setMnemonic('u');
		help = new JMenu("Help");
		help.setMnemonic('e');
		menuItemChat = new JMenuItem("Chat");
		menuItemEnquiries = new JMenuItem("Enquiries Form");
		menuItemAbout = new JMenuItem("ARDS");
		menuItemAbout.setToolTipText("about ARDS");
		menuItemHelp = new JMenuItem("Tip");
		//desktop.setLayout(new GridLayout(1,3));
		//chatFrame = new ChatFrame();
	}
	
	private void addMenuItemsToMenu(){
		action.add(menuItemChat);
		action.add(menuItemEnquiries);
		about.add(menuItemAbout);
		help.add(menuItemHelp);
	}
	private void addMenusToMenuBar(){
		menuBar.add(action);
		menuBar.add(about);
		menuBar.add(help);
	}
	private void addComponentsToWindow(){
		this.add(desktop);
	}
	
	private void registerListeners(){
		menuItemChat.addActionListener(this);
		menuItemEnquiries.addActionListener(this);
		menuItemAbout.addActionListener(this);
		menuItemHelp.addActionListener(this);
	}
	
	private void setWindowProperties(){
		this.setJMenuBar(menuBar);
		this.setTitle("Accounts Receivable Distance Service [Employee Dashboard]");
		this.setSize(900,600);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.setLayout(new GridLayout(1,1));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public Dashboard(Employee eobj){
		init();
		addMenuItemsToMenu();
		addMenusToMenuBar();
		addComponentsToWindow();
		setWindowProperties();
		registerListeners();
		desktop.add(new ProfileFrame(eobj));
		enquiryFrame = new EnquiriesFrame();
		desktop.add(enquiryFrame);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == menuItemChat){
			if(!isOpen(chatFrame)){
				chatFrame = new ChatFrame();
				desktop.add(chatFrame);
			}else
				JOptionPane.showMessageDialog(null, "Chat is already open!");
		}
		
		if(e.getSource() == menuItemEnquiries){
			if(!isOpen(enquiryFrame)){
				enquiryFrame = new EnquiriesFrame();
				desktop.add(enquiryFrame);
			}else
				JOptionPane.showMessageDialog(null, "Enquiries Form is already open!");
		}
		
		if(e.getSource() == menuItemHelp)
			JOptionPane.showMessageDialog(this, "No help. Every soul for themselves. :(");
		
	}
	
	private boolean isOpen(JInternalFrame frame){
		for(JInternalFrame jif : desktop.getAllFrames()){
			if(jif.equals(frame))
				return true;
		}
		return false;
	}
	
	/*
	public static void getStudentData(models.Employee eobj){
		
	}
	public void updateDashboard(models.Employee eobj){
		//this.sobj = sobj;
	}
	*/
}
