package com.pismo.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import com.pismo.dto.AccountRequestDto;
import com.pismo.dto.AccountResponseDto;
import com.pismo.exceptions.AccountAlreadyExistsException;
import com.pismo.exceptions.AccountNotFoundException;
import com.pismo.model.Account;
import com.pismo.repository.AccountRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    private Account account;

    private static final long ACCOUNT_ID = 1L;
    private static final String DOCUMENT_NUMBER = "1234567";


    public AccountServiceTest() {}

    @BeforeEach
    public void setUp() {
        account = new Account();
        account.setAccountId(ACCOUNT_ID);
        account.setDocumentNumber(DOCUMENT_NUMBER);
    }

    @Test
    void getAccountByIdWithSuccess() {
        // When
        when(accountRepository.findById(ACCOUNT_ID)).thenReturn(Optional.of(account));

        // Do
        AccountResponseDto response = accountService.getAccountById(ACCOUNT_ID);

        // Assert
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals(DOCUMENT_NUMBER, response.getDocumentNumber());
        verify(accountRepository, times(1)).findById(ACCOUNT_ID);
    }

    @Test
    void getAccountById_NonExistingAccount_ThrowsAccountNotFoundException() {
        // Arrange
        when(accountRepository.findById(ACCOUNT_ID)).thenReturn(Optional.empty());

        // Do & Assert
        AccountNotFoundException exception = assertThrows(AccountNotFoundException.class, () -> {
            accountService.getAccountById(ACCOUNT_ID);
        });
        assertEquals("Account with ID :" + ACCOUNT_ID +  " not found.", exception.getMessage());
        verify(accountRepository, times(1)).findById(1L);
    }

    @Test
    void createAccountWithSuccess() {
        // Arrange
        AccountRequestDto requestDto = AccountRequestDto.builder().documentNumber(DOCUMENT_NUMBER).build();

        when(accountRepository.existsByDocumentNumber(DOCUMENT_NUMBER)).thenReturn(false);
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        // Do
        AccountResponseDto response = accountService.createAccount(requestDto);

        // Assert
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals(DOCUMENT_NUMBER, response.getDocumentNumber());
        verify(accountRepository, times(1)).save(any(Account.class));
    }

    @Test
    void createAccountWhenAccountAlreadyExistsException() {
        // Arrange
        AccountRequestDto requestDto = new AccountRequestDto();
        requestDto.setDocumentNumber(DOCUMENT_NUMBER);
        when(accountRepository.existsByDocumentNumber(DOCUMENT_NUMBER)).thenReturn(true);

        // Do and asserts
        AccountAlreadyExistsException exception = assertThrows(AccountAlreadyExistsException.class, () -> {
            accountService.createAccount(requestDto);
        });
        assertEquals("Account with document number "+DOCUMENT_NUMBER +" already exists.", exception.getMessage());
        verify(accountRepository, never()).save(any(Account.class));
    }
}
