package com.pismo.services;

import static com.pismo.model.OperationTypeEnum.WITHDRAWAL;

import com.pismo.dto.TransactionRequestDto;
import com.pismo.dto.TransactionResponseDto;
import com.pismo.exceptions.AccountCreditLimitException;
import com.pismo.exceptions.AccountNotFoundException;
import com.pismo.exceptions.OperationTypeNotFoundException;
import com.pismo.model.Account;
import com.pismo.model.OperationType;
import com.pismo.model.OperationTypeEnum;
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
        OperationTypeEnum operationType = transactionRequestDto.getOperationType();
        Double amount = transactionRequestDto.getAmount();

        // Validate account existence
        Account account = accountRepository.findById(accountId)
            .orElseThrow(() -> new AccountNotFoundException("Account with ID " + accountId + " not found."));

        // Validate operation type existence
        OperationType transactionOperationType = operationTypeRepository.findById(operationType.getId())
            .orElseThrow(() -> new OperationTypeNotFoundException("Operation type with ID " + operationType.getId() + " not found."));

        if (operationType == WITHDRAWAL) {
            doWithDrawal(account, amount);
        } else {
            doPayment(account, amount);
        }

        // Create transaction
        Transaction transaction = Transaction.builder()
            .account(account)
            .operationType(transactionOperationType)
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

    private void doPayment(Account account, Double amount) {
        Double creditLimit = account.getCreditLimit();

        //check limit amount
        if (creditLimit < amount) {
            throw new AccountCreditLimitException("Exceed the limit of your account");
        }

        // update creditLimit
        Double updateLimitCredit = creditLimit - amount;
        account.setCreditLimit(updateLimitCredit);
        accountRepository.save(account);

    }

    private void doWithDrawal(Account account, Double amount) {
        Double creditLimit = account.getCreditLimit();

        // update creditLimit
        Double updateLimitCreditWithDrawal = creditLimit + amount;
        account.setCreditLimit(updateLimitCreditWithDrawal);
        accountRepository.save(account);
    }

}
