package view;

import controllers.MainMenuController;

public class MainMenu extends javax.swing.JFrame {

    // Menu form components
    // Top level menu options
    private javax.swing.JMenu classroomMenuOption = new javax.swing.JMenu("Classrooms");
    private javax.swing.JMenu offeredCoursesMenuOption = new javax.swing.JMenu("Courses");
    private javax.swing.JMenu facultyMenuOption = new javax.swing.JMenu("Faculty");
    private javax.swing.JMenu studentMenuOption = new javax.swing.JMenu("Students");
    private javax.swing.JMenu fileMenuOption = new javax.swing.JMenu("File");

    // Sub-menu choices
    private javax.swing.JMenuItem addClassroom = new javax.swing.JMenuItem("Add Classroom");
    private javax.swing.JMenuItem saveClassroom = new javax.swing.JMenuItem("Save Classroom");
    private javax.swing.JMenuItem listClassrooms = new javax.swing.JMenuItem("List Classrooms");
    private javax.swing.JMenuItem readClassrooms = new javax.swing.JMenuItem("Read Classrooms");
    private javax.swing.JMenuItem addOfferedCourse = new javax.swing.JMenuItem("Add Course");
    private javax.swing.JMenuItem saveCourse = new javax.swing.JMenuItem("Save Course");
    private javax.swing.JMenuItem listOfferedCourses = new javax.swing.JMenuItem("List Courses");
    private javax.swing.JMenuItem readCourse = new javax.swing.JMenuItem("Read Courses");
    private javax.swing.JMenuItem addFaculty = new javax.swing.JMenuItem("Add Faculty");
    private javax.swing.JMenuItem saveFaculty = new javax.swing.JMenuItem("Save Faculty");
    private javax.swing.JMenuItem listFaculty = new javax.swing.JMenuItem("List Faculty");
    private javax.swing.JMenuItem readFaculty = new javax.swing.JMenuItem("Read Faculties");
    private javax.swing.JMenuItem addStudent = new javax.swing.JMenuItem("Add Student");
    private javax.swing.JMenuItem saveStudent = new javax.swing.JMenuItem("Save Student");
    private javax.swing.JMenuItem listStudents = new javax.swing.JMenuItem("List Students");
    private javax.swing.JMenuItem readStudents = new javax.swing.JMenuItem("Read Students");
    private javax.swing.JMenuItem exitApplication = new javax.swing.JMenuItem("Exit");

    // Logger
    private javax.swing.JMenuItem setLogLevel = new javax.swing.JMenu("Set Log Level");
    private javax.swing.JMenuItem logFinest = new javax.swing.JMenuItem("Log All");
    private javax.swing.JMenuItem logInfo = new javax.swing.JMenuItem("Log Info");
    private javax.swing.JMenuItem logWarning = new javax.swing.JMenuItem("Log Warning");
    private javax.swing.JMenuItem logSevere = new javax.swing.JMenuItem("Log Severe");

    // Menu bar that contains the top level menu options
    private javax.swing.JMenuBar menuBar = new javax.swing.JMenuBar();

    /**
     * Constructor
     */
    public MainMenu(MainMenuController controller) {

        // Add the top level menu choices to the menu bar
        menuBar.add(fileMenuOption);
        menuBar.add(classroomMenuOption);
        menuBar.add(offeredCoursesMenuOption);
        menuBar.add(facultyMenuOption);
        menuBar.add(studentMenuOption);

        // Add the sub menu items
        fileMenuOption.add(setLogLevel);
        fileMenuOption.add(exitApplication);
        classroomMenuOption.add(addClassroom);
        classroomMenuOption.add(listClassrooms);
        classroomMenuOption.add(saveClassroom);
        classroomMenuOption.add(readClassrooms);
        offeredCoursesMenuOption.add(addOfferedCourse);
        offeredCoursesMenuOption.add(listOfferedCourses);
        offeredCoursesMenuOption.add(saveCourse);
        offeredCoursesMenuOption.add(readCourse);
        facultyMenuOption.add(addFaculty);
        facultyMenuOption.add(listFaculty);
        facultyMenuOption.add(saveFaculty);
        facultyMenuOption.add(readFaculty);
        studentMenuOption.add(addStudent);
        studentMenuOption.add(listStudents);
        studentMenuOption.add(saveStudent);
        studentMenuOption.add(readStudents);

        // Add menu items to log level menu
        setLogLevel.add(logFinest);
        setLogLevel.add(logInfo);
        setLogLevel.add(logWarning);
        setLogLevel.add(logSevere);

        // Link the menu bar to the form
        setJMenuBar(menuBar);

        // Once the form is created, link this controller to the sub menu items
        addClassroom.addActionListener(controller);
        listClassrooms.addActionListener(controller);
        saveClassroom.addActionListener(controller);
        readClassrooms.addActionListener(controller);
        exitApplication.addActionListener(controller);
        addOfferedCourse.addActionListener(controller);
        listOfferedCourses.addActionListener(controller);
        saveCourse.addActionListener(controller);
        readCourse.addActionListener(controller);
        addFaculty.addActionListener(controller);
        listFaculty.addActionListener(controller);
        saveFaculty.addActionListener(controller);
        readFaculty.addActionListener(controller);
        addStudent.addActionListener(controller);
        listStudents.addActionListener(controller);
        saveStudent.addActionListener(controller);
        readStudents.addActionListener(controller);
        logFinest.addActionListener(controller);
        logInfo.addActionListener(controller);
        logWarning.addActionListener(controller);
        logSevere.addActionListener(controller);

        /**
         * set close function to dispose of the form
         */
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        // Set Size of form after creating
        setSize(400, 150);

    }

}
