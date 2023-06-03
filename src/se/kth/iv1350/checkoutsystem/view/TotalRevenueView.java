package se.kth.iv1350.checkoutsystem.view;

import se.kth.iv1350.checkoutsystem.model.Sale;
import se.kth.iv1350.checkoutsystem.model.SaleObserver;

/**
 * Class for generating a view output of current total sales amount
 */
public class TotalRevenueView implements SaleObserver {
    int totalSale = 0;
    @Override
    /**
     * Method called from observer and prints current total sales amount.
     */
    public void completedSale(Sale sale) {
        totalSale += sale.getRunningTotal();
        System.out.println("The total store sales is currently at " + totalSale + "\n");
    }
}
