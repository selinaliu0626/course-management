/*
 * This is the main entry into the application. It creates a menu controller object
 * and the controller object creates the forms and the data models as needed
 */
package controllers;

import java.util.logging.Logger;

public class Application {

    //Logger.
    private static final Logger DEBUG_LOGGER = Logger.getLogger(Application.class.getName());

    // Get methods for the loggers
    public static Logger getDEBUG_LOGGER() {
        return DEBUG_LOGGER;
    }

    public static void main(String[] args) {

        // Create main menu controller, the controller creates the menu form
        MainMenuController controller = new MainMenuController();

        // Retrieve the main menu form from the controller and make it visible
        controller.getMainMenu().setVisible(true);
    }
}
