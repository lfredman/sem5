package se.kth.iv1350.checkoutsystem.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.kth.iv1350.checkoutsystem.integration.ItemDTO;

import static org.junit.jupiter.api.Assertions.*;

class ItemTest {
    Item item;
    @BeforeEach
    void setUp() {
        ItemDTO itemDTO= new ItemDTO(100, 20, "Carrots", 12345);
        item = new Item(itemDTO, 5);
    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void changeItemQuantity() {
        item.changeItemQuantity(10);
        assertEquals(15, item.getQuantity(), "Wrong amount");
        item.changeItemQuantity(3);
        assertEquals(18, item.getQuantity(), "Wrong amount");
        item.changeItemQuantity(-10);
        assertEquals(8, item.getQuantity(), "Wrong amount");
        item.changeItemQuantity(0);
        assertEquals(8, item.getQuantity(), "Wrong amount");
    }
}