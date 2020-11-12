/*
 * Listens for events on the input form. 
 * Implements the ActionListener interface which contains a single method, 
 * "actionPerformed" - this method contains all the logic to process the data
 * on the form, as well as several other events
 */
package controllers;

import datacontainers.ClassroomDC;
import datamodels.Classroom;
import exceptionhandlers.ErrorPopup;
import exceptionhandlers.InvalidDataException;
import view.inputforms.ClassroomInputForm;

import javax.swing.*;
import java.awt.event.ActionListener;

import static controllers.Application.getDEBUG_LOGGER;

public class InputClassroomFormController implements ActionListener {

    // The Classroom data model is passed in via the constructor
    private ClassroomDC m_classroomDataContainer;

    // The form is created here and this constructor object gets passed to it
    private ClassroomInputForm form;

    // Constructor 
    public InputClassroomFormController(ClassroomDC p_classroomDataContainer) {

        getDEBUG_LOGGER().finest("Construct InputClassroomFormController.");
        // Store the passed in data model
        this.m_classroomDataContainer = p_classroomDataContainer;

        // create the form
        form = new ClassroomInputForm(this);

        // make the form visible
        this.form.setVisible(true);
    }

    /**
     * Implements actionPerformed method of the ActionListener interface
     */
    public void actionPerformed(java.awt.event.ActionEvent event) {

        if (event.getActionCommand().equals("Save")) {
            this.saveData();
        } else if (event.getActionCommand().equals("Clear")) {
            this.clearForm();
        } else if (event.getActionCommand().equals("Close")) {
            this.closeForm();
        }
    }

    /**
     * Private save method If an error is thrown, handle it by creating an error
     * popup and don't save the classroom
     */
    private void saveData() {

        // Create a new classroom object
        Classroom aClassroom = new Classroom();

        try {
            // Retrieve the room numer from the form
            String roomNumber = form.getRoomNumberTextfield().getText();

            // If the room number is valid, store it in the classroom object
            aClassroom.setRoomNumber(roomNumber);

            // Finish processing the rest of the data from the form
            // Retrieve the data model associated with the drop down list
            ComboBoxModel datamodel = this.form.getRoomTypeCombobox().getModel();
            // Retrieve the selected item from the data model, notice
            // it is stored as type Object, we need to convert it in the next step
            Object selectedItem = datamodel.getSelectedItem();
            // Convert (Cast) the selected item to a String
            String roomType = (String) selectedItem;
            // Store the room type
            aClassroom.setTypeOfRoom(roomType);
            // set capacity
            int capacity;
            try {
                capacity = Integer.parseInt(this.form.getRoomCapacityTextField().getText());
            } catch (NumberFormatException e) {
                getDEBUG_LOGGER().warning("Invalid capacity value for classroom.");
                throw new InvalidDataException("Invalid capacity value");
            }
            aClassroom.setCapacity(capacity);
            // Add the new classroom to the list in ClassroomDataModel
            m_classroomDataContainer.getListOfClassrooms().add(aClassroom);
            getDEBUG_LOGGER().finest("Added:"+aClassroom.toString());
        } catch (InvalidDataException e) {
            getDEBUG_LOGGER().severe("error: "+e.getMessage());
            new ErrorPopup(this.form, e);
        }
    }

    /**
     * Private method to clearForm the data
     */
    private void clearForm() {
        getDEBUG_LOGGER().finest("clear Classroom form");
        form.getRoomNumberTextfield().setText("");
        form.getRoomTypeCombobox().setSelectedIndex(0);
        form.getRoomCapacityTextField().setText("");
    }

    /**
     * Private method to close the form
     */
    private void closeForm() {
        this.form.dispose();
    }

    // gettes to access the private data in the controller object
    public ClassroomDC getDataModel() {
        return m_classroomDataContainer;
    }

    public ClassroomInputForm getForm() {
        return form;
    }
}
