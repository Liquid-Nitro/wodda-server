package models;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.*;

import Server.MainServer;



public class Customer implements Serializable {
    private String customerId;
    private String firstName;
    private String lastName;
    private String email;
    private String contactNumber;
    private Connection con;
	ResultSet result = null;
	Statement stat = null;
	private String nature;
	private String complaint;
	
	JFrame frame;
	JButton button, button1, button2;
	
    public Customer(String customerId, String firstName, String lastName, String email, String contactNumber) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.contactNumber = contactNumber;
    }
    
    public Customer() {
    	
    	 con = MainServer.getDatabaseConnection();
    	 JFrame login = new JFrame("Customer Login");
    	 login.setSize(400,400);
    	 JButton logg = new JButton("Login");
    	 JLabel cusid =new JLabel ("Customer ID:");
	     cusid.setBounds(30,10, 100,30); 
	     JLabel pword =new JLabel ("Password:");
	     pword.setBounds(30,50, 100,30); 
	     JTextField enterid = new JTextField();
	     JTextField enterpword = new JTextField();
	     enterid.setBounds(130,10, 200,30);  
	     enterpword.setBounds(130,50, 200,30);  
    	 logg.setBounds(30,120,100,30);
    	 
    	 login.add(cusid);login.add(pword);login.add(enterid);login.add(enterpword);login.add(logg);
    	 
    	 login.setLayout(null);
	     login.setVisible(true);
	     login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	     
    	 logg.addActionListener(new ActionListener() {
              @Override
              public void actionPerformed(ActionEvent e) {
            	  
            		String sql2 ="SELECT password FROM complaint.custlogin WHERE id ='"+ enterid.getText() + "'" ;
            		
            		
                		Statement state = null;
                		ResultSet res = null;
                		String password = null;
                		try {
                			 state = con.createStatement();
                			 res = state.executeQuery(sql2);
                		if (res.next()) {
                			
                			password = res.getString("password");
                		}
							if(password.equals(enterpword.getText()) ) {
								  frame = new JFrame("Customer");
							    	frame.setSize(400,400);
							    	button = new JButton("Lodge Complaint");
							        button1 = new JButton("Make a Query");
							        button2 = new JButton("Live Chat");
							        
							        button.setBounds(130,50,150,60);
							        button1.setBounds(130,150,150,60);
							        button2.setBounds(130,250,150,60);
							        
							        frame.add(button);frame.add(button1);
							        frame.add(button2);
							        
							        frame.setLayout(null);
							        frame.setVisible(true);
							        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
							        
							        button.addActionListener(new ActionListener() {
							            @Override
							            public void actionPerformed(ActionEvent e) {
							               		JFrame frame1 = new JFrame("Lodge Complaint");
							               		JButton lodge;
							               		lodge = new JButton("Next");
							               		
							               		JButton back;
							               		back = new JButton("Back");
							               		
							               		JLabel l,l1,l2,l3,l4;
							               		JTextArea area;
							               		JTextField t,t1,t2,t3,t4;
							        	       
							        	        l=new JLabel ("ID:");
							        	        l.setBounds(10,10, 100,30); 
							        	        l1=new JLabel ("First Name:");
							        	        l1.setBounds(10,50, 100,30); 
							        	        l2=new JLabel ("Last Name:");
							        	        l2.setBounds(10,90, 100,30); 
							        	        l3=new JLabel ("Email:");
							        	        l3.setBounds(10,130, 100,30); 
							        	        l4=new JLabel ("Contact No.:");
							        	        l4.setBounds(10,170, 100,30); 
							        	      
							        	        
							        	        
							        	        t = new JTextField();
							        	        t1 = new JTextField();
							        	        t2 = new JTextField();
							        	        t3 = new JTextField();
							        	        t4 = new JTextField();
							        	      
							        	    
							        	        
							        	        t.setBounds(90,10, 200,30);  
							        	        t1.setBounds(90,50, 200,30);  
							        	        t2.setBounds(90,90, 200,30);  
							        	        t3.setBounds(90,130, 200,30);  
							        	        t4.setBounds(90,170, 200,30);  
							        	            	        
							        	        frame1.setSize(500,500);
							        	        
							        	        lodge.setBounds(10,320,100,40);
							        	        back.setBounds(10,380,100,40);
							        	        
							        	        frame1.add(l);frame1.add(t);
							        	        frame1.add(l1);frame1.add(t1);
							        	        frame1.add(l2); frame1.add(l3); frame1.add(t4);
							        	        frame1.add(t2);frame1.add(t3);frame1.add(l4);
							        	      
							        	        
							        	        frame1.add(lodge);
							        	        frame1.add(back);
							        	        frame1.setLayout(null);
							        	        frame1.setVisible(true);
							        	        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
							        	       
							        	        lodge.addActionListener(new ActionListener() {
							        	            @Override
							        	            public void actionPerformed(ActionEvent e) {
							        	            	
							        	               		save(t.getText(),t1.getText(), t2.getText(), t3.getText(), t4.getText());
							        	               		setCustomerId(t.getText());
							        	               		setFirstName(t1.getText());
							        	               		setLastName(t2.getText());
							        	               		setEmail(t3.getText());
							        	               		setContactNumber(t4.getText());
							        	             
							        	               		
							        	               		JLabel issue =new JLabel ("Complaint Type:");
							        	        	        issue .setBounds(10,10, 100,30); 
							        	               	  	String type[]={"Technical Issues", "Bad Customer Service", "BroadBand", "Wifi Issues", "Other"};        
							        	               	  	JComboBox complaint = new JComboBox(type);
							        	               	  	complaint.setBounds(110,10, 200,30);
							        	               	  	
							        	               	  	JLabel complaintarea =new JLabel ("Issue:");
							        	               	  	complaintarea .setBounds(10,50, 100,30); 
							        	               	  	JTextArea area=new JTextArea();  
							        	               	  	area.setBounds(110,50,350,300);  
							        	               	  	
							        	               	  	JButton lodgecomp;
							        	               		lodgecomp = new JButton("Lodge Compaint");
							        	               		lodgecomp.setBounds(10,360,140,60);
							        	               		
							        	               		
							        	               		JFrame frame2 = new JFrame("Lodge Complaint");
							        	               		frame2.setSize(500,500);
							        	               		
							        	               		frame2.add(issue);frame2.add(complaint);frame2.add(complaintarea);frame2.add(area);
							        	               		frame2.add(lodgecomp); 
							        	               		
							        	               		frame2.setLayout(null);
							        	        	        frame2.setVisible(true);
							        	        	        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
							        	        	        frame1.setVisible(false);
							        	        	        
							        	        	        lodgecomp.addActionListener(new ActionListener() {
							                    	            @Override
							                    	            public void actionPerformed(ActionEvent e) {
							                    	               		
							                    	            	int selected = complaint.getSelectedIndex();
							                    	            	setNature(type[selected]);
							                    	            	setComplaint(area.getText());
							                    	            	savecomplaint(type[selected], area.getText());
							                    	            	JOptionPane.showMessageDialog(frame, "Complaint Lodged Successfully","Action Demo", JOptionPane.INFORMATION_MESSAGE);
							                   	    	         	frame2.setVisible(false);
							                    	            	 }	                      	                         
							                    	        });
							        	            	 }	         
							        	         
							        	        });
							        	     
							        	       
							            	 }	   
							                     
							        });
							        
							     
							        
							        button1.addActionListener(new ActionListener() {
							        	  @Override
							              public void actionPerformed(ActionEvent e) {
							                 		JFrame frame1 = new JFrame("Query / Request");
							                 		JButton lodge;
							                 		lodge = new JButton("Next");
							                 		
							                 		JButton back;
							                 		back = new JButton("Back");
							                 		
							                 		JLabel l,l1,l2,l3,l4;
							                 		JTextArea area;
							                 		JTextField t,t1,t2,t3,t4;
							          	       
							          	        l=new JLabel ("ID:");
							          	        l.setBounds(10,10, 100,30); 
							          	        l1=new JLabel ("First Name:");
							          	        l1.setBounds(10,50, 100,30); 
							          	        l2=new JLabel ("Last Name:");
							          	        l2.setBounds(10,90, 100,30); 
							          	        l3=new JLabel ("Email:");
							          	        l3.setBounds(10,130, 100,30); 
							          	        l4=new JLabel ("Contact No.:");
							          	        l4.setBounds(10,170, 100,30); 
							          	      
							          	        
							          	        
							          	        t = new JTextField();
							          	        t1 = new JTextField();
							          	        t2 = new JTextField();
							          	        t3 = new JTextField();
							          	        t4 = new JTextField();
							          	      
							          	    
							          	        
							          	        t.setBounds(90,10, 200,30);  
							          	        t1.setBounds(90,50, 200,30);  
							          	        t2.setBounds(90,90, 200,30);  
							          	        t3.setBounds(90,130, 200,30);  
							          	        t4.setBounds(90,170, 200,30);  
							          	            	        
							          	        frame1.setSize(500,500);
							          	        
							          	        lodge.setBounds(10,320,100,40);
							          	        back.setBounds(10,380,100,40);
							          	        
							          	        frame1.add(l);frame1.add(t);
							          	        frame1.add(l1);frame1.add(t1);
							          	        frame1.add(l2); frame1.add(l3); frame1.add(t4);
							          	        frame1.add(t2);frame1.add(t3);frame1.add(l4);
							          	      
							          	        
							          	        frame1.add(lodge);
							          	        frame1.add(back);
							          	        frame1.setLayout(null);
							          	        frame1.setVisible(true);
							          	        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
							          	       
							          	        lodge.addActionListener(new ActionListener() {
							          	            @Override
							          	            public void actionPerformed(ActionEvent e) {
							          	            	
							          	               		//save(t.getText(),t1.getText(), t2.getText(), t3.getText(), t4.getText());
							          	               		setCustomerId(t.getText());
							          	               		setFirstName(t1.getText());
							          	               		setLastName(t2.getText());
							          	               		setEmail(t3.getText());
							          	               		setContactNumber(t4.getText());
							          	               		
							          	               		JLabel issue =new JLabel ("Type:");
							          	        	        issue .setBounds(10,10, 100,30); 
							          	               	  	String type[]={"","Query", "Request"};        
							          	               	  	JComboBox complaint = new JComboBox(type);
							          	               	  	complaint.setBounds(110,10, 200,30);
							          	               	  	
							          	               	  	JLabel complaintarea =new JLabel ("Query/Request:");
							          	               	  	complaintarea .setBounds(10,50, 100,30); 
							          	               	  	JTextArea area=new JTextArea();  
							          	               	  	area.setBounds(110,50,350,300);  
							          	               	  	
							          	               	  	JButton proceed;
							          	               		proceed = new JButton("Proceed");
							          	               		proceed.setBounds(10,360,140,60);
							          	               		
							          	               		
							          	               		JFrame frame2 = new JFrame("Lodge Complaint");
							          	               		frame2.setSize(500,500);
							          	               		
							          	               		frame2.add(issue);frame2.add(complaint);frame2.add(complaintarea);frame2.add(area);
							          	               		frame2.add(proceed); 
							          	               		
							          	               		frame2.setLayout(null);
							          	        	        frame2.setVisible(true);
							          	        	        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
							          	        	        frame1.setVisible(false);
							          	        	        
							          	        	        proceed.addActionListener(new ActionListener() {
							                      	            @Override
							                      	            public void actionPerformed(ActionEvent e) {
							                      	               		
							                      	            	int selected = complaint.getSelectedIndex();
							                      	            	setNature(type[selected]);
							                    	            	setComplaint(area.getText());
							                      	            //	savecomplaint(type[selected], area.getText());
							                      	            	JOptionPane.showMessageDialog(frame, "Query/Request made Successfully","Action Demo", JOptionPane.INFORMATION_MESSAGE);
							                     	    	         	frame2.setVisible(false);
							                      	            	 }	                      	                         
							                      	        });
							          	            	 }	         
							          	         
							          	        });
							          	     
							          	       
							              	 }	   
							                       
							          });
							          
							        
							        button2.addActionListener(new ActionListener() {
							            @Override
							            public void actionPerformed(ActionEvent e) {
							               		
							            	
							            	 }	                      	                         
							        });
							    	
							    	 }else {
							    			JOptionPane.showMessageDialog(frame, "Incorrect Login Try Again","Login Error", JOptionPane.INFORMATION_MESSAGE);
		                   	    	         enterid.setText("");
		                   	    	         enterpword.setText("");
							    	 }
						} catch (HeadlessException | SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}	  }                    	                         
                        });
             
    }
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	
	public String getEmail() {
		return email;
	}

	
	public void setEmail(String email) {
		this.email = email;
	}

	
	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	
	public void setNature(String nature) {
		this.nature = nature;
	}
	public String getNature() {
		return nature;
	}
	
	public void setComplaint(String complaint) {
		this.complaint = complaint;
	}
	public String getComplaint() {
		return complaint;
	}
    // Getters and setters
	
	public void save (String customerId, String firstName, String lastName, String email, String contactNumber) {
		String sql ="INSERT INTO complaint.customer" + "(customerid,firstname,lastname,email,contact)" + "values('" + customerId +"','" + firstName + "','"+ lastName +"','" + email +"','" + contactNumber +"')";
		
		try {
			
			stat = con.createStatement();
			stat.executeUpdate(sql);
						
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void savecomplaint (String nature, String complaint) {
		String sql ="INSERT INTO complaint.complaint" + "(nature,complaint)" + "values('" + nature +"','" + complaint + "')";
		
		try {
			
			stat = con.createStatement();
			stat.executeUpdate(sql);
						
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		new Customer();
		
	}

}
