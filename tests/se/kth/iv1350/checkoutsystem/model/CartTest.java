package se.kth.iv1350.checkoutsystem.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.kth.iv1350.checkoutsystem.integration.ItemDTO;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CartTest {

    Cart cart;
    ItemDTO item1;
    ItemDTO item2;
    @BeforeEach
    void setUp() {
        cart = new Cart();
        item1 = new ItemDTO(500, 25, "Carrots", 100);
        item2 = new ItemDTO(200, 12, "Tomato", 101);

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void addToCart() {
        assertTrue( cart.getCartItems().isEmpty(), "Problems with cart count, should be empty");
        cart.addToCart(item1, 10);
        ArrayList<Item> cartContent = cart.getCartItems();
        assertEquals(1, cartContent.size(), "Problems with cart count");
        assertEquals(10, cartContent.get(0).getQuantity(), "Problems with cart item amount");
        assertEquals(10, cartContent.get(0).getQuantity(), "Problems with cart item amount");

        cart.addToCart(item2, 10);
        cartContent = cart.getCartItems();
        assertEquals(2, cartContent.size(), "Problems with cart count");
        assertNotEquals(cartContent.get(0), cartContent.get(1));

    }

    @Test
    void getCartItems() {
        cart.addToCart(item1, 10);
        assertNotEquals(null, cart.getCartItems(), "Could not parse cart");
        assertEquals(1, cart.getCartItems().size(), "Problems with cart count");
    }
}