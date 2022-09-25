package com.codebusters.bankaccountkata.IT.steps;

import com.codebusters.bankaccountkata.api.controller.dto.OperationRequest;
import com.codebusters.bankaccountkata.domain.model.Operation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;

public class DepositSteps {
    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private  ObjectMapper objectMapper;

    private Operation expectedOperation;
    private Operation actualOperation;


    @Given("^the following operation$")
    public void givenTheFollowingTransaction(OperationRequest transaction) {
    }

    @When("^the user make a deposit$")
    public void whenTheUserMakeADeposit() throws JsonProcessingException {
        actualOperation = objectMapper.readValue(
                testRestTemplate.getForEntity("/api/bank-account/deposit", String.class)
                        .getBody(), Operation.class
        );
    }

    @Then("^we return the following history$")
    public void thenWeReturnTheFollowingHistory(Operation operation){
        expectedOperation = operation;
        validateDeposit();
    }

    private void validateDeposit() {
        Assertions.assertEquals(expectedOperation, actualOperation);
    }
}
