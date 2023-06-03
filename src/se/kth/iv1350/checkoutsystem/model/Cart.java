package se.kth.iv1350.checkoutsystem.model;

import se.kth.iv1350.checkoutsystem.integration.ItemDTO;
import java.util.ArrayList;

/**
 * Class for storing the items bought.
 */
public class Cart {

    private ArrayList<Item> cart;

    /**
     * Constructor for cart objects
     */
    public Cart() {
        cart = new ArrayList<Item>();
    }

    /**
     * Method for adding a item to the cart
     * @param itemDTO The DTO of the item to be added.
     * @param quantity  The amount of items to be added to the cart.
     * @return  The cart object.
     */
    public Cart addToCart(ItemDTO itemDTO, int quantity){
        if (itemAlreadyInCart(itemDTO.getIdentifier(), quantity) == false && quantity > 0){
            cart.add(new Item(itemDTO, quantity));
        }
        return this;
    }

    /**
     * Method for finding items already added to cart. Will increase amount at duplicates.
     * @param newItemidentifier The identifier of the object to be added
     * @param quantity  Quantity of the item to be added.
     * @return true if already in cart, false if not in cart
     */
    private boolean itemAlreadyInCart(int newItemidentifier, int quantity){
        for (int i = 0; i < cart.size(); i++)
            if (cart.get(i).getItemIdentifier() == newItemidentifier){
                Item updatedItem = cart.get(i).changeItemQuantity(quantity);
                cart.set(i, updatedItem);
                return true;
            }
        return false;
    }

    /**
     * Getter for the cart object
     * @return The cart object
     */
    public ArrayList<Item> getCartItems() {
        return cart;
    }
}
