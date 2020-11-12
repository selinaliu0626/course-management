package interfaces;

import datamodels.Classroom;
import exceptionhandlers.InvalidDataException;

public interface ICourse {
    String getCourseID();

    String getCourseName();

    Classroom getClassroom();

    void setCourseID(String courseID) throws InvalidDataException;

    void setCourseName(String courseName) throws InvalidDataException;

    void setClassroom(Classroom classroom) throws InvalidDataException;

}