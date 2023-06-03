package se.kth.iv1350.checkoutsystem.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.kth.iv1350.checkoutsystem.integration.FileLogManager;
import se.kth.iv1350.checkoutsystem.integration.ItemNotFoundException;
import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {
    Controller controller = new Controller(new FileLogManager("test.txt"));

    @BeforeEach
    void setUp() {
        controller.initializeSale();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void initializeSale() {
        assertNotEquals(null, controller.sale, "Controller could not construct sale object");
        assertEquals(0, controller.sale.getRunningTotal(), "Controller could not access the methods in sale");
    }

    @Test
    void enterItem() {
        try {
            controller.enterItem(69,10);
            assertEquals(1, controller.sale.getCart().getCartItems().size(), "Could not add items");
            assertEquals(1, controller.sale.getCart().getCartItems().size(), "Added invalid items");
            controller.enterItem(69,10);
            assertEquals(1, controller.sale.getCart().getCartItems().size(), "Could not add same item again");
            assertEquals(69, controller.sale.getCart().getCartItems().get(0).getItemIdentifier(), "Wrong idem was added");

        } catch (ItemNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SystemInventoryException e) {
            throw new RuntimeException(e);
        }

        try {
            assertNull(controller.enterItem(107889,10), "Item shoud not be added");
            fail("This action should throw an error");
        } catch (ItemNotFoundException e) {
            assertTrue(e.getMessage().contains("107889"));
        } catch (SystemInventoryException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void endSale() {
        try {
            controller.enterItem(19,10);
            assertEquals(224, controller.endSale(), "Expected RunningTotal not achieved");
            controller.enterItem(39,1);
            assertEquals(236, controller.endSale(), "Expected RunningTotal not achieved");

        } catch (ItemNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SystemInventoryException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    void paidAmount() {
        assertEquals(0, controller.paidAmount(0), "Change calculations not working");
        try {
            controller.enterItem(39,10);
        } catch (ItemNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SystemInventoryException e) {
            throw new RuntimeException(e);
        }
        assertEquals(75, controller.paidAmount(200), "Change calculations not working");
    }

    @Test
    void getCart() {
        assertNotEquals(null, controller.getCart(controller.sale), "Could not parse cart");
    }

    @Test
    void getCartItems() {
        try {
            controller.enterItem(39,10);
            controller.enterItem(39,5);
            controller.enterItem(19,10);
        } catch (ItemNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SystemInventoryException e) {
            throw new RuntimeException(e);
        }
        assertNotEquals(null, controller.getCartItems(controller.sale.getCart()), "Could not parse cart items");
    }

    @Test
    void getCartItem() {
        try {
            controller.enterItem(39,10);
        } catch (ItemNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SystemInventoryException e) {
            throw new RuntimeException(e);
        }
        assertNotEquals(null, controller.getCartItem(0, controller.sale.getCart().getCartItems()), "Could not parse item from cart");
    }

    @Test
    void getCartItemDescription() {
        try {
            controller.enterItem(39,10);
            controller.enterItem(39,5);
            controller.enterItem(19,10);
        } catch (ItemNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SystemInventoryException e) {
            throw new RuntimeException(e);
        }
        assertEquals("Rice", controller.getCartItemDescription(controller.sale.getCart().getCartItems().get(0)), "Could not parse item description");
        assertEquals("Milk", controller.getCartItemDescription(controller.sale.getCart().getCartItems().get(1)), "Could not parse item description");
    }

    @Test
    void getCartItemPrice() {
        try {
            controller.enterItem(39,10);
            controller.enterItem(39,5);
            controller.enterItem(19,10);
        } catch (ItemNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SystemInventoryException e) {
            throw new RuntimeException(e);
        }
        assertEquals(10, controller.getCartItemPrice(controller.sale.getCart().getCartItems().get(0)), "Could not parse item price");
        assertEquals(20, controller.getCartItemPrice(controller.sale.getCart().getCartItems().get(1)), "Could not parse item price");
    }
    @Test
    void getRunningTotal() {
        try {
            controller.enterItem(39,10);
            assertEquals(125, controller.getRunningTotal(controller.sale), "Wrong running total");
            controller.enterItem(39,5);
            controller.enterItem(19,10);
            assertEquals(411, controller.getRunningTotal(controller.sale), "Wrong running total");

        } catch (ItemNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SystemInventoryException e) {
            throw new RuntimeException(e);
        }
    }

}