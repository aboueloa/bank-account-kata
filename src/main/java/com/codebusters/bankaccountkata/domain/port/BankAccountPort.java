package com.codebusters.bankaccountkata.domain.port;

import com.codebusters.bankaccountkata.domain.exception.BankAccountException;
import com.codebusters.bankaccountkata.domain.model.Operation;
import com.codebusters.bankaccountkata.domain.model.OperationHistory;
import com.codebusters.bankaccountkata.domain.model.OperationRequest;

public interface BankAccountPort {
    Operation save(OperationRequest operationRequest) throws BankAccountException;

    Operation withdrawMoney(OperationRequest operationRequest) throws BankAccountException;

    OperationHistory getOperationHistory(String clientId) throws BankAccountException;
}
