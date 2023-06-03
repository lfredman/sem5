package se.kth.iv1350.checkoutsystem.integration;
import se.kth.iv1350.checkoutsystem.model.Cart;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing the External Inventory System
 */
public class ExternalInventorySystem {

    private static ExternalInventorySystem inventorySystem;
    private List<ItemDTO> inventory = new ArrayList<>();
    private Log log;

    /**
     * Dummy database for items in the external inventorysystem.
     * Item with identifier 100 will simulate db not responding
     */
    private ExternalInventorySystem(Log systemLog) {
        log = systemLog;

        inventory.add(new ItemDTO(20, 12, "Milk", 19));
        inventory.add(new ItemDTO(5, 12, "Egg", 29));
        inventory.add(new ItemDTO(10, 25, "Rice", 39));
        inventory.add(new ItemDTO(25, 6, "Pasta", 49));
        inventory.add(new ItemDTO(50, 26, "Meat", 59));
        inventory.add(new ItemDTO(3, 6, "Potato", 69));
        inventory.add(new ItemDTO(-1, -1, "db crash", 100));
    }

    /**
     * Getter for the static external inventory instance
     * @param systemLog The systemLog instance used for logging
     * @return The external inventory instance
     */
    public static ExternalInventorySystem getInventory(Log systemLog){
        if (inventorySystem != null){
            return inventorySystem;
        }
        inventorySystem = new ExternalInventorySystem(systemLog);
        return inventorySystem;
    }

    /**
     * Makes a search in the dummy database.
     * @param identifier The identifier of the scanned item
     * @return The matched item or null if no match.
     * @throws DatabaseUnreachableException If the database is not responding or a connection-error have occurred.
     * @throws ItemNotFoundException if no match was found for the supplied item identifier.
     */
    public ItemDTO fetchItem(int identifier) throws DatabaseUnreachableException, ItemNotFoundException {
        if (identifier == 100){
            RuntimeException e = new DatabaseUnreachableException("Could not reach the database");
            log.write(e.toString());
            throw e;
        }
        for (ItemDTO item : inventory) {
            if (item.getIdentifier() == identifier){
                return item;
            }
        }
        throw new ItemNotFoundException("Identifier " + identifier + " could not be parsed for database");
    }
    /**
     * Dummy method for updating the external inventory system
     * @param cart The cart object of the sale.
     */
    public void updateInventory(Cart cart){
    }
}
