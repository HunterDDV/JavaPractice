package studentserver;

/**
 *
 * @author Denzil Vorster
 *
 */

import java.io.Serializable;

public class StudentDetails implements Serializable {

    //Private variables of the student
    private String studentID = "0";
    private String studentName = "default";
    private String studentSurname = "default";
    private String studentContactNbr = "123";
    private String studentAddress = "default";

    //Constructors
    public StudentDetails() {

    }

    public StudentDetails(String studentID, String studentName, String studentSurname, String studentContactNbr, String studentAddress) {
        this.studentID = studentID;
        this.studentName = studentName;
        this.studentSurname = studentSurname;
        this.studentContactNbr = studentContactNbr;
        this.studentAddress = studentAddress;
    }

    //All getter methods
    public String getStudentID() {
        return studentID;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getStudentSurname() {
        return studentSurname;
    }

    public String getStudentContactNbr() {
        return studentContactNbr;
    }

    public String getStudentAddress() {
        return studentAddress;
    }

}
