package com.codebusters.bankaccountkata.domain.service;

import com.codebusters.bankaccountkata.domain.exception.BankAccountDepositException;
import com.codebusters.bankaccountkata.domain.model.Operation;
import com.codebusters.bankaccountkata.domain.model.Transaction;
import com.codebusters.bankaccountkata.domain.port.BankAccountPort;
import lombok.AllArgsConstructor;
@AllArgsConstructor
public class BankAccountService {
    private final BankAccountPort bankAccountPort;

    public Operation makeDeposit(Transaction transaction) throws BankAccountDepositException {
        return bankAccountPort.save(transaction);
    }

    public Operation withdrawal(Transaction transaction) {
        return null;
    }
}
