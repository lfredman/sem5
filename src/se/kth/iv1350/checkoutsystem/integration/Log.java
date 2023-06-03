package se.kth.iv1350.checkoutsystem.integration;

/**
 * Interface used for logging messages
 */
public interface Log {
    /**
     * Method for generating a log to designated location
     * @param logMessage The message getting logged.
     */
    public void write(String logMessage);
}
