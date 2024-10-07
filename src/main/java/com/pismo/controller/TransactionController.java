package com.pismo.controller;

import com.pismo.dto.AccountResponseDto;
import com.pismo.dto.TransactionRequestDto;
import com.pismo.dto.TransactionResponseDto;
import com.pismo.exceptions.TransactionInvalidRequestException;
import com.pismo.services.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    @Operation(summary = "Create a new Account")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Transaction created successfully",
            content = { @Content(mediaType = "application/json",
                schema = @Schema(implementation = AccountResponseDto.class)) }),
        @ApiResponse(responseCode = "400", description = "Invalid input provided for creating transaction",
            content = { @Content(mediaType = "application/json",  schema = @Schema(implementation = TransactionInvalidRequestException.class))})
    })
    public ResponseEntity<TransactionResponseDto> createTransaction(@Valid @RequestBody TransactionRequestDto transactionRequestDto) {
        logger.info("Request to create transaction: {}", transactionRequestDto);
        TransactionResponseDto responseDto = transactionService.createTransaction(transactionRequestDto);

        logger.info("Successfully created transaction: {}", responseDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }
}
