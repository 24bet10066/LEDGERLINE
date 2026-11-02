package com.ledgerline.service;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;
class InventoryCalculatorTest {
 @Test void receiptIncreasesBalance(){assertEquals(new BigDecimal("15"),InventoryCalculator.after(new BigDecimal("10"),new BigDecimal("5")));}
 @Test void issueCannotCreateNegativeStock(){assertThrows(IllegalArgumentException.class,()->InventoryCalculator.after(new BigDecimal("2"),new BigDecimal("-3")));}
 @Test void transferUsesSignedMovements(){assertEquals(new BigDecimal("7"),InventoryCalculator.after(new BigDecimal("10"),new BigDecimal("-3")));assertEquals(new BigDecimal("13"),InventoryCalculator.after(new BigDecimal("10"),new BigDecimal("3")));}
}
