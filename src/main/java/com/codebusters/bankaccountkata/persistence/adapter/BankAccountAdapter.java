package com.codebusters.bankaccountkata.persistence.adapter;

import com.codebusters.bankaccountkata.domain.model.History;
import com.codebusters.bankaccountkata.domain.model.Transaction;
import com.codebusters.bankaccountkata.domain.port.BankAccountPort;
import org.springframework.stereotype.Service;

@Service
public class BankAccountAdapter implements BankAccountPort {
    @Override
    public History save(Transaction transaction) {
        return null;
    }
}
