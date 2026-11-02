package com.ledgerline.service;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class LowStockRulesTest {
    @Test
    void availableAtOrBelowReorderLevelIsLow() {
        assertTrue(LowStockRules.isLow(new BigDecimal("4"), new BigDecimal("4")));
        assertTrue(LowStockRules.isLow(new BigDecimal("3.5"), new BigDecimal("4")));
        assertFalse(LowStockRules.isLow(new BigDecimal("4.1"), new BigDecimal("4")));
    }

    @Test
    void shortageNeverBecomesNegative() {
        assertEquals(new BigDecimal("2.5"), LowStockRules.shortage(new BigDecimal("1.5"), new BigDecimal("4")));
        assertEquals(BigDecimal.ZERO, LowStockRules.shortage(new BigDecimal("8"), new BigDecimal("4")));
    }
}
