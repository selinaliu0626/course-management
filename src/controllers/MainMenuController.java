/*
 * Listens for events on the menu form. 
 * Implements the ActionListener interface which contains a single method, 
 * "actionPerformed"
 */
package controllers;

import datacontainers.ClassroomDC;
import datacontainers.CourseDC;
import datacontainers.FacultyDC;
import datacontainers.StudentDC;
import exceptionhandlers.ErrorPopup;
import utilities.io.ClassroomIO;
import utilities.io.CourseIO;
import utilities.io.FacultyIO;
import utilities.io.StudentIO;
import view.MainMenu;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.SimpleFormatter;

import static controllers.Application.getDEBUG_LOGGER;

public class MainMenuController implements ActionListener {

    // File location
    JFileChooser fileChooserDialog = new JFileChooser();

    // The data models are instantiated here and passed to the 
    // constructors for the controllers
    ClassroomDC classroomDC = new ClassroomDC();
    CourseDC courseDC = new CourseDC();
    StudentDC studentDC = new StudentDC();
    FacultyDC facultyDC = new FacultyDC();

    // The main menu form gets created here. Notice it takes this controller object
    // as an argument to the constructor
    private MainMenu mainMenu = new MainMenu(this);

    // DEBUG_FILEHANDLER will log the messages created by DEBUG_LOGGER, to a file
    private static FileHandler DEBUG_FILEHANDLER;

    /**
     * Constructor.
     */
    public MainMenuController(){
        classroomDC.setListOfClassrooms(new ArrayList<>());
        courseDC.setListOfCourses(new ArrayList<>());
        studentDC.setListOfStudents(new ArrayList<>());
        facultyDC.setListOfFaculty(new ArrayList<>());

        try {

            JFileChooser fileChooserDialog = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "Log file", "log");
            fileChooserDialog.setFileFilter(filter);
            String filepath = "Default.log";
            String ext;
            try{
                fileChooserDialog.setDialogTitle("Choose Log file to write");
                int returnVal = fileChooserDialog.showSaveDialog(mainMenu);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    filepath = fileChooserDialog.getSelectedFile().getPath();
                    String[] fileNames = filepath.split("/");
                    String fileName = fileNames[fileNames.length-1];
                    if(fileName.endsWith(".log")){
                        fileName = fileName.split("\\.")[0];
                    }
                    if(fileName == null || fileName.isBlank()){
                        getDEBUG_LOGGER().severe("Log name is blank.");
                        new ErrorPopup(null, new Exception("Invalid Log File."));
                        System.exit(1);
                    }
                    ext = filter.getExtensions()[0];
                    if (!filepath.toUpperCase().endsWith(ext.toUpperCase())){
                        filepath = filepath+'.'+ext;
                    }
                }
            } catch (Exception exp) {
                getDEBUG_LOGGER().severe("Couldn't write log file: " + exp.getMessage());
            }
            // Create a debug log file with the name and location specified by args[1]
            // (This should look somewhat familiar)
            DEBUG_FILEHANDLER = new FileHandler(filepath);

            // Unlike the console output, file output needs to be formatted!
            // We will use a simple formatter class for the log file
            DEBUG_FILEHANDLER.setFormatter(new SimpleFormatter());

            // Set the default level of logging in the debug logger (can be changed by user)
            getDEBUG_LOGGER().setLevel(Level.SEVERE);

            // Link the debug file handler to the debug logger
            getDEBUG_LOGGER().addHandler(DEBUG_FILEHANDLER);

        } catch (IOException e) {
            // OOPS! Something's wrong with one of the log files, halt processing!
            System.exit(0);
        }
    }
    /**
     * The ActionListener interface contains a single method, actionPerformed
     */
    public void actionPerformed(java.awt.event.ActionEvent event) {
        //  Figure out which button was clicked
        String menuItemClicked = event.getActionCommand();

        // create the controller which will open the correct form (refer to the controller constructor
        // methods do determine which data container classes need to be passed to the controller constructors)
        if (menuItemClicked.equals("Add Classroom")) {
            InputClassroomFormController inputController = new InputClassroomFormController(classroomDC);
        } else if (menuItemClicked.equals("List Classrooms")) {
            ReportClassroomController reportController = new ReportClassroomController(classroomDC);
        }  if (menuItemClicked.equals("Add Course")) {
            InputCourseFormController inputController = new InputCourseFormController(courseDC, classroomDC);
        } else if (menuItemClicked.equals("List Courses")) {
            ReportCourseController reportController = new ReportCourseController(courseDC);
        }  if (menuItemClicked.equals("Add Faculty")) {
            InputFacultyFormController inputFacultyController = new InputFacultyFormController(facultyDC, courseDC);
        } else if (menuItemClicked.equals("List Faculty")) {
            ReportFacultyController reportController = new ReportFacultyController(facultyDC);
        }  if (menuItemClicked.equals("Add Student")) {
            InputStudentFormController inputController = new InputStudentFormController(studentDC, courseDC);
        } else if (menuItemClicked.equals("List Students")) {
            ReportStudentController reportController = new ReportStudentController(studentDC);
        } else if (menuItemClicked.equals("Save Classroom")) {
            ClassroomIO.writeJSONFile(fileChooserDialog, mainMenu, classroomDC);
        }else if (menuItemClicked.equals("Save Course")) {
            CourseIO.writeJSONFile(fileChooserDialog, mainMenu, courseDC);
        }else if (menuItemClicked.equals("Save Faculty")) {
            FacultyIO.writeJSONFile(fileChooserDialog, mainMenu, facultyDC);
        }else if (menuItemClicked.equals("Save Student")) {
            StudentIO.writeJSONFile(fileChooserDialog, mainMenu, studentDC);
        } else if (menuItemClicked.equals("Read Classrooms")) {
            classroomDC = ClassroomIO.readClassroom(fileChooserDialog, mainMenu);
        }else if(menuItemClicked.equals("Read Courses")) {
            courseDC = CourseIO.readCourse(fileChooserDialog, mainMenu);
        }else  if(menuItemClicked.equals("Read Faculties")) {
            facultyDC = FacultyIO.readFaculty(fileChooserDialog, mainMenu);
        }else  if(menuItemClicked.equals("Read Students")) {
            studentDC = StudentIO.readStudent(fileChooserDialog, mainMenu);
        }else if (menuItemClicked.equals("Exit")) {
            System.exit(0);
            // New logging menu options
        } else if (menuItemClicked.equals("Log Warning")) {
            getDEBUG_LOGGER().setLevel(Level.WARNING);
        } else if (menuItemClicked.equals("Log Info")) {
            getDEBUG_LOGGER().setLevel(Level.INFO);
        } else if (menuItemClicked.equals("Log Severe")) {
            getDEBUG_LOGGER().setLevel(Level.SEVERE);
        } else if (menuItemClicked.equals("Log All")) {
            getDEBUG_LOGGER().setLevel(Level.ALL);
        }

    }

    // Getter used in the Application.java class
    public MainMenu getMainMenu() {
        return mainMenu;
    }
}
