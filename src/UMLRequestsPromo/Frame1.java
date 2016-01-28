/**
 * This software was built by Filipe Neves
 * Date: 06/12/2015
 * This program, UMLRequestPromo allows a client to Request, confirms and pay a Promotion
 * Meanwhile, you can login as ADMIN to see the request, setup them, and set them as completed.
 * The program implements Multithread, GUI, and Design patterns.
 */


/**------------------------------------------------------------------------------------- 
 * Design Patterns:
In order to build this program, I checked about design patterns informations in : 
http://www.tutorialspoint.com/design_pattern/ 

I found that because Design Patterns can be defined in different categories, I could use more than one design pattern
idea for different parts of the code.
The main design pattern here is Template Pattern.
Template pattern was used about the major class in our program : Promotions.
Using template pattern, Promotion class was build as abstract, with all type of promotions being concrete classes 
that specified what "type" of class was build.When the client in the interface describes the type of
 promotion he wants, the object of one concrete class is created.

Overall that are some design patterns principles used in another parts of the code.
CritteriaPatern works as a example of principles being used.
This program uses list of objects: list of DraftedPromotions, StartedPromotions, CompletedPromotions.
Each object enters list defined about variables of Promotion class, that are changed while executing.
*/

package UMLRequestsPromo;
import java.awt.EventQueue;
//import java.util.HashMap;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;

import java.awt.SystemColor;


import UMLRequestsPromo.Client;
import UMLRequestsPromo.Promotion;

import javax.swing.JLabel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.GridLayout;
import javax.swing.JTextArea;
import javax.swing.border.MatteBorder;
import javax.swing.JTextField;
import java.awt.Dimension;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JSeparator;
import javax.swing.JPasswordField;

public class Frame1{

	// The JFrame that represents the window itself:
	static JFrame frame;
	
	// The JFrame components that will be used
	//*****************
	//JPanels are substrutures. panel_3 and panel_4 are for Client and Admin, respectively.
	static JPanel panel_3;
	static JPanel panel_4;
	static JPanel panel;
	static JPanel panel_1;
	static JPanel panel_2;
	//This two JLabels are used to build the title of the program.
	static JLabel header;
	static JLabel header2;
	//This labels are used to print Promotions info in different parts of the application.
	static JLabel PPromotion;
	static JLabel SPromotion;
	static JLabel  auxlabel1;
	static JLabel  auxlabel2;
	static JLabel  auxlabel3;
	static JLabel  auxlabel4; 
	//This two buttons makes the very first menu (Admin x Client)
	static JButton button1;
	static JButton button2;
	//This two buttons are used to logout as Client or as Admin.
	static JButton c_logout;
	static JButton a_logout;
	//This buttons are for options: RequestPromotions, Watch Drafts, Waiting Payment
	//								Set as pending, Set as completed.
	static JButton buttonRPromotion;
	static JButton buttonWDraft;
	static JButton buttonWPayment; 
	static JButton buttonSPending;
	static JButton buttonSCompleted;
	//This two buttons are used many times during the client or admin(aminor) application.
	static JButton minorButton;
	static JButton aminorButton;
	//The last three buttons: Confirm drafted promotion, Cancel drafted promotion, Next promotion to analyse.
	static JButton confirm;
	static JButton cancel;
	static JButton PNext;
	//Two Labels to print instruction to login as client/admin
	static JLabel nameclient;
	static JLabel company_message;
	static JLabel nameadmin;
	static JLabel admin_message;
	//This are used to read the company_name(client) and password(Admin) at login.
	static JTextField company_name;
	static JPasswordField passwordField;
	//Two minor components to help the visual.
	static JComboBox P_comboBox;
	static JSeparator Separator;
	
	//The SwingWorker thread that count the promotions in each category.
	// We decided work with SwingWorker instead of regular multithread because JSwing is not thread safe.
	private static SwingWorker<Void, Void> promotion_numbers;
	
	
// Now about the data variable, not directly interface related.
//-------------------------------------------------------------->>
	
	//each promotion has its own identifier,this is used for parameters.
		static int identifier = 0; 
		
		//list of clients
		static List<Client> clientsList = new ArrayList<Client> ();

		//list of all promotions
		static List<Promotion> promotionList = new ArrayList<Promotion> ();

		//list of promotions with draft projects ready to be analyzed
		static List<Promotion> draftProjectList = new ArrayList<Promotion> ();

		//list of promotions that are waiting for draft processes
		static List<Promotion> pendingList = new ArrayList<Promotion> ();
		
		//list of promotions that were started
		static List<Promotion> startedList = new ArrayList<Promotion> ();
		
		//list of promotions that were completed and are waiting payment
		static List<Promotion> completedList = new ArrayList<Promotion> ();
		
		//index to correlate clients with the respective projects in the both lists 
		static int index = 0;
		
		static String userType = null;   // Client login
		static String userType2 = null;  // Admin login  
		
		//Used to pass a new client Object as parameter.
		static Client client = null;
		static Staff employee;
		
		// Variables used in the thread that determines the number of promotions in each state.
		static int pstart = 0;
		static int pdrafted =0, pcompleted=0, ppending=0, ptocomplete=0;
		static boolean safe_value = false, in_cmenu = false, in_amenu = false ;
		
		//Auxiliary variables
		static boolean test;
		static String aux;
		static int janelas = 0;
	    static boolean c_logged = false;
	    static boolean a_logged = false;
	    static int c_option = 0, a_option = 0;
	    static int var, i = 0, tempIndex = 0, j=0;
		static int c_part= 0, a_part = 0;
//<<--------------------------------------------------------------		
    // End of the regular variables used in the "UMLRequestPromo"
		

		
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Frame1 window = new Frame1();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
}
	
	/**
	 * Create the application.
	 */
	public Frame1() {
		initialize();	
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
	
		//This constructor initializes and set the start/default option to each JComponent
		//The initialize() is where most events_handlers are specified too.
		//Event handlers that are used by another components were outspaced to procedures.
		
		//Starting with the JFrame itself.
		frame = new JFrame();
	    //Adjust color, size and minor variables to the JFrame frame object.
		frame.getContentPane().setBackground(SystemColor.inactiveCaptionBorder);
		frame.setBackground(new Color(255, 255, 255));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 500);
		frame.getContentPane().setLayout(null);
		
	//Creates the panel that works as the header in the window. 
	    panel = new JPanel();
		panel.setBackground(SystemColor.textHighlight);
		panel.setBounds(0, 0, 500, 60);
		frame.getContentPane().add(panel);
		panel.setLayout(new BorderLayout(0, 0));
	//The text that's in the header.		
	    header = new JLabel("Welcome to BusyPoint!");
		panel.add(header, BorderLayout.CENTER);
		header.setBackground(Color.RED);
	    header.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 18));
		header.setHorizontalAlignment(SwingConstants.CENTER);
	//The 2 logout options  are possible in the header after login
		c_logout = new JButton("Loggout as UserType ");
		c_logout.setVisible(false);
		panel.add(c_logout, BorderLayout.NORTH);
		a_logout = new JButton("Loggout as ADMIN ");
		a_logout.setVisible(false);
		
	//Creates the panel that will work to Client App
		  panel_3 = new JPanel();
		  panel_3.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		  panel_3.setBounds(0, 60, 500, 400);
		  frame.getContentPane().add(panel_3);
		  panel_3.setLayout(null);
		  panel_3.setVisible(false);
		
    //Creates the panel that will work to Admin App
		  panel_4 = new JPanel();
		  panel_4.setBounds(500, 60, 500, 400);
		  frame.getContentPane().add(panel_4);
		  panel_4.setLayout(null);
		  panel_4.setVisible(false);
		  
    //Creates the password field that will be used to login the Admin		  
		  passwordField = new JPasswordField();
		  passwordField.setBounds(277, 134, 193, 29);
		  panel_4.add(passwordField);
		  passwordField.setVisible(true);
		  passwordField.setHorizontalAlignment(SwingConstants.CENTER);
		
    //Creates the label that will work to get the Admin password.
		  nameadmin = new JLabel("  Insert the admin password:  ");
		  nameadmin.setBounds(10, 135, 257, 28);
		  nameadmin.setDisabledIcon(null);
		  nameadmin.setBorder(new MatteBorder(3, 2, 3, 2, (Color) new Color(51, 153, 255)));
		  nameadmin.setFont(new Font("Segoe UI", Font.ITALIC, 16));
		  panel_4.add(nameadmin);
		  nameadmin.setHorizontalAlignment(SwingConstants.LEFT);
		  
	//Just a message to explain how to proceed with inserting the company data.   
		    company_message = new JLabel("Insert the name of the company, then ENTER. If the company does not exist, it will be created.");
		    company_message.setFont(new Font("Tahoma", Font.PLAIN, 9));
		    company_message.setBounds(20, 174, 460, 59);
		    company_message.setVerticalAlignment(SwingConstants.TOP);
		    panel_3.add(company_message);
		  
	//Just a message to explain how to proceed with inserting the Admin password   
		    admin_message = new JLabel("Insert the password for ADMIN, then ENTER.");
		    admin_message.setFont(new Font("Tahoma", Font.PLAIN, 9));
		    admin_message.setBounds(20, 174, 460, 59);
		    admin_message.setVerticalAlignment(SwingConstants.TOP);
		    panel_4.add(admin_message);
		  
	 //Creates the label that will work to get the Client name
		  nameclient = new JLabel("  Insert the name of the company:  ");
		  nameclient.setBounds(10, 135, 257, 28);
		  nameclient.setBorder(new MatteBorder(3, 2, 3, 2, (Color) new Color(51, 153, 255)));
		  panel_3.add(nameclient);
		  nameclient.setFont(new Font("Segoe UI", Font.ITALIC, 16));
		  
	  //This is the JTextField where will catch the client login.
		  company_name = new JTextField();  		    
		  company_name.setLocation(277, 134);
		  company_name.setSize(new Dimension(193, 29));
		  company_name.setHorizontalAlignment(SwingConstants.LEFT);
          company_name.setFont(new Font("Segoe UI", Font.PLAIN, 18));
	      panel_3.add(company_name);
		 
	//Three buttons are used in the client Menu: 	    
	//The button to request promotions. (client)	    
		    buttonRPromotion = new JButton("Request a Promotion.");
		    buttonRPromotion.setPreferredSize(new Dimension(150, 25));
		    buttonRPromotion.setVisible(false);
		    buttonRPromotion.setForeground(new Color(0, 255, 0));
		    buttonRPromotion.setFont(new Font("Segoe UI", Font.BOLD, 14));
		    buttonRPromotion.setBounds(10, 125, 225, 75);
		    panel_3.add(buttonRPromotion);
		    
	//The button to watch the draft projects.  (client)	    
		    buttonWDraft = new JButton("View draft projects.");
		    buttonWDraft.setVisible(false);
		    buttonWDraft.setForeground(new Color(255, 215, 0));
		    buttonWDraft.setFont(new Font("Segoe UI", Font.BOLD, 14));
		    buttonWDraft.setBounds(245, 125, 225, 75);
		    panel_3.add(buttonWDraft);
		    
		    PNext      = new JButton("Next");

   //The button to see the projects waiting for payment.
		    buttonWPayment = new JButton("View projects waiting payment.");
		    buttonWPayment.setVisible(false);
		    buttonWPayment.setForeground(new Color(255, 0, 0));
		    buttonWPayment.setFont(new Font("Segoe UI", Font.BOLD, 14));
		    buttonWPayment.setBounds(106, 211, 304, 75);
		    panel_3.add(buttonWPayment);
     
	//Two buttons are used at max at the admin Menu:
	//The button to setup the pending promotions	    
		    buttonSPending = new JButton("Check the pending promotions.");
		    buttonSPending.setVisible(false);
		    buttonSPending.setForeground(new Color(255, 215, 0));
		    buttonSPending.setFont(new Font("Segoe UI", Font.BOLD, 12));
		    buttonSPending.setBounds(10, 125, 225, 75);
		    panel_4.add(buttonSPending);

   //The button to set the promotions as completed
		    buttonSCompleted = new JButton("Set promotions as completed.");
		    buttonSCompleted.setVisible(false);
		    buttonSCompleted.setForeground(new Color(0, 255, 0));
		    buttonSCompleted.setFont(new Font("Segoe UI", Font.BOLD, 14));
		    buttonSCompleted.setBounds(245, 125, 225, 75);
		    panel_4.add(buttonSCompleted);
		   
	//Creates the ComboBox that will be used to chose which promotion are being requested.
		    P_comboBox = new JComboBox();
		    P_comboBox.setVisible(false);
		    P_comboBox.setModel(new DefaultComboBoxModel(new String[] {"Chose one type :", "A magazine advert", "A newspaper advert", "A poster advert", "A radio advert", "A TV advert", "A Web advert", "A e-mail advert"}));
		    P_comboBox.setName("Vixi\r\nSer\u00E1\r\nQue \r\n\u00C9\r\nAssim\r\n");
		    P_comboBox.setBounds(22, 134, 213, 37);
		    panel_3.add(P_comboBox);
		    
     //Creates a separator between Client and Admin sides. (will help to see as the threads later)
		    Separator = new JSeparator();
		   	Separator.setBounds(495, 0, 50, 500);
		 	panel_3.add(Separator);
		    Separator.setForeground(Color.BLACK);
		    Separator.setVisible(true);
		    Separator.setOrientation(SwingConstants.VERTICAL);
		    
	//Creates a auxiliar button that is used in different and sample parts of the Client code.
		    minorButton = new JButton("----");
		    minorButton.setVisible(false);
		    minorButton.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		    panel_3.add(minorButton);
		    
	//Creates a auxiliar button that is used in different and sample parts of the Admin code.
		    aminorButton = new JButton("----");
		    aminorButton.setVisible(false);
		    aminorButton.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		    panel_4.add(aminorButton);
		    
	//---------------------------	    
    //Creates the panel that's used within the 2 first buttons as Start Menu.
		panel_1 = new JPanel();
		panel_1.setBounds(0, 60, 500, 400);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(new GridLayout(0, 2, 0, 0));
		
		//Button2 equals the Client-thread
		button2 = new JButton("Log in as Client");
		button2.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		panel_1.add(button2);
		
	 //Button1 equals the Admin thread
		button1 = new JButton("Log in as Admin");
		panel_1.add(button1);
		button1.setForeground(new Color(0, 0, 0));
		button1.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		
		//Below are the event handlers of the JComponents.
		//							******** 
		
	
			//The actions for when button1 and button2 are pressed. 
			//**********************************************************
				button1.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent arg0) {
						//If one login is requested, the frame "divides" in two sides.
						Frame1.janelas++;
						//The frame expands and the button1 isn't need.
						button1.setVisible(false);
						frame.setSize(1000, 500);
						panel_4.setVisible(true);
						Admin_frame();
					}});
				button2.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						//If one login is requested, the frame "divides" in two sides.
						Frame1.janelas++;
						button2.setVisible(false);
						//The frame expands and the button1 isn't need.
						frame.setSize(1000, 500);
						panel_3.setVisible(true);
						Client_frame();
					}
				});
				//**********************************************************
				
				
			//The actions for when the logout buttons are pressed.
			//**********************************************************
				//The client logged out!
				c_logout.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					//All this must be invisible if they are in screen
					in_cmenu = false;
					c_logged = false;
					c_logout.setVisible(false);
					panel_3.setVisible(false);
				    company_name.setVisible(false);
					nameclient.setVisible(false);
					minorButton.setVisible(false);
				    P_comboBox.setVisible(false);
				    //If they were already initialized:
				    if (c_part != 0){
				    	auxlabel1.setVisible(false);
				    	auxlabel2.setVisible(false);
				    	auxlabel3.setVisible(false);
				    	auxlabel4.setVisible(false);
				    	
				    }
				    
				    button2.setVisible(true);
					buttonRPromotion.setVisible(false);
					buttonWDraft.setVisible(false);
					buttonWPayment.setVisible(false);
					company_name.setVisible(true);
					company_name.setText("");
					company_message.setText("Insert the name of the company, then ENTER. "
										+ "If the company does not exist, it will be created.");
					nameclient.setVisible(true);
					Frame1.janelas--;
					}
				});
				//The admin logged out!
				a_logout.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						//if they were not initialized, they must now or we will get point exception.
						
					//Every possible element must be invisible
						in_amenu = false;
						a_logged = false;
						a_logout.setVisible(false);
						aminorButton.setVisible(false);
						panel_4.setVisible(false);
						//If they were already initialized:
					    if (a_part != 0){
					    	auxlabel1.setVisible(false);
					    	auxlabel2.setVisible(false);
					    	auxlabel3.setVisible(false);
					    	auxlabel4.setVisible(false);
					    }
					    
					    //But button 1 must return
					    button1.setVisible(true);
						passwordField.setText("");
						passwordField.setVisible(true);
						nameadmin.setVisible(true);
						admin_message.setVisible(true);
						Frame1.janelas--;
						}
					});
				
			//The actions for when the Request Promotion is pressed.
			//**********************************************************
				buttonRPromotion.addMouseListener(new MouseAdapter() {
			    	@Override
			    	public void mouseClicked(MouseEvent arg0) {
			    	in_cmenu = false;
			    	company_message.setFont(new Font("Tahoma", Font.PLAIN, 9));
				    company_message.setBounds(20, 174, 460, 59);
			    	buttonRPromotion.setVisible(false);
			    	buttonWDraft.setVisible(false);
			    	buttonWPayment.setVisible(false);
			    	//The comboBox where the client will choose the promotions is.
			    	P_comboBox.setVisible(true);
			    	
			    	company_message.setText("Chose in the list above what you want to request!");
			    	company_message.setVisible(true);
			    	panel_3.add(minorButton);
			    	minorButton.setBounds(43, 211, 427, 114);
				    panel_3.add(minorButton);
				    minorButton.setText("Request!");
				    minorButton.setVisible(true);
				    minorButton.setForeground(new Color(0, 255, 0));
				    c_option = 1;
			    	}
			    });
			
				//The actions for when the Watch Drafted is pressed.
				//**********************************************************
					buttonWDraft.addMouseListener(new MouseAdapter() {
				    	@Override
				    	public void mouseClicked(MouseEvent arg0) {
				    		//Everytime the menu is left behind the thread must stop showing menu buttons.
				    		in_cmenu = false;
				    		
				    		cancel = new JButton() ;
				    		company_message.setVisible(false);
				    	//Because the process must be called in another way, we created showing_drafted.
				    		showing_drafted();   
				    	}
				    });
				
				
			//The actions for when the client auxiliary button is pressed.
			//**********************************************************
				 minorButton.addMouseListener(new MouseAdapter() {
			    	@Override
			    	public void mouseClicked(MouseEvent arg0) {
			    	JLabel     auxxlabel =    new JLabel();
			    	JTextField auxtextfield = new JTextField();
			    	
			    	//The commands to when the auxiliary button is clicked about requesting promotions
			    	if (c_option == 1){
			    	  aux =  P_comboBox.getSelectedItem().toString();	
			    	  if (aux.equals("Chose one type :")){}
			    	  //If the button is pressed in a valid option.
			    	  else{
			    		 aux = aux + " was requested!";
			    		 company_message.setText(aux);
			    		 minorButton.setVisible(false);
			    		 P_comboBox.setVisible(false);
			    	    //Here we add the new request to that client.
			    		   if((aux).equals("A magazine advert was requested!" ))
								promotionList.add(new MagazineAdverts(identifier++, client));
						   else if((aux).equals("A newspaper advert was requested!" ))
								promotionList.add(new NewspaperAdvert(identifier++, client));
						   else if((aux).equals("A poster advert was requested!" ))
								promotionList.add(new PosterAdvert(identifier++, client));
						   else if((aux).equals("A radio advert was requested!" ))
								promotionList.add(new RadioAdvert(identifier++, client));
						   else if((aux).equals("A TV advert was requested!" ))
								promotionList.add(new TvAdvert(identifier++, client));
						   else if((aux).equals("A Web advert was requested!" ))
								promotionList.add(new WebAdvert(identifier++, client));
						   else if((aux).equals("A e-mail advert was requested!" ))
								promotionList.add(new EmailAdvert(identifier++, client));
			    	       //Using the comboBox for the user choose the promotion:	   
			    		    aux = "Chose one type :" ;
						    P_comboBox.setSelectedItem(aux);
						    company_message.setLocation(150, 300);
						    company_message.setFont(new Font("Segoe UI", Font.BOLD, 14));
						    menuClient();
						    
						  }	 
			    	   }
			    	
			    	//When the auxiliary button is clicked to confirm a drafted project.
			    	if (c_option == 2){
			    		//Confirms the promotion!
						draftProjectList.remove(promotionList.get(tempIndex));
			    		panel_3.add(auxxlabel);
			    		auxxlabel.setText("What's the Purchase Order(PO)?");
			    		auxxlabel.setBounds(300, 225, 200, 30);
						auxtextfield.setBounds(315, 250, 150, 30);
						panel_3.add(auxtextfield);
						
						 auxtextfield.addActionListener(new ActionListener() {
						    	public void actionPerformed(ActionEvent e) {
						    		//The new promotion enters the StartedList and is defined confirmed. 
						    		promotionList.get(tempIndex).setConfirmed(true);
						    		var = Integer.parseInt(auxtextfield.getText().toString());
									promotionList.get(tempIndex).setPonumber(var);
									startedList.add(promotionList.get(tempIndex));
									
									//Removing this buttons.
						            auxlabel1.setVisible(false);
						            auxlabel2.setVisible(false);
						            auxlabel3.setVisible(false);
						            auxlabel4.setVisible(false);
						            PNext.setVisible(false);
						            auxxlabel.setVisible(false);
						            auxtextfield.setVisible(false);
						            cancel.setVisible(false);
						            minorButton.setVisible(false);
						 			i = 0;
						 			j = 2;
						            menuClient();
			
						    	}
						    });
						
			    	}
			    if (c_option == 3){
			    	//Auxiliary promotion object that's used to compare in two different list of promotions.
			    	Promotion t3;
			    	
			    	auxlabel1.setVisible(false);
		            auxlabel2.setVisible(false);
		            auxlabel3.setVisible(false);
		            auxlabel4.setVisible(false);
		            minorButton.setVisible(false);
					var = -1;
					
					//Find the invoice respective promotion index in the promotionList.
					for (j=0; j<promotionList.size(); j++)
					{
						var++;
						t3 = promotionList.get(j);
						if (t3.getidentifier() == completedList.get(i).getidentifier())
						{
							tempIndex = var;
						}
					}
					//The promotions is paid.
					promotionList.get(tempIndex).payment.setPaymentMade(true);
					promotionList.get(tempIndex).setCompleted(false);
					completedList.remove(promotionList.get(tempIndex));
					j = 2;
					aux = userType + " promotion's paid!";
		    		company_message.setText(aux);
		    		company_message.setVisible(true);
		    		company_message.setForeground(new Color(0,255,0));
					menuClient();
			    }
			    	}
			    });
				
				//The actions for when the Setup pending promotions button is pressed 
					//**********************************************************
						buttonSPending.addMouseListener(new MouseAdapter() {
					    	@Override
					    	public void mouseClicked(MouseEvent arg0) {
					    		//We need stop the other thread from showing the menu buttons.
					    		in_amenu = false;
					    		
					    		PPromotion = new JLabel();
					    		a_option = 1;
					    		//Because this must be iterative called, we outspaced it in a method.
					            showing_toDraft();							
					    	} 
					   });
	
				//The actions for when the admin auxiliary button is pressed.
				//**********************************************************
						 aminorButton.addMouseListener(new MouseAdapter() {
						    	@Override
						    	public void mouseClicked(MouseEvent arg0) {
						    		double money;
						    		aminorButton.setFont(new Font("Segoe UI", Font.PLAIN, 18));
						    		//The button is pressed to setup(draft) a request
						    		if(a_option == 1)
						    			drafting();
						    		//The a_option == 2 was erased.
						    		//The button is pressed to set a promotion as completed.
						    		if(a_option == 3){
						    			
						    			//Set the promotion as finally completed.
						    			promotionList.get(tempIndex).setCompleted(true);
						    			//A promotion completed is different status from a confirmed one.
						    			promotionList.get(tempIndex).setConfirmed(false);
										startedList.remove(promotionList.get(tempIndex));
										aminorButton.setForeground(new Color(0, 0, 0));
										 
							    		 
										//add invoice to be payed in invoice list
										promotionList.get(tempIndex).setInvoice(promotionList.get(tempIndex).description.getCost());
										completedList.add(promotionList.get(tempIndex));
						    		    aminorButton.setVisible(false);
						    		    SPromotion.setVisible(false);
						    		    PNext.setVisible(false);
						    		    menuAdmin();
						    		}
						    	    
						    }
						    });
				
				//The actions for when the Set Completed button is pressed.
				//**********************************************************
						      buttonSCompleted.addMouseListener(new MouseAdapter() {
						    	@Override
						    	public void mouseClicked(MouseEvent arg0) {
						    		//Stop the menu thread from showing the menu JComponents.
						    		in_amenu = false;
						    		
						    		showing_completed();
						    	}
						  });
	
				//The actions for when the Waiting Payment button is pressed.
				//**********************************************************		      
						      buttonWPayment.addMouseListener(new MouseAdapter() {
							    	@Override
							    	public void mouseClicked(MouseEvent arg0) {
							    		//We need stop the other thread from showing the menu buttons.
							    		in_cmenu= false;
							    		
							    		company_message.setVisible(false);
							    		showing_wpayment();
							    	}
							    });
						      
				//The actions for when the Next button is pressed.
				//**********************************************************	
						      PNext.addMouseListener(new MouseAdapter() {
						      	@Override
						      	public void mouseClicked(MouseEvent arg0) {	
						      	//This button can be pressed in two situations:
						      		
						      	//If is called by the admin in showing_toDraft()	
						      		if(c_option != 2){
						      		i++;
						      	   showing_toDraft();}
						      	 
						      		//If is called by the client in the showing_drafted()
						      		if(c_option == 2){
						      			i++;
						      	    	auxlabel1.setText("");
						      			auxlabel2.setText("");
						      			auxlabel3.setText("");
						      			auxlabel4.setText("");
						      			showing_drafted();
						      		}
						      	}
						      });
						
	}
	
	//The Client and Admin Jpanels, each one in a side of the program at the same time,
	//This was design, at the begin, as 2 different frames,
	//Some research showed me that creating 2 JFrames is not the best option, so was decided to work with 2 JPanels. 
	 
	//Client JPanel
	public void Client_frame(){
		  //Formatting the header to the new format
		  if(Frame1.janelas == 1){
		   header.setText("BusyPoint: Client");
		   panel_1.setBounds(0,60,1000,440);
		  }
		  
		  else if(Frame1.janelas == 2){
			  //Adding the header for the other option
			    panel_2 = new JPanel();
				panel_2.setBackground(SystemColor.textHighlight);
				panel_2.setBounds(0, 0, 500, 60);
				frame.getContentPane().add(panel_2);
				panel_2.setLayout(new BorderLayout(0, 0));
			    panel_2.setVisible(true);			
			    header2 = new JLabel("BusyPoint: Client");
				panel_2.add(header2, BorderLayout.CENTER);
			    header2.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 18));
				header2.setHorizontalAlignment(SwingConstants.CENTER); 
		  }
	
		  panel_3.setVisible(true);
		  
		  //This ActionListener is used to take the client name
		  company_name.addActionListener(new ActionListener() {
		    	public void actionPerformed(ActionEvent e) {
		    	aux = company_name.getText();
		      //Verifying if exists some client with this name.  
		    	client = addClient(aux);    	
		    	
		      //userType receive the name of the company
				userType = aux;
				c_logged = true;
				//Used to break the loop from Action Listener
				Client_frame();
		    	}
		    });
		  
		  if(c_logged == true){
			//Now it's logged, we don't need this.  
			  nameclient.setVisible(false);
			  company_name.setVisible(false);
			  company_message.setVisible(false);
			  c_logout.setVisible(true);
			  if(Frame1.janelas == 2){
				panel_2.add(c_logout, BorderLayout.NORTH);
			  }
			  c_logout.setText("Loggout as : "+ userType);
			  menuClient();
		  }
		  
	   }
		
	   // Admin JPanel.
	   public void Admin_frame(){
		   if (Frame1.janelas == 1){
			   panel.setBounds(500,0,500,60);
			   panel_1.setBounds(0,60,1000,440);
			   header.setText("BusyPoint: Admin");
		   }
		  
		   else if(Frame1.janelas == 2){
			 //Adding the header for the other option
			    panel_2 = new JPanel();
				panel_2.setBackground(SystemColor.textHighlight);
				panel_2.setBounds(500, 0, 500, 60);
				frame.getContentPane().add(panel_2);
				panel_2.setLayout(new BorderLayout(0, 0));
			    panel_2.setVisible(true);			
			    header2 = new JLabel("BusyPoint: Admin");
				panel_2.add(header2, BorderLayout.CENTER);
			    header2.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 18));
				header2.setHorizontalAlignment(SwingConstants.CENTER); 
		   }
		   
		 //This ActionListener is used to take the Admin password
			passwordField.addActionListener(new ActionListener() {
			    	public void actionPerformed(ActionEvent e) {
			    	aux = passwordField.getText();
			      //Verifying if the password is correct  
			    	if (aux.equals("busypoint123")){
			    		userType2 = aux;
			    		a_logged = true;
			    		Admin_frame();}
			    	else{
			    		admin_message.setText("Invalid password!");	
			    	    passwordField.setText("");
			    	
			      }
			  }});
			    		
			    	if (a_logged == true){
			    	  //The logout button must appear
			    		if(Frame1.janelas == 1){
			    			panel.add(a_logout, BorderLayout.NORTH);
			    		}
			    		else if(Frame1.janelas == 2){
			    			panel_2.add(a_logout, BorderLayout.NORTH);
			    		}
			    		
			    		a_logout.setVisible(true);
			    	  ///Now it's logged, we don't need this.  
						  nameadmin.setVisible(false);
						  passwordField.setVisible(false);
						  admin_message.setVisible(false);
			    		  menuAdmin();
			    	}
			    	
			    
	   }

//This method is used to showing the promotions waiting for the admin setup as draft.
public void showing_toDraft(){
	//Auxiliary promotion object that will be used to compare 2 different promotion lists.
	Promotion t = promotionList.get(i);

	//This is used to indicates each part of the program we are.
	a_option = 1;
	
	//This finds the first Pending promotion in the list.
	while((t.isPending() == false) && (i < promotionList.size()) ){
		i++;
		t = promotionList.get(i);
	}
	
	//Erasing the menu buttons we don't need.
	buttonSPending.setVisible(false);
	buttonSCompleted.setVisible(false);
	
   // That's the setup button.
	aminorButton.setVisible(true);
	aminorButton.setText("Setup");
	aminorButton.setFont(new Font("Segoe UI", Font.PLAIN, 11));
	aminorButton.setBounds(200, 100, 77, 28);

	//Setting the NEXT button false, can be changes if there's another pending promotion.
	PNext.setVisible(false);
	
	//There's another pending promotion ?
	if(promotionList.size() > i+ 1){
		Promotion t2= promotionList.get(i+1);
		if(t2.isPending() == true)
			PNext.setVisible(true);
	}

	//Setting PNext, even if he would be not visible this time.
	PNext.setFont(new Font("Segoe UI", Font.PLAIN, 11));
	PNext.setBounds(100, 100, 77, 28);
	panel_4.add(PNext);   
	panel_4.add(aminorButton);
	panel_4.add(PNext);	
	
	//Showing the pending promotion found.
	if (t.isPending())
	{
	PPromotion.setText(++i + ". Client: " + t.client.getName() + 
					         "  ->  Promotion: " +  t.description.getType());
	i--;
	panel_4.add(PPromotion);
	PPromotion.setVisible(true);
	PPromotion.setBounds(20, 50, 300, 50);
	pendingList.add(t);
	}
	
}

//This method is used in order the admin setup the pending promotions.
public void drafting(){
	a_part=1;
	//The visual JComponents that will be used to the client input the Promotions descriptions.
	//It's absolute that's the first time they will be used, so it's okay initialize them here.
	auxlabel1   = new JLabel();
	auxlabel2	= new JLabel();
	auxlabel3	= new JLabel();
	auxlabel4	= new JLabel();
	JTextField 	auxtext1 = new JTextField();
	JTextField 	auxtext2 = new JTextField();
	JTextField 	auxtext3 = new JTextField();
	JTextArea   auxatext = new JTextArea();
	JButton     testeDDD = new JButton();
	
    //Erasing some buttons we don't need here.
	PNext.setVisible(false);
	aminorButton.setVisible(false);
	admin_message.setVisible(false);
    PPromotion.setVisible(false);
    
    //Adding the labels that will identify for the user where update each value:  
    auxlabel1.setText("Type: ");
    auxlabel1.setBounds(25, 50, 100, 20);
	auxlabel1.setBorder(new MatteBorder(3, 2, 3, 2, (Color) new Color(51, 153, 255)));
	auxlabel1.setFont(new Font("Segoe UI", Font.ITALIC, 16));
    panel_4.add(auxlabel1);
         
    auxlabel2.setText("Cost: ");
    auxlabel2.setBounds(25, 100, 100, 20);
    auxlabel2.setBorder(new MatteBorder(3, 2, 3, 2, (Color) new Color(51, 153, 255)));
    auxlabel2.setFont(new Font("Segoe UI", Font.ITALIC, 16));
    panel_4.add(auxlabel2);
    
    auxlabel3.setText("End date: ");
    auxlabel3.setBounds(25, 150, 100, 20);
    auxlabel3.setBorder(new MatteBorder(3, 2, 3, 2, (Color) new Color(51, 153, 255)));
    auxlabel3.setFont(new Font("Segoe UI", Font.ITALIC, 16));
    auxlabel3.setVisible(true);
	panel_4.add(auxlabel3);
		  
	auxlabel4.setText("Details: ");
	auxlabel4.setBounds(25, 200, 100, 20);
	auxlabel4.setBorder(new MatteBorder(3, 2, 3, 2, (Color) new Color(51, 153, 255)));
	auxlabel4.setFont(new Font("Segoe UI", Font.ITALIC, 16));
	auxlabel4.setVisible(true);
	panel_4.add(auxlabel4);
	
	//And the JText areas where the admin will update the info he want.
	panel_4.add(auxtext1);
	auxtext1.setBounds(150, 50, 150, 20);
	panel_4.add(auxtext2);
	auxtext2.setBounds(150, 100, 150, 20);
	auxtext2.setText("0.00");
   	panel_4.add(auxtext3);
   	auxtext3.setBounds(150, 150, 150, 20);    
    panel_4.add(auxatext);
    auxatext.setBounds(150,200, 200, 60 );

    //This is a local button used only here.
    //The a_minorButton could be used here too.
    testeDDD.setText("Send draft!");
    panel_4.add(testeDDD);
    testeDDD.setBounds(200,300, 100, 25);	     
    testeDDD.setVisible(true);
    
    //The local button action
	testeDDD.addMouseListener(new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent arg0) {
			//Setting the promotion description in the promotion.
		    promotionList.get(i).description.setType(auxtext1.getText().toString());
		    promotionList.get(i).description.setDetails(auxatext.getText().toString());
		    promotionList.get(i).description.setEndDate(auxtext3.getText().toString());
		    promotionList.get(i).description.setCost(auxtext2.getText().toString());

		    //Removing this JComponents we don't need visible anymore.
		  	auxlabel1.setVisible(false);
	  		auxlabel2.setVisible(false);
	  		auxlabel3.setVisible(false);
     		auxlabel4.setVisible(false);
		    auxtext1.setVisible(false);
		    auxtext2.setVisible(false);
		    auxtext3.setVisible(false);
		    auxatext.setVisible(false);
		    testeDDD.setVisible(false);
		    // The pending promotion is now draft.
		    draftProjectList.add(promotionList.get(i));
			promotionList.get(i).setPending(false);
			Admin_frame();	    		
		   }
	   });    
}


//This method is used to showing the drafted promotions the client can confirms!
public void showing_drafted(){
	c_part = 1;
	c_option = 2;
	
	//That's a auxiliar variable to define if a drafted promotion was already found (should stop going to the next).
	int stop = 0;
	
	//Again a promotion that will be used in order to compare different list of Promotions.
	Promotion x;
	
	Promotion t = draftProjectList.get(i);
	
	//Removing the buttons of the previous screen
	buttonRPromotion.setVisible(false);
	buttonWDraft.setVisible(false);
	buttonWPayment.setVisible(false);   		
	auxlabel1.setText("");
	auxlabel2.setText("");
	auxlabel3.setText("");
	auxlabel4.setText("");
	
	
    if(t.client.getName().equals(userType)){
    //Find the right promotion, recursive stop flag is 1.
    stop = 1;		
	//Adding the labels that will identify for the user where update each value:  
	aux = "Type:   ";
	aux = aux + draftProjectList.get(i).description.getType();
    auxlabel1.setText(aux);
	auxlabel1.setBorder(new MatteBorder(1, 2, 1, 2, (Color) new Color(51, 153, 255)));
	auxlabel1.setFont(new Font("Segoe UI", Font.ITALIC, 16));
	panel_3.add(auxlabel1);
	auxlabel1.setBounds(25, 50, 200, 20);
	auxlabel1.setVisible(true);
    
	aux = "Cost:   ";
	aux = aux + draftProjectList.get(i).description.getCost();
    auxlabel2.setText(aux);
    auxlabel2.setBorder(new MatteBorder(1, 2, 1, 2, (Color) new Color(51, 153, 255)));
    auxlabel2.setFont(new Font("Segoe UI", Font.ITALIC, 16));
    panel_3.add(auxlabel2);
    auxlabel2.setBounds(25, 100, 200, 20);
    auxlabel2.setVisible(true);
    
    aux = "End Date:   ";
	aux = aux + draftProjectList.get(i).description.getEndDate();
    auxlabel3.setText(aux);
    auxlabel3.setBorder(new MatteBorder(1, 2, 1, 2, (Color) new Color(51, 153, 255)));
    auxlabel3.setFont(new Font("Segoe UI", Font.ITALIC, 16));
    auxlabel3.setVisible(true);
	panel_3.add(auxlabel3);
	auxlabel3.setBounds(25, 150, 200, 20);
	
	aux = "Details:   ";
	aux = aux + draftProjectList.get(i).description.getDetails();
	auxlabel4.setText(aux);
	auxlabel4.setBorder(new MatteBorder(1, 2, 1, 2, (Color) new Color(51, 153, 255)));
	auxlabel4.setFont(new Font("Segoe UI", Font.ITALIC, 16));
	auxlabel4.setVisible(true);
	panel_3.add(auxlabel4);	
	auxlabel4.setBounds(25, 200, 200, 100);
    
    //The cancel button, for the user cancel the drafted promotion.
	cancel.setText("Cancel the request!");
	cancel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
    cancel.setBounds(275, 150, 200, 50);
    panel_3.add(cancel);
    cancel.setForeground(new Color(255, 0, 0));
    
    //The auxiliary button, here used for the user confirm the drafted promotion.
    minorButton.setText("Confirm the drafted request!");
    minorButton.setVisible(true);
    minorButton.setBounds(275, 100, 200, 50);
    minorButton.setFont(new Font("Segoe UI", Font.PLAIN, 11));
    minorButton.setForeground(new Color(0, 255, 0));
    
    //This is the "Next" button should be used if there's another drafted promotion of this client
    panel_3.add(PNext);
    PNext.setBounds(125, 350, 100, 20);
    PNext.setText("Next");
    j = i+1;   
    
    var= -1;
    //We need to find who is this promotion in the promotionList.
    for(j=0; j < promotionList.size(); j++)
    {
		var++;
		x = promotionList.get(var);
		if (x.identifier == draftProjectList.get(i).identifier)
		{
		  //We found what we searched, var.
			tempIndex = var;
			j = 10000;
		}
	}
   
	// The Next button should be visible ?
    var = draftProjectList.size();
    PNext.setVisible(false);
	j = i+1;
    while(j<var){
    	x= draftProjectList.get(j);
	    //If any next draft project is from this client, it should.
    	if(x.client.getName().equals(userType)){
			PNext.setVisible(true);
		}
		j++;
	}
	
	// Deleting the promotion if the user cancel the request.
		cancel.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent arg0) {
		    	draftProjectList.remove(promotionList.get(tempIndex));
		    	clientsList.remove(promotionList.get(tempIndex));
		    	promotionList.remove(tempIndex);
				if (PNext.isVisible()){
			    	auxlabel1.setText("");
					auxlabel2.setText("");
					auxlabel3.setText("");
					auxlabel4.setText("");
					showing_drafted();
				}
				else{
					auxlabel1.setVisible(false);
					auxlabel2.setVisible(false);
					auxlabel3.setVisible(false);
					auxlabel4.setVisible(false);
					minorButton.setVisible(false);
					cancel.setVisible(false);
					PNext.setVisible(false);
					menuClient();
				}			
		    }
		   });
	}
    
    // The NEXT Button mouse click event can be found where the buttons events are (above in the code).
	if (stop!= 1){
		i++;
		showing_drafted();
	}
     
}

//Method to show the completed promotions to the admin confirms:
public void showing_completed(){
	i = 0;
	a_option = 3;
	Promotion t = startedList.get(i);
	
    //SPromotion can only be initialized here.
	SPromotion = new JLabel();
	
	//Another local promotion to comparison.
	Promotion t2;
	
	
	//Removing the button that can be visible at this point
       buttonSPending.setVisible(false);
	
	
	SPromotion.setBounds(125, 50, 200, 50);
	buttonSCompleted.setVisible(false);
	aminorButton.setVisible(true);
	aminorButton.setText("Confirm");
    aminorButton.setForeground(new Color(0, 255, 0));
    aminorButton.setBounds(125,150,200,200);
    aminorButton.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 18));
	
    //Shows the almost finished promotion.
    panel_4.add(SPromotion);
	SPromotion.setText(++i + ". Client: " + t.client.getName() + 
	         "  ->  Promotion: " +  t.description.getType());
 
    //NEXT button should be visible ?
	if(promotionList.size() > i+ 1)
		PNext.setVisible(true);
	PNext.setBounds(200, 250, 100, 20);
	
	//Find the given promotion set as completed in the promotionList	
	  var = -1;
	  for(j=0; j<promotionList.size(); j++){
		  var++;
		  t2 = promotionList.get(j);
		  if (t2.identifier == t.identifier)
			  tempIndex = var;
	  }	
}

// Method that shows the promotions only waiting for payment.	 
public void	 showing_wpayment(){
	
	Promotion t = completedList.get(i);
	c_option = 3;
	
	//Removing buttons
	buttonWPayment.setVisible(false);
	buttonRPromotion.setVisible(false);
	buttonWDraft.setVisible(false);
	PNext.setVisible(false);
	
	//Adding the button to pay.
	minorButton.setText("Confirm the payment!");
	minorButton.setVisible(true);
	minorButton.setBounds(225, 100, 250, 150);
	minorButton.setForeground(new Color(0, 255, 0));
	minorButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
	
	if(t.client.getName().equals(userType)){
		//Adding the labels that will identify for the user where update each value:  
		aux = "Type:   ";
		aux = aux + completedList.get(i).description.getType();
	    auxlabel1.setText(aux);
		auxlabel1.setBorder(new MatteBorder(1, 2, 1, 2, (Color) new Color(51, 153, 255)));
		auxlabel1.setFont(new Font("Segoe UI", Font.ITALIC, 16));
		panel_3.add(auxlabel1);
		auxlabel1.setBounds(25, 50, 200, 20);
		auxlabel1.setVisible(true);
	    
		aux = "Cost:   ";
		aux = aux + completedList.get(i).description.getCost();
	    auxlabel2.setText(aux);
	    auxlabel2.setBorder(new MatteBorder(1, 2, 1, 2, (Color) new Color(51, 153, 255)));
	    auxlabel2.setFont(new Font("Segoe UI", Font.ITALIC, 16));
	    panel_3.add(auxlabel2);
	    auxlabel2.setBounds(25, 100, 200, 20);
	    auxlabel2.setVisible(true);
	    
	    aux = "End Date:   ";
		aux = aux + completedList.get(i).description.getEndDate();
	    auxlabel3.setText(aux);
	    auxlabel3.setBorder(new MatteBorder(1, 2, 1, 2, (Color) new Color(51, 153, 255)));
	    auxlabel3.setFont(new Font("Segoe UI", Font.ITALIC, 16));
	    auxlabel3.setVisible(true);
		panel_3.add(auxlabel3);
		auxlabel3.setBounds(25, 150, 200, 20);
		
		aux = "Details:   ";
		aux = aux + completedList.get(i).description.getDetails();
		auxlabel4.setText(aux);
		auxlabel4.setBorder(new MatteBorder(1, 2, 1, 2, (Color) new Color(51, 153, 255)));
		auxlabel4.setFont(new Font("Segoe UI", Font.ITALIC, 16));
		auxlabel4.setVisible(true);
		panel_3.add(auxlabel4);	
		auxlabel4.setBounds(25, 200, 200, 100);
	}
}
			
	   
//Here are the procedures that are not GUI based:	   
	   public static Client addClient(String aux)
	   {
	   	
	   	
	   	//a test is done to check if the client already exist in the database
	   	test = false;
	   	for(Client cl : clientsList) 
	   		if(cl.getName().equals(aux)) 
	   			test = true;

	   	//if client does not exist, a member of staff is assigned to the client
	   	
	   	if (!test)
	   	{
	   		// (for the example, a common staff employee will be used for all clients
	   		// but in the final program it will be already in the database)
	   		employee = new Staff (435, 2700.00, "account manager", 7);

	   	}
	   	//else, if client already exists
	   	else
	   	{
	   		// find the client in the list
	   		for(Client cl : clientsList) 
	   			if(cl.getName().equals(aux)) 
	   				client = cl;
	   		
	   		//recover its employee info
	   		employee = client.getStaffAssigned();
	   		
	   	}
	   	
	   	//the company name will be the variable inserted when the client
	   	//logged in the system and the main contact in the company will be
	   	//"contact 1" in this example but must be added in the database
	   	//in the final program. The client is added to the clientsList
	   	return client = new Client(aux,"contact 1", employee);
	   }
	   
//The menuClient starts the thread responsible to count the promotions in each category and work with menu.
//The menuClient don't need to start the thread after the first time, only setting the in_cmenu to true, as a signal.	   
public static void menuClient()
	{
		pstart++;
		i=0;
		in_cmenu = true;
		if (pstart == 1)
			datthread();
	}

//The menuAdmin starts the thread responsible to count the promotions in each category and work with menu.
//The menuAdmin don't need to start the thread after the first time, only setting the in_cmenu to true, as a signal.
	   public static void menuAdmin()
		{
		   pstart++;
		   i = 0;
		   //This button is responsable for "There's no action to be done now" message.
		   aminorButton.setVisible(false);
		   in_amenu = true;
		   if (pstart == 1)
			  datthread();
		}

/**This method invokes a new SwingWorker thread.
SwingWorkers are backgoung threads that should not be used to define much Event Handlers.
This thread works to count the number of promotions in : total, pending, drafted, completed...
This is useful as the menu will always be updated, with each inclusion/cancel promotion. 
At first, I would implement 2 JPanels (client, Admin) who works with ONE thread EACH.
But as JSwing is hard to work with concurrency, not safe thread at most situations, this background thread was enough.
*/
static void datthread(){
	promotion_numbers = new SwingWorker<Void, Void>(){

		//What the thread does in background ?
		@Override
		protected Void doInBackground() throws Exception {
			//From the first login until the program is closed:
			while(pstart > 0){
				
				// This three promotions objects are used for find the number of promotions in each category.
				for(Promotion a : draftProjectList) 
					if(a.client.getName().equals(userType))
						pdrafted++;
				
				for(Promotion b : completedList) 
					if(b.client.getName().equals(userType))
						pcompleted++;
				
				for(Promotion c : promotionList) 
				{
					if (c.isPending() == true)
						ppending++;
					if (c.isConfirmed() == true)
						ptocomplete++;				
				}
			  safe_value = true;
			  // So the program is currently in the Client Menu.
			  if ((in_cmenu == true) && (safe_value = true)){
				  //This button is always visible in the client Menu.
				  buttonRPromotion.setVisible(true);
				  //Confirms the number of drafted and waiting payment promotions.
				  if (pdrafted == 0)
						buttonWDraft.setVisible(false);
					else 
						buttonWDraft.setVisible(true);
					
					if (pcompleted == 0)
						buttonWPayment.setVisible(false);
					else
						buttonWPayment.setVisible(true);
			  }
			  
			  if((in_amenu == true) && (safe_value == true)){
				  // If the program is currently in the Admin menu.
				  
				  // There's any action to do ?
				  if((ppending == 0) && (ptocomplete == 0)){
					    aminorButton.setText("There are no actions to be done now.");
						aminorButton.setBounds(75,195 , 350, 50);
						aminorButton.setVisible(true);}
				  else
					  aminorButton.setVisible(false);
			
				  //Verifies what action should be showed.
				  if (ppending == 0)
						buttonSPending.setVisible(false);
					else 
						buttonSPending.setVisible(true);
					
					if (ptocomplete == 0)
						buttonSCompleted.setVisible(false);
					else
						buttonSCompleted.setVisible(true);
			  }
			  Thread.sleep(200);
			  //Reseting the values, they're not safe at this point.
			  safe_value = false;
			  pdrafted = 0;
			  pcompleted = 0;
			  ppending = 0;
			  ptocomplete = 0;
			}
			return null;
			}	
	};
	//The thread is send to execute.
	promotion_numbers.execute();	
}
	   
}


