package com.codebusters.bankaccountkata.IT.steps;

import com.codebusters.bankaccountkata.api.dto.OperationRequestDTO;
import com.codebusters.bankaccountkata.domain.exception.BankAccountException;
import com.codebusters.bankaccountkata.domain.model.Operation;
import com.codebusters.bankaccountkata.domain.model.OperationRequest;
import com.codebusters.bankaccountkata.domain.service.BankAccountService;
import io.cucumber.java.After;
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
    private OperationRequestDTO operationRequestDTO;

    @After(value = "@Deposit")
    public void after() throws BankAccountException {
        bankAccountService.withdrawal(new OperationRequest("ayman.aboueloula", 1000));
    }
    @Given("^the following deposit operation$")
    public void givenTheFollowingTransaction(OperationRequestDTO transaction) {
        this.operationRequestDTO = transaction;
    }

    @When("^the user make a deposit$")
    public void whenTheUserMakeADeposit() {
        actualOperation = testRestTemplate.postForEntity("/api/bank-operation/deposit", operationRequestDTO, Operation.class).getBody();
    }

    @Then("^we return the following deposit operation")
    public void thenWeReturnTheFollowingOperation(Operation operation) {
        expectedOperation = operation;
        validateDeposit();
    }

    private void validateDeposit() {
        Assertions.assertEquals(expectedOperation.getOperation(), actualOperation.getOperation());
        Assertions.assertEquals(expectedOperation.getAmount(), actualOperation.getAmount());
    }
}
