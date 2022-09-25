package com.codebusters.bankaccountkata.domain.port;

import com.codebusters.bankaccountkata.domain.model.History;
import com.codebusters.bankaccountkata.domain.model.Transaction;

public interface BankAccountPort {
    History save(Transaction transaction);
}
