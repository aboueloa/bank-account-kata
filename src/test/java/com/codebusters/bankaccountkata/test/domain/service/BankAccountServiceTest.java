package com.codebusters.bankaccountkata.test.domain.service;

import com.codebusters.bankaccountkata.domain.model.History;
import com.codebusters.bankaccountkata.domain.model.Operation;
import com.codebusters.bankaccountkata.domain.model.Transaction;
import com.codebusters.bankaccountkata.domain.port.BankAccountPort;
import com.codebusters.bankaccountkata.domain.service.BankAccountService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertThrows;
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
    private static final History HISTORY_WHEN_CLIENT_ID_NOT_NULL = History.builder().clientId(CLIENT_ID).amount(AMOUNT).balance(AMOUNT).operation(Operation.DEPOSIT).operationDate(NOW).build();
    private static final Transaction TRANSACTION_WHEN_CLIENT_ID_NOT_NULL = Transaction.builder().clientId(CLIENT_ID).amount(AMOUNT).build();
    private static final Transaction TRANSACTION_WHEN_CLIENT_ID_IS_NULL = Transaction.builder().clientId(null).amount(AMOUNT).build();



    @Test
    public void test_make_deposit_while_client_id_not_null() {
        when(bankAccountPort.save(TRANSACTION_WHEN_CLIENT_ID_NOT_NULL)).thenReturn(HISTORY_WHEN_CLIENT_ID_NOT_NULL);

        var actualHistory = bankAccountService.makeDeposit(TRANSACTION_WHEN_CLIENT_ID_NOT_NULL);

        Assertions.assertEquals(HISTORY_WHEN_CLIENT_ID_NOT_NULL, actualHistory);
    }

    @Test
    public void test_make_deposit_while_client_id_is_null() {
        Exception exception = assertThrows(NullPointerException.class, () -> bankAccountService.makeDeposit(TRANSACTION_WHEN_CLIENT_ID_IS_NULL));

        var expectedMsg = "client id shouldn't be null";
        var actualMsg = exception.getMessage();

        Assertions.assertEquals(expectedMsg, actualMsg);
    }
}
