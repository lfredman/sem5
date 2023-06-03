package se.kth.iv1350.checkoutsystem.integration;

import se.kth.iv1350.checkoutsystem.model.Sale;
import se.kth.iv1350.checkoutsystem.model.SaleObserver;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Class for generating a total revenue file from the sales system
 */
public class TotalRevenueFileOutput implements SaleObserver, Log {
    private FileWriter logFile;
    private int totalSale = 0;
    private int salesCounter = 0;

    /**
     * Constructor which sets up the output file
     * @param logPath Destination path of the file
     */
    public TotalRevenueFileOutput(String logPath) {
        try {
            logFile = new FileWriter(logPath);
        } catch (IOException e) {
            System.out.println("Could not generate logfile");
            throw new RuntimeException(e);
        }
    }

    /**
     * Method for writing the message to the file
     * @param logMessage The message getting logged.
     */
    public void write(String logMessage){
        try {
            logFile.write(logMessage);
            logFile.flush();
        } catch (IOException e) {
            System.out.println("Could not write to logfile");
            throw new RuntimeException(e);
        }
    }

    /**
     * Method for generating the file output and parsing values from system.
     * @param sale The most recent sales object
     */
    @Override
    public void completedSale(Sale sale){
        salesCounter += 1;
        totalSale += sale.getRunningTotal();
        String message = "The total after " + salesCounter + " sales is at: " + totalSale + "\n";
        write(message);
    }



}

