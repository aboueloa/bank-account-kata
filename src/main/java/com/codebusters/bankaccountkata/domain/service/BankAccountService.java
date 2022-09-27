package com.codebusters.bankaccountkata.domain.service;

import com.codebusters.bankaccountkata.domain.exception.BankAccountException;
import com.codebusters.bankaccountkata.domain.model.Operation;
import com.codebusters.bankaccountkata.domain.model.OperationHistory;
import com.codebusters.bankaccountkata.domain.model.OperationRequest;
import com.codebusters.bankaccountkata.domain.port.BankAccountPort;
import lombok.AllArgsConstructor;
/**
 * service used by the api package independent of spring boot
 */
@AllArgsConstructor
public class BankAccountService {
    private final BankAccountPort bankAccountPort;

    public Operation makeDeposit(OperationRequest operationRequest) throws BankAccountException {
        return bankAccountPort.save(operationRequest);
    }

    public Operation withdrawal(OperationRequest operationRequest) throws BankAccountException {
        return bankAccountPort.withdrawMoney(operationRequest);
    }

    public OperationHistory getHistory(String clientId) {
        return null;
    }
}
