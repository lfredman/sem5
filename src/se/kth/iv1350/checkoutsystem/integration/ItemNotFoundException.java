package se.kth.iv1350.checkoutsystem.integration;

/**
 * Exception class for item parsing errors
 */
public class ItemNotFoundException extends Exception {
    /**
     * Exception thrown when not matching item identifier could be found
     * @param cause Exception message from the database
     */
    public ItemNotFoundException(String cause){
        super(cause);
    }
}
