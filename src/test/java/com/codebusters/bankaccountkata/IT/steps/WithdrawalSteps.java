package com.codebusters.bankaccountkata.IT.steps;

import com.codebusters.bankaccountkata.api.dto.OperationRequestDTO;
import com.codebusters.bankaccountkata.domain.exception.BankAccountException;
import com.codebusters.bankaccountkata.domain.model.Operation;
import com.codebusters.bankaccountkata.domain.model.OperationRequest;
import com.codebusters.bankaccountkata.domain.model.OperationType;
import com.codebusters.bankaccountkata.domain.service.BankAccountService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;

@Slf4j
public class WithdrawalSteps {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private BankAccountService bankAccountService;
    private Operation expectedOperation;
    private Operation actualOperation;
    private OperationRequestDTO operationRequestDTO;

    @Given("^the following withdrawal operation$")
    public void givenTheFollowingTransaction(OperationRequestDTO transaction) {
        this.operationRequestDTO = transaction;
    }

    @When("^the user make a withdrawal$")
    public void whenTheUserMakeAWithdrawal() {
        testRestTemplate.postForEntity("/api/bank-operation/withdrawal", operationRequestDTO, OperationRequestDTO.class);
    }

    @Then("^we return the following withdrawal operation")
    public void thenWeReturnTheFollowingOperation(Operation operation) throws BankAccountException {
        actualOperation = bankAccountService.withdrawal(new OperationRequest(operationRequestDTO.getClientId(), operation.getAmount()));
        expectedOperation = Operation.builder().amount(-operation.getAmount()).operation(OperationType.WITHDRAWAL).build();
        validateDeposit();
    }

    private void validateDeposit() {
        Assertions.assertEquals(expectedOperation.getOperation(), actualOperation.getOperation());
        Assertions.assertEquals(expectedOperation.getAmount(), actualOperation.getAmount());
    }

}
