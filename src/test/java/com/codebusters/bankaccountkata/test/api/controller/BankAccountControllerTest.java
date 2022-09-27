package com.codebusters.bankaccountkata.test.api.controller;

import com.codebusters.bankaccountkata.api.controller.BankAccountController;
import com.codebusters.bankaccountkata.domain.exception.BankAccountException;
import com.codebusters.bankaccountkata.domain.model.Operation;
import com.codebusters.bankaccountkata.domain.model.OperationHistory;
import com.codebusters.bankaccountkata.domain.model.OperationType;
import com.codebusters.bankaccountkata.domain.model.OperationRequest;
import com.codebusters.bankaccountkata.domain.service.BankAccountService;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class BankAccountControllerTest {
    private static final String API_BANK_ACCOUNT_DEPOSIT = "/api/bank-operation/deposit";

    private static final String API_BANK_ACCOUNT_WITHDRAWAL = "/api/bank-operation/withdrawal";
    private static final String API_BANK_ACCOUNT_HISTORY = "/api/bank-operation/history/clientId";


    private static final String CLIENT_ID = "clientId";
    private static final int AMOUNT = 100;
    private static final Operation DEPOSIT_OPERATION = Operation.builder().operation(OperationType.DEPOSIT).amount(AMOUNT).build();

    private static final Operation WITHDRAWAL_OPERATION = Operation.builder().operation(OperationType.WITHDRAWAL).amount(AMOUNT).build();

    private static final OperationRequest OPERATION_REQUEST = new OperationRequest(CLIENT_ID, AMOUNT);
    public static final OperationHistory OPERATIONS = OperationHistory.builder().balance(0).operations(new ArrayList<>()).build();
    @MockBean
    private BankAccountService bankAccountService;

    @Autowired
    private BankAccountController bankAccountController;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void when_post_request_to_deposit_and_invalid_operationRequest() throws Exception {
        String operationRequest = "{\"clientId\": \"\", \"amount\" : -1}";
        mockMvc.perform(MockMvcRequestBuilders.post(API_BANK_ACCOUNT_DEPOSIT)
                        .content(operationRequest)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.clientId", Is.is("client id must be not null")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.amount", Is.is("amount must be > 0")))
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    public void when_post_request_to_deposit_and_new_client_is_created() throws Exception {
        when(bankAccountService.makeDeposit(OPERATION_REQUEST)).thenThrow(new BankAccountException("client doesn't exist"));
        String operationRequest = "{\"clientId\": \"clientId\", \"amount\" : 100}";
        var mockRes = mockMvc.perform(MockMvcRequestBuilders.post(API_BANK_ACCOUNT_DEPOSIT)
                        .content(operationRequest)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn();
        Assertions.assertEquals("client doesn't exist", mockRes.getResponse().getContentAsString());
    }

    @Test
    public void when_post_request_to_withdrawal() throws Exception {
        when(bankAccountService.withdrawal(OPERATION_REQUEST)).thenReturn(WITHDRAWAL_OPERATION);
        String operationRequest = "{\"clientId\": \"clientId\", \"amount\" : 100}";
        var mockRes = mockMvc.perform(MockMvcRequestBuilders.post(API_BANK_ACCOUNT_WITHDRAWAL)
                        .content(operationRequest)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andReturn();
        String actualRes = mockRes.getResponse().getContentAsString();
        String expectedRes = objectMapper.writeValueAsString(WITHDRAWAL_OPERATION);
        assertThat(expectedRes).isEqualToIgnoringWhitespace(actualRes);
    }

    @Test
    public void when_post_request_to_getHistory() throws Exception {
        when(bankAccountService.getHistory(CLIENT_ID)).thenReturn(OPERATIONS);
        var mockRes = mockMvc.perform(MockMvcRequestBuilders.get(API_BANK_ACCOUNT_HISTORY)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andReturn();
        String actualRes = mockRes.getResponse().getContentAsString();
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        String expectedRes = objectMapper.writeValueAsString(OPERATIONS);
        assertThat(expectedRes).isEqualToIgnoringWhitespace(actualRes);
    }

}
