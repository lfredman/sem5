package se.kth.iv1350.checkoutsystem.model;

import org.junit.Rule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.kth.iv1350.checkoutsystem.integration.FileLogManager;
import se.kth.iv1350.checkoutsystem.integration.ItemNotFoundException;
import se.kth.iv1350.checkoutsystem.integration.Printer;
import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class ReceiptTest {


    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    Sale sale;
    Printer printer;
    Receipt receipt;

    @BeforeEach
    void setUp() {
        sale = new Sale(new FileLogManager("test.txt"));
        printer = new Printer();
        receipt = new Receipt(sale, 100, 50);
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    void tearDown() {
        System.setOut(standardOut);
    }

    @Test
    void generateAndPrintReceipt() {
        //System.out.println("Hello")

        receipt.generateAndPrintReceipt(receipt);
        assertEquals("Receipt for purchase at\n" +
                "Name:  Quantity:  Price: \n" +
                "-------------------------\n" +
                "-------------------------\n" +
                "Total Price:        0\n" +
                "Total VAT:          0\n" +
                "Amount Paid:        100\n" +
                "Change to customer: 50", outputStreamCaptor.toString().trim().replaceAll("(?<=at)(.*)(?=\n)", ""));
    }

    @Test
    void generateSaleinfo() {
        try {
            sale.addItem(19, 10);
            sale.addItem(49, 50);
            sale.addItem(69, 20);
            assertEquals("Name:  Quantity:  Price: \n" +
                            "-------------------------\n" +
                            "Milk    10         20\n" +
                            "Pasta    50         25\n" +
                            "Potato    20         3\n" +
                            "-------------------------",
                    receipt.generateSaleinfo(sale),outputStreamCaptor.toString().trim());
        } catch (ItemNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}