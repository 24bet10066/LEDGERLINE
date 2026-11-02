package com.ledgerline.service;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class InventoryWorkflowTest {
    @Test void receiptThenIssueLeavesExpectedBalance() {
        BigDecimal opening = new BigDecimal("10");
        BigDecimal afterReceipt = InventoryCalculator.after(opening, new BigDecimal("7.5"));
        assertEquals(new BigDecimal("12.5"), InventoryCalculator.after(afterReceipt, new BigDecimal("-5")));
    }

    @Test void transferBalancesSourceAndDestination() {
        BigDecimal source = InventoryCalculator.after(new BigDecimal("25"), new BigDecimal("-8"));
        BigDecimal destination = InventoryCalculator.after(new BigDecimal("2"), new BigDecimal("8"));
        assertEquals(new BigDecimal("17"), source);
        assertEquals(new BigDecimal("10"), destination);
    }

    @Test void returnsCanCompensateAnIssueWithoutAllowingNegativeStock() {
        BigDecimal afterIssue = InventoryCalculator.after(new BigDecimal("6"), new BigDecimal("-4"));
        assertEquals(new BigDecimal("4"), InventoryCalculator.after(afterIssue, new BigDecimal("2")));
        assertThrows(IllegalArgumentException.class, () -> InventoryCalculator.after(afterIssue, new BigDecimal("-3")));
    }
}
