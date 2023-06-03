package se.kth.iv1350.checkoutsystem.controller;

/**
 * Class for the system inventory exception
 */
public class SystemInventoryException extends Exception{
    /**
     * Exception thrown when the system inventory could not be reached.
     * @param cause The exception message.
     */
    public SystemInventoryException(Throwable cause){
        super(cause);
    }
}
