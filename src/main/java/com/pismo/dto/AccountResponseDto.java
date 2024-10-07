package com.pismo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponseDto {
    @JsonProperty("account_id")
    private long id;
    @JsonProperty("document_number")
    private String documentNumber;
}
