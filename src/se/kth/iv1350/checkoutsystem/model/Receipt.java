package se.kth.iv1350.checkoutsystem.model;

import se.kth.iv1350.checkoutsystem.integration.Printer;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Class representing the receipt
 */
public class Receipt {
    private LocalDateTime datetime;
    private Cart cart;
    private Sale sale;
    private int totalPrice;
    private int totalVat;
    private int amountPaid;
    private int change;
    private Printer printer;

    /**
     * Constructor for the receipt object
     * @param sale Object of the sale class
     * @param paidAmount The amount customer have paid
     * @param change    The amount of change customer should receive
     */
    public Receipt(Sale sale, int paidAmount, int change) {
        printer = new Printer();
        this.datetime = sale.getSaleTime();
        this.cart = sale.getCart();
        this.sale = sale;
        this.totalPrice = sale.getRunningTotal();
        this.totalVat = sale.getTotalVAT();
        this.amountPaid = paidAmount;
        this.change = change;
    }

    /**
     * Generates a receipt and sends it to the printer
     * @param receipt An object of the receipt class.
     */
    public void generateAndPrintReceipt(Receipt receipt){
        String generatedReceipt = "\nReceipt for purchase at " + datetime + "\n"
                + this.generateSaleinfo(sale) + "\n" +
                "Total Price:        " + sale.getRunningTotal() + "\n" +
                "Total VAT:          " + sale.getTotalVAT() + "\n" +
                "Amount Paid:        " + amountPaid + "\n" +
                "Change to customer: " + change + "\n" ;

        printer.print(generatedReceipt);
    }
    /**
     * Method for generating a summary of the items bought with some additional information.
     * @param sale  A reference to the sale object containing the items to summarize.
     * @return  A formatted string containing information about the sale.
     */
    public String generateSaleinfo(Sale sale){

        String saleInfo = "Name:  Quantity:  Price: \n" +
                "-------------------------\n";
        ArrayList<Item> cartItems = cart.getCartItems();
        for (int i = 0; i < cartItems.size(); i++) {
            Item item = cartItems.get(i);
            saleInfo += item.getName() + "    " + item.getQuantity() + "         " + item.getPrice() + "\n";
        }
        saleInfo += "-------------------------";
        return saleInfo;
    }
}
