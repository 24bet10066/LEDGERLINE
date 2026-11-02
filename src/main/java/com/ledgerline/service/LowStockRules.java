package com.ledgerline.service;

import java.math.BigDecimal;

public final class LowStockRules {
    private LowStockRules() {
    }

    public static boolean isLow(BigDecimal available, BigDecimal reorderLevel) {
        return available.compareTo(reorderLevel) <= 0;
    }

    public static BigDecimal shortage(BigDecimal available, BigDecimal reorderLevel) {
        return reorderLevel.subtract(available).max(BigDecimal.ZERO);
    }
}
