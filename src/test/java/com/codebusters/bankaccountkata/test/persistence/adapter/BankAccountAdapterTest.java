package com.codebusters.bankaccountkata.test.persistence.adapter;

import com.codebusters.bankaccountkata.domain.exception.BankAccountDepositException;
import com.codebusters.bankaccountkata.domain.model.Account;
import com.codebusters.bankaccountkata.domain.model.Operation;
import com.codebusters.bankaccountkata.domain.model.OperationType;
import com.codebusters.bankaccountkata.domain.model.Transaction;
import com.codebusters.bankaccountkata.persistence.adapter.BankAccountAdapter;
import com.codebusters.bankaccountkata.persistence.repository.AccountRepository;
import com.codebusters.bankaccountkata.persistence.repository.HistoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
public class BankAccountAdapterTest {
    @Mock
    private AccountRepository accountRepository;

    @Mock
    private HistoryRepository historyRepository;

    @InjectMocks
    private BankAccountAdapter bankAccountAdapter;

    private static final int AMOUNT = 1000;
    private static final String CLIENT_ID = "clientId";
    private static final Operation HISTORY = Operation.builder().amount(AMOUNT).operation(OperationType.DEPOSIT).build();
    private static final Transaction TRANSACTION = Transaction.builder().clientId(CLIENT_ID).amount(AMOUNT).build();

    @Test
    public void test_save() throws BankAccountDepositException {
        ArgumentCaptor<String> clientIdCapture = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> transactionClientIdCapture = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Integer> amountCapture = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Operation> historyCapture = ArgumentCaptor.forClass(Operation.class);

        doNothing().when(accountRepository).deposit(amountCapture.capture(),  clientIdCapture.capture());
        when(accountRepository.findByAccountId(CLIENT_ID)).thenReturn(Account.builder().clientId(CLIENT_ID).balance(0).build());
        doNothing().when(historyRepository).save(transactionClientIdCapture.capture(), historyCapture.capture());
        when(accountRepository.contains(CLIENT_ID)).thenReturn(true);

        var actual = bankAccountAdapter.save(TRANSACTION);

        Assertions.assertEquals(CLIENT_ID, clientIdCapture.getValue());
        Assertions.assertEquals(CLIENT_ID, transactionClientIdCapture.getValue());
        Assertions.assertEquals(1000, amountCapture.getValue());
        Assertions.assertEquals(HISTORY.getAmount(), historyCapture.getValue().getAmount());
        Assertions.assertEquals(HISTORY.getAmount(), actual.getAmount());
    }

    @Test
    public void test_save_when_client_is_new() {
        doNothing().when(accountRepository).deposit(AMOUNT,  CLIENT_ID);
        when(accountRepository.findByAccountId(CLIENT_ID)).thenReturn(Account.builder().clientId(CLIENT_ID).balance(0).build());
        doNothing().when(historyRepository).save(CLIENT_ID, HISTORY);
        when(accountRepository.contains(CLIENT_ID)).thenReturn(false);

        var exception = assertThrows(BankAccountDepositException.class, () -> bankAccountAdapter.save(TRANSACTION));
        var actual = exception.getMessage();
        var expected = "client doesn't exist so we create a new Account with the client id given";

        Assertions.assertEquals(expected, actual);
    }
}
