package datacontainers;

import datamodels.Student;

import java.util.ArrayList;

public class StudentDC {
    private ArrayList<Student> listOfStudents = new ArrayList<>();
    public void setListOfStudents(ArrayList<Student> listOfStudents){
        this.listOfStudents = listOfStudents;
    }

    public ArrayList<Student> getListOfStudents() {
        return listOfStudents;
    }
}
