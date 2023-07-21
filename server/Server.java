package server;


/*
 * The following will have information rgarding the database
 * Datbase name wodda
 * 
 * tables customers,employees, complaints, livechat
 * 
 * customers
 * cusid 	    varchar(30) primary key
 * password     varchar(30)
 * firstname    varchar(30)
 * lastname     varchar(30)
 * email        varchar(30)
 * contactno    varchar(30)
 * 
 * complaints
 * complaintid  varchar(30) primary key
 * cid          varchar(30) foreign key
 * category     varchar(30)
 * details  	varchar(50)
 * 
 * employees
 * sid          varchar(30) primary key
 * password     varchar(30)
 * firstname    varchar(30)
 * lastname     varchar(30)
 * email        varchar(30)
 * contactno    varchar(30)
 * role         varchar(30)
 * 
 * 
 * 
 * 
 */


import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

import client.Customer;
import client.Employee;

public class Server {

    private ServerSocket servSock;
    private Socket connection ;
    private ObjectOutputStream os;
    private ObjectInputStream is;
    private static Connection dBConn = null;
    private Statement stmt;
    private ResultSet result = null;

    
    public Server(){
        this.createConnection();
        this.waitForRequests();

    }

    private void createConnection(){
        try{
            //create new instance of the serverSocket listening to poort 8888
            servSock = new ServerSocket(8888);

        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

    private void configureStreams() {
        try{
            //Instantiate the output stream, using the getOutputStream method
            //of the socket object as argument to the constructor
            os = new  ObjectOutputStream(connection.getOutputStream());
            //Instantiate the input stream, using the getOutputStream method
            //of the socket object as argument to the constructor
            is = new ObjectInputStream(connection.getInputStream());

            
        }catch(IOException ex){
            ex.printStackTrace();
        }
    
    }

    private static Connection getDatabaseConnection(){
            if (dBConn == null){
                try{
                    String url = "jdbc:mysql://localhost:3306/wodda";
                    dBConn = DriverManager.getConnection(url, "root", "");
                    JOptionPane.showMessageDialog(null, "DB Connection Established", "CONNECTION STATUS", JOptionPane.INFORMATION_MESSAGE);
                }catch(SQLException ex){
                    JOptionPane.showMessageDialog(null, "Could not connect to database \n" + ex,"Connection Failure", JOptionPane.ERROR_MESSAGE); 
                }
            }

            return dBConn;


        }

        private void closeConnection(){
            try{
                os.close();
                is.close();
                connection.close();
            }catch(IOException ex){
                ex.printStackTrace();
            }
        }

        

        private void addCustomerToDB(Customer cus){
            String sql = String.format("INSERT INTO wodda. `customers` (cusid, password, firstname, lastname, email, contactno) VALUES('%s','%s','%s','%s','%s','%s')",cus.getCustomerId(),cus.getPassword(),cus.getFirstName(),cus.getLastName(),cus.getEmail(), cus.getContactNumber());
            try{
                stmt = dBConn.createStatement();
                if(stmt.executeUpdate(sql) == 1){
                    os.writeObject(true);//Return true to client if successful

                }else{
                    os.writeObject(true);//Return flase to client if unsuccessful :(
                }
            }catch(IOException ioe){
                //save logs for later they are kind of annoying
                ioe.printStackTrace();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }

        private Customer findCustomerbyId(String id){
            Customer cus = new Customer();
            String query = String.format("SELECT * FROM wodda.customers WHERE cusid = %s",id);
            try{
                stmt = dBConn.createStatement();
                result = stmt.executeQuery(query);
                if(result.next()){
                    cus.setCustomerId(result.getString(1));
                    cus.setPassword(result.getString(2));
                    cus.setFirstName(result.getString(3));
                    cus.setLastName(result.getString(4));
                    cus.setEmail(result.getString(5));
                    cus.setContactNumber(result.getString(6));

                }
            }catch(SQLException e){
                e.printStackTrace();
            }
            return cus;
        }

        //deleteCustomerInDB
        private void deleteCustomerInDB(String id){
            String query = String.format("Delete * FROM wodda.customers WHERE cusid = %s",id);
            try{
                stmt = dBConn.createStatement();
                result = stmt.executeQuery(query);

                if (stmt.executeUpdate(query) == 1){
                    os.writeObject(true);

                }else{
                    os.writeObject(true);//Return flase to client if unsuccessful :(
                }
            }catch(IOException ioe){
                ioe.printStackTrace();  
                
            }catch(SQLException e){
                e.printStackTrace();
            }
        }

        // viewAllCustomers
    private List<Customer> viewAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        String query = "SELECT * FROM customers";
        try {
            stmt = dBConn.createStatement();
            result = stmt.executeQuery(query);
            while (result.next()) {
                Customer cus = new Customer();
                cus.setCustomerId(result.getString("cusid"));
                cus.setPassword(result.getString("password"));
                cus.setFirstName(result.getString("firstname"));
                cus.setLastName(result.getString("lastname"));
                cus.setEmail(result.getString("email"));
                cus.setContactNumber(result.getString("contactno"));
                customers.add(cus);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }//end of viewAll Customers


        //addEmployeetoDB starts here
        private void addEmployeeToDB(Employee emp) {
            String sql = String.format("INSERT INTO employees (sid, password, firstName, lastName, email, contactno, role) " +
                                    "VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s')",
                                    emp.getstaffId(), emp.getPassword(), emp.getFirstName(), emp.getLastName(),
                                    emp.getEmail(), emp.getContactNumber(), emp.getRole());
    
            try {
                stmt = dBConn.createStatement();
                if (stmt.executeUpdate(sql) == 1) {
                    os.writeObject(true); // Return true to the client if successful
                } else {
                    os.writeObject(false); // Return false to the client if unsuccessful
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }//end of second catch
        }//end of addEmployeetoDB


    //find Employee starts here
    private Employee findEmployeeById(String staffId) {
        Employee emp = new Employee();
        String query = String.format("SELECT * FROM employees WHERE sid = '%s'", staffId);
        try {
            stmt = dBConn.createStatement();
            result = stmt.executeQuery(query);
            if (result.next()) {
                emp.setstaffId(result.getString(1));
                emp.setPassword(result.getString(2));
                emp.setFirstName(result.getString(3));
                emp.setLastName(result.getString(4));
                emp.setEmail(result.getString(5));
                emp.setContactNumber(result.getString(6));
                emp.setRole(result.getString(7));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return emp;
    }//find Employee ends here

    //delete employee starts here
    private void deleteEmployeeById(String staffId) {
        String sql = String.format("DELETE FROM employees WHERE staffId = '%s'", staffId);

        try {
            stmt = dBConn.createStatement();
            if (stmt.executeUpdate(sql) == 1) {
                os.writeObject(true); // Return true to the client if successful
            } else {
                os.writeObject(false); // Return false to the client if unsuccessful
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }//delete employee ends here

    //viewEmployees
    private List<Employee> viewAllEmployees() {
    List<Employee> employees = new ArrayList<>();
    String query = "SELECT * FROM employees";
    try {
        stmt = dBConn.createStatement();
        result = stmt.executeQuery(query);
        while (result.next()) {
            Employee emp = new Employee();
            emp.setstaffId(result.getString("staffId"));
            emp.setPassword(result.getString("password"));
            emp.setFirstName(result.getString("firstName"));
            emp.setLastName(result.getString("lastName"));
            emp.setEmail(result.getString("email"));
            emp.setContactNumber(result.getString("contactNumber"));
            emp.setRole(result.getString("role"));
            employees.add(emp);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return employees;
    }   //view employees end here

    // addComplaintToDB starts here
    private void addComplaintToDB(Complaint complaint) {
        String sql = String.format("INSERT INTO complaints (complaintid, cid, category, details) " +
                "VALUES ('%s', '%s', '%s', '%s')",
                complaint.getComplaintId(), complaint.getCustomerId(), complaint.getCategory(), complaint.getDetails());

        try {
            stmt = dBConn.createStatement();
            if (stmt.executeUpdate(sql) == 1) {
                os.writeObject(true); // Return true to the client if successful
            } else {
                os.writeObject(false); // Return false to the client if unsuccessful
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }// end of addComplaintToDB

    private void waitForRequests(){
            String action = "";
            getDatabaseConnection();
            Customer cObj = null;
            Employee e = null;

            try{
                while(true){
                    connection = servSock.accept();
                    this.configureStreams();
                    try{
                        action = (String) is.readObject();
                        if(action.equals("Add Customer")){
                            cObj = (Customer) is.readObject();
                            addCustomerToDB(cObj);
                            os.writeObject(true);
                        } else if(action.equals("Find Customer")){
                            String id = (String) is.readObject();
                            //call method to find customer based on the id
                            cObj = findCustomerbyId(id);
                            os.writeObject(cObj);
                        }
                        else if (action.equals("Add Employee")) {
                            Employee empObj = (Employee) is.readObject();
                            addEmployeeToDB(empObj);
                            os.writeObject(true);
                            }
                        else if (action.equals("Find Employee")) {
                            String id = (String) is.readObject();
                            e = findEmployeeById(id);
                            os.writeObject(e);
                        }
                        else if (action.equals("Delete Employee")) {
                        String staffId = (String) is.readObject();
                        deleteEmployeeById(staffId);
                        os.writeObject(true);
                        }
                        else if (action.equals("View Employees")) {
                        List<Employee> employees = viewAllEmployees();
                        os.writeObject(employees);
                        }


                    }catch(ClassNotFoundException ex){
                        ex.printStackTrace();

                    }catch(ClassCastException ex){
                        ex.printStackTrace();
                    }
                    this.closeConnection();
                   
                }
            }catch(EOFException ex){
                System.out.println("Client has terminated connections with the server");

            }catch(IOException ex){
                ex.printStackTrace();
            }
        }




}








