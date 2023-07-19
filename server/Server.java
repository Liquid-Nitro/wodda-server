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
                    String url = "jdbc:mysql://localhost:3306/dblab";
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


}
