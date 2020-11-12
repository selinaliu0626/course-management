package datamodels;

import interfaces.ICourse;
import exceptionhandlers.InvalidDataException;

import static controllers.Application.getDEBUG_LOGGER;

public class Course implements ICourse {
    private String courseID;

    private String courseName;

    private Classroom classroom;

    @Override
    public String getCourseID() {
        return courseID;
    }

    @Override
    public String getCourseName() {
        return courseName;
    }

    @Override
    public Classroom getClassroom() {
        return classroom;
    }

    @Override
    public void setCourseID(String courseID)throws InvalidDataException {
        // Test for valid courseID
        if (courseID!=null && courseID.isBlank()) {
            throw new InvalidDataException("Course ID missing");
        }
        if (!courseID.matches("^[a-zA-Z]{4}[0-9]{3}$")) {
            throw new InvalidDataException("Invalid courseID specified");
        }
        // If valid, set courseID
        String updatedCourseID = courseID.substring(0, 4).toUpperCase() +
                courseID.substring(4);
        this.courseID = updatedCourseID;
        getDEBUG_LOGGER().finest("setting courseID to: "+courseID);
    }

    @Override
    public void setCourseName(String courseName)throws InvalidDataException {
    //Test for valid courseName
        if (courseName != null && courseName.isBlank()) {
            throw new InvalidDataException("No courseName specified");}
    //If valid,set CourseName
        this.courseName = courseName;
        getDEBUG_LOGGER().finest("setting courseName to: "+courseName);
    }

    @Override
    public void setClassroom(Classroom classroom)throws InvalidDataException  {
        if(classroom == null){
            this.classroom = new Classroom();
            throw new InvalidDataException("Invalid classroom, setting to default classroom");
        }
        this.classroom = classroom;
        getDEBUG_LOGGER().finest("setting classroom to: "+classroom.toString());
    }
    @Override
    public String toString() {
        return "CourseID: " + courseID + "; CourseName: " + courseName + ";\nClassroom: " + classroom;
    }
}
