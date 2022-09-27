package com.codebusters.bankaccountkata.test.domain.service;

import com.codebusters.bankaccountkata.domain.exception.BankAccountException;
import com.codebusters.bankaccountkata.domain.model.Operation;
import com.codebusters.bankaccountkata.domain.model.OperationType;
import com.codebusters.bankaccountkata.domain.model.OperationRequest;
import com.codebusters.bankaccountkata.domain.port.BankAccountPort;
import com.codebusters.bankaccountkata.domain.service.BankAccountService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;

import static org.mockito.Mockito.when;

@SpringBootTest
public class BankAccountServiceTest {
    @Mock
    private BankAccountPort bankAccountPort;

    @InjectMocks
    private BankAccountService bankAccountService;

    private static final Instant NOW = Instant.now();
    private static final int AMOUNT = 1000;
    private static final String CLIENT_ID = "clientId";
    private static final Operation OPERATION_WHEN_CLIENT_ID_NOT_NULL_AND_IS_DEPOSIT = Operation.builder().amount(AMOUNT).operation(OperationType.DEPOSIT).operationDate(NOW).build();
    private static final OperationRequest OPERATION_REQUEST_WHEN_CLIENT_ID_NOT_NULL = OperationRequest.builder().clientId(CLIENT_ID).amount(AMOUNT).build();
    private static final Operation OPERATION_WHEN_OPERATION_IS_WITHDRAWAL = Operation.builder().amount(AMOUNT).operation(OperationType.WITHDRAWAL).operationDate(NOW).build();



    @Test
    public void test_make_deposit_while_client_id_not_null() throws BankAccountException {
        when(bankAccountPort.save(OPERATION_REQUEST_WHEN_CLIENT_ID_NOT_NULL)).thenReturn(OPERATION_WHEN_CLIENT_ID_NOT_NULL_AND_IS_DEPOSIT);

        var actualHistory = bankAccountService.makeDeposit(OPERATION_REQUEST_WHEN_CLIENT_ID_NOT_NULL);

        Assertions.assertEquals(OPERATION_WHEN_CLIENT_ID_NOT_NULL_AND_IS_DEPOSIT, actualHistory);
    }

    @Test
    public void test_withdrawal() throws BankAccountException {
        when(bankAccountPort.withdrawMoney(OPERATION_REQUEST_WHEN_CLIENT_ID_NOT_NULL)).thenReturn(OPERATION_WHEN_OPERATION_IS_WITHDRAWAL);

        var actualOperation = bankAccountService.withdrawal(OPERATION_REQUEST_WHEN_CLIENT_ID_NOT_NULL);

        Assertions.assertEquals(OPERATION_WHEN_OPERATION_IS_WITHDRAWAL, actualOperation);
    }
}
