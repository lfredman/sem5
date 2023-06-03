package se.kth.iv1350.checkoutsystem.startup;

import se.kth.iv1350.checkoutsystem.controller.Controller;
import se.kth.iv1350.checkoutsystem.integration.FileLogManager;
import se.kth.iv1350.checkoutsystem.integration.Log;
import se.kth.iv1350.checkoutsystem.integration.TotalRevenueFileOutput;
import se.kth.iv1350.checkoutsystem.view.View;

/**
 * Main class for the program.
 */
public class Main {
    /**
     * Main method for program. Creates and initialize the other layers.
     * @param Args Arguments from terminal
     */
    public static void main(String Args[]){
        Log systemLog = new FileLogManager("log.txt");
        Controller contr = new Controller(systemLog);
        View view = new View(contr);
    }

}

