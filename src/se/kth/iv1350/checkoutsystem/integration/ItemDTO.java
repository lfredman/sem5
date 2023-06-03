package se.kth.iv1350.checkoutsystem.integration;

/**
 * DTO-class for the objects in the ExternalInventorySystem.
 */
public class ItemDTO {
    private final int price;
    private final int vat;
    private final String description;
    private final int identifier;

    /**
     * Constructor for the DTO-objects
     * @param price The price of the item
     * @param vat   The VAT-rate for the item
     * @param description   The description of the item
     * @param identifier    The identifier of the item
     */
    public ItemDTO(int price, int vat, String description, int identifier) {
        this.price = price;
        this.vat = vat;
        this.description = description;
        this.identifier = identifier;
    }

    /**
     * Getter for the item-DTO price
     * @return Item price
     */
    public int getPrice() {
        return price;
    }
    /**
     * Getter for the item-DTO VAT-rate
     * @return Item vat-rate
     */
    public int getVat() {
        return vat;
    }
    /**
     * Getter for the item-DTO description
     * @return Item description
     */
    public String getDescription() {
        return description;
    }
    /**
     * Getter for the item-DTO identifier
     * @return  Item identifier
     */
    public int getIdentifier() {
        return identifier;
    }
}
