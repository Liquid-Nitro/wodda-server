package server;

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

import javax.swing.JOptionPane;

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
        //this.waitForRequests();

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
            String sql = String.format("INSERT INTO wodda. `customers` (cusid, password, firstname, lastname, email, contactno) VALUES('%s','%s','%s','%s','%s','%s)'",cus.getCustomerId(),cus.getPassword(),cus.getFirstName(),cus.getLastName(),cus.getEmail(), cus.getContactNumber());
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

        private void addEmployeeToDB(Customer cus){
            String sql = String.format("");
        }

        private void waitFOrRequests(){
            String action = "";
        }
}



