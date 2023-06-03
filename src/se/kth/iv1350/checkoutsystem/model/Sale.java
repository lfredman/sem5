package se.kth.iv1350.checkoutsystem.model;
import se.kth.iv1350.checkoutsystem.integration.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Represents the core business logic of one sale.
 */
public class Sale {
    private LocalDateTime saleTime;
    private int runningTotal;
    private int totalVAT;
    private Cart cart;
    private Receipt receipt;
    private ExternalInventorySystem externalInventorySystem;
    private AccountingSystem accountingSystem;
    private ArrayList<SaleObserver> observers = new ArrayList<>();



    /**
     * Initialize a sale instance.
     */
    public Sale(Log systemLog) {
        cart = new Cart();
        setTimeOfSale();
        externalInventorySystem = ExternalInventorySystem.getInventory(systemLog);
        accountingSystem = new AccountingSystem();
    }

    /**
     * Sets time of the sale-object
     */
    private void setTimeOfSale() {
        this.saleTime = java.time.LocalDateTime.now();
    }

    /**
     * Method for adding product to <code>Sale</code>.
     * @param identifier The product's identifier.
     * @param quantity   The amount of product bought.
     * @return           Returns the <code>sale</code>-object.
     * @throws ItemNotFoundException Exception thrown when item could not be parsed from DB.
     * @throws DatabaseUnreachableException Exception thrown when database connection error occur
     */

    public Sale addItem(int identifier, int quantity) throws ItemNotFoundException, DatabaseUnreachableException {
        ItemDTO itemDTO = externalInventorySystem.fetchItem(identifier);
        cart.addToCart(itemDTO, quantity);
        updateRunningTotal();

        return this;
    }

    /**
     * Summarizes the bought items and calculates running total.
     * @return  Running total of <code>sale</code>.
     */
    public int summarizeSale() {
        updateRunningTotal();
        return runningTotal;
    }

    /**
     * Calculates the change to the customer based on amount paid.
     * @param amount The amount customer have paid
     * @return  Change customer should receive.
     */
    public int paidAmount(int amount) {
        int customerChange = calculateChange(amount);
        accountingSystem.logSale(this);
        externalInventorySystem.updateInventory(cart);
        receipt = new Receipt(this, amount, customerChange);
        receipt.generateAndPrintReceipt(receipt);
        notifyObservers();
        return customerChange;
    }

    /**
     * Creates a simple change calculation
     * @param amount Amount paid from customer.
     * @return  The change customer should receive.
     */
    private int calculateChange(int amount){
        return amount - runningTotal;
    }

    /**
     * Method for updating the objects runningTotal attribute.
     */
    private void updateRunningTotal(){
        double runningTotalExVAT = 0;
        double totalVAT = 0;
        for (Item item : cart.getCartItems()){
            runningTotalExVAT += (item.getQuantity() * item.getPrice());
            totalVAT += (item.getQuantity() * item.getPrice()) * (item.getVat()/100.0);
        }
        this.runningTotal = (int)(runningTotalExVAT + totalVAT);
        this.totalVAT = (int)totalVAT;
    }


    /**
     * Getter method for saleTime attribute
     * @return Time of the sale
     */
    public LocalDateTime getSaleTime() {
        return saleTime;
    }

    /**
     * Getter method for the runningTotal of the sale instance
     * @return
     */
    public int getRunningTotal() {
        return runningTotal;
    }

    /**
     * Getter method for the totalVAT attribute in the sales instance.
     * @return The current total vat of the sale.
     */
    public int getTotalVAT() {
        return totalVAT;
    }

    /**
     * Getter method for the cart of sales instance
     * @return  The cart object
     */
    public Cart getCart() {
        return cart;
    }

    /**
     * Method for adding observers to the observers-list
     * @param observer
     */
    public void addObserver(SaleObserver observer){
        observers.add(observer);
    }

    /**
     * Method called when sales end and notifies all observers in the list
     */
    private void notifyObservers(){
        for(SaleObserver observer:observers){
            observer.completedSale(this);
        }

    }


}
