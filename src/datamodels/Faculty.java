package datamodels;

import exceptionhandlers.InvalidDataException;

import java.time.LocalDate;
import java.util.ArrayList;

import static controllers.Application.getDEBUG_LOGGER;

public class Faculty extends Person{

    public Faculty(){
        super();
        this.listOfCourses = new ArrayList<>();
    }

    private LocalDate dateOfHire;

    private double salary;

    private String status = "Fulltime";

    private ArrayList<Course> listOfCourses;

    public LocalDate getDateOfHire() {
        return dateOfHire;
    }


    public void setDateOfHire(LocalDate dateOfHire) {

        this.dateOfHire = dateOfHire;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary)throws InvalidDataException {
        //Test for valid salary
        if (salary <= 0) {
            this.salary = 0;
            throw new InvalidDataException("Invalid Salary, setting to $0");
        }
        //If valid,set for salary
        this.salary = salary;
        getDEBUG_LOGGER().finest("setting salary to: "+salary);
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status)throws InvalidDataException {
        if(status==null || status.isBlank()){
            this.status = "Fulltime";
            throw new InvalidDataException("Invalid status, setting to Fulltime");
        }
        this.status = status;
        getDEBUG_LOGGER().finest("setting status to: "+status);
    }

    public ArrayList<Course> getListOfCourses() {
        return listOfCourses;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("Faculty{");
        String res = "name="+ this.getName() +", address="+this.getAddress()+", dateOfBirth="+this.getDateOfBirth()+", dateOfHire="+dateOfHire+", Salary="+salary+", Status="+status;
        builder.append(res);
        for (Course course : listOfCourses) {
            builder.append(course.toString());
        }
        builder.append('}');
        return builder.toString();
    }
}
