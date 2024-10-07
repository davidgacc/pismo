package com.pismo.services;

import com.pismo.dto.AccountRequestDto;
import com.pismo.dto.AccountResponseDto;
import com.pismo.exceptions.AccountAlreadyExistsException;
import com.pismo.exceptions.AccountNotFoundException;
import com.pismo.model.Account;
import com.pismo.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);
    @Autowired
    private AccountRepository accountRepository;

    public AccountResponseDto getAccountById(Long accountId) {

        logger.info("Fetching account with ID: {}", accountId);

        Account account = accountRepository.findById(accountId)
            .orElseThrow(() -> new AccountNotFoundException("Account with ID :" + accountId + " not found."));

        return AccountResponseDto.builder()
            .id(account.getAccountId())
            .documentNumber(account.getDocumentNumber())
            .build();
    }


    public AccountResponseDto createAccount(AccountRequestDto accountRequestDto) {
        String documentNumber = accountRequestDto.getDocumentNumber();
        logger.info("Creating account with document number: {}", documentNumber);

        if (accountRepository.existsByDocumentNumber(documentNumber)) {
            logger.error("Account with document number: {} already exists.", documentNumber);
            throw new AccountAlreadyExistsException(documentNumber);
        }

        Account account = Account.builder()
            .documentNumber(documentNumber)
            .build();

        Account savedAccount = accountRepository.save(account);
        logger.info("Account created successfully with ID: {}", savedAccount.getAccountId());

        return AccountResponseDto.builder()
            .id(savedAccount.getAccountId())
            .documentNumber(savedAccount.getDocumentNumber())
            .build();
    }
}
