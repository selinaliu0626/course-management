/*
 * Listens for events on the input form. 
 * Implements the ActionListener interface which contains a single method, 
 * "actionPerformed" - this method contains all the logic to process the data
 * on the form, as well as several other events
 */
package controllers;

import datacontainers.CourseDC;
import datacontainers.FacultyDC;
import datamodels.Faculty;
import exceptionhandlers.ErrorPopup;
import exceptionhandlers.InvalidDataException;
import view.inputforms.FacultyInputForm;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import static controllers.Application.getDEBUG_LOGGER;

public class InputFacultyFormController implements ActionListener {

    // The data containers are passed in via the constructor
    FacultyDC facultyDC;
    CourseDC offeredCourseDC;

    // The form is declared here
    FacultyInputForm form;

    public InputFacultyFormController(FacultyDC facultyDC,
            CourseDC offeredCourseDC) {

        getDEBUG_LOGGER().finest("Construct InputFacultyFormController.");
        this.facultyDC = facultyDC;
        this.offeredCourseDC = offeredCourseDC;
        form = new FacultyInputForm(this);
        form.setVisible(true);

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
     * Private method to save all the data on the form and create a new faculty
     * object
     */
    private void saveData() {

        Faculty newFaculty = new Faculty();


        // Retrieve data from all text fields and store directly into object
        // Flags errors on the required fields
        try {
            newFaculty.setName(form.getNameField().getText());
            newFaculty.setAddress(form.getAddressField().getText());
        } catch (InvalidDataException e){
            getDEBUG_LOGGER().warning(e.getMessage());
            new ErrorPopup(this.form, e);
            return;
        }
        // Retrieve status and convert to Status enumerated type and store in object
        try {
            String selectedStatus = (String) form.getStatusField().getSelectedItem();
            newFaculty.setStatus(selectedStatus);
        } catch (InvalidDataException e) {
            getDEBUG_LOGGER().warning(e.getMessage());
            new ErrorPopup(this.form, e);
        }

        // Retrieve salary and convert to a double before storing in object
        // Notice we are catching an error here in the UI code, not in the data model
        String salarystring = form.getSalaryField().getText();
        // Default value is zero
        Double salarydouble = 0d;
        try {
            if (salarystring.length() > 0) {
                try {
                    salarydouble = Double.parseDouble(salarystring);
                    newFaculty.setSalary(salarydouble);
                } catch (NumberFormatException var13) {
                    throw new InvalidDataException("Invalid Salary Value, Setting to 0");
                }
            }
        } catch (InvalidDataException e){
            getDEBUG_LOGGER().warning(e.getMessage());
            new ErrorPopup(this.form, e);
        }

        // Retrieve date of birth values from spinners and convert to a date object
        // These fields return objects so we need to first convert them to strings
        // and thenconvert them to integers
        // Faculty dates will never be empty because we use spinners
        Integer dobYear;
        Integer dobDay;
        try {
            try {
                dobYear = Integer.parseInt(form.getDateOfBirthYear().getValue().toString());
                dobDay = Integer.parseInt(form.getDateOfBirthDay().getValue().toString());
            } catch (NumberFormatException e) {
                throw new InvalidDataException("Invalid BirthDate value");
            }
            // Let's use a month mapper to convert the month to a numeric value
            Integer dobMonth = this.getIntegerMonthFromString(form.getDateOfBirthMonth().getValue().toString());
            LocalDate dateOfBirth = LocalDate.of(dobYear, dobMonth, dobDay);
            // Store in object
            newFaculty.setDateOfBirth(dateOfBirth);
        } catch (InvalidDataException e){
            getDEBUG_LOGGER().warning(e.getMessage());
            new ErrorPopup(this.form, e);
        }


        // Retrieve date of hire values from spinners and convert to a date object
        // These fields return objects so convert them to first convert them to strings
        // and thenconvert them to integers
        Integer dohYear;
        Integer dohMonth;
        Integer dohDay;
        try {
            try {
                dohYear = Integer.parseInt(form.getDateOfHireYear().getValue().toString());
                dohDay = Integer.parseInt(form.getDateOfHireDay().getValue().toString());
                // Let's use a different month mapper for date of hire and convert the
                // string month to a GregorianCalendar class month
                dohMonth = this.getMonthFromMonthArray(form.getDateOfHireMonth().getValue().toString());
            } catch (NumberFormatException e) {
                throw new InvalidDataException("Invalid HireDate Value");
            }
            LocalDate dateOfHire = LocalDate.of(dohYear, dohMonth, dohDay);
            // Store in object
            newFaculty.setDateOfHire(dateOfHire);
        } catch (InvalidDataException e){
            getDEBUG_LOGGER().warning(e.getMessage());
            new ErrorPopup(this.form, e);
        }
        // Store the object in the application data model
        this.facultyDC.getListOfFaculty().add(newFaculty);
        getDEBUG_LOGGER().finest("Added: "+newFaculty.toString());

    }

    /**
     * Private method to clear the data
     */
    private void clearForm() {

        getDEBUG_LOGGER().finest("clear Faculty form");
        // The spinners are reset to their first value
        this.form.getDateOfBirthYear().getModel().setValue("1950");
        this.form.getDateOfBirthDay().getModel().setValue("01");
        this.form.getDateOfBirthMonth().getModel().setValue("Jan");
        this.form.getDateOfHireDay().getModel().setValue("01");
        this.form.getDateOfHireMonth().getModel().setValue("Jan");
        this.form.getDateOfHireYear().getModel().setValue("1950");

        // The text fields are set to blank
        this.form.getNameField().setText("");
        this.form.getAddressField().setText("");
        this.form.getSalaryField().setText("");
        this.form.getStatusField().setSelectedIndex(0);
    }

    /**
     * Private method to close the form
     */
    private void closeForm() {
        this.form.dispose();
    }

    // The following methods are used by the form to convert data, you don't have
    // to know how they work!
    /**
     * Private method to convert the year, month, day to a long
     */
    private long getLong(int year, int month, int day) {
        long datevalue = 0;
        return datevalue;
    }

    /**
     * Private helper method to return the right month since java calendar
     * months start at zero.
     *
     * @param stringmonth
     * @return
     */
    private int getIntegerMonthFromString(String stringmonth) {
        if (stringmonth.equals("Jan")) {
            return 1;
        } else if (stringmonth.equals("Feb")) {
            return 2;
        } else if (stringmonth.equals("Mar")) {
            return 3;
        } else if (stringmonth.equals("Apr")) {
            return 4;
        } else if (stringmonth.equals("May")) {
            return 5;
        } else if (stringmonth.equals("June")) {
            return 6;
        } else if (stringmonth.equals("Jul")) {
            return 7;
        } else if (stringmonth.equals("Aug")) {
            return 8;
        } else if (stringmonth.equals("Sep")) {
            return 9;
        } else if (stringmonth.equals("Oct")) {
            return 10;
        } else if (stringmonth.equals("Nov")) {
            return 11;
        } else {
            return 12;  // default december
        }
    }

    private int getMonthFromMonthArray(String stringmonth) {
        int month = 0;
        String[] monthArray = {"Jan", "Feb", "Mar", "Apr", "May", "Jun",
            "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        for (int i = 0; i < 12; i++) {
            if (stringmonth.equals(monthArray[i])) {
                month = i + 1;
            }
        }
        return month;
    }
}
