package com.codebusters.bankaccountkata.IT.steps;

import com.codebusters.bankaccountkata.domain.exception.BankAccountException;
import com.codebusters.bankaccountkata.domain.model.OperationHistory;
import com.codebusters.bankaccountkata.domain.service.BankAccountService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class HistorySteps {
    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private ObjectMapper objectMapper;
    private OperationHistory actualOperation;

    @When("^a client (.*) want to see his history$")
    public void whenTheClientWantToSeeTheHistory(String client) throws JsonProcessingException {
        actualOperation = objectMapper.readValue(
                testRestTemplate.getForEntity("/api/bank-operation/history/ayman.aboueloula", String.class)
                        .getBody(), OperationHistory.class);
    }
    @Then("^we return the following history operation$")
    public void whenWeReturnTheFollowingHistoryOperation(OperationHistory operationHistory) {
        Assertions.assertEquals(operationHistory.getBalance(), actualOperation.getBalance());
    }
}
