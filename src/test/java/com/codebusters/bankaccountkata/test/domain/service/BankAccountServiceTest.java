package com.codebusters.bankaccountkata.test.domain.service;

import com.codebusters.bankaccountkata.domain.exception.BankAccountDepositException;
import com.codebusters.bankaccountkata.domain.model.Operation;
import com.codebusters.bankaccountkata.domain.model.OperationType;
import com.codebusters.bankaccountkata.domain.model.Transaction;
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
    private static final Operation OPERATION_WHEN_CLIENT_ID_NOT_NULL_AND_IS_DEPOSIT = Operation.builder().amount(AMOUNT).balance(AMOUNT).operation(OperationType.DEPOSIT).operationDate(NOW).build();
    private static final Transaction TRANSACTION_WHEN_CLIENT_ID_NOT_NULL = Transaction.builder().clientId(CLIENT_ID).amount(AMOUNT).build();
    private static final Operation OPERATION_WHEN_OPERATION_IS_WITHDRAWAL = Operation.builder().amount(AMOUNT).balance(AMOUNT).operation(OperationType.WITHDRAWAL).operationDate(NOW).build();



    @Test
    public void test_make_deposit_while_client_id_not_null() throws BankAccountDepositException {
        when(bankAccountPort.save(TRANSACTION_WHEN_CLIENT_ID_NOT_NULL)).thenReturn(OPERATION_WHEN_CLIENT_ID_NOT_NULL_AND_IS_DEPOSIT);

        var actualHistory = bankAccountService.makeDeposit(TRANSACTION_WHEN_CLIENT_ID_NOT_NULL);

        Assertions.assertEquals(OPERATION_WHEN_CLIENT_ID_NOT_NULL_AND_IS_DEPOSIT, actualHistory);
    }

    @Test
    public void test_withdrawal() {
        when(bankAccountPort.withdrawMoney(TRANSACTION_WHEN_CLIENT_ID_NOT_NULL)).thenReturn(OPERATION_WHEN_OPERATION_IS_WITHDRAWAL);

        var actualOperation = bankAccountService.withdrawal(TRANSACTION_WHEN_CLIENT_ID_NOT_NULL);

        Assertions.assertEquals(OPERATION_WHEN_OPERATION_IS_WITHDRAWAL, actualOperation);
    }
}
