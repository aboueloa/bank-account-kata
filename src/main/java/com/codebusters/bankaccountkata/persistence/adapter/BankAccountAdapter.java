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
        return null;
    }
}
