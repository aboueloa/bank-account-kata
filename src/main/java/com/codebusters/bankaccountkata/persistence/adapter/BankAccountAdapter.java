package com.codebusters.bankaccountkata.persistence.adapter;

import com.codebusters.bankaccountkata.domain.exception.BankAccountDepositException;
import com.codebusters.bankaccountkata.domain.model.Operation;
import com.codebusters.bankaccountkata.domain.model.OperationType;
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
    public Operation save(Transaction transaction) throws BankAccountDepositException {
        LOCK.lock();
        Operation operation;
        boolean clientExist;
        try {
            clientExist = accountRepository.contains(transaction.getClientId());
            accountRepository.deposit(transaction.getAmount(), transaction.getClientId());
            int balance = accountRepository.findByAccountId(transaction.getClientId()).getBalance();
            operation = Operation.builder()
                    .amount(transaction.getAmount())
                    .balance(balance)
                    .operationDate(Instant.now())
                    .operation(OperationType.DEPOSIT)
                    .build();
            historyRepository.save(transaction.getClientId(), operation);
            if(!clientExist) {
                throw new BankAccountDepositException("client doesn't exist so we create a new Account with the client id given", operation);
            }
            return operation;
        }
        finally {
            LOCK.unlock();
        }
    }
}
