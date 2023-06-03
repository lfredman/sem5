package se.kth.iv1350.checkoutsystem.model;

/**
 * Interface for an observer class
 */
public interface SaleObserver {
    void completedSale(Sale sale);
}
