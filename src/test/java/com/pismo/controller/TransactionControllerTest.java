package com.pismo.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pismo.dto.TransactionRequestDto;
import com.pismo.dto.TransactionResponseDto;
import com.pismo.services.TransactionService;
import java.time.LocalDateTime;
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
class TransactionControllerTest {

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private TransactionController transactionController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(transactionController).build();
        objectMapper = new ObjectMapper();
    }
    @Test
    void createTransactionWithSuccess() throws Exception {
        // Arrange
        TransactionRequestDto requestDto = TransactionRequestDto.builder()
                                            .accountId(1L)
                                            .operationTypeId(1L)
                                            .amount(100.0)
                                            .build();


        TransactionResponseDto responseDto = TransactionResponseDto.builder()
                                                .transactionId(1L)
                                                .accountId(1L)
                                                .operationTypeId(1L)
                                                .amount(100.0)
                                                .eventDate(LocalDateTime.now()).build();

        when(transactionService.createTransaction(any(TransactionRequestDto.class))).thenReturn(responseDto);

        // Act & Assert
        mockMvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.transaction_id").value(1L))
            .andExpect(jsonPath("$.account_id").value(1L))
            .andExpect(jsonPath("$.operation_type_id").value(1L))
            .andExpect(jsonPath("$.amount").value(100.0));

        verify(transactionService, times(1)).createTransaction(any(TransactionRequestDto.class));
    }

    @Test
    void createTransactionInvalidRequest() throws Exception {
        // Arrange
        TransactionRequestDto requestDto = TransactionRequestDto.builder().build();

        // Act and Asserts
        mockMvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
            .andExpect(status().isBadRequest());
    }
}
