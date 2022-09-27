package com.codebusters.bankaccountkata.test.persistence.adapter;

import com.codebusters.bankaccountkata.domain.exception.BankAccountException;
import com.codebusters.bankaccountkata.domain.model.Operation;
import com.codebusters.bankaccountkata.domain.model.OperationHistory;
import com.codebusters.bankaccountkata.domain.model.OperationType;
import com.codebusters.bankaccountkata.domain.model.OperationRequest;
import com.codebusters.bankaccountkata.persistence.adapter.BankAccountAdapter;
import com.codebusters.bankaccountkata.persistence.repository.OperationRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
public class BankAccountAdapterTest {
    @Mock
    private OperationRepository operationRepository;

    @InjectMocks
    private BankAccountAdapter bankAccountAdapter;

    private static final int AMOUNT = 1000;
    private static final String CLIENT_ID = "clientId";
    private static final Operation HISTORY = Operation.builder().amount(AMOUNT).operation(OperationType.DEPOSIT).build();
    private static final OperationRequest OPERATION_REQUEST = OperationRequest.builder().clientId(CLIENT_ID).amount(AMOUNT).build();


    @Test
    public void test_save() throws BankAccountException {
        ArgumentCaptor<String> transactionClientIdCapture = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Operation> historyCapture = ArgumentCaptor.forClass(Operation.class);

        when(operationRepository.computeBalance(CLIENT_ID)).thenReturn(0);
        doNothing().when(operationRepository).save(transactionClientIdCapture.capture(), historyCapture.capture());
        when(operationRepository.contains(CLIENT_ID)).thenReturn(true);

        var actual = bankAccountAdapter.save(OPERATION_REQUEST);

        Assertions.assertEquals(CLIENT_ID, transactionClientIdCapture.getValue());
        Assertions.assertEquals(HISTORY.getAmount(), historyCapture.getValue().getAmount());

        Assertions.assertEquals(HISTORY.getAmount(), actual.getAmount());
    }

    @Test
    public void test_save_when_client_is_new() {
        when(operationRepository.contains(CLIENT_ID)).thenReturn(false);

        var exception = assertThrows(BankAccountException.class, () -> bankAccountAdapter.save(OPERATION_REQUEST));
        var actual = exception.getMessage();
        var expected = "Client doesn't exist";

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void test_withdrawal_when_client_is_new() {
        when(operationRepository.contains(CLIENT_ID)).thenReturn(false);

        var exception = assertThrows(BankAccountException.class, () -> bankAccountAdapter.withdrawMoney(OPERATION_REQUEST));
        var actual = exception.getMessage();
        var expected = "Client doesn't exist";

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void test_withdrawal_when_not_enough() {
        when(operationRepository.contains(CLIENT_ID)).thenReturn(true);
        when(operationRepository.computeBalance(CLIENT_ID)).thenReturn(0);

        var exception = assertThrows(BankAccountException.class, () -> bankAccountAdapter.withdrawMoney(OPERATION_REQUEST));
        var expected = "insufficient balance";
        Assertions.assertEquals(expected, exception.getMessage());
    }

    @Test
    public void test_withdrawal() throws BankAccountException {
        ArgumentCaptor<String> transactionClientIdCapture = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Operation> historyCapture = ArgumentCaptor.forClass(Operation.class);

        when(operationRepository.computeBalance(CLIENT_ID)).thenReturn(2000);
        doNothing().when(operationRepository).save(transactionClientIdCapture.capture(), historyCapture.capture());
        when(operationRepository.contains(CLIENT_ID)).thenReturn(true);

        var actual = bankAccountAdapter.withdrawMoney(OPERATION_REQUEST);

        Assertions.assertEquals(CLIENT_ID, transactionClientIdCapture.getValue());
        Assertions.assertEquals(-HISTORY.getAmount(), historyCapture.getValue().getAmount());

        Assertions.assertEquals(HISTORY.getAmount(), -actual.getAmount());
    }

    @Test
    public void test_operation_history_when_client_is_new() {
        when(operationRepository.contains(CLIENT_ID)).thenReturn(false);

        var exception = assertThrows(BankAccountException.class, () -> bankAccountAdapter.getOperationHistory(CLIENT_ID));
        var actual = exception.getMessage();
        var expected = "Client doesn't exist";

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void test_operation_history() throws BankAccountException {
        when(operationRepository.computeBalance(CLIENT_ID)).thenReturn(2000);
        when(operationRepository.contains(CLIENT_ID)).thenReturn(true);
        when(operationRepository.getOperation(CLIENT_ID)).thenReturn(List.of(HISTORY));

        var actual = bankAccountAdapter.getOperationHistory(CLIENT_ID);

        assertThat(actual.getOperations()).extracting(Operation::getAmount).containsExactly(AMOUNT);
        assertThat(actual.getOperations()).extracting(Operation::getOperation).containsExactly(OperationType.DEPOSIT);
    }
}
