package se.kth.iv1350.checkoutsystem.integration;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Class handling logging to local file
 */
public class FileLogManager implements Log {
    private FileWriter logFile;

    /**
     * Constructor for setup of logfile
     */
    public FileLogManager(String logPath) {
        try {
            logFile = new FileWriter(logPath);
        } catch (IOException e) {
            System.out.println("Could not generate logfile");
            throw new RuntimeException(e);
        }
    }

    /**
     * Simple method for getting current time as string.
     * @return Current time
     */
    private String getCurrentTime(){
        LocalDateTime dateTime = java.time.LocalDateTime.now();
        return dateTime.toString();
    }

    /**
     * Method for writing message to logfile
     * @param logMessage The message getting logged.
     */
    public void write(String logMessage){
        try {
            logFile.write(getCurrentTime() + "  " + logMessage + "\n");
            logFile.flush();
        } catch (IOException e) {
            System.out.println("Could not write to logfile");
            throw new RuntimeException(e);
        }
    }
}
