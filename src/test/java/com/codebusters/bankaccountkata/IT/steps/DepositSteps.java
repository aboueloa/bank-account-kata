package com.codebusters.bankaccountkata.IT.steps;

import com.codebusters.bankaccountkata.domain.model.History;
import com.codebusters.bankaccountkata.domain.model.Transaction;
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

    private History expectedHistory;
    private History actualHistory;


    @Given("^the following transaction$")
    public void givenTheFollowingTransaction(Transaction transaction) {
    }

    @When("^the user make a deposit$")
    public void whenTheUserMakeADeposit() throws JsonProcessingException {
        actualHistory = objectMapper.readValue(
                testRestTemplate.getForEntity("/api/bank-account/deposit", String.class)
                        .getBody(), History.class
        );
    }

    @Then("^we return the following history$")
    public void thenWeReturnTheFollowingHistory(History history){
        expectedHistory = history;
        validateDeposit();
    }

    private void validateDeposit() {
        Assertions.assertEquals(expectedHistory, actualHistory);
    }
}
