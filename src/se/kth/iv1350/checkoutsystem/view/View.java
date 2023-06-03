package se.kth.iv1350.checkoutsystem.view;
import se.kth.iv1350.checkoutsystem.controller.Controller;
import se.kth.iv1350.checkoutsystem.controller.SystemInventoryException;
import se.kth.iv1350.checkoutsystem.integration.ItemNotFoundException;
import se.kth.iv1350.checkoutsystem.model.Cart;
import se.kth.iv1350.checkoutsystem.model.Item;
import se.kth.iv1350.checkoutsystem.model.Sale;
import se.kth.iv1350.checkoutsystem.model.SaleObserver;

import java.util.ArrayList;

/**
 * Represent the view layer.
 */
public class View {
    private Controller contr;
    private SaleObserver saleView;

    /**
     * Creates a new instance of view and shows an example-run of the system.
     * @param contr An instance of class Controller
     */
    public View(Controller contr) {
         this.contr = contr;
         saleView = new TotalRevenueView();
         contr.addViewObs(saleView);
         contr.initializeSale();
         runFakeExcecution(contr);

    }

    /**
     * Method for running a fake execution of the system.
     * @param contr The controller instance used in the system
     */
    private void runFakeExcecution(Controller contr){

        System.out.println("Scan the first item:\n--------------------------");

        addItem(293847,234243); //Add item1
        addItem(100,2);   //Add item2
        addItem(19,2);   //Add item2
        addItem(69,2);   //Add item2
        addItem(69,2);   //Add item2
        System.out.println("The total price for this sale is: " + contr.endSale()); //Cashier end sale
        System.out.println("The change to the customer is: " + contr.paidAmount(500)); //Customer pays

        System.out.println("------- NEW SALE ---------------------------------");

        addItem(29,20);   //Add item2
        addItem(69,2);   //Add item2
        System.out.println("The total price for this sale is: " + contr.endSale()); //Cashier end sale
        System.out.println("The change to the customer is: " + contr.paidAmount(500)); //Customer pays


    }

    /**
     * Method for adding an item to the cart
     * @param identifier identifier of the item
     * @param quantity the amount of the item bought
     */
    private void addItem(int identifier, int quantity) {
        try{
            Sale sale = contr.enterItem(identifier, quantity);
            printSaleinfo(sale);

        } catch (ItemNotFoundException e) {
           System.out.println("!!! Could not parse item information, invalid item identifier: " + identifier + " !!!\n");
        } catch (SystemInventoryException e){
            System.out.println("System inventory error have occurred. Contact support!\n");
        }

    }
    /**
     * Method for printing sales-info in the frontend
     * @param saleInfo Sales data which is formatted and printed
     */
     private void printSaleinfo(Sale saleInfo){

         System.out.println("Current Sale:");
         System.out.println("Description:  Price: ");
         System.out.println("--------------------------");
         Cart cart = contr.getCart(saleInfo);
         ArrayList<Item> cartItems = contr.getCartItems(cart);

         for (int i = 0; i < contr.getCartSize(cartItems); i++) {
             Item item = contr.getCartItem(i, cartItems);
             System.out.println(contr.getCartItemDescription(item) + "             " + contr.getCartItemPrice(item));
         }

         System.out.println("Running Total: " + contr.getRunningTotal(saleInfo));
         System.out.println("--------------------------\n");

     }
}
