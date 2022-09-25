package com.codebusters.bankaccountkata.test.persistence.adapter;

import com.codebusters.bankaccountkata.domain.model.Account;
import com.codebusters.bankaccountkata.domain.model.History;
import com.codebusters.bankaccountkata.domain.model.Operation;
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

import java.time.Instant;

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
    private static final History HISTORY = History.builder().amount(AMOUNT).clientId(CLIENT_ID).operation(Operation.DEPOSIT).build();
    private static final Transaction TRANSACTION = Transaction.builder().clientId(CLIENT_ID).amount(AMOUNT).build();

    @Test
    public void test_save() {
        ArgumentCaptor<String> clientIdCapture = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Integer> amountCapture = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<History> historyCapture = ArgumentCaptor.forClass(History.class);

        doNothing().when(accountRepository).deposit(amountCapture.capture(),  clientIdCapture.capture());
        when(accountRepository.findByAccountId(CLIENT_ID)).thenReturn(Account.builder().clientId(CLIENT_ID).balance(0).build());
        doNothing().when(historyRepository).save(historyCapture.capture());

        var actual = bankAccountAdapter.save(TRANSACTION);

        Assertions.assertEquals("clientId", clientIdCapture.getValue());
        Assertions.assertEquals(1000, amountCapture.getValue());
        Assertions.assertEquals(HISTORY.getAmount(), historyCapture.getValue().getAmount());
        Assertions.assertEquals(HISTORY.getClientId(), historyCapture.getValue().getClientId());
        Assertions.assertEquals(HISTORY.getAmount(), actual.getAmount());
        Assertions.assertEquals(HISTORY.getClientId(), actual.getClientId());
    }
}
