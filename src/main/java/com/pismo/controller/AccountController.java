package com.pismo.controller;

import com.pismo.dto.AccountRequestDto;
import com.pismo.dto.AccountResponseDto;
import com.pismo.services.AccountService;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);
    @Autowired
    private AccountService accountService;

    @Operation(summary = "Get a Account by Id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found the account",
            content = { @Content(mediaType = "application/json",
                schema = @Schema(implementation = AccountResponseDto.class)) }),
        @ApiResponse(responseCode = "400", description = "Invalid id supplied",
            content = @Content),
        @ApiResponse(responseCode = "404", description = "Account not found",
            content = @Content) })
    @GetMapping(value = "/{accountId}")
    public ResponseEntity<AccountResponseDto> getAccountById(@PathVariable Long accountId) {
        logger.info("Request to get account with ID: {}", accountId);
        AccountResponseDto accountResponse = accountService.getAccountById(accountId);

        logger.info("Successfully retrieved account: {}", accountResponse);
        return new ResponseEntity<>(accountResponse, HttpStatus.OK);
    }

    @Operation(summary = "Create a new Account")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Account created successfully",
            content = { @Content(mediaType = "application/json",
                schema = @Schema(implementation = AccountResponseDto.class)) }),
        @ApiResponse(responseCode = "400", description = "Invalid input provided",
            content = @Content)
    })
    @PostMapping
    public ResponseEntity<AccountResponseDto> createAccount(@Valid @RequestBody AccountRequestDto accountRequestDto) {
        logger.info("Request to create account with document number: {}", accountRequestDto.getDocumentNumber());

        AccountResponseDto createdAccount = accountService.createAccount(accountRequestDto);
        logger.info("Successfully created account: {}", createdAccount);

        return new ResponseEntity<>(createdAccount, HttpStatus.CREATED);
    }
}
