package studentserver;

/**
 *
 * @author Denzil Vorster
 *
 */

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JOptionPane;

public class Server {

    //All connection variables for server side
    private ObjectOutputStream outputStream = null;
    private ObjectInputStream inputStream = null;
    private ServerSocket serverSocket = null;
    private Socket connectionSocket = null;
    private ConnectionDB dbConnection = null;
    private StudentDetails student = null;

    //Server constructor
    public Server() {
        startServer();

    }

    //"master" method which runs multiple required methods
    public void startServer() {
        try {
            serverSocket = new ServerSocket(9806);

            while (!serverSocket.isClosed()) {
                try {
                    //Methods run by the "master"
                    waitForConnection();
                    getStreams();
                    runProcess();

                } catch (Exception e) {
                    System.out.println("All methods not executed: " + e);

                }
            }

        } catch (Exception ex) {
            System.out.println("Something went wrong: " + ex);

        } finally {
            closeConnection();

        }
    }

    //Method which waits for a connection from the client
    private void waitForConnection() {
        System.out.println("Waiting for connection from Client.\n");

        try {
            connectionSocket = serverSocket.accept();

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Failed to establish connection." + ex);

        }
        System.out.println("Connection was successful!");

    }

    //Method for establishing input and output streams
    private void getStreams() {
        try {
            outputStream = new ObjectOutputStream(connectionSocket.getOutputStream());
            inputStream = new ObjectInputStream(connectionSocket.getInputStream());

            outputStream.flush();

        } catch (IOException ex) {
            System.out.println("I/O stream could not be established: " + ex);

        }
    }

    //Method used for inserting records into the database
    private void saveToDatabase() {
        dbConnection = new ConnectionDB();
        dbConnection.createConnection();
        dbConnection.inserStudentRecord(student.getStudentID(), student.getStudentName(), student.getStudentSurname(), student.getStudentContactNbr(), student.getStudentAddress());
        dbConnection.close();

    }

    //Method for accepting data from the client and updating it to the database
    private void runProcess() {
        try {
            student = (StudentDetails) inputStream.readObject();
            saveToDatabase();

        } catch (IOException ex) {
            System.out.println("I/O error: " + ex);

        } catch (ClassNotFoundException ex) {
            System.out.println("Class error: " + ex);

        }
    }

    //Method for closing all connection variables
    private void closeConnection() {
        try {
            outputStream.close();
            inputStream.close();
            connectionSocket.close();
            serverSocket.close();

        } catch (Exception e) {
            System.out.println("Connection could not be closed! " + e);

        }
    }
}
