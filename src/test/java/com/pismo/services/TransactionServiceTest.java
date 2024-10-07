package com.pismo.services;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.pismo.dto.TransactionRequestDto;
import com.pismo.dto.TransactionResponseDto;
import com.pismo.exceptions.AccountNotFoundException;
import com.pismo.exceptions.OperationTypeNotFoundException;
import com.pismo.model.Account;
import com.pismo.model.OperationType;
import com.pismo.model.Transaction;
import com.pismo.repository.AccountRepository;
import com.pismo.repository.OperationTypeRepository;
import com.pismo.repository.TransactionRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {
    @Mock
    private AccountRepository accountRepository;

    @Mock
    private OperationTypeRepository operationTypeRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionService transactionService;

    private Account account;
    private OperationType operationType;

    @BeforeEach
    public void setUp() {
        account = new Account();
        account.setAccountId(1L);

        operationType = new OperationType();
        operationType.setOperationTypeId(1L);
    }

    @Test
    void createTransactionWithSuccess() {
        // Arrange
        TransactionRequestDto requestDto = new TransactionRequestDto();
        requestDto.setAccountId(1L);
        requestDto.setOperationTypeId(1L);
        requestDto.setAmount(100.0);

        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        when(operationTypeRepository.findById(1L)).thenReturn(Optional.of(operationType));
        when(transactionRepository.save(any(Transaction.class))).thenAnswer(invocation -> {
            Transaction transaction = invocation.getArgument(0);
            transaction.setTransactionId(1L); // Set a mock ID
            transaction.setEventDate(LocalDateTime.now()); // Mock event date
            return transaction;
        });

        // Act
        TransactionResponseDto response = transactionService.createTransaction(requestDto);

        // Assert
        assertNotNull(response);
        assertEquals(1L, response.getAccountId());
        assertEquals(1L, response.getOperationTypeId());
        assertEquals(100.0, response.getAmount());
    }

    @Test
    void createTransactionWithAccountNotFoundException() {
        // Arrange
        TransactionRequestDto requestDto = new TransactionRequestDto();
        requestDto.setAccountId(1L);
        requestDto.setOperationTypeId(1L);
        requestDto.setAmount(100.0);

        when(accountRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        AccountNotFoundException exception = assertThrows(AccountNotFoundException.class, () -> {
            transactionService.createTransaction(requestDto);
        });
        assertEquals("Account with ID 1 not found.", exception.getMessage());
    }

    @Test
    void createTransactionWithTypeNotFoundException() {
        // Arrange
        TransactionRequestDto requestDto = new TransactionRequestDto();
        requestDto.setAccountId(1L);
        requestDto.setOperationTypeId(1L);
        requestDto.setAmount(100.0);

        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        when(operationTypeRepository.findById(1L)).thenReturn(Optional.empty());

        // Act and Asserts
        OperationTypeNotFoundException exception = assertThrows(OperationTypeNotFoundException.class, () -> {
            transactionService.createTransaction(requestDto);
        });
        assertEquals("Operation type with ID 1 not found.", exception.getMessage());
    }
}
