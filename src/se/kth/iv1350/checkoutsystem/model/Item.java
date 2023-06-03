package se.kth.iv1350.checkoutsystem.model;

import se.kth.iv1350.checkoutsystem.integration.ItemDTO;

/**
 * Class representing an item.
 */
public class Item {
    private final int price;
    private final int vat;
    private final String description;
    private final int itemIdentifier;
    private final String name;
    private int quantity;

    /**
     * Constructor of the item-objects.
     * @param itemDTO DTO from the ExternalInventorySystem
     * @param quantity  Amount of items to be bought
     */
    public Item(ItemDTO itemDTO, int quantity) {
        this.price = itemDTO.getPrice();
        this.vat = itemDTO.getVat();
        this.description = itemDTO.getDescription();
        this.itemIdentifier = itemDTO.getIdentifier();
        this.name = itemDTO.getDescription();
        this.quantity = quantity;
    }

    /**
     * Method for changing the amount of specified item to be bought
     * @param additionalToAdd The amount the items will get increased with.
     * @return An instance of the updated item-object
     */
    public Item changeItemQuantity(int additionalToAdd){
        this.quantity = this.quantity + additionalToAdd;
        return this;
    }

    /**
     * Getter for the items price
     * @return Price of the item
     */
    public int getPrice() {
        return price;
    }
    /**
     * Getter for the items vat-rate
     * @return Vat-rate of the item
     */
    public int getVat() {
        return vat;
    }
    /**
     * Getter for the items description
     * @return Description of the item
     */
    public String getDescription() {
        return description;
    }
    /**
     * Getter for the items identifier
     * @return Identifier of the item
     */
    public int getItemIdentifier() {
        return itemIdentifier;
    }
    /**
     * Getter for the items name
     * @return Name of the item
     */
    public String getName() {
        return name;
    }
    /**
     * Getter for the quantity of items in cart
     * @return Quantity of the item
     */
    public int getQuantity() {
        return quantity;
    }
}
