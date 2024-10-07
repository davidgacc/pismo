package com.pismo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequestDto {
    @NotNull(message = "Account ID is required.")
    private Long accountId;

    @NotNull(message = "Operation Type ID is required.")
    private Long operationTypeId;

    @NotNull(message = "Amount is required.")
    @Positive(message = "Amount must be positive.")
    private Double amount;
}
