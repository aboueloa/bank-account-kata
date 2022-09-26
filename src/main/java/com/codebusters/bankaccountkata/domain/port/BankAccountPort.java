package com.codebusters.bankaccountkata.domain.port;

import com.codebusters.bankaccountkata.domain.exception.BankAccountDepositException;
import com.codebusters.bankaccountkata.domain.model.Operation;
import com.codebusters.bankaccountkata.domain.model.Transaction;

public interface BankAccountPort {
    Operation save(Transaction transaction) throws BankAccountDepositException;
}
