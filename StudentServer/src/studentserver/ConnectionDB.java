package studentserver;

/**
 *
 * @author Denzil Vorster
 *
 */

import java.sql.*;
import javax.swing.JOptionPane;

class ConnectionDB {

    //All databse connection variables
    PreparedStatement prepStatement = null;
    ResultSet resultSet = null;
    
    private Connection connection = null;
    private String sql = null;
    
    private static final String url = "jdbc:mysql://localhost/pihe2019";
    private static final String username = "root";
    private static final String password = "root";

    public ConnectionDB() {

    }

    //Method for making the connection to the database
    public void createConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, password);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Connection could not be made: " + e);
            
        }
    }

    //Method for inserting into the database
    public void inserStudentRecord(
            String studentID, String studentName, String studentSurname, String studentContactNbr, String studentAddress) {
            sql = String.format("INSERT INTO details(student_ID, firstname, lastname, Contact_number, address)\nVALUES(?, ?, ?, ?, ?)");

        try {
            prepStatement = connection.prepareStatement(sql);
            
            prepStatement.setString(1, studentID);
            prepStatement.setString(2, studentName);
            prepStatement.setString(3, studentSurname);
            prepStatement.setString(4, studentContactNbr);
            prepStatement.setString(5, studentAddress);
            
            prepStatement.executeUpdate();
            
        } catch (SQLException v) {
            System.out.println("SQL error: " + v);
            
        }

    }

    //Method for closing the connection
    public void close() {
        try {
            connection.close();
            
        } catch (SQLException ex) {
            System.out.println("Could not close connection: " + ex);
            
        }
    }
}
