package com.ledgerline.dto;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
public record StockRequest(@NotNull Long productId,@NotNull Long warehouseId,@NotNull Long locationId,@NotNull @Positive BigDecimal quantity,@NotBlank String type,@NotBlank String reference,@NotBlank String reason){}
