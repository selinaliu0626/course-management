package utilities.io;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import datacontainers.ClassroomDC;
import datamodels.Classroom;
import view.MainMenu;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;

import static controllers.Application.getDEBUG_LOGGER;


public class ClassroomIO {

    static File outputFile;
    private static File inputFile;
    private static ClassroomDC classroomDC = new ClassroomDC();

    /**
     * Constructor is declared private because the IO classes are utilities
     * which contain static methods
     */
    private ClassroomIO() {
    }

    /**
     * Writes out the classroom data in JSON format containing all classrooms in
     * the classroom data container
     *
     */
    public static void writeJSONFile(JFileChooser fileChooserDialog, MainMenu mainMenu,
                                     ClassroomDC classroomDataContainer) {

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
            gson.toJson(classroomDataContainer.getListOfClassrooms(), jsonFile);

            getDEBUG_LOGGER().finest("success to save classrooms JSON file");
        } catch (Exception exp) {
            getDEBUG_LOGGER().warning("Couldn't write classrooms JSON file: " + exp.getMessage());
        } finally {
            // Flush the output stream and close the file
            if (jsonFile != null) {
                jsonFile.flush();
                jsonFile.close();
            }
        }
    }
    /**
     * Reads a JSON formatted file of classrooms and returns an array list of
     * classrooms in a data container
     *
     */
    public static ClassroomDC readClassroom(JFileChooser fileChooserDialog, MainMenu mainMenu) {

        try {

            // Customize the file chooser dialog
            fileChooserDialog.setDialogTitle("Read Classroom");
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
            Classroom[] classroomArray = gson.fromJson(jsonFile, Classroom[].class);

            // Create arraylist for data container
            ArrayList<Classroom> listOfClassrooms = new ArrayList<>();

            // Convert to arraylist for the data model
            for (int i = 0; i < classroomArray.length; i++) {
                listOfClassrooms.add(classroomArray[i]);
            }
            classroomDC.setListOfClassrooms(listOfClassrooms);
            getDEBUG_LOGGER().finest("success to read classroom JSON file.");
        } catch (Exception exp) {
            getDEBUG_LOGGER().warning("Couldn't read Classroom: " + exp.getMessage());
        } finally {
            return classroomDC;
        }
    }

}

