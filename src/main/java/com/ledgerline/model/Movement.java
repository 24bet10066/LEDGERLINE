package com.ledgerline.model;
import java.math.BigDecimal;
import java.time.LocalDateTime;
public record Movement(String sku,String product,String warehouse,BigDecimal quantity,String type,String reference,LocalDateTime createdAt) {}
