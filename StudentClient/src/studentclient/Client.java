package studentclient;

/**
 *
 * @author Denzil Vorster
 *
 */

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.*;

public class Client extends JFrame {

    //Data transfer variables
    private ObjectOutputStream outputS;
    private ObjectInputStream inputS;
    private Socket client;

    //JFrame Items
    private JLabel labStudentID = new JLabel("Student ID:");
    private JLabel labStudentNsme = new JLabel("First Name:");
    private JLabel labStudentSurname = new JLabel("Last Name:");
    private JLabel labStudentContactNbr = new JLabel("Contact Number:");
    private JLabel labStudentAddress = new JLabel("Address:");
    private JLabel labTemp = new JLabel("");

    private JTextField tfStudentID = new JTextField(30);
    private JTextField tfStudentNsme = new JTextField(30);
    private JTextField tfStudentSurname = new JTextField(30);
    private JTextField tfStudentContactNbr = new JTextField(30);
    private JTextField tfStudentAddress = new JTextField(30);

    private JButton butRegister = new JButton("Register");

    private JPanel Panel = new JPanel();

    private StudentDetails student;

    //Null constructor
    public Client() {
        super("Student Details");
        createForm();
        
        //Action Listener attached to the register button
        butRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                student = new StudentDetails(tfStudentID.getText(), tfStudentNsme.getText(), tfStudentSurname.getText(), tfStudentContactNbr.getText(), tfStudentAddress.getText());
                runClient();

            }
        });
    }

    //Method for the JFrame creation and instantation
    private void createForm() {
        //Adding the panel to the JFrame and attaching the Grid Layout
        this.add(Panel, BorderLayout.CENTER);
        GridLayout Gl = new GridLayout(6, 2);
        Panel.setLayout(Gl);

        //Adding all conmponents to the panel
        Panel.add(labStudentID);
        Panel.add(tfStudentID);
        
        Panel.add(labStudentNsme);
        Panel.add(tfStudentNsme);
        
        Panel.add(labStudentSurname);
        Panel.add(tfStudentSurname);
        
        Panel.add(labStudentContactNbr);
        Panel.add(tfStudentContactNbr);
        
        Panel.add(labStudentAddress);
        Panel.add(tfStudentAddress);
        
        Panel.add(labTemp);
        Panel.add(butRegister);
        
        //Setting the JFrame properties
        this.setSize(400, 300);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    //Another "master" method for running multiple other methods
    public void runClient() {
        try {
            //The methods run by the "master"
            connecToServer();
            getStreams();
            runProcess();
            
        } catch (Exception e) {
            System.out.println("Something went wrong running all the methods: " + e);
            
        } finally {
            closeConnection();
            
        }
    }

    //Methods for connecting client to server
    private void connecToServer() {
        System.out.println("Attemping to connect.\n");
        
        try {
            client = new Socket("localhost", 9806);
            System.out.println("Connection to database successfull.");
            
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Connection failed!" + ex);
        }
    }

    //Method for establishing input and output streams
    private void getStreams() {
        try {
            outputS = new ObjectOutputStream(client.getOutputStream());
            inputS = new ObjectInputStream(client.getInputStream());
            outputS.flush();
            
        } catch (IOException ex) {
            System.out.println("could not open input and output");
            
        }
    }

    //Method for sending the object to the server
    private void runProcess() {
        try {
            outputS.writeObject(student);
            System.out.println("Data successfully sent.");
            
        } catch (IOException ex) {
            System.out.println("Data failed to send.");
            
        }
    }

    //Method for closing all connection variables
    private void closeConnection() {
        try {
            outputS.close();
            inputS.close();
            client.close();
            
        } catch (Exception ex) {
            System.out.println("All connections could not be closed: " + ex);

        }
    }
}
