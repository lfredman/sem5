package se.kth.iv1350.checkoutsystem.integration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExternalInventorySystemTest {
    ExternalInventorySystem inventory;
    @BeforeEach
    void setUp() {
        inventory = ExternalInventorySystem.getInventory(new FileLogManager("test.txt"));
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void fetchItem() {
        ItemDTO result = null;
        try {
            result = (inventory.fetchItem(69));
        } catch (ItemNotFoundException e) {
            throw new RuntimeException("Could not fetch item", e);
        }
        assertEquals(69, result.getIdentifier());

    }
}