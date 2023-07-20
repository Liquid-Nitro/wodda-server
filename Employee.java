package models;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import Server.MainServer;
 
public class Employee {
    // frame
    JFrame f;
    // Table
    JTable j;
    private Connection con;
	ResultSet result, result1 = null;
	Statement stat, stat1 = null;
 
    // Constructor
    Employee()
    {
    	
    	con = MainServer.getDatabaseConnection();
    	
    	 JFrame login = new JFrame("Employee Login");
    	 login.setSize(400,400);
    	 JButton logg = new JButton("Login");
    	 JLabel cusid =new JLabel ("Employee ID:");
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
	            	String sql2 ="SELECT password FROM complaint.emplogin WHERE id ='"+ enterid.getText() + "'" ;
            		
            		
            		Statement state;
            		ResultSet res;
            		try {
            			state = con.createStatement();
            			res = state.executeQuery(sql2);
            			String password = null;
            			if (res.next()) {
                			
                			password = res.getString("password");
                		}
							if(password.equals(enterpword.getText()) ){
								dashboard();
							}else {
								 JOptionPane.showMessageDialog(login, "Incorrect Login Try Again","Login Error", JOptionPane.INFORMATION_MESSAGE);
							        enterid.setText("");
							        enterpword.setText("");
							 }
            		}catch (HeadlessException | SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}	}
	        });
    }
    
    public void dashboard() {
    	
    	
    	JFrame frame = new JFrame("Customer Complaints");
    	frame.setSize(400,400);
    	JButton button = new JButton("View Complaint");
        JButton button1 = new JButton("Schedule a Technician");
        JButton button2 = new JButton("View Completed Responses");
        
        button.setBounds(130,50,150,60);
        button1.setBounds(110,150,200,60);
        button2.setBounds(130,250,150,60);
        
        frame.add(button);frame.add(button1);
        frame.add(button2);
        
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               		viewall();
            	
            	 }	                      	                         
        });
    	
    	         
    }
    
    
    public void viewall() {
    	 // Frame initialization
    	con = MainServer.getDatabaseConnection();
    	
    	String sql ="SELECT * FROM complaint.customer";
    	String sql2 ="SELECT * FROM complaint.complaint";
    	
      JFrame  f = new JFrame();
        JTable j = new JTable();
        JButton back = new JButton("Back");
        JButton response = new JButton("Respond");
        
        back.setBounds(10,200, 100,30);
        response.setBounds(130,200,100,30);
      // Frame Title
        f.setTitle("Complaint List");
        String[] columnNames = { "ID", "First Name", "Last Name" , "Email", "Contact Number", "Nature","Complaint" };
        // Data to be displayed in the JTable
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        Customer c = new Customer();
		try {
    		stat = con.createStatement();
    		result = stat.executeQuery(sql);
    		
    		stat1 = con.createStatement();
    		result1 = stat1.executeQuery(sql2);
    		
    		 while (result.next() && result1.next()) {
    			 model.addRow(new Object[]{result.getString("customerid"),result.getString("firstname"), result.getString("lastname"), result.getString("email"), result.getString("contact"), result1.getString("nature"), result1.getString("complaint")});   
    			 
    		 }			

    	} catch (SQLException e1) {
    		// TODO Auto-generated catch block
    		e1.printStackTrace();
    	}
       
        // Column Names
		f.add(back); f.add(response);
        j.setModel(model);

        j.setBounds(30, 40, 200, 300);
 
        // adding it to JScrollPane
        JScrollPane scrollPane = new JScrollPane(j);
        f.add(scrollPane);
        // Frame Size
        f.setSize(1000, 300);
        // Frame Visible = true
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
   public void complaintresponse() {
	   con = MainServer.getDatabaseConnection();
   	
   		String sql ="SELECT * FROM complaint.customer";
   		
   		try {
    		   		
    		stat1 = con.createStatement();
    		result1 = stat1.executeQuery(sql);
    		
    		String info = null;
    		int i = 0;
    		 String type[]={};        
    		
    		 while (result1.next()) {
    			 type[i] = result1.getString("customerid");
    			 i++;
    		 }			
    		 JComboBox complaint = new JComboBox(type);
    		 
    		 

    	} catch (SQLException e1) {
    		// TODO Auto-generated catch block
    		e1.printStackTrace();
    	}
   		
   		
   }
    
    public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		new Employee();
	}

}
