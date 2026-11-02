package com.ledgerline.model;
import java.math.BigDecimal;
public record Product(long id,String sku,String name,String category,BigDecimal quantity,BigDecimal reorderLevel,BigDecimal cost,BigDecimal price) {}
