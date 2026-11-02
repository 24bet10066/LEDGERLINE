package com.ledgerline.dto;

import java.math.BigDecimal;

public record LowStockItem(
        String sku,
        String productName,
        String warehouseName,
        BigDecimal quantity,
        BigDecimal reorderLevel,
        BigDecimal shortage) {
}
