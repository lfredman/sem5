package se.kth.iv1350.checkoutsystem.controller;
import se.kth.iv1350.checkoutsystem.integration.DatabaseUnreachableException;
import se.kth.iv1350.checkoutsystem.integration.ItemNotFoundException;
import se.kth.iv1350.checkoutsystem.integration.Log;
import se.kth.iv1350.checkoutsystem.integration.TotalRevenueFileOutput;
import se.kth.iv1350.checkoutsystem.model.Cart;
import se.kth.iv1350.checkoutsystem.model.Item;
import se.kth.iv1350.checkoutsystem.model.Sale;
import se.kth.iv1350.checkoutsystem.model.SaleObserver;
import java.util.ArrayList;

/**
 * Class representing the controller layer
 */
public class Controller {
    Sale sale;
    Log systemLog;
    SaleObserver saleLog;
    SaleObserver viewobs;

    /**
     * Constructor of the controller
     * @param systemLog Log object
     */
    public Controller(Log systemLog) {
        this.systemLog = systemLog;
        this.saleLog = new TotalRevenueFileOutput("sale.txt");
    }
    /**
     * Initializes a new sale.
     */
    public void initializeSale(){
        sale = new Sale(systemLog);
        addObserver(saleLog);
        if (viewobs != null){
            addObserver(viewobs);
        }
    }
    /**
     * Method for adding items to sale
     * @param identifier Item identifier
     * @param quantity  Quantity of item to be added
     * @return  The current sale
     * @throws ItemNotFoundException Exception thrown when item could not be parsed from DB.
     * @throws SystemInventoryException Exception thrown when database connection error occur
     */

    public Sale enterItem(int identifier, int quantity) throws ItemNotFoundException, SystemInventoryException {
        try{
            return sale.addItem(identifier, quantity);
        } catch (DatabaseUnreachableException e){
            throw new SystemInventoryException(e);
        }
    }
    /**
     * Method for ending the sale
     * @return The total price
     */
    public int endSale(){
        return sale.summarizeSale();
    }
    /**
     * Method for enter the amount customer have paid and get change
     * @param amount The amount customer have paid
     * @return  THe change customer should receive.
     */
    public int paidAmount(int amount){
        int change = sale.paidAmount(amount);
        initializeSale();
        return change;
    }

    /**
     * Method for getting the current sale cart
     * @param saleinfo the current sale object
     * @return the cart object from current sale
     */
    public Cart getCart(Sale saleinfo) {
        return saleinfo.getCart();
    }
    /**
     * Method for getting item from cart object
     * @param cart object where items should get extracted.
     * @return An arraylist of items in cart
     */
    public ArrayList<Item> getCartItems(Cart cart) {
        return cart.getCartItems();
    }

    /**
     * Method for getting length of cart
     * @param cartItems Arraylist of cart-items
     * @return The length of cart
     */
    public int getCartSize(ArrayList<Item> cartItems){
        return cartItems.size();
    }

    /**
     * Method for getting item from cart array
     * @param itemLocationInArray Location of item in array
     * @param cartItems The cart array
     * @return The item object
     */
    public Item getCartItem(int itemLocationInArray, ArrayList<Item> cartItems){
        return cartItems.get(itemLocationInArray);
    }
    /**
     * Method for getting item description
     * @param item  Item object
     * @return  Description string
     */
    public String getCartItemDescription(Item item){
        return item.getDescription();
    }
    /**
     * Method for getting item price
     * @param item Item object
     * @return Price of item
     */
    public int getCartItemPrice(Item item){
        return item.getPrice();
    }

    /**
     * Getting the running total from the current sale
     * @param saleInfo The sale object
     * @return  The running total
     */
    public int getRunningTotal(Sale saleInfo){
        return saleInfo.getRunningTotal();
    }

    /**
     * Method for adding an observer to the sales observer
     * @param obs The SalesObserver
     */
    public void addObserver(SaleObserver obs) {
        sale.addObserver(obs);
    }

    /**
     * Setter method for the view implementing sales observer
     * @param obs The SalesObserver from view
     */
    public void addViewObs(SaleObserver obs){
        this.viewobs = obs;
    }
}
