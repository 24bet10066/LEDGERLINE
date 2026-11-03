package com.ledgerline.dto;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
public record ProductRequest(@NotBlank String sku,@NotBlank String name,@NotNull @Positive Long categoryId,@NotNull @Positive Long supplierId,@NotNull @PositiveOrZero BigDecimal reorderLevel,@NotNull @PositiveOrZero BigDecimal cost,@NotNull @PositiveOrZero BigDecimal price){}
