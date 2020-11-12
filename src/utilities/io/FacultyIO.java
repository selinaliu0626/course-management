package utilities.io;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import datacontainers.FacultyDC;
import datamodels.Faculty;
import view.MainMenu;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;

import static controllers.Application.getDEBUG_LOGGER;

public class FacultyIO {

    static File outputFile;
    private static File inputFile;
    private static FacultyDC facultyDC = new FacultyDC();

    /**
     * Constructor is declared private because the IO classes are utilities
     * which contain static methods
     */
    private FacultyIO() {
    }

    /**
     * Writes out the classroom data in JSON format containing all classrooms in
     * the classroom data container
     *
     */
    public static void writeJSONFile(JFileChooser fileChooserDialog, MainMenu mainMenu,
                                     FacultyDC facultyDataContainer) {

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
            gson.toJson(facultyDataContainer.getListOfFaculty(), jsonFile);

            getDEBUG_LOGGER().finest("success to save Faculty JSON file.");
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
    public static FacultyDC readFaculty(JFileChooser fileChooserDialog, MainMenu mainMenu) {

        try {

            // Customize the file chooser dialog
            fileChooserDialog.setDialogTitle("Read Faculty");
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
            Faculty[] facultyArrayay= gson.fromJson(jsonFile, Faculty[].class);

            // Create arraylist for data container
            ArrayList<Faculty> listOfFaculties = new ArrayList<>();

            // Convert to arraylist for the data model
            for (int i = 0; i < facultyArrayay.length; i++) {
                listOfFaculties.add(facultyArrayay[i]);
            }
            facultyDC.setListOfFaculty(listOfFaculties);

            getDEBUG_LOGGER().finest("success to read Faculty JSON file.");
        } catch (Exception exp) {
            getDEBUG_LOGGER().warning("Couldn't read Faculty: " + exp.getMessage());
        } finally {
            return facultyDC;
        }
    }

}
