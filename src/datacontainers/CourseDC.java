package datacontainers;

import datamodels.Course;

import java.util.ArrayList;
import java.util.List;

public class CourseDC {

    private ArrayList<Course> listOfCourses = new ArrayList<>();

    public List<Course> getListOfCourses() {
        return this.listOfCourses;
    }

    public void setListOfCourses(ArrayList<Course> listOfCourses) {
        this.listOfCourses = listOfCourses;
    }
}
