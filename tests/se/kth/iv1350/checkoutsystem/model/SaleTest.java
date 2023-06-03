package se.kth.iv1350.checkoutsystem.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.kth.iv1350.checkoutsystem.integration.AccountingSystem;
import se.kth.iv1350.checkoutsystem.integration.ExternalInventorySystem;
import se.kth.iv1350.checkoutsystem.integration.FileLogManager;
import se.kth.iv1350.checkoutsystem.integration.ItemNotFoundException;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class SaleTest {

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();


    Cart cart;
    ExternalInventorySystem externalInventorySystem;
    AccountingSystem accountingSystem;
    Sale sale;

    @BeforeEach
    void setUp() {
        cart = new Cart();
        externalInventorySystem = ExternalInventorySystem.getInventory(new FileLogManager("test.txt"));
        accountingSystem = new AccountingSystem();
        sale = new Sale(new FileLogManager("test.txt"));
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    void tearDown() {
        System.setOut(standardOut);
    }

    @Test
    void addItem() {
        Exception e1 = null;
        Exception e2 = null;
        try {
            sale.addItem(1929,1);
        } catch (ItemNotFoundException e) {
            e1 = e;
        }
        assertNotNull(e1);
        assertEquals("Identifier 1929 could not be parsed for database", e1.getMessage(), "Wrong exception-message");
        try {
            assertEquals(null, sale.addItem(6239,1), "Handled error wrong");
        } catch (ItemNotFoundException e) {
            e2 = e;
        }
        assertNotNull(e2);
        assertEquals("Identifier 6239 could not be parsed for database", e2.getMessage(), "Wrong exception-message");
        try {
            sale.addItem(69,8);
        } catch (ItemNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void summarizeSale() {
        try {
            sale.addItem(69, 10);
        } catch (ItemNotFoundException e) {
            throw new RuntimeException(e);
        }
        assertEquals(31, sale.summarizeSale(), "Returned wrong total");
    }

    @Test
    void paidAmount() {
        try {
            sale.addItem(69, 10);
        } catch (ItemNotFoundException e) {
            throw new RuntimeException(e);
        }

        assertEquals(100-31, sale.paidAmount(100), "Returned wrong calulated change");
    }


    @Test
    void getSaleTime() {
        String now = java.time.LocalDateTime.now().toString().replaceAll("(?<=\\.)(.*)", "");
        String receiptTime = sale.getSaleTime().toString().replaceAll("(?<=\\.)(.*)", "");
        assertEquals(now, receiptTime, "Time not matching");
    }

    @Test
    void getRunningTotal() {

        try {
            sale.addItem(29, 8);
            sale.addItem(59, 3);
            sale.addItem(59, 10);
        } catch (ItemNotFoundException e) {
            throw new RuntimeException(e);
        }
        assertEquals(863, sale.getRunningTotal(), "Problems calculating running total");

        try {
            sale.addItem(82347, 10);
        } catch (ItemNotFoundException e) {
            assertNotNull(e);
        }
        assertEquals(863, sale.getRunningTotal(), "Problems calculating running total");


    }

    @Test
    void getTotalVAT() {

        try {
            sale.addItem(82347, 10);
        } catch (ItemNotFoundException e) {
            assertNotNull(e);
        }

        try {
            sale.addItem(29, 8);
            sale.addItem(59, 3);
            sale.addItem(59, 10);
        } catch (ItemNotFoundException e) {
            throw new RuntimeException(e);
        }
        assertEquals(173, sale.getTotalVAT(), "Problems calculating VAT");
    }

    @Test
    void getCart() {

        assertNotEquals(null, sale.getCart(), "Could not parse cart");
        try {
            sale.addItem(29, 8);
        } catch (ItemNotFoundException e) {
            throw new RuntimeException(e);
        }
        assertEquals(1, sale.getCart().getCartItems().size(), "Could not parse cart objects");

    }
}