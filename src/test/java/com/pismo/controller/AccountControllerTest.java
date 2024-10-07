package com.pismo.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pismo.dto.AccountRequestDto;
import com.pismo.dto.AccountResponseDto;
import com.pismo.services.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


@ExtendWith(MockitoExtension.class)
class AccountControllerTest {

    @Mock
    private AccountService accountService;

    @InjectMocks
    private AccountController accountController;

    public final Long ACCOUNT_ID = 1L;
    public final String DOCUMENT_NUMBER = "123456789";

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(accountController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void getAccountByIdWithSuccess() throws Exception {
        // Arrange
        AccountResponseDto responseDto = AccountResponseDto.builder()
                                            .id(ACCOUNT_ID)
                                            .documentNumber(DOCUMENT_NUMBER)
                                            .build();

        when(accountService.getAccountById(ACCOUNT_ID)).thenReturn(responseDto);

        // Do And Assert
        mockMvc.perform(get("/accounts/{accountId}", ACCOUNT_ID)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.account_id").value(ACCOUNT_ID))
            .andExpect(jsonPath("$.document_number").value(DOCUMENT_NUMBER));

        verify(accountService, times(1)).getAccountById(ACCOUNT_ID);
    }

    @Test
    void createAccountWithSuccess() throws Exception {
        // Arrange
        AccountRequestDto requestDto = AccountRequestDto.builder().documentNumber(DOCUMENT_NUMBER).build();
        AccountResponseDto responseDto = AccountResponseDto.builder().id(ACCOUNT_ID).documentNumber(DOCUMENT_NUMBER).build();

        when(accountService.createAccount(any(AccountRequestDto.class))).thenReturn(responseDto);

        // Do and Asserts
        mockMvc.perform(post("/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.account_id").value(ACCOUNT_ID))
            .andExpect(jsonPath("$.document_number").value(DOCUMENT_NUMBER));

        verify(accountService, times(1)).createAccount(any(AccountRequestDto.class));
    }
}
