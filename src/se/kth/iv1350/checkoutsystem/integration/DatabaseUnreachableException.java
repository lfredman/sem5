package se.kth.iv1350.checkoutsystem.integration;

/**
 * Exceptionsclass for database connection errors.
 */
public class DatabaseUnreachableException extends RuntimeException{
    /**
     * Constructor method for exception
     * @param cause Reason for failure
     */
    public DatabaseUnreachableException(String cause){
        super(cause);
    }
}
