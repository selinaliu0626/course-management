/*
 * Listens for events on the input form. 
 * Implements the ActionListener interface which contains a single method, 
 * "actionPerformed" - this method contains all the logic to process the data
 * on the form, as well as several other events
 */
package controllers;

import datacontainers.CourseDC;
import datacontainers.StudentDC;
import datamodels.Student;
import exceptionhandlers.ErrorPopup;
import exceptionhandlers.InvalidDataException;
import view.inputforms.StudentInputForm;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.Month;

import static controllers.Application.getDEBUG_LOGGER;

public class InputStudentFormController implements ActionListener {

    // The data datacontainers are passed in
    StudentDC studentDC;
    CourseDC courseDC;

    // Input form is created here
    StudentInputForm form;

    // Create a new Student object used in the event methods
    Student newStudent;

    public InputStudentFormController(StudentDC studentDC,
            CourseDC courseDC) {

        getDEBUG_LOGGER().finest("Construct InputStudentFormController.");
        // store local pointers to the data datacontainers passed in
        this.studentDC = studentDC;
        this.courseDC = courseDC;

        // create the form, pass it this controller
        form = new StudentInputForm(this);

    }

    public void actionPerformed(ActionEvent event) {
        if (event.getActionCommand().equals("Save")) {
            this.saveData();
        } else if (event.getActionCommand().equals("Clear")) {
            this.clearForm();
        } else if (event.getActionCommand().equals("Close")) {
            this.closeForm();
        }
    }

    /**
     * Private method to save all the data on the form and create a new student
     * object
     */
    public void saveData() {

        // Create a new Student object used in the event methods
        newStudent = new Student();

        try {
            // Retrieve data from all text fields and store directly into object
            newStudent.setName(form.getNameField().getText());
            newStudent.setAddress(form.getAddressField().getText());
            String studentIdString = form.getStudentIdField().getText();
            newStudent.setStudentID(studentIdString);

            // Retrieve GPA and convert to a double before storing in object
            // If there is a problem converting to a double, it will throw a built in
            // runtime exception which we will catch here and handle it by storing a
            // GPA of zero.
            // An example of testing in the form code and not the data model
            float gpadouble = 0;
            try {
                String gpastring = form.getGpaField().getText();
                gpadouble = Float.parseFloat(gpastring);
            } catch (NumberFormatException exp) {
                getDEBUG_LOGGER().warning(exp.getMessage());
                new ErrorPopup(this.form, exp);
            }
            try{
                newStudent.setGPA(gpadouble);
            } catch (InvalidDataException e) {
                getDEBUG_LOGGER().warning(e.getMessage());
                new ErrorPopup(this.form, e);
            }

            // We retrieve the date data and convert to a LocalDate object
            LocalDate lcDate = this.getDate(form.getDateOfBirthFormattedTextField1().getText());

            // Store
            try {
                newStudent.setDateOfBirth(lcDate);
            } catch (InvalidDataException e){
                getDEBUG_LOGGER().warning(e.getMessage());
                new ErrorPopup(this.form, e);
            }
            lcDate = this.getDate(form.getDateOfGraduationFormattedTextField().getText());
            newStudent.setDateOfGraduation(lcDate);
            // Store
            this.studentDC.getListOfStudents().add(newStudent);
            getDEBUG_LOGGER().finest("Added: "+newStudent.toString());
        } catch (InvalidDataException e){
            getDEBUG_LOGGER().severe(e.getMessage());
            new ErrorPopup(this.form, e);
        }
    }

    /**
     * Private method to clear the data
     */
    private void clearForm() {
        getDEBUG_LOGGER().finest("clear Student form");
        // The text fields are set to blank
        form.getNameField().setText("");
        form.getStudentIdField().setText("");
        form.getAddressField().setText("");
        form.getGpaField().setText("");
        form.getDateOfBirthFormattedTextField1().setText("");
        form.getDateOfGraduationFormattedTextField().setText("");
    }

    /**
     * Private method to close the form
     */
    private void closeForm() {
        form.dispose();
    }

    //  The following methods are used by the form, you don't need to know how 
    //  these work but if you want to see how form data is transformed into 
    //  actual data types from strings, it's worth a read
    private LocalDate getDate(String formStringDate) {

        // Try and parse the date values.  For student, we don't use spinners so
        // the user needs to enter values.  If they are not valid, set a default date
        // but let the user know.
        String[] dateElements = formStringDate.split("-");
        try {
            Integer dobMonth = this.getIntegerMonthFromString(dateElements[0]);
            Integer dobDay = Integer.parseInt(dateElements[1]);
            Integer dobYear = Integer.parseInt(dateElements[2]);
            LocalDate newdate = LocalDate.of(dobYear, dobMonth, dobDay);
            getDEBUG_LOGGER().finest("set date as: "+newdate.toString());
            return newdate;
        } catch (NumberFormatException exp) {
            // Bad date, set a default
            getDEBUG_LOGGER().warning("Bad date, setting default date");
            new ErrorPopup(form, new InvalidDataException("Bad date, setting default date"));
            LocalDate newdate = LocalDate.of(1970, Month.JANUARY, 1);
            return newdate;
        }
    }

    private int getIntegerMonthFromString(String stringmonth) {
        if (stringmonth.equals("Jan")) {
            return 0;
        } else if (stringmonth.equals("Feb")) {
            return 1;
        } else if (stringmonth.equals("Mar")) {
            return 2;
        } else if (stringmonth.equals("Apr")) {
            return 3;
        } else if (stringmonth.equals("May")) {
            return 4;
        } else if (stringmonth.equals("June")) {
            return 5;
        } else if (stringmonth.equals("Jul")) {
            return 6;
        } else if (stringmonth.equals("Aug")) {
            return 7;
        } else if (stringmonth.equals("Sep")) {
            return 8;
        } else if (stringmonth.equals("Oct")) {
            return 9;
        } else if (stringmonth.equals("Nov")) {
            return 10;
        } else {
            return 11;
        }
    }

}
