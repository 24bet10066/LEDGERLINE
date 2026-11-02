package com.ledgerline.dto;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
public record TransferRequest(@NotNull Long productId,@NotNull Long fromWarehouse,@NotNull Long fromLocation,@NotNull Long toWarehouse,@NotNull Long toLocation,@NotNull @Positive BigDecimal quantity,@NotBlank String reference){}
