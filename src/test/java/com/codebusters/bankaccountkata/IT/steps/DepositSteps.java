package com.codebusters.bankaccountkata.IT.steps;

import com.codebusters.bankaccountkata.api.dto.OperationRequest;
import com.codebusters.bankaccountkata.domain.exception.BankAccountDepositException;
import com.codebusters.bankaccountkata.domain.model.Operation;
import com.codebusters.bankaccountkata.domain.model.Transaction;
import com.codebusters.bankaccountkata.domain.service.BankAccountService;
import com.fasterxml.jackson.core.JsonProcessingException;
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
    private BankAccountService bankAccountService;

    private Operation expectedOperation;
    private Operation actualOperation;
    private OperationRequest operationRequest;


    @Given("^the following operation$")
    public void givenTheFollowingTransaction(OperationRequest transaction) {
        this.operationRequest = transaction;
    }

    @When("^the user make a deposit$")
    public void whenTheUserMakeADeposit() {
        testRestTemplate.postForEntity("/api/bank-operation/deposit", operationRequest, OperationRequest.class);
    }

    @Then("^we return the following history$")
    public void thenWeReturnTheFollowingHistory(Operation operation) throws BankAccountDepositException {
        actualOperation = bankAccountService.makeDeposit(new Transaction(operationRequest.getClientId(), operation.getAmount()));
        expectedOperation = operation;
        validateDeposit();
    }

    private void validateDeposit() {
        Assertions.assertEquals(expectedOperation.getOperation(), actualOperation.getOperation());
        Assertions.assertEquals(expectedOperation.getAmount(), actualOperation.getAmount());
        Assertions.assertEquals(expectedOperation.getBalance(), actualOperation.getBalance());
    }
}
