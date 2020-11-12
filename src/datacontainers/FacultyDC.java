package datacontainers;

import datamodels.Faculty;

import java.util.ArrayList;

public class FacultyDC {

    private ArrayList<Faculty> listOfFaculties = new ArrayList<>();

    public void setListOfFaculty(ArrayList<Faculty> listOfFaculties) {
        this.listOfFaculties = listOfFaculties;
    }

    public ArrayList<Faculty> getListOfFaculty() {
        return listOfFaculties;
    }
}
