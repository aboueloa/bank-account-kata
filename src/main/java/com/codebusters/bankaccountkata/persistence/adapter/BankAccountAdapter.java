package com.codebusters.bankaccountkata.persistence.adapter;

import com.codebusters.bankaccountkata.domain.exception.BankAccountException;
import com.codebusters.bankaccountkata.domain.model.Operation;
import com.codebusters.bankaccountkata.domain.model.OperationHistory;
import com.codebusters.bankaccountkata.domain.model.OperationRequest;
import com.codebusters.bankaccountkata.domain.model.OperationType;
import com.codebusters.bankaccountkata.domain.port.BankAccountPort;
import com.codebusters.bankaccountkata.persistence.repository.OperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class BankAccountAdapter implements BankAccountPort {
    private final OperationRepository operationRepository;

    private static final ReentrantLock LOCK = new ReentrantLock();

    @Autowired
    public BankAccountAdapter(OperationRepository operationRepository) {
        this.operationRepository = operationRepository;
    }

    @Override
    public Operation save(OperationRequest operationRequest) throws BankAccountException {
        checkIfClientExist(operationRequest.getClientId());
        LOCK.lock();
        try {
            var operation = Operation.builder()
                    .amount(operationRequest.getAmount())
                    .operationDate(Instant.now())
                    .operation(OperationType.DEPOSIT)
                    .build();
            operationRepository.save(operationRequest.getClientId(), operation);
            return operation;
        }
        finally {
            LOCK.unlock();
        }
    }

    @Override
    public Operation withdrawMoney(OperationRequest operationRequest) throws BankAccountException {
        checkIfClientExist(operationRequest.getClientId());
        LOCK.lock();
        try {
            int balance = operationRepository.computeBalance(operationRequest.getClientId());
            if(balance < operationRequest.getAmount()) {
                throw new BankAccountException("insufficient balance");
            }
            var operation = Operation.builder()
                    .amount(-operationRequest.getAmount())
                    .operationDate(Instant.now())
                    .operation(OperationType.WITHDRAWAL)
                    .build();
            operationRepository.save(operationRequest.getClientId(), operation);
            return operation;
        }
        finally {
            LOCK.unlock();
        }
    }

    @Override
    public OperationHistory getOperationHistory(String clientId) {
        return null;
    }

    private void checkIfClientExist(String clientId) throws BankAccountException {
        if(!operationRepository.contains(clientId)) {
            throw new BankAccountException("Client doesn't exist");
        }
    }
}












