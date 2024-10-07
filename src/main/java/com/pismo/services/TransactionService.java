package com.pismo.services;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private OperationTypeRepository operationTypeRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    public TransactionResponseDto createTransaction(TransactionRequestDto transactionRequestDto) {
        Long accountId = transactionRequestDto.getAccountId();
        Long operationTypeId = transactionRequestDto.getOperationTypeId();
        Double amount = transactionRequestDto.getAmount();

        // Validate account existence
        Account account = accountRepository.findById(accountId)
            .orElseThrow(() -> new AccountNotFoundException("Account with ID " + accountId + " not found."));

        // Validate operation type existence
        OperationType operationType = operationTypeRepository.findById(operationTypeId)
            .orElseThrow(() -> new OperationTypeNotFoundException("Operation type with ID " + operationTypeId + " not found."));

        // Create transaction
        Transaction transaction = Transaction.builder()
            .account(account)
            .operationType(operationType)
            .amount(amount)
            .build();

        Transaction savedTransaction = transactionRepository.save(transaction);

        return TransactionResponseDto.builder()
            .transactionId(savedTransaction.getTransactionId())
            .accountId(savedTransaction.getAccount().getAccountId())
            .operationTypeId(savedTransaction.getOperationType().getOperationTypeId())
            .amount(savedTransaction.getAmount())
            .eventDate(savedTransaction.getEventDate())
            .build();
    }
}
