package views;

import java.awt.Frame;
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
import javax.swing.JScrollPane;

import controller.Controller;
import models.Student;

//DASHBOARD CODE GOES HERE
public class Dashboard extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JDesktopPane desktop;
	private JMenuBar menuBar;
	private JMenu action, about, help;
	private JMenuItem menuItemChat, menuItemEnquiries, menuItemAbout, menuItemHelp, menuItemLogout, menuItemExit;
	ChatFrame chatFrame;
	EnquiriesFrame enquiryFrame;
	Controller controller;
	private Student sobj;
	
	
	
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
		menuItemChat.setIcon(new ImageIcon("src\\images\\chat.png"));
		menuItemEnquiries = new JMenuItem("Enquiries Form");
		menuItemEnquiries.setIcon(new ImageIcon("src\\images\\enquiry.png"));
		menuItemAbout = new JMenuItem("ARDS");
		menuItemAbout.setToolTipText("about ARDS");
		menuItemHelp = new JMenuItem("Tip");
		menuItemLogout = new JMenuItem("Logout");
		menuItemLogout.setIcon(new ImageIcon("src\\images\\logout.png"));
		menuItemExit = new JMenuItem("Exit");
		menuItemExit.setIcon(new ImageIcon("src\\images\\exit.png"));
		//desktop.setLayout(new GridLayout(1,3));
		//chatFrame = new ChatFrame();
	}
	
	private void addMenuItemsToMenu(){
		action.add(menuItemEnquiries);
		action.add(menuItemChat);
		action.add(menuItemLogout);
		action.add(menuItemExit);
		
		about.add(menuItemAbout);
		help.add(menuItemHelp);
	}
	private void addMenusToMenuBar(){
		menuBar.add(action);
		menuBar.add(about);
		menuBar.add(help);
	}
	private void addComponentsToWindow(){
		JScrollPane desk = new JScrollPane(desktop);
		this.add(desk);
		
	}
	
	private void registerListeners(){
		menuItemChat.addActionListener(this);
		menuItemEnquiries.addActionListener(this);
		menuItemAbout.addActionListener(this);
		menuItemHelp.addActionListener(this);
		menuItemLogout.addActionListener(this);
		menuItemExit.addActionListener(this);
	}
	
	private void setWindowProperties(){
		this.setIconImage((new ImageIcon("src\\images\\logo.png")).getImage());
		this.setJMenuBar(menuBar);
		this.setTitle("Accounts Receivable Distance Service [Student Dashboard]");
		this.setSize(999,800);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.setLayout(new GridLayout(1,1));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public Dashboard(Student sobj, Controller controller){
		this.controller = controller;
		this.sobj = sobj;
		init();
		addMenuItemsToMenu();
		addMenusToMenuBar();
		addComponentsToWindow();
		setWindowProperties();
		registerListeners();
		Controller.logger(" Student client dashboard opened...");
		desktop.add(new ProfileFrame(sobj));
		menuItemEnquiries.doClick();//allows for an instance of enquiry frame to be open when the dashboard opens
		this.setExtendedState(Frame.MAXIMIZED_BOTH);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == menuItemChat){
			if(controller.checkSession() != 0){
				if(!isOpen(chatFrame)){
					chatFrame = new ChatFrame(sobj,controller);
					desktop.add(chatFrame);
				}else
					JOptionPane.showMessageDialog(this, "Chat is already open!");
			}else{
				Controller.logger(" Session expired...");
				JOptionPane.showMessageDialog(this, "Session expired! Please re-login.");
			}
		}
		
		if(e.getSource() == menuItemEnquiries){
			if(controller.checkSession() != 0){
				if(!isOpen(enquiryFrame)){
					enquiryFrame = new EnquiriesFrame(sobj,controller);
					desktop.add(enquiryFrame);
				}else
					JOptionPane.showMessageDialog(this, "Enquiries Form is already open!");
			}else{
				Controller.logger(" Session expired...");
				JOptionPane.showMessageDialog(this, "Session expired! Please re-login.");
			}
				
		}
		
		if(e.getSource() == menuItemHelp)
			JOptionPane.showMessageDialog(this, "No help. Every soul for themselves. :(");
		
		if(e.getSource() == menuItemLogout){
			if(controller.checkSession() != 0){
				int opt = JOptionPane.showConfirmDialog(this, "Are you sure you want to LOGOUT?");
				if(opt == JOptionPane.OK_OPTION){
					controller.attemptLogout();
					Controller.logger(" Logout Successful... ?");
					closeInternalFrames();// chatFrame.dispose();
				}
			}else{
				Controller.logger(" Session expired...");
				JOptionPane.showMessageDialog(this, "Logged Out or Session Expired.");
			}
				
		}
		
		if(e.getSource() == menuItemExit){
			if(controller.checkSession() != 0){
				int opt = JOptionPane.showConfirmDialog(this, "Are you sure you want to EXIT?");
				if(opt == JOptionPane.OK_OPTION){
					controller.attemptLogout();
					Controller.logger(" Student client logged out and exited...");
					System.exit(0);
				}
			}else{
				Controller.logger(" Session expired... Dashboard Closed");
				System.exit(0);
			}
				
		}
		
	}//ACTION PERFORMED END
	
	private boolean isOpen(JInternalFrame frame){
		for(JInternalFrame jif : desktop.getAllFrames()){
			if(jif.equals(frame))
				return true;
		}
		return false;
	}
	
	private void closeInternalFrames(){
		desktop.removeAll();
		desktop.updateUI();
	}
	
	public void logout(int sessionID){
		if(sessionID == 0)
			JOptionPane.showMessageDialog(this, "Successfully Logged Out.");
		if(sessionID == 404)
			JOptionPane.showMessageDialog(this, "Logged Out or Session Expired.");
	}
	

}
