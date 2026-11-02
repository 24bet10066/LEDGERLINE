package com.ledgerline.dto;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
public record PurchaseRequest(@NotBlank String orderNumber,@NotNull Long supplierId,@NotNull Long warehouseId,@NotNull Long productId,@NotNull @Positive BigDecimal quantity,@NotNull @PositiveOrZero BigDecimal cost){}
