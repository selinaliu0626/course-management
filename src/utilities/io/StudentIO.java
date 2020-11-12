package utilities.io;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import datacontainers.StudentDC;
import datamodels.Student;
import view.MainMenu;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;

import static controllers.Application.getDEBUG_LOGGER;

public class StudentIO {

    static File outputFile;
    private static File inputFile;
    private static StudentDC studentDC= new StudentDC();

    /**
     * Constructor is declared private because the IO classes are utilities
     * which contain static methods
     */
    private StudentIO() {
    }

    /**
     * Writes out the classroom data in JSON format containing all classrooms in
     * the classroom data container
     *
     */
    public static void writeJSONFile(JFileChooser fileChooserDialog, MainMenu mainMenu,
                                     StudentDC studentDataContainer) {

        PrintWriter jsonFile = null;

        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Json file", "json");
        fileChooserDialog.setFileFilter(filter);
        String filepath;
        String ext;

        try {
            // Customize the file chooser dialog
            fileChooserDialog.setDialogTitle("Save JSON File");

            // Select an output file using the file chooser dialog
            int returnVal = fileChooserDialog.showSaveDialog(mainMenu);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                outputFile = fileChooserDialog.getSelectedFile();
                filepath = outputFile.getPath();
                ext = filter.getExtensions()[0];
                if (!filepath.toUpperCase().endsWith(ext.toUpperCase())){
                    outputFile = new File(filepath+'.'+ext);
                }
            }
            jsonFile = new PrintWriter(outputFile);

            // Create JSON object
            Gson gson = new GsonBuilder().create();

            // Convert classroom list to JSON format
            gson.toJson(studentDataContainer.getListOfStudents(), jsonFile);

            getDEBUG_LOGGER().finest("success to save Student JSON file.");
        } catch (Exception exp) {
            getDEBUG_LOGGER().warning("Couldn't write JSON file: " + exp.getMessage());
        } finally {
            // Flush the output stream and close the file
            if (jsonFile != null) {
                jsonFile.flush();
                jsonFile.close();
            }
        }
    }
    /**
     * Reads a JSON formatted file of faculty and returns an array list of
     * faculty in a data container
     *
     * @return
     */
    public static StudentDC readStudent(JFileChooser fileChooserDialog, MainMenu mainMenu) {

        try {

            // Customize the file chooser dialog
            fileChooserDialog.setDialogTitle("Read Student");
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "Json file", "json");
            fileChooserDialog.setFileFilter(filter);

            // Select an output file using the file chooser dialog
            int returnVal = fileChooserDialog.showOpenDialog(mainMenu);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                inputFile = fileChooserDialog.getSelectedFile();
            }

            // Create input file
            BufferedReader jsonFile = new BufferedReader(new FileReader(inputFile));

            // Create JSON object
            Gson gson = new GsonBuilder().create();

            // fromJson returns an array
           Student[] studentArrayay= gson.fromJson(jsonFile, Student[].class);

            // Create arraylist for data container
            ArrayList<Student> listOfStudents = new ArrayList<>();

            // Convert to arraylist for the data model
            for (int i = 0; i < studentArrayay.length; i++) {
                listOfStudents.add(studentArrayay[i]);
            }
            studentDC.setListOfStudents(listOfStudents);

            getDEBUG_LOGGER().finest("success to read Student JSON file.");
        } catch (Exception exp) {
            getDEBUG_LOGGER().warning("Couldn't read Student: " + exp.getMessage());
        } finally {
            return studentDC;
        }
    }

}
