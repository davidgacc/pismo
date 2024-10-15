package com.pismo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@AllArgsConstructor
@Data
@NoArgsConstructor
@ToString
public class TransactionResponseDto {
    @JsonProperty("transaction_id")
    private Long transactionId;

    @JsonProperty("account_id")
    private Long accountId;

    @JsonProperty("operation_type_id")
    private Integer operationTypeId;

    private Double amount;

    @JsonProperty("event_date")
    private LocalDateTime eventDate;
}
