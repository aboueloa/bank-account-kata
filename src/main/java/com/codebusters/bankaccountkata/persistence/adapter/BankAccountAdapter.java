package com.codebusters.bankaccountkata.persistence.adapter;

import com.codebusters.bankaccountkata.domain.model.History;
import com.codebusters.bankaccountkata.domain.model.Operation;
import com.codebusters.bankaccountkata.domain.model.Transaction;
import com.codebusters.bankaccountkata.domain.port.BankAccountPort;
import com.codebusters.bankaccountkata.persistence.repository.AccountRepository;
import com.codebusters.bankaccountkata.persistence.repository.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class BankAccountAdapter implements BankAccountPort {
    private final AccountRepository accountRepository;
    private final HistoryRepository historyRepository;

    private static final ReentrantLock LOCK = new ReentrantLock();

    @Autowired
    public BankAccountAdapter(AccountRepository accountRepository, HistoryRepository historyRepository) {
        this.accountRepository = accountRepository;
        this.historyRepository = historyRepository;
    }

    @Override
    public History save(Transaction transaction) {
        LOCK.lock();
        History history;
        try {
            accountRepository.deposit(transaction.getAmount(), transaction.getClientId());
            int balance = accountRepository.findByAccountId(transaction.getClientId()).getBalance();
            history = History.builder()
                    .amount(transaction.getAmount())
                    .balance(balance)
                    .operationDate(Instant.now())
                    .clientId(transaction.getClientId())
                    .operation(Operation.DEPOSIT)
                    .build();
            historyRepository.save(history);
        }
        finally {
            LOCK.unlock();
        }
        return history;
    }
}
