package datamodels;

import exceptionhandlers.InvalidDataException;

import java.time.LocalDate;
import java.util.ArrayList;

import static controllers.Application.getDEBUG_LOGGER;

public class Student extends Person {
    public Student(){
        super();
        this.listOfCourse = new ArrayList<>();
    }
    private int studentID;

    private LocalDate dateOfGraduation;

    private float gpa;

    private ArrayList<Course> listOfCourse;

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) throws InvalidDataException{
        //Test for valid studentID
        if(studentID== 0){
            throw new InvalidDataException("No studentID");
        }
        if(studentID/1000000 <= 0 || studentID/1000000 >= 10){
            throw new InvalidDataException("Invalid StudentID: "+studentID);
        }
        //If valid,set for studentID
        this.studentID = studentID;
        getDEBUG_LOGGER().finest("setting studentID to: "+studentID);
    }

    public void setStudentID(String studentID) throws InvalidDataException {
        if (studentID == null || studentID.isBlank()) {
            throw new InvalidDataException("Student ID not specified.");
        }
        if(studentID.charAt(0)=='0'){
            throw new InvalidDataException("Student ID can not start with zero: "+studentID);
        }
        if(studentID.charAt(0)=='-'){
            throw new InvalidDataException("Student ID can not be negative: "+studentID);
        }
        if(studentID.length() != 7){
            throw new InvalidDataException("Student ID must be 7 characters in length: "+studentID);
        }
        int newId = 0;
        try {
            newId = Integer.parseInt(studentID);
        } catch (Exception e){
            throw new InvalidDataException("Not valid number string for ID");
        }
        if(newId < 1000000 || newId > 9999999){
            throw new InvalidDataException("Invalid StudentID");
        }
        //If valid,set for studentID
        this.studentID = newId;
        getDEBUG_LOGGER().finest("setting studentID to: "+studentID);
    }

    public LocalDate getDateOfGraduation() {
        return dateOfGraduation;
    }

    public void setDateOfGraduation(LocalDate dateOfGraduation) {
        this.dateOfGraduation = dateOfGraduation;
    }

    public float getGPA() {
        return gpa;
    }

    public void setGPA(float gpa)throws InvalidDataException {
        //Test for valid GPA
        if (gpa > 0 && gpa <= 4) {
            this.gpa = gpa;
        } else {
            this.gpa = 0;
            throw new InvalidDataException("Invalid GPA, setting to 0.0");
        }
    }

    public ArrayList<Course> getListOfCourses() {
        return listOfCourse;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Student{");
        String res = "Name="+ this.getName() +", Address="+this.getAddress()+", Date of Birth="+this.getDateOfBirth()+
                ", StudentID="+studentID+", Graduation="+dateOfGraduation+", GPA="+gpa;
        stringBuilder.append(res);
        for (Course course : listOfCourse) {
            stringBuilder.append(course.toString());
        }
        stringBuilder.append('}');
        return stringBuilder.toString();
    }
}
